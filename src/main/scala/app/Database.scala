package app

import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.{MongoClient, MongoCollection}
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.model.Filters.equal

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


case class Pony(_id: ObjectId, name: String, id: Int)
object Pony {
  def apply (name: String, id: Int): Pony =
    Pony(new ObjectId, name, id)
}
case class Quote(_id: ObjectId, author: Pony, text: String, id: Int)
object Quote {
  def apply (author: Pony, text: String, id: Int): Quote =
    Quote(new ObjectId, author, text, id)
}

trait Database {
  def saveQuote (quote: Quote): Future[Quote]
  def findQuotes (): Future[Seq[Quote]]
  def findQuote (id: Int): Future[Option[Quote]]
}

object DatabaseImpl extends Database {
  private val codecRegistry = fromRegistries(
    fromProviders(classOf[Quote], classOf[Pony]),
    DEFAULT_CODEC_REGISTRY)
  private val client = MongoClient(sys.env("MONGO_URL"))
  private val db = client.getDatabase("ponies")
    .withCodecRegistry(codecRegistry)
  private val quotes: MongoCollection[Quote] =
    db.getCollection("quotes")


  def saveQuote (quote: Quote): Future[Quote] = {
    quotes.insertOne(quote).toFuture() map { _ => quote }
  }

  def findQuotes (): Future[Seq[Quote]] = {
    quotes.find().toFuture()
  }

  def findQuote (id: Int): Future[Option[Quote]] = {
    quotes.find(equal("id", id)).first().toFutureOption()
  }
}

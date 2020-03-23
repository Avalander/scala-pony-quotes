package app.store

import org.mongodb.scala.bson.codecs.Macros
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.{MongoClient, MongoCollection}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


object StoreImpl extends Store {
  private val codecRegistry = fromRegistries(
    fromProviders(
      Macros.createCodecProvider[Pony](),
      Macros.createCodecProvider[Quote]()
    ),
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

  def findQuotesByAuthor (id: Int): Future[Seq[Quote]] = {
    quotes.find(equal("author.id", id)).toFuture()
  }
}

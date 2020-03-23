package app

import org.mongodb.scala.bson.ObjectId

import scala.concurrent.Future

package object store {
  final case class Pony(_id: ObjectId, name: String, id: Int)
  object Pony {
    def apply (name: String, id: Int): Pony =
      Pony(new ObjectId, name, id)
  }
  case class Quote(_id: ObjectId, author: Pony, text: String, id: Int)
  object Quote {
    def apply (author: Pony, text: String, id: Int): Quote =
      Quote(new ObjectId, author, text, id)
  }

  trait Store {
    def saveQuote (quote: Quote): Future[Quote]
    def findQuotes (): Future[Seq[Quote]]
    def findQuote (id: Int): Future[Option[Quote]]
    def findQuotesByAuthor (id: Int): Future[Seq[Quote]]
  }
}

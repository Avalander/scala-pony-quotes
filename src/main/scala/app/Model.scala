package app

import spray.json.DefaultJsonProtocol._

object Model {
  final case class PonyResponse(name: String, id: Int) {
    def asDb (): Pony = Pony(name, id)
  }
  final case class QuoteResponse(author: PonyResponse, text: String, id: Int) {
    def asDb (): Quote = Quote(author.asDb, text, id)
  }

  final case class GetQuotesResponse(items: Seq[QuoteResponse])

  object implicits {
    implicit val ponyFormat = jsonFormat2(PonyResponse)
    implicit val quoteFormat = jsonFormat3(QuoteResponse)
    implicit val quoteListFormat = jsonFormat1(GetQuotesResponse)

    implicit class ExtPony(p: Pony) {
      def asModel (): PonyResponse = {
        val Pony(_, name, id) = p
        PonyResponse(name, id)
      }
    }

    implicit class ExtQuote(q: Quote) {
      def asModel (): QuoteResponse = {
        val Quote(_, author, text, id) = q
        QuoteResponse(author.asModel, text, id)
      }
    }

    implicit class ExtQuoteSeq (qs: Seq[Quote]) {
      def asModel (): Seq[QuoteResponse] = {
        qs map (_.asModel)
      }
    }
  }
}

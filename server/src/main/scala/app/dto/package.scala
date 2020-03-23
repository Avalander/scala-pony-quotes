package app

import spray.json.DefaultJsonProtocol._

package object dto {
  final case class Pony(id: Int, name: String)
  final case class Quote(id: Int, author: Pony, text: String)
  final case class QuoteList(items: Seq[Quote])

  object Converter {
    implicit def ponyToDb(p: Pony): store.Pony = {
      val Pony(id, name) = p
      store.Pony(name, id)
    }

    implicit def ponyToDto(p: store.Pony): Pony = {
      val store.Pony(_, name, id) = p
      Pony(id, name)
    }

    implicit def quoteToDb(q: Quote): store.Quote = {
      val Quote(id, author, text) = q
      store.Quote(author, text, id)
    }

    implicit def quoteToDto(q: store.Quote): Quote = {
      val store.Quote(_, author, text, id) = q
      Quote(id, author, text)
    }

    implicit def quotesToDb(qs: Seq[Quote]): Seq[store.Quote] = {
      qs map quoteToDb
    }

    implicit def quotesToDto(qs: Seq[store.Quote]): Seq[Quote] = {
      qs map quoteToDto
    }

    implicit val ponyFormat = jsonFormat2(Pony)
    implicit val quoteFormat = jsonFormat3(Quote)
    implicit val quoteListFormat = jsonFormat1(QuoteList)
  }
}

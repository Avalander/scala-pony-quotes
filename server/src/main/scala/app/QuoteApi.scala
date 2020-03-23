package app

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import app.dto.Converter._
import app.store._


trait QuoteApi {
  val db: Store

  val route: Route = concat(
    path("quote") {
      concat(
        get {
          parameters((Symbol("author").as[Int])) {
            case (id) =>
              val quotes = db.findQuotesByAuthor(id)
              onSuccess(quotes) { q =>
                complete(dto.QuoteList(q))
              }
          }
        },
        get {
          val quotes = db.findQuotes()
          onSuccess(quotes) { q =>
            complete(dto.QuoteList(q))
          }
        },
        post {
          entity(as[dto.Quote]) { quote =>
            val saved = db.saveQuote(quote)
            onComplete(saved) { _ =>
              complete("Quote created")
            }
          }
        }
      )
    },
    get {
      pathPrefix("quote" / IntNumber) { id =>
        val quote = db.findQuote(id)
        onSuccess(quote) {
          case Some(q) => complete(quoteToDto(q))
          case None    => complete(StatusCodes.NotFound)
        }
      }
    },
  )
}

package app

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import app.Model._
import app.Model.implicits._

import scala.io.StdIn


trait PonyQuoteRoute {
  val db: Database
  val route: Route = concat(
    path("quote") {
      concat(
        get {
          val quotes = db.findQuotes()
          onSuccess(quotes) { q =>
            complete(GetQuotesResponse(q.asModel()))
          }
        },
        post {
          entity(as[QuoteResponse]) { quote =>
            val saved = db.saveQuote(quote.asDb())
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
          case Some(q) => complete(q.asModel())
          case None    => complete(StatusCodes.NotFound)
        }
      }
    },
  )
}

object PonyQuotes extends App {
  implicit val system = ActorSystem("pony-quotes")
  implicit val executionContext = system.dispatcher

  // Routes

  val ponyApi = new PonyQuoteRoute {
    override val db: Database = DatabaseImpl
  }

  val route: Route = ponyApi.route

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println("Server listening on port 8080")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}

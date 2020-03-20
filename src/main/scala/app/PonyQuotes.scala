package app

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.io.StdIn

import Database._
import Model._
import Model.implicits._


object PonyQuotes extends App {
  implicit val system = ActorSystem("pony-quotes")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  // Routes

  val route: Route = concat(
    path("quote") {
      concat(
        get {
          val quotes = findQuotes()
          onSuccess(quotes) { q =>
            complete(GetQuotesResponse(q.asModel()))
          }
        },
        post {
          entity(as[QuoteResponse]) { quote =>
            val saved = saveQuote(quote.asDb())
            onComplete(saved) { done =>
              complete("Quote created")
            }
          }
        }
      )
    },
    get {
      pathPrefix("quote" / IntNumber) { id =>
        val quote = findQuote(id)
        onSuccess(quote) {
          case Some(q) => complete(q.asModel())
          case None    => complete(StatusCodes.NotFound)
        }
      }
    },
  )

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println("Server listening on port 8080")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}

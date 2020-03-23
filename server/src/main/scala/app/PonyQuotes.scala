package app

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route

import app.store._


object PonyQuotes extends App {
  implicit val system = ActorSystem("pony-quotes")
  implicit val executionContext = system.dispatcher

  val port = sys.env("PORT").toInt

  val ponyApi = new QuoteApi {
    override val db = StoreImpl
  }

  val route: Route = ponyApi.route

  val bindingFuture = Http().bindAndHandle(route, "localhost", port)

  println(s"Server listening on port $port")
}

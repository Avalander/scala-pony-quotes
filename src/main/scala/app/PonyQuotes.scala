package app

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route

import scala.io.StdIn

import app.store._


object PonyQuotes extends App {
  implicit val system = ActorSystem("pony-quotes")
  implicit val executionContext = system.dispatcher

  val ponyApi = new QuoteApi {
    override val db = StoreImpl
  }

  val route: Route = ponyApi.route

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println("Server listening on port 8080")
//  StdIn.readLine()
//  bindingFuture
//    .flatMap(_.unbind())
//    .onComplete(_ => system.terminate())
}

package app

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Future

import app.dto.{Pony, Quote}

object QuoteApiClient {
  implicit private val system = ActorSystem()
  implicit private val executionContext = system.dispatcher

  implicit private val ponyFormat = jsonFormat2(Pony)
  implicit private val quoteFormat = jsonFormat3(Quote)

  private val host = sys.env("SERVER_URL")

  def postQuote(quote: Quote): Future[HttpResponse] =
    Marshal(quote).to[MessageEntity] flatMap { entity =>
      Http().singleRequest(HttpRequest(
        method = HttpMethods.POST,
        uri = s"$host/quote",
        entity = entity
      ))
    }

  def getQuote(id: Int): Future[Quote] = {
    Http().singleRequest(HttpRequest(
      method = HttpMethods.GET,
      uri = s"$host/quote/$id"
    )) flatMap  { x =>
      Unmarshal(x).to[Quote]
    }
  }
}

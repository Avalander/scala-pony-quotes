package app

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Future

import app.dto.{Pony, Quote, QuoteList}

object QuoteApiClient {
  implicit private val system = ActorSystem()
  implicit private val executionContext = system.dispatcher

  implicit private val ponyFormat = jsonFormat2(Pony)
  implicit private val quoteFormat = jsonFormat3(Quote)
  implicit private val quoteListFormat = jsonFormat1(QuoteList)

  private val host = sys.env("SERVER_URL")
  private val quoteEndpoint = s"http://$host/quote"

  def postQuote(quote: Quote): Future[HttpResponse] =
    Marshal(quote).to[MessageEntity] flatMap { entity =>
      Http().singleRequest(HttpRequest(
        method = HttpMethods.POST,
        uri = quoteEndpoint,
        entity = entity
      ))
    }

  def getQuote(id: Int): Future[Quote] = {
    Http().singleRequest(HttpRequest(
      method = HttpMethods.GET,
      uri = s"$quoteEndpoint/$id"
    )) flatMap  { x =>
      Unmarshal(x).to[Quote]
    }
  }

  def getAllQuotes: Future[QuoteList] = {
    Http().singleRequest(HttpRequest(
      method = HttpMethods.GET,
      uri = quoteEndpoint
    )) flatMap { x =>
      Unmarshal(x).to[QuoteList]
    }
  }

  def getQuotesByAuthor(id: Int): Future[QuoteList] =
    Http() singleRequest HttpRequest(
      method = HttpMethods.GET,
      uri = s"$quoteEndpoint?author=$id"
    ) flatMap { x =>
      Unmarshal(x).to[QuoteList]
    }
}

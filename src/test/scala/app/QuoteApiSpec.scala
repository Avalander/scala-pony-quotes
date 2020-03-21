package app

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.{HttpEntity, MediaTypes, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import app.dto.Converter._
import app.store._
import org.mockito.ArgumentCaptor
import org.mockito.Matchers.any
import org.mockito.Mockito.{verify, when}
import org.scalatest._
import org.scalatestplus.mockito.MockitoSugar

import scala.concurrent.Future

class QuoteApiSpec extends WordSpec with Matchers with ScalatestRouteTest with MockitoSugar {
  val dbMock = mock[Store]
  val api = new QuoteApi {
    override val db = dbMock
  }

  "The quote API" should {
    "return all quotes for GET /quote" in {
      when(dbMock.findQuotes()) thenReturn {
        Future.successful(Fixtures.allQuotes)
      }
      Get("/quote") ~> api.route ~> check {
        responseAs[dto.QuoteList] shouldEqual {
          dto.QuoteList(Fixtures.allQuotes)
        }
      }
    }

    "query quotes by author id" in {
      when(dbMock.findQuotesByAuthor(1)) thenReturn {
        Future.successful(Fixtures.allQuotes)
      }
      Get("/quote?author=1") ~> api.route ~> check {
        responseAs[dto.QuoteList] shouldEqual {
          dto.QuoteList(Fixtures.allQuotes)
        }
      }
    }

    "return quote 1 for GET /quote/1" in {
      when(dbMock.findQuote(1)) thenReturn {
        Future.successful(Some(Fixtures.quote_01))
      }
      Get("/quote/1") ~> api.route ~> check {
        responseAs[dto.Quote] shouldEqual {
          quoteToDto(Fixtures.quote_01)
        }
      }
    }

    "return Not Found when quote does not exist" in {
      when(dbMock.findQuote(1)) thenReturn {
        Future.successful(None)
      }
      Get("/quote/1") ~> api.route ~> check {
        status shouldEqual StatusCodes.NotFound
        responseAs[String] shouldEqual "The requested resource could not be found but may be available again in the future."
      }
    }

    "saves a quote" in {
      val quoteCaptor = ArgumentCaptor.forClass(classOf[Quote])
      when(dbMock.saveQuote(any[Quote])) thenReturn {
        Future.successful(Fixtures.quote_02)
      }
      val body = HttpEntity(MediaTypes.`application/json`, Fixtures.quote_02_json)
      Post("/quote", body) ~> api.route ~> check {
        verify(dbMock).saveQuote(quoteCaptor.capture())
        val Quote(_, author, text, quoteId) = quoteCaptor.getValue
        val Pony(_, name, ponyId) = author

        Assertions.assert(text == Fixtures.quote_02.text)
        Assertions.assert(quoteId == Fixtures.quote_02.id)
        Assertions.assert(name == Fixtures.quote_02.author.name)
        Assertions.assert(ponyId == Fixtures.quote_02.author.id)
      }
    }
  }
}

object Fixtures {
  val twilight: Pony = Pony("Twilight Sparkle", 1)
  val pinkie: Pony = Pony("Pinkie Pie", 2)

  val quote_01: Quote = Quote(pinkie, "Eternal chaos comes with chocolate rain, you guys. Chocolate rain!", 1)
  val quote_02: Quote = Quote(twilight, "All the ponies in this town are CRAZY!", 2)
  val quote_02_json = ByteString(
    s"""{
       |"author": {
       |  "name": "Twilight Sparkle",
       |  "id": 1
       |},
       |"text": "All the ponies in this town are CRAZY!",
       |"id": 2
       |}
       |""".stripMargin)

  val allQuotes = Seq(
    quote_01,
    quote_02,
  )
}

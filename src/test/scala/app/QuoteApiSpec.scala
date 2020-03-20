package app

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import app.Model.GetQuotesResponse
import app.Model.implicits._
import org.mockito.Mockito.when
import org.scalatest._
import org.scalatestplus.mockito.MockitoSugar

import scala.concurrent.Future

class QuoteApiSpec extends WordSpec with Matchers with ScalatestRouteTest with MockitoSugar {
  val dbMock = mock[Database]
  val api = new PonyQuoteRoute {
    override val db = dbMock
  }

  "The quote API" should {
    "return all quotes for GET /quote" in {
      when(dbMock.findQuotes()) thenReturn {
        Future.successful(Fixtures.allQuotes)
      }
      Get("/quote") ~> api.route ~> check {
        responseAs[GetQuotesResponse] shouldEqual {
          GetQuotesResponse(Fixtures.allQuotes.asModel())
        }
      }
    }
  }
}

object Fixtures {
  val twilight: Pony = Pony("Twilight Sparkle", 1)
  val pinkie: Pony = Pony("Pinkie Pie", 2)

  val quote_01: Quote = Quote(pinkie, "Eternal chaos comes with chocolate rain, you guys. Chocolate rain!", 1)
  val quote_02: Quote = Quote(twilight, "All the ponies in this town are CRAZY!", 2)

  val allQuotes = Seq(
    quote_01,
    quote_02,
  )
}

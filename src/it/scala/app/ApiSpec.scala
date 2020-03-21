package app

import akka.http.scaladsl.model.StatusCodes
import app.dto.{Pony, Quote}
import app.{QuoteApiClient => QA}
import org.scalatest.AsyncWordSpec

class ApiSpec extends AsyncWordSpec {
  "The quote API" should {
    val quote = Quote(
      100,
      Pony(3, "Pinkie Pie"),
      "Rainbow Dash thinks Fluttershy is a tree."
    )

    "create a new quote" in {
      QA.postQuote(quote) map { r =>
        assert(r.status == StatusCodes.OK)
      }
    }

    "get the recently created quote" in {
      QA.getQuote(100) map { q =>
        assert(q.id == quote.id)
        assert(q.author == quote.author)
        assert(q.text == quote.text)
      }
    }
  }
}

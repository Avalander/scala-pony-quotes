package app

import akka.http.scaladsl.model.StatusCodes
import app.dto.{Pony, Quote, QuoteList}
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

    "get the quote in get all quotes" in {
      QA.getAllQuotes map { ql =>
        assert(ql.items.nonEmpty)
        val QuoteList(qs) = ql
        val q = qs find (_.id == quote.id)
        assert(q.contains(quote))
      }
    }

    "get the quote in get quotes by author" in {
      QA.getQuotesByAuthor(quote.author.id) map { ql =>
        assert(ql.items.nonEmpty)
        val QuoteList(qs) = ql
        val q = qs find (_.id == quote.id)
        assert(q.contains(quote))
      }
    }
  }
}

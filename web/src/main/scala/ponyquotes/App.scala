package ponyquotes

import html.Implicit._
import html.dsl.Html._
import html.dsl.Attributes._
import html.dsl.Events._
import ponyquotes.api.QuoteApi
import ponyquotes.model._
import tea.{App, Dispatch, start}
import org.scalajs.dom.{Event, document, window}
import superfine.Superfine.VNode

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object App {
  def main (args: Array[String]): Unit = {
    window.addEventListener("DOMContentLoaded", {
      (_: Event) => mount()
    })
  }

  // State

  type State = Seq[Quote]

  // Messages

  sealed trait Message
  case object FetchAllQuotes extends Message
  case class UpdateQuotes(quotes: Seq[Quote]) extends Message

  // Effects

  sealed trait Effect
  case object OnFetchAllQuotes extends Effect

  implicit def handleEffect (dispatch: Dispatch[Message], effect: Effect): Unit = {
    effect match {
      case OnFetchAllQuotes =>
        QuoteApi.fetchAllQuotes() andThen {
          case Failure(e) => println(e)
          case Success(x) =>
            println(x.items)
            dispatch(UpdateQuotes(x.items))
        }
    }
  }

  // mount

  type Update = (State, Seq[Effect])

  def mount (): Unit = {
    start(new App[State, Message, Effect] {
      val node = document.querySelector("#app")

      def init (): Update =
        (List[Quote](), List(OnFetchAllQuotes))

      def update (state: State, message: Message): Update = {
        message match {
          case FetchAllQuotes   => (state, List(OnFetchAllQuotes))
          case UpdateQuotes(qs) => (qs, Nil)
        }
      }

      def view(state: State, dispatch: Dispatch[Message]): VNode = {
        div(
          `class` := "main-content",
          h1("Pony Quotes", `class` := "title"),
          View.quoteList(state),
        )
      }
    })
  }

  // View

  object View {
    def quoteList (quotes: Seq[Quote]): VNode = {
      div(`class` := "quote-list",
        (quotes map quote),
      )
    }

    def quote (quote: Quote): VNode = {
      div(`class` := "quote",
        blockquote(quote.text),
        p(`class` := "author",
          quote.author.name,
        ),
      )
    }
  }
}

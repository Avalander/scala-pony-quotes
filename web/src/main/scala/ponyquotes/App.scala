package ponyquotes

import html.Implicit._
import html.dsl.Attributes._
import html.dsl.Events._
import html.dsl.Html._
import org.scalajs.dom.{Event, document, window}
import ponyquotes.api.QuoteApi
import ponyquotes.api.RemoteData
import ponyquotes.model._
import superfine.Superfine.VNode
import tea.{App, Dispatch, start}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object App {
  def main (args: Array[String]): Unit = {
    window.addEventListener("DOMContentLoaded", {
      _: Event => mount()
    })
  }

  // State

  type State = RemoteData[Seq[Quote]]

  // Messages

  sealed trait Message
  case object FetchAllQuotes extends Message
  case class UpdateQuotes(quotes: RemoteData[Seq[Quote]]) extends Message

  // Effects

  sealed trait Effect
  case object OnFetchAllQuotes extends Effect

  implicit def handleEffect (dispatch: Dispatch[Message], effect: Effect): Unit = {
    effect match {
      case OnFetchAllQuotes =>
        QuoteApi.fetchAllQuotes() andThen {
          case Failure(e) => println(e)
          case Success(x) =>
            dispatch(UpdateQuotes(
              RemoteData.Success(x.items.toSeq)
            ))
        }
    }
  }

  // mount

  type Update = (State, Seq[Effect])

  def mount (): Unit = {
    start(new App[State, Message, Effect] {
      val node = document.querySelector("#app")

      def init (): Update =
        (RemoteData.NotAsked, List(OnFetchAllQuotes))

      def update (state: State, message: Message): Update = {
        message match {
          case FetchAllQuotes   => (
            RemoteData.Fetching,
            List(OnFetchAllQuotes)
          )
          case UpdateQuotes(qs) => (qs, Nil)
        }
      }

      def view(state: State, dispatch: Dispatch[Message]): VNode = {
        div(
          `class` := "main-content",
          h1("Pony Quotes", `class` := "title"),
          button("Reload",
            `class` := "btn primary",
            onClick := {
              (_: Event) => dispatch(FetchAllQuotes)
            }
          ),
          View.quotes(state),
        )
      }
    })
  }

  // View

  object View {
    def quotes (quotes: RemoteData[Seq[Quote]]): VNode = quotes match {
      case RemoteData.NotAsked   => p("No quotes")
      case RemoteData.Fetching   => p("Loading")
      case RemoteData.Success(x) => quoteList(x)
      case RemoteData.Failure(e) => div(
        p("Something went wrong"),
        p(e.getMessage),
      )
    }

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

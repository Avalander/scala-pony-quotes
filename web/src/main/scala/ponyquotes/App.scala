package ponyquotes

import html.Implicit._
import html._
import org.scalajs.dom.{Event, document}
import ponyquotes.Api._
import superfine.Superfine.{VNode => HH}
import tea.{App, Dispatch, start}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


object App {
  def main (args: Array[String]): Unit = {
    println("This be stuff")
    jokes()
  }

  def jokes(): Unit = {
    type State = Option[String]

    sealed trait Message
    case class SetJoke(joke: String) extends Message
    case object FetchJoke extends Message
    case class JokeError(e: Throwable) extends Message

    sealed trait Effect
    case object OnFetchJoke extends Effect

    implicit def handleEffect (dispatch: Dispatch[Message], effect: Effect): Unit =
      effect match {
        case OnFetchJoke =>
          fetchJoke() andThen {
            case Failure(e) => dispatch(JokeError(e))
            case Success(j) => dispatch(SetJoke(j.joke))
          }
      }

    start(new App[State, Message, Effect] {
      val node = document.querySelector("#jokes")

      def init(): (State, Seq[Effect]) = (None, List(OnFetchJoke))

      def update (prev: State, message: Message): (State, Seq[Effect]) =
        message match {
          case SetJoke(joke) => (Some(joke), Nil)
          case FetchJoke     => (prev, List(OnFetchJoke))
          case JokeError(e)  => {
            println(e)
            (Some("Oops, something went wrong"), Nil)
          }
        }

      def view (state: State, dispatch: Dispatch[Message]): HH = {
        state match {
          case None =>
            div(
              p("Press the button to load a joke."),
              button("Gimme a joke!",
                onClick := {
                  (e: Event) => dispatch(FetchJoke)
                }
              )
            )
          case Some(joke) =>
            div(
              p(joke),
              button("Another joke!",
                onClick := {
                  (e: Event) => dispatch(FetchJoke)
                }
              )
            )
        }
      }
    })
  }
}

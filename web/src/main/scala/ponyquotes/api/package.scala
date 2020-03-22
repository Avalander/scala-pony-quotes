package ponyquotes

import org.scalajs.dom.raw.{Event, XMLHttpRequest}

import scala.concurrent.{Future, Promise}
import scala.scalajs.js.JSON

package object api {
  class FetchError(val message: String, val statusCode: Int) extends Exception

  class Headers(val headers: Map[String, String] = Map())
  object Headers {
    def apply(): Headers = new Headers()
    def apply(headers: (String, String)*): Headers = new Headers(headers.toMap)
  }

  private val EMPTY_HEADERS = Headers()

  def fetch[T](method: String, url: String, request: Headers = EMPTY_HEADERS): Future[T] = {
    val p = Promise[T]()
    val xhr = new XMLHttpRequest()
    xhr.open(method, url)
    for {
      (key, value) <- request.headers
    } xhr.setRequestHeader(key, value)
    xhr.onload = {
      (_: Event) =>
        if (xhr.status >= 200 && xhr.status < 300) {
          val r = JSON.parse(xhr.responseText)
          p success r.asInstanceOf[T]
        }
        else p failure new FetchError(xhr.responseText, xhr.status)
    }
    xhr.send()
    p.future
  }

  def get[T] (url: String, request: Headers = EMPTY_HEADERS): Future[T] =
    fetch("GET", url, request)
}

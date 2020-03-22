package ponyquotes.api

import ponyquotes.model._

import scala.concurrent.Future

object QuoteApi {
  private val JsonHeaders = Headers(
    "Accept" -> "application/json",
    "Content-Type" -> "application/json",
  )

  def fetchAllQuotes (): Future[QuoteList] =
    get("/quote", JsonHeaders)
}

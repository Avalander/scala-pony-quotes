package ponyquotes.api

sealed trait RemoteData[+T]

object RemoteData {
  case object NotAsked extends RemoteData[Nothing]
  case object Fetching extends RemoteData[Nothing]
  case class Success[+T](x: T) extends RemoteData[T]
  case class Failure(e: Throwable) extends RemoteData[Nothing]
}

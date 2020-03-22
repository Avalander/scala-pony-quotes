package ponyquotes

import scala.scalajs.js

package object model {
  @js.native
  trait Pony extends js.Object {
    val id: Int
    val name: String
  }

  @js.native
  trait Quote extends js.Object {
    val author: Pony
    val text: String
    val id: Int
  }

  @js.native
  trait QuoteList extends js.Object {
    val items: Seq[Quote]
  }
}

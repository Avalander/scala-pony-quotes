package superfine

import org.scalajs.dom.Element

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

@js.native
@JSImport("superfine", JSImport.Namespace)
object Superfine extends js.Object {
  type Children = js.UndefOr[String]|js.Array[js.Object]
  type Props = js.Object|js.Dictionary[Any]
  type VNode = js.Object

  def patch(node: Element, view: VNode): Element = js.native
  def h(
    tag: String,
    props: js.Dictionary[Any],
    children: Children = js.undefined
  ): VNode = js.native
}
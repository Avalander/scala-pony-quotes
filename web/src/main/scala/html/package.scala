import org.scalajs.dom.Event

package object html {
  val button = new VNode("button")
  val div = new VNode("div")
  val input = new VNode("input")
  val p = new VNode("p")

  class TagAttribute(name: String) {
    def := (value: String): Attribute = Attribute(name, value)
  }

  val `class` = new TagAttribute("class")
  val `type` = new TagAttribute("type")
  val id = new TagAttribute("id")
  val name = new TagAttribute("name")

  class TagEvent(name: String) {
    def := (fn: Event => Unit): EventHandler = EventHandler(name, fn)
  }

  val onClick = new TagEvent("onclick")
  val onInput = new TagEvent("oninput")
}

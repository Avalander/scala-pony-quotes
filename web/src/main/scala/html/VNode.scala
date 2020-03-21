package html

import org.scalajs.dom.Event
import superfine.Superfine.{h, VNode => HH}

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

sealed trait Prop
case class Child(child: HH) extends Prop
case class TextNode(text: String) extends Prop
case class Attribute(key: String, value: String) extends Prop
case class EventHandler(key: String, fn: Event => Unit) extends Prop


object Implicit {
  implicit class Attr(a: Symbol) {
    def := [T](v: T): (String, T) = a.name -> v
  }

  implicit def toChildNode(node: HH): Child = Child(node)

  implicit def toTextNode(text: String): TextNode = TextNode(text)
}

object Example {
  import Implicit._

  div(
    `class` := "potato",
    input(
      `type` := "text",
      id := "name",
      name := "name"
    ),
    input(
      `type` := "text",
      id := "password",
      name := "password"
    )
  )
}

class VNode(tag: String) {
  def apply(props: Prop*): HH = {
    val attrs = (props filter isAttr map {
      case Attribute(key, value) => (key, value)
      case EventHandler(key, fn) => (key, fn) : (String, js.Function1[Event, Unit])
    }).toMap[String, Any].toJSDictionary
    val text = props find isTextNode map {
      case TextNode(text) => text
    }
    val children: js.Array[js.Object] = (props filter isChild map {
      case Child(node) => node : HH
    }).toJSArray

    text match {
      case Some(t) => h(tag, attrs, t)
      case None    => h(tag, attrs, children)
    }
  }

  private def isTextNode (node: Prop): Boolean = {
    node match {
      case TextNode(_) => true
      case _           => false
    }
  }

  private def isAttr (node: Prop): Boolean = {
    node match {
      case Attribute(_, _)    => true
      case EventHandler(_, _) => true
      case _                  => false
    }
  }

  private def isChild (node: Prop): Boolean = {
    node match {
      case Child(_) => true
      case _        => false
    }
  }
}

package html.dsl

import html.EventHandler
import org.scalajs.dom.Event

object Events {
  class TagEvent(name: String) {
    def := (fn: Event => Unit): EventHandler = EventHandler(name, fn)
  }

  val onAbort = new TagEvent("onabort") // 	<audio>, <embed>, <img>, <object>, <video> 	Script to be run on abort
  val onAfterPrint = new TagEvent("onafterprint") // 	<body> 	Script to be run after the document is printed
  val onBeforePrint = new TagEvent("onbeforeprint") // 	<body> 	Script to be run before the document is printed
  val onBeforeUnload = new TagEvent("onbeforeunload") // 	<body> 	Script to be run when the document is about to be unloaded
  val onBlur = new TagEvent("onblur") // 	All visible elements. 	Script to be run when the element loses focus
  val onCanPlay = new TagEvent("oncanplay") // 	<audio>, <embed>, <object>, <video> 	Script to be run when a file is ready to start playing (when it has buffered enough to begin)
  val onCanPlayThrough = new TagEvent("oncanplaythrough") // 	<audio>, <video> 	Script to be run when a file can be played all the way to the end without pausing for buffering
  val onChange = new TagEvent("onchange") // 	All visible elements. 	Script to be run when the value of the element is changed
  val onClick = new TagEvent("onclick") // 	All visible elements. 	Script to be run when the element is being clicked
  val onContextMenu = new TagEvent("oncontextmenu") // 	All visible elements. 	Script to be run when a context menu is triggered
  val onCopy = new TagEvent("oncopy") // 	All visible elements. 	Script to be run when the content of the element is being copied
  val onCueChange = new TagEvent("oncuechange") // 	<track> 	Script to be run when the cue changes in a <track> element
  val onCut = new TagEvent("oncut") // 	All visible elements. 	Script to be run when the content of the element is being cut
  val onDblClick = new TagEvent("ondblclick") // 	All visible elements. 	Script to be run when the element is being double-clicked
  val onDrag = new TagEvent("ondrag") // 	All visible elements. 	Script to be run when the element is being dragged
  val onDragEnd = new TagEvent("ondragend") // 	All visible elements. 	Script to be run at the end of a drag operation
  val onDragEnter = new TagEvent("ondragenter") // 	All visible elements. 	Script to be run when an element has been dragged to a valid drop target
  val onDragLeave = new TagEvent("ondragleave") // 	All visible elements. 	Script to be run when an element leaves a valid drop target
  val onDragOver = new TagEvent("ondragover") // 	All visible elements. 	Script to be run when an element is being dragged over a valid drop target
  val onDragStart = new TagEvent("ondragstart") // 	All visible elements. 	Script to be run at the start of a drag operation
  val onDrop = new TagEvent("ondrop") // 	All visible elements. 	Script to be run when dragged element is being dropped
  val onDurationChange = new TagEvent("ondurationchange") // 	<audio>, <video> 	Script to be run when the length of the media changes
  val onEmptied = new TagEvent("onemptied") // 	<audio>, <video> 	Script to be run when something bad happens and the file is suddenly unavailable (like unexpectedly disconnects)
  val onEnded = new TagEvent("onended") // 	<audio>, <video> 	Script to be run when the media has reach the end (a useful event for messages like "thanks for listening")
  val onError = new TagEvent("onerror") // 	<audio>, <body>, <embed>, <img>, <object>, <script>, <style>, <video> 	Script to be run when an error occurs
  val onFocus = new TagEvent("onfocus") // 	All visible elements. 	Script to be run when the element gets focus
  val onHashChange = new TagEvent("onhashchange") // 	<body> 	Script to be run when there has been changes to the anchor part of the a URL
  val onInput = new TagEvent("oninput") // 	All visible elements. 	Script to be run when the element gets user input
  val onInvalid = new TagEvent("oninvalid") // 	All visible elements. 	Script to be run when the element is invalid
  val onKeyDown = new TagEvent("onkeydown") // 	All visible elements. 	Script to be run when a user is pressing a key
  val onKeyPress = new TagEvent("onkeypress") // 	All visible elements. 	Script to be run when a user presses a key
  val onKeyUp = new TagEvent("onkeyup") // 	All visible elements. 	Script to be run when a user releases a key
  val onLoad = new TagEvent("onload") // 	<body>, <iframe>, <img>, <input>, <link>, <script>, <style> 	Script to be run when the element is finished loading
  val onLoadedData = new TagEvent("onloadeddata") // 	<audio>, <video> 	Script to be run when media data is loaded
  val onLoadedMetadata = new TagEvent("onloadedmetadata") // 	<audio>, <video> 	Script to be run when meta data (like dimensions and duration) are loaded
  val onLoadStart = new TagEvent("onloadstart") // 	<audio>, <video> 	Script to be run just as the file begins to load before anything is actually loaded
  val onMouseDown = new TagEvent("onmousedown") // 	All visible elements. 	Script to be run when a mouse button is pressed down on an element
  val onMouseMove = new TagEvent("onmousemove") // 	All visible elements. 	Script to be run as long as the  mouse pointer is moving over an element
  val onMouseOut = new TagEvent("onmouseout") // 	All visible elements. 	Script to be run when a mouse pointer moves out of an element
  val onMouseOver = new TagEvent("onmouseover") // 	All visible elements. 	Script to be run when a mouse pointer moves over an element
  val onMouseUp = new TagEvent("onmouseup") // 	All visible elements. 	Script to be run when a mouse button is released over an element
  val onMouseWheel = new TagEvent("onmousewheel") // 	All visible elements. 	Script to be run when a mouse wheel is being scrolled over an element
  val onOffline = new TagEvent("onoffline") // 	<body> 	Script to be run when the browser starts to work offline
  val onOnline = new TagEvent("ononline") // 	<body> 	Script to be run when the browser starts to work online
  val onPageHide = new TagEvent("onpagehide") // 	<body> 	Script to be run when a user navigates away from a page
  val onPageShow = new TagEvent("onpageshow") // 	<body> 	Script to be run when a user navigates to a page
  val onPaste = new TagEvent("onpaste") // 	All visible elements. 	Script to be run when the user pastes some content in an element
  val onPause = new TagEvent("onpause") // 	<audio>, <video> 	Script to be run when the media is paused either by the user or programmatically
  val onPlay = new TagEvent("onplay") // 	<audio>, <video> 	Script to be run when the media has started playing
  val onPlaying = new TagEvent("onplaying") // 	<audio>, <video> 	Script to be run when the media has started playing
  val onPopState = new TagEvent("onpopstate") // 	<body> 	Script to be run when the window's history changes.
  val onProgress = new TagEvent("onprogress") // 	<audio>, <video> 	Script to be run when the browser is in the process of getting the media data
  val onRateChange = new TagEvent("onratechange") // 	<audio>, <video> 	Script to be run each time the playback rate changes (like when a user switches to a slow motion or fast forward mode).
  val onReset = new TagEvent("onreset") // 	<form> 	Script to be run when a reset button in a form is clicked.
  val onResize = new TagEvent("onresize") // 	<body> 	Script to be run when the browser window is being resized.
  val onScroll = new TagEvent("onscroll") // 	All visible elements. 	Script to be run when an element's scrollbar is being scrolled
  val onSearch = new TagEvent("onsearch") // 	<input> 	Script to be run when the user writes something in a search field (for <input="search">)
  val onSeeked = new TagEvent("onseeked") // 	<audio>, <video> 	Script to be run when the seeking attribute is set to false indicating that seeking has ended
  val onSeeking = new TagEvent("onseeking") // 	<audio>, <video> 	Script to be run when the seeking attribute is set to true indicating that seeking is active
  val onSelect = new TagEvent("onselect") // 	All visible elements. 	Script to be run when the element gets selected
  val onStalled = new TagEvent("onstalled") // 	<audio>, <video> 	Script to be run when the browser is unable to fetch the media data for whatever reason
  val onStorage = new TagEvent("onstorage") // 	<body> 	Script to be run when a Web Storage area is updated
  val onSubmit = new TagEvent("onsubmit") // 	<form> 	Script to be run when a form is submitted
  val onSuspend = new TagEvent("onsuspend") // 	<audio>, <video> 	Script to be run when fetching the media data is stopped before it is completely loaded for whatever reason
  val onTimeUpdate = new TagEvent("ontimeupdate") // 	<audio>, <video> 	Script to be run when the playing position has changed (like when the user fast forwards to a different point in the media)
  val onToggle = new TagEvent("ontoggle") // 	<details> 	Script to be run when the user opens or closes the <details> element
  val onUnload = new TagEvent("onunload") // 	<body> 	Script to be run when a page has unloaded (or the browser window has been closed)
  val onVolumeChange = new TagEvent("onvolumechange") // 	<audio>, <video> 	Script to be run each time the volume of a video/audio has been changed
  val onWaiting = new TagEvent("onwaiting") // 	<audio>, <video> 	Script to be run when the media has paused but is expected to resume (like when the media pauses to buffer more data)
  val onWheel = new TagEvent("onwheel") // 	All visible elements. 	Script to be run when the mouse wheel rolls up or down over an element
}

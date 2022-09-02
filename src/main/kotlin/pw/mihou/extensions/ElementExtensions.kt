package pw.mihou.extensions

import org.jsoup.nodes.Element

fun Element.getFirstElementWithClass(className: String): Element? = getElementsByClass(className).first()
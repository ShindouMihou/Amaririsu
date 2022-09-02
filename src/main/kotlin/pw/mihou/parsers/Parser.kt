package pw.mihou.parsers

import org.jsoup.nodes.Document

interface Parser<Type> {

    fun from(url: String, document: Document): Type

}
package pw.mihou.parsers

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

interface NodeParser<Result> {

    fun from(document: Document, element: Element): Result


}
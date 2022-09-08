package pw.mihou.parsers

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import pw.mihou.parsers.options.ParserOptions

interface NodeParser<Result, Options: ParserOptions> {

    fun from(document: Document, element: Element, options: Options): Result


}
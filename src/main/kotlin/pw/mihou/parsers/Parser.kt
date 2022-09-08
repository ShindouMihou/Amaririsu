package pw.mihou.parsers

import org.jsoup.nodes.Document
import pw.mihou.parsers.options.ParserOptions

interface Parser<Type, Options: ParserOptions> {

    fun from(url: String, document: Document, options: Options? = null): Type

}
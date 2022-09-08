package pw.mihou.parsers.options

import org.jsoup.nodes.Document
import pw.mihou.Amaririsu

abstract class ParserOptions {
    var connector: (url: String) -> Document = Amaririsu.connector
}
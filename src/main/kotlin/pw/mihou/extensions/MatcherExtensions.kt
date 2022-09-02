package pw.mihou.extensions

operator fun MatchResult.get(name: String): String? {
    return groups[name]?.value
}
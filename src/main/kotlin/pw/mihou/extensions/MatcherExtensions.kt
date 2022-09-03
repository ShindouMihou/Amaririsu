package pw.mihou.extensions

operator fun MatchResult.get(name: String): String? {
    return groups[name]?.value
}

fun Regex.matchOrThrow(contents: CharSequence): MatchResult {
    return matchEntire(contents)
        ?: throw IllegalArgumentException(
            "\"$contents\" cannot be matched by \"${pattern}\". " +
                    "Try contacting the developers at https://github.com/ShindouMihou/Amaririsu."
        )
}
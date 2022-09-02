package pw.mihou.cache

interface Cache {
    fun set(uri: String, item: Cacheable)
    fun get(uri: String): Cacheable?
}
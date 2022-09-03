package pw.mihou

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import pw.mihou.cache.Cache
import pw.mihou.cache.Cacheable
import pw.mihou.models.SearchResult
import pw.mihou.models.series.Series
import pw.mihou.parsers.modules.SeriesParser

object Amaririsu {

    private var cache: Cache? = null
    @Volatile var connector: (url: String) -> Document = { url -> Jsoup.connect(url).get() }

    /**
     * Sets the cache to be used.
     *
     * @param cache the cache to use for caching.
     * @return the current updated instance for chain-calling methods.
     */
    fun set(cache: Cache): Amaririsu {
        synchronized(this) {
            this.cache = cache
            return this
        }
    }

    /**
     * Gets from the cache if available or requests information of the series given a link before
     * caching the series when the cache is available.
     *
     * @param url the link to the series.
     * @return an instance of the series.
     */
    fun series(url: String): Series = cache(
        url = url,
        otherwise = { SeriesParser.from(url, connector(url)) },
        validator = { cacheable -> cacheable is Series }
    ) as Series

    /**
     * Searches the name whether as series or user using the search engine of ScribbleHub.
     * @param name the name to search.
     * @return the search results.
     */
    fun search(name: String): SearchResult {
        // TODO: Implement this search method.
        throw UnsupportedOperationException("Searching is not supported at this moment.")
    }

    /**
     * Gets from the cache if there is any otherwise executes the given function to request a new instance.
     * This will also validate that the cached result matches the validator.
     *
     * @param url the link that is also used as a cache key.
     * @param otherwise the function to request and create a new instance.
     * @param validator the validator to validate that the cached instance is proper.
     * @return a [Cacheable] result that matches all the conditions.
     */
    private fun cache(url: String, otherwise: (String) -> Cacheable, validator: (Cacheable) -> Boolean): Cacheable {
        synchronized(url) {
            val cached = cache?.get(url)

            if (cached != null && validator(cached)) {
                return cached
            }

            val result = otherwise(url)

            if (cache != null) {
                cache!!.set(url, result)
            }

            return result
        }
    }

}
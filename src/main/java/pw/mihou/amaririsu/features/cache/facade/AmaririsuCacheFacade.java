package pw.mihou.amaririsu.features.cache.facade;

import pw.mihou.amaririsu.features.cache.core.AmaririsuCacheCore;

import java.util.Optional;

public class AmaririsuCacheFacade {

    /**
     * This replaces or adds the value onto the cache with the specified key.
     *
     * @param key The key of the cache.
     * @param value The value of the cache.
     * @param tts The time to cache the object in seconds.
     */
    public static void put(String key, Object value, long tts) {
        AmaririsuCacheCore.put(key, value, tts);
    }

    /**
     * This removes the value of the key specified from the cache.
     *
     * @param key The key to remove.
     */
    public static void remove(String key) {
        AmaririsuCacheCore.remove(key);
    }

    /**
     * This selects the cache stored inside the specified key.
     *
     * @param key The key to select.
     * @return The cached entitiy if present.
     */
    public static Optional<AmaririsuCacheEntity> select(String key) {
        return AmaririsuCacheCore.select(key);
    }

}

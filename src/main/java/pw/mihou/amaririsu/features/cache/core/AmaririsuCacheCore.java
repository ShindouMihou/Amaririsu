package pw.mihou.amaririsu.features.cache.core;

import pw.mihou.amaririsu.features.cache.facade.AmaririsuCacheEntity;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AmaririsuCacheCore {

    private static final ConcurrentHashMap<String, AmaririsuCacheEntity> cache = new ConcurrentHashMap<>();

    /**
     * This replaces or adds the value onto the cache with the specified key.
     *
     * @param key The key of the cache.
     * @param value The value of the cache.
     * @param tts The time to cache the object in seconds.
     */
    public static void put(String key, Object value, long tts) {
        // To prevent a scheduler from accidentally removing the key when it is renewed
        // we run the cancel method of the scheduler first.
        remove(key);

        cache.put(key.toLowerCase(), new AmaririsuCacheEntityCore(key, value, tts));
    }

    /**
     * This removes the value of the key specified from the cache while
     * not triggering the {@link AmaririsuCacheEntityCore#cancel()} method which would
     * cancle the scheduler especially when running.
     *
     * @param key The key to remove.
     */
    public static void internRemove(String key) {
        cache.remove(key.toLowerCase());
    }

    /**
     * This removes the value of the key specified from the cache.
     *
     * @param key The key to remove.
     */
    public static void remove(String key) {
        select(key).map(entity -> (AmaririsuCacheEntityCore) entity).ifPresent(entity -> {
            entity.cancel();
            cache.remove(key.toLowerCase());
        });
    }

    /**
     * This selects the cache stored inside the specified key.
     *
     * @param key The key to select.
     * @return The cached entitiy if present.
     */
    public static Optional<AmaririsuCacheEntity> select(String key) {
        return cache.containsKey(key.toLowerCase()) ? Optional.of(cache.get(key.toLowerCase())) : Optional.empty();
    }

}

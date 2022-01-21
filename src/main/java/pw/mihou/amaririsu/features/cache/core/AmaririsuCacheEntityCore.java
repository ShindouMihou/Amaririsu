package pw.mihou.amaririsu.features.cache.core;

import pw.mihou.amaririsu.features.cache.facade.AmaririsuCacheEntity;
import pw.mihou.amaririsu.features.threading.AmaririsuThreadPool;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class AmaririsuCacheEntityCore implements AmaririsuCacheEntity {

    private final Object OBJECT;
    private final ScheduledFuture<?> REMOVING_FUTURE;

    /**
     * This creates an immutable cache entity that will expire on a specific
     * time specified. You can choose to ignore the cache lifetime if you wish, but
     * it will be ejected from cache on that time. This is immutable to prevent multi-threading
     * issues that would occur on the previous Amatsuki library.
     *
     * @param key       The key of the object being stored. This is used to automatically eject
     *                  this cache object when possible.
     * @param object    The object to store.
     * @param cacheTTS  The TTS of the cache.
     */
    public AmaririsuCacheEntityCore(String key, Object object, long cacheTTS) {
        this.OBJECT = object;
        this.REMOVING_FUTURE = AmaririsuThreadPool.schedule(
                () -> AmaririsuCacheCore.internRemove(key),
                System.currentTimeMillis() + Duration.ofSeconds(cacheTTS).toMillis(),
                TimeUnit.MILLISECONDS
        );
    }

    /**
     * An internal method of {@link AmaririsuCacheEntity} that causes the removing
     * future to cancel itself when removed and so forth.
     */
    public void cancel() {
        REMOVING_FUTURE.cancel(true);
    }

    @Override
    public <R> Optional<R> get(Class<R> rClass) {
        return OBJECT.getClass().equals(rClass) ? Optional.of((R) OBJECT) : Optional.empty();
    }

}

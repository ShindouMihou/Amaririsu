package pw.mihou.amaririsu.features.cache.facade;

import java.util.Optional;

public interface AmaririsuCacheEntity {

    /**
     * Collects the value of this entity only when the type matches with what is
     * specified to prevent further issues down the road.
     *
     * @param rClass The class type expected.
     * @param <R> The type expected.
     * @return The value of this entity.
     */
    <R> Optional<R> get(Class<R> rClass);

}

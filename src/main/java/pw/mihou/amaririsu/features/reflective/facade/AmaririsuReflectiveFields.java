package pw.mihou.amaririsu.features.reflective.facade;

import java.util.Optional;

public interface AmaririsuReflectiveFields {

    /**
     * Collects the value of the field tht matches the specified field name
     * regardless of whether the type matches or not.
     * 
     * @param field The field name to select.
     * @param <R> The type expected to be returned.
     * @return The value of the field or default.
     */
    <R> Optional<R> get(String field);

    /**
     * Collects the value of the field that matches the specified field name
     * and performs a type-check.
     *
     * @param field The field name to select.
     * @param rClass The default value of the field.
     * @param <R> The type expected to be returned.
     * @return The value of the field or default.
     */
    @SuppressWarnings("unchecked")
    default <R> Optional<R> getWithType(String field, Class<R> rClass) {
        return get(field).filter(o ->
                rClass.isAssignableFrom(o.getClass())
                || o.getClass().equals(rClass)
        ).map(o -> (R) o);
    }

    /**
     * Collects the value of the field that matches the specified field name
     * and performs a type-check through {@link AmaririsuReflectiveFields#getWithType(String, Class)} before
     * checking whether field is optional or not otherwise returns default.
     *
     * @param field The field name to select.
     * @param defaultValue The default value of the field.
     * @param <R> The type expected to be returned.
     * @param rClass The class type expected to be returned.
     * @return The value of the field or default.
     */
    default <R> R getOrElse(String field, R defaultValue, Class<R> rClass) {
        return (R) getWithType(field, rClass).orElse(defaultValue);
    }
}

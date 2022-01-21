package pw.mihou.amaririsu.features.reflective;

import pw.mihou.amaririsu.features.cache.facade.AmaririsuCacheFacade;
import pw.mihou.amaririsu.features.reflective.annotations.AmaririsuConstructor;
import pw.mihou.amaririsu.features.reflective.annotations.AmaririsuContract;
import pw.mihou.amaririsu.features.reflective.annotations.AmaririsuField;
import pw.mihou.amaririsu.features.reflective.annotations.AmaririsuInvisible;
import pw.mihou.amaririsu.features.reflective.core.AmaririsuReflectiveFieldsCore;
import pw.mihou.amaririsu.features.reflective.facade.AmaririsuReflectiveAdapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class AmaririsuReflectiveCore {

    private static final Map<Class, AmaririsuReflectiveAdapter> adapters = new HashMap<>();

    /**
     * This is used to add an adapter when a variable requires a specific class
     * that {@link pw.mihou.amaririsu.features.reflective.facade.AmaririsuReflectiveFacade} cannot transform.
     *
     * @param tClass The class to transform into.
     * @param adapter The adapter of the class.
     */
    public static void addAdapter(Class<?> tClass, AmaririsuReflectiveAdapter<?> adapter) {
        adapters.put(tClass, adapter);
    }

    /**
     * Checks if the class specified has an adapter.
     *
     * @param clazz The class to check.
     * @return Does this class have an adpter?
     */
    public static boolean hasAdapter(Class<?> clazz) {
        return adapters.containsKey(clazz);
    }

    /**
     * Gets the adapter that can be used to transform the specified class.
     *
     * @param tClass The class type to expect.
     * @param <T> The type to expect.
     * @return The adapter that can be used to transform a set of fields to
     * the specified class.
     */
    public static <T> AmaririsuReflectiveAdapter<T> getAdapter(Class<T> tClass) {
        return adapters.get(tClass);
    }

    private static void acceptCacheStep(String key, Object value) {
        Class<?> clazz = value.getClass();

        if (!clazz.isAnnotationPresent(AmaririsuContract.class))
            return;

        AmaririsuCacheFacade.put(key, value, clazz.getAnnotation(AmaririsuContract.class).cacheTTS());
    }

    @SuppressWarnings("unchecked")
    public static <T> T accept(String cacheKey, Class<T> clazz, AmaririsuReflectiveFieldsCore params) {
        try {
            T t;

            // We'll first check if there are any constructors with no parameters.
            if (Arrays.stream(clazz.getDeclaredConstructors()).anyMatch(constructor -> constructor.getParameterCount() == 0)) {
                t = clazz.newInstance();

                Arrays.stream(t.getClass().getDeclaredFields())
                        .forEach(field -> {
                            field.setAccessible(true);

                            if (!field.isAnnotationPresent(AmaririsuInvisible.class)) {
                                String fieldName = field.isAnnotationPresent(AmaririsuField.class) ?
                                        field.getAnnotation(AmaririsuField.class).name() : field.getName();

                                // This is where we perform some Reflection magic with Java. The purpose of this
                                // section is to generate or fill the value with this field which requires quite
                                // some magic.
                                try {
                                    Class<?> a = field.getType();

                                    if (hasAdapter(a)) {
                                        field.set(t, getAdapter(a).transformFrom(params));
                                    } else {
                                        try {
                                            field.set(t, params.getWithType(fieldName, fromPrimitiveToNonPrimitive(a)).orElseThrow(() ->
                                                    new IllegalArgumentException("Amaririsu failed to find the field with the specified name: " +
                                                            fieldName + " to transform for class: " + clazz.getName())
                                            ));
                                        } catch (ClassCastException exception) {
                                            exception.printStackTrace();
                                            throw new IllegalStateException("Amaririsu was unable to magically transform the field " +
                                                    fieldName + " on " + clazz.getName() + ". Please try adding an adapter.");
                                        }
                                    }
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            } else {
                // This is actually one of my masterpiece code and also the most unreadable one.
                // A custom constructor must have the AmaririsuConstructor annotation to be recognized by Amaririsu.
                // If it doesn't then we'll check if any constructors matches the keys that the params have.
                // If there aren't then we'll just end up failing everything.
                Constructor<T> constructor = (Constructor<T>) Arrays.stream(clazz.getDeclaredConstructors())
                        .filter(cons -> cons.isAnnotationPresent(AmaririsuConstructor.class))
                        .findFirst()
                        .orElse(Arrays.stream(clazz.getDeclaredConstructors())
                                .filter(cons -> Arrays.stream(cons.getParameters()).allMatch(parameter -> {
                                    String key = parameter.isAnnotationPresent(AmaririsuField.class) ?
                                            parameter.getAnnotation(AmaririsuField.class).name() :
                                            (parameter.isNamePresent() ? parameter.getName() : null);

                                    if (key == null) {
                                        throw new IllegalStateException("A constructor parameter on Class: [" +
                                                clazz.getName()
                                                +"] must be annotated with AmaririsuField to be recognized.");
                                    }

                                    return params.getWithType(key, fromPrimitiveToNonPrimitive(parameter.getType())).isPresent();
                                }))
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException(
                                        "No constructor for " + clazz.getName() + " is useable by Amariris."
                                )));

                constructor.setAccessible(true);
                t = (T) constructor.newInstance(Arrays.stream(constructor.getParameters()).map(parameter -> {
                    String key = parameter.isAnnotationPresent(AmaririsuField.class) ?
                            parameter.getAnnotation(AmaririsuField.class).name() :
                            (parameter.isNamePresent() ? parameter.getName() : null);

                    if (key == null) {
                        // This throws an assertion error because the above code actually recognizes that
                        // this parameter should have a name.
                        throw new AssertionError();
                    }
                    Class<?> a = parameter.getType();

                    return hasAdapter(a) ? getAdapter(a).transformFrom(params) :
                            params.getWithType(key, fromPrimitiveToNonPrimitive(parameter.getType())).orElseThrow(() ->
                                    new IllegalArgumentException("Amaririsu failed to find the field with the specified name: " +
                                            key + " to transform for class: " + clazz.getName())
                            );
                }).toArray());
            }

            acceptCacheStep(cacheKey, t);
            return t;
        }  catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This identifies primitive classes and returns back their non-primitive
     * class values since for some reason, type-checking requires it.
     *
     * @param clazz The class to identify.
     * @return The non-primitive class variant.
     */
    private static Class<?> fromPrimitiveToNonPrimitive(Class<?> clazz) {
        if (clazz.equals(Boolean.class) || clazz.equals(boolean.class))
            return Boolean.class;
        else if (clazz.equals(Integer.class) || clazz.equals(int.class))
            return Integer.class;
        else if (clazz.equals(Long.class) || clazz.equals(long.class))
            return Long.class;
        else if (clazz.equals(Character.class) || clazz.equals(char.class))
            return Character.class;
        else if (clazz.equals(String.class))
            return String.class;
        else if (clazz.equals(Double.class) || clazz.equals(double.class))
            return Double.class;
        else
            return clazz;
    }

}

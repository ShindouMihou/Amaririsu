package pw.mihou.amaririsu.features.reflective.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface AmaririsuContract {

    /**
     * The amount of time to cache this model in seconds. This is by default
     * caching the model up to 21600 seconds or 6 hours which is a standard
     * that is fine as most stories doesn't update and trending updates once
     * a day.
     *
     * @return The time in seconds to cache this model.
     */
    long cacheTTS() default 21600;

}

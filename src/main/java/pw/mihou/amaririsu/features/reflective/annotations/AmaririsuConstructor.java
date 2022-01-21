package pw.mihou.amaririsu.features.reflective.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is used to tell {@link pw.mihou.amaririsu.Amaririsu} that we want
 * this constructor to be selected whenever we are creating a model.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface AmaririsuConstructor {
}

package pw.mihou.amaririsu.features.reflective.annotations;

import pw.mihou.amaririsu.features.reflective.AmaririsuReflectiveCore;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This tells {@link AmaririsuReflectiveCore} to ignore this
 * field or parameter during reflection or creation of the model. This is handy for cases when
 * you need to handle stuff yourself.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AmaririsuInvisible {
}

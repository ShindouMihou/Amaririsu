package pw.mihou.amaririsu.features.reflective.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface AmaririsuField {

    /**
     * The name of the field that this will be accepting. For instance, if you want
     * a field to accept a story's title then all you have to do is write <b>title</b> or if
     * you want this field to accept the story's synopsis then write <b>synopsis</b>.
     *
     * @return The name of the field that this parameter or field will accept.
     */
    String name();

}

package pw.mihou.amaririsu.features.reflective.core;

import pw.mihou.amaririsu.features.reflective.facade.AmaririsuReflectiveFields;

import java.util.HashMap;
import java.util.Optional;

public class AmaririsuReflectiveFieldsCore implements AmaririsuReflectiveFields {
    
    public final HashMap<String, Object> fields = new HashMap<>();

    /**
     * This adds the field specified to the fields map. This is purely
     * an internal method used to erase the section where a HashMap needs
     * to be created first.
     *
     * @param name The name of the field.
     * @param value The value of the field.
     */
    public void addField(String name, Object value) {
        this.fields.put(name.toLowerCase(), value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> Optional<R> get(String field) {
        if (fields.containsKey(field.toLowerCase())) {
            return Optional.of((R) fields.get(field));
        }

        return Optional.empty();
    }

}

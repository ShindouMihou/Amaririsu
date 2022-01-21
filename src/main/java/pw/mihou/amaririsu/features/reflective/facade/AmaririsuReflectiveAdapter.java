package pw.mihou.amaririsu.features.reflective.facade;

public interface AmaririsuReflectiveAdapter<R> {

    /**
     * Transforms a set of fields into the specified class type
     * manually using the method written in this adapter.
     *
     * @param fields The fields to transform from.
     * @return The class type specified.
     */
    R transformFrom(AmaririsuReflectiveFields fields);

}

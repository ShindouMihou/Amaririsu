package pw.mihou.amaririsu.features.reflective.facade;

import pw.mihou.amaririsu.features.reflective.AmaririsuReflectiveCore;

public interface AmaririsuReflectiveFacade {

    /**
     * This is used to add an adapter when a variable requires a specific class
     * that {@link pw.mihou.amaririsu.features.reflective.facade.AmaririsuReflectiveFacade} cannot transform.
     *
     * @param tClass The class to transform into.
     * @param adapter The adapter of the class.
     */
    static void addAdapter(Class<?> tClass, AmaririsuReflectiveAdapter<?> adapter) {
        AmaririsuReflectiveCore.addAdapter(tClass, adapter);
    }

}

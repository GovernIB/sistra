/*
 * 
 */
package es.caib.consola;

/**
 * Class ServiceLocator.
 */
public final class ServiceLocator {

    /**
     * Atributo slc de ServiceLocator.
     */
    private static ServiceLocator slc;

    /**
     * Instancia un nuevo service locator de ServiceLocator.
     */
    private ServiceLocator() {
    }

    /**
     * Obtiene una instancia individual de ServiceLocator.
     * 
     * @return instancia individual de ServiceLocator
     */
    public static ServiceLocator getInstance() {
        if (slc == null) {
            slc = new ServiceLocator();
        }
        return slc;
    }

    
}

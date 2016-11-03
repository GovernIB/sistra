package es.caib.sistra.persistence.delegate;


/**
 * Define métodos estáticos para obtener delegates del interfaz de Sistra.
 */
public final class DelegateSISTRAUtil {

    private DelegateSISTRAUtil() {

    }
    
    public static SistraDelegate getSistraDelegate() {
        return (SistraDelegate) DelegateFactory.getDelegate(SistraDelegate.class);
    }
        
}

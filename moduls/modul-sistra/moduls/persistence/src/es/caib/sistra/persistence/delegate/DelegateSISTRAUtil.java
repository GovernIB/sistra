package es.caib.sistra.persistence.delegate;


/**
 * Define m�todos est�ticos para obtener delegates del interfaz de Sistra.
 */
public final class DelegateSISTRAUtil {

    private DelegateSISTRAUtil() {

    }
    
    public static SistraDelegate getSistraDelegate() {
        return (SistraDelegate) DelegateFactory.getDelegate(SistraDelegate.class);
    }
        
}

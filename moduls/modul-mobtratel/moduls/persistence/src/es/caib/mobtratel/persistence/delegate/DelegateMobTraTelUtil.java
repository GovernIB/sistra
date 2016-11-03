package es.caib.mobtratel.persistence.delegate;


/**
 * Define métodos estáticos para obtener delegates del interfaz de Sistra.
 */
public final class DelegateMobTraTelUtil {

    private DelegateMobTraTelUtil() {

    }
    
    public static MobTraTelDelegate getMobTraTelDelegate() {
        return (MobTraTelDelegate) DelegateFactory.getDelegate(MobTraTelDelegate.class);
    }
        
}

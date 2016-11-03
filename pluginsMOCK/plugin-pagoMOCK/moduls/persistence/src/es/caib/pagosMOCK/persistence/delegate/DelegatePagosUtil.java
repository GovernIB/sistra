package es.caib.pagosMOCK.persistence.delegate;


/**
 * Define métodos estáticos para obtener delegates del interfaz del asistente de pagos
 */
public final class DelegatePagosUtil {

    private DelegatePagosUtil() {

    }
    
    public static PagosDelegate getPagosDelegate() {
        return (PagosDelegate) DelegateFactory.getDelegate(PagosDelegate.class);
    }
        
}

package es.caib.pagosMOCK.persistence.delegate;


/**
 * Define m�todos est�ticos para obtener delegates del interfaz del asistente de pagos
 */
public final class DelegatePagosUtil {

    private DelegatePagosUtil() {

    }
    
    public static PagosDelegate getPagosDelegate() {
        return (PagosDelegate) DelegateFactory.getDelegate(PagosDelegate.class);
    }
        
}

package es.caib.pagos.persistence.delegate;

/**
 * Define métodos estáticos para obtener delegates locales
 */
public final class DelegateUtil {

    private DelegateUtil() {

    }
  
    public static SesionPagoDelegate getSesionPagoDelegate() {
        return (SesionPagoDelegate) DelegateFactory.getDelegate(SesionPagoDelegate.class);
    }
    
 }

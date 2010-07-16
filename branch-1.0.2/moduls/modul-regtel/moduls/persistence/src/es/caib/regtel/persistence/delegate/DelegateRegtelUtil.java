package es.caib.regtel.persistence.delegate;


/**
 * Define métodos estáticos para obtener delegates del interfaz del Registro Telemático
 */
public final class DelegateRegtelUtil {

    private DelegateRegtelUtil() {

    }
    
    public static RegistroTelematicoDelegate getRegistroTelematicoDelegate() {
        return (RegistroTelematicoDelegate) DelegateFactory.getDelegate(RegistroTelematicoDelegate.class);
    }
        
}

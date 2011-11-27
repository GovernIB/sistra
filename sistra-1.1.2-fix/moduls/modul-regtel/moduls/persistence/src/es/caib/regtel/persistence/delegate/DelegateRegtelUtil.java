package es.caib.regtel.persistence.delegate;


/**
 * Define m�todos est�ticos para obtener delegates del interfaz del Registro Telem�tico
 */
public final class DelegateRegtelUtil {

    private DelegateRegtelUtil() {

    }
    
    public static RegistroTelematicoDelegate getRegistroTelematicoDelegate() {
        return (RegistroTelematicoDelegate) DelegateFactory.getDelegate(RegistroTelematicoDelegate.class);
    }
        
}

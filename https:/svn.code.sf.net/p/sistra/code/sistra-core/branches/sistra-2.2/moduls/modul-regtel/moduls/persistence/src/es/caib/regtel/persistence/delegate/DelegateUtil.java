package es.caib.regtel.persistence.delegate;



/**
 * Define métodos estáticos para obtener delegates.
 */
public final class DelegateUtil {

    private DelegateUtil() {

    }

    public static RegistroOrganismoDelegate getRegistroOrganismoDelegate() {
        return (RegistroOrganismoDelegate) DelegateFactory.getDelegate(RegistroOrganismoDelegate.class);
    }
        
    public static RegistroTelematicoWsDelegate getRegistroTelematicoWsDelegate() {
        return (RegistroTelematicoWsDelegate) DelegateFactory.getDelegate(RegistroTelematicoWsDelegate.class);
    }
    
    public static ConsultaPADDelegate getConsultaPADDelegate() {
        return (ConsultaPADDelegate) DelegateFactory.getDelegate(ConsultaPADDelegate.class);
    }
    
}

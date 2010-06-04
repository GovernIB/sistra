package es.caib.redose.persistence.delegate;


/**
 * Define métodos estáticos para obtener delegates del interfaz del RDS.
 */
public final class DelegateRDSUtil {

    private DelegateRDSUtil() {

    }
    
    public static RdsDelegate getRdsDelegate() {
        return (RdsDelegate) DelegateFactory.getDelegate(RdsDelegate.class);
    }
        
}

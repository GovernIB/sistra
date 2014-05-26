package es.caib.redose.persistence.delegate;


/**
 * Define m�todos est�ticos para obtener delegates del interfaz del RDS.
 */
public final class DelegateRDSUtil {

    private DelegateRDSUtil() {

    }
    
    public static RdsDelegate getRdsDelegate() {
        return (RdsDelegate) DelegateFactory.getDelegate(RdsDelegate.class);
    }
        
}

package es.caib.redose.persistence.delegate;



/**
 * Define métodos estáticos para obtener delegates.
 */
public final class DelegateUtil {

    private DelegateUtil() {

    }

    public static ModeloDelegate getModeloDelegate() {
        return (ModeloDelegate) DelegateFactory.getDelegate(ModeloDelegate.class);
    }
    
    public static VersionDelegate getVersionDelegate() {
        return (VersionDelegate) DelegateFactory.getDelegate(VersionDelegate.class);
    }
    
    public static PlantillaDelegate getPlantillaDelegate() {
        return (PlantillaDelegate) DelegateFactory.getDelegate(PlantillaDelegate.class);
    }
    
    public static FormateadorDelegate getFormateadorDelegate() {
        return (FormateadorDelegate) DelegateFactory.getDelegate(FormateadorDelegate.class);
    }
    
    public static PlantillaIdiomaDelegate getPlantillaIdiomaDelegate() {
        return (PlantillaIdiomaDelegate) DelegateFactory.getDelegate(PlantillaIdiomaDelegate.class);
    }
    
    public static UbicacionDelegate getUbicacionDelegate() {
        return (UbicacionDelegate) DelegateFactory.getDelegate(UbicacionDelegate.class);
    }
    
    public static LogOperacionDelegate getLogOperacionDelegate() {
        return (LogOperacionDelegate) DelegateFactory.getDelegate(LogOperacionDelegate.class);
    }
    
    public static RdsDelegate getRdsDelegate() {
        return (RdsDelegate) DelegateFactory.getDelegate(RdsDelegate.class);
    }
    
    public static IdiomaDelegate getIdiomaDelegate() {
        return (IdiomaDelegate) DelegateFactory.getDelegate(IdiomaDelegate.class);
    }
    
    public static RdsAdminDelegate getRdsAdminDelegate() {
        return (RdsAdminDelegate) DelegateFactory.getDelegate(RdsAdminDelegate.class);
    }
    
    public static RdsProcesosDelegate getRdsProcesosDelegate() {
        return (RdsProcesosDelegate) DelegateFactory.getDelegate(RdsProcesosDelegate.class);
    }
    
    public static ConfiguracionDelegate getConfiguracionDelegate() {
        return (ConfiguracionDelegate) DelegateFactory.getDelegate(ConfiguracionDelegate.class);
    }
    
    public static VersionCustodiaDelegate getVersionCustodiaDelegate() {
        return (VersionCustodiaDelegate) DelegateFactory.getDelegate(VersionCustodiaDelegate.class);
    }
   
    public static LogGestorDocumentalErroresDelegate getLogErrorGestorDocumentalDelegate() {
        return (LogGestorDocumentalErroresDelegate) DelegateFactory.getDelegate(LogGestorDocumentalErroresDelegate.class);
    }
    
    public static FicheroExternoDelegate getFicheroExternoDelegate() {
        return (FicheroExternoDelegate) DelegateFactory.getDelegate(FicheroExternoDelegate.class);
    }
}

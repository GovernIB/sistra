package es.caib.zonaper.persistence.delegate;

/**
 * Define métodos estáticos para obtener delegates.
 */
public final class DelegateUtil {

    private DelegateUtil() {

    }
   
    public static TramitePersistenteDelegate getTramitePersistenteDelegate() {
        return (TramitePersistenteDelegate) DelegateFactory.getDelegate(TramitePersistenteDelegate.class);
    }
  
    public static EntradaTelematicaDelegate getEntradaTelematicaDelegate() {
        return (EntradaTelematicaDelegate) DelegateFactory.getDelegate(EntradaTelematicaDelegate.class);
    }
    
    public static NotificacionTelematicaDelegate getNotificacionTelematicaDelegate() {
        return (NotificacionTelematicaDelegate) DelegateFactory.getDelegate(NotificacionTelematicaDelegate.class);
    }
    
    public static EntradaPreregistroDelegate getEntradaPreregistroDelegate() {
        return (EntradaPreregistroDelegate) DelegateFactory.getDelegate(EntradaPreregistroDelegate.class);
    }
    
    public static BackupDelegate getBackupDelegate()
    {
    	return ( BackupDelegate ) DelegateFactory.getDelegate( BackupDelegate.class );
    }
    
    public static PadAplicacionDelegate getPadAplicacionDelegate()
    {
    	return ( PadAplicacionDelegate ) DelegateFactory.getDelegate( PadAplicacionDelegate.class );
    }
        
    public static ExpedienteDelegate getExpedienteDelegate()
    {
    	return ( ExpedienteDelegate ) DelegateFactory.getDelegate( ExpedienteDelegate.class );
    }
    
    public static EventoExpedienteDelegate getEventoExpedienteDelegate()
    {
    	return ( EventoExpedienteDelegate ) DelegateFactory.getDelegate( EventoExpedienteDelegate.class );
    }
    
    public static ElementoExpedienteDelegate getElementoExpedienteDelegate()
    {
    	return ( ElementoExpedienteDelegate ) DelegateFactory.getDelegate( ElementoExpedienteDelegate.class );
    }
    
    public static EstadoExpedienteDelegate getEstadoExpedienteDelegate()
    {
    	return ( EstadoExpedienteDelegate ) DelegateFactory.getDelegate( EstadoExpedienteDelegate.class );
    }
    
    
    public static ProcesoBackupDelegate getProcesoBackupDelegate()
    {
    	return ( ProcesoBackupDelegate ) DelegateFactory.getDelegate( ProcesoBackupDelegate.class );
    }

    public static ProcesosAutoDelegate getProcesosAutoDelegate()
    {
    	return ( ProcesosAutoDelegate ) DelegateFactory.getDelegate( ProcesosAutoDelegate.class );
    }
 
    public static ConsultaPADDelegate getConsultaPADDelegate(){
    	return (ConsultaPADDelegate) DelegateFactory.getDelegate(ConsultaPADDelegate.class);
    }
    
    public static DominiosDelegate getDominiosDelegate(){
    	return (DominiosDelegate) DelegateFactory.getDelegate(DominiosDelegate.class);
    }
    
    public static ConfiguracionDelegate getConfiguracionDelegate() {
        return (ConfiguracionDelegate) DelegateFactory.getDelegate(ConfiguracionDelegate.class);
    }
    
    public static RegistroExternoDelegate getRegistroExternoDelegate() {
        return (RegistroExternoDelegate) DelegateFactory.getDelegate(RegistroExternoDelegate.class);
    }
    
    public static RegistroExternoPreparadoDelegate getRegistroExternoPreparadoDelegate() {
        return (RegistroExternoPreparadoDelegate) DelegateFactory.getDelegate(RegistroExternoPreparadoDelegate.class);
    }
    
    public static LogRegistroDelegate getLogRegistroDelegate() {
        return (LogRegistroDelegate) DelegateFactory.getDelegate(LogRegistroDelegate.class);
    }
    
    public static DelegacionDelegate getDelegacionDelegate() {
        return (DelegacionDelegate) DelegateFactory.getDelegate(DelegacionDelegate.class);
    }
    
    public static BandejaFirmaDelegate getBandejaFirmaDelegate() {
        return (BandejaFirmaDelegate) DelegateFactory.getDelegate(BandejaFirmaDelegate.class);
    }
    
    public static AvisosDelegacionDelegate getAvisosDelegacionDelegate() {
        return (AvisosDelegacionDelegate) DelegateFactory.getDelegate(AvisosDelegacionDelegate.class);
    }
}


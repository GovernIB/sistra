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
}


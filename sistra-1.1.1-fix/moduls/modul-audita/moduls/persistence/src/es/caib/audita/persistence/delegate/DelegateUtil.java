package es.caib.audita.persistence.delegate;

/**
 * Define métodos estáticos para obtener delegates.
 */
public final class DelegateUtil 
{
    public static LoggerEventoDelegate getLoggerEventoDelegate()
    {
    	return ( LoggerEventoDelegate ) DelegateFactory.getDelegate( LoggerEventoDelegate.class );
    }
    
    public static AuditoriaDelegate getAuditoriaDelegate()
    {
    	return ( AuditoriaDelegate ) DelegateFactory.getDelegate( AuditoriaDelegate.class );
    }
    
    public static ConfiguracionDelegate getConfiguracionDelegate()
    {
    	return ( ConfiguracionDelegate ) DelegateFactory.getDelegate( ConfiguracionDelegate.class );
    }
    
    public static CuadroMandoInicioDelegate getCuadroMandoInicioDelegate()
    {
    	return ( CuadroMandoInicioDelegate ) DelegateFactory.getDelegate( CuadroMandoInicioDelegate.class );
    }
}

package es.caib.mobtratel.persistence.delegate;


/**
 * Define métodos estáticos para obtener delegates.
 */
public final class DelegateUtil {

    private DelegateUtil() {

    }

    public static EnvioDelegate getEnvioDelegate() {
        return (EnvioDelegate) DelegateFactory.getDelegate(EnvioDelegate.class);
    }

    public static CuentaDelegate getCuentaDelegate() {
        return (CuentaDelegate) DelegateFactory.getDelegate(CuentaDelegate.class);
    }

    public static PermisoDelegate getPermisoDelegate() {
        return (PermisoDelegate) DelegateFactory.getDelegate(PermisoDelegate.class);
    }

    public static ProcEnviosDelegate getProcEnviosDelegate() {
        return (ProcEnviosDelegate) DelegateFactory.getDelegate(ProcEnviosDelegate.class);
    }

    public static ConfiguracionDelegate getConfiguracionDelegate() {
        return (ConfiguracionDelegate) DelegateFactory.getDelegate(ConfiguracionDelegate.class);
    }
    
}

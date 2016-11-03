package org.ibit.rol.form.persistence.delegate;

/**
 * Define métodos estáticos para obtener delegates.
 */
public final class DelegateUtil {

    private DelegateUtil() {

    }

    public static AyudaDelegate getAyudaDelegate() {
        return (AyudaDelegate) DelegateFactory.getDelegate(AyudaDelegate.class);
    }

    public static ComponenteDelegate getComponenteDelegate() {
        return (ComponenteDelegate) DelegateFactory.getDelegate(ComponenteDelegate.class);
    }

    public static FormularioDelegate getFormularioDelegate() {
        return (FormularioDelegate) DelegateFactory.getDelegate(FormularioDelegate.class);
    }

    public static GruposDelegate getGruposDelegate() {
        return (GruposDelegate) DelegateFactory.getDelegate(GruposDelegate.class);
    }
    
    public static IdiomaDelegate getIdiomaDelegate() {
        return (IdiomaDelegate) DelegateFactory.getDelegate(IdiomaDelegate.class);
    }

    public static MascaraDelegate getMascaraDelegate() {
        return (MascaraDelegate) DelegateFactory.getDelegate(MascaraDelegate.class);
    }

    public static PantallaDelegate getPantallaDelegate() {
        return (PantallaDelegate) DelegateFactory.getDelegate(PantallaDelegate.class);
    }

    public static PaletaDelegate getPaletaDelegate() {
        return (PaletaDelegate) DelegateFactory.getDelegate(PaletaDelegate.class);
    }

    public static PatronDelegate getPatronDelegate() {
        return (PatronDelegate) DelegateFactory.getDelegate(PatronDelegate.class);
    }

    public static PerfilDelegate getPerfilDelegate() {
        return (PerfilDelegate) DelegateFactory.getDelegate(PerfilDelegate.class);
    }

    public static ValorPosibleDelegate getValorPosibleDelegate() {
        return (ValorPosibleDelegate) DelegateFactory.getDelegate(ValorPosibleDelegate.class);
    }

    public static ValidadorFirmaDelegate getValidadorFirmaDelegate() {
        return (ValidadorFirmaDelegate) DelegateFactory.getDelegate(ValidadorFirmaDelegate.class);
    }

    public static PuntoSalidaDelegate getPuntoSalidaDelegate() {
        return (PuntoSalidaDelegate) DelegateFactory.getDelegate(PuntoSalidaDelegate.class);
    }

    public static PropiedadSalidaDelegate getPropiedadSalidaDelegate() {
        return (PropiedadSalidaDelegate) DelegateFactory.getDelegate(PropiedadSalidaDelegate.class);
    }
    
    public static VersionDelegate getVersionDelegate() {
        return (VersionDelegate) DelegateFactory.getDelegate(VersionDelegate.class);
    }

    public static InstanciaDelegate getInstanciaDelegate(boolean local) {
        if (local) {
            return (InstanciaDelegate) DelegateFactory.getDelegate(InstanciaLocalDelegate.class);
        } else {
            return (InstanciaDelegate) DelegateFactory.getDelegate(InstanciaRemoteDelegate.class);
        }
    }

    public static InstanciaTelematicaDelegate getInstanciaTelematica() {
        return (InstanciaTelematicaLocalDelegate) DelegateFactory.getDelegate(InstanciaTelematicaLocalDelegate.class);
    }
    
    public static ConfiguracionDelegate getConfiguracionDelegate() {
        return (ConfiguracionDelegate) DelegateFactory.getDelegate(ConfiguracionDelegate.class);
    }
}

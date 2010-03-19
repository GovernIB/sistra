package es.caib.sistra.persistence.delegate;

/**
 * Define métodos estáticos para obtener delegates.
 */
public final class DelegateUtil {

    private DelegateUtil() {

    }

    public static IdiomaDelegate getIdiomaDelegate() {
        return (IdiomaDelegate) DelegateFactory.getDelegate(IdiomaDelegate.class);
    }
    
    public static DatoJustificanteDelegate getDatoJustificanteDelegate() {
        return (DatoJustificanteDelegate) DelegateFactory.getDelegate(DatoJustificanteDelegate.class);
    }
    
    public static DocumentoDelegate getDocumentoDelegate() {
        return (DocumentoDelegate) DelegateFactory.getDelegate(DocumentoDelegate.class);
    }
    
    public static DocumentoNivelDelegate getDocumentoNivelDelegate() {
        return (DocumentoNivelDelegate) DelegateFactory.getDelegate(DocumentoNivelDelegate.class);
    }
    
    public static EspecTramiteNivelDelegate getEspecTramiteNivelDelegate() {
        return (EspecTramiteNivelDelegate) DelegateFactory.getDelegate(EspecTramiteNivelDelegate.class);
    }
    
    public static MensajeTramiteDelegate getMensajeTramiteDelegate() {
        return (MensajeTramiteDelegate) DelegateFactory.getDelegate(MensajeTramiteDelegate.class);
    }
    
    public static TramiteDelegate getTramiteDelegate() {
        return (TramiteDelegate) DelegateFactory.getDelegate(TramiteDelegate.class);
    }
    
    public static MensajePlataformaDelegate getMensajePlataformaDelegate() {
        return (MensajePlataformaDelegate) DelegateFactory.getDelegate(MensajePlataformaDelegate.class);
    }
    
    public static TramiteNivelDelegate getTramiteNivelDelegate() {
        return (TramiteNivelDelegate) DelegateFactory.getDelegate(TramiteNivelDelegate.class);
    }
    
    public static TramiteVersionDelegate getTramiteVersionDelegate() {
        return (TramiteVersionDelegate) DelegateFactory.getDelegate(TramiteVersionDelegate.class);
    }
    
    public static OrganoResponsableDelegate getOrganoResponsableDelegate() {
        return (OrganoResponsableDelegate) DelegateFactory.getDelegate(OrganoResponsableDelegate.class);
    }
    
    public static DominioDelegate getDominioDelegate() {
        return (DominioDelegate) DelegateFactory.getDelegate(DominioDelegate.class);
    }
    
    public static GeneradorDelegate getGeneradorDelegate() {
        return (GeneradorDelegate) DelegateFactory.getDelegate(GeneradorDelegate.class);
    }
    
    public static CuadernoCargaDelegate getCuadernoCargaDelegate()
    {
    	return ( CuadernoCargaDelegate ) DelegateFactory.getDelegate( CuadernoCargaDelegate.class );
    }
    
    public static FicheroCuadernoDelegate getFicheroCuadernoDelegate()
    {
    	return ( FicheroCuadernoDelegate ) DelegateFactory.getDelegate( FicheroCuadernoDelegate.class );
    }
    
    public static AuditoriaCuadernoDelegate getAuditoriaCuadernoDelegate()
    {
    	return ( AuditoriaCuadernoDelegate ) DelegateFactory.getDelegate( AuditoriaCuadernoDelegate.class ); 
    }
    
    public static InstanciaDelegate getInstanciaDelegate(boolean local) {
        if (local) {
            return (InstanciaDelegate) DelegateFactory.getDelegate(InstanciaLocalDelegate.class);
        } else {
            return (InstanciaDelegate) DelegateFactory.getDelegate(InstanciaRemoteDelegate.class);
        }
    }
    
    public static ConsultaPADDelegate getConsultaPADDelegate(){
    	return (ConsultaPADDelegate) DelegateFactory.getDelegate(ConsultaPADDelegate.class);
    }
    
    public static ConfiguracionDelegate getConfiguracionDelegate(){
    	return (ConfiguracionDelegate) DelegateFactory.getDelegate(ConfiguracionDelegate.class);
    }
    
    public static GestorFormularioDelegate getGestorFormularioDelegate(){
    	return (GestorFormularioDelegate) DelegateFactory.getDelegate(GestorFormularioDelegate.class);
    }
            
}

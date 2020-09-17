
package es.caib.zonaper.ws.v2.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.caib.zonaper.ws.v2.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EventoExpedienteAccesiblePorClave_QNAME = new QName("", "accesiblePorClave");
    private final static QName _EventoExpedienteDocumentos_QNAME = new QName("", "documentos");
    private final static QName _EventoExpedienteFecha_QNAME = new QName("", "fecha");
    private final static QName _EventoExpedienteTextoSMS_QNAME = new QName("", "textoSMS");
    private final static QName _EventoExpedienteEnlaceConsulta_QNAME = new QName("", "enlaceConsulta");
    private final static QName _Fault_QNAME = new QName("urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", "fault");
    private final static QName _ConfiguracionAvisosExpedienteAvisoSMS_QNAME = new QName("", "avisoSMS");
    private final static QName _ConfiguracionAvisosExpedienteHabilitarAvisos_QNAME = new QName("", "habilitarAvisos");
    private final static QName _ConfiguracionAvisosExpedienteAvisoEmail_QNAME = new QName("", "avisoEmail");
    private final static QName _ExpedienteNifRepresentado_QNAME = new QName("", "nifRepresentado");
    private final static QName _ExpedienteConfiguracionAvisos_QNAME = new QName("", "configuracionAvisos");
    private final static QName _ExpedienteNombreRepresentado_QNAME = new QName("", "nombreRepresentado");
    private final static QName _ExpedienteEventos_QNAME = new QName("", "eventos");
    private final static QName _ExpedienteIdentificadorProcedimiento_QNAME = new QName("", "identificadorProcedimiento");
    private final static QName _ExpedienteNifRepresentante_QNAME = new QName("", "nifRepresentante");
    private final static QName _ExpedienteNumeroEntradaBTE_QNAME = new QName("", "numeroEntradaBTE");
    private final static QName _DocumentoExpedienteNombre_QNAME = new QName("", "nombre");
    private final static QName _DocumentoExpedienteTitulo_QNAME = new QName("", "titulo");
    private final static QName _DocumentoExpedienteClaveRDS_QNAME = new QName("", "claveRDS");
    private final static QName _DocumentoExpedienteCodigoRDS_QNAME = new QName("", "codigoRDS");
    private final static QName _DocumentoExpedienteVersionRDS_QNAME = new QName("", "versionRDS");
    private final static QName _DocumentoExpedienteModeloRDS_QNAME = new QName("", "modeloRDS");
    private final static QName _DocumentoExpedienteEstructurado_QNAME = new QName("", "estructurado");
    private final static QName _DocumentoExpedienteContenidoFichero_QNAME = new QName("", "contenidoFichero");
    private final static QName _ObtenerElementosExpedienteTamPagina_QNAME = new QName("urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", "tamPagina");
    private final static QName _ObtenerElementosExpedientePagina_QNAME = new QName("urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", "pagina");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.caib.zonaper.ws.v2.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AltaEventoExpediente }
     * 
     */
    public AltaEventoExpediente createAltaEventoExpediente() {
        return new AltaEventoExpediente();
    }

    /**
     * Create an instance of {@link TramitePersistente }
     * 
     */
    public TramitePersistente createTramitePersistente() {
        return new TramitePersistente();
    }

    /**
     * Create an instance of {@link ExisteZonaPersonalUsuario }
     * 
     */
    public ExisteZonaPersonalUsuario createExisteZonaPersonalUsuario() {
        return new ExisteZonaPersonalUsuario();
    }

    /**
     * Create an instance of {@link Expediente }
     * 
     */
    public Expediente createExpediente() {
        return new Expediente();
    }

    /**
     * Create an instance of {@link ObtenerTotalElementosExpediente }
     * 
     */
    public ObtenerTotalElementosExpediente createObtenerTotalElementosExpediente() {
        return new ObtenerTotalElementosExpediente();
    }

    /**
     * Create an instance of {@link ObtenerElementosExpedienteResponse }
     * 
     */
    public ObtenerElementosExpedienteResponse createObtenerElementosExpedienteResponse() {
        return new ObtenerElementosExpedienteResponse();
    }

    /**
     * Create an instance of {@link ExisteExpediente }
     * 
     */
    public ExisteExpediente createExisteExpediente() {
        return new ExisteExpediente();
    }

    /**
     * Create an instance of {@link ObtenerElementosExpediente }
     * 
     */
    public ObtenerElementosExpediente createObtenerElementosExpediente() {
        return new ObtenerElementosExpediente();
    }

    /**
     * Create an instance of {@link EstadoPago }
     * 
     */
    public EstadoPago createEstadoPago() {
        return new EstadoPago();
    }

    /**
     * Create an instance of {@link ObtenerEstadoPagosTramite }
     * 
     */
    public ObtenerEstadoPagosTramite createObtenerEstadoPagosTramite() {
        return new ObtenerEstadoPagosTramite();
    }

    /**
     * Create an instance of {@link AltaExpedienteResponse }
     * 
     */
    public AltaExpedienteResponse createAltaExpedienteResponse() {
        return new AltaExpedienteResponse();
    }

    /**
     * Create an instance of {@link AltaZonaPersonalUsuarioResponse }
     * 
     */
    public AltaZonaPersonalUsuarioResponse createAltaZonaPersonalUsuarioResponse() {
        return new AltaZonaPersonalUsuarioResponse();
    }

    /**
     * Create an instance of {@link DocumentosExpediente }
     * 
     */
    public DocumentosExpediente createDocumentosExpediente() {
        return new DocumentosExpediente();
    }

    /**
     * Create an instance of {@link ObtenerPersistentes }
     * 
     */
    public ObtenerPersistentes createObtenerPersistentes() {
        return new ObtenerPersistentes();
    }

    /**
     * Create an instance of {@link ExisteZonaPersonalUsuarioResponse }
     * 
     */
    public ExisteZonaPersonalUsuarioResponse createExisteZonaPersonalUsuarioResponse() {
        return new ExisteZonaPersonalUsuarioResponse();
    }

    /**
     * Create an instance of {@link ObtenerUrlAccesoAnonimo }
     * 
     */
    public ObtenerUrlAccesoAnonimo createObtenerUrlAccesoAnonimo() {
        return new ObtenerUrlAccesoAnonimo();
    }

    /**
     * Create an instance of {@link EventoExpediente }
     * 
     */
    public EventoExpediente createEventoExpediente() {
        return new EventoExpediente();
    }

    /**
     * Create an instance of {@link TramitesPersistentes }
     * 
     */
    public TramitesPersistentes createTramitesPersistentes() {
        return new TramitesPersistentes();
    }

    /**
     * Create an instance of {@link ModificarAvisosExpediente }
     * 
     */
    public ModificarAvisosExpediente createModificarAvisosExpediente() {
        return new ModificarAvisosExpediente();
    }

    /**
     * Create an instance of {@link ConfiguracionAvisosExpediente }
     * 
     */
    public ConfiguracionAvisosExpediente createConfiguracionAvisosExpediente() {
        return new ConfiguracionAvisosExpediente();
    }

    /**
     * Create an instance of {@link DocumentoExpediente }
     * 
     */
    public DocumentoExpediente createDocumentoExpediente() {
        return new DocumentoExpediente();
    }

    /**
     * Create an instance of {@link ObtenerUrlAccesoAnonimoResponse }
     * 
     */
    public ObtenerUrlAccesoAnonimoResponse createObtenerUrlAccesoAnonimoResponse() {
        return new ObtenerUrlAccesoAnonimoResponse();
    }

    /**
     * Create an instance of {@link ObtenerEstadoPagosTramiteResponse }
     * 
     */
    public ObtenerEstadoPagosTramiteResponse createObtenerEstadoPagosTramiteResponse() {
        return new ObtenerEstadoPagosTramiteResponse();
    }

    /**
     * Create an instance of {@link BackofficeFacadeException }
     * 
     */
    public BackofficeFacadeException createBackofficeFacadeException() {
        return new BackofficeFacadeException();
    }

    /**
     * Create an instance of {@link ObtenerTotalElementosExpedienteResponse }
     * 
     */
    public ObtenerTotalElementosExpedienteResponse createObtenerTotalElementosExpedienteResponse() {
        return new ObtenerTotalElementosExpedienteResponse();
    }

    /**
     * Create an instance of {@link EstadoPagos }
     * 
     */
    public EstadoPagos createEstadoPagos() {
        return new EstadoPagos();
    }

    /**
     * Create an instance of {@link ExisteExpedienteResponse }
     * 
     */
    public ExisteExpedienteResponse createExisteExpedienteResponse() {
        return new ExisteExpedienteResponse();
    }

    /**
     * Create an instance of {@link ModificarAvisosExpedienteResponse }
     * 
     */
    public ModificarAvisosExpedienteResponse createModificarAvisosExpedienteResponse() {
        return new ModificarAvisosExpedienteResponse();
    }

    /**
     * Create an instance of {@link ObtenerPersistentesResponse }
     * 
     */
    public ObtenerPersistentesResponse createObtenerPersistentesResponse() {
        return new ObtenerPersistentesResponse();
    }

    /**
     * Create an instance of {@link EventosExpediente }
     * 
     */
    public EventosExpediente createEventosExpediente() {
        return new EventosExpediente();
    }

    /**
     * Create an instance of {@link AltaEventoExpedienteResponse }
     * 
     */
    public AltaEventoExpedienteResponse createAltaEventoExpedienteResponse() {
        return new AltaEventoExpedienteResponse();
    }

    /**
     * Create an instance of {@link AltaExpediente }
     * 
     */
    public AltaExpediente createAltaExpediente() {
        return new AltaExpediente();
    }

    /**
     * Create an instance of {@link AltaZonaPersonalUsuario }
     * 
     */
    public AltaZonaPersonalUsuario createAltaZonaPersonalUsuario() {
        return new AltaZonaPersonalUsuario();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "accesiblePorClave", scope = EventoExpediente.class)
    public JAXBElement<Boolean> createEventoExpedienteAccesiblePorClave(Boolean value) {
        return new JAXBElement<Boolean>(_EventoExpedienteAccesiblePorClave_QNAME, Boolean.class, EventoExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentosExpediente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "documentos", scope = EventoExpediente.class)
    public JAXBElement<DocumentosExpediente> createEventoExpedienteDocumentos(DocumentosExpediente value) {
        return new JAXBElement<DocumentosExpediente>(_EventoExpedienteDocumentos_QNAME, DocumentosExpediente.class, EventoExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fecha", scope = EventoExpediente.class)
    public JAXBElement<String> createEventoExpedienteFecha(String value) {
        return new JAXBElement<String>(_EventoExpedienteFecha_QNAME, String.class, EventoExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "textoSMS", scope = EventoExpediente.class)
    public JAXBElement<String> createEventoExpedienteTextoSMS(String value) {
        return new JAXBElement<String>(_EventoExpedienteTextoSMS_QNAME, String.class, EventoExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "enlaceConsulta", scope = EventoExpediente.class)
    public JAXBElement<String> createEventoExpedienteEnlaceConsulta(String value) {
        return new JAXBElement<String>(_EventoExpedienteEnlaceConsulta_QNAME, String.class, EventoExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BackofficeFacadeException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", name = "fault")
    public JAXBElement<BackofficeFacadeException> createFault(BackofficeFacadeException value) {
        return new JAXBElement<BackofficeFacadeException>(_Fault_QNAME, BackofficeFacadeException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "avisoSMS", scope = ConfiguracionAvisosExpediente.class)
    public JAXBElement<String> createConfiguracionAvisosExpedienteAvisoSMS(String value) {
        return new JAXBElement<String>(_ConfiguracionAvisosExpedienteAvisoSMS_QNAME, String.class, ConfiguracionAvisosExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "habilitarAvisos", scope = ConfiguracionAvisosExpediente.class)
    public JAXBElement<Boolean> createConfiguracionAvisosExpedienteHabilitarAvisos(Boolean value) {
        return new JAXBElement<Boolean>(_ConfiguracionAvisosExpedienteHabilitarAvisos_QNAME, Boolean.class, ConfiguracionAvisosExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "avisoEmail", scope = ConfiguracionAvisosExpediente.class)
    public JAXBElement<String> createConfiguracionAvisosExpedienteAvisoEmail(String value) {
        return new JAXBElement<String>(_ConfiguracionAvisosExpedienteAvisoEmail_QNAME, String.class, ConfiguracionAvisosExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nifRepresentado", scope = Expediente.class)
    public JAXBElement<String> createExpedienteNifRepresentado(String value) {
        return new JAXBElement<String>(_ExpedienteNifRepresentado_QNAME, String.class, Expediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfiguracionAvisosExpediente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "configuracionAvisos", scope = Expediente.class)
    public JAXBElement<ConfiguracionAvisosExpediente> createExpedienteConfiguracionAvisos(ConfiguracionAvisosExpediente value) {
        return new JAXBElement<ConfiguracionAvisosExpediente>(_ExpedienteConfiguracionAvisos_QNAME, ConfiguracionAvisosExpediente.class, Expediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nombreRepresentado", scope = Expediente.class)
    public JAXBElement<String> createExpedienteNombreRepresentado(String value) {
        return new JAXBElement<String>(_ExpedienteNombreRepresentado_QNAME, String.class, Expediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventosExpediente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "eventos", scope = Expediente.class)
    public JAXBElement<EventosExpediente> createExpedienteEventos(EventosExpediente value) {
        return new JAXBElement<EventosExpediente>(_ExpedienteEventos_QNAME, EventosExpediente.class, Expediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "identificadorProcedimiento", scope = Expediente.class)
    public JAXBElement<String> createExpedienteIdentificadorProcedimiento(String value) {
        return new JAXBElement<String>(_ExpedienteIdentificadorProcedimiento_QNAME, String.class, Expediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nifRepresentante", scope = Expediente.class)
    public JAXBElement<String> createExpedienteNifRepresentante(String value) {
        return new JAXBElement<String>(_ExpedienteNifRepresentante_QNAME, String.class, Expediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "numeroEntradaBTE", scope = Expediente.class)
    public JAXBElement<String> createExpedienteNumeroEntradaBTE(String value) {
        return new JAXBElement<String>(_ExpedienteNumeroEntradaBTE_QNAME, String.class, Expediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nombre", scope = DocumentoExpediente.class)
    public JAXBElement<String> createDocumentoExpedienteNombre(String value) {
        return new JAXBElement<String>(_DocumentoExpedienteNombre_QNAME, String.class, DocumentoExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "titulo", scope = DocumentoExpediente.class)
    public JAXBElement<String> createDocumentoExpedienteTitulo(String value) {
        return new JAXBElement<String>(_DocumentoExpedienteTitulo_QNAME, String.class, DocumentoExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "claveRDS", scope = DocumentoExpediente.class)
    public JAXBElement<String> createDocumentoExpedienteClaveRDS(String value) {
        return new JAXBElement<String>(_DocumentoExpedienteClaveRDS_QNAME, String.class, DocumentoExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "codigoRDS", scope = DocumentoExpediente.class)
    public JAXBElement<Long> createDocumentoExpedienteCodigoRDS(Long value) {
        return new JAXBElement<Long>(_DocumentoExpedienteCodigoRDS_QNAME, Long.class, DocumentoExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "versionRDS", scope = DocumentoExpediente.class)
    public JAXBElement<Integer> createDocumentoExpedienteVersionRDS(Integer value) {
        return new JAXBElement<Integer>(_DocumentoExpedienteVersionRDS_QNAME, Integer.class, DocumentoExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "modeloRDS", scope = DocumentoExpediente.class)
    public JAXBElement<String> createDocumentoExpedienteModeloRDS(String value) {
        return new JAXBElement<String>(_DocumentoExpedienteModeloRDS_QNAME, String.class, DocumentoExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "estructurado", scope = DocumentoExpediente.class)
    public JAXBElement<Boolean> createDocumentoExpedienteEstructurado(Boolean value) {
        return new JAXBElement<Boolean>(_DocumentoExpedienteEstructurado_QNAME, Boolean.class, DocumentoExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "contenidoFichero", scope = DocumentoExpediente.class)
    public JAXBElement<byte[]> createDocumentoExpedienteContenidoFichero(byte[] value) {
        return new JAXBElement<byte[]>(_DocumentoExpedienteContenidoFichero_QNAME, byte[].class, DocumentoExpediente.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", name = "tamPagina", scope = ObtenerElementosExpediente.class)
    public JAXBElement<Integer> createObtenerElementosExpedienteTamPagina(Integer value) {
        return new JAXBElement<Integer>(_ObtenerElementosExpedienteTamPagina_QNAME, Integer.class, ObtenerElementosExpediente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", name = "pagina", scope = ObtenerElementosExpediente.class)
    public JAXBElement<Integer> createObtenerElementosExpedientePagina(Integer value) {
        return new JAXBElement<Integer>(_ObtenerElementosExpedientePagina_QNAME, Integer.class, ObtenerElementosExpediente.class, value);
    }

}

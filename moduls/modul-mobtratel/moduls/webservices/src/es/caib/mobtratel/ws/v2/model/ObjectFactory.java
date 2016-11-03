
package es.caib.mobtratel.ws.v2.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.caib.mobtratel.ws.v2.model package. 
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

    private final static QName _Fault_QNAME = new QName("urn:es:caib:mobtratel:ws:v2:model:BackofficeFacade", "fault");
    private final static QName _MensajeEnvioCuentaEmisora_QNAME = new QName("", "cuentaEmisora");
    private final static QName _MensajeEnvioEmails_QNAME = new QName("", "emails");
    private final static QName _MensajeEnvioFechaCaducidad_QNAME = new QName("", "fechaCaducidad");
    private final static QName _MensajeEnvioIdProcedimiento_QNAME = new QName("", "idProcedimiento");
    private final static QName _MensajeEnvioSmss_QNAME = new QName("", "smss");
    private final static QName _MensajeEnvioFechaProgramacionEnvio_QNAME = new QName("", "fechaProgramacionEnvio");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.caib.mobtratel.ws.v2.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MensajeEnvioSMS }
     * 
     */
    public MensajeEnvioSMS createMensajeEnvioSMS() {
        return new MensajeEnvioSMS();
    }

    /**
     * Create an instance of {@link MensajesEnvioSMS }
     * 
     */
    public MensajesEnvioSMS createMensajesEnvioSMS() {
        return new MensajesEnvioSMS();
    }

    /**
     * Create an instance of {@link EnviarMensaje }
     * 
     */
    public EnviarMensaje createEnviarMensaje() {
        return new EnviarMensaje();
    }

    /**
     * Create an instance of {@link MensajesEnvioEmail }
     * 
     */
    public MensajesEnvioEmail createMensajesEnvioEmail() {
        return new MensajesEnvioEmail();
    }

    /**
     * Create an instance of {@link MensajeEnvio }
     * 
     */
    public MensajeEnvio createMensajeEnvio() {
        return new MensajeEnvio();
    }

    /**
     * Create an instance of {@link BackofficeFacadeException }
     * 
     */
    public BackofficeFacadeException createBackofficeFacadeException() {
        return new BackofficeFacadeException();
    }

    /**
     * Create an instance of {@link MensajeEnvioEmail }
     * 
     */
    public MensajeEnvioEmail createMensajeEnvioEmail() {
        return new MensajeEnvioEmail();
    }

    /**
     * Create an instance of {@link EnviarMensajeResponse }
     * 
     */
    public EnviarMensajeResponse createEnviarMensajeResponse() {
        return new EnviarMensajeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BackofficeFacadeException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:es:caib:mobtratel:ws:v2:model:BackofficeFacade", name = "fault")
    public JAXBElement<BackofficeFacadeException> createFault(BackofficeFacadeException value) {
        return new JAXBElement<BackofficeFacadeException>(_Fault_QNAME, BackofficeFacadeException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cuentaEmisora", scope = MensajeEnvio.class)
    public JAXBElement<String> createMensajeEnvioCuentaEmisora(String value) {
        return new JAXBElement<String>(_MensajeEnvioCuentaEmisora_QNAME, String.class, MensajeEnvio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MensajesEnvioEmail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "emails", scope = MensajeEnvio.class)
    public JAXBElement<MensajesEnvioEmail> createMensajeEnvioEmails(MensajesEnvioEmail value) {
        return new JAXBElement<MensajesEnvioEmail>(_MensajeEnvioEmails_QNAME, MensajesEnvioEmail.class, MensajeEnvio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fechaCaducidad", scope = MensajeEnvio.class)
    public JAXBElement<XMLGregorianCalendar> createMensajeEnvioFechaCaducidad(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_MensajeEnvioFechaCaducidad_QNAME, XMLGregorianCalendar.class, MensajeEnvio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "idProcedimiento", scope = MensajeEnvio.class)
    public JAXBElement<String> createMensajeEnvioIdProcedimiento(String value) {
        return new JAXBElement<String>(_MensajeEnvioIdProcedimiento_QNAME, String.class, MensajeEnvio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MensajesEnvioSMS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "smss", scope = MensajeEnvio.class)
    public JAXBElement<MensajesEnvioSMS> createMensajeEnvioSmss(MensajesEnvioSMS value) {
        return new JAXBElement<MensajesEnvioSMS>(_MensajeEnvioSmss_QNAME, MensajesEnvioSMS.class, MensajeEnvio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fechaProgramacionEnvio", scope = MensajeEnvio.class)
    public JAXBElement<XMLGregorianCalendar> createMensajeEnvioFechaProgramacionEnvio(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_MensajeEnvioFechaProgramacionEnvio_QNAME, XMLGregorianCalendar.class, MensajeEnvio.class, value);
    }

}

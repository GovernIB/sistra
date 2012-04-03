
package es.caib.regtel.ws.v2.model.detalleacuserecibo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import es.caib.regtel.ws.v2.model.ReferenciaRDS;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.caib.regtel.ws.v2.model.detalleacuserecibo package. 
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

    private final static QName _AvisoConfirmadoEnvio_QNAME = new QName("", "confirmadoEnvio");
    private final static QName _AvisoFechaEnvio_QNAME = new QName("", "fechaEnvio");
    private final static QName _DetalleAcuseReciboAvisos_QNAME = new QName("", "avisos");
    private final static QName _DetalleAcuseReciboFicheroAcuseRecibo_QNAME = new QName("", "ficheroAcuseRecibo");
    private final static QName _DetalleAcuseReciboFechaAcuseRecibo_QNAME = new QName("", "fechaAcuseRecibo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.caib.regtel.ws.v2.model.detalleacuserecibo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Avisos }
     * 
     */
    public Avisos createAvisos() {
        return new Avisos();
    }

    /**
     * Create an instance of {@link Aviso }
     * 
     */
    public Aviso createAviso() {
        return new Aviso();
    }

    /**
     * Create an instance of {@link DetalleAcuseRecibo }
     * 
     */
    public DetalleAcuseRecibo createDetalleAcuseRecibo() {
        return new DetalleAcuseRecibo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoConfirmacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "confirmadoEnvio", scope = Aviso.class)
    public JAXBElement<TipoConfirmacion> createAvisoConfirmadoEnvio(TipoConfirmacion value) {
        return new JAXBElement<TipoConfirmacion>(_AvisoConfirmadoEnvio_QNAME, TipoConfirmacion.class, Aviso.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fechaEnvio", scope = Aviso.class)
    public JAXBElement<XMLGregorianCalendar> createAvisoFechaEnvio(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_AvisoFechaEnvio_QNAME, XMLGregorianCalendar.class, Aviso.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Avisos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "avisos", scope = DetalleAcuseRecibo.class)
    public JAXBElement<Avisos> createDetalleAcuseReciboAvisos(Avisos value) {
        return new JAXBElement<Avisos>(_DetalleAcuseReciboAvisos_QNAME, Avisos.class, DetalleAcuseRecibo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenciaRDS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ficheroAcuseRecibo", scope = DetalleAcuseRecibo.class)
    public JAXBElement<ReferenciaRDS> createDetalleAcuseReciboFicheroAcuseRecibo(ReferenciaRDS value) {
        return new JAXBElement<ReferenciaRDS>(_DetalleAcuseReciboFicheroAcuseRecibo_QNAME, ReferenciaRDS.class, DetalleAcuseRecibo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fechaAcuseRecibo", scope = DetalleAcuseRecibo.class)
    public JAXBElement<XMLGregorianCalendar> createDetalleAcuseReciboFechaAcuseRecibo(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DetalleAcuseReciboFechaAcuseRecibo_QNAME, XMLGregorianCalendar.class, DetalleAcuseRecibo.class, value);
    }

}

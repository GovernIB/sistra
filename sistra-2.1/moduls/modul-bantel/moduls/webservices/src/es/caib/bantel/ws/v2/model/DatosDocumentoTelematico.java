
package es.caib.bantel.ws.v2.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatosDocumentoTelematico complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatosDocumentoTelematico">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoReferenciaRds" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="claveReferenciaRds" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="referenciaGestorDocumental" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="extension" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="firmas" type="{urn:es:caib:bantel:ws:v2:model:FirmaWS}FirmasWS"/>
 *         &lt;element name="estructurado" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="codigoDocumentoCustodia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatosDocumentoTelematico", namespace = "urn:es:caib:bantel:ws:v2:model:DatosDocumentoTelematico", propOrder = {
    "codigoReferenciaRds",
    "claveReferenciaRds",
    "referenciaGestorDocumental",
    "nombre",
    "extension",
    "content",
    "firmas",
    "estructurado",
    "codigoDocumentoCustodia"
})
public class DatosDocumentoTelematico {

    protected long codigoReferenciaRds;
    @XmlElement(required = true)
    protected String claveReferenciaRds;
    @XmlElementRef(name = "referenciaGestorDocumental", type = JAXBElement.class)
    protected JAXBElement<String> referenciaGestorDocumental;
    @XmlElement(required = true)
    protected String nombre;
    @XmlElement(required = true)
    protected String extension;
    @XmlElement(required = true)
    protected byte[] content;
    @XmlElement(required = true)
    protected FirmasWS firmas;
    protected boolean estructurado;
    @XmlElementRef(name = "codigoDocumentoCustodia", type = JAXBElement.class)
    protected JAXBElement<String> codigoDocumentoCustodia;

    /**
     * Gets the value of the codigoReferenciaRds property.
     * 
     */
    public long getCodigoReferenciaRds() {
        return codigoReferenciaRds;
    }

    /**
     * Sets the value of the codigoReferenciaRds property.
     * 
     */
    public void setCodigoReferenciaRds(long value) {
        this.codigoReferenciaRds = value;
    }

    /**
     * Gets the value of the claveReferenciaRds property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveReferenciaRds() {
        return claveReferenciaRds;
    }

    /**
     * Sets the value of the claveReferenciaRds property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveReferenciaRds(String value) {
        this.claveReferenciaRds = value;
    }

    /**
     * Gets the value of the referenciaGestorDocumental property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getReferenciaGestorDocumental() {
        return referenciaGestorDocumental;
    }

    /**
     * Sets the value of the referenciaGestorDocumental property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setReferenciaGestorDocumental(JAXBElement<String> value) {
        this.referenciaGestorDocumental = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtension(String value) {
        this.extension = value;
    }

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setContent(byte[] value) {
        this.content = ((byte[]) value);
    }

    /**
     * Gets the value of the firmas property.
     * 
     * @return
     *     possible object is
     *     {@link FirmasWS }
     *     
     */
    public FirmasWS getFirmas() {
        return firmas;
    }

    /**
     * Sets the value of the firmas property.
     * 
     * @param value
     *     allowed object is
     *     {@link FirmasWS }
     *     
     */
    public void setFirmas(FirmasWS value) {
        this.firmas = value;
    }

    /**
     * Gets the value of the estructurado property.
     * 
     */
    public boolean isEstructurado() {
        return estructurado;
    }

    /**
     * Sets the value of the estructurado property.
     * 
     */
    public void setEstructurado(boolean value) {
        this.estructurado = value;
    }

    /**
     * Gets the value of the codigoDocumentoCustodia property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCodigoDocumentoCustodia() {
        return codigoDocumentoCustodia;
    }

    /**
     * Sets the value of the codigoDocumentoCustodia property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodigoDocumentoCustodia(JAXBElement<String> value) {
        this.codigoDocumentoCustodia = ((JAXBElement<String> ) value);
    }

}

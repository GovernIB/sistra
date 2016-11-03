
package es.caib.mobtratel.ws.v1.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for MensajeEnvio complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MensajeEnvio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cuentaEmisora" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaProgramacionEnvio" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="fechaCaducidad" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="inmediato" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="emails" type="{urn:es:caib:mobtratel:ws:v1:model:MensajeEnvioEmail}MensajesEnvioEmail" minOccurs="0"/>
 *         &lt;element name="smss" type="{urn:es:caib:mobtratel:ws:v1:model:MensajeEnvioSMS}MensajesEnvioSMS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MensajeEnvio", namespace = "urn:es:caib:mobtratel:ws:v1:model:MensajeEnvio", propOrder = {
    "nombre",
    "cuentaEmisora",
    "fechaProgramacionEnvio",
    "fechaCaducidad",
    "inmediato",
    "emails",
    "smss"
})
public class MensajeEnvio {

    @XmlElement(required = true)
    protected String nombre;
    @XmlElementRef(name = "cuentaEmisora", type = JAXBElement.class)
    protected JAXBElement<String> cuentaEmisora;
    @XmlElementRef(name = "fechaProgramacionEnvio", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> fechaProgramacionEnvio;
    @XmlElementRef(name = "fechaCaducidad", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> fechaCaducidad;
    protected boolean inmediato;
    @XmlElementRef(name = "emails", type = JAXBElement.class)
    protected JAXBElement<MensajesEnvioEmail> emails;
    @XmlElementRef(name = "smss", type = JAXBElement.class)
    protected JAXBElement<MensajesEnvioSMS> smss;

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
     * Gets the value of the cuentaEmisora property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCuentaEmisora() {
        return cuentaEmisora;
    }

    /**
     * Sets the value of the cuentaEmisora property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCuentaEmisora(JAXBElement<String> value) {
        this.cuentaEmisora = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the fechaProgramacionEnvio property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getFechaProgramacionEnvio() {
        return fechaProgramacionEnvio;
    }

    /**
     * Sets the value of the fechaProgramacionEnvio property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setFechaProgramacionEnvio(JAXBElement<XMLGregorianCalendar> value) {
        this.fechaProgramacionEnvio = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the fechaCaducidad property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getFechaCaducidad() {
        return fechaCaducidad;
    }

    /**
     * Sets the value of the fechaCaducidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setFechaCaducidad(JAXBElement<XMLGregorianCalendar> value) {
        this.fechaCaducidad = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the inmediato property.
     * 
     */
    public boolean isInmediato() {
        return inmediato;
    }

    /**
     * Sets the value of the inmediato property.
     * 
     */
    public void setInmediato(boolean value) {
        this.inmediato = value;
    }

    /**
     * Gets the value of the emails property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link MensajesEnvioEmail }{@code >}
     *     
     */
    public JAXBElement<MensajesEnvioEmail> getEmails() {
        return emails;
    }

    /**
     * Sets the value of the emails property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link MensajesEnvioEmail }{@code >}
     *     
     */
    public void setEmails(JAXBElement<MensajesEnvioEmail> value) {
        this.emails = ((JAXBElement<MensajesEnvioEmail> ) value);
    }

    /**
     * Gets the value of the smss property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link MensajesEnvioSMS }{@code >}
     *     
     */
    public JAXBElement<MensajesEnvioSMS> getSmss() {
        return smss;
    }

    /**
     * Sets the value of the smss property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link MensajesEnvioSMS }{@code >}
     *     
     */
    public void setSmss(JAXBElement<MensajesEnvioSMS> value) {
        this.smss = ((JAXBElement<MensajesEnvioSMS> ) value);
    }

}

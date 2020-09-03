
package es.caib.zonaper.ws.v2.model.elementoexpediente;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for FiltroElementosExpediente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FiltroElementosExpediente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nif" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipos" type="{urn:es:caib:zonaper:ws:v2:model:ElementoExpediente}TiposElementoExpediente"/>
 *         &lt;element name="fechaInicio" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaFin" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="idioma" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FiltroElementosExpediente", propOrder = {
    "nif",
    "tipos",
    "fechaInicio",
    "fechaFin",
    "idioma"
})
public class FiltroElementosExpediente {

    @XmlElement(required = true)
    protected String nif;
    @XmlElement(required = true)
    protected TiposElementoExpediente tipos;
    @XmlElementRef(name = "fechaInicio", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> fechaInicio;
    @XmlElementRef(name = "fechaFin", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> fechaFin;
    @XmlElement(required = true)
    protected String idioma;

    /**
     * Gets the value of the nif property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNif() {
        return nif;
    }

    /**
     * Sets the value of the nif property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNif(String value) {
        this.nif = value;
    }

    /**
     * Gets the value of the tipos property.
     * 
     * @return
     *     possible object is
     *     {@link TiposElementoExpediente }
     *     
     */
    public TiposElementoExpediente getTipos() {
        return tipos;
    }

    /**
     * Sets the value of the tipos property.
     * 
     * @param value
     *     allowed object is
     *     {@link TiposElementoExpediente }
     *     
     */
    public void setTipos(TiposElementoExpediente value) {
        this.tipos = value;
    }

    /**
     * Gets the value of the fechaInicio property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Sets the value of the fechaInicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setFechaInicio(JAXBElement<XMLGregorianCalendar> value) {
        this.fechaInicio = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the fechaFin property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getFechaFin() {
        return fechaFin;
    }

    /**
     * Sets the value of the fechaFin property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setFechaFin(JAXBElement<XMLGregorianCalendar> value) {
        this.fechaFin = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the idioma property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Sets the value of the idioma property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdioma(String value) {
        this.idioma = value;
    }

}

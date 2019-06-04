
package es.caib.zonaper.ws.v2.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nif" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaDesde" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="fechaHasta" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "nif",
    "fechaDesde",
    "fechaHasta"
})
@XmlRootElement(name = "obtenerPersistentes")
public class ObtenerPersistentes {

    @XmlElement(namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", required = true)
    protected String nif;
    @XmlElement(namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaDesde;
    @XmlElement(namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaHasta;

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
     * Gets the value of the fechaDesde property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaDesde() {
        return fechaDesde;
    }

    /**
     * Sets the value of the fechaDesde property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaDesde(XMLGregorianCalendar value) {
        this.fechaDesde = value;
    }

    /**
     * Gets the value of the fechaHasta property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaHasta() {
        return fechaHasta;
    }

    /**
     * Sets the value of the fechaHasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaHasta(XMLGregorianCalendar value) {
        this.fechaHasta = value;
    }

}

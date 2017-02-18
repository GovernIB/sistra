
package es.caib.regtel.ws.v2.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OficinaRegistral complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OficinaRegistral">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoOficina" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codigoOrgano" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OficinaRegistral", namespace = "urn:es:caib:regtel:ws:v2:model:OficinaRegistral", propOrder = {
    "entidad",
    "codigoOficina",
    "codigoOrgano"
})
public class OficinaRegistral {

    @XmlElementRef(name = "entidad", type = JAXBElement.class)
    protected JAXBElement<String> entidad;
    @XmlElement(required = true)
    protected String codigoOficina;
    @XmlElement(required = true)
    protected String codigoOrgano;

    /**
     * Gets the value of the entidad property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEntidad() {
        return entidad;
    }

    /**
     * Sets the value of the entidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEntidad(JAXBElement<String> value) {
        this.entidad = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the codigoOficina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoOficina() {
        return codigoOficina;
    }

    /**
     * Sets the value of the codigoOficina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoOficina(String value) {
        this.codigoOficina = value;
    }

    /**
     * Gets the value of the codigoOrgano property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoOrgano() {
        return codigoOrgano;
    }

    /**
     * Sets the value of the codigoOrgano property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoOrgano(String value) {
        this.codigoOrgano = value;
    }

}


package es.caib.regtel.ws.v2.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatosNotificacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatosNotificacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idioma" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoAsunto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="acuseRecibo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="accesiblePorClave" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="aviso" type="{urn:es:caib:regtel:ws:v2:model:Aviso}Aviso"/>
 *         &lt;element name="oficioRemision" type="{urn:es:caib:regtel:ws:v2:model:OficioRemision}OficioRemision"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatosNotificacion", namespace = "urn:es:caib:regtel:ws:v2:model:DatosNotificacion", propOrder = {
    "idioma",
    "tipoAsunto",
    "acuseRecibo",
    "accesiblePorClave",
    "aviso",
    "oficioRemision"
})
public class DatosNotificacion {

    @XmlElement(required = true)
    protected String idioma;
    @XmlElement(required = true)
    protected String tipoAsunto;
    protected boolean acuseRecibo;
    @XmlElementRef(name = "accesiblePorClave", type = JAXBElement.class)
    protected JAXBElement<Boolean> accesiblePorClave;
    @XmlElement(required = true)
    protected Aviso aviso;
    @XmlElement(required = true)
    protected OficioRemision oficioRemision;

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

    /**
     * Gets the value of the tipoAsunto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoAsunto() {
        return tipoAsunto;
    }

    /**
     * Sets the value of the tipoAsunto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoAsunto(String value) {
        this.tipoAsunto = value;
    }

    /**
     * Gets the value of the acuseRecibo property.
     * 
     */
    public boolean isAcuseRecibo() {
        return acuseRecibo;
    }

    /**
     * Sets the value of the acuseRecibo property.
     * 
     */
    public void setAcuseRecibo(boolean value) {
        this.acuseRecibo = value;
    }

    /**
     * Gets the value of the accesiblePorClave property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getAccesiblePorClave() {
        return accesiblePorClave;
    }

    /**
     * Sets the value of the accesiblePorClave property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setAccesiblePorClave(JAXBElement<Boolean> value) {
        this.accesiblePorClave = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the aviso property.
     * 
     * @return
     *     possible object is
     *     {@link Aviso }
     *     
     */
    public Aviso getAviso() {
        return aviso;
    }

    /**
     * Sets the value of the aviso property.
     * 
     * @param value
     *     allowed object is
     *     {@link Aviso }
     *     
     */
    public void setAviso(Aviso value) {
        this.aviso = value;
    }

    /**
     * Gets the value of the oficioRemision property.
     * 
     * @return
     *     possible object is
     *     {@link OficioRemision }
     *     
     */
    public OficioRemision getOficioRemision() {
        return oficioRemision;
    }

    /**
     * Sets the value of the oficioRemision property.
     * 
     * @param value
     *     allowed object is
     *     {@link OficioRemision }
     *     
     */
    public void setOficioRemision(OficioRemision value) {
        this.oficioRemision = value;
    }

}

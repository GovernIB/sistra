
package es.caib.zonaper.ws.v2.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TramitePersistente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TramitePersistente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descripcionTramite" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaInicio" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="fechaUltimoAcceso" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="idSesionTramitacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idTramite" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idioma" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="versionTramite" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TramitePersistente", namespace = "urn:es:caib:zonaper:ws:v2:model:TramitePersistente", propOrder = {
    "descripcionTramite",
    "fechaInicio",
    "fechaUltimoAcceso",
    "idSesionTramitacion",
    "idTramite",
    "idioma",
    "versionTramite"
})
public class TramitePersistente {

    @XmlElement(required = true)
    protected String descripcionTramite;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaInicio;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaUltimoAcceso;
    @XmlElement(required = true)
    protected String idSesionTramitacion;
    @XmlElement(required = true)
    protected String idTramite;
    @XmlElement(required = true)
    protected String idioma;
    protected int versionTramite;

    /**
     * Gets the value of the descripcionTramite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionTramite() {
        return descripcionTramite;
    }

    /**
     * Sets the value of the descripcionTramite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionTramite(String value) {
        this.descripcionTramite = value;
    }

    /**
     * Gets the value of the fechaInicio property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Sets the value of the fechaInicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaInicio(XMLGregorianCalendar value) {
        this.fechaInicio = value;
    }

    /**
     * Gets the value of the fechaUltimoAcceso property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }

    /**
     * Sets the value of the fechaUltimoAcceso property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaUltimoAcceso(XMLGregorianCalendar value) {
        this.fechaUltimoAcceso = value;
    }

    /**
     * Gets the value of the idSesionTramitacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSesionTramitacion() {
        return idSesionTramitacion;
    }

    /**
     * Sets the value of the idSesionTramitacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSesionTramitacion(String value) {
        this.idSesionTramitacion = value;
    }

    /**
     * Gets the value of the idTramite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTramite() {
        return idTramite;
    }

    /**
     * Sets the value of the idTramite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTramite(String value) {
        this.idTramite = value;
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

    /**
     * Gets the value of the versionTramite property.
     * 
     */
    public int getVersionTramite() {
        return versionTramite;
    }

    /**
     * Sets the value of the versionTramite property.
     * 
     */
    public void setVersionTramite(int value) {
        this.versionTramite = value;
    }

}

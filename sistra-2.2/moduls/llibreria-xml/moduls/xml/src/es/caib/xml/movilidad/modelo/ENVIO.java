//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.07.07 at 08:58:40 AM CEST 
//


package es.caib.xml.movilidad.modelo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{}NOMBRE"/>
 *         &lt;element ref="{}CUENTA"/>
 *         &lt;element ref="{}FECHA_PROGRAMACION" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD" minOccurs="0"/>
 *         &lt;element ref="{}INMEDIATO" minOccurs="0"/>
 *         &lt;element ref="{}MENSAJES"/>
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
    "nombre",
    "cuenta",
    "fechaprogramacion",
    "fechacaducidad",
    "inmediato",
    "mensajes"
})
@XmlRootElement(name = "ENVIO")
public class ENVIO {

    @XmlElement(name = "NOMBRE", required = true)
    protected String nombre;
    @XmlElement(name = "CUENTA", required = true)
    protected String cuenta;
    @XmlElement(name = "FECHA_PROGRAMACION")
    protected String fechaprogramacion;
    @XmlElement(name = "FECHA_CADUCIDAD")
    protected String fechacaducidad;
    @XmlElement(name = "INMEDIATO")
    protected String inmediato;
    @XmlElement(name = "MENSAJES", required = true)
    protected MENSAJES mensajes;

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBRE() {
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
    public void setNOMBRE(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the cuenta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUENTA() {
        return cuenta;
    }

    /**
     * Sets the value of the cuenta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCUENTA(String value) {
        this.cuenta = value;
    }

    /**
     * Gets the value of the fechaprogramacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAPROGRAMACION() {
        return fechaprogramacion;
    }

    /**
     * Sets the value of the fechaprogramacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAPROGRAMACION(String value) {
        this.fechaprogramacion = value;
    }

    /**
     * Gets the value of the fechacaducidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDAD() {
        return fechacaducidad;
    }

    /**
     * Sets the value of the fechacaducidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDAD(String value) {
        this.fechacaducidad = value;
    }

    /**
     * Gets the value of the inmediato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINMEDIATO() {
        return inmediato;
    }

    /**
     * Sets the value of the inmediato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINMEDIATO(String value) {
        this.inmediato = value;
    }

    /**
     * Gets the value of the mensajes property.
     * 
     * @return
     *     possible object is
     *     {@link MENSAJES }
     *     
     */
    public MENSAJES getMENSAJES() {
        return mensajes;
    }

    /**
     * Sets the value of the mensajes property.
     * 
     * @param value
     *     allowed object is
     *     {@link MENSAJES }
     *     
     */
    public void setMENSAJES(MENSAJES value) {
        this.mensajes = value;
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.07.19 at 01:07:26 PM CEST 
//


package es.caib.xml.datospropios.modelo;

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
 *         &lt;element name="OCULTAR_CLAVE_TRAMITACION" type="{}string_si_no" minOccurs="0"/>
 *         &lt;element name="OCULTAR_NIF_NOMBRE" type="{}string_si_no" minOccurs="0"/>
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
    "ocultarclavetramitacion",
    "ocultarnifnombre"
})
@XmlRootElement(name = "PERSONALIZACION_JUSTIFICANTE")
public class PERSONALIZACIONJUSTIFICANTE {

    @XmlElement(name = "OCULTAR_CLAVE_TRAMITACION")
    protected String ocultarclavetramitacion;
    @XmlElement(name = "OCULTAR_NIF_NOMBRE")
    protected String ocultarnifnombre;

    /**
     * Gets the value of the ocultarclavetramitacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOCULTARCLAVETRAMITACION() {
        return ocultarclavetramitacion;
    }

    /**
     * Sets the value of the ocultarclavetramitacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOCULTARCLAVETRAMITACION(String value) {
        this.ocultarclavetramitacion = value;
    }

    /**
     * Gets the value of the ocultarnifnombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOCULTARNIFNOMBRE() {
        return ocultarnifnombre;
    }

    /**
     * Sets the value of the ocultarnifnombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOCULTARNIFNOMBRE(String value) {
        this.ocultarnifnombre = value;
    }

}

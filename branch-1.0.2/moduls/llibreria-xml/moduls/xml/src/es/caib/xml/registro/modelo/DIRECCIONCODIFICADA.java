//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.07.07 at 08:58:49 AM CEST 
//


package es.caib.xml.registro.modelo;

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
 *         &lt;element ref="{}CODIGO_PROVINCIA" minOccurs="0"/>
 *         &lt;element ref="{}NOMBRE_PROVINCIA" minOccurs="0"/>
 *         &lt;element ref="{}CODIGO_MUNICIPIO" minOccurs="0"/>
 *         &lt;element ref="{}NOMBRE_MUNICIPIO" minOccurs="0"/>
 *         &lt;element ref="{}CODIGO_POBLACION" minOccurs="0"/>
 *         &lt;element ref="{}NOMBRE_POBLACION" minOccurs="0"/>
 *         &lt;element ref="{}DOMICILIO" minOccurs="0"/>
 *         &lt;element ref="{}CODIGO_POSTAL" minOccurs="0"/>
 *         &lt;element ref="{}TELEFONO" minOccurs="0"/>
 *         &lt;element ref="{}FAX" minOccurs="0"/>
 *         &lt;element ref="{}PAIS_ORIGEN" minOccurs="0"/>
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
    "codigoprovincia",
    "nombreprovincia",
    "codigomunicipio",
    "nombremunicipio",
    "codigopoblacion",
    "nombrepoblacion",
    "domicilio",
    "codigopostal",
    "telefono",
    "fax",
    "paisorigen"
})
@XmlRootElement(name = "DIRECCION_CODIFICADA")
public class DIRECCIONCODIFICADA {

    @XmlElement(name = "CODIGO_PROVINCIA")
    protected String codigoprovincia;
    @XmlElement(name = "NOMBRE_PROVINCIA")
    protected String nombreprovincia;
    @XmlElement(name = "CODIGO_MUNICIPIO")
    protected String codigomunicipio;
    @XmlElement(name = "NOMBRE_MUNICIPIO")
    protected String nombremunicipio;
    @XmlElement(name = "CODIGO_POBLACION")
    protected String codigopoblacion;
    @XmlElement(name = "NOMBRE_POBLACION")
    protected String nombrepoblacion;
    @XmlElement(name = "DOMICILIO")
    protected String domicilio;
    @XmlElement(name = "CODIGO_POSTAL")
    protected String codigopostal;
    @XmlElement(name = "TELEFONO")
    protected String telefono;
    @XmlElement(name = "FAX")
    protected String fax;
    @XmlElement(name = "PAIS_ORIGEN")
    protected String paisorigen;

    /**
     * Gets the value of the codigoprovincia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIGOPROVINCIA() {
        return codigoprovincia;
    }

    /**
     * Sets the value of the codigoprovincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIGOPROVINCIA(String value) {
        this.codigoprovincia = value;
    }

    /**
     * Gets the value of the nombreprovincia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREPROVINCIA() {
        return nombreprovincia;
    }

    /**
     * Sets the value of the nombreprovincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREPROVINCIA(String value) {
        this.nombreprovincia = value;
    }

    /**
     * Gets the value of the codigomunicipio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIGOMUNICIPIO() {
        return codigomunicipio;
    }

    /**
     * Sets the value of the codigomunicipio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIGOMUNICIPIO(String value) {
        this.codigomunicipio = value;
    }

    /**
     * Gets the value of the nombremunicipio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREMUNICIPIO() {
        return nombremunicipio;
    }

    /**
     * Sets the value of the nombremunicipio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREMUNICIPIO(String value) {
        this.nombremunicipio = value;
    }

    /**
     * Gets the value of the codigopoblacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIGOPOBLACION() {
        return codigopoblacion;
    }

    /**
     * Sets the value of the codigopoblacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIGOPOBLACION(String value) {
        this.codigopoblacion = value;
    }

    /**
     * Gets the value of the nombrepoblacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREPOBLACION() {
        return nombrepoblacion;
    }

    /**
     * Sets the value of the nombrepoblacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREPOBLACION(String value) {
        this.nombrepoblacion = value;
    }

    /**
     * Gets the value of the domicilio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDOMICILIO() {
        return domicilio;
    }

    /**
     * Sets the value of the domicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOMICILIO(String value) {
        this.domicilio = value;
    }

    /**
     * Gets the value of the codigopostal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIGOPOSTAL() {
        return codigopostal;
    }

    /**
     * Sets the value of the codigopostal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIGOPOSTAL(String value) {
        this.codigopostal = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTELEFONO() {
        return telefono;
    }

    /**
     * Sets the value of the telefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTELEFONO(String value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAX() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFAX(String value) {
        this.fax = value;
    }

    /**
     * Gets the value of the paisorigen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAISORIGEN() {
        return paisorigen;
    }

    /**
     * Sets the value of the paisorigen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAISORIGEN(String value) {
        this.paisorigen = value;
    }

}


package es.caib.zonaper.ws.v2.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UsuarioAutenticadoInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UsuarioAutenticadoInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="apellido1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="apellido2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="autenticacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="metodoAutenticacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nif" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UsuarioAutenticadoInfo", namespace = "urn:es:caib:zonaper:ws:v2:model:UsuarioAutenticadoInfo", propOrder = {
    "apellido1",
    "apellido2",
    "autenticacion",
    "email",
    "metodoAutenticacion",
    "nif",
    "nombre",
    "username"
})
public class UsuarioAutenticadoInfo {

    @XmlElement(required = true)
    protected String apellido1;
    @XmlElement(required = true)
    protected String apellido2;
    @XmlElement(required = true)
    protected String autenticacion;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(required = true)
    protected String metodoAutenticacion;
    @XmlElement(required = true)
    protected String nif;
    @XmlElement(required = true)
    protected String nombre;
    @XmlElement(required = true)
    protected String username;

    /**
     * Gets the value of the apellido1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Sets the value of the apellido1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellido1(String value) {
        this.apellido1 = value;
    }

    /**
     * Gets the value of the apellido2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Sets the value of the apellido2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellido2(String value) {
        this.apellido2 = value;
    }

    /**
     * Gets the value of the autenticacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutenticacion() {
        return autenticacion;
    }

    /**
     * Sets the value of the autenticacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutenticacion(String value) {
        this.autenticacion = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the metodoAutenticacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetodoAutenticacion() {
        return metodoAutenticacion;
    }

    /**
     * Sets the value of the metodoAutenticacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetodoAutenticacion(String value) {
        this.metodoAutenticacion = value;
    }

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
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

}

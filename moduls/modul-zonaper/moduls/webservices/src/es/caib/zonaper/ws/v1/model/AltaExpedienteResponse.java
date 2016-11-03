
package es.caib.zonaper.ws.v1.model;

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
 *         &lt;element name="altaExpedienteReturn" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "altaExpedienteReturn"
})
@XmlRootElement(name = "altaExpedienteResponse")
public class AltaExpedienteResponse {

    @XmlElement(namespace = "urn:es:caib:zonaper:ws:v1:model:BackofficeFacade", required = true)
    protected String altaExpedienteReturn;

    /**
     * Gets the value of the altaExpedienteReturn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltaExpedienteReturn() {
        return altaExpedienteReturn;
    }

    /**
     * Sets the value of the altaExpedienteReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltaExpedienteReturn(String value) {
        this.altaExpedienteReturn = value;
    }

}


package es.caib.mobtratel.ws.v1.model;

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
 *         &lt;element name="enviarMensajeReturn" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "enviarMensajeReturn"
})
@XmlRootElement(name = "enviarMensajeResponse")
public class EnviarMensajeResponse {

    @XmlElement(namespace = "urn:es:caib:mobtratel:ws:v1:model:BackofficeFacade", required = true)
    protected String enviarMensajeReturn;

    /**
     * Gets the value of the enviarMensajeReturn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnviarMensajeReturn() {
        return enviarMensajeReturn;
    }

    /**
     * Sets the value of the enviarMensajeReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnviarMensajeReturn(String value) {
        this.enviarMensajeReturn = value;
    }

}

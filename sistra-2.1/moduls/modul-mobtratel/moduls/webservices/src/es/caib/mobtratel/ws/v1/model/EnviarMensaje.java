
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
 *         &lt;element name="mensaje" type="{urn:es:caib:mobtratel:ws:v1:model:MensajeEnvio}MensajeEnvio"/>
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
    "mensaje"
})
@XmlRootElement(name = "enviarMensaje")
public class EnviarMensaje {

    @XmlElement(namespace = "urn:es:caib:mobtratel:ws:v1:model:BackofficeFacade", required = true)
    protected MensajeEnvio mensaje;

    /**
     * Gets the value of the mensaje property.
     * 
     * @return
     *     possible object is
     *     {@link MensajeEnvio }
     *     
     */
    public MensajeEnvio getMensaje() {
        return mensaje;
    }

    /**
     * Sets the value of the mensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link MensajeEnvio }
     *     
     */
    public void setMensaje(MensajeEnvio value) {
        this.mensaje = value;
    }

}

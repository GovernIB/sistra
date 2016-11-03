
package es.caib.mobtratel.ws.v1.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MensajesEnvioEmail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MensajesEnvioEmail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mensajes" type="{urn:es:caib:mobtratel:ws:v1:model:MensajeEnvioEmail}MensajeEnvioEmail" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MensajesEnvioEmail", namespace = "urn:es:caib:mobtratel:ws:v1:model:MensajeEnvioEmail", propOrder = {
    "mensajes"
})
public class MensajesEnvioEmail {

    protected List<MensajeEnvioEmail> mensajes;

    /**
     * Gets the value of the mensajes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mensajes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMensajes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MensajeEnvioEmail }
     * 
     * 
     */
    public List<MensajeEnvioEmail> getMensajes() {
        if (mensajes == null) {
            mensajes = new ArrayList<MensajeEnvioEmail>();
        }
        return this.mensajes;
    }

}

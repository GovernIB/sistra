
package es.caib.zonaper.ws.v2.model;

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
 *         &lt;element name="obtenerTotalElementosExpedienteReturn" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "obtenerTotalElementosExpedienteReturn"
})
@XmlRootElement(name = "obtenerTotalElementosExpedienteResponse")
public class ObtenerTotalElementosExpedienteResponse {

    @XmlElement(namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
    protected long obtenerTotalElementosExpedienteReturn;

    /**
     * Gets the value of the obtenerTotalElementosExpedienteReturn property.
     * 
     */
    public long getObtenerTotalElementosExpedienteReturn() {
        return obtenerTotalElementosExpedienteReturn;
    }

    /**
     * Sets the value of the obtenerTotalElementosExpedienteReturn property.
     * 
     */
    public void setObtenerTotalElementosExpedienteReturn(long value) {
        this.obtenerTotalElementosExpedienteReturn = value;
    }

}

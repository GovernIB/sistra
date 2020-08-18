
package es.caib.zonaper.ws.v2.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import es.caib.zonaper.ws.v2.model.elementoexpediente.ElementosExpediente;


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
 *         &lt;element name="obtenerElementosExpedienteReturn" type="{urn:es:caib:zonaper:ws:v2:model:ElementoExpediente}ElementosExpediente"/>
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
    "obtenerElementosExpedienteReturn"
})
@XmlRootElement(name = "obtenerElementosExpedienteResponse")
public class ObtenerElementosExpedienteResponse {

    @XmlElement(namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", required = true)
    protected ElementosExpediente obtenerElementosExpedienteReturn;

    /**
     * Gets the value of the obtenerElementosExpedienteReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ElementosExpediente }
     *     
     */
    public ElementosExpediente getObtenerElementosExpedienteReturn() {
        return obtenerElementosExpedienteReturn;
    }

    /**
     * Sets the value of the obtenerElementosExpedienteReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ElementosExpediente }
     *     
     */
    public void setObtenerElementosExpedienteReturn(ElementosExpediente value) {
        this.obtenerElementosExpedienteReturn = value;
    }

}


package es.caib.zonaper.ws.v2.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import es.caib.zonaper.ws.v2.model.elementoexpediente.FiltroElementosExpediente;


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
 *         &lt;element name="filtroElementosExpediente" type="{urn:es:caib:zonaper:ws:v2:model:ElementoExpediente}FiltroElementosExpediente"/>
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
    "filtroElementosExpediente"
})
@XmlRootElement(name = "obtenerElementosExpediente")
public class ObtenerElementosExpediente {

    @XmlElement(namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", required = true)
    protected FiltroElementosExpediente filtroElementosExpediente;

    /**
     * Gets the value of the filtroElementosExpediente property.
     * 
     * @return
     *     possible object is
     *     {@link FiltroElementosExpediente }
     *     
     */
    public FiltroElementosExpediente getFiltroElementosExpediente() {
        return filtroElementosExpediente;
    }

    /**
     * Sets the value of the filtroElementosExpediente property.
     * 
     * @param value
     *     allowed object is
     *     {@link FiltroElementosExpediente }
     *     
     */
    public void setFiltroElementosExpediente(FiltroElementosExpediente value) {
        this.filtroElementosExpediente = value;
    }

}

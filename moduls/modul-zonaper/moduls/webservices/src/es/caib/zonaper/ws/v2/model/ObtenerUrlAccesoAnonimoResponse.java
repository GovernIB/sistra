
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
 *         &lt;element name="obtenerUrlAccesoAnonimoReturn" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "obtenerUrlAccesoAnonimoReturn"
})
@XmlRootElement(name = "obtenerUrlAccesoAnonimoResponse")
public class ObtenerUrlAccesoAnonimoResponse {

    @XmlElement(namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", required = true)
    protected String obtenerUrlAccesoAnonimoReturn;

    /**
     * Gets the value of the obtenerUrlAccesoAnonimoReturn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObtenerUrlAccesoAnonimoReturn() {
        return obtenerUrlAccesoAnonimoReturn;
    }

    /**
     * Sets the value of the obtenerUrlAccesoAnonimoReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObtenerUrlAccesoAnonimoReturn(String value) {
        this.obtenerUrlAccesoAnonimoReturn = value;
    }

}

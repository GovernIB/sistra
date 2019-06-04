
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
 *         &lt;element name="obtenerPersistentesReturn" type="{urn:es:caib:zonaper:ws:v2:model:TramitePersistente}TramitesPersistentes"/>
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
    "obtenerPersistentesReturn"
})
@XmlRootElement(name = "obtenerPersistentesResponse")
public class ObtenerPersistentesResponse {

    @XmlElement(namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", required = true)
    protected TramitesPersistentes obtenerPersistentesReturn;

    /**
     * Gets the value of the obtenerPersistentesReturn property.
     * 
     * @return
     *     possible object is
     *     {@link TramitesPersistentes }
     *     
     */
    public TramitesPersistentes getObtenerPersistentesReturn() {
        return obtenerPersistentesReturn;
    }

    /**
     * Sets the value of the obtenerPersistentesReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link TramitesPersistentes }
     *     
     */
    public void setObtenerPersistentesReturn(TramitesPersistentes value) {
        this.obtenerPersistentesReturn = value;
    }

}

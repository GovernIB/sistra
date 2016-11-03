
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
 *         &lt;element name="existeZonaPersonalUsuarioReturn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "existeZonaPersonalUsuarioReturn"
})
@XmlRootElement(name = "existeZonaPersonalUsuarioResponse")
public class ExisteZonaPersonalUsuarioResponse {

    @XmlElement(namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade")
    protected boolean existeZonaPersonalUsuarioReturn;

    /**
     * Gets the value of the existeZonaPersonalUsuarioReturn property.
     * 
     */
    public boolean isExisteZonaPersonalUsuarioReturn() {
        return existeZonaPersonalUsuarioReturn;
    }

    /**
     * Sets the value of the existeZonaPersonalUsuarioReturn property.
     * 
     */
    public void setExisteZonaPersonalUsuarioReturn(boolean value) {
        this.existeZonaPersonalUsuarioReturn = value;
    }

}

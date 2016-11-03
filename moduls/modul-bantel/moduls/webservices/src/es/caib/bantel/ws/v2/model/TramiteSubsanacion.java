
package es.caib.bantel.ws.v2.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TramiteSubsanacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TramiteSubsanacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="expedienteCodigo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="expedienteUnidadAdministrativa" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TramiteSubsanacion", namespace = "urn:es:caib:bantel:ws:v2:model:TramiteBTE", propOrder = {
    "expedienteCodigo",
    "expedienteUnidadAdministrativa"
})
public class TramiteSubsanacion {

    @XmlElement(required = true)
    protected String expedienteCodigo;
    protected long expedienteUnidadAdministrativa;

    /**
     * Gets the value of the expedienteCodigo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpedienteCodigo() {
        return expedienteCodigo;
    }

    /**
     * Sets the value of the expedienteCodigo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpedienteCodigo(String value) {
        this.expedienteCodigo = value;
    }

    /**
     * Gets the value of the expedienteUnidadAdministrativa property.
     * 
     */
    public long getExpedienteUnidadAdministrativa() {
        return expedienteUnidadAdministrativa;
    }

    /**
     * Sets the value of the expedienteUnidadAdministrativa property.
     * 
     */
    public void setExpedienteUnidadAdministrativa(long value) {
        this.expedienteUnidadAdministrativa = value;
    }

}

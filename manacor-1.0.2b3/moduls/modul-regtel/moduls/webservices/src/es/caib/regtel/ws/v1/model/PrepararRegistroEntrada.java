
package es.caib.regtel.ws.v1.model;

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
 *         &lt;element name="entrada" type="{urn:es:caib:regtel:ws:v1:model:DatosRegistroEntrada}DatosRegistroEntrada"/>
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
    "entrada"
})
@XmlRootElement(name = "prepararRegistroEntrada")
public class PrepararRegistroEntrada {

    @XmlElement(namespace = "urn:es:caib:regtel:ws:v1:model:BackofficeFacade", required = true)
    protected DatosRegistroEntrada entrada;

    /**
     * Gets the value of the entrada property.
     * 
     * @return
     *     possible object is
     *     {@link DatosRegistroEntrada }
     *     
     */
    public DatosRegistroEntrada getEntrada() {
        return entrada;
    }

    /**
     * Sets the value of the entrada property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosRegistroEntrada }
     *     
     */
    public void setEntrada(DatosRegistroEntrada value) {
        this.entrada = value;
    }

}

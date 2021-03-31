
package es.caib.zonaper.ws.v2.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ElementosExpediente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ElementosExpediente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="elemento" type="{urn:es:caib:zonaper:ws:v2:model:ElementoExpediente}ElementoExpediente" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ElementosExpediente", namespace = "urn:es:caib:zonaper:ws:v2:model:ElementoExpediente", propOrder = {
    "elemento"
})
public class ElementosExpediente {

    protected List<ElementoExpediente> elemento;

    /**
     * Gets the value of the elemento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the elemento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getElemento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ElementoExpediente }
     * 
     * 
     */
    public List<ElementoExpediente> getElemento() {
        if (elemento == null) {
            elemento = new ArrayList<ElementoExpediente>();
        }
        return this.elemento;
    }

}


package es.caib.zonaper.ws.v2.model.elementoexpediente;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TiposElementoExpediente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TiposElementoExpediente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipo" type="{urn:es:caib:zonaper:ws:v2:model:ElementoExpediente}TipoElementoExpediente" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TiposElementoExpediente", propOrder = {
    "tipo"
})
public class TiposElementoExpediente {

    protected List<TipoElementoExpediente> tipo;

    /**
     * Gets the value of the tipo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tipo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTipo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoElementoExpediente }
     * 
     * 
     */
    public List<TipoElementoExpediente> getTipo() {
        if (tipo == null) {
            tipo = new ArrayList<TipoElementoExpediente>();
        }
        return this.tipo;
    }

}

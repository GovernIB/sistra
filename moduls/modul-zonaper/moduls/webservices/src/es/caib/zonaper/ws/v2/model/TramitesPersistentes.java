
package es.caib.zonaper.ws.v2.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TramitesPersistentes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TramitesPersistentes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tramitePersistente" type="{urn:es:caib:zonaper:ws:v2:model:TramitePersistente}TramitePersistente" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TramitesPersistentes", namespace = "urn:es:caib:zonaper:ws:v2:model:TramitePersistente", propOrder = {
    "tramitePersistente"
})
public class TramitesPersistentes {

    protected List<TramitePersistente> tramitePersistente;

    /**
     * Gets the value of the tramitePersistente property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tramitePersistente property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTramitePersistente().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TramitePersistente }
     * 
     * 
     */
    public List<TramitePersistente> getTramitePersistente() {
        if (tramitePersistente == null) {
            tramitePersistente = new ArrayList<TramitePersistente>();
        }
        return this.tramitePersistente;
    }

}


package es.caib.sistra.wsClient.v2.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FormulariosConsulta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FormulariosConsulta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="formularios" type="{urn:es:caib:sistra:ws:v2:model:FormularioConsulta}FormularioConsulta" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormulariosConsulta", namespace = "urn:es:caib:sistra:ws:v2:model:FormularioConsulta", propOrder = {
    "formularios"
})
public class FormulariosConsulta {

    protected List<FormularioConsulta> formularios;

    /**
     * Gets the value of the formularios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formularios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormularios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FormularioConsulta }
     * 
     * 
     */
    public List<FormularioConsulta> getFormularios() {
        if (formularios == null) {
            formularios = new ArrayList<FormularioConsulta>();
        }
        return this.formularios;
    }

}

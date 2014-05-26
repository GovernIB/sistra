
package es.caib.redose.ws.v2.model;

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
 *         &lt;element name="insertarDocumentoConTransformacionReturn" type="{urn:es:caib:redose:ws:v2:model:ReferenciaRDS}ReferenciaRDS"/>
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
    "insertarDocumentoConTransformacionReturn"
})
@XmlRootElement(name = "insertarDocumentoConTransformacionResponse")
public class InsertarDocumentoConTransformacionResponse {

    @XmlElement(namespace = "urn:es:caib:redose:ws:v2:model:BackofficeFacade", required = true)
    protected ReferenciaRDS insertarDocumentoConTransformacionReturn;

    /**
     * Gets the value of the insertarDocumentoConTransformacionReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ReferenciaRDS }
     *     
     */
    public ReferenciaRDS getInsertarDocumentoConTransformacionReturn() {
        return insertarDocumentoConTransformacionReturn;
    }

    /**
     * Sets the value of the insertarDocumentoConTransformacionReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferenciaRDS }
     *     
     */
    public void setInsertarDocumentoConTransformacionReturn(ReferenciaRDS value) {
        this.insertarDocumentoConTransformacionReturn = value;
    }

}

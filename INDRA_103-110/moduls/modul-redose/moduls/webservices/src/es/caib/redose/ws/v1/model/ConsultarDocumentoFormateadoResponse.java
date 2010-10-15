
package es.caib.redose.ws.v1.model;

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
 *         &lt;element name="consultarDocumentoFormateadoReturn" type="{urn:es:caib:redose:ws:v1:model:DocumentoRDS}DocumentoRDS"/>
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
    "consultarDocumentoFormateadoReturn"
})
@XmlRootElement(name = "consultarDocumentoFormateadoResponse")
public class ConsultarDocumentoFormateadoResponse {

    @XmlElement(namespace = "urn:es:caib:redose:ws:v1:model:BackofficeFacade", required = true)
    protected DocumentoRDS consultarDocumentoFormateadoReturn;

    /**
     * Gets the value of the consultarDocumentoFormateadoReturn property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoRDS }
     *     
     */
    public DocumentoRDS getConsultarDocumentoFormateadoReturn() {
        return consultarDocumentoFormateadoReturn;
    }

    /**
     * Sets the value of the consultarDocumentoFormateadoReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoRDS }
     *     
     */
    public void setConsultarDocumentoFormateadoReturn(DocumentoRDS value) {
        this.consultarDocumentoFormateadoReturn = value;
    }

}

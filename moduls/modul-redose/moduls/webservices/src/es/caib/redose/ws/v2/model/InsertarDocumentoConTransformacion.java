
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
 *         &lt;element name="documento" type="{urn:es:caib:redose:ws:v2:model:DocumentoRDS}DocumentoRDS"/>
 *         &lt;element name="transformacion" type="{urn:es:caib:redose:ws:v2:model:TransformacionRDS}TransformacionRDS"/>
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
    "documento",
    "transformacion"
})
@XmlRootElement(name = "insertarDocumentoConTransformacion")
public class InsertarDocumentoConTransformacion {

    @XmlElement(namespace = "urn:es:caib:redose:ws:v2:model:BackofficeFacade", required = true)
    protected DocumentoRDS documento;
    @XmlElement(namespace = "urn:es:caib:redose:ws:v2:model:BackofficeFacade", required = true)
    protected TransformacionRDS transformacion;

    /**
     * Gets the value of the documento property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoRDS }
     *     
     */
    public DocumentoRDS getDocumento() {
        return documento;
    }

    /**
     * Sets the value of the documento property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoRDS }
     *     
     */
    public void setDocumento(DocumentoRDS value) {
        this.documento = value;
    }

    /**
     * Gets the value of the transformacion property.
     * 
     * @return
     *     possible object is
     *     {@link TransformacionRDS }
     *     
     */
    public TransformacionRDS getTransformacion() {
        return transformacion;
    }

    /**
     * Sets the value of the transformacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransformacionRDS }
     *     
     */
    public void setTransformacion(TransformacionRDS value) {
        this.transformacion = value;
    }

}

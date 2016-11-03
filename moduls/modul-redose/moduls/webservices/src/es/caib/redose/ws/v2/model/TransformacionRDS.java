
package es.caib.redose.ws.v2.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransformacionRDS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransformacionRDS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="convertToPDF" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="barcodePDF" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransformacionRDS", namespace = "urn:es:caib:redose:ws:v2:model:TransformacionRDS", propOrder = {
    "convertToPDF",
    "barcodePDF"
})
public class TransformacionRDS {

    protected boolean convertToPDF;
    protected boolean barcodePDF;

    /**
     * Gets the value of the convertToPDF property.
     * 
     */
    public boolean isConvertToPDF() {
        return convertToPDF;
    }

    /**
     * Sets the value of the convertToPDF property.
     * 
     */
    public void setConvertToPDF(boolean value) {
        this.convertToPDF = value;
    }

    /**
     * Gets the value of the barcodePDF property.
     * 
     */
    public boolean isBarcodePDF() {
        return barcodePDF;
    }

    /**
     * Sets the value of the barcodePDF property.
     * 
     */
    public void setBarcodePDF(boolean value) {
        this.barcodePDF = value;
    }

}


package es.caib.regtel.ws.v1.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatosRegistroSalida complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatosRegistroSalida">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="datosExpediente" type="{urn:es:caib:regtel:ws:v1:model:DatosExpediente}DatosExpediente"/>
 *         &lt;element name="oficinaRegistral" type="{urn:es:caib:regtel:ws:v1:model:OficinaRegistral}OficinaRegistral"/>
 *         &lt;element name="datosInteresado" type="{urn:es:caib:regtel:ws:v1:model:DatosInteresado}DatosInteresado"/>
 *         &lt;element name="datosRepresentado" type="{urn:es:caib:regtel:ws:v1:model:DatosRepresentado}DatosRepresentado"/>
 *         &lt;element name="datosNotificacion" type="{urn:es:caib:regtel:ws:v1:model:DatosNotificacion}DatosNotificacion"/>
 *         &lt;element name="documentos" type="{urn:es:caib:regtel:ws:v1:model:Documento}Documentos" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatosRegistroSalida", namespace = "urn:es:caib:regtel:ws:v1:model:DatosRegistroSalida", propOrder = {
    "datosExpediente",
    "oficinaRegistral",
    "datosInteresado",
    "datosRepresentado",
    "datosNotificacion",
    "documentos"
})
public class DatosRegistroSalida {

    @XmlElement(required = true)
    protected DatosExpediente datosExpediente;
    @XmlElement(required = true)
    protected OficinaRegistral oficinaRegistral;
    @XmlElement(required = true)
    protected DatosInteresado datosInteresado;
    @XmlElement(required = true)
    protected DatosRepresentado datosRepresentado;
    @XmlElement(required = true)
    protected DatosNotificacion datosNotificacion;
    @XmlElementRef(name = "documentos", type = JAXBElement.class)
    protected JAXBElement<Documentos> documentos;

    /**
     * Gets the value of the datosExpediente property.
     * 
     * @return
     *     possible object is
     *     {@link DatosExpediente }
     *     
     */
    public DatosExpediente getDatosExpediente() {
        return datosExpediente;
    }

    /**
     * Sets the value of the datosExpediente property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosExpediente }
     *     
     */
    public void setDatosExpediente(DatosExpediente value) {
        this.datosExpediente = value;
    }

    /**
     * Gets the value of the oficinaRegistral property.
     * 
     * @return
     *     possible object is
     *     {@link OficinaRegistral }
     *     
     */
    public OficinaRegistral getOficinaRegistral() {
        return oficinaRegistral;
    }

    /**
     * Sets the value of the oficinaRegistral property.
     * 
     * @param value
     *     allowed object is
     *     {@link OficinaRegistral }
     *     
     */
    public void setOficinaRegistral(OficinaRegistral value) {
        this.oficinaRegistral = value;
    }

    /**
     * Gets the value of the datosInteresado property.
     * 
     * @return
     *     possible object is
     *     {@link DatosInteresado }
     *     
     */
    public DatosInteresado getDatosInteresado() {
        return datosInteresado;
    }

    /**
     * Sets the value of the datosInteresado property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosInteresado }
     *     
     */
    public void setDatosInteresado(DatosInteresado value) {
        this.datosInteresado = value;
    }

    /**
     * Gets the value of the datosRepresentado property.
     * 
     * @return
     *     possible object is
     *     {@link DatosRepresentado }
     *     
     */
    public DatosRepresentado getDatosRepresentado() {
        return datosRepresentado;
    }

    /**
     * Sets the value of the datosRepresentado property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosRepresentado }
     *     
     */
    public void setDatosRepresentado(DatosRepresentado value) {
        this.datosRepresentado = value;
    }

    /**
     * Gets the value of the datosNotificacion property.
     * 
     * @return
     *     possible object is
     *     {@link DatosNotificacion }
     *     
     */
    public DatosNotificacion getDatosNotificacion() {
        return datosNotificacion;
    }

    /**
     * Sets the value of the datosNotificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatosNotificacion }
     *     
     */
    public void setDatosNotificacion(DatosNotificacion value) {
        this.datosNotificacion = value;
    }

    /**
     * Gets the value of the documentos property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Documentos }{@code >}
     *     
     */
    public JAXBElement<Documentos> getDocumentos() {
        return documentos;
    }

    /**
     * Sets the value of the documentos property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Documentos }{@code >}
     *     
     */
    public void setDocumentos(JAXBElement<Documentos> value) {
        this.documentos = ((JAXBElement<Documentos> ) value);
    }

}

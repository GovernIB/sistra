
package es.caib.zonaper.ws.v2.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import es.caib.zonaper.ws.v2.model.elementoexpediente.FiltroElementosExpediente;


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
 *         &lt;element name="filtroElementosExpediente" type="{urn:es:caib:zonaper:ws:v2:model:ElementoExpediente}FiltroElementosExpediente"/>
 *         &lt;element name="pagina" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="tamPagina" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
    "filtroElementosExpediente",
    "pagina",
    "tamPagina"
})
@XmlRootElement(name = "obtenerElementosExpediente")
public class ObtenerElementosExpediente {

    @XmlElement(namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", required = true)
    protected FiltroElementosExpediente filtroElementosExpediente;
    @XmlElementRef(name = "pagina", namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", type = JAXBElement.class)
    protected JAXBElement<Integer> pagina;
    @XmlElementRef(name = "tamPagina", namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", type = JAXBElement.class)
    protected JAXBElement<Integer> tamPagina;

    /**
     * Gets the value of the filtroElementosExpediente property.
     * 
     * @return
     *     possible object is
     *     {@link FiltroElementosExpediente }
     *     
     */
    public FiltroElementosExpediente getFiltroElementosExpediente() {
        return filtroElementosExpediente;
    }

    /**
     * Sets the value of the filtroElementosExpediente property.
     * 
     * @param value
     *     allowed object is
     *     {@link FiltroElementosExpediente }
     *     
     */
    public void setFiltroElementosExpediente(FiltroElementosExpediente value) {
        this.filtroElementosExpediente = value;
    }

    /**
     * Gets the value of the pagina property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getPagina() {
        return pagina;
    }

    /**
     * Sets the value of the pagina property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setPagina(JAXBElement<Integer> value) {
        this.pagina = ((JAXBElement<Integer> ) value);
    }

    /**
     * Gets the value of the tamPagina property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getTamPagina() {
        return tamPagina;
    }

    /**
     * Sets the value of the tamPagina property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setTamPagina(JAXBElement<Integer> value) {
        this.tamPagina = ((JAXBElement<Integer> ) value);
    }

}

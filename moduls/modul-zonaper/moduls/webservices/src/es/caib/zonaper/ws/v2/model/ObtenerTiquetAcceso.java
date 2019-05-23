
package es.caib.zonaper.ws.v2.model;

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
 *         &lt;element name="idSesionTramitacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="usuarioAutenticadoInfo" type="{urn:es:caib:zonaper:ws:v2:model:UsuarioAutenticadoInfo}UsuarioAutenticadoInfo"/>
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
    "idSesionTramitacion",
    "usuarioAutenticadoInfo"
})
@XmlRootElement(name = "obtenerTiquetAcceso")
public class ObtenerTiquetAcceso {

    @XmlElement(namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", required = true)
    protected String idSesionTramitacion;
    @XmlElement(namespace = "urn:es:caib:zonaper:ws:v2:model:BackofficeFacade", required = true)
    protected UsuarioAutenticadoInfo usuarioAutenticadoInfo;

    /**
     * Gets the value of the idSesionTramitacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSesionTramitacion() {
        return idSesionTramitacion;
    }

    /**
     * Sets the value of the idSesionTramitacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSesionTramitacion(String value) {
        this.idSesionTramitacion = value;
    }

    /**
     * Gets the value of the usuarioAutenticadoInfo property.
     * 
     * @return
     *     possible object is
     *     {@link UsuarioAutenticadoInfo }
     *     
     */
    public UsuarioAutenticadoInfo getUsuarioAutenticadoInfo() {
        return usuarioAutenticadoInfo;
    }

    /**
     * Sets the value of the usuarioAutenticadoInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link UsuarioAutenticadoInfo }
     *     
     */
    public void setUsuarioAutenticadoInfo(UsuarioAutenticadoInfo value) {
        this.usuarioAutenticadoInfo = value;
    }

}

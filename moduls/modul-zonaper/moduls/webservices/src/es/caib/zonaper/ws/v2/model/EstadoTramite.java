
package es.caib.zonaper.ws.v2.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EstadoTramite.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EstadoTramite">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PENDIENTE"/>
 *     &lt;enumeration value="FINALIZADO"/>
 *     &lt;enumeration value="NO_EXISTE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EstadoTramite", namespace = "urn:es:caib:zonaper:ws:v2:model:EstadoPago")
@XmlEnum
public enum EstadoTramite {

    PENDIENTE,
    FINALIZADO,
    NO_EXISTE;

    public String value() {
        return name();
    }

    public static EstadoTramite fromValue(String v) {
        return valueOf(v);
    }

}

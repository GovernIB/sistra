
package es.caib.zonaper.ws.v2.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoElementoExpediente.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoElementoExpediente">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="REGISTRO"/>
 *     &lt;enumeration value="ENVIO"/>
 *     &lt;enumeration value="PREREGISTRO"/>
 *     &lt;enumeration value="PREENVIO"/>
 *     &lt;enumeration value="NOTIFICACION"/>
 *     &lt;enumeration value="COMUNICACION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoElementoExpediente", namespace = "urn:es:caib:zonaper:ws:v2:model:ElementoExpediente")
@XmlEnum
public enum TipoElementoExpediente {

    REGISTRO,
    ENVIO,
    PREREGISTRO,
    PREENVIO,
    NOTIFICACION,
    COMUNICACION;

    public String value() {
        return name();
    }

    public static TipoElementoExpediente fromValue(String v) {
        return valueOf(v);
    }

}

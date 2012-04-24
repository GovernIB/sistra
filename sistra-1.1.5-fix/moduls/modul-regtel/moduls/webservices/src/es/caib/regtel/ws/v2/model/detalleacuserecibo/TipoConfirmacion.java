
package es.caib.regtel.ws.v2.model.detalleacuserecibo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoConfirmacion.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoConfirmacion">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DESCONOCIDO"/>
 *     &lt;enumeration value="ENVIADO"/>
 *     &lt;enumeration value="NO_ENVIADO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoConfirmacion")
@XmlEnum
public enum TipoConfirmacion {

    DESCONOCIDO,
    ENVIADO,
    NO_ENVIADO;

    public String value() {
        return name();
    }

    public static TipoConfirmacion fromValue(String v) {
        return valueOf(v);
    }

}

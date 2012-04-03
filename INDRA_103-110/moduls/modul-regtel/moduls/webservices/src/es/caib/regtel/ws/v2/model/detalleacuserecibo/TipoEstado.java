
package es.caib.regtel.ws.v2.model.detalleacuserecibo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoEstado.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoEstado">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PENDIENTE"/>
 *     &lt;enumeration value="ENTREGADA"/>
 *     &lt;enumeration value="RECHAZADA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoEstado")
@XmlEnum
public enum TipoEstado {

    PENDIENTE,
    ENTREGADA,
    RECHAZADA;

    public String value() {
        return name();
    }

    public static TipoEstado fromValue(String v) {
        return valueOf(v);
    }

}

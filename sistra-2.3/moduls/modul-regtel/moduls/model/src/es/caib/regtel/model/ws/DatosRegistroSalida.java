
package es.caib.regtel.model.ws;

import java.util.ArrayList;


public class DatosRegistroSalida {

    private DatosExpediente datosExpediente;
    private OficinaRegistral oficinaRegistral;
    private DatosInteresado datosInteresado;
    private DatosRepresentado datosRepresentado;
    private DatosNotificacion datosNotificacion;
    private ArrayList documentos;

    public DatosExpediente getDatosExpediente() {
        return datosExpediente;
    }

    public void setDatosExpediente(DatosExpediente value) {
        this.datosExpediente = value;
    }

    public OficinaRegistral getOficinaRegistral() {
        return oficinaRegistral;
    }

    public void setOficinaRegistral(OficinaRegistral value) {
        this.oficinaRegistral = value;
    }

    public DatosInteresado getDatosInteresado() {
        return datosInteresado;
    }

    public void setDatosInteresado(DatosInteresado value) {
        this.datosInteresado = value;
    }

    public DatosRepresentado getDatosRepresentado() {
        return datosRepresentado;
    }

    public void setDatosRepresentado(DatosRepresentado value) {
        this.datosRepresentado = value;
    }

    public DatosNotificacion getDatosNotificacion() {
        return datosNotificacion;
    }

    public void setDatosNotificacion(DatosNotificacion value) {
        this.datosNotificacion = value;
    }

    public ArrayList getDocumentos() {
        return documentos;
    }

    public void setDocumentos(ArrayList value) {
        this.documentos = value;
    }

}

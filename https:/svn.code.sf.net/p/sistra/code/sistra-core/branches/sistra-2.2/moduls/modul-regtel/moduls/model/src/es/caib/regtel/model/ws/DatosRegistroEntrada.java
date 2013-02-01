
package es.caib.regtel.model.ws;

import java.util.ArrayList;


public class DatosRegistroEntrada {

    private OficinaRegistral oficinaRegistral;
    private DatosInteresado datosInteresado;
    private DatosRepresentado datosRepresentado;
    private DatosAsunto datosAsunto;
    private ArrayList documentos;

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

    public DatosAsunto getDatosAsunto() {
        return datosAsunto;
    }

    public void setDatosAsunto(DatosAsunto value) {
        this.datosAsunto = value;
    }

     public ArrayList getDocumentos() {
        return documentos;
    }

    public void setDocumentos(ArrayList value) {
        this.documentos = value;
    }

}

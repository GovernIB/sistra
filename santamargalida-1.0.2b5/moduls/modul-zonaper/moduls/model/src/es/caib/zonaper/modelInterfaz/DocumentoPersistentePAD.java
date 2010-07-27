package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;

import es.caib.redose.modelInterfaz.ReferenciaRDS;

public class DocumentoPersistentePAD  implements Serializable {

	public final static char ESTADO_CORRECTO = 'S';
	public final static char ESTADO_INCORRECTO = 'N';
	public final static char ESTADO_NORELLENADO = 'V';
	
    // Fields    	     
     private String identificador;
     private int numeroInstancia=1;
     private char estado=ESTADO_NORELLENADO;
     private ReferenciaRDS refRDS;
     private String nombreFicheroAnexo;
     private String descripcionGenerico;

    // Constructors
    /** default constructor */
    public DocumentoPersistentePAD() {
    }

	public char getEstado() {
		return estado;
	}

	public void setEstado(char estado) {
		this.estado = estado;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getNombreFicheroAnexo() {
		return nombreFicheroAnexo;
	}

	public void setNombreFicheroAnexo(String nombreFicheroAnexo) {
		this.nombreFicheroAnexo = nombreFicheroAnexo;
	}

	public int getNumeroInstancia() {
		return numeroInstancia;
	}

	public void setNumeroInstancia(int numeroInstancia) {
		this.numeroInstancia = numeroInstancia;
	}

	public ReferenciaRDS getRefRDS() {
		return refRDS;
	}

	public void setReferenciaRDS(ReferenciaRDS refRDS) {
		this.refRDS = refRDS;
	}

	public String getDescripcionGenerico() {
		return descripcionGenerico;
	}

	public void setDescripcionGenerico(String descripcionGenerico) {
		this.descripcionGenerico = descripcionGenerico;
	}

}

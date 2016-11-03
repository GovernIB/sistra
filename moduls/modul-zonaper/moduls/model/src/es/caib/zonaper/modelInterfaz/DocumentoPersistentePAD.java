package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;

import es.caib.redose.modelInterfaz.ReferenciaRDS;

public class DocumentoPersistentePAD  implements Serializable {

	public final static char ESTADO_CORRECTO = 'S';
	public final static char ESTADO_INCORRECTO = 'N';
	public final static char ESTADO_NORELLENADO = 'V';
	
	public final static String TIPO_FORMULARIO = "F";
	public final static String TIPO_ANEXO = "A";
	public final static String TIPO_PAGO = "P";
	
	/**
	 * Para trámites que se ejecutan de forma delegada indica que el usuario que tiene actualmente el trámite 
	 * ha realizado todas sus acciones sobre el paso pero queda que otro delegado firme el documento
	 */
	public static final String ESTADO_PENDIENTE_DELEGACION_FIRMA = "DF";
	
	/**
	 * Para trámites que se ejecutan de forma delegada indica que se ha rechazado el documento 
	 * desde la bandeja de firma y debe subsanarlo
	 */
	public static final String ESTADO_RECHAZADO_DELEGACION_FIRMA = "RF";
	
    // Fields    	     
     private String identificador;
     private int numeroInstancia=1;
     private char estado=ESTADO_NORELLENADO;
     private ReferenciaRDS refRDS;
     private String nombreFicheroAnexo;
     private String descripcionGenerico;
     private String delegacionEstado;
     private String delegacionFirmantes;
     private String delegacionFirmantesPendientes;  

     private String tipoDocumento;
     private String esPagoTelematico = "N";
     
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

	public String getDelegacionEstado() {
		return delegacionEstado;
	}

	public void setDelegacionEstado(String delegacionEstado) {
		this.delegacionEstado = delegacionEstado;
	}

	public String getDelegacionFirmantes() {
		return delegacionFirmantes;
	}

	public void setDelegacionFirmantes(String delegacionFirmantes) {
		this.delegacionFirmantes = delegacionFirmantes;
	}

	public String getDelegacionFirmantesPendientes() {
		return delegacionFirmantesPendientes;
	}

	public void setDelegacionFirmantesPendientes(
			String delegacionFirmantesPendientes) {
		this.delegacionFirmantesPendientes = delegacionFirmantesPendientes;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getEsPagoTelematico() {
		return esPagoTelematico;
	}

	public void setEsPagoTelematico(String esPagoTelematico) {
		this.esPagoTelematico = esPagoTelematico;
	}

}

package es.caib.sistra.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.sistra.modelInterfaz.DocumentoConsulta;

/**
 * 
 * Clase que modeliza el resultado de registrar
 * un trámite independientemente del destino: 
 * Registro, Envío o Consulta
 *
 */
public class ResultadoRegistrar implements Serializable{
	
	public static final int REGISTRO_TELEMATICO = 0;
	public static final  int CONSULTA = 1;
	public static final  int ENVIO = 2;
	public static final  int PREREGISTRO = 3;
	
	/**
	 * Indica tipo de registro: Registro  Telemático / Preregistro / Envio / Consulta
	 */	
	private int tipo;
	
	/**
	 * Para trámites de tipo Registro/Preregistro/Envio: Número de registro/envio
	 */	
	private String numero;
	
	/**
	 * Para trámites de tipo Registro/Preregistro/Envio: Fecha de registro/envio
	 */	
	private Date fecha;
	
	/**
	 * Para trámites de tipo Registro/Preregistro/Envio: Referencia RDS del Justificante
	 */
	private ReferenciaRDS rdsJustificante;
	
	/**
	 * Para trámites de tipo Registro/Preregistro/Envio: Justificante cacheado para mejorar acceso
	 */
	private AsientoCompleto justificanteRegistro = null;
	
	/**
	 * Para trámites de tipo Consulta: Documentos adicionales devueltos
	 */
	private List documentos;
	
	/**
	 * Procedimiento.
	 */
	private String procedimiento;
	
	public List getDocumentos() {
		return documentos;
	}
	
	/**
	 * Genera una lista de DocumentosConsultaFront a partir de la lista de documentos 
	 * @return
	 */
	public List getDocumentosConsultaFront() {
		List documentosConsulta = new ArrayList();		
		for (int i=0;i<getDocumentos().size();i++){
			DocumentoConsulta doc = (DocumentoConsulta) getDocumentos().get(i);
			DocumentoConsultaFront docF = new DocumentoConsultaFront();
			
			docF.setTipo(doc.getTipoDocumento() == DocumentoConsulta.TIPO_DOCUMENTO_URL ? DocumentoConsultaFront.TIPO_DOCUMENTO_URL : DocumentoConsultaFront.TIPO_DOCUMENTO_BIN );
			docF.setNombre(doc.getNombreDocumento());
			docF.setUrlEnlace(doc.getUrlAcceso());
			docF.setUrlVentanaNueva(doc.isUrlNuevaVentana());
			
			documentosConsulta.add(docF);
		}
		return documentosConsulta;
	}
	
	public List getTipoDocumentos() {
		List documentosConsulta = new ArrayList();		
		for (int i=0;i<getDocumentos().size();i++){
			documentosConsulta.add( Character.toString(( (DocumentoConsulta) getDocumentos().get(i)).getTipoDocumento()));
		}
		return documentosConsulta;
	}
	
	public void setDocumentos(List documentos) {
		this.documentos = documentos;
	}
	public ReferenciaRDS getRdsJustificante() {
		return rdsJustificante;
	}
	public void setRdsJustificante(ReferenciaRDS rdsJustificante) {
		this.rdsJustificante = rdsJustificante;		
	}	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public AsientoCompleto getJustificanteRegistro() {
		return justificanteRegistro;
	}
	public void setJustificanteRegistro(AsientoCompleto justificanteRegistro) {
		this.justificanteRegistro = justificanteRegistro;
	}

	public String getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}
	
	
}

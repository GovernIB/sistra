package es.caib.redose.model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Modeliza datos de trabajo de migracion.
 */
public class MigracionExportWork {
	
	/**
	 * ID Trabajo
	 */
	private String id;
	
	/**
	 * Parametros migracion.
	 */
	private MigracionParametros parametros;
	
	/**
	 * Total documentos (se migraran solo el numero maximo).
	 */
	private long totalDocumentos;
	
	/**
	 * Lista de codigos de documentos a migrar (solo contiene hasta el numero maximo).
	 */
	private List codigos;
	
	/**
	 * Indice actual.
	 */
	private int index;
			
	/**
	 * Ultimo acceso (para poder purgar trabajos antiguos).
	 */
	private Date lastAccess;
	
	/**
	 * Errores.
	 */
	private List errores;
	
	
	/**
	 * Crea trabajo.
	 * @param parametros parametros migracion
	 * @param totalDocumentos numero total docs
	 * @param codigos codigos de documentos a migrar
	 */
	public MigracionExportWork(MigracionParametros parametros,
			long totalDocumentos, List codigos) {
		super();
		this.parametros = parametros;
		this.totalDocumentos = totalDocumentos;
		this.codigos = codigos;
		
		id = generateId();
		index = 0;
		lastAccess = new Date();
		errores = new ArrayList();
	}
	
	public Long[] obtenerDocsProximaIteracion(){		
		
		int inc = this.getParametros().getNumDocsIteracion();
		int numDocsIteracion = this.getParametros().getNumDocsIteracion();
		
		// Comprobamos si ya se ha terminado
		if (this.index ==  this.codigos.size()){
			return new Long[0];
		}
		
		// Comprobamos si son las ultimas entradas
		if ((this.index + numDocsIteracion) > this.codigos.size()){
			inc = this.codigos.size() - this.index;
		}
				
		// Devolvemos entradas seleccionadas
		Long [] res = new Long[inc];
		for (int i=0; i < inc; i++)
			res[i] = (Long) this.codigos.get(this.index + i);
				
		// Actualizamos indice
		this.index = this.index + inc;
		
		// Actualizamos fecha ultimo acceso
		this.lastAccess = new Date();
		
		return res;		
	}
	

	public MigracionParametros getParametros() {
		return parametros;
	}


	public Date getLastAccess() {
		return lastAccess;
	}
	
	public int incrementarError(Exception ex) {
		errores.add(ex);
		return errores.size();
	}

	public String obtenerDescripcionErrores() {
		String res = "";
		int i = 1;
		for (Iterator it = errores.iterator(); it.hasNext();) {
			Exception ex = (Exception) it.next();
			res += "ERROR " + i + ":\n" ;
			res += "------------------\n" ;
			res += ExceptionUtils.getFullStackTrace(ex);
			res += "\n\n\n";
			i++;
		}
		return res;
	}
	

	public long getTotalDocumentos() {
		return totalDocumentos;
	}


	public String getId() {
		return id;
	}

	public int getNumErrores() {
		return errores.size();
	}

	public int getTotalProcesados() {
		return index;
	}

	public int getTotalProcesar() {
		return codigos.size();
	}
	
	private String generateId(){
		SecureRandom sr = new SecureRandom();
		return (new Date()).getTime() + "" + sr.nextInt(99999999); 
	}

}

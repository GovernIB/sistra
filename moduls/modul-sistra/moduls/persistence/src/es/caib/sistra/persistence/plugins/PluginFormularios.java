package es.caib.sistra.persistence.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.caib.sistra.model.DatosFormulario;
import es.caib.sistra.model.ReferenciaCampo;
import es.caib.zonaper.modelInterfaz.DocumentoPersistentePAD;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;

/**
 * Plugin que permite acceder a los datos de los formularios
 */
public class PluginFormularios {

	/**
	 * Datos de los formularios
	 */
	private Map datosFormularios=null;	
	/**
	 * Estado persistencia del trámite
	 */
	private TramitePersistentePAD tramitePersistentePAD;
	/**
	 * Indica si se permiten modificar los formularios
	 */
	private boolean readOnly=true;
	/**
	 *  Lista de formularios cuyos datos han sido modificados (mediante función setDatoFormulario)
	 *  La lista contiene el código de formulario
	 */
	private List formulariosDatosModificados = new ArrayList();		
	/**
	 * Indica como se accede a el código para campos lista (con código/descripción)
	 */
	private static final String CODIGO_LISTAS = "[CODIGO]";		
	 
	
	/**
	 * Constructor de datos de formulario (acceso read-only a los datos del formulario)
	 */
	public PluginFormularios(boolean readOnly){
		this.setReadOnly(readOnly);
	}
			
	/**
	 * Establece datos de los formularios
	 */
	public void setDatosFormularios(Map datos){
		datosFormularios = datos;
	}
	
	/**
	 * Establece estado persistencia
	 */
	public void setEstadoPersistencia(TramitePersistentePAD tramitePAD){
		tramitePersistentePAD = tramitePAD;
	}		
	
	/**
	 * Obtiene numero de valores para un campo. Si no existe devuelve 0.
	 * 
	 * @param idDocumento
	 * @param instancia
	 * @param referenciaCampo
	 * @param numValor
	 * @return
	 * @throws Exception
	 */
	public int getNumeroValoresCampo(String idDocumento,int instancia,String referenciaCampo) throws Exception{
		DatosFormulario form = (DatosFormulario) datosFormularios.get(idDocumento + "-" + instancia);
		if (form == null) throw new Exception("Referencia no válida: no existe documento " +idDocumento+"-"+ instancia);		
		return form.getNumeroValoresCampo(referenciaCampo);
	}
	
	/**
	 * Obtiene numero de valores para un campo. Si no existe devuelve 0.
	 * 
	 * @param referenciaCampo
	 * @return
	 * @throws Exception
	 */
	public int getNumeroValoresCampo(String referenciaCampo) throws Exception{
		ReferenciaCampo ref = new ReferenciaCampo(referenciaCampo);				
		return getNumeroValoresCampo(ref.getIdentificadorDocumento(),ref.getInstancia(),ref.getCampo());				
	}
	
	
	/**
	 * Obtiene el valor de un campo multivaluado del formulario 
	 * 
	 * @param idDocumento
	 * @param instancia
	 * @param referenciaCampo
	 * @param numValor
	 * @return
	 * @throws Exception
	 */
	public String getDatoFormulario(String idDocumento,int instancia,String referenciaCampo,int numValor) throws Exception{
		DatosFormulario form = (DatosFormulario) datosFormularios.get(idDocumento + "-" + instancia);
		if (form == null) throw new Exception("Referencia no válida: no existe documento " +idDocumento+"-"+ instancia);
		
		if (referenciaCampo.endsWith(CODIGO_LISTAS)){
			return form.getIndiceCampo(referenciaCampo.substring(0,referenciaCampo.length() - CODIGO_LISTAS.length()),numValor);
		}else{
			return form.getValorCampo(referenciaCampo,numValor);
		}		
	}			
	
	/**
	 * Obtiene el valor de un campo del formulario 
	 * 
	 * @param idDocumento
	 * @param instancia
	 * @param referenciaCampo
	 * @return
	 * @throws Exception
	 */
	public String getDatoFormulario(String idDocumento,int instancia,String referenciaCampo) throws Exception{
		return getDatoFormulario(idDocumento,instancia,referenciaCampo,0);
	}
		
	/**
	 * Obtiene el valor de un campo del formulario 
	 * 
	 * @param referenciaCampo en formato idDocumento.instancia.campo
	 * @return
	 * @throws Exception
	 */
	public String getDatoFormulario(String referenciaCampo) throws Exception{				
		return getDatoFormulario(referenciaCampo,0);				
	}
	
	/**
	 * Obtiene el valor de un campo multivaluado del formulario 
	 * 
	 * @param referenciaCampo en formato idDocumento.instancia.campo
	 * @return
	 * @throws Exception
	 */
	public String getDatoFormulario(String referenciaCampo,int numValor) throws Exception{
		
		ReferenciaCampo ref = new ReferenciaCampo(referenciaCampo);				
		return getDatoFormulario(ref.getIdentificadorDocumento(),ref.getInstancia(),ref.getCampo(),numValor);				
	}
	
	/**
	 * Establece el valor de un campo del formulario 
	 * 
	 * @param idDocumento
	 * @param instancia
	 * @param referenciaCampo
	 * @return
	 * @throws Exception
	 */
	public void setDatoFormulario(String idDocumento,int instancia,String referenciaCampo,String valor) throws Exception{
		setDatoFormulario(idDocumento,instancia,referenciaCampo,0,valor);
	}
	
	/**
	 * Establece el valor de un campo multivaluado del formulario 
	 * 
	 * @param idDocumento
	 * @param instancia
	 * @param referenciaCampo
	 * @return
	 * @throws Exception
	 */
	public void setDatoFormulario(String idDocumento,int instancia,String referenciaCampo,int numValor,String valor) throws Exception{
		DatosFormulario form = (DatosFormulario) datosFormularios.get(idDocumento + "-" + instancia);
		if (form == null) throw new Exception("Referencia no válida: no existe documento " +idDocumento+"-"+ instancia);
		if (this.isReadOnly()) throw new Exception("No se puede modificar el documento, ya que el plugin esta configurado read-only");;
		
		if (referenciaCampo.endsWith(CODIGO_LISTAS)){			
			form.setIndiceCampo(referenciaCampo.substring(0,referenciaCampo.length() - CODIGO_LISTAS.length()),numValor,valor);
		}else{
			form.setValorCampo(referenciaCampo,numValor,valor);
		}		
		
		// Marcamos el formulario como modificado
		formulariosDatosModificados.add(idDocumento + "-" + instancia);
	}
		
	/**
	 * Establece el valor de un campo del formulario 
	 * 
	 * @param referenciaCampo en formato idDocumento.instancia.campo
	 * @return
	 * @throws Exception
	 */
	public void setDatoFormulario(String referenciaCampo,String valor) throws Exception{		
		setDatoFormulario(referenciaCampo,0,valor);		
	}
	
	/**
	 * Establece el valor de un campo multivaluado del formulario 
	 * 
	 * @param referenciaCampo en formato idDocumento.instancia.campo
	 * @return
	 * @throws Exception
	 */
	public void setDatoFormulario(String referenciaCampo,int numValor,String valor) throws Exception{		
		ReferenciaCampo ref = new ReferenciaCampo(referenciaCampo);				
		setDatoFormulario(ref.getIdentificadorDocumento(),ref.getInstancia(),ref.getCampo(),numValor,valor);				
	}
	
	
	/**
	 * Obtiene el estado del formulario 
	 * 
	 * @param idDocumento
	 * @param instancia
	 * @param referenciaCampo
	 * @return
	 * @throws Exception
	 */
	public String getEstadoFormulario(String idDocumento,int instancia) throws Exception{
		DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(idDocumento + "-" + instancia);
		if (docPAD == null) throw new Exception("No existe documento " + idDocumento + "-" + instancia);
		return Character.toString(docPAD.getEstado());
	}

	/**
	 * Establece estado de un formulario
	 * 
	 * @param idDocumento
	 * @param instancia
	 * @param estado
	 * @return
	 * @throws Exception
	 */
	public void setEstadoFormulario(String idDocumento,int instancia,String estado) throws Exception{
		if (this.isReadOnly()) throw new Exception("No se puede modificar el documento, ya que el plugin esta configurado read-only");;
		if (datosFormularios.get(idDocumento + "-" + instancia) == null) throw new Exception("Formulario no inicializado");
		DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(idDocumento + "-" + instancia);		
		if (docPAD == null) throw new Exception("No existe documento " + idDocumento + "-" + instancia);
		
		char e = estado.charAt(0);
		if (estado.length() != 1 || e != DocumentoPersistentePAD.ESTADO_CORRECTO || e != DocumentoPersistentePAD.ESTADO_INCORRECTO || e!= DocumentoPersistentePAD.ESTADO_NORELLENADO ){
			throw new Exception("Estado no válido");
		}
		
		docPAD.setEstado(e);
	}
	
	/**
	 * Obtiene el xml del formulario
	 * 
	 * @param idDocumento
	 * @param instancia
	 * @return
	 * @throws Exception
	 */
	public String getXmlFormulario(String idDocumento,int instancia) throws Exception{
		DatosFormulario form = (DatosFormulario) datosFormularios.get(idDocumento + "-" + instancia);
		if (form == null) throw new Exception("Referencia no válida: no existe documento " +idDocumento+"-"+ instancia);
		return form.getString();					
	}		
	
	public Map getDatosFormularios() {
		return datosFormularios;
	}

	protected boolean isReadOnly() {
		return readOnly;
	}

	protected void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public List getFormulariosDatosModificados() {
		return formulariosDatosModificados;
	}

	public void setFormulariosDatosModificados(List formulariosDatosModificados) {
		this.formulariosDatosModificados = formulariosDatosModificados;
	}
	
}

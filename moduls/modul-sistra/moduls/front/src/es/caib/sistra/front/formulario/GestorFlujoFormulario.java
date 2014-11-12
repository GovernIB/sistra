package es.caib.sistra.front.formulario;

import java.util.Map;

import es.caib.sistra.front.storage.Storable;
import es.caib.sistra.model.ConfiguracionGestorFlujoFormulario;
import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.TramiteFront;


public interface GestorFlujoFormulario extends Storable
{
	
	/**
	 * Idenficador gestor formularios.
	 * @param idGestor
	 */
	public void setId(String idGestor);
	
	/**
	 * Idenficador gestor formularios.
	 * @return idGestor
	 */
	public String getId();
	
	
	/**
	 * Inicializacion de la clase
	 * @param initParams
	 */
	public void init(Map initParams);
	
	/**
	 * Realiza conexi�n con sistema de rellenado de formularios para pasarle la informaci�n necesaria
	 * 
	 * @param confGestorForm	Configuraci�n espec�fica del gestor de formularios
	 * @param formulario	DocumentFront con la informaci�n del formulario a abrir
	 * @param informacionTramite	TramiteFront con la informaci�n del tr�mite
	 * @param parametrosRetorno	Par�metros que debe retornar el sistema de formularios al invocar a sistra 
	 * @return
	 */
	public String irAFormulario( 
			ConfiguracionGestorFlujoFormulario confGestorForm,
			DocumentoFront formulario,
			TramiteFront informacionTramite,
			Map parametrosRetorno );
	
	/**
	 * Realiza el proceso de guardar los datos del formulario
	 * 
	 * @param xmlInicial XML con los datos originales
	 * @param xmlActual XML con los nuevos datos
	 * @param guardadoSinFinalizar Indica si se ha guardado el formulario sin finalizar
	 * @return Devuelve el token al sistema de rellenado de formularios para que pueda redirigir el navegador al sistema de tramitaci�n
	 */
	public String guardarDatosFormulario( String xmlInicial, String xmlActual, boolean guardadoSinFinalizar );
	
	/**
	 * Una vez guardado el formulario desde el sistema de rellenado de formularios se redirige el navegador al sistema de tramitaci�n 
	 * 
	 * @param token
	 * @return
	 */
	public ResultadoProcesoFormulario continuarTramitacion( String token );

	/**
	 * Permite cancelar el rellenado de un formulario
	 * @return Devuelve el token al sistema de rellenado de formularios para que pueda redirigir el navegador al sistema de tramitaci�n
	 */
	public String cancelarFormulario();
	
	/**
	 * Una vez cancelado el formulario en el sistema de rellenado permite continuar con la tramitaci�n
	 * @return 
	 */
	public boolean continuarCancelacion( String token );

}

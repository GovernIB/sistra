package es.caib.sistra.front.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.front.Constants;
import es.caib.sistra.model.PasoTramitacion;
import es.caib.sistra.model.TramiteFront;

public class PasosNecesariosController extends TramiteController
{
	private static Log _log = LogFactory.getLog( PasosNecesariosController.class ); 
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		// Calculamos textos pasos
		TramiteFront tramite = this.getTramiteFront( request );
		List lstPasos = tramite.getPasos();
		for ( int i = 0; i < lstPasos.size(); i++ )
		{
			PasoTramitacion paso = ( PasoTramitacion ) lstPasos.get( i );
			establecerTituloYCuerpo( tramite, paso );
		}
		request.setAttribute( "pasos", lstPasos );
		
		// Calculamos navegación enlace siguiente
		Map navegacion = calcularNavegacion(tramite);
		
		if ( _log.isDebugEnabled() )
		{
			for (int i = 0;i<tramite.getPasos().size();i++)
			{
				_log.debug( "PASO " + i + " - <" + ((List) navegacion.get("anterior")).get(i).toString() + "> <" + ((List) navegacion.get("siguiente")).get(i).toString() + ">");
			}		
		}
		request.setAttribute( "navegacion", navegacion );
		
		// En caso de flujo de tramitación verificamos si esta en estado de pasar y a quien
		if (tramite.isFlujoTramitacion()) {			
			// Si hay que pasarlo a algún Nif lo indicamos:
			String nifFlujo=tramite.getFlujoTramitacionNif();
			if (!StringUtils.isEmpty(nifFlujo)){
				request.setAttribute("pasarFlujoTramitacion",nifFlujo);
			}				
		}
		
		//
		// 	COMPROBAMOS SI HAY DELEGACION Y HAY QUE PASAR EL TRAMITE
		//
		
		// Si estamos en pantalla de anexar documento no mostramos la zona de remitir
		//org.apache.struts.action.mapping.instance=ActionConfig[path=/protected/irAAnexar,name=irAAnexarForm,scope=request,type=es.caib.sistra.front.action.IrAAnexarAction
		ActionConfig action = (ActionConfig) request.getAttribute("org.apache.struts.action.mapping.instance");
		if  (!(action != null && "es.caib.sistra.front.action.IrAAnexarAction".equals(action.getType()))){
			if (tramite.isRemitirDelegacionFirma()){
				request.setAttribute("pendienteDelegacionFirma","true");
				return;
			}
			if (tramite.isRemitirDelegacionPresentacion()) {
				request.setAttribute("pendienteDelegacionPresentacion","true");
				return;
			}
		}
		
		
		// En caso de trámite de consulta y paso justificante, enviamos a la configuración de resultado consulta
		if ( tramite.isConsultar() && tramite.getPasoTramitacion().getTipoPaso() == PasoTramitacion.PASO_FINALIZAR )
		{
			request.setAttribute( "zonaPasoTramite", ".step7ResultadoConsulta" );
		}
		
		// En caso de tramite anonimo controlamos si la clave ya ha sido almacenada
		if (tramite.getDatosSesion().getNivelAutenticacion() == 'A'){
			if (request.getSession().getAttribute(Constants.CLAVE_ALMACENADA_KEY + "-" + tramite.getIdPersistencia()) != null){
				request.setAttribute(Constants.CLAVE_ALMACENADA_KEY,"true");
			}
		}
	}
	
	/**
	 * Calculamos navegación enlace siguiente 
	 * @param tramite
	 * @return
	 */
	private Map calcularNavegacion( TramiteFront tramite )
	{
		Map navegacion = new HashMap();
		List siguientes = new ArrayList();
		List anteriores = new ArrayList();
		navegacion.put("anterior",anteriores);
		navegacion.put("siguiente",siguientes);
		
		boolean habilitarSiguiente,habilitarAnterior,completadosAnteriores=true;		
		for ( int i = 0; i < tramite.getPasos().size(); i++ )
		{
			 			
			PasoTramitacion paso = ( PasoTramitacion ) tramite.getPasos().get( i );			
			
			// Comprobación de pasos anteriores completados
			// (damos por completados los pendientes de flujo y de delegacion)
			PasoTramitacion pasoAnterior =  null;
			if (i>0){
				pasoAnterior = ( PasoTramitacion ) tramite.getPasos().get( i-1 );
				if (pasoAnterior.getCompletado().equals(PasoTramitacion.ESTADO_COMPLETADO)
					||
					pasoAnterior.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_FLUJO)
					||
					pasoAnterior.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_DELEGACION_PRESENTACION)
					||
					pasoAnterior.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_DELEGACION_FIRMA)	){
						completadosAnteriores = true;
				}else{
						completadosAnteriores = false;
				}
			}
			
			
			// SE HA ENVIADO EL TRAMITE			
			if ( i < tramite.getPasoActual() &&
				 tramite.getPasoActual() > tramite.getPasoNoRetorno() &&
				 i <= tramite.getPasoNoRetorno()) {
				// Si es un paso anterior al de no retorno (registrar) no mostramos enlaces
				habilitarSiguiente = false;
				habilitarAnterior  = false;
				siguientes.add(i,new Boolean(habilitarSiguiente));
				anteriores.add(i,new Boolean(habilitarAnterior));			
				continue;
			}
			
			// PASO COMPLETADO					
			if (paso.getCompletado().equals(PasoTramitacion.ESTADO_COMPLETADO)) {
			
				// Siguiente siempre que todos los pasos anteriores esten completados y no tiene que ser el último
				if (completadosAnteriores && i<(tramite.getPasos().size()-1)){
					habilitarSiguiente = true;
				}else{
					habilitarSiguiente = false;
				}	
				
				// Si se ha enviado no dejamos volver atrás
				if (paso.getTipoPaso() == PasoTramitacion.PASO_FINALIZAR) {
					habilitarAnterior = false;
				}else{
					habilitarAnterior = true;
				}
				
				/**
				 * MODIFICACION: NO MOSTRAMOS PAGINA DE PASOS
				 */
				if ( i > 0 && pasoAnterior.getTipoPaso() == PasoTramitacion.PASO_PASOS)
					habilitarAnterior = false;
				
				siguientes.add(i,new Boolean(habilitarSiguiente));
				anteriores.add(i,new Boolean(habilitarAnterior));
				continue;
			}
			
			// PASO PENDIENTE DE FLUJO 
			if (paso.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_FLUJO)
				||
				paso.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_DELEGACION_PRESENTACION)
				||
				paso.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_DELEGACION_FIRMA)
				){
				
				// Permitimos anterior
				habilitarAnterior = true;							

				// Mostramos siguiente si estamos en paso Rellenar y el siguiente es anexar
				if (	paso.getTipoPaso() == PasoTramitacion.PASO_RELLENAR &&
						((PasoTramitacion) tramite.getPasos().get( i + 1 )).getTipoPaso() == PasoTramitacion.PASO_ANEXAR
					){
						habilitarSiguiente = true;
				}else{
						habilitarSiguiente = false;
				}
				
				/**
				 * MODIFICACION: NO MOSTRAMOS PAGINA DE PASOS
				 */
				if (i > 0 && pasoAnterior.getTipoPaso() == PasoTramitacion.PASO_PASOS)
					habilitarAnterior = false;
				
				siguientes.add(i,new Boolean(habilitarSiguiente));
				anteriores.add(i,new Boolean(habilitarAnterior));
				continue;
			}				
				
			
			// PASO NO COMPLETADO						
			if (paso.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE)){
				
				habilitarSiguiente = false;
				habilitarAnterior = false;
								
				// Si se han completado todas los anteriores mostramos anterior				
				if (completadosAnteriores){
					habilitarAnterior = true;
				}
								
				/**
				 * MODIFICACION: NO MOSTRAMOS PAGINA DE PASOS
				 */
				if (i > 0 && pasoAnterior.getTipoPaso() == PasoTramitacion.PASO_PASOS)
					habilitarAnterior = false;
				
				siguientes.add(i,new Boolean(habilitarSiguiente));
				anteriores.add(i,new Boolean(habilitarAnterior));
								
			}						
						
		}		
		
		return navegacion;
	}
	
	
	private void establecerTituloYCuerpo( TramiteFront tramite, PasoTramitacion paso )
	{
		int tipoPaso = paso.getTipoPaso();
		String keyPreffix = "paso." + tipoPaso;
		String titleKey = keyPreffix + ".titulo";
		String tabKey = keyPreffix + ".tab";
		String textKey = keyPreffix + ".texto";
		paso.setClaveTitulo(  titleKey );
		paso.setClaveTab( tabKey );
		char tipoTramitacion = tramite.getTipoTramitacion();
		char nivelAutenticacion = tramite.getDatosSesion().getNivelAutenticacion();
		switch( tipoPaso )
		{
			case PasoTramitacion.PASO_DEBESABER : 
			{
				String informacionInicio = tramite.getInformacionInicio();
				if ( !StringUtils.isEmpty( informacionInicio ) )
				{
					textKey += ".informacionInicio";
				}
				if ( tramite.isDescargarPlantillas() )
				{
					textKey += ".descargarPlantillas";
				}
				paso.setClaveCuerpo( textKey );
				return;
			}
			case PasoTramitacion.PASO_RELLENAR :
			{
				paso.setClaveCuerpo( textKey );
				return;
			}
			case PasoTramitacion.PASO_ANEXAR :
			{
				paso.setClaveCuerpo( textKey );
				return;
			}
			case PasoTramitacion.PASO_PAGAR :
			{
				paso.setClaveCuerpo( textKey );
				return;
			}
			case PasoTramitacion.PASO_REGISTRAR :
			{
				if ( tramite.getRegistrar() )
				{
					textKey += ".registro";
					titleKey += ".registro";
					tabKey += ".registro";
				}
				else
				{
					textKey += ".envio";
					titleKey += ".envio";
					tabKey += ".envio";
				}
				if ( Constants.TIPO_CIRCUITO_TRAMITACION_TELEMATICO == tipoTramitacion )
				{
					textKey += ".telematico";
				}
				else if ( Constants.TIPO_CIRCUITO_TRAMITACION_PRESENCIAL == tipoTramitacion )
				{
					textKey += ".presencial";
				}
				else if ( Constants.TIPO_CIRCUITO_TRAMITACION_DEPENDE == tipoTramitacion )
				{
					textKey += ".depende";
				}
				paso.setClaveTab( tabKey );
				paso.setClaveTitulo( titleKey );
				paso.setClaveCuerpo( textKey );
				return;
			}
			case PasoTramitacion.PASO_FINALIZAR :
			{
				if ( tramite.getRegistrar() )
				{
					textKey += ".registro";
					titleKey += ".registro";
					tabKey += ".registro";
				}
				else if ( tramite.isConsultar())
				{
					textKey += ".consulta";
					titleKey += ".consulta";
					tabKey += ".consulta";
				}
				else
				{
					textKey += ".envio";
					titleKey += ".envio";
					tabKey += ".envio";
				}
				
				if ( Constants.TIPO_CIRCUITO_TRAMITACION_TELEMATICO == tipoTramitacion   )
				{
					textKey += ".telematico";
				}
				else if ( Constants.TIPO_CIRCUITO_TRAMITACION_PRESENCIAL == tipoTramitacion )
				{
					textKey += ".presencial";
				}
				else if ( Constants.TIPO_CIRCUITO_TRAMITACION_DEPENDE == tipoTramitacion )
				{
					textKey += ".depende";
				}
				paso.setClaveTab( tabKey );
				paso.setClaveTitulo( titleKey );
				paso.setClaveCuerpo( textKey );
				
				return;
			}				
			default :
			{
				paso.setClaveCuerpo( keyPreffix + ".texto" );
			}
		}
	}
}

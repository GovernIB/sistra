package es.caib.sistra.front.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.model.TramiteFront;

public class StepNavController extends BaseController
{

	/**
	 * Como resultado de la ejecucion de este controller, apareceran como atributos del request las siguientes variables:
	 * pasoAnterior
	 * pasoSiguiente
	 * Si tienen valor "true", quiere decir que se puede acceder, respectivamente, al paso anterior y al paso siguiente
	 */
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		TramiteFront tramite = this.getTramiteFront( request );
		
		// Recogemos navegacion enlaces(calculada en PasosNecesariosController)
		Map navegacion = (Map) request.getAttribute("navegacion");
		
		request.setAttribute( "pasoAnterior",((List) navegacion.get("anterior")).get(tramite.getPasoActual()).toString() );
		request.setAttribute( "pasoSiguiente",((List) navegacion.get("siguiente")).get(tramite.getPasoActual()).toString() );
		
		
		/*
		TramiteFront tramite = this.getTramiteFront( request );
		if ( tramite != null )
		{
			int pasoActual 	= tramite.getPasoActual();
			int nPasos 		= tramite.getPasos().size();
			int pasoNoRetorno = tramite.getPasoNoRetorno();
			
			// Establecer pasoSiguiente si se cumplen las siguientes condiciones:
			//   - Si hay pasos + pasos
			//	 - El paso actual está completado o si esta pendiente de flujo y es el paso de rellenar		
			PasoTramitacion pasoAct = (PasoTramitacion) tramite.getPasos().get(pasoActual);
			if ( pasoActual < nPasos -1  && 
					( 
							pasoAct.getCompletado().equals(PasoTramitacion.ESTADO_COMPLETADO) 
							||
							(pasoAct.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_FLUJO) && pasoAct.getTipoPaso() == PasoTramitacion.PASO_RELLENAR) 
					)
				)
			{
				request.setAttribute( "pasoSiguiente", "true" );
			}
			else
			{
				request.setAttribute( "pasoSiguiente", "false" );
			}
			// establecer pasoAnterior
			// Si no se ha llegado al punto de no retorno
			// O se está entre el punto de no retorno y el último paso
			if ( pasoActual <= pasoNoRetorno || pasoActual > pasoNoRetorno + 1 )
			{
				request.setAttribute( "pasoAnterior", "true" );
			}
			else
			{
				request.setAttribute( "pasoAnterior", "false" );
			}
		}
		else
		{
			request.setAttribute( "pasoSiguiente", "false" );
			request.setAttribute( "pasoAnterior", "false" );
		}
		*/
	}

}

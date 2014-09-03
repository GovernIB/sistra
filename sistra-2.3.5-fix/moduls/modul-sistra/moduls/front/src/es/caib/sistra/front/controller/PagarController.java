package es.caib.sistra.front.controller;

import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.TramiteFront;

public class PagarController extends BaseController
{
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		TramiteFront tramite 			= this.getTramiteFront( request );
				
		// Comprobamos si:
		//	- existe algún pago a realizar (obligatorio u opcional)		
		//  - hay algún pago realizado
		//  - metodos de pagos permitidos
		String puedePagar = "N";
		String pagosIniciados = "N";
		char pagosMetodos = 'X';
		for (Iterator it=tramite.getPagos().iterator();it.hasNext();){			
			DocumentoFront pago = (DocumentoFront) it.next();
			// Pago puede pagarse?
			if (puedePagar.equals("N") && pago.getObligatorio() != DocumentoFront.DEPENDIENTE){
				puedePagar = "S";
			}
			// Existe algun pago realizado
			if (pago.getEstado() != DocumentoFront.ESTADO_NORELLENADO){
				pagosIniciados = "S";
			}
			// Existe algun pago que puede ser presencial
			if (pagosMetodos == 'X'){
				// Primera asignacion
				pagosMetodos = pago.getPagoMetodos();
			}else{
				if (pago.getPagoMetodos() != pagosMetodos) pagosMetodos = 'A'; 					
			}						
		}
		
		request.setAttribute( "puedePagar", puedePagar);
		request.setAttribute( "pagosIniciados", pagosIniciados);
		request.setAttribute( "pagosMetodos", Character.toString(pagosMetodos));
		
	}
}

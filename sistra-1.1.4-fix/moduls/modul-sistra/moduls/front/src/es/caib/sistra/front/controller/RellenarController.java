package es.caib.sistra.front.controller;

import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.TramiteFront;

public class RellenarController extends BaseController
{
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		//super.execute( tileContext, request, response, servletContext );
		
		TramiteFront tramite 			= this.getTramiteFront( request );
				
		// Comprobamos si puede haber ficheros a firmar
		// Comprobamos que formularios puede rellenar el usuario según el flujo de tramitación
		// Comprobar que iconos deben mostrarse: Obligatorio (S)-Opcional(N)-Dependiente(D)-Firmar(F)
		String firmarFormularios = "N";		
		HashMap flujoDocumentos = new HashMap();
		HashMap iconos = new HashMap();
		iconos.put("S","false");	
		iconos.put("N","false");	
		iconos.put("D","false");	
		iconos.put("F","false");	
		for (Iterator it=tramite.getFormularios().iterator();it.hasNext();){
			 DocumentoFront doc = (DocumentoFront) it.next();
			 
			 if (doc.isFirmar()) {
				  firmarFormularios = "S";
				  iconos.put("F","true");		
			 }
			 
			 if (!tramite.isFlujoTramitacion() 
					 ||
					(tramite.isFlujoTramitacion() && 
					 doc.getNifFlujo().equals(tramite.getDatosSesion().getNifUsuario())
					)
				){
				 flujoDocumentos.put(doc.getIdentificador(),"true");				 
			 }else{
				 flujoDocumentos.put(doc.getIdentificador(),"false");
			 }
			 
			 switch (doc.getObligatorio()){
				 case DocumentoFront.OBLIGATORIO:
					 	iconos.put("S","true");				 		
				 		break;
				 case DocumentoFront.OPCIONAL:
					 	iconos.put("N","true");
				 		break;
				 case DocumentoFront.DEPENDIENTE:
					 	iconos.put("D","true");
				 		break;				
			 }
			 
			  
		}
		request.setAttribute( "firmarFormularios", firmarFormularios);
		request.setAttribute( "flujoDocumentos", flujoDocumentos);
		request.setAttribute( "iconos", iconos);
		
		// Comprobamos si se ha iniciado pagos
		if (tramite.iniciadoPagos()){
			request.setAttribute( "iniciadoPagos", "true");
		}else{
			request.setAttribute( "iniciadoPagos", "false");
		}
		
		// Comprobamos si el tramite tiene flujo tramitación
		if (tramite.isFlujoTramitacion()){						
			request.setAttribute( "flujoTramitacion", "true");
			request.setAttribute( "flujoTramitacionNifUsuarioActual", tramite.getDatosSesion().getNifUsuario());
		}else{
			request.setAttribute( "flujoTramitacion", "false");
		}
		
		
		
	}
}

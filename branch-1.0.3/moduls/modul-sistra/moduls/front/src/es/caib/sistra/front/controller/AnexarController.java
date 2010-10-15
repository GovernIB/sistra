package es.caib.sistra.front.controller;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.TramiteFront;

/**
 * Controller Paso Anexar
 *
 */
public class AnexarController extends BaseController
{
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		//super.execute( tileContext, request, response, servletContext );
		
		TramiteFront tramite 			= this.getTramiteFront( request );
				
		// Comprobamos:
		//	- si puede haber ficheros a firmar
		//	- maximo instancias en documentos genericos
		//	- si se puede aportar algun documento
		//  - si el usuario actual puede modificar documento
		//  - iconos que deben mostrarse (Obligatorio/Opcional/Firmar/Compulsar/Fotocopia/Presentar telematicamente/Presentar presencialmente)
		String firmarFicheros = "N";
		String debeAportar = "N";
		Hashtable instanciasGenericos = new Hashtable();
		HashMap flujoDocumentos = new HashMap();
		HashMap iconos = new HashMap();
		iconos.put("S","false");	
		iconos.put("N","false");			
		iconos.put("F","false");	
		iconos.put("C","false");
		iconos.put("X","false");
		iconos.put("P","false");
		iconos.put("T","false");
		for (Iterator it=tramite.getAnexos().iterator();it.hasNext();){
			 DocumentoFront doc = ((DocumentoFront) it.next());			 
			 
			 // Si es dependiente no lo tenemos en cuenta y pasamos al siguiente
			 if (doc.getObligatorio() == DocumentoFront.DEPENDIENTE) continue;
			 
			 // Comprobamos si hay ficheros a firmar
			 if (doc.isFirmar()) {
				  firmarFicheros = "S";
				  iconos.put("F","true");		
			 }
			 
			 // Calculamos numero de instancias para genericos
			 if (doc.isAnexoGenerico()){
				 instanciasGenericos.put(doc.getIdentificador(),new Integer(tramite.getAnexoNumeroInstancias(doc.getIdentificador())));
			 }
			 
			 // Comprobamos si debe anexar algun fichero
			 if (doc.getObligatorio() == DocumentoFront.OBLIGATORIO || doc.getObligatorio() == DocumentoFront.OPCIONAL){
				 debeAportar = "S";
			 }
			 			 
			 // Comprobamos si el usuario puede modificar documento (segun el flujo de tramitacion) 	
			 if (	!tramite.isFlujoTramitacion() 
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
			 } 
			 			 
			 if (doc.isAnexoCompulsar()) iconos.put("C","true");
			 if (doc.isAnexoFotocopia()) iconos.put("X","true");	
			 if (doc.isAnexoPresentarTelematicamente())  iconos.put("T","true");
			 if (!doc.isAnexoPresentarTelematicamente()) iconos.put("P","true");			 
				 
		}
		
		if (tramite.isFlujoTramitacion()){
			request.setAttribute( "flujoTramitacionNifUsuarioActual", tramite.getDatosSesion().getNifUsuario());
		}
	
		request.setAttribute( "flujoDocumentos", flujoDocumentos);
		request.setAttribute( "firmarFicheros", firmarFicheros);
		request.setAttribute( "debeAportar", debeAportar);
		request.setAttribute( "instanciasGenericos", instanciasGenericos);
		request.setAttribute( "iconos", iconos);
	}
}

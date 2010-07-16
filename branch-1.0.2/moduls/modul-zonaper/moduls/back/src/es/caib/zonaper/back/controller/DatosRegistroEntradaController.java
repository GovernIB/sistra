package es.caib.zonaper.back.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.util.MessageResources;

import es.caib.util.StringUtil;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.xml.registro.factoria.impl.DireccionCodificada;
import es.caib.zonaper.back.Constants;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.ValorDominio;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.DominiosDelegate;

public class DatosRegistroEntradaController extends BaseController
{
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		DominiosDelegate dominios = DelegateUtil.getDominiosDelegate();
		
		EntradaPreregistro preregistro = ( EntradaPreregistro ) request.getAttribute( "preregistro" );
		
		// Parseo del asiento registral
		FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
		AsientoRegistral asiento 			= factoria.crearAsientoRegistral( new ByteArrayInputStream( consultarDocumentoRDS ( preregistro.getCodigoRdsAsiento(), preregistro.getClaveRdsAsiento() ) ) );
		
		// Obtenemos datos para la pagina
		// 		- Oficina, tipo asunto, idioma, organo destino
		String oficina = request.getParameter( "oficina" );
		oficina = StringUtils.isEmpty( oficina ) ? "" : oficina; 
		String tipoAsunto 				= asiento.getDatosAsunto().getTipoAsunto();
		String idiomaAsunto 			= asiento.getDatosAsunto().getIdiomaAsunto();
		String codigoOrganoDestino 		= asiento.getDatosAsunto().getCodigoOrganoDestino();
		String descripcionOrganoDestino = asiento.getDatosAsunto().getDescripcionOrganoDestino();
		//		- Interesado
		DatosInteresado datosInteresado = obtenerRepresentate( asiento );
		String interesado = "";
		if ( datosInteresado != null )
		{
			interesado = datosInteresado.getIdentificacionInteresado();
		}
		// 		- Procedencia geografica (del asiento preregistro)
		String fueraBaleares = "", municipioBaleares="";
		boolean esBaleares = false;
		if(  datosInteresado != null && datosInteresado.getDireccionCodificada() != null )
		{
			DireccionCodificada direccionCodificada = datosInteresado.getDireccionCodificada();
			String pais 		= direccionCodificada.getPaisOrigen();
			String provincia 	= direccionCodificada.getCodigoProvincia();
			String nombreProvincia = direccionCodificada.getNombreProvincia();
			String municipio 	= direccionCodificada.getCodigoMunicipio();
			String nombreMunicipio = direccionCodificada.getNombreMunicipio(); 
			
			if (Constants.CODIGO_PAIS_ESPANYA.equals(pais)){
				if (Constants.CODIGO_PROVINCIA_BALEARES.equals(provincia)){
					esBaleares = true;
					municipioBaleares = municipio;
				}else{
					fueraBaleares = nombreMunicipio + "(" + nombreProvincia + ")" ;
				}				
			}else{
				fueraBaleares = dominios.obtenerDescripcionPais(pais);
			}
			
		}
						
        // Obtenemos listas de valores (Lista de ValorDominio]
		// 	- Obtenemos oficinas usuario
		List lstOficinas =  dominios.obtenerOficinas(request.getUserPrincipal().getName());
		//	- Obtener tipos de asunto
		List lstTiposAsunto = dominios.obtenerTiposAsunto();
		//  - Obtener municipios baleares
		List lstMunicipiosBaleares = dominios.listarLocalidadesProvincia(Constants.CODIGO_PROVINCIA_BALEARES);
		ValorDominio opcVacio = new ValorDominio();
		opcVacio.setCodigo("");opcVacio.setDescripcion("");
		lstMunicipiosBaleares.add(0,opcVacio);
		//  - Lista de idiomas
		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		List idiomas = new ArrayList();
		ValorDominio vd = new ValorDominio();
		vd.setCodigo("es");
		vd.setDescripcion(resources.getMessage( getLocale( request ), "idioma.castellano"));
		idiomas.add(vd);
		vd = new ValorDominio();
		vd.setCodigo("ca");
		vd.setDescripcion(resources.getMessage( getLocale( request ), "idioma.catalan"));
		idiomas.add(vd);
				
		// Fecha de entrada en registro
		String fechaEntrada = StringUtil.fechaACadena(new Date(),StringUtil.FORMATO_FECHA);
		
		// Establecemos datos en la request
		request.setAttribute( "oficina", oficina );
		request.setAttribute( "tipoAsunto", tipoAsunto );
		request.setAttribute( "idiomaAsunto", idiomaAsunto );
		request.setAttribute( "codigoOrganoDestino", codigoOrganoDestino );
		request.setAttribute( "descripcionOrganoDestino", descripcionOrganoDestino );
		request.setAttribute( "extractoAsunto", asiento.getDatosAsunto().getExtractoAsunto() );
		request.setAttribute( "interesado", interesado );
		request.setAttribute( "esBaleares", new Boolean( esBaleares ) );
		request.setAttribute( "municipioBaleares",  municipioBaleares );
		request.setAttribute( "fueraBaleares",  fueraBaleares );
		request.setAttribute( "oficinas", lstOficinas );
		request.setAttribute( "tiposAsunto", lstTiposAsunto );
		request.setAttribute( "municipiosBaleares", lstMunicipiosBaleares );
		request.setAttribute( "idiomas", idiomas );
		request.setAttribute( "fechaEntrada", fechaEntrada );

	}
		
	
}

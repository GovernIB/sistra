package es.caib.sistra.back.action.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.apache.struts.util.MessageResources;

import es.caib.sistra.model.Documento;
import es.caib.sistra.model.DocumentoNivel;
import es.caib.sistra.model.OrganoResponsable;
import es.caib.sistra.model.Tramite;
import es.caib.sistra.model.TramiteNivel;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.ConfiguracionDelegate;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DocumentoDelegate;
import es.caib.sistra.persistence.delegate.DocumentoNivelDelegate;
import es.caib.sistra.persistence.delegate.GruposDelegate;
import es.caib.sistra.persistence.delegate.OrganoResponsableDelegate;
import es.caib.sistra.persistence.delegate.TramiteDelegate;
import es.caib.sistra.persistence.delegate.TramiteNivelDelegate;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;

public class FuncsNodesController implements Controller
{
    protected static Log log = LogFactory.getLog(FuncsNodesController.class);
    protected static final String ACTION_ALTA_TRAMITE = "/back/tramite/alta.do";
    protected static final String ACTION_EDICION_TRAMITE = "/back/tramite/seleccion.do";
    protected static final String ACTION_ALTA_VERSION = "/back/tramiteVersion/alta.do";
    protected static final String EDICION_VERSION = "/back/tramiteVersion/seleccion.do";
    protected static final String EDICION_ESPECIFICACIONES_VERSION = "/back/especificacionesGenericas/seleccion.do";
    protected static final String ALTA_NIVEL = "/back/tramiteNivel/alta.do";
    protected static final String EDICION_NIVEL = "/back/tramiteNivel/seleccion.do";
    protected static final String ALTA_DOCUMENTO = "/back/documento/alta.do";
    protected static final String EDICION_DOCUMENTO = "/back/documento/seleccion.do";
    protected static final String ALTA_DOCUMENTO_NIVEL= "/back/documentoNivel/alta.do";
    protected static final String EDICION_DOCUMENTO_NIVEL = "/back/documentoNivel/seleccion.do";
    protected static final String LISTA_MENSAJES = "/back/mensajeTramite/lista.do";
    protected static final String EXPORTAR_VERSION= "/generar/xml.do";
    protected static final String IMPORTAR_VERSION= "/back/tramiteVersion/importar.do";
    
    protected static final String NIVEL_AUTH_ANONIMO = "A";
    protected static final String NIVEL_AUTH_USUARIO = "U";
    protected static final String NIVEL_AUTH_CERTIFICADO = "C";
    
    protected static final char TIPO_DOCUMENTO_PAGO = 'P';
    protected static final char TIPO_DOCUMENTO_ANEXO = 'A';
    protected static final char TIPO_DOCUMENTO_FORMULARIO = 'F';
    
    
    public void perform(ComponentContext arg0, HttpServletRequest request, HttpServletResponse arg2, ServletContext arg3) throws ServletException, IOException
    {
    	MessageResources resources = this.getResources(request);
    	
		String parentId = "foldersTree"; 
    	ArrayList arlNodos = new ArrayList();
    	Nodo nodoRaiz = new Nodo();
    	nodoRaiz.setDescripcion( resources.getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.repositorioTramites") );
    	nodoRaiz.setId( parentId );
    	arlNodos.add( nodoRaiz );
    	
    	try 
		{
	    	OrganoResponsableDelegate organoDelegate = DelegateUtil.getOrganoResponsableDelegate();

	    	/*
	    	List lstOrganos = organoDelegate.listarOrganoResponsables();

	    	if ( lstOrganos != null )
	    	{
	    		for ( Iterator it = ( Iterator ) lstOrganos.iterator(); it.hasNext(); )
	    		{
	    			OrganoResponsable organo = ( OrganoResponsable ) it.next();
    		*/
	    			String strCodigoOrgano = request.getParameter( "codigo" );
	    			Long codigoOrgano = null;
	    			if ( strCodigoOrgano == null || "null".equals( strCodigoOrgano ))
	    			{
	    				codigoOrgano = ( Long ) request.getSession().getAttribute( "codigoOrgano" ); 
	    			}
	    			else
	    			{
	    				codigoOrgano = new Long( strCodigoOrgano );
	    				request.getSession().setAttribute( "codigoOrgano", codigoOrgano );
	    			}
	    			OrganoResponsable organo = organoDelegate.obtenerOrganoResponsable( codigoOrgano );
	    			
	    			if ( organo != null )
	    			{
		    			Nodo nodo = new Nodo();
		    			nodo.setId( obtenerIdNodoOrgano( organo ) );
		    			nodo.setParentId( parentId );
		    			nodo.setFolder( true );
		    			
		    			nodo.setDescripcionLink( resources.getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.altaTramite") );
		    			nodo.setDescripcion( organo.getDescripcion() );
		    			nodo.getParameters().put("idOrgano",String.valueOf( organo.getCodigo() ));
		    			nodo.setAction( ACTION_ALTA_TRAMITE );
		    			nodo.setTipo(Nodo.ORGANO_RESPONSABLE);
		    			arlNodos.add( nodo ); 
		    			
		    			iterateTramites( nodo, arlNodos, organo, request );
	    			}
	    	/*		
	    		}
	    	}
	    	*/
	    	
	    	request.setAttribute( "nodosMenu", arlNodos );
	    	
		}
		catch( DelegateException exc )
		{
			throw new ServletException(exc);
		}
	}
    
    		
    private void anadirTramite(Nodo nodoPadre, ArrayList arlNodos, HttpServletRequest request, Tramite tramite) throws DelegateException{
    		String nodoId = obtenerId( "tramite", tramite.getCodigo() ); 
    		
			Nodo nodo1 = new Nodo();
			nodo1.setId( nodoId );
			nodo1.setDescripcion( tramite.getIdentificador() );
			nodo1.setParentId( nodoPadre.getId() );
			nodo1.setFolder( true );
			nodo1.setTipo(Nodo.TRAMITE);
			
			Nodo nodo2 = new Nodo();
			nodo2.setFolder( false );
			nodo2.setId(obtenerId( "definicionTramite" , tramite.getCodigo()));
			nodo2.setParentId( nodoId );
			nodo2.setDescripcion( getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.definicionTramite") );			
			nodo2.getParameters().put("codigo",String.valueOf( tramite.getCodigo() ));
			nodo2.setAction( ACTION_EDICION_TRAMITE );			
			

			Nodo nodo21 = new Nodo();
			nodo21.setFolder( false );
			nodo21.setId(obtenerId( "importarTramite" , tramite.getCodigo()));
			nodo21.setParentId( nodoId );
			nodo21.setDescripcion( getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.importarVersion") );			
			nodo21.getParameters().put( "codigoTramite" , String.valueOf( tramite.getCodigo() ));
			nodo21.setAction( IMPORTAR_VERSION );
			
			Nodo nodo3 = new Nodo();
			nodo3.setFolder( true );
			nodo3.setId( obtenerId( "versionesTramite" , tramite.getCodigo()  ) );
			nodo3.setParentId( nodoId );
			nodo3.setDescripcion( getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.versiones") );
			nodo3.setAction( ACTION_ALTA_VERSION );
			nodo3.getParameters().put("idTramite",String.valueOf ( tramite.getCodigo() ));
			nodo3.setDescripcionLink( getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.altaVersion") );
									
			arlNodos.add( nodo1 );
			arlNodos.add( nodo2 );
			arlNodos.add( nodo21 );
			arlNodos.add( nodo3 );
    		
    		Set versiones = tramite.getVersiones();
    		iterateVersionesTramite( nodo3, arlNodos, tramite, request );
    	}
    
    private void iterateTramites( Nodo nodoPadre, ArrayList arlNodos, OrganoResponsable organo, HttpServletRequest request ) throws DelegateException
    {
       	if(StringUtils.isNotEmpty(request.getUserPrincipal().getName())){
       		try{
      			GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
      			TramiteDelegate tramiteDelegate = DelegateUtil.getTramiteDelegate();
	    		Set tramites = tramiteDelegate.listarTramitesOrganoResponsable( organo.getCodigo() );
		    	for ( Iterator it = ( Iterator ) tramites.iterator(); it.hasNext(); )
		    	{
		    		Tramite tramite = ( Tramite ) it.next();
		    		if( Boolean.valueOf( DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("habilitar.permisos")).booleanValue()){
		    			if(gruposDelegate.existeUsuarioByGruposTramite(request.getUserPrincipal().getName(),new Long(tramite.getCodigo())) || gruposDelegate.existeUsuarioByTramite(request.getUserPrincipal().getName(),new Long(tramite.getCodigo()))){
		    				anadirTramite(nodoPadre, arlNodos, request, tramite);
		    			}
		    		}else{
		    			anadirTramite(nodoPadre, arlNodos, request, tramite);
		    		}
   				}
   	        }catch(DelegateException e){
   	        	
   	        }
       	}    	    	
    }
    
    private void iterateVersionesTramite( Nodo nodoPadre, ArrayList arlNodos, Tramite tramite, HttpServletRequest request ) throws DelegateException
    {
    	TramiteVersionDelegate tramiteVersionDelegate = DelegateUtil.getTramiteVersionDelegate();
    	Set versiones = tramiteVersionDelegate.listarTramiteVersiones( tramite.getCodigo() );
    	for ( Iterator it = ( Iterator ) versiones.iterator(); it.hasNext(); )
    	{
    		TramiteVersion version = ( TramiteVersion ) it.next();
    		String nodoVersionId = obtenerId( "version", version.getCodigo() );
    		
    		Nodo nodoVersion = new Nodo();
    		nodoVersion.setFolder( true );
    		nodoVersion.setId( nodoVersionId );
    		nodoVersion.setParentId( nodoPadre.getId() );
    		nodoVersion.setDescripcion( getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.version") + " " + version.getVersion() );
    		nodoVersion.setTipo(Nodo.TRAMITE_VERSION);
    		
    		Nodo nodoDefinicionVersion = new Nodo();
    		nodoDefinicionVersion.setFolder( false );
    		nodoDefinicionVersion.setId( obtenerId( "definicionVersion", version.getCodigo() ) );
    		nodoDefinicionVersion.setParentId( nodoVersion.getId() );
    		nodoDefinicionVersion.setDescripcion( getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.definicionVersion") );
    		nodoDefinicionVersion.setAction( EDICION_VERSION );
    		nodoDefinicionVersion.getParameters().put("codigo",String.valueOf( version.getCodigo() ) );
    		
    		Nodo nodoMensajes = new Nodo();
    		nodoMensajes.setFolder( false ); 
    		nodoMensajes.setId( obtenerId( "mensajesValidaciones", version.getCodigo() ) );
    		nodoMensajes.setParentId( nodoVersion.getId() );
    		nodoMensajes.setDescripcion( getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.mensajesValidaciones"));
    		nodoMensajes.setAction( LISTA_MENSAJES );
    		nodoMensajes.getParameters().put("idTramiteVersion",String.valueOf( version.getCodigo() ) );
    		
    		Nodo nodoEspecificacionesGenericas = new Nodo();
    		nodoEspecificacionesGenericas.setFolder( false ); 
    		nodoEspecificacionesGenericas.setId( obtenerId( "especificacionesGenericas", version.getCodigo() ) );
    		nodoEspecificacionesGenericas.setParentId( nodoVersion.getId() );
    		nodoEspecificacionesGenericas.setDescripcion( getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.especificacionesGenericas") );
    		nodoEspecificacionesGenericas.setAction( EDICION_ESPECIFICACIONES_VERSION );
    		nodoEspecificacionesGenericas.getParameters().put( "codigo" ,String.valueOf( version.getEspecificaciones().getCodigo() ) );    					
			
    		/*
    		Nodo nodoExportacion = new Nodo();
    		nodoExportacion.setFolder( false );
    		nodoExportacion.setId( obtenerId( "exportar", version.getCodigo() ) );
    		nodoExportacion.setParentId( nodoVersion.getId() );
    		nodoExportacion.setDescripcion( "Exportar versión" );
    		nodoExportacion.setAction( EXPORTAR_VERSION );
    		nodoExportacion.setParamId( "codigo" );
    		nodoExportacion.setParamValue( String.valueOf( version.getCodigo() ) );
    		*/
    		
    		Nodo nodoEspecificacionesNivel = new Nodo();
    		nodoEspecificacionesNivel.setFolder( true );
    		nodoEspecificacionesNivel.setId( obtenerId ( "especificacionesNivel", version.getCodigo() ) );
    		nodoEspecificacionesNivel.setParentId( nodoVersion.getId() );
    		nodoEspecificacionesNivel.setDescripcion( getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.especificacionesNivel") );
    		nodoEspecificacionesNivel.setAction( ALTA_NIVEL );
    		nodoEspecificacionesNivel.getParameters().put( "idTramiteVersion" ,String.valueOf( version.getCodigo() ) );
    		nodoEspecificacionesNivel.setDescripcionLink( getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.altaNivel") );
    		
    		
    		Nodo nodoDocumentos = new Nodo();
    		nodoDocumentos.setFolder( true );
    		nodoDocumentos.setId( obtenerId ( "documentos", version.getCodigo() ) );
    		nodoDocumentos.setParentId( nodoVersion.getId() );
    		nodoDocumentos.setDescripcion( getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.documentos") );
    		nodoDocumentos.setAction( ALTA_DOCUMENTO );
    		nodoDocumentos.getParameters().put( "idTramiteVersion" , String.valueOf( version.getCodigo() ) );
    		nodoDocumentos.setDescripcionLink( getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.altaDocumento") );
    		    		
    		arlNodos.add( nodoVersion );
    		arlNodos.add( nodoDefinicionVersion );
    		arlNodos.add( nodoMensajes );
    		arlNodos.add( nodoEspecificacionesGenericas );
    		//arlNodos.add( nodoExportacion );
    		arlNodos.add( nodoEspecificacionesNivel );
    		iterateNivelesAutenticacionVersion( nodoEspecificacionesNivel, arlNodos, version, request );
    		arlNodos.add( nodoDocumentos );
    		iterateDocumentosVersion( nodoDocumentos, arlNodos, version, request );    		
    		
    		
    		

    		//iterateMensajesVersion( version );
    		//iterateJustificanteVersion( version );
    	}
    }
     
    private void iterateNivelesAutenticacionVersion( Nodo nodoPadre, ArrayList arlNodos, TramiteVersion version,HttpServletRequest request ) throws DelegateException
    {
    	TramiteNivelDelegate nivelAutenticacionVersionDelegate = DelegateUtil.getTramiteNivelDelegate();
    	Set nivelesAutenticacionVersion = nivelAutenticacionVersionDelegate.listarTramiteNiveles( version.getCodigo() );
    	for ( Iterator it = ( Iterator ) nivelesAutenticacionVersion.iterator(); it.hasNext(); )
    	{
    		TramiteNivel nivelAutenticacionVersion = ( TramiteNivel ) it.next();
    		Nodo nodoNivelAutenticacion = new Nodo();
    		nodoNivelAutenticacion.setFolder( false );
    		nodoNivelAutenticacion.setId( obtenerId( "nivelAuth", nivelAutenticacionVersion.getCodigo() ) );
    		nodoNivelAutenticacion.setParentId( nodoPadre.getId() );
    		nodoNivelAutenticacion.setDescripcion( this.obtenerDescripcionNiveles( nivelAutenticacionVersion.getNivelAutenticacion(), request ));
    		nodoNivelAutenticacion.setAction( EDICION_NIVEL );
    		nodoNivelAutenticacion.getParameters().put( "codigo" , String.valueOf( nivelAutenticacionVersion.getCodigo()  ));
    		
    		arlNodos.add( nodoNivelAutenticacion );
    	}
    }
    
    private void iterateDocumentosVersion( Nodo nodoPadre, ArrayList arlNodos, TramiteVersion version,HttpServletRequest request ) throws DelegateException
    {
    	DocumentoDelegate documentoDelegate = DelegateUtil.getDocumentoDelegate();
    	Set documentos = documentoDelegate.listarDocumentos( version.getCodigo() );
    	for ( Iterator it = ( Iterator ) documentos.iterator(); it.hasNext(); )
    	{	
    		Documento documento = ( Documento ) it.next();
    		
    		Nodo nodoDocumento = new Nodo();
    		nodoDocumento.setFolder( true );
    		nodoDocumento.setId( obtenerId( "documento", documento.getCodigo() ) );
    		nodoDocumento.setParentId( nodoPadre.getId() );
    		nodoDocumento.setDescripcion( obtenerDescripcionDocumento( documento, request ) );
    		switch (documento.getTipo()){
    			case Documento.TIPO_ANEXO:
    				nodoDocumento.setTipo(Nodo.DOCUMENTO_ANEXO);
    				break;
    			case Documento.TIPO_FORMULARIO:
    				nodoDocumento.setTipo(Nodo.DOCUMENTO_FORMULARIO);
    				break;
    			case Documento.TIPO_PAGO:
    				nodoDocumento.setTipo(Nodo.DOCUMENTO_PAGO);
    				break;
    		}
    		   		
    		Nodo nodoDefinicionDocumento = new Nodo();
    		nodoDefinicionDocumento.setFolder( false );
    		nodoDefinicionDocumento.setId( obtenerId( "definicionDocumento", documento.getCodigo() ));
    		nodoDefinicionDocumento.setParentId( nodoDocumento.getId() );
    		nodoDefinicionDocumento.setDescripcion( getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.definicionDocumento") );
    		nodoDefinicionDocumento.setAction( EDICION_DOCUMENTO );
    		nodoDefinicionDocumento.getParameters().put( "codigo",String.valueOf ( documento.getCodigo() ));
    		
    		Nodo nodoEspecificacionesNivel = new Nodo();
    		nodoEspecificacionesNivel.setFolder( true );
    		nodoEspecificacionesNivel.setId( obtenerId( "especificacionesNivelDocumento", documento.getCodigo()) );
    		nodoEspecificacionesNivel.setParentId( nodoDocumento.getId() );
    		nodoEspecificacionesNivel.setDescripcion( getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.especificacionesNivel") );
    		nodoEspecificacionesNivel.setAction( ALTA_DOCUMENTO_NIVEL );
    		nodoEspecificacionesNivel.getParameters().put( "idDocumento" , String.valueOf ( documento.getCodigo() ));
    		nodoEspecificacionesNivel.setDescripcionLink( getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.altaNivel") );
    		
    		arlNodos.add( nodoDocumento );
    		arlNodos.add( nodoDefinicionDocumento );
    		arlNodos.add( nodoEspecificacionesNivel );
    		
    		iterateEspecificacionesNivelAutenticacionDocumento( nodoEspecificacionesNivel, arlNodos, documento, request );
    	}
    	
    }
    
    private void iterateEspecificacionesNivelAutenticacionDocumento( Nodo nodoPadre, ArrayList arlNodos, Documento documento,HttpServletRequest request ) throws DelegateException
    {
    	DocumentoNivelDelegate documentoNivelDelegate = DelegateUtil.getDocumentoNivelDelegate();
    	Set especificionesNivelDocumento = documentoNivelDelegate.listarDocumentoNiveles( documento.getCodigo() );
    	for ( Iterator it = ( Iterator ) especificionesNivelDocumento.iterator(); it.hasNext(); )
    	{
    		DocumentoNivel documentoNivel = ( DocumentoNivel ) it.next();
    		
    		Nodo nodoDocumentoNivel = new Nodo();
    		nodoDocumentoNivel.setFolder( false );
    		nodoDocumentoNivel.setId( obtenerId( "nivelAuthDocumento", documentoNivel.getCodigo() ) );
    		nodoDocumentoNivel.setParentId( nodoPadre.getId() );
    		nodoDocumentoNivel.setDescripcion( this.obtenerDescripcionNiveles( documentoNivel.getNivelAutenticacion(), request ));
    		nodoDocumentoNivel.setAction( EDICION_DOCUMENTO_NIVEL );
    		nodoDocumentoNivel.getParameters().put( "codigo" , String.valueOf( documentoNivel.getCodigo()  ));
    		arlNodos.add( nodoDocumentoNivel );
    		
    	}
    }
    
    private String obtenerIdNodoOrgano( OrganoResponsable organo )
    {
    	return obtenerId( "organo", organo.getCodigo() ); 
    }
    
    private String obtenerId( String kind, Long codigo )
    {
    	return kind + String.valueOf( codigo) ;
    }
    
    private String obtenerDescripcionNiveles( String cadenaNivelesAutenticacion , HttpServletRequest request)
    {
    	String strReturn = "";
    	boolean varios = false;
    	String separator = " / ";
    	if ( cadenaNivelesAutenticacion.indexOf( NIVEL_AUTH_ANONIMO ) != -1 )
    	{
    		String desc = getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.anonimo");
    		strReturn += varios ? separator + desc : desc;
    		varios = true;
    	}
    	if ( cadenaNivelesAutenticacion.indexOf( NIVEL_AUTH_USUARIO) != -1 )
    	{
    		String desc = getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.usuario");
    		strReturn += varios ? separator + desc : desc;
    		varios = true;
    	}
    	if ( cadenaNivelesAutenticacion.indexOf( NIVEL_AUTH_CERTIFICADO) != -1 )
    	{
    		String desc = getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.certificado");
    		strReturn += varios ? separator + desc : desc;
    		varios = true;
    	}
    	return strReturn;
    }
    
    private String obtenerDescripcionDocumento( Documento documento, HttpServletRequest request )
    {
    	String strReturn = null;
    	switch ( documento.getTipo() )
    	{
    		case TIPO_DOCUMENTO_FORMULARIO :
    			strReturn = getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.formulario");
    		break;
    		case TIPO_DOCUMENTO_ANEXO :
    			strReturn = getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.anexo");
    		break;
    		case TIPO_DOCUMENTO_PAGO :
    			strReturn = getResources(request).getMessage((Locale) request.getSession().getAttribute(Globals.LOCALE_KEY),"nodos.pago");
    		break;
    			
    	}
    	return strReturn + ": " +documento.getIdentificador();
    	
    }
    
    public MessageResources getResources( HttpServletRequest request )
    {
     return ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    }
    
}

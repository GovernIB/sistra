<%@ page import="es.caib.sistra.persistence.delegate.*,es.caib.sistra.model.*" %>
<%
	
	// ----- PARTICULARIZACION DE CODIGO PARA LOGIN TRAMITACION (LO DEMAS IGUAL PARA TRAMITACION Y ZONA PERSONAL) ---------------------

	// Comprobamos si intentamos acceder a un trámite y pedimos autenticación requerida	
	String niveles="";
	String tramite=null;
	String modelo=null;
	int version=-1;	
	String textoAtencion="";
	String modo = null;
		
	try{		
		
		if (!esLoginClave) {
			String savedRequestUrl = savedRequest.getRequestURI();
			if (savedRequest.getQueryString() != null) {
				savedRequestUrl += "?" + savedRequest.getQueryString();
			}
			
			// Si intenta acceder a un trámite buscamos modelo y versión
			String in = request.getContextPath() + "/protected/init.do?";		
			if (!savedRequestUrl.startsWith(in) && !esLoginClave) throw new Exception("Punto de entrada no válido: " + savedRequestUrl);
				
				String params = savedRequestUrl.substring(in.length());
				savedRequestUrl = params;
				
				StringTokenizer st = new StringTokenizer(savedRequestUrl,"&");
				String element;
				while (st.hasMoreElements()){
					element = (String) st.nextElement();		
					if (element.startsWith("language=")){
						language = element.substring(("language=").length());
					}else if (element.startsWith("modelo=")){
						modelo = element.substring(("modelo=").length());					
					}else if (element.startsWith("version=")){
						version = Integer.parseInt(element.substring(("version=").length()));					
					}else if (element.startsWith("autenticacion="))
					{
						modo = element.substring(("autenticacion=").length());
					}
				}
				
				if (modelo == null || version == -1) throw new Exception("Faltan parámetros");
	
				// Obtenemos descripcion y metodos de autenticacion permitidos
				TramiteVersionDelegate delegate = DelegateUtil.getTramiteVersionDelegate();			
				TramiteVersion tv = delegate.obtenerTramiteVersion(modelo,version);		
				tramite = ((TraTramite) (tv.getTramite().getTraduccion(language))).getDescripcion();
				for (Iterator it = tv.getNiveles().iterator();it.hasNext();){
					TramiteNivel tn = (TramiteNivel) it.next();
	 				niveles = niveles + tn.getNivelAutenticacion();
				}
				
				
	//			System.out.println( "CLM Language: [" + language + "]" + new Locale(language) );
	
				if ( "A".equals( niveles ) || (niveles.indexOf("A") >= 0 && tv.getAnonimoDefecto() == 'S'))
				{
					response.sendRedirect( request.getContextPath()+ "/protected/j_security_check?j_username=nobody&j_password=nobody" );
					return;
				}
				
				
				// Filtramos niveles acceso
				if (modo != null){
					String nivelesModo = "";
			 		if (niveles.indexOf("C") >= 0 && modo.indexOf("C") >= 0 ) nivelesModo += "C";
			 		if (niveles.indexOf("U") >= 0 && modo.indexOf("U") >= 0 ) nivelesModo += "U";
			 		if (niveles.indexOf("A") >= 0 && modo.indexOf("A") >= 0 ) nivelesModo += "A";
			 		niveles = nivelesModo;
				}
					
				textoAtencion = tramite;
		}
		
		session.setAttribute(Globals.LOCALE_KEY, new Locale(language));
		
	}catch(Exception ex){
		ex.printStackTrace();
		out.println("ERROR AUTENTICACION: " + ex.toString());
		return;
	}
	
	
	// ----- FIN PARTICULARIZACION DE CODIGO PARA LOGIN (LO DEMAS IGUAL PARA TRAMITACION Y ZONA PERSONAL) ---------------------
%>
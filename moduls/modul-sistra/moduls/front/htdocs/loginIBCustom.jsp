<%@ page import="es.caib.sistra.persistence.delegate.*,es.caib.sistra.model.*" %>
<%
	// ----- PARTICULARIZACION DE CODIGO PARA LOGIN (LO DEMAS IGUAL PARA TRAMITACION Y ZONA PERSONAL) ---------------------
	String niveles = "CLAVE_CERTIFICADO;CLAVE_PIN;CLAVE_PERMANENTE;ANONIMO;CLIENTCERT";
	String textoAtencion="";
	String modo = "";

	try
	{

		if (!esLoginClave) {
			String savedRequestUrl = urlSavedRequest;
			if (queryStringSavedRequest != null) {
				savedRequestUrl += "?" + queryStringSavedRequest;
			}

			// Comprobamos que el punto de entrada sea v�lido
			String in = request.getContextPath() + "/protected/init.do";
			if (!savedRequestUrl.startsWith(in)) throw new Exception("Punto de entrada no v�lido:" + savedRequestUrl);

			if ( savedRequestUrl.indexOf( "?" ) > 0 )
			{
				in = in + "?";
				String params = savedRequestUrl.substring(in.length());
				savedRequestUrl = params;

				StringTokenizer st = new StringTokenizer(savedRequestUrl,"&");
				String element;
				while (st.hasMoreElements())
				{
					element = (String) st.nextElement();
					if (element.startsWith("language="))
					{
						language = element.substring(("language=").length());
					}
					else if (element.startsWith("lang="))
					{
						language = element.substring(("lang=").length());
					}
					else if (element.startsWith("autenticacion="))
					{
						modo = element.substring(("autenticacion=").length());
					}
				}
			}

			// Obtenemos descripcion y metodos de autenticacion permitidos
			TramiteVersionDelegate delegate = DelegateUtil.getTramiteVersionDelegate();
			TramiteVersion tv = delegate.obtenerTramiteVersion(modelo,version);
			tramite = ((TraTramite) (tv.getTramite().getTraduccion(language))).getDescripcion();
			String nivelesTramite = "";
			for (Iterator it = tv.getNiveles().iterator();it.hasNext();){
				TramiteNivel tn = (TramiteNivel) it.next();
				nivelesTramite = nivelesTramite + tn.getNivelAutenticacion();
			}

			// Si se establece modo, no se hace caso de los niveles del tramite
			String filtroNiveles = null;
			if (modo.length() > 0){
				filtroNiveles = modo;
			} else {
				filtroNiveles = nivelesTramite;
			}

			// Filtramos niveles acceso
			String nivelesModo = "";
	 		if (niveles.indexOf("CLAVE_CERTIFICADO") >= 0 && (filtroNiveles.indexOf("C") >= 0  )) nivelesModo += "CLAVE_CERTIFICADO;";
	 		if (niveles.indexOf("CLIENTCERT") >= 0 && filtroNiveles.indexOf("C") >= 0 ) nivelesModo += "CLIENTCERT;";
	 		if (niveles.indexOf("CLAVE_PIN;CLAVE_PERMANENTE") >= 0 && filtroNiveles.indexOf("U") >= 0 ) nivelesModo += "CLAVE_PIN;CLAVE_PERMANENTE;";
	 		if (niveles.indexOf("ANONIMO") >= 0 && filtroNiveles.indexOf("A") >= 0 ) nivelesModo += "ANONIMO";
	 		if (nivelesModo.endsWith(";")) {
				nivelesModo = nivelesModo.substring(0, nivelesModo.length() - 1);
			}
	 		niveles = nivelesModo;

		}


		session.setAttribute(Globals.LOCALE_KEY, new Locale(language));
		//System.out.println( "CLM Language: [" + language + "]" + new Locale(language) );

	}catch(Exception ex)
	{
		ex.printStackTrace();
		out.println("ERROR AUTENTICACION: " + ex.toString());
		return;
	}

	// ----- FIN PARTICULARIZACION DE CODIGO PARA LOGIN (LO DEMAS IGUAL PARA TRAMITACION Y ZONA PERSONAL) ---------------------
%>

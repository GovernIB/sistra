<%
	// ----- PARTICULARIZACION DE CODIGO PARA LOGIN (LO DEMAS IGUAL PARA TRAMITACION Y ZONA PERSONAL) ---------------------	
	String niveles = "CUA";
	String textoAtencion="";
	String modo = null;
	
	try
	{				
		
		if (!esLoginClave) {
			String savedRequestUrl = savedRequest.getRequestURI();
			if (savedRequest.getQueryString() != null) {
				savedRequestUrl += "?" + savedRequest.getQueryString();
			}
			
			// Comprobamos que el punto de entrada sea válido
			String in = request.getContextPath() + "/protected/init.do";
			if (!savedRequestUrl.startsWith(in)) throw new Exception("Punto de entrada no válido:" + savedRequestUrl);
			
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
			
			// Filtramos niveles acceso
			if (modo != null){
				String nivelesModo = "";
		 		if (niveles.indexOf("C") >= 0 && modo.indexOf("C") >= 0 ) nivelesModo += "C";
		 		if (niveles.indexOf("U") >= 0 && modo.indexOf("U") >= 0 ) nivelesModo += "U";
		 		if (niveles.indexOf("A") >= 0 && modo.indexOf("A") >= 0 ) nivelesModo += "A";
		 		niveles = nivelesModo;
			}
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

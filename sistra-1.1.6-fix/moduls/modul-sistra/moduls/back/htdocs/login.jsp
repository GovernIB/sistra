<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ page contentType="text/html; charset=ISO-8859-1" import="java.util.*, org.apache.struts.Globals" %>
<bean:define id="es" value="es" />
<bean:define id="ca" value="ca" />
<%
	String language = "ca";	

	// Comprobamos si intentamos acceder a un trámite y pedimos autenticación requerida
	try
	{	
		// Accedemos a request destino
		String savedRequest = (String) request.getAttribute("savedrequest");
		
		// Si intenta acceder a un trámite buscamos modelo y versión
//		String in = request.getContextPath() + "/init.do";						
		
		if ( savedRequest.indexOf( "?" ) > 0 )
		{
			savedRequest = savedRequest.substring(savedRequest.indexOf( "?" ) + 1);
			StringTokenizer st = new StringTokenizer(savedRequest,"&");
			String element;
			while (st.hasMoreElements())
			{	
				element = (String) st.nextElement();
				System.out.println( "elemento " + element);
				if (element.startsWith("language="))
				{
					language = element.substring(("language=").length());
				}
			}
		}
			
			session.setAttribute(Globals.LOCALE_KEY, new Locale(language));
			System.out.println( "CLM Language: [" + language + "]" + new Locale(language) );
	
	}catch(Exception ex)
	{
		ex.printStackTrace();
		out.println("ERROR AUTENTICACION: " + ex.toString());
		return;
	}
%>
<bean:define id="languageBean" value="<%= language %>" />
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
   <title><bean:message key="login.titulo"/></title>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />   
   <link href="css/styleA.css" rel="stylesheet" type="text/css" />
</head>
<body class="ventana">
<center>
<h1><bean:message key="login.titulo" /></h1>
<br />
	<center> 
	    <form method="post" action="j_security_check">
	        <table class="marc">
	        <tr>
	            <td class="label"><bean:message key="login.user" /></td>
	            <td class="inputLargo"><input type="text" name="j_username" maxlength="256" tabindex="1" value="" class="textLargo" /></td>
	        </tr>
	        <tr>
	            <td class="label"><bean:message key="login.pwd" /></td>
	            <td class="inputLargo"><input type="password" name="j_password" maxlength="256" tabindex="2" value="" class="textLargo" /></td>
	        </tr>
	        </table>
	        <br />
	        <table class="nomarc">
	        <tr>
	            <td align="center">
	                <input type="submit" name="submit" value="Entrar" class="button" />
	                <input type="reset" value="Reiniciar" class="button" />
	            </td>
	        </tr>
	        </table>
	    </form>       
	</center>

</body>
</html>

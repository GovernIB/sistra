<%@ page contentType="text/html; charset=ISO-8859-1"%>
<%@ page import="java.util.*, org.apache.struts.Globals, es.caib.util.ConvertUtil, org.apache.commons.httpclient.*, es.caib.sistra.front.json.JSONObject, org.apache.commons.httpclient.methods.*, java.io.FileInputStream,es.caib.sistra.plugins.PluginFactory, es.caib.sistra.plugins.login.PluginLoginIntf, java.lang.reflect.Method" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%

	// Parametros login clave
	Object savedRequest = null;
	boolean esLoginClave = false;
	boolean esError = false;
	String ticketClave = null;
	String urlCallback = null;
	String metodos = null;
	String urlClave = null;
	String urlDestino = null;
	String language = null;

	// Request origen 
	savedRequest = session.getAttribute("savedrequest");
	
	// Configuracion organismo
	es.caib.zonaper.persistence.delegate.ConfiguracionDelegate delegateF = es.caib.zonaper.persistence.delegate.DelegateUtil.getConfiguracionDelegate();
	java.util.Properties configProperties =  delegateF.obtenerConfiguracion();
	String urlSistra = configProperties.getProperty("sistra.url");
	String contextoRaiz = configProperties.getProperty("sistra.contextoRaiz.front");
	String entidad = (String) request.getParameter("entidad");
	es.caib.zonaper.model.OrganismoInfo infoOrg = delegateF.obtenerOrganismoInfo();
	if (entidad != null) {
		infoOrg = delegateF.obtenerOrganismoInfo(entidad);
	}else{
		entidad = infoOrg.getEntidadDefecto();
	}

	// Configuracion plugin login Clave: obtener contexto login
	PluginLoginIntf plg = PluginFactory.getInstance().getPluginLogin();
	Method method = plg.getClass().getDeclaredMethod("obtenerContextoAutenticadorClave", null);
	Method methodUser = plg.getClass().getDeclaredMethod("obtenerUsuarioLoginIB", null);
	Method methodPass = plg.getClass().getDeclaredMethod("obtenerPasswordLoginIB", null);
	Object result = method.invoke(plg, null);
	Object resultUser = methodUser.invoke(plg, null);
	Object resultPass = methodPass.invoke(plg, null);
	String contextAutenticadorClave =  (String) result;
	String username = (String) resultUser;
	String password = (String) resultPass;
	

	// Idioma
	language = request.getParameter("language");
	if (language == null) {
		language = request.getParameter("lang");
	}
	if (language == null) {
		language = "es";
	}
	request.setAttribute("lang", language);
	
	
	// Obtenemos url origen y query string
	Method methodUrlOrigen = savedRequest.getClass().getDeclaredMethod("getRequestURI", null);
	String urlSavedRequest =  (String) methodUrlOrigen.invoke(savedRequest, null);
	Method methodQueryString = savedRequest.getClass().getDeclaredMethod("getQueryString", null);
	String queryStringSavedRequest =  (String) methodQueryString.invoke(savedRequest, null);
	
		
	// Indica si es login despues de pasar por clave
	esLoginClave = urlSavedRequest.endsWith("/protected/redireccionLoginIB.jsp");
	
	// Indica si es redirección por login erróneo
	if (request.getParameter("error") != null){
		esError = Boolean.parseBoolean(request.getParameter("error"));
	}
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><%=infoOrg.getNombre()%></title>
<link href="<%=infoOrg.getUrlLoginCssCustom()%>" rel="stylesheet" type="text/css" />
<script type="text/javascript">
<!--
function loginClave()
{
	document.getElementById('loginClave').submit();
}
-->
</script>

<!-- DETECCION NAVEGADOR (Compatibles: IE >=6 , FireFox >= 1.5)-->
<script type="text/javascript">
<!--

function checkIt(string)
{
	place = detect.indexOf(string) + 1;
	thestring = string;
	return place;
}

var detect = navigator.userAgent.toLowerCase();
var OS,browser,version,total,thestring,aux,posDecimal;

if (checkIt('konqueror'))
{
	browser = "Konqueror";
	OS = "Linux";
}
else if (checkIt('safari')) browser = "Safari";
else if (checkIt('omniweb')) browser = "OmniWeb";
else if (checkIt('opera')) browser = "Opera";
else if (checkIt('webtv')) browser = "WebTV";
else if (checkIt('icab')) browser = "iCab";
else if (checkIt('msie')) {browser = "Internet Explorer"; thestring = "msie";}
else if (checkIt('firefox')) {browser = "Firefox"; thestring = "firefox";}
else if (!checkIt('compatible'))
{
	browser = "Netscape Navigator"
	version = detect.charAt(8);
}
else browser = "An unknown browser";


if (!version) {
	aux = detect.substring(place + thestring.length);

	posDecimal = aux.indexOf('.');
	version = aux.substring(0, posDecimal);
	do{
	  version = version + '' + aux.charAt(posDecimal);
	  posDecimal++;
	}while (!isNaN(aux.charAt(posDecimal)) && posDecimal < aux.length);
}


if (!OS)
{
	if (checkIt('linux')) OS = "Linux";
	else if (checkIt('x11')) OS = "Unix";
	else if (checkIt('mac')) OS = "Mac"
	else if (checkIt('win')) OS = "Windows"
	else OS = "an unknown operating system";
}

if (browser == "Internet Explorer" && parseFloat( version, 10) < 7 ){
	alert("<bean:message key="errors.ie.versionminima" />");
}

if (browser == "Firefox" && parseFloat( version, 10) < 4 ){
	alert("<bean:message key="errors.firefox.versionminima" />");
}

-->
</script>

<!--  PARTICULARIZACION LOGIN (para zonaperfront y sistrafront todo lo demas igual)-->
<%@ include file="loginIBCustom.jsp" %>
<!--  PARTICULARIZACION LOGIN -->

<%

	//Si la url origen es redireccionClave.jsp, hacemos login automatico
	if (esLoginClave) {
			
		// Obtenemos ticket pasado por POST
		Class[] paramsParametersOrigen = new Class[] {String.class};
		Method methodParametersOrigen = savedRequest.getClass().getDeclaredMethod("getParameterValues", paramsParametersOrigen);
		String[] values =  (String []) methodParametersOrigen.invoke(savedRequest, new String("ticket"));
		System.out.println( "VALORES:" + values.length );
		ticketClave = values[0];
	} else {
		// Url Callback
		if (request.getContextPath().indexOf("zonaperfront") != -1) {
			urlCallback = urlSistra + contextoRaiz + "/zonaperfront/protected/redireccionLoginIB.jsp";
		} else {
			urlCallback = urlSistra + contextoRaiz + "/sistrafront/protected/redireccionLoginIB.jsp";
		}
		// Metodos permitidos
		metodos = niveles; // Ver metodos de autenticacion permitidos
		// Url redireccion clave
		//urlClave = urlSistra + "/" + contextAutenticadorClave + "/login";
		urlClave = contextAutenticadorClave + "/login";
		// Url destino
		urlDestino = urlSavedRequest;
		if (queryStringSavedRequest != null) {
			urlDestino += "?" + queryStringSavedRequest;
		}
		
		session.setAttribute("urlDestino", urlDestino);
		
		final HttpClient client = new HttpClient();
		final PostMethod postMethod = new PostMethod(urlClave);
		JSONObject json = new JSONObject();
		json.put("aplicacion","SISTRA");
		json.put("entidad", entidad);
		json.put("forzarAutenticacion", false);
		json.put("idioma", language);
		json.put("metodosAutenticacion", metodos);
		json.put("qaa", 2);
		json.put("urlCallback", urlCallback);
		// TODO ALEX: REVISAR EL MANEJO DEL ERROR
		json.put("urlCallbackError", urlCallback);
		postMethod.addRequestHeader("Authorization", "Basic " + ConvertUtil.cadenaToBase64(username + ":" + password));
		postMethod.setRequestEntity((new StringRequestEntity(json.toString(), "application/json", "UTF-8")));
		client.executeMethod(postMethod);
		final String loginPage = postMethod.getResponseBodyAsString();
		
		// Redirigimos a pagina de login
		response.sendRedirect(loginPage);
		
	}
%>

</head>

<body <%=(esLoginClave && !esError?"onload='loginClave();'":"")%> >

<div id="contenidor">

	<!-- continguts -->
	<div id="continguts">
		<!-- tramitacion -->
		<div id="tramitacion">
			<br />
			<% if (esLoginClave) { %>
			<bean:message key="login.clave.redirigiendoClave" />
			<form id="loginClave" action="j_security_check" method="post">
				<input name="j_username" id="j_username" type="hidden" value="{TICKET-<%=session.getId()%>}"/>
				<input name="j_password" id="j_password" type="hidden" value="<%=ticketClave%>"/>
			</form>
			<% } %>


			<% if (!esLoginClave) { %>

			<%if (esError){ %>
			<div class="error"><h2><bean:message key="login.error" /></h2></div>
			<%}%>
			<% } %>

		</div>
	</div>
</div>
</body>
</html>




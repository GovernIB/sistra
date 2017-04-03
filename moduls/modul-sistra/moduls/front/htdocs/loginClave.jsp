<%@ page contentType="text/html; charset=ISO-8859-1"%>
<%@ page import="java.util.*, org.apache.struts.Globals, es.caib.util.ConvertUtil, java.io.FileInputStream,es.caib.sistra.plugins.PluginFactory, es.caib.sistra.plugins.login.PluginLoginIntf, java.lang.reflect.Method" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%

	// Parametros login clave
	Object savedRequest = null;
	boolean esLoginClave = false;
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
	es.caib.zonaper.model.OrganismoInfo infoOrg = delegateF.obtenerOrganismoInfo();
	if (request.getParameter("entidad") != null) {
		infoOrg = delegateF.obtenerOrganismoInfo( (String) request.getParameter("entidad"));
	}

	// Configuracion plugin login Clave: obtener contexto login
	PluginLoginIntf plg = PluginFactory.getInstance().getPluginLogin();
	Method method = plg.getClass().getDeclaredMethod("obtenerContextoAutenticadorClave", null);
	Object result = method.invoke(plg, null);
	String contextAutenticadorClave =  (String) result;

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
	esLoginClave = urlSavedRequest.endsWith("/protected/redireccionClave.jsp");
	
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


function ocultarAyudaAdmin() {
	var capaI = document.getElementById('contactoAdministrador');
	capaI.style.display = 'none';
}

// Abre pantalla de ayuda
function mostrarAyudaAdmin() {


	var capaI = document.getElementById('contactoAdministrador');



	// tama?os de la ventana y la p?gina
	var ventanaX = document.documentElement.clientWidth;
	var ventanaY = document.documentElement.clientHeight;
	var capaY = document.getElementById('contenidor').offsetHeight;

/*
	// la capa de fondo ocupa toda la p?gina
	with (capaIF) {
		if(ventanaY > capaY) style.height = ventanaY + 'px';
		else style.height = capaY + 'px';
		if(document.all) style.filter = "alpha(opacity=30)";
		else style.MozOpacity = 0.3;
		if(document.all) style.width = ventanaX + 'px';
		style.display = 'block';
	}
	// OJO, descomentar si se quiere poder pulsar en cualquier parte de la pantalla
	//capaIF.onclick = cerrarInfo;
*/

	// mostramos, miramos su tama?o y centramos la capaInfo con respecto a la ventana
	capaI.style.display = 'block';
	capaInfoX = capaI.offsetWidth;
	capaInfoY = capaI.offsetHeight;
	with (capaI) {
		style.left = (ventanaX-capaInfoX)/2 + 'px';
		style.top = (((ventanaY-capaInfoY)/2)+ document.documentElement.scrollTop) + 'px';
	}


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
<%@ include file="loginClaveCustom.jsp" %>
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
			urlCallback = urlSistra + contextoRaiz + "/zonaperfront/protected/redireccionClave.jsp";
		} else {
			urlCallback = urlSistra + contextoRaiz + "/sistrafront/protected/redireccionClave.jsp";
		}
		// Metodos permitidos
		metodos = niveles; // Ver metodos de autenticacion permitidos
		// Url redireccion clave
		urlClave = urlSistra + "/" + contextAutenticadorClave + "/iniciarSesionClave.html";
		// Url destino
		urlDestino = urlSavedRequest;
		if (queryStringSavedRequest != null) {
			urlDestino += "?" + queryStringSavedRequest;
		}
	}

	//  Contacto soporte
    String telefonoSoporte = infoOrg.getTelefonoIncidencias();
    if (telefonoSoporte == null) {
      telefonoSoporte = "&nbsp;";
    }
    request.setAttribute("telefonoSoporte", telefonoSoporte);

    String urlSoporte = infoOrg.getUrlSoporteIncidencias();
    if (urlSoporte == null) {
       urlSoporte = "&nbsp;";
    }
    request.setAttribute("urlSoporte", urlSoporte);

    String emailSoporte = infoOrg.getEmailSoporteIncidencias();
    if (emailSoporte == null) {
       emailSoporte = "&nbsp;";
    }
    request.setAttribute("emailSoporte",emailSoporte);

	// Construimos url de soporte reemplazando variables
	String tituloTramite = es.caib.util.StringUtil.replace(textoAtencion,"\"","\\\"");
    String urlSoporteFinal = es.caib.util.StringUtil.replace(urlSoporte,"@asunto@",tituloTramite);
    urlSoporteFinal = es.caib.util.StringUtil.replace(urlSoporteFinal,"@idioma@",language);
%>

</head>

<body <%=(esLoginClave?"onload='loginClave();'":"")%> >


<%-- <logic:equal name="<%=es.caib.sistra.front.Constants.MOSTRAR_EN_IFRAME%>" value="false"> --%>
<div id="contactoAdministrador" class="contactoAdministrador">
	<h1 class="ayuda"><bean:message key="administrador.ayuda"/></h1>
	<p>
		<!--  Soporte por url y telefono (opcional) -->
		<logic:notEqual name="urlSoporte"  value="&nbsp;" >
			<logic:notEqual name="telefonoSoporte" value="&nbsp;" >
				<bean:message key="administrador.soporteUrlTelefono" arg0="<%=urlSoporteFinal%>" arg1="<%=telefonoSoporte%>"/>
			</logic:notEqual>
			<logic:equal name="telefonoSoporte" value="&nbsp;" >
				<bean:message key="administrador.soporteUrl" arg0="<%=urlSoporteFinal%>"/>
			</logic:equal>
		</logic:notEqual>

		<!--  Soporte por email y telefono (opcional) -->
		<logic:equal name="urlSoporte" value="&nbsp;" >
			<logic:notEqual name="emailSoporte" value="&nbsp;" >
				<logic:notEqual name="telefonoSoporte" value="&nbsp;" >
					<bean:message key="administrador.soporteEmailTelefono" arg0="<%=emailSoporte%>" arg1="<%=telefonoSoporte%>"/>
				</logic:notEqual>
				<logic:equal name="telefonoSoporte" value="&nbsp;" >
					<bean:message key="administrador.soporteEmail" arg0="<%=emailSoporte%>"/>
				</logic:equal>
			</logic:notEqual>
		</logic:equal>
	</p>
	<p align="center">
		<a title="<bean:message key="message.continuar"/>" onclick="javascript:ocultarAyudaAdmin();" href="javascript:void(0);">
		<bean:message key="message.continuar"/>
		</a>
	</p>
</div>
<%-- </logic:equal> --%>


<div id="contenidor">
	<!-- capçal -->
	<div id="capsal">
		<a href="<%=infoOrg.getUrlPortal()%>" accesskey="0"><img id="logoCAIB" class="logo" src="<%=infoOrg.getUrlLoginLogo()%>" alt="Logo <%=infoOrg.getNombre()%>" /></a>
	</div>

	<!-- títol -->
	<p id="titolAplicacio"><bean:message key="login.titulo"/></p>

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

			<bean:message key="login.presentacion.parrafo1" />
			<%if (textoAtencion.length() > 0){ %>
			:<p id="nomTramit"><%=textoAtencion%></p>
			<%}%>
			<p><bean:message key="login.presentacion.parrafo2" /></p>

			<% if (niveles.indexOf("C")>=0 || niveles.indexOf("U")>=0){ %>
			<div id="indexCD">
				<img style="display: block;margin-left: auto; margin-right: auto;" src = "../images/logo_Clave.png" alt="<bean:message key="login.clave.titulo" />"/>
				<p><bean:message key="login.clave.instrucciones" /></p>

				<form name="formCD" action="<%=urlClave%>" method="post">
					<input type="hidden" name="metodos" value="<%=metodos%>" />
					<input type="hidden" name="urlCallbackLogin" value="<%=ConvertUtil.cadenaToBase64UrlSafe(urlCallback)%>" />
					<input type="hidden" name="urlDestino" value="<%=ConvertUtil.cadenaToBase64UrlSafe(urlDestino)%>" />
					<input type="hidden" name="idioma" value="<%=language%>" />
					<p class="formBotonera">
						<input type="submit" value="<bean:message key="login.boton.iniciar" />" title="<bean:message key="login.boton.iniciar" />" />
					</p>
				</form>

				<p>
					<a href="http://clave.gob.es" target="_blank"><bean:message key="login.clave.mesInfoText" /></a>
				</p>

			</div>
			<%} %>

			<% if (niveles.indexOf("A")>=0){ %>
			<div id="indexAN">
				<h2><bean:message key="login.anonimo.titulo" /></h2>
				<p><bean:message key="login.anonimo.instrucciones.parrafo1" /></p>
				<form name="formAN" method="post" action="j_security_check">
					<input name="j_username" id="j_username" type="hidden" value="nobody" />
					<input name="j_password" id="j_password" type="hidden" value="nobody" />
					<p class="formBotonera">
					<input name="formANboton" type="submit" value="<bean:message key="login.boton.iniciar" />" title="<bean:message key="login.anonimo.boton.title" />"/>
					</p>
				</form>
			</div>
			<%} %>
			<% } %>

			<div class="sep"></div>

		</div>
	</div>

	<!-- peu -->
	<div id="peu">

		<div class="esquerra">&copy; <%=infoOrg.getNombre()%></div>

			<!-- contacte -->
			<div class="centre">
				<%=infoOrg.getPieContactoHTML()%>
			</div>

			<!-- /contacte -->
			<div class="dreta">
				<bean:message key="header.mailAdministrador"/>
				<a href="javascript:void(0)" onclick="mostrarAyudaAdmin();">
				<bean:message key="header.mailAdministrador.enlace"/>
				</a>.
			</div>

	</div>
</div>
</body>
</html>




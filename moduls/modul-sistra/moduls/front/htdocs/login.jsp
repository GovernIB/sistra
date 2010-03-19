<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Organismo Test</title>
<link href="../estilos/loginMOCK.css" rel="stylesheet" type="text/css" />
<%@ page contentType="text/html; charset=ISO-8859-1" import="java.util.*, org.apache.struts.Globals" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>

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
var OS,browser,version,total,thestring;

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
else if (checkIt('msie')) browser = "Internet Explorer";
else if (checkIt('firefox')) browser = "Firefox";
else if (!checkIt('compatible'))
{
	browser = "Netscape Navigator"
	version = detect.charAt(8);
}
else browser = "An unknown browser";


if (!version) {
	version = detect.charAt(place + thestring.length);
	posDecimal = place + thestring.length + 1;
	if (detect.charAt(posDecimal) == '.'){	
		do{
		  version = version + '' + detect.charAt(posDecimal);		
		  posDecimal++;								
		}while (!isNaN(detect.charAt(posDecimal)));		
	}
}


if (!OS)
{
	if (checkIt('linux')) OS = "Linux";
	else if (checkIt('x11')) OS = "Unix";
	else if (checkIt('mac')) OS = "Mac"
	else if (checkIt('win')) OS = "Windows"
	else OS = "an unknown operating system";
}

if (browser == "Internet Explorer" && parseFloat( version, 10) < 6 ){
	alert("<bean:message key="errors.ie.versionminima" />");
}

if (browser == "Firefox" && parseFloat( version, 10) < 1.5 ){
	alert("<bean:message key="errors.firefox.versionminima" />");
}
-->
</script>


<!--  FIRMA DIGITAL -->
<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/constantes.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/time.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/appletHelper.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/instalador.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/firma.js"></script>	
<script type="text/javascript">
<!--			

		baseDownloadURL = "<%=request.getContextPath()%>/firma/aFirma";
		base = "<%=request.getContextPath()%>/firma/aFirma";

		function prepararEntornoFirma(){
			cargarAppletFirma();
		}
		
		function loginCertificado(){
				
			if (clienteFirma == undefined) { 
	          alert("No se ha podido instalar el entorno de firma");
	          return false;
        	}
				
			var cadena = document.formCD.j_username.value;
		
			clienteFirma.initialize();
			clienteFirma.setSignatureAlgorithm("sha1WithRsaEncryption");
			clienteFirma.setSignatureMode("EXPLICIT");
			clienteFirma.setSignatureFormat("CMS");
			clienteFirma.setData(cadena);
		
			clienteFirma.sign();
			
			if(clienteFirma.isError()){
				error = 'Error: '+clienteFirma.getErrorMessage();
				alert(error);
				return false;
			}else{	
			     firma = clienteFirma.getSignatureBase64Encoded();
			     document.formCD.j_password.value = "{FIRMA:"+firma+"}";
			     return true;
			}
		}
	
//-->
</script>


<!--  PARTICULARIZACION LOGIN (para zonaperfront y sistrafront todo lo demas igual)-->
<%@ include file="loginCustom.jsp" %>
<!--  PARTICULARIZACION LOGIN -->

</head>

<body onload="prepararEntornoFirma()">
<div id="contenedor">
	<!-- capçal -->	
	<div id="capsal">
		<a href="http://www.google.es" accesskey="0"><img id="logoCAIB" class="logo" src="../images/logoMOCK.gif" alt="Logo Organismo Test" /></a>
	</div>
	
	<!-- títol -->
	<p id="titolAplicacio"><bean:message key="login.titulo"/></p>
	
	<!-- continguts -->
	<div id="continguts">
		<!-- tramitacion -->
		<div id="tramitacion">
		
			<br />
			<bean:message key="login.presentacion.parrafo1" />
			<%if (textoAtencion.length() > 0){ %>
			:<p id="nomTramit"><%=textoAtencion%></p>
			<%}%>	
			<p><bean:message key="login.presentacion.parrafo2" /></p>
			
			<% if (niveles.indexOf("C")>=0){ %>
			<div id="indexCD">
				<h2><bean:message key="login.certificado.titulo" /></h2>
				<p><bean:message key="login.certificado.instrucciones.parrafo" /></p>
				<form name="formCD" method="post" action="j_security_check" onSubmit="loginCertificado();">
					<input name="j_username" id="j_username" type="hidden" value="<%=request.getSession().getId()%>"/>
					<input name="j_password" id="j_password" type="hidden" />				
					<p class="formBotonera">
						<input type="submit" value="<bean:message key="login.boton.iniciar" />" title="<bean:message key="login.certificado.boton.title" />" />
					</p>	
				</form>
			</div>
			<%} %>
			<% if (niveles.indexOf("U")>=0){ %>			
			<div id="indexUC">
				<h2><bean:message key="login.usuario.titulo" /></h2>
				<p><bean:message key="login.usuario.instrucciones.parrafo1" /></p>
				<form name="formUC" method="post" action="j_security_check">
					<label for="USUARIO"><bean:message key="login.usuario.usuario" /></label> <input name="j_username" id="j_username" type="text" class="txt" />
					<div class="separacio"></div>
					<label for="CONTRASENYA"><bean:message key="login.usuario.passwd" /></label> <input name="j_password" id="j_password" type="password" class="txt" />
					<p class="formBotonera"><input name="formUCboton" type="submit" value="<bean:message key="login.boton.iniciar" />" title="<bean:message key="login.usuario.boton.title" />" /></p>
				</form>			
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
			<div class="sep"></div>
	
		</div>	
	</div>		
	
	<!-- peu -->
	<div id="peu">
		&copy; Organismo Test
	</div>
</div>
</body>
</html>




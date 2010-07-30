<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.sistra.front.util.TramiteRequestHelper"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%--
<html:html xhtml="true">
--%>
<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<%= lang %>" lang="<%= lang %>" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
<title><bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/></title>
<link href="estilos/sistrafront-0.0.2.css" rel="stylesheet" type="text/css" />
<link href="estilos/stamargalida.css" rel="stylesheet" type="text/css" />	
<logic:notEmpty name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom">
	<link href="<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom" />" rel="stylesheet" type="text/css" />
</logic:notEmpty>
<script type="text/javascript" src="js/globales.js"></script>

<!--  Scripts para firma (depende implementacion) -->
<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
			 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">		
		<script type="text/javascript" src="<%=request.getContextPath()%>/firma/caib/js/firma.js"></script>			
</logic:equal>

<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
			 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">					
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/constantes.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/time.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/appletHelper.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/instalador.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/firma.js"></script>	
	<script type="text/javascript">		
		baseDownloadURL = "<%=request.getContextPath()%>/firma/aFirma";
		base = "<%=request.getContextPath()%>/firma/aFirma";
	</script>
	
</logic:equal>		

<!-- DETECCION NAVEGADOR (Compatibles: IE >=6 , FireFox >= 1.5)-->
<script type="text/javascript">
<!--
	var errorIE="<bean:message key="errors.ie.versionminima" />";
	var errorFirefox="<bean:message key="errors.firefox.versionminima" />";
	
	
	function doOnLoad(){
		// Chequeamos version navegador
		checkVersionNavegador(errorIE,errorFirefox);
		// Preparamos entorno firma
		if (typeof prepararEntornoFirma != 'undefined') { // Si existe la funcion ejecutarla.
            prepararEntornoFirma();
        }
	}
-->
</script>



</head>
<logic:notPresent name="message">
<body onload="doOnLoad();">
<div style='visibility:hidden;position:absolute'>
	Version :<%=es.caib.sistra.front.util.Util.getVersion()%>
	<%
	 if (TramiteRequestHelper.getTramiteFront( request ) != null){
	%>		 
	Tag Cuaderno Carga:  <%=TramiteRequestHelper.getTramiteFront( request ).getTagCuadernoCarga()%>
	Fecha exportación xml: <%=es.caib.util.StringUtil.timestampACadena(TramiteRequestHelper.getTramiteFront( request ).getFechaExportacion()) %>		  
	<%
	 }
	%>
</div>
</logic:notPresent>
<logic:present name="message">
<body onload="doOnLoad();mostrarInfo();">
</logic:present>
<div id="contenidor">
	<!--  cabecera -->
	<tiles:insert name="header"/>
<%-- Para StaMargalida, el header comprende tambien los datosUsuario 
	<!--  datos usuario -->
	<tiles:insert name="datosUsuario"/>
--%>
	<%-- <div id="capsalfranja"></div>--%>
	<div class='content'>
		<!-- continguts -->
		<div id="continguts">
		
		<tiles:insert name="main"/>
		</div>
	</div>
	<!-- pie pagina -->
	<!--  <div id="peuVora"></div> -->
	<tiles:insert name="footer"/>
</div><!-- contenedor -->
</body>
<%--
</html:html>
--%>
</html>
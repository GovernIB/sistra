<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.sistra.front.util.TramiteRequestHelper"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:html xhtml="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/></title>
<link href="estilos/sistrafront-0.0.2.css" rel="stylesheet" type="text/css" />
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
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/instalador.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/firma.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/utils.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/constantes.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/configClienteaFirmaSistra.js"></script>
	<script type="text/javascript">		
		base = "<%=request.getAttribute("urlSistraAFirma")%><%=request.getContextPath()%>/firma/aFirma";
		baseDownloadURL = "<%=request.getAttribute("urlSistraAFirma")%><%=request.getContextPath()%>/firma/aFirma";
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
		
		// està dins d'un iframe?
		if(top.window.location != self.window.location) {
			estaIframe();
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
	<!--  datos usuario -->
	<tiles:insert name="datosUsuario"/>
	<div id="capsalfranja"></div>
	<!-- continguts -->
	<div id="continguts">
	<tiles:insert name="main"/>
	</div>
	<!-- pie pagina -->
	<!--  <div id="peuVora"></div> -->
	<tiles:insert name="footer"/>
</div><!-- contenedor -->
</body>
</html:html>
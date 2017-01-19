<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:html xhtml="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre" /></title>
<link href="css/zonaperfront-0.0.2.css" rel="stylesheet" type="text/css" media="screen" />
<link href="css/calendar.css" rel="stylesheet" type="text/css" />
<logic:notEmpty name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom">
	<link href="<bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom" />" rel="stylesheet" type="text/css" />
</logic:notEmpty>
<!-- 
<link href="css/estils_print.css" rel="stylesheet" type="text/css" media="print" />
 -->
<script type="text/javascript" src="js/jquery-1.4.1.min.js"></script>
<script type="text/javascript" src="js/jquery.selectboxes.pack.js"></script>
<script type="text/javascript" src="js/ajuda.js"></script>
<script type="text/javascript" src="js/globales.js"></script>

<!--  Scripts para firma (depende implementacion) -->
<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
			 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">		
		<script type="text/javascript" src="<%=request.getContextPath()%>/firma/caib/js/firma.js"></script>			
</logic:equal>

<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
			 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">					
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/configClienteaFirmaSistra.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/miniapplet.js"></script>	
	<script type="text/javascript">		
		base = "<%=session.getAttribute("urlSistraAFirma")%><%=request.getContextPath()%>/firma/aFirma";
		baseDownloadURL = "<%=session.getAttribute("urlSistraAFirma")%><%=request.getContextPath()%>/firma/aFirma";
	</script>
	
</logic:equal>		

<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
			 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_FIRMAWEB%>">		
		<script type="text/javascript" src="<%=request.getContextPath()%>/firma/firmaWeb/js/configFirmaWeb.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/firma/firmaWeb/js/firmaweb.js"></script>
		<script type="text/javascript">		
			FIRMAWEB_CONTEXTO = '<bean:write name="<%=es.caib.zonaper.front.Constants.CONTEXTO_RAIZ%>"/>';			
		</script>			
</logic:equal>


<script type="text/javascript">
<!--
	function doOnLoad(){
		// Check version navegador (Compatibles: IE >=7 , FireFox >= 4)
		var errorIE="<bean:message key="errors.ie.versionminima" />";
		var errorFirefox="<bean:message key="errors.firefox.versionminima" />";
		checkVersionNavegador(errorIE,errorFirefox);
		
		// està dins d'un iframe?
		if(top.window.location != self.window.location) {
			contenidor_H = document.getElementById("contenidor").offsetHeight;
			top.window.document.getElementById("frm").style.height = parseInt((contenidor_H/14.5), 10) + 2 + "em";
		}
		
	}
	
-->
</script>
</head>

<body onload="doOnLoad()">
<div style='visibility:hidden;position:absolute'>Version:<%=es.caib.zonaper.front.util.Util.getVersion()%></div>
<div id="contenidor">
	<!-- capsal -->
	<tiles:insert name="header"/>		
	<!-- continguts -->
	<div id="continguts">
		<!-- titol -->
		<h1 id="titolPagina"></h1>
		<div id="titolOmbra"></div>
		<tiles:insert name="main"/>
	</div>
	<tiles:insert name="footer"/>
</div>
<div id="fondo"></div>
</body>
</html:html>

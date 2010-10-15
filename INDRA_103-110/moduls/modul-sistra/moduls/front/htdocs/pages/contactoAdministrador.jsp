<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.struts.Globals,java.util.Locale"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:html xhtml="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><bean:message key="administrador.titulo"/></title>
<link href="estilos/sistrafront-0.0.2.css" rel="stylesheet" type="text/css" media="screen" />
<logic:notEmpty name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom">
	<link href="<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom" />" rel="stylesheet" type="text/css" />
</logic:notEmpty>
<link href="estilos/estilos_print.css" rel="stylesheet" type="text/css" media="print" />
<script type="text/javascript" src="js/globales.js"></script>
</head>

<%
	// Construimos url de soporte
	String url = request.getContextPath() + "/protected/init.do?modelo=IN0014CON&version=1&centre=WEB&tipus_escrit=PTD";
	
	// - Lenguaje
	String lang = "es";
 	Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
	if (locale != null) lang = locale.getLanguage();
	url += "&language="+lang;
	
	// - Titulo trámite (se pasa por parametro)
	String titulo = request.getParameter("tituloTramite");
	if (titulo!=null) url+="&asunto=" + java.net.URLEncoder.encode(titulo);	
%>


<body style="padding:0;">
	<!-- capçal -->
	
	<div id="capsal">
		<!-- <img src="imgs/capsal/logo.gif" alt="Logo del Govern de les Illes Balears" /> -->
	</div> 
	
	<!-- títol -->
	<h1 class="ayuda"><bean:message key="administrador.ayuda"/></h1>
	<div id="pieTitulo"></div>
	<!-- continguts -->
	<div id="continguts" style="padding:1em;">
		<p>
			<bean:message key="administrador.contacto1"/>
			<a title="<bean:message key="administrador.soporte"/>" href="<%=url%>" target="_blank">
			<bean:message key="administrador.contacto2"/>
			</a>
			<bean:message key="administrador.contacto3"/>
		</p>
	</div>
	<!-- peu -->
	<!-- 
	<div id="peu" style="padding-left:1em;">
		&copy; Govern de les Illes Balears
	</div>
	 -->
</body>
</html:html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ page import="java.util.*" %>

<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="formulario.titulo"/></title>
   <meta http-equiv="Content-type" content='text/html; charset="ISO-8859-1"' />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
</head>
<body class="ventana">
		
	<bean:message key="errors.xpathsDuplicados"/>

	<ul>
	<logic:iterate name="duplicados" id="xpath">
		<li><bean:write name="xpath"/></li>
	</logic:iterate>
	</ul>
	
    <bean:define id="urlExportar"><html:rewrite page="/generar/xml.do" paramId="idForm" paramName="idForm"/></bean:define>
    <button class="buttonl" type="button" onclick="forward('<%=urlExportar%>&generarDuplicados=S')">
    	<bean:message key="boton.exportarDuplicados.si" />
    </button>
    &nbsp;&nbsp;
    <button class="buttonl" type="button" onclick="forward('<%=urlExportar%>&generarDuplicados=N')">
    	<bean:message key="boton.exportarDuplicados.no" />
    </button>
</body>
</html:html>
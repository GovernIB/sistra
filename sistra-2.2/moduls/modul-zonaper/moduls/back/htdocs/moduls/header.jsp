<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>

<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>

<div id="capsal">
<!-- 
	<a href="http://intranet.caib.es"><bean:message key="header.tornarIntranet" /></a>
	<img src="imgs/capsal/logo_caib.gif" alt="Govern de les Illes Balears" />
 -->	
	<h1><bean:message key="cabecera.titulo" /></h1>
</div>
<div id="capsalUsuari">
<strong><bean:write name="<%=es.caib.zonaper.back.Constants.NOMBRE_USUARIO_KEY%>"/> </strong> (<%= request.getRemoteUser() %>)
</div>
<ul id="mollaPa">
<li><html:link action="inicio.do"><bean:message key="mollapa.inicio" /></html:link></li>
<li><bean:message key="mollapa.confirmacion" /></li>
</ul>
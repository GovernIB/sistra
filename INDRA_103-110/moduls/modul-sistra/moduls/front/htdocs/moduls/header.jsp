<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="org.apache.struts.Globals,java.util.Locale"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>
<bean:define id="urlPortal">
	<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="urlPortal"/>
</bean:define>

<!-- capçal -->
<div id="cap">
	<html:link href="<%=urlPortal%>" paramId="lang" paramName="lang" accesskey="0" >
		<img id="logoCAIB" class="logo" src="<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="urlLogo"/>" alt="Logo <bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/>" />
	</html:link>
</div>

<!-- titol -->
<p id="titolAplicacio"><bean:message key="aplicacion.titulo"/></p>

 
<!--  Contacto soporte --> 
	<bean:define id="urlSoporte" type="String">
		<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="urlSoporteIncidencias"/>
	</bean:define>
	<bean:define id="tituloJs" type="String">
		<bean:write name="descripcion" />	
	</bean:define>
	<bean:define id="telefonoSoporte" type="String">
		<logic:notEmpty name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias" >
		<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias"/>
		</logic:notEmpty>
		<logic:empty  name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias" >
			&nbsp;
		</logic:empty>
	</bean:define>
	<%
		// Construimos url de soporte reemplazando variables
		String tituloTramite = es.caib.util.StringUtil.replace(tituloJs,"\"","\\\"");
	    String urlSoporteFinal = es.caib.util.StringUtil.replace(urlSoporte,"@asunto@",tituloTramite);
	    urlSoporteFinal = es.caib.util.StringUtil.replace(urlSoporteFinal,"@idioma@",lang);			
	%>

<div id="contactoAdministrador" class="contactoAdministrador">
	<h1 class="ayuda"><bean:message key="administrador.ayuda"/></h1>
	<p>
		<bean:message key="administrador.contacto1"/>
		<a title="<bean:message key="administrador.soporte"/>" href="<%=urlSoporteFinal%>" target="_blank">
		<bean:message key="administrador.contacto2"/>
		</a>
		<bean:message key="administrador.contacto3" arg0="<%=telefonoSoporte%>"/>
	</p>
	<p align="center">
		<a title="<bean:message key="message.continuar"/>" onclick="javascript:ocultarAyudaAdmin();" href="javascript:void(0);">
		<bean:message key="message.continuar"/>
		</a>
	</p>	
</div>


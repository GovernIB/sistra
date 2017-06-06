<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="org.apache.struts.Globals,java.util.Locale,java.net.URLEncoder"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>

<bean:define id="urlPortal">
	<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="urlPortal"/>
</bean:define>

<logic:present name="ID_INSTANCIA">
<bean:define id="urlFormularioIncidencias">
 <html:rewrite page="/protected/mostrarFormularioIncidencias.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>

<script type="text/javascript">
<!--
	function formularioIncidencias() {
		mostrarFormularioIncidencias('<%=urlFormularioIncidencias%>');
	}
-->
</script>

<logic:equal name="<%=es.caib.sistra.front.Constants.MOSTRAR_EN_IFRAME%>" value="false">
<!-- capçal -->
<div id="cap">
	<html:link href="<%=urlPortal%>" paramId="lang" paramName="lang" accesskey="0" >
		<img id="logoCAIB" class="logo" src="<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="urlLogo"/>" alt="Logo <bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/>" />
	</html:link>
</div>
</logic:equal>

<!-- titol -->
<p id="titolAplicacio"><bean:message key="aplicacion.titulo"/></p>

 
<!--  Contacto soporte --> 	
<logic:equal name="<%=es.caib.sistra.front.Constants.MOSTRAR_EN_IFRAME%>" value="false">
<div id="contactoAdministrador" class="contactoAdministrador">
	<h1 class="ayuda"><bean:message key="administrador.ayuda"/></h1>
	<div id="contactoAdministradorSoporte">		
	<p>		
		<bean:write name="literalContacto" filter="false" />			
	</p>
	</div>
	<div id="contactoAdministradorContent"></div>
	<p align="center">
		<a title="<bean:message key="message.continuar"/>" onclick="javascript:ocultarAyudaAdmin();" href="javascript:void(0);">
		<bean:message key="message.continuar"/>
		</a>
	</p>	
</div>
</logic:equal>
</logic:present>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="org.apache.struts.Globals,java.util.Locale"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>

<!-- capçal -->
<div id="capsal">
	<html:link href="http://www.caib.es" paramId="lang" paramName="lang" accesskey="0" >
		<img id="logoCAIB" class="logo" src="imgs/capsal/logo.gif" alt="Logo del Govern de les Illes Balears" />
	</html:link>
</div>

<!-- titol -->
<p id="titolAplicacio"><bean:message key="tituloAplicacion"/></p>

 
<div id="contactoAdministrador" class="contactoAdministrador">
	
	<%
		// Construimos url de soporte
		String url = request.getContextPath() + "/protected/init.do";		
	%>
	<h1 class="ayuda"><bean:message key="administrador.ayuda"/></h1>
	<p>
		<bean:message key="administrador.contacto1"/>
		<a title="<bean:message key="administrador.soporte"/>" onclick="javascript:document.forms['contactoAdmin'].submit();" href="javascript:void(0);">
		<bean:message key="administrador.contacto2"/>
		</a>
		<bean:message key="administrador.contacto3"/>
	</p>
	<p align="center">
		<a title="<bean:message key="message.continuar"/>" onclick="javascript:ocultarAyudaAdmin();" href="javascript:void(0);">
		<bean:message key="message.continuar"/>
		</a>
	</p>
	<form name="contactoAdmin" action="<%=url%>" target="_blank">
	 	<input type="hidden" name="modelo" value="IN0014CON" />	 	
	 	<input type="hidden" name="version" value="1" />	 	
	 	<input type="hidden" name="centre" value="WEB" />	 
	 	<input type="hidden" name="tipus_escrit" value="PTD" />	 
	 	<logic:present name="descripcion">
			<bean:define id="tituloJs" type="String">
				<bean:write name="descripcion" />	
			</bean:define>
			<input type="hidden" name="asunto" value="<%=es.caib.util.StringUtil.replace(tituloJs,"\"","\\\"")%>" />			
		</logic:present>	 		 	
	 	<input type="hidden" name="language" value="<%=lang%>" />	 	
	 </form>
</div>


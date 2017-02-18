<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="org.apache.struts.Globals,java.util.Locale,java.net.URLEncoder"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>
<bean:define id="datosSesion" name="datossesion" type="es.caib.sistra.model.DatosSesion" /> 
<bean:define id="urlPortal">
	<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="urlPortal"/>
</bean:define>
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
	<bean:define id="telefonoSoporte" type="String">
		<logic:notEmpty name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias">
			<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias" />
		</logic:notEmpty>
		<logic:empty name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias">
			&nbsp;
		</logic:empty>				
	</bean:define>	
	<bean:define id="urlSoporte" type="String">
		<logic:notEmpty name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="urlSoporteIncidencias">
			<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="urlSoporteIncidencias" />
		</logic:notEmpty>
		<logic:empty name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="urlSoporteIncidencias">
			&nbsp;
		</logic:empty>			
	</bean:define>	
	<bean:define id="emailSoporte" type="String">
		<logic:notEmpty name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="emailSoporteIncidencias">
			<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="emailSoporteIncidencias" />
		</logic:notEmpty>
		<logic:empty name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="emailSoporteIncidencias">
			&nbsp;
		</logic:empty>					
	</bean:define>		
	<bean:define id="tituloJs" type="String">
		<logic:present name="descripcion">
			<bean:write name="descripcion" />
		</logic:present>
		<logic:notPresent name="descripcion">
			&nbsp;
		</logic:notPresent>	
	</bean:define>	
	<%
		// Construimos url de soporte reemplazando variables
		String tituloTramite = es.caib.util.StringUtil.replace(tituloJs,"\"","\\\"");
		String urlSoporteFinal = es.caib.util.StringUtil.replace(urlSoporte,"@asunto@", URLEncoder.encode(tituloTramite, "ISO-8859-1"));
	    urlSoporteFinal = es.caib.util.StringUtil.replace(urlSoporteFinal,"@idioma@",lang);		    	    	    
	%>
<logic:equal name="<%=es.caib.sistra.front.Constants.MOSTRAR_EN_IFRAME%>" value="false">
<div id="contactoAdministrador" class="contactoAdministrador">
	<h1 class="ayuda"><bean:message key="administrador.ayuda"/></h1>
	<div id="contactoAdministradorUrl">	
	<p>		
			<!--  Soporte por url y telefono (opcional) -->
			<logic:notEqual name="urlSoporte"  value="&nbsp;" >
				<logic:notEqual name="telefonoSoporte" value="&nbsp;" >
					<bean:message key="administrador.soporteUrlTelefono" arg0="<%=urlSoporteFinal%>" arg1="<%=telefonoSoporte%>"/>
				</logic:notEqual>
				<logic:equal name="telefonoSoporte" value="&nbsp;" >
					<bean:message key="administrador.soporteUrl" arg0="<%=urlSoporteFinal%>"/>
				</logic:equal>					
			</logic:notEqual>
			
			<!--  Soporte por email y telefono (opcional) -->
			<logic:equal name="urlSoporte" value="&nbsp;" >
				<logic:notEqual name="emailSoporte" value="&nbsp;" >
					<logic:notEqual name="telefonoSoporte" value="&nbsp;" >
						<bean:message key="administrador.soporteEmailTelefono" arg0="<%=emailSoporte%>" arg1="<%=telefonoSoporte%>"/>
					</logic:notEqual>
					<logic:equal name="telefonoSoporte" value="&nbsp;" >
						<bean:message key="administrador.soporteEmail" arg0="<%=emailSoporte%>"/>
					</logic:equal>					
				</logic:notEqual>
			</logic:equal>			
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

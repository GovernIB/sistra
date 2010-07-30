<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="org.apache.struts.Globals,java.util.Locale"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>

<bean:define id="urlEliminarTramite">
        <html:rewrite page="/protected/abandonarTramite.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<%--
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
			<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias"/>
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


	<% Esto viene de datosUsuario.jsp %>
	<script type="text/javascript">
	<!--
	function cancelarTramite( url )
	{
		if ( confirm ( '<bean:message key="cancelacionTramite.confirmacion" />' ) )
		{
			document.location.href=url;
		} 
	}
	-->
	</script>

	<div id="capsalUsuari">
	aaaaaaaaaaaa
	<!--  Acceso anonimo -->
	<logic:equal name="metodoAutenticacion" value="A">
		<bean:message key="datosUsuario.usuario" />: <strong><bean:message key="datosUsuario.anonimo" /></strong>
		<logic:present name="tramite"> 
			<bean:define id="urlGuardarClave">
					<html:rewrite page="/protected/guardarClave.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
			</bean:define>
			<bean:define id="pasoNoRetorno" name="tramite" property="pasoNoRetorno" /> 
			<bean:define id="idPersistencia" name="tramite" property="idPersistencia" /> 

			<logic:equal name="tramite" property="circuitoReducido" value="false">
				<logic:lessEqual name="tramite" property="pasoActual" value="<%= String.valueOf( pasoNoRetorno ) %>">
					<bean:message key="datosUsuario.claveTramite" />: <strong><bean:write name="tramite" property="idPersistencia"/></strong>
					 &nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;
					   <html:link styleClass="guardar" href="<%= urlGuardarClave + "&idPersistencia=" + idPersistencia %>"><bean:message key="datosUsuario.guardarClaveTextoEnlace" /></html:link>
					   <a href="#" class="cancelar" onclick="javascript:cancelarTramite( '<%= urlEliminarTramite %>' )" title="<bean:message key="datosUsuario.cancelarTramiteTextoEnlace" />"><bean:message key="datosUsuario.cancelarTramiteTextoEnlace" /></a>
				</logic:lessEqual>
			</logic:equal>
		</logic:present>
	</logic:equal>

	<!--  Acceso autenticado -->
	<logic:notEqual name="metodoAutenticacion" value="A">
		<logic:present name="datossesion">
			<bean:define id="datosSesion" name="datossesion" type="es.caib.sistra.model.DatosSesion" />
			<bean:message key="datosUsuario.usuario" />: <strong><%= datosSesion.getNombreCompletoUsuario() %></strong>. 
		</logic:present>	
		<logic:present name="tramite">
			<bean:define id="pasoNoRetorno" name="tramite" property="pasoNoRetorno" /> 
			<bean:define id="idPersistencia" name="tramite" property="idPersistencia" /> 
			<logic:lessEqual name="tramite" property="pasoActual" value="<%= String.valueOf( pasoNoRetorno ) %>">
				&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;<a href="#" class="cancelar" onclick="javascript:cancelarTramite( '<%= urlEliminarTramite %>' )" title="<bean:message key="datosUsuario.cancelarTramiteTextoEnlace" />"><bean:message key="datosUsuario.cancelarTramiteTextoEnlace" /></a>
			</logic:lessEqual>
		</logic:present>
	</logic:notEqual>

	</div>
--%>

<%-- HEADER --%>
<div class="header">
	<%-- Nombre del Organismo --%>
	<h1><bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/></h1>
	<%-- Titulo de la Aplicación --%>
	<h2><bean:message key="aplicacion.titulo"/></h2>
	<div>
		<script type="text/javascript">
			<!--
			function cancelarTramite( url )
			{
				if ( confirm ( '<bean:message key="cancelacionTramite.confirmacion" />' ) )
				{
					document.location.href=url;
				}
			}
			-->
		</script>
		<%-- Datos del Usuario --%>
		<ul class="dades">
		
			<!--  Acceso anonimo -->
			<logic:equal name="metodoAutenticacion" value="A">
				<li><bean:message key="datosUsuario.usuario" />: <strong><bean:message key="datosUsuario.anonimo" /></strong></li>
				<logic:present name="tramite">
					<bean:define id="pasoNoRetorno" name="tramite" property="pasoNoRetorno" />
					<bean:define id="idPersistencia" name="tramite" property="idPersistencia" />
					<logic:equal name="tramite" property="circuitoReducido" value="false">
						<logic:lessEqual name="tramite" property="pasoActual" value="<%= String.valueOf( pasoNoRetorno ) %>">
							<li>
								<bean:message key="datosUsuario.claveTramite" />: <strong><bean:write name="tramite" property="idPersistencia"/></strong>
							</li>
						</logic:lessEqual>
					</logic:equal>
				</logic:present>
			</logic:equal>

			<!--  Acceso autenticado -->
			<logic:notEqual name="metodoAutenticacion" value="A">
				<logic:present name="datossesion">
					<bean:define id="datosSesion" name="datossesion" type="es.caib.sistra.model.DatosSesion" />
					<li>
						<bean:message key="datosUsuario.usuario" />: <strong><%= datosSesion.getNombreCompletoUsuario() %></strong>
					</li>
				</logic:present>
			</logic:notEqual>
		</ul>
		
		<ul class="opcions">
			<logic:equal name="metodoAutenticacion" value="A">
				<logic:present name="tramite">
					<bean:define id="urlGuardarClave">
						<html:rewrite page="/protected/guardarClave.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
					</bean:define>
					<bean:define id="pasoNoRetorno" name="tramite" property="pasoNoRetorno" />
					<bean:define id="idPersistencia" name="tramite" property="idPersistencia" />
					<logic:equal name="tramite" property="circuitoReducido" value="false">
						<logic:lessEqual name="tramite" property="pasoActual" value="<%= String.valueOf( pasoNoRetorno ) %>">
								<li><html:link styleClass="desa" href="<%= urlGuardarClave + "&idPersistencia=" + idPersistencia %>"><bean:message key="datosUsuario.guardarClaveTextoEnlace" /></html:link></li>
						</logic:lessEqual>
					</logic:equal>
				</logic:present>
			</logic:equal>
			
			<li><a href="#" class="elimina"onclick="javascript:cancelarTramite( '<%= urlEliminarTramite %>' )" title="<bean:message key="datosUsuario.cancelarTramiteTextoEnlace" />"><bean:message key="datosUsuario.cancelarTramiteTextoEnlace" /></a></li>
		</ul>
		<h3><bean:write name="descripcion" /></h3>
	</div>
</div>

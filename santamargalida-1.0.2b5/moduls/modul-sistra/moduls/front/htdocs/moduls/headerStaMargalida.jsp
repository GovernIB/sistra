<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="org.apache.struts.Globals,java.util.Locale"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>

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
		
		<logic:present name="ID_INSTANCIA">
			<ul class="opcions">
				<!--  Acceso anonimo -->
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
				
				<bean:define id="urlEliminarTramite">
					<html:rewrite page="/protected/abandonarTramite.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
				</bean:define>
				<li><a href="#" class="elimina"onclick="javascript:cancelarTramite( '<%= urlEliminarTramite %>' )" title="<bean:message key="datosUsuario.cancelarTramiteTextoEnlace" />"><bean:message key="datosUsuario.cancelarTramiteTextoEnlace" /></a></li>
			</ul>
		</logic:present>
		<h3><bean:write name="descripcion" /></h3>
	</div>
</div>

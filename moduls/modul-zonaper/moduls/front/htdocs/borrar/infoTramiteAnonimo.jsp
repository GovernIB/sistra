<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.util.StringUtil" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="estadoTramite" name="estadoTramite" type="java.lang.String" />
		<!-- informacio -->
		<div id="info">
			<logic:notPresent name="tramiteNoExiste">
				<bean:define id="urlTramite" name="urlTramite" type="java.lang.String"/>
				<p><bean:message key="infoTramiteAnonimo.introduccion" /></p>
				<p></p>
				<h2><bean:message key="infoTramiteAnonimo.titulo" /></h2>
				<p class="alerta"><bean:message key="<%= "infoTramiteAnonimo.informacionEstado." + estadoTramite %>" arg0="<%= StringUtil.fechaACadena( ( java.util.Date ) request.getAttribute( "fechaCaducidad" ) ) %>"/></p>
				<table id="tramiteAnonimo" cellpadding="0" cellspacing="0">
				<tr>
					<th><bean:message key="infoTramiteAnonimo.label.titulo" /></th>
					<td><a href="<%= urlTramite %>" title="<bean:message key='<%= "infoTramiteAnonimo.enlace.titulo." + estadoTramite %>'/>"><bean:write name="descripcion" /></a></td>
				</tr>
				<tr>
					<th><bean:message key='<%= "infoTramiteAnonimo.label.fecha." + estadoTramite %>'/></th>
					<td><bean:write name="fechaModificacion" format="dd/MM/yyyy"/> <bean:message key="infoTramiteAnonimo.aLas" /> <bean:write name="fechaModificacion" format="HH:mm"/>h.</td>
				</tr>
				<logic:equal name="estadoTramite" value="C">
					<tr>
						<th><bean:message key="infoTramiteAnonimo.label.fechaLimite" /></th>
						<td><bean:write name="fechaCaducidad" format="dd/MM/yyyy"/></td>
					</tr>
				</logic:equal>
				</table>
			</logic:notPresent>
			<logic:present name="tramiteNoExiste">		
				<p class="alerta"><bean:message key="infoTramiteAnonimo.tramiteNoExistente" /></p>
			</logic:present>
			<p class="parrafFinal"><html:link action="/protected/inicioAnonimo" styleClass="tornar"><bean:message key="infoTramiteAnonimo.volver" /></html:link></p>
		</div>
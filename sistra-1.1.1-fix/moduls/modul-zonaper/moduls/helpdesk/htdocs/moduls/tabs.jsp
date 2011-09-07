<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="es.caib.zonaper.helpdesk.front.Constants" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="urlConfirmacion" type="java.lang.String">
	<html:rewrite href="/zonaperback/init.do" paramId="lang" paramName="<%= Globals.LOCALE_KEY  %>" paramProperty="language" paramScope="session"/>
</bean:define>
<table cellpadding="0" cellspacing="0" id="capTM">
	<tr>
		<td id="capMenu">
			<!--  OPCION 1: BUSQUEDA CLAVE DE TRAMITACION -->
			<logic:equal name="<%=Constants.OPCION_SELECCIONADA_KEY%>" value="<%=Constants.CLAVE_TAB%>">
				<span>
					<bean:message key="tabs.claveTramitacion"/>							
				</span>
			</logic:equal>
			<logic:notEqual name="<%=Constants.OPCION_SELECCIONADA_KEY%>" value="<%=Constants.CLAVE_TAB%>">
				<html:link action="claveTramitacion">
					<bean:message key="tabs.claveTramitacion"/>							
				</html:link>	
			</logic:notEqual>

			<!--  OPCION 2: ESTADO DEL TRAMITE -->
			<logic:equal name="<%=Constants.OPCION_SELECCIONADA_KEY%>" value="<%=Constants.ESTADO_TAB%>">
				<span>
					<bean:message key="tabs.estadoTramite"/>							
				</span>
			</logic:equal>
			<logic:notEqual name="<%=Constants.OPCION_SELECCIONADA_KEY%>" value="<%=Constants.ESTADO_TAB%>">
				<html:link action="estadoTramite">
					<bean:message key="tabs.estadoTramite"/>							
				</html:link>	
			</logic:notEqual>

		<!--  OPCION 3: PAGOS TELEMATICOS -->
			<logic:equal name="<%=Constants.OPCION_SELECCIONADA_KEY%>" value="<%=Constants.PAGO_TAB%>">
				<span>
					<bean:message key="tabs.pagosTelematicos"/>							
				</span>
			</logic:equal>
			<logic:notEqual name="<%=Constants.OPCION_SELECCIONADA_KEY%>" value="<%=Constants.PAGO_TAB%>">
				<html:link action="pagosTelematicos">
					<bean:message key="tabs.pagosTelematicos"/>							
				</html:link>	
			</logic:notEqual>
			
			<!--  OPCION 4: CONSULTA AUDITORIA DE TRAMITACION -->
			<logic:equal name="<%=Constants.OPCION_SELECCIONADA_KEY%>" value="<%=Constants.AUDITORIA_TAB%>">
				<span>
						<bean:message key="tabs.consultaAuditoria"/>	
				</span>
			</logic:equal>
			<logic:notEqual name="<%=Constants.OPCION_SELECCIONADA_KEY%>" value="<%=Constants.AUDITORIA_TAB%>">
					<html:link action="auditoriaTramitacion">
						<bean:message key="tabs.consultaAuditoria"/>	
					</html:link>		
			</logic:notEqual>
		
			<!--  OPCION 5: USUARIOS -->
			<logic:equal name="<%=Constants.OPCION_SELECCIONADA_KEY%>" value="<%=Constants.USUARIOS_TAB%>">
				<span>
						<bean:message key="tabs.usuarios"/>	
				</span>
			</logic:equal>
			<logic:notEqual name="<%=Constants.OPCION_SELECCIONADA_KEY%>" value="<%=Constants.USUARIOS_TAB%>">
					<html:link action="usuarios">
						<bean:message key="tabs.usuarios"/>	
					</html:link>		
			</logic:notEqual>
		
			<!--  OPCION 6: USUARIOS -->
			<logic:equal name="<%=Constants.OPCION_SELECCIONADA_KEY%>" value="<%=Constants.BACKUP_TAB%>">
				<span>
						<bean:message key="tabs.backup"/>	
				</span>
			</logic:equal>
			<logic:notEqual name="<%=Constants.OPCION_SELECCIONADA_KEY%>" value="<%=Constants.BACKUP_TAB%>">
					<html:link action="backup">
						<bean:message key="tabs.backup"/>	
					</html:link>		
			</logic:notEqual>
		
		
		</td>
	</tr>
</table>


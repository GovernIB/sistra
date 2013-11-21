<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ page import="es.caib.zonaper.modelInterfaz.ConstantesZPE"%>
<bean:define id="sesion" name="<%=es.caib.zonaper.front.Constants.DATOS_SESION_KEY%>" type="es.caib.zonaper.model.DatosSesion" />
<bean:define id="firstPage" value="0" />
				
			<h1>
				<bean:message key="alertas.titulo" />
			</h1>
						
			<p>
				<logic:equal name="alertasSmsZonaPersonal" value="true">
					<bean:message key="alertas.instrucciones.emailSms" />
				</logic:equal>
				<logic:equal name="alertasSmsZonaPersonal" value="false">
					<bean:message key="alertas.instrucciones.email" />
				</logic:equal>
			</p>

			<html:form action="/protected/actualizarAlertas" styleId="formConfirmacion">
				<div class="element">
					<span class="etiqueta"><bean:message key="alertas.alertasTramitacion" /></span>
					<div class="control">
						<ul>
							<li><label for="habilitarAvisosExpediente"><html:checkbox property="habilitarAvisosExpediente" styleId="habilitarAvisosExpediente"/><bean:message key="alertas.alertasTramitacion.expedientes" /></label></li>
						</ul>
					</div>
				</div>				
				<logic:equal name="es.caib.zonaper.front.DATOS_SESION" property="perfilAcceso" scope="session" value="<%=ConstantesZPE.DELEGACION_PERFIL_ACCESO_CIUDADANO%>">		
				<div class="botonera">
					<button type="submit" tabindex="9"><bean:message key="alertas.guardar"/></button>
				</div>				
				</logic:equal>
				<logic:equal name="es.caib.zonaper.front.DATOS_SESION" property="perfilAcceso" scope="session" value="<%=ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO%>">		
					<p class="alerta">
						<bean:message key="alertas.noGuardarDelegado" />
					</p>
				</logic:equal>				
				
			</html:form>


<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<bean:define id="sesion" name="<%=es.caib.zonaper.front.Constants.DATOS_SESION_KEY%>" type="es.caib.zonaper.model.DatosSesion" />
<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>

<bean:define id="urlEnlaceNotificacionExterna" name="notificacionesExternas" type="java.lang.String"/>
<bean:define id="infoNotificacionsNoSistra" type="java.util.Map" name="infoNotificacionsNoSistra"/>

			<%
				String info = infoNotificacionsNoSistra.get(lang).toString();
			if (!info.isEmpty()){
			%>
				<div class="missatge">
					<h3>
						<b><bean:message key="menuAutenticado.mensaje.importante"/>:</b>
					</h3>
					<p><%=info%></p>
				</div>
			<%}%>
			
			<!-- titol -->
			<h1>
				<bean:message key="menuAutenticado.encabezamiento" />
			</h1>
			<!-- /titol -->
			
			<!-- meues dades -->
			<div class="modul">
				<h2><bean:message key="menuAutenticado.misDatos" /></h2>
				<ul>
					<li><html:link action="/protected/mostrarDatosPersonales"><bean:message key="menuAutenticado.misDatos.datosPersonales"/></html:link></li>
					<logic:equal name="habilitarAlertas" value="true">
					<li><html:link action="/protected/mostrarAlertas"><bean:message key="menuAutenticado.misDatos.alertas"/></html:link></li>
					</logic:equal>
				</ul>
			</div>
			<!-- /meues dades -->
			
			<!-- tramits -->
			<div class="modul marc">
				<h2><bean:message key="menuAutenticado.misTramites" /></h2>
				<ul>
					<li><html:link action="/protected/tramitesSinEnviar"><bean:message key="menuAutenticado.misTramites.tramitesSinEnviar" /></html:link></li>
					<li><html:link action="/protected/estadoExpedientes"><bean:message key="menuAutenticado.misTramites.estadoTramites" /></html:link></li>
					<logic:notEmpty name="notificacionesExternas">
						<li><html:link href="<%=urlEnlaceNotificacionExterna%>"><bean:message key="menuAutenticado.misTramites.notificacionesExternas"/></html:link></li>
					</logic:notEmpty>
				</ul>
			</div>
			<!-- /tramits -->
			
			<!-- delegaciones --> 
			<logic:equal name="sesion" property="permitirDelegacion" value="S">
		 	<div class="modul marc">
				<h2><bean:message key="menuAutenticado.misDelegaciones"/></h2>
				<ul>
					<li><html:link action="/protected/mostrarDelegaciones"><bean:message key="menuAutenticado.misDelegaciones.delegaciones" /></html:link></li>
					<li><html:link action="/protected/mostrarBandejaFirma"><bean:message key="menuAutenticado.misDelegaciones.bandejaFirma" /></html:link></li>						
				</ul>
			</div>
			</logic:equal>
			
		

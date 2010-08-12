<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="sesion" name="<%=es.caib.zonaper.front.Constants.DATOS_SESION_KEY%>" type="es.caib.zonaper.model.DatosSesion" />
<bean:define id="firstPage" value="0" />
<!-- continguts -->
		<div id="continguts">
				
			<h1>
				<bean:message key="alertas.titulo" />
			</h1>
						
			<p><bean:message key="alertas.instrucciones" /></p>

			<html:form action="/protected/actualizarAlertas" styleId="formConfirmacion">
				<div class="element">
					<span class="etiqueta"><bean:message key="alertas.alertasTramitacion" /></span>
					<div class="control">
						<ul>
							<li><label for="novaNotificacio"><html:checkbox property="habilitarAvisosExpediente" /><bean:message key="alertas.alertasTramitacion.expedientes" /></label></li>
						</ul>
					</div>
				</div>				
				<div class="botonera">
					<button type="submit" tabindex="9"><bean:message key="alertas.guardar"/></button>
				</div>				
			</html:form>

		</div>
		<!-- /continguts -->
	</div>
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
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
					<li><html:link action="/protected/mostrarAlertas"><bean:message key="menuAutenticado.misDatos.alertas"/></html:link></li>
				</ul>
			</div>
			<!-- /meues dades -->
			
			<!-- tramits -->
			<div class="modul marc">
				<h2><bean:message key="menuAutenticado.misTramites" /></h2>
				<ul>
					<li><html:link action="/protected/tramitesSinEnviar"><bean:message key="menuAutenticado.misTramites.tramitesSinEnviar" /></html:link></li>
					<li><html:link action="/protected/estadoExpedientes"><bean:message key="menuAutenticado.misTramites.estadoTramites" /></html:link></li>						
				</ul>
			</div>
			<!-- /tramits -->
			
			<!-- subscripcions 
		 	<div class="modul marc">
				<h2><bean:message key="menuAutenticado.misSuscripciones"/></h2>
				<ul>
					<li><bean:message key="menuAutenticado.misSuscripciones.proximamente" /></li>
				</ul>
			</div>
			-->
			<!-- /subscripcions -->
		
		</div>
		<!-- /continguts -->
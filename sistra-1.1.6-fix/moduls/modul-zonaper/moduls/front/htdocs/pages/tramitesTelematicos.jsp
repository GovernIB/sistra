<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
		<!-- titol -->
			<h1>
				<bean:message key="tramitesTelematicos.encabezamiento" />
			</h1>
			<!-- /titol -->
			
			<!-- meues dades -->
			<div class="modul">
				<h2><bean:message key="tramitesTelematicos.encabezado1" /></h2>
				<ul>
					<li><html:link action="/protected/datosPersonales"><bean:message key="tramitesTelematicos.datosPersonales"/></html:link>
					<li><html:link action="/protected/alertasSMS"><bean:message key="tramitesTelematicos.alertasSMS"/></html:link>
					<li><a href="dades_alertes.html"></a></li>
					<!-- <li><a href="dades_personalitzacio.html">Personalització del portal</a></li> -->
				</ul>
			</div>
			<!-- /meues dades -->
			
			<!-- tramits -->
			<div class="modul marc">
				<h2><bean:message key="tramitesTelematicos.encabezado2" /></h2>
				<ul>
					<li>
						<logic:greaterThan name="numeroTramitesNoFinalizados" value="0">
							<html:link action="/protected/tramitesSinEnviar"><bean:message key="tramitesTelematicos.tramitesSinEnviar" /></html:link>
						</logic:greaterThan>
					</li>
					
					<li>
						<logic:greaterThan name="numeroTramitesFinalizados" value="0">
							<html:link action="/protected/tramitesEstado"><bean:message key="tramitesTelematicos.tramitesEstado" /></html:link>
						</logic:greaterThan>
					</li>
				</ul>
			</div>
			<!-- /tramits -->
			
			<!-- subscripcions -->
			<!-- <div class="modul marc">
				<h2><bean:message key="tramitesTelematicos.encabezado3"/></h2>
				<ul>
					<li><a href="subscripcions_consultar.html">Consultar dades actuals</a></li>
					<li><a href="subscripcions_modificar.html">Modificar</a></li>
				</ul>
			</div>-->
			<!-- /subscripcions -->
		

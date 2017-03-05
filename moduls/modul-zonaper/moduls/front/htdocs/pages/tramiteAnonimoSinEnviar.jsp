<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<bean:define id="tramitePersistente" name="tramitePersistente" type="es.caib.zonaper.modelInterfaz.TramitePersistentePAD"/>
<bean:define id="locale" name="org.apache.struts.action.LOCALE" scope="session" />
<bean:define id="language" name="org.apache.struts.action.LOCALE" scope="session" />

<bean:define id="contextoRaizSistra" name="<%=es.caib.zonaper.front.Constants.CONTEXTO_RAIZ%>" type="java.lang.String"/>

<bean:define id="urlTramitacion" type="java.lang.String">
	<html:rewrite href="<%=contextoRaizSistra + "/sistrafront/inicio"%>" paramId="language" paramName="language" paramProperty="language" />
</bean:define>

		<!-- titol -->
		<h1>
			<bean:message key="tramiteAnonimoSinEnviar.titulo" />
		</h1>
		<!-- /titol -->
		<!-- informacio -->
		<div id="info">
			<p><bean:message key="tramiteAnonimoSinEnviar.texto" /></p>
			
			<table class="llistatElements">
			<thead>
				<tr>			
					<th><bean:message key="tramiteAnonimoSinEnviar.idioma" /></th>
					<th><bean:message key="tramiteAnonimoSinEnviar.fechaModificacion" /></th>
					<th><bean:message key="tramiteAnonimoSinEnviar.fechaLimite" /></th>
					<th><bean:message key="tramiteAnonimoSinEnviar.tramite" /></th>
				</tr>
			</thead>
			<tr onmouseover="selecItemTabla(this);">								
				<td>
					<bean:message key="<%=\"tramiteAnonimoSinEnviar.idioma.\" + tramitePersistente.getIdioma()%>"/>
				</td>												
				<td><bean:write name="tramitePersistente" property="fechaModificacion" format="dd/MM/yyyy hh:mm"/></td>												
				<td><bean:write name="tramitePersistente" property="fechaCaducidad" format="dd/MM/yyyy"/></td>												
				<td>					
						<!--  Descripcion del trámite con link si puede continuarse -->
						<html:link href="<%= urlTramitacion + \"&modelo=\" + tramitePersistente.getTramite() + \"&version=\" + tramitePersistente.getVersion() %>" paramId="idPersistencia" paramName="tramitePersistente" paramProperty="idPersistencia">
								<bean:write name="tramitePersistente" property="descripcion"/>								
						</html:link>
				</td>
			</tr>									
			</table>		
			
		</div>

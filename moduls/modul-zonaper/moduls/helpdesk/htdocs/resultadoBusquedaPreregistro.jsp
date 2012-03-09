<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.zonaper.helpdesk.front.Constants" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript">
function mostrarDatosTramite(clave){
	document.getElementById('claveTramite').value = clave;
	document.detalleTramiteForm.submit();
}
</script>		
		<!-- documentación a aportar -->
		<tiles:insert name=".formularioBusquedaPreregistro" />
		
		<div class="continguts">
		
		<!-- resultats -->
		<h2><bean:message key="resultadoBusqueda.titulo"/></h2>
		</p>
		<logic:notEmpty name="estado">
			<bean:define id="estado" name="estado" type="java.lang.String" />
			<p class="alerta"><bean:message key="<%= "resultadoBusquedaEstado.informacionEstado." + estado %>" /></p>
		</logic:notEmpty>
		<logic:empty name="estado">
			<logic:empty name="lstTramites">
				<p><bean:message key="resultadoBusqueda.noEncontrados"/></p>
			</logic:empty>
			<logic:notEmpty name="lstTramites">
			<bean:size id="arraySize" name="lstTramites"/>
			<p>
				<bean:message key="resultadoBusqueda.numero.inicio"/> <strong><bean:write name="arraySize"/></strong> <bean:message key="resultadoBusqueda.numero.fin"/>
			</p>
			<html:form action="detalleTramitePreregistro">
			<html:hidden styleId="claveTramite" property="claveTramite"/>
			<table cellpadding="8" cellspacing="0" class="resultats">
			<thead>
			<tr>
				<th><bean:message key="resultadoBusqueda.numero"/></th>
				<th><bean:message key="resultadoBusqueda.tramite"/></th>
				<th><bean:message key="resultadoBusqueda.version"/></th>
				<th><bean:message key="resultadoBusqueda.idioma"/></th>
				<th><bean:message key="resultadoBusqueda.descripcion"/></th>
				<th><bean:message key="resultadoBusqueda.nivelAutenticacion"/></th>
				<th><bean:message key="resultadoBusqueda.nifRepresentante"/></th>
				<th><bean:message key="resultadoBusqueda.nombreRepresentante"/></th>
				<th><bean:message key="resultadoBusqueda.fechaCaducidad"/></th>
			</tr>	
			</thead>
			<tbody>	
				<logic:iterate id="tramite" name="lstTramites">
				
				<tr onmouseover="selecItemTabla(this);" onclick="mostrarDatosTramite('<bean:write name="tramite" property="idPersistencia"/>');" class="nou">
					<td><bean:write name="tramite" property="numeroPreregistro"/></td>
					<td><bean:write name="tramite" property="tramite"/></td>
					<td><bean:write name="tramite" property="version"/></td>
					<td>
						<logic:equal name="tramite" property="idioma" value='<%= Constants.CATALAN %>'>
							<bean:message key="resultadoBusqueda.idioma.catalan"/>	
						</logic:equal>
						<logic:equal name="tramite" property="idioma" value='<%= Constants.CASTELLANO %>'>
							<bean:message key="resultadoBusqueda.idioma.castellano"/>	
						</logic:equal>
					</td>
					<td><bean:write name="tramite" property="descripcionTramite"/></td>
					<td>
						<logic:equal name="tramite" property="nivelAutenticacion" value='<%= String.valueOf(Constants.MODO_AUTENTICACION_USUARIO_PWD) %>'>
							<bean:message key="resultadoBusqueda.usuarioPassword"/>	
						</logic:equal>
						<logic:equal name="tramite" property="nivelAutenticacion" value='<%= String.valueOf(Constants.MODO_AUTENTICACION_CERTIFICADO) %>'>
							<bean:message key="resultadoBusqueda.certificado"/>	
						</logic:equal>
						<logic:equal name="tramite" property="nivelAutenticacion" value='<%= String.valueOf(Constants.MODO_AUTENTICACION_ANONIMO) %>'>
							<bean:message key="resultadoBusqueda.anonimo"/>	
						</logic:equal>
					</td>
					<td><bean:write name="tramite" property="nifRepresentante"/></td>
					<td><bean:write name="tramite" property="nombreRepresentante"/></td>											
					<td><bean:write name="tramite" property="fechaCaducidad" format="dd/MM/yyyy HH:mm"/></td>
				</tr>
				</logic:iterate>														
			</tbody>
		   </table>
		   </html:form>
		   <!-- ajuda columna -->
			<script type="text/javascript">
			<!--
				ajudaColumnes = new Array();
				ajudaColumnes[0] = "<bean:message key="resultadoBusqueda.ayuda.estado"/>";
			-->
			</script>
			<div id="ajudaColumna"></div>
			<!-- /ajuda columna -->
			</logic:notEmpty>
		</logic:empty>
	</div>
	<!-- /continguts -->
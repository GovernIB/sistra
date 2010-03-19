<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.zonaper.helpdesk.front.Constants"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
		<!-- documentación a aportar -->
		<tiles:insert name=".formularioBusqueda" />
	
		<div class="continguts">
		
		<!-- resultats -->
		<h2><bean:message key="resultadoBusqueda.titulo"/></h2>
		</p>
		<logic:empty name="logs">
			<p><bean:message key="resultadoBusqueda.noEncontrados"/></p>
		</logic:empty>
		<logic:notEmpty name="logs">
		<bean:size id="arraySize" name="logs"/>
		<p>
			<bean:message key="resultadoBusqueda.numero.inicio"/> <strong><bean:write name="arraySize"/></strong> <bean:message key="resultadoBusqueda.numero.fin"/>
		</p>
		<table cellpadding="0" cellspacing="0" class="resultats">
		<thead>
			<tr>
				<th class="estado"><bean:message key="resultadoBusqueda.tipoEvento"/></th>	
				<th><bean:message key="resultadoBusqueda.fecha"/></th>
				<th><bean:message key="resultadoBusqueda.clavePersistencia"/></th>
				<th><bean:message key="resultadoBusqueda.tramite"/></th>
				<th><bean:message key="resultadoBusqueda.idioma"/></th>
				<th><bean:message key="resultadoBusqueda.clave"/></th>
			</tr>
		</thead>
		<tbody>
			<logic:iterate id="log" name="logs">
			<tr onmouseover="selecItemTabla(this);"  class="nou">
				<td class="estado"><bean:write name="log" property="tipoEvento"/></td>
				<td><bean:write name="log" property="hora" format="dd/MM/yyyy HH:mm"/></td>
				<td><bean:write name="log" property="clavePersistencia"/></td>
				<td><bean:write name="log" property="descTramite"/></td>
				<td>
					<logic:equal name="log" property="idioma" value='<%= Constants.CATALAN %>'>
						<bean:message key="resultadoBusqueda.idioma.catalan"/>	
					</logic:equal>
					<logic:equal name="log" property="idioma" value='<%= Constants.CASTELLANO %>'>
						<bean:message key="resultadoBusqueda.idioma.castellano"/>	
					</logic:equal>
				</td>
				<td><bean:write name="log" property="clave"/></td>
			</tr>
			</logic:iterate>														
		</tbody>
		</table>
		   <!-- ajuda columna -->
		<script type="text/javascript">
		<!--
			ajudaColumnes = new Array();
		-->
		</script>
		<div id="ajudaColumna"></div>
		<!-- /ajuda columna -->

		</logic:notEmpty>
		
	</div>
	<!-- /continguts -->

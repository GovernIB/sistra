<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.zonaper.helpdesk.front.Constants"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
		<!-- documentación a aportar -->
		<tiles:insert name=".formularioBusquedaClave" />
		
		<div class="continguts">
		
		<!-- resultats -->
		<h2><bean:message key="resultadoBusqueda.titulo"/></h2>
		<logic:present name="superadoMaximo">
		<p class="alerta">
			<bean:message key="resultadoBusqueda.superadoMaximo"/>
		</p>
		</logic:present>
		<logic:notPresent name="superadoMaximo">
		<logic:empty name="lstTramites">
			<p><bean:message key="resultadoBusqueda.noEncontrados"/></p>
		</logic:empty>
		<logic:notEmpty name="lstTramites">
		<bean:size id="arraySize" name="lstTramites"/>
		<p>
			<bean:message key="resultadoBusqueda.numero.inicio"/> <strong><bean:write name="arraySize"/></strong> <bean:message key="resultadoBusqueda.numero.fin"/>
		</p>
		<table cellpadding="8" cellspacing="0" class="resultats">
		<thead>
			<tr>
			<th class="estado"><bean:message key="resultadoBusqueda.estado"/></th>	
			<th><bean:message key="resultadoBusqueda.fecha"/></th>
			<th><bean:message key="resultadoBusqueda.clavePersistencia"/></th>
			<th><bean:message key="resultadoBusqueda.tramite"/></th>
			<th><bean:message key="resultadoBusqueda.idioma"/></th>
			</tr>
		</thead>
		<tbody>
			<logic:iterate id="tramite" name="lstTramites">
			<tr onmouseover="selecItemTabla(this);"  class="nou">
				<td class="estado">
					<logic:equal name="tramite" property="estado" value='<%= Constants.NO_FINALIZADO %>'>
						<bean:message key="resultadoBusqueda.estado.noFinalizado"/>	
					</logic:equal>
					<logic:equal name="tramite" property="estado" value='<%= Constants.PENDIENTE_CONFIRMACION %>'>
						<bean:message key="resultadoBusqueda.estado.pendienteConfirmacion"/>	
					</logic:equal>
					<logic:equal name="tramite" property="estado" value='<%= Constants.FINALIZADO %>'>
						<bean:message key="resultadoBusqueda.estado.finalizado"/>	
					</logic:equal>
					<logic:equal name="tramite" property="estado" value='<%= Constants.BORRADO %>'>
						<bean:message key="resultadoBusqueda.estado.borrado"/>	
					</logic:equal>
				</td>
				<td><bean:write name="tramite" property="fecha" format="dd/MM/yyyy HH:mm"/></td>
				<td><strong><bean:write name="tramite" property="idPersistencia"/></strong></td>
				<td><bean:write name="tramite" property="tramite"/></td>
				<td>
					<logic:equal name="tramite" property="idioma" value='<%= Constants.CATALAN %>'>
						<bean:message key="resultadoBusqueda.idioma.catalan"/>	
					</logic:equal>
					<logic:equal name="tramite" property="idioma" value='<%= Constants.CASTELLANO %>'>
						<bean:message key="resultadoBusqueda.idioma.castellano"/>	
					</logic:equal>
				</td>
			</tr>
			</logic:iterate>														
		
	   </table>
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
		</logic:notPresent>

	</div>
	<!-- /continguts -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.mobtratel.modelInterfaz.ConstantesMobtratel" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

 		<!-- Formulario búsqueda -->
		<tiles:insert name=".formularioBusquedaEstadisticasSMS" />
	
		<!--  Resultados búsqueda -->	
		<div id="resultatsRecerca">
		<h3><bean:message key="resultadoBusquedaEstadisticasSMS.titulo"/></h3>		
		<logic:empty name="estadisticas">
			<p><bean:message key="resultadoBusquedaEstadisticasSMS.noEncontrados"/></p>
		</logic:empty>
		<logic:notEmpty name="estadisticas">
			<table cellpadding="8" cellspacing="0" id="tablaResultats">
			<tr>
				<th><bean:message key="resultadoBusquedaEstadisticasSMS.mes"/></th>	
				<th><bean:message key="resultadoBusquedaEstadisticasSMS.cuenta"/></th>
				<th><bean:message key="resultadoBusquedaEstadisticasSMS.procedimiento"/></th>
				<th><bean:message key="resultadoBusquedaEstadisticasSMS.numSms"/></th>				
			</tr>				
				<logic:iterate id="estadistica" name="estadisticas">
					<bean:define id="numMes" name="estadistica" property="mes" type="java.lang.Integer"/>
					<tr>				
						<td><bean:message key="<%=\"mes.\" + (numMes.intValue() - 1)%>"/>  </td>
						<td><bean:write name="estadistica" property="cuenta" /></td>
						<td><bean:write name="estadistica" property="idProcedimiento" /></td>
						<td><bean:write name="estadistica" property="sms"/></td>				
					</tr>
				</logic:iterate>														
			</table> 
		</logic:notEmpty>
	</div>
	<p class="tornarArrere"><strong><a href="/mobtratelfront/init.do"><bean:message key="editarEnvio.volver"/></a></strong></p>
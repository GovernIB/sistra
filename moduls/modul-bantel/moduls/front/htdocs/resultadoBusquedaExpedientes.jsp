<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.bantel.modelInterfaz.ConstantesBTE"%>
<%@ page import="es.caib.zonaper.modelInterfaz.ConstantesZPE"%>
<%@ page import="es.caib.xml.registro.factoria.ConstantesAsientoXML" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

	<!-- Formulario búsqueda -->
<tiles:insert name=".formularioBusquedaExpedientes" />

<logic:present name="page">

<!--  Resultados búsqueda -->	
<div id="resultatsRecerca">
	<h3><bean:message key="resultadoBusquedaExpedientes.titulo"/></h3>		
	<logic:empty name="page" property="list">
		<p><bean:message key="resultadoBusquedaExpedientes.noEncontrados"/></p>
	</logic:empty>
	<logic:notEmpty name="page" property="list">
	<table cellpadding="8" cellspacing="10" id="tablaResultats">
	<tr>
		<th><bean:message key="resultadoBusquedaExpedientes.fecha"/></th>
		<th><bean:message key="resultadoBusquedaExpedientes.idExpediente"/></th>	
		<th><bean:message key="resultadoBusquedaExpedientes.nif"/></th>		
		<!-- 
		<th><bean:message key="resultadoBusquedaExpedientes.procedimiento"/></th>
		 -->
		<th><bean:message key="resultadoBusquedaExpedientes.estado"/></th>
		<th><bean:message key="resultadoBusquedaExpedientes.numeroEntrada"/></th>
	</tr>				
		<bean:define id="numeroPagina" name="page" property="numeroPagina" type="java.lang.Integer"/>
		<logic:iterate id="pagina" name="page" property="list">
		<tr onmouseover="selecItemTabla(this);" onclick="detalleExpediente('<bean:write name="pagina" property="identificadorExpediente"/>','<bean:write name="pagina" property="unidadAdministrativa"/>', '<bean:write name="pagina" property="claveExpediente"/>');" class="nou" title="<bean:message key="resultadoBusquedaExpedientes.verDetalleExpediente"/>">
			<td><bean:write name="pagina" property="fechaInicio" format="dd/MM/yyyy HH:mm"/></td>
			<td><bean:write name="pagina" property="identificadorExpediente" /></td>
			<td><bean:write name="pagina" property="nifRepresentante" /></td>
			<!-- 
			<td><bean:write name="pagina" property="identificadorProcedimiento" /></td>
			 -->
			<td>
				<logic:equal name="pagina" property="estado" value="<%=ConstantesZPE.ESTADO_AVISO_PENDIENTE%>">
					<bean:message key="resultadoBusquedaExpedientes.estado.avisoPendiente"/>
				</logic:equal>				
				<logic:equal name="pagina" property="estado" value="<%=ConstantesZPE.ESTADO_AVISO_RECIBIDO%>">
					<bean:message key="resultadoBusquedaExpedientes.estado.avisoRecibido"/>
				</logic:equal>
				<logic:equal name="pagina" property="estado" value="<%=ConstantesZPE.ESTADO_NOTIFICACION_PENDIENTE%>">
					<bean:message key="resultadoBusquedaExpedientes.estado.notificacionPendiente"/>
				</logic:equal>
				<logic:equal name="pagina" property="estado" value="<%=ConstantesZPE.ESTADO_NOTIFICACION_RECIBIDA%>">
					<bean:message key="resultadoBusquedaExpedientes.estado.notificacionRecibida"/>
				</logic:equal>
				<logic:equal name="pagina" property="estado" value="<%=ConstantesZPE.ESTADO_NOTIFICACION_RECHAZADA%>">
					<bean:message key="resultadoBusquedaExpedientes.estado.notificacionRechazada"/>
				</logic:equal>
				<logic:equal name="pagina" property="estado" value="<%=ConstantesZPE.ESTADO_SOLICITUD_ENVIADA%>">
					<bean:message key="resultadoBusquedaExpedientes.estado.solicitudEnviada"/>
				</logic:equal>
				<logic:equal name="pagina" property="estado" value="<%=ConstantesZPE.ESTADO_SOLICITUD_ENVIADA_PENDIENTE_DOCUMENTACION_PRESENCIAL%>">
					<bean:message key="resultadoBusquedaExpedientes.estado.solicitudPendienteDocumentacion"/>
				</logic:equal>
			</td>		
			<td><bean:write name="pagina" property="numeroEntradaBTE" /></td>				
		</tr>
		</logic:iterate>														
	</table> 
	<p id="barraNav">
		&lt;
		<logic:equal name="page" property="previousPage" value="true">
			<a href="javascript:document.forms['busquedaExpedientesForm'].pagina.value='<%= String.valueOf ( numeroPagina.intValue() - 1 )%>'; document.forms['busquedaExpedientesForm'].submit();" title=""> <bean:message key="resultadoBusquedaExpedientes.anterior"/></a>
		</logic:equal>
		<logic:equal name="page" property="previousPage" value="false">
		<bean:message key="resultadoBusquedaExpedientes.anterior"/> &nbsp; 
		</logic:equal>
		|
		<logic:equal name="page" property="nextPage" value="true">
			<a href="javascript:document.forms['busquedaExpedientesForm'].pagina.value='<%= String.valueOf ( numeroPagina.intValue() + 1 )%>'; document.forms['busquedaExpedientesForm'].submit();" title=""><bean:message key="resultadoBusquedaExpedientes.proxima"/> </a>
		</logic:equal>
		<logic:equal name="page" property="nextPage" value="false">
		 <bean:message key="resultadoBusquedaExpedientes.proxima"/> &nbsp; 
		</logic:equal>
		&gt;
	</p>
	</logic:notEmpty>
</div>

</logic:present>
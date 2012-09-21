<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.mobtratel.model.Envio"%>
<%@ page import="es.caib.mobtratel.model.Cuenta"%>
<%@ page import="es.caib.mobtratel.modelInterfaz.ConstantesMobtratel" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

 		<!-- Formulario búsqueda -->
		<tiles:insert name=".formularioBusqueda" />
	
		<!--  Resultados búsqueda -->	
		<div id="resultatsRecerca">
		<h3><bean:message key="resultadoBusqueda.titulo"/></h3>		
		<logic:empty name="page">
			<p><bean:message key="resultadoBusqueda.noEncontrados"/></p>
		</logic:empty>
		<logic:notEmpty name="page">
		<table cellpadding="8" cellspacing="0" id="tablaResultats">
		<tr>
			<th class="estado"><bean:message key="resultadoBusqueda.estado"/></th>	
			<th><bean:message key="resultadoBusqueda.cuenta"/></th>
			<th><bean:message key="resultadoBusqueda.nombre"/></th>
			<th><bean:message key="resultadoBusqueda.fechaRegistro"/></th>
			<th><bean:message key="resultadoBusqueda.tipo"/></th>
			<th><bean:message key="resultadoBusqueda.fechaProgramacion"/></th>
			<th><bean:message key="resultadoBusqueda.fecha"/></th>
			<th><bean:message key="resultadoBusqueda.fechaCaducidad"/></th>
		</tr>				
			<bean:define id="numeroPagina" name="page" property="page" type="java.lang.Integer"/>
			<logic:iterate id="pagina" name="page" property="list">
			<tr onmouseover="selecItemTabla(this);" onclick="detalleEnvio('<bean:write name="pagina" property="codigo"/>');" class="nou" title="<bean:message key="resultadoBusqueda.verDetalleEnvio"/>">
				<td class="estado">
					<!--  Icono enviado / no enviado -->
					<logic:equal name="pagina" property="estado" value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_ERROR)%>">
						<img src="imgs/icones/form_procesado_error.gif" title="<bean:message key="resultadoBusqueda.enviadoError"/>"/>
					</logic:equal>
					<logic:equal name="pagina" property="estado" value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_ENVIADO)%>">
						<img src="imgs/icones/form_procesado_si.gif" title="<bean:message key="resultadoBusqueda.enviado"/>"/>
					</logic:equal>
					<logic:equal name="pagina" property="estado" value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_PENDIENTE)%>">
						<img src="imgs/icones/form_procesado_no.gif" title="<bean:message key="resultadoBusqueda.noEnviado"/>"/>
					</logic:equal>
					<logic:equal name="pagina" property="estado" value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_CANCELADO)%>">
						<img src="imgs/icones/document_parat.gif" title="<bean:message key="resultadoBusqueda.cancelado"/>"/>
					</logic:equal>
					<logic:equal name="pagina" property="estado" value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_PROCESANDO)%>">
						<img src="imgs/icones/documento_enviandose.gif" title="<bean:message key="resultadoBusqueda.enviandose"/>"/>
					</logic:equal>
				</td>				
				<td><bean:write name="pagina" property="cuenta.nombre" /></td>
				<td><bean:write name="pagina" property="nombre" /></td>
				<td><bean:write name="pagina" property="fechaRegistro" format="dd/MM/yyyy HH:mm" /></td>
				<td>
					<logic:equal name="pagina" property="inmediato" value="true">
						<bean:message key="resultadoBusqueda.tipoInmediato"/>
					</logic:equal>
					<logic:equal name="pagina" property="inmediato" value="false">
						<bean:message key="resultadoBusqueda.tipoProgramado"/>
					</logic:equal>
					
				</td>
				<td><bean:write name="pagina" property="fechaProgramacionEnvio" format="dd/MM/yyyy HH:mm" /></td>
				<td><bean:write name="pagina" property="fechaEnvio" format="dd/MM/yyyy HH:mm" /></td>
				<td><bean:write name="pagina" property="fechaCaducidad" format="dd/MM/yyyy HH:mm" /></td>
				
			</tr>
			</logic:iterate>														
		</table> 
		<p id="barraNav">
			&lt;
			<logic:equal name="page" property="previousPage" value="true">
				<a href="javascript:document.forms['busquedaEnviosForm'].pagina.value='<%= String.valueOf ( numeroPagina.intValue() - 1 )%>'; document.forms['busquedaEnviosForm'].submit();" title=""> <bean:message key="resultadoBusqueda.anterior"/></a>
			</logic:equal>
			<logic:equal name="page" property="previousPage" value="false">
			<bean:message key="resultadoBusqueda.anterior"/> &nbsp; 
			</logic:equal>
			|
			<logic:equal name="page" property="nextPage" value="true">
				<a href="javascript:document.forms['busquedaEnviosForm'].pagina.value='<%= String.valueOf ( numeroPagina.intValue() + 1 )%>'; document.forms['busquedaEnviosForm'].submit();" title=""><bean:message key="resultadoBusqueda.proxima"/> </a>
			</logic:equal>
			<logic:equal name="page" property="nextPage" value="false">
			 <bean:message key="resultadoBusqueda.proxima"/> &nbsp; 
			</logic:equal>
			&gt;
		</p>
		</logic:notEmpty>
	</div>
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<bean:define id="sesion" name="<%=es.caib.zonaper.front.Constants.DATOS_SESION_KEY%>" type="es.caib.zonaper.model.DatosSesion" />

<!-- titol -->
<h1>
	<bean:message key="detalleExpediente.titulo" />
</h1>
<!-- /titol -->			
<p>			
	<bean:message key="detalleExpediente.texto" />
</p>
			

<div id="nomTramit">
	<dl>
		<dt><bean:message key="detalleExpediente.expediente"/>:</dt>
		<dd><bean:write name="descripcionExpediente"/></dd>
		<logic:notEmpty name="codigoExpediente">
		<dt><bean:message key="detalleExpediente.codigoExpediente"/>:</dt>
		<dd><bean:write name="codigoExpediente"/></dd>
		</logic:notEmpty>
	</dl>				
</div>

<table class="llistatElements">
	<thead>
		<tr>
			<th><bean:message key="detalleExpediente.tabla.accion" /></th>
			<th><bean:message key="detalleExpediente.tabla.titulo" /></th>
			<th><bean:message key="detalleExpediente.tabla.estado" /></th>
			<th><bean:message key="detalleExpediente.tabla.fecha" /></th>
		</tr>
	</thead>
	<tbody>
	<logic:iterate name="elementosExpediente" id="elemento" type="es.caib.zonaper.model.ElementoExpediente">
	
		<bean:define id="descElemento" name="elementosExpeDescripcion" property="<%=elemento.getCodigo().toString()%>" />
		<bean:define id="estadoElemento" name="elementosExpeEstado" property="<%=elemento.getCodigo().toString()%>" type="java.lang.String"/>
		<bean:define id="novedadElemento" name="elementosExpeNovedad" property="<%=elemento.getCodigo().toString()%>" />
	
							
			<logic:equal name="novedadElemento" value="true">
				<tr class="novetat">
			</logic:equal>
			<logic:notEqual name="novedadElemento" value="true">
				<tr>
			</logic:notEqual>
		
		
			<td class="accio">
				<logic:equal name="elemento" property="tipoElemento" value="<%=es.caib.zonaper.model.ElementoExpediente.TIPO_ENTRADA_TELEMATICA%>">
					<bean:message key="detalleExpediente.tramite" />
				</logic:equal>
				<logic:equal name="elemento" property="tipoElemento" value="<%=es.caib.zonaper.model.ElementoExpediente.TIPO_ENTRADA_PREREGISTRO%>">
					<bean:message key="detalleExpediente.tramite" />
				</logic:equal>
				<logic:equal name="elemento" property="tipoElemento" value="<%=es.caib.zonaper.model.ElementoExpediente.TIPO_AVISO_EXPEDIENTE%>">
					<bean:message key="detalleExpediente.aviso" />
				</logic:equal>
				<logic:equal name="elemento" property="tipoElemento" value="<%=es.caib.zonaper.model.ElementoExpediente.TIPO_NOTIFICACION%>">
					<bean:message key="detalleExpediente.notificacion" />
				</logic:equal>							 
			</td>		
							
			<td>
				 <a href="<%="mostrarDetalleElemento.do?tipo=" + elemento.getTipoElemento() + "&amp;codigo=" + elemento.getCodigoElemento() %>">
				 	<bean:write name="descElemento"/>
				 </a>
			</td>
			
			<td class="estat">
				<bean:message key="<%=estadoElemento%>" />													
			</td>
			
			<td>
				<bean:write name="elemento" property="fecha" format="dd/MM/yyyy  HH:mm"/>
			</td>
		</tr>
	</logic:iterate>				
	</tbody>
</table>		

<!-- Pie entrega doc presencial -->
<logic:equal name="pieDocPresencial" value="S">
	<div id="pieDocPresencial">				
		<bean:message key="detalleExpediente.pie.solicitudEnviadaPendienteDocumentacionPresencial" />				
	</div>							
</logic:equal>
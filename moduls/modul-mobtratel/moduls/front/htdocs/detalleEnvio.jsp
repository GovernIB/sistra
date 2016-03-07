<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.mobtratel.model.Envio"%>
<%@ page import="es.caib.mobtratel.front.Constants" %>
<%@ page import="java.util.Set"%>
<%@ page import="es.caib.mobtratel.modelInterfaz.ConstantesMobtratel" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>


<bean:define id="messageAyuda" value="ayuda.detalleEnvio" />

<bean:define id="urlAyuda" type="java.lang.String">
	<html:rewrite page="/ayuda.do" paramId="<%= Constants.MESSAGE_KEY %>" paramName="messageAyuda"/>
</bean:define>

<script type="text/javascript">
     <!--
     function edit(url) {
       obrir(url, "Edicion", 940, 600);
     }    
     // -->
</script>


<logic:present name="messageKey">
		<p class="alerta"><bean:message name="messageKey"/></p>
</logic:present>			

<bean:define id="urlEditarText">
    <html:rewrite page="/editarText.jsp"/>
</bean:define>

<bean:define id="urlCambioEstado" >
	<html:rewrite page="/cambiarEstadoEnvio.do" paramId="codigo" paramName="detalleEnvioForm" paramProperty="codigo"/>
</bean:define>

<bean:define id="urlExportarCSV" >
	<html:rewrite page="/exportCSVEnvioAction.do" paramId="codigo" paramName="detalleEnvioForm" paramProperty="codigo"/>
</bean:define>


			<h2><bean:message key="detalleEnvio.datosEnvio"/></h2>
		<p align="right"><html:link href="#" onclick="<%= \"javascript:obrir('\" + urlAyuda + \"', 'Edicion', 540, 400);\"%>"><img src="imgs/icones/ico_ayuda.gif" border="0"/><bean:message key="ayuda.enlace" /></html:link></p>
			<div class="atencio">
					<p>
					<!--  Icono enviado / no enviado -->
					<logic:equal name="envio" property="estado" value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_ERROR)%>">
						<img src="imgs/icones/form_procesado_error.gif" title="<bean:message key="resultadoBusqueda.enviadoError"/>"/>
						<strong><bean:message key="detalleEnvio.datosEnvio.estado"/>:</strong> <bean:message key="detalleEnvio.datosEnvio.enviadoError"/>
					</logic:equal>
					<logic:equal name="envio" property="estado" value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_ENVIADO)%>">
						<img src="imgs/icones/form_procesado_si.gif" title="<bean:message key="resultadoBusqueda.enviado"/>"/>
						<strong><bean:message key="detalleEnvio.datosEnvio.estado"/>:</strong> <bean:message key="detalleEnvio.datosEnvio.enviado"/>
					</logic:equal>
					<logic:equal name="envio" property="estado" value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_PENDIENTE)%>">
						<img src="imgs/icones/form_procesado_no.gif" title="<bean:message key="resultadoBusqueda.noEnviado"/>"/>
						<strong><bean:message key="detalleEnvio.datosEnvio.estado"/>:</strong> <bean:message key="detalleEnvio.datosEnvio.noEnviado"/>
					</logic:equal>
					<logic:equal name="envio" property="estado" value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_CANCELADO)%>">
						<img src="imgs/icones/document_parat.gif" title="<bean:message key="resultadoBusqueda.cancelado"/>"/>
						<strong><bean:message key="detalleEnvio.datosEnvio.estado"/>:</strong> <bean:message key="detalleEnvio.datosEnvio.cancelado"/>
					</logic:equal>
					<logic:equal name="envio" property="estado" value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_PROCESANDO)%>">
						<img src="imgs/icones/documento_enviandose.gif" title="<bean:message key="resultadoBusqueda.enviandose"/>"/>
						<strong><bean:message key="detalleEnvio.datosEnvio.estado"/>:</strong> <bean:message key="detalleEnvio.datosEnvio.enviandose"/>
					</logic:equal>
					</p>
					<p>
						<logic:equal name="permitirCambioEstado" value="S">
						
							<!--  Se prodra cancelar siempre que no este enviado o ya haya sido cancelado -->
							<logic:notEqual name="envio" property="estado" value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_ENVIADO)%>">							
								<logic:notEqual name="envio" property="estado" value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_CANCELADO)%>">							
									<input type="button" value="<bean:message key="detalleEnvio.datosEnvio.cancelar"/>" onclick="<%= "javascript:document.location.href='" + urlCambioEstado + "'"  %>"/>
								</logic:notEqual>
							</logic:notEqual>
							
						</logic:equal>
						<input type="button" value="<bean:message key="detalleEnvio.datosEnvio.exportarCSV"/>" onclick="<%= "javascript:document.location.href='" + urlExportarCSV + "'"  %>"/>
					</p>
					
			</div>
			
			<h3><bean:message key="detalleEnvio.datosEnvio.datos"/></h3>
			<div id="dadesJustificant">			
				<ul>
					<li><span class="label"><bean:message key="detalleEnvio.datosEnvio.nombre"/>:</span> <span><bean:write name="envio" property="nombre"/></span></li>
				</ul>
				<logic:notEmpty name="envio" property="fechaRegistro">
					<ul>
						<li><span class="label"><bean:message key="detalleEnvio.datosEnvio.fechaRegistro"/>:</span> <span><bean:write name="envio" property="fechaRegistro" format="dd/MM/yyyy - HH:mm 'h.'"/></span></li>
					</ul>
				</logic:notEmpty>			
				<logic:notEmpty name="envio" property="fechaEnvio">
					<ul>
						<li><span class="label"><bean:message key="detalleEnvio.datosEnvio.fechaEnviado"/>:</span> <span><bean:write name="envio" property="fechaEnvio" format="dd/MM/yyyy - HH:mm 'h.'"/></span></li>
					</ul>
				</logic:notEmpty>
				<logic:notEmpty name="envio" property="fechaProgramacionEnvio">
					<ul>
						<li><span class="label"><bean:message key="detalleEnvio.datosEnvio.fechaProgramacion"/>:</span> <span><bean:write name="envio" property="fechaProgramacionEnvio" format="dd/MM/yyyy - HH:mm 'h.'"/></span></li>
					</ul>
				</logic:notEmpty>
				<logic:notEmpty name="envio" property="fechaCaducidad">
					<ul>
						<li><span class="label"><bean:message key="detalleEnvio.datosEnvio.fechaCaducidad"/>:</span> <span><bean:write name="envio" property="fechaCaducidad" format="dd/MM/yyyy - HH:mm 'h.'"/></span></li>
					</ul>
				</logic:notEmpty>
				<ul>
					<li><span class="label"><bean:message key="detalleEnvio.datosEnvio.usuario"/>:</span> <span><bean:write name="envio" property="usuarioSeycon"/></span></li>
				</ul>
				
										
			</div>
			<logic:present name="erroresMensajes">
			<h3><bean:message key="detalleEnvio.errores"/></h3>
				<logic:present name="smss">
				<h4><bean:message key="detalleEnvio.errores.sms"/></h4>
				<div id="errores">
						<table cellpadding="8" cellspacing="0" id="tablaResultats">
							<tr>
								<th><bean:message key="detalleEnvio.errores.sms.mensaje"/></th>
								<th><bean:message key="detalleEnvio.errores.sms.error"/></th>
							</tr>
							<logic:iterate id="msj" name="envio" property="smss">
								<logic:notEmpty name="msj" property="error">
							<tr class="nou"/>
								<td><bean:write name="msj" property="mensajeStr" /></td>
								<td><bean:write name="msj" property="error" /></td>
							</tr>
								</logic:notEmpty>
							</logic:iterate>
						</table>														
				</div>
				</logic:present>
				<logic:present name="emails">
				<h4><bean:message key="detalleEnvio.errores.email"/></h4>
				<div id="errores">
						<table cellpadding="8" cellspacing="0" id="tablaResultats">
							<tr>
								<th><bean:message key="detalleEnvio.errores.email.titulo"/></th>
								<th><bean:message key="detalleEnvio.errores.email.error"/></th>
							</tr>
							<logic:iterate id="msj" name="envio" property="emails">
								<logic:notEmpty name="msj" property="error">
							<tr class="nou"/>
								<td><bean:write name="msj" property="titulo" /></td>
								<td><bean:write name="msj" property="error" /></td>
							</tr>
								</logic:notEmpty>
							</logic:iterate>
						</table>														
				</div>
				</logic:present>
			</logic:present>


			<p class="tornarArrere"><strong><a href="busquedaEnvios.do"><bean:message key="detalleEnvio.volver"/></a></strong></p>
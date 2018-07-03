<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.zonaper.helpdesk.front.Constants"%>
<%@ page import="es.caib.zonaper.model.DetallePagoTelematico"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="urlRecuperar" >
	<html:rewrite page="/recuperarDui.do" paramId="codigo" paramName="detallePagoForm" paramProperty="codigo"/>
</bean:define>

	<h1 class="ajuda"><bean:message key="detallePago.datosPago"/></h1>
	<div id="titolOmbra"></div>
	
		<!-- continguts -->
	<div class="continguts">
	
			<!-- justificant -->
		<div id="justificant">

	
			<!-- justificant -->
			<table class="pagament" cellpadding="0" cellspacing="0">
			<caption><bean:message key="detallePago.datosPago.datosPago"/></caption>
			<tbody>
				<tr>
					<th><bean:message key="detallePago.datosPago.nombre"/></th>
					<td><bean:write name="pago" property="nombre"/>	</td>
				</tr>
				<tr>
					<th><bean:message key="detallePago.datosPago.nif"/></th>
					<td><bean:write name="pago" property="nif"/></td>
				</tr>
				<tr>
					<th><bean:message key="detallePago.datosPago.tasa"/></th>
					<td><bean:write name="pago" property="tasa"/></td>
				</tr>
				<tr>
					<th><bean:message key="detallePago.datosPago.importe"/></th>
					<td><bean:write name="pago" property="importe"/></td>
				</tr>
				<tr>
					<th><bean:message key="detallePago.datosPago.estadoPlataforma"/></th>
					<td>
						<logic:equal name="pago" property="estadoPlataforma" value='<%= Constants.XMLPAGO_CONFIRMADO %>'>
							<bean:message key="detallePago.datosPago.plataforma.estado.confirmado"/>	
						</logic:equal>
						<logic:equal name="pago" property="estadoPlataforma" value='<%= Constants.XMLPAGO_NO_INICIADO %>'>
							<bean:message key="detallePago.datosPago.plataforma.estado.noIniciado"/>	
						</logic:equal>
						<logic:equal name="pago" property="estadoPlataforma" value='<%= Constants.XMLPAGO_PENDIENTE_CONFIRMAR %>'>
							<bean:message key="detallePago.datosPago.plataforma.estado.pendiente"/>	
						</logic:equal>
						<logic:equal name="pago" property="estadoPlataforma" value='<%= Constants.XMLPAGO_EN_CURSO %>'>
							<bean:message key="detallePago.datosPago.plataforma.estado.enCurso"/>	
						</logic:equal>
						<logic:equal name="pago" property="estadoPlataforma" value='<%= Constants.XMLPAGO_TIEMPO_EXCEDIDO %>'>
							<bean:message key="detallePago.datosPago.plataforma.estado.tiempoExcedido"/>	
						</logic:equal>
					</td>
				</tr>
			
				<logic:equal name="pago" property="estadoPlataforma" value='<%= Constants.XMLPAGO_CONFIRMADO %>'>
				<tr>
					<th><bean:message key="detallePago.datosPago.tipo"/></th>
					<td>					
							<logic:equal name="pago" property="tipo" value='<%= Character.toString(Constants.PRESENCIAL) %>'>
								<bean:message key="detallePago.datosPago.tipo.presencial"/>	
						</logic:equal>
							<logic:equal name="pago" property="tipo" value='<%= Character.toString(Constants.TELEMATICO) %>'>
								<bean:message key="detallePago.datosPago.tipo.telematico"/>	
						</logic:equal>												
					</td>
				</tr>
				 
				 <tr>
					<th><bean:message key="detallePago.datosPago.dui"/></th>
					<td><bean:write name="pago" property="dui"/></td>
				</tr>
				 
				<tr>
					<th><bean:message key="detallePago.datosPago.fecha"/></th>
					<td><bean:write name="pago" property="fecha" format="dd/MM/yyyy HH:mm"/></td>
				</tr>			
				</logic:equal>								

			</tbody>
			</table>
			
			<logic:present name="sesionPago">
			<table class="pagament" cellpadding="0" cellspacing="0">
			<caption><bean:message key="detallePago.asistentePago"/></caption>
			<tbody>
				<tr>
					<th><bean:message key="detallePago.asistentePago.estado"/></th>					
					<td>
					<logic:equal name="sesionPago" property="estado" value="0">
						<bean:message key="detallePago.asistentePago.estado.noExisteSesion"/>
					</logic:equal>
					<logic:equal name="sesionPago" property="estado" value="1">
						<bean:message key="detallePago.asistentePago.estado.enCurso"/>
					</logic:equal>
					<logic:equal name="sesionPago" property="estado" value="2">
						<bean:message key="detallePago.asistentePago.estado.pagoConfirmado"/>
					</logic:equal>
					<logic:equal name="sesionPago" property="estado" value="3">
						<bean:message key="detallePago.asistentePago.estado.pagoPendienteConfirmar"/>
					</logic:equal>
					</td>
				</tr>
				<logic:notEmpty name="sesionPago" property="descripcionEstado">
					<tr>
						<th><bean:message key="detallePago.asistentePago.descripcionEstado"/></th>
						<td><bean:write name="sesionPago" property="descripcionEstado"/></td>
					</tr>
				</logic:notEmpty>
				<tr>
					<th><bean:message key="detallePago.asistentePago.tipoPago"/></th>
					<td>
						<logic:equal name="sesionPago" property="tipo" value="T">
							<bean:message key="detallePago.asistentePago.tipoPago.Telematico"/>
						</logic:equal>
						<logic:equal name="sesionPago" property="tipo" value="P">
							<bean:message key="detallePago.asistentePago.tipoPago.Presencial"/>
						</logic:equal>						
					</td>
				</tr>
				<tr>
					<th><bean:message key="detallePago.asistentePago.localizador"/></th>
					<td><bean:write name="pago" property="localizador"/></td>
				</tr>
				
			</tbody>
			</table>
			</logic:present>
			<!-- /justificant -->
		
		</div>
		<!-- /justificant -->
		<!-- botonera -->
		<!--  NO APLICABLE DEBIDO A GENERALIZACION DE PLUGINS
		<div class="botonera">

			<logic:notEmpty name="pago" property="url">
				<button type="button" title="<bean:message key="detallePago.recuperar.instrucciones"/>" onclick="window.open('<bean:write name="pago" property="url"/>','');"><img src="imgs/botons/novafinestra.gif" alt="" /> <bean:message key="detallePago.recuperar"/></button>
			</logic:notEmpty>
			
			 Si no hay justificante comprobamos que no haya sido por problema de comunicacion con pagos 
			<logic:equal name="pago" property="estadoPortal" value="<%=Constants.PAGO_PAGADO%>">
				<logic:empty name="pago" property="url">			
					<bean:message key="detallePago.datosPago.url.noComprobadoPagado"/>
				</logic:empty>
			</logic:equal>
			
		</div>
		-->
		<!-- /botonera -->
	
	
	</div>
	<!-- /continguts -->

	<!-- tornar arrere -->
	<div id="tornarArrere"><a href="pagosTelematicos.do"><bean:message key="detallePago.volver"/></a></div>
	<!-- /tornar arrere -->

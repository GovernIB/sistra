<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.zonaper.helpdesk.front.Constants"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript" src="js/formularioBusqueda.js" charset="utf-8"></script>

		<!-- documentación a aportar -->
		<tiles:insert name=".formularioPagosTelematicos" />
		
		<div class="continguts">
		
		<!-- resultats -->
		<h2><bean:message key="resultadoBusqueda.titulo"/></h2>
		</p>
		<logic:notEmpty name="estado">
			<bean:define id="estado" name="estado" type="java.lang.String" />
			<p class="alerta"><bean:message key="<%= \"resultadoBusquedaEstado.informacionEstado.\" + estado %>" /></p>
		</logic:notEmpty>
		<logic:empty name="estado">
			<logic:empty name="lstPagos">
				<p><bean:message key="resultadoBusqueda.noEncontrados"/></p>
			</logic:empty>
			<logic:notEmpty name="lstPagos">
			<bean:size id="arraySize" name="lstPagos"/>
			<p>
				<bean:message key="resultadoBusqueda.numero.inicio"/> <strong><bean:write name="arraySize"/></strong> <bean:message key="resultadoBusqueda.numero.fin"/>
			</p>
			<table cellpadding="9" cellspacing="0" class="resultats">
			<thead>
			<tr>
				<th><bean:message key="resultadoBusqueda.fechaTramite"/></th>
				<th><bean:message key="resultadoBusqueda.estadoTramite"/></th>	
				<logic:notEqual name="nivel" value="<%=Character.toString(Constants.MODO_AUTENTICACION_ANONIMO)%>"> 
					<th><bean:message key="resultadoBusqueda.clavePersistencia"/></th>
				</logic:notEqual>
				<th><bean:message key="resultadoBusqueda.idioma"/></th>
				<th><bean:message key="resultadoBusqueda.estado"/></th>	
				<th><bean:message key="resultadoBusqueda.tipo"/></th>	
				<th><bean:message key="resultadoBusqueda.fechaPago"/></th>				
				<th><bean:message key="resultadoBusqueda.dui"/></th>
				<th><bean:message key="resultadoBusqueda.importe"/></th>				
			</tr>	
			</thead>
			<tbody>	
				<logic:iterate id="pago" name="lstPagos">
				<tr onmouseover="selecItemTabla(this);"   onclick="detallePago('<bean:write name="pago" property="codigoRDS"/>','<bean:write name="pago" property="claveRDS"/>','<bean:write name="pago" property="idioma"/>');" class="nou" title="<bean:message key="resultadoBusqueda.verDetallePago"/>">
					<td><bean:write name="pago" property="fecha" format="dd/MM/yyyy HH:mm"/></td>
					<td>
						<logic:equal name="pago" property="estadoTramite" value='<%= Constants.NO_FINALIZADO %>'>
							<bean:message key="resultadoBusqueda.estado.noFinalizado"/>	
						</logic:equal>
						<logic:equal name="pago" property="estadoTramite" value='<%= Constants.PENDIENTE_CONFIRMACION %>'>
							<bean:message key="resultadoBusqueda.estado.pendienteConfirmacion"/>	
						</logic:equal>
						<logic:equal name="pago" property="estadoTramite" value='<%= Constants.FINALIZADO %>'>
							<bean:message key="resultadoBusqueda.estado.finalizado"/>	
						</logic:equal>
						<logic:equal name="pago" property="estadoTramite" value='<%= Constants.BORRADO %>'>
							<bean:message key="resultadoBusqueda.estado.borrado"/>	
						</logic:equal>
					</td>
					<logic:notEqual name="nivel" value="<%=Character.toString(Constants.MODO_AUTENTICACION_ANONIMO)%>"> 
						<td><bean:write name="pago" property="idPersistencia"/></td>
					</logic:notEqual>
					
					<td>
						<logic:equal name="pago" property="idioma" value='<%= Constants.CATALAN %>'>
							<bean:message key="resultadoBusqueda.idioma.catalan"/>	
						</logic:equal>
						<logic:equal name="pago" property="idioma" value='<%= Constants.CASTELLANO %>'>
							<bean:message key="resultadoBusqueda.idioma.castellano"/>	
						</logic:equal>
					</td>
					<td>
						<strong>
						<logic:equal name="pago" property="estado" value='<%= Constants.XMLPAGO_CONFIRMADO %>'>
							<bean:message key="resultadoBusqueda.estadoPago.confirmado"/>	
						</logic:equal>
						<logic:equal name="pago" property="estado" value='<%= Constants.XMLPAGO_NO_INICIADO %>'>
							<bean:message key="resultadoBusqueda.estadoPago.noIniciado"/>	
						</logic:equal>
						<logic:equal name="pago" property="estado" value='<%= Constants.XMLPAGO_PENDIENTE_CONFIRMAR %>'>
							<bean:message key="resultadoBusqueda.estadoPago.iniciado"/>	
						</logic:equal>
						<logic:equal name="pago" property="estado" value='<%= Constants.XMLPAGO_EN_CURSO %>'>
							<bean:message key="resultadoBusqueda.estadoPago.enCurso"/>	
						</logic:equal>
						<logic:equal name="pago" property="estado" value='<%= Constants.XMLPAGO_TIEMPO_EXCEDIDO %>'>
							<bean:message key="resultadoBusqueda.estadoPago.tiempoExcedido"/>	
						</logic:equal>
						</strong>
					</td>
					<td>
						<strong>
						<logic:equal name="pago" property="tipo" value='<%= Character.toString(Constants.PRESENCIAL) %>'>
							<bean:message key="resultadoBusqueda.tipo.presencial"/>	
						</logic:equal>
						<logic:equal name="pago" property="tipo" value='<%= Character.toString(Constants.TELEMATICO) %>'>
							<bean:message key="resultadoBusqueda.tipo.telematico"/>	
						</logic:equal>
						</strong>
					</td>
					<td><bean:write name="pago" property="fechaPago" format="dd/MM/yyyy HH:mm"/></td>					
					<td><bean:write name="pago" property="dui"/></td>
					<td><bean:write name="pago" property="importe"/></td>
					
				</tr>
				</logic:iterate>														
			</tbody>
		   </table>
		   <!-- ajuda columna -->
			<script type="text/javascript">
			<!--
				ajudaColumnes = new Array();
				ajudaColumnes[0] = "";
				ajudaColumnes[1] = "";
				ajudaColumnes[2] = "";
				ajudaColumnes[3] = "";
				ajudaColumnes[4] = "<bean:message key="resultadoBusqueda.ayuda.estado"/>";
			-->
			</script>
			<div id="ajudaColumna"></div>
			<!-- /ajuda columna -->
			</logic:notEmpty>
		</logic:empty>
	</div>
	<!-- /continguts -->

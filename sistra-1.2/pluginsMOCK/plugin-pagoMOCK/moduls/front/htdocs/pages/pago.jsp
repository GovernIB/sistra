<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script>
	function volver(url){
		location.href = url;
	}
	
	
	function validar(){
		if(document.getElementById("modoPago") == null || document.getElementById("modoPago").value == ""){
			alert('Tiene que elegir la forma de pago.');
		}
	}
</script>
	<h2><bean:message key="pago.datosPago"/></h2>
	<p>	
		<span class="label"><bean:message key="pago.modelo"/></span>: <span><bean:write name="modelo" /></span><br>
		<span class="label"><bean:message key="pago.concepto"/></span>: <span><bean:write name="concepto" /></span><br>
		<span class="label"><bean:message key="pago.fechaDevengo"/></span>: <span><bean:write name="fechaDevengo" /></span><br>
		<span class="label"><bean:message key="pago.importe"/></span>: <span><bean:write name="importe" /> &euro;</span><br>
	</p>

	
	<html:form action="/realizarPago">		
		<h2><bean:message key="pago.modoPago"/></h2>
		<p>
			<label for="modoPago">
				<html:radio property="modoPago" value="T"><bean:message key="pago.modoPago.telematico"/></html:radio>
				<html:radio property="modoPago" value="P"><bean:message key="pago.modoPago.presencial"/></html:radio>
			</label>
		</p>
		<h2><bean:message key="pago.datosTarjeta"/></h2>		
		<p>
			<label for="numeroTarjeta">
				<span class="etiqueta"><bean:message key="pago.numeroTarjeta"/></span>
				<html:text property="numeroTarjeta" maxlength="16" size="17"/>						
			</label>
		</p>
		<p>
			<label for="fechaCaducidadTarjeta">
				<span class="etiqueta"><bean:message key="pago.fechaCaducidadTarjeta"/></span>
				<html:text property="fechaCaducidadTarjeta" maxlength="4" size="5"/>						
			</label>
		</p>
		<p>
			<label for="codigoVerificacionTarjeta">
				<span class="etiqueta"><bean:message key="pago.codigoVerificacionTarjeta"/></span>
				<html:text property="codigoVerificacionTarjeta" maxlength="3" size="4"/>						
			</label>
		</p>
		<p>		
			<html:submit>
				<bean:message key="pago.pagar"/>
			</html:submit>
			
			<input type="button" onclick="volver('<bean:write name="urlRetornoSistra"/>')" value="<bean:message key="pago.volverSistra"/>" />
		</p>
	</html:form>

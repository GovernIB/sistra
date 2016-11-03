<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="es.caib.sistra.model.DocumentoFront"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<bean:define id="urlPago" type="java.lang.String">
        <html:rewrite page="/protected/irAPago.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<bean:define id="urlCancelarPago" type="java.lang.String">
        <html:rewrite page="/protected/anularPago.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>

<bean:define id="labelPagoTelematico" type="java.lang.String">
	<bean:message key="pasoPagar.pagoTelematico"/>
</bean:define>
<bean:define id="labelPagoPresencial" type="java.lang.String">
	<bean:message key="pasoPagar.pagoPresencial"/>
</bean:define>

<script type="text/javascript">
<!--
	var mensajeEnviando = '<bean:message key="pasoPagar.mensajeAbrirPago"/>';
//-->
</script>

<h2><bean:message key="pasoPagar.efectuarPago"/></h2>

<%-- No existen tasas a pagar --%>
<logic:equal name="puedePagar" value="N">
	<p><bean:message key="pasoPagar.efectuarPago.instrucciones.noPagar"/></p>
</logic:equal>

<%-- Existen tasas a pagar --%>
<logic:equal name="puedePagar" value="S">
			
				<%-- Aviso de finalizar tramite tras pagar --%>
				<p class="alerta">
					<bean:message key="pasoPagar.instrucciones.finalizarTramite"/>
				</p>
			
				<%-- Info del pagos de pagos --%>
				<p class="ultimo">
					<logic:equal name="pagosMetodos" value="A">
						<bean:message key="pasoPagar.instrucciones.ambosMetodos"/>		
					</logic:equal>
					<logic:equal name="pagosMetodos" value="P">
						<bean:message key="pasoPagar.instrucciones.metodoPresencial"/>		
					</logic:equal>
					<logic:equal name="pagosMetodos" value="T">
						<bean:message key="pasoPagar.instrucciones.metodoTelematico"/>	
					</logic:equal>
				</p>								
				
				<div id="listadoTasas">	
				<%-- Listado de las tasas  --%>
				<logic:iterate id="pago" name="tramite" property="pagos" type="es.caib.sistra.model.DocumentoFront">			
					<logic:notEqual name="pago" property="obligatorio" value='D'>
					<%
						String urlIrPago = urlPago + "&identificador=" + pago.getIdentificador() + "&instancia=" + pago.getInstancia();
						String urlAnularPago = urlCancelarPago + "&identificador=" + pago.getIdentificador() + "&instancia=" + pago.getInstancia();
					%>
					<div class="iconos">
						<logic:equal name="pago" property="obligatorio" value='S'><img src="imgs/tramitacion/iconos/doc_obligatorio.gif" alt="<bean:message key="pasoPagar.iconografia.pagoObligatorio"/>" title="Pago obligatorio" /></logic:equal>
						<logic:equal name="pago" property="obligatorio" value='N'><img src="imgs/tramitacion/iconos/doc_opcional.gif" alt="<bean:message key="pasoPagar.iconografia.pagoOpcional"/>" title="Pago opcional" /></logic:equal>
						<logic:notEqual name="pago" property="estado" value='S'><img src="imgs/tramitacion/iconos/form_no_realizado.gif" alt="<bean:message key="pasoPagar.iconografia.pagoNoRealizado"/>" title="Pago no realizado"/></logic:notEqual>
						<logic:equal name="pago" property="estado" value='S'><img src="imgs/tramitacion/iconos/form_realizado.gif" alt="<bean:message key="pasoPagar.iconografia.pagoRealizadoCorrectamente"/>" title="Pago realizado corréctamente"/></logic:equal>				
						<img src="imgs/tramitacion/iconos/ico_pagado.gif" alt="<bean:message key="pasoPagar.pago"/>" title="Pago" />
					</div>
														
					<p>
						<strong><bean:write name="pago" property="descripcion" /></strong> 
						<br/>
						<%-- Pago no iniciado: redirigimos a asistente pago --%>
						<logic:equal name="pago" property="estado" value='<%=Character.toString(DocumentoFront.ESTADO_NORELLENADO)%>'>
							<html:link href="<%=urlIrPago%>" onclick="accediendoEnviando(mensajeEnviando);">
								<strong><bean:message key="pasoPagar.iniciarPago" /></strong>
							</html:link>	
						</logic:equal>
						
						<%-- Pago iniciado: redirigimos a asistente pago y damos opcion a anular si el pago es opcional --%>
						<logic:equal name="pago" property="estado" value='<%=Character.toString(DocumentoFront.ESTADO_INCORRECTO)%>'>
							<html:link href="<%=urlIrPago%>" onclick="accediendoEnviando(mensajeEnviando);">
								<strong><bean:message key="pasoPagar.continuarPago" /></strong>
							</html:link>	
							<logic:equal name="pago" property="obligatorio" value="<%=Character.toString(DocumentoFront.OPCIONAL)%>">
								<logic:equal name="pago" property="pagoTipo" value="P">
									- 
									<html:link href="<%=urlAnularPago%>" onclick="accediendoEnviando(mensajeEnviando);">
										<strong><bean:message key="pagoPagar.cancelarPago" /></strong>
									</html:link>
								</logic:equal>					
							</logic:equal>
						</logic:equal>						

						<%-- Pago realizado: mostramos mensaje pagado y damos opcion a anular si es presencial --%>
						<logic:equal name="pago" property="estado" value='<%=Character.toString(DocumentoFront.ESTADO_CORRECTO)%>'>	
							<%-- En caso de ser presencial, permitimos anularlo --%>
							<logic:equal name="pago" property="pagoTipo" value="P">
								<bean:message key="pasoPagar.pagoRealizado.presencial"/>
								<html:link href="<%=urlAnularPago%>">
								  - <strong><bean:message key="pagoPagar.anularPago"/></strong>
								</html:link>	
							</logic:equal>		
							<%-- En caso de ser telematico no se permite anular --%>
							<logic:equal name="pago" property="pagoTipo" value="T">
								<bean:message key="pasoPagar.pagoRealizado.telematico"/>								
							</logic:equal>	
						</logic:equal>						
					</p>																	
					</logic:notEqual>
			</logic:iterate>			
		</div>
</logic:equal>							

<div class="sep"></div>

<!-- capa mensaje accediendo -->
<div id="capaInfoFondo"></div>
<div id="capaInfoForms"></div>
		
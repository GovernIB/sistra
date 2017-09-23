<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.bantel.modelInterfaz.ConstantesBTE"%>
<%@ page import="es.caib.zonaper.modelInterfaz.ConstantesZPE"%>
<%@ page import="es.caib.xml.registro.factoria.ConstantesAsientoXML" %>
<%@ page import="es.caib.bantel.front.util.UtilBantelFront"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<bean:define id="idioma" name="<%=Globals.LOCALE_KEY%>" property="language" scope="session" type="java.lang.String" />

<script type="text/javascript" src="js/formularioBusquedaExpedientes.jsp"></script>

<h2><bean:message key="formularioBusquedaExpedientes.busquedaExpedientes"/></h2>

<logic:empty name="procedimientos">
	<bean:message key="errors.noGestor"/>
</logic:empty>

<logic:notEmpty name="procedimientos">				
		 
	<html:form action="busquedaExpedientes" styleId="busquedaExpedientesForm" styleClass="centrat">
		<html:hidden property="pagina" />				
		<p>
			<bean:message key="formularioBusqueda.fechaDesde"/>
			<html:text property="fechaDesde" styleId="fechaDesde" size="10"/>  
			<bean:message key="formularioBusqueda.fechaHasta"/>
			<html:text property="fechaHasta" styleId="fechaHasta" size="10"/>  				
			<bean:message key="formularioBusquedaExpedientes.nif"/>  <html:text property="usuarioNif" size="23" />
			<bean:message key="formularioBusquedaExpedientes.idExpediente"/>
			<html:text property="idExpediente" size="30"/> 
			<bean:message key="formularioBusquedaExpedientes.numeroEntrada"/>
			<html:text property="numeroEntrada" size="30"/>			
		</p>
		<p>
			<bean:message key="formularioBusqueda.procedimiento"/>
			<html:select property="identificadorProcedimiento">
				<html:option value="-1" ><bean:message key="formularioBusqueda.tramite.todos"/></html:option>
				<logic:iterate id="procedimiento" name="procedimientos" type="es.caib.bantel.model.Procedimiento">															
					<html:option value="<%=procedimiento.getIdentificador()%>">
						<%=UtilBantelFront.getDescripcionProcedimientoCombo(procedimiento, idioma)%>
					</html:option>
				</logic:iterate>
			</html:select>
			<bean:message key="formularioBusqueda.estado"/>
			<html:select property="estado">
				<html:option value="*" ><bean:message key="resultadoBusquedaExpedientes.estado.todos"/></html:option>
				<html:option value="<%=ConstantesZPE.ESTADO_AVISO_PENDIENTE%>" ><bean:message key="resultadoBusquedaExpedientes.estado.avisoPendiente"/></html:option>
				<html:option value="<%=ConstantesZPE.ESTADO_AVISO_RECIBIDO%>" ><bean:message key="resultadoBusquedaExpedientes.estado.avisoRecibido"/></html:option>
				<html:option value="<%=ConstantesZPE.ESTADO_NOTIFICACION_PENDIENTE%>" ><bean:message key="resultadoBusquedaExpedientes.estado.notificacionPendiente"/></html:option>
				<html:option value="<%=ConstantesZPE.ESTADO_NOTIFICACION_RECIBIDA%>" ><bean:message key="resultadoBusquedaExpedientes.estado.notificacionRecibida"/></html:option>
				<html:option value="<%=ConstantesZPE.ESTADO_NOTIFICACION_RECHAZADA%>" ><bean:message key="resultadoBusquedaExpedientes.estado.notificacionRechazada"/></html:option>
				<html:option value="<%=ConstantesZPE.ESTADO_SOLICITUD_ENVIADA%>" ><bean:message key="resultadoBusquedaExpedientes.estado.solicitudEnviada"/></html:option>
				<html:option value="<%=ConstantesZPE.ESTADO_SOLICITUD_ENVIADA_PENDIENTE_DOCUMENTACION_PRESENCIAL%>" ><bean:message key="resultadoBusquedaExpedientes.estado.solicitudPendienteDocumentacion"/></html:option>				
			</html:select>
			<bean:message key="formularioBusqueda.resultadosPorPagina"/>
			<html:select property="longitudPagina">
				<html:option value="10" >10</html:option>
				<html:option value="20" >20</html:option>
				<html:option value="30" >30</html:option>
				<html:option value="40" >40</html:option>
				<html:option value="50" >50</html:option>
				<html:option value="100" >100</html:option>
			</html:select>
		</p>
		<p class="centrado">
			<bean:define id="botonEnviar" type="java.lang.String">
                 <bean:message key="formularioBusquedaExpedientes.enviarBusqueda" />
               </bean:define>
            <bean:define id="botonAlta" type="java.lang.String">
                 <bean:message key="formularioBusquedaExpedientes.altaExpediente" />
               </bean:define>   
               
			<input name="buscar" id="buscar" type="button" value="<%=botonEnviar%>" onclick="javascript:validaFormulario( this.form );"/>					
			
			<input name="altaExpediente" id="altaExpediente" type="button" value="<%=botonAlta%>" onclick="alta();"/>
			
		</p>
		<div class="separacio"></div>			
	</html:form>	
</logic:notEmpty>						
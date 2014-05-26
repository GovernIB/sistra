<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.mobtratel.model.Envio"%>
<%@ page import="es.caib.mobtratel.front.Constants" %>
<%@ page import="es.caib.mobtratel.modelInterfaz.ConstantesMobtratel" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script language="javascript" src="js/formularioBusqueda.js"></script>

<bean:define id="urlAltaEmail" >
	<html:rewrite page="/altaEnvioEmail.do" />
</bean:define>
<bean:define id="urlAltaSMS" >
	<html:rewrite page="/altaEnvioSMS.do" />
</bean:define>
<bean:define id="urlEnvioFichero" >
	<html:rewrite page="/altaEnvioFichero.do" />
</bean:define>

<bean:define id="messageAyuda" value="ayuda.busqueda" />

<bean:define id="urlAyuda" type="java.lang.String">
	<html:rewrite page="/ayuda.do" paramId="<%= Constants.MESSAGE_KEY %>" paramName="messageAyuda"/>
</bean:define>


<p class="alerta"><bean:message key="formularioBusqueda.ayuda"/></p>


		<h2><bean:message key="formularioBusqueda.busquedaEnvios"/></h2>
		<p align="right"><html:link href="#" onclick="<%= \"javascript:obrir('\" + urlAyuda + \"', 'Edicion', 540, 400);\"%>"><img src="imgs/icones/ico_ayuda.gif" border="0"/><bean:message key="ayuda.enlace" /></html:link></p>
		 
			<html:form action="busquedaEnvios" styleId="busquedaEnviosForm" styleClass="centrat">
				<html:hidden property="pagina" />				
					<p>
					<bean:message key="formularioBusqueda.año"/> 
					<html:select property="anyo">
						<html:option value="0">---</html:option>
						<logic:iterate id="tmpAnyo" name="anyos">
									<html:option value="<%= tmpAnyo.toString() %>" />
						</logic:iterate>			
					</html:select> 
					<bean:message key="formularioBusqueda.mes"/>
					<html:select property="mes">
						<logic:iterate id="tmpMes" name="meses">
									<html:option value="<%= tmpMes.toString() %>"><bean:message key='<%= "mes." + tmpMes %>' /></html:option>
						</logic:iterate>			
					</html:select> 					
					<!--  
					<bean:message key="formularioBusqueda.tipo"/>
					<html:select property="tipo">
						<html:option value="T" ><bean:message key="formularioBusqueda.tipo.todos"/></html:option>
						<html:option value="<%=Envio.TIPO_EMAIL%>" ><bean:message key="formularioBusqueda.tipo.email"/></html:option>
						<html:option value="<%=Envio.TIPO_SMS%>" ><bean:message key="formularioBusqueda.tipo.sms"/></html:option>
						<html:option value="<%=Envio.TIPO_EMAIL_SMS%>" ><bean:message key="formularioBusqueda.tipo.emailsms"/></html:option>
					</html:select>
					-->
					<bean:message key="formularioBusqueda.enviado"/>
					<html:select property="enviado">
						<html:option value="T" ><bean:message key="formularioBusqueda.enviado.todos"/></html:option>
						<html:option value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_ENVIADO)%>" ><bean:message key="formularioBusqueda.enviado.correctos"/></html:option>
						<html:option value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_PENDIENTE)%>" ><bean:message key="formularioBusqueda.enviado.noenviados"/></html:option>
						<html:option value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_ERROR)%>" ><bean:message key="formularioBusqueda.enviado.error"/></html:option>
						<html:option value="<%=String.valueOf(ConstantesMobtratel.ESTADOENVIO_CANCELADO)%>" ><bean:message key="formularioBusqueda.enviado.cancelado"/></html:option>
					</html:select>				
					<bean:message key="formularioBusqueda.cuenta"/>
					<html:select property="cuenta">
							<html:option value="T" ><bean:message key="formularioBusqueda.cuenta.todas"/></html:option>
							<logic:present name="cuentas">
				   			<html:options collection="cuentas" property="codigo" labelProperty="nombre" />
				   			</logic:present>
					</html:select>				
			<!-- 
					&nbsp;&nbsp;&nbsp;&nbsp;<span><a href="javascript:void(0);" onclick="obrirRecercaAv(this);">[+]</a></span>
			 -->
					</p>					
				<bean:define id="botonEnviar" type="java.lang.String">
	                  <bean:message key="boton.enviarBusqueda" />
                 </bean:define>
				<bean:define id="botonAltaEmail" type="java.lang.String">
	                  <bean:message key="boton.altaEmail" />
                 </bean:define>
				<bean:define id="botonAltaSMS" type="java.lang.String">
	                  <bean:message key="boton.altaSMS" />
                 </bean:define>
				<bean:define id="botonAltaEnvioFichero" type="java.lang.String">
	                  <bean:message key="boton.altaEnvioFichero" />
                 </bean:define>
				<p class="centrado">
					<input name="imprimirSolicitudBoton" id="imprimirSolicitudBoton" type="button" value="<%=botonEnviar%>" onClick="javascript:validaFormulario( this.form );"/>
					<input name="altaEmailBoton" id="altaEmailBoton" type="button" value="<%=botonAltaEmail%>" onclick="<%= "javascript:document.location.href='" + urlAltaEmail + "'"  %>"/>
					<input name="altaSMSBoton" id="altaSMSBoton" type="button" value="<%=botonAltaSMS%>" onclick="<%= "javascript:document.location.href='" + urlAltaSMS + "'"  %>"/>
					<input name="altaEnvioFicheroBoton" id="altaEnvioFicheroBoton" type="button" value="<%=botonAltaEnvioFichero%>" onclick="<%= "javascript:document.location.href='" + urlEnvioFichero + "'"  %>"/>
				</p>
				<div class="separacio"></div>			
			</html:form>	
		
		

<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.mobtratel.front.Constants" %>
<%@ page import="es.caib.mobtratel.modelInterfaz.ConstantesMobtratel" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<bean:define id="urlAltaEmail" >
	<html:rewrite page="/altaEnvioEmail.do" />
</bean:define>
<bean:define id="urlAltaSMS" >
	<html:rewrite page="/altaEnvioSMS.do" />
</bean:define>
<bean:define id="urlEnvioFichero" >
	<html:rewrite page="/altaEnvioFichero.do" />
</bean:define>

<bean:define id="messageAyuda" value="ayuda.estadisticasSMS" />

<bean:define id="urlAyuda" type="java.lang.String">
	<html:rewrite page="/ayuda.do" paramId="<%= Constants.MESSAGE_KEY %>" paramName="messageAyuda"/>
</bean:define>

		<h2><bean:message key="formularioBusquedaEstadisticasSMS.busquedaEstadisticasSMS"/></h2>
		<p align="right"><html:link href="#" onclick="<%= \"javascript:obrir('\" + urlAyuda + \"', 'Edicion', 540, 400);\"%>"><img src="imgs/icones/ico_ayuda.gif" border="0"/><bean:message key="ayuda.enlace" /></html:link></p>
		 
			<html:form action="busquedaEstadisticasSMS" styleId="busquedaEnviosForm" styleClass="centrat">
				<p>
					<bean:message key="formularioBusquedaEstadisticasSMS.año"/> 
					<html:select property="anyo">
						<logic:iterate id="tmpAnyo" name="anyos">
									<html:option value="<%= tmpAnyo.toString() %>" />
						</logic:iterate>			
					</html:select> 
					<bean:message key="formularioBusquedaEstadisticasSMS.cuenta"/>
					<html:select property="cuenta">
							<html:option value="T" ><bean:message key="formularioBusqueda.cuenta.todas"/></html:option>
							<logic:present name="cuentas">
				   			<html:options collection="cuentas" property="codigo" labelProperty="nombre" />
				   			</logic:present>
					</html:select>				
				</p>					
				<bean:define id="botonEnviar" type="java.lang.String">
	                  <bean:message key="formularioBusquedaEstadisticasSMS.enviarBusqueda" />
                 </bean:define>
				<p class="centrado">
					<input name="imprimirSolicitudBoton" id="imprimirSolicitudBoton" type="submit" value="<%=botonEnviar%>"/>					
				</p>
				<div class="separacio"></div>			
			</html:form>	
		
		

<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.bantel.modelInterfaz.ConstantesBTE"%>
<%@ page import="es.caib.xml.registro.factoria.ConstantesAsientoXML" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript" src="js/formularioBusquedaExpedientes.js"></script>

<h2><bean:message key="formularioBusquedaExpedientes.busquedaExpedientes"/></h2>

<logic:empty name="procedimientos">
	<bean:message key="errors.noGestor"/>
</logic:empty>

<logic:notEmpty name="procedimientos">				
		 
	<html:form action="busquedaExpedientes" styleId="busquedaExpedientesForm" styleClass="centrat">
		<html:hidden property="pagina" />				
		<p>
			<bean:message key="formularioBusquedaExpedientes.año"/> 
			<html:select property="anyo">
				<logic:iterate id="tmpAnyo" name="anyos">
							<html:option value="<%= tmpAnyo.toString() %>" />
				</logic:iterate>			
			</html:select> 
			<bean:message key="formularioBusquedaExpedientes.mes"/>
			<html:select property="mes">
				<logic:iterate id="tmpMes" name="meses">
							<html:option value="<%= tmpMes.toString() %>"><bean:message key='<%= "mes." + tmpMes %>' /></html:option>
				</logic:iterate>			
			</html:select> 					
			<bean:message key="formularioBusquedaExpedientes.nif"/>  <html:text property="usuarioNif" size="9" /> 
			<bean:message key="formularioBusquedaExpedientes.numeroEntrada"/>
			<html:text property="numeroEntrada" size="30"/>
			<bean:message key="formularioBusqueda.procedimiento"/>
			<html:select property="identificadorProcedimiento">
				<html:option value="-1" ><bean:message key="formularioBusqueda.tramite.todos"/></html:option>
				<logic:iterate id="procedimiento" name="procedimientos" type="es.caib.bantel.model.Procedimiento">															
					<html:option value="<%=procedimiento.getIdentificador()%>">
						<%=procedimiento.getIdentificador() + "-" + (procedimiento.getDescripcion().length()>60?procedimiento.getDescripcion().substring(0,60)+"...":procedimiento.getDescripcion())%>
					</html:option>
				</logic:iterate>
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
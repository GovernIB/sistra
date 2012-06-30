<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<bean:define id="fullName" name="fullName" type="java.lang.String"/>
<bean:define id="urlOriginal" type="java.lang.String" value="<%=StringUtils.defaultString((String) request.getAttribute( \"urlOriginal\"))%>"/>	
<bean:define id="look" name="look" type="java.lang.String"/>
<bean:define id="nombre" name="nombre" type="java.lang.String"/>
<bean:define id="apellido1" name="apellido1" type="java.lang.String"/>
<bean:define id="apellido2" name="apellido2" type="java.lang.String"/>

	<!-- continguts -->
	<div id="continguts">
		
		<p><bean:message key="actualizarDatosPAD.bienvenida" /></p>
		
		<div class="atencion">
			<bean:message name="keyInstrucciones" arg0="<%= fullName %>"/>
		</div>
						
		<logic:present name="keyError">
			<div class="error">
				<p><bean:message name="keyError" /></p> 				
			</div>
		</logic:present>
		
		<html:form action="/actualizarDatosPAD" styleId="formConfirmacion">
			<html:hidden property="urlOriginal" value="<%= urlOriginal %>"/>
			<html:hidden property="look" value="<%= look%>"/>
			<label for="nombre">
				<span class="etiqueta"><bean:message key="actualizarDatosPAD.nombre"/>:</span>
				<input name="nombre" type="text" value="<%= nombre%>"  tabindex="1" />
			</label>
			<logic:equal name="isPersonaFisica" value="true">	
			<label for="apellido1">
				<span class="etiqueta"><bean:message key="actualizarDatosPAD.apellido1"/>:</span>
				<input name="apellido1" type="text" value="<%= apellido1%>" tabindex="2" />
			</label>
			<label for="apellido2">
				<span class="etiqueta"><bean:message key="actualizarDatosPAD.apellido2"/>:</span>
				<input name="apellido2" type="text" value="<%= apellido2%>" tabindex="3" />
			</label>
			</logic:equal>	
			<div class="botonera"><input name="botonConfirmar" id="botonConfirmar" type="button" value="<bean:message key="actualizarDatosPAD.enviar"/>" tabindex="4" onclick="this.form.submit()"/></div>
		</html:form>
		
		<p><bean:message key="actualizarDatosPAD.modificarEnZonaPersonal" /></p>
		
	</div>
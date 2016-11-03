<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.zonaper.modelInterfaz.ConstantesZPE"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
	<!-- continguts -->
	<div id="continguts">
	
	<bean:define id="ciudadanoTitle" type="java.lang.String">
		<bean:message key="perfilAcceso.ciudadano.title" />		
	</bean:define>
	
	<bean:define id="delegadoTitle"  type="java.lang.String">
		<bean:message key="perfilAcceso.delegado.title" />		
	</bean:define>
		
	<p><bean:message key="perfilAcceso.descripcion" /></p>
	
	<html:form action="/elegirPerfilAccesoPAD" styleId="formConfirmacion">
		<html:hidden property="urlOriginal" value="<%= ( String ) request.getAttribute( \"urlOriginal\") %>"/>
		<p>
		<html:radio property="perfil" value="<%=ConstantesZPE.DELEGACION_PERFIL_ACCESO_CIUDADANO%>" title="<%=ciudadanoTitle%>"/>
		<bean:message key="perfilAcceso.ciudadano" />
		</p>
		<p>
		<html:radio property="perfil" value="<%=ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO%>" title="<%=delegadoTitle%>" />
		<bean:message key="perfilAcceso.delegado" /> 
		<html:select property="nifEntidad" >
		    <html:options collection="listaEntidadesDelegadas"
		       property="nif" labelProperty="nombreCompleto" />
		  </html:select>
		</p>
		<div class="botonera"><input name="botonConfirmar" id="botonConfirmar" type="button" value="<bean:message key="actualizarDatosPAD.enviar"/>" tabindex="4" onclick="this.form.submit()"/></div>
	</html:form>
		
	</div>
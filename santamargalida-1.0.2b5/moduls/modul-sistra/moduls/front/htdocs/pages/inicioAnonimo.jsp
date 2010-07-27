<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<script type="text/javascript">
<!--
function validaFormulario( form )
    {
		if ( form.idPersistencia.value == null || form.idPersistencia.value == '' )
		{
			alert( "Introduzca un identificador de persistencia" );	
			form.idPersistencia.focus();
			return;
		}
        var filter  = /^\w{8}\-\w{8}\-\w{8}$/;
        if ( !filter.test( form.idPersistencia.value ) )
        {
        	alert( "Introduzca un identificador de persistencia valido" );	
			form.idPersistencia.focus();
			return;
        }
        form.submit();
	}
-->
</script>
	<bean:define id="urlIniciarTramite">
	        <html:rewrite page="/protected/nuevoTramite.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
	</bean:define>
			<p><bean:message key="inicioAnonimo.instrucciones.parrafo1.texto1"/> '<strong><bean:write name="descripcion" /></strong>'.</p>
			<p><bean:message key="inicioAnonimo.instrucciones.parrafo2.texto1"/> <strong><bean:message key="inicioAnonimo.instrucciones.parrafo2.texto2"/></strong> <bean:message key="inicioAnonimo.instrucciones.parrafo2.texto3"/></p>
			<!-- nuevo trámite -->
			<div id="nuevoTA">
				<h2><bean:message key="inicioAnonimo.instrucciones.iniciarNuevoTramite"/></h2>
				<p><bean:message key="inicioAnonimo.instrucciones.iniciarNuevoTramite.instrucciones.texto1"/> <strong><bean:message key="inicioAnonimo.instrucciones.iniciarNuevoTramite.instrucciones.texto2"/></strong> <bean:message key="inicioAnonimo.instrucciones.iniciarNuevoTramite.instrucciones.texto3"/> </p>
				<form name="f" action="" method="post">
					<p class="formBotonera"><input name="formANboton" type="button" value="<bean:message key="inicioAnonimo.boton.nuevo"/>" title="<bean:message key="inicioAnonimo.boton.nuevoTramite"/>" onclick="document.location = '<bean:write name="urlIniciarTramite" />'"/></p>
				</form>
			</div>
			<!-- continuar tramite -->
			<div id="continuarTA">
				<h2><bean:message key="inicioAnonimo.instrucciones.continuarTramiteInacabado"/></h2>
				<p><bean:message key="inicioAnonimo.instrucciones.continuarTramiteInacabado.instrucciones.texto1"/> <strong><bean:message key="inicioAnonimo.instrucciones.continuarTramiteInacabado.instrucciones.texto2"/></strong> <bean:message key="inicioAnonimo.instrucciones.continuarTramiteInacabado.instrucciones.texto3"/></p>
				<html:form action="/protected/cargarTramite">
					<html:hidden property="ID_INSTANCIA" value="<%= (String) request.getAttribute("ID_INSTANCIA") %>" /> 
					<p class="formBotonera"><label for="idPersistencia"><bean:message key="inicioAnonimo.instrucciones.continuarTramiteInacabado.clave"/> </label> <html:text property="idPersistencia" styleId="idPersistencia"/> <input name="formANcontinuarboton" type="button" value="<bean:message key="inicioAnonimo.boton.continuar"/>" title="<bean:message key="inicioAnonimo.boton.continuarTramite"/>" onclick="javascript:validaFormulario( this.form );"/></p>
				</html:form>
			</div>
			<div class="sep"></div>
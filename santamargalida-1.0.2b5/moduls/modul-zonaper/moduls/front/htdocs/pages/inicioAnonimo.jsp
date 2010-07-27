<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="locale" name="org.apache.struts.action.LOCALE" scope="session" />
<script type="text/javascript">
<!--
function validaFormulario( form )
    {
    	// Eliminamos espacios en blanco    	
		form.idPersistencia.value = form.idPersistencia.value.replace(/^\s+|\s+$/g,'');
    
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
		<!-- informacio -->
		<div id="info">
			<html:form action="/protected/infoTramiteAnonimo">
			<p><bean:message key="inicioAnonimo.parrafo1.texto" /></p>
			<h2><bean:message key="inicioAnonimo.titulo" /></h2>
			<p><bean:message key="inicioAnonimo.encabezamiento" /></p>
			<p><html:text property="idPersistencia" size="70"/> <input type="button" value="Iniciar" onclick="javascript:validaFormulario( this.form );"/></p>
			</html:form>
		</div>	
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript">
<!--
function validaFormulario( form )
    {
    	// Eliminamos espacios en blanco    	
		form.idPersistencia.value = form.idPersistencia.value.replace(/^\s+|\s+$/g,'');
    
		if ( form.idPersistencia.value == null || form.idPersistencia.value == '' )
		{
			alert( "<bean:message key="menuAnonimo.claveVacia" />" );	
			form.idPersistencia.focus();
			return;
		}
        var filter  = /^\w{8}\-\w{8}\-\w{8}$/;
        if ( !filter.test( form.idPersistencia.value ) )
        {
        	alert( "<bean:message key="menuAnonimo.claveInvalida" />" );	
			form.idPersistencia.focus();
			return;
        }
        form.submit();
	}
-->
</script>

		<!-- titol -->
			<h1>
				<bean:message key="accesoDirectoAnonimo.encabezamiento" />
			</h1>
			<!-- /titol -->
			
			<!-- meues dades -->
			<div class="modul anonim">
				<h2><bean:message key="accesoDirectoAnonimo.misTramites" /></h2>
				<p>
					<bean:message key="accesoDirectoAnonimo.misTramites.texto" />
					
					<logic:present name="tramiteNoExiste">		
						<p class="alerta"><bean:message key="infoTramiteAnonimo.tramiteNoExistente" /></p>
					</logic:present>
					
					<html:form action="/protected/realizarAccesoDirectoAnonimo">
						<p>
							<html:hidden property="codigo" value="<%=request.getParameter("codigo")%>" /> 
							<html:hidden property="tipo" value="<%=request.getParameter("tipo")%>"/> 
							<html:text property="idPersistencia" size="35"/> 
							<input type="button" value="<bean:message key="accesoDirectoAnonimo.misTramites.boton" />" onclick="javascript:validaFormulario( this.form );"/>
						</p>
					</html:form>					
				
				</p>
			</div>
			<!-- /meues dades -->

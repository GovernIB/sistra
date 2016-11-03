<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.bantel.modelInterfaz.ConstantesBTE"%>
<%@ page import="es.caib.xml.registro.factoria.ConstantesAsientoXML" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript" src="js/formularioBusqueda.js"></script>
<script type="text/javascript">
 $(document).ready(function () {
	  $('#entidadNif').focus(function() {
	    $('#entidadNombre').val("");
	  });
	  $('#entidadNombre').focus(function() {
	    $('#entidadNif').val("");
	  });
	});


//funcion que envia a dar de alta una entidad
function irAlta(){
	document.busquedaEntidadesForm.action='<html:rewrite page="/irAltaEntidad.do"/>';
	document.busquedaEntidadesForm.submit();
}
function irBuscar(){
	document.busquedaEntidadesForm.action='<html:rewrite page="/busquedaEntidades.do"/>';
	document.busquedaEntidadesForm.submit();
}
</script>

<!-- 
<h2><bean:message key="formularioBusqueda.busquedaEntidades"/></h2>
 -->
 
<p>
	<bean:message key="formularioBusqueda.descripcion"/>
</p>

<html:errors/>		
	 
<html:form action="busquedaEntidades" styleClass="centrat">
	<div id="recercaAv">
		<p>
			
			<bean:message key="formularioBusqueda.nif"/>
			<html:text property="entidadNif" styleId="entidadNif" size="12" maxlength="12" />
			<bean:message key="formularioBusqueda.nombre"/>
			<html:text property="entidadNombre"  styleId="entidadNombre" size="25" maxlength="50" />
			<input name="buscar" id="buscar" type="button" value="<bean:message key="boton.enviarBusqueda" />" onclick="javascript:irBuscar();"/> 			
		</p>
		<p>
			<bean:message key="formularioBusqueda.altaEntidad"/>
			<input name="alta" id="alta" type="button" value="<bean:message key="boton.altaEntidad"/>" onclick="javascript:irAlta();"/>
		</p>		
	</div>
	<div class="separacio"></div>			
</html:form>	
		
		
		
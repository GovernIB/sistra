<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<script type="text/javascript" src="js/jquery.selectboxes.pack.js"></script>
<script type="text/javascript" src="js/funcions.js"></script>
<script type="text/javascript">
	function alta(){
		document.forms["0"].flagValidacion.value="entradaAlta";
		document.forms["0"].action='<html:rewrite page="/altaExpediente.do"/>';
		document.forms["0"].submit();
	}
	
	function consulta(){
		document.forms["0"].action='<html:rewrite page="/confirmacionRecuperacionExpediente.do"/>';
		document.forms["0"].submit();
	}

	function volver(){
		document.location='<html:rewrite page="/busquedaExpedientes.do"/>';
	}
</script>
<script type="text/javascript">
     <!--
     function mostrarArbolUnidades(url) {
        obrir(url, "Arbol", 540, 400);
     }
     // -->
</script>
<bean:define id="urlArbol">
    <html:rewrite page="/arbolUnidades.do"/>
</bean:define>
<bean:define id="btnAlta" type="java.lang.String">
	<bean:message key="confirmacion.alta"/>
</bean:define>
<bean:define id="btnConsultar" type="java.lang.String">
	<bean:message key="confirmacion.modificar"/>
</bean:define>
	<div id="contenidor">
	
				
		<!-- continguts -->
		<div class="continguts">
		
			<p><bean:message key="confirmacion.inicio.cabecera"/></p>
			
			<html:form action="recuperarExpediente" styleClass="remarcar opcions">
				<html:hidden property="flagValidacion"/>
				<p class="botonera">
					<html:submit  value="<%=btnAlta%>" onclick="alta();"/>
					<html:submit value="<%=btnConsultar%>" onclick="consulta();"/>
				</p>
			
			</html:form>
			
		</div>
		<!-- /continguts -->
		
	</div>

	<!-- tornar enrere -->
		<div id="enrere">
			<html:form style="background-color:white" action="recuperarExpediente" >
			<a href="#" onclick="javascript:volver()">
				<bean:message key="detalle.aviso.tornar" />				
			</a>	
			</html:form>
		</div>
			<!-- /tornar enrere -->


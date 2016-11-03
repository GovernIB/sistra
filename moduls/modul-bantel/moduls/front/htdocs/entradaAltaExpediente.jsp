<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript" src="js/ajuda.js"></script>
<script type="text/javascript" src="js/funcions.js"></script>
<script type="text/javascript">
	function alta(){
		document.forms["0"].tipo.value = "A";
		document.forms["0"].action='<html:rewrite page="/altaExpediente.do"/>';
		document.forms["0"].submit();
	}
	
	function recuperar(){
		document.forms["0"].tipo.value = "E";
		document.forms["0"].action='<html:rewrite page="/altaEntradaExpediente.do"/>';
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
<bean:define id="btnAltaEntrada" type="java.lang.String">
	<bean:message key="confirmacion.altaEntrada"/>
</bean:define>
		<!-- ajuda boto -->
		<button id="ajudaBoto" type="button" title="Activar ajuda"><img src="imgs/botons/ajuda.gif" alt="" /> <bean:message key="confirmacion.ayuda"/></button>
		<!-- /ajuda boto -->
		<div id="opcions">
				&nbsp;
		</div>
		<!-- titol -->
		<!--<h1>Gestión de expedientes</h1>-->
		<!-- /titol -->
		
		<!-- ajuda -->
		<div id="ajuda">
			<h2><bean:message key="ajuda.titulo"/></h2>
			<br/>
			<bean:message key="ajuda.expediente.consulta.numeroEntrada"/>
		</div>
			
		<html:errors/>
			
		<!-- continguts -->
		<div class="continguts">
		
			<p><bean:message key="confirmacion.texto.alta1"/></p>
			
			<ul>
				<li>
					<bean:message key="confirmacion.texto.alta2"/>
				</li>

				<li>
					<bean:message key="confirmacion.texto.alta3"/>
				</li>
			</ul>
			
			<html:form action="altaExpediente" styleClass="remarcar opcions">
				<html:hidden property="tipo"/>
				
				<p class="botonera2">
					<html:submit value="<%=btnAltaEntrada%>" onclick="recuperar();"/>
					<html:text property="numeroEntrada" styleId="numeroEntrada"/>
				</p>
				
				<p class="botonera2">
					<html:submit  value="<%=btnAlta%>" onclick="alta();"/>
				</p>
				
			
			</html:form>
			
			
		</div>
		<!-- /continguts -->
	
		<!-- tornar enrere -->
		<div id="enrere">
			<a href="#" onclick="javascript:volver()">
				<bean:message key="detalle.aviso.tornar" />				
			</a>				
		</div>
		<!-- /tornar enrere -->


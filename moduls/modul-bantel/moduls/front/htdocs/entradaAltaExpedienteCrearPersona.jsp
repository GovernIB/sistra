<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript" src="js/ajuda.js"></script>
<script type="text/javascript" src="js/funcions.js"></script>
<script type="text/javascript">
	function volver(){
		document.location='<html:rewrite page="/busquedaExpedientes.do"/>';
	}
</script>


<bean:define id="btnAlta" type="java.lang.String">
	<bean:message key="mensaje.continuar"/>
</bean:define>

<bean:define id="nifPersona" type="java.lang.String" name="nifPersona"/>

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
	<bean:message key="expediente.alta.noExisteDestinatario"/>
</div>

<html:errors/>

<!-- continguts -->
<div class="continguts">

	<p><bean:message key="expediente.alta.noExisteDestinatario"/></p>

	<!--  PENDIENTE METER CREACION PERSONA -->
	AQUI HAY QUE METER LA CREACION DE PERSONA COMO ESTA EN EL ALTA DE EXPEDIENTE NORMAL PARA NIF: <bean:write name="nifPersona"/> O LO PONES ASÍ <%=nifPersona%>



	<!--  PENDIENTE ESTABLECER NUM ENTRADA -->
	<html:form action="altaEntradaExpediente" styleClass="remarcar opcions">
		<html:hidden property="tipo"/>

		<p class="botonera2">
			<html:submit value="<%=btnAlta%>" onclick="recuperar();"/>
			<html:hidden property="tipo" value="E"/>
			<html:hidden property="numeroEntrada"/>
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


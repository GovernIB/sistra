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
	
	function realizarAltaDestinatario(){
 		var nifAlta = $("#nifAltaDestinatario").val();
 		var nombreAlta = $("#nombreAltaDestinatario").val();
 		var ape1Alta = $("#apellido1AltaDestinatario").val();
 		var ape2Alta = $("#apellido2AltaDestinatario").val();

 		if(confirm ( "<bean:message key='expediente.alta.altaDestinatario.confirmacion' />" )){

 			var mapVars = {};
 			mapVars["nif"] = nifAlta;
 			mapVars["nombre"] = nombreAlta;
 			mapVars["apellido1"] = ape1Alta;
 			mapVars["apellido2"] = ape2Alta;

 			$.ajax({
				type: "POST",
				url: "altaPersona.do",
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",
				data: mapVars,
				dataType: "json",
				error: function() {
					alert("Error enviando datos al servidor. Intentelo de nuevo.");					
				},
				success: function(json) {					
					if (json.error == "") {
	 					alert("<bean:message key="expediente.alta.altaDestinatario.altaRealizada"/>");
	 					document.forms["1"].submit();
	 				} else {
	 	 				alert(json.error);
	 				}						
				}
			});
 	 		
 		}
 	}
</script>



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

	<!--  capa alta destinatario -->
	 <div id="altaDestinatario">	
	 	<p>
			<bean:message key="expediente.alta.altaDestinatario.intro"/>
		</p>	
	 	<form  class="remarcar opcions">
	 	
	 		<p>
				<label for="nifAltaDestinatario"><bean:message key="expediente.alta.altaDestinatario.nif"/></label>
				<input type="text" id="nifAltaDestinatario" name="nifAltaDestinatario" size="12"  class="nif" value="<%=nifPersona%>" readonly/>
			</p>
	 		<p>
				<label for="nombreAltaDestinatario"><bean:message key="expediente.alta.altaDestinatario.nombre"/></label>
				<input type="text" id="nombreAltaDestinatario" name="nombreAltaDestinatario" class="pc40" maxlength="50" />
			</p>
			<p>
				<label for="apellido1AltaDestinatario"><bean:message key="expediente.alta.altaDestinatario.apellido1"/></label>
				<input type="text" id="apellido1AltaDestinatario" name="apellido1AltaDestinatario" class="pc40" maxlength="50"/>
			</p>
			<p>
				<label for="apellido2AltaDestinatario"><bean:message key="expediente.alta.altaDestinatario.apellido2"/></label>
				<input type="text" id="apellido2AltaDestinatario" name="apellido2AltaDestinatario" class="pc40" maxlength="50"/>
			</p>
			<p class="botonera">
				<input type="button" onclick="realizarAltaDestinatario();" value="<bean:message key="expediente.alta.altaDestinatario.botonAlta"/>"/>	
			</p>
	 	</form> 	
	</div>	

	<!--  PENDIENTE ESTABLECER NUM ENTRADA  -->
	<html:form action="altaEntradaExpediente" styleClass="remarcar opcions">
		<html:hidden property="tipo"/>
		<html:hidden property="tipo" value="E"/>
		<html:hidden property="numeroEntrada"/>
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


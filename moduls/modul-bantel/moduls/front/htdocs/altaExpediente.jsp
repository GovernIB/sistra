<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<script type="text/javascript" src="js/ajuda.js"></script>
<script type="text/javascript" src="js/funcions.js"></script>
<script type="text/javascript" src="js/mensaje.js"></script>
<script type="text/javascript">
     <!--

     // Configuracion procedimientos
     var configProcedimientos = {}; 
     <logic:iterate id="procedimiento" name="procedimientosGestor">
	     configProcedimientos["<bean:write name="procedimiento" property="identificador"/>"] = "<bean:write name="procedimiento" property="permitirSms"/>";     												
	 </logic:iterate>
    
     function mostrarArbolUnidades(url) {
        obrir(url, "Arbol", 540, 400);
     }
     
     function fillDestinatario(){
		nif = $("#nif").val();
		$.ajaxSetup({scriptCharset: "utf-8" , contentType: "application/json; charset=utf-8"});
		$.getJSON("fillPersona.do", {  nif: nif }, 
			function(json){				
				if (json.nif == "") {
					alert("<bean:message key="expediente.alta.noExisteDestinatario"/>");
				}				
				$("#nombre").val(json.nombre);
				$("#usuarioSeycon").val(json.usuarioSeycon);				
			});
	}

    function fillProcedimiento(){
        var idProcedimiento = $("#identificadorProcedimiento").val();
		if (configProcedimientos[idProcedimiento] == "S") {
			$("#divMovil").show();
		} else {
			$("#divMovil").hide();
			$("#movil").val("");
		} 
    }

    function mostrarAltaDestinatario(mostrar) {
    	var capaI = document.getElementById('altaDestinatario');
        if (mostrar) {

        	Tamanyos.iniciar();
        	Fondo.mostrar();
        	
        	$("#nifAltaDestinatario").val("");
     		$("#nombreAltaDestinatario").val("");
     		$("#apellido1AltaDestinatario").val("");
     		$("#apellido2AltaDestinatario").val("");
            
    		var ventanaX = document.documentElement.clientWidth;
    		var ventanaY = document.documentElement.clientHeight;
    		var capaY = document.getElementById('contenedor').offsetHeight;
    		
    		// mostramos, miramos su tama?o y centramos la capaInfo con respecto a la ventana
    		capaI.style.display = 'block';
    		capaInfoX = capaI.offsetWidth;
    		capaInfoY = capaI.offsetHeight;
    		with (capaI) {
    			style.left = (ventanaX-capaInfoX)/2 + 'px';
    			style.top = (ventanaY-capaInfoY)/2 + 'px';
    		}		
        } else {
        	Fondo.esconder();
        	capaI.style.display = 'none';
    	}
    }

     function realizarAltaDestinatario(){
 		var nifAlta = $("#nifAltaDestinatario").val();
 		var codPais = $("#codigoPais").val();
 		var nombreAlta = $("#nombreAltaDestinatario").val();
 		var ape1Alta = $("#apellido1AltaDestinatario").val();
 		var ape2Alta = $("#apellido2AltaDestinatario").val();

 		if(confirm ( "<bean:message key='expediente.alta.altaDestinatario.confirmacion' />" )){

 			var mapVars = {};
 			mapVars["nif"] = nifAlta;
 			mapVars["codigoPais"] = codPais;
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
	 					mostrarAltaDestinatario(false);
	 				} else {
	 	 				alert(json.error);
	 				}						
				}
			});
 	 		
 		}
 	}
		
	function alta(){		
		if(confirm ( "<bean:message key='expediente.alta.confirmacion' />" )){
			document.forms["0"].submit();
			return true;
		}else{
			return false;
		}						
	}

	function volver(){
		document.location='<html:rewrite page="/busquedaExpedientes.do"/>';			
	}

	function cambioHabilitar() {
		if ($('#habilitarAvisos').val() == "S") {
 			 $('#direcciones').show();		
 		} else {
 			 $('#direcciones').hide();			
 		}				
 	}
	
	//funcion que se ejecutan solo entrar en la pagina
	$(document).ready(function(){
		cambioHabilitar();
		fillProcedimiento();				        
		}
	);
    // -->
</script>

<bean:define id="urlArbol">
    <html:rewrite page="/arbolUnidades.do"/>
</bean:define>
<bean:define id="btnAlta" type="java.lang.String">
	<bean:message key="confirmacion.altaBoton"/>
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
			
			<p>
				<bean:message key="ajuda.expediente.alta"/>
			</p>
			
			<p>
				<bean:message key="ajuda.expediente.datosExpediente"/>
				<ul class="ajudaUl">
					<li><bean:message key="ajuda.expediente.alta.procedimiento"/></li>
					<li><bean:message key="ajuda.expediente.alta.identificador"/></li>
				</ul>				
			</p>
			
			<p>
				<bean:message key="ajuda.expediente.datosDestinatario"/>
				<ul class="ajudaUl">
					<li><bean:message key="ajuda.expediente.alta.nif"/></li>
					<li><bean:message key="ajuda.expediente.alta.nombre"/></li>
				</ul>
			</p>
			
			<p>
				<bean:message key="ajuda.expediente.datosDefinicion"/>
				<ul class="ajudaUl">
					<li><bean:message key="ajuda.expediente.alta.idioma"/></li>
					<li><bean:message key="ajuda.expediente.alta.Descripción"/></li>					
				</ul>
			</p>
			
			<p>
				<bean:message key="ajuda.expediente.datosAvisos"/>
				<ul class="ajudaUl">
					<li><bean:message key="ajuda.expediente.alta.habilitar"/></li>
					<li><bean:message key="ajuda.expediente.alta.email"/></li>
					<li><bean:message key="ajuda.expediente.alta.movil"/></li>
				</ul>
			</p>
			
			<p>
				<bean:message key="ajuda.CampoObligarorio"/>
			</p>
				
		</div>
		<!-- /ajuda -->
		<html:errors/>
		
		<!-- continguts -->
		<div class="continguts">
		
			<p class="titol"><bean:message key="confirmacion.alta"/></p>
			
			<html:form action="realizarAltaExpediente" styleClass="remarcar opcions">
				<html:hidden property="tipo"/>
				<html:hidden property="numeroEntrada"/>
				<html:hidden property="flagValidacion" value="altaExpedient"/>
				<html:hidden property="usuarioSeycon" styleId="usuarioSeycon"/>
				
				<p class="titol major">
					<bean:message key="expediente.datos"/>
				</p>
				
				<p>
					<label for="identificadorProcedimiento"><bean:message key="expediente.identificadorProcedimiento"/><sup>*</sup></label>
					
					<logic:present name="existeEntrada">
						<html:hidden property="identificadorProcedimiento" styleId="identificadorProcedimiento"/>
						<bean:define id="idProcEntrada" name="detalleExpedienteForm" property="identificadorProcedimiento" type="java.lang.String"/>							
						<logic:iterate id="procedimiento" name="procedimientosGestor">
							<logic:equal name="procedimiento" property="identificador" value="<%=idProcEntrada%>">
								<bean:write name="procedimiento" property="identificador"/> - <bean:write name="procedimiento" property="descripcion"/>
							</logic:equal>															
						</logic:iterate>
					</logic:present>
					<logic:notPresent name="existeEntrada">
						<html:select property="identificadorProcedimiento" styleClass="pc40" styleId="identificadorProcedimiento" onchange="fillProcedimiento();">
							<logic:iterate id="procedimiento" name="procedimientosGestor">							
								<html:option value="<%=((es.caib.bantel.model.Procedimiento)procedimiento).getIdentificador()%>" ><bean:write name="procedimiento" property="identificador"/> - <bean:write name="procedimiento" property="descripcion"/></html:option>
							</logic:iterate>
						</html:select>
					</logic:notPresent>					
				</p>							
				
				<p>
					<label for="identificadorExp"><bean:message key="confirmacion.identificadorExpediente"/><sup>*</sup></label>
					<html:text property="identificadorExp" maxlength="50"/>
				</p>
				
				<html:hidden property="claveExp"/>
				
				
				<p class="titol major">
					<bean:message key="expediente.datos.destinatario"/>
				</p>
					
				<p>
					<label for="nif"><bean:message key="expediente.nif"/><sup>*</sup></label>
					<logic:present name="existeEntrada">
						<html:text property="nif" styleId="nif" readonly="true"/>					
					</logic:present>
					<logic:notPresent name="existeEntrada">
						<html:text property="nif" styleId="nif" styleClass="nifBusqueda" onblur="this.value=this.value.toUpperCase()"/>					
						<img src="imgs/botons/cercar.gif" alt="<bean:message key='botons.cercar'/>"  onclick="javascript:fillDestinatario();"/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="mostrarAltaDestinatario(true);" value="<bean:message key='expediente.alta.altaDestinatario.botonAlta'/>"/> 
					</logic:notPresent>
				</p>
				<p>
					<label for="nombre"><bean:message key="expediente.nombre"/><sup>*</sup></label>
					<html:text property="nombre" styleId="nombre" styleClass="pc40" readonly="true"/>
				</p>
				
				
				<p class="titol major">
					<bean:message key="expedient.definicio"/>
				</p>
				<p>
					<label for="idioma"><bean:message key="expediente.idioma"/><sup>*</sup></label>
					
					<html:select property="idioma" styleClass="pc15">
						<html:option value="es"><bean:message key="expediente.castellano"/></html:option>
						<html:option value="ca"><bean:message key="expediente.catalan"/></html:option>
						<html:option value="en"><bean:message key="expediente.ingles"/></html:option>
					</html:select>
				</p>
				<p>
					<label for="descripcion"><bean:message key="expediente.descripcion"/><sup>*</sup></label>
					<html:textarea property="descripcion" rows="5"  cols="40" styleClass="pc40" />
				</p>
				
				<p class="titol major">
					<bean:message key="expedient.datos.aviso"/>
				</p>
				<p>
					<label for="habilitarAvisos"><bean:message key="expediente.habilitarAvisos"/><sup>*</sup></label>
					
					<logic:equal name="obligatorioAvisos" value="true">
						<html:hidden property="habilitarAvisos" styleId="habilitarAvisos" value="S"/>
						<bean:message key="expediente.avisos.si"/>						
					</logic:equal>
					
					<logic:equal name="obligatorioAvisos" value="false">						
						 <html:select property="habilitarAvisos" styleId="habilitarAvisos" styleClass="pc40" onchange="cambioHabilitar()" >
							<html:option value="S"><bean:message key="expediente.avisos.si"/></html:option>
							<html:option value="N"><bean:message key="expediente.avisos.no"/></html:option>
						</html:select>
					</logic:equal>
					
				</p>
				
				<div id="direcciones">
					<p>
						<label for="email"><bean:message key="expediente.avisoEmail"/><sup>*</sup></label>
						<html:text property="email" styleClass="pc30" maxlength="50"/>
					</p>
					<div id="divMovil">
					<p>
						<label for="movil"><bean:message key="expediente.avisoSMS"/></label>
						<html:text property="movil" styleId="movil" maxlength="10"/>
					</p>
					</div>
				</div>
				
				<p class="botonera">
					<input type="button" onclick="alta();" value="<%=btnAlta%>"/>
				</p>
			
			</html:form>
			
		</div>
		<!-- /continguts -->

<div id="fondo"></div>				
				
 <!--  capa alta destinatario -->
 <div id="altaDestinatario" class="altaDestinatario">	
 	<p>
		<bean:message key="expediente.alta.altaDestinatario.intro"/>
	</p>	
 	<form class="remarcar opcions">
 		<p>
			<label for="nifAltaDestinatario"><bean:message key="expediente.alta.altaDestinatario.nif"/></label>
			<input type="text" id="nifAltaDestinatario" name="nifAltaDestinatario" size="12"  class="nif"/>
		</p>
		<p>
			<label for="codigoPais"><bean:message key="expediente.alta.altaDestinatario.pais"/><sup>*</sup></label>
			<select name="codigoPais" id="codigoPais" property="codigoPais" class="pc20">
				<logic:iterate id="pais" name="paises">	
					<option value="<%=((es.caib.bantel.front.json.Pais)pais).getCodigo()%>" ><bean:write name="pais" property="descripcion"/></option>
				</logic:iterate>
			<select>
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
		<p>
			<a href="javascript:mostrarAltaDestinatario(false);"><bean:message key="expediente.alta.altaDestinatario.botonCancelar"/></a>
		</p>
 	</form> 	
</div>					

	<!-- tornar enrere -->
		<div id="enrere">
			<a href="#" onclick="javascript:volver()">
				<bean:message key="detalle.aviso.tornar" />				
			</a>				
		</div>
    <!-- /tornar enrere -->
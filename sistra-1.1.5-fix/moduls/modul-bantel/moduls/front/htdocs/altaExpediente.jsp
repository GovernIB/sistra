<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<script type="text/javascript" src="js/jquery.selectboxes.pack.js"></script>
<script type="text/javascript" src="js/ajuda.js"></script>
<script type="text/javascript" src="js/funcions.js"></script>
<script type="text/javascript">
     <!--
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


     function mostrarAltaDestinatario(mostrar) {
     	var capaI = document.getElementById('altaDestinatario');
         if (mostrar) {

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
         	capaI.style.display = 'none';
     	}
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
 				var index = document.forms["0"].unidadAdm.selectedIndex;
 				document.forms["0"].nombreUnidad.value=document.forms["0"].unidadAdm.options[index].text;
 				document.forms["0"].submit();
 				return true;
 			}else{
 				return false;
 			}						
 		}
 		
     // -->
</script>
<bean:define id="urlConfirmacion" type="java.lang.String">
	<html:rewrite href="/zonaperback/init.do" paramId="lang" paramName="<%= Globals.LOCALE_KEY  %>" paramProperty="language" paramScope="session"/>
</bean:define>
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
			<br/>
			<bean:message key="ajuda.CampoObligarorio"/>
				<bean:message key="ajuda.expediente.alta.identificador"/>
				<bean:message key="ajuda.expediente.unidad"/>
				<bean:message key="ajuda.expediente.alta.clave"/>
				<bean:message key="ajuda.expediente.alta.nif"/>
				<bean:message key="ajuda.expediente.alta.nombre"/>
				<bean:message key="ajuda.expediente.alta.idioma"/>
				<bean:message key="ajuda.expediente.alta.Descripción"/>
			<bean:message key="ajuda.finCampoObligarorio"/>
			
				<bean:message key="ajuda.expediente.alta.habilitar"/>
				<bean:message key="ajuda.expediente.alta.email"/>
				<bean:message key="ajuda.expediente.alta.movil"/>
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
				<html:hidden property="nombreUnidad"/>
				
				<p class="titol major">
					<bean:message key="expediente.datos"/>
				</p>
				<p>
					<label for="identificadorExp"><bean:message key="confirmacion.identificadorExpediente"/></label>
					<html:text property="identificadorExp"/>
				</p>
				
				<p>
					<label for="unidadAdm"><bean:message key="confirmacion.unidadAdministrativa"/></label>
					<html:select property="unidadAdm" styleClass="pc40">
						<logic:iterate id="unidad" name="unidades">	
							<html:option value="<%=((es.caib.bantel.front.json.UnidadAdministrativa)unidad).getCodigo()%>" ><bean:write name="unidad" property="descripcion"/></html:option>
						</logic:iterate>
					</html:select>
<%--					<button type="button" onclick="mostrarArbolUnidades('=urlArbol + "?id=unidadAdm" ');"><bean:message key="confirmacion.seleccionar"/></button>--%>
				</p>

				<p>
					<label for="claveExp"><bean:message key="confirmacion.claveExpediente"/></label>
					<html:text property="claveExp"/>
				</p>
				
				<p class="titol major">
					<bean:message key="expediente.datos.destinatario"/>
				</p>
					
				<p>
					<label for="nif"><bean:message key="expediente.nif"/></label>
					<logic:present name="existeEntrada">
						<html:text  property="nif" styleId="nif" readonly="true"/>					
					</logic:present>
					<logic:notPresent name="existeEntrada">
						<html:text property="nif" styleId="nif" onblur="this.value=this.value.toUpperCase()"/>					
						<img src="imgs/botons/cercar.gif" alt="<bean:message key='botons.cercar'/>"  onclick="javascript:fillDestinatario();"/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="mostrarAltaDestinatario(true);" value="<bean:message key='expediente.alta.altaDestinatario.botonAlta'/>"/>  
					</logic:notPresent>
				</p>
				<p>
					<label for="nombre"><bean:message key="expediente.nombre"/></label>
					<html:text  property="nombre" styleId="nombre"  styleClass="pc40" readonly="true"/>
				</p>
				
				
				<p class="titol major">
					<bean:message key="expedient.definicio"/>
				</p>
				<p>
					<label for="idioma"><bean:message key="expediente.idioma"/></label>
					<html:select property="idioma" styleClass="pc15">
						<html:option value="es"><bean:message key="expediente.castellano"/></html:option>
						<html:option value="ca"><bean:message key="expediente.catalan"/></html:option>
						<html:option value="en"><bean:message key="expediente.ingles"/></html:option>
					</html:select>
				</p>
				<p>
					<label for="descripcion"><bean:message key="expediente.descripcion"/></label>
					<html:textarea property="descripcion" rows="5"  cols="40" styleClass="pc40" />
				</p>
				
				<p class="titol major">
					<bean:message key="expedient.datos.aviso"/>
				</p>
				<p>
					<label for="habilitarAvisos"><bean:message key="expediente.habilitarAvisos"/></label>
					<html:select property="habilitarAvisos" styleClass="pc15">
						<html:option value=""><bean:message key="expediente.noEspecificado"/></html:option>
						<html:option value="S"><bean:message key="expediente.si"/></html:option>
						<html:option value="N"><bean:message key="expediente.no"/></html:option>
					</html:select>
				</p>
				
				<p>
					<label for="email"><bean:message key="expediente.avisoEmail"/></label>
					<html:text property="email" styleClass="pc30"/>
				</p>
				
				<p>
					<label for="movil"><bean:message key="expediente.avisoSMS"/></label>
					<html:text property="movil"/>
				</p>
				
				<p class="botonera">
					<input type="button" onclick="alta();" value="<%=btnAlta%>"/>
				</p>
			
			</html:form>
			
		</div>
		<!-- /continguts -->
		
						
 <!--  capa alta destinatario -->
 <div id="altaDestinatario" class="altaDestinatario">	
 	<p>
		<bean:message key="expediente.alta.altaDestinatario.intro"/>
	</p>	
 	<form  class="remarcar opcions">
 		<p>
			<label for="nifAltaDestinatario"><bean:message key="expediente.alta.altaDestinatario.nif"/></label>
			<input type="text" id="nifAltaDestinatario" name="nifAltaDestinatario" size="12"  class="nif"/>
		</p>
 		<p>
			<label for="nombreAltaDestinatario"><bean:message key="expediente.alta.altaDestinatario.nombre"/></label>
			<input type="text" id="nombreAltaDestinatario" name="nombreAltaDestinatario" class="pc40" />
		</p>
		<p>
			<label for="apellido1AltaDestinatario"><bean:message key="expediente.alta.altaDestinatario.apellido1"/></label>
			<input type="text" id="apellido1AltaDestinatario" name="apellido1AltaDestinatario" class="pc40" />
		</p>
		<p>
			<label for="apellido2AltaDestinatario"><bean:message key="expediente.alta.altaDestinatario.apellido2"/></label>
			<input type="text" id="apellido2AltaDestinatario" name="apellido2AltaDestinatario" class="pc40" />
		</p>
		<p class="botonera">
			<input type="button" onclick="realizarAltaDestinatario();" value="<bean:message key="expediente.alta.altaDestinatario.botonAlta"/>"/>
			<input type="button" onclick="mostrarAltaDestinatario(false);" value="<bean:message key="expediente.alta.altaDestinatario.botonCancelar"/>"/>
		</p>
 	</form> 	
</div>					
		
				

<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<link href="estilos/calendar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.selectboxes.pack.js"></script>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/lang/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>
<script type="text/javascript" src="js/calendario.js"></script>
<script type="text/javascript" src="js/fechas.js"></script>
<script type="text/javascript" src="js/funcions.js"></script>
<script type="text/javascript" src="js/mensaje.js"></script>
	
<script type="text/javascript">
//funcion que se ejecutan solo entrar en la pagina
$(document).ready(function(){

	<logic:present name="detalleEntidadForm">
		<logic:notEqual name="detalleEntidadForm" property="modificacio"  value="S">
		 	$('#divApellidos').hide();
	     	$('#documentoId').text('CIF'); 
	     </logic:notEqual>	
	
	     <logic:equal name="detalleEntidadForm" property="modificacio"  value="S">
		     <logic:equal name="detalleEntidadForm" property="tipo"  value="CIF">
			 	$('#divApellidos').hide();
		  		$('#documentoId').text('CIF'); 
			 </logic:equal>		  	
			 <logic:notEqual name="detalleEntidadForm" property="tipo"  value="CIF">
			 	$('#documentoId').text('NIF'); 
			 </logic:notEqual>
	  	 </logic:equal>
	  </logic:present>

	  <logic:notPresent name="detalleEntidadForm">
		 	$('#divApellidos').hide();
	     	$('#documentoId').text('CIF'); 	    	
	  </logic:notPresent>
	 	
		$.postJSON = function(url, data, callback) {
        	return jQuery.ajax({
		        'type': 'POST',
		        'url': url,
		        'data': data,
		        'dataType': 'json',
		        'success': callback
		    });
		};
		if($("#altaCorrecta").val() == "S"){
			alert("<bean:message key="entidad.alta.correcta"/>");
			$("#altaCorrecta").val('');
		}
	}
);


function llenarMunicipios(){
	var codProv = $("#provincia").val();	
	var url_json = '<html:rewrite page="/listarMunicipios.do"/>';
	var data ='provincia='+codProv;
	$.postJSON(url_json, data, 
		function(datos){
			$("#municipio").removeOption(/./);
			$("#municipio").removeOption(""); 
			$("#municipio").addOption(datos, false); 	
	});
}

function volver(){
	document.detalleEntidadForm.action='<html:rewrite page="/init.do" />';
	document.detalleEntidadForm.submit();
}
</script>

<script type="text/javascript"> 
 
$(function() 
{ 
  $('input.tipoPersonaClass').click( function( event ) 
  { 
    var val = ($('input:radio[name=tipoPersona]:checked').val()); 
    if (val == "nif") {
      $('#divApellidos').show();     
      $('#documentoId').text('NIF');  
    } else {
      $('#divApellidos').hide();
      $('#documentoId').text('CIF'); 
    }
  }) 
} ); 
 
</script> 

		<bean:define id="urlHabilitarEntidad"  type="java.lang.String">
			<html:rewrite page="/habilitarEntidad.do"/>
		</bean:define>
						
		<!-- continguts -->
		<div class="continguts">
			<html:errors/>		
			<p class="titol"><bean:message key="entidad.alta.titulo"/></p>
			<html:form action="realizarAltaEntidad" enctype="multipart/form-data" styleClass="remarcar opcions">
			<div class="remarcar">
				<html:hidden styleId="modificacio" property="modificacio"/>
				<html:hidden styleId="habilitada" property="habilitada"/>
				<html:hidden styleId="representante" property="representante"/>
				<html:hidden styleId="personaJuridica" property="persona.personaJuridica"/>
				<html:hidden styleId="altaCorrecta" property="altaCorrecta"/>
				<logic:notEqual name="detalleEntidadForm" property="modificacio"  value="S">
					<p>
						<label for="tipoPersona"><bean:message key="entidad.tipoPersona"/></label>
						<input class="tipoPersonaClass" type="radio" name="tipoPersona" id="tipoPersonaCif" value="cif" checked="checked"/> 
					    <bean:message key="entidad.tipoPersonaCif"/>					    
					    <input class="tipoPersonaClass" type="radio" name="tipoPersona" id="tipoPersonaNif" value="nif"/> 
					    <bean:message key="entidad.tipoPersonaNif"/>
					</p>
				</logic:notEqual>
				<p>
					<label for="persona.nif" id="documentoId"><bean:message key="entidad.documentoIdLegal"/></label>
					<logic:equal name="detalleEntidadForm" property="modificacio" value="S">
						<html:text styleId="nif" property="persona.nif" styleClass="pc40" readonly="true"/>
					</logic:equal>
					<logic:notEqual name="detalleEntidadForm" property="modificacio"  value="S">
						<html:text styleId="nif" property="persona.nif" styleClass="pc40"/>
					</logic:notEqual>
				</p>
				<p>
					<label for="persona.nombre"><bean:message key="entidad.nombre"/></label>
					<html:text styleId="nombre" property="persona.nombre" styleClass="pc40"/>
				</p>
				<div id="divApellidos">
				<p>
					<label for="persona.apellido1"><bean:message key="entidad.apellido1"/></label>
					<html:text styleId="apellido1" property="persona.apellido1" styleClass="pc40"/>
				</p>
				<p>
					<label for="persona.apellido2"><bean:message key="entidad.apellido2"/></label>
					<html:text styleId="apellido2" property="persona.apellido2" styleClass="pc40"/>
				</p>
				</div>
				<p>
					<label for="persona.direccion"><bean:message key="entidad.direccion"/></label>
					<html:text styleId="direccion" property="persona.direccion" styleClass="pc40"/>
				</p>
				<p>
					<label for="persona.codigoPostal"><bean:message key="entidad.codigoPostal"/></label>
					<html:text styleId="codigoPostal" property="persona.codigoPostal" styleClass="pc40"/>
				</p>
				<p>
					<label for="persona.provincia"><bean:message key="entidad.provincia"/></label>
					<html:select property="persona.provincia" styleId="provincia" onchange="javascript:llenarMunicipios();" styleClass="pc20">
						<logic:iterate id="provincia" name="provincias">	
							<html:option value="<%=((es.caib.zonaper.delega.json.Provincia)provincia).getCodigo()%>" ><bean:write name="provincia" property="descripcion"/></html:option>
						</logic:iterate>
					</html:select>
				</p>
				<p>
					<label for="persona.municipio"><bean:message key="entidad.municipio"/></label>
					<html:select property="persona.municipio" styleId="municipio" styleClass="pc20">
						<logic:present name="municipios">
							<logic:iterate id="municipio" name="municipios">	
								<html:option value="<%=((es.caib.zonaper.delega.json.Localidad)municipio).getCodigo().toString()%>"><bean:write name="municipio" property="descripcion"/></html:option>
							</logic:iterate>
						</logic:present>
					</html:select>
				</p>
				<p>
					<label for="persona.telefonoFijo"><bean:message key="entidad.telefonoFijo"/></label>
					<html:text styleId="telefonoFijo" property="persona.telefonoFijo" styleClass="pc40"/>
				</p>
				<p>
					<label for="persona.telefonoMovil"><bean:message key="entidad.telefonoMovil"/></label>
					<html:text styleId="telefonoMovil" property="persona.telefonoMovil" styleClass="pc40"/>
				</p>
				<p>
					<label for="persona.email"><bean:message key="entidad.email"/></label>
					<html:text styleId="email" property="persona.email" styleClass="pc40"/>
				</p>
				<p>
					<label for="persona.habilitarAvisosExpediente"><bean:message key="entidad.habilitarAvisosExpediente"/></label>
					<html:radio property="persona.habilitarAvisosExpediente" value="true"/>Si
					<html:radio property="persona.habilitarAvisosExpediente" value="false"/>No
				</p>
				<p>
					<label for="persona.usuarioSeycon"><bean:message key="entidad.usuarioSeycon"/></label>
					<logic:equal name="detalleEntidadForm" property="modificacio"  value="S">
						<html:text styleId="usuarioSeycon" property="persona.usuarioSeycon" styleClass="pc40" readonly="true"/>
					</logic:equal>
					<logic:notEqual name="detalleEntidadForm" property="modificacio" value="S">
						<html:text styleId="usuarioSeycon" property="persona.usuarioSeycon" styleClass="pc40"/>
					</logic:notEqual>
					
				</p>
				<p class="botonera">
					<logic:equal name="detalleEntidadForm" property="modificacio"  value="S">
						<html:submit><bean:message key="entidad.modifica"/></html:submit>
					</logic:equal>
					<logic:notEqual name="detalleEntidadForm" property="modificacio" value="S">
						<html:submit><bean:message key="entidad.alta"/></html:submit>
					</logic:notEqual>
					<input type="button" value="<bean:message key="entidad.volver"/>" onclick="volver()"/>
				</p>
				
			</div>
			</html:form>
		</div>
		<!-- /continguts -->
		
		<div id="fondo"></div>
		
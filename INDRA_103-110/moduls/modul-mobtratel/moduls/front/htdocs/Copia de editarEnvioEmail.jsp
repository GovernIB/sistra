<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.mobtratel.model.Envio"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib uri="front" prefix="front"%>

	<!-- calendari -->
<link href="estilos/calendar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/lang/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>
<script type="text/javascript" src="js/calendario.js"></script>
<script type="text/javascript" src="js/fechas.js"></script>

<!-- mask -->
<script type="text/javascript" src="js/jquery-1.2.3.pack.js"></script>
<script type="text/javascript" src="js/jquery.maskedinput-1.1.4.pack.js"></script>

<script type="text/javascript">
     <!--
     function edit(url) {
       obrir(url, "Edicion", 940, 600);
     }
     // Obrir un pop up
	function obrir(url, name, x, y) {
	   window.open(url, name, 'scrollbars=yes, resizable=yes, width=' + x + ',height=' + y);
	}
    function validaFormulario( form )
    {
    	form.submit();
    }
    

    function borrarFechaProgramacion() {
	document.getElementById('fechaProgramacion').value = '';
	document.getElementById('horaProgramacion').value = '';
    }
    function borrarFechaCaducidad() {
	document.getElementById('fechaCaducidad').value = '';
	document.getElementById('horaCaducidad').value = '';
    }
    
    // HORA
	jQuery(function($){
		$("#horaProgramacion").mask("99:99");
	});
    // HORA
	jQuery(function($){
		$("#horaCaducidad").mask("99:99");
	});

     // -->
</script>


<html:errors/>	
<logic:present name="ok">
		<div class="correcte"><bean:message name="ok"/></div>
</logic:present>			
<logic:present name="error">
		<div class="error"><bean:message name="error"/></div>
</logic:present>			
<logic:present name="errorFormato">
		<div class="error"><bean:write name="errorFormato"/></div>
</logic:present>			


<logic:notPresent name="ok">
<h2><bean:message key="editarEnvio.email.titulo"/></h2>

<div id="contenedor">	
			
	<html:form action="editarEnvioEmail" styleId="editarEnvioEmailForm" styleClass="centrat">

	<div class="element">
		<div class="etiqueta"><label for="nombre"><bean:message key="editarEnvio.email.mensaje.nombre"/></label></div>
		<div class="control"><html:text styleClass="data" tabindex="1" property="nombre" size="100" maxlength="100" /></div>
	</div>			
	<div class="element">
		<div class="etiqueta"><label for="titulo"><bean:message key="editarEnvio.email.mensaje.titulo"/></label></div>
		<div class="control"><html:text styleClass="data" tabindex="2" property="titulo" size="100" maxlength="100"/></div>
	</div>			
	<div class="element">
		<div class="etiqueta"><label for="mensaje"><bean:message key="editarEnvio.email.mensaje.mensaje"/></label></div>
		<div class="control"><html:textarea property="mensaje" tabindex="3" cols="50" rows="3"/></div>
	</div>			
	<div class="element">
		<div class="etiqueta"><label for="destinatarios"><bean:message key="editarEnvio.email.mensaje.destinatarios"/></label></div>
		<div class="control">
			<p><bean:message key="editarEnvio.email.mensaje.destinatarios.ayuda"/></p>
			<html:textarea property="destinatarios" tabindex="4" cols="50" rows="3"/>
		</div>
	</div>			
	<div class="element">
		<div class="etiqueta"><label for="cuenta"><bean:message key="editarEnvio.email.mensaje.cuenta"/></label></div>
		<div class="control">
		    	<logic:present name="cuentas">
			    	<html:select property="cuenta" tabindex="5">
		   			<html:options collection="cuentas" property="codigo" labelProperty="nombre" />
	   		    	</html:select>
			</logic:present>
			<logic:notPresent name="cuentas">
				<span class="alert"><bean:message key="editarEnvio.email.mensaje.noCuentas"/></span>
			</logic:notPresent>
		</div>
	</div>			
	<div class="element">
		<div class="etiqueta"><label for="fechaProgramacion"><bean:message key="editarEnvio.email.mensaje.programacion"/></label></div>
		<div class="control">
			<html:text property="fechaProgramacion"  tabindex="6" styleId="fechaProgramacion" readonly="true" size="10" />
        		<button id="dia_calendario" type="button" tabindex="7" title="Mostrar calendari"><img src="imgs/botons/calendari.gif" alt="" /></button> <button type="button" tabindex="8" title="Esborrar dia" onclick="borrarFechaProgramacion();"><img src="imgs/botons/calendari_borrar.gif" alt="" /></button>
        		<label for="horaProgramacion"><bean:message key="editarEnvio.email.mensaje.horaProgramacion"/></label>
        		<html:text property="horaProgramacion"  tabindex="9"  styleId="horaProgramacion" size="3" maxlength="5" styleClass="hora" />
		</div>
	</div>		
	<div class="element">
		<div class="etiqueta"><label for="fechaCaducidad"><bean:message key="editarEnvio.email.mensaje.caducidad"/></label></div>
		<div class="control">
			<html:text property="fechaCaducidad" tabindex="10" styleId="fechaCaducidad" readonly="true" size="10" />
        		<button id="dia_calendario_caducidad" type="button" tabindex="11" title="Mostrar calendari"><img src="imgs/botons/calendari.gif" alt="" /></button> <button type="button" tabindex="12" title="Esborrar dia" onclick="borrarFechaCaducidad();"><img src="imgs/botons/calendari_borrar.gif" alt="" /></button>
        		<label for="horaCaducidad"><bean:message key="editarEnvio.email.mensaje.horaCaducidad"/></label>
        		<html:text property="horaCaducidad"  tabindex="13"  styleId="horaCaducidad" size="3" maxlength="5" styleClass="hora" />
		</div>
	</div>			
	<script type="text/javascript">
	<!--
		Calendar.setup({
			inputField: "fechaProgramacion", ifFormat: "%d/%m/%Y", button: "dia_calendario", weekNumbers: false
		});
		Calendar.setup({
			inputField: "fechaCaducidad", ifFormat: "%d/%m/%Y", button: "dia_calendario_caducidad", weekNumbers: false
		});
	-->
	</script>
	<p class="botonera">
                <front:accio tipus="alta" tabindex="14" styleClass="button" />
	</p>
	</html:form>
</logic:notPresent>
<p class="tornarArrere"><strong><a href="/mobtratelfront/init.do"><bean:message key="editarEnvio.volver"/></a></strong></p>
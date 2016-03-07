<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.mobtratel.model.Envio"%>
<%@ page import="es.caib.mobtratel.front.Constants" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib uri="front" prefix="front"%>


<bean:define id="messageAyuda" value="ayuda.envioSms" />

<bean:define id="urlAyuda" type="java.lang.String">
	<html:rewrite page="/ayuda.do" paramId="<%= Constants.MESSAGE_KEY %>" paramName="messageAyuda"/>
</bean:define>

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

<h2><bean:message key="editarEnvio.sms.titulo"/></h2>
		<p align="right"><html:link href="#" onclick="<%= \"javascript:obrir('\" + urlAyuda + \"', 'Edicion', 540, 400);\"%>"><img src="imgs/icones/ico_ayuda.gif" border="0"/><bean:message key="ayuda.enlace" /></html:link></p>
<div id="contenedor">	
			
	<html:form action="editarEnvioSMS" styleId="editarEnvioSmsForm" styleClass="centrat">
	<div class="element">
		<div class="etiqueta"><label for="nombre"><bean:message key="editarEnvio.sms.mensaje.nombre"/></label></div>
		<div class="control"><html:text styleClass="data" tabindex="1" property="nombre" size="100" maxlength="100" /></div>
	</div>			
	<div class="element">
		<div class="etiqueta"><label for="mensaje"><bean:message key="editarEnvio.sms.mensaje.mensaje"/></label></div>
		<div class="control"><html:textarea property="mensaje" tabindex="2" cols="50" rows="3"/></div>
	</div>			
	<div class="element">
		<div class="etiqueta"><label for="destinatarios"><bean:message key="editarEnvio.sms.mensaje.destinatarios"/></label></div>
		<div class="control">
			<p><bean:message key="editarEnvio.sms.mensaje.destinatarios.ayuda"/></p>
			<html:textarea property="destinatarios" tabindex="3" cols="50" rows="3"/>
		</div>
	</div>			
	<div class="element">
		<div class="etiqueta"><label for="cuenta"><bean:message key="editarEnvio.sms.mensaje.cuenta"/></label></div>
		<div class="control">
		    	<logic:present name="cuentas">
			    	<html:select property="cuenta" tabindex="4" >
		   			<html:options collection="cuentas" property="codigo" labelProperty="nombre" />
	   		    	</html:select>
			</logic:present>
			<logic:notPresent name="cuentas">
				<span class="alert"><bean:message key="editarEnvio.sms.mensaje.noCuentas"/></span>
			</logic:notPresent>
		</div>
	</div>			
	<div class="element">
	
	
	<div class="etiqueta"><label for="fechaProgramacion"><bean:message key="editarEnvio.sms.mensaje.tipoProgramacion"/></label></div>
		<div class="control">
			<!-- Envio inmediato o programado -->	
			<html:radio property="inmediato" value="S"><bean:message key="editarEnvio.sms.mensaje.tipoProgramacion.inmediato"/></html:radio>
			<html:radio property="inmediato" value="N"><bean:message key="editarEnvio.sms.mensaje.tipoProgramacion.programado"/></html:radio>
			<!--  Envio programado -->
			<bean:message key="editarEnvio.sms.mensaje.programacion"/>
			<html:text property="fechaProgramacion" tabindex="5"  styleId="fechaProgramacion" readonly="true" size="10" />
        	<button id="dia_calendario" type="button" tabindex="6" title="Mostrar calendari"><img src="imgs/botons/calendari.gif" alt="" /></button> <button type="button" tabindex="7"  title="Esborrar dia" onclick="borrarFechaProgramacion();"><img src="imgs/botons/calendari_borrar.gif" alt="" /></button>
        	&nbsp;<label for="horaProgramacion"><bean:message key="editarEnvio.email.mensaje.horaProgramacion"/></label>
			<html:select property="horaProgramacion"   tabindex="7">
				<logic:iterate id="tmpHora" name="horas">
					<html:option value="<%= tmpHora.toString() %>"><%= tmpHora.toString() %></html:option>
				</logic:iterate>			
			</html:select>				
		</div>
	</div>			
	<div class="element">
		<div class="etiqueta"><label for="fechaCaducidad"><bean:message key="editarEnvio.sms.mensaje.caducidad"/></label></div>
		<div class="control">
			<html:text property="fechaCaducidad" tabindex="9"  styleId="fechaCaducidad" readonly="true" size="10" />
        	<button id="dia_calendario_caducidad" type="button"  tabindex="10" title="Mostrar calendari"><img src="imgs/botons/calendari.gif" alt="" /></button> <button type="button" tabindex="11"   title="Esborrar dia" onclick="borrarFechaCaducidad();"><img src="imgs/botons/calendari_borrar.gif" alt="" /></button>
        	&nbsp;&nbsp;<label for="horaCaducidad"><bean:message key="editarEnvio.email.mensaje.horaCaducidad"/></label>
			<html:select property="horaCaducidad"  tabindex="12">
				<logic:iterate id="tmpHora" name="horas">
					<html:option value="<%= tmpHora.toString() %>"><%= tmpHora.toString() %></html:option>
				</logic:iterate>			
			</html:select>				
		</div>
	</div>			
	<div class="element">
		<div class="etiqueta"><label for="confirmarEnvio"><bean:message key="editarEnvio.sms.mensaje.confirmarEnvio"/></label></div>
		<div class="control">
				<!-- Envio inmediato o programado -->	
				<html:radio property="confirmarEnvio" value="S">Si</html:radio>
				<html:radio property="confirmarEnvio" value="N">No</html:radio>
				 &nbsp;
				<bean:message key="editarEnvio.sms.mensaje.confirmarEnvio.detalle"/>
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
                <front:accio tipus="alta"  tabindex="13" styleClass="button" />
	</p>
	
	</html:form>
</logic:notPresent>

<p class="tornarArrere"><strong><a href="init.do"><bean:message key="editarEnvio.volver"/></a></strong></p>
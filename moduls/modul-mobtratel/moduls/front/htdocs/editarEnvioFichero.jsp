<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.mobtratel.model.Envio"%>
<%@ page import="es.caib.mobtratel.front.Constants" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib uri="front" prefix="front"%>

<bean:define id="messageAyuda" value="ayuda.envioFichero" />

<bean:define id="urlAyuda" type="java.lang.String">
	<html:rewrite page="/ayuda.do" paramId="<%= Constants.MESSAGE_KEY %>" paramName="messageAyuda"/>
</bean:define>

<script type="text/javascript">
     <!--
	function edit(url) {
       		obrir(url, "Edicion", 940, 600);
     	}
     	
     	function archivoSeleccionar(obj) {
		valor = obj.value;
		if (valor.indexOf(':') != -1 && valor.length > 10) {
			ar = valor.substr(valor.lastIndexOf('\\')+1);
			obj.style.display = 'none';				
			// creamos nombre
			span = document.createElement('span');
			span.innerHTML = ar + ' - <a href="javascript:void(0);" onclick="archivoBorrar(this)">Borrar archivo</a>';
			obj.parentNode.appendChild(span);
		}
     	}
			
	function archivoBorrar(obj) {
		obj.parentNode.parentNode.innerHTML = '<input type="file" name="archivo" id="archivo" onchange="archivoSeleccionar(this)" />';
	}


     // -->
</script>


<html:errors/>

<logic:present name="messageKey">
		<div class="error"><bean:write name="messageKey"/></div>
</logic:present>			

<h2><bean:message key="editarEnvioFichero.titulo"/></h2>
<p align="right"><html:link href="#" onclick="<%= \"javascript:obrir('\" + urlAyuda + \"', 'Edicion', 540, 400);\"%>"><img src="imgs/icones/ico_ayuda.gif" border="0"/><bean:message key="ayuda.enlace" /></html:link></p>

<logic:present name="ok">
		<div class="correcte"><bean:message name="ok"/></div>
</logic:present>	

<logic:present name="errorFormato">
		<div class="error"><bean:write name="errorFormato"/></div>
</logic:present>			
		

<logic:notPresent name="ok">
<div id="contenedor">	
		
	<html:form action="editarEnvioFichero" styleId="editarEnvioFicheroForm" styleClass="centrat"  enctype="multipart/form-data">

		
		<div class="element">
			<div class="etiqueta">
				<label for="archivo">Adjuntar archivo</label>
			</div>
			<div class="control">
				<html:file property="fichero" styleClass="file" tabindex="1"  onchange="archivoSeleccionar(this)"/>
			</div>
		</div>

		<bean:define id="botonEnviar" type="java.lang.String">
	    	<bean:message key="boton.enviarBusqueda" />
        </bean:define>

		<p class="botonera">
        	<front:accio tipus="alta" tabindex="2" styleClass="button" />

		</p>


	</html:form>
</div>	
</logic:notPresent>	


<p class="tornarArrere"><strong><a href="init.do"><bean:message key="detalleEnvio.volver"/></a></strong></p>
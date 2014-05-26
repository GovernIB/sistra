<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<bean:define id="urlFinalizar">
        <html:rewrite page="/protected/finalizar.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>

<bean:define id="urlMostrarDocumento">
        <html:rewrite page="/protected/mostrarDocumento.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>


<logic:notEmpty name="tramite" property="instruccionesEntrega">
	<h2><bean:message key="pasoResultadoAsistente.ResultadoAsistente.instrucciones"/></h2>
	<p><bean:write name="tramite" property="instruccionesEntrega" filter="false"/></p>
</logic:notEmpty>

<h2><bean:message key="pasoResultadoAsistente.ResultadoAsistente.imprimir"/></h2>
<p><bean:message key="pasoResultadoAsistente.ResultadoAsistente.informacion"/></p>


<!-- Capa formularios -->
<div id="listadoFormularios">

	<logic:iterate id="formulario" name="tramite" property="formularios" type="es.caib.sistra.model.DocumentoFront">
											
		<div class="iconos">
			<img src="imgs/tramitacion/iconos/ico_form.gif" alt="<bean:message key="pasoRellenar.formulario"/>" title="Formulario" />
		</div>
		
		<p>					
			<span style="position: relative;">					
				<html:link href="<%= urlMostrarDocumento + \"&identificador=\" + formulario.getIdentificador() + \"&instancia=\" + formulario.getInstancia() %>">
					<strong>
						<bean:write name="formulario" property="descripcion" />
					</strong>
				</html:link>										
			</span>			
		</p>
					
	</logic:iterate>
</div>
<!--  Fin div formularios -->

<p class="ultimo">			

<p class="centrado">
	<strong><bean:message key="pasoResultadoConsulta.finalizacion.instrucciones" /></strong>
</p>
<p class="centrado">
	<input name="finalizarPRBoton" id="finalizarPRBoton" type="button" value="<bean:message key="finalizacion.boton.finalizar"/>" onclick="javascript:document.location.href='<%= urlFinalizar.toString() %>';" />
</p>								
<div class="sep"></div>
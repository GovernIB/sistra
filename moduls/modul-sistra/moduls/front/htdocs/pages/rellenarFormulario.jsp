<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
		<h2><bean:message key="rellenarFormulario.rellenarFormulario"/></h2>
		<p class="ultimo"><bean:message key="rellenarFormulario.rellenarFormulario.instrucciones"/></p>
		<div id="datosTramiteImprimir">
		<html:form action="/protected/guardarFormulario">
			<html:hidden name="irAFormularioForm" property="ID_INSTANCIA"/>
			<html:hidden name="irAFormularioForm" property="instancia" />
			<html:hidden name="irAFormularioForm" property="identificador" />
			<html:hidden property="datosAnteriores" value="aa"/>
			<html:hidden property="datosNuevos" value="bb"/>
				<h3><span class="letra">B</span> <bean:message key="rellenarFormulario.rellenarFormulario.datosConvocatoria"/></h3>
				<p><bean:message key="rellenarFormulario.rellenarFormulario.datosConvocatoria.fecha"/> <input name="fecha" type="text" size="10"/> <bean:message key="rellenarFormulario.rellenarFormulario.datosConvocatoria.fechaFinal"/> <input name="fechaFinal" type="text" size="10"/></p>
				<h3><span class="letra">D</span> <bean:message key="rellenarFormulario.rellenarFormulario.datosPersona"/></h3>
				<p>NIF <input name="nif_pi" type="text" size="9"/> <bean:message key="rellenarFormulario.rellenarFormulario.datosPersona.nombre"/> <input name="nombre_pi" type="text" size="57"/></p>
				<p><bean:message key="rellenarFormulario.rellenarFormulario.datosPersona.domicilio"/> <input name="domicilio_pi" type="text" size="64"/></p>
				<p><bean:message key="rellenarFormulario.rellenarFormulario.datosPersona.localidad"/> <input name="localidad_pi" type="text" size="19"/> <bean:message key="rellenarFormulario.rellenarFormulario.datosPersona.provincia"/> <input name="provincia_pi" type="text" size="19"/></p>
				<p><bean:message key="rellenarFormulario.rellenarFormulario.datosPersona.codigoPostal"/> <input name="cp_pi" type="text" size="9"/> <bean:message key="rellenarFormulario.rellenarFormulario.datosPersona.nacionalidad"/> <input name="nacionalidad_pi" type="text" size="9"/></p>
				<p><bean:message key="rellenarFormulario.rellenarFormulario.datosPersona.telefono"/> <input name="telefono_pi" type="text" size="9"/> <bean:message key="rellenarFormulario.rellenarFormulario.datosPersona.correoElectronico"/> <input name="email_pi" type="text" size="22"/></p>
			</div>
			<p class="centrado"><input name="imprimirSolicitudBoton" id="imprimirSolicitudBoton" type="button" value="Enviar" onclick="javascript:this.form.submit()"/></p>
			<div class="sep"></div>
		</html:form>	
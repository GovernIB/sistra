<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<script type="text/javascript">
<!--
	function validaFormulario( form )
    {
		if ( isEmptyObject ( form.nombre_pi ) )
		{
			alert( "Introduzca el nombre completo" );	
			form.nombre_pi.focus();
			return;
		}
		if ( isEmptyObject( form.nif_pi ) )
		{
			alert( "Introduzca un NIF válido" );
			form.nif_pi.focus();
			return;
		}
		if ( !validaNIF( form.nif_pi.value ) )
		{
			alert( "Introduzca un NIF válido" );
			form.nif_pi.focus();
			return;
		}
		if ( !isEmptyObject( form.cp_pi ) && !esCodigoPostal( form.cp_pi.value ) )
		{
			form.cp_pi.focus();
			return;
		}
		if ( !isEmptyObject( form.email_pi ) && !esEmail( form.email_pi.value ) )
		{
			form.email_pi.focus();
			alert( "Introduzca un email correcto" );
			return;
		}
		form.submit();
    }
-->    
</script>
				<h3><span class="letra">A</span> Datos de la convocatoria</h3>
				<p>Fecha de convocatoria <html:text name="fakeFormForm" property="fecha" size="10" readonly="true"/> Fecha final de presentación de instancias <html:text name="fakeFormForm" property="fechaFinal" size="10" readonly="true"/></p>
				<h3><span class="letra">B</span> Datos de la persona interesada</h3>
				<p>NIF <html:text name="fakeFormForm" property="nif_pi" size="9" maxlength="9"/> Nombre completo <html:text name="fakeFormForm" property="nombre_pi" size="57" maxlength="50"/></p>
				<p>Domicilio (Calle/Plaza y número) <html:text name="fakeFormForm" property="domicilio_pi" size="64" /></p>
				<p>Localidad <html:text name="fakeFormForm" property="localidad_pi" size="19" /> Provincia <html:text name="fakeFormForm" property="provincia_pi" size="19" /></p>
				<p>Código Postal <html:text name="fakeFormForm" property="cp_pi" size="9" /> Nacionalidad <html:text name="fakeFormForm" property="nacionalidad_pi" size="9" /></p>
				<p>Teléfono <html:text name="fakeFormForm" property="telefono_pi" size="9" maxlength="9"/> Correo Electrónico <html:text name="fakeFormForm" property="email_pi" size="22" /></p>
				<h3><span class="letra">C</span> Datos complementarios</h3>
				<p>Tipo de examen <html:radio name="fakeFormForm" property="tipoexamen_pi" value="G"/>Global <html:radio name="fakeFormForm" property="tipoexamen_pi" value="P"/>Parcial</p>
				<p>¿Ha realizado cursos complementarios? <html:checkbox name="fakeFormForm" property="cursoscomplementarios_pi" value="true"/></p>

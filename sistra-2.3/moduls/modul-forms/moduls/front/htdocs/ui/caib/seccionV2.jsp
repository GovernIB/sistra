<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.ibit.rol.form.model.Validacion"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>

<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.Seccion"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
	<h4 class="imc-seccio">
		<span class="imc-se-marca"><nested:write property="letraSeccion"/></span>
		<span class="imc-se-titol"><nested:write property="traduccion.etiqueta"/></span>
	</h4>
</nested:root>
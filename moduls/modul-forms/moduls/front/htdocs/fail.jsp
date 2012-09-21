<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/></title>
<link href="/sistrafront/estilos/estilos.css" rel="stylesheet" type="text/css" />
<logic:notEmpty name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom">
	<link href="<bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom" />" rel="stylesheet" type="text/css" />
</logic:notEmpty>
<script type="text/javascript" src="/sistrafront/js/globales.js"></script>
</head>
<body onLoad="mostrarInfo()">
<div id="contenidor">
	<!--  cabecera -->
<!-- capçal -->
<div id="cap">
	<logic:equal name="<%=org.ibit.rol.form.front.Constants.MOSTRAR_EN_IFRAME%>" value="false">
	<img class="logo" src="<bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="urlLogo"/>" alt="Logo <bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/>" />		    	
	</logic:equal>	
</div>
<!-- titol -->
<p id="titolAplicacio"><bean:message key="main.title"/>	
</p>
	<div id="capsalfranja"></div>
	<!-- continguts -->
	<div id="continguts">
		<div id="tramitacion">
			<!-- paso en cuestión -->
			<div id="pasoInf">
	<!-- capa de información -->
	<div id="capaInfoFondo"></div>
	<div id="capaInfo" class='error'>	
		<p class="atencion"><bean:message key="message.atencion" /></p>
		<p><html:errors /></p>
				<p><a href="/sistrafront/main.do"><bean:message key="message.continuar" /></a></p>
	</div>
	<!-- end capa de informacion -->

			<h2><bean:message key="errors.general.titulo" /></h2>
				<p class="ultimo"><html:errors /></p>
			<div class="separacio"></div>
			</div>
		</div>
	</div>
	<!-- pie pagina -->
	<!--  <div id="peuVora"></div> -->
	<!-- peu -->
	<div id="peu">
	<logic:equal name="<%=org.ibit.rol.form.front.Constants.MOSTRAR_EN_IFRAME%>" value="false">
		&copy; <bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/>				
	</logic:equal>
	</div>
</div><!-- contenidor -->
</body>
</html>

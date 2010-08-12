<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-15" />
<title><bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/></title>
<link href="/sistrafront/estilos/estilos.css" rel="stylesheet" type="text/css" />
<link href="/sistrafront/estilos/stamargalida.css" rel="stylesheet" type="text/css" />
<logic:notEmpty name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom">
	<link href="<bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom" />" rel="stylesheet" type="text/css" />
</logic:notEmpty>
<script type="text/javascript" src="/sistrafront/js/globales.js"></script>
</head>
<body onLoad="mostrarInfo()">
<div id="contenidor">
<%--
	<!--  cabecera -->
<!-- capçal -->
<div id="cap">
	<img class="logo" src="<bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="urlLogo"/>" alt="Logo <bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/>" />		    	
</div>
<!-- titol -->
<p id="titolAplicacio"><bean:message key="main.title"/>	
</p>
--%>



	<%-- HEADER --%>
	<div class="header">
		<%-- Nombre del Organismo --%>
		<h1><bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/></h1>
		<%-- Titulo de la Aplicación --%>
		<h2><bean:message key="main.title"/></h2>
		<div>
			
			<h3><bean:message key="errors.general.titulo" /></h3>
		</div>
	</div>




	<div id="capsalfranja"></div>
	<!-- continguts -->
	<div id="continguts">
		<div class="content">
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

				<%--<h2><bean:message key="errors.general.titulo" /></h2>
					<p class="ultimo"><html:errors /></p>--%>
				<div class="separacio"></div>
				</div>
			</div>
		</div>
	</div>
	<%--
	<!-- pie pagina -->
	<!--  <div id="peuVora"></div> -->
	<!-- peu -->
	<div id="peu">
		&copy; <bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/>				
	</div>
	--%>
	
	<%--FOOTER--%>
	<%
		String idioma = es.caib.zonaper.front.util.LangUtil.getLang(request);
		if (idioma == null) {
			idioma = "ct";
		} else {
			if (idioma.equals("ca")) {
				idioma = "ct";
			}
		}
	%>
	<div class="footer">
		<div class="interior">
			<h4><bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/></h4>
			<address><bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="pieContactoHTML" filter="false"/></address>
			<ul class="menupeu">
				<li><a href="http://www.ajsantamargalida.net/dadesPeu/avis.<%= idioma %>.html" target="_blank"><bean:message key="pie.avisoLegal" /></a></li>
				<li><a href="http://www.ajsantamargalida.net/contacte/index.<%= idioma %>.html" target="_blank"><bean:message key="pie.contacto" /></a></li>
				<li><a href="http://www.ajsantamargalida.net/dadesPeu/proteccio_dades.<%= idioma %>.html" target="_blank"><bean:message key="pie.proteccion" /></a></li>
			</ul>
			<ul class="accessibilitat">
				<li><a href="http://jigsaw.w3.org/css-validator/check/refererr" target="_blank"><img src="estilo_azul/images/css21.gif" width="66" height="31" alt="CSS 2.1" /></a></li>
				<li><a href="http://validator.w3.org/check?uri=referer" target="_blank"><img src="estilo_azul/images/xhtml10.gif" width="64" height="31" alt="XHTML 1.0" /></a></li>
				<li><a href="http://www.w3.org/WAI/WCAG1AA-Conformance" target="_blank"><img src="estilo_azul/images/wai.gif" width="66" height="31" alt="WAI-AA" /></a></li>
				<li class="rss"><a href="http://www.ajsantamargalida.net/dadesPeu/rss.<%= idioma %>.html" target="_blank"><img src="estilo_azul/images/rss.gif" width="31" height="31" alt="WAI-AA" /><span><bean:message key="pie.sindicacion" /></span></a></li>
			</ul>
		</div>
	</div>
	
	
</div><!-- contenidor -->
</body>
</html>

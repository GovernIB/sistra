<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html xhtml="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><bean:message key="actualizarDatosPAD.titulo"/> - <bean:write name="<%=es.caib.zonaper.filter.front.Constants.ORGANISMO_INFO_KEY%>" property="urlPortal" /></title>
<link href="estilos/zonaperfilter-0.0.2.css" rel="stylesheet" type="text/css" media="screen" />
<logic:notEmpty name="<%=es.caib.zonaper.filter.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom">
	<link href="<bean:write name="<%=es.caib.zonaper.filter.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom" />" rel="stylesheet" type="text/css" />
</logic:notEmpty>
<script type="text/javascript">
<!--
// Abre pantalla de ayuda
function ayudaAdmin() {
	ventana = window.open("/sistrafront/contactoAdministrador.do","ayudaAdministrador","width=500,height=200,scrollbars=1,top=100,left=300");
	ventana.focus();
}
-->
</script>
</head>
<body>
<div id="contenidor">
	<tiles:insert name="capsal"/>
	<tiles:insert name="continguts" />
	<tiles:insert name="peu"/>
</div>
</body>
</html:html>

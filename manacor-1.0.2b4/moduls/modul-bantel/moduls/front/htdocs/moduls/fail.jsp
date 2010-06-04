<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><bean:message key="titulo.aplicacion" /></title>
<link href="estilos/estilos.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!--  cabecera -->
<tiles:insert page="/moduls/header.jsp"/>
<!--  tabs -->
<!-- tiles:insert name="tabs"/-->
<!-- continguts -->
<div id="contenedor">
<html:errors />
</div>
<!-- pie pagina -->
<tiles:insert name="/moduls/footer.jsp"/>
</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>ZONAPERDELEGA</title>
<link href="estilos/estilos.css" rel="stylesheet" type="text/css" />
<link href="estilos/zonaperAdmin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.4.1.min.js"></script>
<script type="text/javascript">
<!--
	var txtTitol = "Títol";
	var txtFitxer = "Fitxer";
-->
</script>
</head>
<logic:notPresent name="message">
<body>
</logic:notPresent>
<logic:present name="message">

<!--  RAFA: MENSAJE NO UTILIZA LA CAPA DE INFO
<body onLoad="mostrarInfo()">
 -->
 
</logic:present>
<!--  cabecera -->
<tiles:insert name="header"/>
<!--  tabs -->
<tiles:insert name="tabs"/>
<!-- continguts -->
<div id="contenedor">
<tiles:insert name="main"/>
</div>
<!-- pie pagina -->
<tiles:insert name="footer"/>
</body>
</html>

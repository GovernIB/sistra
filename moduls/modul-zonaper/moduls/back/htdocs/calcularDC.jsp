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
<title>ZONAPERBACK</title>
<link href="estilos/estilos.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="contenedor">
		<div class="atencio">
			<bean:message key="busqueda.calcularDC.aviso"/>
		</div>
		<br/>
		<br/>
		<p align="center">
			<big><strong><bean:message key="busqueda.digitoControl"/><bean:write name="digitoControl"/></strong></big>			
		</p>
	</div>
</body>
</html>
		
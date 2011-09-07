<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ca" lang="ca">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Govern de les Illes Balears</title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/globales.js"></script>
</head>

<body>
<div id="contenedor">
	<!-- capsal -->
	<tiles:insert name="header"/>		
	<!-- continguts -->
	<div id="continguts">
		<tiles:insert name="main"/>
	</div>
	<tiles:insert name="footer"/>
</div>
</body>
</html>

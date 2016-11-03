<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<bean:define id="idioma" name="<%=Globals.LOCALE_KEY%>" property="language" scope="session" type="java.lang.String" />

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>BANTELFRONT</title>
<link href="estilos/estilos.css" rel="stylesheet" type="text/css" />
<link href="estilos/gestion-expedientes.css" rel="stylesheet" type="text/css" />
<link href="estilos/jquery-ui.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/utilidades.js"></script>
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/jquery.selectboxes.pack.js"></script>
<script src="js/jquery.ui.core.min.js"></script>	
<script src="js/jquery.ui.datepicker.min.js"></script>
<script src="js/jquery.ui.datepicker-<%=idioma%>.js"></script>

<script type="text/javascript">
<!--
	// texts
	var txtTitol = "Títol";
	var txtFitxer = "Fitxer";
-->
</script>

<script type="text/javascript">
<!--
// capa de información. Puede ser: info, error, ok.
function mostrarInfo() {
	var capaI = document.getElementById('capaInfo');
	var capaIF = document.getElementById('capaInfoFondo');
	// tamaños de la ventana y la página
	var ventanaX = document.documentElement.clientWidth;
	var ventanaY = document.documentElement.clientHeight;
	var capaY = document.getElementById('contenedor').offsetHeight;
	// la capa de fondo ocupa toda la página
	with (capaIF) {
		if(ventanaY > capaY) style.height = ventanaY + 'px';
		else style.height = capaY + 'px';
		if(document.all) style.filter = "alpha(opacity=30)";
		else style.MozOpacity = 0.3;
		if(document.all) style.width = ventanaX + 'px';
		style.display = 'block';
	}
	capaIF.onclick = cerrarInfo;
	
	// mostramos, miramos su tamaño y centramos la capaInfo con respecto a la ventana
	capaI.style.display = 'block';
	capaInfoX = capaI.offsetWidth;
	capaInfoY = capaI.offsetHeight;
	with (capaI) {
		style.left = (ventanaX-capaInfoX)/2 + 'px';
		style.top = (ventanaY-capaInfoY)/2 + 'px';
	}
}
// esconder las capas info
function cerrarInfo() {
	document.getElementById('capaInfo').style.display = 'none';
	document.getElementById('capaInfoFondo').style.display = 'none';
}

/**
* Funciones para las capas de información de las plantillas
*/

// para conocer la posición X del enlace con respecto a la página
function saberPosX(obj) {
	var curleft = 0;
	if (obj.offsetParent) {
		while (obj.offsetParent) {
			curleft += obj.offsetLeft
			obj = obj.offsetParent;
		}
	} else if (obj.x) {
		curleft += obj.x;
	}
	return curleft;
}
// para conocer la posición Y del enlace con respecto a la página
function saberPosY(obj) {
	var curtop = 0;
	if (obj.offsetParent) {
		while (obj.offsetParent) {
			curtop += obj.offsetTop
			obj = obj.offsetParent;
		}
	} else if (obj.y) {
		curtop += obj.y;
	}
	return curtop;
}
// para mostrar la capa
function mostrarDocInfo(obj,capa) {
	capaInfo = document.getElementById(capa+'info'); // la capa info en cuestión
	posX = saberPosX(obj);
	posY = saberPosY(obj);
	altura = obj.offsetHeight;
	with (capaInfo) {
		style.top = posY + altura + 'px';
		style.left = posX + 'px';
		style.display = 'block';
	}
	document.getElementById(capa).onmouseout = esconderDocInfo; // cuando el ratón se quite adiós a la capa
	document.getElementById(capa).onblur = esconderDocInfo;
}
// para esconder la capa
function esconderDocInfo() {
	capaInfo.style.display = "none";
}

// Mostrar información iconografía
function icosMasInfo() {
	icos = document.getElementById('iconografia');
	spans = icos.getElementsByTagName('span');
	for(i=0; i<spans.length; i++) {
		if(spans[i].style.display == 'inline') spans[i].style.display = 'none';
		else  spans[i].style.display = 'inline';
	}
	if(icos.getElementsByTagName('span')[0].style.display == 'inline') document.getElementById('iconografiaMasInfo').innerHTML = 'Ocultar Info';
	else document.getElementById('iconografiaMasInfo').innerHTML = 'Más Info';
}

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

<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title></title>
<link href="css/styleA.css" rel="stylesheet" type="text/css" />
<link href="aaaaaaaaaaaa" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="contenidor">
<!-- titol -->
	<div id="continguts">
			<!-- paso en cuestión -->
			<div id="pasoInf">
				<!-- capa de información -->
				<div id="capaInfoFondo"></div>
				<div id="capaInfo" class='error'>	
					<p class="atencion"><bean:message key="errors.idOperacion.error" /></p>
					<p class="atencion"><bean:message key="errors.idOperacion.recordatorio" /></p>
					<p><html:link href="lista.do"><bean:message key="errors.idOperacion.continuar" /></html:link></p>
				</div>
				<!-- end capa de informacion -->
			</div>
	</div>
</div>
</body>
</html>







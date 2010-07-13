<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:html xhtml="true" >
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
    <title><bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre" /></title>
	<link href="css/zonaperfront-0.0.2.css" rel="stylesheet" type="text/css" media="screen" />
	<logic:notEmpty name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom">
		<link href="<bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom" />" rel="stylesheet" type="text/css" />
	</logic:notEmpty>    
</head>
<body>

<div id="contenidor">
	
	<!-- continguts -->
	<div id="continguts">
		 <p class="alerta">
			<bean:message key="mensaje.texto.errorGenerico"/>	
			<p><i>Mensaje Depuración:<html:errors /></i></p>			    
		</p>
		
		<p align="center">    
    <html:link forward="main">Inicio</html:link>
		 </p>
	</div>
	


</div>
    
   
	 
</body>
</html:html>
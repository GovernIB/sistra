<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<html:html xhtml="true">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><bean:write name="<%=es.caib.redose.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/></title>
<link href="css/redosefront-0.0.2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/globales.js"></script>
</head>

<body>
<div id="contenidor">
	<!-- cap -->
	<tiles:insert name="header"/>		
	<!-- continguts -->
	<div id="continguts">
		<tiles:insert name="main"/>
	</div>
	<tiles:insert name="footer"/>
</div>
</body>
</html:html>

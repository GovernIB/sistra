<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="main.title" /></title>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
</head>
<body class="ventana">
<table class="nomarc">
    <tr><td class="titulo"><bean:message key="main.title" /></td></tr>
    <tr><td>&nbsp;</td></tr>
    <tr><td align="center"><html:link href="/redoseback/upload.jsp">Upload</html:link></td></tr>
</table>
</body>
</html:html>
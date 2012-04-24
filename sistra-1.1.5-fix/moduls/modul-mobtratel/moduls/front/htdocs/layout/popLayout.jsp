<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<bean:define id="pathIconografia" name="pathIconografia" scope="session"/>
<html:html xhtml="true">
    <head>
        <title><bean:message key="main.title"/></title>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
        <link rel="stylesheet" href="<html:rewrite page='<%=pathIconografia + "/css/estilo.css" %>'/>" type="text/css" />
    </head>
    <body>
        <tiles:insert attribute="contenido" />
    </body>
</html:html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<tiles:importAttribute name="apartados" scope="page" />
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="menu.title" /></title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
</head>
<body class="menu">
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
    <tr><td class="titulo"><bean:message key="menu.title" /></td></tr>
    <tr><td>&nbsp;</td></tr>
    <tr><td class="user"><%=request.getRemoteUser()%></td></tr>
    <tr><td>
        <ul>
        <logic:iterate id="apartado" name="apartados">
            <tiles:insert beanName="apartado" flush="false" />
        </logic:iterate>
        </ul>
    </td></tr>
    <tr><td>&nbsp;</td></tr>
    <tr><td class="salir"><html:link action="/logout" target="_parent"><bean:message key="session.close" /></html:link></td></tr>
</table>
</body>
</html:html>
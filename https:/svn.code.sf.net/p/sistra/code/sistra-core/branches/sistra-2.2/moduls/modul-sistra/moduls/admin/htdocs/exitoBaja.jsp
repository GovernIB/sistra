<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:html xhtml="true" >
<head>
   <title><bean:message key="success.baja" /></title>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
    <script type="text/javascript">
        <logic:present name="reloadMenu">
            //top.Menu.location.reload(true);
            <bean:define id="urlRecargaMenu">
            	<html:rewrite page="/arbol.do" paramId="id" paramName="nodoId" />
            </bean:define>
            parent.treeframe.location.href = '<bean:write name="urlRecargaMenu" />';
        </logic:present>
    </script>
</head>
<body class="ventana">
<table class="marc">
    <tr><td class="titulo">
        <bean:message key="success.baja" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="success.bajaEntidad" /></td></tr>
</table>
</html:html>
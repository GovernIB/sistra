<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>

<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="componente.titulo"/></title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
</head>

<body class="ventana">
<table class="marc">
    <tr><td class="titulo"><bean:message key="componente.eleccion" /></td></tr>
    <tr><td class="subtitulo"><bean:message key="componente.eleccion.subtitulo" /></td></tr>
</table>
<br />

<html:form action="/admin/componente/eleccion" >
<html:hidden property="idPaleta" />
<table class="marc">
    <tr>
        <td class="label"><bean:message key="componente.label" /></td>
        <td><html:radio styleClass="check" property="tipo" value="label" /></td>
    </tr>
    <tr>
        <td class="label"><bean:message key="componente.textbox" /></td>
        <td><html:radio styleClass="check" property="tipo" value="textbox" /></td>
    </tr>
    <tr>
        <td class="label"><bean:message key="componente.combobox" /></td>
        <td><html:radio styleClass="check" property="tipo" value="combobox" /></td>
    </tr>
    <tr>
        <td class="label"><bean:message key="componente.listbox" /></td>
        <td><html:radio styleClass="check" property="tipo" value="listbox" /></td>
    </tr>
    <tr>
        <td class="label"><bean:message key="componente.checkbox" /></td>
        <td><html:radio styleClass="check" property="tipo" value="checkbox" /></td>
    </tr>
    <tr>
        <td class="label"><bean:message key="componente.filebox" /></td>
        <td><html:radio styleClass="check" property="tipo" value="filebox" /></td>
    </tr>
    <tr>
        <td class="label"><bean:message key="componente.radiobutton" /></td>
        <td><html:radio styleClass="check" property="tipo" value="radiobutton" /></td>
    </tr>
</table>
<br />
<table class="nomarc">
    <tr>
        <td align="left">
            <html:submit styleClass="button" >
                <bean:message key="boton.acept" />
            </html:submit>
        </td>
        <td align="right">
            <html:cancel styleClass="button" ><bean:message key="boton.cancel" /></html:cancel>
        </td>
    </tr>
</table>
</html:form>
</body>
</html:html>
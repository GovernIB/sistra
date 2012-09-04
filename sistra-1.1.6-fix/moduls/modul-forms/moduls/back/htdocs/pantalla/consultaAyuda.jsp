<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="ayuda.title"/></title>
   <meta http-equiv="Content-type" content='text/html; charset="ISO-8859-1"' />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
</head>

<body class="ventana">

<table class="nomarcd">
    <tr><td class="titulo"><bean:message key="ayuda.consulta" /></td></tr>
    <tr><td class="subtitulo"><bean:message key="ayuda.subtitulo" /></td></tr>
</table>
<br />

<logic:empty name="mostrar" >
    <table class="marcd">
        <tr><td class="alert"><bean:message key="ayuda.vacia" /></td></tr>
    </table>
</logic:empty>

<logic:notEmpty name="mostrar" >
    <table class="marcd">
        <tr>
            <td class="label"><bean:message key="ayuda.descripcion1" /></td>
            <td class="output"><bean:write name="ayudaForm" property="traduccion.descripcionCorta" /></td>
        </tr>
        <tr>
            <td class="label"><bean:message key="ayuda.descripcion2" /></td>
            <td class="output"><bean:write name="ayudaForm" property="traduccion.descripcionLarga" filter="false" /></td>
        </tr>
        <tr>
            <td class="label"><bean:message key="ayuda.web" /></td>
            <td class="output"><bean:write name="ayudaForm" property="traduccion.urlWeb" /></td>
        </tr>
        <tr>
            <td class="label"><bean:message key="ayuda.sonido" /></td>
            <td class="output"><bean:write name="ayudaForm" property="traduccion.urlSonido" /></td>
        </tr>
        <tr>
            <td class="label"><bean:message key="ayuda.video" /></td>
            <td class="output"><bean:write name="ayudaForm" property="traduccion.urlVideo" /></td>
        </tr>
    </table>
</logic:notEmpty>
<br />
<table class="nomarc">
     <tr><td align="center">
        <input type="button" class="button" value='<bean:message key="boton.acept" />' onclick="window.close()" />
     </td></tr>
    <form>
</table>

</body>
</html:html>
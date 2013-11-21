<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="puntosalida.titulo"/></title>
   <meta http-equiv="Content-type" content='text/html; charset="ISO-8859-1"' />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
</head>

<body class="ventana">
<bean:define id="idFormulario" name="idFormulario"/>
<br />
<table class="marc">
    <tr><td class="titulo"><bean:message key="puntosalida.selec" /></td></tr>
</table>
<br />

<html:form action="/back/salida/alta">
<html:hidden property="idFormulario"/>
<table class="marc">
<tr>
    <td class="label"><bean:message key="puntosalida.titulo"/></td>
    <td class="input">
        <html:select tabindex="1" property="idPuntoSalida">
             <html:options collection="puntosalidaOptions"
                                        property="id"
                                   labelProperty="nombre" />
        </html:select>
   </td>
</tr>
</table>
<br />
<table class="nomarc">
    <tr>
        <td align="left">
             <html:submit styleClass="button" >
                <bean:message key="boton.relac" />
            </html:submit>
        </td>
    </tr>
</table>
</html:form>
</body>
</html:html>
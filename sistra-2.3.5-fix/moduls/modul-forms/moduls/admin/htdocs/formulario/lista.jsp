<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="formulario.titulo"/></title>
   <meta http-equiv="Content-type" content='text/html; charset="ISO-8859-1"' />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
   <script type="text/javascript">
   <!--
        <logic:present name="reloadMenu">
            parent.Menu.location.reload(true);
        </logic:present>
   //-->
   </script>
</head>

<body class="ventana">
<table class="marc">
    <tr><td class="titulo">
        <bean:message key="formulario.selec" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="formulario.selec.subtitulo" /></td></tr>
</table>

<br />

<logic:present name="message">
<table class=nomarc>
<tr>
	<td style="color:red"><bean:write name="message" filter="false"/></td>
</tr>
</table>
	<br />
</logic:present>

<logic:empty name="formularioOptions">
    <table class="marc">
      <tr><td class="alert"><bean:message key="formulario.selec.vacio" /></td></tr>
    </table>
</logic:empty>

<logic:notEmpty name="formularioOptions">
    <table class="marc">
        <logic:iterate id="formulario" name="formularioOptions">
            <tr>
                <td class="outputd" width="87%">
                    <bean:write name="formulario" property="modelo" />
                    <bean:define id="modelo" name="formulario" property="modelo" type="java.lang.String"/>
                    (<bean:write name="formulario" property="traduccion.titulo" />)
                    v<bean:write name="formulario" property="version" />
                </td>
                <td align="left">
                    <!--Botón para editar un formulario-->
                    <bean:define id="urlEditar"><html:rewrite page="/admin/formulario/seleccion.do" paramId="id" paramName="formulario" paramProperty="id"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>
                </td>
            </tr>
        </logic:iterate>
    </table>
</logic:notEmpty>

<br />

</body>
</html:html>
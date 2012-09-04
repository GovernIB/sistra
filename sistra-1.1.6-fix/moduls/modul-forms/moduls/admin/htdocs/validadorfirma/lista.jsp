<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="validador.titulo"/></title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
</head>
<body class="ventana">

<table class="marc">
    <tr><td class="titulo">
        <bean:message key="validador.selec" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="validador.selec.subtitulo" /></td></tr>
</table>
<br />
<bean:size id="numOptions" name="validadorOptions" />

<logic:equal name="numOptions" value="0">
    <table class="marc">
      <tr><td class="alert"><bean:message key="validador.selec.vacio" /></td></tr>
    </table>
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <table class="marc">
        <logic:iterate id="validador" name="validadorOptions">
            <tr>
                <td class="outputd" width="70%">
                    <bean:write name="validador" property="nombre" />
                    <bean:define id="nombre" name="validador" property="nombre" type="java.lang.String"/>
                </td>
                <td>
                    <bean:define id="urlEditar"><html:rewrite page="/admin/validador/seleccion.do" paramId="id" paramName="validador" paramProperty="id"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>

                    <bean:define id="mensajeBaja"><bean:message arg0='<%=nombre%>' key='validador.baja' /></bean:define>
                    <bean:define id="urlBaja"><html:rewrite page="/admin/validador/baja.do" paramId="id" paramName="validador" paramProperty="id"/></bean:define>
                    <%
                    mensajeBaja = mensajeBaja.replace("\'","&#145;");
                    mensajeBaja = mensajeBaja.replace("\"","&#34;");
                    %>
                    <button class="button" type="button" onclick="confirmAndForward('<%=mensajeBaja%>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
                </td>
            </tr>
        </logic:iterate>
    </table>
    <br />
    <table class="nomarc">
      <tr>
        <td align="left">
            <button class="buttond" type="button" onclick="forward('<html:rewrite page="/admin/validador/alta.do"/>')">
                <bean:message key="boton.nuevo" />
            </button>
        </td>
      </tr>
    </table>
</logic:notEqual>
</body>
</html:html>
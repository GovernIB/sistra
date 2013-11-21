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
   <title><bean:message key="ficheroExportacion.titulo"/></title>
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
        <bean:message key="ficheroExportacion.selec" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="ficheroExportacion.selec.subtitulo" /></td></tr>
</table>

<br />

<html:errors/>

<logic:empty name="ficheroExportacionOptions">
    <table class="marc">
      <tr><td class="alert"><bean:message key="ficheroExportacion.selec.vacio" /></td></tr>
    </table>
</logic:empty>

<logic:notEmpty name="ficheroExportacionOptions">
    <table class="marc">
        <logic:iterate id="ficheroExportacion" name="ficheroExportacionOptions">
            <tr>
                <td class="outputd" width="70%">
                    <bean:write name="ficheroExportacion" property="identificadorTramite" />
                    <bean:define id="codigo" name="ficheroExportacion" property="identificadorTramite" type="java.lang.String"/>                    
                </td>
                <td align="right">
                    <bean:define id="urlEditar"><html:rewrite page="/back/ficheroExportacion/seleccion.do" paramId="codigo" paramName="ficheroExportacion" paramProperty="identificadorTramite"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>
                    <bean:define id="descripcion" name="ficheroExportacion" property="identificadorTramite" type="java.lang.String"/>
                    <bean:define id="mensajeBaja"><bean:message arg0='<%="codigo"%>' arg1='<%=StringUtils.escape(descripcion)%>' key='ficheroExportacion.baja' /></bean:define>
                    <bean:define id="urlBaja"><html:rewrite page="/back/ficheroExportacion/baja.do" paramId="codigo" paramName="ficheroExportacion" paramProperty="identificadorTramite"/></bean:define>
                    <button class="button" type="button" onclick="confirmAndForward('<%=mensajeBaja%>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
                </td>
            </tr>
        </logic:iterate>
    </table>
</logic:notEmpty>

<br />

    <table class="nomarc">
      <tr>
        <td align="left">
            <button class="buttond" type="button" onclick="forward('<html:rewrite page="/back/ficheroExportacion/alta.do"/>')">
                <bean:message key="boton.nuevo" />
            </button>
        </td>
      </tr>
    </table>

</body>
</html:html>
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
   <title><bean:message key="gestorFormularios.titulo"/></title>
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
        <bean:message key="gestorFormularios.selec" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="gestorFormularios.selec.subtitulo" /></td></tr>
</table>

<br />

<logic:empty name="gestorFormulariosOptions">
    <table class="marc">
      <tr><td class="alert"><bean:message key="gestorFormularios.selec.vacio" /></td></tr>
    </table>
</logic:empty>

<logic:notEmpty name="gestorFormulariosOptions">
    <table class="marc">
        <logic:iterate id="gestorFormulario" name="gestorFormulariosOptions">
            <tr>
                <td class="outputd" width="70%">
                    <bean:write name="gestorFormulario" property="identificador" />
                    <bean:write name="gestorFormulario" property="descripcion" />
                </td>
                <td align="right">
                    <bean:define id="urlEditar"><html:rewrite page="/back/gestorFormularios/seleccion.do" paramId="identificador" paramName="gestorFormulario" paramProperty="identificador"/></bean:define> 
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>
                    <bean:define id="ident" name="gestorFormulario" property="identificador" type="java.lang.String"/> 
                    <bean:define id="desc" name="gestorFormulario" property="descripcion" type="java.lang.String"/> 
                    <bean:define id="mensajeBaja"><bean:message arg0='<%=ident%>' arg1='<%=StringUtils.escape(desc)%>' key='gestorFormularios.baja' /></bean:define> 
                    <bean:define id="urlBaja"><html:rewrite page="/back/gestorFormularios/baja.do" paramId="identificador" paramName="gestorFormulario" paramProperty="identificador"/></bean:define> 
                    <button class="button" type="button" onclick="confirmAndForward('<%= StringUtils.escape ( mensajeBaja )%>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
                </td>
            </tr>
        </logic:iterate>
    </table>
</logic:notEmpty>

<br />

    <table class="nomarc">
      <tr>
        <td align="left">
            <button class="buttond" type="button" onclick="forward('<html:rewrite page="/back/gestorFormularios/alta.do" paramId="idOrgano"/>')">
                <bean:message key="boton.nuevo" />
            </button>
        </td>
      </tr>
    </table>

</body>
</html:html>
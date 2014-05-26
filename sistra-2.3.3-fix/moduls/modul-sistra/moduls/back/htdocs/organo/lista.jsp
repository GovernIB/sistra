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
   <title><bean:message key="organo.titulo"/></title>
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
        <bean:message key="organo.selec" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="organo.selec.subtitulo" /></td></tr>
</table>

<br />

<logic:empty name="organoOptions">
    <table class="marc">
      <tr><td class="alert"><bean:message key="organo.selec.vacio" /></td></tr>
    </table>
</logic:empty>

<logic:notEmpty name="organoOptions">
    <table class="marc">
        <logic:iterate id="organo" name="organoOptions">
            <tr>
                <td class="outputd" width="70%">
                    <bean:write name="organo" property="codigo" />
                    <bean:define id="modOrgano" name="organo" property="codigo" type="java.lang.Long"/>
                    (<bean:write name="organo" property="descripcion" />)
                </td>
                <td align="right">
                    <bean:define id="urlEditar"><html:rewrite page="/back/organo/seleccion.do" paramId="codigo" paramName="organo" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>

                    <bean:define id="descripcion" name="organo" property="descripcion" type="java.lang.String"/>
                    <bean:define id="mensajeBaja"><bean:message arg0='<%=modOrgano.toString()%>' arg1='<%=StringUtils.escape(descripcion)%>' key='organo.baja' /></bean:define>
                    <bean:define id="urlBaja"><html:rewrite page="/back/organo/baja.do" paramId="codigo" paramName="organo" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="confirmAndForward('<%= StringUtils.escape( mensajeBaja )%>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
                    <bean:define id="urlArbolTramites"><html:rewrite page="/back/framesetTramitacion.do" paramId="codigo" paramName="organo" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlArbolTramites%>')"><bean:message key="boton.tramites" /></button>
                    <bean:define id="urlDominios"><html:rewrite page="/back/dominio/lista.do" paramId="codigoOrganoOrigen" paramName="organo" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlDominios%>')"><bean:message key="boton.dominios" /></button>
                </td>
            </tr>
        </logic:iterate>
    </table>
</logic:notEmpty>

<br />

    <table class="nomarc">
      <tr>
        <td align="left">
            <button class="buttond" type="button" onclick="forward('<html:rewrite page="/back/organo/alta.do"/>')">
                <bean:message key="boton.nuevo" />
            </button>
        </td>
      </tr>
    </table>

</body>
</html:html>
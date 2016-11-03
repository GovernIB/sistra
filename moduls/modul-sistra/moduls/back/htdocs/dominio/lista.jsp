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
   <title><bean:message key="dominio.titulo"/></title>
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
        <bean:message key="dominio.selec" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="dominio.selec.subtitulo" /></td></tr>
</table>

<br />

<logic:empty name="dominioOptions">
    <table class="marc">
      <tr><td class="alert"><bean:message key="dominio.selec.vacio" /></td></tr>
    </table>
</logic:empty>

<logic:notEmpty name="dominioOptions">
    <table class="marc">
        <logic:iterate id="dominio" name="dominioOptions">
            <tr>
                <td class="outputd" width="70%">
                    <bean:write name="dominio" property="codigo" />
                    <bean:define id="modDominio" name="dominio" property="codigo" type="java.lang.Long"/>
                    (<bean:write name="dominio" property="identificador" />)
                </td>
                <td align="right">
                    <bean:define id="urlEditar"><html:rewrite page="/back/dominio/seleccion.do" paramId="codigo" paramName="dominio" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>

                    <bean:define id="descripcion" name="dominio" property="identificador" type="java.lang.String"/>
                    <bean:define id="mensajeBaja"><bean:message arg0='<%=modDominio.toString()%>' arg1='<%=StringUtils.escape(descripcion)%>' key='dominio.baja' /></bean:define>
                    <bean:define id="urlBaja"><html:rewrite page="/back/dominio/baja.do" paramId="codigo" paramName="dominio" paramProperty="codigo"/></bean:define>
                	<bean:define id="urlExportar"><html:rewrite page="/back/dominio/exportar.do" paramId="codigo" paramName="dominio" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="confirmAndForward('<%= StringUtils.escape ( mensajeBaja )%>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
                    <button class="button" type="button" onclick="forward('<%=urlExportar%>')"><bean:message key="boton.exportar" /></button>
                </td>
            </tr>
        </logic:iterate>
    </table>
</logic:notEmpty>

<br />

    <table class="nomarc">
      <tr>
        <td align="left">
            <button class="buttond" type="button" onclick="forward('<html:rewrite page="/back/dominio/alta.do" paramId="idOrgano" paramName="codigoOrganoOrigen"/>')">
                <bean:message key="boton.nuevo" />
            </button>
      	<logic:notEmpty name="codigoOrganoOrigen">
            <button class="buttond" type="button" onclick="forward('<html:rewrite page="/back/dominio/importar.do" paramId="codigoOrganoOrigen" paramName="codigoOrganoOrigen"/>')">
                <bean:message key="boton.importar" />
            </button>
	    </logic:notEmpty>        
        </td>
      </tr>
    </table>

</body>
</html:html>
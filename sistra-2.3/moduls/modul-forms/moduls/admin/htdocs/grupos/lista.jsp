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
   <title><bean:message key="grupos.titulo"/></title>
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
        <bean:message key="grupos.titulo" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="grupo.selec.subtitulo" /></td></tr>
</table>

<br />
<logic:present name="message">
	<bean:write name="message" filter="false"/>
	<br />
</logic:present>


<logic:empty name="grupos">
    <table class="marc">
      <tr><td class="alert"><bean:message key="grupo.selec.vacio" /></td></tr>
    </table>
</logic:empty>

<logic:notEmpty name="grupos">
    <table class="marc">
        <logic:iterate id="grupo" name="grupos">
            <tr>
                <td class="outputd" width="71%">
                    <bean:write name="grupo" property="nombre" />
                </td>
                <td align="left">
                    <!--Botón para editar un grupo-->
                    <bean:define id="urlEditar"><html:rewrite page="/admin/grupo/editModificar.do" paramId="codigo" paramName="grupo" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>
                   
                    <!--Botón para eliminar un grupo-->
                    <bean:define id="urlBaja"><html:rewrite page="/admin/grupo/eliminar.do" paramId="codigo" paramName="grupo" paramProperty="codigo"/></bean:define>
                    <bean:define id="nombre" name="grupo" property="nombre" type="java.lang.String"/>
    	            <bean:define id="mensaje"><bean:message arg0='<%=nombre%>' key='grupo.baja'/></bean:define>
	       	        <%
    	               	mensaje = mensaje.replace("\'","&#145;");
	    	            mensaje = mensaje.replace("\"","&#34;");
                    %>
        	        <button class="button" type="button" onclick="confirmAndForward('<%=mensaje%>', '<%=urlBaja%>')"><bean:message key="boton.baixa"/></button>
                </td>
            </tr>
        </logic:iterate>
    </table>
</logic:notEmpty>

<br />

    <table class="nomarc">
      <tr>
        <td align="left">
            <button class="buttond" type="button" onclick="forward('<html:rewrite page="/admin/grupo/alta.do"/>')">
                <bean:message key="grupo.nuevo" />
            </button>
        </td>
      </tr>
    </table>

</body>
</html:html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<bean:define id="firstPage" value="0" />

<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="erroresGestorDocumental.title"/></title>
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
        <bean:message key="erroresGestorDocumental.selec" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="erroresGestorDocumental.selec.subtitulo" /></td></tr>
</table>

<br />


<logic:empty name="page" property="list">
    <table class="marc">
      <tr><td class="alert"><bean:message key="erroresGestorDocumental.selec.vacio" /></td></tr>
    </table>
</logic:empty>

<logic:notEmpty name="page" property="list">
    <table class="marc">
			<logic:iterate id="erroresGestorDocumental" name="page" property="list">	
            <tr>
            	<td class="outputd" width="30%">
            		<bean:write name="erroresGestorDocumental" property="fecha" format="dd/MM/yyyy hh:mm:ss"/>
            	</td>
                <td class="outputd" width="30%">
                   <bean:write name="erroresGestorDocumental" property="documento.codigo" /> - <bean:write name="erroresGestorDocumental" property="documento.clave" />
                </td>
                <td class="outputd" width="25%">
                    <bean:write name="erroresGestorDocumental" property="descripcionError" />
                </td>
                <td align="right">
                    <bean:define id="urlEditar"><html:rewrite page="/back/erroresGestorDocumental/seleccion.do" paramId="codigo" paramName="erroresGestorDocumental" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>
                </td>
                <td align="right">
                    <bean:define id="urlBorrar"><html:rewrite page="/back/erroresGestorDocumental/borrar.do" paramId="codigo" paramName="erroresGestorDocumental" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlBorrar%>')"><bean:message key="boton.borrar" /></button>
                </td>
            </tr>
        </logic:iterate>
    </table>
    <div id="barraNav">
		<logic:equal name="page" property="previousPage" value="true">
		&lt;&lt; <html:link action="/back/erroresGestorDocumental/lista" paramId="pagina" paramName="firstPage"><bean:message key="erroresGestorDocumental.paginacion.inicio" /></html:link> &lt; <html:link action="/back/erroresGestorDocumental/lista" paramId="pagina" paramName="page" paramProperty="previousPageNumber"><bean:message key="erroresGestorDocumental.paginacion.anterior" /></html:link>
		</logic:equal> 
		- Del <bean:write name="page" property="firstResultNumber" /> al <bean:write name="page" property="lastResultNumber" />, de <bean:write name="page" property="totalResults" /> - 
		<logic:equal name="page" property="nextPage" value="true">			 
		<html:link action="/back/erroresGestorDocumental/lista" paramId="pagina" paramName="page" paramProperty="nextPageNumber"><bean:message key="erroresGestorDocumental.paginacion.siguiente" /></html:link> &gt; <html:link action="/back/erroresGestorDocumental/lista" paramId="pagina" paramName="page" paramProperty="lastPageNumber"><bean:message key="erroresGestorDocumental.paginacion.final" /></html:link> &gt;&gt;
		</logic:equal>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<bean:define id="urlBorrarLog"><html:rewrite page="/back/erroresGestorDocumental/borrarLog.do"/></bean:define>
		<button class="button" type="button" onclick="forward('<%=urlBorrarLog%>')">
			<bean:message key="erroresGestorDocumental.borrarLog" />
		</button>
	</div>
</logic:notEmpty>

<br />


</body>
</html:html>
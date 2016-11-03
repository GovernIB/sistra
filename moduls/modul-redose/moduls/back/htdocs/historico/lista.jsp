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
   <title><bean:message key="historico.titulo"/></title>
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
        <bean:message key="historico.selec" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="historico.selec.subtitulo" /></td></tr>
</table>

<br />


<logic:empty name="page" property="list">
    <table class="marc">
      <tr><td class="alert"><bean:message key="historico.selec.vacio" /></td></tr>
    </table>
</logic:empty>

<logic:notEmpty name="page" property="list">
    <table class="marc">
			<logic:iterate id="historico" name="page" property="list">	
            <tr>
            	<td class="outputd" width="30%">
            		<bean:write name="historico" property="fecha" format="dd/MM/yyyy hh:mm:ss"/>
            	</td>
                <td class="outputd" width="55%">
                    <bean:write name="historico" property="codigo" />
                    <bean:define id="tipoOperacion" name="historico" property="tipoOperacion.nombre" type="java.lang.String"/>
                    (<bean:write name="historico" property="usuarioSeycon" /> : <bean:write name="historico" property="tipoOperacion.nombre" />)
                </td>
                <td align="right">
                    <bean:define id="urlEditar"><html:rewrite page="/back/historico/seleccion.do" paramId="codigo" paramName="historico" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>
                </td>
            </tr>
        </logic:iterate>
    </table>
    <div id="barraNav">
		<logic:equal name="page" property="previousPage" value="true">
		&lt;&lt; <html:link action="/back/historico/lista" paramId="pagina" paramName="firstPage"><bean:message key="historico.paginacion.inicio" /></html:link> &lt; <html:link action="/back/historico/lista" paramId="pagina" paramName="page" paramProperty="previousPageNumber"><bean:message key="historico.paginacion.anterior" /></html:link>
		</logic:equal> 
		- Del <bean:write name="page" property="firstResultNumber" /> al <bean:write name="page" property="lastResultNumber" />, de <bean:write name="page" property="totalResults" /> - 
		<logic:equal name="page" property="nextPage" value="true">			 
		<html:link action="/back/historico/lista" paramId="pagina" paramName="page" paramProperty="nextPageNumber"><bean:message key="historico.paginacion.siguiente" /></html:link> &gt; <html:link action="/back/historico/lista" paramId="pagina" paramName="page" paramProperty="lastPageNumber"><bean:message key="historico.paginacion.final" /></html:link> &gt;&gt;
		</logic:equal>
	</div>
</logic:notEmpty>

<br />


</body>
</html:html>
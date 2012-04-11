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
        <bean:message key="formulario.importar.titulo" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="formulario.importar.subtitulo" /></td></tr>
</table>

<br />

<html:errors/>

	<logic:present name="duplicados">
		<bean:message key="errors.xpathsDuplicados"/>			
		<ul>
		<logic:iterate name="duplicados" id="xpath">
			<li><bean:write name="xpath"/></li>
		</logic:iterate>
		</ul>
	</logic:present>


<html:form action="/importar/xml" styleId="importarForm" enctype="multipart/form-data">
<table class="marc">
    <tr>
        <td class="label"><bean:message key="formulario.importar.fitxer"/></td>
        <td class="input"><html:file property="fitxer" styleClass="file" tabindex="101"/></td>
    </tr>
    <tr>
        <td class="label"><bean:message key="formulario.importar.model"/></td>
        <td class="input"><html:text styleClass="data" tabindex="102" property="model" maxlength="20" /></td>
    </tr>
    <tr>
        <td class="label"><bean:message key="formulario.importar.version"/></td>
        <td class="input"><html:text styleClass="data" tabindex="103" property="version" maxlength="2" /></td>
    </tr>
</table>
<br />
<table class="nomarc">
   <tr>
       <td align="left">
       		<logic:present name="duplicados">
       			<html:hidden property="generarDuplicados" value="S"/>
            	<html:submit styleClass="buttonl"><bean:message key="boton.importarConDuplicados" /></html:submit>
			</logic:present>
			<logic:notPresent name="duplicados">
				<html:hidden property="generarDuplicados" value=""/>
				<html:submit styleClass="button"><bean:message key="boton.importar" /></html:submit>
			</logic:notPresent>
			
        </td>
    </tr>
</table>
</html:form>


</body>
</html:html>
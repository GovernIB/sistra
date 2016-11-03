
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>


<tiles:importAttribute name="listaRelaciones" scope="page" />
<html:html locale="true" xhtml="true">

<head>
   <title><bean:message key="tramite.editar.title"/></title>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
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
<html:form action="/admin/tramite/editModificar.do"  >
<table class="marc">
    <tr><td class="titulo">
            <bean:message key="tramite.editar.title" />
    </td></tr>
</table>
<br />

<logic:present name="alert">
<table class="marc">
    <tr><td class="alert"><bean:message key="alert" /></td></tr>
</table>
<br />
</logic:present>

    <table class="marc">
		<tr>
		    <td class="label"><bean:message key="tramite.codigo"/></td>
		    <td class="output">
		    	<bean:write name="tramite" property="codigo"/>
		    </td>
		</tr>
		<tr>
		    <td class="label"><bean:message key="tramite.identificador"/></td>
		    <td class="output">
		    	<bean:write name="tramite" property="identificador"/>
		    </td>

		</tr>
		<tr>
		    <td class="label"><bean:message key="tramite.desc"/></td>
		    <td class="output">
		    	<bean:write name="tramite" property="traduccion.descripcion"/>
		    </td>
		</tr>

	</table>
    
    <table class="nomarc">
       <tr>
            <td align="left">
	            <button class="buttond" type="button" onclick="forward('<html:rewrite page="/admin/tramites/lista.do"/>')">
               		<bean:message key="boton.volver" />
	            </button>
            </td>
        </tr>
    </table>

	</br>

    <logic:iterate id="element" name="listaRelaciones">
        <tiles:insert beanName="element" flush="false" />
    </logic:iterate>
</html:form>

</body>
</html:html>
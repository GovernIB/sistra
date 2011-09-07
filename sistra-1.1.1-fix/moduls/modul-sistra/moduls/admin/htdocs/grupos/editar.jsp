
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="grupo.editar.title"/></title>
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
<table class="marc">
    <tr><td class="titulo">
            <bean:message key="grupo.editar.title" />
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
		    <td class="label"><bean:message key="grupo.codigo"/></td>
		    <td class="output">
		    	<bean:write name="grupo" property="codigo"/>
		    </td>
		</tr>
		<tr>
		    <td class="label"><bean:message key="grupo.nombre"/></td>
		    <td class="output">
		    	<bean:write name="grupo" property="nombre"/>
		    </td>

		</tr>
		<tr>
		    <td class="label"><bean:message key="grupo.desc"/></td>
		    <td class="output">
		    	<bean:write name="grupo" property="descripcion"/>
		    </td>
		</tr>

	</table>
    
    <table class="nomarc">
       <tr>
            <td align="left">
	            <button class="buttond" type="button" onclick="forward('<html:rewrite page="/admin/grupo/editModificar.do" paramId="codigo" paramName="grupo" paramProperty="codigo"/>&flag=modificar')">
               		<bean:message key="boton.modificacio" />
	            </button>
            </td>
        </tr>
    </table>


<table class="nomarc">
    <tr><td class="titulo"><bean:message key="grupos.usuario.asociado" /></td></tr>
</table>
<br />

<div id="usuarios">

	<logic:empty name="usuarios">
	    <table class="marc">
	      <tr><td class="alert"><bean:message key="grupo.usuarios.vacio" /></td></tr>
	    </table>
	</logic:empty>
	
	<logic:notEmpty name="usuarios">
	    <table class="marc">
		    <logic:iterate id="usuario" name="usuarios">
		    	<tr>
				    <td class="outputd"><bean:write name="usuario" property="id.usuario" /></td>
				</tr>
			</logic:iterate>
	    </table>
	</logic:notEmpty>
</div>



</body>
</html:html>
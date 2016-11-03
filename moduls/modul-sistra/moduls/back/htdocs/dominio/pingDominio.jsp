<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />      
</head>
<title><bean:message key="dominio.ping.titulo"/></title>
<body class="ventana">
<html:form action="/back/dominio/realizarPing">
<table  class="marc">
	<tr>
		<td class="separador" colspan="2"><bean:message key="dominio.ping.dominio"/></td>
	</tr>
	<tr>
	   <td class="labelo"><bean:message key="dominio.ping.dominio"/></td>
	   <td class="input"><html:text styleClass="data" tabindex="1" property="dominio" readonly="true"/></td>
	</tr>	
	<tr>
		<td class="separador" colspan="2"><bean:message key="dominio.ping.parametros"/></td>
	</tr>
	<tr>
	   <td class="labelo"><bean:message key="dominio.ping.parametro"/></td>
	   <td class="input">
	   		<html:text property="parametros" maxlength="5000"/>		   		
	   	</td>
	</tr>	
	<tr>
		<td></td>
		<td class="input"><html:submit value="Ping" styleClass="button"/></td>	
	</tr>
<logic:present name="error">
	<tr>
		<td class="separador" colspan="2"><bean:message key="dominio.ping.valores"/></td>
	</tr>
	<tr>
		<td colspan="2">
			<bean:write name="error"/>
		</td>			
	</tr>
</logic:present>

<logic:present name="valores">
	<tr>
		<td class="separador" colspan="2"><bean:message key="dominio.ping.valores"/></td>
	</tr>
	<tr>
		<td colspan="2">
			<logic:iterate name="valores" property="filas" id="fila">
				<logic:iterate name="fila" id="columna">
					[<bean:write name="columna" property="key"/>=<bean:write name="columna" property="value"/>]&nbsp;
				</logic:iterate>
				<br/>
			</logic:iterate>		
		</td>			
	</tr>
</logic:present>
</table>
</html:form>
</body>
</html>

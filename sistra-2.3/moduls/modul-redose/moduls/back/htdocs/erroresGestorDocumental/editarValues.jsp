<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<bean:define id="errorByte" name="erroresGestorForm" property="values.error" type="byte[]"/>
<bean:define id="error" type="java.lang.String">
	<%= new String( errorByte, es.caib.xml.ConstantesXML.ENCODING )%>
</bean:define>
<tr>
    <td class="label"><bean:message key="erroresGestorDocumental.fecha"/></td>
    <td class="input"><bean:write name="erroresGestorForm" property="values.fecha" format="dd/MM/yyyy hh:mm:ss"/></td>
</tr>

<tr>
    <td class="label"><bean:message key="erroresGestorDocumental.usuarioSeycon"/></td>
    <td class="input"><bean:write name="erroresGestorForm" property="values.usuarioSeycon" /></td>
</tr>

<tr>
    <td class="label"><bean:message key="erroresGestorDocumental.documento.codigo"/></td>
    <td class="input"><bean:write name="erroresGestorForm" property="values.documento.codigo" /> - <bean:write name="erroresGestorForm" property="values.documento.clave" /></td>
</tr>

<tr>
    <td class="label"><bean:message key="erroresGestorDocumental.documento.nombre"/></td>
    <td class="input"><bean:write name="erroresGestorForm" property="values.documento.nombreFichero" /></td>
</tr>

<tr>
    <td class="label"><bean:message key="erroresGestorDocumental.documento.titulo"/></td>
    <td class="input"><bean:write name="erroresGestorForm" property="values.documento.titulo" /></td>
</tr>

<tr>
    <td class="label"><bean:message key="erroresGestorDocumental.descripcionError"/></td>
    <td class="input"><bean:write name="erroresGestorForm" property="values.descripcionError"/></td>
</tr>

<tr>
    <td class="label"><bean:message key="erroresGestorDocumental.trazaerror"/></td>
    <td class="input"></td>
</tr>

<tr>
    <td class="labelm" colspan="2">
    	<%=error%>
    </td>
</tr>



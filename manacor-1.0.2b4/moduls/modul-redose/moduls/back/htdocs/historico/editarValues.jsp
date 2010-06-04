<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>

<tr>
    <td class="label"><bean:message key="historico.fecha"/></td>
    <td class="input"><bean:write name="historicoForm" property="values.fecha" format="dd/MM/yyyy hh:mm:ss"/></td>
</tr>

<tr>
    <td class="label"><bean:message key="historico.usuarioSeycon"/></td>
    <td class="input"><bean:write name="historicoForm" property="values.usuarioSeycon" /></td>
</tr>

<tr>
    <td class="label"><bean:message key="historico.tipoOperacion"/></td>
    <td class="input"><bean:write name="historicoForm" property="values.tipoOperacion.nombre"/></td>
</tr>

<tr>
    <td class="label"><bean:message key="historico.descripcionOperacion"/></td>
    <td class="input"><bean:write name="historicoForm"  property="values.descripcionOperacion"/></td>
</tr>








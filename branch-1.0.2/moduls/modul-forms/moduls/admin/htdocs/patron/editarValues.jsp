<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:xhtml/>
<tr>
    <td class="labelo"><bean:message key="patron.nombre"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.nombre" maxlength="128" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="patron.descripcion"/></td>
    <td class="input">
        <html:textarea tabindex="2" property="values.descripcion" />
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="patron.ejecutar"/></td>
    <td class="input"><html:checkbox styleClass="check" tabindex="3" property="values.ejecutar" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="patron.codigo"/></td>
    <td class="input">
        <html:textarea tabindex="4" property="values.codigo" />
    </td>
</tr>

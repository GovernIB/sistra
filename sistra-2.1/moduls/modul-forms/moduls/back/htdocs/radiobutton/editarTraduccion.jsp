<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:xhtml/>
<% int ti = 50; %>
<tr>
    <td class="labelo"><bean:message key="componente.nombre"/></td>
    <td class="input"><html:text styleClass="text" tabindex="<%=Integer.toString(ti++)%>" property="traduccion.nombre" maxlength="256" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.ayuda"/></td>
    <td class="input"><html:textarea tabindex="<%=Integer.toString(ti++)%>" property="traduccion.ayuda" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="radiobutton.mensaje"/></td>
    <td class="input"><html:textarea tabindex="<%=Integer.toString(ti++)%>" property="traduccion.mensajeValidacion" /></td>
</tr>

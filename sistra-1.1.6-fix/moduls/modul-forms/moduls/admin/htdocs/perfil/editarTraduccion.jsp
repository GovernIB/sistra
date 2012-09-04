<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:xhtml/>
<tr>
    <td class="labelo"><bean:message key="perfil.nombre"/></td>
    <td class="input"><html:text styleClass="text" tabindex="2" property="traduccion.nombre" maxlength="256" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="perfil.descripcion"/></td>
    <td class="input"><html:textarea tabindex="3" property="traduccion.descripcion" /></td>
</tr>

<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:xhtml/>
<tr>
    <td class="labelo"><bean:message key="perfil.iconografia"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.pathIconografia" maxlength="1024" /></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="perfil.codigo"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.codigoEstandard" maxlength="10" /></td>
</tr>

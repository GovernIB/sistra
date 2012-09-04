<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<html:hidden property="idModelo" />
<tr>
    <td class="labelo"><bean:message key="version.version"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.version" maxlength="3" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="version.descripcion"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.descripcion" maxlength="100" /></td>
</tr>


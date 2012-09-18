<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:xhtml/>
<tr>
    <td class="labelo"><bean:message key="paleta.nombre"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.nombre" maxlength="128" /></td>
</tr>

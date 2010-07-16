<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<tr>
    <td class="label"><bean:message key="formulario.mantener"/></td>
    <td class="input"><html:radio styleClass="check" property="newVersion" value="1" tabindex="2" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.cambiar"/></td>
    <td class="input"><html:radio styleClass="check" property="newVersion" value="2" tabindex="3" /></td>
</tr>

<body onload="document.forms[0].newVersion[0].checked=true;" />
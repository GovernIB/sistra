<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<% int ti = 1; %>
<html:hidden property="idComponente" />
<html:hidden property="imagen" />
<tr>
    <td class="label"><bean:message key="valorposible.defecto"/></td>
    <td class="input"><html:checkbox styleClass="check" tabindex="<%=Integer.toString(ti++)%>" property="values.defecto" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="valorposible.valor"/></td>
    <td class="input"><html:text styleClass="text" tabindex="<%=Integer.toString(ti++)%>" property="values.valor" maxlength="128" /></td>
</tr>

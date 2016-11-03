<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<tr>
    <td class="labelo"><bean:message key="modelo.modelo"/></td>
    <td class="input"><html:text styleClass="data" tabindex="1" property="values.modelo" maxlength="20" /></td>
</tr>

<tr>
    <td class="labelo"><bean:message key="modelo.nombre"/></td>
    <td class="input"><html:text styleClass="data" tabindex="1" property="values.nombre" maxlength="100" /></td>
</tr>

<tr>
    <td class="label"><bean:message key="modelo.descripcion"/></td>
    <td class="input"><html:textarea tabindex="10" property="values.descripcion" /></td>
</tr>

<tr>
    <td class="labelo"><bean:message key="modelo.estructurado"/></td>
    <td class="input">Si<html:radio property="values.estructurado" value="S"/> No<html:radio property="values.estructurado" value="N"/></td>
</tr>

<tr>
    <td class="labelo"><bean:message key="modelo.custodia"/></td>
    <td class="input">Si<html:radio property="values.custodiar" value="S"/> No<html:radio property="values.custodiar" value="N"/></td>
</tr>

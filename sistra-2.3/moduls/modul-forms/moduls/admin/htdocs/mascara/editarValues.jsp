<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<tr>
    <td class="labelo"><bean:message key="mascara.nombre"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.nombre" maxlength="256" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="mascara.descripcion"/></td>
    <td class="input">
        <html:textarea tabindex="2" property="values.descripcion" />
    </td>
</tr>
</table>
<br />

<bean:size id="numOptions" name="mascaraForm" property="variables" />
<table class="nomarc">
    <tr><td class="titulo" colspan="2"><bean:message key="mascara.variables" /></td></tr>
</table>

<table class="marc">
<logic:equal name="numOptions" value="0">
      <tr><td class="alert" colspan="2"><bean:message key="mascara.variables.vacio" /></td></tr>
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <logic:iterate  id="variable" name="mascaraForm" property="variables" indexId="index">
        <tr>
            <td class="label">
                <bean:message key="mascara.variables.nombre" /><%=index%>
            </td>
            <td class="input">
                <input type="text" name="variables" maxlength="256" tabindex="<%=new Integer(3 + index.intValue()).toString()%>" value="<%=variable%>" class="text" />
            </td>
        </tr>
    </logic:iterate>
</logic:notEqual>

<tr>
    <td align="center" colspan="2">
        <input type="submit" name="addValor" class="buttond" value='<bean:message key="boton.addValor" />' />
        <input type="submit" name="removeValor" class="buttond" value='<bean:message key="boton.removeValor" />' />
    </td>
</tr>

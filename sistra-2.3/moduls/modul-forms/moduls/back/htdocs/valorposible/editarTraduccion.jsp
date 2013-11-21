<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<% int ti = 50; %>
<tr>
    <td class="labelo"><bean:message key="valorposible.etiqueta"/></td>
    <td class="input"><html:text styleClass="text" tabindex="<%=Integer.toString(ti++)%>" property="traduccion.etiqueta" maxlength="4000" /></td>
</tr>
<logic:notEqual name="valorPosibleForm" property="imagen" value="false">
<logic:present name="valorPosibleForm" property="traduccion.archivo">
<tr>
    <td class="labelo"><bean:message key="valorposible.imagen" /></td>
    <td class="input">
        <bean:define id="lang" name="valorPosibleForm" property="lang" type="java.lang.String"/>
        <html:img page='<%="/back/valorposible/imagen.do?lang=" + lang%>' paramId="id" paramName="valorPosibleForm" paramProperty="values.id" />
        <html:hidden property="traduccion.etiqueta" />
    </td>
</tr>
</logic:present>
<logic:notPresent name="valorPosibleForm" property="traduccion.archivo">
<tr>
    <td class="label"><bean:message key="valorposible.imagen" /></td>
    <td class="input">
        <html:file property="archivo" styleClass="file" tabindex="<%=Integer.toString(ti++)%>"/>
        <html:hidden property="traduccion.etiqueta" value="-"/>
    </td>
</tr>
</logic:notPresent>
</logic:notEqual>

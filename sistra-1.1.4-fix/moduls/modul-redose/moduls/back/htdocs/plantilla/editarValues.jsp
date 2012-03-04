<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>

<html:hidden property="idVersion" />
<%request.setAttribute("estemAPlantilles","Y"); %>
<tr>
    <td class="labelo"><bean:message key="plantilla.tipo"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.tipo" maxlength="3" /></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="plantilla.formateador"/></td>
    <td class="input"><html:select styleClass="text" property="values.formateador.identificador"><html:options collection="formateadores" property="key" labelProperty="value"/></html:select></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="plantilla.defecto"/></td>
    <td class="input"><html:checkbox property="values.defecto" value="S" tabindex="1" /></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="plantilla.barcode"/></td>
    <td class="input"><html:checkbox property="values.barcode" value="S" tabindex="1" /></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="plantilla.sello"/></td>
    <td class="input"><html:checkbox property="values.sello" value="S" tabindex="1" /></td>
</tr>
<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<tr>
    <td class="labelo"><bean:message key="formulario.titulo"/></td>
    <td class="input"><html:text styleClass="text" tabindex="9" property="traduccion.titulo" maxlength="256" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.descripcion"/></td>
    <td class="input"><html:textarea tabindex="10" property="traduccion.descripcion" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.entidad1"/></td>
    <td class="input"><html:text styleClass="text" tabindex="11" property="traduccion.nombreEntidad1" maxlength="256" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.entidad2"/></td>
    <td class="input"><html:text styleClass="text" tabindex="12" property="traduccion.nombreEntidad2" maxlength="256" /></td>
</tr>

<logic:present name="formularioForm" property="traduccion.plantilla">
    <tr>
        <td class="label"><bean:message key="formulario.plantilla" /></td>
        <td class="input">
            <bean:define id="lang" name="formularioForm" property="lang" type="java.lang.String"/>
            <html:link page='<%="/back/formulario/plantilla.do?lang=" + lang%>' paramId="id" paramName="formularioForm" paramProperty="values.id"><bean:write name="formularioForm" property="traduccion.plantilla.nombre"/></html:link>
            <logic:present name="bloqueado">
                <html:submit property="borrarPlantilla" styleClass="button"><bean:message key="boton.borrar"/></html:submit>
            </logic:present>
        </td>
    </tr>
    <tr>
        <td class="label"><bean:message key="formulario.plantilla.nueva"/></td>
</logic:present>
<logic:notPresent name="formularioForm" property="traduccion.plantilla">
    <input type="hidden" name="temp" value="" />
    <tr>
        <td class="label"><bean:message key="formulario.plantilla"/></td>
</logic:notPresent>
    <td class="input"><html:file property="plantilla" styleClass="file" tabindex="13"/></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.plantilla"/></td>
</tr>


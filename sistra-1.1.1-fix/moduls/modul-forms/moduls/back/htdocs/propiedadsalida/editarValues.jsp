<%@ page language="java" %>
<%@ page import="org.ibit.rol.form.back.util.Util"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>

<html:hidden property="idSalida" />
<tr>
    <td class="labelo"><bean:message key="propiedadsalida.nombre"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.nombre" maxlength="128" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="propiedadsalida.valor"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="2" property="values.valor" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="propiedadsalida.expresion"/></td>
    <td class="input"><html:checkbox styleClass="check" tabindex="3" property="values.expresion" /></td>
</tr>


<logic:present name="propiedadSalidaForm" property="values.plantilla">
    <tr>
        <td class="label"><bean:message key="propiedadsalida.plantilla" /></td>
        <td class="input">
            <html:link page="/back/propiedadsalida/plantilla.do" paramId="id" paramName="propiedadSalidaForm" paramProperty="values.id"><bean:write name="propiedadSalidaForm" property="values.plantilla.nombre"/></html:link>
            <%-- <input type="text" name="temp" class="curt" readonly="readonly" value='<bean:write name="formularioForm" property="traduccion.plantilla.nombre"/>' /> --%>
            <logic:present name="bloqueado">
                <html:submit property="borrarPlantilla" styleClass="button"><bean:message key="boton.borrar"/></html:submit>
            </logic:present>
        </td>
    </tr>
    <tr>
        <td class="label"><bean:message key="propiedadsalida.plantilla.nueva"/></td>
</logic:present>
<logic:notPresent name="propiedadSalidaForm" property="values.plantilla">
    <input type="hidden" name="temp" value="" />
    <tr>
        <td class="label"><bean:message key="propiedadsalida.plantilla"/></td>
</logic:notPresent>
    <td class="input"><html:file property="plantilla" styleClass="file" tabindex="13"/></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.plantilla"/></td>
</tr>
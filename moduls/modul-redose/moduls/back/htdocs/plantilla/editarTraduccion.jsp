<%@ page language="java" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<logic:present name="plantillaForm" property="traduccion.archivo">
    <tr>
        <td class="label"><bean:message key="plantilla.plantilla" /></td>
        <input type="hidden" name="temp" value="tmp" />
        <td class="input">
            <bean:define id="lang" name="plantillaForm" property="lang" type="java.lang.String"/>
            <html:link page='<%="/back/plantilla/mostrarFichero.do?lang=" + lang%>' paramId="codigo" paramName="plantillaForm" paramProperty="values.codigo"><bean:write name="plantillaForm" property="traduccion.nombreFichero"/></html:link>
            <html:submit property="borrarPlantilla" styleClass="button"><bean:message key="boton.borrar"/></html:submit>
        </td>
    </tr>
    <tr>
        <td class="label"><bean:message key="plantilla.plantilla.nueva"/></td>
</logic:present>
<logic:notPresent name="plantillaForm" property="traduccion.archivo">
    <!--   <input type="hidden" name="temp" value="" /> -->
    <tr>
        <td class="label"><bean:message key="plantilla.plantilla"/></td>
</logic:notPresent>
    <td class="input"><html:file property="plantilla" styleClass="file" tabindex="13"/></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.plantilla"/></td>
</tr>
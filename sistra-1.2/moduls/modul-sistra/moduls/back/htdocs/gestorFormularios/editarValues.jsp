<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>

<script type="text/javascript">
     <!--
     function viewAyudaExpresion() {
        var url = '<html:rewrite page="/gestorFormularios/ayudaPantalla.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     // -->
</script>
<%
String bean = (String)request.getAttribute( "form.beanName");
%>

<tr>
	<td class="separador" colspan="2"><bean:message key="gestorFormularios.definicion"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="gestorFormularios.identificador"/></td>
    <td class="input">
    	<logic:notPresent name="<%=bean%>" property="values.identificador">
            <html:text styleClass="data" tabindex="1" property="values.identificador" maxlength="15" />
        </logic:notPresent>
        <logic:present name="<%=bean%>" property="values.identificador">
            <bean:write name="<%=bean%>" property="values.identificador" />
        </logic:present>   
    </td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="gestorFormularios.parametrosFormulario"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="gestorFormularios.descripcion"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="1" property="values.descripcion" maxlength="50" /></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="gestorFormularios.urlGestor"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="1" property="values.urlGestor" maxlength="500" /></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="gestorFormularios.urlTramitacion"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="1" property="values.urlTramitacionFormulario" maxlength="500" /></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="gestorFormularios.urlRedireccion"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="1" property="values.urlRedireccionFormulario" maxlength="500" /></td>
</tr>



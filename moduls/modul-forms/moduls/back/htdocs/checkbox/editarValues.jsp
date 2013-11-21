<%@ page language="java" %>
<%@ page import="org.ibit.rol.form.back.util.Util"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<% int ti = 1; %>
<html:hidden property="idPantalla" />
<input type="hidden" name="idOperacion" value="<%=Util.getIdOperacion(request)%>"/>	

<tr>
    <td class="labelo"><bean:message key="componente.nombreLogico"/></td>
    <td class="input"><html:text styleClass="text" tabindex="<%=Integer.toString(ti++)%>" property="values.nombreLogico" maxlength="128" /></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.nombrelogico"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.posicion"/></td>
    <td class="input">
    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.posicion">
        <html:option value="0"><bean:message key="componente.posicion.0" /></html:option>
        <html:option value="1"><bean:message key="componente.posicion.1" /></html:option>
    </html:select>
</tr>
<tr>
    <td class="label"><bean:message key="componente.pdf"/></td>
    <td class="input"><html:text styleClass="data" tabindex="<%=Integer.toString(ti++)%>" property="values.etiquetaPDF" maxlength="128" /></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.etiquetaPDF"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="checkbox.defecto"/></td>
    <td class="input"><html:checkbox styleClass="check" tabindex="<%=Integer.toString(ti++)%>" property="checked" /></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.checkbox.defecto"/></td>
</tr>

<!--  INDRA: PROPIEDADES PARA CAMPOS DE PANTALLAS DE DETALLE DE LISTA DE ELEMENTOS -->
<logic:present name="detalle">
<logic:equal name="detalle" value="true">
<tr>
    <td class="label"><bean:message key="componente.mostrarEnTabla"/></td>
    <td class="input"><html:checkbox styleClass="check" tabindex="<%=Integer.toString(ti++)%>" property="values.mostrarEnTabla" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.anchoColumna"/></td>
    <td class="input"><html:text styleClass="text" tabindex="<%=Integer.toString(ti++)%>" property="values.anchoColumna" maxlength="3" /></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.mostrarListaElementos"/></td>
</tr>
</logic:equal>
</logic:present>
<!--  INDRA: PROPIEDADES PARA CAMPOS DE PANTALLAS DE DETALLE DE LISTA DE ELEMENTOS -->
<tiles:insert page="/moduls/editarExpresiones.jsp">
    <tiles:put name="tabindex" value="<%=new Integer(ti)%>" />
</tiles:insert>
<% ti += 5; %>

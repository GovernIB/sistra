<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<% int ti = 1; %>
<html:hidden property="idPaleta" />
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
    <td class="labela" colspan="2"><bean:message key="ayuda.posicion"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.pdf"/></td>
    <td class="input"><html:text styleClass="text" tabindex="<%=Integer.toString(ti++)%>" property="values.etiquetaPDF" maxlength="128" /></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.etiquetaPDF"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.tipovalor"/></td>
    <td class="input">
        <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.tipoValor" >
            <html:option value="java.lang.String"><bean:message key="componente.java.lang.String" /></html:option>
            <html:option value="java.lang.Integer"><bean:message key="componente.java.lang.Integer" /></html:option>
            <html:option value="java.lang.Float"><bean:message key="componente.java.lang.Float" /></html:option>
            <html:option value="org.ibit.rol.form.model.Archivo"><bean:message key="componente.Archivo" /></html:option>
        </html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.obligatorio"/></td>
    <td class="input"><html:checkbox styleClass="check" tabindex="<%=Integer.toString(ti++)%>" property="values.obligatorio" /></td>
</tr>
<tiles:insert page="/moduls/editarExpresiones.jsp">
    <tiles:put name="tabindex" value="<%=new Integer(ti)%>" />
</tiles:insert>
<% ti += 4; %>

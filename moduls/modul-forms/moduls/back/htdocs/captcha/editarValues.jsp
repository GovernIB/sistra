<%@ page language="java" %>
<%@ page import="org.ibit.rol.form.back.util.Util"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<% int ti = 1; %>
<html:hidden property="idPantalla" />
<html:hidden property="pantallaDetalle" />
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
        <html:option value="1"><bean:message key="componente.posicion.1" /></html:option>
        <html:option value="0"><bean:message key="componente.posicion.0" /></html:option>
        <html:option value="2"><bean:message key="componente.posicion.2" /></html:option>
        <html:option value="3"><bean:message key="componente.posicion.3" /></html:option>
    </html:select>
</tr>
<tr>
    <td class="label"><bean:message key="componente.colSpan"/></td>
    <td class="input">
    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.colSpan">
        <html:option value="2"><bean:message key="componente.colSpan.2" /></html:option>       
    </html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.sinEtiqueta"/></td>
    <td class="input">
    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.sinEtiqueta">
        <html:option value="false"><bean:message key="componente.sinEtiqueta.conEtiqueta" /></html:option>
        <html:option value="true"><bean:message key="componente.sinEtiqueta.sinEtiqueta" /></html:option>          
    </html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.alineacion"/></td>
    <td class="input">
    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.alineacion">
        <html:option value="I"><bean:message key="componente.alineacion.izquierda" /></html:option>
        <html:option value="C"><bean:message key="componente.alineacion.centro" /></html:option>       
        <html:option value="D"><bean:message key="componente.alineacion.derecha" /></html:option>         
    </html:select>
    </td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.posicion"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.encuadrar.marcar"/></td>
    <td class="input">
    	 <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.encuadrar">
	        <html:option value="true"><bean:message key="componente.encuadrar.si" /></html:option>
	        <html:option value="false"><bean:message key="componente.encuadrar.no" /></html:option>                
	    </html:select>
    </td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.encuadrar"/></td>
</tr>
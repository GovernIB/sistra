<%@ page language="java" %>
<%@ page import="org.ibit.rol.form.back.util.Util"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

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
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.colSpan"/></td>
    <td class="input">
    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.colSpan">
        <html:option value="1"><bean:message key="componente.colSpan.1" /></html:option>
        <html:option value="2"><bean:message key="componente.colSpan.2" /></html:option>
        <html:option value="3"><bean:message key="componente.colSpan.3" /></html:option>
        <html:option value="4"><bean:message key="componente.colSpan.4" /></html:option>
        <html:option value="5"><bean:message key="componente.colSpan.5" /></html:option>
        <html:option value="6"><bean:message key="componente.colSpan.6" /></html:option>
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
    <td class="label"><bean:message key="componente.listaelementos.anchuramaxima"/></td>
    <td class="input">
    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.anchuraMaxima">
        <html:option value="false"><bean:message key="componente.listaelementos.anchuramaxima.0" /></html:option>
        <html:option value="true"><bean:message key="componente.listaelementos.anchuramaxima.1" /></html:option>
    </html:select>
    </td>
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
    <td class="label">&nbsp;</td>
    <td class="input">
    
   <logic:empty name="listaelementosForm" property="values.id">
		<button class="buttond" type="button" disabled="disabled">
	        <bean:message key="listaelementos.boton.detalle" />
	    </button>
	</logic:empty>
	
	<logic:notEmpty name="listaelementosForm" property="values.id">
		<bean:define id="urlDetalle">
			 <html:rewrite page="/back/pantalla/seleccionDetalle.do" paramId="componente" paramName="listaelementosForm" paramProperty="values.id"/>
		</bean:define>
		<button class="buttondetalle" type="button" onclick="forward('<%=urlDetalle%>')">
	        <bean:message key="listaelementos.boton.detalle" />
	    </button>
	</logic:notEmpty>
	
    </td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="listaelementos.boton.texto"/></td>
</tr>
<tiles:insert page="/moduls/editarExpresiones.jsp">
    <tiles:put name="tabindex" value="<%=new Integer(ti)%>" />
    <tiles:put name="expresionesPermitidas" value="145" />
</tiles:insert>
<% ti += 5; %>

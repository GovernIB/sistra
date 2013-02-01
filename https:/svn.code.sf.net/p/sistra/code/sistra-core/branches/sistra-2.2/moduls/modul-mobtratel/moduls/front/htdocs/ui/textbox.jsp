<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<html:xhtml/>
<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.TextBox"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    <% boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo())); %>
    <% boolean disabled = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionDependencia())); %>
    <nested:write property="traduccion.nombre"/>:
    <% if (campo.getFilas() > 1) { %>
    <br /><html:textarea
        property="<%=nombre%>"
        readonly="<%=(autocalculo)%>"
        disabled="<%=disabled%>"
        onchange='<%=(!autocalculo) ? "onFieldChange(this.form, this.name)" : ""%>'
        rows="<%=String.valueOf(campo.getFilas())%>"
        cols="<%=String.valueOf(campo.getColumnas())%>"
        styleClass='<%=(autocalculo ? "frmro" : "frm")%>'
        onfocus='<%="setAyuda(" + campo.getOrden() + ")"%>'
        tabindex="<%=Integer.toString((autocalculo) ? 0 : campo.getOrden()+1)%>"
    />
    <% } else { %>
    <html:text
        property="<%=nombre%>"
        readonly="<%=(autocalculo)%>"
        disabled="<%=(disabled)%>"
        onchange='<%=(!autocalculo) ? "onFieldChange(this.form, this.name)" : ""%>'
        size="<%=String.valueOf(campo.getColumnas())%>"
        styleClass='<%=(autocalculo ? "frmro" : "frm")%>'
        onfocus='<%="setAyuda(" + campo.getOrden() + ")"%>'
        tabindex="<%=Integer.toString((autocalculo) ? 0 : campo.getOrden()+1)%>"
    />
    <% } %>
</nested:root>
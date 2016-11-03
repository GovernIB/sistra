<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.ibit.rol.form.model.Validacion"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:xhtml/>

<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.TextBox"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    <% boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo()));%>
    <% boolean disabled = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionDependencia())); %>
    <% boolean bloqueado = campo.isBloqueado();%>    
    <!-- INDRA: nuevo span para la etiqueta del campo -->
    <% boolean required = (campo.findValidacion("required") != null?true:false);%>
	<% String classEtiqueta = (required?"formEtiquetaOb":"formEtiqueta"); %>
    <span class="<%=classEtiqueta%>"><nested:write property="traduccion.nombre" filter="false"/><nested:notEqual property="traduccion.nombre" value="&nbsp;">:</nested:notEqual></span>
		<!-- fin INDRA -->
    <% if (campo.getFilas() > 1) { %>
    <br />
		<html:textarea
        property="<%=nombre%>"
        readonly="<%=(autocalculo) || (bloqueado)%>"
        disabled="<%=disabled%>"
        onchange='<%=(!autocalculo && !bloqueado) ? "onFieldChange(this.form, this.name)" : ""%>'
        rows="<%=String.valueOf(campo.getFilas())%>"
        cols="<%=String.valueOf(campo.getColumnas())%>"
        styleClass='<%=(autocalculo || bloqueado ? "frmro" : "frm")%>'
        onfocus='<%="this.hasFocus=true;setAyuda(" + campo.getOrden() + ")"%>'        
        onblur="this.hasFocus=false;"
        tabindex="<%=Integer.toString((autocalculo) ? 0 : campo.getOrden()+1)%>"
    />
    <% } else { %>
    <% Validacion vmaxlength = campo.findValidacion("maxlength"); %>
    <html:text
        property="<%=nombre%>"
        readonly="<%=(autocalculo) || (bloqueado)%>"
        disabled="<%=(disabled)%>"
        onchange='<%=(!autocalculo && !bloqueado) ? "onFieldChange(this.form, this.name)" : ""%>'
        size="<%=String.valueOf(campo.getColumnas())%>"
        maxlength='<%=(vmaxlength != null ? vmaxlength.getValores()[0] : "")%>'
        styleClass='<%=(autocalculo || bloqueado? "frmro" : "frm")%>'
        onfocus='<%="this.hasFocus=true;setAyuda(" + campo.getOrden() + ")"%>'        
        onblur="this.hasFocus=false;"
        tabindex="<%=Integer.toString((autocalculo) ? 0 : campo.getOrden()+1)%>"        
    />
    <% } %>
    
		<!-- INDRA: nuevo span para la separación entre etiqueta/campo y la siguiente -->
		<span class="formSep"></span>
		<!-- fin INDRA -->
</nested:root>
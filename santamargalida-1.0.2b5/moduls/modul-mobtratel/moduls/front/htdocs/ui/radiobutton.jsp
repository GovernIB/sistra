<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<html:xhtml/>
<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.RadioButton"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    <% boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo())); %>
    <% boolean disabled = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionDependencia())); %>
    <nested:write property="traduccion.nombre"/>: <br />
    <logic:iterate id="vp" name="campo" property="allValoresPosibles">
        <html:radio
            property="<%=nombre%>"
            idName="vp"
            value="valor"
            disabled="<%=(disabled)%>"
            onchange='<%=(!autocalculo) ? "onFieldChange(this.form, this.name)" : ""%>'
            onfocus='<%="setAyuda(" + campo.getOrden() + ")" + (autocalculo?"; this.blur()":"")%>'
            tabindex="<%=Integer.toString((autocalculo) ? 0 : campo.getOrden()+1)%>"
        ><bean:write name="vp" property="traduccion.etiqueta"/></html:radio><br />
    </logic:iterate>
</nested:root>
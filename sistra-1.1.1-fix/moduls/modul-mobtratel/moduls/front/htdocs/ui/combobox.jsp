<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<html:xhtml/>
<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.ComboBox"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    <% boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo())); %>
    <% boolean disabled = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionDependencia())); %>
    <nested:write property="traduccion.nombre"/>:
    <html:select
        property="<%=nombre%>"
        disabled="<%=(disabled)%>"
        onchange='<%=(!autocalculo) ? "onFieldChange(this.form, this.name)" : ""%>'
        styleClass='<%=(autocalculo ? "frmro" : "frm")%>'

        onfocus='<%="setAyuda(" + campo.getOrden() + ")" + (autocalculo?"; this.blur()":"")%>'
        tabindex="<%=Integer.toString((autocalculo) ? 0 : campo.getOrden()+1)%>"
    ><%-- llevat: style="width: 200px" --%>
    <% if (!campo.isObligatorio()) { %>
        <option value="">...</option>
    <% } %>
        <html:optionsCollection
        name="campo"
    property="allValoresPosibles"
       label="traduccion.etiqueta"
       value="valor"/>
    </html:select>
</nested:root>
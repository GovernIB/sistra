<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:xhtml/>

<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.ListBox"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    <% boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo())); %>
    <% boolean disabled = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionDependencia())); %>
    <% boolean bloqueado = campo.isBloqueado();%>
		<!-- INDRA: nuevo span para la etiqueta del campo -->
    <span class="formEtiqueta"><nested:write property="traduccion.nombre" filter="false"/><nested:notEqual property="traduccion.nombre" value="&nbsp;">:</nested:notEqual></span>
		<!-- fin INDRA -->
    <html:select
        size="<%=String.valueOf(campo.getAltura())%>"
        multiple="true"
        property="<%=nombre%>"
        disabled="<%=(disabled || bloqueado)%>"
        onchange='<%=(!autocalculo) ? "onFieldChange(this.form, this.name)" : ""%>'
        styleClass='<%=(autocalculo || bloqueado? "frmro" : "frm")%>'
        onfocus='<%="setAyuda(" + campo.getOrden() + ")" + (autocalculo? "; this.blur()":"")%>'
        tabindex="<%=Integer.toString((autocalculo) ? 0 : campo.getOrden()+1)%>"
    >
			<html:optionsCollection 
					    name="campo"
			    		property="allValoresPosibles"
						label="traduccion.etiqueta"
			       		value="valor"/>			       		
		</html:select>
		<!-- INDRA: nuevo span para la separación entre etiqueta/campo y la siguiente -->
		<span class="formSep"></span>
		<!-- fin INDRA -->
</nested:root>
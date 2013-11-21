<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.ibit.rol.form.model.Validacion"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>

<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.TextBox"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>

	<% Validacion vmaxlength = campo.findValidacion("maxlength"); %>
	<% boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo()));%>
	<% boolean disabled = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionDependencia())); %>    
    <% boolean bloqueado = campo.isBloqueado();%>    
	
	<div class="<%=org.ibit.rol.form.front.util.UtilFrontV2.generateStyleClass(campo)%>" data-type="text">
		<div class="imc-el-etiqueta">
			<nested:equal property="sinEtiqueta" value="false">
				<label for="<%=nombre%>"><nested:write property="traduccion.nombre"/></label>
			</nested:equal>			
		</div>
		<div class="imc-el-control">
			 <nested:lessEqual property="filas" value="1">
			 	<html:text property="<%=nombre%>" 
			 		styleId="<%=nombre%>"
			 		readonly="<%=(autocalculo) || (bloqueado)%>"
        			disabled="<%=disabled%>"
			 		onchange='<%=(!autocalculo && !bloqueado) ? "onFieldChange(this.form, this.name)" : ""%>'
			 		maxlength='<%=(vmaxlength != null ? vmaxlength.getValores()[0] : "")%>'/>
			 </nested:lessEqual>
			 <nested:greaterThan property="filas" value="1">
				 <html:textarea property="<%=nombre%>" 
				 	styleId="<%=nombre%>" 
				 	rows="<%=String.valueOf(campo.getFilas())%>" 
				 	cols="<%=String.valueOf(campo.getColumnas())%>"
				 	readonly="<%=(autocalculo) || (bloqueado)%>"
        			disabled="<%=disabled%>"
        			onchange='<%=(!autocalculo && !bloqueado) ? "onFieldChange(this.form, this.name)" : ""%>'/>
			 </nested:greaterThan>		
		</div>
		<nested:notEmpty property="traduccion.ayuda">
		<div class="imc-el-ajuda">
			<p><nested:write property="traduccion.ayuda"/></p>
		</div>		
		</nested:notEmpty>
	</div>
	
</nested:root>
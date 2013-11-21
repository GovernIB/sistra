<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>


<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.CheckBox"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>

	<% boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo())); %> 
	<% boolean disabled = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionDependencia())); %>   
    <% boolean bloqueado = campo.isBloqueado();%>
    <% boolean encuadrar = campo.isEncuadrar();%>
    

	<div class="<%=org.ibit.rol.form.front.util.UtilFrontV2.generateStyleClass(campo)%>" data-type="check">
		<div class="imc-el-control">
			<label>
				<html:checkbox
			        property="<%=nombre%>"
			        styleId="<%=nombre%>"
			        disabled="<%=(disabled || bloqueado)%>"                    
			        onclick='<%=(!autocalculo && !bloqueado) ? "onFieldChange(this.form, this.name)" : ""%>'			        
			    ></html:checkbox>
				<span><nested:write property="traduccion.nombre"/></span>
			</label>
		</div>
		<nested:notEmpty property="traduccion.ayuda">
		<div class="imc-el-ajuda">
			<p><nested:write property="traduccion.ayuda"/></p>
		</div>		
		</nested:notEmpty>
	</div>
	
</nested:root>
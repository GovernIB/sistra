<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>



<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.RadioButton"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    <nested:define id="etiquetaTxt" type="java.lang.String" property="traduccion.nombre"/>

	<% boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo())); %> 
	<% boolean disabled = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionDependencia())); %>   
    <% boolean bloqueado = campo.isBloqueado();%>

	<fieldset class="<%=org.ibit.rol.form.front.util.UtilFrontV2.generateStyleClass(campo)%>" data-type="radio-list">
		<legend class="imc-label">
			<nested:equal property="sinEtiqueta" value="false">
				<%=org.ibit.rol.form.front.util.UtilFrontV2.generaHtmlEtiqueta(etiquetaTxt)%>	
			</nested:equal>
		</legend>
		<ul>
			<% int i = 0; %>
			<logic:iterate name="campo" property="allValoresPosibles" id="opcion" type="org.ibit.rol.form.model.ValorPosible">
			<li>
				<html:radio property="<%=nombre%>" styleId="<%=nombre + \"_\" + i%>" value="<%=opcion.getValor()%>"
	            	disabled="<%=(disabled || bloqueado)%>"
	            	onclick='<%=(!autocalculo && !bloqueado) ? "onFieldChange(this.form, this.name)" : ""%>'	        	            	           
	        	/>
				<label for="<%=nombre + \"_\" + i%>"><%=((org.ibit.rol.form.model.TraValorPosible) opcion.getTraduccion()).getEtiqueta()%></label>				
			</li>
			<% i++; %>
			</logic:iterate>			
		</ul>		
		<nested:notEmpty property="traduccion.ayuda">
		<div class="imc-el-ajuda">
			<p><nested:write property="traduccion.ayuda"/></p>
		</div>		
		</nested:notEmpty>
	</fieldset>
	
</nested:root>

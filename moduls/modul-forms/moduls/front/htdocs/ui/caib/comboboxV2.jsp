<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils, org.ibit.rol.form.model.ValorPosible, org.ibit.rol.form.model.TraValorPosible"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.ComboBox"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    
    <% boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo())); %>
    <% boolean disabled = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionDependencia())); %>
    <% boolean bloqueado = campo.isBloqueado();%>
    
	<div class="<%=org.ibit.rol.form.front.util.UtilFrontV2.generateStyleClass(campo)%>" data-type="select">
		<div class="imc-el-etiqueta">
			<nested:equal property="sinEtiqueta" value="false">
				<label for="<%=nombre%>"><nested:write property="traduccion.nombre"/></label>
			</nested:equal>				
		</div>
		<div class="imc-el-control">
			 <div class="imc-select imc-opcions">			 	
			 	<a class="imc-select"><span></span></a>
			 	<html:hidden property="<%=nombre%>" styleId="<%=nombre%>"/>	
				<ul>	
				<% if (!campo.isObligatorio()) { %>
					<li<nested:empty name="pantallaForm" property="<%=nombre%>"> class="imc-select-seleccionat"</nested:empty>>
						<a data-value="" tabindex="0" href="javascript:;">...</a>
					</li>
    			<% } %>				
				<logic:iterate name="campo" property="allValoresPosibles" id="opcion" type="ValorPosible">
					<li>
						<a data-value="<%=opcion.getValor()%>" tabindex="0" href="javascript:;"><%=((TraValorPosible) opcion.getTraduccion()).getEtiqueta()%></a>
					</li>				
				</logic:iterate>
 				</ul>				
			</div>		
		</div>		
		<nested:notEmpty property="traduccion.ayuda">
		<div class="imc-el-ajuda">
			<p><nested:write property="traduccion.ayuda"/></p>
		</div>		
		</nested:notEmpty>
	</div>
	
	<!--  Añadimos evento onchange -->
	<script type="text/javascript">
		<!--
			$(function(){
				// Seleccionamos elemento
				control_select("<%=nombre%>", "<nested:write name="pantallaForm" property="<%=nombre%>"/>");
			<% if (autocalculo || bloqueado) { %>
				// Ponemos a solo lectura
				control_readOnly("<%=nombre%>", true);	
			<% } else { %>
				// Evento onchange		
				var el_element = $imc_formulari.find(".imc-el-name-<%=nombre%>:first");			
				//el_element.find("div.imc-opcions:first").addClass("imc-opcions-no-events").inputSelect({			
				el_element.find("div.imc-opcions:first").inputSelect({
					alAcabar: function() {
						setTimeout("onFieldChange(document.getElementById(\"pantallaForm\"), \"<%=nombre%>\")",200);	
						// onFieldChange(document.getElementById("pantallaForm"), "<%=nombre%>");				
					}
				});						
			<% } %>

			});

	-->
	</script>
	
</nested:root>
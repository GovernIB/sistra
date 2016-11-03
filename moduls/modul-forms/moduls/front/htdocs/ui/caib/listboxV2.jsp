<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.ListBox"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    <nested:define id="etiquetaTxt" type="java.lang.String" property="traduccion.nombre"/>

	<% boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo())); %>    
    <% boolean bloqueado = campo.isBloqueado();%>

	<fieldset class="<%=org.ibit.rol.form.front.util.UtilFrontV2.generateStyleClass(campo)%>" data-type="check-list-scroll">
		<legend class="imc-label">
			<nested:equal property="sinEtiqueta" value="false">
				<%=org.ibit.rol.form.front.util.UtilFrontV2.generaHtmlEtiqueta(etiquetaTxt)%>	
			</nested:equal>					
		</legend>
		<ul>
			<% int i = 0; %>
			<logic:iterate name="campo" property="allValoresPosibles" id="opcion" type="org.ibit.rol.form.model.ValorPosible">
			<li>
				<label>
					<logic:equal name="campo" property="seleccionMultiple" value="true">
						<html:checkbox property="<%=nombre%>" styleId="<%=nombre + "_" + i%>" value="<%=opcion.getValor()%>"
							onclick='<%=(!autocalculo && !bloqueado) ? "onFieldChange(this.form, this.name)" : ""%>'/>
					</logic:equal>
					<logic:equal name="campo" property="seleccionMultiple" value="false">
						<html:radio property="<%=nombre%>" styleId="<%=nombre + "_" + i%>" value="<%=opcion.getValor()%>"
							onclick='<%=(!autocalculo && !bloqueado) ? "onFieldChange(this.form, this.name)" : ""%>'/>
					</logic:equal>						
					<span><%=((org.ibit.rol.form.model.TraValorPosible) opcion.getTraduccion()).getEtiqueta()%></span>
				</label>
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
	
	<!--  Cargamos valores -->
	<script type="text/javascript">
        <!--
		$(function(){
			<%
	     		String[] values = (String[]) ((org.ibit.rol.form.front.action.PantallaForm) request.getAttribute("pantallaForm")).getMap().get(nombre);	    	
	    		String arrayValues = "[";
		    	if (values != null){	    		
		    		for (int index=0;index<values.length;index++){
		    			if (index>0) arrayValues += ",";
		    			arrayValues += "'" + values[index] + "'";			    			
		    		}
		    	}
		    	arrayValues +="]";
     		%>

			control_select("<%=nombre%>", <%=arrayValues%>);	

			<% if (autocalculo || bloqueado) { %>
			// Ponemos a solo lectura
			control_readOnly("<%=nombre%>", true);	
			<% } %>
			
		});
		  //-->
    </script>
	
</nested:root>
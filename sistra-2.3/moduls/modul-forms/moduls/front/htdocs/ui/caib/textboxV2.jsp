<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="org.ibit.rol.form.model.Validacion"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>

<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.TextBox"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    <nested:define id="etiquetaTxt" property="traduccion.nombre" type="java.lang.String" />
    <nested:define id="valorTxt" name="pantallaForm" property="<%=nombre%>" type="java.lang.String"/>
    <nested:define id="traduccion" property="traduccion" type="org.ibit.rol.form.model.TraCampo"/>

	<% 
	   Validacion vmaxlength = campo.findValidacion("maxlength"); 
	   boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo()));
	   boolean disabled = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionDependencia()));     
       boolean bloqueado = campo.isBloqueado(); 
       String  placeholderTxt = traduccion.getPlaceholder();
       if (placeholderTxt == null)  {
    	   placeholderTxt = "";
       }
       
       String dataType = "text";
       String styleClassInput = "";
       String inputType = "text";
       if (campo.getFilas() <= 1) {
	       if ("FE".equals(campo.getTipoTexto())) {
	    		dataType  ="date";
	    		inputType = "text";
	    		styleClassInput = "imc-data";
	       }
	       if ("HO".equals(campo.getTipoTexto())) {
	    		dataType  = "time";
	    		inputType = "time";
	    		styleClassInput = "imc-hora";
	       } 
	       if ("NU".equals(campo.getTipoTexto())) {
	    		dataType  = "text";
	    		inputType = "number";
	    		styleClassInput = "imc-numero";
	       } 
	       if ("IM".equals(campo.getTipoTexto())) {
	    		dataType  = "text";
	    		inputType = "text";
	    		styleClassInput = "";
	       } 
       }
    %>  
    
    <nested:equal property="oculto" value="true">
    	<div class="<%=org.ibit.rol.form.front.util.UtilFrontV2.generateStyleClass(campo)%>" data-type="hidden">
             <html:hidden property="<%=nombre%>" styleId="<%=nombre%>"/>
        </div>    	
    </nested:equal>
    
    
    <nested:equal property="oculto" value="false">
    
    <div class="<%=org.ibit.rol.form.front.util.UtilFrontV2.generateStyleClass(campo)%>" data-type="<%=dataType%>">
   		<div class="imc-el-etiqueta">
			<nested:equal property="sinEtiqueta" value="false">
				<label for="<%=nombre%>"><%=org.ibit.rol.form.front.util.UtilFrontV2.generaHtmlEtiqueta(etiquetaTxt)%></label>
			</nested:equal>			
		</div>
		<div class="imc-el-control">
			 <nested:lessEqual property="filas" value="1">
			 		<input
			 			type="<%=inputType%>" 
			 			name="<%=nombre%>" 			 			
			 			id="<%=nombre%>"
			 			<% if (autocalculo || bloqueado) { %> 
			 			readonly
			 			<%} %>
			 			<% if (disabled) { %> 
			 			disabled
			 			<%} %>			 			
			 			onchange='<%=(!autocalculo && !bloqueado) ? "onFieldChange(this.form, this.name)" : ""%>'
			 			<% if (vmaxlength != null) { %> 
			 			maxlength='<%=vmaxlength.getValores()[0]%>'
			 			<%} %>
			 			<% if (!"".equals(styleClassInput)) { %> 
			 			class="<%=styleClassInput%>"
			 			<%} %>  
			 			value="<%=org.apache.struts.util.ResponseUtils.filter(valorTxt)%>"
			 			<% if (StringUtils.isNotBlank(placeholderTxt)) { %>
			 			placeholder="<%=org.apache.struts.util.ResponseUtils.filter(placeholderTxt)%>"
			 			<%} %>  
			 			>			 					 				 				 
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
	</nested:equal>
	
</nested:root>
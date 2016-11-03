<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils,org.ibit.rol.form.front.util.JSUtil"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.TreeBox"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    <nested:define id="etiquetaTxt" type="java.lang.String" property="traduccion.nombre"/>

	<% boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo())); %>    
    <% boolean bloqueado = campo.isBloqueado();%>


	<fieldset class="<%=org.ibit.rol.form.front.util.UtilFrontV2.generateStyleClass(campo)%>" data-type="tree">
		<legend class="imc-label">
			<nested:equal property="sinEtiqueta" value="false">
				<%=org.ibit.rol.form.front.util.UtilFrontV2.generaHtmlEtiqueta(etiquetaTxt)%>				
			</nested:equal>			
		</legend>
		<ul>	
		</ul>
		<nested:notEmpty property="traduccion.ayuda">
		<div class="imc-el-ajuda">
			<p><nested:write property="traduccion.ayuda"/></p>
		</div>		
		</nested:notEmpty>
	</fieldset>
	
	<script type="text/javascript">
	    <!--
		$(function(){
	    	// Construye tree    	    	
	    	control_refill('<%=nombre%>',<%=JSUtil.valoresPosiblesToJS(campo.getAllValoresPosibles())%>);    	

    	   // Alimenta los valores del arbol
	     	<%
	     		String[] values = (String[]) ((org.ibit.rol.form.front.action.PantallaForm) request.getAttribute("pantallaForm")).getMap().get(nombre);	    	
	    		String arrayValues = "[";
		    	if (values != null){	    		
		    		for (int i=0;i<values.length;i++){
		    			if (i>0) arrayValues += ",";
		    			arrayValues += "'" + values[i] + "'";
		    		}
		    	}
		    	arrayValues +="]";
	     	%>     
	     	  
	     	
	     	// Seleccionamos opciones
	       	control_select("<%=nombre%>", <%=arrayValues%>);	  

	       	// Expandimos arbol
	       	<nested:equal value="campo" property="expandirTree" value="true">
	       	control_expandAll("<%=nombre%>", true);
	       	</nested:equal>   	

	       	<% if (autocalculo || bloqueado) { %>
			// Ponemos a solo lectura
			control_readOnly("<%=nombre%>", true);	
			<% } %>
			
		}); 	
	
	    // -->
	    </script>
</nested:root>	    
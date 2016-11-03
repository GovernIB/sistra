<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils,org.ibit.rol.form.front.util.JSUtil,org.ibit.rol.form.front.action.PantallaForm"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>

<!-- NO SE USA 
<script src="<html:rewrite page='/js/demoCheckboxNodes.js'/>"></script>   
 -->
 
<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.TreeBox"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>

    <% boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo())); %>
    <% boolean disabled = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionDependencia())); %>
    
    <span class="formEtiqueta">    
    	<nested:write property="traduccion.nombre" filter="false"/><nested:notEqual property="traduccion.nombre" value="&nbsp;">:</nested:notEqual>
    </span>
    
   <logic:greaterThan name="campo" property="altura" value="0">
    <DIV id="treeContainer" style="height:<%=campo.getAltura() * 30%>px;overflow:auto;" onmouseover="<%="setAyuda(" + campo.getOrden() + ")" + (autocalculo ? "; this.blur()":"")%>"></DIV>
    </logic:greaterThan>
    
    <logic:lessEqual name="campo" property="altura" value="0">
    <DIV id="treeContainer" style="height:200px;overflow:auto;" onmouseover="<%="setAyuda(" + campo.getOrden() + ")" + (autocalculo ? "; this.blur()":"")%>"></DIV>
    </logic:lessEqual>
    
     <br />
   
    <script type="text/javascript">
    <!--
    	// Construye tree    	    	
    	refillTree('<%=nombre%>',<%=JSUtil.valoresPosiblesToJS(campo.getAllValoresPosibles())%>,<%=campo.isExpandirTree()%>);    	
    	// Alimenta los valores del arbol
     	<%
     		String[] values = (String[]) ((PantallaForm) request.getAttribute("pantallaForm")).getMap().get(nombre);	    	
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
     	selectOptionsTree("<%=nombre%>", <%=arrayValues%>);

    // -->
    </script>
		<!-- INDRA: nuevo span para la separación entre etiqueta/campo y la siguiente -->
		<span class="formSep"></span>
		<!-- fin INDRA -->
</nested:root>
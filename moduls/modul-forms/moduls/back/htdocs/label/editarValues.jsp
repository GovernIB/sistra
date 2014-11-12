<%@ page language="java" %>
<%@ page import="org.ibit.rol.form.back.util.Util"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:xhtml/>
<script type="text/javascript">
    <!--      
    // Ajusta valores minimos colspan
    function ajustarColspan() {
       	var tipoTextoSelect = document.getElementById("tipoEtiqueta");
       	var tipoTexto = tipoTextoSelect.options[tipoTextoSelect.selectedIndex].value;
       	var colspanSelect = document.getElementById("colSpan");
       	var colspan = colspanSelect.options[colspanSelect.selectedIndex].value;
       	
		var minColSpan = 1;
		
		// Si no es texto normal, debe ocupar toda la linea
		if (tipoTexto != "NO") { 
			minColSpan = 6;	       	        	
		} 

        // Reseteamos select colspan
		while (colspanSelect.length > 0) {
    	   	colspanSelect.remove(0);
    	}
       	for (var i = minColSpan; i <= 6; i++) {
      	    var opcio = new Option(i, i);
      	  	colspanSelect.add(opcio);       	        
      	}	 
		if (colspan >= minColSpan){
			colspanSelect.value = colspan;
		}		          
   	}
//-->
</script>
<% int ti = 1; int minColSpan = 1; %>
<logic:notEqual name="labelForm" property="values.tipoEtiqueta" value="NO">
	<% minColSpan = 6; %>
</logic:notEqual> 

<html:hidden property="idPantalla" />
<input type="hidden" name="idOperacion" value="<%=Util.getIdOperacion(request)%>"/>	
<tr>
    <td class="labelo"><bean:message key="componente.nombreLogico"/></td>
    <td class="input"><html:text styleClass="text" tabindex="<%=Integer.toString(ti++)%>" property="values.nombreLogico" maxlength="128" /></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.nombrelogico"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="label.tipo"/></td>
    <td class="input">
    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.tipoEtiqueta"  styleId="tipoEtiqueta" onchange="ajustarColspan()">
       <html:option value="NO"><bean:message key="label.tipo.normal" /></html:option>
        <html:option value="IN"><bean:message key="label.tipo.info" /></html:option>
        <html:option value="AL"><bean:message key="label.tipo.alerta" /></html:option>
        <html:option value="ER"><bean:message key="label.tipo.error" /></html:option>
    </html:select>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.tipoEtiqueta"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.posicion"/></td>
    <td class="input">
    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.posicion">
       <html:option value="1"><bean:message key="componente.posicion.1" /></html:option>
        <html:option value="0"><bean:message key="componente.posicion.0" /></html:option>
        <html:option value="2"><bean:message key="componente.posicion.2" /></html:option>
        <html:option value="3"><bean:message key="componente.posicion.3" /></html:option>
    </html:select>
</tr>
<tr>
    <td class="label"><bean:message key="componente.colSpan"/></td>
    <td class="input">
    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.colSpan" styleId="colSpan">
        <% for (int i = minColSpan ; i <= 6; i++) { %>															
			<html:option value="<%=Integer.toString(i)%>"><bean:message key="<%=\"componente.colSpan.\" + i%>"/></html:option>
		<% } %>     
    </html:select>
     - <bean:message key="label.colspan.restriccion"/>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.sinEtiqueta"/></td>
    <td class="input">
    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.sinEtiqueta">
        <html:option value="false"><bean:message key="componente.sinEtiqueta.conEtiqueta" /></html:option>
        <html:option value="true"><bean:message key="componente.sinEtiqueta.sinEtiqueta" /></html:option>        
    </html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.alineacion"/></td>
    <td class="input">
    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.alineacion">
        <html:option value="I"><bean:message key="componente.alineacion.izquierda" /></html:option>
        <html:option value="C"><bean:message key="componente.alineacion.centro" /></html:option>       
        <html:option value="D"><bean:message key="componente.alineacion.derecha" /></html:option>         
    </html:select>
    </td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.posicion"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.encuadrar.marcar"/></td>
    <td class="input">
    	 <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.encuadrar">
	        <html:option value="true"><bean:message key="componente.encuadrar.si" /></html:option>
	        <html:option value="false"><bean:message key="componente.encuadrar.no" /></html:option>                
	    </html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.encuadrar.cabecera"/></td>
    <td class="input">
    	 <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.encuadrarCabecera">
	        <html:option value="true"><bean:message key="componente.encuadrar.si" /></html:option>
	        <html:option value="false"><bean:message key="componente.encuadrar.no" /></html:option>                
	    </html:select>
    </td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.encuadrar"/></td>
</tr>

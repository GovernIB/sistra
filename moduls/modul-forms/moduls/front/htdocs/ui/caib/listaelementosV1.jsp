<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils,org.ibit.rol.form.front.util.JSUtil,org.ibit.rol.form.front.action.PantallaForm"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>

<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.ListaElementos"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    <nested:define id="pathIconografia" name="pathIconografia" scope="request"/>

	<script type="text/javascript">
		var <%="listaelementos_"+nombre+"_validacionInsertar"%>="<bean:message bundle="caibMessages" key="validacion.insertarElemento"/>";
		var <%="listaelementos_"+nombre+"_validacionEliminar"%>="<bean:message bundle="caibMessages" key="validacion.eliminarElemento"/>";
		var <%="listaelementos_"+nombre+"_validacionModificar"%>="<bean:message bundle="caibMessages" key="validacion.modificarElemento"/>";				
		var <%="listaelementos_"+nombre+"_validacionSubir"%>="<bean:message bundle="caibMessages" key="validacion.subirElemento"/>";				
		var <%="listaelementos_"+nombre+"_validacionBajar"%>="<bean:message bundle="caibMessages" key="validacion.bajarElemento"/>";				
	</script>   
	    
	<span class="formEtiqueta">    
    	<nested:write property="traduccion.nombre" filter="false"/><nested:notEqual property="traduccion.nombre" value="&nbsp;">:</nested:notEqual>
    </span>
    
    <!--  EL CAMPO EN SI NO CONTIENE NINGUN VALOR -->
    <html:hidden property="<%=nombre%>"/>
    
    <logic:equal name="campo" property="anchuraMaxima" value="true">
	<div style="overflow:hidden; clear:left; width:100%;">
	</logic:equal>
	<logic:equal name="campo" property="anchuraMaxima" value="false">
	<div style="overflow:hidden; width:27.5em;">
	</logic:equal>
    
    <!--  OPERACIONES -->            
   	<button type="button" class="botonListaElementos" id="<%="listaelementos@"+nombre+"_insertar"%>" onclick='detalleElemento("<%=nombre%>","insertar",<%="listaelementos_"+nombre+"_validacionInsertar"%>);' title='<bean:message bundle="caibMessages" key="boton.insertarElemento"/>'><bean:message bundle="caibMessages" key="boton.insertarElemento"/></button>
	<button type="button" class="botonListaElementos" id="<%="listaelementos@"+nombre+"_eliminar"%>" onclick='detalleElemento("<%=nombre%>","eliminar",<%="listaelementos_"+nombre+"_validacionEliminar"%>);' title='<bean:message bundle="caibMessages" key="boton.eliminarElemento"/>'><bean:message bundle="caibMessages" key="boton.eliminarElemento"/></button>
	<button type="button" class="botonListaElementos" id="<%="listaelementos@"+nombre+"_modificar"%>" onclick='detalleElemento("<%=nombre%>","modificar",<%="listaelementos_"+nombre+"_validacionModificar"%>);' title='<bean:message bundle="caibMessages" key="boton.modificarElemento"/>'><bean:message bundle="caibMessages" key="boton.modificarElemento"/></button>
   	&nbsp;&nbsp;
   	<button type="button" class="botonListaElementos" id="<%="listaelementos@"+nombre+"_subir"%>" onclick='detalleElemento("<%=nombre%>","subir",<%="listaelementos_"+nombre+"_validacionSubir"%>);' title='<bean:message bundle="caibMessages" key="boton.subirElemento"/>'><bean:message bundle="caibMessages" key="boton.subirElemento"/></button>
	<button type="button" class="botonListaElementos" id="<%="listaelementos@"+nombre+"_bajar"%>" onclick='detalleElemento("<%=nombre%>","bajar",<%="listaelementos_"+nombre+"_validacionBajar"%>);' title='<bean:message bundle="caibMessages" key="boton.bajarElemento"/>'><bean:message bundle="caibMessages" key="boton.bajarElemento"/></button>		

    
    <!--  TABLA DE ELEMENTOS -->
    <logic:equal name="campo" property="anchuraMaxima" value="true">
	<div id="listaContainer" style="overflow:auto; width:99%; height:170px; margin-top:.5em; background-color:#fff;" onMouseOver='<%="setAyuda(" + campo.getOrden() + ")"%>'>    
	<table id="listaContainerTabla" style="width:96%;">
	</logic:equal>
	<logic:equal name="campo" property="anchuraMaxima" value="false">
	<div id="listaContainer" style="overflow:auto; width:26.5em; height:170px; margin-top:.5em; background-color:#fff;" onMouseOver='<%="setAyuda(" + campo.getOrden() + ")"%>'>    
	<table id="listaContainerTabla">
	</logic:equal>            
		<thead>
			<tr>
				<th scope="col" style="width:.1em;">&nbsp;</TH>
				<logic:iterate name="<%=\"listaelementos@\"+nombre%>" property="camposTabla" id="campoTabla">
				<logic:notEmpty name="campoTabla" property="anchoColumna">				
				<th scope="col" style="width:<bean:write name="campoTabla" property="anchoColumna"/>%;">				
				</logic:notEmpty>
				<logic:empty name="campoTabla" property="anchoColumna">				
				<th scope="col">				
				</logic:empty>		        		        
		        <bean:write name="campoTabla" property="traduccion.nombre" filter="false"/></th>	
		        </logic:iterate>	      				
			</tr>
		</thead>
		<tbody>
		 <%int i=0;%>
	     <logic:iterate name="<%=\"listaelementos@\"+nombre%>" property="filasTabla" id="filaTabla">
	     	<tr>
		      <td><input name="<%="listaelementos@"+nombre+"-index"%>" type="radio" value="<%=i++%>"></td>
		      <logic:iterate name="filaTabla" id="datoTabla">
		      <td><bean:write name="datoTabla"/></td>
			  </logic:iterate>	  	    
		    </tr>	      
	     </logic:iterate>			
		</tbody>
	</table>    
	</div>    
		
	</div> 
	<!-- INDRA: nuevo span para la separación entre etiqueta/campo y la siguiente -->
	<span class="formSep"></span>
	<!-- fin INDRA -->
</nested:root>
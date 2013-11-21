<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils,org.ibit.rol.form.front.util.JSUtil,org.ibit.rol.form.front.action.PantallaForm"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>


<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.ListaElementos"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    
    <!--  meter altura -->
	<fieldset class="<%=org.ibit.rol.form.front.util.UtilFrontV2.generateStyleClass(campo)%>" data-type="table">
		<legend class="imc-label">
			<nested:equal property="sinEtiqueta" value="false">
				<nested:write property="traduccion.nombre"/>
			</nested:equal>				
		</legend>
		<div class="imc-el-taula-botonera">
			<ul>
				<li>
					<button class="imc-bt-afegix" type="button" title="<bean:message bundle="caibMessages" key="boton.insertarElemento"/>" onclick='detalleElemento("<%=nombre%>","insertar");'>
						<span><bean:message bundle="caibMessages" key="boton.insertarElemento"/></span><span class="imc-bt-icona">&nbsp;</span>
					</button>
				</li>
				<li>
					<button class="imc-bt-elimina" type="button" title="<bean:message bundle="caibMessages" key="boton.eliminarElemento"/>" onclick='detalleElemento("<%=nombre%>","eliminar");'>
						<span><bean:message bundle="caibMessages" key="boton.eliminarElemento"/></span><span class="imc-bt-icona">&nbsp;</span>
					</button>
				</li>
				<li>
					<button class="imc-bt-modifica" type="button" title="<bean:message bundle="caibMessages" key="boton.modificarElemento"/>" onclick='detalleElemento("<%=nombre%>","modificar");'>
						<span><bean:message bundle="caibMessages" key="boton.modificarElemento"/></span><span class="imc-bt-icona">&nbsp;</span>
					</button>
				</li>
			</ul>
			<ul class="imc-el-ta-bo-posicio">
				<li>
					<button class="imc-bt-puja" type="button" title="<bean:message bundle="caibMessages" key="boton.subirElemento"/>" onclick='detalleElemento("<%=nombre%>","subir");'>
						<span><bean:message bundle="caibMessages" key="boton.subirElemento"/></span><span class="imc-bt-icona">&nbsp;</span>
					</button>
				</li>
				<li>
					<button class="imc-bt-baixa" type="button" title="<bean:message bundle="caibMessages" key="boton.bajarElemento"/>" onclick='detalleElemento("<%=nombre%>","bajar");'>
						<span><bean:message bundle="caibMessages" key="boton.bajarElemento"/></span><span class="imc-bt-icona">&nbsp;</span>
					</button>
				</li>
			</ul>
		</div>
		<div class="imc-el-taula-elements">
			<table>
				<thead>
					<tr>
						<th class="imc-check"></th>
						<logic:iterate name="<%=\"listaelementos@\"+nombre%>" property="camposTabla" id="campoTabla">
							<th <logic:notEmpty name="campoTabla" property="anchoColumna"> style="width:<bean:write name="campoTabla" property="anchoColumna"/>%;"</logic:notEmpty>>
								<span><bean:write name="campoTabla" property="traduccion.nombre"/></span>
							</th>							
				        </logic:iterate>	      				
					</tr>				
				</thead>
				<tbody>
					<%int i=0;%>
				     <logic:iterate name="<%=\"listaelementos@\"+nombre%>" property="filasTabla" id="filaTabla">
				     	<tr>
				     	  <td class="imc-check"><input type="radio" name="<%="listaelementos@"+nombre+"-index"%>" id="<%="listaelementos@"+nombre+"-index"%>" value="<%=i++%>"></td>	
					      <logic:iterate name="filaTabla" id="datoTabla">
					     	 <td><bean:write name="datoTabla"/></td>
						  </logic:iterate>	  	    
					    </tr>	      
				     </logic:iterate>						
				</tbody>
			</table>
		</div>
		<nested:notEmpty property="traduccion.ayuda">
		<div class="imc-el-ajuda">
			<p><nested:write property="traduccion.ayuda"/></p>
		</div>		
		</nested:notEmpty>
	</fieldset>		
	
</nested:root>
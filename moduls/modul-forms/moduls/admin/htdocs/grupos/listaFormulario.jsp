<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<html:xhtml/>
	<script type="text/javascript" src="../../js/jquery-1.2.3.pack.js"></script>
	<script type="text/javascript" src="../../js/jquery.selectboxes.pack.js"></script>
	<script type="text/javascript">
	<!--
	$().ready(function() {
	 $('#asigna').click(function() {
	 	if($("#gruposNoAsoc").val()!=null && $("#gruposNoAsoc").val().toString()!=''){
		   var gruposNoAsoc=  $("#gruposNoAsoc").val().toString();
		   var formulario = document.getElementById("idFormulario").value;
		   $.ajaxSetup({scriptCharset: "utf-8" , contentType: "application/json; charset=utf-8"}); 
		   $.getJSON("asociar.do", { idForm: formulario, gruposNoAsoc : gruposNoAsoc },function(json){	
				if(json.asociado=="true"){
					!$('#gruposNoAsoc option:selected').remove().appendTo('#grupos');
				}else{
					alert("No se ha podido asociar el formulario al/los grupo/os");
				}			
			});
	   	}
	 });
	 $('#desasigna').click(function() {
	 	if($("#grupos").val()!=null && $("#grupos").val().toString()!=''){
		   var grupos=  $("#grupos").val().toString();
		   var formulario = document.getElementById("idFormulario").value;
	       $.ajaxSetup({scriptCharset: "utf-8" , contentType: "application/json; charset=utf-8"}); 
		   $.getJSON("asociar.do", { grupo: grupos, idForm: formulario},function(json){	
				if(json.asociado=="true"){
					!$('#grupos option:selected').remove().appendTo('#gruposNoAsoc');
				}else{
					alert("No se ha podido desasociar el formulario al/los grupo/os");
				}			
			});
		}
	 });
	});
	
	
	//-->
	</script>
<tiles:useAttribute name="idFormulario"/>
<tiles:useAttribute name="existeGrupo"/>
<tiles:useAttribute name="gruposForm"/>
<tiles:useAttribute name="gruposNoForm"/>
<html:hidden name="formularioForm" property="flag" value=""/>
<input type="hidden" name="idFormulario" id="idFormulario" value=<%=idFormulario%> />
<br />
<table class="nomarc">
    <tr><td class="titulo"><bean:message key="grupos.asociado" /></td></tr>
</table>
<br />

<logic:equal name="existeGrupo" value="false">
    <table class="marc">
      <tr><td class="alert"><bean:message key="grupo.selec.vacio" /></td></tr>
    </table>
</logic:equal>

<logic:equal name="existeGrupo" value="true">
    <table class="marc">
    	<tr>
    		<td class="labeld">Grupos no asociados</td>
    		<td>&nbsp;</td>
    		<td class="labeld">Grupos asociados</td>
    	</tr>
    	<tr>
	    	<td style="width: 40%;" rowspan="2" align="center">
    		<html:select styleId="gruposNoAsoc" name="formularioForm" property="gruposNoAsoc" multiple="multiple" size="5" styleClass="selectGrupos" >
				<logic:iterate id="grupoNoAsc" name="gruposNoForm">	
					<html:option value="<%=((org.ibit.rol.form.model.Grupos)grupoNoAsc).getCodigo()%>" ><bean:write name="grupoNoAsc" property="nombre"/></html:option>
				</logic:iterate>
			</html:select>
			
    		</td>
    		<td style="width: 20%" align="center">
    			<button id="asigna" class="buttond" type="button" >
               		<bean:message key="boton.asociar" />
	            </button>
    		</td>
    		<td style="width: 40%" rowspan="2" align="center">
    		<html:select styleId="grupos" name="formularioForm" property="grupos" multiple="multiple" size="5" styleClass="selectGrupos" >
				<logic:iterate id="grupo" name="gruposForm">	
					<html:option value="<%=((org.ibit.rol.form.model.Grupos)grupo).getCodigo()%>" ><bean:write name="grupo" property="nombre"/></html:option>
				</logic:iterate>
			</html:select>
    		</td >
    		
    		
    	</tr>
    	<tr>
    		<td style="width: 20%" align="center">
    			<button id="desasigna" class="buttond" type="button" >
               		<bean:message key="boton.desasociar" />
	            </button>
    		</td>
    	</tr>
    </table>
</logic:equal>




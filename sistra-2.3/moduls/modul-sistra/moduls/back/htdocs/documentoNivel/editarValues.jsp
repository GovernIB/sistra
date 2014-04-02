<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<bean:define id="urlEditarText">
    <html:rewrite page="/editarText.jsp"/>
</bean:define>
<bean:define id="urlArbol">
    <html:rewrite page="/arbolForms.do"/>
</bean:define>

<script type="text/javascript">
     <!--
     function viewAyudaExpresion() {
        var url = '<html:rewrite page="/documentoNivel/ayudaPantalla.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     
     function reloadVersionFormulario( desplegable )
     {
     	var urlActual = document.location.href;
     	var iIdx = 0;
     	var lastIdx = urlActual.indexOf( "&" );
     	lastIdx =  lastIdx < 0 ? urlActual.length : lastIdx;
     	urlActual = urlActual.substring( iIdx, lastIdx );
//     	alert( urlActual );
     	document.location.href = urlActual + "&documentoNivel.formularioFormsModelo=" + desplegable.options[ desplegable.selectedIndex ].value;
     }
     
     function mostrarArbolForms(url) {        
        obrir(url, "Arbol", 540, 400);
     }
     
    function resetearModeloVersion(){
	    campoModelo = document.getElementsByName("values.formularioFormsModelo").item(0);
     	campoVersion = document.getElementsByName("values.formularioFormsVersion").item(0);
    	campoModelo.value="";
     	campoVersion.value="";
    }
     
     function cambioGestorFormulario(){
	    campoGestor = document.getElementsByName("values.formularioGestorFormulario").item(0);
     	campoModelo = document.getElementsByName("values.formularioFormsModelo").item(0);
     	campoVersion = document.getElementsByName("values.formularioFormsVersion").item(0);
     	botonForms = document.getElementById("botonForms");
     	botonAbrirForms = document.getElementById("botonAbrirForms");
     	
     	if (campoGestor.options[campoGestor.selectedIndex].value == 'forms'){
     		botonForms.style.visibility="visible";
     		botonAbrirForms.style.visibility="visible";
     		campoModelo.readOnly=true;   
     		campoVersion.readOnly=true; 		
     	}else{
     		botonForms.style.visibility="hidden";
     		botonAbrirForms.style.visibility="hidden";
     		campoModelo.readOnly=false;   
     		campoVersion.readOnly=false; 		
     	}     	
     }
     
     function abrirForms(){
     	campoModelo = document.getElementsByName("values.formularioFormsModelo").item(0).value;
     	campoVersion = document.getElementsByName("values.formularioFormsVersion").item(0).value;
     	window.open("/formback/init.do?modelo="+campoModelo+"&version="+campoVersion,"FORMS");
     }
     
     // -->
</script>
<script type="text/javascript">
     <!--
     function edit(url) {
       obrir(url, "Edicion", 940, 600);
     }
     // -->
</script>

<html:hidden property="idDocumento" />
<tr>
	<td class="separador" colspan="2"><bean:message key="documentoNivel.datosEspecificacion"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="documentoNivel.nivelAutenticacion"/></td>
    <td class="input">Certificado <html:multibox property="nivelesAutenticacionSelected" value="C"/> Usuario/Password <html:multibox property="nivelesAutenticacionSelected" value="U"/> Anónimo <html:multibox property="nivelesAutenticacionSelected" value="A"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="documentoNivel.version"/></td>
    <td class="input">
    	<html:select property="values.version">
   			<html:options collection="listaversionesrds" property="CODIGO" labelProperty="DESCRIPCION" />
    	</html:select>
    </td>
</tr>
<tr>
    <td class="labelo"><bean:message key="documentoNivel.obligatorio"/></td>
    <td class="input">Si<html:radio property="values.obligatorio" value="S"/> No<html:radio property="values.obligatorio" value="N"/> Dependiente script<html:radio property="values.obligatorio" value="D"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documentoNivel.obligatorioScript"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="obligatorioScript"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=obligatorioScript&titulo=documentoNivel.obligatorioScript" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documentoNivel.flujoTramitacionScript"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="flujoTramitacionScript"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=flujoTramitacionScript&titulo=documentoNivel.flujoTramitacionScript" %>');"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="documentoNivel.firmar"/></td>
    <td class="input">Si<html:radio property="values.firmar" value="S"/> No<html:radio property="values.firmar" value="N"/>&nbsp;&nbsp;&nbsp;<bean:message key="documentoNivel.firmarNoPagos"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documentoNivel.firmante"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="values.firmante"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=values.firmante&titulo=documentoNivel.firmante" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documentoNivel.contentType"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="values.contentType"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=values.contentType&titulo=documentoNivel.contentType" %>');"/></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="documentoNivel.datosFormulario"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documentoNivel.formularioGestor"/></td>
    <td class="input">
    	<html:select styleClass="text" property="values.formularioGestorFormulario" onchange="cambioGestorFormulario();resetearModeloVersion();">
    		<html:options collection="gestoresFormularios" property="key" labelProperty="value"/>
    	</html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="documentoNivel.formularioForms"/></td>
    <td class="input">
    	<bean:message key="documentoNivel.formularioFormsModelo"/>    	
		<html:text property="values.formularioFormsModelo"   size="11"/>    	
		<bean:message key="documentoNivel.formularioFormsVersion"/>
		<html:text property="values.formularioFormsVersion" size="3"/>    	
				
		<input type="button" id="botonForms" value="..."  class = "botonEditar" 
			onclick="mostrarArbolForms('<%=urlArbol + "?modelo=values.formularioFormsModelo&version=values.formularioFormsVersion" %>');"
		/>
		
		<input type="button" id="botonAbrirForms" value="FORMS"  class = "botonEditar" 
			onclick="abrirForms();"
		/>
		
    </td>
</tr>
<!-- 
<tr>
    <td class="label"><bean:message key="documentoNivel.formularioGuardarSinTerminar"/></td>
    <td class="input">
    	<bean:message key="afirmacion"/><html:radio property="values.formularioGuardarSinTerminar" value="S"/> 
    	<bean:message key="negacion"/><html:radio property="values.formularioGuardarSinTerminar" value="N"/>     
    </td>
</tr>
 -->
<tr>
    <td class="label"><bean:message key="documentoNivel.formularioDatosInicialesScript"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="formularioDatosInicialesScript"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=formularioDatosInicialesScript&titulo=documentoNivel.formularioDatosInicialesScript" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documentoNivel.formularioConfiguracionScript"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="formularioConfiguracionScript"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=formularioConfiguracionScript&titulo=documentoNivel.formularioConfiguracionScript" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documentoNivel.formularioValidacionPostFormScript"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="formularioValidacionPostFormScript"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=formularioValidacionPostFormScript&titulo=documentoNivel.formularioValidacionPostFormScript" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documentoNivel.formularioModificacionPostFormScript"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="formularioModificacionPostFormScript"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=formularioModificacionPostFormScript&titulo=documentoNivel.formularioModificacionPostFormScript" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documentoNivel.formularioPlantillaScript"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="formularioPlantillaScript"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=formularioPlantillaScript&titulo=documentoNivel.formularioPlantillaScript" %>');"/></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="documentoNivel.datosPago"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documentoNivel.pagoCalcularPagoScript"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="pagoCalcularPagoScript"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=pagoCalcularPagoScript&titulo=documentoNivel.pagoCalcularPagoScript" %>');"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="documentoNivel.tipoPago"/></td>
    <td class="input"><bean:message key="documentoNivel.tipoPago.telematico"/><html:radio property="values.pagoMetodos" value="T"/> <bean:message key="documentoNivel.tipoPago.presencial"/><html:radio property="values.pagoMetodos" value="P"/><bean:message key="documentoNivel.tipoPago.ambos"/><html:radio property="values.pagoMetodos" value="A"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="documentoNivel.pagoPlugin"/></td>
    <td class="input">
    	<html:select property="values.pagoPlugin">
   			<html:options collection="listaPluginsPago" property="CODIGO" labelProperty="DESCRIPCION" />
    	</html:select>
    </td>
</tr>
<script>cambioGestorFormulario();</script>

<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<script type="text/javascript">
     <!--
     function viewAyudaExpresion() {
        var url = '<html:rewrite page="/documento/ayudaPantalla.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     
     // Funcion para establecer el modelo de pago automáticamente 		 
     function establecerModelo(){          
	  	if (getCheckedValue(document.forms[0]["values.tipo"]) == 'P'){  	
	  		alert("Se ha establecido el modelo de pago por defecto");
	  		document.forms[0]["values.modelo"].value = "<%=es.caib.redose.modelInterfaz.ConstantesRDS.MODELO_PAGO%>";	  		
	  	}	
     }
      
     function getCheckedValue(radioObj) {
	if(!radioObj)
		return "";
	var radioLength = radioObj.length;
	if(radioLength == undefined)
		if(radioObj.checked)
			return radioObj.value;
		else
			return "";
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
	}
     // -->
</script>
<html:hidden property="idTramiteVersion" />
<tr>
	<td class="separador" colspan="2"><bean:message key="documento.datosDocumento"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="documento.tipo"/></td>
    <td class="input">Formulario<html:radio property="values.tipo" value="F" onclick="establecerModelo();"/> Anexo<html:radio property="values.tipo" value="A" onclick="establecerModelo();"/> Pago<html:radio property="values.tipo" value="P" onclick="establecerModelo();"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="documento.identificador"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.identificador" maxlength="5" /></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="documento.orden"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.orden" maxlength="2" /></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="documento.modelo"/></td>
    <td class="input">    	
    	<html:select property="values.modelo">
   			<html:options collection="listamodelos" property="CODIGO" labelProperty="DESCRIPCION" />
    	</html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="documento.idPad"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.idPad" maxlength="20" /></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="documento.datosFormulario"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documento.formularioPreregistro"/></td>
    <td class="input">Si<html:radio property="values.formularioPreregistro" value="S"/> No<html:radio property="values.formularioPreregistro" value="N"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documento.formularioJustificante"/></td>
    <td class="input">Si<html:radio property="values.formularioJustificante" value="S"/> No<html:radio property="values.formularioJustificante" value="N"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documento.formularioAnexarJustificante"/></td>
    <td class="input">Si<html:radio property="values.formularioAnexarJustificante" value="S"/> No<html:radio property="values.formularioAnexarJustificante" value="N"/></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="documento.datosAnexo"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documento.anexoPresentar"/></td>
    <td class="input">Telemáticamente<html:radio property="values.anexoPresentarTelematicamente" value="S"/> Presencial<html:radio property="values.anexoPresentarTelematicamente" value="N"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documento.anexoExtensiones"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.anexoExtensiones" maxlength="50" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="documento.anexoConversionPDF"/></td>
    <td class="input">
    	Si<html:radio property="values.anexoConversionPDF" value="S"/> No<html:radio property="values.anexoConversionPDF" value="N"/>
		&nbsp;&nbsp;&nbsp;
    	<small><bean:message key="documento.anexoConversionPDFAviso"/></small>
    	
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="documento.anexoTamanyoMax"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.anexoTamanyoMax" maxlength="5" /> Kb.</td>
</tr>
<tr>
    <td class="label"><bean:message key="documento.anexoUrlPlantilla"/></td>
    <td class="input"><html:text styleClass="textLargo" property="values.anexoUrlPlantilla" maxlength="500"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documento.anexoDescargarPlantilla"/></td>
    <td class="input">Si<html:radio property="values.anexoDescargarPlantilla" value="S"/> No<html:radio property="values.anexoDescargarPlantilla" value="N"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documento.anexoCompulsarPreregistro"/></td>
    <td class="input">Si<html:radio property="values.anexoCompulsarPreregistro" value="S"/> No<html:radio property="values.anexoCompulsarPreregistro" value="N"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documento.anexoFotocopia"/></td>
    <td class="input">Si<html:radio property="values.anexoFotocopia" value="S"/> No<html:radio property="values.anexoFotocopia" value="N"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="documento.generico"/></td>
    <td class="input">
       <table>
		<tr>
		    <td class="input" nowrap="nowrap" width="1%">Si<html:radio property="values.generico" value="S"/> No<html:radio property="values.generico" value="N"/></td>
		    <td nowrap="nowrap" width="1%" align="right"><bean:message key="documento.maxGenericos"/></td>
		    <td class="input" nowrap="nowrap" width="1%"><html:text styleClass="text" tabindex="1" property="values.maxGenericos" maxlength="2" /></td>
		    <td>&nbsp;</td>
		</tr>        
    </table>    
    </td>
</tr>
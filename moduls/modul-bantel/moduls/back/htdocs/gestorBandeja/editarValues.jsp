<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<script type="text/javascript">
     <!--
     function viewAyudaExpresion() {
        var url = '<html:rewrite page="/gestorBandeja/ayudaPantalla.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     // -->
</script>
<tr>
	<td class="separador" colspan="2"><bean:message key="gestorBandeja.definicion"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="gestorBandeja.seyconID"/></td>
    <td class="input"><html:text styleClass="data" tabindex="1" property="values.seyconID" maxlength="50" readonly="<%= request.getAttribute( "idReadOnly" ) != null %>"/></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="gestorBandeja.propiedades"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="gestorBandeja.email"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="10" property="values.email" maxlength="500"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="gestorBandeja.informeAviso"/></td>
    <td class="input"><html:text styleClass="text" tabindex="10" property="values.intervaloInforme" maxlength="2"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="gestorBandeja.permitirCambioEstado"/></td>
    <td class="input">Si<html:radio property="values.permitirCambioEstado" value="S"/> No<html:radio property="values.permitirCambioEstado" value="N"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="gestorBandeja.permitirCambioEstadoMasivo"/></td>
    <td class="input">Si<html:radio property="values.permitirCambioEstadoMasivo" value="S"/> No<html:radio property="values.permitirCambioEstadoMasivo" value="N"/></td>    
</tr>
<tr>
    <td class="labelo"><bean:message key="gestorBandeja.permitirGestionExpedientes"/></td>
    <td class="input">Si<html:radio property="values.permitirGestionExpedientes" value="S"/> No<html:radio property="values.permitirGestionExpedientes" value="N"/></td>    
</tr>
<tr>
	<td class="labelo"><bean:message key="gestorBandeja.tramitesGestionados"/></td>
	<td class="labelo">
	<div id="multibox">
		<table width="100%">		
			<logic:iterate id="tramite" name="tramiteOptions" type="es.caib.bantel.model.Tramite">		
				<tr>			
					<td align="left">
						<html:multibox property="tramites" value="<%= tramite.getIdentificador() %>"/> <bean:write name="tramite" property="descripcion" />
					</td>
				</tr>			
			</logic:iterate>
		</table>
	</div>
	</td>
</tr>


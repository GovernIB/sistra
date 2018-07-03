<%@ page language="java" import="es.caib.bantel.model.Procedimiento"%>
<%@ page import="es.caib.bantel.back.util.Util"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<html:xhtml/>

<input type="hidden" name="idOperacion" value="<%=Util.getIdOperacion(request)%>"/>	

<bean:define id="urlArbol">
    <html:rewrite page="/arbolProcs.do"/>
</bean:define>
<bean:define id="urlArbolServicios">
    <html:rewrite page="/arbolServicios.do"/>
</bean:define>
<bean:define id="entidad" name="entidad" type="java.lang.String"/>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.1.min.js"></script>
<script type="text/javascript">
     <!--
     function viewAyuda() {
        var url = '<html:rewrite page="/tramite/ayuda.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     
     
     function mostrarErrores(){
        var url = "<html:rewrite page="/tramite/erroresIntegracion.jsp" />";
        url = url + "?codigoTramiteError=" + $("#identificadorProcedimiento").val();
        obrir(url, "Errores", 540, 400);
	}
     
    function mostrarArbolForms(url) {        
         obrir(url, "Arbol", 540, 400);
    }
    
    function mostrarArbolServicios(url) {
        //var url = '<html:rewrite page="/arbolServicios.do" />';
        obrir(url, "Arbol", 540, 400);
     }
    
    function lanzaAviso(){
    	var procedimiento = $("#identificadorProcedimiento").val();

			var mapVars = {};
			mapVars["proc"] = procedimiento;	
    	
		if(confirm ( "<bean:message key='tramite.lanzarAviso.confirmacion' />" )){
			$.ajax({
	    		type: "POST",
	    		url: "lanzaAviso.do",
	    		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
	    		data: mapVars,
	    		dataType: "json",
	    		error: function() {
	    			alert("Error enviando datos al servidor. Intentelo de nuevo.");					
	    		},
	    		success: function(json) {					
	    			if (json.error == "") {
	    	 			alert("<bean:message key="tramite.lanzarAviso.entradasAvisadas"/>");
	    	 		} else {
	    	 	 		alert(json.error);
	    	 		}						
	    		}
	    	});
		}
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

<html:hidden property="readOnly" value="<%= request.getAttribute( \"idReadOnly\" ).toString() %>"/> 

<tr>
	<td class="separador" colspan="2"><bean:message key="tramite.definicion"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="tramite.identificador"/></td>
    <td class="input"><html:text styleClass="data" tabindex="1" property="values.identificador" styleId="identificadorProcedimiento" maxlength="20" readonly="<%= Boolean.parseBoolean((String) request.getAttribute( \"idReadOnly\" )) %>" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.entidad"/></td>
    <td class="input">
    	<html:select property="values.entidad" disabled="<%= Boolean.parseBoolean((String) request.getAttribute( \"idReadOnly\" )) %>" >
   			<html:options collection="listaEntidades" property="identificador" labelProperty="descripcion" />
    	</html:select>
    	<logic:equal name="idReadOnly" value="true">
    		<html:hidden property="values.entidad"/>
    	</logic:equal>
    </td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="tramite.procExterno"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="tramite.idProcExt"/></td>
    <td class="input">
    	<html:text styleClass="data" tabindex="1" property="values.idProcExt" styleId="identificadorProcedimientoExt" maxlength="20" readonly="true"  />
    	<input type="button" id="botonForms" value="..." class = "botonEditar"
			onclick="mostrarArbolForms('<%=urlArbol + "?identificador=values.idProcExt" %>');"
		/>
    </td>
</tr>

<logic:equal name="idReadOnly" value="true">                                                                        

<tr>
	<td class="separador" colspan="2"><bean:message key="tramite.gestionExpedientes"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.gestionExpedientes.unidadAdministrativa"/></td>
    <td class="input">
    	<html:select property="values.unidadAdministrativa">
   			<html:options collection="listaUnidadesAdministrativa" property="codigo" labelProperty="descripcion" />
    	</html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.gestionExpedientes.oficinaRegistro"/></td>
    <td class="input">
    	<html:select property="values.oficinaRegistro">
   			<html:options collection="listaOficinas" property="codigo" labelProperty="descripcion" />
    	</html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.gestionExpedientes.servicioRegistro"/></td>
    <td class="input">
    	<html:select property="values.organoRegistro">
   			<html:options collection="listaOrganos" property="codigo" labelProperty="descripcion" />
    	</html:select>
    	<input type="button" value="..."  class = "botonEditar" onclick="mostrarArbolServicios('<%=urlArbolServicios + "?id=values.organoRegistro&entidad=" + entidad %>');"/>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.gestionExpedientes.permitirSmsAvisos"/></td>
    <td class="input">Si<html:radio property="values.permitirSms" value="S"/> No <html:radio property="values.permitirSms" value="N"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.gestionExpedientes.permitirPlazoNotificacionesVariable"/></td>
    <td class="input">Si<html:radio property="values.permitirPlazoNotificacionesVariable" value="S"/> No (<bean:message key="tramite.gestionExpedientes.permitirPlazoNotificacionesVariable.plazoDefecto"/>)<html:radio property="values.permitirPlazoNotificacionesVariable" value="N"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.gestionExpedientes.claveAccesoDefecto"/></td>
    <td class="input">Si<html:radio property="values.accesoClaveDefecto" value="S"/> No <html:radio property="values.accesoClaveDefecto" value="N"/></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="tramite.avisosProcedimiento"/></td>	
</tr>
<tr>
    <td class="label"><bean:message key="tramite.avisosProcedmiento.remitente"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="10" property="values.remitenteAvisosProcedimiento" maxlength="255"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.avisosProcedmiento.responderA"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="10" property="values.emailRespuestaAvisosProcedimiento" maxlength="255"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.avisosIncidencias.mail"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="10" property="values.emailIncidencias" maxlength="255"/></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="tramite.avisoBackOffice"/></td>
</tr>
<tr>
	<td class="label"><bean:message key="tramite.periodica"/></td>
    <td class="input">Si<html:radio property="values.periodica" value="S"/> No <html:radio property="values.periodica" value="N"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.inmediata"/></td>
    <td class="input">Si<html:radio property="values.inmediata" value="S"/> No <html:radio property="values.inmediata" value="N"/></td>
</tr>
<tr>
	<td class="label"><bean:message key="tramite.forzarAviso"/></td>
	<td class="input"><input type="button" onclick="lanzaAviso();" value="<bean:message key="tramite.lanzarAviso.value"/>"  class="button"/></td>            
</tr>
<tr>
    <td class="label"><bean:message key="tramite.gestionExpedientes.permitirAvisosNotificaciones"/></td>
    <td class="input">Si<html:radio property="values.avisarNotificaciones" value="S"/> No <html:radio property="values.avisarNotificaciones" value="N"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.tipoAcceso"/></td>
    <td class="input">EJB<html:radio property="values.tipoAcceso" value="<%=Character.toString(Procedimiento.ACCESO_EJB)%>"/> Webservice<html:radio property="values.tipoAcceso" value="<%=Character.toString(Procedimiento.ACCESO_WEBSERVICE)%>"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.url"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="10" property="values.url" maxlength="500"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.versionWS"/></td>
    <td class="input">
    	<html:select property="values.versionWS">
   			<html:options collection="listaVersionesWS" property="CODIGO" labelProperty="DESCRIPCION" />
    	</html:select>
    </td>
</tr>
<tr>
    <td class="label">Soap Action</td>
    <td class="input">
    	<html:text styleClass="textLargo" tabindex="10" property="values.soapActionWS" maxlength="100"/>    	
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.jndiEJB"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="10" property="values.jndiEJB" maxlength="100"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.localizacionEJB"/></td>
    <td class="input"><bean:message key="tramite.localizacionEJBLocal"/><html:radio property="values.localizacionEJB" value="<%=Character.toString(Procedimiento.EJB_LOCAL)%>"/> <bean:message key="tramite.localizacionEJBRemoto"/><html:radio property="values.localizacionEJB" value="<%=Character.toString(Procedimiento.EJB_REMOTO)%>"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.autenticacionExplicita"/></td>
    <td class="input">
    	<bean:message key="tramite.autenticacionExplicita.authImplicita"/><html:radio property="values.autenticacionEJB" value="<%=Character.toString(Procedimiento.AUTENTICACION_SIN)%>"/> 
    	<bean:message key="tramite.autenticacionExplicita.authExplicitaUserPass"/><html:radio property="values.autenticacionEJB" value="<%=Character.toString(Procedimiento.AUTENTICACION_ESTANDAR)%>"/>
    	<bean:message key="tramite.autenticacionExplicita.authExplicitaOrganismo"/><html:radio property="values.autenticacionEJB" value="<%=Character.toString(Procedimiento.AUTENTICACION_ORGANISMO)%>"/>    	
    </td>    
</tr>
<tr>
    <td class="label">&nbsp;</td>
    <td class="input">
	    User <html:text styleClass="text" tabindex="10" property="userPlain" maxlength="200"/> 
	    Password <html:text styleClass="text" tabindex="10" property="passPlain" maxlength="50"/>
    </td>
</tr>

<logic:notEmpty name="tramiteForm" property="values.ultimoAviso">
	<tr>
		<td class="separador" colspan="2"><bean:message key="tramite.ultimoAviso"/></td>
	</tr>
	<tr>
	    <td class="label"><bean:message key="tramite.fechaAviso"/></td>
	    <td class="input">
		    <bean:write name="tramiteForm" property="values.ultimoAviso" format="dd/MM/yyyy HH:mm"/>
	    </td>
	</tr>		
	<logic:present name="tramiteForm" property="values.errores">
		<tr>
			<td class="label"><bean:message key="tramite.erroresAviso"/></td>
			<td class="input"><input type="button" onclick="mostrarErrores();" value="<bean:message key="tramite.verErrores.value"/>"  class="button"/></td>            
		</tr>
	</logic:present>
</logic:notEmpty>

</logic:equal>
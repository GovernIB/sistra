<%@ page language="java" import="es.caib.bantel.model.Tramite"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.1.min.js"></script>
<script type="text/javascript">
     <!--
     function viewAyuda() {
        var url = '<html:rewrite page="/tramite/ayuda.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     
     
     function mostrarErrores(){
        var url = "<html:rewrite page="/tramite/erroresIntegracion.jsp" />";
        url = url + "?codigoTramiteError=<%=request.getAttribute("codigoTramiteError")%>";
        obrir(url, "Errores", 540, 400);
	}
     // -->
</script>
<tr>
	<td class="separador" colspan="2"><bean:message key="tramite.definicion"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="tramite.identificador"/></td>
    <td class="input"><html:text styleClass="data" tabindex="1" property="values.identificador" maxlength="20" readonly="<%= request.getAttribute( "idReadOnly" ) != null %>" /></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="tramite.descripcion"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="10" property="values.descripcion" maxlength="100"/></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="tramite.avisoBackOffice"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.intervaloInforme"/></td>
    <td class="input">
    	<html:text styleClass="text" tabindex="10" property="values.intervaloInforme" maxlength="2"/>
    	<i><bean:message key="tramite.intervaloInformeNulo"/></i>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.inmediata"/></td>
    <td class="input">Si<html:radio property="values.inmediata" value="S"/> No <html:radio property="values.inmediata" value="N"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.tipoAcceso"/></td>
    <td class="input">EJB<html:radio property="values.tipoAcceso" value="<%=Character.toString(Tramite.ACCESO_EJB)%>"/> Webservice<html:radio property="values.tipoAcceso" value="<%=Character.toString(Tramite.ACCESO_WEBSERVICE)%>"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.url"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="10" property="values.url" maxlength="200"/></td>
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
    <td class="label"><bean:message key="tramite.jndiEJB"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="10" property="values.jndiEJB" maxlength="100"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.localizacionEJB"/></td>
    <td class="input"><bean:message key="tramite.localizacionEJBLocal"/><html:radio property="values.localizacionEJB" value="<%=Character.toString(Tramite.EJB_LOCAL)%>"/> <bean:message key="tramite.localizacionEJBRemoto"/><html:radio property="values.localizacionEJB" value="<%=Character.toString(Tramite.EJB_REMOTO)%>"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.autenticacionExplicita"/></td>
    <td class="input">
    	<bean:message key="tramite.autenticacionExplicita.authImplicita"/><html:radio property="values.autenticacionEJB" value="<%=Character.toString(Tramite.AUTENTICACION_SIN)%>"/> 
    	<bean:message key="tramite.autenticacionExplicita.authExplicitaUserPass"/><html:radio property="values.autenticacionEJB" value="<%=Character.toString(Tramite.AUTENTICACION_ESTANDAR)%>"/>
    	<bean:message key="tramite.autenticacionExplicita.authExplicitaOrganismo"/><html:radio property="values.autenticacionEJB" value="<%=Character.toString(Tramite.AUTENTICACION_ORGANISMO)%>"/>    	
    </td>    
</tr>
<tr>
    <td class="label">&nbsp;</td>
    <td class="input">
	    User <html:text styleClass="text" tabindex="10" property="userPlain" maxlength="200"/> 
	    Password <html:text styleClass="text" tabindex="10" property="passPlain" maxlength="50"/>
    </td>
</tr>
<logic:present name="tramiteForm" property="values.errores">
	<tr>
		<td class="label">&nbsp;</td>
		<td class="input"><input type="button" onclick="mostrarErrores();" value="<bean:message key="tramite.verErrores.value"/>"  class="button"/></td>            
	</tr>
</logic:present>
<!--  
<tr>
	<td class="separador" colspan="2"><bean:message key="tramite.accesoEntradas"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramite.rolAcceso"/></td>
    <td class="input"><html:text styleClass="text" tabindex="10" property="values.rolAcceso" maxlength="100"/></td>
</tr>
-->

<tr>
	<td class="separador" colspan="2"><bean:message key="tramite.exportacion"/></td>
</tr>
<logic:present name="tramiteForm" property="values.archivoFicheroExportacion">
    <tr>
        <td class="label"><bean:message key="tramite.ficheroExportacion" /></td>
        <input type="hidden" name="temp" value="tmp" />
        <td class="input">            
            <html:link page='<%="/back/tramite/mostrarFichero.do"%>' paramId="codigo" paramName="tramiteForm" paramProperty="values.identificador"><bean:write name="tramiteForm" property="values.nombreFicheroExportacion"/></html:link>
            <html:submit property="borrarFicheroExportacion" styleClass="button"><bean:message key="boton.borrar"/></html:submit>
        </td>
    </tr>
    <tr>
        <td class="label"><bean:message key="tramite.nuevoFicheroExportacion"/></td>
</logic:present>
<logic:notPresent name="tramiteForm" property="values.archivoFicheroExportacion">
    <!--   <input type="hidden" name="temp" value="" /> -->
    <tr>
        <td class="label"><bean:message key="tramite.ficheroExportacion"/></td>
</logic:notPresent>
    <td class="input"><html:file property="ficheroExportacion" styleClass="file" tabindex="13"/></td>    
</tr>
<tr>
        <td class="label">&nbsp;</td>
        <td class="input"><input type="button" onclick="viewAyuda();" value="Ayuda"  class="button"/></td>            
</tr>
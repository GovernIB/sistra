<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="urlArbol">
    <html:rewrite page="/arbolUnidades.do"/>
</bean:define>
<bean:define id="urlEditarText">
    <html:rewrite page="/editarText.jsp"/>
</bean:define>
<html:xhtml/>
<script type="text/javascript">
     <!--
     function edit(url) {
       obrir(url, "Edicion", 940, 600);
     }
     // -->
</script>
<script type="text/javascript">
     <!--
     function viewAyudaExpresion() {
        var url = '<html:rewrite page="/tramiteVersion/ayudaPantalla.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     // -->
</script>
    	
<script type="text/javascript">
     <!--
     function mostrarArbolUnidades(url) {
        //var url = '<html:rewrite page="/arbolUnidades.do" />';
        obrir(url, "Arbol", 540, 400);
     }
     // -->
</script>
    	

<html:hidden property="idTramite" />
<tr>
	<td class="separador" colspan="2"><bean:message key="tramiteVersion.datosVersion"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="tramiteVersion.version"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.version" maxlength="2" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramiteVersion.motivo"/></td>
    <td class="input"><html:textarea tabindex="10" property="values.motivo" /></td>
</tr>
<logic:notEmpty name="tramiteVersionForm" property="values.bloqueadoModificacionPor">
<tr>
    <td class="label"><bean:message key="tramiteVersion.bloqueadoModificacionPor"/></td>
    <td class="input"><bean:write name="tramiteVersionForm" property="values.bloqueadoModificacionPor"/></td>
</tr>
</logic:notEmpty>
<tr>
    <td class="label"><bean:message key="tramiteVersion.tagCuadernoCarga"/></td>
    <td class="input"><html:text styleClass="text" tabindex="10" property="values.cuadernoCargaTag" maxlength="100"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramiteVersion.fechaExportacion"/></td>
    <td class="input"><bean:write name="tramiteVersionForm" property="values.fechaExportacion" format="dd/MM/yyyy - HH:mm:ss"/></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="tramiteVersion.parametrosVersion"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramiteVersion.plazo"/></td>
    <td>
    <table>
		<tr>
		    <td nowrap="nowrap" width="1%" align="right"><bean:message key="tramiteVersion.inicioPlazo"/></td>
		    <td class="input" nowrap="nowrap" width="1%"><html:text styleClass="text" tabindex="1" property="inicioPlazo" maxlength="19" /></td>
		    <td nowrap="nowrap" width="1%" align="right"><bean:message key="tramiteVersion.finPlazo"/></td>
		    <td class="input" nowrap="nowrap" width="1%"><html:text styleClass="text" tabindex="1" property="finPlazo" maxlength="19" /></td>
		    <td width="3%"><em>dd/mm/yyyy hh:mi:ss</em></td>
		</tr>
	</table>
	</td>	
</tr>
<tr>
    <td class="labelo"><bean:message key="tramiteVersion.idiomas"/></td>
    <td class="input">
    <logic:iterate id="lenguaje" name="listaLenguajes" type="java.lang.String">
		<bean:write name="lenguaje"/><html:multibox property="idiomas" value="<%=lenguaje%>"/>
	</logic:iterate>    
    </td>
</tr>
<tr>
    <td class="labelo"><bean:message key="tramiteVersion.firmar"/></td>
    <td class="input">
    	<table>
    		<tr>
    			<td nowrap="nowrap" width="1%">Si<html:radio property="values.firmar" value="S"/></td>
    			<td nowrap="nowrap" width="1%">No<html:radio property="values.firmar" value="N"/></td>
    			<td>&nbsp;</td>
    		</tr>
    	</table>
    </td>
</tr>
<tr>
		    <td class="labelo"><bean:message key="tramiteVersion.reducido"/></td>
		    <td>
		    	<table>
		    		<tr>
		    			<td nowrap="nowrap" width="1%">Si<html:radio property="values.reducido" value="S"/></td>
		    			<td nowrap="nowrap" width="1%">No<html:radio property="values.reducido" value="N"/></td>
		    			<td width="10%">&nbsp;</td>
		    			<td><bean:message key="tramiteVersion.redireccionFin"/></td>
		    			<td nowrap="nowrap" width="1%">Si<html:radio property="values.redireccionFin" value="S"/></td>
		    			<td nowrap="nowrap" width="1%">No<html:radio property="values.redireccionFin" value="N"/></td>	    			
		    			<td width="30%">&nbsp;</td>		    		
		    		</tr>		    		
		    	</table>
		    </td> 
</tr>
<tr>
	    <td class="labelo"><bean:message key="tramiteVersion.anonimoDefecto"/></td>
        <td class="input">
    	<table>
    		<tr>
    			<td nowrap="nowrap" width="1%">Si<html:radio property="values.anonimoDefecto" value="S"/></td>
    			<td nowrap="nowrap" width="1%">No<html:radio property="values.anonimoDefecto" value="N"/></td>
    			<td>&nbsp;</td>
    		</tr>
    	</table>
    </td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="tramiteVersion.datosDestino"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="tramiteVersion.destino"/></td>
    <td class="input">
    	<table width="200" border="0">
		  <tr>
		    <td>Registro</td>
		    <td><html:radio property="values.destino" value="R"/>  </td>
		    <td>&nbsp;</td>
		    <td>Bandeja</td>
		    <td><html:radio property="values.destino" value="B"/></td>
		    <td>&nbsp;</td>
		    <td>Consulta</td>
		    <td><html:radio property="values.destino" value="C"/></td>
		    <td>&nbsp;</td>
		    <td>Asistente</td>
		    <td><html:radio property="values.destino" value="A"/></td>
		  </tr>
		  <tr>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>Confirmaci&oacute;n<br />
		      autom&aacute;tica</td>
		    <td><html:checkbox property="values.preenvioConfirmacionAutomatica" value="S"/></td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		  </tr>
		</table>    	
    </td>    
</tr>
<tr>
    <td class="labelo"><bean:message key="tramiteVersion.organoDestino"/></td>
    <td class="input">
    	<html:select property="values.organoDestino">
   			<html:options collection="listaorganosdestino" property="CODIGO" labelProperty="DESCRIPCION" />
    	</html:select>
    </td>
</tr>
<tr>
    <td class="labelo"><bean:message key="tramiteVersion.unidadAdministrativa"/></td>
    <td class="input">
      	<html:select style="width:420px" property="values.unidadAdministrativa">
   			<html:options collection="listaunidadesadministrativas" property="CODIGO" labelProperty="DESCRIPCION" />
    	</html:select>
        <input type="button" value="..."  class = "botonEditar" onclick="mostrarArbolUnidades('<%=urlArbol + "?id=values.unidadAdministrativa" %>');"/>
    </td>
</tr>
<tr>
    <td class="labelo"><bean:message key="tramiteVersion.registroOficina"/></td>
    <td class="input">
    	<html:select property="values.registroOficina">
   			<html:options collection="listaoficinasregistro" property="CODIGO" labelProperty="DESCRIPCION" />
    	</html:select>
    </td>
</tr>
<tr>
    <td class="labelo"><bean:message key="tramiteVersion.registroAsunto"/></td>
    <td class="input">
    	<html:select property="values.registroAsunto">
   			<html:options collection="listatiposasunto" property="CODIGO" labelProperty="DESCRIPCION" />
    	</html:select>
    </td>
</tr>

<tr>
	<td class="separador" colspan="2"><bean:message key="tramiteVersion.parametrosConsulta"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="tramiteVersion.consultaTipo"/></td>
    <td class="input"><table><tr><td>EJB <html:radio property="values.consultaTipoAcceso" value="E"/></td><td>Web Service <html:radio property="values.consultaTipoAcceso" value="W"/></td></tr></table></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramiteVersion.consultaUrl"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="1" property="values.consultaUrl" maxlength="500" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramiteVersion.consultaEJB"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="1" property="values.consultaEJB" maxlength="500" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramiteVersion.consultaLocalizacion"/></td>
    <td class="input"><table><tr><td>Local <html:radio property="values.consultaLocalizacion" value="L"/></td><td>Remoto <html:radio property="values.consultaLocalizacion" value="R"/></td></tr></table></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramiteVersion.consultaAutenticacionExplicita"/></td>
    <td class="input"><table><tr><td>No <html:radio property="values.consultaAuth" value="N"/></td><td><bean:message key="tramiteVersion.consultaAutenticacionExplicita.authExplicitaUserPass"/> <html:radio property="values.consultaAuth" value="S"/><td><bean:message key="tramiteVersion.consultaAutenticacionExplicita.authExplicitaOrganismo"/> <html:radio property="values.consultaAuth" value="C"/></td></tr></table></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramiteVersion.consultaAutenticacionExplicita.usuario"/></td>
    <td class="input"><html:text styleClass="text" tabindex="10" property="userPlain" maxlength="200"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="tramiteVersion.consultaAutenticacionExplicita.password"/></td>
    <td class="input"><html:text styleClass="text" tabindex="10" property="passPlain" maxlength="50"/></td>
</tr>


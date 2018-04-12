<%@ page language="java" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="bean" name="form.beanName" type="java.lang.String"/>
<bean:define id="urlDatoJustificante">
    <html:rewrite page="/back/datoJustificante/lista.do" paramId="idEspecTramiteNivel" paramName="<%= bean %>" paramProperty="values.codigo"/>
</bean:define>
<bean:define id="urlEditarText">
    <html:rewrite page="/editarText.jsp"/>
</bean:define>
<html:xhtml/>
<script type="text/javascript">
     <!--
     function viewAyudaExpresion() {
        var url = '<html:rewrite page="/especificacionesTramite/ayudaPantalla.jsp" />';
        obrir(url, "Edicion", 540, 400);
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
<tr>
	<td class="separador" colspan="2"><bean:message key="especificacionesTramite.separador.parametrosInicio"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="especificacionesTramite.activo"/></td>
    <td class="input">Si<html:radio property="values.activo" value="S"/> No<html:radio property="values.activo" value="N"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="especificacionesTramite.flujoTramitacion"/></td>
    <td class="input">Si<html:radio property="values.flujoTramitacion" value="S"/> No<html:radio property="values.flujoTramitacion" value="N"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.diasPersistencia"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.diasPersistencia" maxlength="3" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.diasPrerregistro"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.diasPrerregistro" maxlength="3" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.validacionInicioScript"/></td>
    <td class="input"><html:textarea tabindex="10" property="validacionInicioScript" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=validacionInicioScript&titulo=especificacionesTramite.validacionInicioScript" %>');"/></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="especificacionesTramite.separador.referenciaRepresentante"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.campoRteNif"/></td>
    <td class="input"><html:textarea tabindex="10" property="campoRteNif" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=campoRteNif&titulo=especificacionesTramite.campoRteNif" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.campoRteNom"/></td>
    <td class="input"><html:textarea tabindex="10" property="campoRteNom" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=campoRteNom&titulo=especificacionesTramite.campoRteNom" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.campoCodigoProvincia"/></td>
    <td class="input"><html:textarea tabindex="10" property="provinciaScript" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=provinciaScript&titulo=especificacionesTramite.campoCodigoProvincia" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.campoCodigoLocalidad"/></td>
    <td class="input"><html:textarea tabindex="10" property="localidadScript" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=localidadScript&titulo=especificacionesTramite.campoCodigoLocalidad" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.campoCodigoPais"/></td>
    <td class="input"><html:textarea tabindex="10" property="paisScript" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=paisScript&titulo=especificacionesTramite.campoCodigoPais" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.datosRte"/></td>
    <td class="input"><html:textarea tabindex="10" property="datosRpte" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=datosRpte&titulo=especificacionesTramite.datosRte" %>');"/></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="especificacionesTramite.separador.referenciaRepresentado"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.campoRdoNif"/></td>
    <td class="input"><html:textarea tabindex="10" property="campoRdoNif" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=campoRdoNif&titulo=especificacionesTramite.campoRdoNif" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.campoRdoNom"/></td>
    <td class="input"><html:textarea tabindex="10" property="campoRdoNom" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=campoRdoNom&titulo=especificacionesTramite.campoRdoNom" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.datosRdo"/></td>
    <td class="input"><html:textarea tabindex="10" property="datosRpdo" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=datosRpdo&titulo=especificacionesTramite.datosRdo" %>');"/></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="especificacionesTramite.separador.destinatarioTramite"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.destinatarioTramite"/></td>
    <td class="input"><html:textarea tabindex="10" property="destinatarioTramite" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=destinatarioTramite&titulo=especificacionesTramite.destinatarioTramite" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.procedimientoDestinoTramite"/></td>
    <td class="input"><html:textarea tabindex="10" property="procedimientoDestinoTramite" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=procedimientoDestinoTramite&titulo=especificacionesTramite.procedimientoDestinoTramite" %>');"/></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="especificacionesTramite.separador.opcionesFin"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.urlFin"/></td>
    <td class="input"><html:textarea tabindex="10" property="urlFin" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=urlFin&titulo=especificacionesTramite.urlFin" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.checkEnvio"/></td>
    <td class="input"><html:textarea tabindex="10" property="checkEnvio" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=checkEnvio&titulo=especificacionesTramite.checkEnvio" %>');"/></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="especificacionesTramite.separador.notificacionTelematica"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="especificacionesTramite.habilitarNotificacionTelematica"/></td>
    <td class="input">
    	<html:select property="values.habilitarNotificacionTelematica">
    		<logic:iterate id="opcion" name="habilitarNotificacionOptions" type="es.caib.sistra.model.Opcion">
    			<html:option value="<%=opcion.getCodigo()%>"  key="<%=opcion.getDescripcion()%>"  />
	    	</logic:iterate>
	    </html:select>
    </td>
</tr>
<!--  Avisos: solo si son obligatorios los avisos para notificaciones -->
<logic:equal name="habilitarAvisos" value="true">
	<tr>
	    <td class="labelo"><bean:message key="especificacionesTramite.habilitarAvisos.permitirSMS"/></td>
	    <td class="input">
	    	<bean:message key="afirmacion"/><html:radio property="values.permitirSMS" value="S"/>
	    	<bean:message key="negacion"/><html:radio property="values.permitirSMS" value="N"/>
	   	</td>
	</tr>
</logic:equal>

<tr>
	<td class="separador" colspan="2"><bean:message key="especificacionesTramite.separador.alertasTramitacion"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="especificacionesTramite.habilitarAlertasTramitacion"/></td>
    <td class="input">
    	<html:select property="values.habilitarAlertasTramitacion">
    		<logic:iterate id="opcion" name="habilitarAlertasTramitacionOptions" type="es.caib.sistra.model.Opcion">
    			<html:option value="<%=opcion.getCodigo()%>"  key="<%=opcion.getDescripcion()%>"  />
	    	</logic:iterate>
	    </html:select>
    </td>
</tr>
<tr>
    <td class="labelo"><bean:message key="especificacionesTramite.habilitarAlertasTramitacion.permitirSMS"/></td>
    <td class="input">
    	<bean:message key="afirmacion"/><html:radio property="values.permitirSMSAlertasTramitacion" value="S"/>
    	<bean:message key="negacion"/><html:radio property="values.permitirSMSAlertasTramitacion" value="N"/>
   	</td>
</tr>

<tr>
    <td class="labelo"><bean:message key="especificacionesTramite.habilitarAlertasTramitacion.finAuto"/></td>
    <td class="input">
    	<bean:message key="afirmacion"/><html:radio property="values.finalizarTramiteAuto" value="S"/>
    	<bean:message key="negacion"/><html:radio property="values.finalizarTramiteAuto" value="N"/>
   	</td>
</tr>

<tr>
	<td class="separador" colspan="2"><bean:message key="especificacionesTramite.separador.datosContacto"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="especificacionesTramite.habilitarAvisos.avisoEmail"/></td>
    <td class="input"><html:textarea tabindex="10" property="avisoEmail" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=avisoEmail&titulo=especificacionesTramite.avisoEmail" %>');"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="especificacionesTramite.habilitarAvisos.avisoSMS"/></td>
    <td class="input"><html:textarea tabindex="10" property="avisoSMS" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=avisoSMS&titulo=especificacionesTramite.avisoSMS" %>');"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="especificacionesTramite.habilitarAlertasTramitacion.verificarMovil"/></td>
    <td class="input">
    	<html:select property="values.verificarMovil">
    		<logic:iterate id="opcion" name="verificarMovilOptions" type="es.caib.sistra.model.Opcion">
    			<html:option value="<%=opcion.getCodigo()%>"  key="<%=opcion.getDescripcion()%>"  />
	    	</logic:iterate>
	    </html:select>
    </td>
</tr>

<logic:present name="<%=bean%>" property="values.codigo">
	<bean:define id="codigo" name="<%= bean %>" property="values.codigo"/>
<%
	String parametroBack = "tramiteNivelForm".equals( bean ) ? "idTramiteNivel" : "codigo";
	codigo = "tramiteNivelForm".equals( bean ) ? request.getAttribute( "idTramiteNivel" ) : codigo;
	codigo = ( codigo == null ? request.getParameter( "codigo" ) : codigo );
%>
<tr>
	<td class="separador" colspan="2"><bean:message key="especificacionesTramite.separador.datosJustificante"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.separador.datosJustificante.ocultarNifNombre"/></td>
    <td class="input">
    	<html:select property="values.ocultarNifNombreJustif">
    		<logic:iterate id="opcion" name="personalizacionJustificanteOptions" type="es.caib.sistra.model.Opcion">
    			<html:option value="<%=opcion.getCodigo()%>"  key="<%=opcion.getDescripcion()%>"  />
	    	</logic:iterate>
	    </html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.separador.datosJustificante.ocultarClaveTramitacion"/></td>
    <td class="input">
    	<html:select property="values.ocultarClaveTramitacionJustif">
    		<logic:iterate id="opcion" name="personalizacionJustificanteOptions" type="es.caib.sistra.model.Opcion">
    			<html:option value="<%=opcion.getCodigo()%>"  key="<%=opcion.getDescripcion()%>"  />
	    	</logic:iterate>
	    </html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.separador.datosJustificante.datosSolicitud"/></td>
    <td class="input">
    <button class="buttond" type="button" onclick="forward('<%=urlDatoJustificante + "&" + parametroBack + "=" + codigo %>')">
        <bean:message key="boton.datoJustificante" />
    </button>
    </td>
</tr>
</logic:present>
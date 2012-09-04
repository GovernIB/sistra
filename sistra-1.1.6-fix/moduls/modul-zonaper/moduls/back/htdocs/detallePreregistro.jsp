<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.xml.datospropios.factoria.ConstantesDatosPropiosXML" %>
<%@ page import="es.caib.zonaper.back.Constants"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="messageAyuda" value="ayuda.datosTramite" />
<bean:define id="urlAyuda" type="java.lang.String">
	<html:rewrite page="/ayuda.do" paramId="<%= Constants.MESSAGE_KEY %>" paramName="messageAyuda"/>
</bean:define>
<script type="text/javascript">
<!--
function marcarDocumentos( chequeado)
{
	var documentos = document.forms[0].documentoChecked;
	if ( documentos != null )
	{
		if ( documentos.length != null && documentos.length > 1 )
		{
			for ( var i = 0; i < documentos.length; i++ )
			{
				documentos[i].checked = chequeado;
			}
		}		
	}
}

function validaFormulario( formulario )
{
	
	/* -- SE HA DECIDIDO NO CHEQUEAR DOCS
	var documentos = formulario.documentoChecked;
	if ( documentos != null )
	{
		if ( documentos.length != null && documentos.length > 1 )
		{
			for ( var i = 0; i < documentos.length; i++ )
			{
				if ( !documentos[i].checked )
				{
					alert( "<bean:message key="detalle.mensajeValidacionChequeo" />" );
					return; 
				}
			}
		}
		else
		{
			if ( !documentos.checked )
			{
					alert( "<bean:message key="detalle.mensajeValidacionChequeo" />" );
					return;
			}
		}		
	}
	*/
	
	
	/*
	var fecha = formulario.fechaRegistro.value;
	var expresionFecha = "^\\d{1,2}\\/\\d{1,2}\\/\\d{4}\\s\\d{1,2}\\:\\d{1,2}$";
	var regexp = new RegExp( expresionFecha );
	if ( fecha != "" && !regexp.test( fecha ) )
	{
		alert( "<bean:message key="detalle.mensajeValidacionFecha" />" );
		formulario.fechaRegistro.focus();
		formulario.fechaRegistro.select();
		return;
	}
	*/
	
	formulario.submit();

}

function volver( formulario )
{
	formulario.action = '<html:rewrite page="/busquedaPreregistro.do"/>';
	formulario.submit();
}

-->
</script>
		<bean:define id="urlMostrarDocumento">
			<html:rewrite page="/mostrarDocumento.do" paramId="codigoEntrada" paramName="preregistro" paramProperty="codigo" />
		</bean:define>
		
		<bean:define id="urlMostrarJustificante">
			<html:rewrite page="/mostrarJustificante.do" />
		</bean:define>
		
			<h2><bean:message key="detalle.titulo" /></h2>
			<p align="right"><html:link href="#" onclick="<%= \"javascript:obrir('\" + urlAyuda + \"', 'Edicion', 540, 400);\"%>"><img src="imgs/icones/ico_ayuda.gif" border="0"/><bean:message key="ayuda.enlace" /></html:link></p>
			<h3><bean:message key="detalle.datosTramite" /></h3>
			<p><strong><bean:message key="busqueda.numeroEntrada" /></strong>: <bean:write name="preregistro" property="numeroPreregistro" /></p>
			<p><strong><bean:message key="detalle.asunto" /></strong>: <bean:write name="datosAsunto" property="extractoAsunto" /></p>
			<p><strong><bean:write name="tipoIdentificacion" /> </strong>: <bean:write name="datosInteresado" property="numeroIdentificacion" /></p>
			<p><strong><bean:message key="detalle.nombre" /></strong>: <bean:write name="datosInteresado" property="identificacionInteresado" /></p>
			<html:form action="confirmarPreregistro">
			<h3><bean:message key="detalle.documentosAportar" /></h3>
			<!-- SE HA DECIDIDO NO CHEQUEAR DOCS
			<html:link href="javascript:marcarDocumentos(true)">[ <bean:message key="detalle.marcar" /> ]</html:link>&nbsp;&nbsp;		 
			<html:link href="javascript:marcarDocumentos(false)">[ <bean:message key="detalle.desmarcar" /> ]</html:link>			
			-->
			<table cellpadding="8" cellspacing="0" id="tabla_ttNotificacions">
				<html:hidden property="codigo" name="preregistro"/>
				<html:hidden property="oficina" name="detallePreregistroForm"/>
				<html:hidden property="municipioBaleares" name="detallePreregistroForm"/>
				<html:hidden property="municipioFuera" name="detallePreregistroForm"/>

				<tr>
				<!-- SE HA DECIDIDO NO CHEQUEAR DOCS
					<th><bean:message key="detalle.chequeado" /></th>
				-->
					<th><bean:message key="detalle.documento" /></th>
					<th><bean:message key="detalle.accionARealizar" /></th>
				</tr>
				
				
				
				
				<!--  Enlace a justificante -->
				<logic:equal name="mostrarJustificante" value="S">
					<tr>
						<td><bean:message key="detalle.justificante" /></td>
						<td>
							<html:link href="<%= urlMostrarJustificante %>" paramId="codigo" paramName="preregistro" paramProperty="codigo"><bean:message key="detalle.justificante" /></html:link>
						</td>
					</tr>	
				</logic:equal>				
				
				
				<logic:iterate id="documento" name="preregistro" property="documentos" type="es.caib.zonaper.model.DocumentoEntradaPreregistro">			
					<logic:equal name="documento" property="presencial" value="S">
					<tr>					
						<td><bean:write name="documento" property="descripcion" /></td>
						<td>
							<!-- Pagos: aportar comprobante de pago -->
							<logic:equal name="documento" property="tipoDocumento" value="<%= Character.toString( ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_PAGO ) %>">
								<bean:message key="detalle.chequeo.pago"/>
							</logic:equal>
							<!-- Formularios: imprimir y firmar -->
							<logic:equal name="documento" property="tipoDocumento" value="<%= Character.toString( ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO_JUSTIFICANTE ) %>">
								<html:link href="<%= urlMostrarDocumento %>" paramId="codigo" paramName="documento" paramProperty="codigo"><bean:message key="detalle.chequeo.formulario" /></html:link>
							</logic:equal>
							<logic:equal name="documento" property="tipoDocumento" value="<%= Character.toString( ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO ) %>">
								<html:link href="<%= urlMostrarDocumento %>" paramId="codigo" paramName="documento" paramProperty="codigo"><bean:message key="detalle.chequeo.formulario" /></html:link>
							</logic:equal>
							<!-- Anexos:  -->
							<logic:equal name="documento" property="tipoDocumento" value="<%= Character.toString( ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO ) %>">
								<%  
								String keyMessage = "detalle.chequeo.anexo" ;
								keyMessage+= (documento.getFotocopia()=='S') ? ".fotocopia" : "";
								keyMessage+= (documento.getCompulsarDocumento()=='S') ? ".compulsar" : "";												
								keyMessage+= (documento.getFotocopia()=='N' && documento.getCompulsarDocumento()=='N') ? ".presencial" : "";											
								%>
								<bean:message key="<%=keyMessage%>"/>
							</logic:equal>
						</td>
					</tr>
					</logic:equal>		
				</logic:iterate>
				
				
				
				
				
				
			</html:form>
			</table>
			<p align="center"><input name="confirmarBoton" id="confirmarBoton" type="button" value="<bean:message key="detalle.confirmar" />" onclick="javascript:validaFormulario ( document.forms[0] )"/></p>

			<p class="tornarArrere"><strong><a href="#" onclick="javascript:volver( document.forms[0] );"><bean:message key="mensajes.enlaceVolver" /></a></strong></p>
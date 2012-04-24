<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="es.caib.zonaper.modelInterfaz.ConstantesZPE"%>
<%@ page import="es.caib.sistra.model.ConstantesSTR"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>
<bean:define id="referenciaPortal"  type="java.lang.String">
	<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property='<%="referenciaPortal("+ lang +")"%>'/>
</bean:define>
<bean:define id="ID_INSTANCIA" name="ID_INSTANCIA" type="java.lang.String"/>
<bean:define id="urlSeleccionNotificacionTelematica">
        <html:rewrite page="/protected/seleccionNotificacionTelematica.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<bean:define id="entornoDesarrollo" value="<%=Boolean.toString(es.caib.sistra.front.util.Util.esEntornoDesarrollo())%>"/>

<!--  Flujo de tramite -->
<logic:present name="pasarFlujoTramitacion">
</logic:present>

<!--  Pendiente presentacion delegacion -->
<logic:present name="pendienteDelegacionPresentacion">
</logic:present>


<!--  Seleccion  notificacion -->
<logic:present name="confirmarSeleccionNotificacionTelematica">
	<div class="alerta">
		<p><bean:message key="finalizacion.notificacionTelematica.confirmacion" arg0="<%=referenciaPortal%>"/></p>
		<p align="center">
			<html:link href="<%=urlSeleccionNotificacionTelematica + "&seleccion=S"%>">
				<bean:message key="finalizacion.notificacionTelematica.habilitar"/>
			</html:link>
			 &nbsp;&nbsp;&nbsp;||&nbsp;&nbsp;&nbsp; 
			<html:link href="<%=urlSeleccionNotificacionTelematica + "&seleccion=N"%>">
				<bean:message key="finalizacion.notificacionTelematica.deshabilitar"/>
			</html:link>
		</p>
	</div>
</logic:present>

<!--  Registro solicitud -->
<logic:present name="permitirRegistrar">

<bean:define id="asiento" name="asiento" type="java.lang.String"/>
<bean:define id="instrucciones" name="instrucciones"/> 
<bean:define id="mostrarFirmaDigital" name="<%=es.caib.sistra.front.Constants.MOSTRAR_FIRMA_DIGITAL%>" type="java.lang.String"/>
<bean:define id="presencial" name="presencial" type="java.lang.String"/>
<bean:define id="urlMostrarDocumento">
        <html:rewrite page="/protected/mostrarDocumento.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<bean:define id="urlMostrarFormularioDebug">
      <html:rewrite page="/protected/mostrarFormularioDebug.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>

<script type="text/javascript">
<!--
	var mensajeEnviando = '<bean:message key="registro.mensajeRegistrar"/>';
	
	function mostrarFormulario(identificador,instancia,presencial,presentar){
	
		if (presencial == 'true' && presentar == 'true' ){
			alert("<bean:message key="registro.mensajeFormularioNoValido"/>");
		}
		
		url = "<%=urlMostrarDocumento%>&identificador=" + identificador + "&instancia=" + instancia;
		document.location = url;
	}
	
	<logic:equal name="entornoDesarrollo" value="true">
	function mostrarFormularioDebug(identificador,instancia){
		url = "<%=urlMostrarFormularioDebug%>&identificador=" + identificador + "&instancia=" + instancia;
		document.location = url;
	}
	</logic:equal>
//-->
</script>

	<h2><bean:message name="tituloKey"/></h2>
	<p><bean:message name="instruccionesKey"/></p>
	
	<!--  Mensaje de alerta (si no es de tipo consulta)-->
	<logic:notEqual name="tramite" property="consultar" value="true">		
		<p class="alerta">
			<bean:message name="importanteKey" />
			<!--  Fecha limite de entrega cuando es presencial --> 
			<logic:equal name="presencial" value="true">
				<!-- mensaje personalizado -->
				<logic:notEmpty name="tramite" property="mensajeFechaLimiteEntregaPresencial">
					<strong><bean:write name="tramite" property="mensajeFechaLimiteEntregaPresencial"/></strong>
				</logic:notEmpty>
				<!--  mensaje x defecto  -->				
				<logic:empty name="tramite" property="mensajeFechaLimiteEntregaPresencial">
					<strong><bean:message key="registro.importante.fechaLimiteEntrega" arg0="<%= ( String ) request.getAttribute( "fechaTopeEntrega" ) %>"/></strong>
				</logic:empty>								
			</logic:equal>		
		</p>
	</logic:notEqual>
	
	<br/>

	<!--  Resumen pasos realizados -->
	<h3><bean:message key="finalizacion.resumen"/></h3>
	<!--  Formularios -->
	<logic:notEmpty name="tramite" property="formularios">			
			<p><bean:message key="finalizacion.resumen.formulario.texto1"/> <strong><bean:message key="finalizacion.resumen.formulario.texto2"/></strong>:</p>
			<div id="resumenDocumentos">
			<logic:iterate id="formulario" name="tramite" property="formularios" type="es.caib.sistra.model.DocumentoFront">			
				<logic:notEqual name="formulario" property="obligatorio" value='D'>
					<logic:equal name="formulario" property="estado" value="S">
						<div class="iconos">
							<img src="imgs/tramitacion/iconos/ico_form.gif"/>
						</div>

						<p>
							<span style="position: relative;">	
											
						    <% 
						    	String urlForm = "javascript:mostrarFormulario('" + formulario.getIdentificador()+ "','" + formulario.getInstancia() + "','" + presencial + "','" + formulario.isPrerregistro() + "');";						    	
						    %>													
							<html:link href="<%=urlForm%>">
								<bean:write name="formulario" property="descripcion" />
							</html:link>

							<logic:equal name="entornoDesarrollo" value="true">
							 <% 
						    	String urlFormDebug = "javascript:mostrarFormularioDebug('" + formulario.getIdentificador()+ "','" + formulario.getInstancia()+ "');";						    	
						    %>	
								<html:link styleClass="pequenyo" href="<%=urlFormDebug%>">
									&nbsp;[XML]
								</html:link>
							</logic:equal>
							
							<!--  Información firmante -->
							<logic:equal name="formulario" property="firmar" value="true">
								<span class="detalleDoc"> 
									<img src="imgs/tramitacion/iconos/doc_firmar.gif"/>		
									<logic:empty name="formulario" property="firmante">
										<bean:message key="finalizacion.resumen.documentos.firmadopor.noComprobarFirmante"/>							
									</logic:empty>
									<logic:notEmpty name="formulario" property="firmante">
										<logic:match name="formulario" property="firmante" value="#">
											<bean:message key="finalizacion.resumen.documentos.firmadospor" arg0="<%=es.caib.util.StringUtil.replace(formulario.getFirmante(),"#"," - ")%>"/>
										</logic:match>	
										<logic:notMatch name="formulario" property="firmante" value="#">
										<bean:message key="finalizacion.resumen.documentos.firmadopor" arg0="<%=formulario.getFirmante()%>"/>							
										</logic:notMatch>											
									</logic:notEmpty>
								</span>
							</logic:equal>
							</span>
						</p>
					</logic:equal>
				</logic:notEqual>	
			</logic:iterate>	
			</div>
	</logic:notEmpty>
	
	<!--  Anexos -->
	<logic:notEmpty name="tramite" property="anexos">			
		<logic:equal name="existenAnexosTelematicos" value="S">		
			<p><bean:message key="finalizacion.resumen.documentos.texto1"/> <strong><bean:message key="finalizacion.resumen.documentos.texto2"/></strong>:</p>
			<div id="resumenDocumentos">
			<logic:iterate id="anexo" name="tramite" property="anexos" type="es.caib.sistra.model.DocumentoFront">
				<logic:notEqual name="anexo" property="obligatorio" value="D">
					<logic:equal name="anexo" property="estado" value="S">
						<logic:equal name="anexo" property="anexoPresentarTelematicamente" value="true">
													
							<div class="iconos">
								<img src="imgs/tramitacion/iconos/ico_anexado.gif"/>
							</div>
	
							<p>
								<span style="position: relative;">
								
								<html:link href="<%= urlMostrarDocumento + "&identificador=" + anexo.getIdentificador() + "&instancia=" + anexo.getInstancia() %>">
									<logic:equal name="anexo" property="anexoGenerico" value="false"> 
										<bean:write name="anexo" property="descripcion" />
									</logic:equal>
									<logic:equal name="anexo" property="anexoGenerico" value="true">
										<bean:write name="anexo" property="anexoGenericoDescripcion" />
									</logic:equal>
								</html:link>
								
								<!--  Información firmante -->
								<logic:equal name="anexo" property="firmar" value="true">
									<span class="detalleDoc"> 
										<img src="imgs/tramitacion/iconos/doc_firmar.gif"/>									
										<logic:empty name="anexo" property="firmante">
											<bean:message key="finalizacion.resumen.documentos.firmadopor.noComprobarFirmante" arg0="<%=anexo.getFirmante()%>"/>							
										</logic:empty>
										<logic:notEmpty name="anexo" property="firmante">
											<logic:match name="anexo" property="firmante" value="#">
												<bean:message key="finalizacion.resumen.documentos.firmadospor" arg0="<%=es.caib.util.StringUtil.replace(anexo.getFirmante(),"#"," - ")%>"/>
											</logic:match>	
											<logic:notMatch name="anexo" property="firmante" value="#">
											<bean:message key="finalizacion.resumen.documentos.firmadopor" arg0="<%=anexo.getFirmante()%>"/>							
											</logic:notMatch>											
										</logic:notEmpty>
									</span>
								</logic:equal>
								
								</span>
							</p>
						</logic:equal>
					</logic:equal>
				</logic:notEqual>
			</logic:iterate>				
		</div>	
		</logic:equal>
	</logic:notEmpty>
		
	<!--  Pagos -->
	<logic:notEmpty name="tramite" property="pagos">
			<logic:equal name="existenPagos" value="S">
				<p><bean:message key="finalizacion.resumen.pagos.texto1"/> <strong><bean:message key="finalizacion.resumen.pagos.texto2"/></strong>:</p>
				<div id="resumenDocumentos">
					<logic:iterate id="pago" name="tramite" property="pagos" type="es.caib.sistra.model.DocumentoFront">
						<logic:equal name="pago" property="estado" value="S">
							<div class="iconos">
								<img src="imgs/tramitacion/iconos/ico_pagado.gif"/>
							</div>
							<p>
								<span style="position: relative;">
									<html:link href="<%= urlMostrarDocumento + "&identificador=" + pago.getIdentificador() + "&instancia=" + pago.getInstancia() %>"><bean:write name="pago" property="descripcion" /></html:link>	
								</span>
							</p>
						</logic:equal>
					</logic:iterate>					
				</div>
			</logic:equal>			
	</logic:notEmpty>
	
			<!-- DOCUMENTACION APORTAR -->			
			<logic:present name="instrucciones" property="documentosEntregar">						
				<bean:define id="documentosEntregar" name="instrucciones" property="documentosEntregar"/>				
				<logic:notEmpty name="documentosEntregar" property="documento">
				<!-- documentación a aportar -->
					<h3><bean:message key="finalizacion.general.documentacionAAportar"/></h3>
					<p><bean:message key="finalizacion.general.documentacionAAportar.instrucciones"/></p>
					<table cellpadding="0" cellspacing="0" id="tablaDocAportar">
					<tr>
						<th><bean:message key="finalizacion.general.documentacionAAportar.documento"/></th>
						<th><bean:message key="finalizacion.general.documentacionAAportar.accion"/></th>
					</tr>
				<logic:iterate id="documento" name="documentosEntregar" property="documento">
					<tr>
						<td class="doc"><bean:write name="documento" property="titulo" /></td>
					
					<logic:equal name="documento" property="tipo" value="J">					
						<td><bean:message key="finalizacion.instrucciones.justificante.firmar"/></td>
					</logic:equal>
					
					<logic:equal name="documento" property="tipo" value="G">					
						<td><bean:message key="finalizacion.instrucciones.formularioJustificante.firmar"/></td>
					</logic:equal>
					
					<logic:equal name="documento" property="tipo" value="F">
						<td><bean:message key="finalizacion.instrucciones.formulario.firmar"/></td>
					</logic:equal>
					
					<logic:equal name="documento" property="tipo" value="A">																		
						<%
							String messageKey = "finalizacion.instrucciones.anexo";
						%>
							<logic:equal name="documento" property="compulsar" value="true">
						<%
							messageKey += ".compulsar";
						%>
							</logic:equal>
							<logic:equal name="documento" property="fotocopia" value="true">
						<%
							messageKey += ".fotocopia";
						%>
							</logic:equal>				
							<logic:equal name="documento" property="fotocopia" value="false">
								<logic:equal name="documento" property="compulsar" value="false">
								<%
									messageKey += ".presencial";
								%>
									</logic:equal>	
							</logic:equal>								
						<td><bean:message key="<%= messageKey %>"/></td>
					</logic:equal>
					<logic:equal name="documento" property="tipo" value="P">
						<td><bean:message key="finalizacion.instrucciones.pago"/></td>
					</logic:equal>
					</tr>
				</logic:iterate>
					</table>
					
			</logic:notEmpty>
		</logic:present>
		<!-- END DOCUMENTACION APORTAR -->
		
		<!--  Opcion de notificacion telematica -->
		<logic:present name="seleccionNotificacionTelematica">
			<br/>
			<h3><bean:message key="finalizacion.notificacionTelematica.titulo"/></h3>
			<logic:equal name="seleccionNotificacionTelematica" value="true">
				<logic:equal name="tramite" property="habilitarNotificacionTelematica" value="<%=ConstantesSTR.NOTIFICACIONTELEMATICA_OBLIGATORIA%>">
					<p><bean:message key="finalizacion.notificacionTelematica.textoObligatoria" arg0="<%=referenciaPortal%>"/></p>
				</logic:equal>
				<logic:equal name="tramite" property="habilitarNotificacionTelematica" value="<%=ConstantesSTR.NOTIFICACIONTELEMATICA_PERMITIDA%>">			
					<p>
						<bean:message key="finalizacion.notificacionTelematica.textoHabilitada" arg0="<%=referenciaPortal%>"/>
					</p>
					<p class="notaPie">
						<bean:message key="finalizacion.notificacionTelematica.cambioDecision"/> <html:link href="<%=urlSeleccionNotificacionTelematica + "&seleccion=N"%>"><bean:message key="finalizacion.notificacionTelematica.deshabilitar"/></html:link>
					</p>					
				</logic:equal>
			</logic:equal>
			<logic:equal name="seleccionNotificacionTelematica" value="false">
				<p>
					<bean:message key="finalizacion.notificacionTelematica.textoDeshabilitada" arg0="<%=referenciaPortal%>"/>
				</p>
				<p class="notaPie">
					<bean:message key="finalizacion.notificacionTelematica.cambioDecision"/>&nbsp;<html:link href="<%=urlSeleccionNotificacionTelematica + "&seleccion=S"%>"><bean:message key="finalizacion.notificacionTelematica.habilitar"/></html:link>
				</p>					
			</logic:equal>			
		</logic:present>
			
		<!--  Firma de envio -->
		<!--  MOSTRAMOS FIRMA: CUANDO HAYA QUE FIRMAR Y SE HAYA AUTENTICADO CON CERTIFICADO -->
		<logic:equal name="mostrarFirmaDigital" value="S">
			<br/>
			<h3><bean:message key="finalizacion.general.firmaSolicitud"/></h3>
			<logic:present name="representante">		
					<bean:define id="representante" name="representante" type="es.caib.xml.registro.factoria.impl.DatosInteresado"/>
					<p>
						<logic:equal name="tramite" property="datosSesion.perfilAcceso" value="<%=ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO%>">
							<bean:message key="finalizacion.general.firmaSolicitud.delegado.instrucciones" arg0="<%=representante.getNumeroIdentificacion()%>"/>							
						</logic:equal>
						<logic:equal name="tramite" property="datosSesion.perfilAcceso" value="<%=ConstantesZPE.DELEGACION_PERFIL_ACCESO_CIUDADANO%>">
						<bean:message key="finalizacion.general.firmaSolicitud.instrucciones" arg0="<%=representante.getNumeroIdentificacion()%>"/>
						</logic:equal>
						
					</p>
			</logic:present>	
			<!--  MOSTRAMOS APPLET FIRMA SEGUN IMPLEMENTACION -->
			<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>" 
						value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
				<p><bean:message key="registro.aFirma.instrucciones"/></p>				
			</logic:equal>
			<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
						 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									
				<p><bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo" /></p>
				<p class="formBotonera"><input type="button" value="<bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo.boton" />" title="<bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo.boton" />" onclick="cargarCertificado();" /></p>
				<p><bean:message key="finalizacion.general.firmaSolicitud.certificadosDisponibles"/></p>
				<p>	
					<jsp:include page="/firma/caib/applet.jsp" flush="false"/>		
					<form name="formFirma" action="">
						<label for="PIN"><bean:message key="finalizacion.general.firmaSolicitud.PINCertificado"/></label> <input name="PIN" id="PIN" type="password" class="txt" />				
					</form>
				</p>	
			</logic:equal>			
		</logic:equal>
		
		
		<!--  Envio del tramite -->	
		<html:form action="/protected/registrarTramite">
		<p class="importante"><input name="registrarTramitacionBoton" id="registrarTramitacionBoton" type="button" value="<bean:message name='botonKey' />" onclick="javascript:enviar(this.form);"/></p>
		<html:hidden property="ID_INSTANCIA" value="<%= ID_INSTANCIA %>"/>
		<html:hidden property="asiento" value="<%= asiento %>"/>
		<html:hidden property="firma" value=""/>
		</html:form>
		<div class="sep"></div>
		<!-- capa accediendo formularios -->
		<div id="capaInfoFondo"></div>
		<div id="capaInfoForms"></div>
			
		<!-- SCRIPT DE ENVIO -->
		<script type="text/javascript">
		<!-- 	
		<logic:equal name="mostrarFirmaDigital" value="S">

			<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
						 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									

					// Content type a firmar
					var contentType = '<%= es.caib.util.FirmaUtil.obtenerContentTypeCAIB(es.caib.util.FirmaUtil.CAIB_REGISTRE_ENTRADA_SIGNATURE_CONTENT_TYPE) %>';
					
					function firmarCAIB(formulario){
					 // Realizamos firma
						var firma = '';	
						var pin = document.formFirma.PIN.value;
						
						var applet = whichApplet ();
						applet.setPassword( pin );
					
						firma = applet.firmarCadena( applet.base64ToCadena ( formulario.asiento.value ), contentType);					
						if (firma == null || firma == ''){
							alert(applet.getLastError());
							return false;
						}												
						formulario.firma.value = firma;
						return true;
					}
			</logic:equal>			
			
			<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
						 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">									 
						 
					function prepararEntornoFirma(){
						cargarAppletFirma(sistra_ClienteaFirma_buildVersion);
					}
						 
					function firmarAFirma(formulario){
					
						if (clienteFirma == undefined) { 
				          alert("No se ha podido instalar el entorno de firma");
				          return false;
			        	}
					
						var asientoB64 = b64UrlSafeToB64(formulario.asiento.value);
					
						clienteFirma.initialize();
						clienteFirma.setShowErrors(false);
						clienteFirma.setSignatureAlgorithm(sistra_ClienteaFirma_SignatureAlgorithm);
						clienteFirma.setSignatureMode(sistra_ClienteaFirma_SignatureMode);
						clienteFirma.setSignatureFormat(sistra_ClienteaFirma_SignatureFormat);
						clienteFirma.setData(asientoB64);
						
						clienteFirma.sign();
						
						if(clienteFirma.isError()){
							error = 'Error: '+clienteFirma.getErrorMessage();
							alert(error);
							return false;
						}else{	
						     firma = clienteFirma.getSignatureBase64Encoded();
						    firma = b64ToB64UrlSafe(firma);
						     formulario.firma.value = firma;
						     return true;
						}
					}
					
					prepararEntornoFirma();
			</logic:equal>			
		
			function enviar(formulario)
			{
				// Firmamos
				<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
							 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
						if (!firmarAFirma(formulario)) return;
				</logic:equal>
				<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
							 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									
						if (!firmarCAIB(formulario)) return;					
				</logic:equal>
								
				// Enviamos
				formulario.submit();				
				
				// Mostramos capa de envio
				accediendoEnviando(mensajeEnviando);
			}
		</logic:equal>		
		
		<logic:equal name="mostrarFirmaDigital" value="N">
			function enviar(formulario){
				// Enviamos
				formulario.submit();			
				// Mostramos capa de envio
				accediendoEnviando(mensajeEnviando);				
			}
			
		</logic:equal>		
		 -->
		</script>
		
		<%--  Si se indica la url de acceso a un documento, abrimos en nueva ventana --%>	
		<logic:present name="urlAcceso">
			<script type="text/javascript">
				// Abrimos ventana plataforma pagos
				window.open("<bean:write name="urlAcceso" filter="false"/>");
			</script>
		</logic:present>
</logic:present>
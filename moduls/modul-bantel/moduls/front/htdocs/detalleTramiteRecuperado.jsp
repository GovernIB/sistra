<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.bantel.modelInterfaz.ConstantesBTE"%>
<%@ page import="es.caib.redose.modelInterfaz.ConstantesRDS"%>
<%@ page import="es.caib.xml.registro.factoria.ConstantesAsientoXML" %>
<%@ page import="es.caib.xml.datospropios.factoria.ConstantesDatosPropiosXML" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript">
     <!--
     function edit(url) {
       obrir(url, "Edicion", 940, 600);
     }
     // Obrir un pop up
	function obrir(url, name, x, y) {
	   window.open(url, name, 'scrollbars=yes, resizable=yes, width=' + x + ',height=' + y);
	}	
     // -->
</script>
<script type="text/javascript">
	function volver(identificadorExp,unidadAdm,claveExp){
		document.location='<html:rewrite page="/recuperarExpediente.do?identificadorExp='+identificadorExp+'&unidadAdm='+unidadAdm+'&claveExp='+claveExp+'" />';		
	}
</script>
<bean:define id="expediente" name="expediente" type="es.caib.zonaper.modelInterfaz.ExpedientePAD" />
<bean:define id="urlEditarText">
    <html:rewrite page="/editarText.jsp"/>
</bean:define>
<bean:define id="urlCambioEstado" >
	<html:rewrite page="/cambiarEstadoTramite.do" paramId="codigo" paramName="detalleTramiteForm" paramProperty="codigo"/>
</bean:define>
<bean:define id="estadoOld" type="java.lang.String" >
<bean:write name="tramite" property="procesada"/>
</bean:define>
<bean:define id="documentosEstructurados" name="documentosEstructurados" type="java.util.Set"/>


			<h2><bean:message key="detalleTramite.datosTramite"/></h2>

			<div class="atencio">
					<!--  Icono procesado / no procesado -->
					<p>
					<logic:equal name="tramite" property="procesada" value='N'>
						<img src="imgs/icones/form_procesado_no.gif" title="No procesado"/> <strong><bean:message key="detalleTramite.datosTramite.estado"/>:</strong> <bean:message key="detalleTramite.datosTramite.noProcesado"/>
					</logic:equal>
					<logic:equal name="tramite" property="procesada" value='S'>
						<img src="imgs/icones/form_procesado_si.gif" title="Procesado"/> <strong><bean:message key="detalleTramite.datosTramite.estado"/>:</strong> <bean:message key="detalleTramite.datosTramite.procesado"/> 
					</logic:equal>
					<logic:equal name="tramite" property="procesada" value='X'>
						<img src="imgs/icones/form_procesado_error.gif" title="Con error"/> <strong><bean:message key="detalleTramite.datosTramite.estado"/>:</strong> <bean:message key="detalleTramite.datosTramite.procesadoError"/>
					</logic:equal>		
					<!--  Fecha de procesamiento -->
					<logic:notEmpty name="tramite" property="fechaProcesamiento">
						&nbsp;[<bean:write name="tramite" property="fechaProcesamiento" format="dd/MM/yyyy-HH:mm:ss"/>]
					</logic:notEmpty>					
					</p>
					<p>
					<!--  Icono envio / registro / preregistro / preenvio -->
					<logic:equal name="tramite" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_ENVIO)%>">
						<logic:equal name="tramite" property="firmada" value="S">
							<img src="imgs/icones/envio_firmado.gif" title="<bean:message key="detalleTramite.datosTramite.envio.firmado"/>"/> <strong><bean:message key="detalleTramite.datosTramite.tipo"/>:</strong> <bean:message key="detalleTramite.datosTramite.envio.firmado"/>
						</logic:equal>
						<logic:equal name="tramite" property="firmada" value="N">
							<img src="imgs/icones/envio.gif" title="<bean:message key="detalleTramite.datosTramite.envio"/>"/> <strong><bean:message key="detalleTramite.datosTramite.tipo"/>:</strong> <bean:message key="detalleTramite.datosTramite.envio"/>
						</logic:equal>						
					</logic:equal>
					<logic:equal name="tramite" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA)%>">
						<logic:equal name="tramite" property="firmada" value="S">
							<img src="imgs/icones/registro_firmado.gif" title="<bean:message key="detalleTramite.datosTramite.registro.firmado"/>"/> <strong><bean:message key="detalleTramite.datosTramite.tipo"/>:</strong> <bean:message key="detalleTramite.datosTramite.registro.firmado"/>
						</logic:equal>
						<logic:equal name="tramite" property="firmada" value="N">
							<img src="imgs/icones/registro.gif" title="<bean:message key="detalleTramite.datosTramite.registro"/>"/> <strong><bean:message key="detalleTramite.datosTramite.tipo"/>:</strong> <bean:message key="detalleTramite.datosTramite.registro"/>
						</logic:equal>
					</logic:equal>
					<logic:equal name="tramite" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_PREENVIO)%>">
						<img src="imgs/icones/preenvio.gif" title="Preenvio confirmado"/> <strong><bean:message key="detalleTramite.datosTramite.tipo"/>:</strong> <bean:message key="detalleTramite.datosTramite.preenvioConfirmado"/>
					</logic:equal>
					<logic:equal name="tramite" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_PREREGISTRO)%>">
						<img src="imgs/icones/preregistro.gif" title="Preregistro confirmado"/> <strong><bean:message key="detalleTramite.datosTramite.tipo"/>:</strong> <bean:message key="detalleTramite.datosTramite.preregistroConfirmado"/>
					</logic:equal>	
					</p>								
					<!--  Icono certificado / usuario / anonimo -->
					<p>
					<logic:equal name="tramite" property="nivelAutenticacion" value="<%=Character.toString(ConstantesBTE.AUTENTICACION_CERTIFICADO)%>">
						<img src="imgs/icones/certificado.gif" title="Certificado"/> <strong><bean:message key="detalleTramite.datosTramite.autenticacion"/>:</strong> <bean:message key="detalleTramite.datosTramite.autenticacion.certificado"/>
					</logic:equal>
					<logic:equal name="tramite" property="nivelAutenticacion" value="<%=Character.toString(ConstantesBTE.AUTENTICACION_USUARIOPASSWORD)%>">
						<img src="imgs/icones/usuario.gif" title="Usuario/Password"/> <strong><bean:message key="detalleTramite.datosTramite.autenticacion"/>:</strong> <bean:message key="detalleTramite.datosTramite.autenticacion.usuario"/>
					</logic:equal>
					<logic:equal name="tramite" property="nivelAutenticacion" value="<%=Character.toString(ConstantesBTE.AUTENTICACION_ANONIMO)%>">
						<img src="imgs/icones/anonimo.gif" title="Anonimo"/> <strong><bean:message key="detalleTramite.datosTramite.autenticacion"/>:</strong> <bean:message key="detalleTramite.datosTramite.autenticacion.anonimo"/>
					</logic:equal>
					</p>	
			</div>
			
			<h3><bean:message key="detalleTramite.datosSolicitud"/></h3>
			<div id="dadesJustificant">			
				<!--  Para tipo preregistro mostramos numero de registro con el que se ha confirmado -->
				<logic:equal name="tramite" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_PREREGISTRO)%>">
					<h4 class="titulo"><bean:message key="detalleTramite.datosSolicitud.datosRegistro"/></h4>
					<ul>
						<li><span class="label"><bean:message key="detalleTramite.datosSolicitud.numeroRegistro"/>:</span> <span><bean:write name="tramite" property="numeroRegistro"/></span></li>
						<li><span class="label"><bean:message key="detalleTramite.datosTramite.datosSolicitud.fechaRegistro"/>:</span> <span><bean:write name="tramite" property="fechaRegistro" format="dd/MM/yyyy - HH:mm 'h.'"/></span></li>
					</ul>
				</logic:equal>
				<logic:equal name="tramite" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA)%>">
					<h4 class="titulo"><bean:message key="detalleTramite.datosSolicitud.datosRegistro"/></h4>
					<ul>
						<li><span class="label"><bean:message key="detalleTramite.datosSolicitud.numeroRegistro"/>:</span> <span><bean:write name="tramite" property="numeroRegistro"/></span></li>
						<li><span class="label"><bean:message key="detalleTramite.datosTramite.datosSolicitud.fechaRegistro"/>:</span> <span><bean:write name="tramite" property="fechaRegistro" format="dd/MM/yyyy - HH:mm 'h.'"/></span></li>
					</ul>
				</logic:equal>
				<logic:notEqual name="tramite" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA)%>">
					<h4 class="titulo"><bean:message key="detalleTramite.datosSolicitud.datosEnvio"/></h4>
					<!--  Para tipo preregistro/preenvio mostramos numero de preregistro -->
					<logic:equal name="tramite" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_ENVIO)%>">
						<ul>
							<li><span class="label"><bean:message key="detalleTramite.datosSolicitud.datosEnvio.numero"/>:</span> <span><bean:write name="tramite" property="numeroRegistro"/></span></li>
							<li><span class="label"><bean:message key="detalleTramite.datosSolicitud.datosEnvio.fecha"/>:</span> <span><bean:write name="tramite" property="fechaRegistro" format="dd/MM/yyyy - HH:mm 'h.'"/></span></li>
						</ul>											
					</logic:equal>
					<logic:notEqual name="tramite" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_ENVIO)%>">
						<ul>
							<li><span class="label"><bean:message key="detalleTramite.datosSolicitud.datosEnvio.numero"/>:</span> <span><bean:write name="tramite" property="numeroPreregistro"/></span></li>
							<li><span class="label"><bean:message key="detalleTramite.datosSolicitud.datosEnvio.fecha"/>:</span> <span><bean:write name="tramite" property="fechaPreregistro" format="dd/MM/yyyy - HH:mm 'h.'"/></span></li>
						</ul>													
					</logic:notEqual>
				</logic:notEqual>												
				<ul>
					<li><span class="label"><bean:message key="detalleTramite.datosSolicitud.asunto"/>:</span> <span><bean:write name="tramite" property="descripcionTramite"/></span></li>
					<logic:notEmpty name="tramite" property="usuarioNif">
						<li><span class="label"><bean:message key="detalleTramite.datosSolicitud.asunto.nombre"/>:</span> <span><bean:write name="tramite" property="usuarioNombre"/></span></li>
						<li><span class="label"><bean:message key="detalleTramite.datosSolicitud.asunto.nif"/>:</span> <span><bean:write name="tramite" property="usuarioNif"/></span></li>
					</logic:notEmpty>
					<logic:notEmpty name="tramite" property="representadoNif">
						<li><span class="label"><bean:message key="detalleTramite.datosSolicitud.asunto.nombreRepresentado"/>:</span> <span><bean:write name="tramite" property="representadoNombre"/></span></li>
						<li><span class="label"><bean:message key="detalleTramite.datosSolicitud.asunto.nifRepresentado"/>:</span> <span><bean:write name="tramite" property="representadoNif"/></span></li>
					 </logic:notEmpty>
					<logic:notEmpty name="tramite" property="delegadoNif">
						<li><span class="label"><bean:message key="detalleTramite.datosSolicitud.asunto.nombreDelegado"/>:</span> <span><bean:write name="tramite" property="delegadoNombre"/></span></li>
						<li><span class="label"><bean:message key="detalleTramite.datosSolicitud.asunto.nifDelegado"/>:</span> <span><bean:write name="tramite" property="delegadoNif"/></span></li>
					</logic:notEmpty>
				</ul>
				
				<!-- Notificaciones telematicas y avisos -->
				<logic:notEmpty name="datosPropios">
						<logic:notEmpty name="datosPropios" property="instrucciones">
							<logic:notEmpty name="datosPropios" property="instrucciones.habilitarNotificacionTelematica">
								<logic:equal name="datosPropios" property="instrucciones.habilitarNotificacionTelematica" value="S">
								<ul>	
									<li>
										<span class="label"><bean:message key="detalleTramite.datosSolicitud.habilitarNotificaciones"/>:</span> 
										<span>Si</span>
									</li>
								</ul>									
								</logic:equal>
							</logic:notEmpty>
							<logic:notEmpty name="datosPropios" property="instrucciones.habilitarAvisos">
								<logic:equal name="datosPropios" property="instrucciones.habilitarAvisos" value="S">
								<ul>	
									<li>
										<span class="label"><bean:message key="detalleTramite.datosSolicitud.habilitarAvisos"/>:</span> 
										<span>
											<logic:notEmpty name="datosPropios" property="instrucciones.avisoEmail">
												Email: <bean:write name="datosPropios" property="instrucciones.avisoEmail"/>
											</logic:notEmpty>
											&nbsp;
											<logic:notEmpty name="datosPropios" property="instrucciones.avisoSMS">
												SMS: <bean:write name="datosPropios" property="instrucciones.avisoSMS"/>
											</logic:notEmpty>											
										</span>
									</li>
								</ul>									
								</logic:equal>
							</logic:notEmpty>							
						</logic:notEmpty>
				</logic:notEmpty>
				
				<!--  accesso al justificante para entradas telematicas -->										
				<logic:notEqual name="tramite" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_PREENVIO)%>">
					<logic:notEqual name="tramite" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_PREREGISTRO)%>">
					<ul>
						<li>
							<span class="label"><bean:message key="detalleTramite.datosSolicitud.justificante"/>:</span> 
							<span>
								<html:link action="mostrarJustificante" paramId="codigo" paramName="tramite" paramProperty="codigo">
									<bean:message key="detalleTramite.datosSolicitud.verJustificante"/>
								</html:link>
							</span>
						</li>
					</ul>
					</logic:notEqual>
				</logic:notEqual>
				
				
				<!--  Datos de confirmacion del preregistro/preenvio -->										
				<logic:notEqual name="tramite" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA)%>">
					<logic:notEqual name="tramite" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_ENVIO)%>">
						<h4 class="titulo"><bean:message key="detalleTramite.datosSolicitud.datosConfirmacion"/></h4>					
						<ul>
							<li>
								<span class="label"><bean:message key="detalleTramite.datosSolicitud.datosConfirmacion.confirmadoPor"/>:</span> 
								<logic:equal name="tramite" property="tipoConfirmacionPreregistro" value="<%=ConstantesBTE.CONFIRMACIONPREREGISTRO_GESTOR%>"> 
									<span><bean:message key="detalleTramite.datosSolicitud.datosConfirmacion.confirmadoPorGestor"/></span>
								</logic:equal>
								<logic:equal name="tramite" property="tipoConfirmacionPreregistro" value="<%=ConstantesBTE.CONFIRMACIONPREREGISTRO_REGISTRO%>"> 
									<span><bean:message key="detalleTramite.datosSolicitud.datosConfirmacion.confirmadoPorPuntoRegistro"/></span>									
								</logic:equal>
								<logic:equal name="tramite" property="tipoConfirmacionPreregistro" value="<%=ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA%>"> 
									<span><bean:message key="detalleTramite.datosSolicitud.datosConfirmacion.confirmadoPorAutomatica"/></span>									
								</logic:equal>
								<logic:equal name="tramite" property="tipoConfirmacionPreregistro" value="<%=ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO%>"> 
									<span><bean:message key="detalleTramite.datosSolicitud.datosConfirmacion.confirmadoPorAutomaticaRegistro"/></span>									
								</logic:equal>
							</li>	
							<li>
								<span class="label"><bean:message key="detalleTramite.datosSolicitud.datosConfirmacion.fecha"/>:</span> <span><bean:write name="tramite" property="fecha" format="dd/MM/yyyy - HH:mm 'h.'"/></span>
							</li>
							<!--  Mostramos numero de registro cuando haya pasado por registro de entrada -->
							<logic:notEqual name="tramite" property="tipoConfirmacionPreregistro" value="<%=ConstantesBTE.CONFIRMACIONPREREGISTRO_GESTOR%>"> 
								<logic:notEqual name="tramite" property="tipoConfirmacionPreregistro" value="<%=ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA%>"> 
									<li><span class="label"><bean:message key="detalleTramite.datosSolicitud.datosConfirmacion.numeroRegistro"/>:</span> <span><bean:write name="tramite" property="numeroRegistro"/></span></li>
								</logic:notEqual>
							</logic:notEqual>
						</ul>	
					</logic:notEqual>	
				</logic:notEqual>	
				
				<!-- Datos solicitud -->				
					<logic:notEmpty name="datosPropios">
						<logic:notEmpty name="datosPropios" property="solicitud">							
							<bean:define id="solicitud" name="datosPropios" property="solicitud" />					
									<h4 class="titulo"><bean:message key="detalleTramite.datosSolicitud"/>:</h4>
									<ul>
							<logic:iterate id="dato" name="solicitud" property="dato">
								<logic:equal name="dato" property="tipo" value="C">
										<li><span class="label"><bean:write name="dato" property="descripcion"/>:</span> <span><bean:write name="dato" property="valor"/></span></li>
								</logic:equal>
								<logic:notEqual name="dato" property="tipo" value="C">
									<li><span class="label2nivel"><bean:write name="dato" property="descripcion"/></span></li>
								</logic:notEqual>
							</logic:iterate>									
									</ul>
						</logic:notEmpty>
				 	</logic:notEmpty>
								<h4 class="titulo"><bean:message key="detalleTramite.datosSolicitud.documentosAportados.telematico"/>:</h4>
								<ul id="docs">
					<logic:iterate id="documento" name="tramite" property="documentos" type="es.caib.bantel.model.DocumentoBandeja">
						<logic:notEmpty name="documento" property="rdsCodigo">
							<logic:notEqual name="documento" property="identificador" value="<%=ConstantesAsientoXML.IDENTIFICADOR_DATOS_PROPIOS%>">
									<li>
										<html:link action="mostrarDocumento" paramId="codigo" paramName="documento" paramProperty="codigo">
											<bean:write name="documento" property="nombre" />
										</html:link>
										<% if (documentosEstructurados.contains(documento.getCodigo())){ %>
											&nbsp;&nbsp;<html:link  styleClass="pequenyo" action="mostrarDocumentoXML" paramId="codigo" paramName="documento" paramProperty="codigo">
												[XML]
											</html:link>
										<%}%>
										
										<span class="pequenyo">
										<bean:define id="codigoFirma" type="java.lang.String">
											<bean:write name="documento" property="rdsCodigo" />
										</bean:define>
										
										<logic:notEmpty name="<%=codigoFirma %>" scope="request">
											<br/>
											<bean:message key="comprobarDocumento.firmadoPor"/>
											<logic:iterate name="<%=codigoFirma %>" id="firma" scope="request" type="es.caib.sistra.plugins.firma.FirmaIntf">							
												&nbsp;
												<a href="/bantelfront/mostrarFirmaDocumento.do?codigo=<%=documento.getRdsCodigo()%>&clave=<%=documento.getRdsClave()%>&nif=<%=firma.getNif()%>" >
													<bean:write name="firma" property="nombreApellidos"/>  									
												</a>	
											</logic:iterate>			
										</logic:notEmpty>
										
										<logic:notEmpty name="<%=\"CUST-\" + codigoFirma %>" scope="request">
											<br/>
											<bean:message key="comprobarDocumento.urlCustodia"/>
											<a href="/bantelfront/mostrarDocumentoCustodia.do?codigo=<%=documento.getRdsCodigo()%>&clave=<%=documento.getRdsClave()%>" target="_blank">
												<bean:write name="<%=\"CUST-\" + codigoFirma %>" />
											</a>														
										</logic:notEmpty>
										</span>
										
									</li>
							</logic:notEqual>					
						</logic:notEmpty>
					</logic:iterate>
								</ul>
					<logic:notEmpty name="datosPropios">
						<logic:notEmpty name="datosPropios" property="instrucciones">
							<bean:define id="instrucciones" name="datosPropios" property="instrucciones" />
							<logic:present name="instrucciones" property="documentosEntregar" >					
									<bean:define id="documentosEntregar" name="instrucciones" property="documentosEntregar" />
									<logic:notEmpty name="documentosEntregar" property="documento">
									<h4 class="titulo"><bean:message key="detalleTramite.datosSolicitud.documentosAportados.presencial"/>:</h4>				
									<ul id="docs">
										<logic:iterate id="documento" name="documentosEntregar" property="documento" type="es.caib.xml.datospropios.factoria.impl.Documento">										
										<%
												String messageKey = "detalleTramite.documentoPresencial";
												switch (documento.getTipo().charValue()){
													case ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_JUSTIFICANTE:
														messageKey += ".justificante";
														break;
													case ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO:
														messageKey += ".formulario";
														break;
													case ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO_JUSTIFICANTE:
														messageKey += ".formularioJustificante";
														break;
													case ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_PAGO:
														messageKey += ".pago";
														break;
													case ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO:
														messageKey +=".anexo";
														if ( documento.isCompulsar().booleanValue() )
														{
															messageKey += ".compulsar";
														}
														if ( documento.isFotocopia().booleanValue() )
														{
															messageKey += ".fotocopia";
														}
														break;										
												 }
											%>
												<li><bean:write name="documento" property="titulo" /><br/><bean:message key="<%=messageKey%>"/></li>											
										</logic:iterate>					
									</ul>	
								</logic:notEmpty>
							</logic:present>	
						</logic:notEmpty>	
					</logic:notEmpty>	
			</div>
			
			<!-- tornar enrere -->
			<div id="enrere">
				<a href="#" onclick="javascript:volver('<%=expediente.getIdentificadorExpediente()%>','<%=expediente.getUnidadAdministrativa()%>','<%=expediente.getClaveExpediente()%>')">
					<bean:message key="detalle.aviso.tornar" />				
				</a>					
			</div>
			<!-- /tornar enrere -->
			
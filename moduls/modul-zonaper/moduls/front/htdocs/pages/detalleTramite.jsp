<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>

<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<%@ page import="es.caib.xml.registro.factoria.ConstantesAsientoXML" %>
<%@ page import="es.caib.xml.datospropios.factoria.ConstantesDatosPropiosXML" %>
<%@ page import="es.caib.xml.registro.factoria.ConstantesAsientoXML" %>
<%@ page import="es.caib.redose.modelInterfaz.ConstantesRDS"%>
<%@ page import="es.caib.util.StringUtil"%>

<bean:define id="tipo" name="tipo" type="java.lang.String" />

<bean:define id="entrada" name="entrada" type="es.caib.zonaper.model.Entrada" />

<bean:define id="urlMostrarDocumento">
	<html:rewrite page="/protected/mostrarDocumento.do" paramId="tipoEntrada" paramName="tipo" />
</bean:define>

<bean:define id="urlMostrarJustificante">
	<html:rewrite page="/protected/mostrarJustificante.do" paramId="tipoEntrada" paramName="tipo" />
</bean:define>

<bean:define id="codigosDocsPresenciales" type="java.util.Map" name="codigosDocsPresenciales"/>


<!-- titol -->
<h1>
	<bean:message key="detalleTramite.titulo" />
</h1>
<!-- /titol -->

<!-- tramit -->
<div id="tramit">
	
	<h2><bean:message key="detalleTramite.datosSolicitudTelematica" /></h2>
	
	<div class="dades">
	
		<!-- dades justificant -->
		<table class="dadesJustificant">
			<caption>
				<logic:equal name="tipo" value="<%= Character.toString( ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA ) %>">
					<bean:message key="detalleTramite.datosRegistro"/>
				</logic:equal>
				<logic:notEqual name="tipo" value="<%= Character.toString( ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA ) %>">
					<bean:message key="detalleTramite.datosEnvio"/>
				</logic:notEqual>
			</caption>
			<!--  Numero y fecha -->
			<logic:equal name="tipo" value="<%= Character.toString( ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA ) %>">
					<!--  Tipo Registro Entrada: mostramos numero y fecha registro -->	
					<tr>
						<th scope="row"><bean:message key="detalleTramite.datosRegistro.numeroRegistro" />:</th>
						<td><bean:write name="entrada" property="numeroRegistro"/></td>
					</tr>
					<tr>
						<th scope="row"><bean:message key="detalleTramite.datosRegistro.fechaRegistro" />:</th>
						<td><bean:write name="entrada" property="fecha" format="dd/MM/yyyy - HH:mm 'h.'"/></td>
					</tr>				
				</logic:equal>								
			<!--  Demas tipos: mostramos numero envio, fecha envio y si procede fecha confirmación -->
			<logic:notEqual name="tipo" value="<%= Character.toString( ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA ) %>">
				<tr>
					<th scope="row"><bean:message key="detalleTramite.datosEnvio.numero" />:</th>
					<td><logic:notEqual name="tipo" value="<%= Character.toString( ConstantesAsientoXML.TIPO_ENVIO ) %>"><bean:write name="entrada" property="numeroPreregistro"/>&nbsp;&nbsp;&nbsp;&nbsp;( D.C.: <%=es.caib.util.StringUtil.calculaDC(entrada.getNumeroPreregistro())%> )</logic:notEqual><logic:equal name="tipo" value="<%= Character.toString( ConstantesAsientoXML.TIPO_ENVIO ) %>"><bean:write name="entrada" property="numeroRegistro"/></logic:equal></td>
				</tr>
				<tr>
					<th scope="row"><bean:message key="detalleTramite.datosEnvio.fecha" />:</th>
					<td><bean:write name="entrada" property="fecha" format="dd/MM/yyyy - HH:mm 'h.'"/></td>
				</tr>					
			</logic:notEqual>
			<!--  Asunto -->
			<!--  Asunto -->
				<tr>
					<th scope="row"><bean:message key="detalleTramite.asunto" />:</th>
					<td><bean:write name="entrada" property="descripcionTramite"/></td>				
				</tr>
				<tr>
					<th scope="row"><bean:message key="detalleTramite.idioma" />:</th>
					<td>
						<bean:define id="idIdioma" type="java.lang.String">
							 <bean:write name="entrada" property="idioma"/>
						</bean:define>
						<bean:message name="idIdioma"/>
					 </td>				
				</tr>
				<logic:notEmpty name="representante" property="numeroIdentificacion">
					<logic:empty name="representado">										
						<tr>
							<th scope="row"><bean:message key="detalleTramite.datosRegistro.nombre"/></th>
							<td><bean:write name="representante" property="identificacionInteresado"/></td>					
						</tr>
						<logic:notEqual name="representante" property="tipoIdentificacion" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF)%>">
							<tr>	
								<th scope="row"><bean:message key="detalleTramite.datosRegistro.NIF"/></th>
								<td><bean:write name="representante" property="numeroIdentificacion"/></td>										
							</tr>
						</logic:notEqual>
						<logic:equal name="representante" property="tipoIdentificacion" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF)%>">
							<tr>
								<th scope="row"><bean:message key="detalleTramite.datosRegistro.CIF"/></th>
								<td><bean:write name="representante" property="numeroIdentificacion"/></td>										
							</tr>
						</logic:equal>
					</logic:empty>
					<logic:notEmpty name="representado">					
							<tr>
								<th scope="row"><bean:message key="detalleTramite.datosRegistro.nombreRepresentante"/></th>
								<td><bean:write name="representante" property="identificacionInteresado"/></td>
							</tr>							
							<logic:notEqual name="representante" property="tipoIdentificacion" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF)%>">
								<tr>
									<th scope="row"><bean:message key="detalleTramite.datosRegistro.NIFRepresentante"/></th>
									<td><bean:write name="representante" property="numeroIdentificacion"/></td>
								</tr>										
							</logic:notEqual>
							<logic:equal name="representante" property="tipoIdentificacion" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF)%>">
								<tr>
									<th scope="row"><bean:message key="detalleTramite.datosRegistro.CIFRepresentante"/></th>
									<td><bean:write name="representante" property="numeroIdentificacion"/></td>										
								</tr>
							</logic:equal>
							<tr>
								<th scope="row"><bean:message key="detalleTramite.datosRegistro.nombreRepresentado"/></th>
								<td><bean:write name="representado" property="identificacionInteresado"/></td>
							</tr>
							<logic:notEqual name="representado" property="tipoIdentificacion" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF)%>">
								<tr>
									<th scope="row"><bean:message key="detalleTramite.datosRegistro.NIFRepresentado"/></th>
									<td><bean:write name="representado" property="numeroIdentificacion"/></td>										
								</tr>
							</logic:notEqual>
							<logic:equal name="representado" property="tipoIdentificacion" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF)%>">
								<tr>
									<th scope="row"><bean:message key="detalleTramite.datosRegistro.CIFRepresentado"/></th>
									<td><bean:write name="representado" property="numeroIdentificacion"/></td>										
								</tr>
							</logic:equal>					
					</logic:notEmpty>	
				</logic:notEmpty>	
				<logic:notEmpty name="delegado">
						<tr>
							<th>
								<bean:message key="detalleTramite.datosRegistro.nombreDelegado"/>:
							</th> 
							<td>
								<bean:write name="delegado" property="identificacionInteresado"/>
							</td>
						</tr>
						<logic:notEqual name="delegado" property="tipoIdentificacion" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF)%>">
							<tr>	
								<th scope="row"><bean:message key="detalleTramite.datosRegistro.NIFDelegado"/></th>
								<td><bean:write name="delegado" property="numeroIdentificacion"/></td>										
							</tr>
						</logic:notEqual>
						<logic:equal name="delegado" property="tipoIdentificacion" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF)%>">
							<tr>
								<th scope="row"><bean:message key="detalleTramite.datosRegistro.CIFDelegado"/></th>
								<td><bean:write name="delegado" property="numeroIdentificacion"/></td>										
							</tr>
						</logic:equal>
				</logic:notEmpty>
				<logic:notEmpty name="entrada" property="habilitarNotificacionTelematica">
					<tr>
						<th>
							<bean:message key="detalleTramite.datosRegistro.notificacionTelematica"/>
						</th> 
						<td>
							<logic:equal name="entrada" property="habilitarNotificacionTelematica" value="S">
								<bean:message key="si"/>
							</logic:equal>
							<logic:equal name="entrada" property="habilitarNotificacionTelematica" value="N">
								<bean:message key="no"/>
							</logic:equal>												
						</td>
					</tr>
				</logic:notEmpty>
				<tr>
					<th>
						<bean:message key="detalleTramite.datosRegistro.presentacion"/>
					</th> 
					<td>
						<logic:equal name="entrada" property="nivelAutenticacion" value="C">
							<bean:message key="detalleTramite.datosRegistro.presentacion.certificado"/>
						</logic:equal>
						<logic:equal name="entrada" property="nivelAutenticacion" value="U">
							<bean:message key="detalleTramite.datosRegistro.presentacion.usuario"/>
						</logic:equal>
						<logic:equal name="entrada" property="nivelAutenticacion" value="A">
							<bean:message key="detalleTramite.datosRegistro.presentacion.anonimo"/>
						</logic:equal>						
					</td>
				</tr>
		</table>
		<!-- /dades justificant -->
		
		<!--  dades propies -->
		<logic:notEmpty name="datosPropios">
			<logic:notEmpty name="datosPropios" property="solicitud">						
				<bean:define id="solicitud" name="datosPropios" property="solicitud" />
				<table class="dadesPropies">					
					<caption>
						<bean:message key="detalleTramite.datosSolicitud" />:
					</caption>							
					<logic:iterate id="dato" name="solicitud" property="dato">
						<logic:equal name="dato" property="tipo" value="C">							
							<bean:define id="valorDato" name="dato" property="valor" type="java.lang.String"/>
							<tr>
								<th scope="row"><bean:write name="dato" property="descripcion"/>:</th>
								<td><%= StringUtil.newLineToBr( valorDato ) %></td>
							</tr>
						</logic:equal>
						<logic:notEqual name="dato" property="tipo" value="C">
							<tr>
								<th class="nivel2"><bean:write name="dato" property="descripcion"/></th>
								<td></td>
							</tr>
						</logic:notEqual>
					</logic:iterate>									
				</table>
			</logic:notEmpty>
	 	</logic:notEmpty>
		<!--  /dades propies -->
		
		<!-- docus -->
		<h3><bean:message key="detalleTramite.documentos" /></h3>
		<ul>
			<logic:iterate id="documento" name="entrada" property="documentos">
				<logic:present name="documento"  property="identificador">
					<logic:notEmpty name="documento" property="codigoRDS">
						<logic:notEqual name="documento" property="codigoRDS" value="0">
							<logic:notEqual name="documento" property="identificador" value="<%=ConstantesAsientoXML.IDENTIFICADOR_DATOS_PROPIOS%>">
								<li>
									<html:link href="<%= urlMostrarDocumento + \"&amp;codigoEntrada=\" + entrada.getCodigo() %>" paramId="codigoDocumento" paramName="documento" paramProperty="codigo">
											<bean:write name="documento" property="descripcion" />
									</html:link>									
									<bean:define id="codigoFirma" type="java.lang.String">
										<bean:write name="documento" property="codigo" />
									</bean:define>
									<logic:notEmpty name="<%=codigoFirma %>" scope="request">
										<bean:message key="comprobarDocumento.firmadoPor"/>
										<logic:iterate name="<%=codigoFirma %>" id="firma" scope="request">							
											&nbsp;<bean:write name="firma" property="nombreApellidos"/> 
											<logic:notEmpty name="firma" property="nifRepresentante">
												&nbsp; <bean:message key="firma.representadoPor"/> <bean:write name="firma" property="nombreApellidosRepresentante"/> - NIF: <bean:write name="firma" property="nifRepresentante"/>
											</logic:notEmpty>										
										</logic:iterate>			
									</logic:notEmpty>									
								</li>
							</logic:notEqual>
						</logic:notEqual>	
					</logic:notEmpty>
				</logic:present>						
			</logic:iterate>		
		</ul>
		<!-- /docus -->
	</div>
	
	
	<!--  DOCUMENTACION PRESENCIAL:  SOLO PARA PRESENCIAL (PREREGISTROS/PREENVIOS)  -->
		<logic:equal name="telematico" value="N">
			<logic:notEmpty name="datosPropios">
				<logic:notEmpty name="datosPropios" property="instrucciones">
					<bean:define id="instrucciones" name="datosPropios" property="instrucciones" />
					<logic:present name="instrucciones" property="documentosEntregar">
						<!-- PARA TODOS MOSTRAMOS LA FECHA LIMITE DE ENTREGA: PARA SER COHERENTES CON CONFIRMACION AUTOMATICA, CONFIRMACION DE INCORRECTOS,ETC.--> 				 
						<h2><bean:message key="detalleTramite.documentacionAAportar" /></h2>
						<p><bean:write name="instrucciones" property="textoInstrucciones" /></p>
						<p class="alerta">
							<logic:empty name="instrucciones" property="textoFechaTopeEntrega">
								<bean:message key="detalleTramite.textoFechaLimiteEntrega" />  <strong><bean:write name="instrucciones" property="fechaTopeEntrega" format="dd/MM/yyyy" /></strong>
							</logic:empty>
							<logic:notEmpty name="instrucciones" property="textoFechaTopeEntrega">
								<bean:write name="instrucciones" property="textoFechaTopeEntrega" /></strong>
							</logic:notEmpty>
						</p>
					
						<table class="accions">
							<thead>
								<tr>
									<th scope="col" class="doc"><bean:message key="detalleTramite.documento" /></th>
									<th scope="col"><bean:message key="detalleTramite.accionARealizar" /></th>
								</tr>
							</thead>
							<tbody>						
								<bean:define id="documentosEntregar" name="instrucciones" property="documentosEntregar" />
								<logic:iterate id="documento" name="documentosEntregar" property="documento" type="es.caib.xml.datospropios.factoria.impl.Documento">
									<tr>
										<!--  Descripcion doc con enlace -->										
										<logic:equal name="documento" property="tipo" value="J">					
											<td>
												<html:link href="<%= urlMostrarJustificante %>" paramId="codigoEntrada" paramName="entrada" paramProperty="codigo">
													<bean:write name="documento" property="titulo" />
												</html:link>
											</td>
										</logic:equal>		
										<logic:equal name="documento" property="tipo" value="G">
											<td> 
												<html:link href="<%= urlMostrarJustificante %>" paramId="codigoEntrada" paramName="entrada" paramProperty="codigo">
													<bean:write name="documento" property="titulo" />
												</html:link>
											</td>
										</logic:equal>										
										<logic:equal name="documento" property="tipo" value="F">
											<td> 		
												<html:link href="<%= urlMostrarDocumento + \"&amp;codigoEntrada=\" + entrada.getCodigo() + \"&amp;codigoDocumento=\" + codigosDocsPresenciales.get(documento.getIdentificador()).toString() %>" >								
													<bean:write name="documento" property="titulo" />
												</html:link>
											</td>
										</logic:equal>										
										<logic:equal name="documento" property="tipo" value="A">											
											<td>
												<bean:write name="documento" property="titulo" />
											</td>
										</logic:equal>										
										<logic:equal name="documento" property="tipo" value="P">
											<td>
												<bean:write name="documento" property="titulo" />
											</td>
										</logic:equal>		
										
										<!--  Descripcion accion -->
										<logic:equal name="documento" property="tipo" value="J">					
											<td>												
												<bean:message key="detalleTramite.instrucciones.justificante.firmar"/>												
											</td>
										</logic:equal>
		
										<logic:equal name="documento" property="tipo" value="G">
											<td> 											
												<bean:message key="detalleTramite.instrucciones.formularioJustificante.firmar"/>																	
											</td>
										</logic:equal>
										
										<logic:equal name="documento" property="tipo" value="F">
											<td> 														
												<bean:message key="detalleTramite.instrucciones.formulario.firmar"/>					
											</td>
										</logic:equal>
										
										<logic:equal name="documento" property="tipo" value="A">
											<%  
												String keyMessage="detalleTramite.instrucciones.anexo";							
												keyMessage+= (documento.isCompulsar().booleanValue()) ? ".compulsar" : "";												
												keyMessage+= (documento.isFotocopia().booleanValue()) ? ".fotocopia" : "";
												keyMessage+= (!documento.isFotocopia().booleanValue() && !documento.isCompulsar().booleanValue()) ? ".presencial" : "";											
											%>
											<td>
												<bean:message key="<%=keyMessage%>"/>						
											</td>
										</logic:equal>
										
										<logic:equal name="documento" property="tipo" value="P">
											<td><bean:message key="detalleTramite.instrucciones.pago"/></td>
										</logic:equal>
									</tr>
								</logic:iterate>
						</tbody>
						</table>
					</logic:present>
				</logic:notEmpty>
			</logic:notEmpty>		
		</logic:equal>
			
<!--  DESCARGA JUSTIFICANTE: SOLO PARA TELEMATICOS (REGISTROS/ENVIOS)  -->
<logic:equal name="telematico" value="S">
	<h2><bean:message key="detalleTramite.datosJustificante" /></h2>
	<p><bean:message key="detalleTramite.datosJustificante.texto" /></p>
	<p style="text-align:center; margin:2em 0;"><input name="XX" type="button" value="<bean:message key="detalleTramite.imprimirJustificante" />" onclick="javascript:document.location.href='<html:rewrite href="<%= urlMostrarJustificante %>" paramId="codigoEntrada" paramName="entrada" paramProperty="codigo"/>'" /></p>
	<p id="getAdobeReader">
		<bean:message key="detalleTramite.getAdobeReader"/>
		<br />
		<a href="http://www.adobe.es/products/acrobat/readstep2.html"><bean:message key="detalleTramite.irWebAdobe"/></a>
	</p>
</logic:equal>			
			
			
		<%--  Si se indica la url de acceso a un documento, abrimos en nueva ventana --%>	
		<logic:present name="urlAcceso">
			<script type="text/javascript">
				// Abrimos ventana plataforma pagos
				window.open("<bean:write name="urlAcceso" />");
			</script>
		</logic:present>

</div>




			
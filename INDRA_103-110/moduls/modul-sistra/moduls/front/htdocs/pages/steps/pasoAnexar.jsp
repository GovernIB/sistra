<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<bean:define id="urlAnexar">
        <html:rewrite page="/protected/irAAnexar.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<bean:define id="urlBorrarAnexo">
        <html:rewrite page="/protected/borrarAnexo.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<bean:define id="urlMostrarAnexo">
        <html:rewrite page="/protected/mostrarDocumento.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<bean:define id="genericoAnterior" value="" type="java.lang.String"/>
<%
	boolean primero;
	String keyTitulo;
	String keyClass;
	String keyCode;
%>

			<h2><bean:message key="pasoAnexar.anexarDocumentos"/></h2>
			
			<%-- Mensaje en caso de que no se deba aportar ningún documento --%>
			<logic:equal name="debeAportar" value="N">				
				<p class="ultimo"><bean:message key="anexarDocumentos.anexar.instrucciones.noDocumentosAportar"/></p>			
			</logic:equal>
			
			
			<%-- En caso de que se pueda aportar algun documento mostramos lista de documentos --%>
			<logic:equal name="debeAportar" value="S">				
				
				<!--  Descripcion paso -->
				<p>
					<bean:message key="pasoAnexar.anexarDocumentos.obligatorio"/>
				</p>
				
				<!--  Tenga en cuenta que -->
				<%boolean cuenta=false;%>
				<logic:equal name="iconos" property="N" value="true">
					<%cuenta=true;%>					
				</logic:equal>
				<logic:equal name="iconos" property="P" value="true">
					<%cuenta=true;%>					
				</logic:equal>
								
				<%if (cuenta){%>
					<p>
					<bean:message key="pasoAnexar.anexarDocumentos.cuenta"/>																	
				<%}%>
								
				<!--  * existen docs opcionales -->
				<logic:equal name="iconos" property="N" value="true">
					<bean:message key="pasoAnexar.anexarDocumentos.opcionales"/>
				</logic:equal>					
				<!--  * existen docs presenciales -->
				<logic:equal name="iconos" property="P" value="true">
					<logic:equal name="iconos" property="N" value="true">&nbsp;<bean:message key="pasoAnexar.anexarDocumentos.y"/>&nbsp;</logic:equal>
					<bean:message key="pasoAnexar.anexarDocumentos.presencial"/>
				</logic:equal>
				
				<!--  Fin Tenga en cuenta que -->
				<%if (cuenta){%>				
					.</p>
				<%}%>
				
				</p>
					
				<p class="ultimo">					
					<!--  Instrucciones anexar -->
					<bean:message key="pasoAnexar.anexarDocumentos.instrucciones"/>			

					<%--  En caso de que existan documentos para firmar mostramos información de firma 
					<logic:equal name="firmarFicheros" value="S">
						<br/><bean:message key="pasoAnexar.anexarDocumentos.firmarDigitalmente"/>
					</logic:equal>
					--%>
				</p>
				
				<%-- iconografia --%>
				<div id="iconografia">
							<a href="javascript:icosMasInfo();" id="iconografiaMasInfo" title="<bean:message key="iconografia.info" />"><bean:message key="iconografia.masInfo"/></a>
					<h4><bean:message key="pasoAnexar.anexarDocumentos.iconografia"/></h4>
					<ul>
						<logic:equal name="iconos" property="S" value="true">
							<li><img src="imgs/tramitacion/iconos/doc_obligatorio.gif" alt="<bean:message key="pasoAnexar.iconografia.documentoObligatorio"/>" /> <bean:message key="pasoAnexar.iconografia.documentoObligatorio"/><span><bean:message key="pasoAnexar.iconografia.documentoObligatorioInfo"/></span></li>
						</logic:equal>
						<logic:equal name="iconos" property="N" value="true">
							<li><img src="imgs/tramitacion/iconos/doc_opcional.gif" alt="<bean:message key="pasoAnexar.iconografia.documentoOpcional"/>" /> <bean:message key="pasoAnexar.iconografia.documentoOpcional"/><span><bean:message key="pasoAnexar.iconografia.documentoOpcionalInfo"/></span></li>
						</logic:equal>
					<%-- 	<li><img src="imgs/tramitacion/iconos/form_dependiente.gif" alt="<bean:message key="pasoAnexar.iconografia.documentoDependiente"/>" /> <bean:message key="pasoAnexar.iconografia.documentoDependiente"/><span><bean:message key="pasoAnexar.iconografia.documentoDependienteInfo"/></span></li>  --%>
						<logic:equal name="iconos" property="C" value="true">
							<li><img src="imgs/tramitacion/iconos/doc_compulsado.gif" alt="<bean:message key="pasoAnexar.iconografia.documentoCompulsado"/>" /> <bean:message key="pasoAnexar.iconografia.documentoCompulsado"/><span><bean:message key="pasoAnexar.iconografia.documentoCompulsadoInfo"/></span></li>
						</logic:equal>
						<logic:equal name="iconos" property="F" value="true">
							<li><img src="imgs/tramitacion/iconos/doc_firmar.gif" alt="<bean:message key="pasoAnexar.iconografia.firmarDocumento"/>" /> <bean:message key="pasoAnexar.iconografia.firmar"/><span><bean:message key="pasoAnexar.iconografia.firmarInfo"/></span></li>				
						</logic:equal>
						<logic:equal name="iconos" property="X" value="true">
							<li><img src="imgs/tramitacion/iconos/doc_fotocopia.gif" alt="<bean:message key="pasoAnexar.iconografia.fotocopia"/>" /> <bean:message key="pasoAnexar.iconografia.fotocopia"/><span><bean:message key="pasoAnexar.iconografia.fotocopiaInfo"/></span></li>				
						</logic:equal>
					</ul>
					<ul class="tipo">
						<logic:equal name="iconos" property="T" value="true">
							<li><img src="imgs/tramitacion/iconos/doc_telematico.gif" alt="<bean:message key="pasoAnexar.iconografia.documentoAnexo"/>" title="<bean:message key="pasoAnexar.iconografia.documentoAnexo"/>" /> <bean:message key="pasoAnexar.iconografia.documentoAnexo"/></li>
						</logic:equal>
						<logic:equal name="iconos" property="P" value="true">
							<li><img src="imgs/tramitacion/iconos/doc_presencial.gif" alt="<bean:message key="pasoAnexar.iconografia.documentoAnexoPresencial"/>" title="<bean:message key="pasoAnexar.iconografia.documentoAnexoPresencial"/>" /> <bean:message key="pasoAnexar.iconografia.documentoAnexoPresencial"/></li>
						</logic:equal>
					</ul>					
				</div>								
				
				<%-- Lista de documentos: dos iteraciones, una para obligatorios y otra para opcionales --%>
				<div id="anexarDocs">
				
				<%--   Inicio for oligatorios/opcionales --%>
				<% 
					for (int i=0;i<2;i++){ 
				
					primero=true;
						
					if (i==0){					
						// Iteracion 0: Documentos obligatorios
						keyTitulo="pasoAnexar.documentosObligatorios";
						keyClass="docObl";
						keyCode="S";
					}else{
						// Iteracion 1: Documentos opcionales
						keyTitulo="pasoAnexar.documentosOpcionales";
						keyClass="docOpc";
						keyCode="N";
					}
				
				%>
												
				<logic:iterate id="anexo" name="tramite" property="anexos" type="es.caib.sistra.model.DocumentoFront">								
						
						
						<logic:equal name="anexo" property="obligatorio" value='<%=keyCode%>'>
						
							<%-- Si se ha empezado a rellenar un genérico metemos todas las instancias debajo (Igual para los 2 bloques) --%>				
							<logic:notEmpty name="genericoAnterior">
								<%-- Si es una instancia del genérico la situamos debajo --%>
								<logic:equal name="genericoAnterior" value="<%=anexo.getIdentificador()%>">
									<br/>
										&nbsp;&nbsp;
										<strong>- <bean:write name="anexo" property="anexoGenericoDescripcion" /> 
											<logic:notEmpty name="anexo" property="anexoFichero">
											(<html:link href="<%= urlMostrarAnexo + "&identificador=" + anexo.getIdentificador() + "&instancia=" + anexo.getInstancia() %>">
												<bean:write name="anexo" property="anexoFichero" />
											</html:link>)
											</logic:notEmpty>
										</strong>
										<logic:equal name="flujoDocumentos" property="<%=anexo.getIdentificador()%>" value="true">										
										 - <html:link href="<%= urlBorrarAnexo + "&identificador=" + anexo.getIdentificador() + "&instancia=" + anexo.getInstancia() %>"><bean:message key="pasoAnexar.documentos.quitarFichero"/></html:link>																												
										</logic:equal>
								</logic:equal>
								<%-- Si la anterior era la última instancia colocamos el botón de Añadir --%>
								<logic:notEqual name="genericoAnterior" value="<%=anexo.getIdentificador()%>">																		
									<bean:define id="maxInstancia" name="instanciasGenericos" property="<%=genericoAnterior%>" type="java.lang.Integer"/>																		
									<logic:greaterThan name="genericoAnteriorMaxInstancias" value="<%=maxInstancia.toString()%>"> 																				
										<logic:equal name="flujoDocumentos" property="<%=genericoAnterior%>" value="true">										
											<br/>
												<html:link href="<%= urlAnexar + "&identificador=" + genericoAnterior + "&instancia=" + (maxInstancia.intValue() + 1) %>">
													<bean:message key="pasoAnexar.documentos.anexarDocumento"/>
												</html:link>
										</logic:equal>										
									</logic:greaterThan>
									<%-- Cerramos parrafo --%>
									</p>
								</logic:notEqual>
							</logic:notEmpty>
							
						
							<%--  Titulo Obligatorios / Opcionales --%>
							<% if (primero){
								primero =false; 
							%>		
									<h3 class="<%=keyClass%>"><bean:message key="<%=keyTitulo%>"/></h3>
							<%
								}						
							%>	
													
							<%-- Si no es una instancia de un genérico anterior seguimos normal --%>
							<logic:notEqual name="genericoAnterior" value="<%=anexo.getIdentificador()%>">	
								
								<%--  Iconos (igual para los 2 bloques) --%>
								<div class="iconos"><logic:equal name="anexo" property="anexoCompulsar" value="true"> <img src="imgs/tramitacion/iconos/doc_compulsado.gif" alt="<bean:message key="pasoAnexar.iconografia.documentoCompulsado"/>" title="Documento compulsado"/></logic:equal><logic:equal name="anexo" property="firmar" value="true"> <img src="imgs/tramitacion/iconos/doc_firmar.gif" alt="<bean:message key="pasoAnexar.iconografia.firmarDocumento"/>" title="Firmar documento"/></logic:equal><logic:equal name="anexo" property="anexoFotocopia" value="true"> <img src="imgs/tramitacion/iconos/doc_fotocopia.gif" alt="<bean:message key="pasoAnexar.iconografia.fotocopia"/>" title="Fotocopia"/></logic:equal> 
								<logic:equal name="anexo" property="anexoPresentarTelematicamente" value="true"> 
									<img src="imgs/tramitacion/iconos/doc_telematico.gif" alt="<bean:message key="pasoAnexar.iconografia.documentoAnexo"/>" title="<bean:message key="pasoAnexar.iconografia.documentoAnexo"/>" />
								</logic:equal>
								<logic:equal name="anexo" property="anexoPresentarTelematicamente" value="false"> 
									<img src="imgs/tramitacion/iconos/doc_presencial.gif" alt="<bean:message key="pasoAnexar.iconografia.documentoAnexoPresencial"/>" title="<bean:message key="pasoAnexar.iconografia.documentoAnexoPresencial"/>" />
								</logic:equal>
								</div>
								<%-- Fin iconos --%>

								<%--  Instrucciones documento (igual para los dos bloques) --%>
								<div class="infoDoc"> 
						            <p><strong><bean:message key="pasoAnexar.documentos.instrucciones.descripcion"/>:</strong> <bean:write name="anexo" property="informacion" filter="false" />.</p>
						            <logic:notEmpty name="anexo" property="anexoPlantilla">
						            	<p><strong><bean:message key="pasoAnexar.documentos.instrucciones.plantilla"/>:</strong> <bean:message key="pasoAnexar.documentos.instrucciones.plantillaTexto" />.</p>
						            </logic:notEmpty>
									<p><strong><bean:message key="pasoAnexar.documentos.instrucciones.presentacion"/>:</strong> 			            			            
						            <%-- Instrucciones presentacion para telemáticos --%>			        
								        <logic:equal name="anexo" property="anexoPresentarTelematicamente" value="true">
								        	<logic:equal name="anexo" property="anexoGenerico" value="false" >
								        		<bean:message key="pasoAnexar.documentos.instrucciones.presentacion.telematico"/>
								        	</logic:equal>
								        	<logic:equal name="anexo" property="anexoGenerico" value="true" >
								        		<bean:message key="pasoAnexar.documentos.instrucciones.presentacion.telematicoGenerico" arg0="<%=Integer.toString(anexo.getAnexoGenericoMax())%>"/>
								        	</logic:equal>								        	
								        	<logic:equal name="anexo" property="anexoCompulsar" value="true">				
												<bean:message key="anexarDocumentos.anexar.instrucciones.telematico.compulsar"/></p>									
											</logic:equal>
								        </logic:equal>			        
								        <%-- Instrucciones presentacion para presenciales --%>
								        <logic:equal name="anexo" property="anexoPresentarTelematicamente" value="false">			        			        			        
											<logic:equal name="anexo" property="anexoFotocopia" value="true">				
												<logic:equal name="anexo" property="anexoCompulsar" value="true">				
													<bean:message key="anexarDocumentos.anexar.instrucciones.presencial.fotocopia.compulsar"/>																			
												</logic:equal>
												<logic:equal name="anexo" property="anexoCompulsar" value="false">				
													<bean:message key="anexarDocumentos.anexar.instrucciones.presencial.fotocopia"/>							
												</logic:equal>
											</logic:equal>
											<logic:equal name="anexo" property="anexoFotocopia" value="false">				
												<bean:message key="anexarDocumentos.anexar.instrucciones.presencial.original"/>																		
											</logic:equal>
										</logic:equal>			            
						            </p>
						        </div>		
						        						        
						        <%-- Anexo de documento --%>     
							        <%-- Limpiamos bean que indica que el anterior ha sido un genérico --%>
								<bean:define id="genericoAnterior" value="" type="java.lang.String"/>
								   			        
								<p class="anexarDoc">					
									
									<strong onmouseover="mostrarInfoDoc(this);">
										<bean:write name="anexo" property="descripcion" />
									</strong>	
									
									<%-- 
										Información de quien debe rellenarlo y de quien lo ha firmado:  																						
									 --%>									
										 <%-- * quién ha rellenado tiene que rellenar el documento cuando lo ha rellenado otro usuario distinto al actual --%>
										<logic:notEmpty name="anexo" property="nifFlujo">									
											<logic:notEqual name="flujoTramitacionNifUsuarioActual" value="<%= anexo.getNifFlujo() %>">	
												<span class="detalleDoc"> 	
					  								<img src="imgs/tramitacion/iconos/clip.gif"/>											
													<logic:equal name="anexo" property="estado" value="S"> 																						
							  								<bean:message key="flujoTramitacion.remitirDocumentoAUsuario.completado" arg0="<%=anexo.getNifFlujo()%>"/>		
													</logic:equal>
													<logic:notEqual name="anexo" property="estado" value="S"> 											
														<bean:message key="flujoTramitacion.remitirDocumentoAUsuario" arg0="<%=anexo.getNifFlujo()%>"/>				
													</logic:notEqual>																
									  			 </span>																							
											</logic:notEqual>
										</logic:notEmpty>
										<!--  * quién lo ha firmado -->																	
										<logic:equal name="anexo" property="firmado" value="true">
											<span class="detalleDoc"> 
													<img src="imgs/tramitacion/iconos/doc_firmar.gif"/>
													<logic:notEmpty name="anexo" property="firmante">
														<bean:message key="pasoAnexar.documentos.documentoFirmadoDigitalmente" arg0="<%=anexo.getFirmante()%>"/>
													</logic:notEmpty>
													<logic:empty name="anexo" property="firmante">
														<bean:message key="pasoAnexar.documentos.documentoFirmadoDigitalmente.noComprobarFirmante"/>
													</logic:empty>
											</span>
										</logic:equal>								
									 									
									<br/>									 									
									
									 									
									<%--  Documento a anexar telematicamente --%>									
									<logic:equal name="anexo" property="anexoPresentarTelematicamente" value="true">						
										
										<%--  Documento no se ha anexado  --%>
										<logic:equal name="anexo" property="estado" value='V'>
											<logic:equal name="flujoDocumentos" property="<%=anexo.getIdentificador()%>" value="true">										
											 	<html:link href="<%= urlAnexar + "&identificador=" + anexo.getIdentificador() + "&instancia=" + anexo.getInstancia() %>"><bean:message key="pasoAnexar.documentos.anexarDocumento"/></html:link>				
											</logic:equal>										
										</logic:equal>
										
										<%--  Documento anexado: vemos si permitimos eliminarlo --%>
										<logic:equal name="anexo" property="estado" value='S'>
											<%-- Generico  --%>
											<logic:equal name="anexo" property="anexoGenerico" value="true">
												<bean:define id="genericoAnterior" name="anexo" property="identificador" type="java.lang.String"/>					
												<bean:define id="genericoAnteriorMaxInstancias" name="anexo" property="anexoGenericoMax" type="java.lang.Integer"/> 
												<bean:message key="pasoAnexar.documentos.ficherosGenericosAnexadoActualmente"/>
												<br/>
												 &nbsp;&nbsp;<strong>- <bean:write name="anexo" property="anexoGenericoDescripcion" /> <logic:notEmpty name="anexo" property="anexoFichero">(<html:link href="<%= urlMostrarAnexo + "&identificador=" + anexo.getIdentificador() + "&instancia=" + anexo.getInstancia() %>"><bean:write name="anexo" property="anexoFichero" /></html:link>)</logic:notEmpty></strong>
												 <logic:equal name="flujoDocumentos" property="<%=anexo.getIdentificador()%>" value="true">										
													  - <html:link href="<%= urlBorrarAnexo + "&identificador=" + anexo.getIdentificador() + "&instancia=" + anexo.getInstancia() %>"><bean:message key="pasoAnexar.documentos.quitarFichero"/></html:link>												
												 </logic:equal>
											</logic:equal>
											<%-- No Generico  --%>
											<logic:equal name="anexo" property="anexoGenerico" value="false">
												<bean:message key="pasoAnexar.documentos.ficheroAnexadoActualmente"/>
												<strong><html:link href="<%= urlMostrarAnexo + "&identificador=" + anexo.getIdentificador() + "&instancia=" + anexo.getInstancia() %>"><bean:write name="anexo" property="anexoFichero" /></html:link></strong>
												<logic:equal name="flujoDocumentos" property="<%=anexo.getIdentificador()%>" value="true">										
													 - <html:link href="<%= urlBorrarAnexo + "&identificador=" + anexo.getIdentificador() + "&instancia=" + anexo.getInstancia() %>"><bean:message key="pasoAnexar.documentos.quitarFichero"/></html:link>
												</logic:equal>												
											</logic:equal>											
										</logic:equal>																				
									</logic:equal>	
									
									<%--  Documento a anexar presencialmente: depende comportamiento en función de si es obligatorio  u opcional --%>																																
									<logic:equal name="anexo" property="anexoPresentarTelematicamente" value="false">	
										
										<%-- Si es obligatorio y no es genérico solo mostramos enlace si tiene plantilla --%>
										<% if (anexo.getObligatorio() == 'S' && !anexo.isAnexoGenerico()) { %>
											<logic:equal name="anexo" property="anexoGenerico" value="false">
												<logic:notEmpty name="anexo" property="anexoPlantilla">
													<logic:equal name="flujoDocumentos" property="<%=anexo.getIdentificador()%>" value="true">										
														<html:link href="<%= urlAnexar + "&identificador=" + anexo.getIdentificador() + "&instancia=" + anexo.getInstancia() %>"><bean:message key="pasoAnexar.documentos.descargarPlantilla"/></html:link>
													</logic:equal>
												</logic:notEmpty>
											</logic:equal>
										<% } %>
										
										<%-- Si es opcional o genérico damos posibilidad a entregarlo o no --%>
										<% if (anexo.getObligatorio() == 'N' || anexo.isAnexoGenerico()) { %>
											
											<%--  Documento no se ha anexado  --%>
											<logic:equal name="anexo" property="estado" value='V'>
												<logic:equal name="flujoDocumentos" property="<%=anexo.getIdentificador()%>" value="true">										
												 	<html:link href="<%= urlAnexar + "&identificador=" + anexo.getIdentificador() + "&instancia=" + anexo.getInstancia() %>"><bean:message key="pasoAnexar.documentos.presentarDocumento"/></html:link>				
												 </logic:equal>
											</logic:equal>																						
											
											<%--  Documento anexado: permitimos eliminarlo --%>											
											<logic:equal name="anexo" property="estado" value='S'>		
											
												<%-- Generico  --%>
												<logic:equal name="anexo" property="anexoGenerico" value="true">
													<bean:define id="genericoAnterior" name="anexo" property="identificador" type="java.lang.String"/>					
													<bean:define id="genericoAnteriorMaxInstancias" name="anexo" property="anexoGenericoMax" type="java.lang.Integer"/> 
													<bean:message key="pasoAnexar.documentos.ficherosGenericosAnexadoActualmente"/>
													<br/>
													 &nbsp;&nbsp;<strong>- <bean:write name="anexo" property="anexoGenericoDescripcion" /> <logic:notEmpty name="anexo" property="anexoFichero">(<html:link href="<%= urlMostrarAnexo + "&identificador=" + anexo.getIdentificador() + "&instancia=" + anexo.getInstancia() %>"><bean:write name="anexo" property="anexoFichero" /></html:link>)</logic:notEmpty></strong>
													 <logic:equal name="flujoDocumentos" property="<%=anexo.getIdentificador()%>" value="true">										
														  - <html:link href="<%= urlBorrarAnexo + "&identificador=" + anexo.getIdentificador() + "&instancia=" + anexo.getInstancia() %>"><bean:message key="pasoAnexar.documentos.quitarFichero"/></html:link>												
													 </logic:equal>
												</logic:equal>
												<%-- No Generico  --%>
												<logic:equal name="anexo" property="anexoGenerico" value="false">
													<bean:message key="pasoAnexar.documentos.ficheroAnexadoActualmente"/>
													<strong><bean:write name="anexo" property="anexoFichero" /></strong>
													<logic:equal name="flujoDocumentos" property="<%=anexo.getIdentificador()%>" value="true">										
														 - <html:link href="<%= urlBorrarAnexo + "&identificador=" + anexo.getIdentificador() + "&instancia=" + anexo.getInstancia() %>"><bean:message key="pasoAnexar.documentos.quitarFichero"/></html:link>
													</logic:equal>												
												</logic:equal>			
																							
											</logic:equal>
											
										<% } %>
										
									</logic:equal>
									
									<%-- Si es genérico cerramos parrafo a la siguiente iteración. Si no lo es, cerramos ahora --%>	
									<logic:notEqual name="anexo" property="anexoGenerico" value="true">	
										</p>	
									</logic:notEqual>
								<%-- Fin Anexo de documento --%>				
					        </logic:notEqual>				        
						</logic:equal>
						<%-- Fin Solo Obligatorios  --%>
					</logic:iterate>
					
					<%-- Fin for de obligatorios/opcionales --%>
					<% } %>
						

					<%-- En caso de que el ultimo documento sea generico, cerramos parrafo --%>	
					<logic:notEmpty name="genericoAnterior">
						<%-- Si la anterior era la última instancia colocamos el botón de Añadir --%>					
						<bean:define id="maxInstancia" name="instanciasGenericos" property="<%=genericoAnterior%>" type="java.lang.Integer"/>									
						<logic:greaterThan name="genericoAnteriorMaxInstancias" value="<%=maxInstancia.toString()%>"> 
							<br/>
							<logic:equal name="flujoDocumentos" property="<%=genericoAnterior%>" value="true">										
								<html:link href="<%= urlAnexar + "&identificador=" + genericoAnterior + "&instancia=" + (maxInstancia.intValue() + 1) %>">
									<bean:message key="pasoAnexar.documentos.anexarDocumento"/>
								</html:link>
							</logic:equal>
						</logic:greaterThan>
						
						<%-- Cerramos parrafo --%>
						</p>
					</logic:notEmpty>							
					
			<%--  Fin div lista documentos --%>			
			</div>	
			
		<%--  Fin comprobación si hay q aportar --%>						
		</logic:equal>					
			
		<div class="sep"></div>
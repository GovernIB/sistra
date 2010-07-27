<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="anexo" name="anexo" type="es.caib.sistra.model.DocumentoFront"/>
<html:xhtml/>
<bean:define id="urlBorrarAnexo">
        <html:rewrite page="/protected/borrarAnexo.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<script type="text/javascript">
<!--
	
	var mensajeEnviando = '<bean:message key="anexarDocumentos.mensajeAnexar"/>';
	
	
	<logic:equal name="anexo" property="firmar" value="true">
		<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
					 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									
		
			<bean:define id="contentTypeFirma" name="anexo" type="java.lang.String" property="contentType" />
			
			var contentType = '<%= contentTypeFirma %>';
		
			function firmarCAIB(form){			
				var applet = whichApplet();
				var firma = '';	
				var pin = document.formFirma.PIN.value;
		
				applet.setPassword( pin );
				
				var rutaFichero = document.anexarDocumentoForm.datos.value;
				if ( rutaFichero == '' )
				{
					alert( "<bean:message key="firmarDocumento.introducirFichero"/>" );
					document.anexarDocumentoForm.datos.focus();
					document.anexarDocumentoForm.datos.select();
					return false;
				}
				
				firma = applet.firmarFichero( rutaFichero, contentType );	
				if (firma == null || firma == ''){
					alert(applet.getLastError());
					return false;
				}
				form.firma.value = firma;	
				return true;
			}
		</logic:equal>
		
		<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
					 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
			
			function prepararEntornoFirma(){
				cargarAppletFirma();
			}
			
			function firmarAFirma(form){
			
			  if (clienteFirma == undefined) { 
		          alert("No se ha podido instalar el entorno de firma");
		          return false;
	        	}
			
				var rutaFichero = document.anexarDocumentoForm.datos.value;
				if ( rutaFichero == '' )
				{
					alert( "<bean:message key="firmarDocumento.introducirFichero"/>" );
					document.anexarDocumentoForm.datos.focus();
					document.anexarDocumentoForm.datos.select();
					return false;
				}
				
				clienteFirma.initialize();
				clienteFirma.setSignatureAlgorithm("sha1WithRsaEncryption");
				clienteFirma.setSignatureMode("EXPLICIT");
				clienteFirma.setSignatureFormat("CMS");
				clienteFirma.setFileuri(rutaFichero);
				
				clienteFirma.sign();
				
				if(clienteFirma.isError()){
					error = 'Error: '+clienteFirma.getErrorMessage();
					alert(error);
					return false;
				}else{	
				     firma = clienteFirma.getSignatureBase64Encoded();
				     form.firma.value = firma;
				     return true;
				}
			}
			
		</logic:equal>
	</logic:equal>	
	
	function anexarDocumento(form,firmar)
	{
	
		<logic:equal name="anexo" property="anexoGenerico" value="true">			
			if 	(form.descPersonalizada.value==''){
				alert( "<bean:message key="anexarDocumento.generico.descPersonalizada.nulo" />" );
				return;
			}
		</logic:equal>	
	
		// Comprobamos si se debe firmar
		//alert("pendiente firmar");
		if ( firmar )
		{		
			// Firmamos
			<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
						 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
				if (!firmarAFirma(form)) return;
			</logic:equal>
			<logic:equal name="implementacionFirma" value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									
				if (!firmarCAIB(form)) return;					
			</logic:equal>			
		}
				
		accediendoEnviando(mensajeEnviando);

		// Enviar formulario
		form.submit();
	}
	
//-->
</script>

				<h2><bean:message key="anexarDocumentos.anexarDocumento"/> <bean:write name="anexo" property="descripcion" /></h2>
				
				<!--  Informacion documento -->
				<div>
					<bean:write name="anexo" property="informacion" filter="false" />					
				</div>
				<!--  <div class="ultimo"></div>  -->
				
				<!-- descargar plantilla  -->
				<logic:notEmpty name="anexo" property="anexoPlantilla">	
					<h2 class="descargarPlantilla">- <bean:message key="anexarDocumentos.descargarPlantilla"/></h2>
					<p class="apartado"><bean:message key="anexarDocumentos.descargarPlantilla.instrucciones" /></p>
					<div id="listadoPlantillas">
						<div class="iconos">
						<!-- 
						<logic:equal name="anexo" property="obligatorio" value='S'><img src="imgs/tramitacion/iconos/doc_obligatorio.gif" alt="Documento obligatorio" title="Documento obligatorio" /></logic:equal><logic:equal name="anexo" property="obligatorio" value='N'><img src="imgs/tramitacion/iconos/doc_opcional.gif" alt="Documento opcional" title="Documento opcional" /></logic:equal><logic:equal name="anexo" property="anexoCompulsar" value="true"><img src="imgs/tramitacion/iconos/doc_compulsado.gif" alt="Documento compulsado" title="Documento compulsado"/></logic:equal> 
						-->
						<img src="imgs/tramitacion/iconos/plantilla.gif" alt="<bean:message key="anexarDocumentos.descargarPlantilla.plantillaDocumento"/>" title="Plantilla del documento" /></div>
						<div class="enlaceDescargar">
							<a id="doc1" href="<%= anexo.getAnexoPlantilla() %>" target="_blank"><bean:message key="anexarDocumentos.descargarPlantilla.instrucciones.descargarPlantilla"/></a>
						</div>
					</div>
					<!-- <div class="ultimo"></div>  -->
				</logic:notEmpty>

	
				<!-- firma documento -->
				<logic:equal name="anexo" property="firmar" value="true">
					<h2 class="firmarAnexo">- <bean:message key="firmarDocumento.firmar"/></h2>
					<!-- anexar documentos -->
					<div id="anexarDocs">														
						<!--  Instrucciones firma -->
						<p class="apartado">					
							<logic:notEmpty name="anexo" property="firmante">
								<bean:message key="firmarDocumento.instrucciones.firmarOtro" arg0="<%=anexo.getFirmante()%>"/>
							</logic:notEmpty>
							<logic:empty name="anexo" property="firmante">
								<bean:message key="firmarDocumento.instrucciones"/>
							</logic:empty>		
						</p>
						<!--  Instrucciones firma y Applet firma (depende de implementacion firma) -->
						<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
									 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
							<p class="apartado">
								<bean:message key="firmarDocumento.aFirma.anexo.instrucciones"/>
							</p>
						</logic:equal>
						<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
									 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									
							<p class="apartado"><bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo" /></p>
							<p class="apartado"><input type="button" value="<bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo.boton" />" title="<bean:message key="login.certificado.instrucciones.iniciarDispositivo.boton" />" onclick="cargarCertificado();" /></p>
							<p class="apartado">
								<bean:message key="firmarDocumento.certificadosDisponibles"/>
							</p>
							<form name="formFirma">		
								<p class="apartado"> 									
									<jsp:include page="/firma/caib/applet.jsp" flush="false"/>	
								</p>												
								<!--  PIN  -->							
								<p class="apartado">
									<bean:message key="firmarDocumento.PINCertificado" /><input type="password" name="PIN" id="PIN" class="txt"/>										
								</p>							
							</form>	
						</logic:equal>
					</div>	
					<!-- <div class="ultimo"></div> -->			
				</logic:equal>
				
				
				<!-- anexar documentos -->
				<h2 class="adjuntarAnexo">- <bean:message key="anexarDocumentos.anexar"/></h2>
				<!-- anexar documentos -->
				<div id="anexarDocs">								
					<!--  Anexar documento telematicamente -->
					<logic:equal name="anexo" property="anexoPresentarTelematicamente" value="true">
						<html:form action="/protected/anexarDocumento" enctype="multipart/form-data" >																			
							<logic:equal name="anexo" property="anexoGenerico" value="true">
								<p class="apartado">
									<bean:message key="anexarDocumentos.anexar.instrucciones.parrafoGenericos"/>
									<br/>								
									<html:text property="descPersonalizada" size="70" maxlength="200"/>
								</p>
							</logic:equal>						
							<p class="apartado">	
								<!--  Instrucciones anexo -->
								<bean:message key="anexarDocumentos.anexar.instrucciones.parrafo1"/>
								<br/>
								<html:hidden name="irAAnexarForm" property="ID_INSTANCIA"/>
								<html:hidden name="irAAnexarForm" property="instancia" />	
								<html:hidden name="irAAnexarForm" property="identificador" />
								<html:hidden property="firma" />
								<html:hidden property="extensiones" value="<%=anexo.getAnexoExtensiones()%>" />
								<html:hidden property="tamanyoMaximo"  value="<%=Integer.toString(anexo.getAnexoTamanyo())%>" />																					
								<html:file property="datos" styleId="datos" size="70"/> 						
								<input name="anexarDocsBoton" id="anexarDocsBoton" type="button" value="<bean:message key="anexarDocumentos.anexar.boton"/>" onclick="javascript:anexarDocumento(this.form,<%=anexo.isFirmar()%>);"/>
							</p>
						</html:form>
						<!--  alerta tamanyos -->
						<p class="alerta">
							<bean:message key="anexarDocumentos.anexar.instrucciones.parrafo2.texto1"/> <strong><bean:write name="anexo" property="anexoExtensiones"/></strong> <bean:message key="anexarDocumentos.anexar.instrucciones.parrafo2.texto2"/> <strong><bean:write name="anexo" property="anexoTamanyo"/> Kb</strong>.
						</p>
							
						<logic:equal name="anexo" property="anexoCompulsar" value="true">				
							<p class="apartado"><bean:message key="anexarDocumentos.anexar.instrucciones.telematico.compulsar"/></p>									
						</logic:equal>
					</logic:equal>	
					
														
					<!--  Anexar documento presencialmente -->					
					<logic:equal name="anexo" property="anexoPresentarTelematicamente" value="false">												
						<logic:equal name="anexo" property="anexoFotocopia" value="true">				
							<logic:equal name="anexo" property="anexoCompulsar" value="true">				
								<p class="apartado"><bean:message key="anexarDocumentos.anexar.instrucciones.presencial.fotocopia.compulsar"/></p>																			
							</logic:equal>
							<logic:equal name="anexo" property="anexoCompulsar" value="false">				
								<p class="apartado"><bean:message key="anexarDocumentos.anexar.instrucciones.presencial.fotocopia"/></p>									
							</logic:equal>
						</logic:equal>
						<logic:equal name="anexo" property="anexoFotocopia" value="false">				
							<p class="apartado"><bean:message key="anexarDocumentos.anexar.instrucciones.presencial.original"/></p>																			
						</logic:equal>		
						<!--  Boton Presentar documento para documentos no telematico -->
						<!--  Solamente se mostrará para documentos: 
									* obligatorios y genéricos
									* opcionales
						-->
						<% if (  (anexo.getObligatorio() == 'S' && anexo.isAnexoGenerico() ) ||
								 (anexo.getObligatorio() == 'N') ){ 
						%>																								
								<p class="apartado"><bean:message key="anexarDocumentos.anexar.instrucciones.presencial"/></p>
								<html:form action="/protected/anexarDocumento">
									<!--  Descripción personalizada si es genérico -->
									<logic:equal name="anexo" property="anexoGenerico" value="true">
										<p class="apartado">
											<bean:message key="anexarDocumentos.anexar.instrucciones.parrafoGenericos"/>
											<br/>								
											<html:text property="descPersonalizada" size="70" maxlength="200"/>
										</p>	
									</logic:equal>
									<p class="apartado">
										<html:hidden name="irAAnexarForm" property="ID_INSTANCIA"/>
										<html:hidden name="irAAnexarForm" property="instancia" />	
										<html:hidden name="irAAnexarForm" property="identificador" />							
										<input name="anexarDocsBoton" id="anexarDocsBoton" type="button" value="<bean:message key="anexarDocumentos.marcarPresentar"/>" onclick="javascript:anexarDocumento(this.form,<%=anexo.isFirmar()%>);"/>
									</p>
								</html:form>	
						<% } %>
						
					</logic:equal>
					
					<!--  Enlace volver  -->
					<p class="volver"><html:link action="/protected/irAPaso" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"><bean:message key="anexarDocumentos.anexar.instrucciones.volverListadoDocumentos"/></html:link></p>					
				</div>
				<div class="sep"></div>
				<!-- capa accediendo formularios -->
				<div id="capaInfoFondo"></div>
				<div id="capaInfoForms"></div>
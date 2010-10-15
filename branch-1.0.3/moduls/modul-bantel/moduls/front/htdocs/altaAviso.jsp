<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<script type="text/javascript" src="js/jquery.selectboxes.pack.js"></script>
<script type="text/javascript" src="js/ajuda.js"></script>
<script type="text/javascript" src="js/funcions.js"></script>
<script type="text/javascript" src="js/mensaje.js"></script>	
<script type="text/javascript">
     function mostrarArbolUnidades(url) {
        obrir(url, "Arbol", 540, 400);
     }
</script>


<!--  Scripts para firma (depende implementacion) -->
	<logic:equal name="<%=es.caib.bantel.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
					 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/constantes.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/time.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/appletHelper.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/instalador.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/firma.js"></script>	
	<script type="text/javascript">		
		baseDownloadURL = "<%=request.getContextPath()%>/firma/aFirma";
		base = "<%=request.getContextPath()%>/firma/aFirma";
	</script>
	</logic:equal>
		
	<logic:equal name="<%=es.caib.bantel.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
					 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">	
			<script type="text/javascript" src="<%=request.getContextPath()%>/firma/caib/js/firma.js"></script>
	</logic:equal>
	
<!--  FIRMA DIGITAL -->
<script type="text/javascript">
<!--
	<logic:equal name="<%=es.caib.bantel.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
					 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									
		
		var contentType = '<%= es.caib.util.FirmaUtil.obtenerContentTypeCAIB(es.caib.util.FirmaUtil.CAIB_DOCUMENT_NOTIFICACIO_CONTENT_TYPE) %>';
		var mensajeEnviando = '<bean:message key="anexarDocumentos.mensajeAnexar"/>';
		
		function firmarCAIB(form){		
			var firma = '';	
			var i = 0;
			var applet;
			var pin;
			applet = whichApplet();
			pin = form.PIN.value;
			applet.setPassword( pin );
			firma = applet.firmarFicheroB64( $('#documentoB64').val(), contentType );	
			if (firma == null || firma == ''){
				alert(applet.getLastError());
				return false;
			}
			$('#firma').val(firma);
			return true;
		}
	
	</logic:equal>	
	
	<logic:equal name="<%=es.caib.bantel.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
					 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
		
		function prepararEntornoFirma(){
			cargarAppletFirma();
		}
		
		function firmarAFirma(form){
	      var firma = '';
	      var i = 0;
		  if (clienteFirma == undefined) { 
	          alert("No se ha podido instalar el entorno de firma");
	          return false;
	       	}
	       	clienteFirma.initialize();
			clienteFirma.setSignatureAlgorithm("sha1WithRsaEncryption");
			clienteFirma.setSignatureMode("EXPLICIT");
			clienteFirma.setSignatureFormat("CMS");
			if($('#documentoB64').val() == null || $('#documentoB64').val() == ''){
				return false;
			}
			
			// Pasamos de b64 urlSafe a b64
			var b64 = b64UrlSafeToB64($('#documentoB64').val());
			clienteFirma.setData(b64);
			clienteFirma.sign();
			if(clienteFirma.isError()){
				error = 'Error: '+clienteFirma.getErrorMessage();
				alert(error);
				return false;
			}else{	
			     firma = clienteFirma.getSignatureBase64Encoded();
			}
			document.getElementById("firma").value=firma;
			return true;
		}
	</logic:equal>	
	
	function anexarDocumento(form)
	{
		Mensaje.mostrar({tipo: "mensaje", modo: "ejecutando", fundido: "si", titulo: "Firmando documento..."});
	
		<logic:equal name="<%=es.caib.bantel.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
					 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
			if (!firmarAFirma(form)){
				Mensaje.cancelar();	 
				return;
			}
		</logic:equal>
		<logic:equal name="<%=es.caib.bantel.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
					 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">	
			if (!firmarCAIB(form)){ 
				Mensaje.cancelar();	
				return;
			}
		</logic:equal>
				
		// Enviar formulario
		var url_json = '<html:rewrite page="/firmarDocumentoAnexo.do"/>';
		var data = "atributo=documentosAltaAviso&nombre="+$('#nombreFirmar').val()+"&codigoRDS="+$('#codigoRDSFirmar').val()+"&claveRDS="+$('#claveRDSFirmar').val()+"&firma="+$('#firma').val();		
		// $.ajaxSetup({scriptCharset: "utf-8", contentType: "application/json; charset=utf-8"});
		$.postJSON(url_json, data, function(datos) {
              	//$('#documentos').html(datos.taula);
              	
                idInfo = "#infoFirmado-" +	$('#codigoRDSFirmar').val();
              	
				$('#documentoB64').val('');
				$('#firma').val('');
				$('#nombreFirmar').val('');
				$('#codigoRDSFirmar').val('');
				$('#claveRDSFirmar').val('');
				
				if (datos.error==""){								
					$(idInfo).html(htmlInfoFirmado);
				}else{
					alert(datos.error);
				}
				
				$('#firmarDocumentosApplet').hide('slow');
				$('#anexar').hide('slow');
				
		        Mensaje.cancelar();	
        	});
        	

	}
	<logic:equal name="<%=es.caib.bantel.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
					 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
		prepararEntornoFirma();
	</logic:equal>
	
	function botonCancelarFirmar() {
		$('#firmarDocumentosApplet').hide('slow');
	}
	
//-->
</script>

<script type="text/javascript">

var htmlInfoFirmado = "- <strong><bean:message key="detalleTramite.datosTramite.envio.firmado"/></strong>";


function altaDocument(){
	
	Mensaje.mostrar({tipo: "mensaje", modo: "ejecutando", fundido: "si", titulo: "Enviando datos..."});
	
	var archivo = $('input[type=file]').val();
	var permitida = false;
	if(archivo != null && archivo != ''){
		var extension = (archivo.substring(archivo.lastIndexOf("."))).toLowerCase();
		if(extension != null && extension != ''){
			var extensiones_permitidas = new Array(".docx", ".doc", ".odt"); 
			for (var i = 0; i < extensiones_permitidas.length; i++) {
				if (extensiones_permitidas[i] == extension) {
					permitida = true;
					break;	
				}
			} 			
		}
	}
	
	if(permitida){
		document.forms["0"].action='<html:rewrite page="/altaDocumentoAviso.do"/>';
		document.forms["0"].flagValidacion.value ="documento";
		document.forms["0"].rutaFitxer.value = document.forms["0"].documentoAnexoFichero.value;
		document.forms["0"].submit();
	}else{				
		alert("<bean:message key="error.aviso.extensiones.fichero"/>");
		Mensaje.cancelar();
	}	
	
	return permitida;
}

function alta(){
	if(confirm ( "<bean:message key='aviso.alta.confirmacion' />" )){
		Mensaje.mostrar({tipo: "mensaje", modo: "ejecutando", fundido: "si", titulo: "Enviando datos..."});
		document.forms["0"].rutaFitxer.value = document.forms["0"].documentoAnexoFichero.value;
		document.forms["0"].action='<html:rewrite page="/realizarAltaAviso.do"/>';
		document.forms["0"].submit();
		return true;
	}else{
		return false;
	}
}
function mostrarFirmar(nombre,codigoRDS,claveRDS){
	var url_json = '<html:rewrite page="/irFirmarDocumento.do"/>';
	var data ='atributo=documentosAltaAviso&nombre='+nombre+'&codigoRDS='+codigoRDS+'&claveRDS='+claveRDS;
	// $.ajaxSetup({scriptCharset: "utf-8", contentType: "application/json; charset=utf-8"});
	$.postJSON(
		url_json,data,
		function(datos){
			if (datos.error==""){								
				var nombreF = '<p class="titol"> <bean:message key="aviso.firmar"/> </p>'; 
				//$('#divNombreFirmar').html(nombreF);			
				//$('#documentos').html(datos.taula);
				$('#documentoB64').val(datos.base64);
				$('#firmarDocumentosApplet').show('slow');
				$('#anexar').hide('slow');
				$('#firma').val('');
				$('#nombreFirmar').val(nombre);
				$('#codigoRDSFirmar').val(codigoRDS);
				$('#claveRDSFirmar').val(claveRDS);
			}else{
				alert(datos.error);
			}
			
		}
	);
}

function mostrarAnexarDocumentos(){
	$('#anexar').show('slow');
	$('#firmarDocumentosApplet').hide('slow');
	$("#botonMostrarAnexar").attr("disabled","disabled");
}

function esconderAnexarDocumentos(){
	$('#anexar').hide('slow');
	$('#firmarDocumentosApplet').hide('slow');
	$("#botonMostrarAnexar").removeAttr("disabled");
}

function volver(identificadorExp,unidadAdm,claveExp){
	document.forms["0"].action='<html:rewrite page="/recuperarExpediente.do?identificadorExp='+identificadorExp+'&unidadAdm='+unidadAdm+'&claveExp='+claveExp+'" />';
	document.forms["0"].submit();
}

$(document).ready(function(){
		$('#firmarDocumentosApplet').hide();
		$('#anexar').hide();
		
		$.postJSON = function(url, data, callback) {
        	return jQuery.ajax({
		        'type': 'POST',
		        'url': url,
		        'data': data,
		        'dataType': 'json',
		        'success': callback
		    });
		};
		        
	}
);

</script>
<bean:define id="urlConfirmacion" type="java.lang.String">
	<html:rewrite href="/zonaperback/init.do" paramId="lang" paramName="<%= Globals.LOCALE_KEY  %>" paramProperty="language" paramScope="session"/>
</bean:define>
<bean:define id="urlArbol">
    <html:rewrite page="/arbolUnidades.do"/>
</bean:define>
<bean:define id="urlAbrirDocumento"  type="java.lang.String">
	<html:rewrite page="/abrirDocumento.do"/>
</bean:define>
		<!-- ajuda boto -->
		<button id="ajudaBoto" type="button" title="Activar ajuda"><img src="imgs/botons/ajuda.gif" alt="" /> <bean:message key="confirmacion.ayuda"/></button>
		<!-- /ajuda boto -->
		<div id="opcions">
				&nbsp;
		</div>
		<!-- titol -->
		<!--<h1>Gestión de expedientes</h1>-->
		<!-- /titol -->
		
		<!-- ajuda -->
		<div id="ajuda">
			<h2><bean:message key="ajuda.titulo"/></h2>
			<br/>
			<bean:message key="ajuda.CampoObligarorio"/>
				<bean:message key="ajuda.aviso.titulo"/>
				<bean:message key="ajuda.aviso.texto"/>
			<bean:message key="ajuda.finCampoObligarorio"/>
			
				<bean:message key="ajuda.aviso.textoSMS"/>
		</div>
		<!-- /ajuda -->
		
		<html:errors/>
		
		
		<!-- continguts -->
		<div class="continguts">
			<%--<h1><bean:write  name="detalleAvisoForm" property="descripcionExpediente"/></h1>--%>
		
			<p class="titol"><bean:message key="aviso.alta"/></p>
			
			<html:form action="realizarAltaAviso" enctype="multipart/form-data" styleClass="remarcar opcions">
			<html:hidden property="descripcionExpediente"/>
			<html:hidden property="flagValidacion" value="alta"/>
			<html:hidden property="rutaFitxer"/>
			<html:hidden property="unidadAdministrativa" />
			<html:hidden property="identificadorExpediente" />
			<html:hidden property="claveExpediente" />
			<html:hidden property="idioma" />
				<p>
					<label for="titulo"><bean:message key="aviso.titulo"/></label>
					<html:text property="titulo" styleClass="pc40"/>
				</p>
				
				<p>
					<label for="texto"><bean:message key="aviso.texto"/></label>
					<html:textarea property="texto" rows="5"   styleClass="pc40"/>
				</p>
				
				<p>
					<label for="textoSMS"><bean:message key="aviso.textoSMS"/></label>
					<html:textarea property="textoSMS" rows="5"  styleClass="pc40"/>
				</p>
				
				<!-- escritorio_docs -->
				<div id="escritorio_docs">
				
					<p class="titol">
						<bean:message key="aviso.documentoanexo"/>
					</p>
					
					<p class="boton">
						<input id="botonMostrarAnexar" type="button" value="<bean:message key='aviso.anexar.documento'/>" onclick="mostrarAnexarDocumentos();"/>
					</p>
					
					<div id="contenido_docs">
						
						<!-- documentos -->
						<div id="documentos">
						
								<logic:empty name="documentosAltaAviso" scope="session">
									<p class="noHay">No hay documentos</p>
								</logic:empty>
								
								<logic:notEmpty name="documentosAltaAviso" scope="session">
									<ul>
									<logic:iterate id="document" name="documentosAltaAviso" type="es.caib.bantel.front.util.DocumentoFirmar" scope="session">
											<li>
												<bean:define id="documento" name="document" property="titulo" type="java.lang.String" />
												<bean:define id="tituloB64" name="document" property="tituloB64" type="java.lang.String" />
												<bean:define id="codigoRDS" name="document" property="codigoRDS" type="java.lang.Long" />
												<bean:define id="claveRDS" name="document" property="claveRDS" type="java.lang.String" />
												
													<a href='<%=urlAbrirDocumento%>?codigo=<%=codigoRDS %>&clave=<%=claveRDS %>' > 
														 <bean:write name="document" property="titulo" />
													</a>
													
													<logic:equal name="document" property="vistoPDF" value="true">
														<div id="infoFirmado-<%=codigoRDS %>"  style="display:inline;">
															<logic:equal name="document" property="firmar" value="false">															
																- <a class="firmar" onclick="mostrarFirmar('<%=tituloB64 %>','<%=codigoRDS %>','<%=claveRDS %>')">Firmar</a>
															</logic:equal>
															<logic:equal name="document" property="firmar" value="true">
																- <strong><bean:message key="detalleTramite.datosTramite.envio.firmado"/></strong>
															</logic:equal>
														</div>
													</logic:equal>
											</li>
									</logic:iterate>
									</ul>
								</logic:notEmpty>
								
						</div>
						<!-- /documentos -->
						
						<!-- anexar -->
						<div id="anexar">
							<p>
								<bean:message key="aviso.explicativo.fichero"/>
							</p>
							<p>
								<bean:message key="aviso.extensiones.fichero"/>
							</p>
							<p>
								<label><bean:message key="aviso.titulo"/></label>
								<html:text property="documentoAnexoTitulo"/>
								<label class="enLinia"><bean:message key="aviso.fichero"/></label>
								<html:file property="documentoAnexoFichero" />

								<!--<html:submit styleId="botonAlta" styleClass="botonAlta" onclick="if(altaDocument()){return true;}else{return false;}"><bean:message key="aviso.alta.documento"/></html:submit>-->
								<input id="botonAlta" class="botonAlta" type='button' value='<bean:message key="aviso.alta.documento"/>' onclick="if(altaDocument()){return true;}else{return false;}"/>
								, o <a id="botonCancelar" onclick="esconderAnexarDocumentos();">Cancelar</a>
							</p>
						</div>
						<!-- /anexar -->
						
						<!-- firmar -->
						<div id="firmarDocumentosApplet">
							<input type='hidden' id='documentoB64' name='documentoB64' value='' />
							<input type='hidden' id='firma' name='firma' value=''/>
							<input type='hidden' id='nombreFirmar' name='nombreFirmar' value=''/>
							<input type='hidden' id='codigoRDSFirmar' name='codigoRDSFirmar' value=''/>
							<input type='hidden' id='claveRDSFirmar' name='claveRDSFirmar' value=''/>		
							<div id="divNombreFirmar">
							</div>
						<!-- anexar documentos -->
							<div id="anexarDocs">
								<logic:equal name="<%=es.caib.bantel.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
								 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
									<p class="apartado">
										<bean:message key="firmarDocumento.aFirma.anexo.instrucciones"/>
									</p>
								</logic:equal>
								<logic:equal name="<%=es.caib.bantel.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
								 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">	
									<!--  Instrucciones firma -->
									<p>		
										<bean:message key="firmarDocumento.instrucciones"/>	<bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo" />
									</p>
									<p>
										<label><bean:message key="firmarDocumento.certificadosDisponibles"/> </label>					
										<jsp:include page="/firma/caib/applet.jsp" flush="false"/>
										&nbsp;
										<input type="button" value="<bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo.boton" />" title="<bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo.boton" />" onclick="cargarCertificado();" style="vertical-align: top; margin-top: 4px;"/>
									</p>
									<!--  PIN  -->							
									<p>
										<label><bean:message key="firmarDocumento.PINCertificado" />: </label>
										<input type="password" name="PIN" id="PIN" class="txt"/>										
									</p>
								</logic:equal>
							</div>	
							<p>
								<label>&nbsp;</label>
								<input type="button" value="<bean:message key='aviso.firmar'/>" onclick="anexarDocumento(this.form);"/>, o <a onclick="botonCancelarFirmar();">Cancelar</a>
							</p>
						</div>
						<!-- /firmar -->
					
					</div>
				
				</div>
				<!-- /escritorio_docs -->
				
				<p class="botonera">
					<html:submit onclick="if(alta()){return true;}else{return false;}"><bean:message key="aviso.alta"/></html:submit>
					<input type="button" onclick="volver('<bean:write name="detalleAvisoForm" property="identificadorExpediente"/>','<bean:write name="detalleAvisoForm" property="unidadAdministrativa"/>','<bean:write name="detalleAvisoForm" property="claveExpediente"/>')" value="<bean:message key="aviso.cancelar"/>"/>
				</p>
			
			</html:form>
			
		</div>
		<!-- /continguts -->
		
		<div id="fondo"></div>
		

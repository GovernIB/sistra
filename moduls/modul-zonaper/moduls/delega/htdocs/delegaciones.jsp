<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="es.caib.zonaper.delega.util.PermisosUtil" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<link href="estilos/calendar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.selectboxes.pack.js"></script>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/lang/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>
<script type="text/javascript" src="js/calendario.js"></script>
<script type="text/javascript" src="js/fechas.js"></script>
<script type="text/javascript" src="js/funcions.js"></script>
<script type="text/javascript" src="js/mensaje.js"></script>
<!--  Scripts para firma (depende implementacion) -->
<logic:equal name="<%=es.caib.zonaper.delega.Constants.IMPLEMENTACION_FIRMA_KEY%>"
					 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/constantes.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/deployJava.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/time.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/instalador.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/firma.js"></script>	
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/utils.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/aFirma/js/configClienteaFirmaSistra.js"></script>
	<script type="text/javascript">		
		base = "<%=request.getAttribute("urlSistraAFirma")%><%=request.getContextPath()%>/firma/aFirma";
		baseDownloadURL = "<%=request.getAttribute("urlSistraAFirma")%><%=request.getContextPath()%>/firma/aFirma";
	</script>
</logic:equal>
		
<logic:equal name="<%=es.caib.zonaper.delega.Constants.IMPLEMENTACION_FIRMA_KEY%>"
		value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">	
	<script type="text/javascript" src="<%=request.getContextPath()%>/firma/caib/js/firma.js"></script>
</logic:equal>
<script type="text/javascript">
<!--
<logic:equal name="<%=es.caib.zonaper.delega.Constants.IMPLEMENTACION_FIRMA_KEY%>"
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
	
<logic:equal name="<%=es.caib.zonaper.delega.Constants.IMPLEMENTACION_FIRMA_KEY%>"
				 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
		
	function prepararEntornoFirma(){
		cargarAppletFirma(sistra_ClienteaFirma_buildVersion);
	}
		
	function firmarAFirma(form){
	    var firma = '';
	    var i = 0;
		if (clienteFirma == undefined) { 
	       alert("No se ha podido instalar el entorno de firma");
	       return false;
	   	}
	   	clienteFirma.initialize();
	   	clienteFirma.setShowErrors(false);
	   	clienteFirma.setSignatureAlgorithm(sistra_ClienteaFirma_SignatureAlgorithm);
		clienteFirma.setSignatureMode(sistra_ClienteaFirma_SignatureMode);
		clienteFirma.setSignatureFormat(sistra_ClienteaFirma_SignatureFormat);
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
			firma = b64ToB64UrlSafe(firma);
   		}
		document.getElementById("firma").value=firma;
		return true;
	}
</logic:equal>	

<logic:equal name="<%=es.caib.zonaper.delega.Constants.IMPLEMENTACION_FIRMA_KEY%>"
				 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
	prepararEntornoFirma();
</logic:equal>
//-->
</script>
	
<script type="text/javascript">
//funcion que se ejecutan solo entrar en la pagina
$(document).ready(function(){
		$.postJSON = function(url, data, callback) {
        	return jQuery.ajax({
		        'type': 'POST',
		        'url': url,
		        'data': data,
		        'dataType': 'json',
		        'success': callback
		    });
		};
		if($("#habilitada").val() =='N'){
			$("#divRepresentante").hide();
		}else{
			if($("#representante").val() =='N'){
				$("#divRepresentante").show();
			}else{
				$("#divRepresentante").hide();
			}
		}
	}
);


function llenarNombreDelegado(){
	var nif = $("#nifDelegado").val();	
	var url_json = '<html:rewrite page="/buscarNombreDelegado.do"/>';
	var data ='nif='+nif;
	$.postJSON(url_json, data, 
		function(datos){
			if(datos.error == ""){
				$("#nombreDelegadoAlta").val(datos.nombre);
				$("#nifDelegado").val(datos.nif);
			}else{
				alert(datos.error);
			}
	});
}

function asignarRepresentante(form){
	if($("#nombreDelegadoAlta").val() == ""){
		alert("<bean:message key="delegaciones.buscar.usuario"/>");
		return;
	}
	Mensaje.mostrar({tipo: "mensaje", modo: "ejecutando", fundido: "si", titulo: "Enviando datos..."});
	var nifDelegante = $("#nif").val();	
	var nifDelegado = $("#nifDelegado").val();	
	var fechaInicioDelegacion = $("#fechaInicioDelegacion").val();	
	var fechaFinDelegacion = $("#fechaFinDelegacion").val();	
	var url_json = '<html:rewrite page="/crearDocumentoDelegacion.do"/>';
	var data ='nifDelegante='+nifDelegante+'&nifDelegado='+nifDelegado+'&fechaInicioDelegacion='+fechaInicioDelegacion+'&fechaFinDelegacion='+fechaFinDelegacion;
	$.postJSON(url_json, data, 
		function(datos){
			if (datos.error==""){
				Mensaje.mostrar({tipo: "mensaje", modo: "ejecutando", fundido: "si", titulo: "Firmando datos..."});
				$('#documentoB64').val(datos.datos);
				<logic:equal name="<%=es.caib.zonaper.delega.Constants.IMPLEMENTACION_FIRMA_KEY%>"
						 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
					if (!firmarAFirma(form)){
						Mensaje.cancelar();	
						return;
					}
				</logic:equal>
				<logic:equal name="<%=es.caib.zonaper.delega.Constants.IMPLEMENTACION_FIRMA_KEY%>"
							 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">	
					if (!firmarCAIB(form)){ 
						Mensaje.cancelar();	
						return;
					}
				</logic:equal>	
				var url_json2 = '<html:rewrite page="/asignarRepresentante.do"/>';
				var data2 ='nifDelegante='+nifDelegante+'&codigoRDS='+datos.codigo+'&claveRDS='+datos.clave+'&firmaJSP='+$('#firma').val();
				$.postJSON(url_json2, data2, function(datos2) {
					if (datos2.error==""){	
						alert("<bean:message key="delegaciones.alta.correcta"/>");							
						document.detalleEntidadForm.submit();
			            Mensaje.cancelar();	
					}else{
						Mensaje.cancelar();	
						alert(datos2.error);
						if(datos2.representante == 'N'){
							$("#divRepresentante").show();
						}else{
							$("#divRepresentante").hide();
						}
					}
		        });
			}else{
				Mensaje.cancelar();	
				alert(datos.error);
				if(datos.representante == 'N'){
					$("#divRepresentante").show();
				}else{
					$("#divRepresentante").hide();
				}
			}
	});
}


function habilitarEntidad(){
	Mensaje.mostrar({tipo: "mensaje", modo: "ejecutando", fundido: "si", titulo: "Enviando datos..."});
	var nifDelegante = $("#nif").val();	
	var url_json = '<html:rewrite page="/habilitarEntidad.do"/>';
	var data ='nif='+nifDelegante+'&habilitar=S';
	$.postJSON(url_json, data, 
		function(datos){
			if (datos.error==""){
				Mensaje.cancelar();	
				document.detalleEntidadForm.submit();						
			}else{
				Mensaje.cancelar();	
				alert(datos.error);
			}
	});
	
}

function deshabilitarEntidad(){
	Mensaje.mostrar({tipo: "mensaje", modo: "ejecutando", fundido: "si", titulo: "Enviando datos..."});
	var nifDelegante = $("#nif").val();	
	var url_json = '<html:rewrite page="/habilitarEntidad.do"/>';
	var data ='nif='+nifDelegante+'&habilitar=N';
	$.postJSON(url_json, data, 
		function(datos){
			if (datos.error==""){
				Mensaje.cancelar();	
				document.detalleEntidadForm.submit();					
			}else{
				Mensaje.cancelar();	
				alert(datos.error);
			}
	});
	
}

function anularRepresentante(codigo){
	Mensaje.mostrar({tipo: "mensaje", modo: "ejecutando", fundido: "si", titulo: "Enviando datos..."});
	var url_json = '<html:rewrite page="/anularRepresentante.do"/>';
	var data ='codigoDelegacion='+codigo;
	$.postJSON(url_json, data, 
		function(datos){
			if (datos.error==""){
				alert("<bean:message key="delegaciones.anulacion.correcta"/>");							
				document.detalleEntidadForm.submit();
				Mensaje.cancelar();	
			}else{
				Mensaje.cancelar();	
				alert(datos.error);
			}
	});
}

function volver(){
	document.detalleEntidadForm.action='<html:rewrite page="/init.do" />';
	document.detalleEntidadForm.submit();
}
</script>
		<bean:define id="urlHabilitarEntidad"  type="java.lang.String">
			<html:rewrite page="/habilitarEntidad.do"/>
		</bean:define>
						
		<!-- continguts -->
		<div class="continguts">
			<html:errors/>		
			<div id="divHabilitar">
				<p class="titol"><bean:message key="entidad.habilitar.delegaciones"/></p>
				<div class="remarcar">
					<html:form action="delegacionesEntidad" enctype="multipart/form-data">
						<html:hidden styleId="nif" property="persona.nif"/>
						<html:hidden styleId="habilitada" property="habilitada"/>
						<html:hidden styleId="representante" property="representante"/>
						<p>
							<div id="divHabDeshab">
								<logic:equal name="detalleEntidadForm" property="habilitada" value="S">
									<bean:message key="entidad.habilitar"/>&nbsp;-&nbsp;
									<a onclick="deshabilitarEntidad()" > 
										<bean:message key="entidad.deshabilitar.delegacion"/>
									</a>
								</logic:equal>
								<logic:notEqual name="detalleEntidadForm" property="habilitada" value="S">
									<bean:message key="entidad.deshabilitar"/>&nbsp;-&nbsp;
									<a onclick="habilitarEntidad()" > 
										<bean:message key="entidad.habilitar.delegacion"/>
									</a>
								</logic:notEqual>
							</div>
						</p>
					</html:form>
				</div>
			</div>
			<div id="divDelegaciones">
				<p class="titol"><bean:message key="entidad.delegaciones"/></p>
				<div class="remarcar">
					<p><bean:message key="entidad.delegaciones.descripcion"/></p>
					<logic:notPresent name="delegaciones">
						<p class="alerta">
							<bean:message key="error.delegaciones.no.existen"/>
						</p>
					</logic:notPresent>
					<logic:present name="delegaciones">	
						<logic:empty name="delegaciones">
							<p class="alerta">
								<bean:message key="error.delegaciones.no.existen"/>
							</p>
						</logic:empty>					
						<logic:notEmpty name="delegaciones">
							<table cellpadding="8" cellspacing="0" id="tablaResultats">
								<tr>									
									<th><bean:message key="delegaciones.delegado"/></th>			
									<th><bean:message key="delegaciones.permisos"/></th>
									<th><bean:message key="delegaciones.fechaInicioDelegacion"/></th>
									<th><bean:message key="delegaciones.fechaFinDelegacion"/></th>			
									<th></th>
								</tr>				
								<logic:iterate id="delegacion" name="delegaciones" type="es.caib.zonaper.model.Delegacion">	
									<tr>
										<td><bean:write name="delegacion" property="nifDelegado"/> - <bean:write name="delegacion" property="nombreDelegado"/></td>
										<td>			
											<bean:message key="<%="delegaciones.permiso." + PermisosUtil.getPermisosOrdenados(delegacion.getPermisos())%>" />										
										</td>
										<td><bean:write name="delegacion" property="fechaInicioDelegacion" format="dd/MM/yyyy"/></td>
										<td><bean:write name="delegacion" property="fechaFinDelegacion" format="dd/MM/yyyy"/></td>
										<td>
											<logic:match name="delegacion" property="permisos"  value="<%=es.caib.zonaper.modelInterfaz.ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD %>">
												<a onclick="anularRepresentante('<bean:write name="delegacion" property="codigo" />')" > 
													<bean:message key="entidad.anular" />
												</a>
											</logic:match>
										</td>
									</tr>
								</logic:iterate>
							</table> 
							</logic:notEmpty>
						</logic:present>	
					</div>																		
			</div>
			<div id="divRepresentante">
				<p class="titol"><bean:message key="entidad.asignar.representante"/></p>
				<form name="formFirma">	
					<input type='hidden' id='documentoB64' name='documentoB64' value='' />
					<input type='hidden' id='firma' name='firma' value=''/>
					<div class="remarcar">
						<p>
							<bean:message key="entidad.asignar.descripcion"/>
						</p>
						<p>
							<label for="fechaInicioDelegacion"><bean:message key="delegaciones.fechaInicioDelegacion"/></label>
							<input type="text" class="pcCalendario" id="fechaInicioDelegacion" readonly="true" size="10"/>
							<button id="inicio_calendario" type="button" class="pcbuttonCalendario" title="Mostrar calendari"><img src="imgs/botons/calendari.gif" alt="" /></button>
							<bean:message key="delegaciones.fechaFinDelegacion"/> &nbsp;
							<input type="text" class="pcCalendario" id="fechaFinDelegacion" readonly="true" size="10"/>
							<button id="fin_calendario" type="button" class="pcbuttonCalendario" title="Mostrar calendari"><img src="imgs/botons/calendari.gif" alt="" /></button>
						</p>
						<p>							
							<label for="nifDelegado"><bean:message key="delegacion.documentoIdLegal"/></label>
							<input type="text" class="pc15" id="nifDelegado"/> <img src="imgs/botons/cercar.gif" alt="<bean:message key='botons.cercar'/>"  onclick="javascript:llenarNombreDelegado();"/> <input type="text" class="pc40" id="nombreDelegadoAlta" readonly="readonly"/> 
						</p>
						<br/>
						<script type="text/javascript">
						<!--
							Calendar.setup({
									inputField: "fechaInicioDelegacion", ifFormat: "%d/%m/%Y", button: "inicio_calendario", weekNumbers: false
							});
							Calendar.setup({
									inputField: "fechaFinDelegacion", ifFormat: "%d/%m/%Y", button: "fin_calendario", weekNumbers: false
							});
						-->
						</script>
						<logic:equal name="<%=es.caib.zonaper.delega.Constants.IMPLEMENTACION_FIRMA_KEY%>"
							 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
							<p class="apartado">
								<bean:message key="firmarDocumento.aFirma.anexo.instrucciones"/>
							</p>
						</logic:equal>
						<logic:equal name="<%=es.caib.zonaper.delega.Constants.IMPLEMENTACION_FIRMA_KEY%>"
							 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">	
							<!--  Instrucciones firma -->
							<p>		
								<bean:message key="delegaciones.firmar.descripcion"/>
								
								<bean:message key="firmarDocumento.instrucciones"/>	<bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo" />
							</p>
							<p>
								<label>&nbsp;</label>
								<input type="button" value="<bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo.boton" />" title="<bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo.boton" />" onclick="cargarCertificado();" style="vertical-align: top; margin-top: 4px;"/>
								<br/>
							</p>
							<p>
								<label><bean:message key="firmarDocumento.certificadosDisponibles"/> </label>					
								<jsp:include page="/firma/caib/applet.jsp" flush="false"/>
								&nbsp;
								
							</p>
							<!--  PIN  -->							
							<p>
								<label><bean:message key="firmarDocumento.PINCertificado" />: </label>
								<input type="password" name="PIN" id="PIN" class="txt"/>										
							</p>
						</logic:equal>
						<p class="botonera">
							<input type="button" onclick="asignarRepresentante(this.form)" value="<bean:message key="delegacion.asignar"/>"/>
							<input type="button" value="<bean:message key="entidad.volver"/>" onclick="volver()"/>
						</p>
					</div>	
				</form>
			</div>
		</div>
		<!-- /continguts -->
		
		<div id="fondo"></div>
	
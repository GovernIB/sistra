<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="es.caib.zonaper.front.util.PermisosUtil" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<link href="css/calendar.css" rel="stylesheet" type="text/css" />
<link href="css/estilos.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquery-1.4.1.min.js"></script>
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/lang/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>
<script type="text/javascript" src="js/calendario.js"></script>
<script type="text/javascript" src="js/fechas.js"></script>
<script type="text/javascript" src="js/mensaje.js"></script>

<bean:define id="sesion" name="<%=es.caib.zonaper.front.Constants.DATOS_SESION_KEY%>" type="es.caib.zonaper.model.DatosSesion" />
<bean:define id="tituloMiPortal" name="tituloMiPortal" type="java.lang.String" />


<logic:match name="permisosEntidad"  value="<%=es.caib.zonaper.modelInterfaz.ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD %>">
<script type="text/javascript">
<!--				      
<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
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
	
<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
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

<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
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
			}else{
				alert(datos.error);
			}
	});
}

function crearDelegacion(form){
	if($("#nombreDelegadoAlta").val() == ""){
		alert("<bean:message key="delegaciones.buscar.usuario"/>");
		return;
	}
	Mensaje.mostrar({tipo: "mensaje", modo: "ejecutando", fundido: "si", titulo: "Enviando datos..."});
	var nifDelegado = $("#nifDelegado").val();	
	var fechaInicioDelegacion = $("#fechaInicioDelegacion").val();	
	var fechaFinDelegacion = $("#fechaFinDelegacion").val();
	var permisos = $("#permiso").val();		
	var url_json = '<html:rewrite page="/protected/crearDocumentoDelegacion.do"/>';
	var data ='nifDelegado='+nifDelegado+'&fechaInicioDelegacion='+fechaInicioDelegacion+'&fechaFinDelegacion='+fechaFinDelegacion+'&permisos='+permisos;
	$.postJSON(url_json, data, 
		function(datos){
			if (datos.error==""){
				Mensaje.mostrar({tipo: "mensaje", modo: "ejecutando", fundido: "si", titulo: "Firmando datos..."});
				$('#documentoB64').val(datos.datos);
				<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
						 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
					if (!firmarAFirma(form)){
						Mensaje.cancelar();	
						return;
					}
				</logic:equal>
				<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
							 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">	
					if (!firmarCAIB(form)){ 
						Mensaje.cancelar();	
						return;
					}
				</logic:equal>	
				var url_json2 = '<html:rewrite page="/protected/crearDelegacion.do"/>';
				var data2 ='codigoRDS='+datos.codigo+'&claveRDS='+datos.clave+'&firmaJSP='+$('#firma').val();
				$.postJSON(url_json2, data2, function(datos2) {
					if (datos2.error==""){								
						alert("<bean:message key="delegaciones.alta.correcta"/>");							
						document.detalleDelegacionForm.action='<html:rewrite page="/protected/mostrarDelegaciones.do" />';
						document.detalleDelegacionForm.submit();
						Mensaje.cancelar();	
					}else{
						Mensaje.cancelar();	
						alert(datos2.error);
					}
		        });
			}else{
				Mensaje.cancelar();	
				alert(datos.error);
			}
	});
}

function anularDelegacion(codigo){
	Mensaje.mostrar({tipo: "mensaje", modo: "ejecutando", fundido: "si", titulo: "Enviando datos..."});
	var url_json = '<html:rewrite page="/protected/anularDelegacion.do"/>';
	var data ='codigoDelegacion='+codigo;
	$.postJSON(url_json, data, 
		function(datos){
			if (datos.error==""){
				alert("<bean:message key="delegaciones.anulacion.correcta"/>");							
				document.detalleDelegacionForm.action='<html:rewrite page="/protected/mostrarDelegaciones.do" />';
				document.detalleDelegacionForm.submit();
				Mensaje.cancelar();	
			}else{
				Mensaje.cancelar();	
				alert(datos.error);
			}
	});
}

</script>
</logic:match>

		<!-- continguts -->
			<div id="divDelegaciones">
				<logic:present name="delegaciones">
					<logic:notEmpty name="delegaciones">
						<p class="titol"><bean:message key="delegaciones.delegaciones"/></p>
						<p><bean:message key="delegaciones.descripcion"/></p>
							<table class="llistatElements">
								<thead>
								<tr>									
									<th><bean:message key="delegaciones.delegado"/></th>			
									<th><bean:message key="delegaciones.permisos"/></th>
									<th><bean:message key="delegaciones.fechaInicioDelegacion"/></th>
									<th><bean:message key="delegaciones.fechaFinDelegacion"/></th>			
									<logic:match name="permisosEntidad"  value="<%=es.caib.zonaper.modelInterfaz.ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD %>">
										<th></th>
									</logic:match>
								</tr>
								</thead>				
								<logic:iterate id="delegacion" name="delegaciones" type="es.caib.zonaper.model.Delegacion">	
									<tr>
										<td><bean:write name="delegacion" property="nifDelegado"/> - <bean:write name="delegacion" property="nombreDelegado"/></td>
										<td>
											<bean:message key="<%="delegaciones.permiso." + PermisosUtil.getPermisosOrdenados(delegacion.getPermisos())%>" />										
										</td>
										<td><bean:write name="delegacion" property="fechaInicioDelegacion"  format="dd/MM/yyyy"/></td>
										<td><bean:write name="delegacion" property="fechaFinDelegacion"  format="dd/MM/yyyy"/></td>
										<td>
											<logic:match name="permisosEntidad"  value="<%=es.caib.zonaper.modelInterfaz.ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD %>">
												<logic:notMatch name="delegacion" property="permisos"  value="<%=es.caib.zonaper.modelInterfaz.ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD %>">
													<a onclick="anularDelegacion('<bean:write name="delegacion" property="codigo" />')" > 
														<bean:message key="delegaciones.anular" />
													</a>
												</logic:notMatch>
											</logic:match>
										</td>
									</tr>
								</logic:iterate>
							</table> 
					</logic:notEmpty>
				</logic:present>														
			</div>
			<br/>
			<br/>
			<div id="divCrearDelegacion">
				<logic:match name="permisosEntidad"  value="<%=es.caib.zonaper.modelInterfaz.ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD %>">
					<p class="titol"><bean:message key="delegaciones.crear.delegacion"/></p>
					<p><bean:message key="delegaciones.crear.descripcion" arg0="<%=tituloMiPortal%>"/></p>
					<html:form action="/protected/crearDelegacion" enctype="multipart/form-data">
						<html:hidden styleId="documentoB64" property="documentoB64"/>
						<html:hidden styleId="firma" property="firma"/>
						<div class="remarcar">
							<p>
								<label for="fechaInicioDelegacion"><bean:message key="delegaciones.fechaInicioDelegacion"/></label>
								<html:text styleId="fechaInicioDelegacion" property="fechaInicioDelegacion" styleClass="pcCalendario" readonly="true" size="10"/>
								<button id="inicio_calendario" type="button" class="pcbuttonCalendario" title="Mostrar calendari"><img src="imgs/botons/calendari.gif" alt="" /></button>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<bean:message key="delegaciones.fechaFinDelegacion"/>
								<html:text styleId="fechaFinDelegacion" property="fechaFinDelegacion" styleClass="pcCalendario" readonly="true" size="10"/>
								<button id="fin_calendario" type="button" class="pcbuttonCalendario" title="Mostrar calendari"><img src="imgs/botons/calendari.gif" alt="" /></button>
							</p>
							<p>
								<label for="nifDelegado"><bean:message key="delegaciones.documentoIdLegal"/></label>
								<html:text styleId="nifDelegado" property="nifDelegado" styleClass="pc40"/>
								<img src="imgs/botons/cercar.gif" alt="<bean:message key='botons.cercar'/>"  onclick="javascript:llenarNombreDelegado();"/> <input type="text" class="pc25" id="nombreDelegadoAlta" readonly="readonly"/> 
							</p>
							<p>
								<label for="permisos"><bean:message key="delegaciones.permisos"/></label>
								<html:select property="permiso" styleId="permiso" styleClass="pc20">
									<html:option value="<%=es.caib.zonaper.modelInterfaz.ConstantesZPE.DELEGACION_PERMISO_RELLENAR_TRAMITE %>"><bean:message key="delegaciones.permiso.T"/></html:option>																		
									<html:option value="<%=es.caib.zonaper.modelInterfaz.ConstantesZPE.DELEGACION_PERMISO_PRESENTAR_TRAMITE %>"><bean:message key="delegaciones.permiso.TP"/></html:option>
									<html:option value="<%=es.caib.zonaper.modelInterfaz.ConstantesZPE.DELEGACION_PERMISO_ABRIR_NOTIFICACION %>"><bean:message key="delegaciones.permiso.TPN"/></html:option>
								</html:select>
							</p>							
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
							
							<br/>
							<br/>
							
							<p class="apartado">
								<bean:message key="delegaciones.firmar.descripcion"/>
							
							<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
								 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">							
									<bean:message key="firmarDocumento.aFirma.anexo.instrucciones"/>
								</p>
							</logic:equal>
							<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
								 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">	
								<!--  Instrucciones firma -->
									<bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo" />
								</p>								
								<div class="pas">
									<p>
										<input id="cargarCertificado" type="button" 
											value="<bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo.boton"/>" 
											title="<bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo.boton" />" 
											onclick="cargaCertificado();" />
									</p>
								</div>
								<div class="pas">
									<p><bean:message key="firmarDocumento.certificadosDisponibles"/></p>
									<jsp:include page="/firma/caib/applet.jsp" flush="false"/>	
								</div>
								<div class="pas">
									<label><bean:message key="firmarDocumento.PINCertificado" />: </label>
									<br/>
									<input type="password" name="PIN" id="PIN" class="txt"/>										
								</div>																
							</logic:equal>
							<p class="botonera">
								<input type="button" onclick="crearDelegacion(this.form)" value="<bean:message key="delegaciones.crear"/>"/>
							</p>
						</div>	
					</html:form>
				</logic:match>
			</div>
		<!-- /continguts -->
	
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript" src="js/mensaje.js"></script>
<script type="text/javascript">
<!--
	var mensajeEnviando = '<bean:message key="anexarDocumentos.mensajeAnexar"/>';
	var mensajePreparando = '<bean:message key="anexar.documento.preparar"/>';
	<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
				 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									
			
		var contentType = '<%= es.caib.util.FirmaUtil.obtenerContentTypeCAIB(es.caib.util.FirmaUtil.CAIB_DOCUMENT_NOTIFICACIO_CONTENT_TYPE) %>';
		
		
		function firmarCAIB(form){			
			var applet = whichApplet();
			var firma = '';	
			var pin = document.firmarDocumentoDelegadoForm.PIN.value;

			applet.setPassword( pin );
				
			var contenidoFichero = $('#documentoB64').val();
			if ( contenidoFichero == '' )
			{
				alert( "<bean:message key="firmarDocumento.introducirFichero"/>" );
				document.anexarDocumentoForm.datos.focus();
				document.anexarDocumentoForm.datos.select();
				return false;
			}
					
			firma = applet.firmarFicheroB64( $('#documentoB64').val(), contentType );	
			if (firma == null || firma == ''){
				alert(applet.getLastError());
				return false;
			}
			form.firma.value = firma;	
			return true;
		}
	</logic:equal>
	
	<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
				 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
					
		function prepararEntornoFirma(){
			cargarAppletFirma(sistra_ClienteaFirma_buildVersion);
		}
		
		function firmarAFirma(form){
		
		  	if (clienteFirma == undefined) { 
	          alert("No se ha podido instalar el entorno de firma");
	          return false;
	      	}
					
			var contenidoFichero = $('#documentoB64').val();
			if ( contenidoFichero == '' )
			{
				alert( "<bean:message key="firmarDocumento.introducirFichero"/>" );
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
   				form.firma.value = firma;
			    return true;
			}
		}
			
		prepararEntornoFirma();
	</logic:equal>
	
	function firmarDocumento(form)
	{
		mostrarMensaje();
		<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
					 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
			if (!firmarAFirma(form)) {
				Mensaje.cancelar();
				return;
			}
		</logic:equal>
		<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>" value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									
			if (!firmarCAIB(form)){
				Mensaje.cancelar();	
				return;					
			}
		</logic:equal>					
		
		// Enviar formulario
		form.submit();
	}
	
	function mostrarMensaje(){
 		Mensaje.mostrar({tipo: "mensaje", modo: "ejecutando", fundido: "si", titulo: "Enviando datos..."});
	}
	
//-->
</script>
<bean:define id="urlAbrirDocumento" type="java.lang.String">
	<html:rewrite page="/protected/abrirDocumento.do"/>
</bean:define>
<bean:define id="sesion" name="<%=es.caib.zonaper.front.Constants.DATOS_SESION_KEY%>" type="es.caib.zonaper.model.DatosSesion" />

<h1><bean:message key="bandejaFirma.firmarDocumento"/> - <bean:write name="documentoParaFirmar" property="descripcionDocumento" /></h1>
	
	<p class="apartado">
		<bean:define id="codigoRDS" name="documentoParaFirmar" property="rdsCodigo" type="java.lang.Long" />
		<bean:define id="claveRDS" name="documentoParaFirmar" property="rdsClave" type="java.lang.String" />
		<bean:message key="bandejaFirma.abrir.documento" />
		<a href="<%=urlAbrirDocumento%>?codigo=<%=codigoRDS %>&clave=<%=claveRDS %>">
			<bean:message key="bandejaFirma.abrir" />
		</a>
	</p>

<html:errors/>		

<!-- firma documento -->
<h1 class="firmarAnexo">- <bean:message key="firmarDocumento.firmar"/></h1>
	
<!-- anexar documentos -->
<div id="anexarDocs">														
	<!--  Instrucciones firma -->
	<p class="apartado">								
		<bean:message key="firmarDocumento.instrucciones.firmarOtro" arg0="<%=sesion.getNifUsuario()%>"/>
	</p>						
	<!--  Instrucciones firma y Applet firma (depende de implementacion firma) -->
	<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
				 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
		<html:form action="/protected/firmarDocumento" enctype="multipart/form-data">
			<html:hidden styleId="documentoB64" property="documentoB64"/>
			<html:hidden styleId="identificador" property="identificador"/>
			<html:hidden styleId="firma" property="firma"/>
			<p class="apartado">
				<bean:message key="firmarDocumento.aFirma.anexo.instrucciones"/>
			</p>
		
			<!--  BOTON FIRMAR -->
			<p class="formBotonera">
				<input name="formCDboton" type="button" value="<bean:message key="firmarDocumento.boton.iniciar" />" title="<bean:message key="firmarDocumento.boton.iniciar" />" onclick="firmarDocumento(this.form);" />
			</p>			
		</html:form>
	</logic:equal>
	<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
				 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									
		<html:form action="/protected/firmarDocumento" enctype="multipart/form-data">	
			<html:hidden styleId="documentoB64" property="documentoB64"/>
			<html:hidden styleId="identificador" property="identificador"/>
			<html:hidden styleId="firma" property="firma"/>
			<p class="apartado"><bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo" /></p>
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
			<p class="botonera">
				<input name="formCDboton" type="button" value="<bean:message key="firmarDocumento.boton.iniciar" />" title="<bean:message key="firmarDocumento.boton.iniciar" />" onclick="firmarDocumento(this.form);" />
			</p>						
		</html:form>	
	</logic:equal>
</div>	

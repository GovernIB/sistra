<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ page import="es.caib.util.StringUtil"%>
<%@ page import="es.caib.zonaper.modelInterfaz.ConstantesZPE"%>

<bean:define id="codigoNotificacion" name="notificacion" property="codigo"/>
<bean:define id="nifRepresentante" name="notificacion" property="nifRepresentante" type="java.lang.String"/>
<bean:define id="xmlAsientoAcuseRecibo" name="xmlAsientoAcuseRecibo" type="java.lang.String"/>
<bean:define id="claveFirma" name="claveFirma" type="java.lang.String"/>
<bean:define id="tipoFirmaAlt" name="tipoFirmaAlt" type="java.lang.String"/>
<bean:define id="plazoDias" name="plazoDias" type="java.lang.String"/>


<bean:define id="mensaje" type="java.lang.String">
	<bean:write name="avisoNotificacion" property="texto" />
</bean:define>
<%
	// Reemplazamos saltos de linea por <br/>
 	String mensajeHtml = mensaje;
 	mensajeHtml = StringUtil.replace(mensajeHtml,"\n","<br/>");
%>

<logic:equal name="puedeAbrir" value="S">
<script type="text/javascript">
    <!-- 
	function enviar(firmarAcuse)
	{
	   	// Realizamos firma
		if (firmarAcuse){
			realizarFirmaAcuse();					
		}
						
		// Enviamos
		var formulario = document.getElementById("formularioEnvio");
		formulario.submit();
	}
<logic:notEmpty name="tipoFirma">
<logic:equal name="tipoFirma" value="CERT">
 	<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
				 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									
		var contentType = '<%= es.caib.util.FirmaUtil.obtenerContentTypeCAIB(es.caib.util.FirmaUtil.CAIB_ACUSE_NOTIFICACIO_CONTENT_TYPE) %>';
		function firmarCAIB(){
		 // Realizamos firma			
		 	var formulario = document.getElementById("formularioEnvio");
		 	var formularioFirma = document.getElementById("formularioFirma");
			var firma = '';	
			var pin = formularioFirma.PIN.value;
			
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
	
	<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
				 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
			
			
			function prepararEntornoFirma(){
				MiniApplet.cargarMiniApplet(base);
			}
			
			function saveSignatureCallback(signatureB64) {
				if (signatureB64 == "AA==") {
					alert("<bean:message key="firma.miniapplet.appletinactivo" />");
					return;
				}
				
				firma = b64ToB64UrlSafe(signatureB64);
				var formulario = document.getElementById("formularioEnvio");
				formulario.firma.value = firma;
			}
			
			function showLogCallback(errorType, errorMessage) {
					error = 'Error: '+errorMessage;
					alert(error);
					Mensaje.cancelar();	
					console.log("Type: " + errorType + "\nMessage: " + errorMessage);
			}
			
			function firmarAFirma(form){
				var formulario = document.getElementById("formularioEnvio");
				if (MiniApplet == undefined) { 
		          alert("No se ha podido instalar el entorno de firma");
		          return false;
		       	}
		      	
				var asientoB64 = b64UrlSafeToB64(formulario.asiento.value);
				
				MiniApplet.sign(
					asientoB64,
					sistra_ClienteaFirma_SignatureAlgorithm,//"SHA1withRSA",
					sistra_ClienteaFirma_SignatureFormat,//"CAdES",
					"",
					saveSignatureCallback,
					showLogCallback);
			}
			prepararEntornoFirma();
	</logic:equal>	


	function realizarFirmaAcuse()
	{
		// Realizamos firma
		<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
					 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
				firmarAFirma(); return;
		</logic:equal>
		<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
					 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									
				if (!firmarCAIB()) return;					
		</logic:equal>	
	}
	
</logic:equal>
<logic:equal name="tipoFirma" value="CLAVE">
	function realizarFirmaAcuse()
	{
		// No hay que hacer nada
	}
</logic:equal>
</logic:notEmpty>
	 -->	
</script>
</logic:equal>

<h1><bean:message key="detalleNotificacion.avisoNotificacion" /></h1>

<!-- notificacio -->
<div id="notificacio">
	<dl>
		<dt><bean:message key="detalleNotificacion.organo"/>:</dt>
		<dd>
			<logic:empty name="unidadAdministrativa">
				&nbsp;
			</logic:empty>
			<logic:notEmpty name="unidadAdministrativa">
				<bean:write name="unidadAdministrativa"/>
			</logic:notEmpty>			
		</dd>
		<dt><bean:message key="detalleNotificacion.expediente"/>:</dt>
		<dd><bean:write name="codigoExpediente"/> - <bean:write name="descExpediente"/></dd>
		<dt><bean:message key="detalleNotificacion.fechaEmision"/>:</dt>
		<dd><bean:write name="notificacion" property="fechaRegistro" format="dd/MM/yyyy '-' HH:mm"/></dd>
		<logic:equal name="controlEntrega" value="S">
			<logic:notEmpty name="notificacion" property="fechaFinPlazo">
				<dt><bean:message key="detalleNotificacion.fechaFinPlazo"/>:</dt>
				<dd><bean:write name="notificacion" property="fechaFinPlazo" format="dd/MM/yyyy"/></dd>
			</logic:notEmpty>
		</logic:equal>
		<dt><bean:message key="detalleNotificacion.asunto"/>:</dt>
		<dd><bean:write name="avisoNotificacion" property="titulo"/></dd>
		<dt><bean:message key="detalleNotificacion.descripcion"/>:</dt>
		<dd><%=mensajeHtml%></dd>
	</dl>	
</div>
<!-- /notificacio -->


<!-- firma -->
<logic:equal name="rechazada" value="S">
	<p class="alerta"><bean:message key="detalleNotificacion.rechazada"/></p>
</logic:equal>

<logic:equal name="rechazada" value="N">
<logic:equal name="notificacion" property="firmarAcuse" value="true">
	<h2><bean:message key="detalleNotificacion.acuse.titulo"/></h2>
	
	<p>
		<logic:equal name="controlEntrega" value="S">
			<bean:message key="detalleNotificacion.notaLegal.controlPlazoHabilitado" arg0="<%=plazoDias%>"/>
		</logic:equal>
		<logic:equal name="controlEntrega" value="N">
			<bean:message key="detalleNotificacion.notaLegal.controlPlazoDeshabilitado" arg0="<%=plazoDias%>"/>
		</logic:equal>
	</p>
	
	
	<logic:equal name="puedeAbrir" value="S">
	
	
		<logic:equal name="tipoFirma" value="CERT">
			<logic:present name="messageKey">
				<p class="alerta"><bean:message name="messageKey" arg0="<%= nifRepresentante %>"/></p>
			</logic:present>
			
			<logic:equal name="es.caib.zonaper.front.DATOS_SESION" property="perfilAcceso" scope="session" value="<%=ConstantesZPE.DELEGACION_PERFIL_ACCESO_CIUDADANO%>">		
				<p><bean:message key="detalleNotificacion.acuse.texto" arg0="<%= nifRepresentante %>"/></p>
			</logic:equal>
			<logic:equal name="es.caib.zonaper.front.DATOS_SESION" property="perfilAcceso" scope="session" value="<%=ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO%>">		
				<p><bean:message key="detalleNotificacion.acuse.accesoDelegado.texto" arg0="<%= nifRepresentante %>"/></p>
			</logic:equal>	
			
			<!--  Form para envio de datos -->
			<html:form action="/protected/abrirNotificacion" styleId="formularioEnvio">
				<html:hidden property="codigo" value="<%= String.valueOf( codigoNotificacion ) %>" />
				<html:hidden property="asiento" value="<%= xmlAsientoAcuseRecibo %>"/>
				<html:hidden property="firma" value=""/>
				<html:hidden property="tipoFirma" value="CERTIFICADO"/>
			</html:form>
				
			<!--  Mostramos applet e instrucciones segun implementacion -->
			<logic:equal name="implementacionFirma" value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">				
				<div id="signaturaAFirma">
					<!--  No necesaria ayuda -->
			</logic:equal>
			<logic:equal name="implementacionFirma" value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									
				<div id="signaturaCaib">
					<div class="pas">
						<p><bean:message key="detalleNotificacion.acuse.introduzcaCert"/></p>
						<p class="botonera">
							<input id="cargarCertificado" type="button" value="<bean:message key="detalleNotificacion.acuse.iniciarDispositivo.boton"/>" onclick="cargaCertificado();" />
						</p>
					</div>
					<div class="pas">
						<p><bean:message key="detalleNotificacion.acuse.certDisponibles"/></p>
						<jsp:include page="/firma/caib/applet.jsp" flush="false"/>	
					</div>
					<div class="pas">
						<form name="formFirma" action="" id="formularioFirma">						
							<label for="PIN"><bean:message key="detalleNotificacion.acuse.pin"/>
							  <input name="PIN" id="PIN" type="password"  value="" />			
							</label>			 				
						</form>
					</div>		
			</logic:equal>
					
				<!--  Boton abrir notificacion -->
				<div class="pas final">
					<p class="botonera">
						<input id="btnFirmar" name="btnFirmar" type="button"
							 value="<bean:message key="detalleNotificacion.abrir"/>" 
							 onclick="javascript:enviar(<bean:write name="notificacion" property="firmarAcuse"/>)"
						/>
					</p>
				</div>
			</div>
		</logic:equal>
		
		<logic:equal name="tipoFirma" value="CLAVE">
			<logic:present name="messageKey">
				<p class="alerta"><bean:message name="messageKey"/></p>
			</logic:present>
		
			<html:form action="/protected/abrirNotificacion" styleId="formularioEnvio">
				<html:hidden property="codigo" value="<%= String.valueOf( codigoNotificacion ) %>" />
				<html:hidden property="asiento" value="<%= xmlAsientoAcuseRecibo %>"/>
				<html:hidden property="tipoFirma" value="CLAVE"/>
				
						
				<!--  Boton abrir notificacion -->
				<div id="signaturaClave">
				<p><bean:message key="detalleNotificacion.abrirClave"/></p>
				<p><html:text size="35" property="firma" value="<%=claveFirma%>"/></p>
				<div class="pas final">
					<p class="botonera">
						<input id="btnFirmar" name="btnFirmar" type="button"
							 value="<bean:message key="detalleNotificacion.abrir"/>" 
							 onclick="javascript:enviar(<bean:write name="notificacion" property="firmarAcuse"/>)"
						/>
					</p>
				</div>
				</div>
			</html:form>				
		</logic:equal>
		
		
		<!-- Cambio de tipo de firma -->
		<logic:notEmpty name="tipoFirmaAlt">
			<p>
				<i>
				<logic:equal name="tipoFirmaAlt" value="CLAVE">
					<bean:message key="detalleNotificacion.cambioTipoFirma.clave"/>
				</logic:equal>
				<logic:equal name="tipoFirmaAlt" value="CERT">
					<bean:message key="detalleNotificacion.cambioTipoFirma.certificado"/>
				</logic:equal>
				<a href="<%="mostrarDetalleElemento.do?tipo=N&codigo=" + codigoNotificacion + "&tipoFirma=" + tipoFirmaAlt%>">
					<bean:message key="detalleNotificacion.cambioTipoFirma.aqui"/>
				</a>
				</i>
			</p>
		</logic:notEmpty>		
	</logic:equal>
		
	<logic:equal name="puedeAbrir" value="N">	
		<p class="alerta"><bean:message key="detalleNotificacion.acuse.accesoDelegado.texto"/></p>
	</logic:equal>	
	
	
</logic:equal>
</logic:equal>

<!-- /firma -->

<!--  envio sin acuse -->
<logic:equal name="notificacion" property="firmarAcuse" value="false">
	<div id="signaturaAFirma">
	
		<logic:equal name="puedeAbrir" value="S">
			<html:form action="/protected/abrirNotificacion" styleId="formularioEnvio">
				<html:hidden property="codigo" value="<%= String.valueOf( codigoNotificacion ) %>" />
				<html:hidden property="asiento" value="<%= xmlAsientoAcuseRecibo %>"/>
				<html:hidden property="firma" value=""/>
				<html:hidden property="tipoFirma" value=""/>		
				<!--  Boton abrir notificacion -->
				<div class="pas final">
					<p class="botonera">
						<input id="btnFirmar" name="btnFirmar" type="button"
							 value="<bean:message key="detalleNotificacion.abrir"/>" 
							 onclick="javascript:enviar(<bean:write name="notificacion" property="firmarAcuse"/>)"
						/>
					</p>
				</div>
			</html:form>					
		</logic:equal>
		
		<logic:equal name="puedeAbrir" value="N">	
			<p class="alerta"><bean:message key="detalleNotificacion.acuse.accesoDelegado.texto"/></p>
		</logic:equal>	
		
	</div>
</logic:equal>





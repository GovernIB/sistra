<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ page import="es.caib.util.StringUtil"%>

<bean:define id="codigoNotificacion" name="notificacion" property="codigo"/>
<bean:define id="nifRepresentante" name="notificacion" property="nifRepresentante" type="java.lang.String"/>
<bean:define id="xmlAsientoAcuseRecibo" name="xmlAsientoAcuseRecibo" type="java.lang.String"/>

<bean:define id="mensaje" type="java.lang.String">
	<bean:write name="avisoNotificacion" property="texto" />
</bean:define>
<%
	// Reemplazamos saltos de linea por <br/>
 	String mensajeHtml = mensaje;
 	mensajeHtml = StringUtil.replace(mensajeHtml,"\n","<br/>");
%>

<script type="text/javascript">
	<!-- 
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
				cargarAppletFirma();
			}
				 
			function firmarAFirma(){
			
				var formulario = document.getElementById("formularioEnvio");
			
				if (clienteFirma == undefined) { 
		          alert("No se ha podido instalar el entorno de firma");
		          return false;
	        	}
			
				var asientoB64 = b64UrlSafeToB64(formulario.asiento.value);
			
				clienteFirma.initialize();
				clienteFirma.setSignatureAlgorithm("sha1WithRsaEncryption");
				clienteFirma.setSignatureMode("EXPLICIT");
				clienteFirma.setSignatureFormat("CMS");
				clienteFirma.setData(asientoB64);
				
				clienteFirma.sign();
				
				if(clienteFirma.isError()){
					error = 'Error: '+clienteFirma.getErrorMessage();
					alert(error);
					return false;
				}else{	
				     firma = clienteFirma.getSignatureBase64Encoded();
				     formulario.firma.value = firma;
				     return true;
				}
			}
	</logic:equal>	


	function enviar(firmarAcuse)
	{
	
		if (firmarAcuse){
			// Realizamos firma
			<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
						 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
					if (!firmarAFirma()) return;
			</logic:equal>
			<logic:equal name="<%=es.caib.zonaper.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
						 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									
					if (!firmarCAIB()) return;					
			</logic:equal>
		}
						
		// Enviamos
		var formulario = document.getElementById("formularioEnvio");
		formulario.submit();
	}
	 -->	
</script>

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
		<dd><bean:write name="codigoExpediente"/></dd>
		<dt><bean:message key="detalleNotificacion.fechaEmision"/>:</dt>
		<dd><bean:write name="notificacion" property="fechaRegistro" format="dd/MM/yyyy '-' HH:mm"/></dd>
		<dt><bean:message key="detalleNotificacion.asunto"/>:</dt>
		<dd><bean:write name="avisoNotificacion" property="titulo"/></dd>
		<dt><bean:message key="detalleNotificacion.descripcion"/>:</dt>
		<dd><%=mensajeHtml%></dd>
	</dl>	
</div>
<!-- /notificacio -->


<!-- firma -->
<logic:equal name="notificacion" property="firmarAcuse" value="true">
	
	<h2><bean:message key="detalleNotificacion.acuse.titulo"/></h2>
	
	<p><bean:message key="detalleNotificacion.notaLegal"/></p>
	
	<logic:present name="messageKey">
		<p class="alerta"><bean:message name="messageKey" arg0="<%= nifRepresentante %>"/></p>
	</logic:present>
	
	<p><bean:message key="detalleNotificacion.acuse.texto" arg0="<%= nifRepresentante %>"/></p>
		
	
	<!--  Form para envio de datos -->
	<html:form action="/protected/abrirNotificacion" styleId="formularioEnvio">
		<html:hidden property="codigo" value="<%= String.valueOf( codigoNotificacion ) %>" />
		<html:hidden property="asiento" value="<%= xmlAsientoAcuseRecibo %>"/>
		<html:hidden property="firma" value=""/>
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
					<p><label for="PIN"><bean:message key="detalleNotificacion.acuse.pin"/></label></p>
					<p><input name="PIN" id="PIN" type="password"  value="" /></p>						 				
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
<!-- /firma -->

<!--  envio sin acuse -->
<logic:equal name="notificacion" property="firmarAcuse" value="false">
	<div id="signaturaAFirma">
		<html:form action="/protected/abrirNotificacion" styleId="formularioEnvio">
			<html:hidden property="codigo" value="<%= String.valueOf( codigoNotificacion ) %>" />
			<html:hidden property="asiento" value="<%= xmlAsientoAcuseRecibo %>"/>
			<html:hidden property="firma" value=""/>
					
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
	</div>
</logic:equal>





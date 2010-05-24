<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<bean:define id="formulario" name="formulario" type="es.caib.sistra.model.DocumentoFront"/>
<bean:define id="contentTypeFirma" name="formulario" type="java.lang.String" property="contentType" />
<bean:define id="urlFirmarDocumento">
        <html:rewrite page="/protected/firmarFormulario.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>

<script type="text/javascript">
<!--
	var mensajeFirmando = '<bean:message key="firmarDocumento.mensajeFirmar"/>';
	
	<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
		value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">	
	
		var contentType = '<%= contentTypeFirma %>';
									
		function firmarCAIB(){
			var firma = '';	
			var pin = document.formFirma.PIN.value;
			
			var applet = whichApplet();
			applet.setPassword( pin );
			
			firma = applet.firmarCadena( applet.base64ToCadena(document.formFirma.base64XmlForm.value), contentType);	
			if (firma == null || firma == ''){
				alert(applet.getLastError());
				return false;
			}	
			document.firmarFormularioForm.firma.value = firma;
			return true;
		}
	</logic:equal>
	
	<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
				 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
			
			function prepararEntornoFirma(){
				cargarAppletFirma();
			}
			
			function firmarAFirma(){
				if (clienteFirma == undefined) { 
		          alert("No se ha podido instalar el entorno de firma");
		          return false;
	        	}
			
				var formB64 = b64UrlSafeToB64(document.formFirma.base64XmlForm.value);
			
				clienteFirma.initialize();
				clienteFirma.setSignatureAlgorithm("sha1WithRsaEncryption");
				clienteFirma.setSignatureMode("EXPLICIT");
				clienteFirma.setSignatureFormat("CMS");
				clienteFirma.setData(formB64);
				
				clienteFirma.sign();
				
				if(clienteFirma.isError()){
					error = 'Error: '+clienteFirma.getErrorMessage();
					alert(error);
					return false;
				}else{	
				     firma = clienteFirma.getSignatureBase64Encoded();
				     document.firmarFormularioForm.firma.value = firma;
				     return true;
				}
		}
	</logic:equal>

	
	
	function firmarDocumento()
	{	
		// Firmamos
		<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>" 
					value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
			if (!firmarAFirma()) return;
		</logic:equal>
		<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
					 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									
			if (!firmarCAIB()) return;					
		</logic:equal>
	
		// Enviamos documento
		document.firmarFormularioForm.submit ();
	}
	
//-->
</script>

<h2>
	<bean:message key="firmarDocumento.firmarDocumento"/> 
	<bean:write name="formulario" property="descripcion" />
</h2>


<!--  Instrucciones firma -->
<p>
	<!--  Si se comprueba el nif firmante -->
	<logic:notEmpty name="formulario" property="firmante">
		<bean:message key="firmarDocumento.instrucciones.firmarOtro" arg0="<%=formulario.getFirmante()%>"/>
	</logic:notEmpty>
	
	<!--  Si no se comprueba el nif firmante -->
	<logic:empty name="formulario" property="firmante">
		<bean:message key="firmarDocumento.instrucciones"/>
	</logic:empty>	

</p>
<!--  Instrucciones firma y Applet firma (depende de implementacion firma) -->
<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>"
			 value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_AFIRMA%>">
	<form name="formFirma">
		<input type="hidden" name="base64XmlForm" value="<bean:write name="base64XmlForm" />"/>
		
		<p><bean:message key="firmarDocumento.aFirma.formulario.instrucciones"/></p>
		
		<!--  BOTON FIRMAR -->
		<p class="formBotonera">
			<input name="formCDboton" type="button" value="<bean:message key="firmarDocumento.boton.iniciar" />" title="<bean:message key="firmarDocumento.boton.iniciar" />" onclick="firmarDocumento();" />
		</p>			
		
	</form>
</logic:equal>
<logic:equal name="<%=es.caib.sistra.front.Constants.IMPLEMENTACION_FIRMA_KEY%>" 
			value="<%=es.caib.sistra.plugins.firma.PluginFirmaIntf.PROVEEDOR_CAIB%>">									
	<!--  Applet firma CAIB-->
	<form name="formFirma">
		<input type="hidden" name="base64XmlForm" value="<bean:write name="base64XmlForm" />"/>
		
		<p><bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo" /></p>
			
		<p class="formBotonera"><input type="button" value="<bean:message key="firmarDocumento.certificado.instrucciones.iniciarDispositivo.boton" />" title="<bean:message key="login.certificado.instrucciones.iniciarDispositivo.boton" />" onclick="cargarCertificado();" /></p>
		<p><bean:message key="firmarDocumento.certificadosDisponibles" /></p>
		<p>	
			<jsp:include page="/firma/caib/applet.jsp" flush="false"/>	
		</p>	
		
		<!--  PIN  -->
		<span style="position: relative;">
			<bean:message key="firmarDocumento.PINCertificado" /><input type="password" name="PIN" id="PIN" class="txt"/>
		</span>
		
		<!--  BOTON FIRMAR -->
		<p class="formBotonera">
			<input name="formCDboton" type="button" value="<bean:message key="firmarDocumento.boton.iniciar" />" title="<bean:message key="firmarDocumento.boton.iniciar" />" onclick="firmarDocumento();" />
		</p>			
		
	</form>
</logic:equal>

<!-- Form para envio de datos -->
<html:form action="/protected/firmarFormulario.do" method="POST">
	<html:hidden property="firma"/>
	<html:hidden name="irAFirmarFormularioForm" property="ID_INSTANCIA"/>
	<html:hidden name="irAFirmarFormularioForm" property="instancia" />
	<html:hidden name="irAFirmarFormularioForm" property="identificador" />
</html:form>

<!--  Enlace volver  -->
<p class="volver"><html:link action="/protected/irAPaso" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"><bean:message key="pasoRellenar.volverLista"/></html:link></p>					

<div class="sep"></div>
<!-- capa accediendo formularios -->
<div id="capaInfoFondo"></div>
<div id="capaInfoForms"></div>
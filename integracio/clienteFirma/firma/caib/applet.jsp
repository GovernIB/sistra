<%
	String isAlwaysDefaultContentTypeForSignature = System.getProperty( "caibsignature.useAlwaysDefaultContentType" );
	if(isAlwaysDefaultContentTypeForSignature == null) isAlwaysDefaultContentTypeForSignature = "false";
%>
<object id="appfirma" name="appfirma" classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" width="230" height="35">
	<param name="code" value="es.caib.sistra.plugins.firma.impl.caib.applet.AppletFirma" />
	<param name="codebase" value="<%=request.getContextPath()%>/firma/caib">
	<param name="archive" value="signaturacaib.core-4.2.1-api.jar,appletFirma.jar" />
	<param name="idioma" value="es" />		
	<param name="scriptable" value="true" />
	<param name="useAlwaysDefaultContentType" value="<%= isAlwaysDefaultContentTypeForSignature%>" />
	<comment>
		<embed type="application/x-java-applet"
		width="230" height="35" align="baseline" 
		code="es.caib.sistra.plugins.firma.impl.caib.applet.AppletFirma" 
		codebase="<%=request.getContextPath()%>/firma/caib"
		archive="signaturacaib.core-4.2.1-api.jar,appletFirma.jar"
		idioma="es"
		id="appfirma2"
		scriptable="true"
		useAlwaysDefaultContentType="<%= isAlwaysDefaultContentTypeForSignature%>">
			<noembed>
				No te suport per applets Java 2 SDK ! !
			</noembed>
		</embed>		
	</comment>
</object>
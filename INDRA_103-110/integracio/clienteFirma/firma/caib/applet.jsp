<%
	String isAlwaysDefaultContentTypeForSignature = System.getProperty( "caibsignature.useAlwaysDefaultContentType" );
	if(isAlwaysDefaultContentTypeForSignature == null) isAlwaysDefaultContentTypeForSignature = "false";
%>
<object id="appfirma" name="appfirma" classid="clsid:CAFEEFAC-0015-0000-FFFF-ABCDEFFEDCBA" width="230" height="35" 
	codebase="https://java.sun.com/update/1.5.0/jinstall-1_5_0_12-windows-i586.cab">
	<param name="code" value="es.caib.sistra.plugins.firma.impl.caib.applet.AppletFirma" />
	<param name="codebase" value="<%=request.getContextPath()%>/firma/caib">
	<param name="archive" value="signaturaapi-2.3.jar,appletFirma.jar" />
	<param name="idioma" value="es" />		
	<param name="scriptable" value="true" />
	<param name="useAlwaysDefaultContentType" value="<%= isAlwaysDefaultContentTypeForSignature%>" />
	<comment>
		<embed type="application/x-java-applet;version=1.5"
		width="230" height="35" align="baseline" 
		code="es.caib.sistra.plugins.firma.impl.caib.applet.AppletFirma" 
		codebase="<%=request.getContextPath()%>/firma/caib"
		archive="signaturaapi-2.3.jar,appletFirma.jar"
		pluginspage="https://java.sun.com/j2se/1.5.0/download.html" 
		idioma="es"
		id="appfirma2"
		scriptable="true"
		useAlwaysDefaultContentType="<%= isAlwaysDefaultContentTypeForSignature%>">
			<noembed>
				No te suport per applets Java 2 SDK, Standard Edition v 1.5 ! !
			</noembed>
		</embed>		
	</comment>
</object>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
	<!-- peu -->
	<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>
	<bean:define id="referenciaPortal"  type="java.lang.String">
		<bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property='<%="referenciaPortal("+ lang +")"%>'/>
	</bean:define>
	<bean:define id="asunto" type="java.lang.String">	
		<bean:message key="footer.soporte.ayuda.asunto" arg0="<%=referenciaPortal%>"/>
	</bean:define>
	<bean:define id="urlSoporte" type="String">
		<bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="urlSoporteIncidencias"/>
	</bean:define>
	<bean:define id="telefonoSoporte" type="String">
		<logic:notEmpty name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias">
		<bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias"/>
		</logic:notEmpty>
		<logic:empty name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias" >
			&nbsp;
		</logic:empty>
	</bean:define>
	<%
		// Construimos url de soporte reemplazando variables
		String textoAsunto = es.caib.util.StringUtil.replace(asunto,"\"","\\\"");
	    String urlSoporteFinal = es.caib.util.StringUtil.replace(urlSoporte,"@asunto@",textoAsunto);
	    urlSoporteFinal = es.caib.util.StringUtil.replace(urlSoporteFinal,"@idioma@",lang);			
	%>
<logic:equal name="<%=es.caib.zonaper.front.Constants.MOSTRAR_EN_IFRAME%>" value="false">	
	<!-- ayuda -->
		<div id="suportTecnic">
			<h2><bean:message key="footer.soporte.ayuda"/></h2>
			<p>
				<bean:message key="footer.soporte.ayuda.texto" arg0="<%=urlSoporteFinal%>" arg1="<%=telefonoSoporte%>"/>
			</p>
			<!-- 
			<form name="contactoAdmin" action="/sistrafront/protected/init.do" target="_blank">
				<input type="hidden" name="modelo" value="IN0014CON" />
				<input type="hidden" name="version" value="1" />
				<input type="hidden" name="centre" value="WEB" />
				<input type="hidden" name="tipus_escrit" value="PTD" />
				<input type="hidden" name="asunto" value="<%=asunto%>" />
				<input type="hidden" name="language" value="<%=lang%>" />
				
				<div class="botonera">
					<input id="suportContactar" type="submit" value="CONTACTAR" />
					<input id="suportDescartar" type="button" value="Descartar" />
				</div>
			</form>		
				 -->
			<p align="center">
				<a id="suportDescartar" title="<bean:message key="footer.soporte.continuar"/>" href="javascript:void(0);">
					<bean:message key="footer.soporte.continuar"/>
				</a>
			</p>			
		</div>
		<!-- /ayuda -->	
	
		<div id="peu">
			
			<div class="esquerra">&copy; <bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre" /></div>
			
			<!-- contacte -->
			<div class="centre">
				<bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="pieContactoHTML" filter="false"/>				
			</div>
			<!-- /contacte -->
			
			<div class="dreta">
				<strong><bean:message key="footer.necesitaAyuda"/></strong> <bean:message key="footer.soporte.inicio"/> 
				<logic:notEmpty name="telefonoSoporte">
					<logic:equal name="telefonoSoporte" value="&nbsp;">
						<a href="<%=urlSoporteFinal%>" target="_blank"><bean:message key="footer.soporte.fin"/></a>.
					</logic:equal>
					<logic:notEqual name="telefonoSoporte" value="&nbsp;">
						<a id="equipSuport"><bean:message key="footer.soporte.fin"/></a>.					
					</logic:notEqual>
				</logic:notEmpty>
				<logic:empty name="telefonoSoporte">
					<a href="<%=urlSoporteFinal%>" target="_blank"><bean:message key="footer.soporte.fin"/></a>.
				</logic:empty>
		
		</div>
		
</div>
</logic:equal>
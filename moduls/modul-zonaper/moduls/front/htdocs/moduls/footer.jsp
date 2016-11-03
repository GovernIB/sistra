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
	<bean:define id="telefonoSoporte" type="String">
		<logic:notEmpty name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias">
			<bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias" />
		</logic:notEmpty>
		<logic:empty name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias">
			&nbsp;
		</logic:empty>				
	</bean:define>	
	<bean:define id="urlSoporte" type="String">
		<logic:notEmpty name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="urlSoporteIncidencias">
			<bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="urlSoporteIncidencias" />
		</logic:notEmpty>
		<logic:empty name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="urlSoporteIncidencias">
			&nbsp;
		</logic:empty>			
	</bean:define>	
	<bean:define id="emailSoporte" type="String">
		<logic:notEmpty name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="emailSoporteIncidencias">
			<bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="emailSoporteIncidencias" />
		</logic:notEmpty>
		<logic:empty name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="emailSoporteIncidencias">
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
				<!--  Soporte por url y telefono (opcional) -->
				<logic:notEqual name="urlSoporte" value="&nbsp;">
					<logic:notEqual name="telefonoSoporte" value="&nbsp;">
						<bean:message key="administrador.soporteUrlTelefono" arg0="<%=urlSoporteFinal%>" arg1="<%=telefonoSoporte%>"/>
					</logic:notEqual>
					<logic:equal name="telefonoSoporte" value="&nbsp;">
						<bean:message key="administrador.soporteUrl" arg0="<%=urlSoporteFinal%>"/>
					</logic:equal>					
				</logic:notEqual>
				
				<!--  Soporte por email y telefono (opcional) -->
				<logic:equal name="urlSoporte" value="&nbsp;">
					<logic:notEqual name="emailSoporte" value="&nbsp;">
						<logic:notEqual name="telefonoSoporte"  value="&nbsp;">
							<bean:message key="administrador.soporteEmailTelefono" arg0="<%=emailSoporte%>" arg1="<%=telefonoSoporte%>"/>
						</logic:notEqual>
						<logic:equal name="telefonoSoporte"  value="&nbsp;">
							<bean:message key="administrador.soporteEmail" arg0="<%=emailSoporte%>"/>
						</logic:equal>					
					</logic:notEqual>
				</logic:equal>					
			</p>			
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
				<strong><bean:message key="footer.necesitaAyuda"/>
				</strong> <bean:message key="footer.soporte.inicio"/>
				<a id="equipSuport"><bean:message key="footer.soporte.fin"/></a> 										
			</div>
		
</div>
</logic:equal>
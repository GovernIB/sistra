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
	
<logic:equal name="<%=es.caib.zonaper.front.Constants.MOSTRAR_EN_IFRAME%>" value="false">	
	<!-- ayuda -->
		<div id="suportTecnic">
			<h2><bean:message key="footer.soporte.ayuda"/></h2>
			<div id="contactoAdministradorSoporte">	
			<p>
					<bean:write name="literalContacto" filter="false"/>					
			</p>		
			</div>
			<div id="contactoAdministradorContent"></div>	
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
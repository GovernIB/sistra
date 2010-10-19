<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<!--  Contacto soporte --> 
	<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>
	<bean:define id="urlSoporte" type="String">
		<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="urlSoporteIncidencias" />
	</bean:define>
	<bean:define id="tituloJs" type="String">
		<bean:write name="descripcion" />	
	</bean:define>
	<bean:define id="telefonoSoporte" type="String">
		<logic:notEmpty name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias" >
			<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias" />
		</logic:notEmpty>
		<logic:empty  name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias" >
			&nbsp;
		</logic:empty>
	</bean:define>
	<%
		// Construimos url de soporte reemplazando variables
		String tituloTramite = es.caib.util.StringUtil.replace(tituloJs,"\"","\\\"");
	    String urlSoporteFinal = es.caib.util.StringUtil.replace(urlSoporte,"@asunto@",tituloTramite);
	    urlSoporteFinal = es.caib.util.StringUtil.replace(urlSoporteFinal,"@idioma@",lang);			
	%>
<logic:equal name="<%=es.caib.sistra.front.Constants.MOSTRAR_EN_IFRAME%>" value="false">
		<!-- peu -->
		<div id="peu">
			
			<div class="esquerra">&copy; <bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/></div>
			
			<!-- contacte -->
			<div class="centre">			
				<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="pieContactoHTML" filter="false"/>				
			</div>
			
			<!-- /contacte -->
			<div class="dreta">
				<bean:message key="header.mailAdministrador"/>
				<logic:notEmpty name="telefonoSoporte">
					<logic:equal name="telefonoSoporte" value="&nbsp;">
						<a href="<%=urlSoporteFinal%>" target="_blank"><bean:message key="header.mailAdministrador.enlace"/></a>.
					</logic:equal>
					<logic:notEqual name="telefonoSoporte" value="&nbsp;">
				 <a href="javascript:void(0)" onclick="mostrarAyudaAdmin();">
					<bean:message key="header.mailAdministrador.enlace"/>
						</a>.					
					</logic:notEqual>
				</logic:notEmpty>
				<logic:empty name="telefonoSoporte">
					<a href="<%=urlSoporteFinal%>" target="_blank"><bean:message key="header.mailAdministrador.enlace"/></a>.
				</logic:empty>
			</div>
		
		</div>
</logic:equal>
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
		<bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="telefonoIncidencias"/>
	</bean:define>
	<%
		// Construimos url de soporte reemplazando variables
		String textoAsunto = es.caib.util.StringUtil.replace(asunto,"\"","\\\"");
		String urlSoporteFinal = es.caib.util.StringUtil.replace(urlSoporte,"@asunto@",textoAsunto);
		urlSoporteFinal = es.caib.util.StringUtil.replace(urlSoporteFinal,"@idioma@",lang);			
	%>
	
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
	</div>
	<%
		String idioma = es.caib.zonaper.front.util.LangUtil.getLang(request);
		if (idioma == null) {
			idioma = "ct";
		} else {
			if (idioma.equals("ca")) {
				idioma = "ct";
			}
		}
	%>
	<div class="footer">
		<div class="interior">
			<h4><bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/></h4>
			<address><bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="pieContactoHTML" filter="false"/></address>
			<ul class="menupeu">
				<li><a href="http://www.ajsantamargalida.net/dadesPeu/avis.<%= idioma %>.html" target="_blank"><bean:message key="pie.avisoLegal" /></a></li>
				<li><a href="http://www.ajsantamargalida.net/contacte/index.<%= idioma %>.html" target="_blank"><bean:message key="pie.contacto" /></a></li>
				<li><a href="http://www.ajsantamargalida.net/dadesPeu/proteccio_dades.<%= idioma %>.html" target="_blank"><bean:message key="pie.proteccion" /></a></li>
			</ul>
			<ul class="accessibilitat">
				<li><a href="http://jigsaw.w3.org/css-validator/check/refererr" target="_blank"><img src="imgs/css21.gif" width="66" height="31" alt="CSS 2.1" /></a></li>
				<li><a href="http://validator.w3.org/check?uri=referer" target="_blank"><img src="imgs/xhtml10.gif" width="64" height="31" alt="XHTML 1.0" /></a></li>
				<li><a href="http://www.w3.org/WAI/WCAG1AA-Conformance" target="_blank"><img src="imgs/wai.gif" width="66" height="31" alt="WAI-AA" /></a></li>
				<li class="rss"><a href="http://www.ajsantamargalida.net/dadesPeu/rss.<%= idioma %>.html" target="_blank"><img src="imgs/rss.gif" width="31" height="31" alt="WAI-AA" /><span><bean:message key="pie.sindicacion" /></span></a></li>
			</ul>
		</div>
	</div>
	
</div>
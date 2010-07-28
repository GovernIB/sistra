<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
	<!--footer-->
<%
	String idioma = es.caib.redose.front.util.LangUtil.getLang(request);
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
			<h4><bean:write name="<%=es.caib.redose.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/></h4>
			<address><bean:write name="<%=es.caib.redose.front.Constants.ORGANISMO_INFO_KEY%>" property="pieContactoHTML" filter="false"/></address>
			<ul class="menupeu">
				<li><a href="http://www.ajsantamargalida.net/dadesPeu/avis.<%= idioma %>.html" target="_blank"><bean:message key="pie.avisoLegal"/></a></li>
				<li><a href="http://www.ajsantamargalida.net/contacte/index.<%= idioma %>.html" target="_blank"><bean:message key="pie.contacto"/></a></li>
				<li><a href="http://www.ajsantamargalida.net/dadesPeu/proteccio_dades.<%= idioma %>.html" target="_blank"><bean:message key="pie.proteccion"/></a></li>
			</ul>
			<ul class="accessibilitat">
				<li><a href="http://jigsaw.w3.org/css-validator/check/refererr" target="_blank"><img src="imgs/peu/css21.gif" width="66" height="31" alt="CSS 2.1" /></a></li>
				<li><a href="http://validator.w3.org/check?uri=referer" target="_blank"><img src="imgs/peu/xhtml10.gif" width="64" height="31" alt="XHTML 1.0" /></a></li>
				<li><a href="http://www.w3.org/WAI/WCAG1AA-Conformance" target="_blank"><img src="imgs/peu/wai.gif" width="66" height="31" alt="WAI-AA" /></a></li>
				<li class="rss"><a href="http://www.ajsantamargalida.net/dadesPeu/rss.<%= idioma %>.html" target="_blank"><img src="imgs/peu/rss.gif" width="31" height="31" alt="WAI-AA" /><span><bean:message key="pie.sindicacion" /></span></a></li>
			</ul>
		</div>
	</div>
	<!--fi footer-->
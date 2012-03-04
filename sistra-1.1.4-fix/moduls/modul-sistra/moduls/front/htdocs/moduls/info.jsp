<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
		<div id="tramitacion">
			<!-- paso en cuestión -->
			<div id="pasoInf">
			<tiles:insert name=".message" />
			<h2><bean:write name="message" property="mensaje" /></h2>
			<p><img src="imgs/nada.gif" height="220"/></p>
			<p class="ultimo"><bean:write name="message" property="mensaje" /></p>
			<div class="sep"></div>
			</div>
		</div>
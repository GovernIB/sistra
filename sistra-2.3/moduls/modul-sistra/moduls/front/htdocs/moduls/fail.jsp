<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
		<div id="tramitacion">
			<!-- paso en cuestión -->
			<div id="pasoInf">
			<tiles:insert name=".message" />
			<h2>Servicio temporalmente no disponible</h2>
			<p>
				<html:errors />
			    <hr />
			    <html:link forward="main">Inicio</html:link>
			</p>
			<p class="ultimo">Ha intentado realizar una navegación no permitida o el servicio se encuentra fuera de servicio de forma temporal. Si esta situación persiste, pongase en contacto con el administrador.</p>
			<div class="sep"></div>
			</div>
		</div>
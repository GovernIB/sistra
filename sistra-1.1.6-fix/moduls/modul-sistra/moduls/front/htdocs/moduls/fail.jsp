<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
		<div id="tramitacion">
			<!-- paso en cuesti�n -->
			<div id="pasoInf">
			<tiles:insert name=".message" />
			<h2>Servicio temporalmente no disponible</h2>
			<p>
				<html:errors />
			    <hr />
			    <html:link forward="main">Inicio</html:link>
			</p>
			<p class="ultimo">Ha intentado realizar una navegaci�n no permitida o el servicio se encuentra fuera de servicio de forma temporal. Si esta situaci�n persiste, pongase en contacto con el administrador.</p>
			<div class="sep"></div>
			</div>
		</div>
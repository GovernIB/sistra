<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
		<div id="tramitacion">
			<!-- paso en cuesti�n -->
			<div id="pasoInf">
			<tiles:insert name=".message" />
			<h2><bean:message key="error.servicioNoDisponible"/></h2>
			<p class="ultimo"><bean:message key="error.informacion"/>.</p>
			<div class="separacio"></div>
			</div>
		</div>
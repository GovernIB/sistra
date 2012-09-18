<%@ page isErrorPage="true" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%
	exception.printStackTrace();
%>
		<div id="tramitacion">
			<!-- paso en cuestión -->
			<div id="pasoInf">
			<tiles:insert name=".messageErrorJsp" />
			<h2>Servicio temporalmente no disponible</h2>
			<p>Ha intentado realizar una navegación no permitida o el servicio se encuentra fuera de servicio de forma temporal. Si esta situación persiste, pongase en contacto con el administrador.</p>
			<p class="ultimo"><html:link action="main">Volver</html:link></p>
			<div class="sep"></div>
			</div>
		</div>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%
	exception.printStackTrace();
%>
		<div id="tramitacion">
			<!-- paso en cuesti�n -->
			<div id="pasoInf">
			<tiles:insert name=".messageErrorJsp" />
			<h2>Servicio temporalmente no disponible</h2>
			<p>Ha intentado realizar una navegaci�n no permitida o el servicio se encuentra fuera de servicio de forma temporal. Si esta situaci�n persiste, pongase en contacto con el administrador.</p>
			<p class="ultimo"><html:link action="main">Volver</html:link></p>
			<div class="sep"></div>
			</div>
		</div>
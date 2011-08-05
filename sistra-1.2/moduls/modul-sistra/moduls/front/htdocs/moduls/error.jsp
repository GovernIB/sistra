<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>

		<div id="tramitacion">
			<!-- paso en cuestión -->
			<div id="pasoInf">
			<tiles:insert name=".message" />
			<h2><bean:message key="errors.general.titulo"/></h2>
			<p class="ultimo"><bean:message key="errors.general.texto"/></p>
			<div class="sep"></div>
			</div>
		</div>
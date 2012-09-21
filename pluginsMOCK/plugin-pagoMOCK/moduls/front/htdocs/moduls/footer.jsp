<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
	<!-- peu -->
<logic:equal name="<%=es.caib.pagos.front.Constants.MOSTRAR_EN_IFRAME%>" value="false">
	<div id="peu">
		<a href="#" title="Enllaç a la web d'Administració Digital"><img id="logoAD" src="imgs/peu/logo_ad.gif" alt="Logo de l'Administraci&oacute; Digital" /></a>
		&copy; Govern de les Illes Balears
	</div>
</logic:equal>
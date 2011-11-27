<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<logic:equal name="<%=es.caib.zonaper.filter.front.Constants.MOSTRAR_EN_IFRAME%>" value="false">
	<!-- peu -->
	<div id="peu">
		&copy; <bean:write name="<%=es.caib.zonaper.filter.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/>
	</div>
</logic:equal>
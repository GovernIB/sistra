<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.ibit.rol.form.model.Validacion"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>

<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.Label"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>

	<div class="<%=org.ibit.rol.form.front.util.UtilFrontV2.generateStyleClass(campo)%>">
		<div class="imc-el-etiqueta">
			<nested:equal property="sinEtiqueta" value="false">
				<nested:write property="traduccion.etiqueta"/>
			</nested:equal>			
		</div>		
	</div>

</nested:root>
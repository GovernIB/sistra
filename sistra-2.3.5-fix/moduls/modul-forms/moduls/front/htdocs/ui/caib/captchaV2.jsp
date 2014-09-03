<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.ibit.rol.form.model.Validacion"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>

<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.Captcha"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    <nested:define id="etiquetaTxt" type="java.lang.String" property="traduccion.nombre"/>

	<div class="<%=org.ibit.rol.form.front.util.UtilFrontV2.generateStyleClass(campo)%>" data-type="text">
   		<div class="imc-el-etiqueta">
			<nested:equal property="sinEtiqueta" value="false">
				<label for="<%=nombre%>"><%=org.ibit.rol.form.front.util.UtilFrontV2.generaHtmlEtiqueta(etiquetaTxt)%></label>
			</nested:equal>			
		</div>
		<div class="imc-el-control">
				<img id="<%=nombre%>_imgCaptcha" src='captchaDownload.do?fieldName=<%=nombre%>&ID_INSTANCIA=<%=request.getAttribute("ID_INSTANCIA")%>'>		 
			 	<input type="text" name="<%=nombre%>"/>			 
			 	<a href="javascript:recaptcha('<%=nombre%>');" class="imc-bt-refrescar" title="<bean:message bundle="caibMessages" key="captcha.refrescar"/>">&nbsp;</a>			 	 			 						 					 					 			 
		</div>
		<nested:notEmpty property="traduccion.ayuda">
		<div class="imc-el-ajuda">
			<p><nested:write property="traduccion.ayuda"/></p>
		</div>		
		</nested:notEmpty>
	</div>
		
</nested:root>

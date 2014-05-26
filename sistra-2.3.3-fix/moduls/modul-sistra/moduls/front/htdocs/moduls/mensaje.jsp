<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.sistra.model.MensajeFront,es.caib.util.StringUtil,org.apache.commons.lang.StringEscapeUtils,es.caib.sistra.front.Constants" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<logic:present name="message">
	<bean:define id="mensajeFront" name="<%=Constants.MENSAJE_KEY%>" type="es.caib.sistra.model.MensajeFront" />
	<bean:define id="mensajeParam" name="<%=Constants.MENSAJE_PARAM%>" type="es.caib.sistra.model.ParametrosMensaje" />
	<bean:define id="mensajeAtencion" type="java.lang.String">
		<bean:message key="message.atencion" />
	</bean:define>
	<bean:define id="mensajeInformacion" type="java.lang.String">
		<bean:message key="message.informacion"/>
	</bean:define>
	<bean:define id="mensaje" type="java.lang.String">
		<bean:write name="message" property="mensaje" />
	</bean:define>
	<%
		String capaInfoClass = " class='ok'";
		String encabezado = mensajeAtencion;
			
		if ( MensajeFront.TIPO_ERROR == mensajeFront.getTipo() )
		{
			capaInfoClass = " class='error'";
		}
		if ( MensajeFront.TIPO_ERROR_CONTINUABLE == mensajeFront.getTipo() )
		{
			capaInfoClass = " class='error'";
		}
		if ( MensajeFront.TIPO_WARNING == mensajeFront.getTipo() )
		{
			capaInfoClass = "";
		}
		if ( MensajeFront.TIPO_INFO == mensajeFront.getTipo() )
		{
			capaInfoClass = " class='ok'";
			encabezado = mensajeInformacion;
		}		
		
		
		// Reemplazamos saltos de linea por <br/>
 		//String mensajeHtml = StringEscapeUtils.escapeHtml(mensaje);
 		String mensajeHtml = mensaje;
 		mensajeHtml = StringUtil.replace(mensajeHtml,"\n","<br/>");
		
	%>

	<!-- capa de información -->
	<div id="capaInfoFondo"></div>
	<div id="capaInfo"<%=capaInfoClass %>>	
		<p class="atencion"><%= encabezado  %></p>
		<p><%=mensajeHtml%></p>
		<bean:define id="mostrarMensajeDebug" value="<%=request.getSession().getServletContext().getInitParameter(\"mensaje.debug\")%>" />
		<logic:equal name="mostrarMensajeDebug" value="true">
			<logic:notEmpty name="mensajeFront" property="mensajeExcepcion">
				<a style="color:#808080;" onclick="$('#mensajeDepuracionDiv').show();"><bean:message key="mensajeDepuracion.enlace"/></a>
				<div id="mensajeDepuracionDiv" style="display:none;">
					<p style="color:#808080;"><i><bean:write name="mensajeFront" property="mensajeExcepcion" /></i></p>
				</div>		
			</logic:notEmpty>
		</logic:equal>		
		
		<logic:notEmpty name="mensajeParam" property="action">
			<logic:notEmpty name="mensajeParam" property="target">
				<p align="center"><html:link action="<%=mensajeParam.getAction()%>" target="<%=mensajeParam.getTarget()%>" name="mensajeParam" property="parametros"><bean:message key="message.continuar"/></html:link></p>
			</logic:notEmpty>
			<logic:empty name="mensajeParam" property="target">
				<p align="center"><html:link action="<%=mensajeParam.getAction()%>" name="mensajeParam" property="parametros">
					<bean:message key="message.continuar"/>
				</html:link></p>
			</logic:empty>			
		</logic:notEmpty>
		
		<logic:empty name="mensajeParam" property="action">
			<logic:equal name="mensajeFront" property="tipo" value="<%=Integer.toString(MensajeFront.TIPO_ERROR)%>">
				<p align="center"><html:link action="main"><bean:message key="message.continuar"/></html:link></p>
			</logic:equal>
			<logic:notEqual name="mensajeFront" property="tipo" value="<%=Integer.toString(MensajeFront.TIPO_ERROR)%>">
				<p align="center"><a href="#" onclick="cerrarInfo()"><bean:message key="message.continuar"/></a></p>
			</logic:notEqual>			
		</logic:empty>		
	</div>
	<!-- end capa de informacion -->
</logic:present>
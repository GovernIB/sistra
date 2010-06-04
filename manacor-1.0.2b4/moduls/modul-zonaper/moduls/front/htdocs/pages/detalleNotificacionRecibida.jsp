<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ page import="es.caib.util.StringUtil"%>


<bean:define id="mensaje" type="java.lang.String">
	<bean:write name="oficioNotificacion" property="texto"/>
</bean:define>
<%
	// Reemplazamos saltos de linea por <br/>
 	String mensajeHtml = mensaje;
 	mensajeHtml = StringUtil.replace(mensajeHtml,"\n","<br/>");
%>


<h1>
	<bean:message key="detalleNotificacion.titulo" />
</h1>

<!-- notificacio -->
<div id="notificacio">
	<dl>
		<dt><bean:message key="detalleNotificacion.organo"/>:</dt>
		<dd>
			<logic:empty name="unidadAdministrativa">
				&nbsp;
			</logic:empty>
			<logic:notEmpty name="unidadAdministrativa">
				<bean:write name="unidadAdministrativa"/>
			</logic:notEmpty>			
		</dd>
		<dt><bean:message key="detalleNotificacion.expediente"/>:</dt>
		<dd><bean:write name="codigoExpediente"/></dd>
		<dt><bean:message key="detalleNotificacion.fechaEmision"/>:</dt>
		<dd><bean:write name="notificacion" property="fechaRegistro" format="dd/MM/yyyy '-' HH:mm"/></dd>
		<dt><bean:message key="detalleNotificacion.fechaApertura"/>:</dt>
		<dd><bean:write name="notificacion" property="fechaAcuse" format="dd/MM/yyyy '-' HH:mm"/><logic:equal name="notificacion" property="firmarAcuse" value="true"> *</logic:equal></dd>
		<dt><bean:message key="detalleNotificacion.asunto"/>:</dt>
		<dd><bean:write name="oficioNotificacion" property="titulo"/></dd>
		<dt><bean:message key="detalleNotificacion.descripcion"/>:</dt>
		<dd><%=mensajeHtml%></dd>	
		<logic:notEmpty name="notificacion" property="documentos">		
			<dt><bean:message key="detalleNotificacion.documentos" />:</dt>
			<dd>
				<ul class="docs">
					<logic:iterate id="documento" name="notificacion" property="documentos">
					<li>
						<bean:define id="urlMostrarDocumento" type="java.lang.String">
							<html:rewrite page="/protected/mostrarDocumentoNotificacion.do" paramId="codigoNotificacion" paramName="notificacion" paramProperty="codigo"/>
						</bean:define>
						
						<html:link href="<%=urlMostrarDocumento%>" paramId="codigo" paramName="documento" paramProperty="codigo">
							<bean:write name="documento" property="descripcion" />
						</html:link>
					</li>
					</logic:iterate>
				</ul>
			</dd>				
		</logic:notEmpty>
	</dl>		
</div>
<!-- /notificacio -->
		
<logic:equal name="notificacion" property="firmarAcuse" value="true">
	<div id="notaLegalPie">
		<p class="data"><sup>*</sup><bean:message key="detalleNotificacion.notaLegalAbierta"/></p>
	</div>
</logic:equal>


</div>
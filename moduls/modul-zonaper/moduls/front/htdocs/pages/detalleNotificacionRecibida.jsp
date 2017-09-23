<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ page import="es.caib.util.StringUtil"%>


<bean:define id="mensaje" type="java.lang.String">
	<bean:write name="oficioNotificacion" property="texto"/>
</bean:define>

<bean:define id="urlMostrarDocumento" type="java.lang.String">
	<html:rewrite page="/protected/mostrarDocumentoNotificacion.do" paramId="codigoNotificacion" paramName="notificacion" paramProperty="codigo"/>
</bean:define>
<bean:define id="urlTramiteSubsanacion" type="java.lang.String">
	<html:rewrite page="/protected/tramiteSubsanacion.do" paramId="codigoNotificacion" paramName="notificacion" paramProperty="codigo"/>
</bean:define>
<bean:define id="plazoDias" name="plazoDias" type="java.lang.String"/>
<%
	// Reemplazamos saltos de linea por <br/>
 	String mensajeHtml = mensaje;
 	mensajeHtml = StringUtil.replace(mensajeHtml,"\n","<br/>");
%>
<h1>
	<bean:message key="detalleNotificacion.titulo" />
</h1>

<p><bean:message key="detalleNotificacion.accedaContenido"/></p>

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
		<dd><bean:write name="codigoExpediente"/> - <bean:write name="descExpediente"/></dd>
		<dt><bean:message key="detalleNotificacion.fechaEmision"/>:</dt>
		<dd><bean:write name="notificacion" property="fechaRegistro" format="dd/MM/yyyy '-' HH:mm"/></dd>
		<dt><bean:message key="detalleNotificacion.fechaApertura"/>:</dt>
		<dd><bean:write name="notificacion" property="fechaAcuse" format="dd/MM/yyyy '-' HH:mm"/><logic:equal name="notificacion" property="firmarAcuse" value="true"> *</logic:equal></dd>
		<dt><bean:message key="detalleNotificacion.asunto"/>:</dt>
		<dd><bean:write name="oficioNotificacion" property="titulo"/></dd>
		<dt><bean:message key="detalleNotificacion.descripcion"/>:</dt>
		<dd><%=mensajeHtml%></dd>	
		
		<!-- Documentos -->
		<logic:notEmpty name="notificacion" property="documentos">		
			<dt><bean:message key="detalleNotificacion.documentos" />:</dt>
			<dd>
				<ul class="docs">
					<logic:iterate id="documento" name="notificacion" property="documentos">
					<li>
						<bean:define id="codigoFirma" type="java.lang.String">
							<bean:write name="documento" property="codigo" />
						</bean:define>
						
						<logic:notEmpty name="<%=\"URL-\" + codigoFirma %>" scope="request">
							<a href="<bean:write name="<%=\"URL-\" + codigoFirma %>" scope="request"/>" target="_blank">
								<bean:write name="documento" property="descripcion" />
							</a>																				
						</logic:notEmpty>
						
						<logic:empty name="<%=\"URL-\" + codigoFirma %>" scope="request">
							<html:link href="<%=urlMostrarDocumento%>" paramId="codigo" paramName="documento" paramProperty="codigo">
								<bean:write name="documento" property="descripcion" />
							</html:link>
						</logic:empty>
						
						<logic:notEmpty name="<%=codigoFirma %>" scope="request">
							<bean:message key="comprobarDocumento.firmadoPor"/>
							<logic:iterate name="<%=codigoFirma %>" id="firma" scope="request">							
								&nbsp;<bean:write name="firma" property="nombreApellidos"/> 
								<logic:notEmpty name="firma" property="nifRepresentante">
									&nbsp; <bean:message key="firma.representadoPor"/> <bean:write name="firma" property="nombreApellidosRepresentante"/> - NIF: <bean:write name="firma" property="nifRepresentante"/>
								</logic:notEmpty> 										
							</logic:iterate>			
						</logic:notEmpty>
					</li>
					</logic:iterate>
				</ul>
			</dd>				
		</logic:notEmpty>
		
		<!-- Tramite subsanacion -->
		<logic:notEmpty name="notificacion" property="tramiteSubsanacionIdentificador">
			<dt><bean:message key="detalleNotificacion.tramite.subsanacion" />:</dt>
			<dd>
			<html:link href="<%=urlTramiteSubsanacion%>">
				<bean:write name="notificacion" property="tramiteSubsanacionDescripcion"/>	
			</html:link>			 
			</dd>
		</logic:notEmpty>		
	</dl>		
</div>
<logic:equal name="notificacion" property="firmarAcuse" value="true">
	<div id="notaLegalPie">
		<p class="data"><sup>*</sup><bean:message key="detalleNotificacion.notaLegalAbierta" arg0="<%=plazoDias%>"/></p>
	</div>
</logic:equal>
<!-- /notificacio -->

<!-- reader -->
<p id="getAdobeReader">
	<bean:message key="detalleNotificacion.getAdobeReader"/>
	<br />
	<a href="http://www.adobe.es/products/acrobat/readstep2.html"><bean:message key="detalleNotificacion.irWebAdobe"/></a>
</p>

<!-- justificant -->
<p>
	<i>
		<bean:message key="detalleNotificacion.datosJustificante.texto" /> <a href='<html:rewrite href="<%= urlMostrarDocumento %>"/>'><bean:message key="detalleNotificacion.datosJustificante.enlace" /></a>
	</i>
</p>

	



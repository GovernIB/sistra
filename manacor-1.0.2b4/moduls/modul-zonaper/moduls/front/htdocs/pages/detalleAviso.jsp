<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ page import="es.caib.util.StringUtil"%>

<h1><bean:message key="detalleAviso.titulo" /></h1>

<bean:define id="fechaRecepcion" type="java.lang.String">
<bean:write name="aviso" property="fecha" format="dd/MM/yyyy '-' HH:mm"/>
</bean:define>

<bean:define id="fechaLectura" type="java.lang.String">
<bean:write name="aviso" property="fechaConsulta" format="dd/MM/yyyy '-' HH:mm"/>
</bean:define>

<bean:define id="mensaje" type="java.lang.String">
	<bean:write name="aviso" property="texto" />
</bean:define>
<%
	// Reemplazamos saltos de linea por <br/>
 	String mensajeHtml = mensaje;
 	mensajeHtml = StringUtil.replace(mensajeHtml,"\n","<br/>");
%>

<!-- avis -->
<div id="avis">
	<dl>
		<dt><bean:message key="detalleAviso.organo"/>:</dt>
		<dd>
			<logic:empty name="unidadAdministrativa">
				&nbsp;
			</logic:empty>
			<logic:notEmpty name="unidadAdministrativa">
				<bean:write name="unidadAdministrativa"/>
			</logic:notEmpty>			
		</dd>
		<dt><bean:message key="detalleAviso.expediente"/>:</dt>
		<dd><bean:write name="codigoExpediente"/></dd>
		<dt><bean:message key="detalleAviso.fechaEmision"/>:</dt>
		<dd><bean:write name="aviso" property="fecha" format="dd/MM/yyyy '-' HH:mm"/></dd>
		<dt><bean:message key="detalleAviso.fechaApertura"/>:</dt>
		<dd><bean:write name="aviso" property="fechaConsulta" format="dd/MM/yyyy '-' HH:mm"/></dd>
		<dt><bean:message key="detalleAviso.asunto"/>:</dt>
		<dd><bean:write name="aviso" property="titulo"/></dd>
		<dt><bean:message key="detalleAviso.descripcion"/>:</dt>
		<dd><%=mensajeHtml%></dd>	
		<logic:notEmpty name="aviso" property="documentos">		
			<dt><bean:message key="detalleAviso.documentos" />:</dt>
			<dd>
				<ul class="docs">
					<logic:iterate id="documento" name="aviso" property="documentos">
					<li>
						<bean:define id="urlMostrarDocumento" type="java.lang.String">
							<html:rewrite page="/protected/mostrarDocumentoAviso.do" paramId="codigoAviso" paramName="aviso" paramProperty="codigo"/>
						</bean:define>
						
						<html:link href="<%=urlMostrarDocumento%>" paramId="codigo" paramName="documento" paramProperty="codigo">
							<bean:write name="documento" property="titulo" />
						</html:link>
					</li>
					</logic:iterate>
				</ul>
			</dd>				
		</logic:notEmpty>
	</dl>		
</div>
<!-- /avis -->
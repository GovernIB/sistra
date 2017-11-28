<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.zonaper.helpdesk.front.Constants"%>
<%@ page import="es.caib.zonaper.model.DetallePagoTelematico"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="urlMostrarDocumento">
	<html:rewrite page="/abrirDocumento.do" paramId="idioma" paramName="tramiteBackup" paramProperty="idioma"/>
</bean:define>

	<h1 class="ajuda"><bean:message key="detalleTramite.datosTramite"/></h1>
	<div id="titolOmbra"></div>
	
		<!-- continguts -->
	<div class="continguts">
	
			<!-- justificant -->
		<div id="justificant">
    			<!-- justificant -->
			<table class="pagament" cellpadding="0" cellspacing="0">
			<caption><bean:message key="detalleTramite.datosSolicitud"/></caption>
			<tbody>
				<tr>
					<th><bean:message key="detalleTramite.datosTramite.identificador"/></th>
					<td><bean:write name="tramiteBackup" property="tramite"/>	</td>
				</tr>
				<tr>
					<th><bean:message key="detalleTramite.datosTramite.version"/></th>
					<td><bean:write name="tramiteBackup" property="version"/></td>
				</tr>
				<tr>
					<th><bean:message key="detalleTramite.datosTramite.descripcion"/></th>
					<td><bean:write name="tramiteBackup" property="descripcion"/></td>
				</tr>
				<tr>
					<th><bean:message key="detalleTramite.datosTramite.persistencia"/></th>
					<td><bean:write name="tramiteBackup" property="idPersistencia"/></td>
				</tr>
				<tr>
					<th><bean:message key="detalleTramite.datosTramite.nivelAutenticacion"/></th>
					<td>
						<logic:equal name="tramiteBackup" property="nivelAutenticacion" value="<%= Character.toString(Constants.MODO_AUTENTICACION_CERTIFICADO) %>">
							<bean:message key="formularioBusqueda.nivel.certificado"/>	
						</logic:equal>
						<logic:equal name="tramiteBackup" property="nivelAutenticacion" value="<%= Character.toString(Constants.MODO_AUTENTICACION_USUARIO_PWD) %>">
							<bean:message key="formularioBusqueda.nivel.usuario"/>
						</logic:equal>
						<logic:equal name="tramiteBackup" property="nivelAutenticacion" value="<%= Character.toString(Constants.MODO_AUTENTICACION_ANONIMO) %>">
							<bean:message key="formularioBusqueda.nivel.anonimo"/>
						</logic:equal>
					</td>
				</tr>
				<logic:notEqual name="tramiteBackup" property="nivelAutenticacion" value="<%= Character.toString(Constants.MODO_AUTENTICACION_ANONIMO) %>">
					<tr>
						<th><bean:message key="detalleTramite.datosTramite.usuario"/></th>
						<td>
							<logic:empty name="nombreCompleto">
								<bean:write name="tramiteBackup" property="usuario"/>
							</logic:empty>
							<logic:notEmpty name="nombreCompleto">
								<bean:write name="nombreCompleto"/>
							</logic:notEmpty>
							
						</td>
					</tr>
				</logic:notEqual>
				<tr>
					<th><bean:message key="detalleTramite.datosTramite.idioma"/></th>
					<td>
						<logic:equal name="tramiteBackup" property="idioma" value='<%= Constants.CATALAN %>'>
							<bean:message key="resultadoBusqueda.idioma.catalan"/>	
						</logic:equal>
						<logic:equal name="tramiteBackup" property="idioma" value='<%= Constants.CASTELLANO %>'>
							<bean:message key="resultadoBusqueda.idioma.castellano"/>	
						</logic:equal>
					</td>
				</tr>
			</tbody>
			</table>
			
			<logic:present name="tramiteBackup" property="documentos">
				<logic:notEmpty name="tramiteBackup" property="documentos">
					<table class="pagament" cellpadding="0" cellspacing="0">
						<caption><bean:message key="detalleTramite.documentos"/></caption>
						<tbody>
							<logic:iterate id="documento" name="tramiteBackup" property="documentos" type="es.caib.zonaper.model.DocumentoPersistente">
								<logic:equal name="documento" property="estado" value="S">
								<tr>
									<th class="tramits"></th>
									<td>
										<html:link href="<%= urlMostrarDocumento + "&amp;codigo=" + documento.getRdsCodigo() + "&amp;clave=" +documento.getRdsClave()%>" >
											<bean:write name="documento" property="identificador" />
											<logic:notEmpty name="documento" property="nombreFicheroAnexo">
												&nbsp;-&nbsp;<bean:write name="documento" property="nombreFicheroAnexo" />
											</logic:notEmpty>
										</html:link>
									</td>
								</tr>
								</logic:equal>
								<logic:notEqual name="documento" property="estado" value="S">
								<tr>
									<th class="tramits"></th>
									<td>
										<bean:write name="documento" property="identificador" />
										<logic:notEmpty name="documento" property="nombreFicheroAnexo">
											&nbsp;-&nbsp;<bean:write name="documento" property="nombreFicheroAnexo" />
										</logic:notEmpty>
									</td>
								<tr>
								</logic:notEqual>
							</logic:iterate>	
						</tbody>
					</table>
				</logic:notEmpty>
			</logic:present>
			<!-- /justificant -->
		
		</div>
		<!-- /botonera -->
	
	
	</div>
	<!-- /continguts -->
	<!-- tornar arrere -->
	<div id="tornarArrere"><a href="backup.do"><bean:message key="detalleTramite.volver"/></a></div>
	<!-- /tornar arrere -->

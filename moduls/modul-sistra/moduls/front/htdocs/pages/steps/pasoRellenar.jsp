<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>
<bean:define id="urlFormulario">
        <html:rewrite page="/protected/irAFormulario.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<bean:define id="urlFirmarDocumento">
        <html:rewrite page="/protected/irAFirmarFormulario.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>

<bean:define id="urlMostrarDocumento">
        <html:rewrite page="/protected/mostrarDocumento.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>

<bean:define id="flujoTramitacion" name="flujoTramitacion" type="java.lang.String"/>

<script type="text/javascript">
<!--
	var mensajeEnviando = '<bean:message key="pasoRellenar.mensajeAbrirFormulario"/>';
//-->
</script>
			<h2><bean:message key="pasoRellenar.rellenarFormularios"/></h2>
			<p class="ultimo">
				<bean:message key="pasoRellenar.rellenarFormularios.instrucciones.parrafo1"/>
				<bean:message key="pasoRellenar.rellenarFormularios.instrucciones.parrafo2"/>
				<!--  En caso de que existan documentos para firmar mostramos información de firma
				<logic:equal name="firmarFormularios" value="S">
					<br/><bean:message key="pasoRellenar.rellenarFormularios.instrucciones.parrafo3"/>
				</logic:equal>
				-->
				<logic:equal name="iconos" property="N" value="true">
					<br/>
					<bean:message key="pasoRellenar.rellenarFormularios.instrucciones.opcionales"/>
				</logic:equal>
			</p>
			<!-- iconografia -->
			<div id="iconografia">
			<a href="javascript:icosMasInfo();" id="iconografiaMasInfo" title="<bean:message key="iconografia.info" />"><bean:message key="iconografia.masInfo"/></a>
				<h4><bean:message key="pasoRellenar.rellenarFormularios.iconografia"/></h4>
				<ul>
					<logic:equal name="iconos" property="S" value="true">
						<li><img src="imgs/tramitacion/iconos/doc_obligatorio.gif" alt="<bean:message key="pasoRellenar.iconografia.formularioObligatorio"/>" /> <bean:message key="pasoRellenar.iconografia.formularioObligatorio"/><span><bean:message key="pasoRellenar.iconografia.formularioObligatorioInfo"/></span></li>
					</logic:equal>
					<logic:equal name="iconos" property="N" value="true">
						<li><img src="imgs/tramitacion/iconos/doc_opcional.gif" alt="<bean:message key="pasoRellenar.iconografia.formularioOpcional"/>" /> <bean:message key="pasoRellenar.iconografia.formularioOpcional"/><span><bean:message key="pasoRellenar.iconografia.formularioOpcionalInfo"/></span></li>
					</logic:equal>
					<logic:equal name="iconos" property="F" value="true">
						<li><img src="imgs/tramitacion/iconos/doc_firmar.gif" alt="<bean:message key="pasoRellenar.iconografia.firmarFormulario"/>" /> <bean:message key="pasoRellenar.iconografia.firma"/><span><bean:message key="pasoRellenar.iconografia.firmaInfo"/></span></li>
					</logic:equal>
					<li>
					<ul class="tipo">
						<li><img src="imgs/tramitacion/iconos/form_no_realizado.gif" alt="<bean:message key="pasoRellenar.iconografia.formularioNoRealizado"/>" /> <bean:message key="pasoRellenar.iconografia.formularioNoRealizado"/><span><bean:message key="pasoRellenar.iconografia.formularioNoRealizadoInfo"/></span></li>
						<li><img src="imgs/tramitacion/iconos/form_realizado.gif" alt="<bean:message key="pasoRellenar.iconografia.formularioRealizadoCorrectamente"/>" /> <bean:message key="pasoRellenar.iconografia.formularioRealizadoCorrectamente"/><span><bean:message key="pasoRellenar.iconografia.formularioRealizadoCorrectamenteInfo"/></span></li>
					</ul>
					</li>
				</ul>
			</div>
			<!-- Capa formularios -->
			<div id="listadoFormularios">

				<logic:iterate id="formulario" name="tramite" property="formularios" type="es.caib.sistra.model.DocumentoFront">
				<logic:notEqual name="formulario" property="obligatorio" value='D'>

					<!-- Iconos -->
					<div class="iconos">
						<logic:equal name="formulario" property="obligatorio" value='S'><img src="imgs/tramitacion/iconos/doc_obligatorio.gif" alt="<bean:message key="pasoRellenar.iconografia.formularioObligatorio"/>" title="<bean:message key="pasoRellenar.iconografia.formularioObligatorio"/>" /></logic:equal>
						<logic:equal name="formulario" property="obligatorio" value='N'><img src="imgs/tramitacion/iconos/doc_opcional.gif" alt="<bean:message key="pasoRellenar.iconografia.formularioOpcional"/>" title="<bean:message key="pasoRellenar.iconografia.formularioOpcional"/>" /></logic:equal>
						<logic:notEqual name="formulario" property="estado" value='S'><img src="imgs/tramitacion/iconos/form_no_realizado.gif" alt="<bean:message key="pasoRellenar.iconografia.formularioNoRealizado"/>" title="<bean:message key="pasoRellenar.iconografia.formularioNoRealizado"/>"/></logic:notEqual>
						<logic:equal name="formulario" property="estado" value='S'><img src="imgs/tramitacion/iconos/form_realizado.gif" alt="<bean:message key="pasoRellenar.iconografia.formularioRealizadoCorrectamente"/>" title="<bean:message key="pasoRellenar.iconografia.formularioRealizadoCorrectamente"/>"/></logic:equal>
						<logic:equal name="formulario" property="firmar" value="true"><img src="imgs/tramitacion/iconos/doc_firmar.gif" alt="<bean:message key="pasoRellenar.iconografia.firmarDocumento"/>" title="<bean:message key="pasoRellenar.iconografia.firmarDocumento"/>" /></logic:equal>
						<img src="imgs/tramitacion/iconos/ico_form.gif" alt="<bean:message key="pasoRellenar.formulario"/>" title="Formulario" />
					</div>

					<p>
					<span style="position: relative;">

					<!--  Formulario a rellenar por el usuario actual -->
					<logic:equal name="flujoDocumentos" property="<%=formulario.getIdentificador()%>" value="true">

						<!-- Pagos iniciados: sólo dejamos ver pdf -->
						<logic:equal name="iniciadoPagos" value="true">
							<html:link href="<%= urlMostrarDocumento + \"&identificador=\" + formulario.getIdentificador() + \"&instancia=\" + formulario.getInstancia() %>" >
								<strong><bean:write name="formulario" property="descripcion" /></strong>
							</html:link>
						</logic:equal>

						<!--  Pagos no iniciados: permitimos modificar -->
						<logic:equal name="iniciadoPagos" value="false">
							<html:link href="<%= urlFormulario + \"&identificador=\" + formulario.getIdentificador() + \"&instancia=\" + formulario.getInstancia() %>" onclick="accediendoEnviando(mensajeEnviando);"><strong><bean:write name="formulario" property="descripcion" /></strong></html:link>
						</logic:equal>
					</logic:equal>

					<!--  Formulario a rellenar por otro usuario: en caso de estar completado, mostramos pdf -->
					<logic:equal name="flujoDocumentos" property="<%=formulario.getIdentificador()%>" value="false">
						<logic:equal name="formulario" property="estado" value="S">
							<html:link href="<%= urlMostrarDocumento + \"&identificador=\" + formulario.getIdentificador() + \"&instancia=\" + formulario.getInstancia() %>"><strong><bean:write name="formulario" property="descripcion" /></strong></html:link>
						</logic:equal>
						<logic:notEqual name="formulario" property="estado" value="S">
							<strong><bean:write name="formulario" property="descripcion" /></strong>
						</logic:notEqual>

						<logic:notEmpty name="formulario" property="nifFlujo">
							<logic:notEqual name="flujoTramitacionNifUsuarioActual" value="<%= formulario.getNifFlujo() %>">
								<span class="detalleDoc">
	  								<img src="imgs/tramitacion/iconos/clip.gif"/>
										<logic:equal name="formulario" property="estado" value="S">
											<bean:message key="flujoTramitacion.remitirFormularioAUsuario.completado" arg0="<%=formulario.getNifFlujo()%>"/>
										</logic:equal>
										<logic:notEqual name="formulario" property="estado" value="S">
											<bean:message key="flujoTramitacion.remitirFormularioAUsuario" arg0="<%=formulario.getNifFlujo()%>"/>
										</logic:notEqual>
								</span>
							</logic:notEqual>
						</logic:notEmpty>
					</logic:equal>

					<!--  Debe firmarse -->
					<logic:equal name="formulario" property="firmar" value="true">
						<logic:equal name="formulario" property="estado" value="S">
							<logic:equal name="formulario" property="firmado" value="false">
								<br/>
								<logic:equal name="formulario" property="pendienteFirmaDelegada" value="false">
									<logic:equal name="formulario" property="rechazadaFirmaDelegada" value="false">
										<bean:message key="pasoRellenar.formularios.debeFirmarseDigitalmente"/> - <html:link  href="<%= urlFirmarDocumento + \"&identificador=\" + formulario.getIdentificador() + \"&instancia=\" + formulario.getInstancia() %>"><bean:message key="pasoRellenar.formularios.firmarFormulario"/></html:link>
									</logic:equal>
								</logic:equal>
								<logic:equal name="formulario" property="pendienteFirmaDelegada" value="true">
									<span class="detalleDoc">
											<img src="imgs/tramitacion/iconos/ico_firma_pendiente.gif" alt="<bean:message key="pasoRellenar.iconografia.firmaDocumentoPendienteBandeja"/>" title="<bean:message key="pasoRellenar.iconografia.firmaDocumentoPendienteBandeja"/>"/>
											<bean:message key="pasoRellenar.documentos.pendienteFirmaDelegada"/>
									</span>
								</logic:equal>
								<logic:equal name="formulario" property="rechazadaFirmaDelegada" value="true">
									<span class="detalleDoc">
											<img src="imgs/tramitacion/iconos/ico_firma_rechazada.gif" alt="<bean:message key="pasoRellenar.iconografia.firmaDocumentoRechazadaBandeja"/>" title="<bean:message key="pasoRellenar.iconografia.firmaDocumentoRechazadaBandeja"/>"/>
											<bean:message key="pasoRellenar.documentos.rechazadaFirmaDelegada"/>
									</span>
								</logic:equal>
							</logic:equal>
							<logic:equal name="formulario" property="firmado" value="true">
								<span class="detalleDoc">
									<img src="imgs/tramitacion/iconos/ico_firma_aceptada.gif" alt="<bean:message key="pasoRellenar.iconografia.firmaDocumentoRealizada"/>" title="<bean:message key="pasoRellenar.iconografia.firmaDocumentoRealizada"/>"/>
									<%=es.caib.sistra.front.util.Util.generaTextoFirma(formulario, lang) %>
								</span>
							</logic:equal>
						</logic:equal>
					</logic:equal>
					<!--  Fin Debe firmarse -->

					</span>
				</p>

				</logic:notEqual>
				</logic:iterate>
			</div>
			<!--  Fin div formularios -->

			<div class="sep"></div>
			<!-- capa accediendo formularios -->
			<div id="capaInfoFondo"></div>
			<div id="capaInfoForms"></div>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<bean:define id="documentoUnico" name="documentoUnico" type="es.caib.sistra.model.DocumentoConsultaFront" />
<bean:define id="urlMostrarDocumentoConsulta">
	<html:rewrite page="/protected/mostrarDocumentoConsulta.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" />
</bean:define>
<bean:define id="urlFinalizar">
        <html:rewrite page="/protected/finalizar.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
			<h2><bean:message key="pasoResultadoConsulta.resultadoConsulta"/></h2>
			<!-- 
				<p><bean:message key="pasoResultadoConsulta.resultadoConsulta.informacion"/></p>
			 -->
			<p>
			<logic:equal name="numeroDocumentos" value="0"><bean:message key="pasoResultadoConsulta.resultadoConsulta.0resultados" /><br/><br/><br/></logic:equal>
			<logic:equal name="numeroDocumentos" value="1">				
				<logic:equal name="documentoUnico" property="tipo" value="B">
					<bean:message key="pasoResultadoConsulta.resultadoConsulta.1resultados" /> 
					<div id="resultadoConsulta">
						<img src="imgs/tramitacion/iconos/doc_fotocopia.gif" alt="Resultado" title="Resultado" />
						&nbsp;<html:link href="<%= urlMostrarDocumentoConsulta + \"&idx=0\" %>" ><bean:write name="documentoUnico" property="nombre"/></html:link>					
					</div>
					
				</logic:equal>
				<logic:equal name="documentoUnico" property="tipo" value="U">
					<logic:equal name="documentoUnico" property="urlVentanaNueva" value="true">
						<bean:message key="pasoResultadoConsulta.resultadoConsulta.1resultados" /> 
							<div id="resultadoConsulta">
								<img src="imgs/tramitacion/iconos/doc_fotocopia.gif" alt="Resultado" title="Resultado" />
								&nbsp;<html:link href="<%= documentoUnico.getUrlEnlace() %>" target="blank"><bean:write name="documentoUnico" property="nombre" /></html:link>						
							</div>
					</logic:equal>
					<logic:notEqual name="documentoUnico" property="urlVentanaNueva" value="true">
						<iframe src="<%= documentoUnico.getUrlEnlace() %>" frameborder="1" width="750" height="400">
						</iframe>
					</logic:notEqual>
				</logic:equal>
			</logic:equal>
			</p>
			<logic:greaterThan name="numeroDocumentos" value="1">
			<p><bean:message key="pasoResultadoConsulta.resultadoConsulta.variosResultados" /></p>
			<div id=resultadoConsulta>
			<!--  Documentos -->
 				<table cellpadding="0" cellspacing="0">
 				<!-- 
				<caption>				
					<bean:message key="pasoResultadoConsulta.resultadoConsulta.documentos"/>
				</caption>				
				 -->
					<logic:iterate id="documento" indexId="documentoIdx" name="params" property="documentosConsulta" type="es.caib.sistra.model.DocumentoConsultaFront">
							<tr style="padding:.2em;">
								<th scope="row"><img src="imgs/tramitacion/iconos/doc_fotocopia.gif"/></th>
								<td>
									&nbsp;
									<logic:equal name="documento" property="tipo" value="B">
									<html:link href="<%= urlMostrarDocumentoConsulta %>" paramId="idx" paramName="documentoIdx"><bean:write name="documento" property="nombre"/></html:link>
									</logic:equal>
									<logic:equal name="documento" property="tipo" value="U">
										<logic:equal name="documento" property="urlVentanaNueva" value="true">
										<html:link href="<%= documento.getUrlEnlace() %>" target="blank"><bean:write name="documento" property="nombre" /></html:link>	
										</logic:equal>
										<logic:notEqual name="documento" property="urlVentanaNueva" value="true">
										<html:link href="<%= documento.getUrlEnlace() %>"><bean:write name="documento" property="nombre" /></html:link>
										</logic:notEqual>
									</logic:equal>
								</td>
							</tr>
					</logic:iterate>
				</table>
			</div>
			</logic:greaterThan>
			<p class="ultimo">
			</p>
			<br/>
			<p class="centrado">
				<strong>
					<bean:message key="pasoResultadoConsulta.finalizacion.instrucciones" />
				</strong>
			</p>
			<p class="centrado">
				<input name="finalizarPRBoton" id="finalizarPRBoton" type="button" value="<bean:message key="finalizacion.boton.finalizar"/>" onclick="javascript:document.location.href='<%= urlFinalizar.toString() %>';" />
			</p>								
			<!--  Fin div Justificante -->
			<div class="sep"></div>
			<logic:equal name="numeroDocumentos" value="1">
			<script type="text/javascript">
				<!--
					<logic:equal name="documentoUnico" property="tipo" value="B">
						setTimeout( "document.location.href='<bean:write name="urlMostrarDocumentoConsulta" />&idx=0'", 2000 );
					</logic:equal>
					<logic:equal name="documentoUnico" property="tipo" value="U">
						<logic:equal name="documentoUnico" property="urlVentanaNueva" value="true">
							window.open( '<%= documentoUnico.getUrlEnlace() %>' );
						</logic:equal>
					</logic:equal>	
				-->
			</script>
			</logic:equal>
			
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="es.caib.util.StringUtil"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>
<bean:define id="referenciaPortal"  type="java.lang.String">
	<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property='<%="referenciaPortal("+ lang + ")"%>'/>
</bean:define>
<bean:define id="urlMostrarDocumento">
        <html:rewrite page="/protected/mostrarDocumento.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<bean:define id="urlFinalizar">
        <html:rewrite page="/protected/finalizar.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<bean:define id="instrucciones" name="instrucciones"/>


<!--  Cuando no hay docs a presentar (tramite completamente telematico) -->
<logic:notPresent name="instrucciones" property="documentosEntregar">
	<!--  Titulo  -->
	<h2><bean:message key="finalizacion.finTelematico"/></h2>
	<!--  Instrucciones fin -->
	<p>
		<bean:write name="instrucciones" property="textoInstrucciones" filter="false"/>
	</p>
	<!--  Instrucciones para guardar justificante -->
	<h3 class="titGuardar"><bean:message key="pasoJustificante.guardarJustificante"/></h3>	
	<p class="apartado">
		<bean:message name="textoJustificante" />		
		<!-- 				
			<bean:message key="pasoJustificante.guardarJustificante.recordatorioZonaPersonal.inicio" arg0="<%=referenciaPortal%>" />
		 -->			
	</p>
	<p class="centrado">
		<input name="guardarJustificanteBoton" id="guardarJustificanteBoton" type="button" value="<bean:message key="pasoJustificante.guardarJustificante.boton"/>"
			 onclick="javascript:document.location.href='<%= urlMostrarDocumento + "&identificador=JUSTIFICANTE"%>'" />
	</p></br></br></br>
	<h3 class="titGuardar"><bean:message key="pasoJustificante.guardarRestoDocumentacion"/></h3>	
	<p class="apartado">
		<bean:message key="pasoJustificante.guardarRestoDocumentacion.informacion"/>			
		<!-- 				
			<bean:message key="pasoJustificante.guardarJustificante.recordatorioZonaPersonal.inicio" arg0="<%=referenciaPortal%>" />
		 -->			
	</p>
	<table cellpadding="0" cellspacing="0" id="tablaDocAportar">
			<tr>
				<th width="80%"><bean:message key="pasoJustificante.guardarRestoDocumentacion.documentaAGuardar"/></th>
				<th width="20%"></th>
			</tr>
		<logic:iterate id="doc" name="documentacion" type="es.caib.xml.registro.factoria.impl.DatosAnexoDocumentacion">
			<logic:notEqual name="doc" property="tipoDocumento" value="D">
				<tr>
					<td class="doc2"><bean:write name="doc" property="extractoDocumento" /></td>
					
					<td class="guardar">
						<logic:equal name="doc" property="tipoDocumento" value="F">
							<logic:equal name="documentacionLink" property="<%=doc.getIdentificadorDocumento()%>" value="true">
								<html:link styleClass="button-guardar" href="<%= urlMostrarDocumento + \"&identificador=\" + StringUtil.getModelo(doc.getIdentificadorDocumento()) + \"&instancia=\" + StringUtil.getVersion(doc.getIdentificadorDocumento()) %>">
								<bean:message key="pasoJustificante.guardarRestoDocumentacion.guardar"/>					
								</html:link>
							</logic:equal>
						</logic:equal>
						<logic:notEqual name="doc" property="tipoDocumento" value="F">
							<html:link styleClass="button-guardar" href="<%= urlMostrarDocumento + \"&identificador=\" + StringUtil.getModelo(doc.getIdentificadorDocumento()) + \"&instancia=\" + StringUtil.getVersion(doc.getIdentificadorDocumento()) %>">
								<bean:message key="pasoJustificante.guardarRestoDocumentacion.guardar"/>					
							</html:link>
						</logic:notEqual>
					</td>
				</tr>
			</logic:notEqual>
		</logic:iterate>
	</table>
</logic:notPresent>


<!--  Cuando hay docs a presentar (tramite con preregistro -->
<logic:present name="instrucciones" property="documentosEntregar">	
	<!--  Titulo  -->
	<h2><bean:message key="finalizacion.entregarSolicitudFirmada"/></h2>
	<!--  Instrucciones fin -->
	<p>
		<bean:write name="instrucciones" property="textoInstrucciones" filter="false"/>
	</p>
	<!-- Fecha tope entrega documentacion presencial-->	
	<p class="alerta">
		<!--  Mensaje x defecto -->
		<logic:empty name="instrucciones" property="textoFechaTopeEntrega">
			<strong><bean:message key="finalizacion.fechaTopeEntrega"/> <bean:write name="instrucciones" property="fechaTopeEntrega" format="dd/MM/yyyy" /></strong>.
		</logic:empty>
		<!--  Mensaje personalizado -->
		<logic:notEmpty name="instrucciones" property="textoFechaTopeEntrega">
			<strong><bean:write name="instrucciones" property="textoFechaTopeEntrega"/></strong>.
		</logic:notEmpty>
	</p>	
	<!-- Listado de docs a presentar  -->	
	<bean:define id="documentosEntregar" name="instrucciones" property="documentosEntregar"/>	
	<logic:notEmpty name="documentosEntregar" property="documento">

		<!--  Tabla de documentos a aportar -->
			<h3 class="titDocumentacion"><bean:message key="finalizacion.documentacionAAportar"/></h3>	
			<table cellpadding="0" cellspacing="0" id="tablaDocAportar">
			<tr>
				<th width="20%"><bean:message key="finalizacion.documentacionAAportar.documento"/></th>
				<th width="60%"><bean:message key="finalizacion.documentacionAAportar.accion"/></th>
				<th width="20%"></th>
			</tr>			
		<logic:iterate id="documento" name="documentosEntregar" property="documento" type="es.caib.xml.datospropios.factoria.impl.Documento">
			<tr>
				<td class="doc2"><bean:write name="documento" property="titulo" /></td>
			<logic:equal name="documento" property="tipo" value="J">
				<td><bean:message key="finalizacion.instrucciones.justificante.firmar"/></td>
			</logic:equal>
			<logic:equal name="documento" property="tipo" value="G">			
				<td><bean:message key="finalizacion.instrucciones.formularioJustificante.firmar"/></td>
			</logic:equal>
			<logic:equal name="documento" property="tipo" value="F">				
				<td><bean:message key="finalizacion.instrucciones.formulario.firmar"/></td>
			</logic:equal>
				<logic:equal name="documento" property="tipo" value="A">
				<%  
					String keyMessage="finalizacion.instrucciones.anexo";							
					keyMessage+= (documento.isCompulsar().booleanValue()) ? ".compulsar" : "";												
					keyMessage+= (documento.isFotocopia().booleanValue()) ? ".fotocopia" : "";
					keyMessage+= (!documento.isFotocopia().booleanValue() && !documento.isCompulsar().booleanValue()) ? ".presencial" : "";											
				%>
				<td>
					<bean:message key="<%=keyMessage%>"/>						
				</td>
			</logic:equal>
			<logic:equal name="documento" property="tipo" value="P">
				<td><bean:message key="finalizacion.instrucciones.pago"/></td>
			</logic:equal>
			
			<td>
				<logic:equal name="documento" property="tipo" value="J">
					<html:link styleClass="button-print" href="<%= urlMostrarDocumento + \"&identificador=JUSTIFICANTE\"%>" title="Justificante">
						<bean:message key="finalizacion.imprimir"/>
					</html:link>														
				</logic:equal>
				<logic:equal name="documento" property="tipo" value="G">
					<html:link styleClass="button-print" href="<%= urlMostrarDocumento + \"&identificador=JUSTIFICANTE\"%>" title="Justificante">
						<bean:message key="finalizacion.imprimir"/>
					</html:link>						
				</logic:equal>
				<logic:equal name="documento" property="tipo" value="F">
					<html:link styleClass="button-print" href="<%= urlMostrarDocumento + \"&identificador=\" + StringUtil.getModelo(documento.getIdentificador()) + \"&instancia=\" + StringUtil.getVersion(documento.getIdentificador()) %>" title="Firmar formulario">
						<bean:message key="finalizacion.imprimir"/>					
					</html:link>	
				</logic:equal>
			</td>
			</tr>
		</logic:iterate>
		</table>									
		
	<!--  Instrucciones entrega específicas-->				
	<logic:notEmpty name="tramite" property="instruccionesEntrega">
		<h3 class="titEntrega"><bean:message key="finalizacion.documentacionAAportar.instruccionesEntrega"/></h3>	
	 	<p class="apartado"><bean:write name="tramite" property="instruccionesEntrega" filter="false"/></p>
	</logic:notEmpty>
		
	</logic:notEmpty>		
	
	<!--  Alerta de entrega de documentacion -->
	<script type="text/javascript">
	<!--
	var mensajeAlertEntrega = "<bean:message key="finalizacion.avisoEntrega"/>";
	alert(mensajeAlertEntrega);
	//-->
	</script>
	
		
</logic:present>



<!--  Boton para cerrar tramite -->		
<p class="ultimo"></p>

<br/>

<logic:equal name="irAZonaPersonal" value="false">
<p class="centrado">
	<strong><bean:message key="finalizacion.finalizacion.instrucciones" /></strong>
</p>
</logic:equal>

<p class="centrado">
<input name="finalizarPRBoton" id="finalizarPRBoton" type="button"
<logic:equal name="irAZonaPersonal" value="true">
value="<bean:message key="finalizacion.boton.irAZonaPersonal" arg0="<%=referenciaPortal%>" />" 		
</logic:equal>
<logic:equal name="irAZonaPersonal" value="false">
value="<bean:message key="finalizacion.boton.finalizar"/>" 		
</logic:equal>
 onclick="javascript:document.location.href='<%= urlFinalizar.toString() %>';" />

</p>

<div class="sep"></div>
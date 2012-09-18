<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="es.caib.redose.modelInterfaz.ConstantesRDS"%>
<%@ page import="es.caib.util.StringUtil"%>
<%@ page import="es.caib.sistra.front.Constants"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>
<bean:define id="referenciaPortal"  type="java.lang.String">
	<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property='<%="referenciaPortal("+ lang +")"%>'/>
</bean:define>
<bean:define id="justificante" name="justificante"/> 
<bean:define id="asientoRegistral" name="justificante" property="asientoRegistral"/>
<bean:define id="datosAsunto" name="asientoRegistral" property="datosAsunto"/>
<bean:define id="datosOrigen" name="asientoRegistral" property="datosOrigen"/>
<bean:define id="isTramiteReducido" name="<%= Constants.TRAMITE_REDUCIDO_KEY %>"/>
<bean:define id="urlMostrarDocumento">
        <html:rewrite page="/protected/mostrarDocumento.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<bean:define id="urlFinalizar">
        <html:rewrite page="/protected/finalizar.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
						
			<logic:equal name="datosOrigen" property="tipoRegistro" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.TIPO_PREENVIO)%>">
				<p class="alerta"><bean:message key="pasoJustificante.guardarJustificante.avisoFinalizar"/></p>
			</logic:equal>
			
			<logic:equal name="datosOrigen" property="tipoRegistro" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.TIPO_PREREGISTRO)%>">
				<p class="alerta"><bean:message key="pasoJustificante.guardarJustificante.avisoFinalizar"/></p>
			</logic:equal>
			
			<!--  Para tramites reducidos mostramos instrucciones de entrega -->
			<logic:equal name="isTramiteReducido" value="true">
				<logic:notEmpty name="tramite" property="instruccionesEntrega">
					<h2><bean:message key="finalizacion.documentacionAAportar.instruccionesEntrega"/></h2>
				 	<p><bean:write name="tramite" property="instruccionesEntrega" filter="false"/></p>
				</logic:notEmpty>
			</logic:equal>
			
			
			<h2><bean:message key="pasoJustificante.guardarJustificante"/></h2>
			
			<p>
				<bean:message key="pasoJustificante.guardarJustificante.informacion"/>					
			</p>
			<p class="ultimo">
				<bean:message key="pasoJustificante.guardarJustificante.recordatorioZonaPersonal.inicio" arg0="<%=referenciaPortal%>" />
			</p>						
						
			<div id="datosJustificante">
				<!--  Datos Registro -->
				<table cellpadding="0" cellspacing="0">
				<caption>
					<logic:equal name="datosOrigen" property="tipoRegistro" value="E">
						<bean:message key="pasoJustificante.guardarJustificante.registro.datosRegistro"/>
					</logic:equal>
					<logic:notEqual name="datosOrigen" property="tipoRegistro" value="E">
						<bean:message key="pasoJustificante.guardarJustificante.envio.datosRegistro"/>
					</logic:notEqual>
				</caption>		
				<!--  Numero y fecha -->		
				<logic:equal name="datosOrigen" property="tipoRegistro" value="E">
					<tr>
						<th scope="row"><bean:message key="pasoJustificante.guardarJustificante.registro.datosRegistro.numeroRegistro"/></th>
						<td><bean:write name="justificante" property="numeroRegistro"/></td>						
					</tr>
					<tr>
						<th scope="row"><bean:message key="pasoJustificante.guardarJustificante.registro.datosRegistro.fechaSolicitud"/> </th>
						<td><bean:write name="justificante" property="fechaRegistro" format="dd/MM/yyyy - HH:mm 'h.'"/></td>
					</tr>
				</logic:equal>
					<logic:notEqual name="datosOrigen" property="tipoRegistro" value="E">
						<tr>
							<th scope="row"><bean:message key="pasoJustificante.guardarJustificante.envio.datosRegistro.numeroRegistro"/></th>
							<td><bean:write name="justificante" property="numeroRegistro"/></td>
						</tr>
						<tr>
							<th scope="row"><bean:message key="pasoJustificante.guardarJustificante.envio.datosRegistro.fechaSolicitud"/></th>
							<td><bean:write name="justificante" property="fechaRegistro" format="dd/MM/yyyy - HH:mm 'h.'"/></td>
						</tr>
					</logic:notEqual>					
				</tr>
				<!--  Asunto -->
				<tr>
					<th scope="row"><bean:message key="pasoJustificante.guardarJustificante.datosRegistro.asunto"/></th>
					<td><bean:write name="datosAsunto" property="extractoAsunto" /></td>
				</tr>				
				<!--  Representante -->
				<logic:notEmpty name="representante" property="numeroIdentificacion">
					<logic:empty name="representado">	
						<tr>									
							<th scope="row"><bean:message key="pasoJustificante.guardarJustificante.datosRegistro.nombre"/></th>
							<td><bean:write name="representante" property="identificacionInteresado"/></td>					
						</tr>							
						<logic:notEqual name="representante" property="tipoIdentificacion" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF)%>">
							<tr>
								<th scope="row"><bean:message key="pasoJustificante.guardarJustificante.datosRegistro.NIF"/></th>
								<td><bean:write name="representante" property="numeroIdentificacion"/></td>										
							</tr>
						</logic:notEqual>
						<logic:equal name="representante" property="tipoIdentificacion" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF)%>">
							<tr>
								<th scope="row"><bean:message key="pasoJustificante.guardarJustificante.datosRegistro.CIF"/></th>
								<td><bean:write name="representante" property="numeroIdentificacion"/></td>										
							</tr>
						</logic:equal>
					</logic:empty>				
				<!-- Representado  -->
					<logic:notEmpty name="representado">					
						<tr>
							<th scope="row"><bean:message key="pasoJustificante.guardarJustificante.datosRegistro.nombreRepresentante"/></th>
							<td><bean:write name="representante" property="identificacionInteresado"/></span></td>
						</tr>
						<logic:notEqual name="representante" property="tipoIdentificacion" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF)%>">
							<tr>
								<th scope="row"><bean:message key="pasoJustificante.guardarJustificante.datosRegistro.NIFRepresentante"/></th>
								<td><bean:write name="representante" property="numeroIdentificacion"/></td>										
							</tr>
						</logic:notEqual>
						<logic:equal name="representante" property="tipoIdentificacion" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF)%>">
							<tr>
								<th scope="row"><bean:message key="pasoJustificante.guardarJustificante.datosRegistro.CIFRepresentante"/></th>
								<td><bean:write name="representante" property="numeroIdentificacion"/></td>										
							</tr>
						</logic:equal>
						<tr>
							<th scope="row"><bean:message key="pasoJustificante.guardarJustificante.datosRegistro.nombreRepresentado"/></th>
							<td><bean:write name="representado" property="identificacionInteresado"/></td>
						</tr>
						<logic:notEqual name="representado" property="tipoIdentificacion" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF)%>">
							<tr>
								<th scope="row"><bean:message key="pasoJustificante.guardarJustificante.datosRegistro.NIFRepresentado"/></th>
								<td><bean:write name="representado" property="numeroIdentificacion"/></td>										
							</tr>
						</logic:notEqual>
						<logic:equal name="representado" property="tipoIdentificacion" value="<%=Character.toString(es.caib.xml.registro.factoria.ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF)%>">
							<tr>
								<th scope="row"><bean:message key="pasoJustificante.guardarJustificante.datosRegistro.CIFRepresentado"/></th>
								<td><bean:write name="representado" property="numeroIdentificacion"/></td>										
							</tr>
						</logic:equal>					
					</logic:notEmpty>
				</logic:notEmpty>
				</table>
				
				<!--  Datos propios -->
				<logic:notEmpty name="datosPropios">
					<logic:notEmpty name="datosPropios" property="solicitud">
						<bean:define id="solicitud" name="datosPropios" property="solicitud" />									
						<table cellpadding="0" cellspacing="0">
							<caption>
								<bean:message key="pasoJustificante.guardarJustificante.datosRegistro.datosSolicitud"/>
							</caption>			
							<logic:iterate id="dato" name="solicitud" property="dato">
								<tr>
									<logic:equal name="dato" property="tipo" value="C">
										<bean:define id="valorDato" name="dato" property="valor" type="java.lang.String"/>
											<th scope="row"><bean:write name="dato" property="descripcion"/>:</th>
											<td><%= StringUtil.newLineToBr( valorDato ) %></td>
									</logic:equal>
									<logic:notEqual name="dato" property="tipo" value="C">
										<th class="nivel2"><bean:write name="dato" property="descripcion"/></th>
										<td></td>
									</logic:notEqual>
								</tr>
							</logic:iterate>									
						</table>
					</logic:notEmpty>
			 	</logic:notEmpty>			
 				
 				<!--  Documentos -->
 				<table cellpadding="0" cellspacing="0">
				<caption>				
					<bean:message key="pasoJustificante.guardarJustificante.datosRegistro.documentos"/>
				</caption>				
					<logic:iterate id="anexo" name="asientoRegistral" property="datosAnexoDocumentacion" type="es.caib.xml.registro.factoria.impl.DatosAnexoDocumentacion">
						<logic:equal name="anexo" property="tipoDocumento" value="O">
							<tr>
								<th scope="row"><img src="imgs/tramitacion/iconos/doc_generico.gif"/></th>
								<td>
									<!-- Solo mostramos enlace a pdf documentos para tramite reducido -->
									<logic:equal name="isTramiteReducido" value="true">
										<html:link href="<%=urlMostrarDocumento + "&identificador=" + StringUtil.getModelo(anexo.getIdentificadorDocumento()) + "&instancia=" + StringUtil.getVersion(anexo.getIdentificadorDocumento())%>">
											<bean:write name="anexo" property="extractoDocumento" />
										</html:link>
									</logic:equal>									
									<logic:equal name="isTramiteReducido" value="false">
										<bean:write name="anexo" property="extractoDocumento" />	
									</logic:equal>
								</td>
							</tr>
						</logic:equal>					
					</logic:iterate>
				</table>				
				
			</div>
			<!--  Fin div Justificante -->
						
			<p class="centrado">
				<input name="guardarJustificanteBoton" id="guardarJustificanteBoton" type="button" value="<bean:message key="pasoJustificante.guardarJustificante.boton"/>"
					 onclick="javascript:document.location.href='<%= urlMostrarDocumento + "&identificador=JUSTIFICANTE"%>'" />
					 </p>
			<p id="getAdobeReader">
					<bean:message key="pasoJustificante.guardarJustificante.informacionPDF"/>
					<br />
					<a  target="_blank" href="http://www.adobe.es/products/acrobat/readstep2.html"><bean:message key="pasoJustificante.guardarJustificante.urlAdobeReader"/></a>
			</p>	
			<div class="sep"></div>
			<logic:equal name="isTramiteReducido" value="true">
				<div class="barraAzul"></div>
				<div id="pasosNav">
				<div id="pasoAnteriorDIV"></div>
				<html:link styleId="pasoSiguiente" action="/protected/finalizar" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" ><bean:message key="finalizacion.boton.finalizar" /></html:link>
				</div>
			</logic:equal>
			
		<%--  Si se indica la url de acceso a un documento, abrimos en nueva ventana --%>	
		<logic:present name="urlAcceso">
			<script type="text/javascript">
				// Abrimos ventana plataforma pagos
				window.open("<bean:write name="urlAcceso" filter="false"/>");
			</script>
		</logic:present>
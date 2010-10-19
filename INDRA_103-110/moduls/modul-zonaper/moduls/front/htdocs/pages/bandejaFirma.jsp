<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.zonaper.modelInterfaz.ConstantesZPE"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<link href="css/estilos.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.4.1.min.js"></script>
<script type="text/javascript" src="js/mensaje.js"></script>
<script type="text/javascript">
<!--
 function mostrarMensaje(){
 	Mensaje.mostrar({tipo: "mensaje", modo: "ejecutando", fundido: "si", titulo: "Enviando datos..."});
 }
//-->
</script>

<bean:define id="locale" name="org.apache.struts.action.LOCALE" scope="session" />
<bean:define id="language" name="org.apache.struts.action.LOCALE" scope="session" />

<bean:define id="nivelAutenticacion" name="es.caib.zonaper.front.DATOS_SESION" property="nivelAutenticacion" scope="session" type="java.lang.Character"/>

<bean:define id="nivelAutenticacionDescripcion" type="java.lang.String">
	<bean:message key="<%="tramitesSinEnviar.nivelAutenticacion."+nivelAutenticacion%>" />
</bean:define>

<bean:define id="urlAbrirDocumento" type="java.lang.String">
	<html:rewrite page="/protected/abrirDocumento.do"/>
</bean:define>

<bean:define id="urlFirmarDocumento" type="java.lang.String">
	<html:rewrite page="/protected/irFirmarDocumento.do"/>
</bean:define>

<bean:define id="urlRechazarDocumento" type="java.lang.String">
	<html:rewrite page="/protected/rechazarDocumento.do"/>
</bean:define>
<bean:define id="sesion" name="<%=es.caib.zonaper.front.Constants.DATOS_SESION_KEY%>" type="es.caib.zonaper.model.DatosSesion" />


		<!-- titol -->
		<h1>
			<bean:message key="bandejaFirma.titulo" />
		</h1>
		<!-- /titol -->
		<!-- informacio -->
		<div id="info">
		
		<p><bean:message key="bandejaFirma.encabezamiento.texto1" /></p>
		<html:errors/>		
		<!--  Lista de tramites persistentes -->
		<logic:empty name="documentosPendientesFirma" >
			<p class="alerta">
				<bean:message key="bandejaFirma.noExisten" />
			</p>
		</logic:empty>
			
		<logic:notEmpty name="documentosPendientesFirma" >
			<table class="llistatElements">
				<thead>
					<tr>
						<th><bean:message key="bandejaFirma.documento" /></th>
						<th><bean:message key="bandejaFirma.tramite" /></th>
						<th><bean:message key="bandejaFirma.firmantes" /></th>
						<th class="opcions"></th>
					</tr>
				</thead>
			
				<logic:iterate id="documento" name="documentosPendientesFirma" type="es.caib.zonaper.front.util.DocumentoPersistenteFront">										
					<tr>								
						<td>
							<bean:write name="documento" property="descripcionDocumento"/>
						</td>												
						<td>
							<bean:write name="documento" property="tramitePersistente.descripcion"/>
						</td>												
						<td>
							<ul>
							<logic:iterate id="firmante" name="documento" property="delegacionFirmantesHTML" type="es.caib.zonaper.front.util.FirmanteFront">										
								<li>
								<bean:write name="firmante" property="nombre"/> 
								<logic:equal name="firmante" property="haFirmado"  value="true">
									<span class="firmat fSi">[ <bean:message key='bandejaFirma.firmado'/> ]</span>
									<!--<img src="imgs/botons/signature-ok.png" title="<bean:message key='bandejaFirma.firmado'/>" alt="<bean:message key='bandejaFirma.firmado'/>"/>-->
								</logic:equal>
								<logic:notEqual name="firmante" property="haFirmado" value="true">
									<span class="firmat fNo">[ <bean:message key='bandejaFirma.firmado.no'/> ]</span>
									<!--<img src="imgs/botons/signature.png" title="<bean:message key='bandejaFirma.firmado.no'/>" alt="<bean:message key='bandejaFirma.firmado.no'/>"/>-->
								</logic:notEqual>
								</li>
							</logic:iterate>
							</ul>
						</td>
						<td>
							<logic:match name="documento" property="delegacionFirmantesPendientes" value="<%=sesion.getNifUsuario()%>">
								<html:link href="<%=urlFirmarDocumento%>" paramId="identificador" paramName="documento" paramProperty="codigo" styleClass="signar"  onclick="mostrarMensaje()">
									<bean:message key="bandejaFirma.firmar" />
								</html:link>
							</logic:match>
							<logic:match name="documento" property="delegacionFirmantesPendientes" value="<%=sesion.getNifUsuario()%>">
								<html:link href="<%= urlRechazarDocumento %>" paramId="identificador" paramName="documento" paramProperty="codigo" styleClass="rebutjar"  onclick="mostrarMensaje()">
									<bean:message key="bandejaFirma.rechazar" />
								</html:link>
							</logic:match>
							<logic:match name="documento" property="delegacionFirmantes" value="<%=sesion.getNifUsuario()%>">
								<bean:define id="codigoRDS" name="documento" property="rdsCodigo" type="java.lang.Long" />
								<bean:define id="claveRDS" name="documento" property="rdsClave" type="java.lang.String" />
								<a href="<%=urlAbrirDocumento%>?codigo=<%=codigoRDS %>&amp;clave=<%=claveRDS %>" class="veure">
									<bean:message key="bandejaFirma.ver" />
								</a>
							</logic:match>
						</td>
					</tr>
				</logic:iterate>
			</table>
		</logic:notEmpty>
	</div>
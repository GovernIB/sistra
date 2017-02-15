<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.zonaper.modelInterfaz.ConstantesZPE"%>
<%@ page import="es.caib.zonaper.modelInterfaz.TramitePersistentePAD"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<bean:define id="locale" name="org.apache.struts.action.LOCALE" scope="session" />
<bean:define id="language" name="org.apache.struts.action.LOCALE" scope="session" />

<bean:define id="contextoRaizSistra" name="<%=es.caib.zonaper.front.Constants.CONTEXTO_RAIZ%>" type="java.lang.String"/>

<bean:define id="urlTramitacion" type="java.lang.String">
	<html:rewrite href='<%=contextoRaizSistra + "/sistrafront/inicio"%>' paramId="language" paramName="language" paramProperty="language" />
</bean:define>


<bean:define id="nivelAutenticacion" name="es.caib.zonaper.front.DATOS_SESION" property="nivelAutenticacion" scope="session" type="java.lang.Character"/>

<bean:define id="nivelAutenticacionDescripcion" type="java.lang.String">
	<bean:message key='<%=\"tramitesSinEnviar.nivelAutenticacion.\"+nivelAutenticacion%>' />
</bean:define>


<bean:define id="sesion" name="<%=es.caib.zonaper.front.Constants.DATOS_SESION_KEY%>" type="es.caib.zonaper.model.DatosSesion" />
		<!-- titol -->
		<h1>
			<bean:message key="tramitesSinEnviar.titulo" />
		</h1>
		<!-- /titol -->
		<!-- informacio -->
		<div id="info">
		
		<p><bean:message key="tramitesSinEnviar.encabezamiento.texto1" /></p>
		
		<!--  Lista de tramites persistentes -->
		<logic:empty name="tramitesPersistentes" >
			<p class="alerta">
				<bean:message key="tramitesSinEnviar.noExisten" />
			</p>
		</logic:empty>
			
		<logic:notEmpty name="tramitesPersistentes" >
			<p><bean:message key="tramitesSinEnviar.encabezamiento.texto2" arg0="<%=nivelAutenticacionDescripcion%>"/></p>
			<table class="llistatElements">
				<thead>
					<tr>
						<th><bean:message key="tramitesSinEnviar.tipoAcceso" /></th>
						<th><bean:message key="tramitesSinEnviar.idioma" /></th>
						<th><bean:message key="tramitesSinEnviar.fechaModificacion" /></th>
						<th><bean:message key="tramitesSinEnviar.fechaLimite" /></th>
						<th><bean:message key="tramitesSinEnviar.tramite" /></th>
					</tr>
				</thead>
			
				<logic:iterate id="tramitePersistente" name="tramitesPersistentes" type="es.caib.zonaper.modelInterfaz.TramitePersistentePAD">
					
					<bean:define id="usuarioIniciador" name="usuarios" property="<%=tramitePersistente.getUsuario()%>" type="es.caib.zonaper.modelInterfaz.PersonaPAD"/>
					<bean:define id="usuarioTramitacion" name="usuarios" property="<%=tramitePersistente.getUsuarioFlujoTramitacion()%>" type="es.caib.zonaper.modelInterfaz.PersonaPAD"/>
	
					<% 
						String iconoAcceso = tramitePersistente.getNivelAutenticacion() == 'U' ? "accesUC" : "accesCD" ;
						String titulo;	
						String setLink;
						String paramLink = "";
						if (tramitePersistente.getNivelAutenticacion() == nivelAutenticacion.charValue() && 
								(
										(tramitePersistente.getUsuarioFlujoTramitacion().equals(request.getUserPrincipal().getName())) 
										|| 
										(ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(sesion.getPerfilAcceso()) && tramitePersistente.getUsuarioFlujoTramitacion().equals(sesion.getCodigoEntidad()))
								)										
							){
								titulo="tramitesSinEnviar.continuarTramite";
								setLink="true";
								
								if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(sesion.getPerfilAcceso())) {
									paramLink = "&amp;perfilAF="+ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO+"&amp;entidadAF="+sesion.getNifEntidad();									
									if (TramitePersistentePAD.ESTADO_PENDIENTE_DELEGACION_PRESENTACION.equals(tramitePersistente.getEstadoDelegacion()) && 
										 (sesion.getPermisosDelegacion().indexOf(ConstantesZPE.DELEGACION_PERMISO_PRESENTAR_TRAMITE) == -1) ){
											titulo="tramitesSinEnviar.accesDelegadoPresentacion";
											setLink="false";																						
									}
								}else{
									paramLink = "&amp;perfilAF="+ConstantesZPE.DELEGACION_PERFIL_ACCESO_CIUDADANO;
								}
								
								if (TramitePersistentePAD.ESTADO_PENDIENTE_DELEGACION_FIRMA.equals(tramitePersistente.getEstadoDelegacion())){
											titulo="tramitesSinEnviar.remitidoDelegacionFirma";
											setLink="false";																						
									}
								
						}else{
							if (!tramitePersistente.getUsuarioFlujoTramitacion().equals(request.getUserPrincipal().getName())){
								titulo="tramitesSinEnviar.remitido";						
							}else{
								titulo=tramitePersistente.getNivelAutenticacion() == 'U' ? "tramitesSinEnviar.accesUC" : "tramitesSinEnviar.accesCD" ;			
							}
							setLink="false";
						}					
					%>
					
					<bean:define id="link" value="<%=setLink%>"/>
							
					<tr title="<bean:message key="<%= titulo %>" />" onmouseover="selecItemTabla(this);">								
						<logic:equal name="tramitePersistente" property="nivelAutenticacion" value="C">
							<td><bean:message key="tramitesSinEnviar.certificado"/></td>
						</logic:equal>
						<logic:equal name="tramitePersistente" property="nivelAutenticacion" value="U">
							<td><bean:message key="tramitesSinEnviar.usuario"/></td>
						</logic:equal>
						<logic:equal name="tramitePersistente" property="nivelAutenticacion" value="A">
							<td><bean:message key="tramitesSinEnviar.anonimo"/></td>
						</logic:equal>
						
						<td>
							<bean:message key='<%=\"tramitesSinEnviar.idioma.\" + tramitePersistente.getIdioma()%>'/>
						</td>												
						<td><bean:write name="tramitePersistente" property="fechaModificacion" format="dd/MM/yyyy HH:mm"/></td>												
						<td><bean:write name="tramitePersistente" property="fechaCaducidad" format="dd/MM/yyyy"/></td>												
						<td>					
							<!--  Descripcion del trámite con link si puede continuarse -->
							<logic:equal name="link" value="true">
								<html:link href='<%= urlTramitacion + \"&amp;modelo=\" + tramitePersistente.getTramite() + \"&amp;version=\" + tramitePersistente.getVersion() + paramLink%>' paramId="idPersistencia" paramName="tramitePersistente" paramProperty="idPersistencia">
									<bean:write name="tramitePersistente" property="descripcion"/>								
								</html:link>
							</logic:equal>							
							<logic:equal name="link" value="false">
									<bean:write name="tramitePersistente" property="descripcion"/>								
							</logic:equal>
	
							<!--  Si el usuario iniciador y el de flujo son distintos establecemos info remitido por/a -->						
							<logic:notEqual name="tramitePersistente" property="usuario" value="<%=tramitePersistente.getUsuarioFlujoTramitacion()%>">						
								<logic:equal name="tramitePersistente" property="usuarioFlujoTramitacion" value="<%=sesion.getCodigoUsuario()%>">
									<br/><i><bean:message key="tramitesSinEnviar.remitidoPor" arg0="<%=usuarioIniciador.getNombreCompleto()%>"/></i>
								</logic:equal>						
								<logic:equal name="tramitePersistente" property="usuario" value="<%=sesion.getCodigoUsuario()%>">
									<br/><i><bean:message key="tramitesSinEnviar.remitidoA" arg0="<%=usuarioTramitacion.getNombreCompleto()%>"/></i>
								</logic:equal>
							</logic:notEqual>
							
							<!--  Si el tramite se tramita en forma delegada y esta pendiente de presentarse o de firmar -->
							<logic:equal name="tramitePersistente" property="estadoDelegacion" value="<%=TramitePersistentePAD.ESTADO_PENDIENTE_DELEGACION_PRESENTACION%>">
								<br/><i><bean:message key="tramitesSinEnviar.remitidoDelegacionPresentacion"/></i>
							</logic:equal>						
							<logic:equal name="tramitePersistente" property="estadoDelegacion" value="<%=TramitePersistentePAD.ESTADO_PENDIENTE_DELEGACION_FIRMA%>">
								<br/><i><bean:message key="tramitesSinEnviar.remitidoDelegacionFirma"/></i>
							</logic:equal>						
						</td>
					</tr>							
				</logic:iterate>			
			</table>		
		</logic:notEmpty>
	</div>

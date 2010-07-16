<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils,java.util.Date"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<script type="text/javascript">
<!--
function cancelarTramite( url )
{
	if ( confirm ( '<bean:message key="cancelacionTramite.confirmacion" />' ) )
	{
		document.location.href=url;
	} 
}
-->
</script>
<bean:define id="urlCargarTramite">
        <html:rewrite page="/protected/cargarTramite.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<bean:define id="urlEliminarTramite">
        <html:rewrite page="/protected/abandonarTramite.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<bean:define id="metodoAutenticacion" name="metodoAutenticacion" type="java.lang.String" />
<%
	String metodoAutenticacionKey = "U".equals( metodoAutenticacion ) ? "tramitesAlmacenados.metodoAutenticacion.usuario" :  "tramitesAlmacenados.metodoAutenticacion.certificado" ;
%>
		<p><bean:message key="tramitesAlmacenados.informacion.parrafo1.texto1"/> '<bean:message key="<%= metodoAutenticacionKey %>"/>' <bean:message key="tramitesAlmacenados.informacion.parrafo1.texto2"/> '<strong><bean:write name="descripcion" /></strong>'.</p>
			<p><bean:message key="tramitesAlmacenados.informacion.parrafo2"/></p>
			<!-- nuevo trámite -->
			<div id="nuevoTA">
				<h2><bean:message key="tramitesAlmacenados.iniciarNuevoTramite"/></h2>
				<p><bean:message key="tramitesAlmacenados.iniciarNuevoTramite.comenzar"/></p>
				<html:form action="/protected/nuevoTramite">
					<html:hidden property="ID_INSTANCIA" value="<%= (String) request.getAttribute("ID_INSTANCIA") %>" /> 
					<p class="formBotonera"><input name="formANcontinuarboton" type="button" value="<bean:message key="tramitesAlmacenados.nuevo"/>" title="<bean:message key="tramitesAlmacenados.iniciarNuevo"/>" onclick="javascript:this.form.submit()"/></p>
				</html:form>
			</div>
			<!-- continuar tramite -->
			<div id="listadoTA">
				<h2><bean:message key="tramitesAlmacenados.listadoTramitesInacabados"/></h2>
				<p><bean:message key="tramitesAlmacenados.listado.texto1"/> <strong><bean:message key="tramitesAlmacenados.listado.texto2"/></strong> <bean:message key="tramitesAlmacenados.listado.texto3"/> <strong><bean:message key="tramitesAlmacenados.listado.texto4"/></strong> <bean:message key="tramitesAlmacenados.listado.texto5"/></p>
				<ul>
				<logic:iterate id="tramite" name="params" property="tramites" type="es.caib.zonaper.modelInterfaz.TramitePersistentePAD">
					<li>
					
						<logic:equal name="tramite"  property="usuarioFlujoTramitacion" value="<%=request.getUserPrincipal().getName()%>">						
							<html:link href="<%= urlCargarTramite + "&idPersistencia=" + tramite.getIdPersistencia()  %>">
								<bean:write name="tramite" property="fechaModificacion" format="dd/MM/yyyy HH:mm"/>
								(<%=es.caib.util.DataUtil.distancia(es.caib.util.StringUtil.fechaACadena(new Date(),"dd/MM/yyyy"),es.caib.util.StringUtil.fechaACadena(tramite.getFechaCaducidad(),"dd/MM/yyyy"),"dd/MM/yyyy")%> <bean:message key="tramitesAlmacenados.dias"/>)											
							</html:link>
							 - <img src="imgs/enlaces/06cancelar.gif" alt="" /> <a href="#" onclick="javascript:cancelarTramite( '<%= urlEliminarTramite + "&idPersistencia=" + tramite.getIdPersistencia() + "&modelo=" + tramite.getTramite() + "&version=" + tramite.getVersion() %>' )" title="Cancelar trámite"><bean:message key="tramitesAlmacenados.borrar"/></a>
						</logic:equal>
						
						<logic:notEqual name="tramite"  property="usuarioFlujoTramitacion" value="<%=request.getUserPrincipal().getName()%>">						
							<bean:write name="tramite" property="fechaModificacion" format="dd/MM/yyyy HH:mm"/>		
								(<%=es.caib.util.DataUtil.distancia(es.caib.util.StringUtil.fechaACadena(new Date(),"dd/MM/yyyy"),es.caib.util.StringUtil.fechaACadena(tramite.getFechaCaducidad(),"dd/MM/yyyy"),"dd/MM/yyyy")%> <bean:message key="tramitesAlmacenados.dias"/>)											
						</logic:notEqual>						
					
						<logic:notEqual name="tramite"  property="nivelAutenticacion" value="A">
							<logic:notEqual name="tramite"  property="usuario" value="<%=tramite.getUsuarioFlujoTramitacion()%>">

								<bean:define id="usuarios" name="params" property="<%=es.caib.sistra.front.Constants.USUARIOSPAD_PARAMS_KEY%>" type="java.util.Map"/>
		
								<!--  Tramites remitidos a este usuario -->
								<logic:equal name="tramite" property="usuarioFlujoTramitacion" value="<%=request.getUserPrincipal().getName()%>">									
									<br>
									<i>
										<bean:message key="tramitesAlmacenados.remitidoPor"/>										
										<bean:write name="usuarios" property="<%=tramite.getUsuario()%>" />																	
									</i>	
								</logic:equal>
								
								<!--  Tramites remitidos por este usuario -->
								<logic:equal name="tramite" property="usuario" value="<%=request.getUserPrincipal().getName()%>">
									<bean:define id="usuarios" name="params" property="<%=es.caib.sistra.front.Constants.USUARIOSPAD_PARAMS_KEY%>" type="java.util.Map"/>
									<br>
									<i>
										<bean:message key="tramitesAlmacenados.remitidoA"/>										 
										<bean:write name="usuarios" property="<%=tramite.getUsuarioFlujoTramitacion()%>" />																	
									</i>	
								</logic:equal>


								
							</logic:notEqual>
						</logic:notEqual>	 						 
						 
					</li>						 
				</logic:iterate>	
				</ul>
			</div>
			<div class="sep"></div>			
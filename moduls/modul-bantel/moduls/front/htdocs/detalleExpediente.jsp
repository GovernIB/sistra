<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<script type="text/javascript" src="js/jquery.selectboxes.pack.js"></script>
<script type="text/javascript" src="js/ajuda.js"></script>
<script type="text/javascript">
	function aviso(){
		document.forms["0"].action='<html:rewrite page="/altaAviso.do"/>';
		document.forms["0"].submit();
	}
	
	function notificacion(){
		document.forms["0"].action='<html:rewrite page="/altaNotificacion.do"/>';
		document.forms["0"].submit();
	}
</script>
<bean:define id="urlConfirmacion" type="java.lang.String">
	<html:rewrite href="/zonaperback/init.do" paramId="lang" paramName="<%= Globals.LOCALE_KEY  %>" paramProperty="language" paramScope="session"/>
</bean:define>
<bean:define id="btnAltaAvis" type="java.lang.String">
	<bean:message key='expediente.altaAviso'/>
</bean:define>
<bean:define id="btnAltaNotif" type="java.lang.String">
	<bean:message key='expediente.altaNotif'/>
</bean:define>
<bean:define id="expediente" name="expediente" type="es.caib.zonaper.modelInterfaz.ExpedientePAD" />

<!--		 ajuda boto -->
		<button id="ajudaBoto" type="button" title="Activar ajuda"><img src="imgs/botons/ajuda.gif" alt="" /> <bean:message key="confirmacion.ayuda"/></button>
<!--		 /ajuda boto -->
		<div id="opcions">
				&nbsp;
		</div>	
		<!-- titol -->
		<!--<h1>Gestión de expedientes</h1>-->
		<!-- /titol -->
		
		<!-- ajuda -->
		<div id="ajuda">
			<h2><bean:message key="ajuda.titulo"/></h2>
			<bean:message key="ajuda.expediente.detalle"/>
		</div>
		<!-- /ajuda -->
		
		
            <html:messages id="msg" message="true">
            <div class="correcte">
				<p>
					<bean:write name="msg"/>.
				</p>
			</div>
			</html:messages>
		
		
		
		<!-- continguts -->
		<div class="continguts">
		
			<p class="titol"><bean:message key="detalle.expediente.detalle" /></p>
			
			
			<div class="avis">
				<dl>
					<dt><bean:message key="confirmacion.identificadorExpediente"/>:</dt>
					<dd><logic:notEmpty name="expediente" property="identificadorExpediente"><bean:write name="expediente" property="identificadorExpediente"/></logic:notEmpty></dd>
					<dt><bean:message key="confirmacion.unidadAdministrativa"/>:</dt>					
					<dd><logic:notEmpty name="nombreUnidad" ><bean:write name="nombreUnidad" /></logic:notEmpty></dd>
					<dt><bean:message key="expediente.descripcion"/>:</dt>
					<dd><logic:notEmpty name="expediente" property="descripcion"><bean:write name="expediente" property="descripcion"/></logic:notEmpty></dd>
				</dl>
				
				
			</div>
			
			
			<p class="titol"><bean:message key="detalle.expediente.historial"/></p>
			
			<table class="historic">
				<thead>
					<tr>
						<th><bean:message key="detalle.expediente.tabla.accion" /></th>
						<th><bean:message key="detalle.expediente.tabla.titulo" /></th>
						<th><bean:message key="detalle.expediente.tabla.estado" /></th>
						<th><bean:message key="detalle.expediente.tabla.fecha" /></th>
					</tr>
				</thead>
				<tbody>
					<logic:iterate name="expediente" property="elementos" id="elemento" indexId="indice">
						<%if (elemento instanceof es.caib.zonaper.modelInterfaz.NotificacionExpedientePAD) {%>
							<% if (((es.caib.zonaper.modelInterfaz.NotificacionExpedientePAD)elemento).getFechaFirmaAcuse() == null){%>
								<tr class="novetat">
							<%}else{%>
								<tr>
							<%}
						}else{ %>
							<tr>
						<%} %>
							<td>
							<%if (elemento instanceof es.caib.zonaper.modelInterfaz.NotificacionExpedientePAD) {%>
								<bean:message key="detalle.expediente.notificacion" />
							<%}else if (elemento instanceof es.caib.zonaper.modelInterfaz.EventoExpedientePAD) {%>
								<bean:message key="detalle.expediente.aviso" />
							<%}else if (elemento instanceof es.caib.zonaper.modelInterfaz.TramiteExpedientePAD) {%>
								<bean:message key="detalle.expediente.tramite" />
							<%} %>
						</td>
						<td>
							<!-- 
							tipos:
									N=notificacion
									A=aviso
									T=tramite
							 -->						
							<%if (elemento instanceof es.caib.zonaper.modelInterfaz.NotificacionExpedientePAD) {%>
								<a href="<%="mostrarDetalleElemento.do?tipo=N&codigo=" + indice + "&identificador=" + expediente.getIdentificadorExpediente() + "&unidad=" + expediente.getUnidadAdministrativa() + "&clave=" + expediente.getClaveExpediente()%>">
									<bean:write name="elemento" property="tituloOficio"/>
								</a>
							<%}else if (elemento instanceof es.caib.zonaper.modelInterfaz.EventoExpedientePAD) {%>
								<a href="<%="mostrarDetalleElemento.do?tipo=A&codigo=" + indice + "&identificador=" + expediente.getIdentificadorExpediente() + "&unidad=" + expediente.getUnidadAdministrativa() + "&clave=" + expediente.getClaveExpediente()%>">
									<bean:write name="elemento" property="titulo"/>
								</a>
							<%}else if (elemento instanceof es.caib.zonaper.modelInterfaz.TramiteExpedientePAD) {%>
								<a href="<%="mostrarDetalleElemento.do?tipo=T&codigo=" + indice  + "&identificador=" + expediente.getIdentificadorExpediente() + "&unidad=" + expediente.getUnidadAdministrativa() + "&clave=" + expediente.getClaveExpediente()%>">
									<bean:write name="elemento" property="descripcion"/>
								</a>
							<%} %>
						</td>
						<td class="estat">
							<%if (elemento instanceof es.caib.zonaper.modelInterfaz.NotificacionExpedientePAD) {%>
								<% if (((es.caib.zonaper.modelInterfaz.NotificacionExpedientePAD)elemento).getFechaFirmaAcuse() == null){%>
									<bean:message key="detalle.expediente.notificacion.estado.pendiente" />
								<%}else{%>
									<bean:message key="detalle.expediente.notificacion.estado.recibida" />
								<%} %>
							<!--los avisos no tienen estado-->
							<%}else if (elemento instanceof es.caib.zonaper.modelInterfaz.TramiteExpedientePAD) {%>
								<bean:message key="detalle.expediente.tramite.estado" />
							<%} %>
						</td>
						<td><bean:write name="elemento" property="fecha" format="dd/MM/yyyy  HH:mm"/></td>
					</tr>
					</logic:iterate>
				</tbody>
			</table>
			<html:form  action="confirmarPreregistro" styleClass="remarcar opcions" >
				<html:hidden name="expediente" property="identificadorExpediente"/>
				<html:hidden name="expediente" property="unidadAdministrativa"/>
				<html:hidden name="expediente" property="claveExpediente"/>
				
				<p class="botonera">
					<html:submit value="<%=btnAltaAvis%>" onclick="aviso();"/>
					<html:submit value="<%=btnAltaNotif%>" onclick="notificacion();"/>
				</p>
			</html:form>
		</div>
		<!-- /continguts -->
	



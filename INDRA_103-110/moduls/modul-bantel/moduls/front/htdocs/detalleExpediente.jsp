<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

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
<bean:define id="habilitarAvisos" value="<%= (expediente.getConfiguracionAvisos().getHabilitarAvisos() == null?"O":(expediente.getConfiguracionAvisos().getHabilitarAvisos().booleanValue())?\"S\":\"N\")%>" type="java.lang.String"/>

<script type="text/javascript" src="js/jquery.selectboxes.pack.js"></script>
<script type="text/javascript" src="js/ajuda.js"></script>
<script type="text/javascript" src="js/funcions.js"></script>
<script type="text/javascript" src="js/mensaje.js"></script>	
<script type="text/javascript">

	function aviso(){
		document.forms["0"].action='<html:rewrite page="/altaAviso.do"/>';
		document.forms["0"].submit();
	}
	
	function notificacion(){
		document.forms["0"].action='<html:rewrite page="/altaNotificacion.do"/>';
		document.forms["0"].submit();
	}

	function volver(){
		document.location='<html:rewrite page="/busquedaExpedientes.do"/>';
	}

	function mostrarModificarAvisos(){
		 $('#divModificacionAvisos').css({
		           position:'absolute',
		           left: '200px',
		          top: ($(window).scrollTop() + 50)
		   });
		  Tamanyos.iniciar();

		  $('#habilitarModif').val($('#habilitar').val());
		  $('#emailModif').val($('#email').val());
		  $('#smsModif').val($('#sms').val());
		  
		  Fondo.mostrar();
		  cambioHabilitar();
		  $('#divModificacionAvisos').show();			
	}

	function esconderModificarAvisos(){
		  Fondo.esconder();			
		  $('#divModificacionAvisos').hide();		  
	}

	function modificarAvisos() {

		var habilitar = $('#habilitarModif').val();
		var email =  $('#emailModif').val();
		var sms = $('#smsModif').val();
		
		if (habilitar == "S" && email == "") {
			alert("<bean:message key="expediente.aviso.configuracionAvisos.emailVacio"/>");
			return;
		}	

		if (habilitar != "S") {
				email = "";
				sms = "";
				$('#emailModif').val("");
				$('#smsModif').val("");
		}	 

		<logic:equal name="permitirSms" value="N">
			// Si no esta habilitado sms, reseteamos sms si se modifica
			sms = "";
		</logic:equal>
		
		
		var data = {};
		data["identificadorExpediente"] = '<bean:write name="expediente" property="identificadorExpediente"/>';
		data["unidadAdministrativa"] = '<bean:write name="expediente" property="unidadAdministrativa"/>';
		data["claveExpediente"] = '<bean:write name="expediente" property="claveExpediente"/>';
		data["habilitar"] = habilitar;
		data["email"] = email;
		data["sms"] = sms;
		
		$.postJSON("modificarConfiguracionAvisos.do", data, modificarAvisosOk); 		
	}

	function modificarAvisosOk(json) {

		if (json.error == "") {
			// Actualizamos valores
			$('#habilitar').val($('#habilitarModif').val());
	    	$('#email').val($('#emailModif').val());
			$('#sms').val($('#smsModif').val());

			// Actualizamos texto
			if ($('#habilitar').val() == "S") {
				$("#avisosHabilitados").text("<bean:message key="expediente.avisos.si"/>");
			} else {
				$("#avisosHabilitados").text("<bean:message key="expediente.avisos.no"/>");				
			}
			
			// Escondemos capa
			alert("<bean:message key="expediente.aviso.configuracionAvisos.modificado"/>");
			esconderModificarAvisos();			
		} else {
			alert(json.error);
		}				
	}

	function cambioHabilitar() {
		if ($('#habilitarModif').val() == "S") {
			 $('#direcciones').show();		
		} else {
			 $('#direcciones').hide();			
		}				
	}
	
	$(document).ready(function(){
				$('#divModificacionAvisos').hide();
				$.postJSON = function(url, data, callback) {
		        	return jQuery.ajax({
				        'type': 'POST',
				        'url': url,
				        'data': data,
				        'dataType': 'json',
				        'success': callback,
				        'error': function() {
							alert("Error enviando datos al servidor. Intentelo de nuevo.");					
						}
				    });
				};				
			}
		);
</script>

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
					<dt><bean:message key="expediente.identificadorProcedimiento"/>:</dt>
					<dd><logic:notEmpty name="descripcionProcedimiento" ><bean:write name="descripcionProcedimiento" /></logic:notEmpty></dd>
					<dt><bean:message key="expediente.descripcion"/>:</dt>
					<dd><logic:notEmpty name="expediente" property="descripcion"><bean:write name="expediente" property="descripcion"/></logic:notEmpty></dd>
					<dt><bean:message key="expediente.idioma"/>:</dt>
					<dd>
						<logic:equal name="expediente" property="idioma" value="es"><bean:message key="expediente.castellano"/></logic:equal>
						<logic:equal name="expediente" property="idioma" value="ca"><bean:message key="expediente.catalan"/></logic:equal>
						<logic:equal name="expediente" property="idioma" value="en"><bean:message key="expediente.ingles"/></logic:equal>
					</dd>
					<dt><bean:message key="expediente.clave"/>:</dt>
					<dd>
						<logic:notEmpty name="expediente" property="claveExpediente">
							<bean:write name="expediente" property="claveExpediente"/>
						</logic:notEmpty>
						<logic:empty name="expediente" property="claveExpediente">
							<i><bean:message key="expediente.claveVacia"/></i>
						</logic:empty>
					</dd>
					<dt><bean:message key="expediente.avisos"/>:</dt>
					<dd>
						<span id="avisosHabilitados">
							<logic:equal name="habilitarAvisos" value="O">
								<bean:message key="expediente.avisos.opcional"/>															
							</logic:equal>
							<logic:equal name="habilitarAvisos" value="S">
								<bean:message key="expediente.avisos.si"/>															
							</logic:equal>
							<logic:equal name="habilitarAvisos" value="N">
								<bean:message key="expediente.avisos.no"/>
							</logic:equal>
						</span>
						<img src="imgs/botons/cercar.gif" alt="<bean:message key="expediente.avisos.modificar"/>"  
										onclick="javascript:mostrarModificarAvisos();"/>
						
					</dd>					
				</dl>				
			</div>
						
			<p class="titol"><bean:message key="detalle.expediente.historial"/></p>
			
			<table class="historic">
				<thead>
					<tr>
						<th><bean:message key="detalle.expediente.tabla.accion" /></th>
						<th><bean:message key="detalle.expediente.tabla.titulo" /></th>
						<th><bean:message key="detalle.expediente.tabla.fecha" /></th>
						<th><bean:message key="detalle.expediente.tabla.estado" /></th>
					</tr>
				</thead>
				<tbody>
					<logic:iterate name="expediente" property="elementos" id="elemento" indexId="indice">
					<tr>
							<td>
							<%if (elemento instanceof es.caib.zonaper.modelInterfaz.NotificacionExpedientePAD) {%>
								<%if ( ((es.caib.zonaper.modelInterfaz.NotificacionExpedientePAD) elemento).isRequiereAcuse()) {%>
									<bean:message key="detalle.expediente.notificacion.conAcuse" />
								<% } else { %>
									<bean:message key="detalle.expediente.notificacion.sinAcuse" />
								<% } %>
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
						<td><bean:write name="elemento" property="fecha" format="dd/MM/yyyy  HH:mm"/></td>
						<td class="estat">
							<%if (elemento instanceof es.caib.zonaper.modelInterfaz.NotificacionExpedientePAD) {%>
								<% if (((es.caib.zonaper.modelInterfaz.NotificacionExpedientePAD)elemento).getDetalleAcuseRecibo().getEstado().equals("PENDIENTE")){%>
									<bean:message key="detalle.notificacion.estado.pendiente"/>
								<%}else if (((es.caib.zonaper.modelInterfaz.NotificacionExpedientePAD)elemento).getDetalleAcuseRecibo().getEstado().equals("ENTREGADA")){%>
									<bean:message key="detalle.notificacion.estado.entregada"/>
								<%}else if (((es.caib.zonaper.modelInterfaz.NotificacionExpedientePAD)elemento).getDetalleAcuseRecibo().getEstado().equals("RECHAZADA")){%>
									<bean:message key="detalle.notificacion.estado.rechazada"/>	
								<%} %>							
							<%}else if (elemento instanceof es.caib.zonaper.modelInterfaz.EventoExpedientePAD) {%>
								<% if (((es.caib.zonaper.modelInterfaz.EventoExpedientePAD)elemento).getFechaConsulta() == null){%>
									<bean:message key="detalle.aviso.estado.pendiente"/>
								<%}else{%>
									<bean:message key="detalle.aviso.estado.entregada"/>
								<%} %>	
							<%}else if (elemento instanceof es.caib.zonaper.modelInterfaz.TramiteExpedientePAD) {%>
								<bean:message key="detalle.expediente.tramite.estado" />
							<%} %>													
						</td>
					</tr>
					</logic:iterate>
				</tbody>
			</table>
			<html:form action="altaAviso" styleClass="remarcar opcions" >
				<p class="botonera">
					<html:submit value="<%=btnAltaAvis%>" onclick="aviso();"/>
					<html:submit value="<%=btnAltaNotif%>" onclick="notificacion();"/>
				</p>
			</html:form>
		
			<!--  capa modificacion avisos expediente -->
			 <div id="divModificacionAvisos" class="remarcar">	
			 	
			 	<form id="formModifAvisos" method="post" class="remarcar opcions2">
			 		<p class="titol2">
						<bean:message key="expediente.aviso.configuracionAvisos.titulo"/>
					</p>
			 		<input type="hidden" id="habilitar" value="<%=habilitarAvisos%>"/>
					<input type="hidden" id="email" value="<%=habilitarAvisos.equals("S")?StringUtils.defaultString(expediente.getConfiguracionAvisos().getAvisoEmail()):""%>"/>
					<input type="hidden" id="sms" value="<%=habilitarAvisos.equals("S")?StringUtils.defaultString(expediente.getConfiguracionAvisos().getAvisoSMS()):""%>"/>
			 	
			 		<p>
						<label><bean:message key="expediente.aviso.configuracionAvisos.habilitar"/><sup>*</sup></label>
						<select id="habilitarModif" class="pc40" onchange="cambioHabilitar()">
						  <option value="S"><bean:message key="expediente.avisos.si"/></option>
						  <option value="N"><bean:message key="expediente.avisos.no"/></option>
						</select>	
					</p>
					<span id="direcciones">
			 		<p>
						<label><bean:message key="expediente.aviso.configuracionAvisos.email"/><sup>*</sup></label>
						<input type="text" class="pc40" id="emailModif"/>
					</p>
					<logic:equal name="permitirSms" value="S">
						<p>
							<label><bean:message key="expediente.aviso.configuracionAvisos.sms"/></label>
							<input type="text" class="pc40" id="smsModif"/>
						</p>
					</logic:equal>
					<logic:equal name="permitirSms" value="N">
						<p>
							<input type="hidden" id="smsModif" value=""/>
						</p>
					</logic:equal>
					</span>
					<p class="botonera">
						<input id="botonModificarAvisos" class="botonAlta" type='button' value='<bean:message key="expediente.aviso.configuracionAvisos.botonModificar"/>' 
								onclick="if(modificarAvisos()){return true;}else{return false;}"/>							
						</p>
						<br/>
					<p>
						<a id="botonCancelarAvisos" onclick="esconderModificarAvisos();"><bean:message key="expediente.aviso.configuracionAvisos.botonCancelar"/></a>
					</p>										
				</form>		 																												
			</div>		
			
		</div>
		<!-- /continguts -->
		
		 
		
		
	<!-- tornar enrere -->
		<div id="enrere">
			<a href="#" onclick="javascript:volver()">
				<bean:message key="detalle.aviso.tornar" />				
			</a>				
		</div>
  <!-- /tornar enrere -->

	<div id="fondo"></div>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="anexos" name="tramite" property="anexos" />
<html:xhtml/>
<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>

<bean:define id="contextoSistra" name="<%=es.caib.sistra.front.Constants.CONTEXTO_RAIZ%>" type="java.lang.String"/>

<bean:define id="referenciaPortal"  type="java.lang.String">
	<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property='<%="referenciaPortal("+ lang +")"%>'/>
</bean:define>
	
<bean:define id="tramite" name="tramite" type="es.caib.sistra.model.TramiteFront" />

<bean:define id="urlGuardarClave">
        <html:rewrite page="/protected/guardarClave.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>

<script type="text/javascript">

<!--

function posicionOffset(obj,tipo) {
	if(obj != null) {
		var totalOffset = (tipo == "left") ? obj.offsetLeft : obj.offsetTop;
		var parentEl = obj.offsetParent;
		while (parentEl != null){
			totalOffset = (tipo == "left") ? totalOffset+parentEl.offsetLeft : totalOffset+parentEl.offsetTop;
			parentEl = parentEl.offsetParent;
		}
		return totalOffset;
	}
}

opacidad = 0;
function fundidoMostrar(id) {
	obj = document.getElementById(id);
	with (obj) {
		if(document.all) style.filter = "alpha(opacity=40)";
		else style.opacity = 0.4;		
	}
	/*
	obj = document.getElementById(id);
	if(opacidad < 40) {
		opacidad += 10;
		if(typeof obj.style.filter != 'undefined') {
			if(window.opera) obj.style.opacity = opacidad/100;
			else obj.style.filter = "alpha(opacity="+opacidad+")";
		} else {
			obj.style.opacity = opacidad/100;
		}
		tiempo = setTimeout('fundidoMostrar("'+id+'")', 50);
	} else {
		clearTimeout(tiempo);
		opacidad = 0;
	}
	*/
}

function mostrarFondo() {
	if(document.getElementById('fondo') == null) {
		// creamos capa
		fondoC = document.createElement('div');
		fondoC.setAttribute('id','fondo');
		document.body.appendChild(fondoC);
	}
	// capturamos tamanyos
	ventanaX = document.documentElement.clientWidth;
	ventanaY = document.body.offsetHeight;
	usuariY = document.documentElement.clientHeight;
	if(usuariY > ventanaY) ventanaY = usuariY;
	// solo para IE, escondemos todos los SELECT
	if(document.all) {
		selects = document.getElementsByTagName('select');
		for(i=0;i<selects.length;i++) selects[i].style.display = 'none';
	}
	// mostramos el fondo
	fondoDiv = document.getElementById('fondo');
	fondoDiv.style.display = 'block';
	if(document.all) fondoDiv.style.filter = "alpha(opacity=0)";
	else fondoDiv.style.opacity = 0;
	fondoDiv.style.width = ventanaX + 'px';
	fondoDiv.style.height = ventanaY + 'px';
	return ventanaX, ventanaY, usuariY;
}

function esconderFondo() {
	// solo para IE, mostramos todos los SELECT
	if(document.all) {
		selects = document.getElementsByTagName('select');
		for(i=0;i<selects.length;i++) selects[i].style.display = 'inline';
	}
	document.getElementById('fondo').style.display = 'none';
}

function mostrarAviso(id) {
	
	mostrarFondo();
	
	fundidoMostrar('fondo');
	
	
	capaClave = document.getElementById(id);
	capaClave.style.display = 'block';
	capaClaveW = capaClave.offsetWidth;
	capaClaveH = capaClave.offsetHeight;
	capaClave.style.left = (ventanaX - capaClaveW)/2 + 'px';
	//capaClave.style.top = (usuariY - capaClaveH)/2 + 'px';
	if(window.XMLHttpRequest) {
		capaClave.style.position = 'fixed';
		capaClave.style.top = (usuariY - capaClaveH)/2 + 'px';
	} else {
		ventanaScrollY = document.documentElement.scrollTop;
		capaClave.style.top = (usuariY - capaClaveH)/2 + ventanaScrollY + 'px';
	}
	return capaClave;
}

function guardarClave() {		
	this.location="<%=urlGuardarClave + "&idPersistencia=" + tramite.getIdPersistencia() %>";
}


function ocultarAviso(id) {
	capaClave = document.getElementById(id);
	capaClave.style.display = 'none';
	esconderFondo();
}

function mostrarPasos(obj) {
	document.getElementById('pasosExplicacion').style.display = 'block';
	obj.innerHTML = '<bean:message key="pasoDebeSaber.enlaceOcultarPasos"/>';
	obj.onclick = function() { esconderPasos(this); };
	estaIframe();
}

function esconderPasos(obj) {
	document.getElementById('pasosExplicacion').style.display = 'none';
	obj.innerHTML = '<bean:message key="pasoDebeSaber.enlaceMostrarPasos"/>';
	obj.onclick = function() { mostrarPasos(this); };
	estaIframe();
}

tamanyo = 1;
function nuevoTamanyo(id,tamanyoFinal) {
	obj = document.getElementById(id);
	if(tamanyo < tamanyoFinal) {
		tamanyo += 40;
		obj.style.height = tamanyo + 'px';
		tiempo = setTimeout('nuevoTamanyo("'+id+'",'+tamanyoFinal+')', 50);
	} else {
		clearTimeout(tiempo);
		tamanyo = 1;
		obj.style.overflow = 'auto';
		obj.style.height = 'auto';
	}
}

tamanyoF = 0;
function nuevoTamanyoFalse(id,tamanyoActual) {
	obj = document.getElementById(id);
	if(tamanyoActual > tamanyoF) {
		tamanyoActual -= 40;
		if(tamanyoActual > 0) obj.style.height = tamanyoActual + 'px';
		tiempo = setTimeout('nuevoTamanyoFalse("'+id+'",'+tamanyoActual+')', 50);
	} else {
		clearTimeout(tiempo);
		obj.style.height = tamanyoF + 'px';
	}
}



// Gestion de avisos: mensajes plataforma y guardar clave
<logic:notEmpty name="tramite" property="mensajesPlataforma">
	window.onload = function() { mostrarAviso('mensajesPlataforma'); };
</logic:notEmpty>
<logic:empty name="tramite" property="mensajesPlataforma">
	<logic:equal name="metodoAutenticacion"  value="A">
		<logic:notPresent name="<%=es.caib.sistra.front.Constants.CLAVE_ALMACENADA_KEY%>">
			window.onload = function() { mostrarAviso('guardarClave'); };
		</logic:notPresent>
	</logic:equal>	
</logic:empty>

// Funcion que muestra el aviso de guardar clave despues de los mensajes de plataforma
function avisoGuardarClave(){
<logic:notEmpty name="tramite" property="mensajesPlataforma">
	<logic:equal name="metodoAutenticacion"  value="A">
		<logic:notPresent name="<%=es.caib.sistra.front.Constants.CLAVE_ALMACENADA_KEY%>">
			mostrarAviso('guardarClave');
		</logic:notPresent>
	</logic:equal>	
</logic:notEmpty>
}

-->
</script>

		<!-- Guardar clave -->
		<logic:equal name="metodoAutenticacion" value="A">
			<div id="guardarClave">
			
				<p class="apartado"><bean:message key="guardarClave.textoContinuarTramitacion" arg0="<%=tramite.getIdPersistencia()%>" arg1="<%=Integer.toString(tramite.getDiasPersistencia())%>"/></p>
				
				<logic:notEmpty name="tramite" property="fechaFinPlazo" >
					<bean:define id="fechaFinalPlazo" type="java.lang.String">
						<bean:write name="tramite" property="fechaFinPlazo" format="dd/MM/yyyy"/>
					</bean:define>
					<p class="apartado">
						<logic:equal name="tramite" property="tipoTramitacion" value="T">
							<bean:message key="guardarClave.textoPlazoEntrega.telematica" arg0="<%=fechaFinalPlazo%>"/>
						</logic:equal>
						
						<logic:notEqual name="tramite" property="tipoTramitacion" value="T">
							<bean:message key="guardarClave.textoPlazoEntrega.presencial" arg0="<%=fechaFinalPlazo%>"/>
						</logic:notEqual>	
					</p>			
				</logic:notEmpty>

				<p class="apartado"><bean:message key="guardarClave.textoEstadoTramitacion" arg0="<%=contextoSistra%>" arg1="<%=referenciaPortal%>" arg2="<%=tramite.getEntidad()%>"/></p>	
				
				<p class="apartado"><bean:message key="guardarClave.textoGuardar" /></p>	

				<p class="continuar">					
					<button type="button" onclick="guardarClave();ocultarAviso('guardarClave');">
						<img src="imgs/enlaces/05guardar.gif" alt="" /> <bean:message key="guardarClave.textoBoton"/>
					</button>
				</p>
			</div>
		</logic:equal>
		
		
		<!-- Mensajes plataforma -->
		<logic:notEmpty name="tramite" property="mensajesPlataforma">
			<div id="mensajesPlataforma">
			
				<logic:iterate name="tramite" property="mensajesPlataforma" id="mensaje">
					<p class="apartado">
						<bean:write name="mensaje" property="value" filter="false"/>
					</p>
				</logic:iterate>
				
				<p class="continuar">					
					<button type="button" onclick="ocultarAviso('mensajesPlataforma');avisoGuardarClave();">
						<bean:message key="mensajesPlataforma.textoBoton"/>
					</button>
				</p>
			</div>
		</logic:notEmpty>

		<!-- Informacion asistente y pasos -->
		<h2 class="nuestroAsistente"><bean:message key="pasoDebeSaber.nuestroAsistente"/></h2>
				<p class="apartado"><bean:message key="pasoDebeSaber.informacionAsistente"/></p>
				<logic:notEmpty name="tramite" property="fechaFinPlazo" >
					<bean:define id="fechaFinalPlazo" type="java.lang.String">
						<bean:write name="tramite" property="fechaFinPlazo" format="dd/MM/yyyy"/>
					</bean:define>
					<p class="apartado">
						<logic:equal name="tramite" property="tipoTramitacion" value="T">
							<bean:message key="guardarClave.textoPlazoEntrega.telematica" arg0="<%=fechaFinalPlazo%>"/>
						</logic:equal>
						
						<logic:notEqual name="tramite" property="tipoTramitacion" value="T">
							<bean:message key="guardarClave.textoPlazoEntrega.presencial" arg0="<%=fechaFinalPlazo%>"/>
						</logic:notEqual>																
					</p>
				</logic:notEmpty>								
				
				<logic:notEqual name="metodoAutenticacion" value="A">
					<p class="apartado">
						<bean:message key="pasoDebeSaber.accesoBuzon"  arg0="<%=referenciaPortal%>" arg1="<%=Integer.toString(tramite.getDiasPersistencia())%>"/>							
					</p>
				</logic:notEqual>
				
				<p class="apartado">
					<bean:message key="pasoDebeSaber.informacionPasos"/>
					<a href="javascript:void(0);" onclick="mostrarPasos(this);"><bean:message key="pasoDebeSaber.enlaceMostrarPasos"/></a>.
				</p>


		<!-- pasos -->
		
		<logic:notEmpty name="pasos">
				<bean:define id="pasos" name="pasos" type="java.util.ArrayList"/>
				<div id="pasosExplicacion">
					<h2 class="pasos"><bean:message key="pasoDebeSaber.explicacionPasos"/></h2>
					<!--  iteracion pasos -->
					<%int nPaso = 0;%>					  
					<dl id="pasosDL">
						<logic:iterate id="paso" name="pasos" type="es.caib.sistra.model.PasoTramitacion">		
							<%if ( nPaso != 0 ){%>
								<dt><bean:message key="pasoPasos.paso"/> <%= String.valueOf( nPaso ) %> - 
									<strong><bean:message key="<%= paso.getClaveTitulo() %>"/></strong>
								</dt>
								<dd><bean:message key="<%= paso.getClaveCuerpo() %>"/></dd>
							<% }
							nPaso++;%>
						</logic:iterate>
					</dl>
					<!--  end iteracion pasos -->
				</div>
		</logic:notEmpty>
		
		<!--  información relativa a LOPD -->		
		<logic:present name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property='<%="tituloLOPD("+ lang +")"%>'>
			<logic:notEmpty name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property='<%="tituloLOPD("+ lang +")"%>'>	
				<h2 class="lopd"><bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property='<%="tituloLOPD("+ lang +")"%>'/></h2>
			</logic:notEmpty>
			<logic:empty name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property='<%="tituloLOPD("+ lang +")"%>'>	
				<h2 class="lopd"><bean:message key="pasoDebeSaber.lopd"/></h2>
			</logic:empty>
		</logic:present>
	
		<!--  información relativa a LOPD -->		
		<logic:present name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property='<%="avisoLOPD("+ lang +")"%>'>
			<logic:notEmpty name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property='<%="avisoLOPD("+ lang +")"%>'>
				<p class="apartado"><bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property='<%="avisoLOPD("+ lang +")"%>' filter="false"/></p>	
			</logic:notEmpty>
		</logic:present>


		<!--  informacion inicio -->
		<logic:notEmpty name="tramite" property="informacionInicio">
			<h2 class="indicaciones"><bean:message key="pasoDebeSaber.antesTramitar"/></h2>
			<p class="apartado"><bean:write name="tramite" property="informacionInicio" filter="false"/></p>
		</logic:notEmpty>
			<!--  Informacion si se deben descargar plantillas -->
		<logic:equal name="tramite" property="descargarPlantillas" value="true">
			<h2 class="plantillas"><bean:message key="pasoDebeSaber.descargarPlantillas"/></h2>
			<p class="apartado"><bean:message key="pasoDebeSaber.descargarPlantillas.instrucciones"/></p>
			<!-- iconografia -->
			<div id="iconografia">
				<a href="javascript:icosMasInfo();" id="iconografiaMasInfo" title="<bean:message key="iconografia.info" />"><bean:message key="iconografia.masInfo"/></a>
				<h4><bean:message key="pasoDebeSaber.descargarPlantillas.iconografia"/></h4>
				<ul>
					<li><img src="imgs/tramitacion/iconos/doc_obligatorio.gif" alt="<bean:message key="pasoDebeSaber.iconografia.documentoObligatorio"/>" /> <bean:message key="pasoDebeSaber.iconografia.documentoObligatorio"/><span><bean:message key="pasoDebeSaber.iconografia.documentoObligatorioInfo"/></span></li>
					<li><img src="imgs/tramitacion/iconos/doc_opcional.gif" alt="<bean:message key="pasoDebeSaber.iconografia.documentoOpcional"/>" /> <bean:message key="pasoDebeSaber.iconografia.documentoOpcional"/><span><bean:message key="pasoDebeSaber.iconografia.documentoOpcionalInfo"/></span></li>
					<li><img src="imgs/tramitacion/iconos/form_dependiente.gif" alt="<bean:message key="pasoDebeSaber.iconografia.documentoDependiente"/>" /> <bean:message key="pasoDebeSaber.iconografia.documentoDependiente"/><span><bean:message key="pasoDebeSaber.iconografia.documentoDependienteInfo"/></span></li>
				</ul>
			</div>
			<!-- listado plantillas -->
			<div id="listadoPlantillas">
			<% int idx = 1; %>
			<logic:iterate id="anexo" name="tramite" property="anexos" type="es.caib.sistra.model.DocumentoFront">
				<logic:equal name="anexo" property="anexoMostrarPlantilla" value="true">
				<div class="iconos"><logic:equal name="anexo" property="obligatorio" value='S'><img src="imgs/tramitacion/iconos/doc_obligatorio.gif" alt="<bean:message key="pasoDebeSaber.iconografia.documentoObligatorio"/>" title="Documento obligatorio" /></logic:equal><logic:equal name="anexo" property="obligatorio" value='D'><img src="imgs/tramitacion/iconos/form_dependiente.gif" alt="<bean:message key="pasoDebeSaber.iconografia.documentoDependiente"/>" title="Documento dependiente" /></logic:equal><logic:equal name="anexo" property="obligatorio" value='N'><img src="imgs/tramitacion/iconos/doc_opcional.gif" alt="<bean:message key="pasoDebeSaber.iconografia.documentoOpcional"/>" title="Documento opcional" /></logic:equal> <img src="imgs/tramitacion/iconos/plantilla.gif" alt="<bean:message key="pasoDebeSaber.iconografia.plantillaDocumento"/>" title="Plantilla del documento" /></div>
				<div class="enlaceDescargar">
					<a id="doc<%= idx %>" href="<%= anexo.getAnexoPlantilla() %>" onmouseover="mostrarDocInfo(this,this.id);" onfocus="mostrarDocInfo(this,this.id);" target="_blank"><bean:message key="pasoDebeSaber.descargarPlantillas.descargarPlantilla"/><bean:write name="anexo" property="descripcion"/></a>
					<div id="doc<%= idx %>info">
						<p><bean:write name="anexo" property="informacion"/></p>
						<p><bean:message key="pasoDebeSaber.descargarPlantillas.restricciones"/></p>
						<ul>
							<li><bean:message key="pasoDebeSaber.descargarPlantillas.tipo"/><bean:write name="anexo" property="anexoExtensiones"/></li>
							<li><bean:message key="pasoDebeSaber.descargarPlantillas.tamanyo"/><bean:write name="anexo" property="anexoTamanyo"/> Kb.</li>
						</ul>
					</div>
				</div>
				</logic:equal>
			<% idx ++; %>	
			</logic:iterate>
			</div>			
		</logic:equal>
			<!--   -->
			<div class="sep"></div>
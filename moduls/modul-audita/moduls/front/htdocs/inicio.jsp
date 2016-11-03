<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<div style="padding:10px; ">
	<!-- PÁGINA DE INICIO -->
	
	<p style="border-bottom:1px dotted #CCCCCC; margin-top:0; ">
	    <html:link action="/refresh" style="margin-left:20px; " title="Actualizar el contenido de esta página"><bean:message key="inicio.refrescarDatos"/></html:link>
	</p>
		
	<h1><bean:message key="inicio.titulo"/></h1>
	<logic:present name="cuadro" >
	<p style="border-bottom:1px dotted #CCCCCC; margin-top:0; "><em><bean:message key="inicio.ultimaActualizacion"/>: <bean:write name="cuadro" property="ultimaActualizacion" /></em></p>
	<table cellpadding="0" cellspacing="0" class="titulo">
	<tr>
		<td class="txt"><bean:message key="inicio.portal.titulo"/></td>
		<td class="linea">&nbsp;</td>
	</tr>
	</table>
	<p class="subtitulo"><bean:message key="inicio.portal.serviciosActivos"/></p>
	<table cellpadding="1" cellspacing="1" id="servicios"  style="vertical-align:bottom;padding:0px;text-align:justify">
	<tr >
		<td><img src="./images/cuadromando/ico_tramites.gif" alt=""  height="20">&nbsp;<strong><bean:message key="inicio.portal.serviciosTelematicosCatalogo"/>: <bean:write name="cuadro" property="portal.serviciosActivosTelematicos" /></strong></td>
		<td style="padding-left:40px"><img src="./images/cuadromando/i_nivel_c.gif" alt="" width="23" height="20">&nbsp;<bean:message key="inicio.portal.certificados"/>: <strong><bean:write name="cuadro" property="portal.nivelCertificados" /></strong></td>
		<td><img src="./images/cuadromando/i_nivel_u.gif" alt="" width="23" height="20">&nbsp;<bean:message key="inicio.portal.usuario"/>: <strong><bean:write name="cuadro" property="portal.nivelUsuario" /></strong></td>
		<td><img src="./images/cuadromando/i_nivel_a.gif" alt="" width="23" height="20">&nbsp;<bean:message key="inicio.portal.anonimos"/>: <strong><bean:write name="cuadro" property="portal.nivelAnonimos" /></strong></td>
	</tr>
	<tr>
		<td></td>
		<td style="padding-left:40px"><img src="./images/cuadromando/i_registro_r.gif" alt="" width="23" height="20">&nbsp;<bean:message key="inicio.portal.registro"/>: <strong><bean:write name="cuadro" property="portal.envioRegistro" /></strong></td>
		<td><img src="./images/cuadromando/i_registro_b.gif" alt="" width="23" height="20">&nbsp;<bean:message key="inicio.portal.bandeja"/>: <strong><strong><bean:write name="cuadro" property="portal.envioBandeja" /></strong></td>
		<td><img src="./images/cuadromando/i_registro_c.gif" alt="" width="23" height="20">&nbsp;<bean:message key="inicio.portal.consulta"/>: <strong><bean:write name="cuadro" property="portal.envioConsulta" /></strong></td>
	</tr>
	<tr>
		<td rowspan="1"></td>
		<td style="padding-left:40px"><img src="./images/cuadromando/ico_03conpago.gif" alt="" width="23" height="20">&nbsp;<bean:message key="inicio.portal.pago"/>: <strong><strong><bean:write name="cuadro" property="portal.documentoTipoPago" /></strong></td>
	</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="titulo">
	<tr>
		<td class="txt"><bean:message key="inicio.tramitacion.titulo"/></td>
		<td class="linea">&nbsp;</td>
	</tr>
	</table>
	<p class="subtitulo"><bean:message key="inicio.tramitacion.datosTotales"/></p>
	<table cellpadding="0" cellspacing="0" class="datosTotales" >
	<tr>
		<td class="colorNaranja"><img src="./images/nada.gif"><bean:message key="inicio.tramitacion.telematica"/>: <span><bean:write name="cuadro" property="tramitacion.tramitesTelematicos" /></span></td>
		<td class="colorVerde"><img src="./images/nada.gif"><bean:message key="inicio.tramitacion.preRegistro"/>: <span><bean:write name="cuadro" property="tramitacion.tramitesPreRegistro" /></span></td>
		<td class="colorMalva"><img src="./images/nada.gif"><bean:message key="inicio.portal.acceso.zonaPersonal"/>: <span><bean:write name="cuadro" property="portal.accesosBuzon" /></span></td>
	</tr>
	</table>
	<table cellpadding="5" cellspacing="0" style="width:100%; border:0; background-color:#FFF3E7; color:#7C4700; margin-top:20px; ">
	<tr>
		<td style="vertical-align:top; "><bean:message key="inicio.tramitacion.maximoTramites.texto1"/> <strong><bean:message key="inicio.tramitacion.maximoTramites.texto2"/></strong> <bean:message key="inicio.tramitacion.maximoTramites.texto3"/> <strong><bean:write name="cuadro" property="tramitacion.numeroMaximoTramites" /></strong>, <bean:message key="inicio.tramitacion.maximoTramites.texto4"/> <strong><bean:write name="cuadro" property="tramitacion.fechaMaximoTramites" /></strong>.</td>
	</tr>
	</table>
	<p class="subtitulo"><bean:message key="inicio.tramitacion.historial"/> <span><bean:write name="cuadro" property="historial" /></span></p>
	<table cellpadding="0" cellspacing="0" style="width:90%; border:0; margin-bottom:20px; ">
	<tr>
		<td style="width:33%; text-align:center; ">
		    <img src="mostrarGraficoFichero.do?fichero=graficatramites.jpg" style="border:0px solid #000; ">
		</td>
		<td style="text-align:center; ">
		    <img src="mostrarGraficoFichero.do?fichero=graficatramitespreregistro.jpg" style="border:0px solid #000; ">
		</td>
		<td style="width:33%; text-align:center; ">
		    <img src="mostrarGraficoFichero.do?fichero=graficazonapersonal.jpg" style="border:0px solid #000; ">
		</td>
	</tr>
	</table>
	<!-- los MAS TRAMITADOS -->
	<table cellpadding="0" cellspacing="0" class="titulo" style="margin-bottom:1px; " id="losMasTramitados">
	<tr>
		<td class="txt"><bean:message key="inicio.masTramitados.titulo"/></td>
		<td class="linea">&nbsp;</td>
	</tr>
	</table>
	<table cellpadding="5" cellspacing="0" class="loMas">
	<tr>
		<th><bean:message key="inicio.masTramitados.modelo"/></th>
		<th width="30%"><bean:message key="inicio.masTramitados.organismo"/></th>
		<th width="10%"><bean:message key="inicio.masTramitados.numero"/></th>
	</tr>
	<logic:iterate id="tramitado" name="cuadro" property="detalleTramitados">
	<tr>
		<td><bean:write name="tramitado" property="modelo" />&nbsp;</td>
		<td><bean:write name="tramitado" property="organismo" />&nbsp;</td>
		<td align="center"><bean:write name="tramitado" property="valor" />&nbsp;</td>
	</tr>								
	</logic:iterate>
	</table>
	<!-- los MAS ACCEDIDO -->
	<table cellpadding="0" cellspacing="0" class="titulo" style="margin-bottom:1px; ">
	<tr>
		<td class="txt"><bean:message key="inicio.masAccedidos.titulo"/></td>
		<td class="linea">&nbsp;</td>
	</tr>
	</table>
	<table cellpadding="5" cellspacing="0" class="loMas">
	<tr>
		<th><bean:message key="inicio.masAccedidos.modelo"/></th>
		<th width="30%"><bean:message key="inicio.masAccedidos.organismo"/></th>
		<th width="10%"><bean:message key="inicio.masAccedidos.numero"/></th>
	</tr>
	<logic:iterate id="accedido" name="cuadro" property="detalleAccedidos">
	<tr>
		<td><bean:write name="accedido" property="modelo" />&nbsp;</td>
		<td><bean:write name="accedido" property="organismo" />&nbsp;</td>
		<td align="center"><bean:write name="accedido" property="valor" />&nbsp;</td>
	</tr>								
	</logic:iterate>
	</table>
	<!-- ULTIMOS servicios -->
	<table cellpadding="0" cellspacing="0" class="titulo" style="margin-bottom:1px; ">
	<tr>
		<td class="txt"><bean:message key="inicio.ultimosDadosAlta.titulo"/></td>
		<td class="linea">&nbsp;</td>
	</tr>
	</table>
	<table cellpadding="5" cellspacing="0" class="loMas">
	<tr>
		<th><bean:message key="inicio.ultimosDadosAlta.modelo"/></th>
		<th width="30%"><bean:message key="inicio.ultimosDadosAlta.organismo"/></th>
		<th width="10%"><bean:message key="inicio.ultimosDadosAlta.numero"/></th>
		<th width="10%"><bean:message key="inicio.ultimosDadosAlta.fechaActivacion"/></th>
	</tr>
	<logic:iterate id="ultimo" name="cuadro" property="detalleUltimos">
	<tr>
		<td><bean:write name="ultimo" property="modelo" />&nbsp;</td>
		<td><bean:write name="ultimo" property="organismo" />&nbsp;</td>
		<td align="center"><bean:write name="ultimo" property="valor" />&nbsp;</td>
		<td align="center"><bean:write name="ultimo" property="fecha" />&nbsp;</td>
	</tr>								
	</logic:iterate>
	</table>
	</logic:present>
</div>
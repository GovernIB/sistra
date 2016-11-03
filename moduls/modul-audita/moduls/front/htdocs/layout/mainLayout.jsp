<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>AUDITA</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control"content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<link href="./estilos/cuadromando.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
<!--

function redirige(url,obj) {
	obj.location.replace(url);
}
function verGrafico(as_tipo,as_titulo){
	document.location = "<html:rewrite page='/grafico.do' />?OPCION=I&ANYO=&MES=08&DIA=&ELEMENTO=" + as_tipo + "&TITULOELEMENTO=" + as_titulo ;
}
function verDetalle(as_tipo){
	document.location = "<html:rewrite page='/detalle.do' />?OPCION=I&ANYO=&MES=08&DIA=&ELEMENTO=" + as_tipo ;
}
function verDetalleResumen(as_opcion){
	document.location = "cuadromando_detalle_resumen.jsp?OPCION=" + as_opcion;
}
function cambiarModo(as_modo){
	ls_url = "cuadromando.jsp?OPCION=" + as_modo;
	redirige(ls_url,this);	
}
function buscar()
{
	document.forms[0].submit();
}

function refrescarInicio(){
	redirige("refrescarinicio.jsp",this);
}
//-->
</script>
<!-- import the calendar script -->
<link rel="alternate stylesheet" type="text/css" media="all" href="js/calendar-brown.css" title="summer" />
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/lang/calendar-es.js"></script>
<script type="text/javascript" src="js/calendario.js"></script>
<script type="text/javascript" src="js/fechas.js"></script>
</head>

<body>
<div id="capaMensaje" style="position:absolute; visibility:hidden; font-family:Verdana, Arial, Helvetica, sans-serif; font-size:10px; border:2px solid #336598; background-color:#FFFFFF; padding:5px; width:300px; overflow:hidden; z-index:10; "></div>
<script type="text/javascript" src="js/SubMensajeDerecha.js"></script>
<script type="text/javascript">
<!--
desplazamientoX = 100;
function getAyuda(a_indice){
	switch(a_indice) {
	   case "C_SESIONES": return "Número total de sesiones iniciadas en el Catálogo de servicios.";
	   case "C_PAGINAS": return "Número total de páginas servidas por el Catálogo.";
	   case "C_SERVGENRED": return "Número total de accesos a servicios de la plataforma de tramitación GENRED desde el catálogo.";
	   case "C_SERVEXT": return "Número total de accesos a servicios externos ofrecidos por diferentes organismos de la Generalitat Valenciana desde el catálogo.";
	   case "C_IDIOMA": return "Número total de páginas servidas por idioma a los usuarios del Catálogo.";
	   case "T_SESIONES": return "Número total de sesiones iniciadas en los diferentes trámites implementados sobre la plataforma de tramitación GENRED.";
	   case "T_TRAMITESINI": return "Número total de trámites iniciados de los implementados sobre la plataforma de tramitación GENRED.";
	   case "T_TRAMITESREG": return "Número total de trámites  registrados de los implementados sobre la plataforma de tramitación GENRED.";
	   case "T_NOTIFENV": return "Número total de notificaciones telemáticas enviadas mediante la plataforma de tramitación GENRED.";
	   case "T_NOTIFENT": return "Número total de notificaciones telemáticas entregadas mediante la plataforma de tramitación GENRED.";
	   case "T_USUARIOS": return "Número máximo de sesiones concurrentes registradas accediendo a la plataforma de tramitación GENRED.";
	   case "T_IDIOMA": return "Número total de sesiones, por idioma, iniciadas en los diferentes trámites implementados sobre la plataforma de tramitación GENRED.";	   
	   case "VERIFCERTIF": return "Número total de accesos al servicio de verificación de certificados implementado sobre la plataforma de tramitación GENRED.";
	   case "S_RBVAL": return "Número total de validaciones de documentos firmados en e-Rubrica.";
	   case "S_RBFIR": return "Número total de firma de documentos en e-Rubrica.";
	   case "S_RBDES": return "Número total de descargas de Rubrica.";
	   case "T_ACCESOSBUZON": return "Número total de accesos al  Buzón del Ciudadano.";
	}
}
//-->
</script>

<tiles:insert name="header" />
<tiles:insert name="main" />
<tiles:insert name="footer"/>

</body>
</html>

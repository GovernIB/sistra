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
	   case "C_SESIONES": return "N�mero total de sesiones iniciadas en el Cat�logo de servicios.";
	   case "C_PAGINAS": return "N�mero total de p�ginas servidas por el Cat�logo.";
	   case "C_SERVGENRED": return "N�mero total de accesos a servicios de la plataforma de tramitaci�n GENRED desde el cat�logo.";
	   case "C_SERVEXT": return "N�mero total de accesos a servicios externos ofrecidos por diferentes organismos de la Generalitat Valenciana desde el cat�logo.";
	   case "C_IDIOMA": return "N�mero total de p�ginas servidas por idioma a los usuarios del Cat�logo.";
	   case "T_SESIONES": return "N�mero total de sesiones iniciadas en los diferentes tr�mites implementados sobre la plataforma de tramitaci�n GENRED.";
	   case "T_TRAMITESINI": return "N�mero total de tr�mites iniciados de los implementados sobre la plataforma de tramitaci�n GENRED.";
	   case "T_TRAMITESREG": return "N�mero total de tr�mites  registrados de los implementados sobre la plataforma de tramitaci�n GENRED.";
	   case "T_NOTIFENV": return "N�mero total de notificaciones telem�ticas enviadas mediante la plataforma de tramitaci�n GENRED.";
	   case "T_NOTIFENT": return "N�mero total de notificaciones telem�ticas entregadas mediante la plataforma de tramitaci�n GENRED.";
	   case "T_USUARIOS": return "N�mero m�ximo de sesiones concurrentes registradas accediendo a la plataforma de tramitaci�n GENRED.";
	   case "T_IDIOMA": return "N�mero total de sesiones, por idioma, iniciadas en los diferentes tr�mites implementados sobre la plataforma de tramitaci�n GENRED.";	   
	   case "VERIFCERTIF": return "N�mero total de accesos al servicio de verificaci�n de certificados implementado sobre la plataforma de tramitaci�n GENRED.";
	   case "S_RBVAL": return "N�mero total de validaciones de documentos firmados en e-Rubrica.";
	   case "S_RBFIR": return "N�mero total de firma de documentos en e-Rubrica.";
	   case "S_RBDES": return "N�mero total de descargas de Rubrica.";
	   case "T_ACCESOSBUZON": return "N�mero total de accesos al  Buz�n del Ciudadano.";
	}
}
//-->
</script>

<tiles:insert name="header" />
<tiles:insert name="main" />
<tiles:insert name="footer"/>

</body>
</html>

<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="es.sistra.clientcert.loginmodule.ClientCertLoginModule" %>
	
<%
String ticket = request.getParameter("ticket");
String urlDestino = ClientCertLoginModule.obtenerUrlDestinoSesionAutenticacion(ticket);
		
%>	
<html>
<head>
	<script type="text/javascript">
 		 function redireccionUrlDestino() {
 	 		 var urlDestino = "<%=urlDestino%>"; 			
 			 document.location = urlDestino; 			 
 		 }
	</script>	
</head>

<body onload="redireccionUrlDestino()">
	<bean:message key="login.clave.redirigiendoDestino" />
</body>
</html>

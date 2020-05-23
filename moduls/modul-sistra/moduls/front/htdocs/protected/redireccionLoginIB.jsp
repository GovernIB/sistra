<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%
		String urlDestino =  (String) session.getAttribute("urlDestino");
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

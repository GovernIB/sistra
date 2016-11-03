<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="es.caib.sistra.plugins.PluginFactory, es.caib.sistra.plugins.login.PluginLoginIntf, java.lang.reflect.Method" %>
	
<%
		String ticket = request.getParameter("ticket");
		PluginLoginIntf plg = PluginFactory.getInstance().getPluginLogin();
		
		Class[] params = new Class[] {String.class};
		Method method = plg.getClass().getDeclaredMethod("obtenerUrlDestinoSesionAutenticacion", params);
		
		Object[] values = new Object[] {ticket};
		Object result = method.invoke(plg,values);
		String urlDestino =  (String) result;
		
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

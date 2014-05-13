
<%@page import="es.caib.util.ValidacionesUtil"%><html>
<head>
<script type="text/javascript">
<!--
	<%	
		// Para la zona personal lo abrimos en el mismo doc, para
		// el resto lo redirigimos al top por si se ejecuta en iframe
		String url = request.getParameter("url");		
		String top = "";
		if (url.indexOf("/zonaperfront/inicio") == -1){
			top	= "top.";
		}
		
		// Validamos si es una URL. Si no lo es redirigimos a portal.
		if (!ValidacionesUtil.esURL(url)) {
			url = "irAPortal.do";	
		}
	%>
	
	<%=top%>document.location="<%=url%>";
	
-->
</script>
</head>
<body>
</body>
</html>
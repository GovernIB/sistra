<html>
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
	%>
	
	<%=top%>document.location="<%=url%>";
	
-->
</script>
</head>
<body>
</body>
</html>
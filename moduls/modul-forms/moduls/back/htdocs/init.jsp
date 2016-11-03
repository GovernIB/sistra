<META HTTP-EQUIV="Window-target" CONTENT="_top">
<script language="Javascript">
<%
	String modelo = request.getParameter("modelo");
	String version = request.getParameter("version");
	String url = request.getContextPath() + "/init.do" + (modelo!=null?"?modelo="+modelo+"&version="+version:"");
%>

	window.top.document.location.href = '<%=url%>';
</script> 
<%@ page contentType="text/html; charset=ISO-8859-1"%>
<%@ page import="java.util.*, org.apache.struts.Globals, java.lang.reflect.Method" %>
<%@ page import="es.caib.sistra.util.loginib.LoginPageLoginIBUtil, es.caib.sistra.util.loginib.LoginPageLoginIBInfo" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%
	//Generamos info pagina login
	LoginPageLoginIBInfo infoLogin = LoginPageLoginIBUtil.generarInfoLogin(session, request);
	// Establecemos idioma
	request.setAttribute("lang", infoLogin.getIdioma());
	session.setAttribute(Globals.LOCALE_KEY, new Locale(infoLogin.getIdioma()));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<%=infoLogin.getIdioma()%>" lang="<%=infoLogin.getIdioma()%>">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><%=infoLogin.getTitulo()%></title>
<% if (infoLogin.getCss() != null){ %>
<link href="<%=infoLogin.getCss()%>" rel="stylesheet" type="text/css" />
<%}%>
<% if (!infoLogin.isLoginError()) {%>
<script type="text/javascript">
<!--
function login() {
	<% if (infoLogin.isRedirigirLoginIB()) { %>
	document.location = "<%=infoLogin.getUrlLoginIB()%>";
	<%}%>
	<% if (infoLogin.isRealizarLogin()) { %>
	document.getElementById('loginForm').submit();
	<%}%>
}
-->
</script>
<%}%>

</head>

<body <%=(!infoLogin.isLoginError()?"onload='login();'":"")%> >

<div id="contenidor">

	<!-- continguts -->
	<div id="continguts">
		<!-- tramitacion -->
		<div id="tramitacion">
			<br />

		    <%if (infoLogin.isLoginError()){ %>
			<div class="error"><h2><bean:message key="login.error" /></h2></div>
			<%}%>

			<% if (!infoLogin.isLoginError()) { %>
			<bean:message key="login.clave.redirigiendoClave" />
				<%if (infoLogin.isRealizarLogin()) { %>
				<form id="loginForm" action="j_security_check" method="post">
					<input name="j_username" id="j_username" type="hidden" value="<%=infoLogin.getUsernameNameLogin()%>"/>
					<input name="j_password" id="j_password" type="hidden" value="<%=infoLogin.getPasswordLogin()%>"/>
				</form>
				<%}%>
			<%}%>

		</div>
	</div>
</div>
</body>
</html>




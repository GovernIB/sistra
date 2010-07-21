<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="ayuda.cuadernoCarga.titulo"/></title>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
</head>

<body class="ventana">
<table class="nomarcd">
    <tr><td class="titulo"><bean:message key="ayuda.cuadernoCarga.titulo" /></td></tr>
<!--     <tr><td class="subtitulo">    <bean:message key="ayuda.expresion.subtitulo" /></td></tr>  -->
</table>
<table class="marcd">
     <tr>
        <td class="labelm">
        	<%if ( "dev".equals( request.getParameter( "type" ) ) )
        	{ %>
	            <bean:define id="paginaDev"><bean:message key="ayuda.cuadernoCarga.paginaDev" /></bean:define>
	            <tiles:insert beanName="paginaDev" />
            <% }
            else
            {%>
            	<bean:define id="paginaAudit"><bean:message key="ayuda.cuadernoCarga.paginaAudit" /></bean:define>
	            <tiles:insert beanName="paginaAudit" />
            <%}%>
        </td>
    </tr>
</table>
<br />
</body>
</html:html>
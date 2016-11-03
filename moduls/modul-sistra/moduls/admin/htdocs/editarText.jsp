<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
</head>
<%
	String idElement = request.getParameter("id");
	String titulo = request.getParameter("titulo");
%>
<script type="text/javascript">
  window.onload=function(){
     processOpener();
  }

  function processOpener(){
     var elements = opener.document.getElementsByName('<%= idElement %>');
     var strValue = elements.item(0).value;
     document.getElementById('ta').value = strValue;
  }
  
  function aceptar()
  {
     var elements = opener.document.getElementsByName('<%= idElement %>');
     elements[0].value = document.getElementById('ta').value;
     window.close();
  }

  function cerrar()
  {
     window.close();
  }

  function borrar()
  {
     document.getElementById('ta').value = '';
  }

</script>
<title><bean:message key="edicion.titulo"/></title>
<body>
<table  class="nomarc">
<tr>
    <td class="separador" style="text-align:center" colspan="3"><bean:message key="<%= titulo %>"/></td>
</tr>
<tr>
    <td colspan="3"><textarea name="ta" id="ta" cols="80" rows="40" style="width:850px;height:500px"></textarea></td>
</tr>
<tr cellpadding="20" style="text-align:center;">
    <td><button onclick="javascript:aceptar();"><bean:message key="edicion.aceptar"/></button></td>
    <td><button onclick="javascript:cerrar();"><bean:message key="edicion.cerrar"/></button></td>
    <td><button onclick="javascript:borrar();"><bean:message key="edicion.borrar"/></button></td>
</tr>
</table>
</body>
</html>

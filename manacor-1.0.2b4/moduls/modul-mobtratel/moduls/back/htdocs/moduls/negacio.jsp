<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:html locale="true" xhtml="false">
<head>
   <title>DENIED</title>
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
</head>

<body class="finestra">
<table class="nomarc">
    <tr><td class="titol"><bean:message key="negacio.baixa.titol" /></td></tr>
</table>
<br />
<table class="marc" >
    <tr>
        <td class="alert"><bean:message key="negacio.baixa.label"/></td>
    </tr>
</table>
<br />
<bean:define id="accioForm" name="baixa.action" type="java.lang.String" />
<html:form action='<%=accioForm%>' >
 <table class="nomarc">
    <tr>
      <td align="center">
       <html:cancel styleClass="button" ><bean:message key="boto.tornar" /></html:cancel>
      </td>
    </tr>
  </table>
</html:form>
</body>
</html:html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%--
Página principal.
    Crea dos frames: 
        /menu.jsp
        /main.jsp
--%>
<html:html locale="true" xhtml="true">
<head>
   <title>FORMSADMIN</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<%--
Frameborder no se puede poner en frameset, de momento está por un bug del mozilla
    http://bugzilla.mozilla.org/show_bug.cgi?id=3655#c27
--%>
<frameset cols="170,*" frameborder="0">
  <html:frame frameName="Menu" action="/menu" frameborder="0"  scrolling="auto" noresize="true" /> 
  <html:frame frameName="Ventana" page="/main.jsp" frameborder="0" scrolling="auto" noresize="true" />
</frameset>
</html:html>
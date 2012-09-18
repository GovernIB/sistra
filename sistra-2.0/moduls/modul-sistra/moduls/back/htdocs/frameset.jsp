<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:html locale="true" xhtml="true">
<head>
<title>Treeview example</title>
<script>
function op() { //This function is used with folders that do not open pages themselves. See online docs.
}
</script>
</head>

<!--
(Please keep all copyright notices.)
This frameset document includes the Treeview script.
Script found in: http://www.treeview.net
Author: Marcelino Alves Martins

You may make other changes, see online instructions, 
but do not change the names of the frames (treeframe and basefrm)
-->
<bean:define id="codigo">
<%= request.getParameter( "codigo" )%>
</bean:define>
<frameset name="treeframeset" id="treeframeset" name="" rows="40%,60%"  onResize="if (navigator.family == 'nn4') window.location.reload()">
  <html:frame frameName="treeframe" action="/arbol" paramId="codigo" paramName="codigo"/> 
  <html:frame frameName="basefrm" href="about:blank" />
</frameset>

</html:html>

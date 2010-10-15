<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="urlBandeja" type="java.lang.String">
	<html:rewrite href="/bantelfront/init.do" paramId="lang" paramName="<%= Globals.LOCALE_KEY  %>" paramProperty="language" paramScope="session"/>
</bean:define>
<div id="tabs">
	<ul>
		<li class="selec"><bean:message key="tabs.confirmacionEntradaBandeja" /></li>
		<li><html:link href="<%= urlBandeja %>"><bean:message key="tabs.bandejaEntrada" /></html:link></li>
	</ul>
</div>
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="urlConfirmacion" type="java.lang.String">
	<html:rewrite href="/zonaperback/init.do" paramId="lang" paramName="<%= Globals.LOCALE_KEY  %>" paramProperty="language" paramScope="session"/>
</bean:define>

<div id="tabs">
	<ul>
			<li class="selec">
				<html:link action="init">
					<bean:message key="tabs.titulo"/>	
				</html:link>		
			</li>
	</ul>
</div>
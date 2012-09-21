<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<div id="tabs">
	<ul>
		<li class="selec">
			<html:link action="init">
				<bean:message key="tabs.titulo.entidades"/>	
			</html:link>		
		</li>
	</ul>
</div>
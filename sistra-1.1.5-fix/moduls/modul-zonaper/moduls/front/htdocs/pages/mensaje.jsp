<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="locale" name="org.apache.struts.action.LOCALE" scope="session" />
		<!-- informacio -->
		<div id="info">
			<h2><bean:message name="subtitulo" /></h2>
			<p><bean:message name="texto" /></p>
			
			<br/>
			<br/>
			<p>
				<a href="javascript:history.back()">
					<bean:message key="mensaje.volver" />
				</a>
			</p>			
		</div>	
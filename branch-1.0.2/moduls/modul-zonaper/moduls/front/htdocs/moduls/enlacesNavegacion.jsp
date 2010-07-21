<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
		<!-- molla pa -->
		<ul id="mollaPa">		
		<logic:iterate id="line" name="enlaces"  type="java.util.Map">
			<logic:equal name="line" property="hasLink" value="true">
			<li><html:link action="<%= ( String ) line.get( "action") %>"><bean:write name="line" property="label"/> &gt; </html:link></li>
			</logic:equal>
			<logic:notEqual name="line" property="hasLink" value="true">
			<li><bean:write name="line" property="label"/></li>
			</logic:notEqual>
		</logic:iterate>	
		</ul>
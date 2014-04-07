<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:xhtml/>
<% int ti = 50; %>
<tr>
    <td class="labelo"><bean:message key="label.etiqueta"/>  (<a href="javascript:obrir('<html:rewrite page="/componente/ayudaFormateoHtml.jsp" />', 'FormateoHTML', 540, 400);"><b>HTML</b></a>)</td>
    <td class="input">
    	<html:text styleClass="text" tabindex="<%=Integer.toString(ti++)%>" property="traduccion.etiqueta" maxlength="256" />    	
    </td>
</tr>

<%@ page language="java"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ page import="org.ibit.rol.form.persistence.conector.MessageResult"%>
<%@ page import="org.ibit.rol.form.persistence.conector.FileResult"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<bean:define id="securePath" name="securePath" scope="request"/>
<html:xhtml/>
<script type="text/javascript">
<!--
function next() {
    document.finalizarForm.submit();
}
// -->
</script>

<p><bean:message bundle="mainMessages" key="resultados.label"/></p>

<ul>
<logic:iterate id="result" name="resultados" scope="request" indexId="i">
    <li>
        <% if (result instanceof MessageResult) { %>
            <% MessageResult messageResult = (MessageResult) result; %>
            <%=java.text.MessageFormat.format(messageResult.getMessage(), messageResult.getParameters())%>
        <% } else if (result instanceof FileResult) { %>
            <% FileResult fileResult = (FileResult) result; %>
            Document <%=fileResult.getName()%> (<%=fileResult.getContentType()%>)
            <html:link action='<%=securePath + "/fileresult?ID_INSTANCIA=" + request.getAttribute("ID_INSTANCIA")%>' 
                       paramId="result"
                       paramName="i"
                       target="_blank">
                <bean:message bundle="mainMessages" key="resultados.descargar" />
            </html:link>
        <% } %>
    </li>
</logic:iterate>
</ul>

<html:form action='<%=securePath + "/finalizar"%>'>
    <input type="hidden" name="ID_INSTANCIA" value="<%=request.getAttribute("ID_INSTANCIA")%>" />
</html:form>

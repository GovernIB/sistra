<%@ page language="java"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:xhtml/>
<script type="text/javascript">
<!--
function next() {
    document.finalizarForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = true;
    document.finalizarForm.elements['SAVE'].disabled = true;
    document.finalizarForm.submit();
    window.location.href='<html:rewrite page="/"/>';
}

function save() {
    document.finalizarForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
    document.finalizarForm.elements['SAVE'].disabled = false;
    document.finalizarForm.submit();
}
// -->
</script>

<p><bean:message key="penultima.label1"/></p>
<p><bean:message key="penultima.label2"/></p>

<html:form action="/finalizar" target="_blank">
    <input type="hidden" name="SAVE" disabled="disabled" value="true" />
    <input type="hidden" name="<%=Constants.CANCEL_PROPERTY%>" disabled="disabled" value="true" />
    <input type="hidden" name="ID_INSTANCIA" value="<%=request.getAttribute("ID_INSTANCIA")%>" />
</html:form>

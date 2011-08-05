<%@ page language="java"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<bean:define id="securePath" name="securePath" scope="request" type="java.lang.String"/>
<bean:define id="actionPath" name="actionPath" scope="request" type="java.lang.String"/>
<html:xhtml/>
<script type="text/javascript">
<!--
function back() {
    document.finalizarForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
    document.finalizarForm.elements['SAVE'].disabled = true;
    document.finalizarForm.elements['DISCARD'].disabled = true;
    document.finalizarForm.elements['PANTALLA_ANTERIOR'].disabled = true;
    document.finalizarForm.submit();
}

function backTo(pantalla) {
    document.finalizarForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
    document.finalizarForm.elements['SAVE'].disabled = true;
    document.finalizarForm.elements['DISCARD'].disabled = true;
    document.finalizarForm.elements['PANTALLA_ANTERIOR'].disabled = false;
    document.finalizarForm.elements['PANTALLA_ANTERIOR'].value = pantalla;
    document.finalizarForm.submit();
}

function next() {
    document.finalizarForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = true;
    document.finalizarForm.elements['SAVE'].disabled = true;
    document.finalizarForm.elements['DISCARD'].disabled = true;
    document.finalizarForm.elements['PANTALLA_ANTERIOR'].disabled = true;
    document.finalizarForm.submit();
}

function save() {
    document.finalizarForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
    document.finalizarForm.elements['SAVE'].disabled = false;
    document.finalizarForm.elements['DISCARD'].disabled = true;
    document.finalizarForm.elements['PANTALLA_ANTERIOR'].disabled = true;
    document.finalizarForm.submit();
}

function discard() {
    document.finalizarForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
    document.finalizarForm.elements['SAVE'].disabled = true;
    document.finalizarForm.elements['DISCARD'].disabled = false;
    document.finalizarForm.elements['PANTALLA_ANTERIOR'].disabled = true;
    document.finalizarForm.submit();
}
// -->
</script>

<p><bean:message bundle="mainMessages" key="penultima.label1"/></p>
<p><bean:message bundle="mainMessages" key="penultima.label2"/></p>

<html:form action='<%=securePath + actionPath%>'>
    <input type="hidden" name="SAVE" disabled="disabled" value="true" />
    <input type="hidden" name="DISCARD" disabled="disabled" value="true" />
    <input type="hidden" name="PANTALLA_ANTERIOR" disabled="disabled" value="" />
    <input type="hidden" name="<%=Constants.CANCEL_PROPERTY%>" disabled="disabled" value="true" />
    <input type="hidden" name="ID_INSTANCIA" value="<%=request.getAttribute("ID_INSTANCIA")%>" />
</html:form>

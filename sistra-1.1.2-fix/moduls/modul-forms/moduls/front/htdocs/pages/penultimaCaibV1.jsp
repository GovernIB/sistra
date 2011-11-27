<%@ page language="java"%>
<%@ page import="org.apache.struts.taglib.html.Constants"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<bean:define id="securePath" name="securePath" scope="request" type="java.lang.String"/>
<bean:define id="actionPath" name="actionPath" scope="request" type="java.lang.String"/>
<html:xhtml/>

<div style="display: none;">MODO FUNCIONAMIENTO: penultimaCaib<bean:write name="sufijoModoFuncionamiento"/></div>

<script type="text/javascript">
<!--    

// Prevenimos doble click en enlaces
var bDobleClick=false;
    
function back() {

	// Protegemos para que no pueda pulsarse antes de que la pagina este cargada
	if (!bLoaded) return;

	// Prevenimos doble click
	if (bDobleClick) return;
	bDobleClick=true;
	
    document.finalizarForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
    document.finalizarForm.elements['SAVE'].disabled = true;
    document.finalizarForm.elements['DISCARD'].disabled = true;
    document.finalizarForm.elements['PANTALLA_ANTERIOR'].disabled = true;
    document.finalizarForm.submit();
}

function backTo(pantalla) {

	// Protegemos para que no pueda pulsarse antes de que la pagina este cargada
	if (!bLoaded) return;

	// Prevenimos doble click
	if (bDobleClick) return;
	bDobleClick=true;
	
    document.finalizarForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
    document.finalizarForm.elements['SAVE'].disabled = true;
    document.finalizarForm.elements['DISCARD'].disabled = true;
    document.finalizarForm.elements['PANTALLA_ANTERIOR'].disabled = false;
    document.finalizarForm.elements['PANTALLA_ANTERIOR'].value = pantalla;
    document.finalizarForm.submit();
}

function next() {

	// Protegemos para que no pueda pulsarse antes de que la pagina este cargada
	if (!bLoaded) return;
	
	// Prevenimos doble click
	if (bDobleClick) return;
	bDobleClick=true;
	
    document.finalizarForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = true;
    document.finalizarForm.elements['SAVE'].disabled = true;
    document.finalizarForm.elements['DISCARD'].disabled = true;
    document.finalizarForm.elements['PANTALLA_ANTERIOR'].disabled = true;
    document.finalizarForm.submit();
}

function save() {

	// Protegemos para que no pueda pulsarse antes de que la pagina este cargada
	if (!bLoaded) return;
	
	// Prevenimos doble click
	if (bDobleClick) return;
	bDobleClick=true;
	
    document.finalizarForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
    document.finalizarForm.elements['SAVE'].disabled = false;
    document.finalizarForm.elements['DISCARD'].disabled = true;
    document.finalizarForm.elements['PANTALLA_ANTERIOR'].disabled = true;
    document.finalizarForm.submit();
}

function discard() {

	// Protegemos para que no pueda pulsarse antes de que la pagina este cargada
	if (!bLoaded) return;
	
	// Prevenimos doble click
	if (bDobleClick) return;
	bDobleClick=true;
	
    document.finalizarForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
    document.finalizarForm.elements['SAVE'].disabled = true;
    document.finalizarForm.elements['DISCARD'].disabled = false;
    document.finalizarForm.elements['PANTALLA_ANTERIOR'].disabled = true;
    document.finalizarForm.submit();
}
// -->
</script>

<logic:empty name="pantallaFinTexto">
	<p><bean:message bundle="caibMessages" key="penultima.label1"/></p>
	<p><bean:message bundle="caibMessages" key="penultima.label2"/></p>
</logic:empty>
<logic:notEmpty name="pantallaFinTexto">
	<p><bean:write name="pantallaFinTexto" filter="false"/></p>
</logic:notEmpty>


<html:form action='<%=securePath + actionPath%>'>
    <input type="hidden" name="SAVE" disabled="disabled" value="true" />
    <input type="hidden" name="DISCARD" disabled="disabled" value="true" />
    <input type="hidden" name="PANTALLA_ANTERIOR" disabled="disabled" value="" />
    <input type="hidden" name="<%=Constants.CANCEL_PROPERTY%>" disabled="disabled" value="true" />
    <input type="hidden" name="ID_INSTANCIA" value="<%=request.getAttribute("ID_INSTANCIA")%>" />
</html:form>

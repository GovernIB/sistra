<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<html:xhtml/>
<bean:define id="securePath" name="securePath" scope="request"/>
<bean:define id="idInstancia" name="ID_INSTANCIA" scope="request"/>
<bean:define id="campo" name="campo" type="org.ibit.rol.form.model.FileBox"/>
<script type="text/javascript">
<!--
    // resetear campo.
    parent.anyadirAnexo('<%=campo.getNombreLogico()%>', '', '', 0);
// -->
</script>
<nested:root name="campo">
    <html:form action='<%=securePath + "/addAnexo?ID_INSTANCIA=" + idInstancia%>' enctype="multipart/form-data">
        <input type="hidden" name="nombre" value="<%=campo.getNombreLogico()%>" />
        <nested:write property="traduccion.nombre" filter="false"/><nested:notEqual property="traduccion.nombre" value="&nbsp;">:</nested:notEqual>
        <html:file property="fichero"
                onchange="this.form.submit()"
                disabled="<%=request.getParameter("disabled") != null%>"/>
    </html:form>
</nested:root>
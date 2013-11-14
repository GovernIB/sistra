<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:xhtml/>
<bean:define id="securePath" name="securePath" scope="request"/>
<bean:define id="campo" name="campo" type="org.ibit.rol.form.model.FileBox"/>
<bean:define id="anexo" name="anexo" type="org.ibit.rol.form.model.Anexo"/>
<script type="text/javascript">
<!--
    function borrar() {
        window.location = '<html:rewrite page='<%=securePath + "/delAnexo.do"%>' paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" />&nombre=<%=campo.getNombreLogico()%>';
    }

    // resetear campo.
    parent.anyadirAnexo('<%=campo.getNombreLogico()%>', '<%=anexo.getName()%>', '<%=anexo.getContentType()%>', <%=anexo.getData().length%>);
// -->
</script>
<bean:write name="campo" property="traduccion.nombre" filter="false"/><nested:notEqual property="traduccion.nombre" value="&nbsp;">:</nested:notEqual>
<bean:write name="anexo" property="name"/> (<bean:write name="anexo" property="contentType"/>)
<input
    type="button"
    name="<%=campo.getNombreLogico()%>_del"
    value="X"
    onclick="borrar()"
    <% if (request.getParameter("disabled") != null) {%>disabled="disabled"<% } %>
/>

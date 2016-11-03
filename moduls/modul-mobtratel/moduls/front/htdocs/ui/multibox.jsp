<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<html:xhtml/>
<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.TextBox"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    <% boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo())); %>
    <% boolean disabled = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionDependencia())); %>
    <nested:write property="traduccion.nombre"/>:<br />
    <html:hidden property="<%=nombre%>" />
    <% if (!autocalculo) { %>
    <input
        type="text"
        name="<%=nombre%>_feed"
        class="frm"
        style="width: 200px"
        <%=disabled?"disabled='disabled'":""%>
        onfocus="setAyuda(<%=campo.getOrden()%>)"
        tabindex="<%=campo.getOrden()+1%>"
    />
    <input
        type="button"
        name="<%=nombre%>_add"
        value="&gt;"
        <%=disabled?"disabled='disabled'":""%>
        onclick="this.form.<%=nombre%>_l.options[this.form.<%=nombre%>_l.length] = new Option(this.form.<%=nombre%>_feed.value); updateMulti(this.form.<%=nombre%>_l, this.form.<%=nombre%>); this.form.<%=nombre%>_feed.value = ''"
        onfocus="setAyuda(<%=campo.getOrden()%>)"
        tabindex="<%=campo.getOrden()+1%>"
    /><br />
    <% } %>
    <select
        name="<%=nombre%>_l"
        size="5"
        style="width: 200px"
        class='<%=autocalculo ? "frmro" : "frm"%>'
        <%=disabled?"disabled='disabled'":""%>
        onfocus="setAyuda(<%=campo.getOrden()%>)"
        tabindex="<%=(autocalculo) ? 0 : campo.getOrden()+1%>"
    >
        <option>fake</option>
    </select>
    <% if (!autocalculo) { %>
    <input
        type="button"
        name="<%=nombre%>_del"
        value="X"
        <%=disabled?"disabled='disabled'":""%>
        onclick="this.form.<%=nombre%>_l.options[this.form.<%=nombre%>_l.selectedIndex] = null; updateMulti(this.form.<%=nombre%>_l, this.form.<%=nombre%>)"
        onfocus="setAyuda(<%=campo.getOrden()%>)"
        tabindex="<%=campo.getOrden()%>"
    />
    <% } %>
    <br />
    <script type="text/javascript">
    <!--
    saveMulti(document.pantallaForm.<%=nombre%>_l, document.pantallaForm.<%=nombre%>);
    // -->
    </script>
</nested:root>
<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:xhtml/>

<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.TextBox"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    <% boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo())); %>
    <% boolean disabled = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionDependencia())); %>
		<!-- INDRA: nuevo span para la etiqueta del campo -->
    <span class="formEtiqueta"><nested:write property="traduccion.nombre" filter="false"/><nested:notEqual property="traduccion.nombre" value="&nbsp;">:</nested:notEqual></span>
		<!-- fin INDRA -->
		<!-- INDRA: nuevo span para un conjunto de ítems en forma de listado colocados al lado de la etiqueta -->
		<span class="formListado">
		<!-- fin INDRA -->
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
		<!-- INDRA: cerramos el span del listado -->
		</span>
		<!-- fin INDRA -->
    <br />
    <script type="text/javascript">
    <!--
    saveMulti(document.pantallaForm.<%=nombre%>_l, document.pantallaForm.<%=nombre%>);
    // -->
    </script>
		<!-- INDRA: nuevo span para la separación entre etiqueta/campo y la siguiente -->
		<span class="formSep"></span>
		<!-- fin INDRA -->
</nested:root>
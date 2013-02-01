<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<script type="text/javascript">
    <!--
          function viewAyuda() {
            var url = '<html:rewrite page="/paleta/textbox/ayudaMascara.jsp" />';
            obrir(url, "Edicion", 540, 400);
         }
   //-->
</script>
<% int ti = 1; %>
<html:hidden property="idPaleta" />
<tr>
    <td class="labelo"><bean:message key="componente.nombreLogico"/></td>
    <td class="input"><html:text styleClass="text" tabindex="<%=Integer.toString(ti++)%>" property="values.nombreLogico" maxlength="128" /></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.nombrelogico"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.posicion"/></td>
    <td class="input">
    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.posicion">
        <html:option value="0"><bean:message key="componente.posicion.0" /></html:option>
        <html:option value="1"><bean:message key="componente.posicion.1" /></html:option>
    </html:select>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.posicion"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="textbox.filas"/></td>
    <td class="input"><html:text styleClass="t30" tabindex="<%=Integer.toString(ti++)%>" property="values.filas" maxlength="3" /></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="textbox.columnas"/></td>
    <td class="input"><html:text styleClass="t30" tabindex="<%=Integer.toString(ti++)%>" property="values.columnas" maxlength="3" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.multilinea"/></td>
    <td class="input"><html:checkbox styleClass="check" tabindex="<%=Integer.toString(ti++)%>" property="values.multilinea" /></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.multilinea"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.pdf"/></td>
    <td class="input"><html:text styleClass="text" tabindex="<%=Integer.toString(ti++)%>" property="values.etiquetaPDF" maxlength="128" /></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.etiquetaPDF"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.tipovalor"/></td>
    <td class="input">
        <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.tipoValor" >
            <html:option value="java.lang.String"><bean:message key="componente.java.lang.String" /></html:option>
            <html:option value="java.lang.Integer"><bean:message key="componente.java.lang.Integer" /></html:option>
            <html:option value="java.lang.Float"><bean:message key="componente.java.lang.Float" /></html:option>
        </html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="textbox.patron"/></td>
    <td class="input">
        <html:select tabindex="<%=Integer.toString(ti++)%>" property="idPatron" >
            <html:option value="0">- <bean:message key="componente.patron.no" /> -</html:option>
            <html:options collection="patronOptions"
                            property="id"
                       labelProperty="nombre"
            />
        </html:select>
    </td>
</tr>
<tiles:insert page="/moduls/editarExpresiones.jsp">
    <tiles:put name="tabindex" value="<%=new Integer(ti)%>" />
</tiles:insert>
<% ti += 5; %>
<script type="text/javascript">
     <!--
     function switchValidaciones() {
        swichDisplay('trShowMask');
        swichDisplay('trMask');
        swichDisplay('trHideMask');
     }
     // -->
</script>
<tr id="trShowMask">
    <td class="label" colspan="2">
        <button class="buttonl" type="button" onclick="javascript:switchValidaciones()">
            <bean:message key="boton.showValidaciones"/>
        </button>
    </td>
</tr>
<tr id="trMask" style="display: none;">
    <td class="label"><bean:message key="textbox.mascaras"/></td>
    <td class="input">
        <table class="marcd">
            <logic:iterate id="validacion" name="textboxForm" property="validacion" indexId="index1">
            <tr>
                <bean:define id="etiqueta" name="validacion" property="nombre" type="java.lang.String"/>
                <td class="label"><bean:message key="<%=etiqueta%>"/></td>
                <td>
                    <html:checkbox styleClass="check" name="validacion" property="activo" indexed="true" tabindex="<%=Integer.toString(ti++)%>" />
                    <logic:notEmpty name="validacion" property="valores">
                        <logic:iterate id="valores" name="validacion" property="valores" indexId="index2">
                            <logic:notEmpty name="validacion"  property='<%="valores[" + index2 + "]"%>'>
                                <bean:define id="content" name="validacion" property='<%="valores[" + index2 + "]"%>' />
                                <input type="text" class="t50" name='<%="validacion["+ index1 +"].valores["+ index2 +"]"%>' value='<%=content%>' tabindex="<%=Integer.toString(ti++)%>" />
                            </logic:notEmpty>
                            <logic:empty name="validacion" property='<%="valores[" + index2 + "]"%>'>
                                <input type="text" class="t50" name='<%="validacion["+ index1 +"].valores["+ index2 +"]"%>' tabindex="<%=Integer.toString(ti++)%>" />
                            </logic:empty>
                        </logic:iterate>
                    </logic:notEmpty>
                </td>
            </tr>
            </logic:iterate>
            <tr>
            <td>
                <input type="button" class="buttond" value='<bean:message key="boton.ayuda.mascara" />' onclick="viewAyuda()" />
            </td>
            </tr>
        </table>
    </td>
</tr>
<tr id="trHideMask" style="display: none;">
    <td class="label" colspan="2">
        <button class="buttonl" type="button" onclick="javascript:switchValidaciones()">
            <bean:message key="boton.hideValidaciones"/>
        </button>
    </td>
</tr>
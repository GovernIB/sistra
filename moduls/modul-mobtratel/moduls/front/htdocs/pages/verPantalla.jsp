<%@ page language="java"%>
<%@ page import="org.ibit.rol.form.model.TextBox,
                 org.ibit.rol.form.model.ComboBox,
                 org.ibit.rol.form.model.RadioButton,
                 org.ibit.rol.form.model.CheckBox,
                 org.ibit.rol.form.model.ListBox,
                 org.ibit.rol.form.model.Label,
                 org.ibit.rol.form.model.Campo,
                 org.apache.struts.taglib.html.Constants,
                 org.apache.struts.Globals,
                 org.ibit.rol.form.front.util.JSUtil,
                 org.ibit.rol.form.persistence.util.ScriptUtil"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<html:xhtml/>
<script src="<html:rewrite page='/staticJs.jsp'/>" type="text/javascript"></script>
<script type="text/javascript">
<!--
// Definir objeto XMLHttpRequest
var xmlhttp=false;
/*@cc_on @*/
/*@if (@_jscript_version >= 5)
try {
    xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
} catch (e) {
    try {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    } catch (E) {
        xmlhttp = false;
    }
}
@end @*/
if (!xmlhttp && typeof XMLHttpRequest!='undefined') {
    xmlhttp = new XMLHttpRequest();
}

// Funcion para llamar autocalculo de un campo en el servidor.
function autocalculo(fieldName, urlParams) {
    var url = "<html:rewrite page="/expresion/autocalculo.do" />";
    // Afegir camps formulari.
    xmlhttp.open("POST", url, false);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.send("ID_INSTANCIA=<%=request.getAttribute("ID_INSTANCIA")%>&fieldName=" + fieldName + "&" + urlParams);
    return xmlhttp.responseText;
}

// Funcion para llamar dependencia de un campo en el servidor.
function dependencia(fieldName, urlParams) {
    var url = "<html:rewrite page="/expresion/dependencia.do" />";
    // Afegir camps formulari.
    xmlhttp.open("POST", url, false);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.send("ID_INSTANCIA=<%=request.getAttribute("ID_INSTANCIA")%>&fieldName=" + fieldName + "&" + urlParams);
    resposta = xmlhttp.responseText;
    if (resposta == 'true')
        return true;
    else
        return false;
}

// Funcion para llamar calculo de valores posibles de un campo en el servidor
function valoresPosibles(fieldName, urlParams) {
    var url = "<html:rewrite page="/expresion/valores.do" />";
    // Afegir camps formulari.
    xmlhttp.open("POST", url, false);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.send("ID_INSTANCIA=<%=request.getAttribute("ID_INSTANCIA")%>&fieldName=" + fieldName + "&" + urlParams);
    var resposta = new Function("return "+xmlhttp.responseText)();
    return resposta;
}

// Funciones de utilidad.
function esNumero(valor, alt) {
    if (isNaN(valor)) {
        return alt;
    } else {
        return valor;
    }
}

function existe(valor, alt) {
    if (valor == undefined || valor == null) {
        return alt;
    } else {
        return valor;
    }
}

// Definición de los valores de pantallas anteriores
<bean:define id="datosAnteriores" name="datosAnteriores" type="java.util.Map"/>
<%=JSUtil.declareVarMap(datosAnteriores)%>
// Fin de los valores de pantallas anteriores.

// fieldName Nombre del campo modificado (sin f_)
function onFieldChange(form, fieldName) {

    // Valores que han sido modificados (el campo modificado + los autocalculados)
    var modificados = new Array();
    modificados.push("f_" + fieldName);

    // Parametros que usaremos para hacer peticiones con todos los valores del formulario
    var urlParams = '';

    // Definicion de los valores de los campos de esta pantalla
<logic:iterate id="campo" name="pantalla" property="campos" type="org.ibit.rol.form.model.Campo">
    <bean:define id="nombre" name="campo" property="nombreLogico"/>
    <% if (campo instanceof ComboBox)  { %>
    var f_<%=nombre%>;
    var f_<%=nombre%>_text;
    if (form.<%=nombre%>.selectedIndex >= 0) {
        f_<%=nombre%> = form.<%=nombre%>.options[form.<%=nombre%>.selectedIndex].value;
        f_<%=nombre%>_text = form.<%=nombre%>.options[form.<%=nombre%>.selectedIndex].text;
         urlParams += '&<%=nombre%>=' + f_<%=nombre%>;
    }
    <% } else if (campo instanceof CheckBox) { %>
    var f_<%=nombre%> = form.<%=nombre%>.checked;
     urlParams += '&<%=nombre%>=' + f_<%=nombre%>;
    <% } else if (campo instanceof ListBox) { %>
    var f_<%=nombre%> = new Array();
    var f_<%=nombre%>_text = new Array();
    for (i = 0; i < form.<%=nombre%>.length; i++) {
        if (form.<%=nombre%>.options[i].selected) {
            f_<%=nombre%>.push(form.<%=nombre%>.options[i].value);
             urlParams += '&<%=nombre%>=' + f_<%=nombre%>;
            f_<%=nombre%>_text.push(form.<%=nombre%>.options[i].text);
        }
    }
    <% } else if (campo instanceof RadioButton) { %>
    var f_<%=nombre%>;
    var f_<%=nombre%>_text;
    for (i = 0; i < form.<%=nombre%>.length; i++) {
        if (form.<%=nombre%>[i].checked) {
            f_<%=nombre%> = form.<%=nombre%>[i].value;
             urlParams += '&<%=nombre%>=' + f_<%=nombre%>;
            f_<%=nombre%>_text = form.<%=nombre%>[i].text;
            continue;
        }
    }
    <% } else if (campo instanceof TextBox) { %>
        <% if (((TextBox) campo).isMultilinea()) { %>
        var f_<%=nombre%> = new Array();
        for (i = 0; i < form.<%=nombre%>_l.length; i++) {
            f_<%=nombre%>.push(form.<%=nombre%>_l.options[i].text);
             urlParams += '&<%=nombre%>=' + f_<%=nombre%>;
        }
        <% } else if (campo.isNatural()) { %>
        var f_<%=nombre%> = parseInt(form.<%=nombre%>.value);
         urlParams += '&<%=nombre%>=' + f_<%=nombre%>;
        <% } else if (campo.isReal()) { %>
        var f_<%=nombre%> = parseFloat(form.<%=nombre%>.value);
         urlParams += '&<%=nombre%>=' + f_<%=nombre%>;
        <% } else { %>
        var f_<%=nombre%> = form.<%=nombre%>.value;
         urlParams += '&<%=nombre%>=' + f_<%=nombre%>;
        <% } %>
    <% } else { %>
    // En teoria nunca se llega aqui.
    var f_<%=nombre%> = form.<%=nombre%>.value;
     urlParams += '&<%=nombre%>=' + f_<%=nombre%>;
    <% } %>
</logic:iterate>

// Expresiones de autocalculo i actualización de los campos.
<logic:iterate id="campo" name="pantalla" property="campos" type="org.ibit.rol.form.model.Campo">
    <bean:define id="nombre" name="campo" property="nombreLogico"/>
    <logic:notEmpty name="campo" property="expresionAutocalculo">
    <% String deps = ScriptUtil.dependencias(campo.getExpresionAutocalculo()); %>
    if (depends("<%=deps%>", modificados) || fieldName == null) {
        modificados.push("f_<%=nombre%>");
        f_<%=nombre%> = autocalculo('<%=nombre%>', urlParams);
        <% if (campo.isReal()) { %>
        if (isNaN(f_<%=nombre%>)) {
            f_<%=nombre%> = 0.0;
        } else {
            f_<%=nombre%> = Math.round(f_<%=nombre%> * 1000) / 1000;
        }
        <% } else if (campo.isNatural()) { %>
        if (isNaN(f_<%=nombre%>)) {
            f_<%=nombre%> = 0;
        } else {
            f_<%=nombre%> = parseInt(f_<%=nombre%>);
        }
        <% } %>

        <% if (campo instanceof ComboBox)  { %>
        selectOption(form.<%=nombre%>, f_<%=nombre%>);
        <% } else if (campo instanceof RadioButton) { %>
        selectRadio(form.<%=nombre%>, f_<%=nombre%>);
        <% } else if (campo instanceof ListBox) { %>
        selectOptions(form.<%=nombre%>, f_<%=nombre%>);
        <% } else if (campo instanceof CheckBox) { %>
        form.<%=nombre%>.checked = f_<%=nombre%>;
        <% } else if (campo instanceof TextBox) { %>
            <% if (((TextBox) campo).isMultilinea()) { %>
            form.<%=nombre%>.value = f_<%=nombre%>.join('\r\n');
            saveMulti(form.<%=nombre%>_l, form.<%=nombre%>);
            <% } else { %>
            form.<%=nombre%>.value = f_<%=nombre%>;
            <% } %>
        <% } else { %>
        // En teoria nunca llega aqui.
        form.<%=nombre%>.value = f_<%=nombre%>;
        <% } %>
     }
    </logic:notEmpty>
</logic:iterate>

// Expresiones valores posibles.
<logic:iterate id="campo" name="pantalla" property="campos" type="org.ibit.rol.form.model.Campo">
    <bean:define id="nombre" name="campo" property="nombreLogico"/>
    <logic:notEmpty name="campo" property="expresionValoresPosibles">
    <% if (campo.isIndexed()) { %>
        <% String deps = ScriptUtil.dependencias(campo.getExpresionValoresPosibles()); %>
        if (depends("<%=deps%>", modificados)) {
            refillSelect(form.<%=nombre%>, valoresPosibles('<%=nombre%>', urlParams) );
            <% if (campo instanceof ComboBox)  { %>
            selectOption(form.<%=nombre%>, f_<%=nombre%>);
            <% } else if (campo instanceof RadioButton) { %>
            selectRadio(form.<%=nombre%>, f_<%=nombre%>);
            <% } else if (campo instanceof ListBox) { %>
            selectOptions(form.<%=nombre%>, f_<%=nombre%>);
            <% } %>
        }
    <% } %>
    </logic:notEmpty>
</logic:iterate>

// Calculo de dependencias
<logic:iterate id="campo" name="pantalla" property="campos" type="org.ibit.rol.form.model.Campo">
    <bean:define id="nombre" name="campo" property="nombreLogico"/>
    <logic:notEmpty name="campo" property="expresionDependencia">
    <%-- Amb la següent executam el codi al servidor, amb la segona, al client. Configurable? --%>
    <% String deps = ScriptUtil.dependencias(campo.getExpresionDependencia()); %>
    if (depends("<%=deps%>", modificados) || fieldName == null) {
        <%-- form.<%=nombre%>.disabled = !(dependencia('<%=nombre%>', urlParams)); --%>
        form.<%=nombre%>.disabled = !(<%=JSUtil.escapeJsExpression(campo.getExpresionDependencia())%>);
        <% if (campo instanceof TextBox) { %>
            <% if (((TextBox) campo).isMultilinea()) { %>
            form.<%=nombre%>_feed.disabled = form.<%=nombre%>.disabled;
            form.<%=nombre%>_l.disabled = form.<%=nombre%>.disabled;
            form.<%=nombre%>_add.disabled = form.<%=nombre%>.disabled;
            form.<%=nombre%>_del.disabled = form.<%=nombre%>.disabled;
            <% } %>
        <% } %>
    }
    </logic:notEmpty>
</logic:iterate>
}
// -->
</script>
<script type="text/javascript">
<!--
function back() {
    document.pantallaForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
    document.pantallaForm.elements['SAVE'].disabled = true;
    document.pantallaForm.elements['PANTALLA_ANTERIOR'].disabled = true;
    document.pantallaForm.submit();
}

function backTo(pantalla) {
    document.pantallaForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
    document.pantallaForm.elements['SAVE'].disabled = true;
    document.pantallaForm.elements['PANTALLA_ANTERIOR'].disabled = false;
    document.pantallaForm.elements['PANTALLA_ANTERIOR'].value = pantalla;
    document.pantallaForm.submit();
}

function next() {
    document.pantallaForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = true;
    document.pantallaForm.elements['SAVE'].disabled = true;
    document.pantallaForm.elements['PANTALLA_ANTERIOR'].disabled = true;
    if (validatePantallaForm(document.pantallaForm)) {
        document.pantallaForm.submit();
    }
}

function save() {
    document.pantallaForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
    document.pantallaForm.elements['SAVE'].disabled = false;
    document.pantallaForm.elements['PANTALLA_ANTERIOR'].disabled = true;
    document.pantallaForm.submit();
}
// -->
</script>
<script type="text/javascript">
<!--
var ayudascampo = new Array();
var nombrescampo = new Array();

function setAyuda(i) {
    var ayuda = document.getElementById("ayuda_campo");
    ayuda.innerHTML = ayudascampo[i];
    ayuda.style.display = 'block';

    var nombre = document.getElementById("nombre_campo");
    nombre.innerHTML = nombrescampo[i];
    nombre.style.display = 'block';
}

function unsetAyuda() {
    var ayuda = document.getElementById("ayuda_campo");
    ayuda.innerHTML = '';
    ayuda.style.display = 'none';

    var nombre = document.getElementById("nombre_campo");
    nombre.innerHTML = '';
    nombre.style.display = 'none';
}


// -->
</script>
<html:errors />
<html:form action="/procesar" onsubmit="return validadatePantallaForm(this)">
    <input type="hidden" name="<%=Constants.CANCEL_PROPERTY%>" disabled="disabled" value="true" />
    <input type="hidden" name="SAVE" disabled="disabled" value="true" />
    <input type="hidden" name="PANTALLA_ANTERIOR" disabled="disabled" value="" />
    <input type="hidden" name="ID_INSTANCIA" value="<%=request.getAttribute("ID_INSTANCIA")%>" />
    <p>
    <nested:iterate id="comp" name="pantalla" property="componentes" indexId="ind" >
        <logic:greaterThan name="ind" value="0">
            <logic:equal name="comp" property="posicion" value="0"></p><p></logic:equal>
            <logic:equal name="comp" property="posicion" value="1">&nbsp;</logic:equal>
        </logic:greaterThan>
        <% if (comp instanceof Campo) { %>
            <script type="text/javascript">
            <!--
            ayudascampo[<%=ind%>] = "<nested:write property='traduccion.ayuda'/>";
            nombrescampo[<%=ind%>] = "<nested:write property="traduccion.nombre"/>";
            //-->
            </script>
        <span onmouseover="setAyuda(<%=ind%>)" style="<%=((Campo)comp).isOculto()?"display: none;":""%>">
        <%
            if (comp instanceof TextBox) {
                if (((TextBox) comp).isMultilinea()) {
                    %><jsp:include page="/ui/multibox.jsp" /><%
                } else {
                    %><jsp:include page="/ui/textbox.jsp" /><%
                }
            } else if (comp instanceof ComboBox) {
                %><jsp:include page="/ui/combobox.jsp" /><%
            } else if (comp instanceof RadioButton) {
                %><jsp:include page="/ui/radiobutton.jsp" /><%
            } else if (comp instanceof CheckBox) {
                %><jsp:include page="/ui/checkbox.jsp" /><%
            } else if (comp instanceof ListBox) {
                %><jsp:include page="/ui/listbox.jsp" /><%
            }
        %></span>
        <%
            } else if (comp instanceof Label) {
            %><span class="<nested:write property='estilo'/>"><nested:write property="traduccion.etiqueta"/></span><%
            }
        %>
    </nested:iterate>
    </p>
</html:form>

<script type="text/javascript">
<!--
    onFieldChange(document.pantallaForm, null);
// -->
</script>

<%-- Això és una xapuça --%>
<% pageContext.removeAttribute(Globals.XHTML_KEY);%>
<html:javascript formName="pantallaForm" staticJavascript="false" htmlComment="true" cdata="false" />
<% pageContext.setAttribute(Globals.XHTML_KEY, "true");%>

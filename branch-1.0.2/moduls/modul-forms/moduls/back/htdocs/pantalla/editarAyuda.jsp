<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib uri="back" prefix="back" %>

<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="ayuda.title"/></title>
   <meta http-equiv="Content-type" content='text/html; charset="ISO-8859-1"' />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
   <script type="text/javascript">
   <!--
        function validar(form) {
            return validateAyudaForm(form);
        }
   //-->
   </script>
</head>

<body class="ventana">

<bean:parameter id="idPerfil" name="idPerfil" value="0"/>

<table class="nomarcd">
    <tr><td class="titulo"><bean:message key="ayuda.editar" /></td></tr>
    <tr><td class="subtitulo"><bean:message key="ayuda.subtitulo" /></td></tr>
</table>
<br />

<table class="marcd">
    <html:form action="/back/ayuda/editar" >
    <html:hidden property="lang" />
    <tr>
        <td class="label"><bean:message key="ayuda.descripcion1" /></td>
        <td class="input"><html:text styleClass="text" property="traduccion.descripcionCorta" maxlength="1024" tabindex="1" /></td>
    </tr>
    <tr>
        <td class="label"><bean:message key="ayuda.descripcion2" /></td>
        <td class="input"><html:textarea property="traduccion.descripcionLarga" tabindex="2"></html:textarea></td>
    </tr>
    <tr>
        <td class="label"><bean:message key="ayuda.web" /></td>
        <td class="input">
            <html:text tabindex="3" styleClass="url" property="traduccion.urlWeb" maxlength="1024" />
            <html:button  property="boto"   styleClass="button" onclick="obrirTest(this.form.elements['traduccion.urlWeb'].value)">
               <bean:message key="boton.test"/>
            </html:button>
        </td>
    </tr>
    <tr>
        <td class="labela" colspan="3"><bean:message key="ayuda.urlweb"/></td>
    </tr>
    <tr>
        <td class="label"><bean:message key="ayuda.sonido" /></td>
        <td class="input">
            <html:text tabindex="4" styleClass="url" property="traduccion.urlSonido" maxlength="1024" />
            <html:button  property="boto"   styleClass="button" onclick="obrirTest(this.form.elements['traduccion.urlSonido'].value)">
               <bean:message key="boton.test"/>
            </html:button>
        </td>
    </tr>
    <tr>
        <td class="labela" colspan="3"><bean:message key="ayuda.urlsonido"/></td>
    </tr>
    <tr>
        <td class="label"><bean:message key="ayuda.video" /></td>
        <td class="input">
            <html:text tabindex="5" styleClass="url" property="traduccion.urlVideo" maxlength="1024" />
            <html:button  property="boto"   styleClass="button" onclick="obrirTest(this.form.elements['traduccion.urlVideo'].value)">
               <bean:message key="boton.test"/>
            </html:button>
        </td>
    </tr>
    <tr>
        <td class="labela" colspan="3"><bean:message key="ayuda.urlvideo"/></td>
    </tr>
</table>
<br />
<table class="nomarcd">
     <input type="hidden" name="idPerfil" value='<bean:write name="idPerfil" />' />
     <tr>
        <td align="left">
            <back:accio tipus="modificacio" styleClass="button" onclick="return validar(this.form);" />
            <html:reset styleClass="button"><bean:message key="boton.reiniciar" /></html:reset>
        </td>
        <td align="right">
            <html:cancel styleClass="button" onclick="window.close()"><bean:message key="boton.cancel" /></html:cancel>
        </td>
     </tr>
    </html:form>
</table>

<bean:define id="pagina" name="ayudaForm" property="page" type="java.lang.Integer"/>

<!-- XAPUÇA -->
<% pageContext.removeAttribute(Globals.XHTML_KEY);%>
<html:javascript
    formName="ayudaForm"
    dynamicJavascript="true"
    staticJavascript="false"
    page="<%=pagina.intValue()%>"
    htmlComment="true"
    cdata="false"
 />
<% pageContext.setAttribute(Globals.XHTML_KEY, "true");%>

</body>
</html:html>
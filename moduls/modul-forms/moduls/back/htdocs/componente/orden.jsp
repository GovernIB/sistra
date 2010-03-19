<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="componente.titulo"/></title>
   <meta http-equiv="Content-type" content='text/html; charset="ISO-8859-15"' />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script type="text/javascript">
   <!--
        <logic:present name="reloadMenu">
            parent.Menu.location.reload(true);
        </logic:present>
   //-->
   </script>
</head>

<body class="ventana">

<bean:define id="idPantalla" name="idPantalla" />
<bean:size id="numOptions" name="componenteOptions" />

<!-- Muestra el arbol de navegación en la parte superior de la pantalla-->
<logic:present name="formulario">
    <p class="path">
    <i><bean:message key="formulario.path" /></i>: <b><html:link page="/back/formulario/seleccion.do" paramId="id" paramName="idFormulario"><bean:write name="formulario" /></html:link></b>
    <logic:present name="pantalla">
        &gt;&gt; <i><bean:message key="pantalla.path" /></i>: <b><html:link page="/back/pantalla/seleccion.do" paramId="id" paramName="idPantalla"><bean:write name="pantalla" /></html:link></b>
        <logic:present name="componente">
            &gt;&gt; <i><bean:message key="componente.path" /></i>: <b><html:link page="/back/componente/seleccion.do" paramId="id" paramName="idComponente"><bean:write name="componente" /></html:link></b>
            <logic:present name="valor">
                &gt;&gt; <i><bean:message key="valorposible.path" /></i>: <b><html:link page="/back/valorposible/seleccion.do" paramId="id" paramName="idValorPosible"><bean:write name="valor" /></html:link></b>
            </logic:present>
        </logic:present>
    </logic:present>
    </p>
</logic:present>

<br />
<table class="marc">
    <tr><td class="titulo"><bean:message key="componente.orden" /></td></tr>
    <tr><td class="subtitulo"><bean:message key="componente.orden.subtitulo" /></td></tr>
</table>
<br />
<logic:equal name="numOptions" value="0">
    <table class="marc">
      <tr><td class="alert"><bean:message key="componente.selec.vacio" /></td></tr>
    </table>
</logic:equal>
<logic:notEqual name="numOptions" value="0">
    <% Long codAnterior = null; %>
    <table class="marc">
        <logic:iterate  id="componente" name="componenteOptions" indexId="idComponente">
            <bean:define id="codComponente" name="componente" property="id" type="java.lang.Long" />
            <html:form action="/back/componente/cambiaorden" >
            <input type="hidden" name="idPantalla" value='<bean:write name="idPantalla" />' />
            <tr>
                <td align="center">
                    <%=idComponente.intValue() + 1%>
                </td>
                <td class="output" >
                    <bean:write name="componente" property="nombreLogico" />
                </td>
                <logic:notEqual name="idComponente" value="0">
                    <html:hidden property="codigo1" value="<%=codComponente.toString()%>" />
                    <html:hidden property="codigo2" value="<%=codAnterior.toString()%>" />
                    <td align="center">
                        <html:submit styleClass="button">
                            <bean:message key="boton.subir" />
                        </html:submit>
                    </td>
                </logic:notEqual>
                <logic:equal name="idComponente" value="0">
                    <td>&nbsp;<br />&nbsp;</td>
                </logic:equal>
            </tr>
           </html:form>
           <%
               if (idComponente != numOptions) {
                   codAnterior = codComponente;
               }
           %>
        </logic:iterate>
    </table>
</logic:notEqual>
<br />
<table class="nomarc">
    <html:form action="/back/componente/orden" >
    <input type="hidden" name="idPantalla" value='<bean:write name="idPantalla" />' />
     <tr><td align="right">
        <html:cancel styleClass="button" ><bean:message key="boton.acept" /></html:cancel>
     </td></tr>
    </html:form>
</table>
</body>
</html:html>
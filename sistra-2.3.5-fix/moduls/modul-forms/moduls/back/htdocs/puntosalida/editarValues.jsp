<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>


<tiles:importAttribute name="listaRelaciones" scope="page" />

<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="puntosalida.titulo"/></title>
   <meta http-equiv="Content-type" content='text/html; charset="ISO-8859-15"' />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
</head>

<body class="ventana">

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
    <tr><td class="titulo">
        <bean:message key="puntosalida.titulo"/>
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="puntosalida.subtitulo" /></td></tr>
</table>
<br />
<table class="marc">
<tr>
    <td class="labelo"><bean:message key="puntosalida.nombre"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" name="salida" property="punto.nombre" maxlength="128" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="puntosalida.implementacion"/></td>
    <td class="input"><html:text styleClass="text" tabindex="2" name="salida" property="punto.implementacion" maxlength="1024" /></td>
</tr>
</table>

<br />
<logic:iterate id="element" name="listaRelaciones">
    <tiles:insert beanName="element" flush="false" >
    </tiles:insert>
</logic:iterate>

</body>
</html:html>
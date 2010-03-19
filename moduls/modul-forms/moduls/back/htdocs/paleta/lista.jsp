<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="paleta.titulo"/></title>
   <meta http-equiv="Content-type" content='text/html; charset="ISO-8859-1"' />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
</head>
<body class="ventana">
<table class="nomarc">
    <tr><td class="titulo"><bean:message key="paleta.lista" /></td></tr>
</table>
<br />
<bean:size id="numOptions" name="paletaOptions" />

<logic:equal name="numOptions" value="0">
    <table class="marc">
      <tr><td class="alert"><bean:message key="paleta.selec.vacio" /></td></tr>
    </table>
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <table class="marc">
        <logic:iterate id="paleta" name="paletaOptions">
            <tr>
                <td class="outputd" width="90%">
                    <bean:write name="paleta" property="nombre" />
                </td>
                <td>
                    <bean:define id="urlEditar"><html:rewrite page="/back/paleta/componentes.do" paramId="idPaleta" paramName="paleta" paramProperty="id"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>

                </td>
            </tr>
        </logic:iterate>
    </table>
    <br />
    <table class="nomarc">
      <tr>
        <td align="left">
             <button class="button" type="buttond" onclick="javascript:history.back()">
                <bean:message key="boton.cancel" />
             </button>
        </td>
      </tr>
    </table>
</logic:notEqual>
</body>
</html:html>
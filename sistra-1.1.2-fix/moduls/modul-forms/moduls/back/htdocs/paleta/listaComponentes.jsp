<%@ page language="java"%>
<%@ page import="org.ibit.rol.form.back.util.ComponenteConfig"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:html locale="true" xhtml="true">
<tiles:useAttribute name="idPaleta" />
<tiles:useAttribute name="componenteOptions" />
<head>
   <title><bean:message key="paleta.titulo"/></title>
   <meta http-equiv="Content-type" content='text/html; charset="ISO-8859-1"' />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
</head>
<body class="ventana">

<br />
<table class="nomarc">
    <tr><td class="titulo"><bean:message key="componente.asociado" /></td></tr>
</table>
<br />

<logic:empty name="componenteOptions">
    <table class="marc">
      <tr><td class="alert"><bean:message key="componente.selec.vacio" /></td></tr>
    </table>
</logic:empty>

<logic:notEmpty name="componenteOptions">
    <table class="marc">
        <logic:iterate  id="componente" name="componenteOptions" >
            <logic:notEmpty name="componente">
            <tr>
                <td class="outputd" width="5%">
                    <bean:define id="tipus">componente.tipo.<%=ComponenteConfig.getTipo(componente)%></bean:define>
                    <bean:message name="tipus" />
                </td>
                <td class="outputd" width="80%" >
                    <bean:write name="componente" property="nombreLogico"/>
                    <logic:present name="componente" property="traduccion.nombre">
                        (<bean:write name="componente" property="traduccion.nombre" />)
                    </logic:present>
                    <logic:present name="componente" property="traduccion.etiqueta" >
                        (<bean:write name="componente" property="traduccion.etiqueta" />)
                    </logic:present>
                </td>
                <td>
                    <bean:define id="urlEditar"><html:rewrite page="/back/componente/guarda.do" paramId="id" paramName="componente" paramProperty="id"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>
                </td>
            </tr>
            </logic:notEmpty>
        </logic:iterate>
    </table>
</logic:notEmpty>

<table class="nomarc">
    <tr><td align="center"><bean:message key="componente.tipo.leyenda" /></td></tr>
</table>
<br />
<table class="nomarc">
  <tr>
    <td align="center">
        <button class="button" type="buttond" onclick="javascript:history.back()">
            <bean:message key="boton.cancel" />
        </button>
    </td>
  </tr>
</table>

</body>
</html:html>
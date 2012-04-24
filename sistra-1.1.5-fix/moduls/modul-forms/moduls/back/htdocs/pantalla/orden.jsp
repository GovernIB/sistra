<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="pantalla.title"/></title>
   <meta http-equiv="Content-type" content='text/html; charset="ISO-8859-1"' />
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

<bean:define id="idFormulario" name="idFormulario" />
<bean:size id="numOptions" name="pantallaOptions" />

<br />
<table class="marc">
    <tr><td class="titulo"><bean:message key="pantalla.orden" /></td></tr>
    <tr><td class="subtitulo"><bean:message key="pantalla.orden.subtitulo" /></td></tr>
</table>
<br />

<logic:equal name="numOptions" value="0">
    <table class="marc">
      <tr><td class="alert"><bean:message key="pantalla.selec.vacio" /></td></tr>
    </table>
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <% Long codAnterior = null; %>
    <table class="marc">
        <logic:iterate id="pantalla" name="pantallaOptions" indexId="idPantalla">
           <logic:empty name="pantalla" property="componenteListaElementos">
	            <bean:define id="codPantalla" name="pantalla" property="id" type="java.lang.Long" />
	            <html:form action="/back/pantalla/cambiaorden" >
	            <input type="hidden" name="idFormulario" value='<bean:write name="idFormulario" />' />
	            <tr>
	                <td align="center">
	                    <%=idPantalla.intValue() + 1%>
	                </td>
	                <td class="output" >
	                    <bean:write name="pantalla" property="nombre" />
	                    <logic:equal name="pantalla" property="inicial" value="true">
	                        (<bean:message key="pantalla.inicial" />)
	                    </logic:equal>
	                    <logic:equal name="pantalla" property="ultima" value="true">
	                        (<bean:message key="pantalla.ultima" />)
	                    </logic:equal>
	                </td>
	                <logic:notEqual name="idPantalla" value="0">
	                    <html:hidden property="codigo1" value="<%=codPantalla.toString()%>" />
	                    <html:hidden property="codigo2" value="<%=codAnterior.toString()%>" />
	                    <td align="center">
	                        <html:submit styleClass="button">
	                            <bean:message key="boton.subir" />
	                        </html:submit>
	                    </td>
	                </logic:notEqual>
	                <logic:equal name="idPantalla" value="0">
	                    <td>&nbsp;<br />&nbsp;</td>
	                </logic:equal>
	            </tr>
	           </html:form>
	           <%
	               if (idPantalla != numOptions) {
	                   codAnterior = codPantalla;
	               }
	           %>       
	        </logic:empty>    
        </logic:iterate>
    </table>
</logic:notEqual>
<br />
<table class="nomarc">
    <html:form action="/back/pantalla/orden" >
    <input type="hidden" name="idFormulario" value='<bean:write name="idFormulario" />' />
     <tr><td align="right">
        <html:cancel styleClass="button" ><bean:message key="boton.acept" /></html:cancel>
     </td></tr>
    </html:form>
</table>
</body>
</html:html>
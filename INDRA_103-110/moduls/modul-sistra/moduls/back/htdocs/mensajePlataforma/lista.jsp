<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="organo.titulo"/></title>
   <meta http-equiv="Content-type" content='text/html; charset="ISO-8859-1"' />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
   <script type="text/javascript">
   <!--
        <logic:present name="reloadMenu">
            parent.Menu.location.reload(true);
        </logic:present>
   //-->
   </script>
</head>

<body class="ventana">
<table class="marc">
    <tr><td class="titulo">
        <bean:message key="mensajePlataforma.asociada" />        
    </td></tr>    
</table>

<br />

<tiles:useAttribute name="mensajePlataformaOptions"/>
<bean:size id="numOptions" name="mensajePlataformaOptions" />

<logic:equal name="numOptions" value="0">
    <table class="marc">
      <tr>
      	<td class="alert"><bean:message key="mensajePlataforma.selec.vacio" /></td>      	
      </tr>
    </table>
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <table class="marc">
        <logic:iterate  id="mensajePlataforma" name="mensajePlataformaOptions" type="es.caib.sistra.model.MensajePlataforma">
            <tr>
                <td class="outputd" width="70%" >                	
                	<bean:message key="<%=\"mensajePlataforma.\"+mensajePlataforma.getIdentificador()%>"/>
                </td>
                <td class="alert">
		      		<logic:equal name="mensajePlataforma" property="activo" value="S">
		      			<bean:message key="mensajePlataforma.activo"/>
		      		</logic:equal>
		      	</td>
                <td align="right">
                    <bean:define id="urlEditar"><html:rewrite page="/back/mensajePlataforma/seleccion.do" paramId="codigo" paramName="mensajePlataforma" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>
                    <bean:define id="nombre" name="mensajePlataforma" property="identificador" type="java.lang.String"/>                    
                </td>
            </tr>
        </logic:iterate>
    </table>
</logic:notEqual>

<br />

    <table class="nomarc">
  <tr>
  <td align="right">    
  </td>
  </tr>
</table>
</body>
</html:html>
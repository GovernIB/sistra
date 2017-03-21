<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="urlArbol">
    <html:rewrite page="/arbolUnidades.do"/>
</bean:define>
<bean:define id="urlArbolServicios">
    <html:rewrite page="/arbolServicios.do"/>
</bean:define>
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="tramiteVersion.titulo"/></title>
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
   <script type="text/javascript">
     <!--
     function mostrarArbolUnidades(url) {
        obrir(url, "Arbol", 540, 400);
     }
     
     function mostrarArbolServicios(url) {
         //var url = '<html:rewrite page="/arbolServicios.do" />';
         obrir(url, "Arbol", 540, 400);
      }
     // -->
</script>
</head>

<body class="ventana">
<table class="marc">
    <tr><td class="titulo">
        <bean:message key="tramiteVersion.importar.titulo" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="tramiteVersion.importarPreview.subtitulo" /></td></tr>
</table>

<br />

<html:errors/>

<html:form action="/importar/xmlProcess" styleId="importarForm">
	<html:hidden property="codigoTramite"/>
	
	
	
<table class="marc">
    <tr>
	    <td class="labelo"><bean:message key="tramiteVersion.organoDestino"/></td>
	    <td class="input">
	    	<html:select property="organoDestino">
	   			<html:options collection="listaorganosdestino" property="CODIGO" labelProperty="DESCRIPCION" />
	    	</html:select>
	    	<input type="button" value="..."  class = "botonEditar" onclick="mostrarArbolServicios('<%=urlArbolServicios + "?id=organoDestino" %>');"/>
	    </td>
	</tr>
	<tr>
	    <td class="labelo"><bean:message key="tramiteVersion.unidadAdministrativa"/></td>
	    <td class="input">
	      	<html:select style="width:420px" property="unidadAdministrativa">
	   			<html:options collection="listaunidadesadministrativas" property="CODIGO" labelProperty="DESCRIPCION" />
	    	</html:select>
	        <input type="button" value="..."  class = "botonEditar" onclick="mostrarArbolUnidades('<%=urlArbol + "?id=unidadAdministrativa" %>');"/>
	    </td>
	</tr>
	<tr>
	    <td class="labelo"><bean:message key="tramiteVersion.registroOficina"/></td>
	    <td class="input">
	    	<html:select property="registroOficina">
	   			<html:options collection="listaoficinasregistro" property="CODIGO" labelProperty="DESCRIPCION" />
	    	</html:select>
	    </td>
	</tr>
	<tr>
	    <td class="labelo"><bean:message key="tramiteVersion.registroAsunto"/></td>
	    <td class="input">
	    	<html:select property="registroAsunto">
	   			<html:options collection="listatiposasunto" property="CODIGO" labelProperty="DESCRIPCION" />
	    	</html:select>
	    </td>
	</tr>
</table>
<br />
<table class="nomarc">
   <tr>
       <td align="left">
            <html:submit styleClass="button"><bean:message key="boton.importar" /></html:submit>
        </td>
    </tr>
</table>
</html:form>


</body>
</html:html>
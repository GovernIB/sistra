<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib uri="back" prefix="back"%>
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="ficheroCuaderno.titulo"/></title>
   <meta http-equiv="Content-type" content='text/html; charset="ISO-8859-1"' />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
   <script type="text/javascript">
   <!--
        <logic:present name="reloadMenu">
            parent.Menu.location.reload(true);
        </logic:present>
        
        function validar(form) 
        {
            return validateFicheroCuadernoForm(form);
        }
        
   //-->
   </script>
</head>

<body class="ventana">
<table class="marc">
    <tr><td class="titulo">
        <bean:message key="ficheroCuaderno.importar.titulo" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="ficheroCuaderno.importar.subtitulo" /></td></tr>
</table>

<br />

<html:errors/>

<logic:present name="errorKey">
<table class="marc">
    <tr><td class="alert"><bean:message name="errorKey" /></td></tr>
</table>
<br />
</logic:present>

<html:form action="/admin/ficheroCuaderno/editar" styleId="ficheroCuadernoForm" enctype="multipart/form-data">
	<html:hidden property="codigoFichero" />
	<html:hidden property="codigo"/>
	<html:hidden property="codigoCuaderno" />
	<table class="marc">
	<%--
	    <tr>
	        <td class="labelo"><bean:message key="ficheroCuaderno.tipo"/></td>
	        <td class="input">
	        	<bean:message key="cuadernoCarga.ficherosCuaderno.tipoT" /><html:radio property="tipo" value="T"/><br/>
	       		<bean:message key="cuadernoCarga.ficherosCuaderno.tipoF" /><html:radio property="tipo" value="F"/><br/> 
	       		<bean:message key="cuadernoCarga.ficherosCuaderno.tipoD" /><html:radio property="tipo" value="D"/>
	       	</td>
	    </tr>
  --%>	    
	    <tr>
	        <td class="labelo"><bean:message key="ficheroCuaderno.importar.fitxer"/></td>
	        <td class="input"><html:file property="fitxer" styleClass="file" tabindex="101"/></td>
	    </tr>
	</table>
<br />
<table class="nomarc">
   <tr>
       <td align="left">
           		<logic:present name="ficheroCuadernoForm" property="codigoFichero">
           			<back:accio tipus="modificacio" styleClass="button" onclick="return validar(this.form);" />
           		</logic:present>
                <logic:notPresent name="ficheroCuadernoForm" property="codigoFichero">
                    <back:accio tipus="alta" styleClass="button" onclick="return validar(this.form);" />
                </logic:notPresent>
        </td>
    </tr>
</table>
</html:form>

<!-- XAPUÇA -->
<% pageContext.removeAttribute(Globals.XHTML_KEY);%>
<html:javascript
    formName="ficheroCuadernoForm"
    dynamicJavascript="true"
    staticJavascript="false"
    htmlComment="true"
    cdata="false"
 />
<% pageContext.setAttribute(Globals.XHTML_KEY, "true");%>

</body>
</html:html>
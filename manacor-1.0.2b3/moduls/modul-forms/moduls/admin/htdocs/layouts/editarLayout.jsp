<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib prefix="admin" uri="admin"%>
<%--
 Parametros tiles:
    title: key del título de la página.
    alta.title: key del título de la acción alta
    modificacion.title: key del título de la acción modificación
    subtitulo: key del subtitulo
    validateMethod: Nombre del metodo de validación
    form.action: Action a la que se realiza el post.
    form.bean: Nombre del form.
    
    paginaValues: pagina que muestra los campos generales a editar.
    paginaTraduccio: pagina que muestra los campos traducibles a editar.
    
 --%>
<tiles:importAttribute name="title" scope="page" />
<tiles:importAttribute name="alta.title" scope="page" />
<tiles:importAttribute name="modificacion.title" scope="page" />
<tiles:importAttribute name="subtitle" scope="page" />
<tiles:importAttribute name="validateMethod" scope="page" />
<tiles:importAttribute name="listaRelaciones" scope="page" />
<tiles:useAttribute id="action" name="form.action" classname="java.lang.String" scope="page" />
<tiles:useAttribute id="bean" name="form.bean" classname="java.lang.String" scope="page" />
<tiles:useAttribute name="paginaValues" scope="page" />
<tiles:useAttribute name="paginaTraduccion" scope="page" />
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message name="title"/></title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
   <script type="text/javascript">
     <!--
     function reload(lang) {
        document.forms[0].select.value = lang;
        if ( validar(document.forms[0]) ) {
            document.forms[0].submit();
        }
     }
     
     function validar(form) {
        return <bean:write name="validateMethod" />(form);
     }

     function swichDisplay(name) {
        var elStyle = document.getElementById(name).style;
        if (elStyle.display == 'none') {
            elStyle.display = 'block';
            try { // table-row per els qui ho soportin, els altres (IE) toquen funcionar amb block.
                elStyle.display = 'table-row';
            } catch (ex) { ; }
        } else {
            elStyle.display = 'none';
        }
     }
     // -->
   </script>
</head>

<body class="ventana">
<table class="marc">
    <tr><td class="titulo">
        <logic:notPresent name="<%=bean%>" property="values.id">
            <bean:message name="alta.title" />
        </logic:notPresent>
        <logic:present name="<%=bean%>" property="values.id">
            <bean:message name="modificacion.title" />
        </logic:present>        
    </td></tr>
    <tr><td class="subtitulo"><bean:message name="subtitle" /></td></tr>
</table>
<br />
<html:errors/>
<html:form action="<%=action%>" styleId="<%=bean%>" enctype="multipart/form-data" >
    <html:hidden property="page" />
    <html:hidden property="values.id" />
    
    <logic:notEmpty name="paginaTraduccion">
        <html:hidden property="lang" />    
        <html:hidden property="select" />    
    </logic:notEmpty>

    <logic:notEmpty name="paginaValues">
        <table class="marc"><tiles:insert attribute="paginaValues" /></table><br />
    </logic:notEmpty>
    
    <logic:notEmpty name="paginaTraduccion">
        <tiles:insert definition=".langtabs">
            <tiles:put name="select" beanName="<%=bean%>" beanProperty="lang" />
        </tiles:insert>        
        <table class="marc"><tiles:insert attribute="paginaTraduccion" /></table><br />
    </logic:notEmpty>
    
    <table class="nomarc">
       <tr>
           <td align="left">
                <logic:present name="<%=bean%>" property="values.id">
                    <admin:accio tipus="modificacio" styleClass="button" onclick="return validar(this.form);" />
                </logic:present>
                <logic:notPresent name="<%=bean%>" property="values.id">
                    <admin:accio tipus="alta" styleClass="button" onclick="return validar(this.form);" />
                </logic:notPresent>           
                <html:reset styleClass="button"><bean:message key="boton.reiniciar" /></html:reset>
            </td>
            <td align="right">
                <html:cancel styleClass="button" ><bean:message key="boton.cancel" /></html:cancel>
            </td>
        </tr>
    </table>
    
</html:form>
<bean:define id="pagina" name="<%=bean%>" property="page" type="java.lang.Integer"/>
<html:javascript
    formName="<%=bean%>"
    dynamicJavascript="true"
    staticJavascript="false"
    page="<%=pagina.intValue()%>"
    htmlComment="true"
    cdata="false" 
 />

<logic:present name="<%=bean%>" property="values.id">
    <br />
    <logic:iterate id="element" name="listaRelaciones">
        <tiles:insert beanName="element" flush="false" >
            <tiles:put name="id" beanName="<%=bean%>" beanProperty="values.id" />
        </tiles:insert>
    </logic:iterate>
</logic:present>

</body>
</html:html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib uri="back" prefix="back"%>
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
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
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
        <logic:present name="reloadMenu">
            parent.Menu.location.reload(true);
        </logic:present>

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
   //-->
   </script>
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

<logic:present name="alert">
<table class="marc">
    <tr><td class="alert"><bean:message key="alert" /></td></tr>
</table>
<br />
</logic:present>

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
        <table class="marc">
            <tiles:insert attribute="paginaTraduccion" />
        </table>
        <br/>
    </logic:notEmpty>

    
    <table class="nomarc">
       <tr>
            <td align="left">
                <!--Si el formulario está bloqueado permitimos modificar -->
                <logic:present name="bloqueado">
                    <logic:present name="<%=bean%>" property="values.id">
                        <back:accio tipus="modificacio" styleClass="button" onclick="return validar(this.form);" />
                    </logic:present>
                    <logic:notPresent name="<%=bean%>" property="values.id">
                        <back:accio tipus="alta" styleClass="button" onclick="return validar(this.form);" />
                    </logic:notPresent>
                    <logic:notEmpty name="botonVersion">
                        <button class="button" type="button" onclick="forward('<html:rewrite page="/back/formulario/nversion.do" paramId="id" paramName="idFormulario" />')">
                            <bean:message key="boton.nuevaVersion" />
                        </button>
                    </logic:notEmpty>
                </logic:present>
                <html:reset styleClass="button"><bean:message key="boton.reiniciar" /></html:reset>
            </td>
            <td align="right">
                <html:cancel styleClass="button"><bean:message key="boton.cancel" /></html:cancel>
            </td>
        </tr>
    </table>

</html:form>


<logic:present name="<%=bean%>" property="values.id">
    <br />
    <logic:iterate id="element" name="listaRelaciones">
        <tiles:insert beanName="element" flush="false" >
            <tiles:put name="id" beanName="<%=bean%>" beanProperty="values.id" />
        </tiles:insert>
    </logic:iterate>
</logic:present>

<bean:define id="pagina" name="<%=bean%>" property="page" type="java.lang.Integer"/>
<!-- XAPUÇA -->
<% pageContext.removeAttribute(Globals.XHTML_KEY);%>
<html:javascript
    formName="<%=bean%>"
    dynamicJavascript="true"
    staticJavascript="false"
    page="<%=pagina.intValue()%>"
    htmlComment="true"
    cdata="false"
 />
<% pageContext.setAttribute(Globals.XHTML_KEY, "true");%>
</body>
</html:html>
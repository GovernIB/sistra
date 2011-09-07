<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml />
<script type="text/javascript">
<!--
     function hijos(id, padre) {
        var url = '<html:rewrite page="/menu.do" />?expand=arbol&select=' + id + '&padre=' + padre;
        alert(url);
        parent.Menu.location.href = url;
     }

     function consultaPantalla(id) {
        var url = '<html:rewrite page="/back/pantalla/seleccion.do" />?id=' + id;
        parent.Ventana.location.href = url;
     }

//-->
</script>

<tiles:importAttribute name="titulo" scope="page" />
<tiles:importAttribute name="nodos" scope="page" />
<tiles:useAttribute name="padre" scope="page" />
<tiles:useAttribute name="options" scope="page" classname="java.lang.String" />
<tiles:useAttribute name="prop" scope="page" classname="java.lang.String" />
<tiles:importAttribute name="pantallaOptions"/>
<tiles:importAttribute name="idFormulario" />

<bean:size id="numOptions" name="<%=options%>" />

<logic:equal name="numOptions" value="0">
    <bean:message key="arbol.pantalla.no" />
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <i><bean:message name="titulo" /></i>
    <ol>
    <logic:iterate  id="nodo" name="<%=options%>" >
        <li>
        <a href="javascript:consultaPantalla(<bean:write name="nodo" property="id" />)">
            <b><bean:write name="nodo" property="<%=prop%>" /></b>
        </a>
        <br />
            <tiles:insert name="nodos" flush="false" >
                <tiles:put name="id" beanName="nodo" beanProperty="id" />
                <tiles:put name="options" value="componenteOptions" />
                <tiles:put name="prop" value="nombreLogico" />
            </tiles:insert>
        </li>
    </logic:iterate>
    </ol>
</logic:notEqual>

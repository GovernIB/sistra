<%@ page language="java"%>
<%@ page import="org.ibit.rol.form.back.util.ComponenteConfig"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<html:xhtml/>

<tiles:useAttribute name="idPantalla" />
<tiles:useAttribute name="componenteOptions" />


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
        <logic:iterate  id="componente" name="componenteOptions" indexId="comId">
            <logic:notEmpty name="componente">
            <tr>
                <td class="outputd" width="5%">
                   <bean:define id="tipus">componente.tipo.<%=ComponenteConfig.getTipo(componente)%></bean:define>
                    <bean:message name="tipus" />
                </td>
                <td class="outputd" width="63%" >
                    <bean:define id="nombre" name="componente" property="nombreLogico" type="java.lang.String"/>
                    <bean:write name="nombre"/>
                    <logic:present name="componente" property="traduccion.nombre">
                        (<bean:write name="componente" property="traduccion.nombre" />)
                    </logic:present>
                    <logic:present name="componente" property="traduccion.etiqueta" >
                        (<bean:write name="componente" property="traduccion.etiqueta" />)
                    </logic:present>
                </td>
                <td align="right">
                    <bean:define id="urlEditar"><html:rewrite page="/back/componente/seleccion.do" paramId="id" paramName="componente" paramProperty="id"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>
                     <!--Si el formulario está bloqueado permitimos modificar -->
                	 <logic:present name="bloqueado">
                        <bean:define id="nombre" name="componente" property="nombreLogico" type="java.lang.String"/>
                        <bean:define id="mensajeBaja"><bean:message arg0='<%=nombre%>' key='componente.baja' /></bean:define>
                        <bean:define id="urlBaja"><html:rewrite page='<%="/back/componente/baja.do?idPantalla=" + idPantalla%>' paramId="id" paramName="componente" paramProperty="id"/></bean:define>
                        <%
                        mensajeBaja = mensajeBaja.replace("\'","&#145;");
                        mensajeBaja = mensajeBaja.replace("\"","&#34;");
    	                %>
                        <button class="button" type="button" onclick="confirmAndForward('<%=mensajeBaja%>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
                    </logic:present>
                </td>
            </tr>
            </logic:notEmpty>
            <logic:empty name="componente">
                <tr>
                    <td colspan="3" class="outputd" style="color: red;"><bean:message key="errors.integridad" arg0="<%=comId.toString()%>"/></td>
                </tr>
            </logic:empty>
        </logic:iterate>
    </table>
</logic:notEmpty>
<table class="nomarc">
    <tr><td align="center"><bean:message key="componente.tipo.leyenda" /></td></tr>
</table>

<br />

<!--
En el caso de que el formulario esté bloqueado permitimos el botón de nuevo componente ni el de ordenar componentes.
-->
<logic:present name="bloqueado">
    <table class="nomarc">
        <tr>
            <td align="right">
                <bean:define id="urlNuevo"><html:rewrite page="/back/componente/listaEleccion.do" paramId="idPantalla" paramName="idPantalla"/></bean:define>
                <button class="buttond" type="button" onclick="forward('<%=urlNuevo%>')">
                    <bean:message key="boton.nuevo" />
                </button>
            </td>
            <td align="left">
                <bean:define id="urlPaleta"><html:rewrite page="/back/paleta/lista.do" paramId="idPantalla" paramName="idPantalla"/></bean:define>
                <button class="buttond" type="button" onclick="forward('<%=urlPaleta%>')"><bean:message key="boton.paleta" /></button>
                <bean:define id="urlOrden"><html:rewrite page="/back/componente/orden.do" paramId="idPantalla" paramName="idPantalla"/></bean:define>
                <button class="buttond" type="button" onclick="forward('<%=urlOrden%>')"><bean:message key="boton.orden" /></button>
            </td>
        </tr>
    </table>
</logic:present>

<%@ page language="java"%>
<%@ page import="org.ibit.rol.form.admin.util.ComponenteConfig"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<tiles:useAttribute name="idPaleta" />
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
        <logic:iterate  id="componente" name="componenteOptions" >
            <tr>
                <td class="outputd" width="5%">
                    <bean:define id="tipus">componente.tipo.<%=ComponenteConfig.getTipo(componente)%></bean:define>
                    <bean:message name="tipus" />
                </td>
                <td class="outputd" width="63%" >
                    <bean:write name="componente" property="nombreLogico"/>
                    <logic:present name="componente" property="traduccion.nombre">
                        (<bean:write name="componente" property="traduccion.nombre" />)
                    </logic:present>
                    <logic:present name="componente" property="traduccion.etiqueta" >
                        (<bean:write name="componente" property="traduccion.etiqueta" />)
                    </logic:present>
                </td>
                <td>
                    <bean:define id="urlEditar"><html:rewrite page="/admin/componente/seleccion.do" paramId="id" paramName="componente" paramProperty="id"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>

                    <bean:define id="nombre" name="componente" property="nombreLogico" type="java.lang.String"/>
                    <bean:define id="mensajeBaja"><bean:message arg0='<%=nombre%>' key='componente.baja' /></bean:define>
                    <bean:define id="urlBaja"><html:rewrite page='<%="/admin/componente/baja.do?idPaleta=" + idPaleta%>' paramId="id" paramName="componente" paramProperty="id"/></bean:define>
                    <%
                    mensajeBaja = mensajeBaja.replace("\'","&#145;");
                    mensajeBaja = mensajeBaja.replace("\"","&#34;");
                    %>
                    <button class="button" type="button" onclick="confirmAndForward('<%=mensajeBaja%>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
                </td>
            </tr>

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
        <bean:define id="urlNuevo">
            <html:rewrite page="/admin/componente/listaEleccion.do" paramId="idPaleta" paramName="idPaleta"/>
        </bean:define>
        <button class="buttond" type="button" onclick="forward('<%=urlNuevo%>')">
            <bean:message key="boton.nuevo" />
        </button>
    </td>
  </tr>
</table>

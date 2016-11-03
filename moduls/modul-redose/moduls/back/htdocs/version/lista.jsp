<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<html:xhtml/>
<tiles:useAttribute name="idModelo"/>
<tiles:useAttribute name="versionOptions"/>
<bean:size id="numOptions" name="versionOptions" />

<br />
<table class="nomarc">
    <tr><td class="titulo"><bean:message key="version.asociada" /></td></tr>
</table>
<br />

<logic:equal name="numOptions" value="0">
    <table class="marc">
      <tr><td class="alert"><bean:message key="version.selec.vacio" /></td></tr>
    </table>
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <table class="marc">
        <logic:iterate  id="version" name="versionOptions" >
            <tr>
                <td class="outputd" width="70%" >
                    <bean:write name="version" property="version" />
                        (<bean:write name="version" property="descripcion" />)
                </td>
                <td align="right">
                    <bean:define id="urlEditar"><html:rewrite page="/back/version/seleccion.do" paramId="codigo" paramName="version" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>

                    <bean:define id="nombre" name="version" property="version" type="java.lang.Integer"/>
                    <bean:define id="mensajeBaja"><bean:message arg0='<%= nombre.toString() %>' key='version.baja' /></bean:define>
                    <bean:define id="urlBaja"><html:rewrite page='<%="/back/version/baja.do?idModelo=" + idModelo%>' paramId="codigo" paramName="version" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="confirmAndForward('<%=mensajeBaja%>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
                </td>
            </tr>
        </logic:iterate>
    </table>
</logic:notEqual>
<br />
<table class="nomarc">
  <tr>
  <td align="right">
    <bean:define id="urlNuevo">
        <html:rewrite page="/back/version/alta.do" paramId="idModelo" paramName="idModelo"/>
    </bean:define>
    <button class="buttond" type="button" onclick="forward('<%=urlNuevo%>')">
        <bean:message key="boton.nuevo" />
    </button>
  </td>
  </tr>
</table>

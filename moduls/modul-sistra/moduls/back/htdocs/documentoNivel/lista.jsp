<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<html:xhtml/>
<tiles:useAttribute name="idDocumento"/>
<tiles:useAttribute name="documentoNivelOptions"/>
<bean:size id="numOptions" name="documentoNivelOptions" />

<br />
<table class="nomarc">
    <tr><td class="titulo"><bean:message key="documentoNivel.asociada" /></td></tr>
</table>
<br />

<logic:equal name="numOptions" value="0">
    <table class="marc">
      <tr><td class="alert"><bean:message key="documentoNivel.selec.vacio" /></td></tr>
    </table>
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <table class="marc">
        <logic:iterate  id="documentoNivel" name="documentoNivelOptions" type="es.caib.sistra.model.DocumentoNivel">
            <tr>
                <td class="outputd" width="70%" >
                    <bean:write name="documentoNivel" property="nivelAutenticacion" />
                </td>
                <td align="right">
                    <bean:define id="urlEditar"><html:rewrite page="/back/documentoNivel/seleccion.do" paramId="codigo" paramName="documentoNivel" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>

                    <bean:define id="nombre" name="documentoNivel" property="nivelAutenticacion" />
                    <bean:define id="mensajeBaja"><bean:message arg0='<%=nombre.toString()%>' key='documentoNivel.baja' /></bean:define>
                    <bean:define id="urlBaja"><html:rewrite page='<%="/back/documentoNivel/baja.do?idDocumento=" + idDocumento%>' paramId="codigo" paramName="documentoNivel" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="confirmAndForward('<%= StringUtils.escape( mensajeBaja )%>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
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
        <html:rewrite page="/back/documentoNivel/alta.do" paramId="idDocumento" paramName="idDocumento"/>
    </bean:define>
    <button class="buttond" type="button" onclick="forward('<%=urlNuevo%>')">
        <bean:message key="boton.nuevo" />
    </button>
  </td>
  </tr>
</table>

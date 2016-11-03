<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<html:xhtml/>
<tiles:useAttribute name="idVersion"/>
<tiles:useAttribute name="plantillaOptions"/>
<bean:size id="numOptions" name="plantillaOptions" />

<br />
<table class="nomarc">
    <tr><td class="titulo"><bean:message key="plantilla.asociada" /></td></tr>
</table>
<br />

<logic:equal name="numOptions" value="0">
    <table class="marc">
      <tr><td class="alert"><bean:message key="plantilla.selec.vacio" /></td></tr>
    </table>
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <table class="marc">
        <logic:iterate  id="plantilla" name="plantillaOptions" >
            <tr>
                <td class="outputd" width="70%" >
                    <bean:write name="plantilla" property="tipo" />
                </td>
                <td align="right">
                    <bean:define id="urlEditar"><html:rewrite page="/back/plantilla/seleccion.do" paramId="codigo" paramName="plantilla" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>

                    <bean:define id="nombre" name="plantilla" property="tipo" type="java.lang.String"/>
                    <bean:define id="mensajeBaja"><bean:message arg0='<%=nombre%>' key='plantilla.baja' /></bean:define>
                    <bean:define id="urlBaja"><html:rewrite page='<%="/back/plantilla/baja.do?idVersion=" + idVersion%>' paramId="codigo" paramName="plantilla" paramProperty="codigo"/></bean:define>
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
        <html:rewrite page="/back/plantilla/alta.do" paramId="idVersion" paramName="idVersion"/>
    </bean:define>
    <button class="buttond" type="button" onclick="forward('<%=urlNuevo%>')">
        <bean:message key="boton.nuevo" />
    </button>
  </td>
  </tr>
</table>

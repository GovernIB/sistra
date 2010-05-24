<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<html:xhtml/>
<tiles:useAttribute name="idFormulario"/>
<tiles:useAttribute name="salidaOptions"/>
<bean:size id="numOptions" name="salidaOptions" />

<br />
<br />
<table class="nomarc">
    <tr><td class="titulo"><bean:message key="puntosalida.asociado" /></td></tr>
</table>
<br />

<logic:equal name="numOptions" value="0">
    <table class="marc">
      <tr><td class="alert"><bean:message key="puntosalida.selec.vacio" /></td></tr>
    </table>
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <table class="marc">
        <logic:iterate  id="salida" name="salidaOptions" >
            <tr>
                <td class="outputd" width="70%" >
                    <bean:write name="salida" property="punto.nombre" />
                </td>
                <td align="right">
                    <bean:define id="idSalida" name="salida" property="id"/>
                    <bean:define id="urlEditar"><html:rewrite page="/back/salida/seleccion.do" paramId="id" paramName="salida" paramProperty="id"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.propied" /></button>
                    <!--
                    En el caso de que el formulario asociado esté bloqueado, no debo pintar
                    el botón de eliminar
                    -->
                    <logic:equal name="salida" property="formulario.bloqueado" value="false">
                        <bean:define id="nombre" name="salida" property="punto.nombre" type="java.lang.String"/>
                        <bean:define id="mensajeBaja"><bean:message arg0='<%=nombre%>' key='puntosalida.baja' /></bean:define>
                        <bean:define id="urlBaja"><html:rewrite page='<%="/back/salida/baja.do?idFormulario=" + idFormulario%>' paramId="idSalida" paramName="salida" paramProperty="id"/></bean:define>
                        <button class="button" type="button" onclick="confirmAndForward('<%=mensajeBaja%>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
                    </logic:equal>
                </td>
            </tr>
        </logic:iterate>
    </table>
</logic:notEqual>

<br />

<logic:present name="bloqueado">
    <table class="nomarc">
      <tr>
      <td align="left">
        <bean:define id="urlNuevo">
            <html:rewrite page="/back/puntosalida/lista.do" paramId="idFormulario" paramName="idFormulario"/>
        </bean:define>
        <button class="buttond" type="button" onclick="forward('<%=urlNuevo%>')">
            <bean:message key="boton.anyadir" />
        </button>
      </td>
      </tr>
    </table>
</logic:present>

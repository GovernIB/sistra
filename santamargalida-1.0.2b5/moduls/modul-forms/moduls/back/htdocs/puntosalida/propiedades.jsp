<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>



<bean:size id="numOptions" name="propiedadSalidaOptions" />

<br />
<br />
<table class="nomarc">
    <tr><td class="titulo"><bean:message key="propiedadsalida.asociada" /></td></tr>
</table>
<br />

<logic:equal name="numOptions" value="0">
    <table class="marc">
      <tr><td class="alert"><bean:message key="propiedadsalida.selec.vacio" /></td></tr>
    </table>
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <table class="marc">
        <logic:iterate  id="propiedad" name="propiedadSalidaOptions" >
            <tr>
                <td class="outputd" width="70%" >
                    <bean:write name="propiedad" property="nombre" />
                </td>
                <td align="right">
                    <bean:define id="urlEditar"><html:rewrite page="/back/propiedadsalida/seleccion.do" paramId="id" paramName="propiedad" paramProperty="id"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>
                    <logic:equal name="propiedad" property="salida.formulario.bloqueado" value="false">
                        <bean:define id="idSalida" name="propiedad" property="salida.id" />
                        <bean:define id="nombre" name="propiedad" property="nombre" type="java.lang.String"/>
                        <bean:define id="mensajeBaja"><bean:message arg0='<%=nombre%>' key='propiedadsalida.baja' /></bean:define>
                        <bean:define id="urlBaja"><html:rewrite page='<%="/back/propiedadsalida/baja.do?idSalida=" + idSalida %>' paramId="idPropiedad" paramName="propiedad" paramProperty="id"/></bean:define>
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
                    <html:rewrite page="/back/propiedadsalida/alta.do" paramId="idSalida" paramName="idSalida"/>
                </bean:define>
                <button class="buttond" type="button" onclick="forward('<%=urlNuevo%>')">
                    <bean:message key="boton.nuevo" />
                </button>
            </td>
            <td align="right">
                <bean:define id="urlCancel">
                    <html:rewrite page="/back/formulario/seleccion.do" paramId="id" paramName="salida" paramProperty="formulario.id"/>
                </bean:define>
                <button class="buttond" type="button" onclick="forward('<%=urlCancel%>')">
                    <bean:message key="boton.cancel" />
                </button>
            </td>
        </tr>
    </table>
</logic:present>


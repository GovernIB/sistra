<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<tiles:useAttribute name="idComponente" />
<tiles:useAttribute name="valoresOptions" />

<br />
<table class="nomarc">
    <tr><td class="titulo"><bean:message key="valorposible.asociado" /></td></tr>
</table>
<br />

<logic:empty name="valoresOptions">
    <table class="marc">
      <tr><td class="alert"><bean:message key="valorposible.selec.vacio" /></td></tr>
    </table>
</logic:empty>

<logic:notEmpty name="valoresOptions">
    <table class="marc">
        <logic:iterate  id="valor" name="valoresOptions" >
            <tr>
                <td class="outputd" width="70%" >
                    <bean:write name="valor" property="traduccion.etiqueta" />
                    <logic:equal name="valor" property="defecto" value="true">
                        (<bean:message key="valorposible.defecto" />)
                    </logic:equal>
                </td>
                <td>
                    <bean:define id="urlEditar"><html:rewrite page="/admin/valorposible/seleccion.do" paramId="id" paramName="valor" paramProperty="id"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>

                    <bean:define id="nombre" name="valor" property="traduccion.etiqueta" type="java.lang.String"/>
                    <bean:define id="mensajeBaja"><bean:message arg0='<%=StringUtils.escape(nombre)%>' key='componente.baja' /></bean:define>
                    <bean:define id="urlBaja"><html:rewrite page='<%="/admin/valorposible/baja.do?idComponente=" + idComponente%>' paramId="id" paramName="valor" paramProperty="id"/></bean:define>
                    <button class="button" type="button" onclick="confirmAndForward('<%=mensajeBaja%>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
                </td>
            </tr>
        </logic:iterate>
    </table>
</logic:notEmpty>

<br />
<table class="nomarc">
    <tr>
        <td align="right">
            <bean:define id="urlNuevo">
                <html:rewrite page="/admin/valorposible/alta.do" paramId="idComponente" paramName="idComponente"/>
            </bean:define>
            <button class="buttond" type="button" onclick="forward('<%=urlNuevo%>')">
                <bean:message key="boton.nuevo" />
            </button>
        </td>
        <td align="left">
        <bean:define id="urlOrden">
            <html:rewrite page="/admin/valorposible/orden.do" paramId="idComponente" paramName="idComponente"/>
        </bean:define>
        <button class="buttond" type="button" onclick="forward('<%=urlOrden%>')">
            <bean:message key="boton.orden" />
        </button>
    </td>
    </tr>
</table>

<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<html:xhtml/>
<tiles:useAttribute name="idFuenteDatos"/>
<tiles:useAttribute name="camposOptions"/>

<logic:notEmpty name="idFuenteDatos">
<bean:size id="numOptions" name="camposOptions" />

<br />
<table class="nomarc">
    <tr><td class="titulo"><bean:message key="campoFuenteDatos.asociada" /></td></tr>
</table>
<br />

<logic:equal name="numOptions" value="0">
    <table class="marc">
      <tr><td class="alert"><bean:message key="campoFuenteDatos.selec.vacio" /></td></tr>
    </table>
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <table class="marc">
        <logic:iterate  id="campo" name="camposOptions" type="es.caib.bantel.model.CampoFuenteDatos">
            <tr>
                <td class="outputd" width="70%" >
                    <bean:write name="campo" property="identificador" />  
                    <logic:equal name="campo" property="esPK" value="S">
                    	[PK]
                    </logic:equal>                      
                </td>
                <td align="right">
                	<bean:define id="idCampo" value="<%=campo.getFuenteDatos().getIdentificador() + \"#\" + campo.getIdentificador()%>" />
                    <bean:define id="urlEditar"><html:rewrite page="/back/campoFuenteDatos/seleccion.do" paramId="identificador" paramName="idCampo"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>
                    <bean:define id="nombre" name="campo" property="identificador" type="java.lang.String"/>
                    <bean:define id="mensajeBaja"><bean:message arg0="<%= nombre %>" key="campoFuenteDatos.baja" /></bean:define>
                    <bean:define id="urlBaja"><html:rewrite page="/back/campoFuenteDatos/baja.do" paramId="identificador" paramName="idCampo"/></bean:define>
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
        <html:rewrite page="/back/campoFuenteDatos/alta.do" paramId="idFuenteDatos" paramName="idFuenteDatos"/>
    </bean:define>
    <button class="buttond" type="button" onclick="forward('<%=urlNuevo%>')">
        <bean:message key="boton.nuevo" />
    </button>
  </td>
  </tr>
</table>
</logic:notEmpty>

<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<tiles:useAttribute name="idComponente" />
<tiles:useAttribute name="valoresOptions" />
<bean:size id="numOptions" name="valoresOptions" />

<br />
<table class="nomarc">
    <tr><td class="titulo"><bean:message key="valorposible.asociado" /></td></tr>
</table>
<br />

<logic:equal name="numOptions" value="0">
    <table class="marc">
      <tr><td class="alert"><bean:message key="valorposible.selec.vacio" /></td></tr>
    </table>
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <table class="marc">
        <logic:iterate  id="valor" name="valoresOptions" indexId="valId">
            <logic:notEmpty name="valor">
            <tr>
                <td class="outputd" width="70%" >
                    <bean:define id="nombre" value="" />
                    <bean:write name="valor" property="traduccion.etiqueta" />
                    <logic:equal name="valor" property="defecto" value="true">
                        (<bean:message key="valorposible.defecto" />)
                    </logic:equal>
                </td>
                <td align="right">
                    <bean:define id="urlEditar"><html:rewrite page="/back/valorposible/seleccion.do" paramId="id" paramName="valor" paramProperty="id"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>

                    <!--Si el formulario está bloqueado permitimos modificar -->
              		 <logic:present name="bloqueado">
                        <bean:define id="nombre" name="valor" property="traduccion.etiqueta" type="java.lang.String"/>
                        <bean:define id="mensajeBaja"><bean:message arg0='<%=StringUtils.escape(nombre)%>' key='componente.baja' /></bean:define>
                        <bean:define id="urlBaja"><html:rewrite page='<%="/back/valorposible/baja.do?idComponente=" + idComponente%>' paramId="id" paramName="valor" paramProperty="id"/></bean:define>
                        <%
                        mensajeBaja = mensajeBaja.replace("\'","&#145;");
                        mensajeBaja = mensajeBaja.replace("\"","&#34;");
    	                %>
                        <button class="button" type="button" onclick="confirmAndForward('<%=mensajeBaja%>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
                    </logic:present>
                    
                </td>
            </tr>
            </logic:notEmpty>
            <logic:empty name="valor">
                <tr>
                    <td colspan="2" class="outputd" style="color: red;"><bean:message key="errors.integridad" arg0="<%=valId.toString()%>"/></td>
                </tr>
            </logic:empty>
        </logic:iterate>
    </table>
</logic:notEqual>
<br />
<logic:present name="bloqueado">
    <table class="nomarc">
        <tr>
            <td align="right">
                <bean:define id="urlNuevo">
                    <html:rewrite page="/back/valorposible/alta.do" paramId="idComponente" paramName="idComponente"/>
                </bean:define>
                <button class="buttond" type="button" onclick="forward('<%=urlNuevo%>')">
                    <bean:message key="boton.nuevo" />
                </button>
            </td>
            <td align="left">
                <bean:define id="urlOrden">
                    <html:rewrite page="/back/valorposible/orden.do" paramId="idComponente" paramName="idComponente"/>
                </bean:define>
                <button class="buttond" type="button" onclick="forward('<%=urlOrden%>')">
                    <bean:message key="boton.orden" />
                </button>
            </td>
        </tr>
    </table>
</logic:present>

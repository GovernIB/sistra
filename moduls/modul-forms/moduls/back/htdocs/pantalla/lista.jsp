<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<html:xhtml/>
<tiles:useAttribute name="idFormulario"/>
<tiles:useAttribute name="pantallaOptions"/>
<bean:size id="numOptions" name="pantallaOptions" />

<br />
<table class="nomarc">
    <tr><td class="titulo"><bean:message key="pantalla.asociada" /></td></tr>
</table>
<br />

<logic:equal name="numOptions" value="0">
    <table class="marc">
      <tr><td class="alert"><bean:message key="pantalla.selec.vacio" /></td></tr>
    </table>
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <table class="marc">
        <logic:iterate id="pantalla" name="pantallaOptions" indexId="panId">
           <logic:notEmpty name="pantalla">
	           <logic:empty name="pantalla" property="componenteListaElementos">
	            <tr>
	                <td class="outputd" width="70%" >
	                    <bean:write name="pantalla" property="nombre" />
	                    <logic:present name="pantalla" property="traduccion.titulo">
	                        (<bean:write name="pantalla" property="traduccion.titulo" />)
	                    </logic:present>
	                    <logic:equal name="pantalla" property="inicial" value="true">
	                        <bean:message key="pantalla.inicial" />
	                    </logic:equal>
	                    <logic:equal name="pantalla" property="ultima" value="true">
	                        <bean:message key="pantalla.ultima" />
	                    </logic:equal>
	                </td>
	                <td align="right">
	                    <bean:define id="urlEditar"><html:rewrite page="/back/pantalla/seleccion.do" paramId="id" paramName="pantalla" paramProperty="id"/></bean:define>
	                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>
	                     <!--Si el formulario está bloqueado permitimos modificar -->
                		 <logic:present name="bloqueado">
	                        <bean:define id="nombre" name="pantalla" property="nombre" type="java.lang.String"/>
	                        <bean:define id="mensajeBaja"><bean:message arg0='<%=nombre%>' key='pantalla.baja' /></bean:define>
	                        <bean:define id="urlBaja"><html:rewrite page='<%="/back/pantalla/baja.do?idFormulario=" + idFormulario%>' paramId="id" paramName="pantalla" paramProperty="id"/></bean:define>
	                        <%
	                        mensajeBaja = mensajeBaja.replace("\'","&#145;");
	                        mensajeBaja = mensajeBaja.replace("\"","&#34;");
    	                    %>
	                        <button class="button" type="button" onclick="confirmAndForward('<%=mensajeBaja%>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>	                        
	                    </logic:present>
	                </td>
	            </tr>
			 </logic:empty>
            </logic:notEmpty>
            
            <logic:empty name="pantalla">
                <tr>
                    <td colspan="2" class="outputd" style="color: red;"><bean:message key="errors.integridad" arg0="<%=panId.toString()%>"/></td>
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
                    <html:rewrite page="/back/pantalla/alta.do" paramId="idFormulario" paramName="idFormulario"/>
                </bean:define>
                <button class="buttond" type="button" onclick="forward('<%=urlNuevo%>')">
                    <bean:message key="boton.nuevo" />
                </button>
            </td>
            <td align="left">
                <bean:define id="urlOrden">
                    <html:rewrite page="/back/pantalla/orden.do" paramId="idFormulario" paramName="idFormulario"/>
                </bean:define>
                <button class="buttond" type="button" onclick="forward('<%=urlOrden%>')">
                    <bean:message key="boton.orden" />
                </button>
            </td>
        </tr>
    </table>
</logic:present>


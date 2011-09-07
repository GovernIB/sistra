<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<logic:present name="tramiteVersionForm" property="values.codigo">
<bean:define id="idTrVersion" name="tramiteVersionForm" property="values.codigo"/>
<bean:define id="urlEspec">
    <html:rewrite page="/back/especificacionesGenericas/seleccion.do" paramId="codigo" paramName="idEspecTramiteNivel"/>
</bean:define>
<bean:define id="urlTramiteNiveles">
    <html:rewrite page="/back/tramiteNivel/lista.do" paramId="idTramiteVersion" paramName="idTrVersion"/>
</bean:define>
<bean:define id="urlTramiteDocumentos">
    <html:rewrite page="/back/documento/lista.do" paramId="idTramiteVersion" paramName="idTrVersion"/>
</bean:define>
<bean:define id="urlTramiteMensajes">
    <html:rewrite page="/back/mensajeTramite/lista.do" paramId="idTramiteVersion" paramName="idTrVersion" />
</bean:define>
<bean:define id="urlDatoJustificante">
    <html:rewrite page="/back/datoJustificante/lista.do" paramId="idEspecTramiteNivel" paramName="tramiteVersionForm" paramProperty="values.codigo"/>
</bean:define>

<table class="nomarc">
  <tr>
  <td align="right">
    <button class="buttond" type="button" onclick="forward('<%=urlEspec%>')">
        <bean:message key="boton.tramiteEspec" />
    </button>
    <button class="buttond" type="button" onclick="forward('<%=urlTramiteNiveles%>')">
        <bean:message key="boton.tramiteNivelAuth" />
    </button>
    <button class="buttond" type="button" onclick="forward('<%=urlTramiteDocumentos%>')">
        <bean:message key="boton.documento" />
    </button>
    <button class="buttond" type="button" onclick="forward('<%=urlTramiteMensajes%>')">
        <bean:message key="boton.mensajeTramite" />
    </button>
    <button class="buttond" type="button" onclick="forward('<%=urlDatoJustificante + "&idTramiteVersion=" + idTrVersion %>')">
        <bean:message key="boton.datoJustificante" />
    </button>
  </td>
  </tr>
</table>
</logic:present>
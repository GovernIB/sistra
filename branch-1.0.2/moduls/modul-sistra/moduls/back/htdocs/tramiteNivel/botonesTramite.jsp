<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<logic:present name="tramiteNivelForm" property="values.codigo">
<bean:define id="urlDatoJustificante">
    <html:rewrite page="/back/datoJustificante/lista.do" paramId="idEspecTramiteNivel" paramName="tramiteNivelForm" paramProperty="values.codigo"/>
</bean:define>

<bean:define id="idTrNivel" name="tramiteNivelForm" property="tramite.codigo"/>

<table class="nomarc">
  <tr>
  <td align="right">
    <button class="buttond" type="button" onclick="forward('<%=urlDatoJustificante + "&idTramiteNivel=" + idTrNivel %>')">
        <bean:message key="boton.datoJustificante" />
    </button>
  </td>
  </tr>
</table>
</logic:present>
<%@ page language="java" %>
<%@ page import="java.util.Locale,
                 org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%--
Dibuja los tabs para idioma:
Parametros:
    (tiles) select: String con el idioma seleccionado
    (request) idiomes: Lista de String con los idiomas soportados
Recursos:
    funcion javascript reload('lang') definida en la página que lo incluye
    Un mensaje por cada codigo de idioma ( ca=Catalàn  etc ...)
    
--%>
<html:xhtml />
<%
    Locale current = (Locale) session.getAttribute(Globals.LOCALE_KEY);
%>
<tiles:useAttribute id="select" name="select" classname="java.lang.String" />
<table class="tabs" cellspacing="0" cellpadding="0" border="0">
    <tr>
    <logic:iterate id="lang" name="idiomas" type="java.lang.String">
       <logic:equal name="lang" value="<%=select%>">
          <td class="tab">
            <%=new Locale(lang).getDisplayLanguage(current)%>
          </td>
       </logic:equal>
       <logic:notEqual name="lang" value="<%=select%>">
          <td class="notab">
            <a href="javascript:reload('<%=lang%>')"><%=new Locale(lang).getDisplayLanguage(current)%></a>
          </td>
       </logic:notEqual>       
    </logic:iterate>
    </tr>
</table>

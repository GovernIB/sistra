<%@ page language="java" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>

<tr>
    <td class="labelo"><bean:message key="tramite.descripcion"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="10" property="traduccion.descripcion" maxlength="100"/></td>
</tr>
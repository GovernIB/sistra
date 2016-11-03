<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<tr>
    <td class="labelo"><bean:message key="formateadores.titulo"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.clase" maxlength="300"/></td>
</tr>

<tr>
    <td class="labelo"><bean:message key="formateadores.nombre"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.descripcion" maxlength="100"/></td>
</tr>





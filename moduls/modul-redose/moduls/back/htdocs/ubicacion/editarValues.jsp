<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<tr>
    <td class="labelo"><bean:message key="ubicacion.ubicacion"/></td>
    <td class="input"><html:text styleClass="data" tabindex="1" property="values.codigoUbicacion" maxlength="5" /></td>
</tr>

<tr>
    <td class="labelo"><bean:message key="ubicacion.nombre"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.nombre" maxlength="50" /></td>
</tr>

<tr>
    <td class="labelo"><bean:message key="ubicacion.plugin"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.pluginAlmacenamiento" maxlength="500" /></td>
</tr>

<tr>
    <td class="labelo"><bean:message key="ubicacion.defecto"/></td>
    <td class="input"><html:checkbox property="values.defecto" value="S" tabindex="1" /></td>
</tr>





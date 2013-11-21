<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<html:hidden property="idFuenteDatos" />
<html:hidden property="identificadorOld" />
<tr>
    <td class="labelo"><bean:message key="campoFuenteDatos.identificador"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.identificador" maxlength="20" /></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="campoFuenteDatos.esPK"/></td>
    <td class="input">
    	 <html:select property="values.esPK">
    	 	 <html:option value="N"  key="campoFuenteDatos.esPK.no"  />
             <html:option value="S"  key="campoFuenteDatos.esPK.si"  />                    
         </html:select>                
</tr>
<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<html:xhtml/>
<nested:root name="ayudaPantalla">
    <nested:nest property="traduccion">
        <nested:notEmpty property="descripcionLarga">
            <nested:write property="descripcionLarga" filter="false" />
        </nested:notEmpty>

        <nested:notEmpty property="urlWeb">
            <p><a target="_blank" href="<nested:write property='urlWeb'/>"><bean:message key="ayuda.urlWeb" /></a></p>
        </nested:notEmpty>
    </nested:nest>
</nested:root>
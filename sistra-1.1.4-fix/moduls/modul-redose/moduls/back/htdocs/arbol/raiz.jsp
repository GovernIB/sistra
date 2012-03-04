<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml />
<tiles:importAttribute name="titulo" scope="page" />
<tiles:importAttribute name="nodos" scope="page" />
<bean:parameter id="select" name="select" value="0" />
<table cellpadding="0" cellspacing="0" border="0">
<tr><td>
    <bean:message name="titulo"/>
</td></tr>
<tiles:insert beanName="nodos" flush="false" >
    <tiles:put name="options" value="formularioOptions" />
    <tiles:put name="prop" value="modelo" />
    <tiles:put name="select" beanName="select" />
</tiles:insert>

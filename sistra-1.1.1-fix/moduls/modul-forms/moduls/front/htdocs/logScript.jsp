<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Log Script</title>

<style type="text/css">
<!--
body {
	background-color: #CCCCCC;
}
body,td,th {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 0.8em;
	color: #000099;
	text-align: left;
}
.error {
	background-color:red;
	font-weight: bold;
}
-->
</style>
</head>
<body onload="location.href='#end'">
<div align="center">
LOG SCRIPTS FORMS
</div>

<div id="contenidor">

<logic:iterate id="log" name="logScript">
	<hr/>
	
	<logic:empty name="log" property="excepcion">
		<table width="90%" border="0" cellpadding="5" cellspacing="0">
	</logic:empty>
	
	<logic:notEmpty name="log" property="excepcion">
		<table width="90%" border="0" cellpadding="5" cellspacing="0" class="error">
	</logic:notEmpty>
	
		 
   		 <tr>
   			 <th scope="col" width="10%">Script:</th>
   			 <th scope="col" width="100%">
   			 	<logic:iterate id="linea" name="log" property="lineasScript">
   			 		<bean:write name="linea"/><br/>
   			 	</logic:iterate>   			 	
   			 </th>
   		 </tr>

		 <tr>
   			 <th scope="col" width="10%">Parametros:</th>
   			 <th scope="col" width="100%"><bean:write name="log" property="parametros"/></th>
   		 </tr>
   		
   		 <logic:empty name="log" property="excepcion">	 
		 <tr>
   			 <th scope="col" width="10%">Resultado:</th>
   			 <th scope="col" width="100%"><bean:write name="log" property="resultado"/></th>
   		 </tr>
		 </logic:empty>
		  
		 <logic:notEmpty name="log" property="excepcion">
		 <tr>
   			 <th scope="col" width="10%">Resultado:</th>
   			 <th scope="col" width="100%">[null]</th>
   		 </tr>   		    		 

		 <tr>
   			 <th scope="col" width="10%">Error:</th>
   			 <th scope="col" width="100%"><bean:write name="log" property="excepcion"/></th>
   		 </tr>
   		 </logic:notEmpty>
   		 
   		 <logic:notEmpty name="log" property="debug">
   		 <tr>
   			 <th scope="col" width="10%">Debug:</th>
   			 <th scope="col" width="100%">
   				<logic:iterate id="debug" name="log" property="debug">
					<bean:write name="debug"/><br/>
				</logic:iterate>			    			 
   			 </th>
   		 </tr>   		    		 
		 </logic:notEmpty>
   		
		</table>	
</logic:iterate>
<hr/>
<div id="botonera" align="center">
<bean:define id="urlRecargar" type="java.lang.String">
        <html:rewrite page="/logScript.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<bean:define id="urlLimpiar" type="java.lang.String">
        <html:rewrite page="/limpiarLogScript.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<A NAME="end"></A>
<input type="button" onclick="document.location='<%=urlRecargar%>'" value="Recargar" />
<input type="button" onclick="document.location='<%=urlLimpiar%>'" value="Limpiar" />
</div>
</div><!-- contenidor -->
</body>
</html>

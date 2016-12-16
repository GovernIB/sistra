<%@ page language="java" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html lang="en">

<bean:define id="id" name="id" type="java.lang.String"/>

<head>
	<link
	 rel="stylesheet"
	 type="text/css"
	 title="Fixed content"
	 href="<%=request.getContextPath()%>/css/fixed.css">
	 
	 <script type="text/javascript">

		 function seleccionar(codigo)
		{		     
		     var index = findOption(opener.document.getElementsByName('<bean:write name="id"/>').item(0).options, codigo);
		     opener.document.getElementsByName('<bean:write name="id"/>').item(0).options[index].selected = 'selected';
		     window.close();
		}
		
		function findOption(options, valor)
		{
		   for(i=0; i<options.length; i++)
		   {
		      if(options[i].value == valor)
		      {
		         return i;
		      }
		   }
		   return 0;
		}	 
	</script>
		 
</head>

<body topmargin=16 marginheight=16>

	<DIV style="TEXT-ALIGN: center;
    font-family: verdana, arial, helvetica, sans-serif;
    font-size: 12pt;
    font-weight:bold;
    vAlign: middle;
    color: #d8dfe7;
    border: 2px solid white /*#d8dfe7*/;
    background-color: #515b67;height:20px;width:500px;
    border-spacing: 2;
    margin: 10px 10px 10px 10px;"><bean:message key="tramiteVersion.organoDestino" /></DIV>
    
    <div id="wrapper" style="text-align: center">    
	    <div id="buscador" style="display: inline-block;">
	    	<html:form action="/back/tramiteVersion/buscarServicios">
				<html:hidden property="idCampo" value="<%=id%>"/>
				<html:text property="filtro" value=""/>
				<html:submit styleClass="button"><bean:message key="menu.buscar" /></html:submit>
			</html:form>
	    </div>
	</div>
	
	<br/>
    
    <div id="wrapperResults" style="text-align: center">    
	    <div id="buscadorResults" style="display: inline-block;">
		    <table border="0" align="left" width="400px">    	
		    	<logic:iterate id="nodo" name="servicios" type="es.caib.regtel.model.ValorOrganismo">
		    	<tr>
		    		<td style="font-weight:bold;">
		    			<span class="txtBuscador"><bean:write name="nodo" property="codigo"/> - <bean:write name="nodo" property="descripcion"/></span> [<a href="javascript:seleccionar('<bean:write name="nodo" property="codigo"/>')"><bean:message key="boton.selec" /></a>] <br/>
		 	 		</td>
		 	 	</tr>
			 	</logic:iterate>
		 	</table>
	</div>
	
	<br/>

	<div id="wrapperReturn" style="text-align: center">    
	    <div id="buscadorReturn" style="display: inline-block;">		 	
		 	<p>
		 		<span class="txtBuscador">
		 			<html:link action="/arbolServicios" paramId="id"><bean:message key="boton.volver" /></html:link>
		 		</span>
		 	</p>
	    </div>
	</div>
     	
 
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="incidencias-messages" />
<html xmlns="http://www.w3.org/1999/xhtml" lang="${lang}" xml:lang="${lang}">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<title>Govern de les Illes Balears</title>
	<link href="css/estilos.css" rel="stylesheet" type="text/css" />
</head>
  <body>
  	
  	
	<div id="contenidor">
  		<div id="continguts">
  			
  		<form action="formulario" method="post" enctype="multipart/form-data">
		    	<input name="lang" type="hidden" value="${lang}"/>
		  		<input name="tramiteDesc" type="hidden" value="${param.tramiteDesc}"/>
		  		<input name="tramiteId" type="hidden" value="${param.tramiteId}"/>
		  		<input name="procedimientoId" type="hidden" value="${param.procedimientoId}"/>
		  		<input name="fechaCreacion" type="hidden" value=" ${param.fechaCreacion}"/>
		  		<input name="idPersistencia" type="hidden" value="${param.idPersistencia}"/>
				<table>
					<tbody>
						<tr>
							<th><fmt:message key="incidencias.nif"/></th>
							<td><input type="text" name="nif" value="${param.nif}" size="10"/></td>
						</tr>
						<tr>
							<th><fmt:message key="incidencias.nombre"/></th>
							<td><input name="nombre" type="text" value="${param.nombre}" size="40"/></td>
						</tr>
						<tr>
							<th><fmt:message key="incidencias.telefono"/></th>
							<td><input name="telefono" type="text" value="" size="10"/></td>
						</tr>
						<tr>
							<th><fmt:message key="incidencias.email"/></th>
							<td><input name="email" type="text" value="" size="40"/></td>
						</tr>
						<tr>
							<th><fmt:message key="incidencias.problemaTipo"/></th>
							<td>
								<select name="problemaTipo">
					  				<c:forEach var="entry" items="${problemasLista}">
					  					 <option value="${entry.key}">${entry.value}</option>				  
									</c:forEach>  			
								</select> 
							</td>
						</tr>
						<tr>
							<th><fmt:message key="incidencias.problemaDesc"/></th>
							<td><textarea name="problemaDesc" rows="4" cols="40"></textarea></td>
						</tr>
						<tr>
							<th><fmt:message key="incidencias.anexo"/></th>
							<td><input name=anexo type="file"/></td>
						</tr>	
						<tr>
							<td colspan="2">&nbsp;</td>							
						</tr>
						<tr>
							<td colspan="2" align="center"><input type="submit" value="<fmt:message key="incidencias.enviar"/>"></td>							
						</tr>								
					</tbody>			
				</table>				
			</form>
			
	  	</div>
  
	    
	  		
			
			
				  		
	  	</form>
  
  	</div>
  
  </body>
 </html>
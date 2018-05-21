<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	es.caib.sistra.persistence.delegate.ConfiguracionDelegate delegateF = es.caib.sistra.persistence.delegate.DelegateUtil.getConfiguracionDelegate();
	
	es.caib.sistra.model.OrganismoInfo infoOrg = delegateF.obtenerOrganismoInfo();
	
%>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="incidencias-messages" />
<html xmlns="http://www.w3.org/1999/xhtml" lang="${lang}" xml:lang="${lang}">
<script type="text/javascript">
<!--
function validaFormulario( form )
    {
		var filter  = /^\w+([\.\+\-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/;
		var filter2  = /^\+?\d{1,3}?[- .]?\(?(?:\d{2,3})\)?[- .]?\d\d\d[- .]?\d\d\d\d$/;
		if ( form.email.value == null || form.email.value == '' )
		{
			if ( form.telefono.value == null || form.telefono.value == '' )
			{
				alert( "<fmt:message key="incidencias.contactoVacio"/>" );	
				form.telefono.focus();
				return false;
			} else {
				if ( !filter2.test( form.telefono.value ) )
				{
					alert( "<fmt:message key="incidencias.telefonoIncorrecto"/>" );	
					form.telefono.focus();
					return false;
				}
			}
		} else {
			if ( !filter.test( form.email.value ) )
			{
				alert( "<fmt:message key="incidencias.emailIncorrecto"/>" );	
				form.email.focus();
				return false;
			}
		}
		
		if (form.horarioContacto.value == null || form.horarioContacto.value == '' ){
			alert( "<fmt:message key="incidencias.horarioContactoVacio"/>" );	
			form.horarioContacto.focus();
			return false;
		}
		
		if ( form.problemaDesc.value == null || form.problemaDesc.value == '' ){
			alert( "<fmt:message key="incidencias.descripcionVacia"/>" );	
			form.problemaDesc.focus();
			return false;
		}
		
		return true;
	}

-->
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<title><%=infoOrg.getNombre()%></title>
	<link href="css/estilos.css" rel="stylesheet" type="text/css" />
</head>
  <body>
  	
  	
	<div id="contenidor">
  		<div id="continguts">
  		<h2><fmt:message key="incidencias.tituloForm"/></h2>	
  		<form action="formulario" method="post" enctype="multipart/form-data">
		    	<input name="lang" type="hidden" value="${lang}"/>
		  		<input name="tramiteDesc" type="hidden" value="${param.tramiteDesc}"/>
		  		<input name="tramiteId" type="hidden" value="${param.tramiteId}"/>
		  		<input name="procedimientoId" type="hidden" value="${param.procedimientoId}"/>
		  		<input name="fechaCreacion" type="hidden" value=" ${param.fechaCreacion}"/>
		  		<input name="idPersistencia" type="hidden" value="${param.idPersistencia}"/>
		  		<input name="nivelAutenticacion" type="hidden" value="${param.nivelAutenticacion}"/>
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
							<th>* <fmt:message key="incidencias.telefono"/></th>
							<td><input name="telefono" type="text" value="" size="10"/></td>
						</tr>
						<tr>
							<th>* <fmt:message key="incidencias.email"/></th>
							<td><input name="email" type="text" value="" size="40"/></td>
						</tr>
						<tr>
							<th>* <fmt:message key="incidencias.horarioContacto"/></th>
							<td><input name="horarioContacto" type="text" value="" size="40"/></td>
						</tr>
                		<c:if test="${mostrarListaProcedimientos == 'S'}">
						<tr>
							<th>* <fmt:message key="incidencias.procedimientoSelec"/></th>
							<td>
								<select name="procedimientoSelec" style="width:272px;">
										<c:forEach var="procedimiento" items="${listaProcedimientos}">
                        					<option value="${procedimiento.identificador}">
                        					${procedimiento.descripcion}
                        					</option>
                    					</c:forEach>
            					</select>
							</td>
						</tr>
						</c:if> 
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
							<th>* <fmt:message key="incidencias.problemaDesc"/></th>
							<td><textarea name="problemaDesc" rows="4" cols="40"></textarea></td>
						</tr>
						<tr>
							<th><fmt:message key="incidencias.anexo"/></th>
							<td style="max-width:30px; overflow:hidden; white-space:nowrap;"><input name=anexo type="file"/></td>
						</tr>	
						<tr>
							<td colspan="2">&nbsp;</td>							
						</tr>
						<tr>
							<td colspan="2" align="center"><input type="submit" value="<fmt:message key="incidencias.enviar"/>" onclick="return validaFormulario( this.form );"></td>							
						</tr>								
					</tbody>			
				</table>				
			</form>
			
	  	</div>
  
	    
	  		
			
			
				  		
	  	</form>
  
  	</div>
  
  </body>
 </html>
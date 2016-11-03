<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<html:xhtml/>

	<script type="text/javascript">
	<!--
	
	 function asignarUsuario(){
	   var usuario =  document.getElementById("usuario").value;
	   if(usuario != null && usuario != ''){
		   var tramite = document.getElementById("idTramite").value;
		   $.ajaxSetup({scriptCharset: "utf-8" , contentType: "application/json; charset=utf-8"}); 
		   $.getJSON("asociarUsuario.do", { usuario: usuario, tramite : tramite},function(datos){
				$('#datosUsuarios').html(datos.taula);
			});
		}
	}	
	function eliminar(idUser){
		
	   	var tramite = document.getElementById("idTramite").value;
	   	var flag = "eliminar";
	   	$.ajaxSetup({scriptCharset: "utf-8" , contentType: "application/json; charset=utf-8"}); 
	   	$.getJSON("asociarUsuario.do", { usuario: idUser, tramite : tramite, flag: flag },function(datos){
			$('#datosUsuarios').html(datos.taula);
		});
	}
	//-->
	</script>
<tiles:useAttribute name="usuarios"/>

<br />
<br />
<table class="nomarc">
    <tr><td class="titulo"><bean:message key="grupos.usuario.asociado" /></td></tr>
</table>
<br />

<table class="marc">
    <tr>
    	<td class="label">
    		<bean:message key="grupo.usuario"/>
    	</td>
    	<td class="input">
			<html:text styleClass="textUser" name="tramiteForm" property="usuario" styleId="usuario" />				    
    		<button id="asignaUsuario" class="buttond" type="button" onclick="asignarUsuario()">
             		<bean:message key="boton.asociar" />
            </button>
    	</td>
    </tr>
</table>



<div id="datosUsuarios">

	<logic:empty name="usuarios">
	    <table class="marc">
	      <tr>
	      	<td class="alert"><bean:message key="grupo.usuarios.vacio" />
	      	</td>
	      </tr>
	    </table>
	</logic:empty>
	
	<logic:notEmpty name="usuarios">
	    <table class="marc">
		    <logic:iterate id="usuario" name="usuarios">
		    	<tr>
				    <td class="outputd"><bean:write name="usuario" property="id.codiUsuario" /></td>
				     <td align="left">
					     <bean:define id="idUsuario" name="usuario" property="id.codiUsuario"/>
        	             <button class="button" type="button" onclick="eliminar('<%=idUsuario%>')"><bean:message key="boton.baixa"/></button>
				     </td>
				</tr>
			</logic:iterate>
	    </table>
	</logic:notEmpty>
</div>











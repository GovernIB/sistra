
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="alta.grupo.title" /></title>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
   <script type="text/javascript" src="../../js/jquery-1.2.3.pack.js"></script>
   <script type="text/javascript" src="../../js/jquery.selectboxes.pack.js"></script>
   <script type="text/javascript">
    <!--
        <logic:present name="reloadMenu">
            parent.Menu.location.reload(true);
        </logic:present>
   
   
   function alta(){
		document.forms["0"].submit();
	}
	
	function cancela(){
		document.forms["0"].action='<html:rewrite page="/admin/grupo/lista.do"/>';
		document.forms["0"].submit();
	}
	function eliminar(idUser){
		var flag = "";
		if(document.getElementById("flag")){
	   		flag = document.getElementById("flag").value;
	   	}
	   	var grupo = document.forms["0"].codigo.value;
	   	$.ajaxSetup({scriptCharset: "utf-8" , contentType: "application/json; charset=utf-8"}); 
	   	$.getJSON("eliminarUsuario.do", { usuario: idUser, grupo : grupo , flag : flag},function(datos){
			$('#datosUsuarios').html(datos.taula);
		});
	}
	
	$().ready(function() {
	 $('#asigna').click(function() {
	 
	   var usuario =  document.getElementById("usuario").value;
	   if(usuario!=null && usuario!=''){
		   var flag = "";
		   if(document.getElementById("flag")){
		   		flag = document.getElementById("flag").value;
		   }
		    
		   var grupo = document.forms["0"].codigo.value;
		   $.ajaxSetup({scriptCharset: "utf-8" , contentType: "application/json; charset=utf-8"}); 
		   $.getJSON("asociarUsuario.do", { usuario: usuario, grupo : grupo , flag : flag},function(datos){
				$('#datosUsuarios').html(datos.taula);
			});
		}
	 });
	});
	//-->
   </script>
</head>

<body class="ventana">
<bean:define id="btnAlta" type="java.lang.String">
	<bean:message key="alta.grupo.guardar"/>
</bean:define>
<bean:define id="btnCancelar" type="java.lang.String">
	<bean:message key="boton.cancel"/>
</bean:define>
<!-- Muestra el arbol de navegación en la parte superior de la pantalla-->

<table class="marc">
    <tr><td class="titulo">
       <logic:present name="grupo">
		   <bean:message key="modificacion.grupo.title" />
       </logic:present>
       <logic:notPresent name="grupo">
      		<bean:message key="alta.grupo.title" /> 
       </logic:notPresent>
      
       
</table>
<br />

<logic:present name="alert">
<table class="marc">
    <tr><td class="alert"><bean:message key="alert" /></td></tr>
</table>
<br />
</logic:present>

<html:errors/>

<html:form action="/admin/grupo/guardar.do"  >
	<logic:present name="flagValidacion">
		<input type="hidden" id="flag" name="flagValidacion" value="<bean:write name='flagValidacion'/>"/>
	</logic:present>
    <table class="marc">
		<tr>
		    <td class="labelo"><bean:message key="grupo.codigo"/></td>
		    <td class="input">
		    <logic:present name="grupo">
   			    <html:text styleClass="text" tabindex="1" name="grupo" property="codigo" maxlength="50" readonly="true"/></td>
		    </logic:present>
		    <logic:notPresent name="grupo">
			    <html:text styleClass="text" tabindex="1" property="codigo" maxlength="50"/></td>		    	
		    </logic:notPresent>
		</tr>
		<tr>
		    <td class="labelo"><bean:message key="grupo.nombre"/></td>
		    <td class="input">
    		    <logic:present name="grupo">
				    <html:text styleClass="text" tabindex="2" name="grupo" property="nombre" />
				</logic:present>
				<logic:notPresent name="grupo">
				    <html:text styleClass="text" tabindex="2" property="nombre" />				    
				</logic:notPresent>
			</td>
		</tr>
		<tr>
		    <td class="label"><bean:message key="grupo.desc"/></td>
		    <td class="input">
   				<logic:notPresent name="grupo">
			    	<html:textarea styleClass="text" rows="4" tabindex="3" property="descripcion" />
   				</logic:notPresent>
       		    <logic:present name="grupo">
			    	<html:textarea styleClass="text" rows="4" tabindex="3" name="grupo" property="descripcion" />
			    </logic:present>
		    </td>
		</tr>
	</table>
    
    <table class="nomarc">
       <tr>
            <td align="left">
                <!--Si el formulario está bloqueado permitimos modificar -->
           	  
                <html:reset styleClass="button"><bean:message key="boton.reiniciar" /></html:reset>
                <html:submit  value="<%=btnAlta%>" onclick="alta();" styleClass="buttond"/>
            </td>
            <td align="right">
            	<html:submit value="<%=btnCancelar%>" onclick="cancela();" styleClass="button"/>  
            </td>
        </tr>
    </table>
	
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
			<html:text styleClass="textUser" tabindex="2" property="usuario" styleId="usuario" />				    
    		<button id="asigna" class="buttond" type="button" >
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
				    <td class="outputd"><bean:write name="usuario" property="id.usuario" /></td>
				     <td align="left">
					     <bean:define id="idUsuario" name="usuario" property="id.usuario"/>
        	             <button class="button" type="button" onclick="eliminar('<%=idUsuario%>')"><bean:message key="boton.baixa"/></button>
				     </td>
				</tr>
			</logic:iterate>
	    </table>
	</logic:notEmpty>
</div>

</html:form>

</body>
</html:html>
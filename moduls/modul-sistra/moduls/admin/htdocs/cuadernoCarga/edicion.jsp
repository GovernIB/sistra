<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib uri="back" prefix="back"%>
<script type="text/javascript">
     <!--
     function viewAyudaExpresion( type ) {
        var url = '<html:rewrite page="/cuadernoCarga/ayudaPantalla.jsp" />?type=' + type ;
        obrir(url, "Edicion", 540, 400);
     }
     function viewAyudaAuditor()
     {
     	viewAyudaExpresion( 'auditor' );
     }
     function viewAyudaDevelopper()
     {
     	viewAyudaExpresion( 'dev' );
     }
     
     // -->
</script>
<bean:define id="urlEditarText">
    <html:rewrite page="/editarText.jsp"/>
</bean:define>

<bean:define id="urlAuditar"><html:rewrite page="/admin/cuadernoCarga/auditar.do" paramId="codigo" paramName="cuadernoCargaForm" paramProperty="codigo"/></bean:define>
<bean:define id="urlBaja"><html:rewrite page="/admin/cuadernoCarga/baja.do" paramId="codigo" paramName="cuadernoCargaForm" paramProperty="codigo"/></bean:define>
<bean:define id="urlCancelPendientesDesarrollador"><html:rewrite page="/admin/cuadernoCarga/pendientesDesarrollador.do" /></bean:define>
<bean:define id="urlCancelPendientesAuditor"><html:rewrite page="/admin/cuadernoCarga/pendientesAuditor.do" /></bean:define>
<bean:define id="urlCancelAuditados"><html:rewrite page="/admin/cuadernoCarga/auditados.do" /></bean:define>
<bean:define id="urlCancelFinalizados"><html:rewrite page="/admin/cuadernoCarga/finalizados.do" /></bean:define>
<bean:define id="urlCancelPendientes"><html:rewrite page="/admin/cuadernoCarga/pendientes.do" /></bean:define>
<bean:define id="urlCancel" type="java.lang.String" value=""/>
<bean:define id="isAlta" value="true" />

<logic:present name="cuadernoCargaForm" property="codigo">
<%
	isAlta = "false";
%>	
</logic:present>
<%--
 Parametros tiles:
    title: key del título de la página.
    alta.title: key del título de la acción alta
    modificacion.title: key del título de la acción modificación
    subtitulo: key del subtitulo
    validateMethod: Nombre del metodo de validación
    form.action: Action a la que se realiza el post.
    form.bean: Nombre del form.
    
    paginaValues: pagina que muestra los campos generales a editar.
    paginaTraduccio: pagina que muestra los campos traducibles a editar.
    paginaTramite: pagina que muestra los campos de un tramite
    
 --%>
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="cuadernoCarga.title"/></title>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
   <script type="text/javascript">
   <!--
		function edit(url) {
			obrir(url, "Edicion", 940, 600);
		}
   
        function reload(lang) {
            document.forms[0].select.value = lang;
            if ( validar(document.forms[0]) ) {
                document.forms[0].submit();
            }
        }

        function validar(form) {
            return validateCuadernoCargaForm(form);
        }
        
        function baja( url )
        {
			document.location.href = url;
        }

        function swichDisplay(name) {
            var elStyle = document.getElementById(name).style;
            if (elStyle.display == 'none') {
                elStyle.display = 'block';
                try { // table-row per els qui ho soportin, els altres (IE) toquen funcionar amb block.
                    elStyle.display = 'table-row';
                } catch (ex) { ; }
            } else {
            }
         }
         function rechazar( form )
         {
         	return finalizarGestion( form, "<bean:message key='cuadernoCarga.rechazar.mensaje' />", "R" ); 
         }
         function aceptar( form )
         {
         	return finalizarGestion( form, "<bean:message key='cuadernoCarga.aceptar.mensaje' />", "A" ); 
         }
         function finalizarGestion( form, message, estado )
         {
         	form.action = form.action + '?es.caib.sistra.admin.taglib.MODIFICACIO=true';
         	form.estadoAuditoria.value = estado;
         	if ( confirm( message ) )
         	{
         		if( validar( form ) )
         		{
         			form.submit();
         			return true;
         		}
         	}
         	return false;
         }
         
   //-->
   </script>
</head>

<body class="ventana">

<table class="marc">
    <tr><td class="titulo">
        <logic:notPresent name="cuadernoCargaForm" property="codigo">
            <bean:message key="cuadernoCarga.alta.title" />
        </logic:notPresent>
        <logic:present name="cuadernoCargaForm" property="codigo">
            <bean:message key="cuadernoCarga.modificacion.title" />
        </logic:present>        
    </td></tr>
</table>

<logic:present name="alert">
<table class="marc">
    <tr><td class="alert"><bean:message key="alert" /></td></tr>
</table>
<br />
</logic:present>

<html:errors/>

    
    <table class="nomarc">
    
	    <html:form action="/admin/cuadernoCarga/editar" styleId="cuadernoCargaForm">
	    <html:hidden property="codigo" />
		<bean:define id="estadoAuditoria" name="cuadernoCarga" property="estadoAuditoria"/>			  	    
		<bean:define id="importado" name="cuadernoCarga" property="importado"/>			  	    
		<%
			boolean isAdmin = (( Boolean ) request.getAttribute( "isAdmin" )).booleanValue();
			boolean enabled = isAdmin &&  ( "A".equals( estadoAuditoria.toString() ) || "N".equals( estadoAuditoria.toString() ) )  ;
			boolean edicionFechaCarga = isAdmin &&  ( "I".equals( estadoAuditoria.toString() ) )   ;
			
			urlCancel = "true".equals(isAlta) ? 
				urlCancelPendientesDesarrollador : 
			( isAdmin ? 
				( ( "I".equals( estadoAuditoria.toString() ) || "P".equals( estadoAuditoria.toString() ) || 
							( ( "A".equals( estadoAuditoria.toString() ) || "N".equals( estadoAuditoria.toString() ) ) 
									&& "N".equals( importado.toString() ) ) 
				   ) ?  
						urlCancelPendientesDesarrollador : urlCancelFinalizados ) 
				: ( "P".equals( estadoAuditoria.toString() ) ? urlCancelPendientesAuditor : urlCancelAuditados )
					
			);
			
			boolean modificarCuadernoCarga = 
				isAdmin && 
				( "I".equals( estadoAuditoria.toString() ) 
				|| ( "A".equals( estadoAuditoria.toString() ) || "N".equals( estadoAuditoria.toString() ) ) && "N".equals( importado.toString() ) 
				) ? 
					true :  
					!isAdmin && ( "P".equals( estadoAuditoria.toString() ) );
			
		%>		
        <table class="marc">
        	<tr>
			    <td class="labelo"><bean:message key="cuadernoCarga.descripcion"/></td>
			    <td class="input"><html:text styleClass="text" tabindex="1" property="descripcion" maxlength="100" size="102"/></td>
			</tr>
			<tr>
			    <td class="label"><bean:message key="cuadernoCarga.fechaAlta"/></td>
			    <td class="input"><bean:write name="cuadernoCarga" property="fechaAlta" format="dd/MM/yyyy - HH:mm:ss" /></td>
			</tr>
			<tr>
			    <td class="label"><bean:message key="cuadernoCarga.fechaCarga"/></td>
			    <logic:equal name="isAuditor" value="true">
			    	<td class="input">
			    		<bean:write name="cuadernoCarga" property="fechaCarga" format="dd/MM/yyyy" /> (dd/MM/yyyy)
			    		<html:hidden property="rawFechaCarga" />
			    	</td>
			    </logic:equal>
			    <logic:equal name="isAdmin" value="true">
			    	<td class="input">			    
			    		<html:text styleClass="text" tabindex="1" property="rawFechaCarga" maxlength="10" size="12" readonly="<%= !edicionFechaCarga %>"/>
			    	</td>
			    </logic:equal>
			</tr>
			<tr>
			    <td class="label"><bean:message key="cuadernoCarga.estadoAuditoria"/></td>
		    	<td class="input">
		    		<bean:message key='<%= "cuadernoCarga.estado." + estadoAuditoria.toString() %>' />
		    		<html:hidden property="estadoAuditoria"/>
		    	</td>
			    <%-- logic:equal name="isAdmin" value="false">
			    	<td class="input">
			    		<html:select property="estadoAuditoria">
			    			<html:option value="I" key="cuadernoCarga.estado.I" />
			    			<html:option value="P" key="cuadernoCarga.estado.P" />
			    			<html:option value="A" key="cuadernoCarga.estado.A" />
			    			<html:option value="N" key="cuadernoCarga.estado.N" />
			    			<html:option value="R" key="cuadernoCarga.estado.R" />
			    		</html:select>
			    	</td>
			    </logic:equal--%>	
			</tr>
			<tr>
			    <td class="label"><bean:message key="cuadernoCarga.fechaAuditoria"/></td>
			    <td class="input"><bean:write name="cuadernoCarga" property="fechaAuditoria" format="dd/MM/yyyy - HH:mm:ss" /></td>
			</tr>			
			<tr>
			    <td class="label"><bean:message key="cuadernoCarga.comentarioAuditoria"/></td>
			    <td class="input">
			    	<html:textarea styleClass="text" tabindex="10" property="comentarioAuditoria" readonly="<%=   ( ( Boolean ) request.getAttribute( \"isAdmin\" ) ).booleanValue() %>"/>
			    	<logic:equal name="isAudit" value="true">
			    		<input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=comentarioAuditoria&titulo=cuadernoCarga.comentarioAuditoria" %>');" />
			    	</logic:equal>	
			    </td>
			</tr>
			<tr>
			    <td class="label"><bean:message key="cuadernoCarga.importado"/></td>
			    <td class="input"><html:checkbox property="importado" value="S" disabled="<%= !enabled %>"/></td>
			</tr>						    
        </table>
        <br />
       <tr>
           <td align="left">
           		<logic:present name="cuadernoCargaForm" property="codigo">
                	<%
                	if ( modificarCuadernoCarga )
                	{
                	%>
  	           		<logic:equal name="isAdmin" value="true">
	           			<back:accio tipus="modificacio" styleClass="button" onclick="return validar(this.form);" />
	           		</logic:equal>
	           		<logic:equal name="isAuditor" value="true">
	           			<button class="buttond" type="button" onclick="return aceptar( this.form )"><bean:message key="cuadernoCarga.aceptar" /></button>
	           			<button class="buttond" type="button" onclick="return rechazar( this.form )"><bean:message key="cuadernoCarga.rechazar" /></button>
	           		</logic:equal>	
                    <%
                    }
                    %>
           		</logic:present>
                <logic:notPresent name="cuadernoCargaForm" property="codigo">
                    <back:accio tipus="alta" styleClass="button" onclick="return validar(this.form);" />
                </logic:notPresent>           
           </td>
           <td align="right">
           		<bean:define id="mensajeBaja"><bean:message key='messatge.baja' /></bean:define>                                                           
           		<logic:equal name="isAdmin" value="true">
					<button class="button" type="button" onclick="confirmAndForward('<%= StringUtils.escape( mensajeBaja ) %>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>           		
				</logic:equal>	
           		<logic:equal name="isAuditor" value="true">
           			<button class="buttond" type="button" onclick="forward('<%= urlAuditar %>') "><bean:message key="boton.auditar" /></button>
           		</logic:equal>
                <%-- html:cancel styleClass="button" ><bean:message key="boton.cancel" /></html:cancel> --%>
                <button class="button" type="button" onclick="forward('<%= urlCancel %>') "><bean:message key="boton.cancel" /></button>
                &nbsp;
                <logic:equal name="isAdmin" value="true">
					<button class="button" type="button" onclick="viewAyudaDevelopper()"><bean:message key="boton.ayuda.expresion" /></button>
				</logic:equal>
				<logic:equal name="isAuditor" value="true">
   					<button class="button" type="button" onclick="viewAyudaAuditor()"><bean:message key="boton.ayuda.expresion" /></button>                
       			</logic:equal>
           </td>
       </tr>

        </html:form>
    </table>
    
    <logic:present name="cuadernoCargaForm" property="codigo">
    	<bean:define id="codigoCuaderno" name="cuadernoCargaForm" property="codigo"/>
    	<bean:define id="urlAltaFicheroCuaderno">
    		<html:rewrite page="/admin/ficheroCuaderno/alta.do" paramId="codigoCuaderno" paramName="cuadernoCargaForm" paramProperty="codigo" />
    	</bean:define>    
    	<bean:define id="mensajeBaja"><bean:message key='cuadernoCarga.ficheroCuaderno.baja' /></bean:define>
    	<table class="nomarc">
		    <tr><td class="titulo"><bean:message key="cuadernoCarga.ficherosCuaderno.asociados" /></td></tr>
		</table>
		<br />		
		<table class="marc">
			<logic:empty name="cuadernoCarga" property="ficheros">
		      <tr><td class="alert"><bean:message key="cuadernoCarga.ficherosCuaderno.vacio" /></td></tr>
			</logic:empty>
	    	<logic:iterate id="fichero" name="cuadernoCarga" property="ficheros">
		    	<bean:define id="urlSeleccionFicheroCuaderno">
		    		<html:rewrite page="/admin/ficheroCuaderno/seleccion.do" paramId="codigoFichero" paramName="fichero" paramProperty="codigo" />
		    	</bean:define>
		    	<bean:define id="urlBajaFicheroCuaderno">
		    		<html:rewrite page="/admin/ficheroCuaderno/baja.do" paramId="codigoFichero" paramName="fichero" paramProperty="codigo" />
		    	</bean:define>
		    	<bean:define id="urlDescargaFicheroCuaderno">
		    		<html:rewrite page="/admin/ficheroCuaderno/descarga.do" paramId="codigoFichero" paramName="fichero" paramProperty="codigo" />
		    	</bean:define>

		    	<bean:define id="tipoFichero" name="fichero" property="tipo" type="java.lang.String" />
		    <tr>	
                <td class="outputd" width="70%" >
                	<bean:message key='<%="cuadernoCarga.ficherosCuaderno.tipo" + tipoFichero  %>' /> <bean:write name="fichero" property="identifier" />
                </td>
                <td align="right">
                    <logic:equal name="isAdmin" value="true">
                	<button class="button" type="button" onclick="forward('<%=urlSeleccionFicheroCuaderno%>&codigoCuaderno=<%=codigoCuaderno.toString() %>')"><bean:message key="boton.selec" /></button>
               		<button class="button" type="button" onclick="confirmAndForward('<%= StringUtils.escape( mensajeBaja )%>', '<%=urlBajaFicheroCuaderno%>&codigo=<%=codigoCuaderno.toString() %>')"><bean:message key="boton.baixa" /></button>
		             </logic:equal>
						<button class="button" type="button" onclick="forward('<%= urlDescargaFicheroCuaderno %>')"><bean:message key="boton.descarga" /></button>               		
                </td>	
            </tr>    
	    	</logic:iterate>
    	</table>
        <logic:equal name="isAdmin" value="true">    	
        	<logic:equal name="cuadernoCarga" property="estadoAuditoria" value="I">					
		    	<table class="nomarc">
				  <tr>
				  <td align="right">
				    <button class="buttond" type="button" onclick="forward('<%=urlAltaFicheroCuaderno%>')">
				        <bean:message key="boton.nuevo" />
				    </button>
				  </td>
				  </tr>
				</table>
			 </logic:equal>	
		</logic:equal>
    	
    </logic:present>

<!-- XAPUÇA -->
<% pageContext.removeAttribute(Globals.XHTML_KEY);%>
<html:javascript
    formName="cuadernoCargaForm"
    dynamicJavascript="true"
    staticJavascript="false"
    htmlComment="true"
    cdata="false"
 />
<% pageContext.setAttribute(Globals.XHTML_KEY, "true");%>
</body>
</html:html>
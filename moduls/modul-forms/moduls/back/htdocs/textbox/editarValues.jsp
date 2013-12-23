<%@ page language="java" %>
<%@ page import="org.ibit.rol.form.back.util.Util"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<script type="text/javascript">
    <!--
          function viewAyuda() {
            var url = '<html:rewrite page="/textbox/ayudaMascara.jsp" />';
            obrir(url, "Edicion", 540, 400);
         }

    	function ajustarColspan() {
        	var filasSelect = document.getElementById("filas");
        	var colspanSelect = document.getElementById("colSpan");
        	var filas = filasSelect.options[filasSelect.selectedIndex].value;
        	var colspan = colspanSelect.options[colspanSelect.selectedIndex].value;
        	
        	if (isNaN(filas)) {
            	filas = 1;
        	}

			var minColSpan = 1;
        	
        	if (filas > 1) {
            	minColSpan = 3;
        	}
       
        	while (colspanSelect.length > 0) {
       	    	colspanSelect.remove(0);
       	    }

        	for (var i = minColSpan; i <= 6; i++) {
       	        var opcio = new Option(i, i);
       	     	colspanSelect.add(opcio);       	        
       	    }	 

			if (colspan >= minColSpan){
				colspanSelect.value = colspan;
			}
	           	
    	}
   //-->
</script>
<% int ti = 1; int minColSpan = 1;%>
<logic:notEmpty name="textboxForm" property="values.colSpan">
	<logic:greaterThan name="textboxForm" property="values.filas" value="1">
		<% minColSpan = 3; %>
	</logic:greaterThan> 
</logic:notEmpty>

<html:hidden property="idPantalla" />
<html:hidden property="pantallaDetalle" />
<input type="hidden" name="idOperacion" value="<%=Util.getIdOperacion(request)%>"/>	
<tr>
    <td class="labelo"><bean:message key="componente.nombreLogico"/></td>
    <td class="input"><html:text styleClass="text" tabindex="<%=Integer.toString(ti++)%>" property="values.nombreLogico" maxlength="128" /></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.nombrelogico"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.posicion"/></td>
    <td class="input">
    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.posicion">
        <html:option value="1"><bean:message key="componente.posicion.1" /></html:option>
        <html:option value="0"><bean:message key="componente.posicion.0" /></html:option>
        <html:option value="2"><bean:message key="componente.posicion.2" /></html:option>
        <html:option value="3"><bean:message key="componente.posicion.3" /></html:option>
    </html:select>
</tr>
<tr>
    <td class="label"><bean:message key="componente.colSpan"/></td>
    <td class="input">
	    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.colSpan" styleId="colSpan">
	    	<% for (int i = minColSpan ; i <= 6; i++) { %>															
				<html:option value="<%=Integer.toString(i)%>"><bean:message key="<%=\"componente.colSpan.\" + i%>"/></html:option>
			<% } %>       
	    </html:select>
	     - <bean:message key="textbox.colspan.restriccion"/>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.sinEtiqueta"/></td>
    <td class="input">
    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.sinEtiqueta">
        <html:option value="false"><bean:message key="componente.sinEtiqueta.conEtiqueta" /></html:option>
        <html:option value="true"><bean:message key="componente.sinEtiqueta.sinEtiqueta" /></html:option>          
    </html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.alineacion"/></td>
    <td class="input">
    <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.alineacion">
        <html:option value="I"><bean:message key="componente.alineacion.izquierda" /></html:option>
        <html:option value="C"><bean:message key="componente.alineacion.centro" /></html:option>       
        <html:option value="D"><bean:message key="componente.alineacion.derecha" /></html:option>         
    </html:select>
    </td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.posicion"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="textbox.oculto"/></td>
    <td class="input"><html:checkbox styleClass="check" tabindex="<%=Integer.toString(ti++)%>" property="values.oculto" /></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.oculto"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="textbox.filas"/></td>
    <td class="input">
    	 <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.filas" styleId="filas" onchange="ajustarColspan()">
			<% for (int i = 1 ; i <= 999; i++) { %>															
				<html:option value="<%=Integer.toString(i)%>"><%=i%></html:option>
			<% } %>
		</html:select>    			
    </td>
</tr>
<tr>
    <td class="labelo"><bean:message key="textbox.columnas"/></td>
    <td class="input">
    	 <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.columnas">
			<% for (int i = 1 ; i <= 999; i++) { %>															
				<html:option value="<%=Integer.toString(i)%>"><%=i%></html:option>
			<% } %>
		</html:select>     	
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.multilinea"/></td>
    <td class="input"><html:checkbox styleClass="check" tabindex="<%=Integer.toString(ti++)%>" property="values.multilinea" /></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.multilinea"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.pdf"/></td>
    <td class="input"><html:text styleClass="text" tabindex="<%=Integer.toString(ti++)%>" property="values.etiquetaPDF" maxlength="128" /></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.etiquetaPDF"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.tipovalor"/></td>
    <td class="input">
        <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.tipoValor" >
            <html:option value="java.lang.String"><bean:message key="componente.java.lang.String" /></html:option>
            <html:option value="java.lang.Integer"><bean:message key="componente.java.lang.Integer" /></html:option>
            <html:option value="java.lang.Float"><bean:message key="componente.java.lang.Float" /></html:option>
        </html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="textbox.patron"/></td>
    <td class="input">
        <html:select tabindex="<%=Integer.toString(ti++)%>" property="idPatron" >
            <html:option value="0">- <bean:message key="componente.patron.no" /> -</html:option>
            <html:options collection="patronOptions"
                            property="id"
                       labelProperty="nombre"
            />
        </html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="textbox.tipo"/></td>
    <td class="input">
        <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.tipoTexto" >
        	<html:option value="NO"><bean:message key="textbox.tipo.normal" /></html:option>
            <html:option value="FE"><bean:message key="textbox.tipo.fecha" /></html:option>
            <html:option value="HO"><bean:message key="textbox.tipo.hora" /></html:option>
            <html:option value="NU"><bean:message key="textbox.tipo.numero" /></html:option>
            <html:option value="IM"><bean:message key="textbox.tipo.importe" /></html:option>
        </html:select>
    </td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.tipoValor"/></td>
</tr>

<tr>
    <td class="label"><bean:message key="componente.encuadrar.marcar"/></td>
    <td class="input">
    	 <html:select tabindex="<%=Integer.toString(ti++)%>" property="values.encuadrar">
	        <html:option value="true"><bean:message key="componente.encuadrar.si" /></html:option>
	        <html:option value="false"><bean:message key="componente.encuadrar.no" /></html:option>                
	    </html:select>
    </td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.encuadrar"/></td>
</tr>

<!--  INDRA: PROPIEDADES PARA CAMPOS DE PANTALLAS DE DETALLE DE LISTA DE ELEMENTOS -->
<logic:equal name="textboxForm" property="pantallaDetalle" value="true">
<tr>
    <td class="label"><bean:message key="componente.mostrarEnTabla"/></td>
    <td class="input"><html:checkbox styleClass="check" tabindex="<%=Integer.toString(ti++)%>" property="values.mostrarEnTabla" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="componente.anchoColumna"/></td>
    <td class="input"><html:text styleClass="text" tabindex="<%=Integer.toString(ti++)%>" property="values.anchoColumna" maxlength="3" /></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.mostrarListaElementos"/></td>
</tr>
</logic:equal>
<!--  INDRA: PROPIEDADES PARA CAMPOS DE PANTALLAS DE DETALLE DE LISTA DE ELEMENTOS -->

<tiles:insert page="/moduls/editarExpresiones.jsp">
    <tiles:put name="tabindex" value="<%=new Integer(ti)%>" />
</tiles:insert>
<% ti += 5; %>
<script type="text/javascript">
     <!--
     function switchValidaciones() {
        swichDisplay('trShowMask');
        swichDisplay('trMask');
        swichDisplay('trHideMask');
     }
     // -->
</script>
<tr id="trShowMask">
    <td class="label" colspan="2">
        <button class="buttonl" type="button" onclick="javascript:switchValidaciones()">
            <bean:message key="boton.showValidaciones"/>
        </button>
    </td>
</tr>
<tr id="trMask" style="display: none;">
    <td class="label"><bean:message key="textbox.validaciones"/></td>
    <td class="input">
        <table class="marcd">
            <logic:iterate id="validacion" name="textboxForm" property="validacion" indexId="index1">
            <tr>
                <bean:define id="etiqueta" name="validacion" property="nombre" type="java.lang.String"/>
                <td class="label"><bean:message key="<%=etiqueta%>"/></td>
                <td>
                    <html:checkbox styleClass="check" name="validacion" property="activo" indexed="true" tabindex="<%=Integer.toString(ti++)%>" />
                    <logic:notEmpty name="validacion" property="valores">
                        <logic:iterate id="valores" name="validacion" property="valores" indexId="index2">
                            <logic:notEmpty name="validacion"  property='<%="valores[" + index2 + "]"%>'>
                                <bean:define id="content" name="validacion" property='<%="valores[" + index2 + "]"%>' />
                                <input type="text" class="t50" name='<%="validacion["+ index1 +"].valores["+ index2 +"]"%>' value='<%=content%>' tabindex="<%=Integer.toString(ti++)%>" />
                            </logic:notEmpty>
                            <logic:empty name="validacion" property='<%="valores[" + index2 + "]"%>'>
                                <input type="text" class="t50" name='<%="validacion["+ index1 +"].valores["+ index2 +"]"%>' tabindex="<%=Integer.toString(ti++)%>" />
                            </logic:empty>
                        </logic:iterate>
                    </logic:notEmpty>
                </td>
            </tr>
            </logic:iterate>
            <tr>
                <td>
                    <input type="button" class="buttond" value='<bean:message key="boton.ayuda.mascara" />' onclick="viewAyuda()" />
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr id="trHideMask" style="display: none;">
    <td class="label" colspan="2">
        <button class="buttonl" type="button" onclick="javascript:switchValidaciones()">
            <bean:message key="boton.hideValidaciones"/>
        </button>
    </td>
</tr>

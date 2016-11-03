<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.xml.datospropios.factoria.ConstantesDatosPropiosXML" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="es.caib.zonaper.back.Constants"%>
<%@ page import="es.caib.zonaper.model.ValorDominio"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="oficinas" name="oficinas" type="java.util.List" />
<bean:define id="tiposAsunto" name="tiposAsunto" type="java.util.List" />
<bean:define id="idiomas" name="idiomas" type="java.util.List" />
<bean:define id="municipiosBaleares" name="municipiosBaleares" type="java.util.List" />

<bean:define id="oficina" name="oficina" type="java.lang.String" />
<bean:define id="tipoAsunto" name="tipoAsunto" type="java.lang.String" />
<bean:define id="idiomaAsunto" name="idiomaAsunto" type="java.lang.String" />
<bean:define id="codigoOrganoDestino" name="codigoOrganoDestino" type="java.lang.String" />
<bean:define id="descripcionOrganoDestino" name="descripcionOrganoDestino" type="java.lang.String" />
<bean:define id="interesado" name="interesado" type="java.lang.String" />

<bean:define id="esBaleares" name="esBaleares" type="java.lang.Boolean" />
<bean:define id="municipioBaleares" name="municipioBaleares" type="java.lang.String" />
<bean:define id="fueraBaleares" name="fueraBaleares" type="java.lang.String" />

<bean:define id="messageAyuda" value="ayuda.datosRegistro" />
<bean:define id="urlAyuda" type="java.lang.String">
	<html:rewrite page="/ayuda.do" paramId="<%= Constants.MESSAGE_KEY %>" paramName="messageAyuda"/>
</bean:define>
<script type="text/javascript">
<!--
function changeProcedenciaGeograficaFuera( form )
{
	var municipioCaibOption = form.municipioBaleares; 
	var municipioCaib = municipioCaibOption.options[ municipioCaibOption.selectedIndex ].value;
	var municipioFuera = form.municipioFuera.value;
	if ( municipioFuera != '' )
	{
		municipioCaibOption.options[0].selected=true;
	}
}

function changeProcedenciaGeograficaBalears( form )
{
	var municipioCaibOption = form.municipioBaleares; 
	var municipioCaib = municipioCaibOption.options[ municipioCaibOption.selectedIndex ].value;
	var municipioFuera = form.municipioFuera.value;
	if ( municipioCaib != '' )
	{
		form.municipioFuera.value='';
	}
}

function enviarFormulario( form )
{

	var oficinaList = form.oficina;
	var oficina = '';
	if (oficinaList.options.length > 0) {
	 oficina = oficinaList.options[ oficinaList.selectedIndex ].value;
	}
	if ( oficina == null || oficina == '' )
	{
		alert( '<bean:message key="datosRegistroEntrada.mensajeOficina" />' );
		oficinaList.focus();
		return;
	}
	
	var municipioCaibOption = form.municipioBaleares; 
	var municipioCaib = municipioCaibOption.options[ municipioCaibOption.selectedIndex ].value;
	var municipioFuera = form.municipioFuera.value;
	if ( ( municipioCaib == null || municipioCaib == '' ) && municipioFuera == '' )
	{
		alert( '<bean:message key="datosRegistroEntrada.mensajeProcedenciaGeografica" />' );
		municipioCaibOption.focus();
		return;
	}

	
	form.submit();
}
-->
</script>
<%! 
	void escribeSelect(javax.servlet.jsp.JspWriter out, String tipo, List valores, String referencia) throws java.io.IOException {

        for ( int i = 0; i < valores.size(); i++ ){
        	
        	String codigo = null;
       		String descripcion = null;
       			
			ValorDominio row = ( ValorDominio ) valores.get( i );
        	codigo = row.getCodigo();
        	descripcion = row.getDescripcion();
        	
            out.write("<option value=\""+codigo+"\" "+ (codigo.equals(referencia) ? "selected" : "")+">");
            if (tipo.equals("N")) {
                out.write(descripcion);
            } else {
                out.write(codigo+" - "+descripcion);
            }
            out.write("</option>\n");
        }
    }
%>
		<p align="right"><html:link href="#" onclick="<%= \"javascript:obrir('\" + urlAyuda + \"', 'Edicion', 540, 400);\"%>"><img src="imgs/icones/ico_ayuda.gif" border="0"/><bean:message key="ayuda.enlace" /></html:link></p>
        
        <!-- Cuerpo central -->

        <center>  
        <table border=1 width="599" bordercolor="#0000FF">
            <html:form action="detallePreregistro">
            <html:hidden property="codigo" name="preregistro" />
            <input type="hidden" name="serie" value="0">
            <tr>
                <td>
                    <!-- Tabla para datos de cabecera -->
                    <table class="bordeEntrada" style="border:0">
                        <tr>
                            <td style="border:0" width="60%">
                                <!-- Data d'entrada -->
                                <font class=""> <bean:message key="datosRegistroEntrada.dataEntrada" /> :</font>
                                <input id="data" disabled="true" type=text name=dataentrada value='<bean:write name="fechaEntrada"/>' size="10" readonly="true">
                            </td>                         
                        </tr>
                        <tr>
                            <td style="border:0" colspan="2">
                                <!-- Despegable para oficinas autorizadas para el usuario -->
                                &nbsp;<br><font class=""><bean:message key="datosRegistroEntrada.oficina" />:</font>
                                <select name="oficina">
                                    <% escribeSelect(out, "S", oficinas, oficina ); %>
                                </select>
                            </td>
                        </tr>
                    </table>
                    <!-- De la tabla principal -->
                </td>
            </tr>
            <tr>
            <td>
            <!-- Tabla para los datos del documentos -->
            <table class="bordeEntrada" style="border:0;">
                <!-- 1ª fila de la tabla -->
                <tr>
	                <td style="border:0;" colspan="2">
	               	 &nbsp;<br><b><bean:message key="datosRegistroEntrada.datosDocumento" /></b><p>
	                </TD>
                </TR>
                <!-- 2ª fila de la tabla -->  
                <tr>
                    <!-- Fecha del documento -->
                    <td style="border:0;" colspan="2">
                        <!-- Despegable para Tipos de documentos -->
                        &nbsp;<font class="errorcampo">*</font>
                        <font class=""><bean:message key="datosRegistroEntrada.tipusDocument" />: </font>
                        <select name="tipo" size="1" disabled="true">
							<% escribeSelect(out, "N", tiposAsunto, tipoAsunto); %>                            
                        </select>
                        <!-- Despegable para Idiomas -->
                        <font class=""><bean:message key="datosRegistroEntrada.idioma" />:</font>
                        <select name="idioma" size="1" disabled="true">
                            <% escribeSelect(out, "N", idiomas, idiomaAsunto); %>
                        </select>
                    </td>
                </tr>
                <!-- 3ª fila de la tabla -->
                <tr>
                <!-- Remitente -->
	                <td width="55%">
	                	&nbsp;<br><font class="errorcampo">*</font>
		                <bean:message key="datosRegistroEntrada.remitent" />
		                <input type=text name=altres size="30" value='<bean:write name="interesado"  />' disabled="true">
	                </TD>
                </TR>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <!-- 5ª fila de la tabla -->
                <tr>
                    <td style="border:0;" colspan="2">
                    <table>
                        <tr>
                            <td valign="middle">
                                <!-- Procedencia geografica -->
                                <font class="errorcampo">*</font>
                                <bean:message key="datosRegistroEntrada.procedenciaGeografica" />&nbsp;&nbsp;
                            </td>
                            <td valign="middle">
                                <!-- Despegable para la Procedencia Geografica de Baleares -->
                                <span class=""><bean:message key="datosRegistroEntrada.balears" />:
                            </td>
                            <td>
	                            <html:select property="municipioBaleares" onchange="javascript:changeProcedenciaGeograficaBalears(this.form);" >
	                            	<% escribeSelect(out, "N", municipiosBaleares, municipioBaleares ); %>
	                            </html:select>
                            </TD>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td valign="bottom" colspan="2">
                                <bean:message key="datosRegistroEntrada.foraBalears" />:&nbsp;
                             <logic:equal name="esBaleares" value="true">   
                                <html:text property="municipioFuera" size="25" maxlength="25" value="" onchange="javascript:changeProcedenciaGeograficaFuera(this.form);"/>
                             </logic:equal>
                             <logic:notEqual name="esBaleares" value="true">
                             	<html:text property="municipioFuera" size="25" maxlength="25" value="<%= fueraBaleares %>"  onchange="javascript:changeProcedenciaGeograficaFuera(this.form);"/>
                             </logic:notEqual>   
                            </td>
                        </tr>
                    </table>
                    </td>
                </tr>
                <!-- 8ª fila de la tabla -->
                <tr>
                    <td style="border:0;">
                    	<!-- Organismo destinatario -->
	                    &nbsp;<br><font class="errorcampo">*</font><font class=""><bean:message key="datosRegistroEntrada.organismeDestinatari" />:</font>
	                    <input id="desti" type="text" name="destinatari" size="15" value='<bean:write name="codigoOrganoDestino" />' disabled="true">
                        <!--  <img src="imgs/registro/buscar.gif" align=middle alt="<bean:message key="datosRegistroEntrada.cercar" />" border="0">  -->
                    </td>
                    <td>
                    <div id="destinatario_desc" style="font-size:10px; font: bold;"><bean:write name="descripcionOrganoDestino" /></div>
                    </td>
                </tr>
            </table>
                </td>
            </tr>
            <tr>
            <td>
            <!-- tabla de datos del Extracto -->
            <table class="bordeEntrada" style="border:0;" >
                <tr>
                <td style="border:0;">
                &nbsp;<br><b><bean:message key="datosRegistroEntrada.datosExtracto" /></b>
                </TD>
                </TR>                           
                <tr>
                    <td style="border:0;">
                        &nbsp;<br>
                        <!-- Extracto del documento -->
                        <font class="errorcampo">*</font>
                        <font class=""><bean:message key="datosRegistroEntrada.extracteDocument" />:
                        <textarea cols="67" onkeypress="return check(event)" rows="3" name="comentario" disabled="true"><bean:write name="extractoAsunto" /></textarea>
                    </td>
                </tr>
                <tr>
                    <td style="border:0">
                        <!-- Boton de enviar -->          
                        <p align="center">
                        <bean:define id="botonEnviar" type="java.lang.String">
	                        <bean:message key="datosRegistroEntrada.botonContinuar"/>
                        </bean:define>
                        <html:button title="<%=botonEnviar%>" value="<%=botonEnviar%>" property="enviar" onclick="javascript:enviarFormulario( this.form )" />
                        </P>
                    </td>
                </tr>
            </table>
                </td>
            </tr>
    
        </table>
        </html:form>

        <!-- Fin Cuerpo central -->

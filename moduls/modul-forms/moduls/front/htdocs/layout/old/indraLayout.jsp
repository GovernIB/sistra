<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested" %>
<bean:define id="pathIconografia" name="pathIconografia" scope="request"/>
<bean:define id="securePath" name="securePath" scope="request"/>
<html:html xhtml="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><bean:message key="main.title"/></title>
        <link rel="stylesheet" href="<html:rewrite page='<%=pathIconografia + "/css/estilo.css" %>'/>" type="text/css" />
        <script type="text/javascript" src="<html:rewrite page='/js/MM_functions.js' />"></script>
        <script type="text/javascript" src="<html:rewrite page='/js/iframe_functions.js' />"></script>
        <script type="text/javascript">
        <!--
            // Variable global que servirá para saber si la pagina se ha terminado de cargar.
            var bLoaded = false;

            function doLoad() {
                // La pagina ha terminado de cargar.
                bLoaded = true;

                if (document.loadHook) { // Si existe la funcion loadHook ejecutarla.
                    loadHook();
                }
            }
        //-->
        </script>
    </head>
    <body onload="doLoad()">
    <div style='visibility:hidden;position:absolute'>Version:<%=org.ibit.rol.form.front.util.Util.getVersion()%></div>
    <table>
        <tr>
            <td>layout de indra!!!!!</td>
        </tr>
    </table>
    <table id="centrar" cellpadding="0" cellspacing="0"><tr><td align="center" style="vertical-align: middle;">
        <div id="general">
            <table id="cuerpo" cellpadding="0" cellspacing="0">
                <tr>
                    <td id="menu" align="left">
                        <div id="ayuda">
                            <nested:root name="formulario">
                                <nested:present property="logotipo1">
                                    <% String alt = "Logo"; %>
                                    <nested:present name="traduccion.nombreEntidad1">
                                        <nested:define id="talt" property="traduccion.nombreEntidad1" type="java.lang.String" />
                                        <% alt = talt; %>
                                    </nested:present>
                                    <nested:present property="urlEntidad1">
                                        <a href="<nested:write property='urlEntidad1'/>" target="_blank">
                                    </nested:present>
                                    <html:img alt="<%=alt%>" page='<%=securePath + "/logotipo1.do"%>' paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" border="0" />
                                    <nested:present property="urlEntidad1"></a></nested:present>
                                    <br />
                                </nested:present>
                                <nested:present property="logotipo2">
                                    <% String alt = "Logo"; %>
                                    <nested:present name="traduccion.nombreEntidad2">
                                        <nested:define id="talt" property="traduccion.nombreEntidad2" type="java.lang.String" />
                                        <% alt = talt; %>
                                    </nested:present>
                                    <nested:present property="urlEntidad2">
                                        <a href="<nested:write property='urlEntidad2'/>" target="_blank">
                                    </nested:present>
                                    <html:img alt="<%=alt%>" page='<%=securePath + "/logotipo2.do"%>' paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" border="0" />
                                    <nested:present property="urlEntidad2"></a></nested:present>
                                    <br />
                                </nested:present>
                            </nested:root>
                            <logic:present name="ayudaPantalla">
                                <br />
                                <div id="titulo"><bean:message key="main.help"/></div>
                                <div id="subtitulo">
                                    <bean:write name="ayudaPantalla" property="traduccion.descripcionCorta"/>
                                </div>
                                <logic:notEmpty name="ayudaPantalla" property="traduccion.descripcionLarga">
                                    <br />
                                    <bean:define id="imgProfesor2"><html:rewrite page='<%=pathIconografia + "/images/profesor2.gif" %>'/></bean:define>
                                    <a href="javascript:void(null)"
                                       onclick="window.open('<html:rewrite page='<%=securePath + "/ayuda.do"%>'  paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" />','AYUDA','width=450,height=500,scrollbars=yes')"
                                       onmouseout="MM_swapImgRestore()"
                                       onfocus="this.blur()"
                                       onmouseover="<%="MM_swapImage('mas_ayuda','','" + imgProfesor2 + "',1)"%>">
                                        <html:img page='<%=pathIconografia + "/images/profesor.gif" %>' alt="Mas ayuda" imageName="mas_ayuda" width="153" height="80" border="0" />
                                    </a>
                                    <br />
                                </logic:notEmpty>
                                <br />
                                <logic:notEmpty name="ayudaPantalla" property="traduccion.urlSonido">
                                    &nbsp;
                                    <a href="javascript:void(null)"
                                    onclick="window.open('<html:rewrite page='<%=securePath + "/playSonido.do"%>' paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" />','SONIDO','width=250,height=100')">
                                        <html:img border="0" page='<%=pathIconografia + "/images/audio.gif" %>' />
                                    </a>
                                    <logic:empty name="ayudaPantalla" property="traduccion.urlVideo">
                                    <br />
                                    </logic:empty>
                                </logic:notEmpty>
                                <logic:notEmpty name="ayudaPantalla" property="traduccion.urlVideo">
                                    &nbsp;
                                    <a href="javascript:void(null)"
                                    onclick="window.open('<html:rewrite page='<%=securePath + "/playVideo.do"%>' paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" />' ,'VIDEO','width=450,height=400')">
                                        <html:img border="0" page='<%=pathIconografia + "/images/video.gif" %>' />
                                    </a>
                                    <br />
                                </logic:notEmpty>
                            </logic:present>

                            <logic:notEmpty name="pantallasProcesadas">
                                <br /><br />
                                <div id="titulo"><bean:message key="main.pantallas"/></div>
                                <div id="pantallas">
                                    <ol>
                                    <logic:iterate id="pantalla" name="pantallasProcesadas" indexId="index">
                                        <bean:define id="nombre" name="pantalla" property="nombre"/>
                                        <li><a href="javascript:backTo('<%=nombre%>')" onfocus="this.blur()">
                                            <bean:write name="pantalla" property="traduccion.titulo" />
                                        </a></li>
                                    </logic:iterate>
                                    </ol>
                                </div>
                            </logic:notEmpty>
                            <br />
                            <div id="nombre_campo" style="display: none;"></div>
                            <div id="ayuda_campo" style="display: none;"></div>
                            <br />
                        </div>
                    </td>
                    <td id="canal" align="left">
                        <h1><tiles:getAsString name="title" /></h1>
                        <div id="contenido">
                            <tiles:insert attribute="contenido"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td width="203px" height="43px">
                        <div class="leyenda">
                            <logic:equal name="saveButton" value="true">
                                <a href="javascript:save()" onfocus="this.blur()" title="<bean:message key='boton.save'/>">
                                    <html:img alt="save" page='<%=pathIconografia + "/images/guardar.gif" %>' border="0" hspace="5"/>
                                </a>
                            </logic:equal>
                            <logic:equal name="discardButton" value="true">
                                <a href="javascript:discard()" onfocus="this.blur()" title="<bean:message key='boton.discard'/>">
                                    <html:img alt="discard" page='<%=pathIconografia + "/images/descartar.gif" %>' border="0" hspace="5"/>
                                </a>
                            </logic:equal>
                        </div>
                    </td>
                    <td width="583px" height="43px">
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <logic:equal name="backButton" value="true">
                                    <td width="50%" align="left" valign="bottom">
                                        <a href="javascript:back()" onfocus="this.blur()" title="<bean:message key='boton.back'/>">
                                            <html:img alt="back" page='<%=pathIconografia + "/images/izquierda.gif" %>' border="0" />
                                        </a>
                                    </td>
                                </logic:equal>
                                <logic:equal name="nextButton" value="true">
                                    <td width="50%" align="right" valign="bottom">
                                        <a href="javascript:next()" onfocus="this.blur()" title="<bean:message key='boton.next'/>">
                                            <html:img alt="next" page='<%=pathIconografia + "/images/derecha.gif" %>' border="0" />
                                        </a>
                                    </td>
                                </logic:equal>
                                <logic:equal name="fileButton" value="true">
                                    <td width="50%" align="right" valign="bottom">
                                        <a href="javascript:next()" onfocus="this.blur()" title="<bean:message key='boton.pdf'/>">
                                            <html:img alt="file" page='<%=pathIconografia + "/images/file.gif" %>' border="0" />
                                        </a>
                                    </td>
                                </logic:equal>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <div id="bajos">
                &nbsp;
            </div>
        </div>
    </td></tr></table>
    </body>
</html:html>
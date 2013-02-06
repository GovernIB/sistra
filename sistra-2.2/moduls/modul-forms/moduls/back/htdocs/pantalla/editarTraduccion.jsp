<%@ page language="java" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<logic:empty name="pantallaForm" property="values.id">
    <tr>
        <td class="labelo"><bean:message key="pantalla.titulo"/></td>
        <td class="input"><html:text styleClass="text" tabindex="5" property="traduccion.titulo" maxlength="256" /></td>
    </tr>
</logic:empty>
<logic:notEmpty name="pantallaForm" property="values.id">
    <bean:define id="idPantalla" name="pantallaForm" property="values.id" />
    <bean:define id="selectLang" name="pantallaForm" property="lang" />
    <script type="text/javascript">
    <!--

        <logic:present name="confirm">
            <bean:define id="nombre" name="confirm" type="java.lang.String"/>
            alert('<bean:message arg0="<%=StringUtils.escape(nombre)%>" key="ayuda.baja.confirm" />');
        </logic:present>

         function editAyuda() {
            var idPerfil = document.forms[0].perfil.value;
            var url = '<html:rewrite page="/back/ayuda/editar.do" />?idPerfil=' + idPerfil + '&idPantalla=<%=idPantalla%>&lang=<%=selectLang%>';
            if (idPerfil != 0){
                obrir(url, "Edicion", 640, 350);
            }
         }

         function viewAyuda() {
            var idPerfil = document.forms[0].perfil.value;
            var url = '<html:rewrite page="/back/ayuda/consulta.do" />?idPerfil=' + idPerfil + '&idPantalla=<%=idPantalla%>&lang=<%=selectLang%>';
            if (idPerfil != 0){
                obrir(url, "Consulta", 640, 300);
            }
         }

         function bajaAyuda() {
            var idPerfil = document.forms[0].perfil.value;
            if (confirmaBaja('<bean:message key="ayuda.baja" />')){
                var url = '<html:rewrite page="/back/ayuda/baja.do" />?idPerfil=' + idPerfil + '&idPantalla=<%=idPantalla%>&lang=<%=selectLang%>';
                if (idPerfil != 0){
                    location = url;
                }
            }
         }
    //-->
    </script>

    <tr>
        <td class="labelo"><bean:message key="pantalla.titulo"/></td>
        <td class="input"><html:text styleClass="text" tabindex="5" property="traduccion.titulo" maxlength="256" /></td>
    </tr>
    <tr>
        <td class="label"><bean:message key="pantalla.ayuda" /></td>
        <td>
            <select name="perfil" style="width: 180px" >
                <logic:present name="perfilOptions" >
                    <logic:iterate id="perfil" name="perfilOptions">
                        <option value="<bean:write name="perfil" property="id" />" >
                            <bean:write name="perfil" property="traduccion.nombre" />
                        </option>
                    </logic:iterate>
                </logic:present>
            </select>
            <input type="button" class="button" value='<bean:message key="boton.ver" />' onclick="viewAyuda()" />
           
            <logic:present name="bloqueado">
                <input type="button" class="button" value='<bean:message key="boton.modificacio" />' onclick="editAyuda()" />
                <input type="button" class="button" value='<bean:message key="boton.baixa" />' onclick="bajaAyuda()" />
            </logic:present>
        </td>
    </tr>
    <tr>
        <td class="labela" colspan="2"><bean:message key="ayuda.perfil.pantalla"/></td>
    </tr>
</logic:notEmpty>

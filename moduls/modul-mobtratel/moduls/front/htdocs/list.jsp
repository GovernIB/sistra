<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15"%>
<%@ page import="java.util.Map,
                 java.util.HashMap"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:html xhtml="true" >
<head>
    <title>FORMS</title>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
    <style>
        body {
            background-color : #E6ECF3;
            font-family: Verdana, Arial;
            font-size: 11px;
            color: Black;
        }

        h1 {
            font-family: Verdana, Arial;
	        font-size: 18px;
        }

        li {
            margin-top: 10px;
        }

        a {
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .error {
	        color: Red;
        }
    </style>
</head>
<body>
    <html:errors />

    <h1>Formularios</h1>

    <ul>
        <%
            Map params = new HashMap(2);
            request.setAttribute("formParams", params);
        %>
        <logic:iterate id="formulario" name="formularios" type="org.ibit.rol.form.model.Formulario">
            <% params.put("modelo", formulario.getModeloDocumento()); %>
            <logic:iterate id="perfil" name="perfiles" type="org.ibit.rol.form.model.PerfilUsuario">
                <% params.put("perfil", perfil.getCodigoEstandard()); %>
                <li>
                    <html:link action="/inici" name="formParams">
                        <bean:write name="formulario" property="traduccion.titulo"/>
                        (modelo <bean:write name="formulario" property="modelo"/>) -
                        Perfil: <bean:write name="perfil" property="traduccion.nombre"/>
                    </html:link>
                </li>
            </logic:iterate>
        </logic:iterate>
    </ul>

    <hr />

    <h1>Reanudar formulario</h1>

    <html:form action="/inici" enctype="multipart/form-data">
        Datos guardados: <html:file property="fichero" /><br />
        <html:submit>Cargar</html:submit>
    </html:form>

</body>
</html:html>
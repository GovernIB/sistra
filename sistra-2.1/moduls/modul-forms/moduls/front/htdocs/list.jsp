<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.Map,
                 java.util.HashMap,
                 org.apache.struts.Globals"%>
<%@ page import="java.util.Enumeration"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:html xhtml="true" >
<head>
    <title>FORMS</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style type="text/css">
        body {
            background-color : #E6ECF3;
            font-family: sans-serif;
            font-size: 11px;
            color: Black;
        }
        h1 {
            font-family: sans-serif;
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
            <% params.put("modelo", formulario.getModelo()); %>
            <li>
                <strong><bean:write name="formulario" property="modelo"/></strong> - <bean:write name="formulario" property="traduccion.titulo"/> -
                <% for (int i = formulario.getVersion(); i >= 1; i--) { %>
                    <% params.put("version", new Integer(i)); %>
                    <logic:iterate id="perfil" name="perfiles" type="org.ibit.rol.form.model.PerfilUsuario">
                        <% params.put("perfil", perfil.getCodigoEstandard()); %>
                        (<html:link action="/inici" name="formParams">
                            V<%=i%>,<bean:write name="perfil" property="traduccion.nombre"/>
                        </html:link>)
                    </logic:iterate>
                <% } %>
            </li>
        </logic:iterate>
    </ul>

    <hr />

    <h1>Reanudar formulario</h1>

    <html:form action="/inici" enctype="multipart/form-data">
        Datos guardados: <html:file property="fichero" /><br />
        <html:submit>Cargar</html:submit>
    </html:form>

    <hr />

    <h1>Datos la petición</h1>
    <ul>
        <li>Usuario: <%=request.getRemoteUser()%></li>
        <li>Idioma: <%=session.getAttribute(Globals.LOCALE_KEY)%></li>
        <li>URI: <%=request.getRequestURI()%></li>
        <li>URL: <%=request.getRequestURL()%></li>
    </ul>

    <h1>Variables de sessió</h1>
    <ul>
        <%
            Enumeration enumeration = session.getAttributeNames();
            while (enumeration.hasMoreElements()) {
                String attributeName = (String) enumeration.nextElement();
                Object attribute = session.getAttribute(attributeName);
                %>
            <li><%=attributeName%>, <%=attribute%></li>
                <%
            }
        %>
    </ul>
</body>
</html:html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ca" xml:lang="ca">
<head>
    <title>SISTEMA TRAMITACIÓ</title>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
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
    <h1>Tornada</h1>

    <hr />

    <h2>Resultats</h2>

    <label for="xmlInicial">XML Inicial</label><br />
    <textarea id="xmlInicial"
              rows="20"
              cols="80"
              name="xmlInicial"
              readonly="readonly"><%=request.getAttribute("xmlInicial")%></textarea><br />

    <label for="xmlFinal">XML Final</label><br />
    <textarea id="xmlFinal"
              rows="20"
              cols="80"
              name="xmlFinal"><%=request.getAttribute("xmlFinal")%></textarea><br />

</body>
</html>
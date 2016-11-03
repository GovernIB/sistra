<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<html:html xhtml="true" >
<head>
    <title><bean:message key="fail.sistemaTramitacion"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
    <style>
        body {
            background-color : #E6ECF3;
            font-family: Verdana, Arial;
            font-size: 11px;
            color: Black;
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
    <hr />
    <html:link forward="main"><bean:message key="fail.inicio"/></html:link>
</body>
</html:html>
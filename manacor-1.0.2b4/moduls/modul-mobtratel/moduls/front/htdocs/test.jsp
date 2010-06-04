<%@ page import="java.util.List,
                 java.util.ArrayList"%>
<%@ page language="java" %>
<html>
<head><title>Test de ClassLoader</title>
<%!
    protected String getLocation(Class clazz) {
        return clazz.getProtectionDomain().getCodeSource().getLocation().toString();
    }

    protected String getClassLoaderName(Class clazz) {
        ClassLoader cl = clazz.getClassLoader();
        if (cl == null) {
            return null;
        }

        return cl.getClass().getName();
    }
%>
<body>
    <h1>Test de class loader</h1>
    <%
        List clazzes = new ArrayList();

        clazzes.add(org.apache.commons.logging.Log.class);

        clazzes.add(org.apache.commons.beanutils.BeanUtils.class);
        clazzes.add(org.apache.commons.collections.FastHashMap.class);
        clazzes.add(org.apache.commons.lang.StringUtils.class);
        clazzes.add(org.apache.commons.validator.Validator.class);
        clazzes.add(org.apache.commons.fileupload.FileUpload.class);
        clazzes.add(org.apache.oro.text.regex.Pattern.class);

        clazzes.add(org.apache.bsf.BSFEngine.class);
        clazzes.add(org.mozilla.javascript.Context.class);
        clazzes.add(com.lowagie.text.pdf.PdfStream.class);

        clazzes.add(org.apache.commons.digester.Digester.class);
        clazzes.add(org.apache.struts.action.ActionServlet.class);
    %>
    <ul>
    <% for (int i = 0; i < clazzes.size(); i++) {
        Class clazz = (Class) clazzes.get(i);
        %>
        <li>
            <%=clazz.getName()%>:<br />
            {<%=getLocation(clazz)%>}<br />
            {<%=getClassLoaderName(clazz)%>}
        </li>
        <%
    }
    %>
    </ul>
</body>
</html>

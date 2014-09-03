<%@ page language="java"%>
<%@ page import="java.util.Map"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<html:xhtml/>
<logic:present name="videoOptions">
    <%
        Map video = (Map)request.getAttribute("videoOptions");
        int width = 320;
        int height = 200;
    %>
    <logic:notEmpty name="videoOptions" property="classId">

    <%--Solo ponemos el tag object si no es un video de Real Player 10--%>
    <logic:notEqual name="videoOptions" property="id" value="RVOCX">
        <object
            <logic:notEqual name="videoOptions" property="id" value="">
                ID="<%=video.get("id")%>"
            </logic:notEqual>
            classid="<%=video.get("classId")%>" width="<%=width%>" height="<%=height%>"
            <logic:notEqual name="videoOptions" property="codeBase" value="">
                codebase="<%=video.get("codeBase")%>"
            </logic:notEqual>>
            <logic:equal name="videoOptions" property="mimeType" value="video/quicktime">
                <param name="src" value="<%=video.get("urlVideo")%>">
            </logic:equal>
            <logic:equal name="videoOptions" property="id" value="MediaPlayer">
                <param name="filename" value="<%=video.get("urlVideo")%>">
            </logic:equal>
            <param name="autoStart" value="True">
    </logic:notEqual>

    <embed type="<%=video.get("mimeType")%>"
        src="<%=video.get("urlVideo")%>" width="<%=width%>" height="<%=height%>"
    <logic:notEqual name="videoOptions" property="pluginSpage" value="">
        pluginspage="<%=video.get("pluginSpage")%>"
    </logic:notEqual>

    <logic:equal name="videoOptions" property="id" value="RVOCX">
         name="real1" controls="ImageWindow" autostart="true" console="video"
    </logic:equal>
        bgcolor="#000000" border="0">
    </embed>

    <%--Ponemos 3 embeds por compatibilidad con el Real Player 10--%>
    <logic:equal name="videoOptions" property="id" value="RVOCX">
        <embed name="real2" src="<%=video.get("urlVideo")%>" autostart="true"
        controls="controlPanel" width="<%=width%>" height="25" console="video"></embed>
        <embed name="real3" src="<%=video.get("urlVideo")%>" autostart="true"
        controls="positionField" width="<%=width%>" height="25" console="video"></embed>
    </logic:equal>

    <logic:notEqual name="videoOptions" property="id" value="RVOCX">
        </object>
    </logic:notEqual>

    </logic:notEmpty>
    <logic:empty name="videoOptions" property="classId">
        Video ERROR
    </logic:empty>
</logic:present>
<logic:notPresent name="videoOptions">
    Url Video Incorrecta
</logic:notPresent>

<%@ page language="java" %>
<%@ page import="java.util.Map"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<html:xhtml/>
<logic:present name="audioOptions">
    <%
        Map audio = (Map)request.getAttribute("audioOptions");
        int width = 220;
        int height = 30;
    %>
    <logic:notEmpty name="audioOptions" property="classId">
        <object ID="<%=audio.get("id")%>" width="<%=width%>" height="<%=height%>"
            classid="<%=audio.get("classId")%>"
             <logic:notEqual name="audioOptions" property="codeBase" value="">
                codebase="<%=audio.get("codeBase")%>"
            </logic:notEqual>>
            <param name="autoStart" value="True">
            <logic:equal name="audioOptions" property="id" value="MediaPlayer">
                <param name="filename" value="<%=audio.get("urlSonido")%>">
            </logic:equal>
            <embed type="<%=audio.get("mimeType")%>"
                src="<%=audio.get("urlSonido")%>"
                <logic:notEqual name="audioOptions" property="pluginSpage" value="">
                    pluginspage="<%=audio.get("pluginSpage")%>"
                </logic:notEqual>
                <logic:equal name="audioOptions" property="id" value="RVOCX">
                    showcontrols="1" showdisplay="1" showstatusbar="1"
                </logic:equal>
                width="<%=width%>" height="<%=height%>">
            </embed>
        </object>
    </logic:notEmpty>
    <logic:empty name="audioOptions" property="classId">
        Audio ERROR
    </logic:empty>
</logic:present>
<logic:notPresent name="audioOptions">
    Url Audio Incorrecta
</logic:notPresent>
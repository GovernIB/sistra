<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<script type="text/javascript">
<!--
     function desplega() {
        var id = document.forms[0].select.value;
        var modelo = document.forms[0].select.options[document.forms[0].select.selectedIndex].text;
        parent.Menu.location.href='<html:rewrite page="/menu.do" />?expand=arbol&select=' + id;
        if (id != 0){;
            parent.Ventana.location.href='<html:rewrite page="/back/formulario/seleccion.do" />?id=' + id;
        } else {
            parent.Ventana.location.href='<html:rewrite page="/back/formulario/lista.do" />';
        }
     }
//-->
</script>
<tiles:useAttribute id="select" name="select" scope="page" />
<tiles:importAttribute name="nodos" scope="page" />
<tiles:useAttribute name="options" scope="page" classname="java.lang.String" />
<tiles:useAttribute name="prop" scope="page" classname="java.lang.String" />
<bean:parameter id="padre" name="padre" value="0" />

<bean:size id="numOptions" name="<%=options%>" />

<logic:equal name="numOptions" value="0">
    <tr><td>
      <bean:message key="formulario.selec.vacio" />
    </td></tr>
    </table>
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <% boolean valid = false; %>
    <form action=''>
    <tr><td>
        <input type="hidden" name="expand" value="arbol" />
        <select name="select" onchange="javascript:desplega()" >
            <option value="0">- <bean:message key="arbol.formulario.select" /> -</option>
            <logic:iterate  id="option" name="<%=options%>" >
                <bean:define id="valor" name="option" property="id" />
                <option value='<%=valor%>' >
                    <bean:write name="option" property="<%=prop%>" />
                </option>
                <%
                    if (valor.toString().equals(select.toString())) {
                        valid = true;
                    }
                %>
            </logic:iterate>
        </select>
    </td></tr>
    </form>
    </table>

    <logic:notEqual name="select" value="0">
        <% if (valid) { %>
       <bean:define id="actual" name="select" />
       <script type="text/javascript">
       <!--
             var selected;
             var numOps = document.forms[0].select.length;
             for (var x = 0; x < numOps; x++) {
                if (document.forms[0].select.options[x].value == <%=actual%>){
                    selected = document.forms[0].select.options[x].index;
                }
             }
             document.forms[0].select.selectedIndex = selected;
       //-->
       </script>

        <tiles:insert name="nodos" flush="false" >
            <tiles:put name="id" beanName="select"  />
            <tiles:put name="padre" beanName="padre"  />
            <tiles:put name="options" value="pantallaOptions" />
            <tiles:put name="prop" value="nombre" />
        </tiles:insert>
        <% } %>
    </logic:notEqual>
</logic:notEqual>


<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%
String id = request.getParameter("id");
String urlFuncsNodes = "/funcsNodesUnidades.do?id=" + id;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html lang="en">
<bean:define id="urlNodes">
    <html:rewrite page="<%= urlFuncsNodes%>" />
</bean:define>
<head>
	<link
	 rel="stylesheet"
	 type="text/css"
	 title="Fixed content"
	 href="estilos/fixed.css">
	 
	 
<script type="text/javascript">

function seleccionar(descripcion)
{
     codigo = descripcion.substr(2);
     var index = findOption(opener.document.getElementsByName('<%= id %>').item(0).options, codigo);
     opener.document.getElementsByName('<%= id %>').item(0).options[index].selected = 'selected';
     window.close();
}

function findOption(options, valor)
{
   for(i=0; i<options.length; i++)
   {
      if(options[i].value == valor)
      {
         return i;
      }
   }
   return 0;
}




</script>
	
<!-- Code for browser detection -->
<script  type="text/javascript" src="js/ua.js"></script>

<!-- Infrastructure code for the tree -->
<script  type="text/javascript" src="js/ftiens4.js"></script>

<script  type="text/javascript" src="<%= urlNodes %>"></script>

</head>

<body topmargin=16 marginheight=16>

<!-- By making any changes to this code you are violating your user agreement.
     Corporate users or any others that want to remove the link should check 
	 the online FAQ for instructions on how to obtain a version without the link -->
<!-- Removing this link will make the script stop from working -->
<div style="position:absolute; top:0; left:0; "><table border=0><tr><td><font size=-2><a style="font-size:7pt;text-decoration:none;color:silver" href="http://www.treemenu.net/" target=_blank></a></font></td></tr></table></div>
<DIV style="TEXT-ALIGN: center;
    font-family: verdana, arial, helvetica, sans-serif;
    font-size: 12pt;
    font-weight:bold;
    vAlign: middle;
    color: #d8dfe7;
    border: 2px solid white /*#d8dfe7*/;
    background-color: #515b67;height:10px;width:500px;
    border-spacing: 2;
    margin: 10px 10px 10px 10px;">Unidades Administrativas</DIV> 
<!-- Build the browser's objects and display default view of the 
     tree. -->
<script type="text/javascript">initializeDocument();<%=request.getParameter("id")==null?"":"irANodo(\"" + request.getParameter("id") + "\");"%></script>

</html>

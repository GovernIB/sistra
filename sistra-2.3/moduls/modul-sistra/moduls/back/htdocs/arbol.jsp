<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html lang="en">
<head>
	<link
	 rel="stylesheet"
	 type="text/css"
	 title="Fixed content"
	 href="css/fixed.css">
	
<!-- Code for browser detection -->
<script type="text/javascript" src="js/ua.js"></script>

<!-- Infrastructure code for the tree -->
<script type="text/javascript" src="js/ftiens4.js"></script>

<script type="text/javascript" src="funcsNodes.do?codigo=<%= request.getParameter( "codigo" ) %>"></script>

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
    margin: 10px 10px 10px 10px;"><bean:message key="nodos.exploradorTramites" /></DIV> 
<!-- Build the browser's objects and display default view of the 
     tree. -->
<script  type="text/javascript">initializeDocument();<%=request.getParameter("id")==null?"":"irANodo(\"" + request.getParameter("id") + "\");"%></script>
<noscript>
A tree for site navigation will open here if you enable JavaScript in your browser. 
</noscript>
<DIV id=fixedBox>
	<A href="javascript:refrescar();">
		<IMG alt="Actualizar"  style="FLOAT: right" src="img/arbol/refrescar.jpg" >
	</A>
	<A href="javascript:maximizar();">
		<IMG alt="Maximizar" style="FLOAT: right" src="img/arbol/maximizar.jpg">
	</A>
	<A href="javascript:restaurar();">
		<IMG alt="Restaurar" style="FLOAT: right" src="img/arbol/restaurar.jpg">
	</A>
	<A href="javascript:minimizar();">
		<IMG alt="Minimizar" style="FLOAT: right" src="img/arbol/minimizar.jpg" >
	</A>
</DIV>
</html>

<%@ page language="java" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

// You can find instructions for this file here:
// http://www.treeview.net

// Decide if the names are links or just the icons
USETEXTLINKS = 1  //replace 0 with 1 for hyperlinks

// Decide if the tree is to start all open or just showing the root folders
STARTALLOPEN = 0 //replace 0 with 1 to show the whole tree

HIGHLIGHT = 1

HIGHLIGHT_BG='#97aac0'


//PRESERVESTATE=1

<logic:iterate id="nodo" name="nodosArbol" type="es.caib.bantel.back.util.NodoArbol">
	<logic:empty name="nodo" property="parentId">
		<bean:write name="nodo" property="idCampo"/> = gFld("<bean:write name='nodo' property='descripcion'/>", "");
		<bean:write name="nodo" property="idCampo"/>.iconSrc = ICONPATH + "ROOT.GIF";			
		<bean:write name="nodo" property="idCampo"/>.iconSrcClosed = ICONPATH + "ROOT.GIF";			
	</logic:empty>
	<logic:notEmpty name="nodo" property="parentId">
		<bean:write name="nodo" property="idCampo"/> = insFld(<bean:write name='nodo' property='idCampoPadre'/>, gFld("<bean:write name='nodo' property='descripcion'/>", ""));									
		<logic:equal name="nodo" property="seleccionable" value="true">
			<bean:write name="nodo" property="idCampo"/>.pospendHTML = '    [<i><a href="javascript:seleccionar(\'<bean:write name='nodo' property='id'/>\');"><bean:message key="boton.selec"/></a></i>]';
		</logic:equal>
	</logic:notEmpty>	
	<bean:write name="nodo" property="idCampo"/>.xID = "<bean:write name="nodo" property="id"/>";
</logic:iterate>
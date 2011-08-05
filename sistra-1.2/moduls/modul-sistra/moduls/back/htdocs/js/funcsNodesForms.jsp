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

<logic:iterate id="nodo" name="unidadesForms" type="es.caib.sistra.back.action.documentoNivel.NodoForms">
	
	<logic:empty name="nodo" property="parentId">
		<bean:write name="nodo" property="id"/> = gFld("<bean:write name='nodo' property='descripcion'/>", "");
		<bean:write name="nodo" property="id"/>.iconSrc = ICONPATH + "ROOT.GIF";			
		<bean:write name="nodo" property="id"/>.iconSrcClosed = ICONPATH + "ROOT.GIF";			
	</logic:empty>
	
	<logic:notEmpty name="nodo" property="parentId">
		<logic:equal name="nodo" property="folder" value="true">
			<bean:write name="nodo" property="id"/> = insFld(<bean:write name='nodo' property='parentId'/>, gFld("<bean:write name='nodo' property='descripcion'/>", ""));							
		</logic:equal>
		aux = "<bean:write name='nodo' property='descripcion'/>";
		
		<logic:notEmpty name="nodo" property="version" >
			<bean:write name="nodo" property="id"/>.pospendHTML = '    [<i><a href="javascript:seleccionar(\'<bean:write name='nodo' property='modelo'/>\',\'<bean:write name='nodo' property='version'/>\');">Seleccionar</a></i>]';
		</logic:notEmpty>
		
		<logic:notEqual name="nodo" property="folder" value="true">				
				<bean:write name="nodo" property="id"/> = insDoc(<bean:write name='nodo' property='parentId'/>, gLnk("R", "<bean:write name='nodo' property='descripcion'/>", ""));
		</logic:notEqual>
	</logic:notEmpty>	

	<!--  ID  -->	
	<bean:write name="nodo" property="id"/>.xID = "<bean:write name="nodo" property="id"/>";
</logic:iterate>
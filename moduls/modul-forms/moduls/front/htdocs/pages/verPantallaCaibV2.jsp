<%@ page language="java"%>
<%@ page import="org.ibit.rol.form.model.TextBox,
                 org.ibit.rol.form.model.ComboBox,
                 org.ibit.rol.form.model.RadioButton,
                 org.ibit.rol.form.model.CheckBox,
                 org.ibit.rol.form.model.FileBox,
                 org.ibit.rol.form.model.ListBox,
                 org.ibit.rol.form.model.TreeBox,
                 org.ibit.rol.form.model.ListaElementos,
                 org.ibit.rol.form.model.Label,
                 org.ibit.rol.form.model.Campo,
                 org.ibit.rol.form.model.Seccion,
                 org.ibit.rol.form.model.Captcha,
                 org.apache.struts.taglib.html.Constants,
                 org.apache.struts.Globals,
                 org.ibit.rol.form.front.util.JSUtil,
                 org.ibit.rol.form.persistence.util.ScriptUtil"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>

<bean:define id="securePath" name="securePath" scope="request"/>

<html:xhtml/>

<%String action = "procesar";%>
<logic:present name="pantallaDetalle">
	<logic:equal name="pantallaDetalle" value="true">
		<%action = "procesarDetalle"; %>	
	</logic:equal>
	<bean:parameter name="listaelementos@accion" id="detalleAccion"/>
</logic:present>

<html:form action='<%=securePath + "/" + action%>' onsubmit="return validatePantallaForm(this)" styleId="pantallaForm">
<!--  Formulario -->
<div id="imc-formulari" class="imc-formulari imc-form">
 <div class="imc-form-contingut">						
    <input type="hidden" name="<%=Constants.CANCEL_PROPERTY%>" disabled="disabled" value="true" />
    <input type="hidden" name="SAVE" disabled="disabled" value="true" />
    <input type="hidden" name="DISCARD" disabled="disabled" value="true" />
    <input type="hidden" name="PANTALLA_ANTERIOR" disabled="disabled" value="" />
    <input type="hidden" name="ID_INSTANCIA" value="<%=request.getAttribute("ID_INSTANCIA")%>" />            
    
    <logic:present name="pantallaDetalle">
		<logic:equal name="pantallaDetalle" value="true">
			<input type="hidden" name="listaelementos@accion" value="<%=request.getParameter("listaelementos@accion")%>" />
		    <input type="hidden" name="listaelementos@campo" value="<%=request.getParameter("listaelementos@campo")%>" />
		    <input type="hidden" name="listaelementos@indice" value="<%=request.getParameter("listaelementos@indice")%>" />
			<logic:equal name="detalleAccion" value="insertar">
				<input type="hidden" name="INSERT_POST_SAVE" value="false" />				
			</logic:equal>			
		</logic:equal>
	</logic:present>
    
    
    <nested:iterate id="comp" name="pantalla" property="componentes" indexId="ind" >
        
        <logic:greaterThan name="ind" value="0">
            <logic:equal name="comp" property="posicion" value="0"><div class="imc-separador imc-sep-salt-carro"></div></logic:equal>
            <logic:equal name="comp" property="posicion" value="2"><div class="imc-separador"></div></logic:equal>        
            <logic:equal name="comp" property="posicion" value="3"><div class="imc-separador imc-sep-punt"></div></logic:equal>
        </logic:greaterThan>
        
        <%
            if (comp instanceof TextBox) {
                if (((TextBox) comp).isMultilinea()) {
                    %><jsp:include page='<%="/ui/caib/multiboxV2.jsp"%>' /><%
                } else {
                    %><jsp:include page='<%="/ui/caib/textboxV2.jsp"%>'/><%
                }
            } else if (comp instanceof ComboBox) {
                %><jsp:include page='<%="/ui/caib/comboboxV2.jsp"%>'/><%
            } else if (comp instanceof RadioButton) {
                %><jsp:include page='<%="/ui/caib/radiobuttonV2.jsp"%>' /><%
            } else if (comp instanceof CheckBox) {
                %><jsp:include page='<%="/ui/caib/checkboxV2.jsp"%>'/><%
            } else if (comp instanceof FileBox) {
                %><jsp:include page='<%="/ui/caib/fileboxV2.jsp"%>'/><%
            } else if (comp instanceof ListBox) {
                %><jsp:include page='<%="/ui/caib/listboxV2.jsp"%>'/><%
            }else if (comp instanceof TreeBox) {
                %><jsp:include page='<%="/ui/caib/treeboxV2.jsp"%>'/><%
            }else if (comp instanceof ListaElementos) {
                %><jsp:include page='<%="/ui/caib/listaelementosV2.jsp"%>'/><%
            } else if (comp instanceof Label) {
            	%><jsp:include page='<%="/ui/caib/labelV2.jsp"%>'/><%
            } else if (comp instanceof Seccion) {
            	%><jsp:include page='<%="/ui/caib/seccionV2.jsp"%>'/><%
            } else if (comp instanceof Captcha) {
            	%><jsp:include page='<%="/ui/caib/captchaV2.jsp"%>'/><%
            }
        %>
        
    </nested:iterate>
    
  </div>
</div>    
<!--  Navegacion -->
<%-- Botonera pagina normal --%>
<nav id="imc-navegacio" class="imc-navegacio imc-nav">
	<ul>
	
			<li class="imc-nav-abandona">
				<a href="javascript:discard()" class="imc-nav-abandona" title="<bean:message bundle="caibMessages" key="boton.cancelar"/>" onfocus="this.blur()"><bean:message bundle="caibMessages" key="boton.cancelar"/></a>
			</li>
			
			<li class="imc-nav-desa">
				<logic:equal name="saveButton" value="true">
					<a href="javascript:save()" class="imc-nav-desa" title="<bean:message bundle="caibMessages" key="boton.save"/>" onfocus="this.blur()">
						<bean:message bundle="caibMessages" key="boton.saveTextV2"/>
					</a>					
				</logic:equal>
				<logic:equal name="saveButton" value="false">
					<span>&nbsp;</span>					
				</logic:equal>
			</li>
			
			<li class="imc-nav-anterior">
			<logic:equal name="backButton" value="true">
				<a href="javascript:back()" class="imc-nav-anterior" title="<bean:message bundle="caibMessages" key="boton.back"/>" onfocus="this.blur()">
					<bean:message bundle="caibMessages" key="boton.backTextV2"/>
				</a>					
			</logic:equal>
			<logic:equal name="backButton" value="false">
				<span>&nbsp;</span>					
			</logic:equal>
			</li>
		
			
			<logic:equal name="endButton" value="true">
			<li class="imc-nav-finalitza">	
				<a href="javascript:next()" class="imc-nav-finalitza" title="<bean:message bundle="caibMessages" key="boton.terminar"/>" onfocus="this.blur()"><bean:message bundle="caibMessages" key="boton.terminar"/></a>
			</li>			
			</logic:equal>
			<logic:equal name="endButton" value="false">
			<li class="imc-nav-seguent">								
				<logic:equal name="nextButton" value="true">
					<a href="javascript:next()" class="imc-nav-seguent" title="<bean:message bundle="caibMessages" key="boton.next" />" onfocus="this.blur()">
						<bean:message bundle="caibMessages" key="boton.nextTextV2"/>
					</a>
				</logic:equal>			
				<logic:equal name="nextButton" value="false">							
					<span>&nbsp;</span>		
				</logic:equal>	
			</li>
			</logic:equal>
			
	</ul>			
</nav>
<%-- Botonera pagina detalle --%>
<logic:present name="pantallaDetalle">
	<logic:equal name="pantallaDetalle" value="true">
		<bean:parameter name="listaelementos@accion" id="detalleAccion"/>
		<div class="imc-element imc-iframe-botonera">
			<ul>						
				<logic:equal name="detalleAccion" value="insertar">
					<li>
						<button class="imc-bt-ta-guarda-i-nou" type="button" onclick=" document.pantallaForm.elements['INSERT_POST_SAVE'].value = true;next();"><span><bean:message bundle="caibMessages" key="boton.guardarInsertarElemento"/></span></button>
					</li>
					<li>
						<button class="imc-bt-ta-guarda" type="button" id="imc-bt-ta-guarda-iframe" onclick="next();"><span><bean:message bundle="caibMessages" key="boton.guardarElemento"/></span></button>
					</li>
					<li>
						<button class="imc-bt-ta-cancela" type="button" id="imc-bt-ta-cancela-iframe" onclick="back();"><span><bean:message bundle="caibMessages" key="boton.cancelarElemento"/></span></button>
					</li>
				</logic:equal>
				<logic:equal name="detalleAccion" value="modificar">						
					<li>
						<button class="imc-bt-ta-guarda" type="button" id="imc-bt-ta-guarda-iframe" onclick="next();"><span><bean:message bundle="caibMessages" key="boton.aceptarElemento"/></span></button>
					</li>
					<li>
						<button class="imc-bt-ta-cancela" type="button" id="imc-bt-ta-cancela-iframe" onclick="back();"><span><bean:message bundle="caibMessages" key="boton.cancelarElemento"/></span></button>
					</li>
				</logic:equal>
			</ul>
		</div>				
	</logic:equal>
</logic:present>
</html:form>


<%-- Això és una xapuça --%>
<% pageContext.removeAttribute(Globals.XHTML_KEY);%>
<html:javascript formName="pantallaForm" staticJavascript="false" htmlComment="true" cdata="false" />
<% pageContext.setAttribute(Globals.XHTML_KEY, "true");%>

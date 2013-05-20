<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ page import="es.caib.bantel.modelInterfaz.ConstantesBTE"%>


<script type="text/javascript" src="js/exportCSV.jsp"></script>

		<h2><bean:message key="exportCSV.exportarTramites"/></h2>
		
		<logic:empty name="tramitesCSV">
			<bean:message key="errors.noGestorCSV" />
		</logic:empty>
		
		<logic:notEmpty name="tramitesCSV">	
			<form name="export" id="export" class="centrat">
			<p>
				<bean:message key="exportCSV.tramite"/>
				<select name="identificadorProcedimientoTramite">
					<logic:iterate id="tramiteCSV" name="tramitesCSV" type="es.caib.bantel.model.TramiteExportarCSV">															
						<option value="<%=tramiteCSV.getIdProcedimientoTramite()%>">
							<%=tramiteCSV.getDescripcion()%>
						</option>
					</logic:iterate>
				</select>
												
				<bean:message key="exportCSV.estado"/>
				<select name="procesada">
					<option value="T"><bean:message key="formularioBusqueda.procesada.todas"/></option>	
					<option value="<%=ConstantesBTE.ENTRADA_NO_PROCESADA%>" ><bean:message key="formularioBusqueda.procesada.noprocesadas"/></option>
					<option value="<%=ConstantesBTE.ENTRADA_PROCESADA%>" ><bean:message key="formularioBusqueda.procesada.correctas"/></option>
					<option value="<%=ConstantesBTE.ENTRADA_PROCESADA_ERROR%>" ><bean:message key="formularioBusqueda.procesada.error"/></option>									
				</select>
			 </p>	
			<p>
				
				<bean:message key="exportCSV.fechaInicio"/>
				<input type="text" name="desde" id="desde"/>
				
				<bean:message key="exportCSV.fechaHasta"/>
				<input type="text" name="hasta" id="hasta"/>
				</p>
				<p class="botonera">
					<input type="button" onclick="javascript:exportCSV();" value="<bean:message key="exportCSV.exportar"/>"/>	
				</p>
			</form>
		
		
		</logic:notEmpty>
		
		
		<!-- capa accediendo formularios -->
		<div id="capaInfoFondo"></div>
		<div id="capaInfoForms"><span id="mensajeEnviando"></span><br/><br/><input type="button" onclick="javascript:cancelExport();" value="<bean:message key="exportCSV.cancelar"/>"/></div>
		<div id="capaDownloadExport"><bean:message key="exportCSV.exportacionFinalizada"/><br/><input type="button" onclick="javascript:downloadCSV();" value="<bean:message key="exportCSV.downloadCSV"/>"/></a></div>
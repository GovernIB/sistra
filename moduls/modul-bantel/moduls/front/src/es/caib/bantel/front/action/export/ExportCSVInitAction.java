package es.caib.bantel.front.action.export;

import java.io.ByteArrayInputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.action.BaseAction;
import es.caib.bantel.front.form.ExportCSVTramitesForm;
import es.caib.bantel.model.CSVExport;
import es.caib.bantel.model.CSVExportWorks;
import es.caib.bantel.model.FicheroExportacion;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;
import es.caib.util.DataUtil;
import es.caib.util.PropertiesOrdered;
import es.caib.util.StringUtil;

/**
 * @struts.action
 *  name="exportCSVTramitesForm"
 *  path="/exportCSVInitAction"
 *  scope="request"
 *  validate="false"
 *
 *
 *	INICIA PROCESO DE EXPORTACION: CREA TRABAJO EN SESION
 *
 *
 */
public class ExportCSVInitAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(ExportCSVInitAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		String result="ERROR:Error desconocido";
		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		
		try{
			
			ExportCSVTramitesForm f  = ( ExportCSVTramitesForm ) form;
			
			// Verificamos parametros de entrada
			if (StringUtils.isEmpty(f.getIdentificadorProcedimientoTramite())) {
				result = "ERROR:" + resources.getMessage( getLocale( request ), "exportCSV.identificadorTramiteNoExiste");
				throw new Exception("No existe identificador tramite");			
			}
			
			String procesada = f.getProcesada();
			if (!StringUtils.isEmpty(f.getProcesada())) {
				if (f.getProcesada().equals("T")) procesada = null;									
			}else{
				procesada = null;
			}
		
			Date desde=null;		
			if (StringUtils.isNotEmpty(f.getDesde())) {
				desde = StringUtil.cadenaAFecha(f.getDesde(),StringUtil.FORMATO_FECHA);
				if (desde == null) {				
					result = "ERROR:" +  resources.getMessage( getLocale( request ), "exportCSV.fechaInicioNoValida");
					throw new Exception("Fecha inicio incorrecta");	
				}
				desde = DataUtil.obtenerPrimeraHora(desde);
			}
		
			Date hasta=null;		
			if (StringUtils.isNotEmpty(f.getHasta())) {
				hasta = StringUtil.cadenaAFecha(f.getHasta(),StringUtil.FORMATO_FECHA);
				if (hasta == null) {				
					result = "ERROR:" + resources.getMessage( getLocale( request ), "exportCSV.fechaHastaNoValida");
					throw new Exception("Fecha fin incorrecta");	
				}
				hasta = DataUtil.obtenerUltimaHora(hasta);
			}										
		
		
			// Buscamos entradas a exportar
			String [] ids = f.getIdentificadorProcedimientoTramite().split("@#@");
			Long codigoProcedimiento = new Long(ids[0]);
			String idTramite = ids[1];
			
			TramiteBandejaDelegate delegate = DelegateUtil.getTramiteBandejaDelegate();
			String [] numEntradas = delegate.obtenerNumerosEntradas(codigoProcedimiento, idTramite,procesada,desde,hasta);
			
			if (numEntradas == null || numEntradas.length == 0){
				result = "ERROR:" + resources.getMessage( getLocale( request ), "exportCSV.noHayEntradas");				
			}else{					
				// Obtenemos fichero guia de exportacion y lo parseamos
				FicheroExportacion ficheroExportacion = DelegateUtil.getFicheroExportacionDelegate().obtenerFicheroExportacion(idTramite);
				if (ficheroExportacion == null){
					result = "ERROR:" + resources.getMessage( getLocale( request ), "exportCSV.noFicheroExportacion");
					throw new Exception("No hay configurado fichero de exportacion para el tramite");
				}		
				PropertiesOrdered propsExport = null;
				try{
					ByteArrayInputStream bis = new ByteArrayInputStream(ficheroExportacion.getArchivoFicheroExportacion().getDatos());			
					propsExport = new PropertiesOrdered();
					propsExport.load(bis);
					bis.close();	    	 
				}catch(Exception ex){
					result = "ERROR:" + resources.getMessage( getLocale( request ), "exportCSV.errorFicheroExportacion");
					throw new Exception("Excepcion cargando fichero de exportacion",ex);								
				}		
				
				// Creamos trabajo y lo guardamos en la sesion
				CSVExport trabajo = new CSVExport(idTramite,numEntradas,propsExport);
				CSVExportWorks works = (CSVExportWorks) request.getSession().getAttribute(CSVExportWorks.KEY_CSV_WORKS);
				if (works == null) {
					works = new CSVExportWorks();
					request.getSession().setAttribute(CSVExportWorks.KEY_CSV_WORKS,works);
				}
				works.addCSVExport(trabajo);
				
				result = "INIT:"+trabajo.getId();
			}		
			
		}catch(Exception ex){
			 log.error("Excepcion inciando proceso de exportacion: " + ex.getMessage(),ex);
			 result="ERROR:" + resources.getMessage( getLocale( request ), "exportCSV.errorIniciandoProcesoExportacion") + ": " + ex.getMessage();
		}
			
		
		// Devolvemos Id trabajo
		response.getWriter().print(result);
        return null;
		
    }
	
	
}

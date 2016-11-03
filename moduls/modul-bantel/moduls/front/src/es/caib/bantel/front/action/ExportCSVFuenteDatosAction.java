package es.caib.bantel.front.action;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.Constants;
import es.caib.bantel.model.CampoFuenteDatos;
import es.caib.bantel.model.FilaFuenteDatos;
import es.caib.bantel.model.FuenteDatos;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.util.CsvDocumento;
import es.caib.util.CsvUtil;

/**
 * @struts.action
 *  path="/exportCSVFuenteDatosAction"
 *  scope="session"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 *  
 * @struts.action-forward
 *  name="success" path="/downloadFichero.do"
 *
 */
public class ExportCSVFuenteDatosAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(ExportCSVFuenteDatosAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {

		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		
		try{			
			CsvDocumento csv = new CsvDocumento();
			
			String id = request.getParameter("identificador");
			
			
			FuenteDatos fd = DelegateUtil.getFuenteDatosDelegate().obtenerFuenteDatos(id);
			
			String [] campos = new String[fd.getCampos().size()];
			int i = 0;
			for (Iterator it = fd.getCampos().iterator(); it.hasNext();) {
				CampoFuenteDatos cfd = (CampoFuenteDatos) it.next();
				campos[i] = cfd.getIdentificador();				
				i++;
			}
			csv.setColumnas(campos);
			
			for (int fila = 1; fila <= fd.getFilas().size(); fila++) {
				FilaFuenteDatos ffd = fd.getFilaFuenteDatos(fila);
				int numFilaCsv = csv.addFila();
				for (int columna = 0; columna < campos.length; columna++) {
					 String vfd = ffd.getValorFuenteDatos(campos[columna]);
					 csv.setValor(numFilaCsv, campos[columna], vfd);
				}
			}
			
			byte[] contents = CsvUtil.exportar(csv);
			
			// Devolvemos csv
			request.setAttribute( Constants.NOMBREFICHERO_KEY, fd.getIdentificador() + ".csv" );		
			request.setAttribute( Constants.DATOSFICHERO_KEY, contents);		
			
			return mapping.findForward("success");
		}catch (Exception ex){
			log.error("Excepcion obteniendo fichero csv: " + ex.getMessage(),ex);
			request.setAttribute("message",resources.getMessage( getLocale( request ), "exportCSV.excepcion"));
			return mapping.findForward( "fail" );
		}
    }	
}

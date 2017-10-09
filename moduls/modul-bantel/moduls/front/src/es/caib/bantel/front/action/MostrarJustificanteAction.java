package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.MostrarJustificanteForm;
import es.caib.bantel.model.TramiteBandeja;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.regtel.PluginRegistroIntf;
import es.caib.util.StringUtil;

/**
 * @struts.action
 *  name="mostrarJustificanteForm"
 *  path="/mostrarJustificante"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/downloadFichero.do"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class MostrarJustificanteAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		MostrarJustificanteForm entrada = (MostrarJustificanteForm ) form;
		TramiteBandejaDelegate tramiteDelegate = DelegateUtil.getTramiteBandejaDelegate();
		
		TramiteBandeja t = tramiteDelegate.obtenerTramiteBandeja(entrada.getCodigo());
		
		byte[] content = null;
		
		// Intentamos obtener justificante del Registro
		PluginRegistroIntf plgRegistro = PluginFactory.getInstance().getPluginRegistro();
		content = plgRegistro.obtenerJustificanteRegistroEntrada(t.getProcedimiento().getEntidad(), t.getNumeroRegistro(), t.getFechaRegistro());
		// Si no se puede obtener, se muestra el de la plataforma
		if (content == null) {
			ReferenciaRDS refRDS = new ReferenciaRDS();
			refRDS.setCodigo(t.getCodigoRdsJustificante().longValue());
			refRDS.setClave(t.getClaveRdsJustificante());
			
			RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
			DocumentoRDS documentoRDS = rdsDelegate.consultarDocumentoFormateado(refRDS,t.getIdioma());
			
			content = documentoRDS.getDatosFichero();
		}
		
		String nomFic = StringUtil.generarNombreFicheroJustificante(t.getIdioma(), t.getNumeroRegistro(), t.getFechaRegistro());
		request.setAttribute( Constants.NOMBREFICHERO_KEY, nomFic );					
		request.setAttribute( Constants.DATOSFICHERO_KEY, content);
		
		return mapping.findForward("success");		
    }
}

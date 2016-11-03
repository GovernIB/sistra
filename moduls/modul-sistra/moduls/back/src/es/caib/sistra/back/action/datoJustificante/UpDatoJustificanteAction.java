package es.caib.sistra.back.action.datoJustificante;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.UpDownDatoJustificanteForm;
import es.caib.sistra.persistence.delegate.DatoJustificanteDelegate;
import es.caib.sistra.persistence.delegate.DelegateUtil;

/**
 * 
 * @struts.action
 *  name="upDownDatoJustificanteForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/datoJustificante/up"
 *
 * @struts.action-forward
 *  name="success" path=".datoJustificante.lista"
 * 
 */
public class UpDatoJustificanteAction extends BaseAction
{
    protected static Log log = LogFactory.getLog(UpDatoJustificanteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {
        log.info("Entramos en UpDatoJustificanteAction");
        DatoJustificanteDelegate datoJustificanteDelegate = DelegateUtil.getDatoJustificanteDelegate();
        UpDownDatoJustificanteForm formulario = ( UpDownDatoJustificanteForm ) form;
        
        datoJustificanteDelegate.upDatoJustificante( formulario.getCodigo() );
        
        Long idEspecTramiteNivel = formulario.getIdEspecTramiteNivel();
        Long idTramiteNivel = formulario.getIdTramiteNivel();
        if ( idTramiteNivel != null )
        {
        	guardarTramiteNivel(mapping, request, idTramiteNivel);
        }
        if ( idTramiteNivel == null && idEspecTramiteNivel != null )
        {
        	guardarEspecTramiteNivel( mapping, request, idEspecTramiteNivel );
        }
        
        return mapping.findForward( "success" );
    }
	
}

package es.caib.sistra.back.action.dominio;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.DominioForm;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DominioDelegate;
import es.caib.util.CifradoUtil;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un Dominio.
 *
 * @struts.action
 *  name="dominioForm"
 *  scope="session"
 *  validate="true"
 *  input=".dominio.editar"
 *  path="/back/dominio/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".dominio.editar"
 *
 * @struts.action-forward
 *  name="success" path=".dominio.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".dominio.lista"
 *
 */
public class EditarDominioAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarDominioAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarDominio");

        DominioDelegate dominioDelegate = DelegateUtil.getDominioDelegate();
        DominioForm dominioForm = (DominioForm) form;
        Dominio dominio = (Dominio) dominioForm.getValues();
                
        if (isCancelled(request)) {
            log.info("isCancelled");
            Long idOrgano = dominioForm.getIdOrgano();
            guardarOrgano(mapping, request, idOrgano);            
            return mapping.findForward("cancel");
        }

        if (request.getParameter("borrarTramite") != null) 
        {
            return mapping.findForward("reload");
        } 
        
        if (isAlta(request) || isModificacion(request)) {
            log.info("isAlta || isModificacio");
            
            Long idOrgano = dominioForm.getIdOrgano();
            
            if ( isModificacion( request ) )
            {
		        if ( !idOrgano.equals( dominio.getOrganoResponsable().getCodigo() ) )
		        {
		        	dominioDelegate.borrarDominio( dominio.getCodigo() );
		        	dominio.setCodigo( null );
		        }
            }
            
            String claveCifrado = (String) DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");
            dominio.setUsr(CifradoUtil.cifrar(claveCifrado,dominioForm.getUserPlain()));
            dominio.setPwd(CifradoUtil.cifrar(claveCifrado,dominioForm.getPassPlain()));            
            
            if (dominioForm.getSqlHex() != null) {
            	dominio.setSql(new String(Hex.decodeHex(dominioForm.getSqlHex().toCharArray()), "ISO-8859-15"));
            } else {
            	dominio.setSql(null);
            }
            
            
            dominioDelegate.grabarDominio( dominio, idOrgano );
            //request.setAttribute("reloadMenu", "true");
            log.info("Creat/Actualitzat " + dominio.getCodigo());

            guardarDominio(mapping, request, dominio.getCodigo());

            return mapping.findForward("success");

        }

        return mapping.findForward("reload");
    }

}

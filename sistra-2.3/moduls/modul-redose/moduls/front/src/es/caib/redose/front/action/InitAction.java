package es.caib.redose.front.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.front.form.InitForm;

/**
 * @struts.action
 *  name="initForm"
 *  path="/init"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/comprobarDocumento.do"
 * 
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class InitAction extends BaseAction
{

	Log logger = LogFactory.getLog( InitAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		// Se establece locale y datos de sesion.
		
		InitForm initForm = ( InitForm ) form;
		if ( initForm != null && initForm.getLanguage() != null ){
 			setLocale(request, new Locale( initForm.getLanguage() ) );
		}
			
		// Obtiene lista de consellerias (para cabecera) y la almacenamos en la sesión
		if (request.getSession().getAttribute("LISTA_CONSELLERIAS") == null)
			request.getSession().setAttribute("LISTA_CONSELLERIAS",obtenerListaConsellerias());
				
		
		return mapping.findForward( "success" );
    }
	
	
	/**
	 * Obtiene lista de consellerias del sac
	 */
	private List obtenerListaConsellerias(){
		
		List cons = new ArrayList();
		
		// Obtenemos valores dominio del EJB		
		try{	
			/*
			
			ValoresDominio dom = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( "GESACCONSE" , null);			
			for (int i=1;i<=dom.getNumeroFilas();i++){
				Conselleria c = new Conselleria();
				c.setCodi(dom.getValor(i,"CODI"));
				c.setAbreviatura(dom.getValor(i,"ABREVIATURA"));
				cons.add(c);			
			}
			*/
		}catch (Exception ex){
			logger.error("Error accediendo a dominio GESACCONSE de Sistema de Tramitación: " + ex.getMessage(),ex);
		}		
		
		return cons;
		
	}

	
}

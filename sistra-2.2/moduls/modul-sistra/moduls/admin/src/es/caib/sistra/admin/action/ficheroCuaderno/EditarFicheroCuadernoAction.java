package es.caib.sistra.admin.action.ficheroCuaderno;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.model.Formulario;

import es.caib.sistra.admin.action.BaseAction;
import es.caib.sistra.admin.form.FicheroCuadernoForm;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.model.admin.FicheroCuaderno;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.FicheroCuadernoDelegate;

/**
 * Action para editar un FicheroCuaderno.
 *
 * @struts.action
 *  name="ficheroCuadernoForm"
 *  scope="request"
 *  validate="true"
 *  input=".ficheroCuaderno.editar"
 *  path="/admin/ficheroCuaderno/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/admin/ficheroCuaderno/alta.do"
 *
 * @struts.action-forward
 *  name="reload" path=".ficheroCuaderno.editar"
 *
 * @struts.action-forward
 *  name="success" path="/admin/cuadernoCarga/seleccion.do"
 *
 * @struts.action-forward
 *  name="cancel" path="/admin/cuadernoCarga/seleccion.do"
 *
 */

public class EditarFicheroCuadernoAction extends BaseAction
{
	public static Log log = LogFactory.getLog( EditarFicheroCuadernoAction.class );
	
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
    	log.debug( "Entramos en EditarFicheroCuaderno" );
    	FicheroCuadernoForm ficheroCuadernoForm = ( FicheroCuadernoForm ) form;
    	request.setAttribute( "codigoCuaderno", ficheroCuadernoForm.getCodigoCuaderno().toString() );
    	
        if (isCancelled(request)) 
        {
            log.debug("isCancelled");
            return mapping.findForward("cancel");
        }
        String tipoFichero = null;
        if (isAlta(request) || isModificacion(request)) 
        {
            log.info("isAlta || isModificacio");
            FicheroCuadernoDelegate delegate = DelegateUtil.getFicheroCuadernoDelegate();
            FicheroCuaderno ficheroCuaderno = null;
            
            if ( isModificacion( request ) )
            {
            	ficheroCuaderno = delegate.obtenerFicheroCuaderno( ficheroCuadernoForm.getCodigoFichero() );
            }
            else
            {
            	ficheroCuaderno = new FicheroCuaderno();
            }
            byte[] contenidoFichero = ficheroCuadernoForm.getFitxer().getFileData();
            
        	try
        	{
        		Dominio dominio = Dominio.fromXml( contenidoFichero );
        		if ( dominio != null )
        		{
        			tipoFichero = FicheroCuaderno.TIPO_DOMINIO;
        		}
        			
        	}
        	catch( Exception exc )
        	{
        		// No se trata de un dominio
        	}
            	
            if ( tipoFichero == null )
            {
            	try
            	{
            		Formulario formulario = Formulario.fromXml( contenidoFichero );
            		if ( formulario != null )
            		{
                		tipoFichero = FicheroCuaderno.TIPO_FORMULARIO;
            		}
            	}
            	catch( Exception exc )
            	{
            		// No se trata de un formulario
            	}
            }
            if ( tipoFichero == null )
            {
            	try
            	{
            		TramiteVersion tramite = TramiteVersion.fromXml( contenidoFichero );
            		if ( tramite != null )
            		{
            			tipoFichero = FicheroCuaderno.TIPO_TRAMITE; 
            		}
            	}
            	catch( Exception exc )
            	{
            	}
            }
            if ( tipoFichero == null )
            {
	    		request.setAttribute( "errorKey", "ficheroCuaderno.tipoFicheroDesconocido" );
            	/*
            	ActionErrors actionErrors = new ActionErrors();
            	actionErrors.add( "ficheroCuaderno.tipoFicheroDesconocido", new ActionMessage( ( String ) request.getAttribute( "errorKey" ) ));
            	this.saveErrors( request, actionErrors)
            	;
            	*/
	    		return mapping.findForward( "reload" );
            }
            
            /*
            
            if ( request.getAttribute( "errorKey" ) != null )
            {
            	ActionErrors actionErrors = new ActionErrors();
            	actionErrors.add( ( String ) request.getAttribute( "errorKey" ), new ActionMessage( ( String ) request.getAttribute( "errorKey" ) ));
            	this.saveErrors( request, actionErrors);
            	return mapping.findForward( "reload" );
            }
            */
            
            ficheroCuaderno.setContenido( contenidoFichero );
            ficheroCuaderno.setNombre( ficheroCuadernoForm.getFitxer().getFileName() );
            
            
            ficheroCuaderno.setCodigo( ficheroCuadernoForm.getCodigoFichero() );
            ficheroCuaderno.setTipo( tipoFichero );
            delegate.grabarFicheroCuaderno( ficheroCuaderno, ficheroCuadernoForm.getCodigoCuaderno() );
            request.setAttribute( "ficheroCuaderno", ficheroCuaderno );
            
        }
        
        return mapping.findForward( "success" );
    }	
}

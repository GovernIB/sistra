package es.caib.mobtratel.front.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.mobtratel.front.form.DetalleEnvioForm;
import es.caib.mobtratel.model.Envio;
import es.caib.mobtratel.model.MensajeEmail;
import es.caib.mobtratel.model.MensajeSms;
import es.caib.mobtratel.modelInterfaz.ConstantesMobtratel;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.EnvioDelegate;
import es.caib.mobtratel.persistence.delegate.PermisoDelegate;

/**
 * @struts.action
 *  name="detalleEnvioForm"
 *  path="/detalleEnvio"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".detalle"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class DetalleEnvioAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleEnvioForm detalleEnvioFormulario = ( DetalleEnvioForm ) form;
		EnvioDelegate delegate = DelegateUtil.getEnvioDelegate();
		Envio envio = delegate.obtenerEnvio( detalleEnvioFormulario.getCodigo() );
		
		
		PermisoDelegate gd = DelegateUtil.getPermisoDelegate();
    	List permisos = gd.listarPermisos(this.getPrincipal(request));
		String permitir = (permisos.size() != 0) ? "S" : "N" ;
		
		request.setAttribute( "envio", envio );
		List erroresSms = getErroresSms(envio.getSmss());
		if(erroresSms.size()!=0) request.setAttribute( "smss",erroresSms);
		List erroresEmail = getErroresEmails(envio.getEmails());
		if(erroresEmail.size()!=0) request.setAttribute( "emails", erroresEmail);
		if((erroresSms.size()!=0) || (erroresEmail.size() != 0)) request.setAttribute( "erroresMensajes", "true");
		request.setAttribute("permitirCambioEstado",permitir);
		
		return mapping.findForward( "success" );
    }
	
	private List getErroresSms(Set smss)
	{
		List result = new ArrayList();
		for(Iterator it=smss.iterator(); it.hasNext();)
		{
			MensajeSms ms = (MensajeSms) it.next();
			if((ms.getEstado() == ConstantesMobtratel.CON_ERROR) && (ms.getError() != null ) &&
				!ms.getError().equals(""))
				result.add(ms);
		}
		return result;
	}
	private List getErroresEmails(Set emails)
	{
		List result = new ArrayList();
		for(Iterator it=emails.iterator(); it.hasNext();)
		{
			MensajeEmail me = (MensajeEmail) it.next();
			if((me.getEstado() == ConstantesMobtratel.CON_ERROR) && (me.getError() != null ) &&
				!me.getError().equals(""))
				result.add(me);
		}
		return result;
	}
}

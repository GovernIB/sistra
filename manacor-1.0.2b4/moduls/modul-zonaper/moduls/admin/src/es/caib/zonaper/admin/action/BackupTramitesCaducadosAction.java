package es.caib.zonaper.admin.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.persistence.delegate.BackupDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  path="/backupTramitesCaducados"
 *  scope="request"
 *  validate="false"
 */
public class BackupTramitesCaducadosAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		BackupDelegate delegate = DelegateUtil.getBackupDelegate();
		Date fechaEjecucion = new Date();
		delegate.procesaTramitesCaducados( fechaEjecucion, false );
		response.sendRedirect("http://www.google.es");
		return null;
	}    
	

}

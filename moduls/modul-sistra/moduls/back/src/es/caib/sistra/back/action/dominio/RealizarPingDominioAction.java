package es.caib.sistra.back.action.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.PingDominioForm;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.delegate.DelegateSISTRAUtil;
import es.caib.util.StringUtil;

/**
 * Action para realizar un ping sobre un dominio
 *
 * @struts.action
 *  path="/back/dominio/realizarPing"
 *  name="pingDominioForm"
 *  scope="session"
 *
 * @struts.action-forward
 *  name="success" path=".dominio.ping"
 */
public class RealizarPingDominioAction extends BaseAction {

	 protected static Log log = LogFactory.getLog( RealizarPingDominioAction.class );

     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	PingDominioForm fForm = (PingDominioForm) form;

    	List params = new ArrayList();
    	if (StringUtils.isNotEmpty(fForm.getParametros())){
    		StringTokenizer st = new StringTokenizer(fForm.getParametros(),"#");
    		while (st.hasMoreElements()){
    			params.add(st.nextToken());
    		}
    	}

    	try{
	    	ValoresDominio val = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio(fForm.getDominio(),params, true);
	    	if (val.isError()){
	    		request.setAttribute("error",val.getDescripcionError());
	    	}else{
	    		request.setAttribute("valores",val);
	    	}
    	}catch (Exception ex){
    		String error = StringUtil.stackTraceToString(ex);
    		request.setAttribute("error",error);
    	}

    	return mapping.findForward("success");

    }

}

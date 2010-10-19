package es.caib.sistra.front.action;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  path="/protected/finalizar"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".finalizar"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class FinalizarAction extends BaseAction
{

	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		
		
		// Intentamos borrar trámite si aún existe
		String urlFin = null;
		try{
			InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request.getParameter("ID_INSTANCIA"), request );
			urlFin = delegate.obtenerUrlFin();
			
			// Comprobamos si tras terminar el tramite se debe ir a la zona personal a mostrar el tramite
			// No valido para consulta/asistente
			if (urlFin.equals("[ZONAPER]")){ 
				String idPersistencia = delegate.obtenerIdPersistencia();
				urlFin = "/zonaperfront/inicio?tramite=" + idPersistencia;				
			}
			 
			delegate.borrarTramite();			
		}catch(Exception ex){		
			ex.printStackTrace();
		}
		
		// Desregistramos instancia y pasamos a url fin
		InstanciaManager.desregistrarInstancia( request );
		if (urlFin == null){
			return mapping.findForward( "success" );
		}else{
			response.sendRedirect("/sistrafront/redireccion.jsp?url=" + URLEncoder.encode(urlFin));
			return null;
		}
	}

}

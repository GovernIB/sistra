package es.caib.redose.back.action.plantilla;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.back.form.PlantillaForm;
import es.caib.redose.model.Plantilla;
import es.caib.redose.model.PlantillaIdioma;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.PlantillaDelegate;

/**
 * @struts.action
 *  path="/back/plantilla/mostrarFichero"
 *  name="plantillaForm"
 *  scope="session"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path="/downloadFichero.do"
 *  
 * @struts.action-forward
 *  name="fail" path="/init.do"
 */
public class MostrarFicheroPlantillaAction extends Action  {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
			
		String lang = request.getParameter("lang");
		String idPlantilla = request.getParameter("codigo");
		
		PlantillaForm plantillaForm = (PlantillaForm) form;
        Plantilla plantillaSesion = (Plantilla) plantillaForm.getValues();
        PlantillaIdioma  plantillaIdioma  = (PlantillaIdioma) plantillaSesion.getTraduccion(lang);
		PlantillaDelegate pd = DelegateUtil.getPlantillaDelegate();
		Plantilla plantilla = pd.obtenerPlantilla(new Long(idPlantilla));
		
		if(!"".equals(plantillaIdioma.getNombreFichero()) && plantillaIdioma.getArchivo() != null && plantillaIdioma.getArchivo().getDatos() != null){
        	request.setAttribute("nombreFichero", plantillaIdioma.getNombreFichero() );		
			request.setAttribute(  "datosFichero", plantillaIdioma.getArchivo().getDatos() );
		
			return mapping.findForward("success");
        }else{
        	plantillaIdioma  = (PlantillaIdioma) plantilla.getTraduccion(lang);
    		if(plantillaIdioma != null){
		request.setAttribute("nombreFichero", plantillaIdioma.getNombreFichero() );		
		request.setAttribute(  "datosFichero", plantillaIdioma.getArchivo().getDatos() );
		
		return mapping.findForward("success");
    		}else{
    			return mapping.findForward("fail");
    		}
        }
	}
	
}


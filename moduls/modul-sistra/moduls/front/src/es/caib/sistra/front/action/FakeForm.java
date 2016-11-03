package es.caib.sistra.front.action;

import java.io.ByteArrayInputStream;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import es.caib.sistra.front.Constants;
import es.caib.xml.ConstantesXML;
import es.caib.xml.analiza.Analizador;
import es.caib.xml.analiza.Nodo;
import es.caib.xml.util.HashMapIterable;

/**
 * @struts.action
 *  name="fakeFormForm"
 *  path="/protected/fakeForm"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".fakeFormLayout"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */

public class FakeForm extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		DynaActionForm dynaActionForm = ( DynaActionForm ) form;
		String xmlForm = ( String ) dynaActionForm.get( "datosAnteriores" );
		mapping.getModuleConfig().findFormBeanConfig( "fakeRetornarATramitacionForm" );				
		
		Analizador analizador = new Analizador();
		HashMapIterable datos = analizador.analizar(new ByteArrayInputStream ( xmlForm.getBytes( ConstantesXML.ENCODING )),ConstantesXML.ENCODING);
		for (Iterator it=datos.iterator();it.hasNext();)
		{
			Nodo nodo = (Nodo)it.next();
			String campo = nodo.getXpath().substring(("/instancia/").length());
			dynaActionForm.set( campo, nodo.getValor());
		}
		
		request.setAttribute( Constants.DESCRIPCION_TRAMITE_PARAMS_KEY,  request.getParameter( Constants.DESCRIPCION_TRAMITE_PARAMS_KEY) );
		
		return mapping.findForward( "success" );
    }
}

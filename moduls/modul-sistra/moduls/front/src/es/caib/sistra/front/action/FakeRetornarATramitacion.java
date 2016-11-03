package es.caib.sistra.front.action;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import es.caib.xml.ConstantesXML;
import es.caib.xml.analiza.Generador;
import es.caib.xml.analiza.Nodo;
import es.caib.xml.util.HashMapIterable;

/**
 * @struts.action
 *  name="fakeRetornarATramitacionForm"
 *  path="/protected/fakeRetornarATramitacion"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".fakeRetornarATramitacion"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class FakeRetornarATramitacion extends BaseAction
{
	private static String[] reservedFields = new String[] { "ID_INSTANCIA", "version", "identificador", "instancia", "urlRetorno" }; 
	                      
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		// Construir el XML de retorno a forms.
		DynaActionForm dynaForm = ( DynaActionForm ) form;
		Map fields = dynaForm.getMap();
		HashMapIterable datos = new HashMapIterable();
		for ( Iterator it = fields.keySet().iterator(); it.hasNext(); )
		{						
			String fieldName = ( String ) it.next();
			if ( isReservedField( fieldName ) )
			{
				continue;
			}
			String fieldValue = ( String ) fields.get( fieldName );
			Nodo nodo = new Nodo("/instancia/"+fieldName,fieldValue);
			datos.put(nodo.getXpath(),nodo);
		}
		
		Generador generador = new Generador();
		generador.setEncoding(ConstantesXML.ENCODING);
		String xml = generador.generarXML(datos);
		
		request.setAttribute( "datos", xml );
		return mapping.findForward( "success" );
    }
	
	
	private boolean isReservedField( String fieldName )
	{
		for ( int i = 0; i < reservedFields.length; i++ )
		{
			if ( reservedFields[ i ].equals( fieldName ) )
			{
				return true;
			}
		}
		return false;
	}
}

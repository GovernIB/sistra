package es.caib.zonaper.back.action;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.xml.datospropios.factoria.ConstantesDatosPropiosXML;
import es.caib.zonaper.back.form.DetallePreregistroForm;
import es.caib.zonaper.model.DocumentoEntradaPreregistro;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;

/**
 * @struts.action
 *  name="detallePreregistroForm"
 *  path="/detallePreregistro"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".detallePreregistro"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class DetallePreregistroAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetallePreregistroForm formulario = ( DetallePreregistroForm ) form;
		EntradaPreregistroDelegate delegate = DelegateUtil.getEntradaPreregistroDelegate();
		EntradaPreregistro preregistro = delegate.obtenerEntradaPreregistroReg( formulario.getCodigo() );
		request.setAttribute ( "preregistro", preregistro );
		
		// Comprobamos is se debe mostrar el justificante o bien existe un formulario-justificante
		String mostrarJust = "S";
		for (Iterator it = preregistro.getDocumentos().iterator();it.hasNext();){
			DocumentoEntradaPreregistro doc = (DocumentoEntradaPreregistro) it.next();
			if (doc.getTipoDocumento() != null && doc.getTipoDocumento().charValue() == ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO_JUSTIFICANTE){
				mostrarJust = "N";
				break;
			}
		}
		request.setAttribute ( "mostrarJustificante", mostrarJust );
		
		return mapping.findForward( "success" );
    }
}

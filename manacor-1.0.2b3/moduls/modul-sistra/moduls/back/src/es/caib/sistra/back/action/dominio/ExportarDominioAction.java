package es.caib.sistra.back.action.dominio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.model.betwixt.Configurator;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DominioDelegate;
import es.caib.xml.ConstantesXML;

/**
 * @struts.action
 * path="/back/dominio/exportar"
 * scope="request"
 * validate="false" 
 * @author clmora
 *
 */
public class ExportarDominioAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DominioDelegate delegate = DelegateUtil.getDominioDelegate();
		Long idDominio = new Long ( request.getParameter( "codigo" ) );
		Dominio dominio = delegate.obtenerDominio( idDominio );
		response.setContentType("application/octet-stream");
		String contentDispositionHeader = "attachment; filename=\"dominio-" + dominio.getIdentificador() + ".xml\"";
		response.setHeader( "Content-Disposition", contentDispositionHeader );
        BeanWriter beanWriter = new BeanWriter(response.getOutputStream(), ConstantesXML.ENCODING );
        beanWriter.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"" + ConstantesXML.ENCODING + "\" ?>");
        Configurator.configure(beanWriter);
        beanWriter.write(dominio);
        beanWriter.close();
		return null;
    }
}

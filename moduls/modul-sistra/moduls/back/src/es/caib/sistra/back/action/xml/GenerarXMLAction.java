package es.caib.sistra.back.action.xml;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.Constants;
import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.model.betwixt.Configurator;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;
import es.caib.xml.ConstantesXML;

/**
 * @struts.action
 * path="/generar/xml"
 * scope="request"
 * validate="false"
 * 
 * @struts.action-forward
 *  name="success" path="/downloadFichero.do"
 */
public class GenerarXMLAction extends BaseAction {

    protected static final Log log = LogFactory.getLog(GenerarXMLAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        
    	// Recuperamos version tramite
    	TramiteVersionDelegate delegate = DelegateUtil.getTramiteVersionDelegate();
        Long idTramiteVersion = new Long(request.getParameter("codigo"));
        TramiteVersion tramiteVersion = delegate.obtenerTramiteVersion( idTramiteVersion ); 
        tramiteVersion = delegate.obtenerTramiteVersionCompleto( tramiteVersion.getTramite().getIdentificador(), tramiteVersion.getVersion() );
        tramiteVersion.setFechaExportacion(new Date());
        
        // Reseteamos informacion de bloqueo (xa que cuando se importe no este bloqueado)
        tramiteVersion.setBloqueadoModificacion("N");
        tramiteVersion.setBloqueadoModificacionPor(null);
        
        // Generamos xml de exportacion
        ByteArrayOutputStream bos = new ByteArrayOutputStream(8192);
        BeanWriter beanWriter = new BeanWriter(bos, ConstantesXML.ENCODING );
        beanWriter.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"" + ConstantesXML.ENCODING + "\" ?>");
        //beanWriter.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"ISO-8859-15\" ?>");
        Configurator.configure(beanWriter);
        beanWriter.write(tramiteVersion);
        beanWriter.close();
        
        byte[] contentFic = bos.toByteArray();
        String nombreFic = "tramite-" + tramiteVersion.getTramite().getIdentificador()+ "-" + tramiteVersion.getVersion() + ".xml";
        bos.close();
        
        // Devolvemos XML
		request.setAttribute( Constants.NOMBREFICHERO_KEY, nombreFic );		
		request.setAttribute( Constants.DATOSFICHERO_KEY, contentFic );
		return mapping.findForward( "success" );        
    }
}

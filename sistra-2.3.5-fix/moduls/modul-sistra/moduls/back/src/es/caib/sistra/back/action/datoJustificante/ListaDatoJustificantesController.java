package es.caib.sistra.back.action.datoJustificante;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DatoJustificanteDelegate;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.EspecTramiteNivelDelegate;

/**
 * Guarda la lista de datoJustificantes de un datoJustificante en el contexto.
 */
public class ListaDatoJustificantesController implements Controller{
    protected static Log log = LogFactory.getLog(ListaDatoJustificantesController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.info("Entramos en ListaDatoJustificantesController");
            
            String idTramiteNivel = request.getParameter( "idTramiteNivel" );
            String idTramiteVersion = request.getParameter( "idTramiteVersion" );
            
            Long id = (Long) request.getAttribute("idEspecTramiteNivel");
            if ( id == null )
            {
            	id = Long.valueOf( request.getParameter( "idEspecTramiteNivel" ) ); 
            }
            if(id==null){
                ComponentContext context = ComponentContext.getContext(request);
                if (context != null){
                    if (context.getAttribute("id").toString().length() > 0) {
                         id = new Long(context.getAttribute("id").toString());
                    }
                }
            }
            DatoJustificanteDelegate datoJustificanteDelegate = DelegateUtil.getDatoJustificanteDelegate();
            
            java.util.List lDatoJustificantes = datoJustificanteDelegate.listarDatoJustificantees( id );
            //log.info( mDatoJustificantees );
            tileContext.putAttribute("datoJustificanteOptions", lDatoJustificantes);
            request.setAttribute( "datoJustificanteOptions", lDatoJustificantes);
            tileContext.putAttribute("idEspecTramiteNivel", id);
            idTramiteNivel = idTramiteNivel == null ?  "" : idTramiteNivel;
            request.setAttribute("idTramiteNivel", idTramiteNivel);
            idTramiteVersion = idTramiteVersion == null ?  "" : idTramiteVersion; 
            request.setAttribute("idTramiteVersion", idTramiteVersion);
            
            // Establecemos bloqueo
            EspecTramiteNivelDelegate espd = DelegateUtil.getEspecTramiteNivelDelegate();
            TramiteVersion tv = espd.obtenerTramiteVersion(id);
            String bloqueado = tv.getBloqueadoModificacion();
            String bloqueadoPor = tv.getBloqueadoModificacionPor();
            if (bloqueado.equals("S")){
            	if (!request.getUserPrincipal().getName().equals(bloqueadoPor)){	        	
    	        	request.setAttribute("bloqueado",bloqueadoPor);
            	}
            }else{
            	request.setAttribute("bloqueado","");
            }   
            
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}

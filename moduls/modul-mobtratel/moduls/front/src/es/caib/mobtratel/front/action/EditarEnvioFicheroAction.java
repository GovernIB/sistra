package es.caib.mobtratel.front.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import es.caib.mobtratel.front.Constants;
import es.caib.mobtratel.front.form.EditarEnvioFicheroForm;
import es.caib.mobtratel.model.Constantes;
import es.caib.mobtratel.modelInterfaz.MensajeEnvio;
import es.caib.mobtratel.modelInterfaz.MensajeEnvioEmail;
import es.caib.mobtratel.modelInterfaz.MensajeEnvioSms;
import es.caib.mobtratel.persistence.delegate.DelegateMobTraTelUtil;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.FormatoException;
import es.caib.mobtratel.persistence.delegate.LimiteDestinatariosException;
import es.caib.mobtratel.persistence.delegate.MaxCaracteresSMSException;
import es.caib.mobtratel.persistence.delegate.MobTraTelDelegate;
import es.caib.mobtratel.persistence.delegate.MobilidadException;
import es.caib.mobtratel.persistence.delegate.PermisoException;
import es.caib.xml.ConstantesXML;
import es.caib.xml.movilidad.factoria.FactoriaObjetosXMLMovilidad;
import es.caib.xml.movilidad.factoria.ServicioMovilidadXML;
import es.caib.xml.movilidad.factoria.impl.Envio;
import es.caib.xml.movilidad.factoria.impl.MensajeEMAIL;
import es.caib.xml.movilidad.factoria.impl.MensajeSMS;



/**
 * Action para editar un Envio desde un Fichero.
 *
 * @struts.action
 *  name="editarEnvioFicheroForm"
 *  path="/editarEnvioFichero"
 *  scope="session"
 *  validate="true"
 *  input=".editarFichero"
 *  
 * @struts.action-forward
 *  name="success" path=".editarFichero"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 *  
 */
public class EditarEnvioFicheroAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarEnvioFicheroAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	request.setAttribute( "idReadOnly", new Boolean( true ) );
    	
    	log.info("Entramos en EditarEnvioFichero");
        
        EditarEnvioFicheroForm envioFicheroForm = (EditarEnvioFicheroForm) form;

        System.out.println("Tamaño fichero: " + envioFicheroForm.getFichero().getFileSize() +  " nombre: " + envioFicheroForm.getFichero().getFileName());

        Envio envio = null;
        try{
        	envio = validaFichero(envioFicheroForm.getFichero());
        }
        catch(Exception e)
        {
        	request.setAttribute("messageKey", "Error XML:" + e.getMessage());
            return mapping.findForward("success");
        }
        
        MobTraTelDelegate delegate = DelegateMobTraTelUtil.getMobTraTelDelegate();
        try{
            MensajeEnvio mensaje = new MensajeEnvio();
            mensaje.setNombre(envio.getNombre());
            mensaje.setCuentaEmisora(envio.getCuenta());
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMATO_FECHAS_XML);
            if((envio.getFechaCaducidad() != null) && (!envio.getFechaCaducidad().equals(""))){
            	mensaje.setFechaCaducidad(sdf.parse(envio.getFechaCaducidad()));
            }else{
    			Properties config = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
    			//si no esta definida la propiedad en mobtratel.properties por defecto le metemos 15 dias.
    			int dias = 15;
    			if(config.getProperty("envio.limite.sin.fecha.caducidad") != null && !"".equals(config.getProperty("envio.limite.sin.fecha.caducidad"))){
    				try{
    					dias = Integer.parseInt(config.getProperty("envio.limite.sin.fecha.caducidad"));
    				}catch(Exception e){
    					dias = 15;
    				}
    			}
    			Calendar cal = Calendar.getInstance();
    			cal.add(Calendar.DATE, dias);
    			mensaje.setFechaCaducidad(cal.getTime());
    		}
            
            // Si el mensaje se ha marcado como inmediato no hacemos caso de la fecha de programacion
            if ("S".equals(envio.getInmediato())){
            	mensaje.setInmediato(true);
            }else{
            	if (StringUtils.isNotEmpty(envio.getFechaProgramacion())){
    	            mensaje.setFechaProgramacionEnvio(sdf.parse(envio.getFechaProgramacion()));
                }
            }
               
            List emails = envio.getMensajesEMAIL();
            List emailsE = new ArrayList();
            for(Iterator it=emails.iterator(); it.hasNext();)
            {
            	MensajeEMAIL mE = (MensajeEMAIL)it.next();
            	MensajeEnvioEmail mee = new MensajeEnvioEmail();
            	String[] dest = mE.getEmails().split(Constantes.SEPARADOR_DESTINATARIOS);
            	mee.setDestinatarios(dest);
            	mee.setTexto(mE.getTexto());
            	mee.setTitulo(mE.getTitulo());
            	emailsE.add(mee);
            }
            mensaje.setEmails(emailsE);

            List smss = envio.getMensajesSMS();
            List smssE = new ArrayList();
            for(Iterator it=smss.iterator(); it.hasNext();)
            {
            	MensajeSMS mS = (MensajeSMS)it.next();
            	MensajeEnvioSms mes = new MensajeEnvioSms();
            	String[] dest = mS.getTelefonos().split(Constantes.SEPARADOR_DESTINATARIOS);
            	mes.setDestinatarios(dest);
            	mes.setTexto(mS.getTexto());
            	smssE.add(mes);
            }
            mensaje.setSmss(smssE);

        	delegate.envioMensaje(mensaje);
        }
        catch(PermisoException e)
        {
        	System.out.println(e.getCause());
        	request.setAttribute( "errorFormato", e.getMessage());
        	return mapping.findForward( "success" );
        }
        catch(FormatoException e)
        {
        	System.out.println(e.getMessage());
        	request.setAttribute( "errorFormato", e.getMessage());
        	return mapping.findForward( "success" );
        }
        catch(LimiteDestinatariosException e)
        {
        	System.out.println(e.getCause());
        	request.setAttribute( "errorFormato", e.getMessage());
        	return mapping.findForward( "success" );
        }
		catch(MaxCaracteresSMSException e)
		{
			System.out.println(e.getCause());
			request.setAttribute( "errorFormato", e.getMessage());
			return mapping.findForward( "success" );
		}
        catch(MobilidadException e)
        {
        	System.out.println(e.getCause());
        	request.setAttribute( "errorFormato", e.getMessage());
        	return mapping.findForward( "success" );
        }

        	request.setAttribute( "ok", "editarEnvioFichero.ok" );
        return mapping.findForward("success");
    }
    
    private Envio validaFichero(FormFile formFile) throws Exception {
    	FactoriaObjetosXMLMovilidad factoria = ServicioMovilidadXML.crearFactoriaObjetosXML();
    	factoria.setEncoding(ConstantesXML.ENCODING);
    	factoria.setIndentacion(true);
    	
    	Envio envio = factoria.crearEnvio(formFile.getInputStream());					
        return envio;
    }
    

}

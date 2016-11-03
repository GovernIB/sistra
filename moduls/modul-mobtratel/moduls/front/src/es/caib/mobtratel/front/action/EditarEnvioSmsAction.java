package es.caib.mobtratel.front.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.mobtratel.front.Constants;
import es.caib.mobtratel.front.form.EditarEnvioSmsForm;
import es.caib.mobtratel.model.Constantes;
import es.caib.mobtratel.modelInterfaz.MensajeEnvio;
import es.caib.mobtratel.modelInterfaz.MensajeEnvioSms;
import es.caib.mobtratel.persistence.delegate.DelegateMobTraTelUtil;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.FormatoException;
import es.caib.mobtratel.persistence.delegate.LimiteDestinatariosException;
import es.caib.mobtratel.persistence.delegate.MaxCaracteresSMSException;
import es.caib.mobtratel.persistence.delegate.MobTraTelDelegate;
import es.caib.mobtratel.persistence.delegate.MobilidadException;
import es.caib.mobtratel.persistence.delegate.PermisoException;

/**
 * @struts.action
 *  name="editarEnvioSmsForm"
 *  path="/editarEnvioSMS"
 *  scope="session"
 *  validate="true"
 *  input=".editarSMS"
 *  
 * @struts.action-forward
 *  name="success" path=".editarSMS"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class EditarEnvioSmsAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
		EditarEnvioSmsForm formulario 	= ( EditarEnvioSmsForm ) form;
		MensajeEnvio mensaje= new MensajeEnvio();
		mensaje.setCuentaEmisora(formulario.getCuenta());
		mensaje.setNombre(formulario.getNombre());
		MensajeEnvioSms mes = new MensajeEnvioSms();
		String telefonos = formulario.getDestinatarios();
		String[] destinatarios = telefonos.split(Constantes.SEPARADOR_DESTINATARIOS);
		mes.setDestinatarios(destinatarios);
		mes.setTexto(formulario.getMensaje());
		mes.setVerificarEnvio("S".equals(formulario.getConfirmarEnvio()));
		mensaje.addSMS(mes);
		
		SimpleDateFormat df = new SimpleDateFormat(Constants.FORMATO_FECHAS);
		String fecha = formulario.getFechaCaducidad();
		String hora = formulario.getHoraCaducidad();
		if((fecha != null) && (!fecha.equals("")))
		{
			if((hora == null) || (hora.equals(""))) fecha += " 00:00";
			else fecha += " " + hora + ":00";
			Date fechaCaducidad = df.parse(fecha);
			mensaje.setFechaCaducidad(fechaCaducidad);
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
		
		if ("S".equals(formulario.getInmediato())){
			mensaje.setInmediato(true);
		}else{		
			fecha = formulario.getFechaProgramacion();
			hora = formulario.getHoraProgramacion();
			if((fecha != null) && (!fecha.equals("")))
			{
				if((hora == null) || (hora.equals(""))) fecha += " 00:00";
				else fecha += " " + hora + ":00";
				Date fechaProgramacion= df.parse(fecha);
				mensaje.setFechaProgramacionEnvio(fechaProgramacion);
			}
		}
		
		MobTraTelDelegate delegate = DelegateMobTraTelUtil.getMobTraTelDelegate();
		try{
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
		request.setAttribute( "ok", "editarEnvio.sms.ok" );
		return mapping.findForward( "success" );
    }

}

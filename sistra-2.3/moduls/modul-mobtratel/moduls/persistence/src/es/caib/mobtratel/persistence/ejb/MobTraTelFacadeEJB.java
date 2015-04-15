package es.caib.mobtratel.persistence.ejb;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.naming.InitialContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.model.Constantes;
import es.caib.mobtratel.model.Cuenta;
import es.caib.mobtratel.model.Envio;
import es.caib.mobtratel.model.MensajeEmail;
import es.caib.mobtratel.model.MensajeSms;
import es.caib.mobtratel.model.Permiso;
import es.caib.mobtratel.modelInterfaz.ConstantesMobtratel;
import es.caib.mobtratel.modelInterfaz.EstadoMensajeEnvio;
import es.caib.mobtratel.modelInterfaz.EstadoMensajeEnvioEmail;
import es.caib.mobtratel.modelInterfaz.EstadoMensajeEnvioSms;
import es.caib.mobtratel.modelInterfaz.MensajeEnvio;
import es.caib.mobtratel.modelInterfaz.MensajeEnvioEmail;
import es.caib.mobtratel.modelInterfaz.MensajeEnvioSms;
import es.caib.mobtratel.persistence.delegate.CuentaDelegate;
import es.caib.mobtratel.persistence.delegate.DelegateException;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.EnvioDelegate;
import es.caib.mobtratel.persistence.delegate.FormatoException;
import es.caib.mobtratel.persistence.delegate.LimiteDestinatariosException;
import es.caib.mobtratel.persistence.delegate.MaxCaracteresSMSException;
import es.caib.mobtratel.persistence.delegate.MobilidadException;
import es.caib.mobtratel.persistence.delegate.PermisoDelegate;
import es.caib.mobtratel.persistence.delegate.PermisoException;
import es.caib.mobtratel.persistence.util.MobUtils;
import es.caib.sistra.plugins.email.ConstantesEmail;
import es.caib.sistra.plugins.sms.ConstantesSMS;
import es.caib.util.ValidacionesUtil;
import es.caib.xml.ConstantesXML;


/**
 * SessionBean para operaciones de otros módulos con MobTraTel
 *
 * @ejb.bean
 *  name="mobtratel/persistence/MobTraTelFacade"
 *  jndi-name="es.caib.mobtratel.persistence.MobTraTelFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * @ejb.env-entry name="roleAuto" type="java.lang.String" value="${role.auto}"
 * 
 * @ejb.security-role-ref role-name="${role.auto}" role-link="${role.auto}"
 */
public abstract class MobTraTelFacadeEJB implements SessionBean
{
	private Log log = LogFactory.getLog( MobTraTelFacadeEJB.class );	
	private javax.ejb.SessionContext ctx;
	private String roleAuto;
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.gestor},${role.auto}"
     */
	public void ejbCreate() throws CreateException 
	{
		try{
			if (roleAuto == null){
				InitialContext initialContext = new InitialContext();
				 roleAuto = (String) initialContext.lookup( "java:comp/env/roleAuto" );
			}		
		}catch(Exception ex){
			throw new CreateException("No se puede establecer roleAuto");
		}
		
	}
	
    public void setSessionContext(javax.ejb.SessionContext ctx) 
    {
	   this.ctx = ctx;
    }
	
	/**
	 * Realiza un Envio con n mensajes de tipo Email o SMS
	 * Podra lanzar las siguientes excepciones:
	 * <ul>
	 *  <li>LimiteDestinatariosException: Cuando se supere el limite de destinatarios por mensaje.</li>
	 * 	<li>PermisoException: Cuando se intente enviar un SMS con una cuenta a la que no tiene</li>
	 *   permiso el usuario que realiza la invocacion</li>
	 *  <li>MobilidadException: En cualquier otro caso.</li>
	 * </ul> 
	 * @throws DelegateException
	 * @return Devuelve el codigo del Envio. 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor},${role.auto}"
     */
	public String envioMensaje(MensajeEnvio mensaje) throws DelegateException
	{
		EnvioDelegate ed = DelegateUtil.getEnvioDelegate();
		CuentaDelegate cd = DelegateUtil.getCuentaDelegate();
		MobUtils utils = MobUtils.getInstance();
		Long codigo = null;
		String erroresFormato = "";
		try {
			Envio envio = new Envio();
			envio.setEstado(ConstantesMobtratel.ESTADOENVIO_PENDIENTE);
			envio.setUsuarioSeycon(ctx.getCallerPrincipal().getName());
			// Comprobamos la cuenta
			Cuenta cuenta = null;

			if((mensaje.getCuentaEmisora() != null) && (!mensaje.getCuentaEmisora().equals("")))
			{
				cuenta = cd.obtenerCuenta(mensaje.getCuentaEmisora());
				if(cuenta == null)
				{
					throw new MobilidadException(new Error("No existe cuenta para enviar los mensajes, titulo:" + mensaje.getNombre()));
				}
			}
			else{
				if(mensaje.getEmails().size() != 0)
				{
					cuenta = cd.obtenerCuentaDefectoEmail();
					if(cuenta == null)
					{
						throw new MobilidadException(new Error("No existe cuenta por defecto para enviar emails, titulo:" + mensaje.getNombre()));
					}
				}
				if(mensaje.getSmss().size() != 0)
				{
					cuenta = cd.obtenerCuentaDefectoSMS();
					if(cuenta == null)
					{
						throw new MobilidadException(new Error("No existe cuenta por defecto para enviar SMS, titulo:" + envio.getNombre()));
					}
				}
			}
			envio.setCuenta(cuenta);
			envio.setNombre(mensaje.getNombre());
			envio.setIdProcedimiento(mensaje.getIdProcedimiento());
			Date now = new Date();
			envio.setFechaRegistro(new Timestamp(now.getTime()));
			if(mensaje.getFechaCaducidad() != null) {
				envio.setFechaCaducidad(new Timestamp(mensaje.getFechaCaducidad().getTime()));
			}else{
				int dias = 15;
				try {
					Properties config = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
					//si no esta definida la propiedad en mobtratel.properties por defecto le metemos 15 dias.
					if(config.getProperty("envio.limite.sin.fecha.caducidad") != null && !"".equals(config.getProperty("envio.limite.sin.fecha.caducidad"))){
						try{
							dias = Integer.parseInt(config.getProperty("envio.limite.sin.fecha.caducidad"));
						}catch(Exception e){
							dias = 15;
						}
					}
				}catch(Exception e){
					//si hay algún problema al coger la configuración por defecto le metemos 15 días. 
				}
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, dias);
				envio.setFechaCaducidad(new Timestamp(cal.getTime().getTime()));
			}
			
			if(mensaje.getFechaProgramacionEnvio() != null) envio.setFechaProgramacionEnvio(new Timestamp(mensaje.getFechaProgramacionEnvio().getTime()));
			envio.setInmediato(mensaje.isInmediato());

			for(Iterator it=mensaje.getEmails().iterator(); it.hasNext();)
			{
				MensajeEnvioEmail mee = (MensajeEnvioEmail)it.next();
				MensajeEmail me = new MensajeEmail();
				me.setHtml(mee.isHtml());
				Set emailSet = new LinkedHashSet(Arrays.asList(mee.getDestinatarios()));
				me.setDestinatarios(MobUtils.compoundDestinatarios(emailSet));
				me.setNumeroDestinatarios(emailSet.size());
				erroresFormato = MobUtils.validarEmails(emailSet);
				if((utils.getMaxDestinatariosEmail().intValue() != 0) && (emailSet.size() > utils.getMaxDestinatariosEmail().intValue()))
				{
					throw new LimiteDestinatariosException(new Error("Se ha superado el límite de destinatarios por mensaje Email."));
				}
				me.setEstado(ConstantesMobtratel.ESTADOENVIO_PENDIENTE);
				me.setMensaje(mee.getTexto().getBytes(ConstantesXML.ENCODING));
				me.setTitulo(mee.getTitulo());
				
				if (me.isVerificarEnvio() && mee.getDestinatarios().length > 0) {
					throw new LimiteDestinatariosException(new Error("Verificar envío solo disponible para un único destinatario."));
				}				
				me.setVerificarEnvio(mee.isVerificarEnvio());
				
				if (StringUtils.isNotBlank(mee.getEmailRespuesta())) {
					if(!ValidacionesUtil.validarEmail(mee.getEmailRespuesta())) {
						erroresFormato += " Email respuesta erroneo: " + mee.getEmailRespuesta();
					} else {
						me.setEmailRespuesta(mee.getEmailRespuesta());
					}
				}
				
				me.setRemitente(mee.getRemitente());
				
				envio.addEmail(me);
			}

			for(Iterator it=mensaje.getSmss().iterator(); it.hasNext();)
			{
				MensajeEnvioSms mes = (MensajeEnvioSms)it.next();
				MensajeSms ms = new MensajeSms();
				Set tlfSet = new LinkedHashSet(Arrays.asList(mes.getDestinatarios()));
				ms.setDestinatarios(MobUtils.compoundDestinatarios(tlfSet));
				ms.setNumeroDestinatarios(tlfSet.size());
				// No cancelamos envio si algun sms no valido para permitir tambien extensiones
				// erroresFormato += MobUtils.validarTelefonos(tlfSet);
				if((utils.getMaxDestinatariosSms().intValue() != 0) && (tlfSet.size() > utils.getMaxDestinatariosSms().intValue()))
				{
					throw new LimiteDestinatariosException(new Error("Se ha superado el límite de destinatarios por mensaje SMS."));
				}
				ms.setEstado(ConstantesMobtratel.ESTADOENVIO_PENDIENTE);
				if(mes.getTexto().length() > utils.getMaxCaracteres().intValue())
				{
					throw new MaxCaracteresSMSException(new Error("Se ha superado el límite de caracteres por mensaje SMS."));
				}
				ms.setMensaje(mes.getTexto().getBytes(ConstantesXML.ENCODING));
				
				if (ms.isVerificarEnvio() && mes.getDestinatarios().length > 0) {
					throw new LimiteDestinatariosException(new Error("Verificar envío solo disponible para un único destinatario."));
				}				
				ms.setVerificarEnvio(mes.isVerificarEnvio());
				
				envio.addSms(ms);
			}
			
			if(!erroresFormato.equals(""))
			{
				throw new FormatoException(new Error(erroresFormato));
			}

			// Comprobar permisos: si es usuario auto no se comprueban permisos
			
			if(!this.ctx.isCallerInRole(roleAuto) &&  !tienePermiso(envio))
			{
				throw new PermisoException(new Error("Usuario no tiene permiso para enviar email con la cuenta: " + cuenta.getNombre()));
			}

			codigo = ed.grabarEnvio(envio);
		} catch (MobilidadException e) {
			throw e;
		} catch (UnsupportedEncodingException e) {
			log.error("Error al recuperar los bytes del mensaje: " + mensaje.getNombre());
			e.printStackTrace();
			throw new MobilidadException(e.getCause());
		}
		return codigo.toString();
	}

	
	/**
	 * Comprueba el estado de un envio.
	 * 
	 * @throws DelegateException
	 * @return Devuelve el codigo del Envio. 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor},${role.auto}"
     */
	public EstadoMensajeEnvio estadoMensaje(String idMensaje) throws DelegateException
	{
		try {
			EnvioDelegate ed = DelegateUtil.getEnvioDelegate();
			Envio envio = ed.obtenerEnvio(Long.valueOf(idMensaje));
			EstadoMensajeEnvio em = new EstadoMensajeEnvio();
			em.setEstado(envio.getEstado());
			em.setFechaEnvio(envio.getFechaEnvio());
			em.setFechaCaducidad(envio.getFechaCaducidad());
			em.setFechaProgramacionEnvio(envio.getFechaProgramacionEnvio());
			em.setInmediato(envio.isInmediato());
					
			if (envio.getEmails() != null && envio.getEmails().size() > 0) {
				em.setEstadoEmails(new ArrayList());
				for (Iterator it = envio.getEmails().iterator(); it.hasNext();) {
					MensajeEmail mensEmail = (MensajeEmail) it.next();
					EstadoMensajeEnvioEmail estadoMensEmail = new EstadoMensajeEnvioEmail();
					estadoMensEmail.setEstado(mensEmail.getEstado());
					estadoMensEmail.setFechaInicioEnvio(mensEmail.getFechaInicioEnvio());
					estadoMensEmail.setFechaFinEnvio(mensEmail.getFechaFinEnvio());
					estadoMensEmail.setVerificacionEnvio(mensEmail.isVerificarEnvio());	
					if (StringUtils.isNotBlank(mensEmail.getEstadoVerificarEnvio())) {
						if ( ConstantesEmail.ESTADO_ENVIADO.equals(mensEmail.getEstadoVerificarEnvio())) {
							estadoMensEmail.setEstadoVerificacionEnvio(ConstantesMobtratel.CONFIRMACION_OK);
						} else if ( ConstantesEmail.ESTADO_NO_ENVIADO.equals(mensEmail.getEstadoVerificarEnvio())) {
							estadoMensEmail.setEstadoVerificacionEnvio(ConstantesMobtratel.CONFIRMACION_KO);
							estadoMensEmail.setErrorVerificacionEnvio(mensEmail.getErrorVerificarEnvio());
						} else {
							estadoMensEmail.setEstadoVerificacionEnvio(ConstantesMobtratel.CONFIRMACION_PENDIENTE);
						}
					}
					estadoMensEmail.setDestinatarios(new String(mensEmail.getDestinatarios(),ConstantesXML.ENCODING).split(Constantes.SEPARADOR_DESTINATARIOS));
					if (mensEmail.getDestinatariosEnviados() != null) {
						estadoMensEmail.setDestinatariosEnviados(new String(mensEmail.getDestinatariosEnviados(),ConstantesXML.ENCODING).split(Constantes.SEPARADOR_DESTINATARIOS));
					}	
					em.getEstadoEmails().add(estadoMensEmail);
				}
			}
			
			if (envio.getSmss() != null && envio.getSmss().size() > 0) {
				em.setEstadoSmss(new ArrayList());
				for (Iterator it = envio.getSmss().iterator(); it.hasNext();) {
					MensajeSms mensSms = (MensajeSms) it.next();
					EstadoMensajeEnvioSms estadoMensSms = new EstadoMensajeEnvioSms();
					estadoMensSms.setEstado(mensSms.getEstado());
					estadoMensSms.setFechaInicioEnvio(mensSms.getFechaInicioEnvio());
					estadoMensSms.setFechaFinEnvio(mensSms.getFechaFinEnvio());
					estadoMensSms.setVerificacionEnvio(mensSms.isVerificarEnvio());		
					if (StringUtils.isNotBlank(mensSms.getEstadoVerificarEnvio())) {
						if ( ConstantesSMS.ESTADO_ENVIADO.equals(mensSms.getEstadoVerificarEnvio())) {
							estadoMensSms.setEstadoVerificacionEnvio(ConstantesMobtratel.CONFIRMACION_OK);
						} else if ( ConstantesSMS.ESTADO_NO_ENVIADO.equals(mensSms.getEstadoVerificarEnvio())) {
							estadoMensSms.setEstadoVerificacionEnvio(ConstantesMobtratel.CONFIRMACION_KO);
							estadoMensSms.setErrorVerificacionEnvio(mensSms.getErrorVerificarEnvio());
						} else {
							estadoMensSms.setEstadoVerificacionEnvio(ConstantesMobtratel.CONFIRMACION_PENDIENTE);
						}
					}										
					estadoMensSms.setDestinatarios(new String(mensSms.getDestinatarios(),ConstantesXML.ENCODING).split(Constantes.SEPARADOR_DESTINATARIOS));
					if (mensSms.getDestinatariosEnviados() != null) {
						estadoMensSms.setDestinatariosEnviados(new String(mensSms.getDestinatariosEnviados(),ConstantesXML.ENCODING).split(Constantes.SEPARADOR_DESTINATARIOS));
					}			
					em.getEstadoSmss().add(estadoMensSms);
				}
			}
			
			return em;
		} catch (Exception ex) {
			log.error("Excepcion obteniendo estado mensaje: " + idMensaje + ". Error: " + ex.getMessage(), ex);
			throw new DelegateException(ex);
		}
		
	
	}
	
	/**
	 * Comprueba si el usuario que realiza el envío tiene permiso sobre la cuenta
	 * @param envio
	 * @return
	 * @throws DelegateException
	 */
	 private boolean tienePermiso(Envio envio) throws DelegateException
	    {		 
		 	boolean permisos = false;
	    	PermisoDelegate pd = DelegateUtil.getPermisoDelegate();

	    	String usuarioSeycon = envio.getUsuarioSeycon();
	    	if(usuarioSeycon != null)
	    	{
	    		Permiso permiso = pd.obtenerPermiso(usuarioSeycon,envio.getCuenta().getCodigo());
	    		if(permiso != null)
	    		{
	    			
	    			if((envio.getEmails().size()!= 0 && (permiso.getEmail() == Permiso.SI)) ||
	    	    	    (envio.getSmss().size()!= 0 && (permiso.getSms() == Permiso.SI)))
	    			{
	    				permisos = true;
	    			}
	    			else permisos = false;
	    			if(permisos && 
	    	    	   ((envio.getEmails().size()!= 0 && (permiso.getCuenta().getEmail() != null)) ||
	    	        	(envio.getSmss().size()!= 0 && (permiso.getCuenta().getSms() != null))))
	    			{
	    				permisos = true;
	    			}
	    			else permisos = false;
	    		}
	    	}
	    	if(!permisos)
	    		log.debug("Envio: " + envio.getCodigo() + " no tiene permiso. UsuarioSeycon: " + usuarioSeycon + 
	    				  " Cuenta: " + envio.getCuenta().getCodigo());
	    	return permisos;
	    }


	 
}

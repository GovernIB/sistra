package es.caib.regtel.persistence.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.regtel.model.ExcepcionRegistroOrganismo;
import es.caib.regtel.model.ResultadoRegistro;
import es.caib.regtel.model.ValorOrganismo;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.regtel.OficinaRegistro;
import es.caib.sistra.plugins.regtel.PluginRegistroIntf;
import es.caib.sistra.plugins.regtel.ServicioDestinatario;
import es.caib.sistra.plugins.regtel.TipoAsunto;
import es.caib.util.StringUtil;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.Justificante;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;

/**
 * SessionBean de registro organismo. Trasladará las operaciones al plugin de registro
 *
 * @ejb.bean
 *  name="regtel/persistence/RegistroOrganismoEJB"
 *  local-jndi-name = "es.caib.regtel.persistence.RegistroOrganismo"
 *  type="Stateless"
 *  view-type="local"
 */

public abstract class RegistroOrganismoEJB  implements SessionBean
{
	
	private static Log log = LogFactory.getLog(RegistroOrganismoEJB.class);
		
	/**
     * @ejb.create-method
     * @ejb.permission role-name = "${role.todos}"
     * @ejb.permission role-name = "${role.auto}"
     * @ejb.permission role-name = "${role.gestor}"
     * @ejb.permission role-name = "${role.registro}"
     */
	public void ejbCreate() throws CreateException 
	{		
	}
	
	public void setSessionContext(javax.ejb.SessionContext ctx) 
	{
	}
   
	/**
	 * Realiza apunte registral de entrada
	 * 
     * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}" 
	 * @ejb.permission role-name = "${role.auto}"  	 
    */
	public ResultadoRegistro registroEntrada(AsientoRegistral asiento,ReferenciaRDS refAsiento,Map refAnexos) throws ExcepcionRegistroOrganismo{
		try{
			log.debug( "Obtenemos plugin registro organismo" );
			PluginRegistroIntf plgRegistro = PluginFactory.getInstance().getPluginRegistro();
			log.debug( "Comenzamos registro contra registro organismo" );
			es.caib.sistra.plugins.regtel.ResultadoRegistro datosRegistro;
			datosRegistro = plgRegistro.registroEntrada(asiento,refAsiento,refAnexos);
			ResultadoRegistro res = new ResultadoRegistro();
			res.setFechaRegistro(StringUtil.fechaACadena(datosRegistro.getFechaRegistro(),StringUtil.FORMATO_REGISTRO));
			res.setNumeroRegistro(datosRegistro.getNumeroRegistro());
			PadDelegate pad = DelegatePADUtil.getPadDelegate();
			pad.logRegistro(ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA,datosRegistro.getNumeroRegistro(),datosRegistro.getFechaRegistro(),null);
			log.debug( "Registro realizado contra registro organismo" );
			return res;
		}catch(Exception ex){
			throw new ExcepcionRegistroOrganismo("Exception realizando registro entrada contra plugin registro",ex);
		}
	}
	
	/**
	 * Realiza apunte registral de registro de salida
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.gestor}"   
	 * @ejb.permission role-name = "${role.auto}"
	 */	
	public ResultadoRegistro registroSalida(AsientoRegistral asiento,ReferenciaRDS refAsiento,Map refAnexos) throws ExcepcionRegistroOrganismo{
		try{
			log.debug( "Obtenemos plugin registro organismo" );
			PluginRegistroIntf plgRegistro = PluginFactory.getInstance().getPluginRegistro();
			log.debug( "Comenzamos registro contra registro organismo" );
			es.caib.sistra.plugins.regtel.ResultadoRegistro datosRegistro;
			datosRegistro = plgRegistro.registroSalida(asiento,refAsiento,refAnexos);
			ResultadoRegistro res = new ResultadoRegistro();
			res.setFechaRegistro(StringUtil.fechaACadena(datosRegistro.getFechaRegistro(),StringUtil.FORMATO_REGISTRO));
			res.setNumeroRegistro(datosRegistro.getNumeroRegistro());
			PadDelegate pad = DelegatePADUtil.getPadDelegate();
			pad.logRegistro(ConstantesAsientoXML.TIPO_REGISTRO_SALIDA,datosRegistro.getNumeroRegistro(),datosRegistro.getFechaRegistro(),null);
			log.debug( "Registro realizado contra registro organismo" );
			return res;
		}catch(Exception ex){
			throw new ExcepcionRegistroOrganismo("Exception realizando registro salida contra plugin registro",ex);
		}
	}
	
	/**
	 * Confirma un preregistro. Debe realizar un apunte registral indicando que se ha confirmado el preregistro.
	 *
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.registro}"
	 */
	public ResultadoRegistro confirmarPreregistro(String oficina,String codigoProvincia,String codigoMunicipio,String descripcionMunicipio,Justificante justificantePreregistro,ReferenciaRDS refJustificante,ReferenciaRDS refAsiento,Map refAnexos) throws ExcepcionRegistroOrganismo{
		try{
			log.debug( "Obtenemos plugin registro organismo" );
			PluginRegistroIntf plgRegistro = PluginFactory.getInstance().getPluginRegistro();
			log.debug( "Comenzamos confirmacion preregistro contra registro organismo" );
			es.caib.sistra.plugins.regtel.ResultadoRegistro datosRegistro;
			datosRegistro = plgRegistro.confirmarPreregistro(oficina,codigoProvincia,codigoMunicipio,descripcionMunicipio,justificantePreregistro,refJustificante,refAsiento,refAnexos);
			ResultadoRegistro res = new ResultadoRegistro();
			res.setFechaRegistro(StringUtil.fechaACadena(datosRegistro.getFechaRegistro(),StringUtil.FORMATO_REGISTRO));
			res.setNumeroRegistro(datosRegistro.getNumeroRegistro());
			PadDelegate pad = DelegatePADUtil.getPadDelegate();
			pad.logRegistro(ConstantesAsientoXML.TIPO_PREREGISTRO,datosRegistro.getNumeroRegistro(),datosRegistro.getFechaRegistro(),null);
			log.debug( "Confirmacion registro realizado contra registro organismo" );
			return res;
		}catch(Exception ex){
			throw new ExcepcionRegistroOrganismo("Exception realizando confirmacion registro contra plugin registro",ex);
		}
	}
	
	/**
	 * Obtiene lista de oficinas registrales
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"
	 * @ejb.permission role-name = "${role.auto}"
	 * 
	 */
	public List obtenerOficinasRegistro() throws ExcepcionRegistroOrganismo{
		try{
			log.debug("Obtenemos plugin de registro");
			PluginRegistroIntf	plgRegistro = PluginFactory.getInstance().getPluginRegistro();
			log.debug("Invocamos el plugin con la funcion de obtener oficinas registro ");
			List resReg = plgRegistro.obtenerOficinasRegistro();

			List result = new ArrayList();
 			if (resReg != null){
				for (Iterator it = resReg.iterator();it.hasNext();){
					OficinaRegistro s = (OficinaRegistro) it.next();
					ValorOrganismo val = new ValorOrganismo();
					val.setCodigo(s.getCodigo());
					val.setDescripcion(s.getDescripcion());
					result.add(val);
				}
 			}
			return result;
		}catch (Exception ex){
			throw new ExcepcionRegistroOrganismo("Excepcion obteniendo oficinas registro del plugin de registro",ex);
		}  	
	}	
	
	/**
	 * Obtiene lista de oficinas registrales para los que el usuario de registro tiene permiso
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"
	 * @ejb.permission role-name = "${role.auto}"
	 * 
	 */
	public List obtenerOficinasRegistroUsuario(String usuario) throws ExcepcionRegistroOrganismo{
		try{
			log.debug("Obtenemos plugin de registro");
			PluginRegistroIntf	plgRegistro = PluginFactory.getInstance().getPluginRegistro();
			log.debug("Invocamos el plugin con la funcion de obtener oficinas registro usuario");
			
			List resReg = plgRegistro.obtenerOficinasRegistroUsuario(usuario);			
			
 			List result = new ArrayList();
 			if (resReg != null){
				for (Iterator it = resReg.iterator();it.hasNext();){
					OficinaRegistro s = (OficinaRegistro) it.next();
					ValorOrganismo val = new ValorOrganismo();
					val.setCodigo(s.getCodigo());
					val.setDescripcion(s.getDescripcion());
					result.add(val);
				}
 			}
			return result;
			
		}catch (Exception ex){
			throw new ExcepcionRegistroOrganismo("Excepcion obteniendo oficinas registro usuario del plugin de registro",ex);
		}  	
	}
	
	/**
	 * Obtiene tipos de asunto
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"
	 * @ejb.permission role-name = "${role.auto}"	 
	 */
	public List obtenerTiposAsunto() throws ExcepcionRegistroOrganismo{
		try{
			log.debug("Obtenemos plugin de registro");
			PluginRegistroIntf	plgRegistro = PluginFactory.getInstance().getPluginRegistro();
			log.debug("Invocamos el plugin con la funcion de obtener tipos de asunto");
			List resReg = plgRegistro.obtenerTiposAsunto();			
			
 			List result = new ArrayList();
 			if (resReg != null){
				for (Iterator it = resReg.iterator();it.hasNext();){
					TipoAsunto s = (TipoAsunto) it.next();
					ValorOrganismo val = new ValorOrganismo();
					val.setCodigo(s.getCodigo());
					val.setDescripcion(s.getDescripcion());
					result.add(val);
				}
 			}			
			return result;
		}catch (Exception ex){
			throw new ExcepcionRegistroOrganismo("Excepcion obteniendo tipos de asunto del plugin de registro",ex);
		}  	
	}
	
	/**
	 * Obtiene lista de servicios destinatarios
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"
	 * @ejb.permission role-name = "${role.auto}"
	 */
	public List obtenerServiciosDestino() throws ExcepcionRegistroOrganismo{
		try{
			log.debug("Obtenemos plugin de registro");
			PluginRegistroIntf	plgRegistro = PluginFactory.getInstance().getPluginRegistro();
			log.debug("Invocamos el plugin con la funcion de obtener servicios destino");
			List resReg = plgRegistro.obtenerServiciosDestino();
			
			List result = new ArrayList();
			if (resReg != null){
				for (Iterator it = resReg.iterator();it.hasNext();){
					ServicioDestinatario s = (ServicioDestinatario) it.next();
					ValorOrganismo val = new ValorOrganismo();
					val.setCodigo(s.getCodigo());
					val.setDescripcion(s.getDescripcion());
					result.add(val);
				}
			}
			return result;
			
		}catch (Exception ex){
			throw new ExcepcionRegistroOrganismo("Excepcion obteniendo servicios destino del plugin de registro",ex);
		}  			
	}
   
	
	/**
	 * Mira si existe la oficina registral
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"
	 * 
	 */
	public boolean existeOficinaRegistro(String oficinaRegistro) throws ExcepcionRegistroOrganismo 
	{ 	
		try
		{	
			log.debug("Obtenemos plugin de registro");
			PluginRegistroIntf	plgRegistro = PluginFactory.getInstance().getPluginRegistro();
			log.debug("Invocamos el plugin con la funcion de obtener oficinas registro usuario");
			
			List resReg = plgRegistro.obtenerOficinasRegistro();			
 			if (resReg != null){
				for (Iterator it = resReg.iterator();it.hasNext();){
					OficinaRegistro s = (OficinaRegistro) it.next();
					if(s.getCodigo().equals(oficinaRegistro)){
						return true;
					}
				}
 			}
 			return false;
		} catch (Exception e) {
	        throw new ExcepcionRegistroOrganismo("Excepcion obteniendo oficina de registro del plugin de registro",e);
	    }	 	 
	 } 
	
	/**
	 * Mira si existe el tipo de asunto
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"
	 * 
	 */
	public boolean existeTipoAsunto(String tipoAsunto) throws ExcepcionRegistroOrganismo 
	{
		try
		{		
			log.debug("Obtenemos plugin de registro");
			PluginRegistroIntf	plgRegistro = PluginFactory.getInstance().getPluginRegistro();
			log.debug("Invocamos el plugin con la funcion de obtener tipos de asunto");
			List resReg = plgRegistro.obtenerTiposAsunto();			
 			if (resReg != null){
				for (Iterator it = resReg.iterator();it.hasNext();){
					TipoAsunto s = (TipoAsunto) it.next();
					if(s.getCodigo().equals(tipoAsunto)){
						return true;
					}
				}
 			}	
 			return false;
		} catch (Exception e) {
	        throw new ExcepcionRegistroOrganismo("Excepcion obteniendo tipo de asunto del plugin de registro",e);
			}			
	 }
	
	/**
	 * Mira si existe el servicio destino
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"
	 * 
	 */
	public boolean existeServicioDestino(String servicioDestino) throws ExcepcionRegistroOrganismo
	{
		try
		{	
			log.debug("Obtenemos plugin de registro");
			PluginRegistroIntf	plgRegistro = PluginFactory.getInstance().getPluginRegistro();
			log.debug("Invocamos el plugin con la funcion de obtener servicios destino");
			List resReg = plgRegistro.obtenerServiciosDestino();
			if (resReg != null){
				for (Iterator it = resReg.iterator();it.hasNext();){
					ServicioDestinatario s = (ServicioDestinatario) it.next();
					if(s.getCodigo().equals(servicioDestino)){
						return true;
					}
				}
			}		
			return false;
		} catch (Exception e) {
	        throw new ExcepcionRegistroOrganismo("Excepcion obteniendo servicio de destino del plugin de registro",e);
	    }	 	 
	 }
	
		
	/**
	 * Anula un registro de entrada
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"
	 *  @ejb.permission role-name = "${role.auto}"
	 */
   public void anularRegistroEntrada(String numeroRegistro, Date fechaRegistro) throws ExcepcionRegistroOrganismo{
	   try{
			log.debug("Obtenemos plugin de registro");
			PluginRegistroIntf	plgRegistro = PluginFactory.getInstance().getPluginRegistro();			
			log.debug("Invocamos el plugin con la funcion de anular registro entrada");
			plgRegistro.anularRegistroEntrada(numeroRegistro, fechaRegistro);			
		}catch (Exception ex){
			throw new ExcepcionRegistroOrganismo("Excepcion accediendo al plugin de registro",ex);
		}  
   }
   
   /**
	 * Anula un registro de salida
	 * @ejb.interface-method
	 *  @ejb.permission role-name = "${role.auto}"
	 */
  public void anularRegistroSalida(String numeroRegistro, Date fechaRegistro) throws ExcepcionRegistroOrganismo{
	   try{
			log.debug("Obtenemos plugin de registro");
			PluginRegistroIntf	plgRegistro = PluginFactory.getInstance().getPluginRegistro();			
			log.debug("Invocamos el plugin con la funcion de anular registro salida");
			plgRegistro.anularRegistroSalida(numeroRegistro, fechaRegistro);			
		}catch (Exception ex){
			throw new ExcepcionRegistroOrganismo("Excepcion accediendo al plugin de registro",ex);
		}  
  }
  
  /**
	 * Obtiene descripcion de oficina para sello
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"
	 *  @ejb.permission role-name = "${role.auto}"
	 */
  public String obtenerDescripcionSelloOficina(String oficina) throws ExcepcionRegistroOrganismo{
	  try{
			log.debug("Obtenemos plugin de registro");
			PluginRegistroIntf	plgRegistro = PluginFactory.getInstance().getPluginRegistro();			
			log.debug("Invocamos el plugin con la funcion de anular registro salida");
			return plgRegistro.obtenerDescripcionSelloOficina(oficina);			
		}catch (Exception ex){
			throw new ExcepcionRegistroOrganismo("Excepcion accediendo al plugin de registro",ex);
		}  
  }
  
}

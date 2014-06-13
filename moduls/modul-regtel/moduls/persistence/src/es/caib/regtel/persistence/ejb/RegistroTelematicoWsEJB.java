package es.caib.regtel.persistence.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.regtel.model.ExcepcionRegistroTelematico;
import es.caib.regtel.model.ReferenciaRDSAsientoRegistral;
import es.caib.regtel.model.ResultadoRegistroTelematico;
import es.caib.regtel.model.ws.AcuseRecibo;
import es.caib.regtel.model.ws.DatosAsunto;
import es.caib.regtel.model.ws.DatosExpediente;
import es.caib.regtel.model.ws.DatosInteresado;
import es.caib.regtel.model.ws.DatosNotificacion;
import es.caib.regtel.model.ws.DatosRegistroEntrada;
import es.caib.regtel.model.ws.DatosRegistroSalida;
import es.caib.regtel.model.ws.DatosRepresentado;
import es.caib.regtel.model.ws.OficinaRegistral;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.regtel.persistence.delegate.DelegateUtil;
import es.caib.regtel.persistence.delegate.RegistroTelematicoDelegate;
import es.caib.regtel.persistence.util.Constantes;
import es.caib.regtel.persistence.util.RegistroEntradaHelper;
import es.caib.regtel.persistence.util.RegistroSalidaHelper;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.zonaper.modelInterfaz.DetalleAcuseRecibo;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.PadBackOfficeDelegate;
import es.caib.zonaper.persistence.delegate.PadDelegate;



/**
 * 
 * Puente entre interfaz ws e interfaz registro telematico para poder establecer permisos
 * específicos en el interfaz de ws 
 *
 * @ejb.bean
 *  name="regtel/persistence/RegistroTelematicoWsEJB"
 *  jndi-name="es.caib.regtel.persistence.RegistroTelematicoWs"
 *  type="Stateless"
 *  view-type="remote"
 *  
 */

public abstract class RegistroTelematicoWsEJB  implements SessionBean
{
	
	private static Log log = LogFactory.getLog(RegistroTelematicoEJB.class);
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name = "${role.gestor}"
     * @ejb.permission role-name = "${role.auto}"
     */
	public void ejbCreate() throws CreateException{}

   
	/**
	 * @param dEnt datos Registro Entrada 
	 * @return ResultadoRegistroTelematico
	 * 
	 * @ejb.interface-method 
	 * @ejb.permission role-name = "${role.gestor}"
     * @ejb.permission role-name = "${role.auto}"
     */
	public ResultadoRegistroTelematico registroEntrada(DatosRegistroEntrada dEnt)  throws Exception{
		
		/* ------------------------------------------------------------------------------------- 
		 * Anterior a version 1.1.0 era obligatorio introducir el usuario seycon y el nif.
		 * A partir de la version 1.1.0 no es obligatorio introducir el usuario seycon. Se introduce
		 * el parametro autenticado (boolean) y el nif.
		 * 
		 * Por motivos de compatibilidad introduce el usuario seycon a partir del nif en caso de que
		 * sea autenticado y no se informe
		 * 
		 * ------------------------------------------------------------------------------------- */		 
		 establecerUsuarioSeycon(dEnt);
		 /* ------------------------------------------------------------------------------------- */
		
		
		RegistroEntradaHelper r = new RegistroEntradaHelper();
		inicializarRegistroEntrada(r, dEnt);
		ReferenciaRDSAsientoRegistral ar = r.generarAsientoRegistral("LOCAL");
		ResultadoRegistroTelematico res = r.registrar("LOCAL",ar);
		return res;
	}
	
	
	/**
	 * @param dEnt datos Registro Entrada 
	 * @return ReferenciaRDSAsientoRegistral
	 * 
	 * @ejb.interface-method 
	 * @ejb.permission role-name = "${role.gestor}"
     * @ejb.permission role-name = "${role.auto}"
     */
	
	public ReferenciaRDSAsientoRegistral prepararRegistroEntrada(DatosRegistroEntrada dEnt, int diasPersistencia) throws Exception {
		
		/* ------------------------------------------------------------------------------------- 
		  Anterior a version 1.1.0 era obligatorio introducir el usuario seycon y el nif.
		 * A partir de la version 1.1.0 no es obligatorio introducir el usuario seycon. Se introduce
		 * el parametro autenticado (boolean) y el nif.
		 * 
		 * Por motivos de compatibilidad introduce el usuario seycon a partir del nif en caso de que
		 * sea autenticado y no se informe
		 * ------------------------------------------------------------------------------------- */		 
		 establecerUsuarioSeycon(dEnt);
		 /* ------------------------------------------------------------------------------------- */
		
		RegistroEntradaHelper r = new RegistroEntradaHelper();
		inicializarRegistroEntrada(r, dEnt);
		ReferenciaRDSAsientoRegistral raWS = r.generarAsientoRegistral("LOCAL");
		
		// Si se indican dias de persistencia se crearan para el asiento y los anexos un uso de persistencia
		// y se realizara el log de este registro preparado en la zona personal para que una vez pasen los dias
		// de persistencia se eliminen estos usos
		if (diasPersistencia > 0) {
			// Generamos un id de persistencia unico
			String idPersistencia = generarIdPersistenciaAsiento(raWS);
			
			// Crear uso de persistencia para asiento y anexo
			generarUsosRDSPrepararRegistro(idPersistencia, raWS);
			
			// Realizar log en la zona personal
			PadDelegate pad = DelegatePADUtil.getPadDelegate();
			pad.logRegistroExternoPreparado(raWS.getAsientoRegistral(), raWS.getAnexos(), idPersistencia, diasPersistencia);
		}
		
		return raWS;
	}

	/**
	 * @param referenciaRDS Referencia RDS del asiento mas referencias RDS de los documentos anexos
	 * @param firma firma del asiento
	 * @return ResultadoRegistroTelematico
	 *  
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.gestor}"
     * @ejb.permission role-name = "${role.auto}"
     */
	public ResultadoRegistroTelematico registroEntradaConFirma(ReferenciaRDSAsientoRegistral referenciaRDS, FirmaIntf firma) throws Exception {
		RegistroEntradaHelper r = new RegistroEntradaHelper();
		RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
		rdsDelegate.asociarFirmaDocumento(referenciaRDS.getAsientoRegistral(), firma);
		ResultadoRegistroTelematico res = r.registrar("LOCAL",referenciaRDS);
		
		// Tras registrar comprobamos si el registro tenia usos de persistencia y si es asi los borramos
		 List lu = rdsDelegate.listarUsos(referenciaRDS.getAsientoRegistral());
		 if (lu != null && lu.size() > 1) {
			 String idPersistencia = generarIdPersistenciaAsiento(referenciaRDS);
			 rdsDelegate.eliminarUsos(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE, idPersistencia);
		 }
		
		return res;
	}
   
	/**
	 * @param notificacion datos del resgistro de salida
	 * @return ResultadoRegistroTelematico
	 * 
	 * @ejb.interface-method 
	 * @ejb.permission role-name = "${role.gestor}"
     * @ejb.permission role-name = "${role.auto}"
     */
	public ResultadoRegistroTelematico registroSalida(DatosRegistroSalida notificacion) throws Exception {
		
		/* ------------------------------------------------------------------------------------- 
		  Anterior a version 1.1.0 era obligatorio introducir el usuario seycon y el nif.
		 * A partir de la version 1.1.0 no es obligatorio introducir el usuario seycon. Se introduce
		 * el parametro autenticado (boolean) y el nif.
		 * 
		 * Por motivos de compatibilidad introduce el usuario seycon a partir del nif en caso de que
		 * sea autenticado y no se informe
		 * ------------------------------------------------------------------------------------- */		 
		 establecerUsuarioSeycon(notificacion);
		 /* ------------------------------------------------------------------------------------- */		
		
		
		RegistroSalidaHelper r = new RegistroSalidaHelper();
		if(notificacion != null){
			if(notificacion.getDatosExpediente() != null){
				DatosExpediente de = notificacion.getDatosExpediente();
				r.setExpediente(de.getUnidadAdministrativa(), de.getIdentificadorExpediente(), de.getClaveExpediente());
			}
			if(notificacion.getDatosInteresado() != null){
				DatosInteresado di = notificacion.getDatosInteresado();
				r.setDatosInteresado(di.getNif(),di.getNombreApellidos(), di.getIdentificadorUsuario(), di.getCodigoPais(),di.getNombrePais(), di.getCodigoProvincia(), di.getNombreProvincia(), di.getCodigoLocalidad(), di.getNombreLocalidad());
			}
			if(notificacion.getDatosRepresentado() != null){
				DatosRepresentado dr = notificacion.getDatosRepresentado();
				r.setDatosRepresentado(dr.getNif(), dr.getNombreApellidos());
			}
			if(notificacion.getOficinaRegistral() != null){
				OficinaRegistral of = notificacion.getOficinaRegistral();
				r.setOficinaRegistro(of.getCodigoOrgano(),of.getCodigoOficina());
			}
			if(notificacion.getDatosNotificacion() != null){
				DatosNotificacion dn = notificacion.getDatosNotificacion();
				if(dn.getAviso() != null && dn.getOficioRemision() != null){
					r.setDatosNotificacion(dn.getIdioma(), dn.getTipoAsunto(), dn.getAviso().getTitulo(), 
							dn.getAviso().getTexto(), dn.getAviso().getTextoSMS(), dn.getOficioRemision().getTitulo(),
							dn.getOficioRemision().getTexto(), dn.isAcuseRecibo(),
							dn.getAccesiblePorClave(), dn.getPlazo());
				}
				if (dn.getOficioRemision().getTramiteSubsanacion() != null){
					r.setTramiteSubsanacion(dn.getOficioRemision().getTramiteSubsanacion().getDescripcionTramite(),dn.getOficioRemision().getTramiteSubsanacion().getIdentificadorTramite(),dn.getOficioRemision().getTramiteSubsanacion().getVersionTramite().intValue(),dn.getOficioRemision().getTramiteSubsanacion().getParametrosTramite());
				}
			}
			if(notificacion.getDocumentos() != null){
				ArrayList docs = notificacion.getDocumentos();
				for(int i=0;i<docs.size();i++){
					if (docs.get(i) instanceof DocumentoRDS){
						DocumentoRDS doc = (DocumentoRDS)docs.get(i);
						r.addDocumento(doc.getModelo(),doc.getVersion(),doc.getDatosFichero(),doc.getNombreFichero(),doc.getExtensionFichero(),doc.getPlantilla(),doc.getFirmas());
					}else if (docs.get(i) instanceof ReferenciaRDS){
						r.addDocumento((ReferenciaRDS)docs.get(i));
					}
				}
			}
		}
		ReferenciaRDSAsientoRegistral ar = r.generarAsientoRegistral("LOCAL");
		ResultadoRegistroTelematico res = r.registrar("LOCAL",ar);
		return res;
	}
	
	/**
	 * @param numeroRegistro numero de registro para obtener el acuse de recibo
	 * @return AcuseRecibo
	 * 
	 * @ejb.interface-method 
	 * @ejb.permission role-name = "${role.gestor}"
	 * @ejb.permission role-name = "${role.auto}"
     */
	public AcuseRecibo obtenerAcuseRecibo(String numeroRegistro) throws Exception {
		AcuseRecibo ar = null;
		RegistroTelematicoDelegate rtd = DelegateRegtelUtil.getRegistroTelematicoDelegate();
		Date date = rtd.obtenerAcuseRecibo(numeroRegistro);
		ar = new AcuseRecibo();
		ar.setFechaAcuseRecibo(date);
		return ar;
	}
	
	/**
	 * @param numeroRegistro numero de registro para obtener el acuse de recibo
	 * @return AcuseRecibo
	 * 
	 * @ejb.interface-method 
	 * @ejb.permission role-name = "${role.gestor}"
	 * @ejb.permission role-name = "${role.auto}"
     */
	public DetalleAcuseRecibo obtenerDetalleAcuseRecibo(String numeroRegistro)  throws Exception {
		try {
			PadBackOfficeDelegate 	ejb = new PadBackOfficeDelegate();
			return ejb.obtenerDetalleAcuseRecibo(numeroRegistro);		
		} catch (Exception ex) {
			throw new ExcepcionRegistroTelematico("Excepcion obteniendo detalle acuse recibo: " + numeroRegistro + " - error: " + ex.getMessage(), ex);
		}
	}
	
	
	private void inicializarRegistroEntrada(RegistroEntradaHelper r, DatosRegistroEntrada dEnt) throws Exception{
		if(dEnt != null){
			if(dEnt.getDatosInteresado() != null){
				DatosInteresado di = dEnt.getDatosInteresado();
				r.setDatosInteresado(di.getNif(),di.getNombreApellidos(), di.getIdentificadorUsuario(), di.getCodigoPais(),di.getNombrePais(), di.getCodigoProvincia(), di.getNombreProvincia(), di.getCodigoLocalidad(), di.getNombreLocalidad());
			}
			if(dEnt.getDatosRepresentado() != null){
				DatosRepresentado dr = dEnt.getDatosRepresentado();
				r.setDatosRepresentado(dr.getNif(), dr.getNombreApellidos());
			}
			if(dEnt.getOficinaRegistral() != null){
				OficinaRegistral of = dEnt.getOficinaRegistral();
				r.setOficinaRegistro(of.getCodigoOrgano(),of.getCodigoOficina());
			}
			if(dEnt.getDatosAsunto() != null){
				DatosAsunto da = dEnt.getDatosAsunto();
				r.setDatosAsunto(da.getCodigoUnidadAdministrativa()+"", da.getIdioma(), da.getTipoAsunto(), da.getAsunto());
			}
			if(dEnt.getDocumentos() != null){
				ArrayList docs = dEnt.getDocumentos();
				for(int i=0;i<docs.size();i++){
					if (docs.get(i) instanceof DocumentoRDS){
						DocumentoRDS doc = (DocumentoRDS)docs.get(i);
						r.addDocumento(doc.getModelo(),doc.getVersion(),doc.getDatosFichero(),doc.getNombreFichero(),doc.getExtensionFichero(),doc.getPlantilla(),doc.getFirmas());
					}else if (docs.get(i) instanceof ReferenciaRDS){
						r.addDocumento((ReferenciaRDS)docs.get(i));
					}
				}
			}
		}
	}
	
	
	/**
	 * 
	 * 
	 * Anterior a version 1.1.0 era obligatorio introducir el usuario seycon y el nif.
	 * A partir de la version 1.1.0 no es obligatorio introducir el usuario seycon. Se introduce
	 * el parametro autenticado (boolean) y el nif.
	 * 
	 * Por motivos de compatibilidad introduce el usuario seycon a partir del nif en caso de que
	 * sea autenticado y no se informe
	 *
	 * @param dEnt
	 * @throws Exception 
	 */
	private void establecerUsuarioSeycon(DatosRegistroEntrada dEnt) throws Exception{
		// Comprobamos version usada dependiendo de si tiene establecido parametro autenticado
		if (dEnt.getDatosInteresado().getAutenticado() == null){
			// Version anterior a 1.1.0: se rellena identificador usuario y nif. Establecemos parametro autenticado.
			dEnt.getDatosInteresado().setAutenticado(new Boolean(StringUtils.isBlank(dEnt.getDatosInteresado().getIdentificadorUsuario())));		
		}else{
			// Version posterior a 1.1.0: se rellena autenticado y nif. Establecemos usuario seycon.
			if (dEnt.getDatosInteresado().getAutenticado().booleanValue()){
				PersonaPAD p = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(dEnt.getDatosInteresado().getNif());
				if (p == null){
					throw new Exception("No existe un usuario con el nif " + dEnt.getDatosInteresado().getNif());
				}
				dEnt.getDatosInteresado().setIdentificadorUsuario(p.getUsuarioSeycon());
			}
		}
	}
	
	/**
	 * 
	 * Anterior a version 1.1.0 era obligatorio introducir el usuario seycon y el nif.
	 * A partir de la version 1.1.0 no es obligatorio introducir el usuario seycon. Se introduce
	 * el parametro autenticado (boolean) y el nif.
	 * 
	 * Por motivos de compatibilidad introduce el usuario seycon a partir del nif en caso de que
	 * sea autenticado y no se informe
	 * 
	 * @param dSal
	 * @throws Exception 
	 */
	private void establecerUsuarioSeycon(DatosRegistroSalida dSal) throws Exception{
		// Comprobamos version usada dependiendo de si tiene establecido parametro autenticado
		if (dSal.getDatosInteresado().getAutenticado() == null){
			// Version anterior a 1.1.0: se rellena identificador usuario y nif. Establecemos parametro autenticado.
			dSal.getDatosInteresado().setAutenticado(new Boolean(StringUtils.isBlank(dSal.getDatosInteresado().getIdentificadorUsuario())));		
		}else{
			// Version posterior a 1.1.0: se rellena autenticado y nif. Establecemos usuario seycon.
			if (dSal.getDatosInteresado().getAutenticado().booleanValue()){
				PersonaPAD p = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(dSal.getDatosInteresado().getNif());
				if (p == null){
					throw new Exception("No existe un usuario con el nif " + dSal.getDatosInteresado().getNif());
				}
				dSal.getDatosInteresado().setIdentificadorUsuario(p.getUsuarioSeycon());
			}
		}
	}
	
	  /**
	 * Genera para un registro preparado para firmar los usos RDS de tipo tramite persistente para el asiento y documentos asociados.
	 */
	private String generarUsosRDSPrepararRegistro(String idPersistencia, ReferenciaRDSAsientoRegistral ra ) throws Exception {
		
		try{
			RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
			UsoRDS uso;
			
			// Creamos uso para asiento					
			uso = new UsoRDS();
			uso.setReferenciaRDS(ra.getAsientoRegistral());
			uso.setReferencia(idPersistencia);
			uso.setTipoUso(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE);
			rds.crearUso(uso);
			
			// Creamos usos para documentos asiento
			Iterator it = ra.getAnexos().keySet().iterator();
	    	while (it.hasNext()){
	    		ReferenciaRDS refAnexo = (ReferenciaRDS) ra.getAnexos().get((String) it.next());
	    		
	    		// Verificamos que no se este usando el doc en otra parte
	    		List usos = rds.listarUsos(refAnexo);
	    		if (usos.size() > 0) {
	    			throw new Exception("Existen usos asociados para el documento " + refAnexo.getCodigo());
	    		}
	    		
	    		// Creamos el uso
	    		uso = new UsoRDS();
				uso.setReferenciaRDS(refAnexo);
				uso.setReferencia(idPersistencia);
				uso.setTipoUso(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE);
				rds.crearUso(uso);	    		
	    	}    	
	    	
	    	return idPersistencia;
	    	
		}catch(Exception ex){
			log.error("Excepcion generando usos RDS para registro preparado",ex);
			throw new Exception("Excepcion generando usos RDS para registro preparado: " + ex.getMessage());
		}
		
	}


	private String generarIdPersistenciaAsiento(ReferenciaRDSAsientoRegistral ra) {
		String idPersistencia = Constantes.PREFIJO_USO_PREPARARREGISTRO + ra.getAsientoRegistral().getCodigo();
		return idPersistencia;
	}
}

package es.caib.bantel.persistence.ejb;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.apache.commons.lang.StringUtils;

import es.caib.bantel.model.DocumentoBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.model.TramiteBandeja;
import es.caib.bantel.modelInterfaz.ConstantesBTE;
import es.caib.bantel.modelInterfaz.DatosDocumentoPresencial;
import es.caib.bantel.modelInterfaz.DatosDocumentoTelematico;
import es.caib.bantel.modelInterfaz.DocumentoBTE;
import es.caib.bantel.modelInterfaz.ExcepcionBTE;
import es.caib.bantel.modelInterfaz.TramiteBTE;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;
import es.caib.bantel.persistence.delegate.ProcedimientoDelegate;
import es.caib.bantel.persistence.util.BteStringUtil;
import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.xml.datospropios.factoria.ConstantesDatosPropiosXML;
import es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios;
import es.caib.xml.datospropios.factoria.ServicioDatosPropiosXML;
import es.caib.xml.datospropios.factoria.impl.DatosPropios;
import es.caib.xml.datospropios.factoria.impl.Documento;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosAnexoDocumentacion;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.xml.registro.factoria.impl.Justificante;

//TODO: Referenciar localmente a los ejbs
/**
 * SessionBean que implementa la interfaz de la BTE con los módulos de la plataforma de tramitación
 *
 * @ejb.bean
 *  name="bantel/persistence/BteSistraFacade"
 *  jndi-name="es.caib.bantel.persistence.BteSistraFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * @ejb.env-entry name="colaAvisos" value="queue/AvisadorBTE" 
 * 
 */
public abstract class BteSistraFacadeEJB implements SessionBean  {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.todos}"
     */
	public void ejbCreate() throws CreateException {		
	}
	
	// --------------------------------------------------------------------
    // INTERFAZ PARA INTEGRACIÓN CON PLATAFORMA TRAMITACIÓN
	// --------------------------------------------------------------------
    /**
     * Desde el registro se crea entrada para preregistros/preenvios confirmados
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.registro}"
     */
    public String crearEntradaPreregistro(ReferenciaRDS refAsiento,ReferenciaRDS refJustificante,Map refDocumentos,String numregistro,Date fechaRegistro)  throws ExcepcionBTE{
    	return crearEntrada(refAsiento,refJustificante,refDocumentos,numregistro,fechaRegistro,ConstantesBTE.CONFIRMACIONPREREGISTRO_REGISTRO);
    }
      
    /**
     * Crea entrada para registros/envios telemáticos
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String crearEntradaTelematica(ReferenciaRDS refAsiento,ReferenciaRDS refJustificante,Map refDocumentos)  throws ExcepcionBTE{
    	return crearEntrada(refAsiento,refJustificante,refDocumentos,null,null,null);
    }
    
    /**
     * El gestor confirma preregistros/preenvios que no se han registrado presencialmente 
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     */
    public String crearEntradaPreregistroIncorrecto(ReferenciaRDS refAsiento,ReferenciaRDS refJustificante,Map refDocumentos,String numregistro,Date fechaRegistro)  throws ExcepcionBTE{
    	return crearEntrada(refAsiento,refJustificante,refDocumentos,numregistro,fechaRegistro,ConstantesBTE.CONFIRMACIONPREREGISTRO_GESTOR);
    }
    
    /**
     * Confirmacion automatica de preenvios
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String crearEntradaPreenvioAutomatico(ReferenciaRDS refAsiento,ReferenciaRDS refJustificante,Map refDocumentos,String numregistro,Date fechaRegistro)  throws ExcepcionBTE{
    	return crearEntrada(refAsiento,refJustificante,refDocumentos,numregistro,fechaRegistro,ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA);
    }
	  
    
    /**
     * Actualización tras la confirmacion presencial de un preenvio con confirmacion automatica
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.registro}"
     */
    public String confirmacionEntradaPreenvioAutomatico(String numPreregistro,String numregistro,Date fechaRegistro)  throws ExcepcionBTE{
    	try{
    		// Obtenemos entrada y comprobamos que sea un preenvio confirmado automaticamente
    		TramiteBandejaDelegate tbd = DelegateUtil.getTramiteBandejaDelegate();
    		TramiteBandeja t = tbd.obtenerTramiteBandejaPorNumeroPreregistro(numPreregistro);
    		if (t==null) throw new Exception("No existe en bandeja tramite con numero preregistro: " + numPreregistro);
    		if (t.getTipo()!=ConstantesAsientoXML.TIPO_PREENVIO) throw new Exception("El tramite con numero preregistro: " + numPreregistro + " no es un preenvio");
    		if (!t.getTipoConfirmacionPreregistro().equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA)) throw new Exception("El tramite con numero preregistro: " + numPreregistro + " no es un preenvio con confirmacion automatica");
    		
    		// Actualizamos informacion de registro
    		t.setTipoConfirmacionPreregistro(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO);
    		t.setNumeroRegistro(numregistro);
    		t.setFechaRegistro(fechaRegistro);    		
    		tbd.grabarTramiteBandeja(t);
    		
    		return t.getNumeroEntrada();
    	}catch (Exception ex){
    		throw new ExcepcionBTE("No se ha podido confirmar el preenvio automatico",ex);
    	}
    }
    
    
    // ------------------------ Funciones utilidad ----------------------------------------------------------------
    /**
     * Crea entrada en Bandeja
     */
    private String crearEntrada(ReferenciaRDS refAsiento,ReferenciaRDS refJustificante,Map refDocumentos,String numregistro,Date fechaRegistro,String tipoConfirmacionPreregistro)  throws ExcepcionBTE{
    	try{
	    	// Obtenemos asiento del RDS
	    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
	    	DocumentoRDS docRDS = rds.consultarDocumento(refJustificante);
	    	DocumentoRDS docAsientoRDS = rds.consultarDocumento(refAsiento,false);
	    	
	    	// Parseamos asiento
	    	FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
	    	Justificante justificante = factoria.crearJustificanteRegistro(new ByteArrayInputStream (docRDS.getDatosFichero()));
			AsientoRegistral asiento = justificante.getAsientoRegistral();
			
	    	// Creamos objeto TramiteBTE
	    	TramiteBTE tramiteBTE = new TramiteBTE();	    	
	    	// Generamos número entrada y fecha entrada
	    	String numeroEntrada = generarNumeroEntrada();
	    	tramiteBTE.setNumeroEntrada(numeroEntrada);
	    	Timestamp ahora = new Timestamp(System.currentTimeMillis());
	    	tramiteBTE.setFecha(ahora);    	
	    	tramiteBTE.setUnidadAdministrativa(new Long(asiento.getDatosAsunto().getCodigoUnidadAdministrativa()));
	    	
	    	// Controlamos que el tipo de asiento sea válido
	    	switch (asiento.getDatosOrigen().getTipoRegistro().charValue()){
	    		case ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA:
	    		case ConstantesAsientoXML.TIPO_ENVIO:
	    		case ConstantesAsientoXML.TIPO_PREREGISTRO:
	    		case ConstantesAsientoXML.TIPO_PREENVIO:
	    			break;
	    		default:
	    			throw new Exception("El tipo asiento no es válido para crear una entrada en la bandeja: " + asiento.getDatosOrigen().getTipoRegistro());	    			
	    	}	    	
	    	tramiteBTE.setTipo(asiento.getDatosOrigen().getTipoRegistro().charValue());
	    	tramiteBTE.setIdentificadorTramite(BteStringUtil.getModelo(asiento.getDatosAsunto().getIdentificadorTramite()));
	    	tramiteBTE.setVersionTramite(BteStringUtil.getVersion(asiento.getDatosAsunto().getIdentificadorTramite()));
	    	tramiteBTE.setIdioma(asiento.getDatosAsunto().getIdiomaAsunto());
	    	Iterator it = asiento.getDatosInteresado().iterator();
	    	while (it.hasNext()){
	    		DatosInteresado di = (DatosInteresado) it.next();
	    		if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE)){
	    			tramiteBTE.setNivelAutenticacion(di.getNivelAutenticacion().charValue());
	    			tramiteBTE.setUsuarioSeycon(di.getUsuarioSeycon());
	    			tramiteBTE.setUsuarioNif(di.getNumeroIdentificacion());
	    			tramiteBTE.setUsuarioNombre(di.getIdentificacionInteresado());	    			
	    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO)){
	    			tramiteBTE.setRepresentadoNif(di.getNumeroIdentificacion());
	    			tramiteBTE.setRepresentadoNombre(di.getIdentificacionInteresado());
	    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_DELEGADO)){
	    			tramiteBTE.setDelegadoNif(di.getNumeroIdentificacion());
	    			tramiteBTE.setDelegadoNombre(di.getIdentificacionInteresado());
	    		}
	    	}
	    	
	    	// En caso de ser una entrada telematica (registro / envio) comprobamos si esta firmada digitalmente
	    	tramiteBTE.setFirmadaDigitalmente(false);
	    	if (tramiteBTE.getTipo() == ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA || tramiteBTE.getTipo() == ConstantesAsientoXML.TIPO_ENVIO){
	    		if (StringUtils.isNotEmpty(tramiteBTE.getUsuarioNif()) && docAsientoRDS.getFirmas() != null){	    			
		    		for ( int i = 0 ; i < docAsientoRDS.getFirmas().length; i++ )
					{
						FirmaIntf firma = docAsientoRDS.getFirmas()[ i ];			
						if ( tramiteBTE.getUsuarioNif().equals(firma.getNif() )){
							tramiteBTE.setFirmadaDigitalmente(true);
							break;
						}						
					}
	    		}
	    	}
	    	
	    	tramiteBTE.setDescripcionTramite(asiento.getDatosAsunto().getExtractoAsunto());
	    	
	    	tramiteBTE.setCodigoReferenciaRDSAsiento(refAsiento.getCodigo());
	    	tramiteBTE.setClaveReferenciaRDSAsiento(refAsiento.getClave());	    	
	    	tramiteBTE.setCodigoReferenciaRDSJustificante(refJustificante.getCodigo());
	    	tramiteBTE.setClaveReferenciaRDSJustificante(refJustificante.getClave());
	    		    	
	    	if (asiento.getDatosOrigen().getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA ||
	    		asiento.getDatosOrigen().getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_ENVIO ){
	    		tramiteBTE.setNumeroRegistro(justificante.getNumeroRegistro());	    	
		    	tramiteBTE.setFechaRegistro(justificante.getFechaRegistro());
		    	tramiteBTE.setNumeroPreregistro(null);	    	
		    	tramiteBTE.setFechaPreregistro(null);
	    	}else{
	    		if (StringUtils.isEmpty(numregistro) || fechaRegistro == null){
	        		throw new ExcepcionBTE("El número y fecha de registro/envío no puede ser nulo");
	        	}
	    		tramiteBTE.setNumeroRegistro(numregistro);	    	
		    	tramiteBTE.setFechaRegistro(fechaRegistro);
		    	tramiteBTE.setNumeroPreregistro(justificante.getNumeroRegistro());	    	
		    	tramiteBTE.setFechaPreregistro(justificante.getFechaRegistro());
	    	}
	    	
	    	
	    	// Tratamos documentos asiento
	    	it = asiento.getDatosAnexoDocumentacion().iterator();
	    	ReferenciaRDS refDatosPropios=null;
	    	HashMap docsPre = new HashMap(); // Hash auxiliar para asociar documentos asiento con documentos presenciales
	    	while (it.hasNext()){
	    		DatosAnexoDocumentacion da = (DatosAnexoDocumentacion) it.next();
	    		DocumentoBTE docBTE = new DocumentoBTE();
	    		docBTE.setNombre(da.getExtractoDocumento());	    		
	    			    		    		
	    		DatosDocumentoTelematico fic = new DatosDocumentoTelematico();
	    		ReferenciaRDS ref = (ReferenciaRDS) refDocumentos.get(da.getIdentificadorDocumento());
	    		fic.setIdentificador(BteStringUtil.getModelo(da.getIdentificadorDocumento()));
	    		fic.setNumeroInstancia(BteStringUtil.getVersion(da.getIdentificadorDocumento()));
	    		fic.setCodigoReferenciaRds(ref.getCodigo());
    			fic.setClaveReferenciaRds(ref.getClave());
    			docBTE.setPresentacionTelematica(fic);
	    		
	    		tramiteBTE.getDocumentos().add(docBTE);
	    		
	    		if (da.getTipoDocumento().charValue() == ConstantesAsientoXML.DATOSANEXO_DATOS_PROPIOS){
	    			refDatosPropios = ref;
	    		}
	    		
	    		docsPre.put(da.getIdentificadorDocumento(),docBTE);
	    	}
	    	
	    	// Parseamos documento de datos propios
	    	if (refDatosPropios == null) throw new Exception("No se encuentra documento de datos propios");
	    	DocumentoRDS dpRDS = rds.consultarDocumento(refDatosPropios);
	    	FactoriaObjetosXMLDatosPropios factoriaDp = ServicioDatosPropiosXML.crearFactoriaObjetosXML();
	    	DatosPropios datosPropios = factoriaDp.crearDatosPropios(new ByteArrayInputStream (dpRDS.getDatosFichero()));
	    	
	    	// Establecemos informacion avisos tramitacion
	    	tramiteBTE.setHabilitarAvisos(datosPropios.getInstrucciones().getHabilitarAvisos());
	    	tramiteBTE.setAvisoEmail(datosPropios.getInstrucciones().getAvisoEmail());
	    	tramiteBTE.setAvisoSMS(datosPropios.getInstrucciones().getAvisoSMS());	    	
	    	
	    	// Establecemos informacion notificacion telematica
	    	tramiteBTE.setHabilitarNotificacionTelematica(datosPropios.getInstrucciones().getHabilitarNotificacionTelematica());
	    	
	    	// Establecemos info tramite subsanacion
	    	if (datosPropios.getInstrucciones().getTramiteSubsanacion() != null){
	    		tramiteBTE.setSubsanacionExpedienteCodigo(datosPropios.getInstrucciones().getTramiteSubsanacion().getExpedienteCodigo());
	    		tramiteBTE.setSubsanacionExpedienteUnidadAdministrativa(datosPropios.getInstrucciones().getTramiteSubsanacion().getExpedienteUnidadAdministrativa());
	    	}
	    	
	    	// Establecemos informacion documentos presenciales	(para preregistro / preenvio)
	    	if (asiento.getDatosOrigen().getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_PREREGISTRO ||
		    		asiento.getDatosOrigen().getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_PREENVIO ){		    
		    	for (it = datosPropios.getInstrucciones().getDocumentosEntregar().getDocumento().iterator();it.hasNext();){
		    		Documento docPres = (Documento) it.next();
		    		// Obviamos el justificante, ya que en la entrada se establecera la referencia y clave RDS de dicho justificante
		    		if ( docPres.getTipo().charValue() ==  ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_JUSTIFICANTE )
		    		{
		    			continue;
		    		}
		    		    		
		    		// Comprobamos si esta asociado a un documento presentado en el asiento
		    		// Si no esta asociado creamos nuevo documento
		    		DocumentoBTE doc = null;    		
		    		if (docPres.getIdentificador() != null ) {
		    			doc = (DocumentoBTE) docsPre.get(docPres.getIdentificador());
		    		}		    		
		    		if (doc == null) {
		    			doc = new DocumentoBTE();
		    			doc.setNombre( docPres.getTitulo() );		    			
		    			tramiteBTE.getDocumentos().add(doc);
		    		}
		    		
		    		// Establecemos parametros presenciales
		    		DatosDocumentoPresencial presencial = new DatosDocumentoPresencial();
		    		if (docPres.getIdentificador() != null ) {		    			
		    			presencial.setIdentificador(BteStringUtil.getModelo(docPres.getIdentificador()));
		    			presencial.setNumeroInstancia(BteStringUtil.getVersion(docPres.getIdentificador()));
		    		}
		    		presencial.setTipoDocumento(docPres.getTipo().charValue());    		    		    		
		    		presencial.setCompulsarDocumento((docPres.isCompulsar().booleanValue()?'S':'N'));
		    		presencial.setFirma((docPres.isFirmar().booleanValue()?'S':'N'));
		    		presencial.setFotocopia((docPres.isFotocopia().booleanValue()?'S':'N'));
		    		doc.setPresentacionPresencial(presencial);
		    		
		    	}  	    		    	
	    	}

	    	// Obtenemos id procedimiento al que pertenece el tramite
	    	// (si xml datos propios ha sido generado antes de la version 1.1.6 no tendra idprocedimiento,
	    	//   ponemos como idprocedimiento el idtramite)
	    	String identificadorProcedimiento = datosPropios.getInstrucciones().getIdentificadorProcedimiento();
	    	if (identificadorProcedimiento == null) {
	    		identificadorProcedimiento = tramiteBTE.getIdentificadorTramite();
	    	}
	    	tramiteBTE.setIdentificadorProcedimiento(identificadorProcedimiento);
	  
	    	
	    	// Convertimos TramiteBTE a TramiteBandeja
	    	TramiteBandejaDelegate td;
	    	TramiteBandeja tramite;
	    	comprobarTramiteBTE(tramiteBTE);
			tramite = tramiteBTEToTramiteBandeja(tramiteBTE);    	
	    	
	    	// Especificamos fecha inicio procesamiento (se renovara cada vez que pase a no procesada)
			tramite.setFechaInicioProcesamiento(ahora);
			
	    	// Especificamos tipo de confirmacion
	    	tramite.setTipoConfirmacionPreregistro(tipoConfirmacionPreregistro);
	    	
	    	// Generamos clave de acceso
	    	tramite.setClaveAcceso(generarClave());	    	
	    	
	    	// Guardamos tramite en Bandeja    	
    		td = DelegateUtil.getTramiteBandejaDelegate();
    		Long id = td.grabarTramiteBandeja(tramite);    		
    		TramiteBandeja tb = td.obtenerTramiteBandeja(id);
    		
    		// Creamos usos documentos para Bandeja
    		crearUsosRDS(tb);
    		
    		// Devolvemos número entrada generada
    		String res = tb.getNumeroEntrada();
    		
    		// Aviso BackOffice
        	avisoBackOffice(res);
        	
        	// Devolvemos número entrada generada
        	return  res;
    		
    	}catch (Exception ex){
    		throw new ExcepcionBTE("No se ha podido guardar trámite",ex);
    	}
    }
        
    /**
     * Convierte TramiteBTE a TramiteBandeja 
     * @param t
     * @return
     */
    private TramiteBandeja tramiteBTEToTramiteBandeja(TramiteBTE tramBte) throws ExcepcionBTE{
    	try{   		
    		ProcedimientoDelegate od;
    		try{
        		od = DelegateUtil.getTramiteDelegate();
        	}catch (Exception ex){
        		throw new ExcepcionBTE("No se han podido obtener delegados",ex);
        	}    	
        	        	        	             	
        	if (tramBte.getCodigoEntrada() != null){
        		throw new ExcepcionBTE("No se permite la modificación, sólo se permiten nuevas entradas");
        	}
        	
        	// Comprobamos si existe el procedimiento del tramite
        	Procedimiento procedimiento;
        	try{
        		procedimiento = (Procedimiento) od.obtenerProcedimiento(tramBte.getIdentificadorProcedimiento());
        		if (procedimiento == null) throw new Exception("No existe procedimiento");
        	}catch(Exception e){
        		throw new ExcepcionBTE("En la Bandeja Telemática no esta definido el procedimiento con id " + tramBte.getIdentificadorTramite()+ ". Revise la configuración de la Bandeja.",e);
        	}
        	
        	// Establecemos datos tramite
        	TramiteBandeja t;        	    				    			 
			t = new TramiteBandeja();			
			t.setUnidadAdministrativa(tramBte.getUnidadAdministrativa());	
			t.setNumeroEntrada(tramBte.getNumeroEntrada());
	    	t.setFecha(tramBte.getFecha());
	    	t.setTipo(tramBte.getTipo());
	    	t.setProcesada('N');
	    	t.setFirmada(tramBte.isFirmadaDigitalmente()?'S':'N');
	    	t.setIdioma(tramBte.getIdioma());
	    	t.setIdentificadorTramite(tramBte.getIdentificadorTramite());
	    	t.setProcedimiento(procedimiento);
	    	t.setVersionTramite(tramBte.getVersionTramite());
	    	t.setNivelAutenticacion(tramBte.getNivelAutenticacion());
	    	t.setUsuarioSeycon(tramBte.getUsuarioSeycon());
	    	t.setDescripcionTramite(tramBte.getDescripcionTramite());
	    	t.setCodigoRdsAsiento(new Long(tramBte.getCodigoReferenciaRDSAsiento()));
	    	t.setClaveRdsAsiento(tramBte.getClaveReferenciaRDSAsiento());
	    	t.setCodigoRdsJustificante(new Long(tramBte.getCodigoReferenciaRDSJustificante()));
	    	t.setClaveRdsJustificante(tramBte.getClaveReferenciaRDSJustificante());
	    	t.setNumeroRegistro(tramBte.getNumeroRegistro());	
	    	t.setFechaRegistro(tramBte.getFechaRegistro());
	    	t.setNumeroPreregistro(tramBte.getNumeroPreregistro());
	    	t.setFechaPreregistro(tramBte.getFechaPreregistro());
	    	t.setUsuarioNif(tramBte.getUsuarioNif());
	    	t.setUsuarioNombre(tramBte.getUsuarioNombre());  			    		
    		t.setRepresentadoNif(tramBte.getRepresentadoNif());
    		t.setRepresentadoNombre(tramBte.getRepresentadoNombre());
    		t.setDelegadoNif(tramBte.getDelegadoNif());
    		t.setDelegadoNombre(tramBte.getDelegadoNombre());	
    		t.setTipoConfirmacionPreregistro(tramBte.getTipoConfirmacionPreregistro());
    		t.setHabilitarAvisos(tramBte.getHabilitarAvisos());
    		t.setAvisoEmail(tramBte.getAvisoEmail());
    		t.setAvisoSMS(tramBte.getAvisoSMS());
    		t.setHabilitarNotificacionTelematica(tramBte.getHabilitarNotificacionTelematica());
    		t.setSubsanacionExpedienteId(tramBte.getSubsanacionExpedienteCodigo());
    		t.setSubsanacionExpedienteUA(tramBte.getSubsanacionExpedienteUnidadAdministrativa());
    		// Establecemos documentos	    	
	    	for (Iterator it = tramBte.getDocumentos().iterator();it.hasNext();){	    		
	    		DocumentoBTE dbantel = (DocumentoBTE) it.next();
	    		DocumentoBandeja d = new DocumentoBandeja();	   
	    		d.setNombre(dbantel.getNombre());
	    		d.setPresencial(dbantel.getPresentacionPresencial() != null?"S":"N");
	    		
	    		// Datos presenciales
	    		if (dbantel.getPresentacionPresencial() != null){	    			    			    		
		    		d.setTipoDocumento(Character.toString(dbantel.getPresentacionPresencial().getTipoDocumento()));
		    		d.setCompulsarDocumento(Character.toString(dbantel.getPresentacionPresencial().getCompulsarDocumento()));
		    		d.setFotocopia(Character.toString(dbantel.getPresentacionPresencial().getFotocopia()));
		    		d.setFirma(Character.toString(dbantel.getPresentacionPresencial().getFirma()));
		    		if (dbantel.getPresentacionPresencial().getIdentificador() != null){
		    			d.setIdentificador(dbantel.getPresentacionPresencial().getIdentificador());
			    		d.setNumeroInstancia(dbantel.getPresentacionPresencial().getNumeroInstancia());
		    		}
	    		}
	    		
	    		// Datos telemáticos
	    		if (dbantel.getPresentacionTelematica() != null){
	    			d.setIdentificador(dbantel.getPresentacionTelematica().getIdentificador());
		    		d.setNumeroInstancia(dbantel.getPresentacionTelematica().getNumeroInstancia());
	    			d.setRdsCodigo(new Long(dbantel.getPresentacionTelematica().getCodigoReferenciaRds()));
	    			d.setRdsClave(dbantel.getPresentacionTelematica().getClaveReferenciaRds());	    			
	    		}	    		
	    		
	    		t.addDocumento(d);
	    	}    	
	    	return t;    		
	    }catch (Exception e){
			throw new ExcepcionBTE("No se ha podido convertir TramitePersistente en TramiteBandeja",e);
		}
    }
        
    /**
     * Comprobaciones sobre tramite BTE
     * @param obj
     * @throws ExcepcionBTE
     */
    private void comprobarTramiteBTE(TramiteBTE obj) throws ExcepcionBTE{            	    	    
	  	// Realizamos comprobaciones
    	//TODO: Realizar comprobaciones entrada en bandeja (num registro, obligatorios,etc)
    }
    
    /**
     * Crea usos RDS para los documentos de la bandeja
     * @param tramiteBTE
     */
    private void crearUsosRDS(TramiteBandeja tramite) throws Exception{
    	ReferenciaRDS refRDS;
    	UsoRDS uso;
    	
    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();    	    	    	
    	
    	// Creamos uso para asiento
    	refRDS = new ReferenciaRDS();
    	refRDS.setCodigo(tramite.getCodigoRdsAsiento().longValue());
    	refRDS.setClave(tramite.getClaveRdsAsiento());
    	uso = new UsoRDS();
    	uso.setReferenciaRDS(refRDS);
    	uso.setReferencia(tramite.getNumeroEntrada());    	
    	uso.setTipoUso(ConstantesRDS.TIPOUSO_BANDEJA);
    	rds.crearUso(uso);
    	
    	// Creamos uso para justificante
    	refRDS = new ReferenciaRDS();
    	refRDS.setCodigo(tramite.getCodigoRdsJustificante().longValue());
    	refRDS.setClave(tramite.getClaveRdsJustificante());
    	uso = new UsoRDS();
    	uso.setReferenciaRDS(refRDS);
    	uso.setReferencia(tramite.getNumeroEntrada());    	
    	uso.setTipoUso(ConstantesRDS.TIPOUSO_BANDEJA);
    	rds.crearUso(uso);
    	
    	// Creamos usos para documentos (aportados telemáticamente)
    	for (Iterator it =tramite.getDocumentos().iterator();it.hasNext();){
    		DocumentoBandeja db = (DocumentoBandeja)it.next();    	
    		if (db.getRdsCodigo() == null) continue; // Sólo para los aportados telemáticamente
	    	refRDS = new ReferenciaRDS();
	    	refRDS.setCodigo(db.getRdsCodigo().longValue());
	    	refRDS.setClave(db.getRdsClave());
	    	uso = new UsoRDS();
	    	uso.setReferenciaRDS(refRDS);
	    	uso.setReferencia(tramite.getNumeroEntrada());    	
	    	uso.setTipoUso(ConstantesRDS.TIPOUSO_BANDEJA);
	    	rds.crearUso(uso);
    	}    	
    	
    }
    
    /**
     * Genera Número de Entrada con el siguiente formato: "BTE"/NUMEROENTRADA/AÑO
     * donde: 
     * 	- Año: año actual
     *  - Numero entrada: Secuencia por año
     * @return
     */
    private String generarNumeroEntrada() throws ExcepcionBTE{
    	TramiteBandejaDelegate td;
		try{
    		td = DelegateUtil.getTramiteBandejaDelegate();
    		return td.generarNumeroEntrada();    		
    	}catch(Exception e){
    		throw new ExcepcionBTE("No se puede generar el numero de entrada",e);
    	}    			
    }
    
    /**
     * Tras producirse una entrada en el BackOffice realizamos aviso inmediato si procede  
     * @param entrada
     */
    private void avisoBackOffice(String numeroEntrada) throws Exception{
    	
    	// TODO Destino cola parametrizable por properties
    	
    	// Recuperamos informacion tramite
    	TramiteBandejaDelegate td = DelegateUtil.getTramiteBandejaDelegate();
		TramiteBandeja tramiteBandeja = td.obtenerTramiteBandeja(numeroEntrada);
		
		// Comprobamos si tiene habilitado el aviso inmediato
		if (tramiteBandeja.getProcedimiento().avisosEnabled() &&
			tramiteBandeja.getProcedimiento().getInmediata() == 'S'){
			
			// Dejamos entrada en la cola de avisos inmediatos
	    	InitialContext ctx = new InitialContext();
	    	String colaAvisos = (String) ctx.lookup("java:comp/env/colaAvisos");
		    Queue queue = (Queue) ctx.lookup(colaAvisos);		    		
		    QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("java:/JmsXA");
		    QueueConnection cnn = factory.createQueueConnection();
		    
		    // Creamos sesion transacted para que hasta q no se haga commit no se envíe el mensaje
		    QueueSession sess = cnn.createQueueSession(true,0);    		
		    
			TextMessage msg = sess.createTextMessage(numeroEntrada);
			QueueSender sender = sess.createSender(queue);
			sender.send(msg);
			
			cnn.close();
		
		}
    	    	
    }
    
    /**
     * Genera clave de acceso (Cadena de 10 carácteres)
     * @return
     */
    private String generarClave(){
    	Random r = new Random();    	
    	String clave="";
    	int ca = Character.getNumericValue('a');
    	for (int i=0;i<10;i++){
    		clave += Character.forDigit(ca + r.nextInt(26),Character.MAX_RADIX);    		
    	}
    	return clave;    	    	
    }
  	
}

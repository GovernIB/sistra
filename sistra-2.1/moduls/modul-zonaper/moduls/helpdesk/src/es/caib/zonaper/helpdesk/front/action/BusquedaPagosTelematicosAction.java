package es.caib.zonaper.helpdesk.front.action;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.xml.ConstantesXML;
import es.caib.xml.analiza.Analizador;
import es.caib.xml.analiza.Nodo;
import es.caib.xml.util.HashMapIterable;
import es.caib.zonaper.helpdesk.front.Constants;
import es.caib.zonaper.helpdesk.front.form.PagosTelematicosForm;
import es.caib.zonaper.model.DocumentoEntradaPreregistro;
import es.caib.zonaper.model.DocumentoEntradaTelematica;
import es.caib.zonaper.model.DocumentoPersistente;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.model.PagoTelematico;
import es.caib.zonaper.model.TramitePersistente;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;
import es.caib.zonaper.persistence.delegate.EntradaTelematicaDelegate;
import es.caib.zonaper.persistence.delegate.TramitePersistenteDelegate;

/**
 * @struts.action
 *  name="pagosTelematicosForm"
 *  path="/busquedaPagosTelematicos"
 *  scope="request"
 *  validate="true"
 *  
 *  
 * @struts.action-forward
 *  name="success" path=".busquedaPagosTelematicos"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class BusquedaPagosTelematicosAction extends BaseAction
{
	// Formato en la que se guarda la fecha del pago en el xml
	private final static String FORMATO_FECHAS = "yyyyMMddHHmmss";	

	// Constantes para la generación del xml
	public final static String XML_ROOT = "/PAGO";	
	private final static String XML_TIPO = XML_ROOT + "/DATOS_PAGO/TIPO";
	private final static String XML_ESTADO = XML_ROOT + "/DATOS_PAGO/ESTADO";
	private final static String XML_NUMERO_DUI = XML_ROOT + "/DATOS_PASARELA/NUMERO_DUI";
	private final static String XML_LOCALIZADOR = XML_ROOT + "/DATOS_PASARELA/LOCALIZADOR";
	private final static String XML_FECHA_PAGO = XML_ROOT + "/DATOS_PASARELA/FECHA_PAGO";
	
	protected static Log log = LogFactory.getLog(BusquedaPagosTelematicosAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		PagosTelematicosForm pagosForm = ( PagosTelematicosForm ) form;
		
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,Constants.PAGO_TAB);		
		
		
		request.setAttribute("nivel", Character.toString(pagosForm.getNivelAutenticacion()));

		List result = new ArrayList();
		

		if(pagosForm.getNivelAutenticacion() == Constants.MODO_AUTENTICACION_ANONIMO)
		{
			if((pagosForm.getClavePersistencia() != null) && !pagosForm.getClavePersistencia().equals(""))
			{
				String clave = pagosForm.getClavePersistencia();
				// Tramites Persistentes y Backup
				fillPagosTramitePersistentePorClave(clave,result);

				// Entradas Telematicas
				fillPagosEntradasTelematicasPorClave(clave,result);
				
				// Pre-Registros
				fillPagosPreRegistrosPorClave(clave,result);
			}
		}
		else
		{
			if((pagosForm.getUsuarioNif() != null) && !pagosForm.getUsuarioNif().equals(""))
			{
				log.debug("Fecha: " + pagosForm.getFecha() + " HoraInicial: " +
						pagosForm.getHoraInicial() + " HoraFinal: " +
						pagosForm.getHoraFinal());
				
				SimpleDateFormat df = new SimpleDateFormat(Constants.FORMATO_FECHAS);
				String fecha = pagosForm.getFecha();
				
				Date fechaInicial = df.parse(fecha + " " + pagosForm.getHoraInicial()+ ":" + pagosForm.getMinInicial());
				Date fechaFinal = df.parse(fecha + " " + pagosForm.getHoraFinal() + ":" + pagosForm.getMinFinal());

				// Buscamos en la zona de persistencia
				TramitePersistenteDelegate delegate = DelegateUtil.getTramitePersistenteDelegate();
				List tramites = delegate.listarTramitesPersistentes(fechaInicial,fechaFinal,/* modelo */null,Character.toString(pagosForm.getNivelAutenticacion()));
				List tramitesPersUsua = purgeTramitesPersistentesPorNif(tramites,pagosForm.getUsuarioNif());				
				fillPagosTramitesPersistentes(tramitesPersUsua,result,false);
				
				// Buscamos en Backup de persistencia
				tramites = delegate.listarTramitesPersistentesBackup(fechaInicial,fechaFinal,/* modelo */null,Character.toString(pagosForm.getNivelAutenticacion()));
				List tramitesPersBackUsua = purgeTramitesPersistentesPorNif(tramites,pagosForm.getUsuarioNif());
				fillPagosTramitesPersistentes(tramitesPersBackUsua,result,true);

				// Buscamos en Entregas Telematicas
				EntradaTelematicaDelegate etd = DelegateUtil.getEntradaTelematicaDelegate();
				tramites = etd.listarEntradaTelematicasNifModelo(pagosForm.getUsuarioNif(),null,fechaInicial,fechaFinal,Character.toString(pagosForm.getNivelAutenticacion()));
				fillPagosEntradasTelematicas(tramites,result);


				// Buscamos en Pre-Registro
				EntradaPreregistroDelegate epd = DelegateUtil.getEntradaPreregistroDelegate();
				tramites = epd.listarEntradaPreregistrosNifModelo(pagosForm.getUsuarioNif(),null,fechaInicial,fechaFinal,Character.toString(pagosForm.getNivelAutenticacion()));
				fillPagosPreRegistros(tramites,result);

			}
		}

		request.setAttribute( "lstPagos", result);

		
		
		return mapping.findForward( "success" );
    }

	private void fillPagosTramitePersistentePorClave(String clave, List result) throws Exception
	{
		// Buscamos en la zona de persistencia
		TramitePersistenteDelegate delegate = DelegateUtil.getTramitePersistenteDelegate();
		TramitePersistente tp = delegate.obtenerTramitePersistente(clave);
		if(tp != null)
		{
			fillPagosFromTramitePersistente(tp,result,false);
		}

		// Buscamos en Backup de Tramites Persistentes
		tp = delegate.obtenerTramitePersistenteBackup(clave);
		
		if(tp != null)
		{
			fillPagosFromTramitePersistente(tp,result,true);
		}
	}
	

	private void fillPagosTramitesPersistentes(List tramites, List result, boolean backup) throws Exception
	{
		for(Iterator it = tramites.iterator(); it.hasNext();)
		{
			TramitePersistente tp = (TramitePersistente)it.next();
			fillPagosFromTramitePersistente(tp,result,backup);
		}
	}
	
	private void fillPagosEntradasTelematicasPorClave(String clave, List result) throws Exception
	{
	 	EntradaTelematicaDelegate  etd = DelegateUtil.getEntradaTelematicaDelegate();
	 	EntradaTelematica et = etd.obtenerEntradaTelematica(clave); 
		if(et != null)
		{
			fillPagosFromEntradaTelematica(et,result);
		}
	}
	


	private void fillPagosEntradasTelematicas(List tramites, List result) throws Exception
	{
		EntradaTelematicaDelegate etd = DelegateUtil.getEntradaTelematicaDelegate();

		for(Iterator it = tramites.iterator(); it.hasNext();)
		{
			EntradaTelematica et = (EntradaTelematica)it.next();
			et = etd.obtenerEntradaTelematica(et.getIdPersistencia());
			fillPagosFromEntradaTelematica(et,result);
		}
	}
	
	private void fillPagosFromEntradaTelematica(EntradaTelematica et, List result)
	{
		Set documentos = et.getDocumentos();
		
		for(Iterator itSet = documentos.iterator(); itSet.hasNext(); )
		{
			DocumentoEntradaTelematica det = (DocumentoEntradaTelematica) itSet.next();
				// Acceder al documento rds con su referencia y parsear el xml para buscar el nif
			ReferenciaRDS referenciaRDS = new ReferenciaRDS();
			referenciaRDS.setCodigo( det.getCodigoRDS());
			referenciaRDS.setClave( det.getClaveRDS() );
			try{
				PagoTelematico pt = obtenerPago(referenciaRDS);
				if(pt != null)
				{
					pt.setClaveRDS(det.getClaveRDS());
					pt.setCodigoRDS(Long.valueOf(det.getCodigoRDS()));
					pt.setIdioma(et.getIdioma());
					pt.setIdPersistencia(et.getIdPersistencia());
					pt.setFecha(new Timestamp(et.getFecha().getTime()));
					pt.setEstadoTramite(Constants.FINALIZADO);
					result.add(pt);
				}
			}
			catch(Exception ex)
			{
				// Seguimos con los siguientes documentos
				continue;
			}
		}
	
	}

	
	public void fillPagosPreRegistros(List tramites, List result) throws Exception
	{
		EntradaPreregistroDelegate epd = DelegateUtil.getEntradaPreregistroDelegate();

		for(Iterator it = tramites.iterator(); it.hasNext();)
		{
			EntradaPreregistro ep = (EntradaPreregistro)it.next();
			ep = epd.obtenerEntradaPreregistro(ep.getIdPersistencia());
			fillPagosFromPreRegistro(ep,result);
		}
	}
	
	private void fillPagosFromPreRegistro(EntradaPreregistro ep, List result)
	{
		Set documentos = ep.getDocumentos();
		
		for(Iterator itSet = documentos.iterator(); itSet.hasNext(); )
		{
			DocumentoEntradaPreregistro dep = (DocumentoEntradaPreregistro) itSet.next();
				// Acceder al documento rds con su referencia y parsear el xml para buscar el nif
			ReferenciaRDS referenciaRDS = new ReferenciaRDS();
			referenciaRDS.setCodigo( dep.getCodigoRDS());
			referenciaRDS.setClave( dep.getClaveRDS() );
			try{
				PagoTelematico pt = obtenerPago(referenciaRDS);
				if(pt != null)
				{
					pt.setClaveRDS(dep.getClaveRDS());
					pt.setCodigoRDS(Long.valueOf(dep.getCodigoRDS()));
					pt.setIdioma(ep.getIdioma());
					pt.setIdPersistencia(ep.getIdPersistencia());
					pt.setFecha(new Timestamp(ep.getFecha().getTime()));
	    			if (ep.getFechaConfirmacion() == null) pt.setEstadoTramite(Constants.PENDIENTE_CONFIRMACION);
	    			else pt.setEstadoTramite(Constants.FINALIZADO);
					result.add(pt);
				}
			}
			catch(Exception ex)
			{
				// Seguimos con los siguientes documentos
				continue;
			}
		}
	
	}

	private void fillPagosPreRegistrosPorClave(String clave, List result) throws Exception
	{
		EntradaPreregistroDelegate  epd = DelegateUtil.getEntradaPreregistroDelegate();
		EntradaPreregistro ep = epd.obtenerEntradaPreregistro(clave);    	
		if(ep != null)
		{
			fillPagosFromPreRegistro(ep,result);
		}
	}
	


	
	private void fillPagosFromTramitePersistente(TramitePersistente tp, List result, boolean backup)
	{
		Set documentos = tp.getDocumentos();


		for(Iterator itSet = documentos.iterator(); itSet.hasNext(); )
		{
			DocumentoPersistente dp = (DocumentoPersistente) itSet.next();
			if ( dp.getRdsClave() != null )
			{
				// Acceder al documento rds con su referencia y parsear el xml para buscar el nif
				ReferenciaRDS referenciaRDS = new ReferenciaRDS();
				referenciaRDS.setCodigo( dp.getRdsCodigo().longValue() );
				referenciaRDS.setClave( dp.getRdsClave() );
				try{
					
					PagoTelematico pt = obtenerPago(referenciaRDS);
					if(pt != null)
					{
						pt.setClaveRDS(dp.getRdsClave());
						pt.setCodigoRDS(dp.getRdsCodigo());
						pt.setIdioma(tp.getIdioma());
						pt.setIdPersistencia(tp.getIdPersistencia());
						pt.setFecha(new Timestamp(tp.getFechaModificacion().getTime()));
						if(backup) pt.setEstadoTramite(Constants.BORRADO);
						else pt.setEstadoTramite(Constants.NO_FINALIZADO);
						result.add(pt);
					}
				}
				catch(Exception ex)
				{
					// Seguimos con los siguientes documentos
					continue;
				}
			}
		}
	
	}
	
	/**
	 * Si el documento es de tipo pago obtiene los datos del pago   
	 * 
	 * @param ref Referencia RDS del documento
	 * @return Datos del pago si es un doc de pago. Null si no es un documento de pago.
	 * @throws Exception
	 */
	private PagoTelematico obtenerPago(ReferenciaRDS ref) throws Exception
	{
		RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
		SimpleDateFormat sdf = new SimpleDateFormat( FORMATO_FECHAS );
		
		// Comprobamos si es un documento de pago
		DocumentoRDS documentoRDS = rdsDelegate.consultarDocumento(ref,false);
		if(!documentoRDS.getModelo().equals(ConstantesRDS.MODELO_PAGO)) return null;
		
		// Si es un documento de pago obtenemos datos del pago
		documentoRDS = rdsDelegate.consultarDocumento( ref );
			Analizador analizador = new Analizador ();			
    	HashMapIterable map = analizador.analizar ( new ByteArrayInputStream(documentoRDS.getDatosFichero()), ConstantesXML.ENCODING );
			PagoTelematico pt = new PagoTelematico();
			pt.setEstado( (map.get(XML_ESTADO) != null) ? ((Nodo) map.get(XML_ESTADO)).getValor() : null);
			pt.setTipo( ((Nodo) map.get(XML_TIPO)).getValor().charAt(0));
			pt.setLocalizador( (map.get(XML_LOCALIZADOR) != null) ? ((Nodo) map.get(XML_LOCALIZADOR)).getValor() : null);
			pt.setDui((map.get(XML_NUMERO_DUI) != null) ? ((Nodo) map.get(XML_NUMERO_DUI)).getValor() : null);
		Nodo nodo = (Nodo) map.get(XML_FECHA_PAGO);		
			if ( nodo != null){
				pt.setFechaPago( StringUtils.isNotEmpty(nodo.getValor()) ? new Timestamp(sdf.parse(nodo.getValor()).getTime()):null);
			}
			return pt;
	
	}

    
    /**
     * Obtiene de la lista de tramites persistentes pasada por parametro los que pertenecen al usuario al que corresponde el nif
     * @param tramites Lista tramites 
     * @param nif Nif
     * @return Lista de tramites persistentes del usuario
     */
    private List purgeTramitesPersistentesPorNif(List tramites, String nif) throws Exception
    {
    	List results = new ArrayList();
    	
    	// Obtenemos usu seycon asociado al nif
    	PersonaPAD p = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(nif);
    	if (p == null) return results;
    	
    	// Buscamos los tramites pertenecientes al usuario
    	for(Iterator it = tramites.iterator(); it.hasNext();)
    	{
    		TramitePersistente tp = (TramitePersistente) it.next();
    		if (p.getUsuarioSeycon().equals(tp.getUsuario())){
    			results.add(tp);
    		}    		
    	}
    	
    	return results;
    }
}

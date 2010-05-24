package es.caib.zonaper.helpdesk.front.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.zonaper.helpdesk.front.Constants;
import es.caib.zonaper.helpdesk.front.form.BusquedaClaveTramitacionForm;
import es.caib.zonaper.helpdesk.front.plugins.PluginAudita;
import es.caib.zonaper.model.DocumentoPersistente;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.model.TramitePersistente;
import es.caib.zonaper.model.TramitePersistenteResumen;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;
import es.caib.zonaper.persistence.delegate.EntradaTelematicaDelegate;
import es.caib.zonaper.persistence.delegate.TramitePersistenteDelegate;

/**
 * @struts.action
 *  name="busquedaClaveTramitacionForm"
 *  path="/busquedaClaveTramitacion"
 *  scope="request"
 *  validate="true"
 *  
 * @struts.action-forward
 *  name="success" path=".busquedaClave"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class BusquedaClaveTramitacionAction extends BaseAction
{
	
	// TODO Pep
	//Configurarlo con properties
	
	protected static int NUMERO_MAX_TRAMITES = 20;
	
	protected static String FIN_FORMULARIO = "</FORMULARIO>";
	
	protected static Log log = LogFactory.getLog(BusquedaClaveTramitacionAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		BusquedaClaveTramitacionForm busquedaForm = ( BusquedaClaveTramitacionForm ) form;
		

		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,Constants.CLAVE_TAB);		

		log.debug("Fecha: " + busquedaForm.getFecha() + " HoraInicial: " +
				busquedaForm.getHoraInicial() + " HoraFinal: " +
				busquedaForm.getHoraFinal());
		
		SimpleDateFormat df = new SimpleDateFormat(Constants.FORMATO_FECHAS);
		String fecha = busquedaForm.getFecha();
		
		

		Date fechaInicial = df.parse(fecha + " " + busquedaForm.getHoraInicial()+ ":" +
									 busquedaForm.getMinInicial());
		Date fechaFinal = df.parse(fecha + " " + busquedaForm.getHoraFinal() + ":" +
								   busquedaForm.getMinFinal());

		TramitePersistenteDelegate delegate = DelegateUtil.getTramitePersistenteDelegate();
		int numeroTramites = delegate.numeroTramitesPersistentesAnonimos(fechaInicial,fechaFinal,busquedaForm.getModelo());
		log.debug("Numero de Tramites en Persistencia que cumplen patrón de búsqueda: " + numeroTramites);
		if(numeroTramites > NUMERO_MAX_TRAMITES)
		{
			request.setAttribute("superadoMaximo",new Boolean(true));
			return mapping.findForward( "success" );
		}
		
		// Buscamos en la zona de persistencia
		
		List tramites = delegate.listarTramitesPersistentes(fechaInicial,fechaFinal,busquedaForm.getModelo(),"A");
		
		List result = new ArrayList();
		
		fillTramitesPersistentes(tramites,result,busquedaForm.getUsuarioNif(),false);
		
		// Buscamos en Backup de Persistencia
		tramites = delegate.listarTramitesPersistentesBackup(fechaInicial,fechaFinal,busquedaForm.getModelo(),"A");
		fillTramitesPersistentes(tramites,result,busquedaForm.getUsuarioNif(),true);
		
		
		// Buscamos en Entregas Telematicas
		
		EntradaTelematicaDelegate etd = DelegateUtil.getEntradaTelematicaDelegate();
		tramites = etd.listarEntradaTelematicasNifModelo(busquedaForm.getUsuarioNif(),busquedaForm.getModelo(),
				                                         fechaInicial,fechaFinal,"A");
		
		Map descTramites = PluginAudita.getInstance().obtenerDescripcionTramites(Constants.DEFAULT_LANG);
		String descTramite = null;

		
		for(Iterator it = tramites.iterator(); it.hasNext();)
		{
			EntradaTelematica et = (EntradaTelematica)it.next();
			TramitePersistenteResumen tpr = new TramitePersistenteResumen();
			tpr.setDescripcion((String) descTramites.get(et.getTramite()));
			tpr.setFecha(new Timestamp(et.getFecha().getTime()));
			tpr.setIdioma(et.getIdioma());
			tpr.setIdPersistencia(et.getIdPersistencia());
			descTramite = (String) descTramites.get(et.getTramite());
			if(descTramite == null) descTramite = "";
			tpr.setTramite(descTramite);
			tpr.setVersion(et.getVersion().intValue());
			tpr.setEstado(Constants.FINALIZADO);
			result.add(tpr);
		}

		// Buscamos en Pre-Registro
		
		EntradaPreregistroDelegate epd = DelegateUtil.getEntradaPreregistroDelegate();
		tramites = epd.listarEntradaPreregistrosNifModelo(busquedaForm.getUsuarioNif(),busquedaForm.getModelo(),
				                                         fechaInicial,fechaFinal,"A");
		for(Iterator it = tramites.iterator(); it.hasNext();)
		{
			EntradaPreregistro ep = (EntradaPreregistro)it.next();
			TramitePersistenteResumen tpr = new TramitePersistenteResumen();
			tpr.setDescripcion((String) descTramites.get(ep.getTramite()));
			tpr.setFecha(new Timestamp(ep.getFecha().getTime()));
			tpr.setIdioma(ep.getIdioma());
			tpr.setIdPersistencia(ep.getIdPersistencia());
			descTramite = (String) descTramites.get(ep.getTramite());
			if(descTramite == null) descTramite = "";
			tpr.setTramite(descTramite);
			tpr.setVersion(ep.getVersion().intValue());
			if(ep.getFechaConfirmacion() == null)	tpr.setEstado(Constants.PENDIENTE_CONFIRMACION);
			else	tpr.setEstado(Constants.FINALIZADO);
			result.add(tpr);
		}

		request.setAttribute( "lstTramites", result);
		
		return mapping.findForward( "success" );
    }
	
	public void fillTramitesPersistentes(List tramites, List result, String nif, boolean backup) throws Exception
	{
		RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();

		Map descTramites = PluginAudita.getInstance().obtenerDescripcionTramites(Constants.DEFAULT_LANG);
		String descTramite = null;
		
		for(Iterator it = tramites.iterator(); it.hasNext();)
		{
			TramitePersistente tp = (TramitePersistente)it.next();
			Set documentos = tp.getDocumentos();
			
			for(Iterator itSet = documentos.iterator(); itSet.hasNext(); )
			{
				DocumentoPersistente dp = (DocumentoPersistente) itSet.next();
				//if ( dp.getIdentificador().startsWith( ConstantesAsientoXML.IDENTIFICADOR_DATOS_PROPIOS ) )
				if ( dp.getRdsClave() != null )
				{
					// Acceder al documento rds con su referencia y parsear el xml para buscar el nif
					ReferenciaRDS referenciaRDS = new ReferenciaRDS();
					referenciaRDS.setCodigo( dp.getRdsCodigo().longValue() );
					referenciaRDS.setClave( dp.getRdsClave() );
					/* Primero miramos si el documento es estructurado
					 * Si no lo es seguimos con el siguiente documento.
					 */
					DocumentoRDS documentoRDS = rdsDelegate.consultarDocumento(referenciaRDS,false);
					if(!documentoRDS.isEstructurado()) continue;
					/* Accedemos al documento pero con los datos */
					documentoRDS = rdsDelegate.consultarDocumento( referenciaRDS );
					byte[] byteArraySolicitud = documentoRDS.getDatosFichero();
					String datos = new String(byteArraySolicitud);
					int idx = datos.toUpperCase().indexOf(FIN_FORMULARIO);
					if(idx != -1)
					{
						idx = datos.toUpperCase().indexOf(nif.toUpperCase());
						if(idx == -1) continue;
						TramitePersistenteResumen tpr = new TramitePersistenteResumen();
						tpr.setDescripcion((String) descTramites.get(tp.getTramite()));
						tpr.setFecha(tp.getFechaModificacion());
						tpr.setIdioma(tp.getIdioma());
						tpr.setIdPersistencia(tp.getIdPersistencia());
						descTramite = (String) descTramites.get(tp.getTramite());
						if(descTramite == null) descTramite = "";
						tpr.setTramite(descTramite);
						tpr.setVersion(tp.getVersion());
						if(!backup)	tpr.setEstado(Constants.NO_FINALIZADO);
						else	tpr.setEstado(Constants.BORRADO);
						result.add(tpr);
					}
					
				}
			}
		}
	}
	
}

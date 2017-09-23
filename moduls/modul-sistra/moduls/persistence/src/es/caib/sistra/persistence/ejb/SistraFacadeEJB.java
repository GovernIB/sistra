package es.caib.sistra.persistence.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.model.AsientoCompleto;
import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.PasoTramitacion;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.model.TraTramite;
import es.caib.sistra.model.Tramite;
import es.caib.sistra.model.TramiteNivel;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.modelInterfaz.InformacionLoginTramite;
import es.caib.sistra.modelInterfaz.TramiteInfo;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;
import es.caib.sistra.persistence.intf.TramiteProcessor;
import es.caib.sistra.persistence.plugins.PluginDominio;
import es.caib.sistra.plugins.login.ConstantesLogin;
import es.caib.util.ConfigurationLogin;
import es.caib.util.StringUtil;
import es.caib.util.UsernamePasswordCallbackHandler;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;


/**
 * SessionBean para operaciones de otros módulos con Sistra
 *
 * @ejb.bean
 *  name="sistra/persistence/SistraFacade"
 *  jndi-name="es.caib.sistra.persistence.SistraFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class SistraFacadeEJB implements SessionBean
{

	private Log log = LogFactory.getLog( SistraFacadeEJB.class );

	//private javax.ejb.SessionContext ctx;

	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException
	{

	}

    public void setSessionContext(javax.ejb.SessionContext ctx)
    {
	//   this.ctx = ctx;
    }

    /**
	 * Intenta finalizar un tramite anonimo que se ha pagado pero no se ha finalizado.
	 *
	 *
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     * @ejb.transaction type="RequiresNew"
     */
	public boolean finalizarTramitePagadoAnonimo( String idPersistencia )
	{

		try {

			log.debug("Se intenta finalizar automaticamente tramite " + idPersistencia);


			PadDelegate pad = DelegatePADUtil.getPadDelegate();
			TramitePersistentePAD tramPer = pad.obtenerTramitePersistente(idPersistencia);

			if (tramPer.getNivelAutenticacion() != ConstantesLogin.LOGIN_ANONIMO) {
				log.debug("Tramite " + idPersistencia + " no es anonimo");
				return false;
			}

			RespuestaFront rf = null;

			Map paramsInicio = tramPer.getParametrosInicio();
			if (paramsInicio == null) {
				paramsInicio = new HashMap();
			}

			log.debug("Cargar tramite de persistencia...");
			InstanciaDelegate dlg = DelegateUtil.getInstanciaDelegate(true);
			dlg.create(
					tramPer.getTramite(), tramPer.getVersion(),
					tramPer.getNivelAutenticacion(),
					new Locale(tramPer.getIdioma()), paramsInicio,
					ConstantesZPE.DELEGACION_PERFIL_ACCESO_CIUDADANO, null);
			rf = dlg.cargarTramite(tramPer.getIdPersistencia());

			if (rf.getInformacionTramite().getPasoTramitacion().getTipoPaso() == PasoTramitacion.PASO_PAGAR) {
				// Esta en paso pagar, pasamos a siguiente paso
				log.debug("Esta en paso PAGAR, intentamos pasar a siguiente paso...");
				rf = dlg.siguientePaso();
			}

			// Si sigue estando en paso pagar, intentamos confirmar pago
			if (rf.getInformacionTramite().getPasoTramitacion().getTipoPaso() == PasoTramitacion.PASO_PAGAR) {
				for (Iterator it = rf.getInformacionTramite().getPagos().iterator(); it.hasNext();) {
					DocumentoFront df = (DocumentoFront) it.next();
					rf = dlg.confirmarPago(df.getIdentificador(), df.getInstancia());
				}
			}

			// Verifica si esta en paso registrar
			if (rf.getInformacionTramite().getPasoTramitacion().getTipoPaso() != PasoTramitacion.PASO_REGISTRAR) {
				log.warn("Tramite " + idPersistencia + " no se puede finalizar automaticamente: no esta en paso REGISTRAR");
				return false;
			}

			// Registrar
			log.debug("Esta en paso REGISTRAR, se intenta registrar...");
			AsientoCompleto asiento = (AsientoCompleto) rf.getParametros().get(
					"asiento");
			rf = dlg.registrarTramite(asiento.getAsiento(), null);

			// Verifica si ha terminado
			if (rf.getInformacionTramite().getPasoTramitacion().getTipoPaso() == PasoTramitacion.PASO_FINALIZAR) {
				log.info("Tramite " + idPersistencia + " finalizado automaticamente");
				return true;
			} else {
				log.warn("Tramite " + idPersistencia + " no se puede finalizar automaticamente: no se ha podido registrar");
				return false;
			}

		} catch (Exception ex) {
			log.warn("Tramite " + idPersistencia + " no se puede finalizar automaticamente: " + ex.getMessage());
			return false;
		}

	}
	
	/**
	 * Obtiene lista de tramites con su descripcion y el procedimiento al que pertenecen
	 *
	 *
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
	public List obtenerListaTramites(String idioma) {
		
		try
		{
			// Obtenemos lista con descripcion de los tramites
			List res = new ArrayList();

			List tramites = DelegateUtil.getTramiteDelegate().listarTramites();

			for (Iterator it=tramites.iterator();it.hasNext();){
				 Tramite tramite = (Tramite) it.next();
				 TraTramite tra = (TraTramite) tramite.getTraduccion(idioma);
				 String descTramite = "";
				 if (tra != null){
					 descTramite = tra.getDescripcion();
				 }else{
					tra = (TraTramite) tramite.getTraduccion("es");
					if (tra != null){
						descTramite = tra.getDescripcion();
					 }else{
						 descTramite =  tramite.getIdentificador();
					 }
				 }
				 
				 TramiteInfo t = new TramiteInfo();
				 t.setCodigo(tramite.getIdentificador());
				 t.setDescripcion(descTramite);
				 t.setProcedimientoId(tramite.getProcedimiento());
				 res.add(t);
			}

			return res;
		}
		catch( Exception exc )
		{
			// Error: devolvemos nulo
			log.error("Excepción al recuperar lista de tramites: " + exc.getMessage(),exc);
			return null;
		}
		
	}

	/**
	 * Obtiene un map con la descripcion de los trámites  (KEY=Id Tramite / VALUE=Descripción)
	 *
	 *
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
	public Map obtenerDescripcionTramites( String idioma )
	{
		try
		{
			// Obtenemos lista con descripcion de los tramites
			Map desc = new HashMap();

			List tramites = DelegateUtil.getTramiteDelegate().listarTramites();

			for (Iterator it=tramites.iterator();it.hasNext();){
				 Tramite tramite = (Tramite) it.next();
				 TraTramite tra = (TraTramite) tramite.getTraduccion(idioma);
				 if (tra != null){
					 desc.put(tramite.getIdentificador(),tra.getDescripcion());
				 }else{
					tra = (TraTramite) tramite.getTraduccion("es");
					if (tra != null){
						 desc.put(tramite.getIdentificador(),tra.getDescripcion());
					 }else{
						 desc.put(tramite.getIdentificador(),tramite.getIdentificador());
					 }
				 }
			}

			return desc;
		}
		catch( Exception exc )
		{
			// Error: devolvemos nulo
			log.error("Excepción al recuperar lista de tramites: " + exc.getMessage(),exc);
			return null;
		}
	}

	/**
	 * Obtiene la información para el login de un trámite
	 * @param modelo Modelo trámite
	 * @param version Versión modelo trámite
	 * @param idioma Idioma
	 * @return Información login
	 *
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
	public InformacionLoginTramite obtenerInfoLoginTramite( String modelo,int version, String idioma )
	{
		try
		{
			InformacionLoginTramite info = new InformacionLoginTramite();

			// Obtenemos configuracion tramite version
			TramiteVersion tv = DelegateUtil.getTramiteVersionDelegate().obtenerTramiteVersion(modelo,version);

			// Descripcion
			Tramite tramite = tv.getTramite();
			TraTramite tra = (TraTramite) tramite.getTraduccion(idioma);
			if (tra != null){
				info.setDescripcionTramite(tra.getDescripcion());
			}else{
				tra = (TraTramite) tramite.getTraduccion("es");
				if (tra != null){
					info.setDescripcionTramite(tra.getDescripcion());
				 }else{
					 info.setDescripcionTramite("");
				 }
			 }

			// Niveles autenticacion
			String niveles = "";
			for (Iterator it = tv.getNiveles().iterator();it.hasNext();){
	 			TramiteNivel tn = (TramiteNivel) it.next();
	 			niveles = niveles + tn.getNivelAutenticacion();
	 		}
			info.setNivelesAutenticacion(niveles);

			// Inicio anonimo por defecto
			info.setInicioAnonimoDefecto(tv.getAnonimoDefecto() == 'S');

			return info;
		}
		catch( Exception exc )
		{
			log.error("Excepción al recuperar info para login tramite " + modelo + " - " + version + " : " + exc.getMessage(),exc);
			return null;
		}
	}

	/**
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public ValoresDominio obtenerDominio(String id, List parametros, boolean debugEnabled) {
		//TODO RAFA Generar evento al fallar dominio??

    	ValoresDominio val  = null;
    	try{
	    	PluginDominio plgDom = new PluginDominio(debugEnabled);
	    	String  idDom = plgDom.crearDominio(id);

	    	if (parametros != null){
		    	for (Iterator it=parametros.iterator();it.hasNext();)
		    	 plgDom.establecerParametro(idDom,it.next().toString());
	    	}

	    	plgDom.recuperaDominio(idDom);

	    	val = plgDom.getValoresDominio(idDom);

    	}catch (Exception ex){

    		// Si se produce excepcion generamos ValoresDominio indicando el error
    		val = new ValoresDominio();
    		val.setError(true);
    		val.setDescripcionError(StringUtil.stackTraceToString(ex));
    	}

    	String params = paramsToString(parametros);

    	if (val.isError()){
    		log.error("Error accediendo dominio " + id + params + ":\n" + val.getDescripcionError());
    	}

    	return val;
	}


    private String paramsToString(List parametros){
    	String params ="";
		if (parametros != null){
			StringBuffer sb = new StringBuffer(parametros.size() * 100);
			sb.append(" con parametros [");
			for (Iterator it=parametros.iterator();it.hasNext();){
				sb.append("'");
				sb.append(it.next());
				sb.append("' ");
			}
			sb.append("]");
			params=sb.toString();
		}
		return params;
    }


}

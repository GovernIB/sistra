package es.caib.sistra.persistence.ejb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.model.TraTramite;
import es.caib.sistra.model.Tramite;
import es.caib.sistra.model.TramiteNivel;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.modelInterfaz.InformacionLoginTramite;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.plugins.PluginDominio;
import es.caib.util.StringUtil;


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

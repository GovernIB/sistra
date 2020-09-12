package es.caib.zonaper.persistence.ejb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import org.apache.commons.lang.StringUtils;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.ElementoExpedienteItf;
import es.caib.zonaper.model.Entrada;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.model.EventoExpediente;
import es.caib.zonaper.model.IndiceElemento;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.model.Page;
import es.caib.zonaper.modelInterfaz.DetalleElementoExpedientePAD;
import es.caib.zonaper.modelInterfaz.FiltroBusquedaElementosExpedientePAD;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.util.ConfigurationUtil;

/**
 * SessionBean para mantener y consultar ElementoExpediente
 *
 * @ejb.bean
 *  name="zonaper/persistence/ElementoExpedienteFacade"
 *  jndi-name="es.caib.zonaper.persistence.ElementoExpedienteFacade"
 *  type="Stateless"
 *  view-type="both"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class ElementoExpedienteFacadeEJB extends HibernateEJB
{
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException
	{
		super.ejbCreate();
	}

	/**
	 * Acceso autenticado. Solo accesible por usuario y delegados.
	 *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ElementoExpediente obtenerElementoExpedienteAutenticado( String tipoElemento,Long codigoElemento )
	{
		try {
			ElementoExpediente elemento = recuperarElementoExpedientePorTipoCodigo(tipoElemento,codigoElemento);

			// Verificamos acceso a expediente
			if (elemento != null && !DelegateUtil.getExpedienteDelegate().verificarAccesoExpedienteAutenticado(elemento.getExpediente().getCodigo())) {
				throw new Exception("No tiene acceso al expediente");
			}

			return elemento;
		} catch (Exception e) {
			throw new EJBException(e);
		}

	}

	/**
	 * Acceso autenticado. Solo accesible por usuario y delegados.
	 *
	 * Recupera lista de ids de expedientes asociadas a indices de busqueda.
	 *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public List obtenerIdsExpedienteElementos( List indicesBusqueda )
	{
		Session session = getSession();
        try
        {
        	List res = new ArrayList();

        	if (indicesBusqueda == null || indicesBusqueda.size() <= 0) {
        		return res;
        	}

        	// Generamos where
        	StringBuilder where = new StringBuilder(indicesBusqueda.size());
        	boolean primer = true;
        	for (int i=0;i<indicesBusqueda.size();i++) {
        		IndiceElemento ie = (IndiceElemento) indicesBusqueda.get(i);
				if (!ie.getTipoElemento().equals(IndiceElemento.TIPO_EXPEDIENTE)) {
					if (!primer) {
	        			where.append(" OR ");
	        		}
	        		where.append(" (e.tipoElemento = :tipo" + i + " AND e.codigoElemento = :codigo" + i + " ) " );
	        		primer = false;
				}
        	}

        	// Generamos query para obtener lista de ids de expediente
			Query query =
        		session.createQuery("FROM ElementoExpediente e WHERE " + where.toString()  );
			for (int i=0;i<indicesBusqueda.size();i++) {
				IndiceElemento ie = (IndiceElemento) indicesBusqueda.get(i);
				if (ie.getTipoElemento().equals(IndiceElemento.TIPO_EXPEDIENTE)) {
					res.add(ie.getCodigoElemento());
				} else {
					query.setParameter("tipo" + i, ie.getTipoElemento());
					query.setParameter("codigo" + i, ie.getCodigoElemento());
				}
			}

			// Ejecutamos query
			List listaElem = query.list();
			for (Iterator it = listaElem.iterator();it.hasNext();) {
				ElementoExpediente ee = (ElementoExpediente) it.next();
				res.add(ee.getExpediente().getCodigo());
			}

        	// Devolvemos map
			return res;
        }
        catch( HibernateException he )
        {
        	throw new EJBException( he );
        }
        catch( Exception exc )
        {
        	throw new EJBException( exc );
        }
        finally
        {
        	close( session );
        }

	}

	/**
	 * Acceso anonimo
	 *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ElementoExpediente obtenerElementoExpedienteAnonimo( String tipoElemento,Long codigoElemento, String idPersistencia )
	{
		try {
			ElementoExpediente elemento = recuperarElementoExpedientePorTipoCodigo(tipoElemento,codigoElemento);

			// Verificamos acceso a expediente
			if (elemento != null && !DelegateUtil.getExpedienteDelegate().verificarAccesoExpedienteAnonimo(elemento.getExpediente().getCodigo(), idPersistencia)) {
				throw new Exception("No tiene acceso al expediente");
			}

			return elemento;
		} catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
	 * Acceso para gestor y procesos auto
	 *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.gestor}"
     */
	public ElementoExpediente obtenerElementoExpediente( String tipoElemento,Long codigoElemento)
	{
		try {
			ElementoExpediente elemento = recuperarElementoExpedientePorTipoCodigo(tipoElemento,codigoElemento);
			return elemento;
		} catch (Exception e) {
			throw new EJBException(e);
		}
	}


	/**
	 * Obtiene elemento expediente por su id persistencia
	 *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ElementoExpediente obtenerElementoExpediente( String idPersistencia )
	{
		try {
			ElementoExpediente elemento = recuperarElementoExpedientePorIdPersistencia(idPersistencia);
			return elemento;
		} catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ElementoExpedienteItf obtenerDetalleElementoExpedienteAutenticado(String tipo, Long codigo)
	{
		try {
			ElementoExpediente elemento = recuperarElementoExpedientePorTipoCodigo(tipo,codigo);
			if (elemento == null) return null;
			// El control de acceso se realizara al acceder al elemento
			return this.obtenerDetalleElementoExpedienteAutenticado(elemento.getCodigo());
		} catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ElementoExpedienteItf obtenerDetalleElementoExpedienteAnonimo(String tipo, Long codigo,String idPersistencia)
	{
		try {
			ElementoExpediente elemento = recuperarElementoExpedientePorTipoCodigo(tipo,codigo);
			if (elemento == null) return null;
			// El control de acceso se realizara al acceder al elemento
			return this.obtenerDetalleElementoExpedienteAnonimo(elemento.getCodigo(),idPersistencia);
		} catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.gestor}"
     */
	public ElementoExpedienteItf obtenerDetalleElementoExpediente(Long id)
	{
		try{
			ElementoExpediente elementoExpediente = this.recuperarElementoExpedientePorCodigo(id);
			if (ElementoExpediente.TIPO_ENTRADA_TELEMATICA.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematica(elementoExpediente.getCodigoElemento());
			}else if (ElementoExpediente.TIPO_ENTRADA_PREREGISTRO.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistro(elementoExpediente.getCodigoElemento());
			}else if (ElementoExpediente.TIPO_AVISO_EXPEDIENTE.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEventoExpedienteDelegate().obtenerEventoExpediente(elementoExpediente.getCodigoElemento());
			}else if (ElementoExpediente.TIPO_NOTIFICACION.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getNotificacionTelematicaDelegate().obtenerNotificacionTelematicaAuto(elementoExpediente.getCodigoElemento());
			}
			return null;
		}catch (Exception ex){
			throw new EJBException("Error obteniendo detalle del elemento expediente con codigo " + id,ex);
		}
	}


	/**
	 * Obtiene codigo expediente al que pertenece un elemento. No esta protegido específicamente ya que solo obtenemos el código.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.todos}"
     */
	public Long obtenerCodigoExpedienteElemento(String tipo, Long codigo)
	{
		try {
			ElementoExpediente elemento = recuperarElementoExpedientePorTipoCodigo(tipo,codigo);
			if (elemento == null) return null;
			return elemento.getExpediente().getCodigo();
		} catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ElementoExpedienteItf obtenerDetalleElementoExpedienteAutenticado(Long id)
	{
		// El control de acceso se realizara al acceder al elemento
		try{
			ElementoExpediente elementoExpediente = this.recuperarElementoExpedientePorCodigo(id);
			if (ElementoExpediente.TIPO_ENTRADA_TELEMATICA.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematicaAutenticada(elementoExpediente.getCodigoElemento());
			}else if (ElementoExpediente.TIPO_ENTRADA_PREREGISTRO.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroAutenticada(elementoExpediente.getCodigoElemento());
			}else if (ElementoExpediente.TIPO_AVISO_EXPEDIENTE.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEventoExpedienteDelegate().obtenerEventoExpedienteAutenticado(elementoExpediente.getCodigoElemento());
			}else if (ElementoExpediente.TIPO_NOTIFICACION.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getNotificacionTelematicaDelegate().obtenerNotificacionTelematicaAutenticada(elementoExpediente.getCodigoElemento());
			}
			return null;
		}catch (Exception ex){
			throw new EJBException("Error obteniendo detalle del elemento expediente con codigo " + id,ex);
		}
	}


	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ElementoExpedienteItf obtenerDetalleElementoExpedienteAnonimo(Long id,String idPersistencia)
	{
		// El control de acceso se realizara al acceder al elemento
		try{
			ElementoExpediente elementoExpediente = recuperarElementoExpedientePorCodigo(id);
			if (ElementoExpediente.TIPO_ENTRADA_TELEMATICA.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematicaAnonima(elementoExpediente.getCodigoElemento(),idPersistencia);
			}else if (ElementoExpediente.TIPO_ENTRADA_PREREGISTRO.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroAnonima(elementoExpediente.getCodigoElemento(),idPersistencia);
			}else if (ElementoExpediente.TIPO_AVISO_EXPEDIENTE.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getEventoExpedienteDelegate().obtenerEventoExpedienteAnonimo(elementoExpediente.getCodigoElemento(),idPersistencia);
			}else if (ElementoExpediente.TIPO_NOTIFICACION.equals(elementoExpediente.getTipoElemento())){
				return DelegateUtil.getNotificacionTelematicaDelegate().obtenerNotificacionTelematicaAnonima(elementoExpediente.getCodigoElemento(),idPersistencia);
			}
			return null;
		}catch (Exception ex){
			throw new EJBException("Error obteniendo detalle del elemento expediente con codigo " + id,ex);
		}
	}

	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
	public void establecerAvisoElementoExpediente(Long id, String idAviso)
	{
		Session session = getSession();
		try
		{
			ElementoExpediente elementoExpediente = ( ElementoExpediente ) session.load( ElementoExpediente.class, id );
			elementoExpediente.setCodigoAviso(idAviso);
			session.update(elementoExpediente);
		}
		catch (HibernateException he)
		{
			throw new EJBException(he);
	    }
		finally
		{
	        close(session);
	    }
	}

	/**
	 *
	 * Obtiene elementos expediente
	 *
	 *
	 * @ejb.interface-method
	 *
	 * @ejb.permission role-name="${role.auto}"
	 */
	public List obtenerElementosExpediente(FiltroBusquedaElementosExpedientePAD filtro, Integer pagina, Integer tamPagina)	{

		List result = new ArrayList();

		Session session = getSession();
		try
		{
			Query query = generarQueryBusquedaElementosExpedientePAD(session,
					filtro, false);

			List elementosExpediente = null;
			if (pagina == null || tamPagina == null) {
				elementosExpediente = query.list();
			} else {
				Page page = new Page( query, pagina, tamPagina);
				elementosExpediente = page.getList();
			}

			// Recorremos elementos expediente para recuperar datos
			final String contextoRaiz = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("sistra.contextoRaiz.front");
    		String urlZonaper = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("sistra.url") + contextoRaiz +
    				"/zonaperfront/inicio?language=" + filtro.getIdioma() + "&loginClaveAuto=true";
			for (Iterator it=elementosExpediente.iterator();it.hasNext();){
				ElementoExpediente e = (ElementoExpediente) it.next();
				ElementoExpedienteItf ei = obtenerDetalleElementoExpediente(e.getCodigo());
				DetalleElementoExpedientePAD ee = new DetalleElementoExpedientePAD();
				if (ei instanceof Entrada) {
					Entrada entrada = (Entrada) ei;
					ee.setDescripcion(entrada.getDescripcionTramite());
					ee.setTipo(Character.toString(entrada.getTipo()));
					ee.setFecha(entrada.getFecha());
					ee.setUrl(urlZonaper + "&tramite=" + e.getIdentificadorPersistencia());
					if (entrada instanceof EntradaPreregistro) {
						EntradaPreregistro preregistro = (EntradaPreregistro) entrada;
						ee.setPendiente(preregistro.getNumeroRegistro() == null);
					}
				} else if (ei instanceof NotificacionTelematica) {
					NotificacionTelematica notificacion = (NotificacionTelematica) ei;
					ee.setDescripcion(notificacion.getTituloAviso());
					ee.setTipo(DetalleElementoExpedientePAD.TIPO_NOTIFICACION);
					ee.setFecha(notificacion.getFechaRegistro());
					ee.setPendiente(notificacion.getFechaAcuse() == null || notificacion.isRechazada());
					ee.setUrl(urlZonaper + "&notificacion=" + e.getIdentificadorPersistencia());
				}  else if (ei instanceof es.caib.zonaper.model.EventoExpediente) {
					es.caib.zonaper.model.EventoExpediente evento = (es.caib.zonaper.model.EventoExpediente) ei;
					ee.setDescripcion(evento.getTitulo());
					ee.setTipo(DetalleElementoExpedientePAD.TIPO_AVISO);
					ee.setFecha(evento.getFecha());
					ee.setPendiente(evento.getFechaConsulta() == null);
					ee.setUrl(urlZonaper + "&aviso=" + e.getIdentificadorPersistencia());
				}
				result.add(ee);
			}

			return result;

		}
		catch (Exception e)
		{
			log.error("Error obteniendo elementos expediente: " + e.getMessage(), e);
			throw new EJBException("Error obteniendo elementos expediente: " + e.getMessage(), e);
	    }
		finally
		{
	        close(session);
	    }
	}


	/**
	 *
	 * Obtiene total elementos expediente
	 *
	 *
	 * @ejb.interface-method
	 *
	 * @ejb.permission role-name="${role.auto}"
	 */
	public Long obtenerTotalElementosExpediente(FiltroBusquedaElementosExpedientePAD filtro)	{
		Session session = getSession();
		try
		{
			Query query = generarQueryBusquedaElementosExpedientePAD(session,
					filtro, true);
			Long total = new Long(query.uniqueResult().toString());
		    return total;
		}
		catch (Exception e)
		{
			log.error("Error obteniendo total elementos expediente: " + e.getMessage(), e);
			throw new EJBException("Error obteniendo total elementos expediente: " + e.getMessage(), e);
	    }
		finally
		{
	        close(session);
	    }
	}

	// ----------------------------------------------------------------------------------------------------------------
	// 		FUNCIONES AUXILIARES
	// ----------------------------------------------------------------------------------------------------------------

	private Query generarQueryBusquedaElementosExpedientePAD(Session session,
			FiltroBusquedaElementosExpedientePAD filtro, boolean total)
			throws HibernateException {
		String filtroTipos = "";
		String filtroFechaInicio = "";
		String filtroFechaFin = "";
		if (filtro.getFechaInicio() != null) {
			filtroFechaInicio = " AND e.fecha >= :fechaInicio";
		}
		if (filtro.getFechaFin() != null) {
			filtroFechaFin = " AND e.fecha <= :fechaFin";
		}
		if (filtro.getTipos() != null && filtro.getTipos().size() > 0) {
			List listaTipos = new ArrayList();
			if (filtro.getTipos().contains(DetalleElementoExpedientePAD.TIPO_REGISTRO)) {
				listaTipos.add("e.tipoElemento = '" + ElementoExpediente.TIPO_ENTRADA_TELEMATICA + "' AND e.bandeja = false");
			}
			if (filtro.getTipos().contains(DetalleElementoExpedientePAD.TIPO_ENVIO)) {
				listaTipos.add("e.tipoElemento = '" + ElementoExpediente.TIPO_ENTRADA_TELEMATICA + "' AND e.bandeja = true");
			}
			if (filtro.getTipos().contains(DetalleElementoExpedientePAD.TIPO_PREREGISTRO)) {
				listaTipos.add("e.tipoElemento = '" + ElementoExpediente.TIPO_ENTRADA_PREREGISTRO + "' AND e.bandeja = false");
			}
			if (filtro.getTipos().contains(DetalleElementoExpedientePAD.TIPO_PREENVIO)) {
				listaTipos.add("e.tipoElemento = '" + ElementoExpediente.TIPO_ENTRADA_PREREGISTRO + "' AND e.bandeja = true");
			}
			if (filtro.getTipos().contains(DetalleElementoExpedientePAD.TIPO_NOTIFICACION)) {
				listaTipos.add("e.tipoElemento = '" + ElementoExpediente.TIPO_NOTIFICACION + "'");
			}
			if (filtro.getTipos().contains(DetalleElementoExpedientePAD.TIPO_AVISO)) {
				listaTipos.add("e.tipoElemento = '" + ElementoExpediente.TIPO_AVISO_EXPEDIENTE + "'");
			}
			filtroTipos = " AND ( ";
			for (int i=0; i<listaTipos.size();i++) {
				if (i>0) {
					filtroTipos += " OR ";
				}
				filtroTipos += " (" + listaTipos.get(i) + ")";
			}
			filtroTipos += " )";
		}

		String sqlTotal = "";
		if (total) {
			sqlTotal = "SELECT count(e) ";
		}

		Query query = session.createQuery( sqlTotal + "FROM ElementoExpediente e where e.expediente.nifRepresentante = :nif " +
				filtroTipos + filtroFechaInicio + filtroFechaFin +
				" order by e.fecha desc");
		query.setParameter( "nif", filtro.getNif());
		if (filtro.getFechaInicio() != null) {
			query.setParameter( "fechaInicio", filtro.getFechaInicio());
		}
		if (filtro.getFechaFin() != null) {
			query.setParameter( "fechaFin", filtro.getFechaFin());
		}
		return query;
	}

	private ElementoExpediente recuperarElementoExpedientePorCodigo( Long id )
	{
		Session session = getSession();
		try
		{
			ElementoExpediente elementoExpediente = ( ElementoExpediente ) session.load( ElementoExpediente.class, id );
			return elementoExpediente;
		}
		catch (HibernateException he)
		{
			throw new EJBException(he);
	    }
		finally
		{
	        close(session);
	    }
	}

	private ElementoExpediente recuperarElementoExpedientePorTipoCodigo(String tipoElemento,Long codigoElemento) throws Exception{
		// Obtenemos elemento expediente
		Session session = getSession();
		try
		{
			Query query =
				session.createQuery( "FROM ElementoExpediente AS ee where ee.tipoElemento = :tipoElemento and ee.codigoElemento = :codigoElemento" ).
				setParameter("tipoElemento", tipoElemento).
				setParameter("codigoElemento", codigoElemento);
			ElementoExpediente elementoExpediente = ( ElementoExpediente ) query.uniqueResult();
			return elementoExpediente;
		}
		catch (HibernateException he)
		{
			throw new EJBException(he);
	    }
		finally
		{
	        close(session);
	    }
	}

	private ElementoExpediente recuperarElementoExpedientePorIdPersistencia(String idPersistencia) throws Exception{
		// Obtenemos elemento expediente
		Session session = getSession();
		try
		{
			Query query =
				session.createQuery( "FROM ElementoExpediente AS ee where ee.identificadorPersistencia = :identificadorPersistencia" ).
				setParameter("identificadorPersistencia", idPersistencia);
			ElementoExpediente elementoExpediente = ( ElementoExpediente ) query.uniqueResult();
			return elementoExpediente;
		}
		catch (HibernateException he)
		{
			throw new EJBException(he);
	    }
		finally
		{
	        close(session);
	    }
	}


}

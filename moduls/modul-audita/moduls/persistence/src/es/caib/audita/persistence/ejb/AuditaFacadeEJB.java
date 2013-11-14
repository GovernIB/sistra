package es.caib.audita.persistence.ejb;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.audita.modelInterfaz.Evento;
import es.caib.audita.persistence.delegate.DelegateUtil;


/**
 * SessionBean para operaciones de otros módulos con Audita.
 * 
 * No interfiere en las tx de las aplicaciones invocantes
 *
 * @ejb.bean
 *  name="audita/persistence/AuditaFacade"
 *  jndi-name="es.caib.audita.persistence.AuditaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="NotSupported"
 */
public abstract class AuditaFacadeEJB implements SessionBean
{
	
	// TODO RAFA PASAR FUNCIONES DE LOG DE EVENTOS A ESTE EJB. HABRA QUE MODIFICAR LOG EN DEMAS MODULOS Y PORTAL
	
	private Log log = LogFactory.getLog( AuditaFacadeEJB.class );
	
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
	   //this.ctx = ctx;
    }
	
	/**
	 * Obtiene un map con la descripcion de los eventos (KEY=Id Evento / VALUE=Descripción)
	 * 
	 * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
	public Map obtenerDescripcionEventos( String idioma )
	{
		try
		{
			Map desc = new HashMap();
			
			// Obtenemos lista con descripcion de los eventos			
			List eventos = DelegateUtil.getAuditoriaDelegate().obtenerListaEventos(idioma);
			
			for(int i=0; i<eventos.size(); i++)
			{
				Map mResult = ( Map ) eventos.get(i);
				String tipo = (String) mResult.keySet().iterator().next();
				String nombre = (String) mResult.get(tipo);
				desc.put(tipo,nombre);
			}
			
			return desc;					
		}
		catch( Exception exc )
		{
			// Error: devolvemos nulo
			log.error("Excepción al recuperar lista de eventos: " + exc.getMessage(),exc);
			return null;
		}
	}
	
	/**
	 * 
	 * Obtiene auditoria de un tramite anónimo o un usuario autenticado entre dos fechas.
	 * Devuelve lista de eventos auditados.
	 * 
	 * @param fechaIni Fecha inicio
	 * @param fechaFin Fecha fin
	 * @param nivelAutenticacion Nivel autenticacion (A/C/U)
	 * @param autenticacion Identificador autenticación: Si A -> Clave persistencia / Si C/U-> Usuario seycon
	 * @return
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.helpdesk}"
	 */
	public List obtenerAuditoria(Date fechaIni,Date fechaFin,char nivelAutenticacion,String autenticacion)
	{
		try
		{
			// Obtenemos lista con descripcion de los eventos			
			List eventos = DelegateUtil.getAuditoriaDelegate().obtenerAuditoria(fechaIni,fechaFin,nivelAutenticacion,autenticacion);
			return eventos;				
		}
		catch( Exception exc )
		{
			// Error: devolvemos nulo
			log.error("Excepción al recuperar lista de eventos: " + exc.getMessage(),exc);
			return null;
		}
	}
	
	/**
	 * Realiza el log de un evento.
	 * 
	 * Devuelve código de evento. Si falla retorna nulo.
	 * 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
	 */
	public Long logEvento( Evento eventoAuditado ){
		try{
			return DelegateUtil.getLoggerEventoDelegate().logEvento(eventoAuditado);
		}catch(Exception exc){
			log.error("Excepción al insertar evento: " + exc.getMessage(),exc);
			return null;
		}		
	}
	
}

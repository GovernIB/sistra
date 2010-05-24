package es.caib.audita.persistence.ejb;

import java.sql.SQLException;
import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import es.caib.audita.modelInterfaz.Evento;

/**
 * SessionBean para dejar trazas de los distintos eventos del sistema de tramitación
 * con el objetivo de poder ser auditados posteriormente
 *
 * @ejb.bean
 *  name="audita/persistence/LoggerEventoFacade"
 *  jndi-name="es.caib.audita.persistence.LoggerEventoFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="RequiresNew"
 */

public abstract class LoggerEventoFacadeEJB extends QueryEJB
{
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.user}"
     */
	public void ejbCreate() throws CreateException 
	{
		super.ejbCreate();
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
	 */
	public Long logEvento( Evento eventoAuditado )
	{
		try
		{
				Long valorSecuencia = obtenerValorSecuencia( "sql.select.seq.aud" );
			
				Object params[] = 
				new Object[] 
				{ valorSecuencia, new Timestamp(System.currentTimeMillis()), eventoAuditado.getTipo(), eventoAuditado.getDescripcion(), 
				  eventoAuditado.getNivelAutenticacion(), eventoAuditado.getUsuarioSeycon(),
				  eventoAuditado.getNumeroDocumentoIdentificacion(), eventoAuditado.getNombre(), 
				  eventoAuditado.getIdioma(), eventoAuditado.getResultado(),
				  eventoAuditado.getModeloTramite(), new Integer( eventoAuditado.getVersionTramite() ), eventoAuditado.getIdPersistencia(), 
				  eventoAuditado.getClave() };
			this.update( "sql.insert.evento", params );
			return valorSecuencia;
		}
		catch ( SQLException exc )
		{
			log.error( exc.getMessage() );
			throw new EJBException( exc );
		}
	}
}

package es.caib.audita.persistence.ejb;

import java.sql.Timestamp;

import javax.ejb.CreateException;

import org.apache.commons.lang.StringUtils;

import es.caib.audita.modelInterfaz.Evento;

/**
 * SessionBean para dejar trazas de los distintos eventos del sistema de tramitación
 * con el objetivo de poder ser auditados posteriormente.
 * 
 * En caso de no poder dar de alta no genera ningun error.
 *
 * @ejb.bean
 *  name="audita/persistence/LoggerEventoFacade"
 *  jndi-name="es.caib.audita.persistence.LoggerEventoFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 * @ejb.transaction type="Required"
 */

public abstract class LoggerEventoFacadeEJB extends QueryEJB
{
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException 
	{
		super.ejbCreate();
	}
	
	/**
	 * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
	public Long logEvento( Evento eventoAuditado )
	{
		return logEventoImpl(eventoAuditado);
	}

	/**
	 * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     * @ejb.transaction type="RequiresNew"
	 */
	public Long logEventoTxNew( Evento eventoAuditado )
	{
		return logEventoImpl(eventoAuditado);
	}
	
	private Long logEventoImpl(Evento eventoAuditado) {
		try
		{
				Long valorSecuencia = obtenerValorSecuencia( "sql.select.seq.aud" );
			
				String descripcion = eventoAuditado.getDescripcion();
				descripcion = StringUtils.substring(descripcion, 0, 3000);
				
				Object params[] = 
				new Object[] 
				{ valorSecuencia, new Timestamp(System.currentTimeMillis()), eventoAuditado.getTipo(), descripcion, 
				  eventoAuditado.getNivelAutenticacion(), eventoAuditado.getUsuarioSeycon(),
				  eventoAuditado.getNumeroDocumentoIdentificacion(), eventoAuditado.getNombre(), 
				  eventoAuditado.getIdioma(), eventoAuditado.getResultado(),
				  eventoAuditado.getModeloTramite(), new Integer( eventoAuditado.getVersionTramite() ), eventoAuditado.getIdPersistencia(), 
				  eventoAuditado.getClave(), eventoAuditado.getProcedimiento() };
			this.update( "sql.insert.evento", params );
			return valorSecuencia;
		}
		catch ( Exception exc )
		{
			log.error( exc.getMessage() );
			//throw new EJBException( exc );
			return null;
		}
	}
}

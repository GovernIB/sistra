package es.caib.sistra.persistence.ejb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Session;
import net.sf.hibernate.impl.SessionFactoryImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SessionBean para generar n�meros de preregistro y env�o
 *
 * @ejb.bean
 *  name="sistra/persistence/GeneradorFacade"
 *  jndi-name="es.caib.sistra.persistence.GeneradorFacade"
 *  type="Stateless"
 *  view-type="remote"
 */
public abstract class GeneradorEJB extends HibernateEJB {
	
	protected static Log log = LogFactory.getLog(GeneradorEJB.class);
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name = "${role.todos}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();
	}
	
	
	/**
	 * Genera n�mero de preregistro con el formato "PRE"/X/NUMEROPREREGISTRO/A�O
	 * donde: 
     * 	- A�o: a�o actual
     * 	- X: Tipo Destino: Registro (R) / Bandeja (B)
     *  - Numero entrada: Secuencia por a�o
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.todos}"
     */
    public String generarNumeroPreregistro(char tipoDestino) {    	
    	Session session = getSession();
   	 	PreparedStatement pst=null;
        try {
	       	// Obtenemos secuencia del a�o actual
	       	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	       	String anyo = sdf.format(new Date());
	       	 
	        // Obtenemos secuencia del a�o actual
	       	//String sql = "SELECT STR_SEQP" + anyo.substring(2) + ".NEXTVAL FROM DUAL";
	       	String sql = ((SessionFactoryImpl)session.getSessionFactory()).getDialect().getSequenceNextValString("STR_SEQP" + anyo.substring(2));
	        pst = session.connection().prepareStatement(sql);
	       	pst.execute(); 
	       	ResultSet rs = pst.getResultSet();
	       	rs.next();
	       	int num = rs.getInt(1);
	
	       	// Devolvemos numero entrada
	       	return "PRE/" + tipoDestino + "/" + num + "/" + anyo;
        } catch (Exception he) {
        	throw new EJBException("No se ha podido generar numero de preregistro. Verifique que exista secuencia STR_SEQPyy (yy:A�o actual)",he);
        } finally {
       	 try{if(pst != null) pst.close();}catch(Exception ex){}
            close(session);
        }    	
    }
    
    /**
	 * Genera n�mero de env�o a Bandeja con el formato "ENV"/NUMEROENVIO/A�O
	 * donde: 
     * 	- A�o: a�o actual
     *  - Numero envio: Secuencia por a�o
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.todos}"
     */
    public String generarNumeroEnvio() {    	
    	Session session = getSession();
   	 PreparedStatement pst=null;
        try {
	       	// Obtenemos secuencia del a�o actual
	       	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	       	String anyo = sdf.format(new Date());
	       	 
	        // Obtenemos secuencia del a�o actual
	       	//String sql = "SELECT STR_SEQE" + anyo.substring(2) + ".NEXTVAL FROM DUAL";
	       	String sql = ((SessionFactoryImpl)session.getSessionFactory()).getDialect().getSequenceNextValString("STR_SEQE" + anyo.substring(2));
	       	pst = session.connection().prepareStatement(sql);
	       	pst.execute(); 
	       	ResultSet rs = pst.getResultSet();
	       	rs.next();
	       	int num = rs.getInt(1);
	
	       	// Devolvemos numero entrada
	       	return "ENV/" + num + "/" + anyo;
        } catch (Exception he) {
        	throw new EJBException("No se ha podido generar numero de env�o. Verifique que exista secuencia STR_SEQEyy (yy:A�o actual)",he);
        } finally {
       	 try{if(pst != null) pst.close();}catch(Exception ex){}
            close(session);
        }    	
    }

}

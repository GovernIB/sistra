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
 * SessionBean para generar números de preregistro y envío
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
	 * Genera número de preregistro con el formato "PRE"/X/NUMEROPREREGISTRO/AÑO
	 * donde: 
     * 	- Año: año actual
     * 	- X: Tipo Destino: Registro (R) / Bandeja (B)
     *  - Numero entrada: Secuencia por año
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.todos}"
     */
    public String generarNumeroPreregistro(char tipoDestino) {    	
    	Session session = getSession();
   	 	PreparedStatement pst=null;
        try {
	       	// Obtenemos secuencia del año actual
	       	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	       	String anyo = sdf.format(new Date());
	       	 
	        // Obtenemos secuencia del año actual
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
        	throw new EJBException("No se ha podido generar numero de preregistro. Verifique que exista secuencia STR_SEQPyy (yy:Año actual)",he);
        } finally {
       	 try{if(pst != null) pst.close();}catch(Exception ex){}
            close(session);
        }    	
    }
    
    /**
	 * Genera número de envío a Bandeja con el formato "ENV"/NUMEROENVIO/AÑO
	 * donde: 
     * 	- Año: año actual
     *  - Numero envio: Secuencia por año
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.todos}"
     */
    public String generarNumeroEnvio() {    	
    	Session session = getSession();
   	 PreparedStatement pst=null;
        try {
	       	// Obtenemos secuencia del año actual
	       	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	       	String anyo = sdf.format(new Date());
	       	 
	        // Obtenemos secuencia del año actual
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
        	throw new EJBException("No se ha podido generar numero de envío. Verifique que exista secuencia STR_SEQEyy (yy:Año actual)",he);
        } finally {
       	 try{if(pst != null) pst.close();}catch(Exception ex){}
            close(session);
        }    	
    }

}

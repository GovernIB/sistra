package es.caib.regtel.persistence.ejb;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;

/**
 * SessionBean que sirve acceder a datos personales para uso interno
 *
 * @ejb.bean
 *  name="regtel/persistence/ConsultaPADEJB"
 *  jndi-name="es.caib.regtel.persistence.ConsultaPAD"
 *  type="Stateless"
 *  view-type="local"
 *  
 *  @ejb.security-identity run-as = "${role.auto}"
 */
public abstract class ConsultaPADEJB implements SessionBean {
	
	protected static Log log = LogFactory.getLog(ConsultaPADEJB.class);
	protected SessionContext ctx;
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		log.info("ejbCreate: " + this.getClass());
	}
		
	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {
		this.ctx = ctx;
	}
	
	/**
	 *  Calcula datos persona a partir de un nif
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	 public PersonaPAD obtenerDatosPADporNif(String nif) {
		 try{
			 PadDelegate pad = DelegatePADUtil.getPadDelegate();
			 PersonaPAD p=  pad.obtenerDatosPersonaPADporNif(nif);  	
			 //	Si no es el usuario actualmente logeado solo dejamos accesible: nombre,apellidos,nif y seycon
			 return resetearDatosPersonaPAD(p);		 
		 }catch (Exception ex){
			 throw new EJBException(ex);
		 }
	}
	 
	

	/**
	  * Calcula datos persona a partir de usuario Seycon
	  *  @ejb.interface-method
      *  @ejb.permission role-name="${role.gestor}"
      *  @ejb.permission role-name="${role.auto}"
	  */	 
	 public PersonaPAD obtenerDatosPADporUsuarioSeycon(String usuarioSeycon){
		 try{
			 PadDelegate pad = DelegatePADUtil.getPadDelegate();
			 PersonaPAD p=  pad.obtenerDatosPersonaPADporUsuario(usuarioSeycon); 	
			 //	Si no es el usuario actualmente logeado solo dejamos accesible: nombre,apellidos,nif y seycon
			 return resetearDatosPersonaPAD(p);		 
		 }catch (Exception ex){
			 throw new EJBException(ex);
		 }
	 }
	 
	 /**
	  *  Si no es el usuario actualmente logeado solo dejamos accesible: nombre,apellidos,nif y seycon
	  * @param p
	  * @return
	  */
	 private PersonaPAD resetearDatosPersonaPAD(PersonaPAD p) {
		 
		 	if(p==null)
		 		return null;
			if (this.ctx.getCallerPrincipal().getName().equals(p.getUsuarioSeycon())) return p; 
			 
			PersonaPAD pr = new PersonaPAD();
			pr.setNif(p.getNif());
			pr.setUsuarioSeycon(p.getUsuarioSeycon());
			pr.setNombre(p.getNombre());
			pr.setApellido1(p.getApellido1());
			pr.setApellido2(p.getApellido2());
			return pr;
		}
}

package es.caib.bantel.persistence.intf;

/**
 * Home interface para EJB de integraci�n con BTE
 */
public interface BteConectorFacadeHome  extends javax.ejb.EJBHome
{
  public BteConectorFacade create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;
}

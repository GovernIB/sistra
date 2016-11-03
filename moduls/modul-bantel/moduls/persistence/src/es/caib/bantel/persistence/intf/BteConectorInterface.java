package es.caib.bantel.persistence.intf;

import es.caib.bantel.modelInterfaz.ExcepcionBTE;

public interface BteConectorInterface {
	/**
    * Realiza avisos de nuevas entradas
    */
   public void avisoEntradas(String [] entradas) throws ExcepcionBTE,java.rmi.RemoteException;
}

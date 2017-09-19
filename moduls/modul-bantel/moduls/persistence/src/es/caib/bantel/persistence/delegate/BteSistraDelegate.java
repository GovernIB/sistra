package es.caib.bantel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.bantel.modelInterfaz.ProcedimientoBTE;
import es.caib.bantel.modelInterfaz.ValoresFuenteDatosBTE;
import es.caib.bantel.persistence.intf.BteSistraFacade;
import es.caib.bantel.persistence.util.BteSistraFacadeUtil;
import es.caib.redose.modelInterfaz.ReferenciaRDS;

/**
 * Interfaz para operar con la BTE
 */
public class BteSistraDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ==        INTEGRACIÓN CON PLATAFORMA TRAMITACIÓN 	 === */
    /* ========================================================= */
	public String crearEntradaTelematica(ReferenciaRDS refAsiento,ReferenciaRDS refJustificante,Map refDocumentos) throws DelegateException {
        try {
            return getFacade().crearEntradaTelematica(refAsiento,refJustificante,refDocumentos);
        } catch (Exception e) {
        		
            throw new DelegateException(e);
        }
    }
    
    public String crearEntradaPreregistro(ReferenciaRDS refAsiento,ReferenciaRDS refJustificante,Map refDocumentos,String numeroRegistro,Date fechaRegistro) throws DelegateException {
        try {
            return getFacade().crearEntradaPreregistro(refAsiento,refJustificante,refDocumentos,numeroRegistro,fechaRegistro);
        } catch (Exception e) {
        		
            throw new DelegateException(e);
        }
    }
    
    public String crearEntradaPreenvioAutomatico(ReferenciaRDS refAsiento,ReferenciaRDS refJustificante,Map refDocumentos,String numeroRegistro,Date fechaRegistro) throws DelegateException {
        try {
            return getFacade().crearEntradaPreenvioAutomatico(refAsiento,refJustificante,refDocumentos,numeroRegistro,fechaRegistro);
        } catch (Exception e) {
        		
            throw new DelegateException(e);
        }
    }
    
    public String crearEntradaPreregistroIncorrecto(ReferenciaRDS refAsiento,ReferenciaRDS refJustificante,Map refDocumentos,String numeroRegistro,Date fechaRegistro) throws DelegateException {
        try {
            return getFacade().crearEntradaPreregistroIncorrecto(refAsiento,refJustificante,refDocumentos,numeroRegistro,fechaRegistro);
        } catch (Exception e) {
        		
            throw new DelegateException(e);
        }
    }
    
    public String confirmacionEntradaPreenvioAutomatico(String numPreregistro,String numregistro,Date fechaRegistro)throws DelegateException {
        try {
            return getFacade().confirmacionEntradaPreenvioAutomatico(numPreregistro,numregistro,fechaRegistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public ValoresFuenteDatosBTE consultaFuenteDatos(String idFuenteDatos, List parametros) throws DelegateException {
        try {
            return getFacade().consultaFuenteDatos(idFuenteDatos, parametros);
        } catch (Exception e) {
        	throw new DelegateException(e);
        }
    }
  
    public ProcedimientoBTE obtenerProcedimiento(String idProcedimiento) throws DelegateException {
        try {
            return getFacade().obtenerProcedimiento(idProcedimiento);
        } catch (Exception e) {
        	throw new DelegateException(e);
        }
    }
    
    public List obtenerProcedimientos() throws DelegateException {
        try {
            return getFacade().obtenerProcedimientos();
        } catch (Exception e) {
        	throw new DelegateException(e);
        }
    }
    
    public List obtenerEntidades() throws DelegateException {
        try {
            return getFacade().obtenerEntidades();
        } catch (Exception e) {
        	throw new DelegateException(e);
        }
    }
    
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private BteSistraFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return BteSistraFacadeUtil.getHome( ).create();
    }

    protected BteSistraDelegate() throws DelegateException {        
    }                  
}


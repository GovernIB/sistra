package es.caib.regtel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.regtel.model.ReferenciaRDSAsientoRegistral;
import es.caib.regtel.model.ResultadoRegistro;
import es.caib.regtel.model.ResultadoRegistroTelematico;
import es.caib.regtel.model.ws.AcuseRecibo;
import es.caib.regtel.model.ws.DatosRegistroEntrada;
import es.caib.regtel.model.ws.DatosRegistroSalida;
import es.caib.regtel.persistence.intf.RegistroTelematicoWsEJB;
import es.caib.regtel.persistence.util.RegistroEntradaHelper;
import es.caib.regtel.persistence.util.RegistroTelematicoWsEJBUtil;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.xml.registro.factoria.impl.Justificante;

/**
 * Interfaz registro telematico WS
 */
public class RegistroTelematicoWsDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	public ResultadoRegistroTelematico registroEntrada(DatosRegistroEntrada entrada)  throws DelegateException { 
        try {
            return getFacade().registroEntrada(entrada);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

	public ReferenciaRDSAsientoRegistral prepararRegistroEntrada(DatosRegistroEntrada entrada, int diasPersistencia) throws DelegateException { 
        try {
            return getFacade().prepararRegistroEntrada(entrada, diasPersistencia);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public ResultadoRegistroTelematico registroEntradaConFirma(ReferenciaRDSAsientoRegistral referenciaRDS, FirmaIntf firma) throws DelegateException { 
        try {
            return getFacade().registroEntradaConFirma(referenciaRDS,firma);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public ResultadoRegistroTelematico registroSalida(DatosRegistroSalida notificacion)  throws DelegateException { 
        try {
            return getFacade().registroSalida(notificacion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

	public AcuseRecibo obtenerAcuseRecibo(String numeroRegistro)  throws DelegateException { 
        try {
            return getFacade().obtenerAcuseRecibo(numeroRegistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private RegistroTelematicoWsEJB getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return RegistroTelematicoWsEJBUtil.getHome( ).create();
    }

    protected RegistroTelematicoWsDelegate() throws DelegateException {       
    }                  
}

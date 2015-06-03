package es.caib.regtel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.regtel.model.ResultadoRegistro;
import es.caib.regtel.model.ResultadoRegistroTelematico;
import es.caib.regtel.persistence.intf.RegistroTelematicoEJB;
import es.caib.regtel.persistence.util.RegistroTelematicoEJBUtil;
import es.caib.xml.registro.factoria.impl.Justificante;

/**
 * Interfaz registro telematico
 */
public class RegistroTelematicoDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	public ResultadoRegistroTelematico registroEntrada(ReferenciaRDS refAsiento, Map refDocs)  throws DelegateException { 
        try {
            return getFacade().registroEntrada(refAsiento,refDocs);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

	public ResultadoRegistroTelematico registroSalida(ReferenciaRDS refAsiento, Map refDocs)  throws DelegateException { 
        try {
            return getFacade().registroSalida(refAsiento,refDocs);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public Date obtenerAcuseRecibo(String numeroRegistro) throws DelegateException { 
        try {
            return getFacade().obtenerAcuseRecibo(numeroRegistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public void anularRegistroEntrada(ReferenciaRDS refJustificante) throws DelegateException { 
        try {
            getFacade().anularRegistroEntrada(refJustificante);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public ResultadoRegistro confirmarPreregistro(String oficina,String codigoProvincia,String codigoMunicipio,String descripcionMunicipio,Justificante justificantePreregistro,ReferenciaRDS refJustificante,ReferenciaRDS refAsiento,Map refAnexos) throws DelegateException { 
        try {
            return getFacade().confirmarPreregistro(oficina,codigoProvincia,codigoMunicipio,descripcionMunicipio,justificantePreregistro,refJustificante,refAsiento,refAnexos);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public List obtenerOficinasRegistro(char tipoRegistro) throws DelegateException { 
        try {
            return getFacade().obtenerOficinasRegistro(tipoRegistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public boolean existeOficinaRegistro(char tipoRegistro, String oficinaRegistro) throws DelegateException { 
        try {
            return getFacade().existeOficinaRegistro(tipoRegistro, oficinaRegistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public List obtenerOficinasRegistroUsuario(char tipoRegistro, String usuario) throws DelegateException { 
        try {
            return getFacade().obtenerOficinasRegistroUsuario(tipoRegistro, usuario);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public List obtenerTiposAsunto() throws DelegateException { 
        try {
            return getFacade().obtenerTiposAsunto();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public boolean existeTipoAsunto(String tipoAsunto) throws DelegateException { 
        try {
            return getFacade().existeTipoAsunto(tipoAsunto);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public List obtenerServiciosDestino() throws DelegateException { 
        try {
            return getFacade().obtenerServiciosDestino();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public boolean existeServicioDestino(String servicioDestino) throws DelegateException { 
        try {
            return getFacade().existeServicioDestino(servicioDestino);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }	
	
	public String obtenerDescripcionSelloOficina(char tipoRegistro, String oficina)  throws DelegateException
	{
		try
		{			
			return getFacade().obtenerDescripcionSelloOficina(tipoRegistro, oficina);				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 }
	
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private RegistroTelematicoEJB getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return RegistroTelematicoEJBUtil.getHome( ).create();
    }

    protected RegistroTelematicoDelegate() throws DelegateException {       
    }

	                
}

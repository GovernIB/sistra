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
	
	public Date obtenerAcuseRecibo(String entidad, String numeroRegistro) throws DelegateException { 
        try {
            return getFacade().obtenerAcuseRecibo(entidad, numeroRegistro);
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
	
	public ResultadoRegistro confirmarPreregistro(String entidad, String oficina,String codigoProvincia,String codigoMunicipio,String descripcionMunicipio,Justificante justificantePreregistro,ReferenciaRDS refJustificante,ReferenciaRDS refAsiento,Map refAnexos) throws DelegateException { 
        try {
            return getFacade().confirmarPreregistro(entidad, oficina,codigoProvincia,codigoMunicipio,descripcionMunicipio,justificantePreregistro,refJustificante,refAsiento,refAnexos);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public List obtenerOficinasRegistro(String entidad, char tipoRegistro) throws DelegateException { 
        try {
            return getFacade().obtenerOficinasRegistro(entidad, tipoRegistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public boolean existeOficinaRegistro( char tipoRegistro, String entidad, String oficinaRegistro) throws DelegateException { 
        try {
            return getFacade().existeOficinaRegistro(tipoRegistro, entidad, oficinaRegistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public List obtenerOficinasRegistroUsuario(String entidad, char tipoRegistro, String usuario) throws DelegateException { 
        try {
            return getFacade().obtenerOficinasRegistroUsuario(entidad, tipoRegistro, usuario);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public List obtenerTiposAsunto(String entidad) throws DelegateException { 
        try {
            return getFacade().obtenerTiposAsunto(entidad);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public boolean existeTipoAsunto(String entidad, String tipoAsunto) throws DelegateException { 
        try {
            return getFacade().existeTipoAsunto(entidad, tipoAsunto);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public List obtenerServiciosDestino(String entidad) throws DelegateException { 
        try {
            return getFacade().obtenerServiciosDestino(entidad);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public boolean existeServicioDestino(String entidad, String servicioDestino) throws DelegateException { 
        try {
            return getFacade().existeServicioDestino(entidad, servicioDestino);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }	
	
	public String obtenerDescripcionSelloOficina(char tipoRegistro, String entidad, String oficina)  throws DelegateException
	{
		try
		{			
			return getFacade().obtenerDescripcionSelloOficina(tipoRegistro, entidad,  oficina);				
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

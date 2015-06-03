package es.caib.regtel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.regtel.model.ResultadoRegistro;
import es.caib.regtel.persistence.intf.RegistroOrganismoEJBLocal;
import es.caib.regtel.persistence.util.RegistroOrganismoEJBUtil;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.Justificante;

public class RegistroOrganismoDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	public ResultadoRegistro registroEntrada(AsientoRegistral asiento,ReferenciaRDS refAsiento,Map refAnexos) throws DelegateException
	{
		try
		{			
			return getFacade().registroEntrada( asiento, refAsiento, refAnexos);				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 }

	public ResultadoRegistro registroSalida(AsientoRegistral asiento,ReferenciaRDS refAsiento,Map refAnexos) throws DelegateException
	{
		try
		{			
			return getFacade().registroSalida( asiento, refAsiento, refAnexos);				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 }
	
	public ResultadoRegistro confirmarPreregistro(String oficina,String codigoProvincia,String codigoMunicipio,String descripcionMunicipio,Justificante justificantePreregistro,ReferenciaRDS refJustificante,ReferenciaRDS refAsiento,Map refAnexos) throws DelegateException
	{
		try
		{			
			return getFacade().confirmarPreregistro(oficina, codigoProvincia, codigoMunicipio, descripcionMunicipio, justificantePreregistro,refJustificante, refAsiento, refAnexos);				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 }
	
	 public void anularRegistroEntrada(String numeroRegistro, Date fechaRegistro) throws DelegateException
		{
			try
			{			
				getFacade().anularRegistroEntrada(numeroRegistro, fechaRegistro);				
			} catch (Exception e) {
		        throw new DelegateException(e);
		    }	 	 
		 }
	 
	 public void anularRegistroSalida(String numeroRegistro, Date fechaRegistro) throws DelegateException
		{
			try
			{			
				getFacade().anularRegistroSalida(numeroRegistro, fechaRegistro);				
			} catch (Exception e) {
		        throw new DelegateException(e);
		    }	 	 
		 }
	
	public List obtenerOficinasRegistro(char tipoRegistro) throws DelegateException
	{
		try
		{			
			return getFacade().obtenerOficinasRegistro(tipoRegistro);				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 }
	
	public List obtenerOficinasRegistroUsuario(char tipoRegistro, String usuario) throws DelegateException
	{
		try
		{			
			return getFacade().obtenerOficinasRegistroUsuario(tipoRegistro, usuario);				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 } 
	
	public List obtenerTiposAsunto() throws DelegateException
	{
		try
		{			
			return getFacade().obtenerTiposAsunto();				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 }
	
	public List obtenerServiciosDestino() throws DelegateException
	{
		try
		{			
			return getFacade().obtenerServiciosDestino();				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 }
	
	public boolean existeOficinaRegistro(char tipoRegistro, String oficinaRegistro) throws DelegateException 
	{ 
		try
		{			
			return getFacade().existeOficinaRegistro(tipoRegistro, oficinaRegistro);				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 } 
	
	public boolean existeTipoAsunto(String tipoAsunto) throws DelegateException 
	{
		try
		{			
			return getFacade().existeTipoAsunto(tipoAsunto);				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 }
	
	public boolean existeServicioDestino(String servicioDestino) throws DelegateException
	{
		try
		{			
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
    private RegistroOrganismoEJBLocal getFacade() throws NamingException,RemoteException,CreateException {      	    	
    	return RegistroOrganismoEJBUtil.getLocalHome().create();
    }

    protected RegistroOrganismoDelegate() throws DelegateException {        
    }                  
}


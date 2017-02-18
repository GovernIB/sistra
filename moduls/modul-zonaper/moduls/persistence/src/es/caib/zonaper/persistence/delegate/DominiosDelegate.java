package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.persistence.intf.DominiosFacadeLocal;
import es.caib.zonaper.persistence.util.DominiosFacadeUtil;

/**
 * Interfaz para obtener dominios
 */
public class DominiosDelegate implements StatelessDelegate {

    public  List listarProvincias() throws DelegateException
	{
		try
		{
			return getFacade().listarProvincias( );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
    
    public  List listarLocalidadesProvincia(String codProv) throws DelegateException
	{
		try
		{
			return getFacade().listarLocalidadesProvincia(codProv );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
    
    public String obtenerDescripcionMunicipio(String codProvincia, String codMunicipio) throws DelegateException
	{
		try
		{
			return getFacade().obtenerDescripcionMunicipio(codProvincia, codMunicipio );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
    
    public String obtenerDescripcionPais(String codPais) throws DelegateException
	{
		try
		{
			return getFacade().obtenerDescripcionPais(codPais );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
    
    public String obtenerDescripcionUA(String codUA) throws DelegateException
	{
		try
		{
			return getFacade().obtenerDescripcionUA(codUA );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}    
    
    public List obtenerOficinas(String entidad, char tipoRegistro, String usuario) throws DelegateException
	{
		try
		{
			return getFacade().obtenerOficinas(entidad, tipoRegistro, usuario );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}    
    
    public List obtenerTiposAsunto(String entidad) throws DelegateException
	{
		try
		{
			return getFacade().obtenerTiposAsunto(entidad);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}    
	
    public String obtenerRaizUnidadesOrganicas() throws DelegateException
	{
		try
		{
			return getFacade().obtenerRaizUnidadesOrganicas();
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
    
    public String obtenerDescripcionSelloOficina(char tipoRegistro,String entidad,  String oficina)  throws DelegateException
	{
		try
		{			
			return getFacade().obtenerDescripcionSelloOficina(tipoRegistro, entidad, oficina);				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 }
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private DominiosFacadeLocal getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return DominiosFacadeUtil.getLocalHome().create();
    }

    protected DominiosDelegate() throws DelegateException {       
    }                  
}


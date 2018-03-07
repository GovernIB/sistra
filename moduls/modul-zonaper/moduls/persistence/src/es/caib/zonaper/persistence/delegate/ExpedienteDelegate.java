package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.Entrada;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.Page;
import es.caib.zonaper.modelInterfaz.ConfiguracionAvisosExpedientePAD;
import es.caib.zonaper.modelInterfaz.ExcepcionPAD;
import es.caib.zonaper.modelInterfaz.FiltroBusquedaExpedientePAD;
import es.caib.zonaper.persistence.intf.ExpedienteFacade;
import es.caib.zonaper.persistence.util.ExpedienteFacadeUtil;

public class ExpedienteDelegate implements StatelessDelegate
{
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public Expediente obtenerExpedienteAutenticado( Long id ) throws DelegateException
	{
		try
		{
			return getFacade().obtenerExpedienteAutenticado( id );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public Expediente obtenerExpedienteAnonimo(Long codigoExpediente, String claveAcceso ) throws DelegateException
	{
		try
		{
			return getFacade().obtenerExpedienteAnonimo( codigoExpediente, claveAcceso );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	
	public boolean existeExpedienteReal( long unidadAdministrativa, String identificadorExpediente) throws DelegateException
	{
		try
		{
			return getFacade().existeExpedienteReal( unidadAdministrativa, identificadorExpediente);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public Expediente obtenerExpedienteAuto( long unidadAdministrativa, String identificadorExpediente) throws DelegateException
	{
		try
		{
			return getFacade().obtenerExpedienteAuto( unidadAdministrativa, identificadorExpediente );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	
	public Expediente obtenerExpedienteReal( long unidadAdministrativa, String identificadorExpediente, String claveExpediente) throws DelegateException
	{
		try
		{
			return getFacade().obtenerExpedienteReal( unidadAdministrativa, identificadorExpediente, claveExpediente );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public Expediente obtenerExpedienteVirtual( Long codigoExpedienteVirtual ) throws DelegateException
	{
		try
		{
			return getFacade().obtenerExpedienteVirtual( codigoExpedienteVirtual );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public Long grabarExpedienteReal( Expediente expediente ) throws DelegateException
	{
		try
		{
			return getFacade().grabarExpedienteReal( expediente );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}	
	
	public Long grabarExpedienteVirtual( Expediente expediente ) throws DelegateException
	{
		try
		{
			return getFacade().grabarExpedienteVirtual( expediente );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}	
		
	
	public int obtenerNumeroExpedientesPendientesUsuario() throws DelegateException
	{
		try
		{
			return getFacade().obtenerNumeroExpedientesPendientesUsuario();
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}	
	
	public Page busquedaPaginadaExpedientesReales(
			FiltroBusquedaExpedientePAD filtro, int numPagina, int longPagina) throws DelegateException
	{
		try
		{
			return getFacade().busquedaPaginadaExpedientesReales(filtro, numPagina, longPagina);	
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}	
	
	public void modificarAvisosExpedienteReal( long unidadAdministrativa, String identificadorExpediente, String claveExpediente, ConfiguracionAvisosExpedientePAD configuracionAvisos) throws DelegateException
	{
		try
		{
			getFacade().modificarAvisosExpedienteReal( unidadAdministrativa, identificadorExpediente, claveExpediente, configuracionAvisos );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public void borrarExpedienteReal(long unidadAdministrativa,
			String identificadorExpediente)  throws DelegateException {
		try
		{
			getFacade().borrarExpedienteReal( unidadAdministrativa, identificadorExpediente);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
		
	}

	public void borrarExpedienteVirtual(Long codigoExpediente)  throws DelegateException {
		try
		{
			getFacade().borrarExpedienteVirtual( codigoExpediente);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
		
	}
	
	public void convertirExpedienteVirtualAReal(Expediente expeVirtual,
			Expediente expeReal)  throws DelegateException {
		try
		{
			getFacade().convertirExpedienteVirtualAReal( expeVirtual, expeReal);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
		
	}
	
	public Page busquedaPaginadaExpedientes( int pagina, int longitudPagina, List filtroExpe )  throws DelegateException
	{
		try
		{
			return getFacade().busquedaPaginadaExpedientes(pagina, longitudPagina, filtroExpe );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public Page busquedaPaginadaExpedientesEntidadDelegada(int pagina, int longitudPagina, String nifEntidad, List filtroExpe )  throws DelegateException
	{
		try
		{
			return getFacade().busquedaPaginadaExpedientesEntidadDelegada(pagina, longitudPagina, nifEntidad, filtroExpe);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public void actualizaEstadoExpedienteAuto(Long id, String estado, Date fecha) throws DelegateException
	{
		try
		{
			getFacade().actualizaEstadoExpedienteAuto(id, estado, fecha);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	
	public Expediente obtenerExpedienteAuto( Long codigoExpediente ) throws DelegateException
	{
		try
		{
			return getFacade().obtenerExpedienteAuto( codigoExpediente ) ;
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public boolean verificarAccesoExpedienteAnonimo(Long id, String claveAcceso) throws DelegateException
	{
		try
		{
			return getFacade().verificarAccesoExpedienteAnonimo( id, claveAcceso ) ;
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}

		
	public boolean verificarAccesoExpedienteAutenticado(Long id) throws DelegateException
	{
		try
		{
			return getFacade().verificarAccesoExpedienteAutenticado( id ) ;
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}

		
	public String obtenerEntidadExpediente( Long codigoExpediente ) throws DelegateException
	{
		try
		{
			return getFacade().obtenerEntidadExpediente( codigoExpediente ) ;
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public List obtenerProcedimientosId(Date fecha) throws DelegateException
	{
		try
		{
			return getFacade().obtenerProcedimientosId(fecha) ;
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private ExpedienteFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	return ExpedienteFacadeUtil.getHome( ).create();
    }
    
    protected ExpedienteDelegate() throws DelegateException {       
    }
	  
}

package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.model.GrupoUsuario;
import es.caib.sistra.model.GrupoUsuarioId;
import es.caib.sistra.model.Grupos;
import es.caib.sistra.model.RolUsuarioTramite;
import es.caib.sistra.model.RolUsuarioTramiteId;
import es.caib.sistra.persistence.intf.GruposFacade;
import es.caib.sistra.persistence.util.GruposFacadeUtil;

/**
 * Business delegate pera consultar grupos.
 */
public class GruposDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public List listarGrupos() throws DelegateException {
        try {
            return getFacade().listarGrupos();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    public Grupos obtenerGrupo(String codigoGrupo) throws DelegateException {
		 try {
	            return getFacade().obtenerGrupo(codigoGrupo);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
	}
    public void guardarGrupo(Grupos grupo) throws DelegateException {
    	 try {
	            getFacade().guardarGrupo(grupo);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
		
	}
    public void modificarGrupo(Grupos grupo) throws DelegateException {
    	 try {
	            getFacade().modificarGrupo(grupo);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
		
	}
    public void eliminarGrupo(Grupos grupo) throws DelegateException {
   	 try {
	            getFacade().eliminarGrupo(grupo);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
		
	}
    public List listarGruposByTramite(Long id) throws DelegateException {
    	try {
            return getFacade().listarGruposByTramite(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
	}
    public List listarUsuariosByTramite(Long id) throws DelegateException {
    	try {
            return getFacade().listarUsuariosByTramite(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
	}

    public List listarGruposByNotTramite(Long id) throws DelegateException {
    	try {
            return getFacade().listarGruposByNotTramite(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
	}
    
    public boolean existenGrupos() throws DelegateException {
    	try {
            return getFacade().existenGrupos();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
	}
    
    public void asociarGrupo(String grupoAsociar, String tramite) throws DelegateException {
    	try {
            getFacade().asociarGrupo(grupoAsociar,tramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
		
	}
    
    public void desAsociarGrupo(String grupoAsociar, String tramite) throws DelegateException {
    	try {
            getFacade().desAsociarGrupo(grupoAsociar,tramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
		
	}
    
    public List obtenerUsuariosByGrupo(String codigo) throws DelegateException {
    	try {
            return getFacade().obtenerUsuariosByGrupo(codigo);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
	}
    
    public void asociarUsuario(GrupoUsuario grpUser) throws DelegateException {
    	try {
            getFacade().asociarUsuario(grpUser);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
		
	}
    public GrupoUsuario obtenerUsuarioGrupo(GrupoUsuarioId id) throws DelegateException {
    	try {
           return getFacade().obtenerUsuarioGrupo(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
	}
    public void eliminarUsuarioGrupo(GrupoUsuario grpUser) throws DelegateException {
    	try {
           getFacade().eliminarUsuarioGrupo(grpUser);
         } catch (Exception e) {
             throw new DelegateException(e);
         }
	}

    public boolean existeUsuariosByGrupo(String codigo) throws DelegateException {
    	try {
            return getFacade().existeUsuariosByGrupo(codigo);
          } catch (Exception e) {
              throw new DelegateException(e);
          }
	}
    public void asociarUsuarioTramite(RolUsuarioTramite userTra) throws DelegateException {
    	try {
             getFacade().asociarUsuarioTramite(userTra);
          } catch (Exception e) {
              throw new DelegateException(e);
          }
		
	}
    
    public RolUsuarioTramite obtenerUsuarioTramite(RolUsuarioTramiteId id) throws DelegateException {
    	try {
            return getFacade().obtenerUsuarioTramite(id);
         } catch (Exception e) {
             throw new DelegateException(e);
         }
	}
    public void eliminarUsuarioTramite(RolUsuarioTramite userTra) throws DelegateException {
    	try {
            getFacade().eliminarUsuarioTramite(userTra);
         } catch (Exception e) {
             throw new DelegateException(e);
         }
		
	}
    
    public boolean existeUsuarioByTramite(String usuario, Long tramite) throws DelegateException{
    	try {
            return getFacade().existeUsuarioByTramite(usuario,tramite);
         } catch (Exception e) {
             throw new DelegateException(e);
         }
	}
    public boolean existeUsuarioByGruposTramite(String usuario, Long tramite) throws DelegateException{
    	try {
            return getFacade().existeUsuarioByGruposTramite(usuario,tramite);
         } catch (Exception e) {
             throw new DelegateException(e);
         }
		
	}
   
    public void borrarTramiteGrupos(Long tramite) throws DelegateException {
    	try {
            getFacade().borrarTramiteGrupos(tramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
		
	}
    
    public void borrarTramiteUsuarios(Long tramite) throws DelegateException {
    	try {
            getFacade().borrarTramiteUsuarios(tramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
		
	}
     
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private GruposFacade getFacade() throws NamingException,CreateException,RemoteException {
        return GruposFacadeUtil.getHome().create();
    }

    protected GruposDelegate() throws DelegateException {        
    }
	
	
	
	
	
	
	
	
	
	
	
}

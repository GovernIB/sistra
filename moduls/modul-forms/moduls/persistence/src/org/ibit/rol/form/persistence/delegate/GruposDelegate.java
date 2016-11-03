package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.GrupoUsuario;
import org.ibit.rol.form.model.GrupoUsuarioId;
import org.ibit.rol.form.model.Grupos;
import org.ibit.rol.form.model.RolUsuarioFormulario;
import org.ibit.rol.form.model.RolUsuarioFormularioId;
import org.ibit.rol.form.persistence.intf.GruposFacade;
import org.ibit.rol.form.persistence.util.GruposFacadeUtil;

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
    public List listarGruposByForm(Long id) throws DelegateException {
    	try {
            return getFacade().listarGruposByForm(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
	}
    public List listarUsuariosByForm(Long id) throws DelegateException {
    	try {
            return getFacade().listarUsuariosByForm(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
	}

    public List listarGruposByNotForm(Long id) throws DelegateException {
    	try {
            return getFacade().listarGruposByNotForm(id);
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
    
    public void asociarGrupo(String grupoAsociar, String formulario) throws DelegateException {
    	try {
            getFacade().asociarGrupo(grupoAsociar,formulario);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
		
	}
    
    public void desAsociarGrupo(String grupoAsociar, String formulario) throws DelegateException {
    	try {
            getFacade().desAsociarGrupo(grupoAsociar,formulario);
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
    public void asociarUsuarioFormulario(RolUsuarioFormulario userForm) throws DelegateException {
    	try {
             getFacade().asociarUsuarioFormulario(userForm);
          } catch (Exception e) {
              throw new DelegateException(e);
          }
		
	}
    
    public RolUsuarioFormulario obtenerUsuarioForm(RolUsuarioFormularioId id) throws DelegateException {
    	try {
            return getFacade().obtenerUsuarioForm(id);
         } catch (Exception e) {
             throw new DelegateException(e);
         }
	}
    public void eliminarUsuarioFormulario(RolUsuarioFormulario userForm) throws DelegateException {
    	try {
            getFacade().eliminarUsuarioFormulario(userForm);
         } catch (Exception e) {
             throw new DelegateException(e);
         }
		
	}
        
    public boolean existeUsuarioByForm(String usuario, Long formulario) throws DelegateException {
    	try {
    		return getFacade().existeUsuarioByForm(usuario, formulario);
         } catch (Exception e) {
             throw new DelegateException(e);
         }
	}
	public boolean existeUsuarioByGruposForm(String usuario, Long formulario) throws DelegateException {
		try {
			return getFacade().existeUsuarioByGruposForm(usuario, formulario);
         } catch (Exception e) {
             throw new DelegateException(e);
         }
	}
    
	public void borrarFormularioGrupos(Long formulario) throws DelegateException {
		try {
			getFacade().borrarFormularioGrupos(formulario);
	    } catch (Exception e) {
	        throw new DelegateException(e);
	    }
			
	}
	    
	public void borrarFormularioUsuarios(Long formulario) throws DelegateException {
		try {
	        getFacade().borrarFormularioUsuarios(formulario);
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

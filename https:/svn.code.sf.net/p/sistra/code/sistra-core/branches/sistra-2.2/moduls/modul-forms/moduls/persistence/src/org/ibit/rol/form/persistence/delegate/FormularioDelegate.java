package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.Salida;
import org.ibit.rol.form.persistence.intf.FormularioFacade;
import org.ibit.rol.form.persistence.util.FormularioFacadeUtil;

/**
 * Business delegate para operar con formularios.
 */
public class FormularioDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long gravarFormulario(Formulario formulario) throws DelegateException {
        try {
            return getFacade().gravarFormulario(formulario);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Long gravarNuevoFormulario(Long id) throws DelegateException {
        try {
            return getFacade().gravarNuevoFormulario(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

     public Long anyadirSalida(Long idFormulario, Long idPuntoSalida) throws DelegateException {
        try {
            return getFacade().anyadirSalida(idFormulario, idPuntoSalida);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void eliminarSalida(Long idSalida) throws DelegateException {
        try {
            getFacade().eliminarSalida(idSalida);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

     public List listarSalidasFormulario(Long idFormulario) throws DelegateException {
        try {
            return getFacade().listarSalidasFormulario(idFormulario);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Salida obtenerSalida(Long idSalida) throws DelegateException {
        try {
            return getFacade().obtenerSalida(idSalida);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Formulario obtenerFormulario(Long idFormulario) throws DelegateException {
        try {
            return getFacade().obtenerFormulario(idFormulario);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public boolean esConfidencial(String modelo, int version) throws DelegateException {
        try {
            return getFacade().esConfidencial(modelo, version);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public boolean tieneAcceso(String modelo, int version) throws DelegateException {
        try {
            return getFacade().tieneAcceso(modelo, version);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Formulario obtenerFormularioCompleto(Long idFormulario) throws DelegateException {
        try {
             return getFacade().obtenerFormularioCompleto(idFormulario);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Formulario obtenerFormulario(String modelo, int version) throws DelegateException {
        try {
            return getFacade().obtenerFormulario(modelo, version);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarFormularios() throws DelegateException {
        try {
            return getFacade().listarFormularios();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List versionesFormulario(Long id) throws DelegateException {
        try {
            return getFacade().versionesFormulario(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Formulario ultimaVersionFormulario(String modelo) throws DelegateException {
        try {
            return getFacade().ultimaVersionFormulario(modelo);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void ultimaVersionFalse(Long id) throws DelegateException {
        try {
            getFacade().ultimaVersionFalse(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarUltimasVersionesFormularios() throws DelegateException {
        try {
            return getFacade().listarUltimasVersionesFormularios();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarFormulario(Long id) throws DelegateException {
        try {
            getFacade().borrarFormulario(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void bloquearFormulario(Long formulario_id) throws DelegateException {
        try {
            getFacade().bloquearFormulario(formulario_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }


    public void desbloquearFormulario(Long formulario_id) throws DelegateException {
        try {
            getFacade().desbloquearFormulario(formulario_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarLogotipo1(Long id_formulario, Long id_archivo) throws DelegateException {
        try {
            getFacade().borrarLogotipo1(id_formulario, id_archivo);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarLogotipo2(Long id_formulario, Long id_archivo) throws DelegateException {
        try {
            getFacade().borrarLogotipo2(id_formulario, id_archivo);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarPlantilla(Long id_formulario, String lang) throws DelegateException {
        try {
            getFacade().borrarPlantilla(id_formulario, lang);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private FormularioFacade getFacade() throws NamingException,CreateException,RemoteException {
        return FormularioFacadeUtil.getHome().create();
    }

    protected FormularioDelegate() throws DelegateException {        
    }

}

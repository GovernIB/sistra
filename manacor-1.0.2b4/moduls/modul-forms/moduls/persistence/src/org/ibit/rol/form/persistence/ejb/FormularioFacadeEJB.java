package org.ibit.rol.form.persistence.ejb;

import net.sf.hibernate.*;

import org.ibit.rol.form.model.*;
import org.ibit.rol.form.persistence.util.ClassUtils;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * SessionBean para mantener y consultar formularios.
 *
 * @ejb.bean
 *  name="form/persistence/FormularioFacade"
 *  jndi-name="org.ibit.rol.form.persistence.FormularioFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class FormularioFacadeEJB extends HibernateEJB {

    /**
     * @ejb.create-method
     * @ejb.permission unchecked="true"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public Long gravarFormulario(Formulario formulario) {
        Session session = getSession();
        try {
        	
        	// INDRA: Si es nuevo automaticamente lo blqamos xa el usuario
        	if (formulario.getId() == null){
        		formulario.setBloqueado(true);
        		formulario.setBloqueadoPor(this.ctx.getCallerPrincipal().getName());
        	}
        	
            session.saveOrUpdate(formulario);
            return formulario.getId();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Copia un formulari.
     * @param id
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public Long gravarNuevoFormulario(Long id) {
        Session session = getSession();
        try {
            Formulario formulario = (Formulario) session.load(Formulario.class, id);
            if (!formulario.isLastVersion()) {
                throw new EJBException("El formulario " + id
                        + " no és la ultima versión del modelo "
                        + formulario.getModelo());
            }

            /*
            Query query = session.createQuery("SELECT MAX(version) FROM Formulario AS f WHERE f.modelo = :modelo ORDER BY f.version");
            query.setParameter("modelo", formulario.getModelo());
            query.setCacheable(true);
            List result = query.list();
            int llista = result.size();
            if (result.isEmpty()) {
                return null;
            }
            Formulario form = (Formulario) result.get(llista - 1);
            */

            int maxVersion = formulario.getVersion();
            int newVersion = maxVersion + 1;

            Formulario newFormulario = (Formulario) ClassUtils.getInstance(formulario.getClass());
            newFormulario.setVersion(newVersion);
            newFormulario.setModelo(formulario.getModelo());
            newFormulario.setLastVersion(true);
            newFormulario.setModoFuncionamiento(formulario.getModoFuncionamiento());
            session.save(newFormulario);
            session.flush();
                                    
            session.saveOrUpdateCopy(formulario, newFormulario.getId());
            newFormulario.setVersion(newVersion);
            newFormulario.setLastVersion(true);
            session.flush();

            //Duplica el Logotipo1
            Archivo logotipo1 = formulario.getLogotipo1();
            if(logotipo1 != null){
                Archivo newLogotipo1 = duplicarArchivo(logotipo1);
                newFormulario.setLogotipo1(newLogotipo1);
                session.flush();
            }

            //Duplica el Logotipo2
            Archivo logotipo2 = formulario.getLogotipo2();
            if(logotipo2 != null){
                Archivo newLogotipo2 = duplicarArchivo(logotipo2);
                newFormulario.setLogotipo2(newLogotipo2);
                session.flush();
            }

            //Duplica el dtd
            Archivo dtd = formulario.getDtd();
            if(dtd != null){
                Archivo newDtd = duplicarArchivo(dtd);
                newFormulario.setDtd(newDtd);
                session.flush();
            }

            //Duplica les plantilles
            for (Iterator iterTrafor = formulario.getLangs().iterator(); iterTrafor.hasNext();) {
                String lang = (String) iterTrafor.next();
                TraFormulario traForm = (TraFormulario) formulario.getTraduccion(lang);
                TraFormulario newTraForm = (TraFormulario) newFormulario.getTraduccion(lang);
                if (traForm.getPlantilla() != null) {
                    Archivo archivo = duplicarArchivo(traForm.getPlantilla());
                    newTraForm.setPlantilla(archivo);
                }
            }
            session.flush();


            //Duplica las Salidas.
            newFormulario.getSalidas().clear();
            List salidas = formulario.getSalidas();
            for (int i = 0; i < salidas.size(); i++) {
                Salida salida = (Salida) salidas.get(i);
                Salida newSalida = (Salida) ClassUtils.getInstance(salida.getClass());
                newSalida.setFormulario(newFormulario);
                newSalida.setPunto(salida.getPunto());
                session.save(newSalida);
                session.flush();

                session.saveOrUpdateCopy(salida, newSalida.getId());
                newFormulario.addSalida(newSalida);
                session.flush();

                //Duplica las Propiedades.
                newSalida.getPropiedades().clear();
                Set propiedades = salida.getPropiedades();
                for (Iterator iterator = propiedades.iterator(); iterator.hasNext();) {
                    PropiedadSalida propiedad = (PropiedadSalida) iterator.next();
                    PropiedadSalida newPropiedad = (PropiedadSalida) ClassUtils.getInstance(propiedad.getClass());
                    newPropiedad.setNombre("New propiedad");
                    newPropiedad.setSalida(newSalida);
                    session.save(newPropiedad);
                    session.flush();

                    session.saveOrUpdateCopy(propiedad, newPropiedad.getId());
                    newSalida.addPropiedad(newPropiedad);
                    session.flush();

                    if (propiedad.getPlantilla() != null) {
                        Archivo newPlantilla = duplicarArchivo(propiedad.getPlantilla());
                        newPropiedad.setPlantilla(newPlantilla);
                    }
                }
            }


            //Duplica las Pantallas
            newFormulario.getPantallas().clear();
            List pantallas = formulario.getPantallas();
            for (int i = 0; i < pantallas.size(); i++) {
                Pantalla pantalla = (Pantalla) pantallas.get(i);
                Pantalla newPantalla = (Pantalla) ClassUtils.getInstance(pantalla.getClass());
                newPantalla.setNombre("New pantalla");
                newPantalla.setFormulario(newFormulario);
                session.save(newPantalla);
                session.flush();

                session.saveOrUpdateCopy(pantalla, newPantalla.getId());
                newFormulario.addPantalla(newPantalla);
                session.flush();

                //Duplica las Ayudas de Pantalla.
                newPantalla.getAyudas().clear();
                Set ayudas = pantalla.getAyudas();
                for (Iterator iterator = ayudas.iterator(); iterator.hasNext();) {
                    AyudaPantalla ayuda = (AyudaPantalla) iterator.next();
                    AyudaPantalla newAyuda = (AyudaPantalla) ClassUtils.getInstance(ayuda.getClass());
                    newAyuda.setPantalla(newPantalla);
                    newAyuda.setPerfil(ayuda.getPerfil());
                    session.save(newAyuda);
                    session.flush();

                    session.saveOrUpdateCopy(ayuda, newAyuda.getId());
                    newPantalla.addAyuda(newAyuda);
                    session.flush();
                }

                //Duplica los Componentes.
                newPantalla.getComponentes().clear();
                List componentes = pantalla.getComponentes();
                for (int j = 0; j < componentes.size(); j++) {
                    Componente componente = (Componente) componentes.get(j);
                    Componente newComponente = (Componente) ClassUtils.getInstance(componente.getClass());
                    newComponente.setNombreLogico("New component");
                    newComponente.setPantalla(newPantalla);
                    session.save(newComponente);
                    session.flush();

                    session.saveOrUpdateCopy(componente, newComponente.getId());
                    newPantalla.addComponente(newComponente);
                    session.flush();

                    if (componente instanceof Campo) {
                        Campo campo = (Campo) componente;
                        Campo newCampo = (Campo) newComponente;

                        newCampo.getValoresPosibles().clear();
                        List valoresPosibles = campo.getValoresPosibles();
                        for (Iterator iterator = valoresPosibles.iterator(); iterator.hasNext();) {
                            ValorPosible vp = (ValorPosible) iterator.next();
                            ValorPosible newVp = (ValorPosible) ClassUtils.getInstance(vp.getClass());
                            newVp.setCampo(newCampo);
                            session.save(newVp);
                            session.flush();

                            session.saveOrUpdateCopy(vp, newVp.getId());
                            newCampo.addValorPosible(newVp);
                            session.flush();

                            if (campo.isImagen()) {
                                for (Iterator iterTra = vp.getLangs().iterator(); iterTra.hasNext();) {
                                    String lang = (String) iterTra.next();
                                    TraValorPosible traVp = (TraValorPosible) vp.getTraduccion(lang);
                                    TraValorPosible newTraVp = (TraValorPosible) newVp.getTraduccion(lang);
                                    if (traVp.getArchivo() != null) {
                                        Archivo archivo = duplicarArchivo(traVp.getArchivo());
                                        newTraVp.setArchivo(archivo);
                                    }
                                }
                                session.flush();
                            }
                        }

                        newCampo.getValidaciones().clear();
                        List validaciones = campo.getValidaciones();
                        for (Iterator iterator = validaciones.iterator(); iterator.hasNext();) {
                            Validacion val = (Validacion) iterator.next();
                            Validacion newVal = (Validacion) ClassUtils.getInstance(val.getClass());
                            newVal.setMascara(val.getMascara());
                            newVal.setCampo(newCampo);
                            session.save(newVal);
                            session.flush();

                            session.saveOrUpdateCopy(val, newVal.getId());
                            newCampo.addValidacion(newVal);
                            session.flush();
                        }
                    }
                }


            }

            formulario.setLastVersion(false);
            
            // -- INDRA: CREAR UNA NUEVA VERSION NO DEBE BLOQUEAR LA ANTERIOR
            // formulario.setBloqueado(true);
            // formulario.setMotivoBloq("Bloqueado por nueva versión.");
            
            session.flush();

            return newFormulario.getId();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    
    private Archivo duplicarArchivo(Archivo oldFile) {
        Archivo archivo = new Archivo();
        archivo.setNombre(oldFile.getNombre());
        archivo.setDatos(oldFile.getDatos());
        archivo.setPesoBytes(oldFile.getPesoBytes());
        archivo.setTipoMime(oldFile.getTipoMime());
        return archivo;
    }


    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public Long anyadirSalida(Long idFormulario, Long idPunto) {
        Session session = getSession();
        try {
          Salida salida = new Salida();
          Formulario formulario = (Formulario)session.load(Formulario.class,  idFormulario);
          PuntoSalida puntoSalida = (PuntoSalida)session.load(PuntoSalida.class, idPunto);

          puntoSalida.addSalida(salida);
          formulario.addSalida(salida);
          salida.setPunto(puntoSalida);
          salida.setFormulario(formulario);

          session.flush();
          return salida.getId();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public void eliminarSalida(Long idSalida) {
        Session session = getSession();
        try {
            Salida salida = (Salida)session.load(Salida.class, idSalida);
            PuntoSalida punto = salida.getPunto();
            if(punto != null){
                punto.removeSalida(salida);
            }
            salida.getFormulario().removeSalida(salida);
            session.delete(salida);
            session.flush();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public List listarSalidasFormulario(Long idFormulario) {
        Session session = getSession();
        try {
            Formulario formulario = (Formulario)session.load(Formulario.class, idFormulario);
            Hibernate.initialize(formulario.getSalidas());
            return formulario.getSalidas();

        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public Salida obtenerSalida(Long idSalida) {
        Session session = getSession();
        try {
            return (Salida)session.load(Salida.class, idSalida);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public Formulario obtenerFormulario(Long idFormulario) {
        Session session = getSession();
        try {
            Formulario formulario = (Formulario) session.load(Formulario.class, idFormulario);
            if (formulario.getLogotipo1() != null) {
                Hibernate.initialize(formulario.getLogotipo1());
            }

            if (formulario.getLogotipo2() != null) {
                Hibernate.initialize(formulario.getLogotipo2());
            }

            for (Iterator iterator = formulario.getLangs().iterator(); iterator.hasNext();) {
                String lang = (String) iterator.next();
                TraFormulario traForm = (TraFormulario) formulario.getTraduccion(lang);
                if (traForm != null && traForm.getPlantilla() != null) {
                    Hibernate.initialize(traForm.getPlantilla());
                }
            }
            return formulario;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     * @ejb.permission role-name="${role.audit}"
     */
    public Formulario obtenerFormularioCompleto(Long idFormulario) {
        Session session = getSession();
        try {
            Formulario formulario = (Formulario) session.load(Formulario.class, idFormulario);

            Hibernate.initialize(formulario.getPantallas());
            for (int i = 0; i < formulario.getPantallas().size(); i++) {
                Pantalla pantalla = (Pantalla)formulario.getPantallas().get(i);
                Hibernate.initialize(pantalla.getAyudas());
                Hibernate.initialize(pantalla.getComponentes());
                for (int j = 0; j < pantalla.getCampos().size(); j++) {
                    Campo campo =  (Campo)pantalla.getCampos().get(j);
                    Hibernate.initialize(campo.getValoresPosibles());
                    if (campo.isImagen()) {
                        for (int k = 0; k < campo.getValoresPosibles().size(); k++) {
                            ValorPosible valorPosible = (ValorPosible) campo.getValoresPosibles().get(k);
                            for (Iterator iterator = valorPosible.getLangs().iterator(); iterator.hasNext();) {
                                String lang = (String) iterator.next();
                                TraValorPosible trVp = (TraValorPosible) valorPosible.getTraduccion(lang);
                                if (trVp != null && trVp.getArchivo() != null) {
                                    Hibernate.initialize(trVp.getArchivo());
                                }
                            }
                        }
                    }
                    Hibernate.initialize(campo.getValidaciones());
                    Hibernate.initialize(campo.getPatron());
                }

            }

            Hibernate.initialize(formulario.getSalidas());
            for (int i = 0; i < formulario.getSalidas().size(); i++) {
                Salida salida = (Salida) formulario.getSalidas().get(i);
                for (Iterator iterator = salida.getPropiedades().iterator(); iterator.hasNext();) {
                    PropiedadSalida propiedad = (PropiedadSalida) iterator.next();
                    if (propiedad.getPlantilla() != null) {
                        Hibernate.initialize(propiedad.getPlantilla());
                    }
                }
            }

            if (formulario.getLogotipo1() != null) {
                Hibernate.initialize(formulario.getLogotipo1());
            }

            if (formulario.getLogotipo2() != null) {
                Hibernate.initialize(formulario.getLogotipo2());
            }

            for (Iterator iterator = formulario.getLangs().iterator(); iterator.hasNext();) {
                String lang = (String) iterator.next();
                TraFormulario traForm = (TraFormulario) formulario.getTraduccion(lang);
                if (traForm != null && traForm.getPlantilla() != null) {
                    Hibernate.initialize(traForm.getPlantilla());
                }
            }
            return formulario;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public Formulario obtenerFormulario(String modelo, int version) {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM Formulario AS f WHERE f.modelo = :modelo AND f.version = :version");
            query.setParameter("modelo", modelo);
            query.setParameter("version", new Integer(version));
            query.setCacheable(true);
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }

            return (Formulario) result.get(0);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public boolean esConfidencial(String modelo, int version) {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM Formulario AS f WHERE f.modelo = :modelo AND f.version = :version");
            query.setParameter("modelo", modelo);
            query.setParameter("version", new Integer(version));
            query.setCacheable(true);
            Formulario formulario = (Formulario) query.uniqueResult();

            if (formulario instanceof FormularioSeguro) {
                FormularioSeguro formSeguro = (FormularioSeguro) formulario;
                return formSeguro.isHttps();
            }

            return false;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public boolean tieneAcceso(String modelo, int version) {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM Formulario AS f WHERE f.modelo = :modelo AND f.version = :version");
            query.setParameter("modelo", modelo);
            query.setParameter("version", new Integer(version));
            query.setCacheable(true);
            Formulario formulario = (Formulario) query.uniqueResult();

            if (formulario instanceof FormularioSeguro) {
                FormularioSeguro formSeguro = (FormularioSeguro) formulario;
                if (formSeguro.isRequerirLogin() && !formSeguro.getRoles().isEmpty()) {
                    for (Iterator iterator = formSeguro.getRoles().iterator(); iterator.hasNext();) {
                        String role = (String) iterator.next();
                        if (ctx.isCallerInRole(role)) {
                            return true;
                        }
                    }
                    return false;
                }
            }

            return true;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public List listarFormularios() {
        Session session = getSession();
        try {
            //Criteria criteri = session.createCriteria(Formulario.class);
            //criteri.addOrder(Order.asc("modelo"));
            //return criteri.list();
            Query query = session.createQuery("FROM Formulario AS f ORDER BY f.modelo ASC, f.version ASC");
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }


    /**
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public List listarUltimasVersionesFormularios() {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM Formulario AS f WHERE f.lastVersion = true ORDER BY f.modelo ASC, f.version ASC");
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public List versionesFormulario(Long id) {
        Session session = getSession();
        try {
            Formulario formulario = (Formulario) session.load(Formulario.class, id);
            Query query = session.createQuery("FROM Formulario AS f WHERE f.modelo = :modelo ORDER BY f.version");
            query.setParameter("modelo", formulario.getModelo());
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public Formulario ultimaVersionFormulario(String modelo) {
        Session session = getSession();
        try {
            Query query = session.createQuery("SELECT MAX(version) FROM Formulario AS f WHERE f.modelo = :modelo ORDER BY f.version");
            query.setParameter("modelo", modelo);
            query.setCacheable(true);
            List result = query.list();
            int llista = result.size();
            if (result.isEmpty()) {
                return null;
            }
            Formulario form = (Formulario) result.get(llista - 1);
            return form;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public void ultimaVersionFalse(Long id) {
        Session session = getSession();
        try {
            Formulario form = (Formulario) session.load(Formulario.class, id);
            form.setLastVersion(false);
            session.update(form);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public void borrarFormulario(Long id) {
        Session session = getSession();
        try {
            Formulario formulario = (Formulario) session.load(Formulario.class, id);
            
            if (!formulario.isBloqueado()){
	           	throw new HibernateException("No tiene bloqueado el formulario " + formulario.getId());
	        }
            
            if (formulario.isBloqueado() && !this.ctx.getCallerPrincipal().getName().equals(formulario.getBloqueadoPor())){
            	throw new HibernateException("Formulario " + id + " bloqueado por otro usuario (" + formulario.getBloqueadoPor() + ")");
            }
            
            // Establecemos ultima version (formulario con version maxima)
        	Query query = session.createQuery("FROM Formulario AS f WHERE f.modelo = :modelo and f.version <> :version ORDER BY f.version DESC");
        	query.setParameter("modelo", formulario.getModelo());
            query.setInteger("version", formulario.getVersion());
            
            List forms = query.list();
            if (!forms.isEmpty()){
            	Formulario form = (Formulario) forms.get(0);
                form.setLastVersion(true);
                session.flush();
                session.evict(formulario);
            }	
            
            session.delete(formulario);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * bloquea el formulario representado por "formulario_id" para el usuario actual
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public void bloquearFormulario(Long formulario_id) {
        Session session = getSession();
        try {        	
            Formulario formulario = (Formulario) session.load(Formulario.class, formulario_id);
            
            if (formulario.isBloqueado()){
            	throw new HibernateException("Formulario " + formulario_id + " ya esta bloqueado por " + formulario.getBloqueadoPor());
            }
            
            formulario.setBloqueado(true);
            formulario.setBloqueadoPor(this.ctx.getCallerPrincipal().getName());
            session.saveOrUpdate(formulario);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * desbloquea el formulario representado por "formulario_id"
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public void desbloquearFormulario(Long formulario_id) {
        Session session = getSession();
        try {
        	Formulario formulario = (Formulario) session.load(Formulario.class, formulario_id);
        	
        	 if (!this.ctx.getCallerPrincipal().getName().equals(formulario.getBloqueadoPor())){
             	throw new HibernateException("Formulario " + formulario_id + " no esta bloqueado por " + formulario.getBloqueadoPor());
             }
        	
            formulario.setBloqueado(false);
            formulario.setBloqueadoPor(null);
            session.saveOrUpdate(formulario);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public void borrarLogotipo1(Long id_formulario, Long id_archivo) {
        Session session = getSession();
        try {
            Formulario formulario = (Formulario) session.load(Formulario.class, id_formulario);
            Query query1 = session.createQuery("FROM Formulario AS f WHERE f.id = :idform");
            query1.setParameter("idform", id_formulario);
            query1.setCacheable(true);
            List result1 = query1.list();
            int llista1 = result1.size();
            if (llista1 > 0) {
                formulario.setLogotipo1(null);
                session.update(formulario);
                session.flush();
            }

            Archivo archivo = (Archivo) session.load(Archivo.class, id_archivo);
            Query query2 = session.createQuery("FROM Archivo AS a WHERE a.id = :idarc");
            query2.setParameter("idarc", id_archivo);
            query2.setCacheable(true);
            List result2 = query2.list();
            int llista2 = result2.size();
            if (llista2 > 0) {
                session.delete(archivo);
            }
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public void borrarLogotipo2(Long id_formulario, Long id_archivo) {
        Session session = getSession();
        try {
            Formulario formulario = (Formulario) session.load(Formulario.class, id_formulario);
            Query query1 = session.createQuery("FROM Formulario AS f WHERE f.id = :idform");
            query1.setParameter("idform", id_formulario);
            query1.setCacheable(true);
            List result1 = query1.list();
            int llista1 = result1.size();
            if (llista1 > 0) {
                formulario.setLogotipo2(null);
                session.update(formulario);
                session.flush();
            }

            Archivo archivo = (Archivo) session.load(Archivo.class, id_archivo);
            Query query2 = session.createQuery("FROM Archivo AS a WHERE a.id = :idarc");
            query2.setParameter("idarc", id_archivo);
            query2.setCacheable(true);
            List result2 = query2.list();
            int llista2 = result2.size();
            if (llista2 > 0) {
                session.delete(archivo);
            }
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public void borrarPlantilla(Long id_formulario, String lang) {
        Session session = getSession();
        try {
            Formulario formulario = (Formulario) session.load(Formulario.class, id_formulario);
            TraFormulario traForm = (TraFormulario) formulario.getTraduccion(lang);
            Long id_archivo = traForm.getPlantilla().getId();
            Query query1 = session.createQuery("FROM Formulario AS f WHERE f.id = :idfor");
            query1.setParameter("idfor", id_formulario);
            query1.setCacheable(true);
            List result1 = query1.list();
            int llista1 = result1.size();
            if (llista1 > 0) {
                traForm.setPlantilla(null);
                session.saveOrUpdate(formulario);
                session.flush();
            }
            Archivo archivo = (Archivo) session.load(Archivo.class, id_archivo);
            Query query2 = session.createQuery("FROM Archivo AS a WHERE a.id = :idarc");
            query2.setParameter("idarc", id_archivo);
            query2.setCacheable(true);
            List result2 = query2.list();
            int llista2 = result2.size();
            if (llista2 > 0) {
                session.delete(archivo);
            }
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
}
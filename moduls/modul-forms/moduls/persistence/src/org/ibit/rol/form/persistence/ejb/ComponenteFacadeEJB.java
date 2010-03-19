package org.ibit.rol.form.persistence.ejb;

import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.Componente;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.Idioma;
import org.ibit.rol.form.model.ListaElementos;
import org.ibit.rol.form.model.Paleta;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.TraPantalla;
import org.ibit.rol.form.model.TraValorPosible;
import org.ibit.rol.form.model.Validacion;
import org.ibit.rol.form.model.ValorPosible;
import org.ibit.rol.form.persistence.util.ClassUtils;

/**
 * SessionBean para matener y consultar componentes.
 *
 * @ejb.bean
 *  name="form/persistence/ComponenteFacade"
 *  jndi-name="org.ibit.rol.form.persistence.ComponenteFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class ComponenteFacadeEJB extends HibernateEJB {

    /**
     * @ejb.create-method
     * @ejb.permission unchecked="true"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    /**
     * Crea o actualiza un componente de una pantalla.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public void gravarComponentePantalla(Componente componente, Long pantalla_id) {
        Session session = getSession();
        try {
            if (componente.getId() == null) {
                Pantalla pantalla = (Pantalla) session.load(Pantalla.class, pantalla_id);
                pantalla.addComponente(componente);
                
                if (!pantalla.getFormulario().isBloqueado()){
    	           	throw new HibernateException("No tiene bloqueado el formulario " + pantalla.getFormulario().getId());
    	        }                
                
                if (pantalla.getFormulario().isBloqueado() && !this.ctx.getCallerPrincipal().getName().equals(pantalla.getFormulario().getBloqueadoPor())){
    	           	throw new HibernateException("Formulario " + pantalla.getFormulario().getId() + " bloqueado por otro usuario (" + pantalla.getFormulario().getBloqueadoPor() + ")");
    	        }                
                
                // -- INDRA: LISTA ELEMENTOS
                // Si el componente es una lista de elementos generamos automaticamente una pantalla de detalle
                if (componente instanceof ListaElementos){
	                Pantalla detalle = new Pantalla();
	                detalle.setNombre(pantalla.getNombre()+"#@#"+componente.getNombreLogico());
	                detalle.setComponenteListaElementos(detalle.getNombre());
	                Formulario formulario = (Formulario) session.load(Formulario.class, pantalla.getFormulario().getId());
	                TraPantalla traduccion = new TraPantalla();
	                traduccion.setTitulo("Detalle");
	                detalle.addTraduccion(Idioma.DEFAULT,traduccion);
	                formulario.addPantalla(detalle);
                }
                // -- INDRA: LISTA ELEMENTOS
                
                session.flush();
                
            } else {
            	
            	// -- INDRA: LISTA ELEMENTOS
            	// Si se realiza un update de un componente lista elementos y se ha cambiado su nombre
            	// updateamos referencias pantalla detalle
            	if (componente instanceof ListaElementos){
            		
            		Componente original = (Componente) session.load(Componente.class,componente.getId());
            		if (!original.getNombreLogico().equals(componente.getNombreLogico())){
            			Formulario formulario = (Formulario) session.load(Formulario.class, original.getPantalla().getFormulario().getId());
            			Pantalla detalle = formulario.findPantalla(original.getPantalla().getNombre()+"#@#"+original.getNombreLogico());
            			detalle.setNombre(original.getPantalla().getNombre()+"#@#"+componente.getNombreLogico());
            			detalle.setComponenteListaElementos(detalle.getNombre());
            			session.update(detalle);            			
            		}
            		session.evict(original);
            		
            	}
            	// -- INDRA: LISTA ELEMENTOS
            		
            	session.update(componente);
            }
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Crea o actualiza un componente de una paleta.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void gravarComponentePaleta(Componente componente, Long paleta_id) {
        Session session = getSession();
        try {
            if (componente.getId() == null) {
                Paleta paleta = (Paleta) session.load(Paleta.class, paleta_id);
                paleta.addComponente(componente);
                session.flush();
            } else {
                session.update(componente);
            }
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Copia un componente de una paleta o pantalla a una pantalla.
     * @param componente_id
     * @param pantalla_id
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public Long copiarComponente(Long componente_id, Long pantalla_id) {
         Session session = getSession();
        try {
            Componente componente = (Componente) session.load(Componente.class, componente_id);
            Pantalla pantalla = (Pantalla) session.load(Pantalla.class, pantalla_id);

            Componente newComponente = (Componente) ClassUtils.getInstance(componente.getClass());
            newComponente.setNombreLogico("New component");
            session.save(newComponente);
            session.flush();

            session.saveOrUpdateCopy(componente, newComponente.getId());
            newComponente.setPaleta(null);
            pantalla.addComponente(newComponente);
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
                                Archivo archivo = new Archivo();
                                archivo.setNombre(traVp.getArchivo().getNombre());
                                archivo.setDatos(traVp.getArchivo().getDatos());
                                archivo.setPesoBytes(traVp.getArchivo().getPesoBytes());
                                archivo.setTipoMime(traVp.getArchivo().getTipoMime());
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

            return newComponente.getId();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Lista todos los componentes de una pantalla.
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public List listarComponentesPantalla(Long pantalla_id) {
        Session session = getSession();
        try {
            Pantalla pantalla = (Pantalla) session.load(Pantalla.class, pantalla_id);
            List componentes = pantalla.getComponentes();
            Hibernate.initialize(componentes);
            return componentes;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Lista todos los componentes de una paleta.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin},${role.form}"
     */
    public List listarComponentesPaleta(Long paleta_id) {
        Session session = getSession();
        try {
            Paleta paleta = (Paleta) session.load(Paleta.class, paleta_id);
            List componentes = paleta.getComponentes();
            Hibernate.initialize(componentes);
            return componentes;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Obtiene los datos de un componente.
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public Componente obtenerComponente(Long componente_id) {
        Session session = getSession();
        try {
            Componente componente = (Componente) session.load(Componente.class, componente_id);
            if (componente instanceof Campo) {
                Hibernate.initialize(((Campo) componente).getValidaciones());
                Hibernate.initialize(((Campo) componente).getValoresPosibles());
            }
            return componente;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form},${role.admin}"
     */
    public void cambiarOrden(Long comp1_id, Long comp2_id) {
        Session session = getSession();
        try {
            Componente c1 = (Componente) session.load(Componente.class, comp1_id);
            Componente c2 = (Componente) session.load(Componente.class, comp2_id);

            if (c1.hasPantalla()) {
            	
            	if (!c1.getPantalla().getFormulario().isBloqueado()){
    	           	throw new HibernateException("No tiene bloqueado el formulario " + c1.getPantalla().getFormulario().getId());
    	        }
            	
	            if (c1.getPantalla().getFormulario().isBloqueado() && !this.ctx.getCallerPrincipal().getName().equals(c1.getPantalla().getFormulario().getBloqueadoPor())){
	             	throw new HibernateException("Formulario " + c1.getPantalla().getFormulario().getId() + " bloqueado por otro usuario (" + c1.getPantalla().getFormulario().getBloqueadoPor() + ")");
	             }	
            }
            
            int aux = c1.getOrden();
            c1.setOrden(c2.getOrden());
            c2.setOrden(aux);

            List componentes;
            if (c1.hasPantalla()) {
                componentes = c1.getPantalla().getComponentes();
            } else {
                componentes = c1.getPaleta().getComponentes();
            }

            componentes.set(c1.getOrden(), c1);
            componentes.set(c2.getOrden(), c2);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Borra un componente.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form},${role.admin}"
     */
    public void borrarComponente(Long id) {
        Session session = getSession();
        try {
            Componente componente = (Componente) session.load(Componente.class, id);
            if (componente.hasPantalla()) {
                
            	if (!componente.getPantalla().getFormulario().isBloqueado()){
    	           	throw new HibernateException("No tiene bloqueado el formulario " + componente.getPantalla().getFormulario().getId());
    	        }
            	
            	 if (componente.getPantalla().getFormulario().isBloqueado() && !this.ctx.getCallerPrincipal().getName().equals(componente.getPantalla().getFormulario().getBloqueadoPor())){
                 	throw new HibernateException("Formulario " + componente.getPantalla().getFormulario().getId() + " bloqueado por otro usuario (" + componente.getPantalla().getFormulario().getBloqueadoPor() + ")");
                 }
            	
                // --- INDRA: LISTA ELEMENTOS
                // Si se borra un componente lista elementos hay que borrar pantalla asociada
                if (componente instanceof ListaElementos){
                	Formulario formulario = componente.getPantalla().getFormulario();
	                Pantalla detalle = formulario.findPantalla(componente.getPantalla().getNombre()+"#@#"+componente.getNombreLogico());
	                formulario.removePantalla(detalle);
	                session.delete(detalle);
                }                                              
                // --- INDRA: LISTA ELEMENTOS
                
                componente.getPantalla().removeComponente(componente);
                
            } else {
                componente.getPaleta().removeComponente(componente);
            }
            session.delete(componente);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    // Métodos especificos para Campos.

    /**
     * Borra las validaciones de un campo.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form},${role.admin}"
     */
    public void borrarValidacionesCampo(Long campo_id) {
        Session session = getSession();
        try {
            Campo campo = (Campo) session.load(Campo.class, campo_id);
            
            if (campo.hasPantalla()) {
            	
            	if (!campo.getPantalla().getFormulario().isBloqueado()){
    	           	throw new HibernateException("No tiene bloqueado el formulario " + campo.getPantalla().getFormulario().getId());
    	        }
            	
	            if (campo.getPantalla().getFormulario().isBloqueado() && !this.ctx.getCallerPrincipal().getName().equals(campo.getPantalla().getFormulario().getBloqueadoPor())){
	             	throw new HibernateException("Formulario " + campo.getPantalla().getFormulario().getId() + " bloqueado por otro usuario (" + campo.getPantalla().getFormulario().getBloqueadoPor() + ")");
	             }
            }
            
            for (Iterator iterator = campo.getValidaciones().iterator(); iterator.hasNext();) {
                Validacion validacion = (Validacion) iterator.next();
                session.delete(validacion);
            }
            campo.getValidaciones().clear();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

}

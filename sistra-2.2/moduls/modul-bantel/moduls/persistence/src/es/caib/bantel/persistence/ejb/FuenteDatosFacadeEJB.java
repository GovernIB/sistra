package es.caib.bantel.persistence.ejb;

import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;
import es.caib.bantel.model.CampoFuenteDatos;
import es.caib.bantel.model.FilaFuenteDatos;
import es.caib.bantel.model.FiltroConsultaFuenteDatos;
import es.caib.bantel.model.FuenteDatos;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Page;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.model.ValorFuenteDatos;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;

/**
 * SessionBean para mantener y consultar FuenteDatosFacade
 *
 * @ejb.bean
 *  name="bantel/persistence/FuenteDatosFacade"
 *  jndi-name="es.caib.bantel.persistence.FuenteDatosFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.env-entry name="roleAdmin" value="${role.admin}"
 *
 * @ejb.transaction type="Required"
 */
public abstract class FuenteDatosFacadeEJB extends HibernateEJB {

	private String ROLE_ADMIN;
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

		try {
			InitialContext ctx = new InitialContext();
			ROLE_ADMIN = (String) ctx.lookup("java:comp/env/roleAdmin");
		} catch (NamingException e) {
			throw new CreateException("No se ha especificado role admin");
		}
		
		
	}
	
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"     
     */
    public List listarFuentesDatos() {
        Session session = getSession();
        try {
        	 Query query = session.createQuery("FROM FuenteDatos");             
             List result = query.list();
             return result;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
	
	
	 /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.gestor}"
     */
    public FuenteDatos obtenerFuenteDatos(String identificador) {
        Session session = getSession();
        try {
        	 Query query = session.createQuery("FROM FuenteDatos AS f WHERE f.identificador = :identificador");
             query.setParameter("identificador", identificador);
             List result = query.list();
             if (result.isEmpty()) {
                 return null;
             }
             FuenteDatos fd = (FuenteDatos) result.get(0);
             Hibernate.initialize(fd);
             Hibernate.initialize(fd.getCampos());
             Hibernate.initialize(fd.getFilas());
             
             
             // En caso de ser gestor realizamos control de acceso
             if (!this.ctx.isCallerInRole(ROLE_ADMIN)) {
            	 	GestorBandejaDelegate gestorBandejaDelegate = DelegateUtil.getGestorBandejaDelegate();
	          		GestorBandeja gestor = gestorBandejaDelegate.obtenerGestorBandeja(this.ctx.getCallerPrincipal().getName());
	          		boolean acceso = false;		
	          		if (gestor != null) {
		          		for (Iterator it=gestor.getProcedimientosGestionados().iterator();it.hasNext();){
		          				Procedimiento procedimiento = (Procedimiento) it.next();
		          				if (procedimiento.getIdentificador().equals(fd.getProcedimiento().getIdentificador())){
		          					acceso = true;
		          					break;
		          				}
		          		}
	          		}
	        
	          		if (!acceso){
	          			throw new Exception("No tiene acceso a fuente de acceso " + fd.getIdentificador());
	          		}
	          		
             }
             
     		 return fd;
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public Long altaFuenteDatos(String identificador, String procedimiento, String descripcion) {
       
    	FuenteDatos fd = this.obtenerFuenteDatos(identificador);
    	if (fd != null) {
    		throw new EJBException("Ya existe fuente de datos " + identificador);
    	}
    	
    	Procedimiento proc = consultarProcedimiento(procedimiento);
    	 
    	Session session = getSession();
        try {
        	fd = new FuenteDatos();
        	fd.setIdentificador(identificador);
        	fd.setProcedimiento(proc);
        	fd.setDescripcion(descripcion);
        	session.save(fd);
        	return fd.getCodigo();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

	
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void modificarFuenteDatos(String identificador, String identificadorNew, String procedimientoNew, String descripcionNew) {
       
    	FuenteDatos fd = this.obtenerFuenteDatos(identificador);
    	if (fd == null) {
    		throw new EJBException("No existe fuente de datos " + identificador);
    	}
    	
    	Procedimiento proc = consultarProcedimiento(procedimientoNew);
    	
    	Session session = getSession();
        try {
        	fd.setIdentificador(identificadorNew);
        	fd.setProcedimiento(proc);
        	fd.setDescripcion(descripcionNew);
        	session.update(fd);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarFuenteDatos(String identificador) {
       
    	FuenteDatos fd = this.obtenerFuenteDatos(identificador);
    	if (fd == null) {
    		throw new EJBException("No existe fuente de datos " + identificador);
    	}
    	
    	Session session = getSession();
        try {
        	// Borramos filas
        	fd.getFilas().removeAll(fd.getFilas());
        	session.update(fd);
        	
        	// Borramos fuente y definicion campos
        	session.delete(fd);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void altaCampoFuenteDatos(String identificadorFuenteDatos, String identificadorCampo) {
       
    	// Obtenemos fuente de datos y verificamos que no existe id campo
    	FuenteDatos fd = this.obtenerFuenteDatos(identificadorFuenteDatos);
    	if (fd == null) {
    		throw new EJBException("No existe fuente de datos " + identificadorFuenteDatos);
    	}
    	if (fd.getCampoFuenteDatos(identificadorCampo) != null) {
    		throw new EJBException("Ya existe campo con identificador " + identificadorCampo);
    	}
    	
    	// Actualizamos BBDD
    	
    	// 1) Añadimos campo a definicion
    	Session session = getSession();
        try {
        	CampoFuenteDatos cfd = new CampoFuenteDatos();
        	cfd.setIdentificador(identificadorCampo);
        	fd.addCampoFuenteDatos(cfd);        	
        	session.update(fd);        	
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
        
        // 2) Añadimos valores a filas existentes
        //	- Recuperamos de nuevo fuente de datos para que este el nuevo campo
        fd = this.obtenerFuenteDatos(identificadorFuenteDatos);
        CampoFuenteDatos cfdNew = fd.getCampoFuenteDatos(identificadorCampo);
        session = getSession();
        try {
        	if (fd.getFilas() != null) {
            	for (Iterator it = fd.getFilas().iterator(); it.hasNext();) {
            		FilaFuenteDatos ffd = (FilaFuenteDatos) it.next();
            		ffd.addValorFuenteDatos(cfdNew, null);
            		session.update(ffd);
            	}
        	}        	        
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }

    }
    

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarCampoFuenteDatos(String identificadorFuenteDatos, String identificadorCampo) {
       
    	// Obtenemos fuente de datos y verificamos que no existe id campo
    	FuenteDatos fd = this.obtenerFuenteDatos(identificadorFuenteDatos);
    	if (fd == null) {
    		throw new EJBException("No existe fuente de datos " + identificadorFuenteDatos);
    	}
    	
    	// Actualizamos BBDD
    	// 1) Eliminamos valores de filas existentes
        Session session = getSession();
        try {
        	if (fd.getFilas() != null) {
            	for (Iterator it = fd.getFilas().iterator(); it.hasNext();) {
            		FilaFuenteDatos ffd = (FilaFuenteDatos) it.next();
            		ffd.removeValorFuenteDatos(identificadorCampo);
            		session.update(ffd);
            	}
        	}        	        
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    	// 2) Eliminamos campo de definicion
        //	- Recuperamos de nuevo fuente de datos (actualizacion filas)
        fd = this.obtenerFuenteDatos(identificadorFuenteDatos);
        CampoFuenteDatos cfd = fd.getCampoFuenteDatos(identificadorCampo);
    	session = getSession();
        try {
        	fd.removeCampoFuenteDatos(cfd);
        	session.update(fd);        	
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
        
    }
    
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void modificarCampoFuenteDatos(String identificadorFuenteDatos, String identificadorCampoOld, String identificadorCampoNew) {
       
    	// Obtenemos fuente de datos
    	FuenteDatos fd = this.obtenerFuenteDatos(identificadorFuenteDatos);
    	if (fd == null) {
    		throw new EJBException("No existe fuente de datos " + identificadorFuenteDatos);
    	}
    	
    	// Verificamos que existe campo antiguo
    	 CampoFuenteDatos cd = fd.getCampoFuenteDatos(identificadorCampoOld);
    	 if (cd == null) {
     		throw new EJBException("No existe campo " + identificadorCampoOld);
     	}
    	 
    	 // Verificamos que no exista otro campo con ese codigo
    	 if (fd.getCampoFuenteDatos(identificadorCampoNew) != null) {
    		 throw new EJBException("Ya existe campo " + identificadorCampoNew);
    	 }
    	
    	// Actualizamos BBDD
    	Session session = getSession();
        try {
        	cd.setIdentificador(identificadorCampoNew);
            session.update(cd);                    	       
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
        
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void altaFilaFuenteDatos(String identificadorFuenteDatos) {
    	// Obtenemos fuente de datos y verificamos que no existe id campo
    	FuenteDatos fd = this.obtenerFuenteDatos(identificadorFuenteDatos);
    	if (fd == null) {
    		throw new EJBException("No existe fuente de datos " + identificadorFuenteDatos);
    	}
    	
    	// Actualizamos BBDD
    	Session session = getSession();
        try {
        	// Creamos fila con todos los campos
        	FilaFuenteDatos ffd = fd.addFilaFuenteDatos();
        	for (Iterator it = fd.getCampos().iterator(); it.hasNext();) {
        		CampoFuenteDatos cfd = (CampoFuenteDatos) it.next();
        		ffd.addValorFuenteDatos(cfd, null);
        	}
        	// Guardamos fila
        	session.save(ffd);        	
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarFilaFuenteDatos(String identificadorFuenteDatos, int numFila) {
    	// Obtenemos fuente de datos y verificamos que no existe id campo
    	FuenteDatos fd = this.obtenerFuenteDatos(identificadorFuenteDatos);
    	if (fd == null) {
    		throw new EJBException("No existe fuente de datos " + identificadorFuenteDatos);
    	}
    	
    	// Obtenemos fila
    	FilaFuenteDatos ffd = fd.getFilaFuenteDatos(numFila);
    	if (ffd == null) {
    		throw new EJBException("No existe fila " + numFila);
    	}
    	
    	// Actualizamos BBDD
    	Session session = getSession();
        try {
        	fd.removeFilaFuenteDatos(ffd);
        	session.delete(ffd);        	
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }
    
    
    
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.gestor}"
     */
    public void establecerValorFuenteDatos(String identificadorFuenteDatos, int numFila, String identificadorCampo, String valorCampo) {
       
    	// Obtenemos fuente de datos y verificamos que no existe id campo
    	FuenteDatos fd = this.obtenerFuenteDatos(identificadorFuenteDatos);
    	if (fd == null) {
    		throw new EJBException("No existe fuente de datos " + identificadorFuenteDatos);
    	}
    	if (fd.getCampoFuenteDatos(identificadorCampo) == null) {
    		throw new EJBException("No existe campo con identificador " + identificadorCampo);
    	}
    	
    	// Actualizamos BBDD
    	Session session = getSession();
        try {
        	
        	FilaFuenteDatos ffd = fd.getFilaFuenteDatos(numFila);
        	if (ffd == null) {
        		throw new Exception("No existe fila " + numFila);
        	}
        	
        	ValorFuenteDatos vfd = null;
        	for (Iterator it = ffd.getValores().iterator(); it.hasNext();) {
        		ValorFuenteDatos v = (ValorFuenteDatos) it.next();
        		if (v.getCampoFuenteDatos().getIdentificador().equals(identificadorCampo)){
        			vfd = v;
        			break;
        		}        		
        	}
        	if (vfd == null) {
        		throw new EJBException("No existe campo " + identificadorCampo + " en la fila " + numFila);
        	}
        	
        	vfd.setValor(valorCampo);
        	session.update(vfd);
        	        	
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
        
    }
    
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.todos}"
     */
    public List realizarConsulta(String idFuenteDatos, List filtros, List parametros) {
    	 Session session = getSession();
         try {       	
        	 
        	 String select = "SELECT distinct v.filaFuenteDatos FROM ValorFuenteDatos v "; 
        	 
        	 String where = "WHERE v.filaFuenteDatos.fuenteDatos.identificador = :idFuenteDatos ";
        	 if (filtros != null && filtros.size() > 0) {
        		 where += " AND ( ";
        		 for (int numFiltro = 0; numFiltro < filtros.size(); numFiltro++) {
        			 FiltroConsultaFuenteDatos ffd = (FiltroConsultaFuenteDatos) filtros.get(numFiltro);
        			 where += (numFiltro == 0?"":ffd.getConector());
        			 where += " (upper(v.campoFuenteDatos.identificador) = :campoFiltro" + numFiltro;
        			 
        			 if (FiltroConsultaFuenteDatos.LIKE.equals(ffd.getOperador())) {
        				 where += " AND upper(v.valor) LIKE '%' || upper(:valorFiltro" + numFiltro + ") || '%' ) ";
        			 } else {
        				 where += " AND upper(v.valor) = upper(:valorFiltro" + numFiltro + ") ) ";
        			 }
        			 
        			 
        		 }
        		 where += " ) ";
        	 }
        	 
        	 Query query = session
             	.createQuery(select + where);
        	 
        	 query.setString("idFuenteDatos", idFuenteDatos);
        	 if (filtros != null && filtros.size() > 0) {
        		 for (int numFiltro = 0; numFiltro < filtros.size(); numFiltro++) {
        			 FiltroConsultaFuenteDatos ffd = (FiltroConsultaFuenteDatos) filtros.get(numFiltro);
        			 query.setString("campoFiltro" + numFiltro, ffd.getCampo().toUpperCase());
        			 query.setString("valorFiltro" + numFiltro, (String) parametros.get(numFiltro));        			 
        		 }        		 
        	 }
        	
        	 List result = query.list();
        	 
        	 return result;
        	 
         } catch (HibernateException he) {
             throw new EJBException(he);
         } finally {
             close(session);
         }
    }    
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     */
    public Page busquedaPaginadaFuenteDatosGestor(int pagina, int longitudPagina )
    {
    	// Recuperamos gestor
    	GestorBandeja gestor = null;
    	try{	    	    		
    		GestorBandejaDelegate gd = DelegateUtil.getGestorBandejaDelegate();
    		gestor = gd.obtenerGestorBandeja(this.ctx.getCallerPrincipal().getName());
	    	if (gestor == null) throw new Exception("No se encuentra gestor para usuario seycon " + this.ctx.getCallerPrincipal().getName());
    	}catch (Exception he) 
		{
	        throw new EJBException(he);
	    } 

    	// Buscamos fuentes de datos de procedimientos gestionados por el gestor
		Session session = getSession();
		try 
		{	
			Criteria criteria = session.createCriteria( FuenteDatos.class );
	    	criteria.setCacheable( false );
	    	criteria.add( Expression.in( "procedimiento",gestor.getProcedimientosGestionados()) );
			Page page = new Page( criteria, pagina, longitudPagina );
			return page;			
	    } 
		catch (Exception he) 
		{
	        throw new EJBException(he);
	    } 
		finally 
	    {
	        close(session);
	    }
    }
    
    
    private Procedimiento consultarProcedimiento(String procedimiento) {
		Procedimiento proc;
		try {
			proc = DelegateUtil.getTramiteDelegate().obtenerProcedimiento(procedimiento);
		} catch (DelegateException e) {
			throw new EJBException("Error al buscar procedimiento " + procedimiento);
		}
    	if (proc == null) {
    		throw new EJBException("No existe procedimiento " + procedimiento);
    	}
		return proc;
	}
}


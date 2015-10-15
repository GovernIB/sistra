package es.caib.redose.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.DocumentoVerifier;
import es.caib.redose.modelInterfaz.ExcepcionRDS;
import es.caib.redose.modelInterfaz.KeyVerifier;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.TransformacionRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.intf.RdsFacade;
import es.caib.redose.persistence.util.RdsFacadeUtil;
import es.caib.sistra.plugins.firma.FirmaIntf;

/**
 * Business delegate para operar con RDS.
 */
public class RdsDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
    public ReferenciaRDS insertarDocumento(DocumentoRDS documento)  throws DelegateException{
    	try {
            return getFacade().insertarDocumento(documento);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	    
    public ReferenciaRDS insertarDocumento(DocumentoRDS documento, TransformacionRDS transformacion) throws DelegateException{
    	try {
            return getFacade().insertarDocumento(documento,transformacion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    /**
     * Actualiza un documento en el RDS    
     */
    public void actualizarDocumento(DocumentoRDS documento)  throws DelegateException{
    	try {
            getFacade().actualizarDocumento(documento);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }    
       
    
    public void actualizarFichero(ReferenciaRDS ref, byte[] datos)throws DelegateException{
    	try {
            getFacade().actualizarFichero(ref,datos);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }    
    
    /**
     *	Crea un uso para un documento 
     */
    public void crearUso(UsoRDS uso)  throws DelegateException{
    	try {
            getFacade().crearUso(uso);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }    
    
    /**
     * Consulta un documento del RDS de tipo estructurado formateado con una plantilla generando dos copias: una para el interesado y otra para la
     * administracion 
     * @param refRds
     * @param idioma
     * @return
     * @throws DelegateException
     */
    public DocumentoRDS consultarDocumentoFormateadoCopiasInteresadoAdmon(ReferenciaRDS refRds,String idioma) throws DelegateException{
    	try {
            return getFacade().consultarDocumentoFormateadoCopiasInteresadoAdmon(refRds,idioma);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    /**
     * Consulta un documento del RDS (datos del documento y fichero asociado)
     */
    public DocumentoRDS consultarDocumento(ReferenciaRDS refRds)  throws DelegateException{    	
    	return consultarDocumento(refRds,true);
    }
    
    /**
     * Consulta un documento del RDS. Permite indicar si sólo se recuperan los datos del documento o también el fichero asociado
     */
    public DocumentoRDS consultarDocumento(ReferenciaRDS refRds,boolean recuperarFichero)  throws DelegateException{
    	try {
            return getFacade().consultarDocumento(refRds,recuperarFichero);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    /**
     * Consulta un documento del RDS de tipo estructurado formateado con una plantilla
     */
    public DocumentoRDS consultarDocumentoFormateado(ReferenciaRDS refRds,String tipoPlantilla,String idioma)  throws DelegateException{
    	try {
            return getFacade().consultarDocumentoFormateado(refRds,tipoPlantilla,idioma);    		    		
        } catch (Exception e) {
            throw new DelegateException(e);
        }
   }
    
    /**
     * Consulta un documento del RDS de tipo estructurado formateado con una plantilla
     */
    public DocumentoRDS consultarDocumentoFormateado(ReferenciaRDS refRds)  throws DelegateException{
    	try {
            return getFacade().consultarDocumentoFormateado(refRds);    		    		
        } catch (Exception e) {
            throw new DelegateException(e);
        }
   } 
    
    /**
     * Consulta un documento del RDS de tipo estructurado formateado con una plantilla
     */
    public DocumentoRDS consultarDocumentoFormateado(ReferenciaRDS refRds,String idioma)  throws DelegateException{
    	try {
            return getFacade().consultarDocumentoFormateado(refRds,idioma);    		    		
        } catch (Exception e) {
            throw new DelegateException(e);
        }
   } 
    
    /**
     * Formatea XML a partir de una plantilla. 
     * @param documentoRDS XML a formatear. Debe tener establecidos los siguientes atributos: datosFichero,nombreFichero y titulo
    * @param modelo Modelo
    * @param version Version
    * @param tipoPlantilla Plantilla (si es nula se utiliza la por defecto)
    * @param idioma Idioma
    * @return
    * @throws ExcepcionRDS
     */
    public DocumentoRDS formatearDocumento(DocumentoRDS documentoRDS,String modelo,int version,String tipoPlantilla,String idioma) throws DelegateException{
    	try {
            return getFacade().formatearDocumento(documentoRDS,modelo,version,tipoPlantilla,idioma);   		    		
        } catch (Exception e) {
            throw new DelegateException(e);
        }
   } 
        
    /**
     * Elimina uso para un documento del RDS     
     */
    public void eliminarUso(UsoRDS usoRDS) throws DelegateException {
    	 try {
             getFacade().eliminarUso(usoRDS);
         } catch (Exception e) {
             throw new DelegateException(e);
         }  
    }
    
    /**
     * Eliminar usos que tienen una determinada referencia para varios documentos del RDS   
     */
    public void eliminarUsos(String tipoUsoId,String referencia)throws DelegateException {
    	 try {
             getFacade().eliminarUsos(tipoUsoId,referencia);
         } catch (Exception e) {
             throw new DelegateException(e);
         } 
    }
    
    /**
     * Consulta usos para un documento del RDS   
     */
    public List listarUsos(ReferenciaRDS refRDS) throws DelegateException {
    	try {
            return getFacade().listarUsos(refRDS);
        } catch (Exception e) {
            throw new DelegateException(e);
        } 
    }
    
    /**
     * Consulta usos para un documento del RDS   
     */
    public void asociarFirmaDocumento(ReferenciaRDS refRDS,FirmaIntf firma) throws DelegateException {
    	try {
            getFacade().asociarFirmaDocumento(refRDS,firma);
        } catch (Exception e) {
            throw new DelegateException(e);
        } 
    }
    
    /**
     * Verifica documento
     */
    public DocumentoVerifier verificarDocumento(KeyVerifier key) throws DelegateException {
    	try {
            return getFacade().verificarDocumento(key);
        } catch (Exception e) {
            throw new DelegateException(e);
        } 
    }
    
    /**
     * Verifica documento
     */
    public DocumentoVerifier verificarDocumento(String csv) throws DelegateException {
    	try {
            return getFacade().verificarDocumento(csv);
        } catch (Exception e) {
            throw new DelegateException(e);
        } 
    }
    
    /**
     * Cambia de UA un documento
     */
    public void cambiarUnidadAdministrativa(ReferenciaRDS refRDS, Long codUA) throws DelegateException{
    	try {
            getFacade().cambiarUnidadAdministrativa(refRDS,codUA);
        } catch (Exception e) {
            throw new DelegateException(e);
        } 
    }
	
    /**
     * Convierte un fichero a PDF/A. Debe tener una extensión permitida: "doc","docx","ppt","xls","odt","jpg","txt"
     * 
     * @param documento
     * @param extension
     * @return
     * @throws DelegateException
     */
    public byte[] convertirFicheroAPDF(byte[] documento,String extension) throws DelegateException{
    	try {
           return getFacade().convertirFicheroAPDF(documento,extension);
        } catch (Exception e) {
            throw new DelegateException(e);
        } 
    }        

    /**
     * Consolida documento en gestion documental
     * @param refRDS Referencia RDS
     * @throws ExcepcionRDS
     */
    public String consolidarDocumento(ReferenciaRDS refRDS)throws DelegateException{
    	try {
           return getFacade().consolidarDocumento(refRDS);
        } catch (Exception e) {
            throw new DelegateException(e);
        }         	
    }
    
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private RdsFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return RdsFacadeUtil.getHome().create();
    }

    protected RdsDelegate() throws DelegateException {       
    }                  
}

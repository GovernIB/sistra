package es.caib.zonaper.persistence.ejb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.regtel.model.ValorOrganismo;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.sistra.modelInterfaz.ConstantesDominio;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateSISTRAUtil;
import es.caib.zonaper.model.ValorDominio;
import es.caib.zonaper.persistence.util.Dominios;


/**
 * SessionBean que sirve para acceder a dominios de datos.
 * Las listas de valores se devuelven como una List de objetos ValorDominio
 *
 * @ejb.bean
 *  name="zonaper/persistence/DominiosFacade"
 *  local-jndi-name = "es.caib.zonaper.persistence.DominiosFacade"
 *  type="Stateless"
 *  view-type="local"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 */
public abstract class DominiosFacadeEJB implements SessionBean {

	private static Log log = LogFactory.getLog(DominiosFacadeEJB.class);

    public void setSessionContext(SessionContext ctx) {       
    }

    /**
     * @ejb.create-method
     * @ejb.permission role-name="${role.todos}"
     */
    public void ejbCreate() throws CreateException 
    {     
    }
	  
    /**
     * Lista provincias
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public List listarProvincias(){
    	try{
    		return Dominios.listarProvincias();
    	}catch (Exception ex){
    		throw new EJBException(ex);    	
    	}    		    
    }
    
    /**
     *  Lista de municipios de una provincia
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public List listarLocalidadesProvincia(String codProv) {
    	try{
    		return Dominios.listarLocalidadesProvincia(codProv);
    	}catch (Exception ex){
    		throw new EJBException(ex);    	
    	}    		    
    }
    
    /**
     * Obtiene descripcion municipio
     * 
     * @param codProvincia
     * @param codMunicipio
     * @return Descripcion municipio
     * @throws Exception
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String obtenerDescripcionMunicipio(String codProvincia, String codMunicipio) throws Exception{
    	try{
    		return Dominios.obtenerDescripcionMunicipio( codProvincia, codMunicipio);
    	}catch (Exception ex){
    		throw new EJBException(ex);    	
    	}  	
    }
    
    /**
     * Obtiene descripcion pais
     * 
     * @param codPais
     * @return Descripcion pais
     * @throws Exception
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String obtenerDescripcionPais(String codPais) throws Exception{
    	try{
    		return Dominios.obtenerDescripcionPais( codPais);
    	}catch (Exception ex){
    		throw new EJBException(ex);    	
    	}  	
    }
    	
    
    /**
     * Obtiene descripcion de unidad
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String obtenerDescripcionUA(String codUA) {
    	try{
    		return Dominios.obtenerDescripcionUA(codUA);
    	}catch (Exception ex){
    		throw new EJBException(ex);    	
    	}    		    
    }
    
    /**
     * Obtiene lista de oficinas registrales usuario
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.registro}"
     */
    public List obtenerOficinas(char tipoRegistro, String usuario) {
    	try{    		
    		List oficinas = DelegateRegtelUtil.getRegistroTelematicoDelegate().obtenerOficinasRegistroUsuario(tipoRegistro, usuario);
    		return listValorOrganismoToListValorDominio(oficinas);    		
    	}catch (Exception ex){
    		throw new EJBException(ex);    	
    	}    		    
    }
   
    /**
     * Obtiene descripcion oficina para el sello
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.registro}"
     */
    public String obtenerDescripcionSelloOficina(char tipoRegistro, String oficina) {
    	try{    		
    		return DelegateRegtelUtil.getRegistroTelematicoDelegate().obtenerDescripcionSelloOficina(tipoRegistro, oficina);    		    	
    	}catch (Exception ex){
    		throw new EJBException(ex);    	
    	}    		    
    }
    
    /**
     * Obtiene lista de tipos asunto
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.registro}"
     */
    public List obtenerTiposAsunto() {
    	try{
    		List asuntos = DelegateRegtelUtil.getRegistroTelematicoDelegate().obtenerTiposAsunto();
    		return listValorOrganismoToListValorDominio(asuntos);
    	}catch (Exception ex){
    		throw new EJBException(ex);    	
    	}    		    
    }
    
    /**
     * Convierte lista de valores organismos a lista de ValorDominio
     * @param listVO
     * @return
     */
    private List listValorOrganismoToListValorDominio(List listVO){
    	List values = new ArrayList();
		if (listVO != null){
    		for (Iterator it = listVO.iterator();it.hasNext();){
    			ValorOrganismo vo = (ValorOrganismo) it.next();
    			ValorDominio vd = new ValorDominio();
    			vd.setCodigo(vo.getCodigo());
    			vd.setDescripcion(vo.getDescripcion());    			
    			values.add(vd);
    		}
		}
		return values;
    }
   
    /**
     * Convierte lista de valores organismos a lista de ValorDominio
     * @param listVO
     * @return
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String obtenerRaizUnidadesOrganicas(){
    	String codigo = "";
    	//Obtenemos valores dominio del EJB
		try{
	    	ValoresDominio dom;
			dom = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( ConstantesDominio.DOMINIO_SAC_ARBOL_UNIDADES_ADMINISTRATIVAS , null);
			if(dom.getFilas().size() > 0){
				Map datos = (Map)dom.getFilas().get(0);
				return (String) datos.get("CODIGO");
			}else{
				throw new Exception("No se han encontrado unidades.");
			}
		}catch(Exception ex){
			throw new EJBException(ex);    
		}
    }
   
}

package es.caib.bantel.front.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.delegate.DelegateSISTRAUtil;
import es.caib.bantel.front.json.Localidad;
import es.caib.bantel.front.json.Pais;
import es.caib.bantel.front.json.Provincia;
import es.caib.bantel.front.json.UnidadAdministrativa;


/**
 * Clase que se comunica con SISTRA para obtener domininios
 *
 */
public class Dominios {

	/**
	 * Lista provincias
	 * 
	 * @return List de Provincia
	 * @throws Exception
	 */
	public static List listarProvincias() throws Exception{
		// Obtenemos valores dominio del EJB
		List provs = new ArrayList();			
		ValoresDominio dom = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( "GEPROVINCI" , null);
		
		for (int i=1;i<=dom.getNumeroFilas();i++){
			Provincia p = new Provincia();
			p.setCodigo(dom.getValor(i,"CODIGO"));
			p.setDescripcion(dom.getValor(i,"DESCRIPCION"));
			provs.add(p);			
		}					
		return provs;
	}
	
	/**
	 * Lista de municipios de una provincia
	 * 
	 * @param codProv Codigo provincia
	 * @return List de Localidad
	 * @throws Exception
	 */
	public static  List listarLocalidadesProvincia(String codProv) throws Exception{
		// Obtenemos valores dominio del EJB
		List locs = new ArrayList();		
		Localidad l;
		
		// Recuperamos lista de localidades
		if (!StringUtils.isEmpty(codProv)){
			List params = new ArrayList();
			params.add(codProv);
			ValoresDominio dom = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( "GEGMUNICI" , params);			
			for (int i=1;i<=dom.getNumeroFilas();i++){
				l = new Localidad();
				l.setCodigo(dom.getValor(i,"CODIGO"));
				l.setDescripcion(dom.getValor(i,"DESCRIPCION"));
				locs.add(l);			
			}			
		}
		
		
		
		return locs;
	}
	
	/**
	 * Lista paises
	 * 
	 * @return List de paises
	 * @throws Exception
	 */
	public static List listarPaises() throws Exception{
		// Obtenemos valores dominio del EJB
		List provs = new ArrayList();			
		ValoresDominio dom = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( "GEPAISES" , null);
		
		for (int i=1;i<=dom.getNumeroFilas();i++){
			Pais p = new Pais();
			p.setCodigo(dom.getValor(i,"CODIGO"));
			p.setDescripcion(dom.getValor(i,"DESCRIPCION"));
			provs.add(p);			
		}					
		return provs;
	}
	
	/**
	 * Lista unidades administrativas
	 * 
	 * @return List de unidades administrativas
	 * @throws Exception
	 */
	public static List listarUnidadesAdministrativas() throws Exception{
		// Obtenemos valores dominio del EJB
		List provs = new ArrayList();			
		ValoresDominio dom = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( "GESACUNADM" , null);
		
		UnidadAdministrativa pVacia = new UnidadAdministrativa();
		pVacia.setCodigo("");
		pVacia.setDescripcion("");
		provs.add(pVacia);
		
		String desc;
		for (int i=1;i<=dom.getNumeroFilas();i++){
			UnidadAdministrativa p = new UnidadAdministrativa();
			p.setCodigo(dom.getValor(i,"CODIGO"));
			desc=StringUtils.defaultString(dom.getValor(i,"DESCRIPCION"));
			if (desc.length() > 35) desc = desc.substring(0,33) + "...";
			p.setDescripcion(desc);
			provs.add(p);			
		}					
		return provs;
	}
	
	/**
	 * Lista unidades administrativas
	 * 
	 * @return List de unidades administrativas
	 * @throws Exception
	 */
	public static ValoresDominio listarUnidadesAdministrativasArbol() throws Exception{
		// Obtenemos valores dominio del EJB
		List provs = new ArrayList();			
		return DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( "GESACARBUA" , null);
	}
	
	public static List getDescUnidadesAdministrativas(Long idUnidad) throws Exception{
		// Obtenemos valores dominio del EJB
		List provs = new ArrayList();		
		List constrains = new ArrayList();
		constrains.add(idUnidad);
		ValoresDominio dom = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( "GESACUADES" , constrains);
		for (int i=1;i<=dom.getNumeroFilas();i++){
			UnidadAdministrativa p = new UnidadAdministrativa();
			p.setCodigo(dom.getValor(i,"CODIGO"));
			p.setDescripcion(StringUtils.defaultString(dom.getValor(i,"DESCRIPCION")));
			
			provs.add(p);			
		}					
		return provs;
	}
	
	
}

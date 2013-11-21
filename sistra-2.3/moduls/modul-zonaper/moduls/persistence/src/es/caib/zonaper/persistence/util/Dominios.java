package es.caib.zonaper.persistence.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import es.caib.sistra.modelInterfaz.ConstantesDominio;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.delegate.DelegateSISTRAUtil;
import es.caib.zonaper.model.ValorDominio;

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
		ValoresDominio dom = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( ConstantesDominio.DOMINIO_GTB_PROVINCIAS , null);
		
		ValorDominio pVacia = new ValorDominio();
		pVacia.setCodigo("");
		pVacia.setDescripcion("");
		provs.add(pVacia);
		
		for (int i=1;i<=dom.getNumeroFilas();i++){
			ValorDominio p = new ValorDominio();
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
		
		if (StringUtils.isEmpty(codProv)){
			ValorDominio l = new ValorDominio();
			l.setCodigo("");
			l.setDescripcion("");
			locs.add(l);
			return locs;
		}
		
		
		List params = new ArrayList();
		params.add(codProv);
		ValoresDominio dom = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( ConstantesDominio.DOMINIO_GTB_MUNICIPIOS_PROVINCIA , params);			
		for (int i=1;i<=dom.getNumeroFilas();i++){
			ValorDominio l = new ValorDominio();
			l.setCodigo(dom.getValor(i,"CODIGO"));
			l.setDescripcion(dom.getValor(i,"DESCRIPCION"));
			locs.add(l);			
		}			
		return locs;
	}
	

	/**
	 * Obtiene detalle de unidad
	 * 
	 * @param codUA Codigo unidad administrativa
	 * @return Descripcion unidad
	 * @throws Exception
	 */
	public static String obtenerDescripcionUA(String codUA) throws Exception{
		
		if (descripcionesUA.containsKey(codUA)) return (String) descripcionesUA.get(codUA);
		
		List params = new ArrayList();
		params.add(codUA);
		ValoresDominio dom = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( ConstantesDominio.DOMINIO_SAC_NOMBRE_UNIDAD_ADMINISTRATIVA,params);			
		String descUA="";
		if (dom != null && dom.getNumeroFilas() > 0){
			descUA = dom.getValor(1,"DESCRIPCION");
			descripcionesUA.put(codUA,descUA);
		}
		return descUA;
	}
	// Cacheamos desc UA
	private static Map descripcionesUA = new HashMap();
	
	
	/**
	 * Obtiene descripcion municipio
	 * 
	 * @param codMunicipio Codigo municipio
	 * @return Descripcion municipio
	 * @throws Exception
	 */
	public static String obtenerDescripcionMunicipio(String codProvincia, String codMunicipio) throws Exception{
		List params = new ArrayList();
		params.add(codProvincia);
		params.add(codMunicipio);
		ValoresDominio dom = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( ConstantesDominio.DOMINIO_GTB_NOMBRE_MUNICIPIO,params);			
		String descMuni="";
		if (dom != null && dom.getNumeroFilas() > 0){
			descMuni = dom.getValor(1,"DESCRIPCION");			
		}
		return descMuni;
	}
	
	/**
	 * Obtiene descripcion pais
	 * 
	 * @param codMunicipio Codigo pais
	 * @return Descripcion municipio
	 * @throws Exception
	 */
	public static String obtenerDescripcionPais(String codPais) throws Exception{
		List params = new ArrayList();
		params.add(codPais);
		ValoresDominio dom = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( ConstantesDominio.DOMINIO_GTB_PAISES,params);			
		String descPais="";
		if (dom != null && dom.getNumeroFilas() > 0){
			for (int i=1;i<=dom.getNumeroFilas();i++){
				if (codPais.equals(dom.getValor(i,"CODIGO"))){
					descPais = dom.getValor(i,"DESCRIPCION");
					break;
				}										
			}					
		}
		return descPais;
	}
}

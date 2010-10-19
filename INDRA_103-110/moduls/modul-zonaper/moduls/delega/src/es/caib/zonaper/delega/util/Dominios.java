package es.caib.zonaper.delega.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.delegate.DelegateSISTRAUtil;
import es.caib.zonaper.delega.json.Localidad;
import es.caib.zonaper.delega.json.Provincia;


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
		
		Provincia pVacia = new Provincia();
		pVacia.setCodigo("");
		pVacia.setDescripcion("");
		provs.add(pVacia);
		
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
		
		if (StringUtils.isEmpty(codProv)){
			Localidad l = new Localidad();
			l.setCodigo("");
			l.setDescripcion("");
			locs.add(l);
			return locs;
		}
		
		
		List params = new ArrayList();
		params.add(codProv);
		ValoresDominio dom = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( "GEGMUNICI" , params);			
		for (int i=1;i<=dom.getNumeroFilas();i++){
			Localidad l = new Localidad();
			l.setCodigo(dom.getValor(i,"CODIGO"));
			l.setDescripcion(dom.getValor(i,"DESCRIPCION"));
			locs.add(l);			
		}			
		return locs;
	}
	
	
}

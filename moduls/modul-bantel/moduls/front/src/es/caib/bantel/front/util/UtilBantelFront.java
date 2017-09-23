package es.caib.bantel.front.util;

import org.apache.commons.lang.StringUtils;

import es.caib.bantel.front.form.BusquedaTramitesForm;
import es.caib.bantel.model.CriteriosBusquedaTramite;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.model.TraProcedimiento;
import es.caib.util.StringUtil;

public class UtilBantelFront {

	public static CriteriosBusquedaTramite crearCriteriosBusquedaTramite(
			BusquedaTramitesForm formularioBusqueda) {
		CriteriosBusquedaTramite criterios = new CriteriosBusquedaTramite();
		
		criterios.setFechaEntradaMinimo(StringUtil.cadenaAFecha(formularioBusqueda.getFechaDesde() + " 00:00:00", StringUtil.FORMATO_TIMESTAMP));
		if (StringUtils.isNotBlank(formularioBusqueda.getFechaHasta())) {
			criterios.setFechaEntradaMaximo(StringUtil.cadenaAFecha(formularioBusqueda.getFechaHasta() + " 23:59:59", StringUtil.FORMATO_TIMESTAMP));
		}
		
		if (!("-1".equals(formularioBusqueda.getIdentificadorProcedimiento()))) {
			criterios.setIdentificadorProcedimiento( formularioBusqueda.getIdentificadorProcedimiento() );
		}		
		if (StringUtils.isNotBlank(formularioBusqueda.getIdentificadorTramite())) {
			criterios.setIdentificadorTramite(formularioBusqueda.getIdentificadorTramite());
		}
		
		criterios.setNivelAutenticacion( formularioBusqueda.getNivelAutenticacion() );
		criterios.setProcesada( formularioBusqueda.getProcesada() );
		criterios.setTipo( formularioBusqueda.getTipo() );
		criterios.setUsuarioNif( formularioBusqueda.getUsuarioNif() );
		criterios.setUsuarioNombre( formularioBusqueda.getUsuarioNombre() );
		criterios.setNumeroEntrada(formularioBusqueda.getNumeroEntrada());
		return criterios;
	}
	
	public static String getDescripcionProcedimientoCombo(Procedimiento procedimiento, String lang) {		
		String id = procedimiento.getIdentificador(); 
		String desc = ((TraProcedimiento) procedimiento.getTraduccion(lang)).getDescripcion();
		
		if (desc.length() > 60) {
			desc = desc.substring(0,60) + "...";
		}
		
		return id + "-" + desc;				
	}
	
	
}

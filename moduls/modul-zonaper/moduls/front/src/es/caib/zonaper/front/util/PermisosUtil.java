package es.caib.zonaper.front.util;

import org.apache.commons.lang.StringUtils;

import es.caib.zonaper.modelInterfaz.ConstantesZPE;

public class PermisosUtil {

	// Reordena permisos para mostrarlos en el orden: TPNR
	public static String getPermisosOrdenados(String permisos){
		String permisosOrd = "";
		if (StringUtils.isEmpty(permisos)){
			return permisos;
		}
		if (permisos.indexOf(ConstantesZPE.DELEGACION_PERMISO_RELLENAR_TRAMITE) != -1){
			permisosOrd += ConstantesZPE.DELEGACION_PERMISO_RELLENAR_TRAMITE;			
		}
		if (permisos.indexOf(ConstantesZPE.DELEGACION_PERMISO_PRESENTAR_TRAMITE) != -1){
			permisosOrd += ConstantesZPE.DELEGACION_PERMISO_PRESENTAR_TRAMITE;			
		}
		if (permisos.indexOf(ConstantesZPE.DELEGACION_PERMISO_ABRIR_NOTIFICACION) != -1){
			permisosOrd += ConstantesZPE.DELEGACION_PERMISO_ABRIR_NOTIFICACION;			
		}
		if (permisos.indexOf(ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD) != -1){
			permisosOrd += ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD;			
		}
		return permisosOrd;
	}
	
}

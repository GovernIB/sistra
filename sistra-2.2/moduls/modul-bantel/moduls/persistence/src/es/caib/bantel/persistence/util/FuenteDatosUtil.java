package es.caib.bantel.persistence.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import es.caib.bantel.model.ConsultaFuenteDatos;
import es.caib.bantel.model.FilaFuenteDatos;
import es.caib.bantel.model.FiltroConsultaFuenteDatos;
import es.caib.bantel.model.ValorFuenteDatos;
import es.caib.bantel.modelInterfaz.ValoresFuenteDatosBTE;

public class FuenteDatosUtil {

	public static String getValor(FilaFuenteDatos f, String idCampo) {
		String valor = null;
		if (idCampo != null && f != null && f.getValores() != null && 	f.getValores().size() > 0) {
			for (Iterator it = 	f.getValores().iterator(); it.hasNext();) {
				ValorFuenteDatos vfd = (ValorFuenteDatos) it.next();
				if (vfd.getCampoFuenteDatos() != null && idCampo.equalsIgnoreCase(vfd.getCampoFuenteDatos().getIdentificador())) {
					valor = vfd.getValor();
					break;
				}
			}
		}
		return valor;
	}
	
	public  static ConsultaFuenteDatos decodificarConsulta(String consultaFuenteDatos) throws Exception {
		
		// Estructura
		//	SELECT campo (, campo)*
		//	FROM fuenteDatos
		//	WHERE expresion  ( [AND | OR] expresion ] )*     expresion:  campo [= | LIKE] ?    
		//  ORDER BY campo
		
		ConsultaFuenteDatos cfd = new ConsultaFuenteDatos();
		
		if (StringUtils.isBlank(consultaFuenteDatos)) {
			throw new Exception("Estructura consulta no es valida: consulta vacia");
		}
		
		int indexFrom = consultaFuenteDatos.indexOf("FROM ");
		int indexWhere = consultaFuenteDatos.indexOf("WHERE ");
		int indexOrderby = consultaFuenteDatos.indexOf("ORDER BY ");
		
		// SELECT campo (, campo)*
		consultaFuenteDatos = consultaFuenteDatos.trim();
		if (!consultaFuenteDatos.startsWith("SELECT ")) {
			throw new Exception("Estructura consulta no es valida: no empieza con SELECT");
		}
		
		if (indexFrom == -1) {
			throw new Exception("Estructura consulta no es valida: no se encuentra FROM");
		}
		String select = consultaFuenteDatos.substring("SELECT ".length(), indexFrom);
		select = select.trim();
		String campos[] = select.split(",");
		if (campos == null || campos.length == 0) {
			throw new Exception("Estructura consulta no es valida: no se encuentran campos en SELECT");
		}
		for (int i = 0; i < campos.length; i++) {
			cfd.getCampos().add(campos[i].trim());			
		}
		
		// FROM fuenteDatos
		String from;
		if (indexWhere != -1) {
			from = consultaFuenteDatos.substring(indexFrom + "FROM ".length(), indexWhere);
		} else if (indexOrderby != -1){
			from = consultaFuenteDatos.substring(indexFrom + "FROM ".length(), indexOrderby);
		} else {
			from = consultaFuenteDatos.substring(indexFrom + "FROM ".length());
		}
		from = from.trim();
		cfd.setIdFuenteDatos(from);
		
		// WHERE expresion  ( [AND | OR] expresion ] )*     expresion:  campo [= | LIKE] ?
		if (indexWhere != -1) {
			String where;
			if (indexOrderby != -1) {
				where = consultaFuenteDatos.substring(indexWhere + "WHERE ".length(), indexOrderby);
			} else {
				where = consultaFuenteDatos.substring(indexWhere + "WHERE ".length());
			}
			
			String split[] = where.split("\\?");
			for (int i = 0; i< split.length;i++) {
				FiltroConsultaFuenteDatos ffd = new FiltroConsultaFuenteDatos();
				String exp = split[i].trim();
				if (StringUtils.isBlank(exp)) {
					continue;
				}
				
				// Conector
				if (exp.startsWith("AND ")) {
					ffd.setConector(FiltroConsultaFuenteDatos.AND);
					exp = exp.substring("AND ".length());
				} else if (exp.startsWith("OR ")) {
					ffd.setConector(FiltroConsultaFuenteDatos.OR);
					exp = exp.substring("OR ".length());
				}
				
				// Operador
				if (exp.endsWith("=")) {
					ffd.setOperador(FiltroConsultaFuenteDatos.IGUAL);
				} else if (exp.endsWith(" LIKE")) {
					ffd.setOperador(FiltroConsultaFuenteDatos.LIKE);
				} else {
					throw new Exception("Estructura consulta no es valida: operador no reconocido");
				}
				
				// Campo
				int indiceIgual = exp.indexOf(" =");
				int indiceLike = exp.indexOf(" LIKE");
				exp = exp.substring(0, Math.max(indiceIgual,indiceLike)).trim();
				ffd.setCampo(exp);
				
				cfd.getFiltros().add(ffd);
				
			}
		}
		
		
		// ORDER BY
		if (indexOrderby != -1) {
			String orderBy = consultaFuenteDatos.substring(indexOrderby + "ORDER BY ".length());
			cfd.setCampoOrden(orderBy.trim());
		}
			
		return cfd;		
	}

	public static void ordenarFilas(List filas, String campoOrden) {
		if (StringUtils.isNotBlank(campoOrden) && filas != null && filas.size() > 0) {
			Collections.sort(filas, new FilaFuenteDatosComparator(campoOrden));
		}
	}

	public static ValoresFuenteDatosBTE generarValores(List filas, List campos) {
		
		ValoresFuenteDatosBTE vfd = new ValoresFuenteDatosBTE();
		
		if (filas != null && filas.size() > 0 ) {
			for (Iterator it = filas.iterator(); it.hasNext();) {
				FilaFuenteDatos ffd = (FilaFuenteDatos) it.next();
				int numFila = vfd.addFila();
				for (Iterator it2 = campos.iterator(); it2.hasNext();) {
					String idCampo = (String) it2.next();
					String valorCampo = getValor(ffd, idCampo);
					vfd.setValor(numFila, idCampo, valorCampo);
				}
			}
		}
		
		return vfd;
	}
	
}

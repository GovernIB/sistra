package es.caib.consola.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.zkutils.zk.SimacCommonUtils;


public class GTTUtilsWeb {

	/**
     * Onclick para ayuda: abre ventana ayuda.
     * 
     * @param label
     *            Parámetro label
     */
    public static void onClickBotonAyuda(final String src) {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantesWEB.SRC, src);
        final Window ventana = (Window) SimacCommonUtils.creaComponente(
                "/ayuda/windows/wAyuda.zul", null, map);
        SimacCommonUtils.abreVentanaModal(ventana);
    }
	
    /**
     * Crea celda tabla.
     * 
     * @param nuevaEtiqueta
     *            Parámetro nueva etiqueta
     * @param style
     *            Parámetro style
     * @return el listcell
     */
    public static Listcell nuevaCeldaDetalle(final Label nuevaEtiqueta,
            final String style) {
        final Listcell celdaRetorno = new Listcell();
        if (style != null) {
            celdaRetorno.setStyle(style);
        }
        celdaRetorno.appendChild(nuevaEtiqueta);
        return celdaRetorno;
    }
    
    /**
     * Método para Nueva etiqueta detalle de la clase GTTUtilsWeb.
     * 
     * @param dato
     *            Parámetro dato
     * @return el label
     */
    public static Label nuevaEtiquetaDetalle(final Object dato) {
        final Label labelRetorno = new Label();
        if (dato != null) {
            if (dato instanceof Date) {
                labelRetorno.setValue(dateToString((Date) dato));
            } else {
                labelRetorno.setValue(dato.toString());
            }
        }
        return labelRetorno;
    }
    
    /**
     * Convierte fecha en formato por defecto.
     * 
     * @param fecha
     *            Parámetro fecha
     * @return fecha formateada
     */
    public static String dateToString(final Date fecha) {
    	return dateToString(fecha, ConstantesWEB.FORMAT_FECHAS);       
    }
    
    /**
     * Convierte fecha segun formato.
     * @param fecha Fecha
     * @param formato Formato
     * @return fecha formateada
     */
    public static String dateToString(final Date fecha, String formato) {
    	if (formato == null) {
            formato = ConstantesWEB.FORMAT_FECHAS;
        }
    	 String resultado;
         if (fecha == null) {
             resultado = null;
         } else {
             final SimpleDateFormat sdf = new SimpleDateFormat(
            		 formato);
             resultado = sdf.format(fecha);
         }
         return resultado;         
    }
}

package org.ibit.rol.form.front.util;

import org.apache.commons.lang.StringUtils;
import org.ibit.rol.form.model.Captcha;
import org.ibit.rol.form.model.CheckBox;
import org.ibit.rol.form.model.ComboBox;
import org.ibit.rol.form.model.Componente;
import org.ibit.rol.form.model.Label;
import org.ibit.rol.form.model.ListBox;
import org.ibit.rol.form.model.ListaElementos;
import org.ibit.rol.form.model.RadioButton;
import org.ibit.rol.form.model.TextBox;
import org.ibit.rol.form.model.TreeBox;

/**
 * Utilidades version 2.
 * @author rsanz
 *
 */
public class UtilFrontV2 {

	/**
	 * Genera style class segun componente.
	 * @param componente Componente
	 * @return class
	 */
	public static String generateStyleClass(Componente componente) {
		String res;
		
		// Elemento
		res ="imc-element";
		
		// Nombre
		res += " imc-el-name-" + componente.getNombreLogico();
		
		// Si es un textbox hidden lo marca como hidden y no requiere nada mas
		if (componente instanceof TextBox && ((TextBox) componente).isOculto()) {
			res += " imc-el-hidden";
			return res;
		}
		
		// Colspan (Lista elementos se muestra en linea completa)
		if (componente instanceof ListaElementos) {
			res += " imc-el-6";
		} else if (componente.getColSpan() > 1){
			res += " imc-el-" + componente.getColSpan();
		}
		
		// Obligatorio
		if ( (componente instanceof TextBox && ((TextBox) componente).findValidacion("required") != null) ||
			 (componente instanceof ComboBox && ((ComboBox) componente).isObligatorio())	) {
			res += " imc-el-obligatori";
		}
		
		// Alineacion
		if ("C".equals(componente.getAlineacion()) ){
			res += " imc-el-centre";
		} else if ("D".equals(componente.getAlineacion()) ){
			res += " imc-el-dreta";
		}
		// Tabla
		if (componente.isEncuadrar()){
			res += " imc-el-table";
			if (componente instanceof Label && ((Label) componente).isEncuadrarCabecera()){
				res += " imc-el-ta-cap";				
			} else {
				res += " imc-el-ta-cela";
			}
		} else if (componente instanceof Label && ((Label) componente).isEncuadrarCabecera()){
			 res += " imc-el-table imc-el-ta-cap imc-el-ta-buida";
		}
		
		
		// Opciones particulares componente
		if (componente instanceof CheckBox) {
			res += " imc-el-check";
		}
		if (componente instanceof Captcha) {
			res += " imc-el-captcha";
		}
		if (componente instanceof ListaElementos) {
			res += " imc-el-taula imc-el-taula-detall-centrat imc-el-taula-botonera-lateral";
		}
		if (componente instanceof ListBox) {
			res += " imc-el-multiple";
			if ( ((ListBox) componente).getAltura() > 1) {
				res += " imc-el-files-" + ((ListBox) componente).getAltura();
			}
		}		
		if (componente instanceof RadioButton) {
			if ( "H".equals(((RadioButton) componente).getOrientacion())) {
				res += " imc-el-horizontal";
			}
		}
		if (componente instanceof TreeBox) {
			res += " imc-el-arbre";
			if ( ((TreeBox) componente).getAltura() > 1) {
				res += " imc-el-files-" + ((TreeBox) componente).getAltura();
			}
		}
		if (componente instanceof TextBox) {
			TextBox textBox = (TextBox) componente;
			if ( textBox.getFilas() > 1) {
				res += " imc-el-files-" + ((TextBox) componente).getFilas();
			}
			if ("IM".equals(textBox.getTipoTexto())) {
				res += " imc-el-import";
			}			
		}		
		if (componente instanceof ComboBox) {
			ComboBox comboBox = (ComboBox) componente;
			// Eventos gestionados por html
			res += " imc-el-selector-events-enlinia";
			// Indice alfabetico
			if (comboBox.isIndiceAlfabetico()) {
				res += " imc-el-index";
			}
		}
		if (componente instanceof Label) {
			// Label y mensaje: reseteamos info
			Label label = (Label) componente;
			if (!"NO".equals(label.getTipoEtiqueta())) {
				res = "imc-missatge-en-linia imc-missatge-en-linia-icona-sup ";
				if ("IN".equals(label.getTipoEtiqueta())) {
					res += "imc-missatge-en-linia-info"; 
				}
				if ("AL".equals(label.getTipoEtiqueta())) {
					res += "imc-missatge-en-linia-alerta"; 
				}
				if ("ER".equals(label.getTipoEtiqueta())) {
					res += "imc-missatge-en-linia-error"; 
				}
			}			
		}
		 
		return res;
	}	
	
	/**
	 * Genera html etiqueta. 
	 * Permite estos tags HTML pero con este formato estricto (sin espacios, minusculas, etc.): 
	 *   - HTML entities (&euro;) 
	 *   - <p> </p>
	 *   - <br/>
	 *   - <strong></strong> <b></b>
	 *   - <em> </em> <i> </i>
	 *   - <u> </u>
	 *   - <ul> </ul>
	 *   - <li> </li>
	 *   - <small></small>
	 *   - <big></big>
	 *   - <sup></sup>
	 *   - <a href="url" target="_blank"> </a> 
	 * @param etiqueta etiqueta
	 * @return etiqueta
	 */
	public static String generaHtmlEtiqueta(String etiqueta) {
		
		String[] permitidos = {"<p>","</p>","<br/>","<strong>","</strong>","<b>","</b>","<u>","</u>","<i>","</i>","<em>","</em>","<ul>","</ul>","<li>","</li>","<small>","</small>","<big>","</big>","<sup>","</sup>"};
		
		// 1. Quitamos todos los < y >
		String etiquetaTxt = quitaComparadores(etiqueta);
	    // 2. Dejamos solo los tags permitidos
	    for (int i = 0; i < permitidos.length; i++) {
	    	String permitidoTraducido = quitaComparadores(permitidos[i]); 
	    	etiquetaTxt = StringUtils.replace(etiquetaTxt, permitidoTraducido, permitidos[i]);
	    }
	    // 3. Dejamos enlaces
	    etiquetaTxt = etiquetaTxt.replaceAll("&lt;a href=\"([^<]*)\" target=\"_blank\"&gt;", "<a href=\"$1\" target=\"_blank\">");
	    etiquetaTxt = StringUtils.replace(etiquetaTxt, quitaComparadores("</a>"), "</a>");
	   
	    return etiquetaTxt;
	}

	/**
	 * Quita < y >.
	 * @param texto texto
	 * @return
	 */
	private static String quitaComparadores(String texto) {
		String etiquetaTxt = StringUtils.replace(texto, ">", "&gt;");
	    etiquetaTxt = StringUtils.replace(etiquetaTxt, "<", "&lt;");
		return etiquetaTxt;
	}
}
 
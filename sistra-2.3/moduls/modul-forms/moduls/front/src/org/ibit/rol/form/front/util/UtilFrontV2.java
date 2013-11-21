package org.ibit.rol.form.front.util;

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
		
		// Colspan (para CheckBox y Lista elementos se muestra en linea completa)
		if (componente instanceof CheckBox || componente instanceof ListaElementos) {
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
			// TODO OPCION RADIOS VERTICAL
			res += " imc-el-horizontal";
		}
		if (componente instanceof TreeBox) {
			res += " imc-el-arbre";
			if ( ((TreeBox) componente).getAltura() > 1) {
				res += " imc-el-files-" + ((TreeBox) componente).getAltura();
			}
		}
		if (componente instanceof TextBox) {
			if ( ((TextBox) componente).getFilas() > 1) {
				res += " imc-el-files-" + ((TextBox) componente).getFilas();
			}
		}
		
		return res;
	}	
}
 
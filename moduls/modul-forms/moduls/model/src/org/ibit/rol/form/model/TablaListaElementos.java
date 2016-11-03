package org.ibit.rol.form.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TablaListaElementos {
	
	private List camposTabla;
	private List datosLista;
	 
		
	public TablaListaElementos(List campos,List datos){
		this.camposTabla = campos;
		this.datosLista = (datos!=null?datos:new ArrayList());
	}	
	
	public int getNumeroFilas(){
		return datosLista.size();
	}
	
	public int getNumeroColumnas(){
		return camposTabla.size();
	}
	
	
	public List getFilasTabla(){
		List filas = new ArrayList();	
		for (int i=0;i<datosLista.size();i++){
			List fila = new ArrayList();			
			for (int j=0;j<camposTabla.size();j++){
				fila.add(getDato(i,j));							
			}			
			filas.add(fila);
		}
		return filas;		
	}
	
	public String getDato(int fila,int columna){
		Map data = (Map) datosLista.get(fila);
		Campo campo = (Campo) camposTabla.get(columna);
		
		if (campo.isIndexed()) {
			Object o = data.get(campo.getNombreLogico() + "_text");
			if (o instanceof String[]) {
				String [] textos = (String []) o;
				String dato="";
				for (int i=0;i<textos.length;i++) dato+= textos[i] + " ";
				return dato;
			}else{
				return (o!=null?o.toString():"");
			}
		}else{
			Object o = data.get(campo.getNombreLogico());
			return (o!=null?o.toString():"");
		}
	}

	public List getCamposTabla() {
		return camposTabla;
	}

}

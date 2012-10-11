package org.ibit.rol.form.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LogScript implements Serializable{

	private String script;
	private String parametros;
	private String resultado;
	private String excepcion;
	
	private List debug = new ArrayList();
	
	
	private final SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss.SSS" );
	
	
	public void addLogDebug(String log){
		if (log == null) log="";
	    debug.add(sdf.format( new Date() ) + " - " + log);
	}
	
	public List getDebug(){
		return debug;
	}

	
	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(Object resultado) {
		if (resultado == null){
			this.resultado = "[null]";
		}else if (resultado instanceof ValorPosible[]){
			ValorPosible[] valores = (ValorPosible[]) resultado;
			this.resultado = "";
			for (int i=0;i<valores.length;i++){
				this.resultado += "[ValorPosible=" + valores[i].getValor() + "]";
			}
		}else if (resultado instanceof ValorPosible){
			this.resultado = "[ValorPosible=" + ((ValorPosible) resultado).getValor() + "]";			
		}else{
			this.resultado = resultado.toString();
		}		
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(String excepcion) {
		this.excepcion = excepcion;
	}
	
	public List getLineasScript(){
		 if (script==null) return new ArrayList();
		 String lineas [] = script.split("\n");
		 return Arrays.asList(lineas);
	}
}

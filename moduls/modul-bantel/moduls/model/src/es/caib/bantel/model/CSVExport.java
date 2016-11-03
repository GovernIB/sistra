package es.caib.bantel.model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import es.caib.util.CsvDocumento;
import es.caib.util.CsvUtil;
import es.caib.util.PropertiesOrdered;

/**
 * Modeliza datos de trabajo de exportacion
 */

public class CSVExport {
	
	public final static int NUMERO_ENTRADAS_PETICION = 10;
	
	/**
	 * ID Trabajo
	 */
	private String id;
		
	private String identificadorTramite;
	
	// Resultado busqueda entradas
	private String [] entradas;
	
	private int index;
	
	// Propiedades exportacion
	private PropertiesOrdered propiedadesExport;
		
	private Date lastAccess;
	
	private List data;
	
	
	
	public CSVExport(String identificadorTramite,String [] entradas, PropertiesOrdered propiedadesExport){
				
		this.setIdentificadorTramite(identificadorTramite);
		this.setEntradas(entradas);
		this.setPropiedadesExport(propiedadesExport);
		this.setIndex(0); 
		this.setLastAccess(new Date());
		this.setData(new ArrayList());
		
		// Generamos id trabajo
		this.setId(generateId());		
		
	}
	
	public void addFilaCSV(String[] entradas){
		this.getData().add(entradas);
		this.setLastAccess(new Date());
	}
	

	private List getData() {
		return data;
	}

	private void setData(List data) {
		this.data = data;
	}	
	
	public String getId() {
		return id;
	}

	private void setId(String id) {
		this.id = id;
	}

	public String[] nextEntradas(){		
		int inc = NUMERO_ENTRADAS_PETICION;

		// Comprobamos si ya se ha terminado
		if (this.getIndex() ==  this.getEntradas().length){
			return new String[0];
		}
		
		// Comprobamos si son las ultimas entradas
		if ((this.getIndex() + NUMERO_ENTRADAS_PETICION) > this.getEntradas().length){
			inc = this.getEntradas().length - this.getIndex();
		}
				
		// Devolvemos entradas seleccionadas
		String [] res = new String[inc];
		for (int i=0;i<inc;i=i+1)
			res[i] = this.getEntradas()[this.getIndex() + i];
				
		// Actualizamos indice
		this.setIndex(this.getIndex() + inc);
		
		return res;		
	}
	
	
	public int getIndex() {
		return index;
	}

	private void setIndex(int index) {
		this.index = index;
	}

	public Date getLastAccess() {
		return lastAccess;
	}

	private void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}


	public String[] getEntradas() {
		return entradas;
	}


	private void setEntradas(String[] entradas) {
		this.entradas = entradas;
	}


	public PropertiesOrdered getPropiedadesExport() {
		return propiedadesExport;
	}


	private void setPropiedadesExport(PropertiesOrdered propiedadesExport) {
		this.propiedadesExport = propiedadesExport;
	}
	
	/*
	public byte[] toCSV() throws Exception{
		// Generamos cabeceras con el nombre de las columnas		
		String [] csv = new String[this.getPropiedadesExport().size()];
		int i=0;
		for (Iterator it = this.getPropiedadesExport().keySet().iterator();it.hasNext();){
			csv[i] = this.getPropiedadesExport().getProperty((String) it.next());
			i++;
		}
		this.getData().add(0,csv);		
		
		// Construimos csv
		ByteArrayOutputStream bos = new ByteArrayOutputStream(this.getData().size() * 500);
		// Establecemos charset compatible para guindous
		ExcelCSVPrinter ecsvp = new ExcelCSVPrinter(bos,"Cp1252"); 
		ecsvp.changeDelimiter(';');
		for (Iterator it = this.getData().iterator();it.hasNext();){
			ecsvp.writeln( (String []) it.next());			
		}
		bos.close();
		
		return bos.toByteArray();
	}
	*/
	
	public byte[] toCSV() throws Exception{
		
		CsvDocumento csvDoc = new CsvDocumento();
		
		// Generamos cabeceras con el nombre de las columnas		
		String [] cabeceras = new String[this.getPropiedadesExport().size()];
		int i=0;
		for (Iterator it = this.getPropiedadesExport().keySet().iterator();it.hasNext();){
			cabeceras[i] = this.getPropiedadesExport().getProperty((String) it.next());
			i++;
		}
		csvDoc.setColumnas(cabeceras);
			
		
		// Construimos csv
		for (int numFila = 0; numFila < this.getData().size(); numFila ++) { 
			int numFilaCsv = csvDoc.addFila();
			String [] datosFila = (String []) this.getData().get(numFila);
			for (int numCol = 0; numCol < cabeceras.length; numCol++) {
				csvDoc.setValor(numFilaCsv, cabeceras[numCol], datosFila[numCol]);
			}					
		}
		
		byte[] result = CsvUtil.exportar(csvDoc);
		
		
		return result;
	}
	
	
	
	private String generateId(){
		SecureRandom sr = new SecureRandom();
		return (new Date()).getTime() + "" + sr.nextInt(99999999); 
	}

	public String getIdentificadorTramite() {
		return identificadorTramite;
	}

	private void setIdentificadorTramite(String identificadorTramite) {
		this.identificadorTramite = identificadorTramite;
	}
	
	public boolean terminado(){
		return (this.getIndex() >= this.getEntradas().length); 
	}

}

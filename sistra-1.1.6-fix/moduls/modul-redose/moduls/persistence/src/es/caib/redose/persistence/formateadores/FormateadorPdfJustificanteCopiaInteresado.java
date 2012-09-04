package es.caib.redose.persistence.formateadores;

/**
 * Generador de PDFs para Justificante que imprime sólo la copia de interesado
 *
 */
public class FormateadorPdfJustificanteCopiaInteresado extends FormateadorPdfJustificante {
	
	public FormateadorPdfJustificanteCopiaInteresado(){
		super();
		this.setCopiaInteresado(true);
	}
		
}

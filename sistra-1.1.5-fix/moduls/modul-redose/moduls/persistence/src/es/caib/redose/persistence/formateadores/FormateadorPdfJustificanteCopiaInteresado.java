package es.caib.redose.persistence.formateadores;

/**
 * Generador de PDFs para Justificante que imprime s�lo la copia de interesado
 *
 */
public class FormateadorPdfJustificanteCopiaInteresado extends FormateadorPdfJustificante {
	
	public FormateadorPdfJustificanteCopiaInteresado(){
		super();
		this.setCopiaInteresado(true);
	}
		
}

package caib_prueba_xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Iterator;

import es.caib.xml.CargaObjetoXMLException;
import es.caib.xml.InicializacionFactoriaException;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosAnexoDocumentacion;
import es.caib.xml.registro.factoria.impl.DatosAsunto;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.xml.registro.factoria.impl.DatosOrigen;
import es.caib.xml.registro.factoria.impl.DireccionCodificada;

public class PruebaLecturaAsientoRegistral {

	private static void imprimirDatosAnexoDocumentacion (DatosAnexoDocumentacion a){
		System.out.println ("DATOS ANEXO DOCUMENTACION");
		
		System.out.println ("Codigo Normalizado: "  + a.getCodigoNormalizado());
		System.out.println ("Extracto Documento: "  + a.getExtractoDocumento());
		System.out.println ("Hash documento: "  + a.getHashDocumento());
		System.out.println ("Identificador Documento: "  + a.getIdentificadorDocumento());
		System.out.println ("Codigo RDS: "  + a.getCodigoRDS());
		System.out.println ("Nombre documento: "  + a.getNombreDocumento());
		System.out.println ("Tipo documento: "  + a.getTipoDocumento());
		System.out.println ("Es estructurado: "  + a.isEstructurado());
		System.out.println ("Esta firmado por terceros: "  + a.isFirmaTerceros ());
	}
	
	private static void imprimirDatosAsunto (DatosAsunto a){
		System.out.println ("DATOS ASUNTO");
		System.out.println ("Fecha asunto: " + a.getFechaAsunto());
		System.out.println ("Organo destino: " + a.getCodigoOrganoDestino());
		System.out.println ("Desc Organo destino: " + a.getDescripcionOrganoDestino());
		System.out.println ("Unidad destino: " + a.getCodigoUnidadAdministrativa());
		System.out.println ("Extracto asunto: " + a.getExtractoAsunto());
		//System.out.println ("Idioma Asunto: " + a.getIdiomaAsunto());
		System.out.println ("NO ESTA CLARO EL FORMATO DEL IDIOMA");
		System.out.println ("Tipo Asunto : " + a.getTipoAsunto ());
		System.out.println ("ID tramite : " + a.getIdentificadorTramite());
	}
	
	private static void imprimirDireccionCodificada (DireccionCodificada d){
		System.out.println ("DIRECCION CODIFICADA");
		System.out.println(d.toString());
	}
	
	private static void imprimirDatosInteresado (DatosInteresado i){
		System.out.println ("DATOS INTERESADO");
		
		//direccion codificada
		if (i.getDireccionCodificada() != null) {
			imprimirDireccionCodificada (i.getDireccionCodificada());
		}
		
		//formato datos interesado
		System.out.println ("Formato datos interesado: " + i.getFormatoDatosInteresado());		
		
		//nivel autenticación
		System.out.println ("Nivel autenticación: " + i.getNivelAutenticacion());
		
		//identificacion interesado
		System.out.println ("Identificación interesado: " + i.getIdentificacionInteresado());
		
		//identificacion interesado
		if (i.getIdentificacionInteresadoDesglosada() != null) {
			System.out.println ("Identificación interesado desglosada: " + i.getIdentificacionInteresadoDesglosada().toString());
		}
		
		
		//numero identificacion
		System.out.println ("Número identificación: " + i.getNumeroIdentificacion ());
						
		//tipo identificacion
		System.out.println ("Tipo identificación: " + i.getTipoIdentificacion ());
		
		//tipo interesado
		System.out.println ("Tipo interesado: " + i.getTipoInteresado ());
	}
	
	private static void imprimirDatosOrigen (DatosOrigen o){
		System.out.println ("DATOS ORIGEN");
		
		System.out.println ("Código entidad registral origen: " + o.getCodigoEntidadRegistralOrigen());
		
		System.out.println ("Fecha entrada registro: " + o.getFechaEntradaRegistro() );
		System.out.println ("Número registro: " + o.getNumeroRegistro());
		System.out.println ("Tipo registro: " + o.getTipoRegistro());
	}
	
	private static void imprimirAsientoRegistral (AsientoRegistral a){
		System.out.println ("ASIENTO REGISTRAL");
		
		//Datos anexo documentacion		
		Iterator eAnexos = a.getDatosAnexoDocumentacion().iterator();
		while (eAnexos.hasNext()){
			imprimirDatosAnexoDocumentacion ((DatosAnexoDocumentacion) eAnexos.next());
		}
		
		// datos asunto
		imprimirDatosAsunto (a.getDatosAsunto());
		
		// datos destino
		// como no se utiliza, no realizamos la prueba

		
		Iterator eDatosInteresado = a.getDatosInteresado().iterator();
		while (eDatosInteresado.hasNext()){
			imprimirDatosInteresado ((DatosInteresado) eDatosInteresado.next());
		}
		
				
		// datos origen
		imprimirDatosOrigen (a.getDatosOrigen());

	}
		
	public static void main (String args[]){
		FactoriaObjetosXMLRegistro factoria = null;
		
		System.out.println ("Creando factoria");
		try {
			factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
						
			AsientoRegistral asiento = factoria.crearAsientoRegistral (new FileInputStream ("moduls/llibreria-xml/moduls/test/asiento_registral_generado.xml"));
			
			imprimirAsientoRegistral (asiento); 
		} 
		catch (InicializacionFactoriaException e) {
			System.err.println (e.getLocalizedMessage());
			e.printStackTrace();		
		} 
		catch (CargaObjetoXMLException co){
			System.err.println ("No se ha podido mapear el contenido del fichero XML en un objeto Justificante: " + co.getLocalizedMessage());
			co.printStackTrace();
		}
		catch (FileNotFoundException fnf){
			System.out.println ("No se encuentra el fichero justificante.xml"); 
		}
		
	}
	
}

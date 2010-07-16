package caib_prueba_xml;

import java.util.Iterator;
import java.util.List;
import java.io.File;
import es.caib.xml.formsconf.factoria.FactoriaObjetosXMLConfForms;
import es.caib.xml.formsconf.factoria.ServicioConfFormsXML;
import es.caib.xml.formsconf.factoria.impl.ConfiguracionForms;
import es.caib.xml.formsconf.factoria.impl.Datos;
import es.caib.xml.formsconf.factoria.impl.Propiedad;
import es.caib.xml.util.HashtableIterable;

public class PruebaLecturaConfiguracion {
		

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {					
			FactoriaObjetosXMLConfForms factoria = ServicioConfFormsXML.crearFactoriaObjetosXML();									
			ConfiguracionForms conf = factoria.crearConfiguracionForms (new File ("moduls/llibreria-xml/moduls/test/conf_form_generado.xml"));							
			
			System.out.println ("Datos");
			Datos datos = conf.getDatos();
			System.out.println ("Idioma: " + datos.getIdioma());
			System.out.println("Version: " + datos.getVersion());
			System.out.println("UrlSistraOK: " + datos.getUrlSisTraOK());
			
			System.out.println ("Propiedades");
			Iterator props = (conf.getPropiedades() != null) ? conf.getPropiedades().iterator() : null;
			
			if (props != null){			
				while (props.hasNext()){
					Propiedad p = (Propiedad) props.next();
					System.out.println ("[" + p.getNombre() + "] = " + p.getValor());
				}
			}
			
			System.out.println ("Bloqueo");
			
			Iterator itBloqueo = (conf.getBloqueo() != null) ? conf.getBloqueo().iterator() : null;
			if (itBloqueo != null){
				while (itBloqueo.hasNext()){
					System.out.println (itBloqueo.next().toString());
				}
			}
									
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}

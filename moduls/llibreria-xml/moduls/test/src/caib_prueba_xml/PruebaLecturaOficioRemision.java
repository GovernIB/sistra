package caib_prueba_xml;

import java.io.FileInputStream;
import java.util.Iterator;

import es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision;
import es.caib.xml.oficioremision.factoria.ServicioOficioRemisionXML;
import es.caib.xml.oficioremision.factoria.impl.OficioRemision;

public class PruebaLecturaOficioRemision {
	

	private static void imprimirOficioRemision (OficioRemision oficioRemision){
		System.out.println ("OFICIO REMISION");
		System.out.println ("Titulo: " + oficioRemision.getTitulo());
		System.out.println ("Texto: " + oficioRemision.getTexto());			
		if (oficioRemision.getTramiteSubsanacion()!=null){
			System.out.println ("Desc tramite: " + oficioRemision.getTramiteSubsanacion().getDescripcionTramite());
			System.out.println ("Id tramite: " + oficioRemision.getTramiteSubsanacion().getIdentificadorTramite());
			System.out.println ("Vers tramite: " + oficioRemision.getTramiteSubsanacion().getVersionTramite());
			if ( oficioRemision.getTramiteSubsanacion().getParametrosTramite() != null){				
				for (Iterator it=oficioRemision.getTramiteSubsanacion().getParametrosTramite().keySet().iterator();it.hasNext();){
					String key = (String) it.next();
					System.out.println ("Param: " + key + " - Valor: " +  oficioRemision.getTramiteSubsanacion().getParametrosTramite().get(key));
				}
			}
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println ("Creando factoria");
			FactoriaObjetosXMLOficioRemision factoria = ServicioOficioRemisionXML.crearFactoriaObjetosXML();
			OficioRemision oficioRemision = factoria.crearOficioRemision (new FileInputStream ("moduls/llibreria-xml/moduls/test/oficio_remision_generado.xml"));			
			
			imprimirOficioRemision (oficioRemision);
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}

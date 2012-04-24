package es.caib.xml.analiza.formdoc;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.Binder;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.CTFFCheckBox;
import org.docx4j.wml.CTFFDDList;
import org.docx4j.wml.CTFFDDList.ListEntry;
import org.docx4j.wml.CTFFDDList.Result;
import org.docx4j.wml.CTFFData;
import org.docx4j.wml.CTFFName;
import org.docx4j.wml.CTFFTextInput;
import org.docx4j.wml.P;
import org.docx4j.wml.Text;
import org.w3c.dom.Node;

import es.caib.xml.analiza.Nodo;
import es.caib.xml.util.HashMapIterable;

/**
 * Analiza un formulario implementado mediante un documento (word, odt,...).
 * @author rsanz
 *
 */
public class AnalizadorDoc {

	/**
	 * Analiza documento y obtiene un map con los xpaths de los valores.
	 * @param contenido
	 * @param tipoDocumento tipo documento: docx, odt, ...
	 * @return Map con los xpaths del formulario.
	 */
	public HashMapIterable analizar(InputStream contenido, String tipoDocumento) throws AnalizadorDocException {
	
		// Control tipos validos
		//de momento solo aceptamos docx
		if (!"docx".equals(tipoDocumento)) {
			throw new AnalizadorDocException("El tipo de documento " + tipoDocumento + " no es valido");
		}
		
		// Interpretar documento y devolver xpaths
		try {
			
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(contenido);
			MainDocumentPart main = wordMLPackage.getMainDocumentPart();
			List<Object> lstParagraphs = main.getJAXBNodesViaXPath("//w:p", false);
			Binder<Node> binder = main.getBinder();
			HashMapIterable hojas = new HashMapIterable();
			String xpath = null;
			String nombre;
			P elem = null;
			//Recorremos los paragrafos
			for (int i = 0; i < lstParagraphs.size(); i++) {

				elem = (P)lstParagraphs.get(i);
				//Para cada paragrafo buscamos el ffData de inicio para obtener el nombre de los campos que hay y el tipo
				List<Object> lstFfdata = XmlUtils.getJAXBNodesViaXPath(binder, elem, ".//w:ffData", false);
				//prerequisito solo un ffData por paragrafo
				if (!lstFfdata.isEmpty()) {
					CTFFData data = (CTFFData)lstFfdata.get(0);
					List<JAXBElement<?>> lstNecoe = data.getNameOrEnabledOrCalcOnExit();
					JAXBElement<?> necoe = null;
					for (int e = 0; e < lstNecoe.size(); e++) {
						necoe = lstNecoe.get(e);
						//nombre
						if (necoe.getValue() instanceof CTFFName) {
							nombre = ((CTFFName)necoe.getValue()).getVal();
							//sustituimos el . por /
							nombre = nombre.replace('_', '/');
							xpath = "/FORMULARIO/" + nombre;
							//System.out.println("Nombre campo: " + nom);
						} else if (necoe.getValue() instanceof CTFFTextInput) {
							//si es textinput debemos buscar un elemento de texto
							List<Object> lstText = XmlUtils.getJAXBNodesViaXPath(binder, elem, ".//w:t[not(@xml:space)]", false);
							if (!lstText.isEmpty()) {
								JAXBElement<?> telem = (JAXBElement<?>)lstText.get(0);
								String valor = ((Text)telem.getValue()).getValue(); 
								Nodo nodo = new Nodo(xpath, valor);
								hojas.put(nodo.getXpath(), nodo);
							}
						} else if (necoe.getValue() instanceof CTFFCheckBox) {
							//checkbox
							BooleanDefaultTrue bValor = ((CTFFCheckBox)necoe.getValue()).getChecked();
							String valor;
							if (bValor == null || !bValor.isVal()) valor = "false";
							else valor = "true";
							
							Nodo nodo = new Nodo(xpath, valor);
							hojas.put(nodo.getXpath(), nodo);
						} else if (necoe.getValue() instanceof CTFFDDList) {
							//lista desplegable
							CTFFDDList desplegable = (CTFFDDList)necoe.getValue(); 
							Result r = desplegable.getResult();
							String valor = null;
							if (r != null) {//Confirmamos que se selecciona valor
								ListEntry seleccionado = desplegable.getListEntry().get(r.getVal().intValue());
								valor = seleccionado.getVal();
							}
							Nodo nodo = new Nodo(xpath, valor);
							hojas.put(nodo.getXpath(), nodo);
						}
						
					}	
				}
			}

			return hojas;
		} catch (Docx4JException d) {
			throw new AnalizadorDocException("Se ha producido una excepcion al cargar el documento: " + d.getMessage(), d);
		} catch (JAXBException j) {
			throw new AnalizadorDocException("Se ha producido una excepcion al obtener algun nodo del documento: " + j.getMessage(), j);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AnalizadorDocException("Se ha producido una excepcion al interpretar documento: " + ex.getMessage(), ex);
		}
		
	}
}



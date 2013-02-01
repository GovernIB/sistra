package es.caib.sistra.plugins.custodia.impl.mock;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.sistra.plugins.custodia.PluginCustodiaIntf;

/**
 * 	
 * 	Objeto MOCK para simular la sincronizacion con custodia
 *
 */
public class PluginCustodiaMock implements PluginCustodiaIntf{

	public String custodiarDocumento(DocumentoRDS documento) throws Exception {
		return documento.getReferenciaRDS().getCodigo()+"01";
	}

	public void eliminarDocumento(String codigoDocumentoCustodia) throws Exception {
		System.out.println("eliminado " + codigoDocumentoCustodia);
	}

}

package es.caib.bantel.front.util;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import es.caib.bantel.model.DocumentoBandeja;
import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.TransformacionRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.xml.ConstantesXML;
import es.caib.xml.documentoExternoNotificacion.factoria.FactoriaObjetosXMLDocumentoExternoNotificacion;
import es.caib.xml.documentoExternoNotificacion.factoria.ServicioDocumentoExternoNotificacionXML;
import es.caib.xml.documentoExternoNotificacion.factoria.impl.DocumentoExternoNotificacion;
import es.caib.zonaper.modelInterfaz.DocumentoExpedientePAD;
import es.caib.zonaper.modelInterfaz.ExcepcionPAD;

/**
 * Métodos de utilitad para obtener los lenguajes.
 */
public final class DocumentosUtil {

    private DocumentosUtil() {

    }
    

	
	/**
	 * Crea un documento RDS a partir del documento pasado por el aviso, los datos del formulario
	 * @param documento documentoRDS
	 * @param form formulario con los datos del aviso
	 * @param fir la firma del documento
	 * @return un documento RDS que ya estara pasa a pdf
	 * @throws ExcepcionPAD
	 */
	public static DocumentoRDS crearDocumentoRDS(DocumentoFirmar documento, String unidadAdministrativa) throws ExcepcionPAD
	{		
		try {
			RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
					
			DocumentoRDS docRDS = new DocumentoRDS();
			docRDS.setDatosFichero( documento.getContenidoFichero() );
			docRDS.setFechaRDS( new Date() );
			docRDS.setUnidadAdministrativa( new Long(unidadAdministrativa));
			docRDS.setTitulo( documento.getTitulo() );
			docRDS.setNombreFichero( documento.getNombre() );
			docRDS.setExtensionFichero( getExtension( documento.getNombre() ) );
		
			// Segun el tipo le asignamos un modelo
			TransformacionRDS transf = null;
			if ( documento.getTipoDocumento().equals("FICHERO"))
			{
				docRDS.setModelo( ConstantesRDS.MODELO_NOTIFICACION  );
				docRDS.setEstructurado( false );
				docRDS.setVersion( 1 );
				
				transf = new TransformacionRDS();
				transf.setBarcodePDF(true);
				transf.setConvertToPDF(true);
			}
			else
			{
				docRDS.setModelo( ConstantesRDS.MODELO_NOTIFICACION_EXTERNO  );
				docRDS.setEstructurado( true );
				docRDS.setVersion( 1 );
			}
			
			// Creamos documento
			ReferenciaRDS refRDS;
			if (transf != null) {
				refRDS = rdsDelegate.insertarDocumento( docRDS, transf );
			} else {
				refRDS = rdsDelegate.insertarDocumento( docRDS );
			}
			docRDS.setReferenciaRDS( refRDS );
			docRDS = rdsDelegate.consultarDocumento(refRDS,true);
			return docRDS;
		} catch (es.caib.redose.persistence.delegate.DelegateException e) {
			throw new ExcepcionPAD("Excepcion creando documento en el RDS",e);
		}

	}
	
	public static String getExtension(String filename){
		if(filename.lastIndexOf(".") != -1){
			return filename.substring(filename.lastIndexOf(".") + 1);
		}else{
			return "";
		}
	}
	
	public static String removeExtension(String filename){
		if(filename.lastIndexOf(".") != -1){
			return filename.substring(0,filename.lastIndexOf("."));
		}else{
			return filename;
		}
	}
	
	public static String formatearFichero(String rutaFitxer) {
		
		rutaFitxer = rutaFitxer.replace("\\","|");
		rutaFitxer = rutaFitxer.replace("|","\\\\");
		return rutaFitxer;
	}
	
	public static boolean noExisteDocumento(String titulo, HttpServletRequest request, String llista){
		boolean noExiste = true;
		ArrayList documentos;
		if(request.getSession().getAttribute(llista) == null){
			documentos = new ArrayList();
		}else{
			documentos = (ArrayList)request.getSession().getAttribute(llista);
		}
		for(int i=0;i<documentos.size() && noExiste;i++){
			DocumentoExpedientePAD doc = (DocumentoExpedientePAD)documentos.get(i);
			if(titulo.equals(doc.getTitulo())){
				noExiste = false;
			}
		}
		return noExiste;
	}
	
	public static boolean extensionCorrecta(String nombre){
		boolean correcta = false;
		nombre = getExtension(nombre);
		String mensajeOk = MensajesUtil.getValue("aviso.extensiones.fichero.formato");
		if(mensajeOk != null && !"".equals(mensajeOk) && nombre != null && !"".equals(nombre) ){
			StringTokenizer st = new StringTokenizer(mensajeOk,",");
			while(st.hasMoreTokens() && !correcta){
				String aux = st.nextToken();
				if(aux != null && aux.trim().equals(nombre.trim())){
					correcta = true;
				}
			}
		}
		return correcta;
	}
	
	// Carga firmas y codigo de custodia en la request
	public static void cargarFirmasDocumentoBandeja(DocumentoBandeja documento, HttpServletRequest request) throws Exception{
		RdsDelegate rdsDeleg = DelegateRDSUtil.getRdsDelegate();
		
		//	 vamos a buscar las firmas de los documentos si existen y las meteremos en la request
		if(documento != null && documento.getRdsCodigo() != null && documento.getRdsClave() != null){
			ReferenciaRDS ref =  new ReferenciaRDS(documento.getRdsCodigo(),documento.getRdsClave());
			if (ref.getCodigo() > 0){
				String codigo = ref.getCodigo()+"";
				DocumentoRDS doc = rdsDeleg.consultarDocumento(ref,false);
				// Cargamos firma
				if(doc != null && doc.getFirmas() != null){
					request.setAttribute(codigo + "",doc.getFirmas());
				}
				// Cargamos codigo custodia
				if (doc != null && StringUtils.isNotBlank(doc.getCodigoDocumentoCustodia()) ) {
					request.setAttribute("CUST-" + codigo, doc.getCodigoDocumentoCustodia());
				}
				
			}
		}
	}
	
	// Carga firmas, codigo de custodia y url doc externo notificacion en la request
	public static void cargarFirmasDocumentosExpedientePad(List documentos, HttpServletRequest request, String tipo) throws Exception{
		RdsDelegate rdsDeleg = DelegateRDSUtil.getRdsDelegate();
		
		//		vamos a buscar las firmas de los documentos si existen y las meteremos en la request
		if(documentos != null){
			ReferenciaRDS ref = null;
			Long codigo = null;
			for(int i=0;i<documentos.size();i++){
				DocumentoExpedientePAD docTipo = (DocumentoExpedientePAD)documentos.get(i);
				ref = new ReferenciaRDS(docTipo.getCodigoRDS(),docTipo.getClaveRDS());
				if (ref.getCodigo() > 0){
					codigo = docTipo.getCodigoRDS();
					DocumentoRDS doc = rdsDeleg.consultarDocumento(ref,false);
					// Cargamos firmas
					if(doc != null && doc.getFirmas() != null){
						request.setAttribute(codigo.toString(),doc.getFirmas());
					}
					// Cargamos codigo custodia
					if (doc != null && StringUtils.isNotBlank(doc.getCodigoDocumentoCustodia()) ) {
						request.setAttribute("CUST-" + codigo, doc.getCodigoDocumentoCustodia());
					}
					// Establecemos en la request si es una referencia a un doc externo (modelo GE0013NOTIFEXT)
					if (doc.getModelo().equals(ConstantesRDS.MODELO_NOTIFICACION_EXTERNO)) {
						// Buscamos url
						doc = rdsDeleg.consultarDocumento(ref, true);
						FactoriaObjetosXMLDocumentoExternoNotificacion factoria = ServicioDocumentoExternoNotificacionXML.crearFactoriaObjetosXML();
						factoria.setEncoding(ConstantesXML.ENCODING);
						factoria.setIndentacion(true);
						DocumentoExternoNotificacion documentoExternoNotificacion = factoria.crearDocumentoExternoNotificacion(new ByteArrayInputStream(doc.getDatosFichero()));
						request.setAttribute("URL-"+docTipo.getCodigoRDS(),documentoExternoNotificacion.getUrl());
					}
				}
			}
		}
	}
	
}

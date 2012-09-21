package es.caib.bantel.front.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleAvisoForm;
import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.TransformacionRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
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
	
}

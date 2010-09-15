package es.caib.sistra.persistence.ejb;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.RemoveException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.modelInterfaz.DocumentoConsulta;
import es.caib.sistra.modelInterfaz.FormularioConsulta;
import es.caib.sistra.persistence.intf.ConsultaEJB;

/**
 * EJB que sirve para generar los interfaces para crear los ejbs que resuelven
 * tramites de tipo consulta 
 *
 * @ejb.bean
 *  name="sistra/persistence/ConsultaPadronEJB"
 *  jndi-name="es.caib.sistra.persistence.ConsultaPadronEJB"
 *  type="Stateless"
 *  view-type="remote"
 *  
 *  @ejb.home remote-class="es.caib.sistra.persistence.intf.ConsultaEJBHome"
 *  @ejb.interface remote-class="es.caib.sistra.persistence.intf.ConsultaEJB"
 */
public class ConsultaPadronEJB implements ConsultaEJB {

	protected static Log log = LogFactory.getLog(ConsultaPadronEJB.class);
	
	private final static String NODE_NATURAL_DE = "<CDTNATURALSOLICITANTE/>";
	private final static String NODE_FECHA_NACIMIENTO = "<CDTFECHANACIMIENTOSOLICITANTE/>";
	private final static String NODE_DOMICILIO = "<CDTDOMICILIOSOLICITANTE/>";
	private final static String NODE_DISTRITO = "<CDTDISTRITOSOLICITANTE/>";
	private final static String NODE_SECCION = "<CDTSECCIONSOLICITANTE/>";
	private final static String NODE_HOJA = "<CDTHOJASOLICITANTE/>";
	private final static String NODE_FECHA_ALTA_PADRON = "<CDTFECHAALTAPADRONSOLICITANTE/>";
	
	private final static String NATURAL_DE = "SANTA MARGALIDA (ILLES BALEARS)";
	private final static String FECHA_NACIMIENTO = "14/05/1974";
	private final static String DOMICILIO = "CARRER LLIBERTAT 0003 Piso:P01\nSANTA MARGALIDA\nSANTAMARGALIDA (BALEARES)";
	private final static String DISTRITO = "01";
	private final static String SECCION = "002";
	private final static String HOJA = "0000000702";
	private final static String FECHA_ALTA_PADRON = "29/07/1993";
	
	
	/**
     * @ejb.create-method
     */
	public void ejbCreate() throws CreateException {
		log.info("ejbCreate: " + this.getClass());
	}

	/**
	 * 
	 * Recibe formularios y obtiene lista de documentos
	 * 
     * @ejb.interface-method
     */
    public DocumentoConsulta[] realizarConsulta(FormularioConsulta forms[]) {
    	
    	if (forms.length!=0){//Hay formularios
    		//Obtenemos el xml y lo reescribimos con los datos que se reciben (Actualmente harcodeados)
    		String xmlForm = forms[0].getXml();
    		String xmlSalida = "";
    		int inicio = 0;
    		int fin =0;
    		
    		// Natural de
    		fin = xmlForm.indexOf(NODE_NATURAL_DE);
    		xmlSalida = xmlForm.substring(inicio, fin) + "<CDTNATURALSOLICITANTE>" + NATURAL_DE + "</CDTNATURALSOLICITANTE>";
    		
    		// Fecha nacimiento
    		inicio=fin + NODE_NATURAL_DE.length();
    		fin = xmlForm.indexOf(NODE_FECHA_NACIMIENTO);
    		xmlSalida = xmlSalida + xmlForm.substring(inicio, fin) + "<CDTFECHANACIMIENTOSOLICITANTE>" + FECHA_NACIMIENTO + "</CDTFECHANACIMIENTOSOLICITANTE>";
    		
    		//domicilio
    		inicio=fin + NODE_FECHA_NACIMIENTO.length();
    		fin = xmlForm.indexOf(NODE_DOMICILIO);
    		xmlSalida = xmlSalida + xmlForm.substring(inicio, fin) + "<CDTDOMICILIOSOLICITANTE>" + DOMICILIO + "</CDTDOMICILIOSOLICITANTE>";
    		
    		//distrito
    		inicio=fin +NODE_DOMICILIO.length();
    		fin = xmlForm.indexOf(NODE_DISTRITO);
    		xmlSalida = xmlSalida + xmlForm.substring(inicio, fin) + "<CDTDISTRITOSOLICITANTE>" + DISTRITO + "</CDTDISTRITOSOLICITANTE>";
    		
    		//seccion
    		inicio=fin +NODE_DISTRITO.length();
    		fin = xmlForm.indexOf(NODE_SECCION);
    		xmlSalida = xmlSalida + xmlForm.substring(inicio, fin) + "<CDTSECCIONSOLICITANTE>" + SECCION + "</CDTSECCIONSOLICITANTE>";
    		
    		//hoja
    		inicio=fin +NODE_SECCION.length();
    		fin = xmlForm.indexOf(NODE_HOJA);
    		xmlSalida = xmlSalida + xmlForm.substring(inicio, fin) + "<CDTHOJASOLICITANTE>" + HOJA + "</CDTHOJASOLICITANTE>";
    		
    		//fecha alta
    		inicio=fin +NODE_HOJA.length();
    		fin = xmlForm.indexOf(NODE_FECHA_ALTA_PADRON);
    		xmlSalida = xmlSalida + xmlForm.substring(inicio, fin) + "<CDTFECHAALTAPADRONSOLICITANTE>" + FECHA_ALTA_PADRON + "</CDTFECHAALTAPADRONSOLICITANTE>";
    		
    		//Resto de xml
    		inicio=fin +NODE_FECHA_ALTA_PADRON.length();
    		xmlSalida = xmlSalida +xmlForm.substring(inicio, xmlForm.length()-1);
    		
    		//Generamos una lista de documentos a devolver
    		List docs = new ArrayList();
        	DocumentoConsulta doc = new DocumentoConsulta();
        	doc.setTipoDocumento(DocumentoConsulta.TIPO_DOCUMENTO_XML);
        	doc.setXml(xmlSalida);
        	doc.setNombreFichero(forms[0].getIdentificador());
        	doc.setModelo("SM0006CEEM");
        	doc.setVersion(1);
        	doc.setPlantilla("PDF");//Tipo de plantilla
        	doc.setNombreDocumento("Sol·licitud Certificat d'Empadronament");
        	
//        	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
//        	DocumentoRDS docRDS = new DocumentoRDS();
//        	docRDS.set
//        	ReferenciaRDS refRDS = new ReferenciaRDS() 
//        	rds.consultarDocumento(refRds, true);
        	
        	docs.add(doc);

    		// Convert a collection to DocumentoConsulta[], which can store objects
        	DocumentoConsulta[] arrayDocs = (DocumentoConsulta[]) docs.toArray(new DocumentoConsulta[0]);
    		
    		return arrayDocs;
    		
    	}else{//No hay formularios
    		return null;
    	}    	
    }

	public EJBHome getEJBHome() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public Handle getHandle() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPrimaryKey() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isIdentical(EJBObject arg0) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public void remove() throws RemoteException, RemoveException {
		// TODO Auto-generated method stub
		
	}
	
//	public boolean existeDocumento (String nombreRDS) throws Exception{
//		
//		log.debug("INICIO existeDocumento: "+nombreRDS);
//		BaseController bd = null;
//		Connection con = null;
//		ResultSet rs = null;
//		
//		try{
////			bd = BaseController
//			return false;
//		}finally{
//			
//		}
//	}
}

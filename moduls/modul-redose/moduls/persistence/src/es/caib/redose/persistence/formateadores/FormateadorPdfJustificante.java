package es.caib.redose.persistence.formateadores;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import es.caib.redose.model.PlantillaIdioma;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.redose.persistence.ejb.ResolveRDS;
import es.caib.redose.persistence.util.UtilRDS;
import es.caib.util.StringUtil;
import es.caib.xml.ConstantesXML;
import es.caib.xml.datospropios.factoria.ConstantesDatosPropiosXML;
import es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios;
import es.caib.xml.datospropios.factoria.ServicioDatosPropiosXML;
import es.caib.xml.datospropios.factoria.impl.Dato;
import es.caib.xml.datospropios.factoria.impl.DatosPropios;
import es.caib.xml.datospropios.factoria.impl.Documento;
import es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision;
import es.caib.xml.oficioremision.factoria.ServicioOficioRemisionXML;
import es.caib.xml.oficioremision.factoria.impl.OficioRemision;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosAnexoDocumentacion;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.xml.registro.factoria.impl.Justificante;
import es.indra.util.pdf.Lista;
import es.indra.util.pdf.NumeroPaginaStamp;
import es.indra.util.pdf.ObjectStamp;
import es.indra.util.pdf.PDFDocument;
import es.indra.util.pdf.Parrafo;
import es.indra.util.pdf.Propiedad;
import es.indra.util.pdf.Seccion;
import es.indra.util.pdf.Subseccion;
import es.indra.util.pdf.Tabla;
import es.indra.util.pdf.UtilPDF;


/**
 * Generador de PDFs para XMLs de Formularios 
 *
 */
public class FormateadorPdfJustificante implements FormateadorDocumento{
	
	/**
	 * Cuando hay documentación para aportar indica si solo se debe generar la copia para el interesado
	 * Este atributo permitirá extender este formateador para implementar un formateador para la copia 
	 * de interesado.
	 *  
	 */
	private boolean copiaInteresado = false;
	
	/**
	 * Genera PDF a partir Xpath
	 */
	public DocumentoRDS formatearDocumento(DocumentoRDS documento, PlantillaIdioma plantilla,List usos) throws Exception {
		
		boolean hayDocumentacionAportar = false;
		boolean registroSalida = false;
		PDFDocument docPDF;
		String cabecera;
		String letras[] = {"A","B","C","D","E","F"};
		int numSecciones = 0;
				
		// Cargamos fichero de properties con los textos a utilizar
		Properties props = new Properties();
		props.load(new ByteArrayInputStream(plantilla.getArchivo().getDatos()));
		String urlLogo = props.getProperty("urlLogo");
		
		// Parseamos asiento
    	FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
		Justificante justificante = factoria.crearJustificanteRegistro(new ByteArrayInputStream (documento.getDatosFichero()));
		AsientoRegistral asiento = justificante.getAsientoRegistral();
		// -- Leemos lista de documentos aportados		
		Lista lista = new Lista();    	
    	ReferenciaRDS refDatosPropios=null;
    	ReferenciaRDS refOficioRemision=null;
    	ReferenciaRDS refAvisoNotificacion=null;
    	Iterator itd = asiento.getDatosAnexoDocumentacion().iterator();
    	while (itd.hasNext()){
    		DatosAnexoDocumentacion da = (DatosAnexoDocumentacion) itd.next();    	    		
    		if (da.getTipoDocumento().charValue() == ConstantesAsientoXML.DATOSANEXO_DATOS_PROPIOS){
    			refDatosPropios = 
    				ResolveRDS.getInstance().resuelveRDS(Long.parseLong(da.getCodigoRDS()));
    		}
    		else if (da.getTipoDocumento().charValue() == ConstantesAsientoXML.DATOSANEXO_OFICIO_REMISION){
    			refOficioRemision = 
    				ResolveRDS.getInstance().resuelveRDS(Long.parseLong(da.getCodigoRDS()));
    		}
    		else if (da.getTipoDocumento().charValue() == ConstantesAsientoXML.DATOSANEXO_AVISO_NOTIFICACION){
    			refAvisoNotificacion = 
    				ResolveRDS.getInstance().resuelveRDS(Long.parseLong(da.getCodigoRDS()));
    		}
    		else
    		{
    			lista.addCampo(da.getExtractoDocumento());
    		}
    	}
    	
    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    	DatosPropios datosPropios = null;
    	if(asiento.getDatosOrigen().getTipoRegistro().charValue() != ConstantesAsientoXML.TIPO_REGISTRO_SALIDA){
    		//Parseamos datos propios si es de entrada
    		if (refDatosPropios != null) {
		    	DocumentoRDS docRDS = rds.consultarDocumento(refDatosPropios);
		    	FactoriaObjetosXMLDatosPropios factoriaDatosPropios = ServicioDatosPropiosXML.crearFactoriaObjetosXML();
		    	datosPropios = factoriaDatosPropios.crearDatosPropios(new ByteArrayInputStream (docRDS.getDatosFichero()));
    		}else{
    			// Registro de entrada externo
    			datosPropios = null;
    		}
    	}else {
    		if (refOficioRemision == null) throw new Exception("No se encuentra documento de oficio de rimisión");
    		if (refAvisoNotificacion == null) throw new Exception("No se encuentra documento de aviso de notificación");
    	}
    	// Según el tipo de asiento establecemos textos
		String tipoRegistro = "envio";
    	switch (asiento.getDatosOrigen().getTipoRegistro().charValue()){
    		case ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA:
    			cabecera = props.getProperty("cabecera.registroEntrada");
    			tipoRegistro = "registro";
    			break;
    		case ConstantesAsientoXML.TIPO_ENVIO:
    			cabecera = props.getProperty("cabecera.envioBandeja");
    			break;	    		
    		case ConstantesAsientoXML.TIPO_PREREGISTRO:	    			
    			cabecera = props.getProperty("cabecera.preRegistro");
    			hayDocumentacionAportar = true;
    			break;	    		
    		case ConstantesAsientoXML.TIPO_PREENVIO:
    			cabecera = props.getProperty("cabecera.preEnvio");
    			hayDocumentacionAportar = true;
    			break;	
    		case ConstantesAsientoXML.TIPO_REGISTRO_SALIDA:
    			cabecera = props.getProperty("cabecera.registroSalida");
    			tipoRegistro = "registro";
    			registroSalida = true;
    			break;
    		default:
    			cabecera = "JUSTIFICANTE";	    			
    	}	    	
		    	
    	// CABECERA: TITULO Y LOGO
    	// Creamos documento con la imagen y el título
		docPDF = new PDFDocument(urlLogo,cabecera);		
		// Código de Barras con el código del justificante
    	//docPDF.setBarcode(Long.toString(documento.getReferenciaRDS().getCodigo()));
    	    	
		// SECCION DATOS REGISTRO
    	Seccion seccion = new Seccion(letras[numSecciones], props.getProperty("datosRegistro." + tipoRegistro + ".titulo"));
    	numSecciones ++;
    		// Numero de registro  -> para preregistro añadimos D.C.
    	String numreg = justificante.getNumeroRegistro();
    	if (asiento.getDatosOrigen().getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_PREREGISTRO || 
    		asiento.getDatosOrigen().getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_PREENVIO){
    			numreg = numreg + "         "+ props.getProperty("datosRegistro.digitoControl") + StringUtil.calculaDC(numreg);
    	}    	
		Propiedad propiedad = new Propiedad(props.getProperty("datosRegistro." + tipoRegistro + ".numeroRegistro"),
				                             numreg);
		seccion.addCampo(propiedad);
		propiedad = new Propiedad(props.getProperty("datosRegistro." + tipoRegistro + ".fechaSolicitud"),
				StringUtil.timestampACadena(justificante.getFechaRegistro()));
		seccion.addCampo(propiedad);
		propiedad = new Propiedad(props.getProperty("datosRegistro.asunto"),
								  asiento.getDatosAsunto().getExtractoAsunto());		
		seccion.addCampo(propiedad);
		
		String destinatario = asiento.getDatosAsunto().getCodigoOrganoDestino();
		if ( StringUtils.isNotEmpty(asiento.getDatosAsunto().getDescripcionOrganoDestino())) 
			destinatario = asiento.getDatosAsunto().getDescripcionOrganoDestino();
		if(asiento.getDatosOrigen().getTipoRegistro().charValue() != ConstantesAsientoXML.TIPO_REGISTRO_SALIDA){
			propiedad = new Propiedad(props.getProperty("datosRegistro.organoDestino"),destinatario);
		}else{
			propiedad = new Propiedad(props.getProperty("datosRegistro.organoRemitente"),destinatario);
		}
		seccion.addCampo(propiedad);		
    	
    	String tipoInteresado = "";
    	String keyTipoId;
    	char nivelAutenticacion='?';
    	
    	boolean existeRepresentado = false;
    	for (Iterator it =  asiento.getDatosInteresado().iterator();it.hasNext();){    	    	
    		DatosInteresado di = (DatosInteresado) it.next();
    		if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO)){
    			existeRepresentado = true;
    			break;
    		}
    	}
    	
    	for (Iterator it =  asiento.getDatosInteresado().iterator();it.hasNext();){    		
    		DatosInteresado di = (DatosInteresado) it.next();
    		if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE)){
    			if (existeRepresentado){
    				tipoInteresado="Representante";
    			}else{
    				tipoInteresado="";
    			}    			    			
    			nivelAutenticacion = di.getNivelAutenticacion()!=null?di.getNivelAutenticacion().charValue():'?';
    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO)){
    			tipoInteresado="Representado";
    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_DELEGADO)){
    			tipoInteresado="Delegado";
    		}
    		
			   
			switch (di.getTipoIdentificacion().charValue()){
				case ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIF:
					keyTipoId = "datosRegistro.tipoIdentificacion.nif" + tipoInteresado;
					break;
				case ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF:
					keyTipoId = "datosRegistro.tipoIdentificacion.cif" + tipoInteresado;
					break;
				default:
					keyTipoId = "datosRegistro.tipoIdentificacion.otros";
					break;	
			}
			
			// Añadimos sólo si se ha informado el dni
			if (StringUtils.isNotEmpty(di.getNumeroIdentificacion())){
				// Nombre
				propiedad = new Propiedad(props.getProperty("datosRegistro.nombre"+tipoInteresado),
	                    di.getIdentificacionInteresado());
				seccion.addCampo(propiedad);
				// Nif
				propiedad = new Propiedad(props.getProperty(keyTipoId),di.getNumeroIdentificacion());
				seccion.addCampo(propiedad);
			}
    	}
    	
    	if(!registroSalida){
    		// Identificador de persistencia (sólo para anónimo)
	    	if (nivelAutenticacion == 'A' && datosPropios != null && datosPropios.getInstrucciones() != null && StringUtils.isNotEmpty(datosPropios.getInstrucciones().getIdentificadorPersistencia())){
		    	propiedad = new Propiedad(props.getProperty("datosRegistro.identificadorPersistencia"),
		    			datosPropios.getInstrucciones().getIdentificadorPersistencia());		    		    
				seccion.addCampo(propiedad);
	    	}
	    	// Notificacion telematica
	    	if (datosPropios != null && datosPropios.getInstrucciones() != null && StringUtils.isNotEmpty(datosPropios.getInstrucciones().getHabilitarNotificacionTelematica())){
		    	propiedad = new Propiedad(props.getProperty("datosRegistro.notificacionTelematica"),
			    			("S".equals(datosPropios.getInstrucciones().getHabilitarNotificacionTelematica()))?props.getProperty("datosRegistro.notificacionTelematica.si"):props.getProperty("datosRegistro.notificacionTelematica.no"));		    		    
				seccion.addCampo(propiedad);
	    	}
	    	// Avisos
	    	/*
	    	if (datosPropios != null && datosPropios.getInstrucciones() != null && StringUtils.isNotEmpty(datosPropios.getInstrucciones().getHabilitarAvisos())){
	    		String valueAvisos;
	    		if ("S".equals(datosPropios.getInstrucciones().getHabilitarNotificacionTelematica())) {
	    			valueAvisos = datosPropios.getInstrucciones().getAvisoEmail() ;
	    			if (StringUtils.isNotBlank(datosPropios.getInstrucciones().getAvisoSMS())) {
	    				valueAvisos +=  " - " + datosPropios.getInstrucciones().getAvisoSMS();
	    			}
	    		} else {
	    			valueAvisos = props.getProperty("datosRegistro.avisos.no");
	    		}
	    		propiedad = new Propiedad(props.getProperty("datosRegistro.avisos"),valueAvisos);		    		    
				seccion.addCampo(propiedad);
	    	}
	    	*/
    	}
    	docPDF.addSeccion(seccion);
		
    	//Si no es registro de salida haremos lo mismo que se hacia hasta el momento.
    	if(!registroSalida){
	    	// SECCION DATOS SOLICITUD   	    
	    	if (datosPropios != null && datosPropios.getSolicitud() != null) {
	    		seccion = new Seccion(letras[numSecciones], props.getProperty("datosSolicitud.titulo"));
	    		numSecciones++;
	    		Subseccion ss = null;    	
		    	for (Iterator it = datosPropios.getSolicitud().getDato().iterator();it.hasNext();){
		    		Dato dato = (Dato) it.next();
		    		if(dato.getTipo().charValue() == ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_BLOQUE){
		    			if(ss !=  null){
		    				seccion.addCampo(ss);
		    			}
		    			ss = new Subseccion(dato.getDescripcion());
		    		}
		    		else if(dato.getTipo().charValue() == ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_CAMPO){
		    			propiedad = new Propiedad(dato.getDescripcion(),dato.getValor());
		    			if(ss == null)
		    			{
		    				seccion.addCampo(propiedad);
		    			}
		    			else
		    			{
		    				ss.addCampo(propiedad);
		    			}
		    		}
		    	}
		    	if(ss != null)
		    	{
		        	seccion.addCampo(ss);
		    	}
		    	docPDF.addSeccion(seccion);
	    	}
    	
	    	// SECCION DOCUMENTOS APORTADOS    	
	    	seccion = new Seccion(letras[numSecciones], props.getProperty("documentosAportados.titulo"));
	    	numSecciones++;
	    	seccion.addCampo(lista);
	    	docPDF.addSeccion(seccion);
    	    	    	 
    	    if(hayDocumentacionAportar)
	    	{    		
	    		// SECCION DE DOCUMENTACION A APORTAR
	    		seccion = new Seccion(letras[numSecciones], props.getProperty("documentacionAportar.titulo"));
	    		seccion.setKeepTogether(true);
	    		numSecciones++;
	    		Vector columnas = new Vector();
	    		columnas.add(props.getProperty("documentacionAportar.documento"));
	    		columnas.add(props.getProperty("documentacionAportar.accion"));
	    		Vector campos = new Vector();
	    		boolean compulsar = false;
	    		boolean fotocopia = false;    		
	    		String key;
	    		Vector cp;    		    		
	    		
	    		for (Iterator it = datosPropios.getInstrucciones().getDocumentosEntregar().getDocumento().iterator();it.hasNext();){
	    			
	    			key = "documentacionAportar.accion.";
	    			
	    			Documento docPres = (Documento) it.next();
	    			
	    			switch (docPres.getTipo().charValue()){
	    				case ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_JUSTIFICANTE:
	    					/// El justificante no se muestra en la tabla de documentación a aportar (se presupone)
	    					continue;
	    				case ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_PAGO:
	    					key += "pago";
	    					break;
	    				case ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO:
	    					key += "firmar";
	    					break;	
	    				case ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO:
	    					fotocopia = docPres.isFotocopia().booleanValue();
	    	    			compulsar = docPres.isCompulsar().booleanValue();    	    			
	    	    			key += fotocopia ? "fotocopia" : "";
	    	    			key += compulsar ? "compulsar" : "";
	    	    			key += (!fotocopia && !compulsar) ? "original" : "";    	    			
	    					break;		
	    			}
	    			
	    			cp = new Vector();
	    			cp.add(docPres.getTitulo());
	    			cp.add(props.getProperty(key));
	    			campos.add(cp);
	    		}
	    		
		   		Tabla tabla =  new Tabla(columnas,campos);
		   		seccion.addCampo(tabla);
		    		
		   		docPDF.addSeccion(seccion);
	    	
		   		// SECCION DE FIRMA
				Parrafo p;
				Seccion seccionFirmar = new Seccion(letras[numSecciones], props.getProperty("declaracion.titulo"));
				seccionFirmar.setKeepTogether(true);
				numSecciones++;
				int numBloques = Integer.parseInt(props.getProperty("declaracion.numBloques"));
				String keyBloques = "declaracion.bloque";
				for(int i=0; i<numBloques; i++)
				{
					String texto = props.getProperty(keyBloques + (i+1) + ".texto");
					if (i == 0){
						// Fecha limite entrega: texto particularizado o texto por defecto
						if (StringUtils.isNotEmpty(datosPropios.getInstrucciones().getTextoFechaTopeEntrega())){
							texto += " " + datosPropios.getInstrucciones().getTextoFechaTopeEntrega();
						}else{						
							texto += props.getProperty(keyBloques + (i+1) + ".texto.fechaLimite") + " " + StringUtil.fechaACadena(datosPropios.getInstrucciones().getFechaTopeEntrega()) + ".";
						}
					}    				
					p = new Parrafo(texto,Integer.parseInt(props.getProperty(keyBloques + (i+1) + ".alignment")));
					seccionFirmar.addCampo(p);
				}
				docPDF.addSeccion(seccionFirmar);
	    	}
    	}else{
    		
    		//SECCION OFICIO REMISION
    		seccion = new Seccion(letras[numSecciones], props.getProperty("oficioRemision.titulo"));//props.getProperty("datosSolicitud.titulo"));
    		numSecciones++;
    		FactoriaObjetosXMLOficioRemision factOfi = ServicioOficioRemisionXML.crearFactoriaObjetosXML();
    		factOfi.setEncoding(ConstantesXML.ENCODING);
    		DocumentoRDS docRDSOficio = rds.consultarDocumento(refOficioRemision);
    		OficioRemision oficioRemision =factOfi.crearOficioRemision(new ByteArrayInputStream(docRDSOficio.getDatosFichero()));
    		propiedad = new Propiedad(props.getProperty("oficioRemision.tituloOficio"),  oficioRemision.getTitulo());		
    		seccion.addCampo(propiedad);
    		propiedad = new Propiedad(props.getProperty("oficioRemision.textoOficio"),  oficioRemision.getTexto());		
    		seccion.addCampo(propiedad);
    		if(oficioRemision.getTramiteSubsanacion() != null){
    			propiedad = new Propiedad(props.getProperty("oficioRemision.tramiteSubsanacion"),  oficioRemision.getTramiteSubsanacion().getDescripcionTramite());
    			seccion.addCampo(propiedad);
    		}
    		
    		
    		docPDF.addSeccion(seccion);
    		
    		//SECCION DOCUMENTOS APORTADOS    	
    		seccion = new Seccion(letras[numSecciones], props.getProperty("documentosAportados.titulo"));
    		numSecciones++;
    		seccion.addCampo(lista);
    		docPDF.addSeccion(seccion);
    	}
    	
    	// Generamos pdf
    	ByteArrayOutputStream bos;
    	
    	// Si hay documentacion a aportar generamos dos copias
    	if(hayDocumentacionAportar){
    		byte[] contentPDF1=null,contentPDF2;
    		
    		// Copia administracion    		
    		if (!this.isCopiaInteresado()){
	    		bos = new ByteArrayOutputStream();
	    		docPDF.setSello(true);
	    		docPDF.setTextoSello(props.getProperty("sello.registroEntrada"));   		
	    		docPDF.setPie(props.getProperty("pie.ejemplar.administracion"));
	    		docPDF.generate(bos);
	    		bos = this.establecerNumerosPagina(new ByteArrayInputStream(bos.toByteArray()));
	    		contentPDF1 = bos.toByteArray();
    		}
    		
    		// Copia interesado (con espacio para sello)
    		bos = new ByteArrayOutputStream();
    		docPDF.setSello(true);
    		docPDF.setTextoSello(props.getProperty("sello.registroEntrada"));
    		docPDF.setPie(props.getProperty("pie.ejemplar.interesado"));
    		docPDF.generate(bos);    		
    		bos = this.establecerNumerosPagina(new ByteArrayInputStream(bos.toByteArray()));
    		contentPDF2 = bos.toByteArray();
    		    		
    		if (!this.isCopiaInteresado()){
    			// Concatenamos PDFs copia administración e interesado
	    		bos = new ByteArrayOutputStream();
	    		InputStream pdfs[] = {new ByteArrayInputStream(contentPDF1),new ByteArrayInputStream(contentPDF2)};
	    		UtilPDF.concatenarPdf(bos,pdfs); 
    		}
    	
    	}else{
        // No hay documentación a aportar, generamos justificante sin copias
    		bos = new ByteArrayOutputStream();    		
    		docPDF.generate(bos);
    		bos = this.establecerNumerosPagina(new ByteArrayInputStream(bos.toByteArray()));
    	}
    	
		// Devolvemos pdf generado
    	DocumentoRDS documentoF = UtilRDS.cloneDocumentoRDS(documento);
    	documentoF.setDatosFichero(bos.toByteArray());		
    	documentoF.setNombreFichero(StringUtil.normalizarNombreFichero(documento.getTitulo()) + ".pdf");	
		
		bos.close();
		
		
		return documentoF;
	}

	public boolean isCopiaInteresado() {
		return copiaInteresado;
	}

	public void setCopiaInteresado(boolean copiaInteresado) {
		this.copiaInteresado = copiaInteresado;
	}
	
	/**
	 * Modifica pdf para incluir numeros de pagina
	 * @param os
	 * @return
	 */
	private ByteArrayOutputStream establecerNumerosPagina(InputStream is) throws Exception{		
		// Establecemos números de página
		ByteArrayOutputStream bosNumPag = new ByteArrayOutputStream();    		
		ObjectStamp [] stamp = new ObjectStamp[1];
		stamp[0] = new NumeroPaginaStamp();
		stamp[0].setPage(0);
		stamp[0].setX(300);
		stamp[0].setY(820);	    		
		UtilPDF.stamp(bosNumPag,is,stamp);		
		return bosNumPag;		
	}

		
}

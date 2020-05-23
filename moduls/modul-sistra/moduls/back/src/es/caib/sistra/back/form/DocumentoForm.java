package es.caib.sistra.back.form;

import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.util.Util;
import es.caib.sistra.model.Documento;
import es.caib.sistra.model.TraDocumento;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DocumentoDelegate;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;


public class DocumentoForm extends TraduccionValidatorForm
{
	private Long idTramiteVersion = null;

	public Long getIdTramiteVersion()
	{
		return idTramiteVersion;
	}

	public void setIdTramiteVersion(Long idTramiteVersion)
	{
		this.idTramiteVersion = idTramiteVersion;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null)
        {
            errors = new ActionErrors();
        }
        try
        {
        	Documento documento = (Documento) getValues();

        	// Un documento no puede tener como código el identificador de datos propios o
        	// asiento
        	if (documento.getIdentificador().equalsIgnoreCase(ConstantesAsientoXML.IDENTIFICADOR_ASIENTO) ||
        		documento.getIdentificador().equalsIgnoreCase(ConstantesAsientoXML.IDENTIFICADOR_DATOS_PROPIOS) ||
        		documento.getIdentificador().equalsIgnoreCase(ConstantesAsientoXML.IDENTIFICADOR_CONFIRMACION_REGISTRO)
        		){
        		errors.add("values.identificador", new ActionError("errors.documento.identificadorReservado"));
        	}

        	// Validaciones anexo
        	if (documento.getTipo() == 'A'){

        		// Extensiones y tamaño máximo sólo para cuando presentar telemáticamente
        		if (documento.getAnexoPresentarTelematicamente() == 'S'){
	        		// Validamos que se hayan introducido las extensiones
	        		if (documento.getAnexoExtensiones() == null || Util.esCadenaVacia(documento.getAnexoExtensiones())){
	        			errors.add("values.anexoExtensiones", new ActionError("errors.documento.extensionesNulo", documento.getIdentificador() ));
	        		}else{
	        			// Validamos formato de las extensiones
	        			try{
	        				StringTokenizer st = new StringTokenizer(documento.getAnexoExtensiones(),",");
	        				while (st.hasMoreTokens()){
	        					String ext = st.nextToken();
	        					if (ext.length() != 3 && ext.length() != 4){
	        						errors.add("values.anexoExtensiones", new ActionError("errors.documento.extensionesIncorrectas", documento.getIdentificador() ));
	        						break;
	        					}
	        				}
	        			}catch(Exception e){
	        				errors.add("values.anexoExtensiones", new ActionError("errors.documento.extensionesIncorrectas", documento.getIdentificador() ));
	        			}
	        		}

	        		// Validamos que se hayan introducido el tamaño máximo
	        		if (documento.getAnexoExtensiones() == null || Util.esCadenaVacia(documento.getAnexoExtensiones())){
	        			errors.add("values.anexoTamanyoMax", new ActionError("errors.documento.tamanyoMaxNulo", documento.getIdentificador() ));
	        		}

	        		// Si se entrega telemáticamente no tiene sentido indicar fotocopia
	        		if (documento.getAnexoFotocopia() == 'S'){
	        			errors.add("values.anexoFotocopia", new ActionError("errors.documento.anexo.telematico.fotocopia", documento.getIdentificador() ));
	        		}

        		}else{
        			// Si se presenta presencialmente el original no tiene sentido indicar que se compulse
        			if (documento.getAnexoFotocopia() == 'N' && documento.getAnexoCompulsarPreregistro() == 'S'){
        				errors.add("values.anexoFotocopia", new ActionError("errors.documento.anexo.presencial.compulsa", documento.getIdentificador() ));
        			}
        		}

	        	// Validamos que si es generico se indique el numero maximo de genericos
	        	if (documento.getGenerico() == 'S' && documento.getMaxGenericos() <= 0){
	        		errors.add("values.maxGenericos", new ActionError("errors.documento.maxgenericos", documento.getIdentificador() ));
	        	}

	        	// TODO: Repasar con tema de Presentar Telemáticamente
	        	// Inconsistencia: anexo fotocopia y generico
	        	if (documento.getGenerico() == 'S' && documento.getAnexoFotocopia() == 'S'){
	        		errors.add("values.generico", new ActionError("errors.documento.genericofotocopia", documento.getIdentificador() ));
	        	}
        	}

            // Comprobar que el nombre de tramite no esta repetido.
            DocumentoDelegate delegate = DelegateUtil.getDocumentoDelegate();
            Set itDocumentos = delegate.listarDocumentos( getIdTramiteVersion() );
            for ( Iterator it = itDocumentos.iterator(); it.hasNext(); )
            {
            	Documento tmp = ( Documento ) it.next();
            	if ( tmp.getIdentificador().equals( documento.getIdentificador() ) && !tmp.getCodigo().equals( documento.getCodigo() ) )
            	{
            		errors.add("values.identificador", new ActionError("errors.documento.duplicado", documento.getIdentificador() ));
            	}
            }

        }
    	catch (DelegateException e)
    	{
            log.error(e);
        }

    	// Valida idiomas requeridos según la versión del trámite
    	if ( isAlta( request ) || isModificacion( request ) )
        {
	        try
	        {
	        	TramiteVersionDelegate delegate = DelegateUtil.getTramiteVersionDelegate();
	        	TramiteVersion tv = delegate.obtenerTramiteVersion(this.getIdTramiteVersion());
	        	String idiomas = tv.getIdiomasSoportados();
	        	StringTokenizer st = new StringTokenizer(idiomas,",");

	        	int info = 0;
	        	int numLangs = 0;

	        	while (st.hasMoreTokens()){
	        		String lang = ( String ) st.nextToken();
	        		numLangs ++;
	        		TraDocumento traduccion = ( TraDocumento ) this.getValues().getTraduccion( lang );

	        		// Valida campos obligatorios
	        		if (  traduccion == null ||
	        			( traduccion != null && StringUtils.isEmpty( traduccion.getDescripcion() ) ) )
	        		{
	        			errors.add( "traduccion.descripcion", new ActionError( "errors.traduccion", lang ) );
	        		}

	        		// Para campos opcionales comprueba si estan metidos en un idioma y para otro no
	        		if ( traduccion != null && StringUtils.isNotEmpty( traduccion.getInformacion() ) )
	        		{
	        			info ++;
	        		}
	        	}

	        	if (info > 0 && info != numLangs){
	        		errors.add( "traduccion.informacion", new ActionError("errors.revisarTraducciones","informacion") );
	        	}


	        }
	    	catch (DelegateException e)
	    	{
	            log.error(e);
	        }
        }

    	return errors;
    }
}

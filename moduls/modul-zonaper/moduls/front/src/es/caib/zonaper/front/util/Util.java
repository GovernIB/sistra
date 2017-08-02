package es.caib.zonaper.front.util;

import java.util.Iterator;

import javax.naming.InitialContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.util.NifCif;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosAnexoDocumentacion;
import es.caib.xml.registro.factoria.impl.DatosInteresado;

public class Util
{
	
	private static Log _log = LogFactory.getLog( Util.class );
	
	public static DocumentoRDS consultarDocumentoRDS( long codigo, String clave ) throws DelegateException
	{
		ReferenciaRDS referenciaRDS = new ReferenciaRDS();
		referenciaRDS.setCodigo( codigo );
		referenciaRDS.setClave( clave );
		
		RdsDelegate rdsDelegate 	= DelegateRDSUtil.getRdsDelegate();
		DocumentoRDS documentoRDS 	= rdsDelegate.consultarDocumento( referenciaRDS );
		return documentoRDS;
	}
	
	
	
	public static RdsDelegate getRDSDelegate()
	{
		return DelegateRDSUtil.getRdsDelegate();
	}
	
	public static Character obtenerTipoIdentificacion( String numeroDocumento ) throws Exception
	{
		char tipoDocumento = NifCif.TIPO_DOCUMENTO_NIF;
		if (StringUtils.isNotEmpty(numeroDocumento))
		{
			switch (NifCif.validaDocumento(numeroDocumento))
			{
				case NifCif.TIPO_DOCUMENTO_NIF:
					tipoDocumento = ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIF;
					break;
				case NifCif.TIPO_DOCUMENTO_CIF:
					tipoDocumento = ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF;
					break;
				case NifCif.TIPO_DOCUMENTO_NIE:
					tipoDocumento = ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIE;
					break;
				case NifCif.TIPO_DOCUMENTO_PASAPORTE:
					tipoDocumento = ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_PASAPORTE;
					break;
				default:
					throw new Exception("El número de identificación del representante ni es nif, ni cif, ni nie, ni pasaporte");
			}
		}
		return new Character( tipoDocumento );
	}
	
	public static DatosAnexoDocumentacion obtenerAnexoAsientoDeTipo( AsientoRegistral asiento, char tipoAnexo )
	{
		for( Iterator it = asiento.getDatosAnexoDocumentacion().iterator(); it.hasNext(); )
		{
			DatosAnexoDocumentacion tmp = ( DatosAnexoDocumentacion ) it.next();
			if ( tmp.getTipoDocumento().charValue() == tipoAnexo )
			{
				return tmp;
			}
		}
		return null;
	}
	
	 public static DatosInteresado obtenerRepresentante( AsientoRegistral asiento )
	 {
		DatosInteresado datosInteresado = null;
		for ( Iterator it = asiento.getDatosInteresado().iterator(); it.hasNext(); )
		{
			datosInteresado = ( DatosInteresado ) it.next();
			if ( ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE.equals( datosInteresado.getTipoInteresado() ) )
			{
				return datosInteresado;
			}
		}
		return datosInteresado;
	 }

	 
	 private static String version = null;	
		
		/**
		 * Obtiene version (en web.xml)
		 */
		public static String getVersion(){
			if (version == null) {
				try{
					InitialContext ic = new InitialContext();
					version = (String) ic.lookup("java:comp/env/release.cvs.tag");
				}catch(Exception ex){
					version = null;
				}		
			}
			return version;
		}
		
	/*
	 
	 	HAY QUE HACERLO EN CAPA EJB
	 
		public static  void checkAcceso(boolean autenticadoSesion,String usuAutSesion,boolean autenticadoElemento,String usuAutElemento) throws Exception{
			if (autenticadoSesion != autenticadoElemento ){
				throw new Exception ("El trámite no pertenece al usuario");
			}else{		
				if (!usuAutSesion.equals(usuAutElemento)){
					throw new Exception ("El trámite no pertenece al usuario");
				}
			}
		}
	*/
		
	/**
	 * Obtiene datos representante del asiento
	 */	
	public static DatosInteresado obtenerDatosRepresentante( AsientoRegistral asiento )
	{			
		for ( Iterator it = asiento.getDatosInteresado().iterator(); it.hasNext(); )
		{
			DatosInteresado datosInteresado = ( DatosInteresado ) it.next();
			if ( ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE.equals( datosInteresado.getTipoInteresado() ) )
			{
				return datosInteresado;
			}				
		}
		return null;
    }	
	
	
	/**
	 * Obtiene datos representado del asiento
	 */	
	public static DatosInteresado obtenerDatosRepresentado( AsientoRegistral asiento )
	{
			for ( Iterator it = asiento.getDatosInteresado().iterator(); it.hasNext(); )
			{
				DatosInteresado datosInteresado = ( DatosInteresado ) it.next();
				if ( ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO.equals( datosInteresado.getTipoInteresado() ) )
				{
					return datosInteresado;
				}				
			}
			
			return null;
			
	}	
	
	/**
	 * Obtiene datos delegado del asiento
	 */	
	public static DatosInteresado obtenerDatosDelegado( AsientoRegistral asiento )
	{
			for ( Iterator it = asiento.getDatosInteresado().iterator(); it.hasNext(); )
			{
				DatosInteresado datosInteresado = ( DatosInteresado ) it.next();
				if ( ConstantesAsientoXML.DATOSINTERESADO_TIPO_DELEGADO.equals( datosInteresado.getTipoInteresado() ) )
				{
					return datosInteresado;
				}				
			}
			
			return null;
			
	}	
	
}

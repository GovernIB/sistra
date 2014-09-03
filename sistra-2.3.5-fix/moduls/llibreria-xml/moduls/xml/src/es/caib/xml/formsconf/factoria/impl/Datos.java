package es.caib.xml.formsconf.factoria.impl;

import es.caib.xml.EstablecerPropiedadException;

public class Datos extends NodoBaseConfForms {
	private String codigoPerfil;
	private String idioma;
	private String layout;
	private boolean guardarSinTerminar;
	private String modelo;
	private String nomParamTokenRetorno;
	private String nomParamXMLDatosFin;
	private String nomParamXMLDatosIni;
	private String nomParamXMLSinTerminar;
	private String urlRedireccionCancel;
	private String urlRedireccionOK;
	private String urlSisTraCancel;
	private String urlSisTraOK;
	private String urlSisTraMantenimientoSesion;
	private Integer version;
	
	Datos(){}

	public void setIdioma(String idioma) throws EstablecerPropiedadException {
		validaCampoObligatorio("Datos", "Idioma", idioma);
		
		this.idioma = idioma;
	}

	public String getIdioma() {		
		return idioma;
	}

	public void setModelo(String modelo) throws EstablecerPropiedadException {
		validaCampoObligatorio("Datos", "Modelo", modelo);
		
		this.modelo = modelo;
	}

	public String getModelo() {		
		return modelo;
	}

	public void setVersion(Integer version) throws EstablecerPropiedadException {
		validaCampoObligatorio("Datos", "Version", version);
		
		this.version = version;
	}

	public Integer getVersion() {		
		return version;
	}

	public void setCodigoPerfil(String codigoPerfil)
			throws EstablecerPropiedadException {
		validaCampoObligatorio("Datos", "CodigoPerfil", codigoPerfil);
		
		this.codigoPerfil = codigoPerfil;
	}

	public String getCodigoPerfil() {		
		return codigoPerfil;
	}

	public void setLayout(String layout) throws EstablecerPropiedadException {
		validaCampoObligatorio("Datos", "Layout", layout);
		
		this.layout = layout;
	}

	public String getLayout() {		
		return layout;
	}

	public void setUrlSisTraOK(String urlSisTraOK)
			throws EstablecerPropiedadException {
		validaCampoObligatorio("Datos", "UrlSisTraOK", urlSisTraOK);
		
		this.urlSisTraOK = urlSisTraOK;
	}

	public String getUrlSisTraOK() {		
		return urlSisTraOK;
	}

	public void setUrlRedireccionOK(String urlRedireccionOK)
			throws EstablecerPropiedadException {
		validaCampoObligatorio("Datos", "UrlRedireccionOK", urlRedireccionOK);
		
		this.urlRedireccionOK = urlRedireccionOK;
	}

	public String getUrlRedireccionOK() {	
		return urlRedireccionOK;
	}

	public void setUrlSisTraCancel(String urlSisTraCancel)
			throws EstablecerPropiedadException {
		validaCampoObligatorio("Datos", "UrlSisTraCancel", urlSisTraCancel);
		
		this.urlSisTraCancel = urlSisTraCancel;
	}

	public String getUrlSisTraCancel() {
		return urlSisTraCancel;
	}

	public void setUrlRedireccionCancel(String urlRedireccionCancel)
			throws EstablecerPropiedadException {
		validaCampoObligatorio("Datos", "UrlRedireccionCancel", urlRedireccionCancel);
		
		this.urlRedireccionCancel = urlRedireccionCancel;
	}

	public String getUrlRedireccionCancel() {	
		return urlRedireccionCancel;
	}

	public void setNomParamXMLDatosIni(String nomParamXMLDatosIni)
			throws EstablecerPropiedadException {
		validaCampoObligatorio("Datos", "NomParamXMLDatosIni", nomParamXMLDatosIni);
		
		this.nomParamXMLDatosIni = nomParamXMLDatosIni;
	}

	public String getNomParamXMLDatosIni() {
		return nomParamXMLDatosIni;
	}

	public void setNomParamXMLDatosFin(String nomParamXMLDatosFin)
			throws EstablecerPropiedadException {
		validaCampoObligatorio("Datos", "NomParamXMLDatosFin", nomParamXMLDatosFin);
		
		this.nomParamXMLDatosFin = nomParamXMLDatosFin;
	}

	public String getNomParamXMLDatosFin() {		
		return nomParamXMLDatosFin;
	}

	public void setNomParamTokenRetorno(String nomParamTokenRetorno)
			throws EstablecerPropiedadException {
		validaCampoObligatorio("Datos", "NomParamTokenRetorno", nomParamTokenRetorno);
		
		this.nomParamTokenRetorno = nomParamTokenRetorno;
	}

	public String getNomParamTokenRetorno() {
		return nomParamTokenRetorno;
	}

	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		validaCampoObligatorio("Datos", "CodigoPerfil", 		getCodigoPerfil ());
		validaCampoObligatorio("Datos", "Idioma", 				getIdioma ());
		validaCampoObligatorio("Datos", "Layout", 				getLayout ());
		validaCampoObligatorio("Datos", "Modelo", 				getModelo ());
		validaCampoObligatorio("Datos", "NomParamTokenRetorno", getNomParamTokenRetorno ());
		validaCampoObligatorio("Datos", "NomParamXMLDatosFin", 	getNomParamXMLDatosFin ());
		validaCampoObligatorio("Datos", "NomParamXMLDatosIni", 	getNomParamXMLDatosIni ());
		validaCampoObligatorio("Datos", "UrlRedireccionCancel", getUrlRedireccionCancel ());
		validaCampoObligatorio("Datos", "UrlRedireccionOK", 	getUrlRedireccionOK ());
		validaCampoObligatorio("Datos", "UrlSisTraCancel", 		getUrlSisTraCancel ());
		validaCampoObligatorio("Datos", "UrlSisTraOK", 			getUrlSisTraOK ());
		validaCampoObligatorio("Datos", "UrlSisTraMantenimientoSesion", 			getUrlSisTraMantenimientoSesion());
		validaCampoObligatorio("Datos", "Version", 				getVersion ());
	}
	
	public boolean equals (Object obj){
		if (obj instanceof Datos){
			
			if (obj == null) return false;
			
			Datos datos = (Datos) obj;
			
			// CodigoPerfil
			String codigoPerfil 			= getCodigoPerfil ();
			String codigoPerfilExt 			= datos.getCodigoPerfil ();
			
			if (!objetosIguales(codigoPerfil, codigoPerfilExt)) return false;
			
			// Idioma
			String idioma 					= getIdioma ();
			String idiomaExt				= datos.getIdioma ();
			
			if (!objetosIguales (idioma, idiomaExt)) return false;
			
			// layout
			String layout 					= getLayout ();
			String layoutExt				= datos.getLayout ();
			
			if (!objetosIguales (layout, layoutExt)) return false;
			
			// guardar sin terminar
			boolean guardar 					= isGuardarSinTerminar();
			boolean guardarExt					= datos.isGuardarSinTerminar();
			
			if (guardar != guardarExt) return false;
			
			// Modelo
			String modelo					= getModelo ();
			String modeloExt				= datos.getModelo ();
			
			if (!objetosIguales (modelo, modeloExt)) return false;
			
			// ParamTokenRetorno
			String paramTokenRetorno		= getNomParamTokenRetorno ();
			String paramTokenRetornoExt		= datos.getNomParamTokenRetorno ();
			
			if (!objetosIguales (paramTokenRetorno, paramTokenRetornoExt)) return false;
			
			// ParamXMLDatosFin
			String paramXMLDatosFin			= getNomParamXMLDatosFin ();
			String paramXMLDatosFinExt		= datos.getNomParamXMLDatosFin ();
			
			if (!objetosIguales (paramXMLDatosFin, paramXMLDatosFinExt)) return false;
			
			// ParamXMLDatosIni
			String paramXMLDatosIni			= getNomParamXMLDatosIni ();
			String paramXMLDatosIniExt		= datos.getNomParamXMLDatosIni ();
			
			if (!objetosIguales (paramXMLDatosIni, paramXMLDatosIniExt)) return false;
			
			// paramXMLSinTerminar
			String paramXMLSinTerminar			= getNomParamXMLSinTerminar();
			String paramXMLSinTerminarExt		= datos.getNomParamXMLSinTerminar();
			
			if (!objetosIguales (paramXMLSinTerminar, paramXMLSinTerminarExt)) return false;
			
			// UrlRedireccionCancel
			String urlRedireccionCancel		= getUrlRedireccionCancel ();
			String urlRedireccionCancelExt	= datos.getUrlRedireccionCancel ();
			
			if (!objetosIguales (urlRedireccionCancel, urlRedireccionCancelExt)) return false;
			
			// UrlRedireccionOK
			String urlRedireccionOK			= getUrlRedireccionOK ();
			String urlRedireccionOKExt		= datos.getUrlRedireccionOK ();
			
			if (!objetosIguales (urlRedireccionOK, urlRedireccionOKExt)) return false;
			
			// UrlSisTraCancel
			String urlSisTraCancel		= getUrlSisTraCancel ();
			String urlSisTraCancelExt	= datos.getUrlSisTraCancel ();
			
			if (!objetosIguales (urlSisTraCancel, urlSisTraCancelExt)) return false;
			
			// UrlSisTraOK			
			String urlSisTraOK			= getUrlSisTraOK ();
			String urlSisTraOKExt		= datos.getUrlSisTraOK ();
			
			if (!objetosIguales (urlSisTraOK, urlSisTraOKExt)) return false;
			
			// UrlSisTraOK			
			String urlSisTraMantenimientoSesion			= getUrlSisTraMantenimientoSesion();
			String urlSisTraMantenimientoSesionExt		= datos.getUrlSisTraMantenimientoSesion();
			
			if (!objetosIguales (urlSisTraMantenimientoSesion, urlSisTraMantenimientoSesionExt)) return false;			
			
			// Version
			if (!objetosIguales (getVersion(), datos.getVersion())) return false;						
			
			// Ok consideramos equivalentes los objetos
			return true;
		}
		
		return super.equals (obj);
	}


	
	public void setUrlSisTraMantenimientoSesion(String urlSisTraMantenimientoSesion)
		throws EstablecerPropiedadException {
		validaCampoObligatorio("Datos", "UrlSisTraMantenimientoSesion", urlSisTraMantenimientoSesion);
		
		this.urlSisTraMantenimientoSesion = urlSisTraMantenimientoSesion;
	}
	
	public String getUrlSisTraMantenimientoSesion() {		
		return urlSisTraMantenimientoSesion;
	}

	public boolean isGuardarSinTerminar() {
		return guardarSinTerminar;
	}

	public void setGuardarSinTerminar(boolean guardarSinTerminar) {
		this.guardarSinTerminar = guardarSinTerminar;
	}

	public String getNomParamXMLSinTerminar() {
		return nomParamXMLSinTerminar;
	}

	public void setNomParamXMLSinTerminar(String nomParamSinTerminar) {
		this.nomParamXMLSinTerminar = nomParamSinTerminar;
	}

}

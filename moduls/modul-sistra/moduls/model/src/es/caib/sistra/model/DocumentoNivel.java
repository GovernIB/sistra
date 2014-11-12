package es.caib.sistra.model;


public class DocumentoNivel extends Traducible {

	public final static char AUTENTICACION_CERTIFICADO = 'C';
	public final static char AUTENTICACION_USUARIOPASSWORD = 'U';
	public final static char AUTENTICACION_ANONIMO = 'A';
	public final static char OBLIGATORIO = 'S';
	public final static char OPCIONAL = 'N';
	public final static char DEPENDIENTE = 'D';
			
	// Fields    
     private Long codigo;
     private Documento documento;
     private String nivelAutenticacion;
     private int version;
     private char obligatorio='S';
     private byte[] obligatorioScript;
     private char firmar='N';     
     private String firmante;
     private String formularioGestorFormulario="forms"; // DEFAULT: FORMS     
     private String formularioFormsModelo;
     private Integer formularioFormsVersion;
     private byte[] formularioDatosInicialesScript;
     private byte[] formularioConfiguracionScript;
     private byte[] formularioValidacionPostFormScript;
     private byte[] formularioModificacionPostFormScript;
     private byte[] formularioPlantillaScript;
     private char formularioGuardarSinTerminar='N';
     private byte[] pagoCalcularPagoScript;
     private char pagoMetodos='A';
     private String pagoPlugin = ".";
     private byte[] flujoTramitacionScript;
     private String contentType;
     
     
     
          
    // Constructors
    /** default constructor */
    public DocumentoNivel() {
    }

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public char getFirmar() {
		return firmar;
	}

	public void setFirmar(char firmar) {
		this.firmar = firmar;
	}

	public byte[] getFormularioDatosInicialesScript() {
		return formularioDatosInicialesScript;
	}

	public void setFormularioDatosInicialesScript(
			byte[] formularioDatosInicialesScript) {
		this.formularioDatosInicialesScript = formularioDatosInicialesScript;
	}	

	public byte[] getFormularioValidacionPostFormScript() {
		return formularioValidacionPostFormScript;
	}

	public void setFormularioValidacionPostFormScript(
			byte[] formularioValidacionPostFormScript) {
		this.formularioValidacionPostFormScript = formularioValidacionPostFormScript;
	}

	public String getNivelAutenticacion() {
		return nivelAutenticacion;
	}

	public void setNivelAutenticacion(String nivelAutenticacion) {
		this.nivelAutenticacion = nivelAutenticacion;
	}

	public byte[] getObligatorioScript() {
		return obligatorioScript;
	}

	public void setObligatorioScript(byte[] obligatorioScript) {
		this.obligatorioScript = obligatorioScript;
	}

	public byte[] getPagoCalcularPagoScript() {
		return pagoCalcularPagoScript;
	}

	public void setPagoCalcularPagoScript(byte[] pagoCalcularPagoScript) {
		this.pagoCalcularPagoScript = pagoCalcularPagoScript;
	}	

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	 public void addTraduccion(String lang, TraDocumentoNivel traduccion) {
        setTraduccion(lang, traduccion);
    }

	public char getObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(char obligatorio) {
		this.obligatorio = obligatorio;
	}

	public String getFirmante() {
		return firmante;
	}

	public void setFirmante(String firmante) {
		this.firmante = firmante;
	}

	public byte[] getFormularioConfiguracionScript() {
		return formularioConfiguracionScript;
	}

	public void setFormularioConfiguracionScript(
			byte[] formularioConfiguracionScript) {
		this.formularioConfiguracionScript = formularioConfiguracionScript;
	}

	public String getFormularioFormsModelo() {
		return formularioFormsModelo;
	}

	public void setFormularioFormsModelo(String formularioFormsModelo) {
		this.formularioFormsModelo = formularioFormsModelo;
	}

	public Integer getFormularioFormsVersion() {
		return formularioFormsVersion;
	}

	public void setFormularioFormsVersion(Integer formularioFormsVersion) {
		this.formularioFormsVersion = formularioFormsVersion;
	}

	public byte[] getFormularioPlantillaScript() {
		return formularioPlantillaScript;
	}

	public void setFormularioPlantillaScript(byte[] formularioPlantillaScript) {
		this.formularioPlantillaScript = formularioPlantillaScript;
	}

	public char getPagoMetodos() {
		return pagoMetodos;
	}

	public void setPagoMetodos(char pagoTipo) {
		this.pagoMetodos = pagoTipo;
	}

	public byte[] getFlujoTramitacionScript() {
		return flujoTramitacionScript;
	}

	public void setFlujoTramitacionScript(byte[] flujoTramitacionScript) {
		this.flujoTramitacionScript = flujoTramitacionScript;
	}

	public byte[] getFormularioModificacionPostFormScript() {
		return formularioModificacionPostFormScript;
	}

	public void setFormularioModificacionPostFormScript(
			byte[] formularioModificacionPostFormScript) {
		this.formularioModificacionPostFormScript = formularioModificacionPostFormScript;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public String getFormularioGestorFormulario() {
		return formularioGestorFormulario;
	}

	public void setFormularioGestorFormulario(String formularioGestorFormulario) {
		this.formularioGestorFormulario = formularioGestorFormulario;
	}

	public String getPagoPlugin() {
		return pagoPlugin;
	}

	public void setPagoPlugin(String pagoPlugin) {
		this.pagoPlugin = pagoPlugin;
	}

	public char getFormularioGuardarSinTerminar() {
		return formularioGuardarSinTerminar;
	}

	public void setFormularioGuardarSinTerminar(char formularioGuardarSinTerminar) {
		this.formularioGuardarSinTerminar = formularioGuardarSinTerminar;
	}

	

}

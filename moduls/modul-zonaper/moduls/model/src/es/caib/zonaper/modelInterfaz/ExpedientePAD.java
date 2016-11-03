package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Clase que representa un expediente en la zona personal. Un expediente est� identificado en la zona personal de forma un�voca por sus propiedades
 * unidadAdministrativa e identificador de expediente.
 * </p>
 * <p>
 * Hay que tener en cuenta que al crear un expediente se indica el idioma de tramitaci�n del mismo (que deber�a ser el idioma utilizado en la tramitaci�n), por lo que los textos asociados deben ir en ese 
 * idioma as� como los de los elementos del expediente.
 * </p>
 */
public class ExpedientePAD implements Serializable
{
	private String identificadorExpediente;
	private Long unidadAdministrativa;
	private String claveExpediente=null;
	private String identificadorProcedimiento;
	private String idioma="ca"; // Defecto ca para hacerlo compatible con version 1.3.7
	private String descripcion;
	private boolean autenticado = true;
	private String identificadorUsuario;
	private String nifRepresentante; // Por compatibilidad si no se establece se extraera automaticamente del usuario seycon o de la info de la entrada si es anonimo 
	private String nifRepresentado;
	private String nombreRepresentado;
	private String numeroEntradaBTE;
	private String identificadorGestor;
	private List elementos = new java.util.ArrayList();
	
	//	Opciones de aviso movilidad 
	private ConfiguracionAvisosExpedientePAD configuracionAvisos = new ConfiguracionAvisosExpedientePAD();
	
	
	/**
	 * Descripci�n del expediente. 
	 * @return String con la descripci�n
	 */
	public String getDescripcion()
	{
		return descripcion;
	}
	/**
	 * Establece la descripci�n del expediente. Par�metro obligatorio.
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}
	/**
	 * Identificador del expediente
	 * @return String
	 */
	public String getIdentificadorExpediente()
	{
		return identificadorExpediente;
	}
	/**
	 * Establece el identificador del expediente. Junto con la unidad administrativa identifica de forma un�voca a un expediente 
	 * @param identificadorExpediente
	 */
	public void setIdentificadorExpediente(String identificadorExpediente)
	{
		this.identificadorExpediente = identificadorExpediente;
	}
	/**
	 * Obtiene el codigo seycon que identifica al ciudadano al cual se le asigna el expediente (para expedientes autenticados)
	 * @return String
	 */
	public String getIdentificadorUsuario()
	{
		return identificadorUsuario;
	}
	/**
	 * Establece el codigo seycon del ciudadano al cual se le asigna el expediente (para expedientes autenticados)
	 * @param identificadorUsuario
	 */
	public void setIdentificadorUsuario(String identificadorUsuario)
	{
		this.identificadorUsuario = identificadorUsuario;
	}
	/**
	 * A�ade un evento a la lista de elementos del expediente 
	 * @param evento
	 */
	public void addEvento( EventoExpedientePAD evento )
	{
		elementos.add( evento );
	}
	/**
	 * Quita un evento de la lista de elementos del expediente
	 * @param evento
	 */
	public void removeEvento( EventoExpedientePAD evento )
	{
		elementos.remove( evento );
	}
	/**
	 * Devuelve la lista de elementos de un expediente
	 * @return java.util.List
	 * @see es.caib.zonaper.modelInterfaz.ElementoExpedientePAD
	 */
	public List getElementos()
	{
		return elementos;
	}
	/**
	 * Devuelve el identificador de la unidad administrativa de un expediente.
	 * @return Long
	 */
	public Long getUnidadAdministrativa()
	{
		return unidadAdministrativa;
	}
	/**
	 * Establece la unidadAdministrativa. Par�metro obligatorio. Junto con el identificador de expediente identifican de forma un�voca a un expediente en la PAD
	 * @param unidadAdministrativa Long
	 */
	public void setUnidadAdministrativa(Long unidadAdministrativa)
	{
		this.unidadAdministrativa = unidadAdministrativa;
	}
	
	/**
	 * Obtiene si el Expediente es autenticado (true) o bien se refiere a una tramitaci�n sin autenticar (false)
	 * @return
	 */
	public boolean isAutenticado() {
		return autenticado;
	}
	
	/**
	 * Establece si el Expediente es autenticado. En este caso se deber� establecer el identificador del usuario.
	 * @param autenticado
	 */
	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}
	
	/**
	 * Obtiene clave de acceso al expediente
	 * @return
	 */
	public String getClaveExpediente() {
		return claveExpediente;
	}
	
	/**
	 * Establece clave de acceso al expediente.
	 * Permite securizar un expediente en el momento de su creaci�n de forma que para cualquier operaci�n 
	 * sobre �l se necesite esta clave de acceso. 
	 * @param claveExpediente
	 */
	public void setClaveExpediente(String claveExpediente) {
		this.claveExpediente = claveExpediente;
	}
	
	/**
	 * Obtiene n�mero de entrada de la BTE que gener� este expediente
	 * @return
	 */
	public String getNumeroEntradaBTE() {
		return numeroEntradaBTE;
	}
	
	/**
	 * Establece n�mero de entrada de la BTE que gener� este expediente. Permite establecer que entrada de la BTE 
	 * ha generado el expediente de forma que se asociar� el tr�mite a dicho expediente.
	 * @param numeroEntradaBTE
	 */
	public void setNumeroEntradaBTE(String numeroEntradaBTE) {
		this.numeroEntradaBTE = numeroEntradaBTE;
	}
	
	/**
	 * Obtiene idioma de tramitaci�n del expediente. Los textos asociados al expediente y a sus eventos deben estar en este idioma.
	 * @return  es:Castellano - ca:Catal�n
	 */
	public String getIdioma() {
		return idioma;
	}
	/**
	 * Establece idioma de tramitaci�n del expediente. Este idioma debe establecerse en funci�n del idioma de la solicitud inicial y los textos asociados al expediente y a sus eventos deben estar en este idioma..
	 *  
	 * @param idioma es:Castellano - ca:Catal�n
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	
	/**
	 * En caso de existir representaci�n obtiene el nif del representado 
	 * @return Nif representado
	 */
	public String getNifRepresentado() {
		return nifRepresentado;
	}
	
	/**
	 * En caso de existir representaci�n establece el nif del representado 
	 * @param nifRepresentado Nif representado
	 */
	public void setNifRepresentado(String nifRepresentado) {
		this.nifRepresentado = nifRepresentado;
	}
	
	/**
	 * En caso de existir representaci�n establece el nombre (nombre y apellidos) del representado
	 * @return Nombre representado
	 */	
	public String getNombreRepresentado() {
		return nombreRepresentado;
	}
	/**
	 * En caso de existir representaci�n establece el nombre (nombre y apellidos) del representado 
	 * @param nifRepresentado Nif representado
	 */	
	public void setNombreRepresentado(String nombreRepresentado) {
		this.nombreRepresentado = nombreRepresentado;
	}
	
	/**
	 * Obtiene configuraci�n de avisos para el expediente
	 * @return
	 */
	public ConfiguracionAvisosExpedientePAD getConfiguracionAvisos() {
		return configuracionAvisos;
	}
	
	/**
	 * Establece configuraci�n de avisos para el expediente
	 * @param configuracionAvisos
	 */
	protected void setConfiguracionAvisos(
			ConfiguracionAvisosExpedientePAD configuracionAvisos) {
		this.configuracionAvisos = configuracionAvisos;
	}
	public String getNifRepresentante() {
		return nifRepresentante;
	}
	public void setNifRepresentante(String nifRepresentante) {
		this.nifRepresentante = nifRepresentante;
	}
	public String getIdentificadorProcedimiento() {
		return identificadorProcedimiento;
	}
	public void setIdentificadorProcedimiento(String identificadorProcedimiento) {
		this.identificadorProcedimiento = identificadorProcedimiento;
	}
	public String getIdentificadorGestor() {
		return identificadorGestor;
	}
	public void setIdentificadorGestor(String identificadorGestor) {
		this.identificadorGestor = identificadorGestor;
	}
		
}

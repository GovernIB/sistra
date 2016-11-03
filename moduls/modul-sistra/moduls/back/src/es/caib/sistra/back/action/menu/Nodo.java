package es.caib.sistra.back.action.menu;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Nodo implements Serializable
{
	
	public final static String ORGANO_RESPONSABLE = "ORGRES";
	public final static String TRAMITE = "TRAMITE";
	public final static String TRAMITE_VERSION = "TRAVER";
	public final static String TRAMITE_NIVEL = "TRANIV";
	public final static String ESPEFICACIONES_NIVEL = "ESPNIV";
	public final static String DOCUMENTO_FORMULARIO = "DOCUM_FORMULARIO";
	public final static String DOCUMENTO_PAGO = "DOCUM_PAGO";
	public final static String DOCUMENTO_ANEXO = "DOCUM_ANEXO";
	public final static String DOCUMENTO_NIVEL = "DOCNIV";
	public final static String INDEFINIDO = "INDEFINIDO";
		
	
	public final static String IR_A_ORGANO = "organo";
	public final static String IR_A_TRAMITE = "tramite";
	public final static String IR_A_DEFINICION_TRAMITE = "definicionTramite";
	public final static String IR_A_TRAMITE_VERSION = "version";
	public final static String IR_A_DEFINICION_TRAMITE_VERSION = "definicionVersion";
	public final static String IR_A_ESPECIFICACIONES_GENERICAS = "especificacionesGenericas";
	public final static String IR_A_MENSAJES_VALIDACIONES = "mensajesValidaciones";
	public final static String IR_A_TRAMITENIVEL = "nivelAuth";
	public final static String IR_A_DOCUMENTOS = "documentos";
	public final static String IR_A_DEFINICION_DOCUMENTO = "definicionDocumento";
	public final static String IR_A_DOCUMENTONIVEL = "nivelAuthDocumento";
	public final static String IR_A_ESPECIFICACIONES_NIVEL_DOCUMENTO = "especificacionesNivelDocumento";
	
	private boolean folder;
	private String id;
	private String descripcion;
	private String parentId;
	private String action;	
	private Map parameters = new HashMap();	
	private String descripcionLink;
	private String tipo="INDEFINIDO";
	
	
	public Nodo()
	{
		super();
	}
	
	/*
	
	public Nodo(boolean folder, String id, String descripcion, String parentId, String action, String paramId, String paramValue, String descripcionLink )
	{
		super();
		this.folder = folder;
		this.id = id;
		this.descripcion = descripcion;
		this.parentId = parentId;
		this.action = action;
		this.paramId = paramId;
		this.paramValue = paramValue;
		this.descripcionLink = descripcionLink;
	}
	
	*/
	
	public String getDescripcion()
	{
		return descripcion;
	}
	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}
	public boolean isFolder()
	{
		return folder;
	}
	public void setFolder(boolean folder)
	{
		this.folder = folder;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getParentId()
	{
		return parentId;
	}
	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}
	public String getAction()
	{
		return action;
	}
	public void setAction(String action)
	{
		this.action = action;
	}

	public String getDescripcionLink()
	{
		return descripcionLink;
	}

	public void setDescripcionLink(String descripcionLink)
	{
		this.descripcionLink = descripcionLink;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}	
}

package es.caib.zonaper.front.action;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.util.ConvertUtil;
import es.caib.util.NifCif;
import es.caib.xml.delegacion.factoria.FactoriaObjetosXMLDelegacion;
import es.caib.xml.delegacion.factoria.ServicioDelegacionXML;
import es.caib.xml.delegacion.factoria.impl.AutorizacionDelegacion;
import es.caib.zonaper.front.Constants;
import es.caib.zonaper.front.json.JSONObject;
import es.caib.zonaper.model.DatosSesion;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.ConsultaPADDelegate;
import es.caib.zonaper.persistence.delegate.DelegacionDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.DominiosDelegate;


/**
 * @struts.action
 *  path="/protected/crearDocumentoDelegacion"
 * 
 */
public class CrearDocumentoDelegacionAction extends Action
{
	private static Log _log = LogFactory.getLog( CrearDocumentoDelegacionAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		String nifDelegante = "";
		String nifDelegado = request.getParameter("nifDelegado");
		String fechaInicioDelegacion = request.getParameter("fechaInicioDelegacion");
		String fechaFinDelegacion = request.getParameter("fechaFinDelegacion");
		String permisos = request.getParameter("permisos");
		DatosSesion datosSes = null;
		JSONObject json = new JSONObject();	
		String error = "";
		String datos = "";
		String clave = "";
		String codigo = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		String nombreDelegante = "";
		String nombreDelegado = "";
		
		DelegacionDelegate deleg = DelegateUtil.getDelegacionDelegate();
		ConsultaPADDelegate consulta = DelegateUtil.getConsultaPADDelegate();
		try{
			
			// Obtenemos datos sesion
			datosSes = (DatosSesion)request.getSession().getAttribute( Constants.DATOS_SESION_KEY );
			
			// Añadimos permisos según jerarquía: TPNR
			permisos = generarPermisosJerarquia(permisos);
			
			// Obtenemos datos entidad delegante
			if (datosSes.getPerfilAcceso().equals(ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO)){
				nifDelegante = datosSes.getNifEntidad();
				nombreDelegante = datosSes.getNombreEntidad();
			}else{
				nifDelegante = datosSes.getNifUsuario();
				nombreDelegante = datosSes.getNombreUsuario();
			}
				
			// Obtenemos datos delegado
			PersonaPAD persona = consulta.obtenerDatosPADporNif(nifDelegado);
			if(persona != null){
				nombreDelegado = persona.getNombreCompleto();
			}else{
				error = error + this.getResources(request).getMessage("error.nifDelegado.no.existe")+"\n";
			}
			
			// Verificamos datos delegacion
			error = datosCorrectos(nifDelegante,nifDelegado,fechaInicioDelegacion,fechaFinDelegacion,permisos,sdf,request);
			
			// Generamos documento delegacion
			if(StringUtils.isBlank(error)){
				if(deleg.habilitadaDelegacion(nifDelegante)){
					if(deleg.obtenerPermisosDelegacion(nifDelegante).contains(ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD) || nifDelegante.equals(datosSes.getNifUsuario())){
						ByteArrayOutputStream baos = null;
						DocumentoRDS doc = null;
						try{
							baos = generarByteArrayOutputStream(nifDelegante, nombreDelegante, nifDelegado, nombreDelegado, sdf2.format(sdf.parse(fechaInicioDelegacion)), sdf2.format(sdf.parse(fechaFinDelegacion)), permisos);
							try{
								doc = crearDocumentoRDS(baos,request);	
								datos = ConvertUtil.bytesToBase64UrlSafe(doc.getDatosFichero());
								clave = doc.getReferenciaRDS().getClave();
								codigo = doc.getReferenciaRDS().getCodigo()+"";
							}catch(Exception e){
								error = this.getResources(request).getMessage("error.delegacion.guardar.documento");
							}
						}catch(Exception e){
							error = this.getResources(request).getMessage("error.delegacion.generar.documento");
						}
					}else{
						error = this.getResources(request).getMessage("error.entidad.no.es.representante");
					}
				}else{
					error = this.getResources(request).getMessage("error.entidad.delegaciones.no.habilitadas");
				}
			}
		}catch(Exception e){
			_log.error("No se puede asignar el representante a la entidad.",e);
			error = this.getResources(request).getMessage("error.entidad.general.asignar");
		}
		
		// Retornamos respuesta
		json.put("datos",datos);
		json.put("codigo",codigo);
		json.put("clave",clave);
		json.put("error",error);
		populateWithJSON(response,json.toString());
				
		return null;		
	}
	
	private static void populateWithJSON(HttpServletResponse response,String json) throws Exception{
		String CONTENT_TYPE="text/x-json;charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json); 		 
	}
	
	private ByteArrayOutputStream generarByteArrayOutputStream(String nifDelegante, String nombreDelegante, String nifDelegado, String nombreDelegado, String fechaInicioDelegacion, String fechaFinDelegacion, String permisosJSP) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FactoriaObjetosXMLDelegacion factoria = ServicioDelegacionXML.crearFactoriaObjetosXML();
		factoria.setEncoding("UTF-8");
		//Creamos el documento de delegación
		AutorizacionDelegacion autDelegacion = factoria.crearAutorizacionDelegacion();		
		factoria.setIndentacion(true);
		autDelegacion.setEntidadId(nifDelegante);
		autDelegacion.setEntidadNombre(nombreDelegante);
		autDelegacion.setDelegadoId(nifDelegado);
		autDelegacion.setDelegadoNombre(nombreDelegado);
		autDelegacion.setPermisos(permisosJSP);
		autDelegacion.setFechaInicio(fechaInicioDelegacion);
		autDelegacion.setFechaFin(fechaFinDelegacion);
		//guardamos el documento de delegación en un byteArrayOutputStram para guardarlo en el redose
		factoria.guardarAutorizacionDelegacion(autDelegacion,baos);
		return baos;
	}
	
	private DocumentoRDS crearDocumentoRDS(ByteArrayOutputStream baos, HttpServletRequest request) throws Exception{
		DominiosDelegate domdeleg = DelegateUtil.getDominiosDelegate();
		RdsDelegate rdsdeleg = DelegateRDSUtil.getRdsDelegate();
		
		DocumentoRDS docRDS = new DocumentoRDS();
		docRDS.setDatosFichero(baos.toByteArray());
		docRDS.setEstructurado(true);
		docRDS.setFechaRDS( new Date() );
		docRDS.setUnidadAdministrativa(new Long(domdeleg.obtenerRaizUnidadesOrganicas()));
		docRDS.setTitulo( "AutorizacionDelegacion" );
		docRDS.setNombreFichero( "AutorizacionDelegacion.xml" );
		docRDS.setExtensionFichero( "xml" );
		docRDS.setModelo( ConstantesRDS.MODELO_AUTORIZACION_DELEGACION  ); 
		docRDS.setVersion( 1 );
		docRDS.setIdioma(getLocale(request).getLanguage());
		ReferenciaRDS ref = rdsdeleg.insertarDocumento(docRDS);
		docRDS = rdsdeleg.consultarDocumento(ref);
		return docRDS;
	}
	
	private String datosCorrectos(String nifDelegante, String nifDelegado, String fechaInicioDelegacion, String  fechaFinDelegacion, String permisos, SimpleDateFormat sdf, HttpServletRequest request){
		String error = "";
		boolean datasErroneas = false;
		Date fechaIni = null;
		Date fechaFi = null;
		if(StringUtils.isBlank(nifDelegante)){
			error = error + this.getResources(request).getMessage("error.nifDelegante.vacio")+"\n";
		}
		if(StringUtils.isBlank(nifDelegado)){
			error = error + this.getResources(request).getMessage("error.nifDelegado.vacio")+"\n";
		}else if(NifCif.validaDocumento(nifDelegado) == -1){
			error = error + this.getResources(request).getMessage("error.nifDelegado.incorrecto")+"\n";
		}
		if(StringUtils.isBlank(fechaInicioDelegacion)){
			error = error + this.getResources(request).getMessage("error.fechaInicioDelegacion.vacio")+"\n";
			datasErroneas = true;
		}else{
			try{
				fechaIni = sdf.parse(fechaInicioDelegacion);
			}catch(Exception e){
				error = error + this.getResources(request).getMessage("error.fechas.formatoNoValido")+"\n";
				datasErroneas = true;
			}
		}
		if(StringUtils.isBlank(fechaFinDelegacion)){
			error = error + this.getResources(request).getMessage("error.fechaFinDelegacion.vacio")+"\n";
			datasErroneas = true;
		}else{
			try{
				fechaFi = sdf.parse(fechaFinDelegacion);
			}catch(Exception e){
				error = error + this.getResources(request).getMessage("error.fechas.formatoNoValido")+"\n";
				datasErroneas = true;
			}
		}
		if(!datasErroneas){
			if(fechaIni.after(fechaFi)){
				error = error + this.getResources(request).getMessage("error.fechas.fechaFin.mayor.fechaIni")+"\n";
			}
		}
		if(StringUtils.isBlank(permisos)){
			error = error + this.getResources(request).getMessage("error.permisos.vacio")+"\n";
		}
		return error;
	}
	
	/**
	 * Genera permisos añadiendo permisos segun jerarquia
	 * @param permisos Se espera un unico permiso: T,P o N
	 * @return
	 * @throws Exception
	 */
	private String generarPermisosJerarquia(String permisos) throws Exception{
		if (StringUtils.isEmpty(permisos) || permisos.length() != 1){
			throw new Exception("No se encuentran los permisos esperados: T,P o N: " + permisos);
		}			
		if (permisos.equals(ConstantesZPE.DELEGACION_PERMISO_RELLENAR_TRAMITE)){
			return ConstantesZPE.DELEGACION_PERMISO_RELLENAR_TRAMITE;
		}else if (permisos.equals(ConstantesZPE.DELEGACION_PERMISO_PRESENTAR_TRAMITE)){
			return ConstantesZPE.DELEGACION_PERMISO_RELLENAR_TRAMITE + ConstantesZPE.DELEGACION_PERMISO_PRESENTAR_TRAMITE;
		}else if (permisos.equals(ConstantesZPE.DELEGACION_PERMISO_ABRIR_NOTIFICACION)){
			return ConstantesZPE.DELEGACION_PERMISO_RELLENAR_TRAMITE + ConstantesZPE.DELEGACION_PERMISO_PRESENTAR_TRAMITE + ConstantesZPE.DELEGACION_PERMISO_ABRIR_NOTIFICACION;
		}else{
			throw new Exception("No se encuentran los permisos esperados: T,P o N: " + permisos);
		}		 
	}
}

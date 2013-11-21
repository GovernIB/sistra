package es.caib.zonaper.front.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.tiles.ComponentContext;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.zonaper.front.util.DocumentoPersistenteFront;
import es.caib.zonaper.front.util.FirmanteFront;
import es.caib.zonaper.model.DatosSesion;
import es.caib.zonaper.model.DocumentoPersistente;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.persistence.delegate.BandejaFirmaDelegate;
import es.caib.zonaper.persistence.delegate.ConsultaPADDelegate;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

public class BandejaFirmaController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		List lResult = new ArrayList();
		
		//Buscamos Documentos pendientes de firma de la entidad delegante
		BandejaFirmaDelegate firmas = DelegateUtil.getBandejaFirmaDelegate();
		
		String nifEntidad;
		DatosSesion datosSesion = this.getDatosSesion(request);
		
		if (datosSesion.getPerfilAcceso().equals(ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO)){
			nifEntidad = datosSesion.getNifEntidad();
		}else{
			nifEntidad = datosSesion.getNifUsuario();
		}
		
		List docs = firmas.obtenerDocumentosPendientesFirma(nifEntidad);
		
		for(int i=0;i<docs.size();i++){
			DocumentoPersistenteFront docPer = docPerToDocFront((DocumentoPersistente)docs.get(i));
			ReferenciaRDS ref = new ReferenciaRDS();
			ref.setClave(docPer.getRdsClave());
			ref.setCodigo(docPer.getRdsCodigo());
			DocumentoRDS doc = DelegateRDSUtil.getRdsDelegate().consultarDocumento(ref,false);
			docPer.setDescripcionDocumento(doc.getTitulo());
			lResult.add(docPer);
		}
		
		request.setAttribute( "documentosPendientesFirma", lResult );
	}
	
	private DocumentoPersistenteFront docPerToDocFront(DocumentoPersistente doc) throws Exception{
		DocumentoPersistenteFront docFront = new DocumentoPersistenteFront();
		if(doc != null){
			docFront.setCodigo(doc.getCodigo());
			docFront.setDelegacionEstado(doc.getDelegacionEstado());
			docFront.setDelegacionFirmantes(doc.getDelegacionFirmantes());
			docFront.setDelegacionFirmantesPendientes(doc.getDelegacionFirmantesPendientes());
			docFront.setDescripcionGenerico(doc.getDescripcionGenerico());
			docFront.setEstado(doc.getEstado());
			docFront.setIdentificador(doc.getIdentificador());
			docFront.setNombreFicheroAnexo(doc.getNombreFicheroAnexo());
			docFront.setNumeroInstancia(doc.getNumeroInstancia());
			docFront.setRdsClave(doc.getRdsClave());
			docFront.setRdsCodigo(doc.getRdsCodigo());
			docFront.setTramitePersistente(doc.getTramitePersistente());
			
			List firmantes = new ArrayList();  
			String[] firmantesDel;
			if(docFront.getDelegacionFirmantes()!= null && StringUtils.isNotBlank(docFront.getDelegacionFirmantes())){
				firmantesDel = docFront.getDelegacionFirmantes().split("#");
				ConsultaPADDelegate deleg = DelegateUtil.getConsultaPADDelegate();
				try {
					for(int i=0;i<firmantesDel.length;i++){
						FirmanteFront firmante = new FirmanteFront();
						firmante.setNombre(deleg.obtenerDatosPADporNif(firmantesDel[i]).getNombreCompleto());
						firmante.setHaFirmado(!docFront.getDelegacionFirmantesPendientes().contains(firmantesDel[i]));
						firmantes.add(firmante);
					}
				} catch (DelegateException e) {
					throw new Exception("No existe el nif del firmante");
				}
			}
			docFront.setDelegacionFirmantesHTML(firmantes);
			
		}
		
		return docFront;
	}
}

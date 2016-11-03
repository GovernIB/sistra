package es.caib.xml.oficioremision.factoria.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.oficioremision.modelo.ObjectFactory;
import es.caib.xml.oficioremision.modelo.PARAMETROTRAMITE;
import es.caib.xml.oficioremision.modelo.TRAMITESUBSANACION;

/** 
 * 
 * Objeto de Tramitesubsanacion
 * 
 * @author magroig
 *
 */
public class TramiteSubsanacion extends NodoBaseOficioRemision  {
			
	 protected String descripcionTramite;
	 protected String identificadorTramite;
	 protected Integer versionTramite;
	 protected Map<String,String> parametrosTramite;
	
	
	TramiteSubsanacion (){
		descripcionTramite = null;
		identificadorTramite = null;
		versionTramite = null;
		parametrosTramite = null;		
	}
			

	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		validaCampoObligatorio("TramiteSubsanacion", "descripcionTramite", this.getDescripcionTramite());
		validaCampoObligatorio("TramiteSubsanacion", "identificadorTramite", this.getIdentificadorTramite());
		validaCampoObligatorio("TramiteSubsanacion", "versionTramite", this.getVersionTramite());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof TramiteSubsanacion){
			
			if (obj == null) return false;
			
			TramiteSubsanacion inst = (TramiteSubsanacion) obj;
						
			if (!objetosIguales (getDescripcionTramite(), inst.getDescripcionTramite())) return false;
			if (!objetosIguales (getIdentificadorTramite(), inst.getIdentificadorTramite())) return false;
			if (!objetosIguales (getVersionTramite(), inst.getVersionTramite())) return false;
			if (!objetosIguales (getParametrosTramite(), inst.getParametrosTramite())) return false;
			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}


	public String getDescripcionTramite() {
		return descripcionTramite;
	}


	public void setDescripcionTramite(String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}


	public String getIdentificadorTramite() {
		return identificadorTramite;
	}


	public void setIdentificadorTramite(String identificadorTramite) {
		this.identificadorTramite = identificadorTramite;
	}


	public Map<String, String> getParametrosTramite() {
		return parametrosTramite;
	}


	public void setParametrosTramite(Map<String, String> parametrosTramite) {
		this.parametrosTramite = parametrosTramite;
	}


	public Integer getVersionTramite() {
		return versionTramite;
	}


	public void setVersionTramite(Integer versionTramite) {
		this.versionTramite = versionTramite;
	}


	public static TramiteSubsanacion fromJAXB(TRAMITESUBSANACION tramiteSubsanacionJAXB) throws JAXBException{
		TramiteSubsanacion tramiteSubsanacion = null;
		if (tramiteSubsanacionJAXB != null){
			tramiteSubsanacion = new TramiteSubsanacion();
			tramiteSubsanacion.setDescripcionTramite(tramiteSubsanacionJAXB.getDESCRIPCIONTRAMITE());
			tramiteSubsanacion.setIdentificadorTramite(tramiteSubsanacionJAXB.getIDENTIFICADORTRAMITE());
			tramiteSubsanacion.setVersionTramite(new Integer(tramiteSubsanacionJAXB.getVERSIONTRAMITE()));
			
			if (tramiteSubsanacionJAXB.getPARAMETROSTRAMITE() != null && 
				tramiteSubsanacionJAXB.getPARAMETROSTRAMITE().getPARAMETROTRAMITE() != null &&
				tramiteSubsanacionJAXB.getPARAMETROSTRAMITE().getPARAMETROTRAMITE().size() > 0){
					Map<String,String> params = new LinkedHashMap<String,String>();
					for (Iterator it = tramiteSubsanacionJAXB.getPARAMETROSTRAMITE().getPARAMETROTRAMITE().iterator();it.hasNext();){
						PARAMETROTRAMITE param = (PARAMETROTRAMITE) it.next();
						params.put(param.getPARAMETRO(),param.getVALOR());
					}
					tramiteSubsanacion.setParametrosTramite(params);				
			}
			
			tramiteSubsanacion.setDescripcionTramite(tramiteSubsanacionJAXB.getDESCRIPCIONTRAMITE());
		}
		return tramiteSubsanacion;
	}


	public static TRAMITESUBSANACION toJAXB(TramiteSubsanacion tramiteSubsanacion) throws JAXBException{
		TRAMITESUBSANACION tramiteSubsanacionJAXB = null;
		if (tramiteSubsanacion != null){
			ObjectFactory of = (new ObjectFactory());
			tramiteSubsanacionJAXB = of.createTRAMITESUBSANACION();
			tramiteSubsanacionJAXB.setDESCRIPCIONTRAMITE(tramiteSubsanacion.getDescripcionTramite());
			tramiteSubsanacionJAXB.setIDENTIFICADORTRAMITE(tramiteSubsanacion.getIdentificadorTramite());
			if (tramiteSubsanacion.getVersionTramite() != null){
				tramiteSubsanacionJAXB.setVERSIONTRAMITE(tramiteSubsanacion.getVersionTramite().intValue());
			}
			if (tramiteSubsanacion.getParametrosTramite() != null && tramiteSubsanacion.getParametrosTramite().size() > 0){
				tramiteSubsanacionJAXB.setPARAMETROSTRAMITE(of.createPARAMETROSTRAMITE());
				for (Iterator it = tramiteSubsanacion.getParametrosTramite().keySet().iterator();it.hasNext();){
					String parametro = (String) it.next();
					String valor = tramiteSubsanacion.getParametrosTramite().get(parametro);
					PARAMETROTRAMITE parametroJAXB = of.createPARAMETROTRAMITE();
					parametroJAXB.setPARAMETRO(parametro);
					parametroJAXB.setVALOR(valor);
					tramiteSubsanacionJAXB.getPARAMETROSTRAMITE().getPARAMETROTRAMITE().add(parametroJAXB);				
				}
			}
		}
		return tramiteSubsanacionJAXB;
	}

	

}

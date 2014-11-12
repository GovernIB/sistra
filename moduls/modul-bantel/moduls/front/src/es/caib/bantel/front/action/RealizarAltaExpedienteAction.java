package es.caib.bantel.front.action;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleExpedienteForm;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.modelInterfaz.TramiteBTE;
import es.caib.bantel.persistence.delegate.DelegateBTEUtil;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;
import es.caib.zonaper.persistence.delegate.PadBackOfficeDelegate;

/**
 * @struts.action
 *  name="detalleExpedienteForm"
 *  path="/realizarAltaExpediente"
 *  validate="true"
 *  input = ".altaExpediente"
 *  
 * @struts.action-forward
 *  name="success" path=".recuperarExpediente"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class RealizarAltaExpedienteAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(RealizarAltaExpedienteAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleExpedienteForm expedienteForm = (DetalleExpedienteForm)form;
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
	
		try{
		
			PadBackOfficeDelegate ejb = new PadBackOfficeDelegate();
			
			// Montamos expediente
			ExpedientePAD expediente = new ExpedientePAD();
			expediente.setDescripcion(expedienteForm.getDescripcion());
			expediente.setIdioma(expedienteForm.getIdioma());
			expediente.setIdentificadorExpediente(expedienteForm.getIdentificadorExp());
			expediente.setIdentificadorProcedimiento(expedienteForm.getIdentificadorProcedimiento());			
			
			// Generamos clave expediente automaticamente
			expedienteForm.setClaveExp(generarClave());
			expediente.setClaveExpediente(expedienteForm.getClaveExp());
			
			// Obtenemos procedimiento para extraer unidad administrativa
			Procedimiento proc = DelegateUtil.getTramiteDelegate().obtenerProcedimiento(expedienteForm.getIdentificadorProcedimiento());
			if (proc.getUnidadAdministrativa() == null) {
				throw new Exception("El procedimiento " + proc.getIdentificador() + " no tiene establecida una unidad administrativa");
			}
			expediente.setUnidadAdministrativa( proc.getUnidadAdministrativa() );

			
			// Si tiene entrada asociada, dependera del nivel de autenticacion de la entrada
			expediente.setNumeroEntradaBTE(expedienteForm.getNumeroEntrada());
			if (StringUtils.isNotEmpty(expedienteForm.getNumeroEntrada())){ 
				TramiteBTE entrada = DelegateBTEUtil.getBteDelegate().obtenerEntrada(expedienteForm.getNumeroEntrada());				
				if (entrada.getNivelAutenticacion() == 'A'){
					expediente.setAutenticado(false);
				}else{
					expediente.setAutenticado(true);					
				}						
				expediente.setNifRepresentante(entrada.getUsuarioNif());
				expediente.setNifRepresentado(entrada.getRepresentadoNif());
				expediente.setNombreRepresentado(entrada.getRepresentadoNombre());
			}else{
			// Si no tiene entrada asociada, debe ser autenticado
				expediente.setAutenticado(true);
				expediente.setNifRepresentante(expedienteForm.getNif());				
			}
						
			if(!StringUtils.isEmpty(expedienteForm.getHabilitarAvisos()) ){
				expediente.getConfiguracionAvisos().setHabilitarAvisos(new Boolean(expedienteForm.getHabilitarAvisos().equals("S")));
				if (expedienteForm.getHabilitarAvisos().equals("S")) {
					expediente.getConfiguracionAvisos().setAvisoEmail(expedienteForm.getEmail());
					expediente.getConfiguracionAvisos().setAvisoSMS(expedienteForm.getMovil());
				}
			}else {
				expediente.getConfiguracionAvisos().setHabilitarAvisos(new Boolean(false));
			}
			ejb.altaExpediente(expediente);
			
			// Redirigimos a consulta expediente
			response.sendRedirect("recuperarExpediente.do?unidadAdm=" + expediente.getUnidadAdministrativa() + "&identificadorExp=" + expediente.getIdentificadorExpediente() + ( expediente.getClaveExpediente() != null?"&claveExp=" + expediente.getClaveExpediente():"") );
			return null;
			
			
		}catch(Exception e){
			
			log.error("Excepcion alta expediente",e);
			
			String mensajeOk = "";
			
			if(e.getMessage().equals("Usuario inexistente")){
				mensajeOk = MensajesUtil.getValue("error.expediente.UsuarioInexistente", request);
			}else if(e.getMessage().equals("La entrada ya tiene un expediente enlazado")){
				mensajeOk = MensajesUtil.getValue("error.expediente.ExpedienteEnlazado", request);
			}else{
				mensajeOk = MensajesUtil.getValue("error.expediente.Excepcion", request) + ": " + e.getMessage();
			}
			
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk);
			request.setAttribute( "enlace","errorExpediente");
			return mapping.findForward("fail");
		}
    }
	
	/**
     * Genera clave de acceso al expediente (Cadena de 10 carácteres)
     * @return
     */
    private String generarClave(){
    	Random r = new Random();    	
    	String clave="";
    	int ca = Character.getNumericValue('a');
    	for (int i=0;i<10;i++){
    		clave += Character.forDigit(ca + r.nextInt(26),Character.MAX_RADIX);    		
    	}
    	return clave;    	    	
    }
}


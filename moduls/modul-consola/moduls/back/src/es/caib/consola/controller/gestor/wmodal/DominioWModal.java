package es.caib.consola.controller.gestor.wmodal;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.codemirror.Codemirror;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.model.OrganoResponsable;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.util.CifradoUtil;
import es.caib.util.StringUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de edicion dominio.
 */
@SuppressWarnings("serial")
public class DominioWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wDominio;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;
    /** Dominio editado.*/
    private Dominio dominio;
    /** Organo responsable. */
    private OrganoResponsable organo;
    	
    /** Id dominio: textbox. */
    private Textbox codigo;
    /** Id dominio: label obligatorio. */
    private Label codigoMandatory;
    /** Descripcion dominio. */
    private Textbox descripcion;
    /** Atributo cachear de DominioWModal. */
    private Checkbox cachear;
    /** Tipo dominio. */
    private Radiogroup tipo;
    /** Tipo dominio SQL. */
    private Radio sql;
    /** Tipo dominio EJB. */
    private Radio ejb;
    /** Tipo dominio WS. */
    private Radio webservice;
    /** Tipo dominio Fuente datos. */
    private Radio fuenteDatos;
    
    /** SQL: atributos. */
    private Vlayout sqlFields;
    /** SQL: Datasource. */
    private Textbox sqlDatasource;
    /** SQL: query. */
    private Codemirror sqlQuery;
    
    /** EJB: atributos.*/
    private Vlayout ejbFields;
    /** EJB: JNDI. */
    private Textbox ejbJndi;
    /** EJB: URL. */
    private Textbox ejbUrl;
    /** EJB: URL (label). */
    private Label lblEjbUrl;
    /** EJB: Localizacion. */
    private Radiogroup ejbLocalizacion;
    /**  EJB: Localizacion local. */
    private Radio ejbLocalizacionLocal;
    /**  EJB: Localizacion remota. */
    private Radio ejbLocalizacionRemoto;
    
    /** WS: atributos. */
    private Vlayout wsFields;
    /** WS: URL. */
    private Textbox wsUrl;
    /** WS: Version. */
    private Combobox wsVersion;
    /** WS: Version 1. */
    private Comboitem wsVersionV1;
    /** WS: Version 2. */
    private Comboitem wsVersionV2;
    /** SoapAction. */
    private Textbox wsSoapAction;
        
    /** FD: atributos. */
    private Vlayout fdFields;
    /** FD: query. */
    private Codemirror fdQuery;
    
    /** Auth. */
    private Radiogroup autenticacionExplicita;
    /**  Aut Explicita no. */
    private Radio autenticacionExplicitaNo;
    /**  Aut Explicita no. */
    private Radio autenticacionExplicitaUsuPass;
    /**  Aut Explicita no. */
    private Radio autenticacionExplicitaOrganismo;
    /** Usu. */
    private Textbox autenticacionExplicitaUsuario;   
    /** Pass. */
    private Textbox autenticacionExplicitaPassword;   
    /** Layout usu/pass.*/
    private Hlayout usuPassLayout;
    /** Fila configuracion auth.*/
    private Row autenticacionRow;
    
    
    /** Boton ping.*/
    private Row pingRow;
    
    
    
    /**
     * After compose.
     * 
     * @param compDominio
     *            Parámetro comp dominio
     */
    @Override
    public final void doAfterCompose(final Component compDominio) {
     	super.doAfterCompose(compDominio);
    	
     	// Parametros apertura
     	modo = (TypeModoAcceso) arg.get(ConstantesWEB.PARAM_MODO_ACCESO);
     	dominio = (Dominio) arg.get(ConstantesWEB.PARAM_OBJETO_EDICION);
     	organo = (OrganoResponsable)  arg.get(ConstantesWEB.PARAM_OBJETO_PADRE);

     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
     	
     	//Refrescamos valores
     	refrescarDatos();
        
     	// Mapea datos a los controles de la pantalla
     	mapearDatos();
     	
    }

    /**
     * Si se pasan datos, se refrescan de BBDD para asegurarse q es lo ultimo.
     */
    private void refrescarDatos() {
    	try {            	
    		if (dominio != null) {
    			dominio = DelegateUtil.getDominioDelegate().obtenerDominio(dominio.getCodigo());
    		}        	
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}	
    }


    /**
     * Mapea datos a los controles de la pantalla.
     */
	private void mapearDatos() {
		
		// Mapeamos datos segun modo edicion
     	if (modo == TypeModoAcceso.ALTA) {
     		pingRow.setVisible(false);
     		tipo.setSelectedItem(sql);	
     		autenticacionExplicita.setSelectedItem(autenticacionExplicitaNo);
     	} else {
     		
			codigo.setValue(dominio.getIdentificador());
			descripcion.setValue(dominio.getDescripcion());
			cachear.setChecked(dominio.getCacheable() == 'S');
			
			// En funcion tipo dominio habilitamos campos
			switch (dominio.getTipo()) {
				case Dominio.DOMINIO_SQL:
					tipo.setSelectedItem(sql);					
					sqlDatasource.setValue(dominio.getUrl());
			        sqlQuery.setValue(dominio.getSql());                    
			        break;
				case Dominio.DOMINIO_EJB:
					tipo.setSelectedItem(ejb);					
					ejbJndi.setValue(dominio.getJNDIName());
					ejbUrl.setValue(dominio.getUrl());
					switch (dominio.getLocalizacionEJB()) {
			    		case Dominio.EJB_LOCAL:
			    			ejbLocalizacion.setSelectedItem(ejbLocalizacionLocal);			    			
			    			break;
			    		case Dominio.EJB_REMOTO:
			    			ejbLocalizacion.setSelectedItem(ejbLocalizacionRemoto);
			    			break;
					}            		
					break;
				case Dominio.DOMINIO_WEBSERVICE:
					tipo.setSelectedItem(webservice);
					wsUrl.setValue(dominio.getUrl());
					wsVersion.setSelectedItem( (dominio.getVersionWS() == "v1"? wsVersionV1 : wsVersionV2));
					wsSoapAction.setValue(dominio.getSoapActionWS());
					break;
				case Dominio.DOMINIO_FUENTE_DATOS:
					tipo.setSelectedItem(fuenteDatos);
					fdQuery.setValue(dominio.getSql());
					break;
			}						
			
			// Auth
			switch (dominio.getAutenticacionExplicita()) {	
				case Dominio.AUTENTICACION_EXPLICITA_SINAUTENTICAR:
					autenticacionExplicita.setSelectedItem(autenticacionExplicitaNo);
					break;
				case Dominio.AUTENTICACION_EXPLICITA_ESTANDAR:
					autenticacionExplicita.setSelectedItem(autenticacionExplicitaUsuPass);	
					try {
						String claveCifrado = (String) DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");					 
						autenticacionExplicitaUsuario.setValue(CifradoUtil.descifrar(claveCifrado,dominio.getUsr()));
						autenticacionExplicitaPassword.setValue(CifradoUtil.descifrar(claveCifrado,dominio.getPwd()));
					} catch (Exception ex) {
						ConsolaUtil.generaDelegateException(ex);
					}
					break;
				case Dominio.AUTENTICACION_EXPLICITA_ORGANISMO:
					autenticacionExplicita.setSelectedItem(autenticacionExplicitaOrganismo);
					break;
				default:
					autenticacionExplicita.setSelectedItem(autenticacionExplicitaNo);									
			}
						
		}
				
		// En funcion tipo habilitamos campos
     	onCheck$tipo();
     	onCheck$autenticacionExplicita();
     	
	}
	
	/**
     * Manejador cambio radio auth.
     */
    public final void onCheck$autenticacionExplicita() {
    	usuPassLayout.setVisible(autenticacionExplicita.getSelectedItem() == autenticacionExplicitaUsuPass); 
    }
	
	 /**
     * Manejador cambio radio tipo dominio.
     */
    public final void onCheck$tipo() {
        if (tipo.getSelectedItem() == sql) {
        	autenticacionRow.setVisible(false);
        	sqlFields.setVisible(true);
            ejbFields.setVisible(false);
            wsFields.setVisible(false);
            fdFields.setVisible(false);            
        } else if (tipo.getSelectedItem() == ejb) {
        	autenticacionRow.setVisible(true);
        	sqlFields.setVisible(false);
            ejbFields.setVisible(true);
            wsFields.setVisible(false);
            fdFields.setVisible(false);            
        	onCheck$ejbLocalizacion();            
        } else if (tipo.getSelectedItem() == webservice) {
        	autenticacionRow.setVisible(true);
        	sqlFields.setVisible(false);
            ejbFields.setVisible(false);
            wsFields.setVisible(true);
            fdFields.setVisible(false);
        } else if (tipo.getSelectedItem() == fuenteDatos) {
        	autenticacionRow.setVisible(false);
        	sqlFields.setVisible(false);
            ejbFields.setVisible(false);
            wsFields.setVisible(false);
            fdFields.setVisible(true);
        }
    }
    
    /**
     * Manejador cambio radio localizacion EJB.
     */
    public final void onCheck$ejbLocalizacion() {
    	// Muestra URL solo si remoto
    	boolean mostrarUrl = (ejbLocalizacion.getSelectedItem() == ejbLocalizacionRemoto); 
    	lblEjbUrl.setVisible(mostrarUrl);
    	ejbUrl.setVisible(mostrarUrl);
    }
	
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wDominio.detach();
    }
    
    /**
     * Click boton Guardar.
     */
    public final void onClick$btnGuardar() {

    	Dominio dominioModif = null;
    	
    	try {
	    	// Modo consulta: acceso no permitido
	    	if (modo == TypeModoAcceso.CONSULTA) {
	    		ConsolaUtil.generaOperacionNoPermitidaException();
	    	}else if (modo == TypeModoAcceso.ALTA) {
	        	dominioModif = new Dominio();  
	        	dominioModif.setOrganoResponsable(organo);
	        } else {
	        	dominioModif = dominio;            	            
	        }
	    	    	
	    	// Verificamos campos obligatorios y establecemos valores
	    	// - Codigo / desc / cacheable
	        String[] nomCampos = {Labels.getLabel("dominio.id"), Labels.getLabel("dominio.descripcion")};
	        String[] valCampos = {codigo.getText(),descripcion.getText()};
	        boolean ok = verificarCamposObligatorios(nomCampos, valCampos);
	        // Verifica si id tiene formato correcto
	    	String codigoText = codigo.getText().toUpperCase();
	    	codigo.setText(codigoText);
			if (ok && !StringUtil.validarFormatoIdentificador(codigoText) ){
	    		ok = false;
	        	mostrarError(Labels.getLabel("procedimiento.identificador") + ": " + Labels.getLabel("error.formatoIdentificadorNoValido"),
	        			 Labels.getLabel("mensaje.atencion"));       	
			}
	    	if (ok) {
	    		dominioModif.setIdentificador(codigoText);
	    		dominioModif.setDescripcion(descripcion.getText());
	    		dominioModif.setCacheable(cachear.isChecked()?'S':'N');
	    	}
	    	// - Tipo
	    	if (ok) {
		    	 if (tipo.getSelectedItem() == sql) {
		    		// Datasource / Sql Obligatorio
		 			ok = verificarCampoObligatorio(Labels.getLabel("dominio.sql.datasource"), sqlDatasource.getText());
		 			ok = ok && verificarCampoObligatorio(Labels.getLabel("dominio.sql.query"), sqlQuery.getValue());
		 			if (ok) {
		 				dominioModif.setTipo(Dominio.DOMINIO_SQL);
		 				dominioModif.setUrl(sqlDatasource.getText());
		 				dominioModif.setSql(sqlQuery.getValue());
		 			}
		         } else if (tipo.getSelectedItem() == ejb) {
		        	 	// JNDI Obligatorio
		    			ok = verificarCampoObligatorio(Labels.getLabel("dominio.ejb.jndi"), ejbJndi.getText()); 		    				
		    			// Url obligario para remoto
		    			if (ejbLocalizacionRemoto.isChecked()) {
		    				ok = ok && verificarCampoObligatorio(Labels.getLabel("dominio.ejb.localizacion"), ejbUrl.getText());
		    			}
		    			if (ok) {
		    				dominioModif.setTipo(Dominio.DOMINIO_EJB);
		    				dominioModif.setLocalizacionEJB(ejbLocalizacionRemoto.isChecked()?Dominio.EJB_REMOTO:Dominio.EJB_LOCAL);
		    				dominioModif.setJNDIName(ejbJndi.getText());
		    				dominioModif.setUrl(ejbUrl.getText());
		    			}	        	 	        	 
		         } else if (tipo.getSelectedItem() == webservice) {
		        	// Version ws
	 				if (wsVersion.getSelectedItem() == null) {
	 					mostrarError(Labels.getLabel("error.campoObligatorio") + " '"
	 		                    + Labels.getLabel("dominio.ws.version") + "'", Labels.getLabel("mensaje.atencion"));
	 					ok = false;
	 				}
	 				// Url 
	 				ok = ok && verificarCampoObligatorio(Labels.getLabel("dominio.ws.url"), wsUrl.getText());
	 				
	 				if (ok) {
	 					dominioModif.setTipo(Dominio.DOMINIO_WEBSERVICE);
	 					dominioModif.setVersionWS((String) wsVersion.getSelectedItem().getValue());
	 					dominioModif.setUrl(wsUrl.getText());
	 					dominioModif.setSoapActionWS(wsSoapAction.getText());	 					
	 				}	        	 
		         } else if (tipo.getSelectedItem() == fuenteDatos) {
		        	// Sql Obligatorio	 			
		 			ok = verificarCampoObligatorio(Labels.getLabel("dominio.fd.query"), fdQuery.getValue());
		 			if (ok) {
		 				dominioModif.setTipo(Dominio.DOMINIO_FUENTE_DATOS);
		 				dominioModif.setSql(fdQuery.getValue());		 				
		 			}	        	 
		         }
	    	}
	    	
	    	// - Autenticacion
	    	if (ok) {
		    	dominioModif.setAutenticacionExplicita(((String) autenticacionExplicita.getSelectedItem().getValue()).charAt(0));
		        String claveCifrado = (String) DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");					 
		        dominioModif.setUsr(CifradoUtil.cifrar(claveCifrado,autenticacionExplicitaUsuario.getValue()));
		        dominioModif.setPwd(CifradoUtil.cifrar(claveCifrado,autenticacionExplicitaPassword.getValue()));
	    	}
	    	 
	        
	        // Guardamos datos
	        if (ok) {                	        	                                       	                        	
	           	// Grabamos organo
	        	Long id = DelegateUtil.getDominioDelegate().grabarDominio(dominioModif, organo.getCodigo());
	        	dominioModif.setCodigo(id);
	        	//Generamos evento            	
	            Events.postEvent(new Event(ConsolaUtil.eventoModoAcesso(modo), wDominio.getParent(), dominioModif));    		    		
	            wDominio.detach();
	        }
    	
        } catch (Exception e) {
			ConsolaUtil.generaDelegateException(e);
		}
    }
    
    /**
     * Click boton Ping.
     */
    public final void onClick$btnPing() {
    	final Map<String, Object> map = new HashMap<String, Object>();
        map.put(ConstantesWEB.PARAM_OBJETO_EDICION, dominio);     
		final Window ventana = (Window) creaComponente(
                "/gestor/windows/ges-dominioPing-win.zul", this.self, map);
        abreVentanaModal(ventana);
    }
    
    /**
     * Ayuda.
     * 
     */
    public final void onClick$btnAyuda() {
        GTTUtilsWeb.onClickBotonAyuda("gestor/fichaDominio");
    }
    
}

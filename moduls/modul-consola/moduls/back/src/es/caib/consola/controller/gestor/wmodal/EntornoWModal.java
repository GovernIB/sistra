package es.caib.consola.controller.gestor.wmodal;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.util.ConsolaUtil;
import es.caib.sistra.model.OrganoResponsable;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Class EntornoWModal.
 */
@SuppressWarnings("serial")
public class EntornoWModal extends BaseComposer {

   /**
     * Listbox organismos.
     */
    private Listbox listOrganismos;

    /**
     * Usuario logeado.
     */
    private Usuario usuarioLogado;

    /**
     * Atributo organismos de EntornoWModal.
     */
    private List<OrganoResponsable> organismos = new ArrayList<OrganoResponsable>();

    /**
     * Atributo w entorno de EntornoWModal.
     */
    private Window wEntorno;

    /**
     * Atributo contenedor principal de EntornoWModal.
     */
    private Include contenedorPrincipal;

    /** Atributo descripcion organismo lh de EntornoWModal. */
    private Listheader descripcionOrganismoLH;
    
    /** Atributo filtro organismo de EntornoWModal. */
    private Textbox filtroOrganismo;
  

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.gva.dgm.tra.util.zk.composer.BaseComposer#doAfterCompose(org.zkoss
     * .zk.ui.Component)
     */
    @Override
    public final void doAfterCompose(final Component compEntorno) {
       
    	
    	super.doAfterCompose(compEntorno);

        usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        
        /*
        contenedorPrincipal = (Include) compEntorno.getParent().getParent()
                .getParent().getParent();
        */
        
        contenedorPrincipal = (Include) Path.getComponent("/priWindow/contenedorPrincipal");

        try {
			organismos = DelegateUtil.getOrganoResponsableDelegate().listarOrganoResponsables();
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}
		
        listOrganismos.setItemRenderer(new ListitemRenderer() {
            public void render(final Listitem item, final Object data) {
                final OrganoResponsable reg = (OrganoResponsable) data;
                item.setValue(reg);
                item.setLabel(reg.getDescripcion());
                item.setAttribute("data", data);
                ComponentsCtrl.applyForward(item,
                        "onDoubleClick=onDoubleClick$itemOrganismo");
            }
        });

        listOrganismos.setModel(new BindingListModelList(organismos, true));
        
    }

    /**
     * Click boton buscar organismo.
     */
    public final void onClick$btnBuscarOrganismos() {
    	
    	try {
			organismos = DelegateUtil.getOrganoResponsableDelegate().listarOrganoResponsables(filtroOrganismo.getValue());
		} catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}
    	
    	listOrganismos.selectItemApi(null);       
    	listOrganismos.setModel(new BindingListModelList(organismos, true));   
    }
    
    
    /**
     * Método para On click$btn aceptar de la clase EntornoWModal.
     */
    public final void onClick$btnAceptar() {
        
    	if (listOrganismos.getSelectedIndex() == ConstantesZK.SINSELECCION) {
        	mostrarMessageBox(Labels.getLabel("entorno.faltaDato"),
                    Labels.getLabel(ConstantesWEB.INFO), Messagebox.OK,
                    Messagebox.EXCLAMATION);
        	return;
        }	
            
        OrganoResponsable value = (OrganoResponsable) ((Listitem) listOrganismos
                    .getSelectedItemApi()).getValue();
		usuarioLogado.setOrganismo(value.getCodigo());
        wEntorno.detach();

        session.setAttribute(ConstantesWEB.TRAMITE, null);
        session.setAttribute(ConstantesWEB.TRAMITE_ID, null);
        session.setAttribute(ConstantesWEB.VERSION, null);
        session.setAttribute(ConstantesWEB.VERSION_NUM, null);
        
        // Se pone zul vacio ya que hay problemas a veces con cacheos
        contenedorPrincipal.setSrc("/ventanaVaciaParaInclude.zul");
        habilitaInclude(contenedorPrincipal, "/gestor/ges-organismo.zul",
                null);

    }

    /**
     * Método para On double click$item organismo de la clase EntornoWModal.
     */
    public final void onDoubleClick$itemOrganismo() {
        onClick$btnAceptar();
    }
    
    /**
     * Click boton Cerrar.
     */
    public final void onClick$btnCancelar() {
    	wEntorno.detach();
    }
}

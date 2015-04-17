package es.caib.consola.controller.gestor.wmodal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.util.ConsolaUtil;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.delegate.DelegateSISTRAUtil;
import es.caib.util.StringUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de ping dominio.
 */
@SuppressWarnings("serial")
public class DominioPingWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wDominioPing;
    /** Dominio editado.*/
    private Dominio dominio;
    	
    
    /** parametros. */
    private Textbox parametros;
    /** Resultado. */
    private Textbox resultado;
    /** idDominio. */
    private Textbox idDominio;
    
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
     	dominio = (Dominio) arg.get(ConstantesWEB.PARAM_OBJETO_EDICION);
     	
     	idDominio.setValue(dominio.getIdentificador());
    }

    
	
	 /**
     * Click boton Cerrar.
     */
    public final void onClick$btnCerrar() {
    	wDominioPing.detach();
    }
    
    /**
     * Click boton Ping.
     */
    public final void onClick$btnPing() {
    	List params = new ArrayList();
    	if (StringUtils.isNotEmpty(parametros.getText())){
    		StringTokenizer st = new StringTokenizer(parametros.getText(),"#");
    		while (st.hasMoreElements()){
    			params.add(st.nextToken());
    		}
    	}
    	    	
    	try{
	    	ValoresDominio val = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio(dominio.getIdentificador(),params);
	    	if (val.isError()){
	    		resultado.setText(val.getDescripcionError());	    		
	    	}else{	    		
	    		String res = "";
	    		for (int i=0;i<val.getFilas().size();i++){
	    			HashMap fila = (HashMap) val.getFilas().get(i);
	    			String ls_fila="";
	    			for (Iterator it = fila.keySet().iterator();it.hasNext();){
	    				String ls_key = (String) it.next();
	    				ls_fila += "[" + ls_key.toUpperCase() + "=" + fila.get(ls_key.toUpperCase()) + "]  ";				
	    			}
	    			res += ls_fila + "\n";	    			
	    		}
	    		resultado.setText(res);	
	    	}
    	}catch (Exception ex){
    		ConsolaUtil.generaDelegateException(ex);
    	}
    }
    
    /**
     * Ayuda.
     * 
     */
    public final void onClick$btnAyuda() {
        GTTUtilsWeb.onClickBotonAyuda("gestor/fichaDominioPing");
    }
    
}

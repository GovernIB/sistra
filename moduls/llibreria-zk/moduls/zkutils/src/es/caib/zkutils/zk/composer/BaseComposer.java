/**
 * 
 * @author Indra
 */
package es.caib.zkutils.zk.composer;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.util.resource.Labels;

import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.SimacCommonUtils;
import es.caib.zkutils.zk.exceptions.AfterComposeException;
import es.caib.zkutils.zk.model.SeleccionSelector;

/**
 * Class BaseComposer.
 */
@SuppressWarnings("serial")
public abstract class BaseComposer extends GenericForwardComposer {
	
	 /** Componente. */
    private Component componentCompose;
    
    /** {@inheritDoc} **/
    @Override
    public void doAfterCompose(final Component comp) {
        try {
            super.doAfterCompose(comp);
            componentCompose = comp;
            // Evento post confirmacion de borrado
            comp.addEventListener(ConstantesZK.EVENTO_POST_CONFIRMAR_BORRADO,
                    new EventListener() {
                        public void onEvent(final Event pEvent) {
                            postConfirmarBorrado(pEvent.getData());
                        }

                    });
            // Evento seleccion selectores         
            comp.addEventListener(ConstantesZK.EVENTO_SELECCION_SELECTOR,
                    new EventListener() {
                        public void onEvent(final Event pEvent) {
                           final SeleccionSelector o = (SeleccionSelector) pEvent.getData();
                           onSeleccionSelector(o.getTipoSelector(),o.getValorSeleccionado());
                        }
                    });

        } catch (final Exception ex) {
            throw new AfterComposeException(
                    "Ha ocurrido un error en el componente " + comp.getId()
                            + ":", ex);
        }
    }
    
    /**
     * Funcion confirmacionBorrado que deberá ser sobreescrita en
     * implementacion.
     * 
     * @param pObj
     *            Objeto que se pasa por la ventana en la funcion
     *            confirmarBorrado
     */
    protected void postConfirmarBorrado(final Object pObj) {
        // A sobreescribir.
    }
    
    /**
     * Funcion para tratar la seleccion de selectores que deberá ser sobreescrita en
     * implementacion.
     * 
     * @param tipoSelector
     *           Tipo selector
     * @param valorSeleccionado  valor seleccionado
     */
    protected void onSeleccionSelector(final String tipoSelector, Object valorSeleccionado) {
        // A sobreescribir.
    }

    /**
     * Método para Habilita include de la clase BaseComposer.
     * 
     * @param include
     *            Parámetro include
     * @param zul
     *            Parámetro zul
     * @param params
     *            Parámetro params
     */
    public final void habilitaInclude(final Include include, final String zul,
            final Map<?, ?> params) {
        SimacCommonUtils.habilitaInclude(include, zul, params);
    }

    /**
     * Obtiene el atributo param execution de BaseComposer.
     * 
     * @param key
     *            Parámetro key
     * @return el atributo param execution
     */
    public final Object getParamExecution(final String key) {
        final Execution execution = Executions.getCurrent();
        Object obj = null;
        if (execution != null) {
            // PRIMERO BUSCAMOS EN LOS ARGUMENTOS DE ENTRADA
            final Map<?, ?> hm = execution.getArg();
            if (hm != null) {
                obj = hm.get(key);
            }
            if (obj == null) {
                // LUEGO BUSCAMOS EN LOS ATRIBUTOS
                obj = execution.getAttribute(key);
                if (obj == null) {
                    // LUEGO BUSCAMOS EN LOS PARÁMETROS (por ejemplo si
                    // invocamos vía
                    // sendRedirect).
                    obj = execution.getParameter(key);
                }
            }

        }

        return obj;
    }

    /**
     * Método para Crear componente de la clase BaseComposer.
     * 
     * @param uri
     *            Parámetro uri
     * @param parent
     *            Parámetro parent
     */
//    public final void crearComponente(final String uri, final Component parent) {
//
//        Executions.createComponents(uri, parent, null);
//    }

    /**
     * Método para Seleccionar combo de la clase BaseComposer.
     * 
     * @param value
     *            Parámetro value
     * @param combo
     *            Parámetro combo
     */
    public final void seleccionarCombo(final String value, final Combobox combo) {
        SimacCommonUtils.seleccionarCombo(value, combo);
    }  

    /**
     * Método para Mostrar message box de la clase BaseComposer.
     * 
     * @param message
     *            Parámetro message
     * @param tittle
     *            Parámetro tittle
     * @param buttons
     *            Parámetro buttons
     * @param icon
     *            Parámetro icon
     */
    public void mostrarMessageBox(final String message, final String tittle,
            final int buttons, final String icon) {
        SimacCommonUtils.mostrarMessageBox(message, tittle, buttons, icon);
    }

    /**
     * Método para Mostrar message box con listener de la clase BaseComposer.
     * 
     * @param message
     *            Parámetro message
     * @param tittle
     *            Parámetro tittle
     * @param buttons
     *            Parámetro buttons
     * @param icon
     *            Parámetro icon
     * @param listener
     *            Parámetro listener
     */
    public void mostrarMessageBox(final String message, final String tittle,
            final int buttons, final String icon, final EventListener listener) {
        SimacCommonUtils.mostrarMessageBox(message, tittle, buttons, icon,
                listener);
    }

    /**
     * Método para Abre ventana modal de la clase BaseComposer.
     * 
     * @param ventana
     *            Parámetro ventana
     */
    public void abreVentanaModal(final Window ventana) {

        SimacCommonUtils.abreVentanaModal(ventana);

    }

    /**
     * Método para Object clone de la clase BaseComposer.
     * 
     * @param o
     *            Parámetro o
     * @return el object
     */
    public Object objectClone(final Object o) {
        return SimacCommonUtils.objectClone(o);
    }

    /**
     * Método para Mostrar error de la clase BaseComposer.
     * 
     * @param message
     *            Parámetro message
     * @param tittle
     *            Parámetro tittle
     */
    public void mostrarError(final String message, final String tittle) {
        SimacCommonUtils.mostrarError(message, tittle);
    }

    /** Muestra mensaje de info.
     * 
     * @param message mensaje 
     * @param tittle titulo
     */
    public void mostrarInfo(final String message, final String tittle) {
    	SimacCommonUtils.mostrarMessageBox(message, tittle, Messagebox.OK, Messagebox.INFORMATION);
    }    
    
    /**
     * Método para Crea componente ventana de la clase BaseComposer.
     * 
     * @param rutaZul
     *            Parámetro ruta zul
     * @param padre
     *            Parámetro padre
     * @param mapa
     *            Parámetro mapa
     * @return el component
     */
    public Component creaComponente(final String rutaZul, final Component padre,
            final Map<String, Object> mapa) {

        return SimacCommonUtils.creaComponente(rutaZul,
                padre, mapa);
    }

    
    /**
     * Verifica si el campo tiene valor.
     * 
     * @param nombreCampo
     *            Nombre campo
     * @param valorCampo
     *            Valor campo
     * @return true si esta rellenado
     */
    protected final boolean verificarCampoObligatorio(final String nombreCampo,
            final String valorCampo) {
        final boolean rellenado = StringUtils.isNotBlank(valorCampo);
        if (!rellenado) {
            mostrarError(Labels.getLabel("error.campoObligatorio") + " '"
                    + nombreCampo + "'", Labels.getLabel("mensaje.atencion"));
        }
        return rellenado;
    }

    /**
     * Verifica si los campos tienen valor.
     * 
     * @param nombresCampos
     *            Nombres campos
     * @param valoresCampos
     *            Valores campos
     * @return true si esta rellenado
     */
    protected final boolean verificarCamposObligatorios(
            final String[] nombresCampos, final String[] valoresCampos) {
        boolean res = true;
        for (int i = 0; i < nombresCampos.length; i++) {
            res = verificarCampoObligatorio(nombresCampos[i], valoresCampos[i]);
            if (!res) {
                break;
            }
        }
        return res;
    }
    
    /**
     * Obtiene item seleccionado en el listbox. Si no hay ninguno seleccionado
     * muestra mensaje de que no se ha seleccionado elemento.
     * 
     * @param listbox
     *            Listbox
     * @return Elemento seleccionado (null si no hay ninguno seleccionado).
     */
    protected final Object obtenerItemSeleccionado(final Listbox listbox) {
        Object valueSelected = null;
        if (listbox.getSelectedIndex() == ConstantesZK.SINSELECCION) {
            mostrarMessageBox(
                    Labels.getLabel(ConstantesZK.SINSELECCION_LABEL),
                    Labels.getLabel("mensaje.atencion"), Messagebox.OK,
                    Messagebox.EXCLAMATION);
        } else {
            valueSelected = listbox.getSelectedItem().getValue();
        }
        return valueSelected;
    }
    
    /**
     * Muestra mensaje de confirmacion de borrado. En caso de aceptar lanza
     * funcion confirmacionBorrado que deberá ser sobreescrita en la
     * implementacion.
     * 
     * @param obj
     *            Objeto a ser pasado a la funcion postConfirmarBorrado en caso
     *            de que se confirme el borrado (opcional). Por si se quiere
     *            pasar algo para particularizar funcionamiento
     *            postConfirmarBorrado.
     * 
     */
    protected final void confirmarBorrado(final Object obj) {
        mostrarMessageBox(Labels.getLabel("mensaje.confirmacionBorrado"),
                Labels.getLabel("mensaje.atencion"), Messagebox.YES
                        + Messagebox.NO, Messagebox.EXCLAMATION,
                new EventListener() {					
                    public void onEvent(final Event event) {
                        if (((Integer) event.getData()).intValue() == Messagebox.YES) {
                            // Invocamos evento confirmacion borrado
                            final Event ev = new Event(
                            		ConstantesZK.EVENTO_POST_CONFIRMAR_BORRADO,
                                    componentCompose, obj);
                            Events.postEvent(ev);
                        }
                    }
                });
    }
    
    
    /**
     * Obtiene component asociado.
     * @return component
     */
    public Component getComponent() {
    	return this.self;
    }
}

/*
 * 
 */
package es.caib.zkutils.zk;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.exceptions.ShowMessageException;

/**
 * Class SimacCommonUtils.
 */
public final class SimacCommonUtils {

    /**
     * Instancia un nuevo simac common utils de SimacCommonUtils.
     */
    private SimacCommonUtils() {

    }

    /**
     * Método para Habilita include de la clase SimacCommonUtils.
     * 
     * @param include
     *            Parámetro include
     * @param zul
     *            Parámetro zul
     * @param params
     *            Parámetro params
     */
    public static void habilitaInclude(final Include include, final String zul,
            final Map<?, ?> params) {
        // Invalidamos el include ya que cuando es el mismo zul pero con cambios
        // en los parametros
        // lo omite (al ponerlo emporamos rendimiento, mejor invocar el
        // invalidate cuando se necesario antes)
        // if(include.getSrc().equals(zul)){
        // include.invalidate();
        // }
        // En vez de invalidar se setea a null el source
        if (include.getSrc() != null && include.getSrc().equals(zul)) {
            include.setSrc(null);
        }

        // Primero borramos los parámetros del include si los hubiera (esto lo
        // hacemos porque sino, al hacer setDynamicProperty, parece que
        // está manteniendo los parámetros como atributos en la ejecución actual
        // (executions.getAttributes), por lo que se mantendrán los
        // parámetros en toda la ejecución.
        include.clearDynamicProperties();
        // Pasamos los parámetros
        if (params != null) {
            final Set<?> set = params.entrySet();
            for (final Iterator<?> iterator = set.iterator(); iterator
                    .hasNext();) {
                final Entry<?, ?> entrada = (Entry<?, ?>) iterator.next();
                include.setDynamicProperty((String) entrada.getKey(),
                        entrada.getValue());
            }
        }
        include.setSrc(zul);
        include.setVisible(true);
    }

    /**
     * Método para Seleccionar combo de la clase SimacCommonUtils.
     * 
     * @param value
     *            Parámetro value
     * @param combo
     *            Parámetro combo
     */
    public static void seleccionarCombo(final String value, final Combobox combo) {
        @SuppressWarnings("unchecked")
        final List<Comboitem> items = combo.getItems();
        for (final Iterator<Comboitem> itCombo = items.iterator(); itCombo
                .hasNext();) {
            final Comboitem next = itCombo.next();
            if (next.getValue().equals(value)) {
                combo.setSelectedItem(next);
                break;
            }
        }
    }

    /**
     * Método para Object clone de la clase SimacCommonUtils.
     * 
     * @param o
     *            Parámetro o
     * @return el object
     */
    public static Object objectClone(final Object o) {
    	
    	throw new RuntimeException("NO SOPORTADO");
    	
    	/*
        Object ret;
        final Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
        try {
            ret = mapper.map(o, o.getClass());
        } catch (final MappingException ex) {
            throw new ShowMessageException(
                    "Ha ocurrido un error clonando el objeto", ex);
        }
        return ret;
        */
    }

    /**
     * Método para Mostrar error de la clase SimacCommonUtils.
     * 
     * @param message
     *            Parámetro message
     * @param tittle
     *            Parámetro tittle
     */
    public static void mostrarError(final String message, final String tittle) {
        try {
            Messagebox.show(message, tittle, Messagebox.OK, Messagebox.ERROR);

        } catch (final InterruptedException ex) {
            throw new ShowMessageException(
                    "Ha ocurrido un error mostrando el mensaje", ex);
        }
    }

    /**
     * Método para Abre ventana modal de la clase SimacCommonUtils.
     * 
     * @param ventana
     *            Parámetro ventana
     */
    public static void abreVentanaModal(final Window ventana) {
        try {
            ventana.doModal();
        } catch (final SuspendNotAllowedException ex) {
            throw new ShowMessageException(
                    "Ha ocurrido un error(ShowMessage) en abriendo la ventana modal",
                    ex);
        } catch (final InterruptedException ex) {
            throw new ShowMessageException(
                    "Ha ocurrido un error(Interrupted) en abriendo la ventana modal ",
                    ex);
        }
    }

    /**
     * Método para Mostrar message box de la clase SimacCommonUtils.
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
    public static void mostrarMessageBox(final String message,
            final String tittle, final int buttons, final String icon) {
        try {
            Messagebox.show(message, tittle, buttons, icon);

        } catch (final InterruptedException ex) {
            throw new ShowMessageException(
                    "Ha ocurrido un error mostrando el mensaje", ex);
        }
    }

    /**
     * Método para Mostrar message box de la clase SimacCommonUtils.
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
    public static void mostrarMessageBox(final String message,
            final String tittle, final int buttons, final String icon,
            final EventListener listener) {
        try {
            Messagebox.show(message, tittle, buttons, icon, listener);

        } catch (final InterruptedException ex) {
            throw new ShowMessageException(
                    "Ha ocurrido un error mostrando el mensaje con listener",
                    ex);
        }
    }

    /**
     * Método para Crea componente ventana de la clase SimacCommonUtils.
     * 
     * @param rutaZul
     *            Parámetro ruta zul
     * @param padre
     *            Parámetro padre
     * @param mapa
     *            Parámetro mapa
     * @return el component
     */
    public static Component creaComponente(final String rutaZul,
            final Component padre, final Map<String, Object> mapa) {
        Component componenteRet = null;
        UiException lanzarUiExcep = null;
        try {
            componenteRet = Executions
                    .createComponents(rutaZul, padre, mapa);
        } catch (UiException uie) {
            // Si es del tipo UIException hacer detach y volver a abrir
            if (uie.getMessage().contains("Not unique")) {
                @SuppressWarnings("unchecked")
                final Collection<Component> componentes = Executions
                        .getCurrent().getDesktop().getComponents();
                final int dosPuntos = uie.getMessage().indexOf(":");
                final String idVentana = uie.getMessage()
                        .substring(dosPuntos + ConstantesZK.DOS).trim();
                for (Component comp : componentes) {
                    if (org.zkoss.zul.Window.class.equals(comp.getClass())
                            && idVentana.equals(comp.getId())) {
                        comp.detach();
                        break;
                    }
                }
                componenteRet = Executions.createComponents(rutaZul, padre,
                        mapa);
            } else {
                lanzarUiExcep = uie;
            }
        }

        if (lanzarUiExcep != null) {
            throw lanzarUiExcep;
        }
        return componenteRet;
    }
}

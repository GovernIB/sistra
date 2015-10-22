/**
 * 
 * @author Indra
 */
package es.caib.zkutils.zk;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.metainfo.ComponentInfo;

/**
 * Class IdGenerator.
 * 
 */
public class IdGenerator implements org.zkoss.zk.ui.sys.IdGenerator {

    /**
     * Atributo constante ID_NUM.
     */
    private static final String ID_NUM = "Id_Num";

    /* (non-Javadoc)
     * @see org.zkoss.zk.ui.sys.IdGenerator#nextComponentUuid(org.zkoss.zk.ui.Desktop, org.zkoss.zk.ui.Component)
     */
  //  @Override
    public final String nextComponentUuid(final Desktop desktop,
            final Component comp, final ComponentInfo compInfo) {
        int i = Integer.parseInt(desktop.getAttribute(ID_NUM).toString());
        i++; // Start from 1
        desktop.setAttribute(ID_NUM, String.valueOf(i));
        return "zk_comp_" + i;
    }

    /* (non-Javadoc)
     * @see org.zkoss.zk.ui.sys.IdGenerator#nextDesktopId(org.zkoss.zk.ui.Desktop)
     */
  //  @Override
    public final String nextDesktopId(final Desktop desktop) {
        if (desktop.getAttribute(ID_NUM) == null) {
            final String number = "0";
            desktop.setAttribute(ID_NUM, number);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.zkoss.zk.ui.sys.IdGenerator#nextPageUuid(org.zkoss.zk.ui.Page)
     */
   // @Override
    public final String nextPageUuid(final org.zkoss.zk.ui.Page page) {
        return null;
    }
}

/**
 * 
 * @author Indra
 */
package es.caib.zkutils.zk.model;

import java.util.Comparator;

import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.TreeitemComparator;
import org.zkoss.zul.event.TreeDataEvent;
import org.zkoss.zul.ext.Sortable;

import es.caib.zkutils.ConstantesZK;

/**
 * Class TreeModelOD.
 */
public class TreeModelOD extends AbstractTreeModel implements Sortable {

    /**
     * Atributo constante serialVersionUID.
     */
    private static final long serialVersionUID = -3901486880065886297L;

    /**
     * Atributo root.
     */
    private final TreeNodeOD root;

    /**
     * Atributo direc.
     */
    private int direc = ConstantesZK.MENOSUNO;

    /**
     * Atributo order by.
     */
    private String orderBy = "";

    /**
     * Instancia un nuevo tree model od de TreeModelOD.
     * 
     * @param proot
     *            Parámetro proot
     */
    public TreeModelOD(final TreeNodeOD proot) {
        super(proot);
        this.root = proot;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.zkoss.zul.TreeModel#getChild(java.lang.Object, int)
     */
   // @Override
    public Object getChild(final Object arg0, final int arg1) {
        return ((TreeNodeOD) arg0).getChild(arg1, orderBy, direc);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.zkoss.zul.TreeModel#getChildCount(java.lang.Object)
     */
    // @Override
    public int getChildCount(final Object arg0) {
        return ((TreeNodeOD) arg0).getChildCount();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.zkoss.zul.TreeModel#isLeaf(java.lang.Object)
     */
    // @Override
    public boolean isLeaf(final Object arg0) {

        boolean retorno = false;

        if (arg0 == null) {
            retorno = true;
        } else {
            retorno = ((TreeNodeOD) arg0).isLeaf();
        }

        return retorno;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.zkoss.zul.TreeModelExt#sort(java.util.Comparator, boolean)
     */
   // @Override
    public void sort(final Comparator pComparator, final boolean pFlag) {
        if (pComparator instanceof TreeitemComparator) {
            // direc = pFlag ? 0 : 1;
            if (pFlag) {
                direc = ConstantesZK.CERO;

            } else {
                direc = ConstantesZK.UNIDAD;
            }

            orderBy = ((TreeitemComparator) pComparator).getTreecol().getId();
            fireEvent(root, ConstantesZK.CERO, root.getChildCount(),
                    TreeDataEvent.STRUCTURE_CHANGED);
        }
    }

	public String getSortDirection(Comparator arg0) {
		// TODO ZK6 VER QUE SE NECESITA
		return "ascending"; 
	}
}

/**
 * 
 */
package es.caib.zkutils.zk.model;

import java.util.List;

import es.caib.zkutils.ConstantesZK;

/**
 * Class TreeNodeOD.
 */
public abstract class TreeNodeOD {

    /**
     * Atributo constante serialVersionUID.
     */
    private static final long serialVersionUID = 3266579228139134460L;

    /**
     * Atributo children.
     */
    private List children;

    /**
     * Obtiene el atributo order by de TreeNodeOD.
     * 
     * @return el atributo order by
     */
    public final String getOrderBy() {
        return orderBy;
    }

    /**
     * Asigna el atributo order by de TreeNodeOD.
     * 
     * @param porderBy
     *            el nuevo valor para order by
     */
    public final void setOrderBy(final String porderBy) {
        this.orderBy = porderBy;
    }

    /**
     * Obtiene el atributo direc de TreeNodeOD.
     * 
     * @return el atributo direc
     */
    public final int getDirec() {
        return direc;
    }

    /**
     * Asigna el atributo direc de TreeNodeOD.
     * 
     * @param pdirec
     *            el nuevo valor para direc
     */
    public final void setDirec(final int pdirec) {
        this.direc = pdirec;
    }

    /**
     * Asigna el atributo children de TreeNodeOD.
     * 
     * @param pchildren
     *            el nuevo valor para children
     */
    public final void setChildren(final List pchildren) {
        this.children = pchildren;
    }

    /**
     * Atributo order by.
     */
    private String orderBy = "";

    /**
     * Atributo direc.
     */
    private int direc = ConstantesZK.MENOSUNO;

    /**
     * Método para Read children de la clase TreeNodeOD.
     */
    public abstract void readChildren();

    /**
     * Obtiene el atributo children de TreeNodeOD.
     * 
     * @return el atributo children
     */
    public List getChildren() {
        return children;
    }

    /**
     * Obtiene el atributo child de TreeNodeOD.
     * 
     * @param arg1
     *            Parámetro arg1
     * @param porderBy
     *            Parámetro porder by
     * @param dir
     *            Parámetro dir
     * @return el atributo child
     */
    public TreeNodeOD getChild(final int arg1, final String porderBy,
            final int dir) {
        TreeNodeOD child = null;
        boolean borrarCache = false;
        if (!this.orderBy.equals(porderBy) || this.direc != dir) {
            borrarCache = true;
            this.orderBy = porderBy;
            this.direc = dir;
        }
        if (children == null || borrarCache) {
            readChildren();
        }
        if (children != null
                && (arg1 > ConstantesZK.MENOSUNO && arg1 < children.size())) {
            child = (TreeNodeOD) children.get(arg1);
        }
        return child;
    }

    /**
     * Obtiene el atributo child count de TreeNodeOD.
     * 
     * @return el atributo child count
     */
    public int getChildCount() {
        int retorno = ConstantesZK.CERO;
        if (children == null) {
            readChildren();
        }
        if (children != null) {
            retorno = children.size();
        }
        return retorno;
    }

    /**
     * Comprueba si es true leaf de TreeNodeOD.
     * 
     * @return true, si es leaf
     */
    public boolean isLeaf() {
        return (getChildCount() == ConstantesZK.CERO);
    }

}

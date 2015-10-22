/**
 * 
 * @author Indra
 */
package es.caib.zkutils.zk.model;

import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

/**
 * Class SortingPagingListModel.
 */
@SuppressWarnings("serial")
public abstract class SortingPagingListModel extends AbstractListModel
        implements Sortable {

    /**
     * Atributo constante DIR_ASC.
     */
    private static final int DIR_ASC = 1;

    /**
     * Atributo constante DIR_DESC.
     */
    private static final int DIR_DESC = 0;

    /**
     * Atributo constante TAMANYO_CACHE.
     */
    private static final int TAMANYO_CACHE = 10;

    /**
     * Atributo constante NULL.
     */
    private static final int NULL = -1;

    /**
     * Atributo begin offset.
     */
    private int beginOffset;

    /**
     * Atributo cache.
     */
    private List<?> cache;

    /**
     * Atributo cache size.
     */
    private int cacheSize = TAMANYO_CACHE;

    /**
     * Atributo cached size.
     */
    private int cachedSize = NULL;

    // Parametros accesibles
    /**
     * Atributo dir.
     */
    private int dir = NULL;

    /**
     * Obtiene el atributo dir de SortingPagingListModel.
     * 
     * @return el atributo dir
     */
    public int getDir() {
        return dir;
    }

    /**
     * Asigna el atributo dir de SortingPagingListModel.
     * 
     * @param pdir
     *            el nuevo valor para dir
     */
    public final void setDir(final int pdir) {
        this.dir = pdir;
    }

    /**
     * Obtiene el atributo order by de SortingPagingListModel.
     * 
     * @return el atributo order by
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * Asigna el atributo order by de SortingPagingListModel.
     * 
     * @param porderBy
     *            el nuevo valor para order by
     */
    public final void setOrderBy(final String porderBy) {
        this.orderBy = porderBy;
    }

    /**
     * Atributo order by.
     */
    private String orderBy;

    // Metodos a sobreescribir

    /**
     * Obtiene el atributo consulta paginada de SortingPagingListModel.
     * 
     * @param itemStartNumber
     *            Parámetro item start number
     * @param pageSize
     *            Parámetro page size
     * @return el atributo consulta paginada
     */
    public abstract List<?> getConsultaPaginada(int itemStartNumber,
            int pageSize);

    /**
     * Obtiene el atributo consulta paginada ordenada de SortingPagingListModel.
     * 
     * @param itemStartNumber
     *            Parámetro item start number
     * @param pageSize
     *            Parámetro page size
     * @return el atributo consulta paginada ordenada
     */
    public abstract List<?> getConsultaPaginadaOrdenada(int itemStartNumber,
            int pageSize);

    /**
     * Obtiene el atributo numero total registros de SortingPagingListModel.
     * 
     * @return el atributo numero total registros
     */
    public abstract int getNumeroTotalRegistros();

    /**
     * Método para Load to cache de la clase SortingPagingListModel.
     * 
     * @param itemStartNumber
     *            Parámetro item start number
     * @param pageSize
     *            Parámetro page size
     */
    private void loadToCache(final int itemStartNumber, final int pageSize) {
        if (dir == NULL) {
            cache = this.getConsultaPaginada(itemStartNumber, pageSize);
        } else {
            cache = getConsultaPaginadaOrdenada(itemStartNumber, pageSize);
        }

        // cache = dir == CACHE_VACIA ?
        // this.getConsultaPaginada(itemStartNumber, pageSize)
        // : getConsultaPaginadaOrdenada(itemStartNumber, pageSize);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.zkoss.zul.ListModel#getElementAt(int)
     */
   // @Override
    public Object getElementAt(final int index) {
        if ((cache == null) || (index < beginOffset)
                || (index >= beginOffset + cacheSize)) {
            beginOffset = index;
            loadToCache(index, cacheSize);
        }
        return cache.get(index - beginOffset);
    }

    /**
     * Método para Borrar cache de la clase SortingPagingListModel.
     */
    public void borrarCache() {
        cache = null;
        cachedSize = NULL;
        beginOffset = 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.zkoss.zul.ListModel#getSize()
     */
    // @Override
    public int getSize() {
        if (cachedSize < 0) {
            cachedSize = this.getNumeroTotalRegistros();
        }
        return cachedSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.zkoss.zul.ListModelExt#sort(java.util.Comparator, boolean)
     */
   //  @Override
    public void sort(@SuppressWarnings("rawtypes") final Comparator comparator,
            final boolean flag) {
        if (comparator instanceof FieldComparator) {
            if (flag) {
                dir = DIR_DESC;
            } else {
                dir = DIR_ASC;
            }
            // dir = flag ? DIR_DESC : DIR_ASC;
            cache = null;
            orderBy = ((FieldComparator) comparator).getRawOrderBy();
            fireEvent(ListDataEvent.CONTENTS_CHANGED, NULL, NULL);
        }
    }

    /**
     * Obtiene el atributo page size de SortingPagingListModel.
     * 
     * @return el atributo page size
     */
    public int getPageSize() {
        return cacheSize;
    }

    /**
     * Asigna el atributo page size de SortingPagingListModel.
     * 
     * @param pPageSize
     *            el nuevo valor para page size
     */
    public final void setPageSize(final int pPageSize) {
        cacheSize = pPageSize;
    }

}

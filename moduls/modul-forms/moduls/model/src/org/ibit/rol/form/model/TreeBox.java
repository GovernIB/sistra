package org.ibit.rol.form.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class TreeBox extends Campo {

    public TreeBox() {
        tipoValor = "java.lang.String[]";
    }

    private int altura;

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public boolean isIndexed() {
        return true;
    }
       
	private boolean expandirTree;

	public boolean isExpandirTree() {
		return expandirTree;
	}

	public void setExpandirTree(boolean expandirTree) {
		this.expandirTree = expandirTree;
	}

	/**
	 * Sobreescribimos para que se devuelva de forma ordenada:
	 * Padre - hijos
	 */
	@Override
	public List getAllValoresPosibles() {
		List res = new ArrayList();
		List vps = super.getAllValoresPosibles();
		
		addValoresPosiblesHijos(vps, res, null);
		
		return res;
	}

	private void addValoresPosiblesHijos(List valoresPosibles, List resultado, String codigoPadre) {
		for (Iterator it = valoresPosibles.iterator(); it.hasNext();) {
			ValorPosible vp = (ValorPosible) it.next();
			if ( (StringUtils.isEmpty(codigoPadre) &&  StringUtils.isEmpty(vp.getParentValor())) ||
				 (StringUtils.isNotEmpty(codigoPadre) && codigoPadre.equals(vp.getParentValor()))
				) {
				resultado.add(vp);
				addValoresPosiblesHijos(valoresPosibles, resultado, vp.getValor().toString());
			}			
		}		
	}
	
	
	
	
}

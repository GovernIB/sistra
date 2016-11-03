package es.caib.redose.model;

import java.io.Serializable;
import java.util.Date;

public class TestGenerarHbm implements Serializable 
{
	private String attr1;
	private Date attr2;
	private int campo1;
	
	public String getAttr1() {
		return attr1;
	}
	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	public Date getAttr2() {
		return attr2;
	}
	public void setAttr2(Date attr2) {
		this.attr2 = attr2;
	}
	public int getCampo1() {
		return campo1;
	}
	public void setCampo1(int campo1) {
		this.campo1 = campo1;
	}

}

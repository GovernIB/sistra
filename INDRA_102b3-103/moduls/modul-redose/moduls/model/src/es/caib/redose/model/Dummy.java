package es.caib.redose.model;

import java.io.Serializable;

public class Dummy implements Serializable 
{
	private Long id;
	private String attr1;
	private String attr2;
	
	public String getAttr1() {
		return attr1;
	}
	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	public String getAttr2() {
		return attr2;
	}
	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String toString()
	{
		return "id : " + id + "; attr1:  " + attr1 + "; attr2:  " + attr2;
	}

}

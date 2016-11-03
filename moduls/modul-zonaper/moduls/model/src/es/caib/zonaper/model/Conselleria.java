package es.caib.zonaper.model;

import java.io.Serializable;

public class Conselleria implements Serializable{

		private String codi;
		private String abreviatura;
		
		public String getAbreviatura() {
			return abreviatura;
		}
		public void setAbreviatura(String abreviatura) {
			this.abreviatura = abreviatura;
		}
		public String getCodi() {
			return codi;
		}
		public void setCodi(String codi) {
			this.codi = codi;
		}
		
		
}

/**
 * 
 */
package no.systema.ebooking.model.jsonjackson.order.childwindow;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import no.systema.main.model.jsonjackson.general.JsonAbstractGrandFatherRecord;
/**
 * @author oscardelatorre
 * @date Feb 08, 2017
 * 
 */
public class JsonEbookingCustomerRecord extends JsonAbstractGrandFatherRecord  {
	
	private String kundnr = null;
	public void setKundnr(String value){ this.kundnr = value;}
	public String getKundnr(){ return this.kundnr; }
	
	private String vadrnr = null;
	public void setVadrnr(String value){ this.vadrnr = value;}
	public String getVadrnr(){ return this.vadrnr; }
	
	private String navn = null;
	public void setNavn(String value){ this.navn = value;}
	public String getNavn(){ return this.navn; }
	
	private String gateAdr = null;
	public void setGateAdr(String value){ this.gateAdr = value;}
	public String getGateAdr(){ return this.gateAdr; }
	
	private String adresse2 = null;
	public void setAdresse2(String value){ this.adresse2 = value;}
	public String getAdresse2(){ return this.adresse2; }
	
	private String postnrSted = null;
	public void setPostnrSted(String value){ this.postnrSted = value;}
	public String getPostnrSted(){ return this.postnrSted; }
	
	private String land = null;
	public void setLand(String value){ this.land = value;}
	public String getLand(){ return this.land; }
	
	private String sonavn = null;
	public void setSonavn(String value){ this.sonavn = value;}
	public String getSonavn(){ return this.sonavn; }
	
	private String kualfa = null;
	public void setKualfa(String value){ this.kualfa = value;}
	public String getKualfa(){ return this.kualfa; }
	
	private String kundNavn = null;
	public void setKundNavn(String value){ this.kundNavn = value;}
	public String getKundNavn(){ return this.kundNavn; }
	
	private String kundPnSt = null;
	public void setKundPnSt(String value){ this.kundPnSt = value;}
	public String getKundPnSt(){ return this.kundPnSt; }
	
		//Delivery Address fields
		private String auxnavn = "";
		public void setAuxnavn(String value){ this.auxnavn = value;}
		public String getAuxnavn(){ return this.auxnavn; }
		
		private String auxtlf = "";
		public void setAuxtlf(String value){ this.auxtlf = value;}
		public String getAuxtlf(){ return this.auxtlf; }
		
		private String auxfax = "";
		public void setAuxfax(String value){ this.auxfax = value;}
		public String getAuxfax(){ return this.auxfax; }
		
		private String auxmail = "";
		public void setAuxmail(String value){ this.auxmail = value;}
		public String getAuxmail(){ return this.auxmail; }
		
	
	/**
	 * User for java reflection in other classes
	 * @return
	 * @throws Exception
	 */
	public List<Field> getFields() throws Exception{
		Class cl = Class.forName(this.getClass().getCanonicalName());
		Field[] fields = cl.getDeclaredFields();
		List<Field> list = Arrays.asList(fields);
		
		return list;
	}
	
}

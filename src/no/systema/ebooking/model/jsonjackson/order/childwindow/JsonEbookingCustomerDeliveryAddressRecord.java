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
 * @date Jan 30, 2017
 * 
 */
public class JsonEbookingCustomerDeliveryAddressRecord extends JsonAbstractGrandFatherRecord  {
	
	private String appCustnr = null;
	public void setAppCustnr(String value){ this.appCustnr = value;}
	public String getAppCustnr(){ return this.appCustnr; }
	
	private String applicationUser = null;
	public void setApplicationUser(String value){ this.applicationUser = value;}
	public String getApplicationUser(){ return this.applicationUser; }
	
	private String ownKundnr = null;
	public void setOwnKundnr(String value){ this.ownKundnr = value;}
	public String getOwnKundnr(){ return this.ownKundnr; }
	
	private String vadrnr = null;
	public void setVadrnr(String value){ this.vadrnr = value;}
	public String getVadrnr(){ return this.vadrnr; }
	
	private String vadrna = null;
	public void setVadrna(String value){ this.vadrna = value;}
	public String getVadrna(){ return this.vadrna; }
	
	private String vadrn1 = null;
	public void setVadrn1(String value){ this.vadrn1 = value;}
	public String getVadrn1(){ return this.vadrn1; }
		
	private String vadrn2 = null;
	public void setVadrn2(String value){ this.vadrn2 = value;}
	public String getVadrn2(){ return this.vadrn2; }
	
	private String vadrn3 = null;
	public void setVadrn3(String value){ this.vadrn3 = value;}
	public String getVadrn3(){ return this.vadrn3; }
	
	private String valand = null;
	public void setValand(String value){ this.valand = value;}
	public String getValand(){ return this.valand; }
	
	private String vakure = null;
	public void setVakure(String value){ this.vakure = value;}
	public String getVakure(){ return this.vakure; }
	
	private String vatlf = null;
	public void setVatlf(String value){ this.vatlf = value;}
	public String getVatlf(){ return this.vatlf; }
	
	private String vafax = null;
	public void setVafax(String value){ this.vafax = value;}
	public String getVafax(){ return this.vafax; }
	
	private String vamail = null;
	public void setVamail(String value){ this.vamail = value;}
	public String getVamail(){ return this.vamail; }
	
	
	
	
	
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

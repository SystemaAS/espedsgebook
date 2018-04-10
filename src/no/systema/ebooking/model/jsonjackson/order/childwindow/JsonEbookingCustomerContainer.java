/**
 * 
 */
package no.systema.ebooking.model.jsonjackson.order.childwindow;

import java.util.Collection;
/**
 * @author oscardelatorre
 * @date Feb 08, 2017
 */
public class JsonEbookingCustomerContainer {
	
	private String user = null;
	public void setUser(String value){ this.user = value;}
	public String getUser(){ return this.user; }
	
	private String ctype = null;
	public void setCtype(String value){ this.ctype = value;}
	public String getCtype(){ return this.ctype; }
	
	private String errMsg = null;
	public void setErrMsg(String value){ this.errMsg = value;}
	public String getErrMsg(){ return this.errMsg; }
	
	private Collection<JsonEbookingCustomerRecord> inqFkund = null;
	public void setInqFkund(Collection<JsonEbookingCustomerRecord> value){ this.inqFkund = value;}
	public Collection<JsonEbookingCustomerRecord> getInqFkund(){ return this.inqFkund; }
	
}

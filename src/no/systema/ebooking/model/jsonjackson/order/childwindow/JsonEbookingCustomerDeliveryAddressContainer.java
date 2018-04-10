/**
 * 
 */
package no.systema.ebooking.model.jsonjackson.order.childwindow;

import java.util.Collection;
/**
 * @author oscardelatorre
 * @date Jan 30, 2017
 */
public class JsonEbookingCustomerDeliveryAddressContainer {
	
	private String user = null;
	public void setUser(String value){ this.user = value;}
	public String getUser(){ return this.user; }
	
	private String ctype = null;
	public void setCtype(String value){ this.ctype = value;}
	public String getCtype(){ return this.ctype; }
	
	//this is an internal variable in order to know what type of partner we are searching for (shipper, consignee or Agent)
	private String wkundnr = null;
	public void setWkundnr(String value){ this.wkundnr = value;}
	public String getWkundnr(){ return this.wkundnr; }
	
	private String wkundnvn = null;
	public void setWkundnvn(String value){ this.wkundnvn = value;}
	public String getWkundnvn(){ return this.wkundnvn; }
	
	private String wvadrnr = null;
	public void setWvadrnr(String value){ this.wvadrnr = value;}
	public String getWvadrnr(){ return this.wvadrnr; }
	
	private String wvadrna = null;
	public void setWvadrna(String value){ this.wvadrna = value;}
	public String getWvadrna(){ return this.wvadrna; }
	
	private String maxv = null;
	public void setMaxv(String value){ this.maxv = value;}
	public String getMaxv(){ return this.maxv; }
	
	private String errMsg = null;
	public void setErrMsg(String value){ this.errMsg = value;}
	public String getErrMsg(){ return this.errMsg; }
	
	private Collection<JsonEbookingCustomerDeliveryAddressRecord> inqdeladdr = null;
	public void setInqdeladdr(Collection<JsonEbookingCustomerDeliveryAddressRecord> value){ this.inqdeladdr = value;}
	public Collection<JsonEbookingCustomerDeliveryAddressRecord> getInqdeladdr(){ return this.inqdeladdr; }
	
}

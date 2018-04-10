/**
 * 
 */
package no.systema.ebooking.model.jsonjackson;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author oscardelatorre
 * @date Jun 24, 2016
 */
public class JsonMainOrderListContainer {
	
	private String user = null;
	public void setUser(String value){ this.user = value;}
	public String getUser(){ return this.user; }
	
	private String kundnr = null;
	public void setKundnr(String value){ this.kundnr = value;}
	public String getKundnr(){ return this.kundnr; }
	
	private String knavn = null;
	public void setKnavn(String value){ this.knavn = value;}
	public String getKnavn(){ return this.knavn; }
	
	
	private String errMsg = null;
	public void setErrMsg(String value){ this.errMsg = value;}
	public String getErrMsg(){ return this.errMsg; }
	
	private Collection<JsonMainOrderListRecord> orderList = null;
	public void setOrderList(Collection<JsonMainOrderListRecord> value){ this.orderList = value;}
	public Collection<JsonMainOrderListRecord> getOrderList(){ return this.orderList; }
	
	private Collection<JsonMainOrderTypesNewRecord> orderTypesNew = null;
	public void setOrderTypesNew(Collection<JsonMainOrderTypesNewRecord> value){ this.orderTypesNew = value;}
	public Collection<JsonMainOrderTypesNewRecord> getOrderTypesNew(){ return this.orderTypesNew; }
	
}

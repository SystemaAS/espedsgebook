/**
 * 
 */
package no.systema.ebooking.filter;

import java.lang.reflect.Field;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * This search class is used at the GUI search behavior
 * It is MANDATORY to have the same attribute name convention as the JSON-object fetched from the JSON-payload at the back-end.
 * The reason for this is the java-reflection mechanism used when searching (since no SQL or other mechanism is used)
 * By using java reflection to match the object fields, these 2 (the JSON object and its SearchFilter object) must have the same attribute name 
 * 
 * @author oscardelatorre
 * @date   Jun 24, 2016
 * 
 */
public class SearchFilterEbookingMainList {
	private static final Logger logger = Logger.getLogger(SearchFilterEbookingMainList.class.getName());
	
	private String orderNr = null;
	public void setOrderNr(String value) {  this.orderNr = value; }
	public String getOrderNr() { return this.orderNr;}
	
	private String date = null;
	public void setDate(String value) {  this.date = value; }
	public String getDate() { return this.date;}
	
	private String fromDate = null;
	public void setFromDate(String value) {  this.fromDate = value; }
	public String getFromDate() { return this.fromDate;}
	
	private String toDate = null;
	public void setToDate(String value) {  this.toDate = value; }
	public String getToDate() { return this.toDate;}
	
	private String sender = null;
	public void setSender(String value) {  this.sender = value; }
	public String getSender() { return this.sender;}
	
	private String receiver = null;
	public void setReceiver(String value) {  this.receiver = value; }
	public String getReceiver() { return this.receiver;}
	
	private String from = null;
	public void setFrom(String value) {  this.from = value; }
	public String getFrom() { return this.from;}
	
	private String to = null;
	public void setTo(String value) {  this.to = value; }
	public String getTo() { return this.to;}
	
	
	/**
	 * Gets the populated values by reflection
	 * @param searchFilte
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> getPopulatedFields() throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		
		Class cl = Class.forName(this.getClass().getCanonicalName());
		Field[] fields = cl.getDeclaredFields();
		List<Field> list = Arrays.asList(fields);
		for(Field field : list){
			field.setAccessible(true);
			//logger.info("FIELD NAME: " + field.getName() + "VALUE:" + (String)field.get(this));
			String value = (String)field.get(this);
			if(value!=null && !"".equals(value)){
				//logger.info(field.getName() + " Value:" + value);
				map.put(field.getName(), value);
			}
		}
		return map;
	}
}

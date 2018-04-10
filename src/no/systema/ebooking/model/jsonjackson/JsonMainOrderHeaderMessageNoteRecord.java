/**
 * 
 */
package no.systema.ebooking.model.jsonjackson;

import no.systema.main.model.jsonjackson.general.JsonAbstractGrandFatherRecord;
import java.util.*;
import java.lang.reflect.Field;
/**
 * @author oscardelatorre
 * @date Feb 07, 2017
 * 
 *
 */
public class JsonMainOrderHeaderMessageNoteRecord extends JsonAbstractGrandFatherRecord{
	
	private String frtavd = null;
	public void setFrtavd(String value) {  this.frtavd = value; }
	public String getFrtavd() {return this.frtavd;}
	
	private String frtopd = null;
	public void setFrtopd(String value) {  this.frtopd = value; }
	public String getFrtopd() { return this.frtopd; }
	
	private String frreff = null;
	public void setFrreff(String value) {  this.frreff = value; }
	public String getFrreff() {return this.frreff;}
	
	private String frunik = null;
	public void setFrunik(String value) {  this.frunik = value; }
	public String getFrunik() { return this.frunik; }
	
	private String frtdt = null;
	public void setFrtdt(String value) {  this.frtdt = value; }
	public String getFrtdt() {return this.frtdt;}
	
	private String frtli = null;
	public void setFrtli(String value) {  this.frtli = value; }
	public String getFrtli() {return this.frtli;}
	
	private String frttxt = null;
	public void setFrttxt(String value) {  this.frttxt = value; }
	public String getFrttxt() {return this.frttxt;}
	
	private String frtkod = null;
	public void setFrtkod(String value) {  this.frtkod = value; }
	public String getFrtkod() {return this.frtkod;}
	
	private String frtles = null;
	public void setFrtles(String value) {  this.frtles = value; }
	public String getFrtles() {return this.frtles;}
	
	private String frtfak = null;
	public void setFrtfak(String value) {  this.frtfak = value; }
	public String getFrtfak() {return this.frtfak;}
	
	
	/**
	 * Used for java reflection in other classes
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

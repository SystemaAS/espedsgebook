/**
 * 
 */
package no.systema.ebooking.model.jsonjackson;

import no.systema.main.model.jsonjackson.general.JsonAbstractGrandFatherRecord;
import java.util.*;
import java.lang.reflect.Field;
/**
 * @author oscardelatorre
 * @date Nov 22, 2017
 * 
 *
 */
public class JsonMainOrderHeaderArchivedDocsRecord extends JsonAbstractGrandFatherRecord{
	
	
	private String doctxt = null;
	public void setDoctxt(String value) {  this.doctxt = value; }
	public String getDoctxt() {return this.doctxt;}
	
	private String doclnk = null;
	public void setDoclnk(String value) {  this.doclnk = value; }
	public String getDoclnk() {return this.doclnk;}
	
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

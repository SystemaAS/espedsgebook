/**
 * 
 */
package no.systema.ebooking.model.jsonjackson;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import no.systema.main.model.jsonjackson.general.JsonAbstractGrandFatherRecord;
/**
 * @author oscardelatorre
 * @date Jan 06, 2017
 * 
 */
public class JsonMainOrderTypesNewRecord extends JsonAbstractGrandFatherRecord {
	
	private String newAvd = ""; //Must be empty string instead of null (back end requir.)
	public void setNewAvd(String value){ this.newAvd = value;}
	public String getNewAvd(){ return this.newAvd; }
	
	private String newModul = ""; //Must be empty string instead of null (back end requir.)
	public void setNewModul(String value){ this.newModul = value;}
	public String getNewModul(){ return this.newModul; }
	
	private String newModul2 = ""; //Must be empty string instead of null (back end requir.)
	public void setNewModul2(String value){ this.newModul2 = value;}
	public String getNewModul2(){ return this.newModul2; }
	
	private String newLandKode = ""; //Must be empty string instead of null (back end requir.)
	public void setNewLandKode(String value){ this.newLandKode = value;}
	public String getNewLandKode(){ return this.newLandKode; }
	
	private String newSideSK = ""; //Must be empty string instead of null (back end requir.)
	public void setNewSideSK(String value){ this.newSideSK = value;}
	public String getNewSideSK(){ return this.newSideSK; }
	
	private String newText = ""; //Must be empty string instead of null (back end requir.)
	public void setNewText(String value){ this.newText = value;}
	public String getNewText(){ return this.newText; }
	
	
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

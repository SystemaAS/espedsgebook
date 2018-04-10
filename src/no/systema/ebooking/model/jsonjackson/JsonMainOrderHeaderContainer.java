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
 * @date Jan 6, 2017
 */
public class JsonMainOrderHeaderContainer {
	
	private String user = null;
	public void setUser(String value){ this.user = value;}
	public String getUser(){ return this.user; }
	
	private String hereff = null;
	public void setHereff(String value){ this.hereff = value;}
	public String getHereff(){ return this.hereff; }
	
	private String heunik = null;
	public void setHeunik(String value){ this.heunik = value;}
	public String getHeunik(){ return this.heunik; }
	
	
	private String errMsg = null;
	public void setErrMsg(String value){ this.errMsg = value;}
	public String getErrMsg(){ return this.errMsg; }
	
	private Collection<JsonMainOrderHeaderRecord> oneorder = null;
	public void setOneorder(Collection<JsonMainOrderHeaderRecord> value){ this.oneorder = value;}
	public Collection<JsonMainOrderHeaderRecord> getOneorder(){ return this.oneorder; }
	
	
}

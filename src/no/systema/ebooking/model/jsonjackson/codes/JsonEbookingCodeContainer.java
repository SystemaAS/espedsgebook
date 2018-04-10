/**
 * 
 */
package no.systema.ebooking.model.jsonjackson.codes;

import java.util.Collection;


/**
 * General Code Container for Ebooking general codes
 * 
 * 
 *
 * @author oscardelatorre
 * @date Jan 07, 2017
 *
 */
public class JsonEbookingCodeContainer {
	private String user = null;
	public void setUser(String value){ this.user = value;}
	public String getUser(){ return this.user; }
	
	private String ie = null;
	public void setIe(String value){ this.ie = value;}
	public String getIe(){ return this.ie; }
	
	private String typ = null;
	public void setTyp(String value){ this.typ = value;}
	public String getTyp(){ return this.typ; }
	
	private String errMsg = null;
	public void setErrMsg(String value){ this.errMsg = value;}
	public String getErrMsg(){ return this.errMsg; }
	
	private Collection<JsonEbookingCodeRecord> kodlista = null;
	public void setKodlista(Collection<JsonEbookingCodeRecord> value){ this.kodlista = value;}
	public Collection<JsonEbookingCodeRecord> getKodlista(){ return this.kodlista; }
	
}

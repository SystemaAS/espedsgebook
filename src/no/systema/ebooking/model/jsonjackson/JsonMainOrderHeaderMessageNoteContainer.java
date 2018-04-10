/**
 * 
 */
package no.systema.ebooking.model.jsonjackson;

import java.util.Collection;

/**
 * @author oscardelatorre
 * @date Feb 07, 2017
 *
 */
public class JsonMainOrderHeaderMessageNoteContainer {
	private String user = null;
	public void setUser(String value) {  this.user = value; }
	public String getUser() { return this.user;}
	
	private String avd = null;
	public void setAvd(String value) {  this.avd = value; }
	public String getAvd() { return this.avd;}
	
	private String opd = null;
	public void setOpd(String value) {  this.opd = value; }
	public String getOpd() { return this.opd;}
	
	private String errMsg = null;
	public void setErrMsg(String value) {  this.errMsg = value; }
	public String getErrMsg() { return this.errMsg;}
	
	private Collection<JsonMainOrderHeaderMessageNoteRecord> freetextlist;
	public void setFreetextlist(Collection<JsonMainOrderHeaderMessageNoteRecord> value){ this.freetextlist = value; }
	public Collection<JsonMainOrderHeaderMessageNoteRecord> getFreetextlist(){ return freetextlist; }
	
	
}

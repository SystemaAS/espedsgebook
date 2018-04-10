/**
 * 
 */
package no.systema.ebooking.model.jsonjackson;

import java.util.Collection;

/**
 * @author oscardelatorre
 * @date Nov 22, 2017
 *
 */
public class JsonMainOrderHeaderArchivedDocsContainer {
	private String user = null;
	public void setUser(String value) {  this.user = value; }
	public String getUser() { return this.user;}
	                  
	private String wstur = null;
	public void setWstur(String value) {  this.wstur = value; }
	public String getWstur() { return this.wstur;}
	
	private String wsunik = null;
	public void setWsunik(String value) {  this.wsunik = value; }
	public String getWsunik() { return this.wsunik;}
	
	private String errMsg = null;
	public void setErrMsg(String value) {  this.errMsg = value; }
	public String getErrMsg() { return this.errMsg;}
	
	private Collection<JsonMainOrderHeaderArchivedDocsRecord> getdoctrip;
	public void setGetdoctrip(Collection<JsonMainOrderHeaderArchivedDocsRecord> value){ this.getdoctrip = value; }
	public Collection<JsonMainOrderHeaderArchivedDocsRecord> getGetdoctrip(){ return getdoctrip; }
	
	
}

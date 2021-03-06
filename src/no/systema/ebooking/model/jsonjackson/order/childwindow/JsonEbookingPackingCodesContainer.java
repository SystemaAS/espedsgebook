/**
 * 
 */
package no.systema.ebooking.model.jsonjackson.order.childwindow;

import java.util.Collection;


/**
 * @author oscardelatorre
 * @date Feb 03, 2017
 *
 */
public class JsonEbookingPackingCodesContainer {
	
	private String callerLineCounter = null;
	public void setCallerLineCounter(String value) {  this.callerLineCounter = value; }
	public String getCallerLineCounter() { return this.callerLineCounter;}
	
	private String singleRecordFlag = null;
	public void setSingleRecordFlag(String value) {  this.singleRecordFlag = value; }
	public String getSingleRecordFlag() { return this.singleRecordFlag;}
	
	private String user = null;
	public void setUser(String value) {  this.user = value; }
	public String getUser() { return this.user;}
	
	private String kode = null;
	public void setKode(String value) {  this.kode = value; }
	public String getKode() { return this.kode;}
	
	private String tekst = null;
	public void setTekst(String value) {  this.tekst = value; }
	public String getTekst() { return this.tekst;}
	
	
	private String fullInfo = null;
	public void setFullInfo(String value) {  this.fullInfo = value; }
	public String getFullInfo() { return this.fullInfo;}
	
	private String errMsg = null;
	public void setErrMsg(String value) {  this.errMsg = value; }
	public String getErrMsg() { return this.errMsg;}
	
	private Collection<JsonEbookingPackingCodesRecord> forpaknKoder;
	public void setForpaknKoder(Collection<JsonEbookingPackingCodesRecord> value){ this.forpaknKoder = value; }
	public Collection<JsonEbookingPackingCodesRecord> getForpaknKoder(){ return forpaknKoder; }
	
	
	
}
